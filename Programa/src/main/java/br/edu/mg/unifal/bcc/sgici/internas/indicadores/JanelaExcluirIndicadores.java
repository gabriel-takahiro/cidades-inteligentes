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

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.Tabelas;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.sgici.modelo.Indicador;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * Classe responsável pela interface que exclui os indicadores
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class JanelaExcluirIndicadores extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JPanel buttonPane;

	/**
	 * Executa a interface que exclui os indicadores
	 * @param listaIndicadoresSelecionados lista dos indicadores selecionados para exclusão
	 */
	public JanelaExcluirIndicadores(ArrayList<Integer> listaIndicadoresSelecionados) {
		setTitle("Excluir indicadores");
		setBounds(100, 100, 670, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("Deseja excluir os indicadores:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		{
			buttonPane = new JPanel();
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("Sim");
				okButton.addActionListener(new ActionListener() {
					/**
					 * Excluir indicadores selecionados
					 */
					public void actionPerformed(ActionEvent e) {
						try {
							Indicador.excluir(listaIndicadoresSelecionados);
							JanelaPrincipal.atualizarIndicadores();
							dispose();
							new JanelaMensagem("Excluído com sucesso!");
						} catch (Exception erroExcluir) {
							JanelaPrincipal.atualizarIndicadores();
							dispose();
							new JanelaMensagem(erroExcluir.getMessage());
						}
					}
				});
				okButton.setBounds(207, 2, 100, 30);
				okButton.setFont(new Font("Arial", Font.PLAIN, 16));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Não");
				cancelButton.addActionListener(new ActionListener() {
					/**
					 * Fechar essa interface
					 */
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setBounds(317, 2, 100, 30);
				cancelButton.setFont(new Font("Arial", Font.PLAIN, 16));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		JScrollPane scrollPane = new JScrollPane();

		table = new JTable();
		scrollPane.setViewportView(table);

		table.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		
		Tabelas.centralizaTabela(table);
		
		try {
			Indicador.mostrarIndicadores(table, listaIndicadoresSelecionados);
		} catch (Exception e) {
			dispose();
			new JanelaMensagem("É necessário selecionar pelo menos 1 indicador.");
			return;
		}

		JSeparator separator = new JSeparator();
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)))
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(10)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 634, GroupLayout.PREFERRED_SIZE))
				.addComponent(buttonPane, GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(28)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)))
					.addGap(10)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(3)
					.addComponent(buttonPane, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
		);
		contentPanel.setLayout(gl_contentPanel);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		setAlwaysOnTop(rootPaneCheckingEnabled);
	}
}
