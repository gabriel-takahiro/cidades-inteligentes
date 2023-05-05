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
package br.edu.mg.unifal.bcc.sgici.interfaces.internas.variaveis;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
import br.edu.mg.unifal.bcc.sgici.modelo.Variavel;

/**
 * Classe responsável pela interface que exclui as variáveis
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class JanelaExcluirVariaveis extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Executa a interface que exclui as variáveis
	 * 
	 * @param listaVariaveis lista de variáveis a serem excluídas
	 */
	public JanelaExcluirVariaveis(List<Variavel> listaVariaveis) {
		setTitle("Excluir variaveis");
		setBounds(100, 100, 670, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Deseja excluir as variáveis:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 634, 30);
		contentPanel.add(lblNewLabel);
		JPanel buttonPane = new JPanel();
		buttonPane.setBounds(0, 222, 644, 35);
		contentPanel.add(buttonPane);
		buttonPane.setLayout(null);
		
		JButton okButton = new JButton("Sim");
		okButton.addActionListener(new ActionListener() {
			/**
			 * Excluir variáveis
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					Variavel.excluir(listaVariaveis);
					JanelaPrincipal.atualizarVariaveis();
					dispose();
					new JanelaMensagem("Excluído com sucesso!");
				} catch (Exception erroExcluir) {
					JanelaPrincipal.atualizarVariaveis();
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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 39, 634, 168);
		contentPanel.add(scrollPane);

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
			Variavel.mostrarVariaveis(table, listaVariaveis);
		} catch (Exception e) {
			dispose();
			new JanelaMensagem("É necessário selecionar pelo menos 1 variável.");
			return;
		}

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 217, 634, 2);
		contentPanel.add(separator);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(rootPaneCheckingEnabled);
	}
}
