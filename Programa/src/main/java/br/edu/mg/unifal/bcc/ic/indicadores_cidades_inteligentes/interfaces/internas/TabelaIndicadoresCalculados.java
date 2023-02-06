package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.IndicadoresBuscados;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.ValorVariavel;

/**
 * Classe responsável pela interface que exibe os indicadores calculados
 * @author Gabriel Takahiro
 * @version 0.3
 * 
 */
public class TabelaIndicadoresCalculados extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable tableIndicadoresComResultado;
	private JLabel lblNewLabel;
	private JTable tableIndicadoresSemResultado;
	private List<IndicadoresBuscados> listaIndicadoresCalculados;
	private ArrayList<IndicadoresBuscados> indicadoresNaoValorados;
	private ArrayList<IndicadoresBuscados> indicadoresValorados;

	private ArrayList<Indicador> listaIndicadoresSelecionados;
	private String nomeMunicipio;
	private int codigo_municipio;
	private String data;
	private int valorRetroativo;

	/**
	 * Executa a interface que exibe os indicadores calculados
	 * @param listaIndicadoresSelecionados lista dos indicadores selecionados para o cálculo
	 * @param nomeMunicipio nome do município que foi utilizado para o cálculo dos indicadores 
	 * @param codigo_municipio  código do município que foi utilizado para o cálculo dos indicadores
	 * @param data ano que foi utilizado para calcular os indicadores
	 * @param valorRetroativo quantidade de anos a mais utilizados para a busca retroativa dos variáveis
	 */
	public TabelaIndicadoresCalculados(ArrayList<Indicador> listaIndicadoresSelecionados, String nomeMunicipio,
			int codigo_municipio, String data, int valorRetroativo) {

		this.listaIndicadoresSelecionados = listaIndicadoresSelecionados;
		this.nomeMunicipio = nomeMunicipio;
		this.codigo_municipio = codigo_municipio;
		this.data = data;
		this.valorRetroativo = valorRetroativo;

		List<IndicadoresBuscados> indicadoresCalculados = CalculoIndicador
				.buscarIndicadoresList(listaIndicadoresSelecionados, codigo_municipio, data);

		indicadoresNaoValorados = new ArrayList<IndicadoresBuscados>();
		indicadoresValorados = new ArrayList<IndicadoresBuscados>();
		for (IndicadoresBuscados indCalculado : indicadoresCalculados) {
			if (indCalculado.getResultado().equals("-")) {
				indicadoresNaoValorados.add(indCalculado);
			} else {
				indicadoresValorados.add(indCalculado);
			}
		}
		setTitle("Indicadores para o município: " + nomeMunicipio);
		setBounds(100, 100, 850, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPaneTabelaIndicadoresComResultado = new JScrollPane();

		lblNewLabel = new JLabel("Indicadores com valor:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));

		JScrollPane scrollPaneTabelaIndicadoresSemResultado = new JScrollPane();

		JLabel lblIndicadoresSemValor = new JLabel("Indicadores sem valor:");
		lblIndicadoresSemValor.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndicadoresSemValor.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnCalcular = new JButton("Calcular");
		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Indicador> indicadoresCalculadosTabela = calcularIndicadores(codigo_municipio, data);
				List<IndicadoresBuscados> indicadoresQueForamCalculados = new ArrayList<IndicadoresBuscados>();

				for (Indicador indicador : indicadoresCalculadosTabela) {
					indicadoresNaoValorados.forEach(ind -> {
						if (ind.getCodigo_indicador() == indicador.getCodigo()) {
							indicadoresQueForamCalculados.add(ind);
						}
					});
				}
				indicadoresQueForamCalculados.forEach(indicador -> {
					indicadoresNaoValorados.remove(indicador);
				});

				Tabelas.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados, codigo_municipio,
						data);

				for (IndicadoresBuscados indCalculado : listaIndicadoresCalculados) {
					indicadoresValorados.add(indCalculado);
				}

				Tabelas.mostrarIndicadoresComResultado(tableIndicadoresComResultado, tableIndicadoresSemResultado,
						indicadoresNaoValorados, indicadoresValorados, nomeMunicipio, codigo_municipio, data,
						valorRetroativo);
			}
		});
		btnCalcular.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblIndicadoresSemValor, GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnCalcular, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
				.addComponent(scrollPaneTabelaIndicadoresSemResultado, GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
				.addComponent(scrollPaneTabelaIndicadoresComResultado, GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPaneTabelaIndicadoresComResultado, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblIndicadoresSemValor, GroupLayout.PREFERRED_SIZE, 27,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnCalcular, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(scrollPaneTabelaIndicadoresSemResultado, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)));

		tableIndicadoresSemResultado = new JTable();
		scrollPaneTabelaIndicadoresSemResultado.setViewportView(tableIndicadoresSemResultado);

		tableIndicadoresComResultado = new JTable();
		scrollPaneTabelaIndicadoresComResultado.setViewportView(tableIndicadoresComResultado);
		contentPane.setLayout(gl_contentPane);

		try {
			Tabelas.mostrarIndicadoresComResultado(tableIndicadoresComResultado, tableIndicadoresSemResultado,
					indicadoresNaoValorados, indicadoresValorados, nomeMunicipio, codigo_municipio, data,
					valorRetroativo);
			Tabelas.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados, codigo_municipio,
					data);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		setVisible(true);
		setMaximizable(true);
		try {
			setMaximum(true);
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
		setLocation(0, 0);
		setResizable(true);
		setClosable(true);
		setIconifiable(true);
	}

	/**
	 * Insere os valores das variáveis colocadas a mão no banco de dados e recalcula os indicadores
	 * selecionados. Somente faz as operações nas linhas que forem selecionadas na interface.
	 * @param codigo_municipio código do município que será utilizado para o cálculo dos indicadores
	 * @param data ano que será utilizado para calcular os indicadores
	 * @return lista de indicadores
	 */
	private ArrayList<Indicador> calcularIndicadores(int codigo_municipio, String data) {
		ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
		for (int i = 0; i < tableIndicadoresSemResultado.getRowCount(); i++) {
			if (tableIndicadoresSemResultado.getValueAt(i, 8).toString().equals("true")
					&& !(tableIndicadoresSemResultado.getValueAt(i, 0).toString().isBlank())) {
				indicadores
						.add(new Indicador(Integer.parseInt(tableIndicadoresSemResultado.getValueAt(i, 0).toString()),
								tableIndicadoresSemResultado.getValueAt(i, 2).toString()));
			}
			if (tableIndicadoresSemResultado.getValueAt(i, 0).toString().isBlank()
					&& tableIndicadoresSemResultado.getValueAt(i, 8).toString().equals("true")) {
				try {
					ValorVariavel.inserirValorVariavel(
							Integer.parseInt(tableIndicadoresSemResultado.getValueAt(i, 3).toString()),
							codigo_municipio, tableIndicadoresSemResultado.getValueAt(i, 5).toString(),
							Float.parseFloat(tableIndicadoresSemResultado.getValueAt(i, 6).toString()));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

		}
		for (Indicador indicador : indicadores) {
			System.out.println(indicador.getCodigo());
		}

		List<IndicadoresBuscados> indicadoresCalculados = CalculoIndicador.listaIndicadoresCalculados(indicadores,
				codigo_municipio, data, true, 0);

		List<IndicadoresBuscados> indicadoresSemResultado = new ArrayList<IndicadoresBuscados>();
		List<Indicador> indicadoresParaExcluir = new ArrayList<Indicador>();

		for (IndicadoresBuscados indicadoresBuscados : indicadoresCalculados) {
			if (indicadoresBuscados.getResultado().equals("-")) {
				indicadoresSemResultado.add(indicadoresBuscados);
				indicadores.forEach(indicador -> {
					if (indicador.getCodigo() == indicadoresBuscados.getCodigo_indicador()) {
						indicadoresParaExcluir.add(indicador);
					}
				});
			}
		}

		indicadoresSemResultado.forEach(indicador -> {
			indicadoresCalculados.remove(indicador);
		});

		indicadoresParaExcluir.forEach(indicador -> {
			indicadores.remove(indicador);
		});

		this.listaIndicadoresCalculados = indicadoresCalculados;

		return indicadores;
	}

	/**
	 * Remove o indicador da tabela de indicadores calculados e passa ele para a 
	 * tabela de indicadores não calculados.
	 * do banco de dados.
	 * @param indicador indicador que será excluído da tabela de indicadores calculados
	 * @param codigo_municipio código do município que foi utilizado para calcular os indicadores
	 * @param data ano que foi utilizado para calcular os indicadores
	 * @param nomeMunicipio nome do município
	 * @param valorRetroativo quantidade de anos a mais utilizados para a busca retroativa dos variáveis
	 * @param tableIndicadoresComResultado tabela dos indicadores que possuem resultado
	 * @param tableIndicadoresSemResultado tabela dos indicadores que não possuem resultado
	 * @param indicadoresNaoValorados lista de indicadores não valorados
	 * @param indicadoresValorados lista de indicadores valorados
	 */
	public static void removerIndicadorCalculado(IndicadoresBuscados indicador, int codigo_municipio, String data,
			String nomeMunicipio, int valorRetroativo, JTable tableIndicadoresComResultado,
			JTable tableIndicadoresSemResultado, ArrayList<IndicadoresBuscados> indicadoresNaoValorados,
			ArrayList<IndicadoresBuscados> indicadoresValorados) {
		indicadoresNaoValorados.add(indicador);
		indicadoresValorados.remove(indicador);

		Tabelas.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados, codigo_municipio, data);

		Tabelas.mostrarIndicadoresComResultado(tableIndicadoresComResultado, tableIndicadoresSemResultado,
				indicadoresNaoValorados, indicadoresValorados, nomeMunicipio, codigo_municipio, data, valorRetroativo);
	}

	/**
	 * Atualiza a interface que exibe os indicadores calculados
	 */
	public void atualizar() {
		List<IndicadoresBuscados> indicadoresCalculados = CalculoIndicador
				.buscarIndicadoresList(listaIndicadoresSelecionados, codigo_municipio, data);
		indicadoresNaoValorados = new ArrayList<IndicadoresBuscados>();
		indicadoresValorados = new ArrayList<IndicadoresBuscados>();
		for (IndicadoresBuscados indCalculado : indicadoresCalculados) {
			if (indCalculado.getResultado().equals("-")) {
				indicadoresNaoValorados.add(indCalculado);
			} else {
				indicadoresValorados.add(indCalculado);
			}
		}

		try {
			Tabelas.mostrarIndicadoresComResultado(tableIndicadoresComResultado, tableIndicadoresSemResultado,
					indicadoresNaoValorados, indicadoresValorados, nomeMunicipio, codigo_municipio, data,
					valorRetroativo);
			Tabelas.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados, codigo_municipio,
					data);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}
