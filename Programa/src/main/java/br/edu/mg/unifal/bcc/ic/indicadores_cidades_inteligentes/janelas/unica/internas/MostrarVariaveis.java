package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal.JanelaPrincipal;

/**
 * Classe responsável pela interface que mostra as variáveis.
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class MostrarVariaveis extends JInternalFrame implements Janelas {

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
						.addComponent(lblVariaveis, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addGap(8)
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE).addGap(15)));
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
	 * 
	 * @return tabela das variáveis
	 */
	public static JTable getTable() {
		return table;
	}

	@Override
	public void abrirJanela(JInternalFrame janelaMostrarVariaveis, JDesktopPane desktopPane) {
		if (janelaMostrarVariaveis.isClosed()) {
			MostrarVariaveis mostrarVariaveis = new MostrarVariaveis();
			desktopPane.add(mostrarVariaveis);
			JanelaPrincipal.setJanelaMostrarVariaveis(mostrarVariaveis);
			return;
		}
		if (janelaMostrarVariaveis.isVisible()) {
			janelaMostrarVariaveis.moveToFront();
			return;
		}
		janelaMostrarVariaveis.setVisible(true);
		janelaMostrarVariaveis.moveToFront();
	}

}
