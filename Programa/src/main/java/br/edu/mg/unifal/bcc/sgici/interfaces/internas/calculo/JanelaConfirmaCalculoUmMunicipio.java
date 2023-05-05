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
package br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo;

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
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.sgici.calculo.CalculaIndicador;

/**
 * Classe responsável pela criação da interface gráfica que confirma o cálculo
 * dos indicadores selecionados para um município.
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class JanelaConfirmaCalculoUmMunicipio extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	/**
	 * Cria uma interface gráfica que permite a confirmação do cálculo dos
	 * indicadores selecionados para um município. Essa interface apresenta uma
	 * mensagem de confirmação e dois botões, um para confirmar e outro para
	 * cancelar a ação.
	 * 
	 * @param cep                          cep do município que deseja calcular os
	 *                                     indicadores
	 * @param data                         ano no formato "yyyy", representando o
	 *                                     ano que será utilizado para calcular os
	 *                                     indicadores.
	 * @param nomeMunicipio                nome do município com a sigla da UF
	 * @param recalcular                   booleano indicando se é necessário
	 *                                     recalcular o indicador. Se true, o
	 *                                     indicador será recalculado; caso
	 *                                     contrário, será reutilizado o valor já
	 *                                     calculado anteriormente.
	 * @param listaIndicadoresSelecionados lista de inteiros representando os
	 *                                     indicadores selecionados.
	 * @param valorRetroativo              quantidade de anos a mais utilizados para
	 *                                     a busca retroativa das variáveis.
	 */
	public JanelaConfirmaCalculoUmMunicipio(String cep, String data, String nomeMunicipio, boolean recalcular,
			ArrayList<Integer> listaIndicadoresSelecionados, int valorRetroativo) {
		setBounds(100, 100, 555, 173);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Deseja fazer a busca para o município: " + nomeMunicipio + "?");
		if (recalcular) {
			lblNewLabel = new JLabel("Deseja recalcular os indicadores para o município: " + nomeMunicipio + "?");
		}
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 519, 30);
		contentPanel.add(lblNewLabel);
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 82, 539, 52);
		contentPanel.add(buttonPane);
		buttonPane.setLayout(null);

		JButton okButton = new JButton("Sim");
		okButton.addActionListener(new ActionListener() {
			/**
			 * Executa o cálculo dos indicadores selecionados para um município
			 */

			public void actionPerformed(ActionEvent e) {
				dispose();
				CalculaIndicador.calculoIndicadoresUmMunicipio(listaIndicadoresSelecionados, valorRetroativo, data,
						recalcular, nomeMunicipio, cep);
			}
		});

		okButton.setBounds(133, 11, 76, 30);
		okButton.setFont(new Font("Arial", Font.PLAIN, 16));
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Não");
		cancelButton.setBounds(327, 11, 71, 30);
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

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
	}

}
