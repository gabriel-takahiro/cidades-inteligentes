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
package br.edu.mg.unifal.bcc.sgici.internas.indicadores;

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

import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.Tabelas;
import br.edu.mg.unifal.bcc.sgici.modelo.Indicador;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

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

		JLabel lblNewLabel = new JLabel("Selecione o indicador que deseja incluir:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));

		JScrollPane scrollPane = new JScrollPane();

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

		textFieldCodigo = new JTextField();
		textFieldCodigo.setColumns(10);

		JLabel lblCdigo = new JLabel("Código do indicador:");
		lblCdigo.setFont(new Font("Arial", Font.PLAIN, 16));

		Tabelas.mostrarIndicadores(tableIndicadores);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblCdigo, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(textFieldCodigo, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addGap(36)
							.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)))
					.addGap(5))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
					.addGap(8)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(lblCdigo, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(textFieldCodigo, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(3))
		);
		contentPane.setLayout(gl_contentPane);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(true);
		setLocationRelativeTo(null);
		setVisible(true);
		setAlwaysOnTop(rootPaneCheckingEnabled);
	}
}
