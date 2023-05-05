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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.Tabelas;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.ProgressoCalculo;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.sgici.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.sgici.modelo.IndicadoresBuscados;
import br.edu.mg.unifal.bcc.sgici.modelo.ValorVariavel;

/**
 * Classe responsável pela interface que exibe os indicadores calculados
 * 
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

	private ArrayList<Integer> listaIndicadoresSelecionados;
	private String nomeMunicipio;
	private int codigo_municipio;
	private String data;
	private int valorRetroativo;

	/**
	 * Executa a interface que exibe os indicadores calculados
	 * 
	 * @param listaIndicadoresSelecionados lista dos indicadores selecionados para o
	 *                                     cálculo
	 * @param nomeMunicipio                nome do município que foi utilizado para
	 *                                     o cálculo dos indicadores
	 * @param codigo_municipio             código do município que foi utilizado
	 *                                     para o cálculo dos indicadores
	 * @param data                         ano que foi utilizado para calcular os
	 *                                     indicadores
	 * @param valorRetroativo              quantidade de anos a mais utilizados para
	 *                                     a busca retroativa dos variáveis
	 */
	public TabelaIndicadoresCalculados(ArrayList<Integer> listaIndicadoresSelecionados, String nomeMunicipio,
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
			/**
			 * Realiza o calculo dos indicadores e o salvamento dos valores das variáveis
			 * preenchidas no programa
			 */
			public void actionPerformed(ActionEvent e) {
				if (JanelaPrincipal.isCalculando()) {
					new JanelaMensagem(
							"O programa já está calculando alguns indicadores.\nTente novamente após o cálculo desses indicadores.");
					return;
				}
				Runnable tarefa = new Runnable() {
					public void run() {
						ProgressoCalculo progressoCalculo = new ProgressoCalculo(nomeMunicipio);
						JanelaPrincipal.abrirJanelas(progressoCalculo);
						
						try {
							ArrayList<Integer> indicadoresCalculadosTabela = calcularIndicadores(codigo_municipio, data);
							if(indicadoresCalculadosTabela.isEmpty()) {
								Tabelas.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados,
										codigo_municipio, data);
								progressoCalculo.dispose();
								return;
							}
							List<IndicadoresBuscados> indicadoresQueForamCalculados = new ArrayList<IndicadoresBuscados>();
							
							for (Integer codigo : indicadoresCalculadosTabela) {
								indicadoresNaoValorados.forEach(ind -> {
									if (ind.getCodigo_indicador() == codigo) {
										indicadoresQueForamCalculados.add(ind);
									}
								});
							}
							indicadoresQueForamCalculados.forEach(indicador -> {
								indicadoresNaoValorados.remove(indicador);
							});
							
							Tabelas.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados,
									codigo_municipio, data);
							
							for (IndicadoresBuscados indCalculado : listaIndicadoresCalculados) {
								indicadoresValorados.add(indCalculado);
							}
							
							Tabelas.mostrarIndicadoresComResultado(tableIndicadoresComResultado,
									tableIndicadoresSemResultado, indicadoresNaoValorados, indicadoresValorados,
									nomeMunicipio, codigo_municipio, data, valorRetroativo);
							progressoCalculo.dispose();
						} catch (Exception e) {
							progressoCalculo.dispose();
							new JanelaMensagem(e.getMessage());
						}
					}
				};
				JanelaPrincipal.executor.execute(tarefa);
			}
		});
		btnCalcular.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Salvar como");
				fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos do Excel (*.xlsx)", "xlsx"));
				int userSelection = fileChooser.showSaveDialog(null);
				if (userSelection == JFileChooser.APPROVE_OPTION) {
					// Criar o arquivo Excel com o nome e o diretório escolhidos pelo usuário
					File fileToSave = fileChooser.getSelectedFile();
					String filePath = fileToSave.getAbsolutePath();
					// Verificar se o nome do arquivo possui a extensão .xlsx, se não, adicionar
					// automaticamente
					if (!filePath.endsWith(".xlsx")) {
						filePath += ".xlsx";
						fileToSave = new File(filePath);
					}
					try {
						XSSFWorkbook workbook = new XSSFWorkbook();
						XSSFSheet sheet = workbook.createSheet("Dados da tabela");
						// Copiar os dados da tabela para o arquivo Excel, incluindo o cabeçalho
						XSSFRow headerRow = sheet.createRow(0);
						for (int j = 0; j < tableIndicadoresComResultado.getColumnCount() - 1; j++) {
							XSSFCell cell = headerRow.createCell(j);
							cell.setCellValue(tableIndicadoresComResultado.getColumnName(j));
						}
						for (int i = 0; i < tableIndicadoresComResultado.getRowCount(); i++) {
							XSSFRow row = sheet.createRow(i + 1);
							for (int j = 0; j < tableIndicadoresComResultado.getColumnCount() - 1; j++) {
								XSSFCell cell = row.createCell(j);
								try {
									cell.setCellValue(tableIndicadoresComResultado.getValueAt(i, j).toString());
								} catch (Exception e1) {
									cell.setCellValue("");
								}
							}
						}

						// Salvar o arquivo Excel
						FileOutputStream outputStream = new FileOutputStream(fileToSave);
						workbook.write(outputStream);
						workbook.close();
						outputStream.close();

						new JanelaMensagem("Tabela salva em " + filePath);
					} catch (IOException ex) {
						new JanelaMensagem("Falha ao salvar a tabela em " + filePath);
					}
				}
			}
		});

		btnExportar.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblIndicadoresSemValor, GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnExportar, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnCalcular, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
				.addComponent(scrollPaneTabelaIndicadoresSemResultado, GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
				.addComponent(scrollPaneTabelaIndicadoresComResultado, GroupLayout.DEFAULT_SIZE, 824, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneTabelaIndicadoresComResultado, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblIndicadoresSemValor, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnCalcular, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnExportar, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPaneTabelaIndicadoresSemResultado, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
		);

		tableIndicadoresSemResultado = new JTable();
		scrollPaneTabelaIndicadoresSemResultado.setViewportView(tableIndicadoresSemResultado);

		tableIndicadoresComResultado = new JTable();
		scrollPaneTabelaIndicadoresComResultado.setViewportView(tableIndicadoresComResultado);
		contentPane.setLayout(gl_contentPane);

		try {
			Tabelas.mostrarIndicadoresComResultado(tableIndicadoresComResultado, tableIndicadoresSemResultado,
					indicadoresNaoValorados, indicadoresValorados, nomeMunicipio, codigo_municipio, data,
					valorRetroativo);
			Tabelas.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados,
					codigo_municipio, data);
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
	 * Insere os valores das variáveis colocadas a mão no banco de dados e recalcula
	 * os indicadores selecionados. Somente faz as operações nas linhas que forem
	 * selecionadas na interface.
	 * 
	 * @param codigo_municipio código do município que será utilizado para o cálculo
	 *                         dos indicadores
	 * @param data             ano que será utilizado para calcular os indicadores
	 * @return lista de indicadores
	 */
	private ArrayList<Integer> calcularIndicadores(int codigo_municipio, String data) {
		ArrayList<Integer> indicadores = new ArrayList<Integer>();
		for (int i = 0; i < tableIndicadoresSemResultado.getRowCount(); i++) {
			if (tableIndicadoresSemResultado.getValueAt(i, 9).toString().equals("true")
					&& !(tableIndicadoresSemResultado.getValueAt(i, 0).toString().isBlank())) {
				indicadores.add(Integer.parseInt(tableIndicadoresSemResultado.getValueAt(i, 0).toString()));
			}
			if (tableIndicadoresSemResultado.getValueAt(i, 0).toString().isBlank()
					&& tableIndicadoresSemResultado.getValueAt(i, 9).toString().equals("true")) {
				try {
					ValorVariavel.inserirUpdateValorVariavel(
							Integer.parseInt(tableIndicadoresSemResultado.getValueAt(i, 3).toString()),
							codigo_municipio, tableIndicadoresSemResultado.getValueAt(i, 5).toString(),
							tableIndicadoresSemResultado.getValueAt(i, 6).toString(),
							Boolean.parseBoolean(tableIndicadoresSemResultado.getValueAt(i, 7).toString().toLowerCase()), false);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

		}
 
		if(indicadores.isEmpty()) {
			return indicadores;
		}
		
		try {
			List<IndicadoresBuscados> indicadoresCalculados = CalculoIndicador.calculaIndicadores(indicadores,
					codigo_municipio, data, false, 0);

			List<IndicadoresBuscados> indicadoresSemResultado = new ArrayList<IndicadoresBuscados>();
			List<Integer> indicadoresParaExcluir = new ArrayList<Integer>();
			
			for (IndicadoresBuscados indicadoresBuscados : indicadoresCalculados) {
				if (indicadoresBuscados.getResultado().equals("-")) {
					indicadoresSemResultado.add(indicadoresBuscados);
					indicadores.forEach(codigo -> {
						if (codigo == indicadoresBuscados.getCodigo_indicador()) {
							indicadoresParaExcluir.add(codigo);
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
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

		return indicadores;
	}

	/**
	 * Remove o indicador da tabela de indicadores calculados e passa ele para a
	 * tabela de indicadores não calculados. do banco de dados.
	 * 
	 * @param indicador                    indicador que será excluído da tabela de
	 *                                     indicadores calculados
	 * @param codigo_municipio             código do município que foi utilizado
	 *                                     para calcular os indicadores
	 * @param data                         ano que foi utilizado para calcular os
	 *                                     indicadores
	 * @param nomeMunicipio                nome do município
	 * @param valorRetroativo              quantidade de anos a mais utilizados para
	 *                                     a busca retroativa dos variáveis
	 * @param tableIndicadoresComResultado tabela dos indicadores que possuem
	 *                                     resultado
	 * @param tableIndicadoresSemResultado tabela dos indicadores que não possuem
	 *                                     resultado
	 * @param indicadoresNaoValorados      lista de indicadores não valorados
	 * @param indicadoresValorados         lista de indicadores valorados
	 */
	public static void removerIndicadorCalculado(IndicadoresBuscados indicador, int codigo_municipio, String data,
			String nomeMunicipio, int valorRetroativo, JTable tableIndicadoresComResultado,
			JTable tableIndicadoresSemResultado, ArrayList<IndicadoresBuscados> indicadoresNaoValorados,
			ArrayList<IndicadoresBuscados> indicadoresValorados) {
		indicadoresNaoValorados.add(indicador);
		indicadoresValorados.remove(indicador);

		Tabelas.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados, codigo_municipio,
				data);

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
			Tabelas.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados,
					codigo_municipio, data);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
