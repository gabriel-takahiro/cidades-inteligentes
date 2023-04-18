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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.indicadores;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.Tabelas;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Indicador;

/**
 * Classe responsável pela interface que inclui indicadores no método de cálculo
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class JanelaIncluirIndicador extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableIndicadores;
	private static JTextField textFieldCodigo;

	/**
	 * Executa a interface que inclui indicadores no método de cálculo
	 * 
	 * @param cadastro true para interface do cadastro de indicadores e false para
	 *                 interface que atualiza indicadores
	 */
	public JanelaIncluirIndicador(boolean cadastro) {
		setTitle("Incluir indicador");
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Selecione o indicador que deseja incluir:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 292, 23);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 564, 179);
		contentPane.add(scrollPane);

		tableIndicadores = new JTable();
		tableIndicadores.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPane.setViewportView(tableIndicadores);

		JButton btnNewButton = new JButton("Adicionar esse indicador");
		btnNewButton.addActionListener(new ActionListener() {
			/**
			 * Adiciona o indicador ao método de cálculo
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					Indicador indicador = Indicador.buscarCodigoEMetodo(Integer.parseInt(textFieldCodigo.getText()));
					if (Integer.toString(indicador.getCodigo()).equals(null)) {
						new JanelaMensagem("É necessário colocar um indicador válido");
						return;
					}
				} catch (Exception erro) {
					new JanelaMensagem("É necessário colocar um indicador válido");
					return;
				}
				if (cadastro) {
					CadastrarIndicadores.colocarIndicador(textFieldCodigo.getText());
					dispose();
					return;
				}
				JanelaAtualizarIndicadores.colocarIndicador(textFieldCodigo.getText());
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton.setBounds(338, 227, 236, 25);
		contentPane.add(btnNewButton);

		textFieldCodigo = new JTextField();
		textFieldCodigo.setBounds(172, 230, 130, 23);
		contentPane.add(textFieldCodigo);
		textFieldCodigo.setColumns(10);

		JLabel lblCdigo = new JLabel("Código do indicador:");
		lblCdigo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCdigo.setBounds(10, 230, 152, 23);
		contentPane.add(lblCdigo);

		Tabelas.mostrarIndicadores(tableIndicadores);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setAlwaysOnTop(rootPaneCheckingEnabled);
	}
}
