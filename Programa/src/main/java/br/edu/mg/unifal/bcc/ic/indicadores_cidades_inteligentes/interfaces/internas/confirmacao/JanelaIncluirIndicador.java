package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.CadastrarIndicadores;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Indicador;

public class JanelaIncluirIndicador extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableIndicadores;
	private static JTextField textFieldCodigo;

	public JanelaIncluirIndicador(boolean cadastro) {
		setTitle("Incluir indicador");
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Selecione o indicador que deseja incluir:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 292, 23);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 40, 564, 179);
		contentPane.add(scrollPane);

		tableIndicadores = new JTable();
		tableIndicadores.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPane.setViewportView(tableIndicadores);

		JButton btnNewButton = new JButton("Adicionar esse indicador");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Indicador indicador = Indicador.buscarCodigoEMetodo(Integer.parseInt(textFieldCodigo.getText()));
					if (Integer.toString(indicador.getCodigo()).equals(null)) {
						new JanelaMensagem("É necessário colocar um indicador válido");
						return;
					}
				} catch (Exception erro) {
					new JanelaMensagem("É necessário colocar um indicador válido");
					return;
				}
				if (cadastro) {
					CadastrarIndicadores.colocarIndicador(textFieldCodigo.getText());
					dispose();
					return;
				}
				JanelaAtualizarIndicadores.colocarIndicador(textFieldCodigo.getText());
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton.setBounds(338, 227, 236, 25);
		contentPane.add(btnNewButton);

		textFieldCodigo = new JTextField();
		textFieldCodigo.setBounds(172, 230, 130, 23);
		contentPane.add(textFieldCodigo);
		textFieldCodigo.setColumns(10);

		JLabel lblCdigo = new JLabel("Código do indicador:");
		lblCdigo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCdigo.setBounds(10, 230, 152, 23);
		contentPane.add(lblCdigo);

		mostrarIndicadores();

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setAlwaysOnTop(rootPaneCheckingEnabled);
	}

	private void mostrarIndicadores() {
		tableIndicadores.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false, false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		Indicador.mostrarIndicadores(tableIndicadores, false);
	}
}
