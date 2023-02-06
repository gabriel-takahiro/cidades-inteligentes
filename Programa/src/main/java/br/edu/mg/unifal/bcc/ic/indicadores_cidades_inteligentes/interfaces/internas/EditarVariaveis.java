package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas;

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

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaAtualizarVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaExcluirVariaveis;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;

/**
 * Classe responsável pela interface que edita as variáveis
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class EditarVariaveis extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTable table;

	/**
	 * Executa a interface que edita as variáveis
	 */
	public EditarVariaveis() {
		setBounds(100, 100, 720, 314);
		setTitle("Editar variáveis");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 720, 314);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblListaVariaveis = new JLabel("Lista das variáveis:");
		lblListaVariaveis.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaVariaveis.setFont(new Font("Arial", Font.PLAIN, 16));

		JScrollPane scrollPaneTable = new JScrollPane();

		table = new JTable();
		scrollPaneTable.setViewportView(table);

		JSeparator separator = new JSeparator();

		JButton btnSelecionarTodos = new JButton("Selecionar Todos");
		btnSelecionarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tabelas.selecionarTodos(6, table);
			}
		});
		btnSelecionarTodos.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Variavel variavel = Tabelas.variavelSelecionada(table);
					variavel = Variavel.buscaVariavel(variavel);
					new JanelaAtualizarVariavel(variavel);
				} catch (Exception erroAtualizar) {
					new JanelaMensagem(erroAtualizar.getMessage());
				}
			}
		});
		btnAtualizar.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Variavel> listaVariaveisSelecionados = new ArrayList<Variavel>();
				listaVariaveisSelecionados = Tabelas.variaveisSelecionadas(table);
				new JanelaExcluirVariaveis(listaVariaveisSelecionados, table);
			}
		});
		btnExcluir.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(10)
						.addComponent(lblListaVariaveis, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE).addGap(10)
						.addComponent(btnSelecionarTodos, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE).addGap(10))
				.addGroup(gl_contentPane.createSequentialGroup().addGap(10)
						.addComponent(scrollPaneTable, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE).addGap(10))
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(10)
						.addComponent(btnAtualizar, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE).addGap(4)
						.addComponent(btnExcluir, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE).addGap(10)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(6)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblListaVariaveis, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelecionarTodos, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(8).addComponent(scrollPaneTable, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE).addGap(11)
				.addComponent(
						separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(11)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnAtualizar, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(13)));
		contentPane.setLayout(gl_contentPane);

		try {
			Tabelas.mostrarVariaveis(6, table);
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
	 * @return tabela de variáveis
	 */
	public static JTable getTable() {
		return table;
	}


	@Override
	public void abrirJanela(JInternalFrame janelaEditarVariaveis, JDesktopPane desktopPane) {
		if (janelaEditarVariaveis.isClosed()) {
			EditarVariaveis editarVariaveis = new EditarVariaveis();
			desktopPane.add(editarVariaveis);
			JanelaPrincipal.setJanelaEditarVariaveis(editarVariaveis);
			return;
		}
		if (janelaEditarVariaveis.isVisible()) {
			janelaEditarVariaveis.moveToFront();
			return;
		}
		janelaEditarVariaveis.setVisible(true);
		janelaEditarVariaveis.moveToFront();
	}

}
