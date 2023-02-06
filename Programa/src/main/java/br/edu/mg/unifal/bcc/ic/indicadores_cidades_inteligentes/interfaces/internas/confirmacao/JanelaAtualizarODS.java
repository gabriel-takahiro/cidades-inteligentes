package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.ODS;

public class JanelaAtualizarODS extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTextField textFieldODS;
	private static JTextArea textAreaTextoODS;

	public JanelaAtualizarODS(ODS ods) {
		setTitle("Atualizar ODS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 179);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldODS = new JTextField(Integer.toString(ods.getNumero_ods()));
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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(136, 42, 198, 55);
		contentPane.add(scrollPane);

		textAreaTextoODS = new JTextArea(ods.getNome_objetivo());
		textAreaTextoODS.setToolTipText("Este campo não pode ser nulo.");
		textAreaTextoODS.setFont(new Font("Arial", Font.PLAIN, 16));
		textAreaTextoODS.setWrapStyleWord(true);
		textAreaTextoODS.setLineWrap(true);
		scrollPane.setViewportView(textAreaTextoODS);

		JButton btnNewButton = new JButton("Atualizar ODS");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JanelaConfirmaAtualizacaoODS(ods);
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton.setBounds(10, 108, 324, 30);
		contentPane.add(btnNewButton);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(rootPaneCheckingEnabled);

	}

	public static void atualizarODS(ODS ods) {
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
			ODS.atualizarODS(new ODS(Integer.parseInt(numero_ods), nome_objetivo), ods.getNumero_ods());
		} catch (Exception e) {
			new JanelaMensagem("ODS inválida.");
		}
	}

}
