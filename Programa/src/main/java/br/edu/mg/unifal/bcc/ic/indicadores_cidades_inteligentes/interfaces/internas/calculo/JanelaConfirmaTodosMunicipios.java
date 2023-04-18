/*Copyright (C) <2022> <Gabriel Takahiro Toma de Lima>
 
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.
 You should have received a copy of the GNU General Public License
 along with this program. If not, see <https://www.gnu.org/licenses/>.
 
Versão em português:

Este programa é um software livre: você pode redistribuí-lo e/ou
modificá-lo sob os termos da Licença Pública Geral GNU, conforme
publicado pela Free Software Foundation, seja a versão 3 da Licença
ou (a seu critério) qualquer versão posterior.
Este programa é distribuído na esperança de que seja útil,
mas SEM QUALQUER GARANTIA; sem a garantia implícita de
COMERCIALIZAÇÃO OU ADEQUAÇÃO A UM DETERMINADO PROPÓSITO. Veja a
Licença Pública Geral GNU para obter mais detalhes.
Você deve ter recebido uma cópia da Licença Pública Geral GNU
junto com este programa. Se não, veja <https://www.gnu.org/licenses/>.
*/
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.calculo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.calculo.resultado.MostrarResultadoParaTodos;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.CalculoIndicador;

/**
 * Classe responsável pela interface que confirma o cálculo dos indicadores
 * selecionados para todos os municípios
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class JanelaConfirmaTodosMunicipios extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Executa a interface que confirma o cálculo dos indicadores selecionados para todos os municípios
	 * @param data ano que será utilizado para calcular os indicadores
	 * @param recalcular true caso seja necessário recalcular o indicador
	 * @param listaIndicadoresSelecionados lista com os indicadores selecionados
	 * @param valorRetroativo anos a mais utilizados para a busca retroativa das variáveis
	 */
	public JanelaConfirmaTodosMunicipios(String data, boolean recalcular,
			ArrayList<Integer> listaIndicadoresSelecionados, int valorRetroativo) {
		setBounds(100, 100, 600, 173);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel(
				"Deseja realizar o cálculo para todos os municípios para o ano de " + data + "?");
		if (recalcular) {
			lblNewLabel = new JLabel(
					"Deseja recalcular os indicadores para todos os municípios para o ano de " + data + "?");
		}
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 564, 30);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 82, 584, 52);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("Sim");
				okButton.addActionListener(new ActionListener() {
					/**
					 * Executa o cálculo dos indicadores selecionados para todos os municípios
					 */
					public void actionPerformed(ActionEvent e) {
						/*try {
							CalculoIndicador.calcularTodosMunicipios(data, recalcular, listaIndicadoresSelecionados,
									valorRetroativo);
							JanelaPrincipal.abrirJanelas(new MostrarResultadoParaTodos(listaIndicadoresSelecionados,
									data, valorRetroativo, recalcular));
							dispose();
						} catch (Exception erro) {
							new JanelaMensagem(erro.getMessage());
						}*/
						dispose();
						ProgressoCalculo progressoCalculo = new ProgressoCalculo("");
						JanelaPrincipal.abrirJanelas(progressoCalculo);
						
						Runnable tarefa = new Runnable() {
							public void run() {
								try {
									CalculoIndicador.calculaIndicadoresTodosMunicipios(listaIndicadoresSelecionados, data, recalcular, valorRetroativo);

									SwingUtilities.invokeLater(new Runnable() {
										public void run() {
											JanelaPrincipal.abrirJanelas(new MostrarResultadoParaTodos(
													listaIndicadoresSelecionados, data, valorRetroativo, recalcular));
										}
									});
									progressoCalculo.dispose();
								} catch (Exception erro) {
									SwingUtilities.invokeLater(new Runnable() {
										public void run() {
											progressoCalculo.dispose();
											new JanelaMensagem(erro.getMessage());
										}
									});
								}
							}
						};
						JanelaPrincipal.executor.execute(tarefa);
					}
				});
				okButton.setBounds(133, 11, 76, 30);
				okButton.setFont(new Font("Arial", Font.PLAIN, 16));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Não");
				cancelButton.setBounds(355, 11, 71, 30);
				cancelButton.setFont(new Font("Arial", Font.PLAIN, 16));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);

				JLabel lblAnosAMais = new JLabel("Anos a mais para busca retroativa das variáveis: " + valorRetroativo);
				lblAnosAMais.setHorizontalAlignment(SwingConstants.LEFT);
				lblAnosAMais.setFont(new Font("Arial", Font.PLAIN, 16));
				lblAnosAMais.setBounds(10, 41, 519, 30);
				contentPanel.add(lblAnosAMais);
				cancelButton.addActionListener(new ActionListener() {
					/**
					 * Fecha essa interface
					 */
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(rootPaneCheckingEnabled);
	}

}
