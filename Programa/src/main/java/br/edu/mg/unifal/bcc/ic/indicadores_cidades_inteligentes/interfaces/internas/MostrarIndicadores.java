package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas;

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

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;

/**
 * Classe responsável pela interface que mostra os indicadores.
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class MostrarIndicadores extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTable table;

	/**
	 * Executa a interface que mostra os indicadores.
	 */
	public MostrarIndicadores() {
		setTitle("Indicadores");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 720, 261);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblIndicadores = new JLabel("Indicadores de cidades inteligentes");
		lblIndicadores.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));

		JScrollPane scrollPaneTabela = new JScrollPane();

		table = new JTable();
		scrollPaneTabela.setViewportView(table);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(5)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblIndicadores, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
								.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE))
						.addGap(5)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(6)
						.addComponent(lblIndicadores, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addGap(8)
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE).addGap(14)));
		contentPane.setLayout(gl_contentPane);
		try {
			Tabelas.mostrarIndicadores(table);
		} catch (Exception e) {
			System.out.println("Falha ao mostrar indicador");
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
	 * @return tabela dos indicadores
	 */
	public static JTable getTable() {
		return table;
	}

	@Override
	public void abrirJanela(JInternalFrame janelaMostrarIndicadores, JDesktopPane desktopPane) {
		if (janelaMostrarIndicadores.isClosed()) {
			MostrarIndicadores mostrarIndicadores = new MostrarIndicadores();
			desktopPane.add(mostrarIndicadores);
			JanelaPrincipal.setJanelaMostrarIndicadores(mostrarIndicadores);
			return;
		}
		if (janelaMostrarIndicadores.isVisible()) {
			janelaMostrarIndicadores.moveToFront();
			return;
		}
		janelaMostrarIndicadores.setVisible(true);
		janelaMostrarIndicadores.moveToFront();
	}

}
