package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Municipio;

/**
 * Classe responsável pela interface que realiza a consulta dos indicadores calculados
 * @author Gabriel Takahiro
 *
 */
public class ConsultarIndicadoresCalculados extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	private JTextField textField;
	private static JTable table;
	private JFormattedTextField textField_1;

	/**
	 * Executa a interface que realiza a consulta dos indicadores calculados
	 */
	public ConsultarIndicadoresCalculados() {
		setTitle("Consultar indicadores");
		setBounds(100, 100, 690, 323);

		JLabel lblAno = new JLabel("Ano:");
		lblAno.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblListaDosIndicadores = new JLabel("Lista dos indicadores:");
		lblListaDosIndicadores.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaDosIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));

		JScrollPane scrollPaneTabela = new JScrollPane();

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Arial", Font.PLAIN, 16));

		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 16));
		textField.setColumns(10);

		JButton btnTabelaIndicadores = new JButton("Tabela dos indicadores");
		btnTabelaIndicadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Indicador> listaIndicadoresSelecionados = new ArrayList<Indicador>();
				listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);

				if (textField.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo CEP.");
					return;
				}

				if (textField_1.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo Data.");
					return;
				}

				int codigo_municipio = 0;
				try {
					codigo_municipio = Municipio.buscaCodigoDoMunicipio(textField.getText());
				} catch (Exception erroCodigoMunicipio) {
					new JanelaMensagem("CEP inválido.");
					return;
				}

				try {
					String nomeMunicipio = Municipio.buscaNomeComUF(textField.getText());
					JanelaPrincipal.abrirJanelas(new TabelaIndicadoresCalculados(listaIndicadoresSelecionados,
							nomeMunicipio, codigo_municipio, textField_1.getText(), 0));
				} catch (Exception e1) {
					new JanelaMensagem("CEP inválido.");
				}

			}
		});
		btnTabelaIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnTabelaDeIndicadoresCalculados = new JButton("Gráfico de indicadores calculados");
		btnTabelaDeIndicadoresCalculados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Indicador> listaIndicadoresSelecionados = new ArrayList<Indicador>();
				listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);

				if (textField.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo CEP.");
					return;
				}

				if (textField_1.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo Data.");
					return;
				}

				int codigo_municipio = 0;
				try {
					codigo_municipio = Municipio.buscaCodigoDoMunicipio(textField.getText());
				} catch (Exception erroCodigoMunicipio) {
					new JanelaMensagem("CEP inválido.");
					return;
				}

				try {
					String nomeMunicipio = Municipio.buscaNomeComUF(textField.getText());
					JanelaPrincipal.abrirJanelas(new GraficoIndicadores(listaIndicadoresSelecionados, nomeMunicipio,
							codigo_municipio, textField_1.getText()));
				} catch (Exception e1) {
					new JanelaMensagem("CEP inválido.");
				}
			}
		});
		btnTabelaDeIndicadoresCalculados.setFont(new Font("Arial", Font.PLAIN, 16));

		MaskFormatter mascaraAno;
		try {
			mascaraAno = new MaskFormatter("####");
			textField_1 = new JFormattedTextField(mascaraAno);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		textField_1.setFont(new Font("Arial", Font.PLAIN, 16));
		textField_1.setColumns(10);

		JButton btnSelecionarTodos = new JButton("Selecionar Todos");
		btnSelecionarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tabelas.selecionarTodos(5, table);
			}
		});
		btnSelecionarTodos.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addComponent(lblAno, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblCep, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblListaDosIndicadores, GroupLayout.PREFERRED_SIZE, 172,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSelecionarTodos, GroupLayout.PREFERRED_SIZE, 170,
										GroupLayout.PREFERRED_SIZE)
								.addGap(2))
						.addComponent(btnTabelaIndicadores, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
						.addComponent(btnTabelaDeIndicadoresCalculados, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAno, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCep, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblListaDosIndicadores, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE))
						.addComponent(btnSelecionarTodos, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnTabelaIndicadores, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnTabelaDeIndicadoresCalculados, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addContainerGap()));

		table = new JTable();
		scrollPaneTabela.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		try {
			Tabelas.mostrarIndicadores(5, table);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
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
	public void abrirJanela(JInternalFrame janelaConsultarIndicadoresCalculados, JDesktopPane desktopPane) {
		if (janelaConsultarIndicadoresCalculados.isClosed()) {
			ConsultarIndicadoresCalculados consultarIndicadoresCalculados = new ConsultarIndicadoresCalculados();
			desktopPane.add(consultarIndicadoresCalculados);
			JanelaPrincipal.setJanelaConsultarIndicadoresCalculados(consultarIndicadoresCalculados);
			return;
		}
		if (janelaConsultarIndicadoresCalculados.isVisible()) {
			janelaConsultarIndicadoresCalculados.moveToFront();
			return;
		}
		janelaConsultarIndicadoresCalculados.setVisible(true);
		janelaConsultarIndicadoresCalculados.moveToFront();
	}
}
