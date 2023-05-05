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

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelasInternas;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.Tabelas;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;

/**
 * Classe responsável pela interface que mostra as variáveis.
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class MostrarVariaveis extends JanelasInternas {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTable table;

	/**
	 * Executa a interface que mostra as variáveis.
	 */
	public MostrarVariaveis() {
		setTitle("Variáveis");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 720, 262);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblVariaveis = new JLabel("Variáveis de indicadores de cidades inteligentes");
		lblVariaveis.setHorizontalAlignment(SwingConstants.CENTER);
		lblVariaveis.setFont(new Font("Arial", Font.PLAIN, 16));

		JScrollPane scrollPaneTabela = new JScrollPane();

		table = new JTable();
		scrollPaneTabela.setViewportView(table);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(5)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblVariaveis, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
								.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE))
						.addGap(5)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(6)
						.addComponent(lblVariaveis, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGap(8).addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
						.addGap(15)));
		contentPane.setLayout(gl_contentPane);
		try {
			Tabelas.mostrarVariaveis(table);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(true);
		setResizable(true);
		setClosable(true);
		setIconifiable(true);
	}

	/**
	 * Este método retorna a tabela dos indicadores disponíveis na classe.
	 * 
	 * @return A tabela dos indicadores
	 */
	public static JTable getTable() {
		return table;
	}

	/**
	 * Sobrescreve o método da classe JanelasInternas para armazenar a referência à
	 * janela MostrarVariaveis na JanelaPrincipal.
	 */
	@Override
	protected void abrirJanelaJDesktopPane(JInternalFrame janela) {
		JanelaPrincipal.setJanelaMostrarVariaveis((MostrarVariaveis) janela);
	}

	/**
	 * Cria uma nova instância da classe MostrarVariaveis.
	 */
	@Override
	protected JInternalFrame instanciaClasseAberta() {
		return new MostrarVariaveis();
	}
}
