package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas;

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
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;

/**
 * Classe responsável pela interface que faz o login no banco de dados
 * @author Gabriel Takahiro
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

		passwordFieldSenha = new JPasswordField("");
		passwordFieldSenha.setBounds(149, 103, 200, 30);
		passwordFieldSenha.setFont(new Font("Arial", Font.PLAIN, 16));

		textFieldUsuario = new JTextField("");
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
					ConnectionFactory.iniciarConexao();
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

		txtIc = new JTextField("");
		txtIc.setFont(new Font("Arial", Font.PLAIN, 16));
		txtIc.setColumns(10);
		txtIc.setBounds(149, 145, 200, 30);
		contentPane.add(txtIc);

		setLocation(0, 0);
		setVisible(true);
		setResizable(false);
	}
}
