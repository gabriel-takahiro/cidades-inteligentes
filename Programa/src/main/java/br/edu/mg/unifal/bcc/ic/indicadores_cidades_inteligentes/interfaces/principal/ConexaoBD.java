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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.JanelaMensagem;

/**
 * Classe responsável pela interface que faz o login no banco de dados
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class ConexaoBD extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPasswordField passwordFieldSenha;
	private JTextField textFieldUsuario;
	private JCheckBox chckbxNewCheckBox;
	private JTextField txtIc;

	/**
	 * Executa a interface responsável pelo login no banco de dados
	 * @param listaMenu lista com os menus
	 * @param janelaPrincipal interface principal do programa
	 * @param desktopPane interface principal que permite multiplas interfaces dentro dela
	 */
	public ConexaoBD(List<JMenu> listaMenu, JanelaPrincipal janelaPrincipal, JDesktopPane desktopPane) {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 375, 287);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		passwordFieldSenha = new JPasswordField("senha123");
		passwordFieldSenha.setBounds(149, 103, 200, 30);
		passwordFieldSenha.setFont(new Font("Arial", Font.PLAIN, 16));

		textFieldUsuario = new JTextField("postgres");
		textFieldUsuario.setBounds(149, 62, 200, 30);
		textFieldUsuario.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldUsuario.setColumns(10);

		JLabel lblUsuario = new JLabel("Usuário:");
		lblUsuario.setBounds(10, 61, 129, 30);
		lblUsuario.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(10, 102, 129, 30);
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnConectar = new JButton("Conectar");
		btnConectar.setBounds(10, 216, 339, 30);
		btnConectar.addActionListener(new ActionListener() {
			/**
			 * Faz o conexão com o banco de dados
			 */
			public void actionPerformed(ActionEvent e) {
				String senha = new String(passwordFieldSenha.getPassword());
				if (textFieldUsuario.getText().isBlank()) {
					new JanelaMensagem("O campo usuário não pode ser nulo.");
					return;
				}
				if (senha.isBlank()) {
					new JanelaMensagem("O campo senha não pode ser nulo.");
					return;
				}

				try {
					new ConnectionFactory(textFieldUsuario.getText(), new String(passwordFieldSenha.getPassword()),
							txtIc.getText());
					listaMenu.forEach(menu -> menu.setEnabled(true));
					janelaPrincipal.instanciarJanelas(chckbxNewCheckBox.isSelected());
					dispose();
				} catch (Exception erro) {
					System.out.println(erro);
					new JanelaMensagem("Usuário e/ou senha e/ou tabela inválidos.");
				}
			}
		});
		btnConectar.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(10, 11, 339, 30);
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPane.setLayout(null);
		contentPane.add(passwordFieldSenha);
		contentPane.add(textFieldUsuario);
		contentPane.add(lblUsuario);
		contentPane.add(lblSenha);
		contentPane.add(btnConectar);
		contentPane.add(lblLogin);

		chckbxNewCheckBox = new JCheckBox("Abrir tutorial");
		chckbxNewCheckBox.setFont(new Font("Arial", Font.PLAIN, 16));
		chckbxNewCheckBox.setBounds(10, 179, 284, 30);
		contentPane.add(chckbxNewCheckBox);

		JLabel lblBancoDados = new JLabel("Banco de dados:");
		lblBancoDados.setFont(new Font("Arial", Font.PLAIN, 16));
		lblBancoDados.setBounds(10, 145, 129, 30);
		contentPane.add(lblBancoDados);

		txtIc = new JTextField("indicadores");
		txtIc.setFont(new Font("Arial", Font.PLAIN, 16));
		txtIc.setColumns(10);
		txtIc.setBounds(149, 145, 200, 30);
		contentPane.add(txtIc);

		setLocation(0, 0);
		setVisible(true);
		setResizable(false);
	}
}
