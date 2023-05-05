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
package br.edu.mg.unifal.bcc.sgici.interfaces.internas;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import br.edu.mg.unifal.bcc.sgici.banco_dados.ResultadoOperacao;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.resultado.MostrarCalculos;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.resultado.ParametrosMostrarCalculo;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.sgici.modelo.ColunaBotao;
import br.edu.mg.unifal.bcc.sgici.modelo.Indicador;
import br.edu.mg.unifal.bcc.sgici.modelo.IndicadoresBuscados;
import br.edu.mg.unifal.bcc.sgici.modelo.Meta;
import br.edu.mg.unifal.bcc.sgici.modelo.ODS;
import br.edu.mg.unifal.bcc.sgici.modelo.Variavel;

/**
 * Classe responsável pela manipulação das tabelas. Essa classe possui métodos
 * que facilitam a manipulação dos dados em tabelas, desde o preenchimento até a
 * filtragem dos dados. Além disso, também conta com métodos para centralizar
 * valores e definir cores de seleção na tabela.
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class Tabelas {

	private static Color corPadrao = new Color(255, 255, 255);
	private static Color corItemSelecionado = new Color(210, 255, 220);
	private static Color corItemAtual = new Color(160, 160, 230);

	/**
	 * Seleciona todos os campos de uma determinada coluna na tabela.
	 * 
	 * @param coluna A coluna que contém o campo a ser selecionado.
	 * @param table  A tabela em que a seleção será realizada.
	 */
	public static void selecionarTodos(int coluna, JTable table) {
		for (int i = 0; i < table.getRowCount(); i++) {
			table.setValueAt(true, i, coluna);
		}
	}
	
	/**
	 * Desmarca todos os campos de uma determinada coluna na tabela.
	 * 
	 * @param coluna A coluna que contém o campo a ser selecionado.
	 * @param table  A tabela em que a seleção será realizada.
	 */
	public static void desmarcarTodos(int coluna, JTable table) {
		for (int i = 0; i < table.getRowCount(); i++) {
			table.setValueAt(false, i, coluna);
		}
	}

	/**
	 * Retorna uma lista com os indicadores selecionados na tabela.
	 * 
	 * @param table A tabela que será utilizada.
	 * @return A lista de indicadores selecionados.
	 */
	public static ArrayList<Integer> indicadoresSelecionados(JTable table) {
		int posicaoCodigo = 0;
		int posicaoBoxSelecionar = 5;
		ArrayList<Integer> indicadores = new ArrayList<Integer>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, posicaoBoxSelecionar).toString().equals("true")) {
				indicadores.add(Integer.parseInt((String) table.getValueAt(i, posicaoCodigo)));
			}
		}
		return indicadores;
	}

	/**
	 * Retorna o indicador selecionado na tabela.
	 * 
	 * @param table A tabela em que a seleção será realizada.
	 * @return O indicador selecionado.
	 */
	public static Indicador indicadorSelecionado(JTable table) {
		int posicaoCodigo = 0;
		int posicaoMetodoCalculo = 3;
		int posicaoBoxSelecionar = 5;
		Indicador indicador = new Indicador();
		int j = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, posicaoBoxSelecionar).toString().equals("true")) {
				j++;
				indicador = new Indicador(Integer.parseInt((String) table.getValueAt(i, posicaoCodigo)),
						(String) table.getValueAt(i, posicaoMetodoCalculo));
			}
		}
		if (j == 0) {
			throw new RuntimeException("Selecione um indicador.");
		}
		if (j > 1) {
			throw new RuntimeException("Selecione apenas um indicador");
		}
		return indicador;
	}

	/**
	 * Mostra uma tabela com todos os indicadores disponíveis no banco de dados.
	 * 
	 * @param coluna A coluna que contém o campo a ser selecionado.
	 * @param table  A tabela que será utilizada.
	 */
	public static void mostrarIndicadores(int coluna, JTable table) {
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false, false, false, true };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		Indicador.mostrarIndicadores(table, true);
		table.getTableHeader().setReorderingAllowed(false);

		TableColumn tc = table.getColumnModel().getColumn(coluna);
		tc.setCellEditor(table.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));

		for (int i = 0; i < table.getRowCount(); i++) {
			table.setValueAt(false, i, coluna);
		}

		centralizaTabela(table, coluna);
	}

	/**
	 * Mostra uma tabela com todos os indicadores disponíveis no banco de dados.
	 * 
	 * @param table A tabela que será utilizada.
	 */
	public static void mostrarIndicadores(JTable table) {
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false, false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		Indicador.mostrarIndicadores(table, false);

		centralizaTabela(table);
	}

	/**
	 * Mostra uma tabela com todos os indicadores que possuem resultado para um
	 * município em uma determinada data.
	 * 
	 * @param tableIndicadoresComResultado A tabela dos indicadores com resultado.
	 * @param tableIndicadoresSemResultado A tabela dos indicadores sem resultado.
	 * @param indicadoresNaoValorados      A lista de indicadores sem resultado.
	 * @param indicadoresValorados         A lista de indicadores com resultado.
	 * @param nomeMunicipio                O nome do município que está sendo feito
	 *                                     o cálculo dos indicadores.
	 * @param codigo_municipio             O código do município utilizado para
	 *                                     calcular o indicador.
	 * @param data                         O ano utilizado para calcular o
	 *                                     indicador.
	 * @param valorRetroativo              O número de anos a mais utilizados para a
	 *                                     busca retroativa das variáveis.
	 */
	public static void mostrarIndicadoresComResultado(JTable tableIndicadoresComResultado,
			JTable tableIndicadoresSemResultado, ArrayList<IndicadoresBuscados> indicadoresNaoValorados,
			ArrayList<IndicadoresBuscados> indicadoresValorados, String nomeMunicipio, int codigo_municipio,
			String data, int valorRetroativo) {
		tableIndicadoresComResultado.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false, false, false,
					false, true };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		DefaultTableModel model = (DefaultTableModel) tableIndicadoresComResultado.getModel();

		String[] nomeColuna = new String[12];
		nomeColuna[0] = "Código do indicador";
		nomeColuna[1] = "Número da ODS";
		nomeColuna[2] = "Número da meta";
		nomeColuna[3] = "Nome do indicador";
		nomeColuna[4] = "Código do município";
		nomeColuna[5] = "Nome do município";
		nomeColuna[6] = "Nome da UF";
		nomeColuna[7] = "Data";
		nomeColuna[8] = "Resultado";
		nomeColuna[9] = "Valor oficial";
		nomeColuna[10] = "Indicador padrão";
		nomeColuna[11] = "Mostrar cálculos";
		model.setColumnIdentifiers(nomeColuna);

		for (IndicadoresBuscados indicador : indicadoresValorados) {
			String[] linha = { Integer.toString(indicador.getCodigo_indicador()), Integer.toString(indicador.getOds()),
					indicador.getMeta(), indicador.getNome_indicador(),
					Integer.toString(indicador.getCodigo_municipio()), indicador.getNome_municipio(),
					indicador.getNome_uf(), indicador.getData(), indicador.getResultado(),
					Boolean.toString(indicador.isValor_oficial()), Boolean.toString(indicador.isPadrao()) };
			model.addRow(linha);
		}

		int posicaoValorOficial = 9;
		corNaLinha(tableIndicadoresComResultado, posicaoValorOficial);
		tableIndicadoresComResultado.getTableHeader().setReorderingAllowed(false);
		Action mostrarCalculo = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				int posicaoCodigo = 0;
				int modelRow = Integer.valueOf(e.getActionCommand());
				for (IndicadoresBuscados indicador : indicadoresValorados) {
					if (Integer.parseInt(tableIndicadoresComResultado.getValueAt(modelRow, posicaoCodigo)
							.toString()) == indicador.getCodigo_indicador()) {
						IndicadoresBuscados indicadorMostrar = indicador;
						MostrarCalculos janelaCalculos = new MostrarCalculos(new ParametrosMostrarCalculo(
								indicadorMostrar, codigo_municipio, data, nomeMunicipio, valorRetroativo,
								tableIndicadoresComResultado, tableIndicadoresSemResultado, indicadoresNaoValorados,
								indicadoresValorados, Boolean.parseBoolean(tableIndicadoresComResultado
										.getValueAt(modelRow, posicaoValorOficial).toString())));
						JanelaPrincipal.abrirJanelas(janelaCalculos);
						return;
					}
				}
			}
		};
		new ColunaBotao(tableIndicadoresComResultado, mostrarCalculo, 11, "Mostrar");
	}

	/**
	 * Mostra uma tabela com todos os indicadores que não possuem resultado para um
	 * município em uma determinada data.
	 * 
	 * @param tableIndicadoresSemResultado A tabela dos indicadores sem resultado.
	 * @param indicadoresNaoValorados      A lista de indicadores sem resultado.
	 * @param codigo_municipio             O código do município utilizado para
	 *                                     calcular o indicador.
	 * @param data                         O ano utilizado para calcular o
	 *                                     indicador.
	 */
	public static void mostrarIndicadoresSemResultado(JTable tableIndicadoresSemResultado,
			ArrayList<IndicadoresBuscados> indicadoresNaoValorados, int codigo_municipio, String data) {
		tableIndicadoresSemResultado.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, true, true, false, true };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {

				int posicaoValorOficial = 8;
				if (tableIndicadoresSemResultado.getValueAt(rowIndex, posicaoValorOficial).equals("true")) {
					return false;
				}

				if (columnIndex == 9) {
					return true;
				}
				if (tableIndicadoresSemResultado.getValueAt(rowIndex, posicaoValorOficial).equals("")
						|| tableIndicadoresSemResultado.getValueAt(rowIndex, posicaoValorOficial).equals(null)) {
					return false;
				}
				return canEdit[columnIndex];
			}
		});

		Indicador.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados,
				codigo_municipio, data);

		int posicaoBoxSeleciona = 9;

		TableColumn tc = tableIndicadoresSemResultado.getColumnModel().getColumn(posicaoBoxSeleciona);
		tc.setCellEditor(tableIndicadoresSemResultado.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(tableIndicadoresSemResultado.getDefaultRenderer(Boolean.class));

		for (int i = 0; i < tableIndicadoresSemResultado.getRowCount(); i++) {
			tableIndicadoresSemResultado.setValueAt(false, i, posicaoBoxSeleciona);
		}
		zebraTabela(tableIndicadoresSemResultado, codigo_municipio);
		tableIndicadoresSemResultado.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * Mostra uma tabela com todas as metas disponíveis no banco de dados.
	 * 
	 * @param coluna A coluna que contém o campo a ser selecionado.
	 * @param table  A tabela que será utilizada.
	 */
	public static void mostrarMetas(int coluna, JTable table) {
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false, true };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		Meta.mostrarMetas(table, true);
		table.getTableHeader().setReorderingAllowed(false);

		TableColumn tc = table.getColumnModel().getColumn(coluna);
		tc.setCellEditor(table.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));

		for (int i = 0; i < table.getRowCount(); i++) {
			table.setValueAt(false, i, coluna);
		}

		centralizaTabela(table, coluna);
	}

	/**
	 * Mostra uma tabela com todas as metas disponíveis no banco de dados.
	 * 
	 * @param table A tabela que será utilizada.
	 */
	public static void mostrarMetas(JTable table) {
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		Meta.mostrarMetas(table, false);

		centralizaTabela(table);
	}

	/**
	 * Retorna uma lista de metas selecionadas na tabela.
	 * 
	 * @param table A tabela que será utilizada.
	 * @return Uma lista contendo as metas selecionadas.
	 */
	public static ArrayList<Meta> metasSelecionadas(JTable table) {
		int posicaoNumeroODS = 0;
		int posicaoNumeroMeta = 1;
		int posicaoTextoMeta = 2;
		int posicaoBoxSeleciona = 3;
		ArrayList<Meta> metas = new ArrayList<Meta>();
		for (int i = posicaoNumeroODS; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, posicaoBoxSeleciona).toString().equals("true")) {
				metas.add(new Meta((String) table.getValueAt(i, posicaoNumeroMeta),
						(String) table.getValueAt(i, posicaoTextoMeta),
						Integer.parseInt((String) table.getValueAt(i, posicaoNumeroODS))));
			}
		}
		return metas;
	}

	/**
	 * Retorna a meta selecionada na tabela. Caso nenhuma meta seja selecionada ou
	 * mais de uma meta seja selecionada, lança uma exceção.
	 * 
	 * @param table A tabela que será utilizada.
	 * @return A meta selecionada.
	 * @throws RuntimeException se nenhuma meta for selecionada ou se mais de uma
	 *                          meta for selecionada
	 */
	public static Meta metaSelecionada(JTable table) {
		int posicaoNumeroODS = 0;
		int posicaoNumeroMeta = 1;
		int posicaoBoxSeleciona = 3;
		Meta meta = new Meta();
		int j = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, posicaoBoxSeleciona).toString().equals("true")) {
				j++;
				meta = new Meta(Integer.parseInt((String) table.getValueAt(i, posicaoNumeroODS)),
						(String) table.getValueAt(i, posicaoNumeroMeta));
			}
		}
		if (j == 0) {
			throw new RuntimeException("Selecione uma meta.");
		}
		if (j > 1) {
			throw new RuntimeException("Selecione apenas uma meta.");
		}
		return meta;
	}

	/**
	 * Mostra uma tabela com todas as ODS disponíveis no banco de dados.
	 * 
	 * @param coluna A coluna que contém o campo a ser selecionado.
	 * @param table  A tabela que será utilizada.
	 */
	public static void mostrarODS(int coluna, JTable table) {
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, true };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		ODS.mostrarODS(table, true);
		table.getTableHeader().setReorderingAllowed(false);

		TableColumn tc = table.getColumnModel().getColumn(coluna);
		tc.setCellEditor(table.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));

		for (int i = 0; i < table.getRowCount(); i++) {
			table.setValueAt(false, i, coluna);
		}

		centralizaTabela(table, coluna);
	}

	/**
	 * Mostra uma tabela com todas as ODS disponíveis no banco de dados.
	 * 
	 * @param table A tabela que será utilizada.
	 */
	public static void mostrarODS(JTable table) {
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		ODS.mostrarODS(table, false);

		centralizaTabela(table);
	}

	/**
	 * Coloca todas as ODS selecionadas em uma lista.
	 * 
	 * @param table A tabela que será utilizada.
	 * @return Uma lista contendo as ODS selecionadas.
	 */
	public static ArrayList<ODS> odsSelecionadas(JTable table) {
		int posicaoNumeroODS = 0;
		int posicaoNomeObjetivo = 1;
		int posicaoBoxSeleciona = 2;
		ArrayList<ODS> objetivos = new ArrayList<ODS>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, posicaoBoxSeleciona).toString().equals("true")) {
				objetivos.add(new ODS(Integer.parseInt((String) table.getValueAt(i, posicaoNumeroODS)),
						(String) table.getValueAt(i, posicaoNomeObjetivo)));
			}
		}
		return objetivos;
	}

	/**
	 * Retorna a ODS selecionada na tabela.
	 * 
	 * @param table A tabela que será utilizada.
	 * @return A ODS selecionada.
	 */
	public static ODS odsSelecionada(JTable table) {
		int posicaoNumeroODS = 0;
		int posicaoNomeObjetivo = 1;
		int posicaoBoxSeleciona = 2;
		ODS ods = new ODS();
		int j = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, posicaoBoxSeleciona).toString().equals("true")) {
				j++;
				ods = new ODS(Integer.parseInt((String) table.getValueAt(i, posicaoNumeroODS)),
						(String) table.getValueAt(i, posicaoNomeObjetivo));
			}
		}
		if (j == 0) {
			throw new RuntimeException("Selecione uma ODS.");
		}
		if (j > 1) {
			throw new RuntimeException("Selecione apenas uma ODS.");
		}
		return ods;
	}

	/**
	 * Mostra uma tabela com todas as variáveis disponíveis no banco de dados.
	 * 
	 * @param coluna A coluna que contém o campo a ser selecionado.
	 * @param table  A tabela que será utilizada.
	 */
	public static void mostrarVariaveis(int coluna, JTable table) {
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false, false, false, false, true };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		Variavel.mostrarVariaveis(table, true);
		table.getTableHeader().setReorderingAllowed(false);

		TableColumn tc = table.getColumnModel().getColumn(coluna);
		tc.setCellEditor(table.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));

		for (int i = 0; i < table.getRowCount(); i++) {
			table.setValueAt(false, i, coluna);
		}

		centralizaTabela(table, coluna);
	}

	/**
	 * Mostra uma tabela com todas as variáveis disponíveis no banco de dados.
	 * 
	 * @param table A tabela que será utilizada.
	 */
	public static void mostrarVariaveis(JTable table) {
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		Variavel.mostrarVariaveis(table, false);

		centralizaTabela(table);
	}

	/**
	 * Coloca todas as variáveis selecionadas em uma lista.
	 * 
	 * @param table A tabela que será utilizada.
	 * @return Uma lista das variáveis selecionadas.
	 */
	public static ArrayList<Variavel> variaveisSelecionadas(JTable table) {
		int posicaoCodigoVariavel = 0;
		int posicaoTipoBanco = 1;
		int posicaoNome = 2;
		int posicaoCodigoBanco = 3;
		int posicaoAtualizacao = 4;
		int posicaoVariavelPadrao = 5;
		int posicaoBoxSeleciona = 6;
		ArrayList<Variavel> variaveis = new ArrayList<Variavel>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, posicaoBoxSeleciona).toString().equals("true")) {
				String codigo_banco = (String) table.getValueAt(i, posicaoCodigoBanco);
				if (codigo_banco == null) {
					variaveis.add(new Variavel(Integer.parseInt((String) table.getValueAt(i, posicaoCodigoVariavel)),
							(String) table.getValueAt(i, posicaoTipoBanco), (String) table.getValueAt(i, posicaoNome),
							null, (String) table.getValueAt(i, posicaoAtualizacao),
							Boolean.parseBoolean((String) table.getValueAt(i, posicaoVariavelPadrao))));
				} else {
					variaveis.add(new Variavel(Integer.parseInt((String) table.getValueAt(i, posicaoCodigoVariavel)),
							(String) table.getValueAt(i, posicaoTipoBanco), (String) table.getValueAt(i, posicaoNome),
							codigo_banco, (String) table.getValueAt(i, posicaoAtualizacao),
							Boolean.parseBoolean((String) table.getValueAt(i, posicaoVariavelPadrao))));
				}
			}
		}
		return variaveis;
	}

	/**
	 * Retorna a variável selecionada na tabela.
	 * 
	 * @param table A tabela que será utilizada.
	 * @return Variável selecionada.
	 */
	public static Variavel variavelSelecionada(JTable table) {
		int posicaoCodigoVariavel = 0;
		int posicaoCodigoBanco = 3;
		int posicaoBoxSeleciona = 6;
		Variavel variavel = new Variavel();
		int j = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, posicaoBoxSeleciona).toString().equals("true")) {
				j++;
				String codigo_banco = (String) table.getValueAt(i, posicaoCodigoBanco);
				if (codigo_banco == null) {
					variavel = new Variavel(Integer.parseInt((String) table.getValueAt(i, posicaoCodigoVariavel)),
							null);
				} else {
					variavel = new Variavel(Integer.parseInt((String) table.getValueAt(i, posicaoCodigoVariavel)),
							codigo_banco);
				}
			}
		}
		if (j == 0) {
			throw new RuntimeException("Selecione uma variável.");
		}
		if (j > 1) {
			throw new RuntimeException("Selecione apenas uma variável.");
		}
		return variavel;
	}

	/**
	 * Mostra o resultado das operações realizadas no banco de dados.
	 * 
	 * @param table              A tabela que será utilizada.
	 * @param resultadoOperacoes Lista contendo informações das operações realizadas
	 *                           no banco de dados, incluindo o nome da operação e o
	 *                           seu status (sucesso ou falha).
	 */
	public static void logCriar(JTable table, List<ResultadoOperacao> resultadoOperacoes) {
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String[] nomeColuna = new String[2];
		nomeColuna[0] = "Operação";
		nomeColuna[1] = "Resultado";
		model.setColumnIdentifiers(nomeColuna);
		table.setRowSorter(new TableRowSorter<>(model));

		for (ResultadoOperacao operacao : resultadoOperacoes) {
			String[] linha = { operacao.getOperacao(), operacao.getResultado() };
			model.addRow(linha);
		}

		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;
			private int posicaoResultado = 1;

			@Override
			public Component getTableCellRendererComponent(JTable table2, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				label.setHorizontalAlignment(JLabel.CENTER);

				if (table2.getValueAt(row, posicaoResultado) == null) {
					label.setBackground(new Color(250, 255, 178));
					label.setForeground(Color.BLACK);
					return label;
				}
				if (table2.getValueAt(row, posicaoResultado).equals("Sucesso")) {
					label.setBackground(new Color(173, 255, 180));
					label.setForeground(Color.BLACK);
					return label;
				}

				label.setBackground(new Color(255, 173, 173));
				label.setForeground(Color.BLACK);
				return label;
			}
		});
	}

	/**
	 * Colore a tabela dos indicadores com resultado, indicando se o indicador foi
	 * calculado utilizando os valores oficiais obtidos pelo próprio software (linha
	 * na cor verde claro), ou se foi calculado utilizando valores inseridos
	 * manualmente (linha na cor vermelha).
	 * 
	 * @param tableIndicadoresComResultado A tabela de indicadores com resultado.
	 * @param coluna                       A coluna que contém o campo a ser
	 *                                     selecionado.
	 */
	public static void corNaLinha(JTable tableIndicadoresComResultado, int coluna) {
		tableIndicadoresComResultado.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;
			private Color corIndicadorValorOficial = new Color(173, 255, 180);
			private Color corIndicadorValorNaoOficial = new Color(255, 173, 173);
			private Color corIndicadorPadrao = new Color(178, 252, 255);
			private Color corIndicadorNaoPadrao = new Color(250, 255, 178);

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(tableIndicadoresComResultado, value,
						isSelected, hasFocus, row, column);

				Object texto = table.getValueAt(row, coluna);

				// Centralizar conteúdo das células
				label.setHorizontalAlignment(JLabel.CENTER);

				if (texto != null && texto.toString().equals("false")) {
					label.setBackground(corIndicadorValorNaoOficial);
					label.setForeground(Color.BLACK);
				} else {
					label.setBackground(corIndicadorValorOficial);
					label.setForeground(Color.BLACK);
				}

				if (column == 10) {
					texto = table.getValueAt(row, column);
					if (texto.equals("false")) {
						label.setBackground(corIndicadorNaoPadrao);
						label.setForeground(Color.BLACK);
					} else {
						label.setBackground(corIndicadorPadrao);
						label.setForeground(Color.BLACK);
					}
				}

				if (isSelected) {
					label.setBackground(corItemAtual);
					label.setForeground(Color.BLACK);
				}
				return label;
			}
		});
		tableIndicadoresComResultado.setSelectionBackground(corItemAtual);
		tableIndicadoresComResultado.setSelectionForeground(Color.BLACK);
	}

	/**
	 * Colore a tabela dos indicadores sem resultado, separando os indicadores
	 * (linha na cor amarela) das variáveis (linha na cor branca).
	 * 
	 * @param tabelaIndicadoresSemResultado A tabela dos indicadores sem resultado.
	 * @param codigo_municipio              O código do município.
	 */
	public static void zebraTabela(JTable tabelaIndicadoresSemResultado, int codigo_municipio) {
		// Definir cor de fundo e cor de texto das células não selecionadas
		tabelaIndicadoresSemResultado.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			private Color indicadorSelecionado = new Color(255, 220, 110);
			private Color indicadorNaoSelecionado = new Color(250, 255, 178);
			private Color variavelSelecionada = new Color(220, 220, 220);
			private int posicaoCodigoIndicador = 0;
			private int posicaoBoxSeleciona = 9;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				// Centralizar conteúdo das células
				label.setHorizontalAlignment(JLabel.CENTER);

				if (!(table.getValueAt(row, posicaoCodigoIndicador).equals("")
						|| table.getValueAt(row, posicaoCodigoIndicador).equals(null))) {
					if (table.getValueAt(row, posicaoBoxSeleciona) != null
							&& table.getValueAt(row, posicaoBoxSeleciona).toString().equals("true")) {
						label.setBackground(indicadorSelecionado);
						label.setForeground(Color.BLACK);
						return label;
					} else {
						label.setBackground(indicadorNaoSelecionado);
						label.setForeground(Color.BLACK);
					}
				} else {
					if (table.getValueAt(row, posicaoBoxSeleciona) != null
							&& table.getValueAt(row, posicaoBoxSeleciona).toString().equals("true")) {
						label.setBackground(variavelSelecionada);
						label.setForeground(Color.BLACK);
						return label;
					} else {
						label.setBackground(corPadrao);
						label.setForeground(Color.BLACK);
					}
				}

				// Definir cor de fundo das células selecionadas
				if (isSelected) {
					label.setBackground(corItemAtual);
					label.setForeground(new Color(0, 0, 0));
				}

				return label;
			}
		});
		tabelaIndicadoresSemResultado.setSelectionBackground(corItemAtual);
		tabelaIndicadoresSemResultado.setSelectionForeground(new Color(0, 0, 0));
	}

	/**
	 * Centraliza os valores na tabela e defini as cores de seleção dos elementos.
	 * 
	 * @param table A tabela que será utilizada.
	 */
	public static void centralizaTabela(JTable table) {
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				// Centralizar conteúdo das células
				label.setHorizontalAlignment(JLabel.CENTER);

				// Definir cor de fundo das células selecionadas
				if (isSelected) {
					label.setBackground(corItemAtual);
					label.setForeground(Color.BLACK);
				} else {
					label.setBackground(corPadrao);
					label.setForeground(Color.BLACK);
				}

				return label;
			}
		});
		table.setSelectionBackground(corItemAtual);
		table.setSelectionForeground(Color.BLACK);
	}

	/**
	 * Centraliza os valores na tabela e defini as cores de seleção dos elementos.
	 * 
	 * @param table  A tabela que será utilizada.
	 * @param coluna A coluna que contém o campo a ser selecionado.
	 */
	public static void centralizaTabela(JTable table, int coluna) {
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				// Centralizar conteúdo das células
				label.setHorizontalAlignment(JLabel.CENTER);

				if (table.getValueAt(row, coluna).toString().equals("true")) {
					label.setBackground(corItemSelecionado);
					label.setForeground(Color.BLACK);
					return label;
				}

				// Definir cor de fundo das células selecionadas
				if (isSelected) {
					label.setBackground(corItemAtual);
					label.setForeground(Color.BLACK);
				} else {
					label.setBackground(corPadrao);
					label.setForeground(Color.BLACK);
				}

				return label;
			}
		});
		table.setSelectionBackground(corItemAtual);
		table.setSelectionForeground(Color.BLACK);
	}
}
