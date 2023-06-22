/*Copyright (C) <2022> <Gabriel Takahiro Toma de Lima>
 
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.
 You should have received a copy of the GNU General Public License
 along with this program. If not, see <https://www.gnu.org/licenses/>.
 
Versão em português:

Este programa é um software livre: você pode redistribuí-lo e/ou
modificá-lo sob os termos da Licença Pública Geral GNU, conforme
publicado pela Free Software Foundation, seja a versão 3 da Licença
ou (a seu critério) qualquer versão posterior.
Este programa é distribuído na esperança de que seja útil,
mas SEM QUALQUER GARANTIA; sem a garantia implícita de
COMERCIALIZAÇÃO OU ADEQUAÇÃO A UM DETERMINADO PROPÓSITO. Veja a
Licença Pública Geral GNU para obter mais detalhes.
Você deve ter recebido uma cópia da Licença Pública Geral GNU
junto com este programa. Se não, veja <https://www.gnu.org/licenses/>.
*/
package br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.resultado;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import br.edu.mg.unifal.bcc.sgici.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.sgici.modelo.IndicadoresBuscados;
import br.edu.mg.unifal.bcc.sgici.modelo.ODS;

/**
 * Classe responsável pela interface que exibe o gráfico dos indicadores
 * 
 * @author Gabriel Takahiro
 * @version 0.2
 */
public class GraficoIndicadores extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollPane;
	private JComboBox<Object> comboBoxPagina;
	private ArrayList<IndicadoresBuscados> indicadoresPorODS;

	/**
	 * Seleciona os indicadores que serão exibidos no gráfico
	 * 
	 * @param indicadoresCalculados indicadores que foram calculados
	 * @return conjunto dos valores dos indicadores selecionados
	 */
	public static CategoryDataset createDataSet(List<IndicadoresBuscados> indicadoresCalculados, int pagina) {

		int quantidadePorPagina = 20; 
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		for(int i = quantidadePorPagina*(pagina-1); i < pagina*quantidadePorPagina && i <= (indicadoresCalculados.size()-1); i++) {
			if (indicadoresCalculados.get(i).getResultado().equals("-")
					|| Double.parseDouble(indicadoresCalculados.get(i).getResultado().replace(",", ".")) > 100.0) {
				
			} else {
				dataSet.addValue(Double.parseDouble(indicadoresCalculados.get(i).getResultado().replace(",", ".")),
						String.valueOf(indicadoresCalculados.get(i).getCodigo_indicador()) + ":" + indicadoresCalculados.get(i).getNome_indicador(),"");
			}
		}
		return dataSet;
	}

	/**
	 * Cria um gráfico de barras
	 * 
	 * @param dataSet conjunto dos valores dos indicadores calculados
	 * @return gráfico de barras
	 */
	public static JFreeChart createBarChart(CategoryDataset dataSet) {

		JFreeChart graficoBarras = ChartFactory.createBarChart("Indicadores Calculados", "Indicadores", "Valores de 0 a 100",
				dataSet, PlotOrientation.HORIZONTAL, true, true, false);

		return graficoBarras;
	}

	/**
	 * Cria um painel com o gráfico de barras
	 * 
	 * @param indicadoresCalculados lista de indicadores calculados
	 * @return painel do gráfico
	 */
	public static ChartPanel criarGrafico(List<IndicadoresBuscados> indicadoresCalculados, int pagina) {

		CategoryDataset dataSet = createDataSet(indicadoresCalculados, pagina);

		JFreeChart grafico = createBarChart(dataSet);

		CategoryPlot plot = grafico.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.gray);
		plot.setRangeGridlinePaint(Color.lightGray);
		plot.setOutlineVisible(false);
		grafico.getLegend().setFrame(BlockBorder.NONE);
		grafico.getLegend().setItemLabelPadding(new RectangleInsets(3.8, 10, 3.8, 0));
		grafico.getLegend().setPadding(new RectangleInsets(45, 10, 3.8, 3.8));
		grafico.getLegend().setItemFont(new Font("Arial", Font.BOLD, 14));
		grafico.getLegend().setPosition(RectangleEdge.RIGHT);

		CategoryPlot barraItem = grafico.getCategoryPlot();

		List<IndicadoresBuscados> indicadoresNegativos = new ArrayList<>();
		for (IndicadoresBuscados indicadoresBuscados : indicadoresCalculados) {
			if (indicadoresBuscados.getResultado().substring(0, 1).equals("-")) {
				indicadoresNegativos.add(indicadoresBuscados);
			}
		}

		for (IndicadoresBuscados indicadores : indicadoresNegativos) {
			indicadoresCalculados.remove(indicadores);
		}

		colorirGrafico(barraItem, indicadoresCalculados);

		ChartPanel painelDoGrafico = new ChartPanel(grafico);
		painelDoGrafico.setPreferredSize(new Dimension(1200, 600));

		return painelDoGrafico;
	}

	/**
	 * Colore o gráfico em verde (entre 70 e 100), amarelo (entre 35 e 70) e
	 * vermelho (entre 0 e 35)
	 * 
	 * @param barraItem             barras do gráfico
	 * @param indicadoresCalculados lista de indicadores calculados
	 */
	private static void colorirGrafico(CategoryPlot barraItem, List<IndicadoresBuscados> indicadoresCalculados) {
		int i = 0;
		for (IndicadoresBuscados indicadoresBuscados : indicadoresCalculados) {
			if (indicadoresBuscados.getResultado().equals("-")) {
			} else if (Double.parseDouble(indicadoresBuscados.getResultado().replace(",", ".")) <= 100.0) {
				if (Double.parseDouble(indicadoresBuscados.getResultado().replace(",", ".")) >= 70.0) {
					barraItem.getRenderer().setSeriesPaint(i, Color.GREEN);
					i++;
				} else if (Double.parseDouble(indicadoresBuscados.getResultado().replace(",", ".")) >= 35.0) {
					barraItem.getRenderer().setSeriesPaint(i, Color.YELLOW);
					i++;
				} else {
					barraItem.getRenderer().setSeriesPaint(i, Color.RED);
					i++;
				}
			}
		}
	}

	/**
	 * Executa a interface que exibe o gráfico de barras dos indicadores
	 * 
	 * @param listaIndicadoresSelecionados lista dos indicadores que foram
	 *                                     selecionados para montar o gráfico
	 * @param nomeMunicipio                nome do município que está sendo
	 *                                     calculado os indicadores
	 * @param codigo_municipio             código do município que está sendo
	 *                                     calculado os indicadores
	 * @param data                         dados relativo ao ano
	 */
	public GraficoIndicadores(ArrayList<Integer> listaIndicadoresSelecionados, String nomeMunicipio,
			int codigo_municipio, String data) {
		setTitle("Gráfico dos indicares");
		List<IndicadoresBuscados> indicadoresCalculados = CalculoIndicador
				.buscarIndicadoresList(listaIndicadoresSelecionados, codigo_municipio, data);

		ArrayList<IndicadoresBuscados> indicadoresNaoValorados = new ArrayList<IndicadoresBuscados>();
		ArrayList<IndicadoresBuscados> indicadoresValorados = new ArrayList<IndicadoresBuscados>();
		for (IndicadoresBuscados indCalculado : indicadoresCalculados) {
			if (indCalculado.getResultado().equals("-")) {
				indicadoresNaoValorados.add(indCalculado);
			} else {
				indicadoresValorados.add(indCalculado);
			}
		}

		indicadoresPorODS = indicadoresValorados;
		int quantidadeIndicadores = indicadoresValorados.size();
		int quantidadePorPagina = 20;

		setBounds(100, 100, 900, 400);

		scrollPane = new JScrollPane();

		JLabel lblMunicipio = new JLabel("Município: " + nomeMunicipio);
		lblMunicipio.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblData = new JLabel("Data: " + data);
		lblData.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblNewLabelODS = new JLabel("ODS:");
		lblNewLabelODS.setFont(new Font("Arial", Font.PLAIN, 16));

		comboBoxPagina = new JComboBox<Object>();
		comboBoxPagina.setFont(new Font("Arial", Font.PLAIN, 16));

		for (int i = 1; (i - 1) * quantidadePorPagina <= quantidadeIndicadores; i++) {
			comboBoxPagina.addItem(i);
		}
		comboBoxPagina.setSelectedIndex(0);
		comboBoxPagina.setMaximumRowCount(5);
		
		JComboBox<Object> comboBoxODS = new JComboBox<Object>();
		comboBoxODS.addItem("Todos");
		ODS.buscarODS(comboBoxODS);
		comboBoxODS.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBoxODS.setSelectedIndex(0);
		comboBoxODS.setMaximumRowCount(5);
		
		comboBoxODS.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				comboBoxPagina.setModel(new DefaultComboBoxModel<>());
				comboBoxPagina.setMaximumRowCount(5);
				indicadoresPorODS = new ArrayList<IndicadoresBuscados>();

				if (comboBoxODS.getSelectedItem().equals("Todos")) {
					indicadoresPorODS = indicadoresValorados;
					for (int i = 1; (i - 1) * quantidadePorPagina <= quantidadeIndicadores; i++) {
						comboBoxPagina.addItem(i);
					}
					return;
				}

				for (IndicadoresBuscados indicador : indicadoresValorados) {
					if (indicador.getOds() == Integer.parseInt(comboBoxODS.getSelectedItem().toString())) {
						indicadoresPorODS.add(indicador);
					}
				}

				int quantidadeIndicadoresPorODS = indicadoresPorODS.size();
				for (int i = 1; (i - 1) * quantidadePorPagina <= quantidadeIndicadoresPorODS; i++) {
					comboBoxPagina.addItem(i);
				}
			}
		});

		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			/**
			 * Faz um gráfico para a ODS selecionada
			 */
			public void actionPerformed(ActionEvent e) {
				scrollPane.setViewportView(GraficoIndicadores.criarGrafico(indicadoresPorODS,
						Integer.parseInt(comboBoxPagina.getSelectedItem().toString())));
			}
		});
		btnConfirmar.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblPagina = new JLabel("Página:");
		lblPagina.setFont(new Font("Arial", Font.PLAIN, 16));

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 869, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(lblMunicipio, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE).addGap(12)
								.addComponent(lblNewLabelODS, GroupLayout.PREFERRED_SIZE, 50,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboBoxODS, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(12)
								.addComponent(lblPagina, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboBoxPagina, GroupLayout.PREFERRED_SIZE, 48,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnConfirmar)))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMunicipio, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBoxODS, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabelODS, GroupLayout.PREFERRED_SIZE, 23,
										GroupLayout.PREFERRED_SIZE))
						.addComponent(btnConfirmar)
						.addComponent(lblPagina, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBoxPagina, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE).addContainerGap()));
		getContentPane().setLayout(groupLayout);

		scrollPane.setViewportView(GraficoIndicadores.criarGrafico(indicadoresPorODS, Integer.parseInt(comboBoxPagina.getSelectedItem().toString())));

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
}
