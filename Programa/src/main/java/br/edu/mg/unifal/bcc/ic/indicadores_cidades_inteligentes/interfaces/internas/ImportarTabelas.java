package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas;

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
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.banco_dados.ImportarBD;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.banco_dados.InserirBD;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.PossuiVariavel;

/**
 * Classe responsável pela interface que importa as tabelas para o banco de dados
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class ImportarTabelas extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	/**
	 * Executa a interface que cria tabelas no banco de dados
	 */
	public ImportarTabelas() {
		setTitle("Importar tabelas");
		setBounds(100, 100, 340, 117);

		JLabel lblImportarIndicadores = new JLabel("Deseja importar os indicadores padrão?");
		lblImportarIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnSim = new JButton("Sim");
		btnSim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExcluirTabelaBD.excluirTodas(true);
				ImportarBD.importarTudo();
				InserirBD.inserirTudo();
				PossuiVariavel.insereTodos();
				JanelaPrincipal.atualizarTudo();
				dispose();
			}
		});
		btnSim.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnaNao = new JButton("Não");
		btnaNao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnaNao.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblImportarIndicadores, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
										.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
												.addComponent(btnSim, GroupLayout.DEFAULT_SIZE, 143,
														Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnaNao, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)))
								.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addComponent(lblImportarIndicadores, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnSim, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnaNao, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
	public void abrirJanela(JInternalFrame janelaImportarTabelas, JDesktopPane desktopPane) {
		if (janelaImportarTabelas.isClosed()) {
			ImportarTabelas importarTabelas = new ImportarTabelas();
			desktopPane.add(importarTabelas);
			JanelaPrincipal.setJanelaImportarTabelas(importarTabelas);
			return;
		}
		if (janelaImportarTabelas.isVisible()) {
			janelaImportarTabelas.moveToFront();
			return;
		}
		janelaImportarTabelas.setVisible(true);
		janelaImportarTabelas.moveToFront();
	}
}
