package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Meta;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.ODS;

public class JanelaAtualizarMeta extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTextField textFieldMeta;
	private static JComboBox<Object> comboBoxODS;
	private static JTextArea textAreaTextoMeta;

	public JanelaAtualizarMeta(Meta meta) {
		setBounds(100, 100, 450, 300);
		setTitle("Atualizar meta");
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

		textFieldMeta = new JTextField(meta.getNumero_meta());
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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 76, 283, 115);
		contentPane.add(scrollPane);

		textAreaTextoMeta = new JTextArea(meta.getTexto_meta());
		textAreaTextoMeta.setToolTipText("Este campo não pode ser nulo.");
		textAreaTextoMeta.setFont(new Font("Arial", Font.PLAIN, 16));
		textAreaTextoMeta.setWrapStyleWord(true);
		textAreaTextoMeta.setLineWrap(true);
		scrollPane.setViewportView(textAreaTextoMeta);

		JButton btnNewButton = new JButton("Atualizar meta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JanelaConfirmaAtualizacaoMeta(meta);
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton.setBounds(10, 200, 283, 30);
		contentPane.add(btnNewButton);

		ODS.buscarODS(comboBoxODS);
		comboBoxODS.setSelectedItem(meta.getNumero_ods());

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(rootPaneCheckingEnabled);
	}

	public static void atualizarMeta(Meta meta) {
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
				Meta.atualizarMeta(textFieldMeta.getText(), textAreaTextoMeta.getText(),
						(int) comboBoxODS.getSelectedItem(), meta.getNumero_meta());
				return;
			}
			new JanelaMensagem("A meta difere da ODS.");
		} catch (Exception e) {
			new JanelaMensagem("Meta inválida.");
		}
	}

}
