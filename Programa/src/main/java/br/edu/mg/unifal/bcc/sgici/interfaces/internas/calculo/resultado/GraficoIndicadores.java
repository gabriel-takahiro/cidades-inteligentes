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
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

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
 * @author Gabriel Takahiro
 * @version 0.2
 */
public class GraficoIndicadores extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollPane;

	/**
	 * Seleciona os indicadores que serão exibidos no gráfico
	 * @param indicadoresCalculados indicadores que foram calculados
	 * @return conjunto dos valores dos indicadores selecionados
	 */
	public static CategoryDataset createDataSet(List<IndicadoresBuscados> indicadoresCalculados) {

		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		int i = 0;
		for (IndicadoresBuscados indicadoresBuscados : indicadoresCalculados) {
			if (indicadoresBuscados.getResultado().equals("-")
					|| Double.parseDouble(indicadoresBuscados.getResultado().replace(",", ".")) > 100.0) {

			} else {
				dataSet.addValue(Double.parseDouble(indicadoresBuscados.getResultado().replace(",", ".")),
						String.valueOf(indicadoresBuscados.getCodigo_indicador()) + ":"
								+ indicadoresBuscados.getNome_indicador(),
						"");
				i++;
				if(i >= 35) {
					return dataSet;
				}
			}
		}
		return dataSet;
	}

	/**
	 * Cria um gráfico de barras
	 * @param dataSet conjunto dos valores dos indicadores calculados
	 * @return gráfico de barras
	 */
	public static JFreeChart createBarChart(CategoryDataset dataSet) {

		JFreeChart graficoBarras = ChartFactory.createBarChart("Indicadores Calculados", "Indicadores", "Porcentagem",
				dataSet, PlotOrientation.HORIZONTAL, true, true, false);

		return graficoBarras;
	}

	/**
	 * Cria um painel com o gráfico de barras
	 * @param indicadoresCalculados lista de indicadores calculados
	 * @return painel do gráfico
	 */
	public static ChartPanel criarGrafico(List<IndicadoresBuscados> indicadoresCalculados) {

		CategoryDataset dataSet = createDataSet(indicadoresCalculados);

		JFreeChart grafico = createBarChart(dataSet);

		CategoryPlot plot = grafico.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.gray);
		plot.setRangeGridlinePaint(Color.lightGray);
		plot.setOutlineVisible(false);
		grafico.getLegend().setFrame(BlockBorder.NONE);
		grafico.getLegend().setItemLabelPadding(new RectangleInsets(0.5, 5.0, 0.5, 60));
		grafico.getLegend().setPadding(new RectangleInsets(40.0, 10.0, 0.0, 0.0));
		grafico.getLegend().setItemFont(new Font("Arial", Font.BOLD, 12));
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
	 * Colore o gráfico em verde (entre 70 e 100), amarelo (entre 35 e 70) e vermelho (entre 0 e 35)
	 * @param barraItem barras do gráfico
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
	 * @param listaIndicadoresSelecionados lista dos indicadores que foram selecionados para montar o gráfico
	 * @param nomeMunicipio nome do município que está sendo calculado os indicadores
	 * @param codigo_municipio código do município que está sendo calculado os indicadores
	 * @param data dados relativo ao ano
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

		setBounds(100, 100, 880, 400);

		scrollPane = new JScrollPane();

		JLabel lblMunicipio = new JLabel("Município: " + nomeMunicipio);
		lblMunicipio.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblData = new JLabel("Data: " + data);
		lblData.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblNewLabelODS = new JLabel("ODS:");
		lblNewLabelODS.setFont(new Font("Arial", Font.PLAIN, 16));

		JComboBox<Object> comboBoxODS = new JComboBox<Object>();
		comboBoxODS.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBoxODS.setMaximumRowCount(5);

		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			/**
			 * Faz um gráfico para a ODS selecionada
			 */
			public void actionPerformed(ActionEvent e) {
				if (comboBoxODS.getSelectedItem().equals("Todos")) {
					scrollPane.setViewportView(GraficoIndicadores.criarGrafico(indicadoresValorados));
					return;
				}
				CalcularParaODS(indicadoresValorados, comboBoxODS.getSelectedItem().toString());
			}
		});
		btnConfirmar.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(lblMunicipio, GroupLayout.PREFERRED_SIZE, 443, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
								.addComponent(lblNewLabelODS, GroupLayout.PREFERRED_SIZE, 56,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(comboBoxODS, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnConfirmar,
										GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMunicipio, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabelODS, GroupLayout.PREFERRED_SIZE, 23,
										GroupLayout.PREFERRED_SIZE))
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(comboBoxODS, GroupLayout.PREFERRED_SIZE, 25,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnConfirmar)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE).addContainerGap()));
		getContentPane().setLayout(groupLayout);

		comboBoxODS.addItem("Todos");
		ODS.buscarODS(comboBoxODS);
		comboBoxODS.setSelectedIndex(0);

		scrollPane.setViewportView(GraficoIndicadores.criarGrafico(indicadoresValorados));

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
	 * Monta um gráfico de barras com base na ODS que está sendo passada como parâmetro
	 * @param listaIndicadoresSelecionados lista dos indicadores selecionados
	 * @param ods número da ods para separar os indicadores
	 */
	private void CalcularParaODS(ArrayList<IndicadoresBuscados> listaIndicadoresSelecionados, String ods) {
		ArrayList<IndicadoresBuscados> indicadoresPorODS = new ArrayList<IndicadoresBuscados>();
		for (IndicadoresBuscados indicador : listaIndicadoresSelecionados) {
			if (indicador.getOds() == Integer.parseInt(ods)) {
				indicadoresPorODS.add(indicador);
			}
		}
		scrollPane.setViewportView(GraficoIndicadores.criarGrafico(indicadoresPorODS));
	}
}
