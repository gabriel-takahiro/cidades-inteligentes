package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaConfirmaCadastroVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Variavel;

/**
 * Classe responsável pela interface que cadastra as variáveis
 * @author Gabriel Takahiro
 *
 */
public class CadastrarVariaveis extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private static JTextField textFieldCodigoVariavel;
	private static JTextField textField_1CodigoBanco;
	private static JComboBox<String> comboBoxTipoDeBanco;
	private static JTextArea textAreaNomeVariavel;
	private static JComboBox<String> comboBox_1Atualizacao;

	/**
	 * Executa a interface responsável pelo cadastro das variáveis
	 */
	public CadastrarVariaveis() {
		setBounds(100, 100, 450, 300);

		setTitle("Cadastrar variável");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 228);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabelCodigoVariavel = new JLabel("Código da variável:");
		lblNewLabelCodigoVariavel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabelCodigoVariavel.setBounds(10, 11, 140, 30);
		contentPane.add(lblNewLabelCodigoVariavel);

		textFieldCodigoVariavel = new JTextField();
		textFieldCodigoVariavel.setToolTipText("Esse campo não pode ser nulo.");
		textFieldCodigoVariavel.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldCodigoVariavel.setBounds(155, 11, 77, 30);
		contentPane.add(textFieldCodigoVariavel);
		textFieldCodigoVariavel.setColumns(10);

		textFieldCodigoVariavel.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			}
		});

		JLabel lblTipoDeBanco = new JLabel("Tipo de banco:");
		lblTipoDeBanco.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTipoDeBanco.setBounds(239, 11, 106, 30);
		contentPane.add(lblTipoDeBanco);

		comboBoxTipoDeBanco = new JComboBox<String>();
		comboBoxTipoDeBanco.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBoxTipoDeBanco.setModel(new DefaultComboBoxModel<String>(new String[] { "Sidra", "BD" }));
		comboBoxTipoDeBanco.setSelectedIndex(0);
		comboBoxTipoDeBanco.setBounds(351, 11, 77, 30);
		contentPane.add(comboBoxTipoDeBanco);

		JLabel lblNomeDaVarivel = new JLabel("Nome da variável:");
		lblNomeDaVarivel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNomeDaVarivel.setBounds(10, 62, 131, 30);
		contentPane.add(lblNomeDaVarivel);

		JScrollPane scrollPaneNomeVariavel = new JScrollPane();
		scrollPaneNomeVariavel.setBounds(146, 52, 282, 45);
		contentPane.add(scrollPaneNomeVariavel);

		textAreaNomeVariavel = new JTextArea();
		textAreaNomeVariavel.setToolTipText("Esse campo não pode ser nulo.");
		textAreaNomeVariavel.setFont(new Font("Arial", Font.PLAIN, 16));
		textAreaNomeVariavel.setWrapStyleWord( true );
		textAreaNomeVariavel.setLineWrap( true );
		scrollPaneNomeVariavel.setViewportView(textAreaNomeVariavel);

		JLabel lblCodigoDoBanco = new JLabel("Código do banco:");
		lblCodigoDoBanco.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCodigoDoBanco.setBounds(10, 108, 131, 30);
		contentPane.add(lblCodigoDoBanco);

		textField_1CodigoBanco = new JTextField();
		textField_1CodigoBanco.setFont(new Font("Arial", Font.PLAIN, 16));
		textField_1CodigoBanco.setBounds(146, 109, 282, 30);
		contentPane.add(textField_1CodigoBanco);
		textField_1CodigoBanco.setColumns(10);

		JLabel lblAtualizacao = new JLabel("Atualização:");
		lblAtualizacao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAtualizacao.setBounds(10, 151, 93, 30);
		contentPane.add(lblAtualizacao);

		comboBox_1Atualizacao = new JComboBox<String>();
		comboBox_1Atualizacao.setModel(new DefaultComboBoxModel<String>(new String[] { "Decenal", "Anual", "Trimestral" }));
		comboBox_1Atualizacao.setSelectedIndex(0);
		comboBox_1Atualizacao.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBox_1Atualizacao.setBounds(113, 151, 106, 30);
		contentPane.add(comboBox_1Atualizacao);

		JButton btnCadastrarVariavel = new JButton("Cadastrar variável");
		btnCadastrarVariavel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JanelaConfirmaCadastroVariavel();
				return;
			}
		});
		btnCadastrarVariavel.setFont(new Font("Arial", Font.PLAIN, 16));
		btnCadastrarVariavel.setBounds(239, 149, 189, 30);
		contentPane.add(btnCadastrarVariavel);

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setClosable(true);
		setIconifiable(true);
	}

	/**
	 * Cadastra a variável no banco de dados
	 */
	public static void cadastrarVariavel() {
		String codigo_variavel = textFieldCodigoVariavel.getText().replace(" ", "");
		String nome_variavel = textAreaNomeVariavel.getText().replace(" ", "");

		if (codigo_variavel.equals("")) {
			new JanelaMensagem("O campo \"Código da variável\" não pode ser nulo.");
			return;
		}
		if (nome_variavel.equals("")) {
			new JanelaMensagem("O campo \"Nome da variável\" não pode ser nulo.");
			return;
		}
		String banco = (String) comboBoxTipoDeBanco.getSelectedItem();
		String codigo = textField_1CodigoBanco.getText().replace(" ", "");

		int codigo_da_variavel = Integer.parseInt(codigo_variavel);

		if (banco.equals("BD")) {
			if (codigo.equals("")) {
				Variavel.cadastrarVariavel(codigo_da_variavel, (String) comboBoxTipoDeBanco.getSelectedItem(),
						textAreaNomeVariavel.getText(), null, (String) comboBox_1Atualizacao.getSelectedItem()
						);
				return;
			}
			new JanelaMensagem("O campo \"Código do banco\" deve ser nulo para o tipo de banco \"BD\".");
			return;
		}
		if (codigo.equals("")) {
			new JanelaMensagem("O campo \"Código do banco\" não pode ser nulo.");
			return;
		}
		Variavel.cadastrarVariavel(codigo_da_variavel, (String) comboBoxTipoDeBanco.getSelectedItem(),
				textAreaNomeVariavel.getText(), codigo, (String) comboBox_1Atualizacao.getSelectedItem());
	}

	@Override
	public void abrirJanela(JInternalFrame janelaCadastrarVariaveis, JDesktopPane desktopPane) {
		if (janelaCadastrarVariaveis.isClosed()) {
			CadastrarVariaveis cadastrarVariaveis = new CadastrarVariaveis();
			desktopPane.add(cadastrarVariaveis);
			JanelaPrincipal.setJanelaCadastrarVariaveis(cadastrarVariaveis);
			return;
		}
		if (janelaCadastrarVariaveis.isVisible()) {
			janelaCadastrarVariaveis.moveToFront();
			return;
		}
		janelaCadastrarVariaveis.setVisible(true);
		janelaCadastrarVariaveis.moveToFront();
	}
}
