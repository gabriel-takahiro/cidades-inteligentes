package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.banco_dados.ExcluirTabelaBD;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal.JanelaPrincipal;

/**
 * Classe responsável pela interface que cria tabelas no banco de dados
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class ExcluirTabelas extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	/**
	 * Executa a interface que cria tabelas no banco de dados
	 */
	public ExcluirTabelas() {
		setTitle("Excluir tabelas");
		setBounds(100, 100, 340, 117);

		JLabel lblExcluirTabelas = new JLabel("Deseja excluir todas as tabelas?");
		lblExcluirTabelas.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnSim = new JButton("Sim");
		btnSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExcluirTabelaBD.excluirTodas(false);
				JanelaPrincipal.atualizarTudo();
				dispose();
			}
		});
		btnSim.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnNao = new JButton("Não");
		btnNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNao.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblExcluirTabelas, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
										.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
												.addComponent(btnSim, GroupLayout.DEFAULT_SIZE, 143,
														Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnNao, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)))
								.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addComponent(lblExcluirTabelas, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnSim, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNao, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addContainerGap(197, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);
		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		setFocusable(true);
	}

	@Override
	public void abrirJanela(JInternalFrame janelaExcluirTabelas, JDesktopPane desktopPane) {
		if (janelaExcluirTabelas.isClosed()) {
			ExcluirTabelas excluirTabelas = new ExcluirTabelas();
			desktopPane.add(excluirTabelas);
			JanelaPrincipal.setJanelaExcluirTabelas(excluirTabelas);
			return;
		}
		if (janelaExcluirTabelas.isVisible()) {
			janelaExcluirTabelas.moveToFront();
			return;
		}
		janelaExcluirTabelas.setVisible(true);
		janelaExcluirTabelas.moveToFront();
	}
}
