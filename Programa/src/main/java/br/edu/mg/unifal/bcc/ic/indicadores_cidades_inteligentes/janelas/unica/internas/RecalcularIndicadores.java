package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaConfirmaTodosMunicipios;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaConfirmaUmMunicipio;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Municipio;

/**
 * Classe responsável pela interface que refaz o cálculo dos indicadores
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class RecalcularIndicadores extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTable table;
	private JFormattedTextField textFieldAno;
	private static JRadioButton rdbtnRecalcularParaTodos;
	private static JFormattedTextField campoFormatadoCep;
	private static JFormattedTextField formattedTextFieldAnos;

	/**
	 * Executa a interface que refaz o cálculo dos indicadores
	 */
	public RecalcularIndicadores() {
		setBounds(100, 100, 450, 300);
		setTitle("Recalcular indicadores");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 720, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIndicadores = new JLabel("Selecione os indicadores para recalcular:");
		lblIndicadores.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));
		lblIndicadores.setBounds(10, 11, 504, 30);
		contentPane.add(lblIndicadores);

		JScrollPane scrollPaneTabela = new JScrollPane();
		scrollPaneTabela.setBounds(10, 49, 684, 163);
		contentPane.add(scrollPaneTabela);

		table = new JTable();
		scrollPaneTabela.setViewportView(table);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 223, 704, 2);
		contentPane.add(separator);

		JLabel lblDigiteUmCep = new JLabel("Digite um CEP do município:");
		lblDigiteUmCep.setHorizontalAlignment(SwingConstants.LEFT);
		lblDigiteUmCep.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDigiteUmCep.setBounds(20, 280, 345, 30);
		contentPane.add(lblDigiteUmCep);

		campoFormatadoCep = new JFormattedTextField();
		campoFormatadoCep.setFont(new Font("Arial", Font.PLAIN, 16));
		campoFormatadoCep.setBounds(375, 280, 110, 30);
		contentPane.add(campoFormatadoCep);

		JRadioButton rdbtnParaUmMunicipio = new JRadioButton("Recalcular para um município");
		rdbtnParaUmMunicipio.setSelected(true);
		rdbtnParaUmMunicipio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnParaUmMunicipio.setSelected(true);
				rdbtnRecalcularParaTodos.setSelected(false);
				lblDigiteUmCep.setVisible(true);
				campoFormatadoCep.setVisible(true);
			}
		});
		rdbtnParaUmMunicipio.setFont(new Font("Arial", Font.PLAIN, 16));
		rdbtnParaUmMunicipio.setBounds(10, 233, 312, 30);
		contentPane.add(rdbtnParaUmMunicipio);

		rdbtnRecalcularParaTodos = new JRadioButton("Recalcular para todos os municípios ");
		rdbtnRecalcularParaTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnRecalcularParaTodos.setSelected(true);
				rdbtnParaUmMunicipio.setSelected(false);
				lblDigiteUmCep.setVisible(false);
				campoFormatadoCep.setVisible(false);
			}
		});
		rdbtnRecalcularParaTodos.setFont(new Font("Arial", Font.PLAIN, 16));
		rdbtnRecalcularParaTodos.setBounds(332, 232, 362, 30);
		contentPane.add(rdbtnRecalcularParaTodos);

		JLabel lblDigiteUmAno = new JLabel("Digite um ano para a consulta (Formato AAAA):");
		lblDigiteUmAno.setHorizontalAlignment(SwingConstants.LEFT);
		lblDigiteUmAno.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDigiteUmAno.setBounds(20, 325, 345, 30);
		contentPane.add(lblDigiteUmAno);

		MaskFormatter mascaraAno;
		try {
			mascaraAno = new MaskFormatter("####");
			textFieldAno = new JFormattedTextField(mascaraAno);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		textFieldAno.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldAno.setBounds(375, 325, 110, 30);
		contentPane.add(textFieldAno);
		textFieldAno.setColumns(10);

		JButton btnRecalcular = new JButton("Recalcular");
		btnRecalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnRecalcularParaTodos.isSelected()) {
					if (textFieldAno.getText().isBlank()) {
						new JanelaMensagem("É necessário preencher o campo Ano.");
						return;
					}

					String anosRetroativos = formattedTextFieldAnos.getText();
					if (anosRetroativos.isBlank()) {
						new JanelaMensagem("Coloque um valor válido em Anos retroativos");
						return;
					}

					int valorRetroativo = 0;
					try {
						valorRetroativo = Integer.parseInt(anosRetroativos);
						if (valorRetroativo < 0) {
							new JanelaMensagem("Coloque um valor não negativo em Anos retroativos");
							return;
						}
					} catch (Exception erroValorRetroativo) {
						new JanelaMensagem("Coloque um valor válido em Anos retroativos");
						return;
					}

					ArrayList<Indicador> listaIndicadoresSelecionados = new ArrayList<Indicador>();
					listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);
					new JanelaConfirmaTodosMunicipios(textFieldAno.getText(), true, listaIndicadoresSelecionados,
							valorRetroativo);
					return;
				}
				if (campoFormatadoCep.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo CEP.");
					return;
				}
				if (textFieldAno.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo Ano.");
					return;
				}

				String anosRetroativos = formattedTextFieldAnos.getText();
				if (anosRetroativos.isBlank()) {
					new JanelaMensagem("Coloque um valor válido em Anos retroativos");
					return;
				}

				int valorRetroativo = 0;
				try {
					valorRetroativo = Integer.parseInt(anosRetroativos);
					if (valorRetroativo < 0) {
						new JanelaMensagem("Coloque um valor não negativo em Anos retroativos");
						return;
					}
				} catch (Exception erroValorRetroativo) {
					new JanelaMensagem("Coloque um valor válido em Anos retroativos");
					return;
				}

				String nomeMunicipio;
				try {
					nomeMunicipio = Municipio.buscaNomeComUF(campoFormatadoCep.getText());
					ArrayList<Indicador> listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);
					new JanelaConfirmaUmMunicipio(campoFormatadoCep.getText(), textFieldAno.getText(), nomeMunicipio,
							true, listaIndicadoresSelecionados, valorRetroativo);
				} catch (Exception e1) {
					new JanelaMensagem("CEP inválido.");
				}
			}
		});
		btnRecalcular.setFont(new Font("Arial", Font.PLAIN, 16));
		btnRecalcular.setBounds(500, 280, 194, 116);
		contentPane.add(btnRecalcular);

		JButton btnSelecionarTodos = new JButton("Selecionar Todos");
		btnSelecionarTodos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tabelas.selecionarTodos(5, table);
			}
		});
		btnSelecionarTodos.setFont(new Font("Arial", Font.PLAIN, 16));
		btnSelecionarTodos.setBounds(524, 11, 170, 30);
		contentPane.add(btnSelecionarTodos);

		JLabel lblAnosAMais = new JLabel("Anos a mais para busca retroativa das variáveis:");
		lblAnosAMais.setHorizontalAlignment(SwingConstants.LEFT);
		lblAnosAMais.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAnosAMais.setBounds(20, 366, 345, 30);
		contentPane.add(lblAnosAMais);

		formattedTextFieldAnos = new JFormattedTextField("0");
		formattedTextFieldAnos.setFont(new Font("Arial", Font.PLAIN, 16));
		formattedTextFieldAnos.setBounds(375, 366, 110, 30);
		contentPane.add(formattedTextFieldAnos);

		try {
			Tabelas.mostrarIndicadores(5, table);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setClosable(true);
		setIconifiable(true);
	}

	/**
	 * 
	 * @return tabela de indicadores
	 */
	public static JTable getTable() {
		return table;
	}


	@Override
	public void abrirJanela(JInternalFrame janelaRecalcularIndicadores, JDesktopPane desktopPane) {
		if (janelaRecalcularIndicadores.isClosed()) {
			RecalcularIndicadores recalcularIndicadores = new RecalcularIndicadores();
			desktopPane.add(recalcularIndicadores);
			JanelaPrincipal.setJanelaRecalcularIndicadores(recalcularIndicadores);
			return;
		}
		if (janelaRecalcularIndicadores.isVisible()) {
			janelaRecalcularIndicadores.moveToFront();
			return;
		}
		janelaRecalcularIndicadores.setVisible(true);
		janelaRecalcularIndicadores.moveToFront();
	}

}
