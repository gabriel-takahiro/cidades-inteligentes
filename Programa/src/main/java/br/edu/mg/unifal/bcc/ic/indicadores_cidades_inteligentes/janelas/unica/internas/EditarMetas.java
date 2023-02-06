package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaAtualizarMeta;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaExcluirMetas;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Meta;

/**
 * Classe responsável pela interface que edita as metas
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class EditarMetas extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTable table;

	/**
	 * Executa a interface que edita as metas
	 */
	public EditarMetas() {
		setBounds(100, 100, 720, 314);
		setTitle("Editar metas");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 720, 314);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblListaMetas = new JLabel("Lista das metas:");
		lblListaMetas.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaMetas.setFont(new Font("Arial", Font.PLAIN, 16));

		JScrollPane scrollPaneTabela = new JScrollPane();

		table = new JTable();
		scrollPaneTabela.setViewportView(table);

		JSeparator separator = new JSeparator();

		JButton btnSelecionarTodos = new JButton("Selecionar Todos");
		btnSelecionarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tabelas.selecionarTodos(3, table);
			}
		});
		btnSelecionarTodos.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Meta meta = Tabelas.metaSelecionada(table);
					meta = Meta.buscaMeta(meta);
					new JanelaAtualizarMeta(meta);
				} catch (Exception erroAtualizar) {
					new JanelaMensagem(erroAtualizar.getMessage());
				}
			}
		});
		btnAtualizar.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Meta> listaMetasSelecionados = new ArrayList<Meta>();
				listaMetasSelecionados = Tabelas.metasSelecionadas(table);
				new JanelaExcluirMetas(listaMetasSelecionados, table);
			}
		});
		btnExcluir.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(10)
						.addComponent(lblListaMetas, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE).addGap(10)
						.addComponent(btnSelecionarTodos, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE).addGap(10))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(10)
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE).addGap(10))
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(10)
						.addComponent(btnAtualizar, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE).addGap(4)
						.addComponent(btnExcluir, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE).addGap(10)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(6)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblListaMetas, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelecionarTodos, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(8).addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE).addGap(11)
				.addComponent(
						separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(11)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnAtualizar, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(13)));
		contentPane.setLayout(gl_contentPane);

		try {
			Tabelas.mostrarMetas(3, table);
		} catch (Exception e1) {
			e1.printStackTrace();
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
	 * @return tabela de metas
	 */
	public static JTable getTable() {
		return table;
	}

	@Override
	public void abrirJanela(JInternalFrame janelaEditarMetas, JDesktopPane desktopPane) {
		if (janelaEditarMetas.isClosed()) {
			EditarMetas editarMetas = new EditarMetas();
			desktopPane.add(editarMetas);
			JanelaPrincipal.setJanelaEditarMetas(editarMetas);
			return;
		} else if (janelaEditarMetas.isVisible()) {
			janelaEditarMetas.moveToFront();
			return;
		}
		janelaEditarMetas.setVisible(true);
		janelaEditarMetas.moveToFront();
	}

}
