package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao;

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

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Meta;

public class JanelaExcluirMetas extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JTable table;

	public JanelaExcluirMetas(List<Meta> listaMetas, JTable tabelaEditar) {
		setTitle("Excluir metas");
		setBounds(100, 100, 670, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Deseja excluir as metas:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 634, 30);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 222, 644, 35);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("Sim");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							Meta.excluir(listaMetas);
							JanelaPrincipal.atualizarMetas();
							dispose();
							new JanelaMensagem("Excluído com sucesso!");
						} catch (Exception erroExcluir) {
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
		scrollPane.setBounds(10, 39, 634, 168);
		contentPanel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		table.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		Meta.mostrarMetas(table, listaMetas);

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
