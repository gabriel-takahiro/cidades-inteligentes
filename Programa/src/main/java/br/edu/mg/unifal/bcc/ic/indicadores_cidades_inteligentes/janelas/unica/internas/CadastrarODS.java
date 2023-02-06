package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaConfirmaCadastroODS;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.ODS;

/**
 * Classe responsável pela interface que cadastra as ODS
 * @author Gabriel Takahiro
 *
 */
public class CadastrarODS extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTextField textFieldODS;
	private static JTextArea textAreaTextoODS;

	/**
	 * Executa a interface responsável pelo cadastro das ODS
	 */
	public CadastrarODS() {
		setTitle("Cadastrar ODS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 179);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldODS = new JTextField();
		textFieldODS.setToolTipText("Este campo não pode ser nulo.");
		textFieldODS.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldODS.setBounds(157, 6, 177, 25);
		contentPane.add(textFieldODS);
		textFieldODS.setColumns(10);

		JLabel lblNewLabelODS = new JLabel("Número da ODS:");
		lblNewLabelODS.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabelODS.setBounds(10, 6, 137, 25);
		contentPane.add(lblNewLabelODS);

		JLabel lblNomeDaODS = new JLabel("Nome da ODS:");
		lblNomeDaODS.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNomeDaODS.setBounds(10, 56, 119, 25);
		contentPane.add(lblNomeDaODS);

		JScrollPane scrollPaneTextoODS = new JScrollPane();
		scrollPaneTextoODS.setBounds(136, 42, 198, 55);
		contentPane.add(scrollPaneTextoODS);

		textAreaTextoODS = new JTextArea();
		textAreaTextoODS.setToolTipText("Este campo não pode ser nulo.");
		textAreaTextoODS.setFont(new Font("Arial", Font.PLAIN, 16));
		textAreaTextoODS.setWrapStyleWord(true);
		textAreaTextoODS.setLineWrap(true);
		scrollPaneTextoODS.setViewportView(textAreaTextoODS);

		JButton btnCadastrarODS = new JButton("Cadastrar ODS");
		btnCadastrarODS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JanelaConfirmaCadastroODS();
			}
		});
		btnCadastrarODS.setFont(new Font("Arial", Font.PLAIN, 16));
		btnCadastrarODS.setBounds(10, 108, 324, 30);
		contentPane.add(btnCadastrarODS);

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setClosable(true);
		setIconifiable(true);

	}

	/**
	 * Cadastra a ODS no banco de dados
	 */
	public static void cadastrarODS() {
		String numero_ods = textFieldODS.getText().replace(" ", "");
		String nome_objetivo = textAreaTextoODS.getText();
		if (numero_ods.equals("")) {
			new JanelaMensagem("O campo \"Número da ODS\" não pode ser nulo.");
			return;
		}
		if (nome_objetivo.equals("")) {
			new JanelaMensagem("O campo \"Nome da ODS\" não pode ser nulo.");
			return;
		}

		try {
			ODS.cadastrarODS(new ODS(Integer.parseInt(numero_ods), nome_objetivo));
		} catch (Exception e) {
			new JanelaMensagem("ODS inválida.");
		}
	}

	@Override
	public void abrirJanela(JInternalFrame janelaCadastrarODS, JDesktopPane desktopPane) {
		if (janelaCadastrarODS.isClosed()) {
			CadastrarODS cadastrarODS = new CadastrarODS();
			desktopPane.add(cadastrarODS);
			JanelaPrincipal.setJanelaCadastrarODS(cadastrarODS);
			return;
		}
		if (janelaCadastrarODS.isVisible()) {
			janelaCadastrarODS.moveToFront();
			return;
		}
		janelaCadastrarODS.setVisible(true);
		janelaCadastrarODS.moveToFront();
	}

}
