package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaConfirmaCadastroMeta;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Meta;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.ODS;

/**
 * Classe responsável pela interface que cadastra as metas
 * @author Gabriel Takahiro
 *
 */
public class CadastrarMetas extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTextField textFieldMeta;
	private static JComboBox<Object> comboBoxODS;
	private static JTextArea textAreaTextoMeta;

	/**
	 * Executa a interface responsável pelo cadastro das metas
	 */
	public CadastrarMetas() {
		setBounds(100, 100, 450, 300);
		setTitle("Cadastrar meta");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 319, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabelMeta = new JLabel("Meta:");
		lblNewLabelMeta.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabelMeta.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabelMeta);

		textFieldMeta = new JTextField();
		textFieldMeta.setToolTipText("Este campo não pode ser nulo.");
		textFieldMeta.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldMeta.setBounds(55, 6, 91, 25);
		contentPane.add(textFieldMeta);
		textFieldMeta.setColumns(10);

		JLabel lblNewLabelODS = new JLabel("ODS:");
		lblNewLabelODS.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabelODS.setBounds(156, 13, 46, 14);
		contentPane.add(lblNewLabelODS);

		comboBoxODS = new JComboBox<Object>();
		comboBoxODS.setMaximumRowCount(5);
		comboBoxODS.setBounds(203, 6, 90, 25);
		contentPane.add(comboBoxODS);

		JLabel lblTextoDaMeta = new JLabel("Texto da meta:");
		lblTextoDaMeta.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTextoDaMeta.setBounds(10, 51, 119, 14);
		contentPane.add(lblTextoDaMeta);

		JScrollPane scrollPaneTextoMeta = new JScrollPane();
		scrollPaneTextoMeta.setBounds(10, 76, 283, 115);
		contentPane.add(scrollPaneTextoMeta);

		textAreaTextoMeta = new JTextArea();
		textAreaTextoMeta.setToolTipText("Este campo não pode ser nulo.");
		textAreaTextoMeta.setFont(new Font("Arial", Font.PLAIN, 16));
		textAreaTextoMeta.setWrapStyleWord(true);
		textAreaTextoMeta.setLineWrap(true);
		scrollPaneTextoMeta.setViewportView(textAreaTextoMeta);

		JButton btnCadastrarMeta = new JButton("Cadastrar meta");
		btnCadastrarMeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JanelaConfirmaCadastroMeta();
			}
		});
		btnCadastrarMeta.setFont(new Font("Arial", Font.PLAIN, 16));
		btnCadastrarMeta.setBounds(10, 200, 283, 30);
		contentPane.add(btnCadastrarMeta);

		try {
			mostrarODS();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setClosable(true);
		setIconifiable(true);

	}

	/**
	 * Mostra as ODS disponíveis no banco de dados
	 */
	public static void mostrarODS() {
		comboBoxODS.setModel(new DefaultComboBoxModel<>());
		ODS.buscarODS(comboBoxODS);
	}

	/**
	 * Cadastra a meta no banco de dados
	 */
	public static void cadastrarMeta() {
		String numero_meta = textFieldMeta.getText().replace(" ", "");
		String texto_meta = textAreaTextoMeta.getText().replace(" ", "");
		if (numero_meta.equals("")) {
			new JanelaMensagem("O campo \"Meta\" não pode ser nulo.");
			return;
		}
		if (texto_meta.equals("")) {
			new JanelaMensagem("O campo \"Texto da meta\" não pode ser nulo.");
			return;
		}

		try {
			if (Meta.metaCorrespondeODS(numero_meta, (int) comboBoxODS.getSelectedItem())) {
				Meta.cadastrarMeta(textFieldMeta.getText(), textAreaTextoMeta.getText(),
						(int) comboBoxODS.getSelectedItem());
				return;
			}
			new JanelaMensagem("A meta difere da ODS.");
		} catch (Exception e) {
			new JanelaMensagem("Meta inválida.");
		}
	}

	@Override
	public void abrirJanela(JInternalFrame janelaCadastrarMetas, JDesktopPane desktopPane) {
		if (janelaCadastrarMetas.isClosed()) {
			CadastrarMetas cadastrarMetas = new CadastrarMetas();
			desktopPane.add(cadastrarMetas);
			JanelaPrincipal.setJanelaCadastrarMetas(cadastrarMetas);
			return;
		}
		if (janelaCadastrarMetas.isVisible()) {
			janelaCadastrarMetas.moveToFront();
			return;
		}
		janelaCadastrarMetas.setVisible(true);
		janelaCadastrarMetas.moveToFront();
	}

}
