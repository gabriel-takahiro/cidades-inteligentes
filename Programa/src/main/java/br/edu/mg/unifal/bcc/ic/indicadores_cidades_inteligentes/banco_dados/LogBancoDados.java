package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.banco_dados;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.Tabelas;

import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class LogBancoDados extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public LogBancoDados(List<ResultadoOperacao> operacoes) {
		setTitle("Log do banco de dados");
		setBounds(100, 100, 500, 300);

		JScrollPane scrollPaneTabela = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
						.addContainerGap()));

		table = new JTable();
		scrollPaneTabela.setViewportView(table);

		Tabelas.logCriar(table, operacoes);
		getContentPane().setLayout(groupLayout);
		setLocation(0, 0);
		setVisible(true);
		setResizable(true);
		setClosable(true);
		setIconifiable(false);
	}
}
