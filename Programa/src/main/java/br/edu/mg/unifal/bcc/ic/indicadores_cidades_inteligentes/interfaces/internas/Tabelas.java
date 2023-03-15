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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas;

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

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.banco_dados.ResultadoOperacao;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.ColunaBotao;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.IndicadoresBuscados;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Meta;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.ODS;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;

/**
 * Classe responsável pela manipulação das tabelas
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class Tabelas {

	/**
	 * Seleciona todos os campos da tabela
	 * 
	 * @param coluna indica em qual coluna está o campo a ser selecionado
	 * @param table  tabela que será utilizada
	 */
	public static void selecionarTodos(int coluna, JTable table) {
		for (int i = 0; i < table.getRowCount(); i++) {
			table.setValueAt(true, i, coluna);
		}
	}

	/**
	 * Coloca todos os indicadores selecionados em uma lista
	 * 
	 * @param table tabela que será utilizada
	 * @return lista com os indicadores selecionados
	 */
	public static ArrayList<Indicador> indicadoresSelecionados(JTable table) {
		ArrayList<Indicador> indicadores = new ArrayList<Indicador>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 5).toString().equals("true")) {
				indicadores.add(new Indicador(Integer.parseInt((String) table.getValueAt(i, 0)),
						(String) table.getValueAt(i, 1), (String) table.getValueAt(i, 2),
						(String) table.getValueAt(i, 3), Boolean.parseBoolean((String) table.getValueAt(i, 4))));
			}
		}
		return indicadores;
	}

	/**
	 * Retorna o indicador selecionado na tabela
	 * 
	 * @param table tabela que será utilizada
	 * @return indicador selecionado
	 */
	public static Indicador indicadorSelecionado(JTable table) {
		Indicador indicador = new Indicador();
		int j = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 5).toString().equals("true")) {
				j++;
				indicador = new Indicador(Integer.parseInt((String) table.getValueAt(i, 0)),
						(String) table.getValueAt(i, 3));
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
	 * Mostra uma tabela com todos os indicadores disponíveis no banco de dados
	 * 
	 * @param coluna indica em qual coluna está o campo a ser selecionado
	 * @param table  tabela que será utilizada
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
	}

	/**
	 * Mostra uma tabela com todos os indicadores disponíveis no banco de dados
	 * 
	 * @param table tabela que será utilizada
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
	}

	/**
	 * Mostra uma tabela com todos os indicadores que possuem resultado
	 * 
	 * @param tableIndicadoresComResultado tabela dos indicadores com resultado
	 * @param tableIndicadoresSemResultado tabela dos indicadores sem resultado
	 * @param indicadoresNaoValorados      lista de indicadores sem resultado
	 * @param indicadoresValorados         lista de indicadores com resultado
	 * @param nomeMunicipio                nome do município que está sendo feito o
	 *                                     cálculo dos indicadores
	 * @param codigo_municipio             código do município utilizado para
	 *                                     calcular o indicador
	 * @param data                         ano utilizado para calcular o indicador
	 * @param valorRetroativo              anos a mais utilizados para a busca
	 *                                     retroativa das variáveis
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

		corNaLinha(tableIndicadoresComResultado, 9);
		tableIndicadoresComResultado.getTableHeader().setReorderingAllowed(false);
		Action mostrarCalculo = new AbstractAction() {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				int modelRow = Integer.valueOf(e.getActionCommand());
				for (IndicadoresBuscados indicador : indicadoresValorados) {
					if (Integer.parseInt(tableIndicadoresComResultado.getValueAt(modelRow, 0).toString()) == indicador
							.getCodigo_indicador()) {
						IndicadoresBuscados indicadorMostrar = indicador;
						MostrarCalculos janelaCalculos = new MostrarCalculos(new ParametrosMostrarCalculo(
								indicadorMostrar, codigo_municipio, data, nomeMunicipio, valorRetroativo,
								tableIndicadoresComResultado, tableIndicadoresSemResultado, indicadoresNaoValorados,
								indicadoresValorados,
								Boolean.parseBoolean(tableIndicadoresComResultado.getValueAt(modelRow, 9).toString())));
						JanelaPrincipal.abrirJanelas(janelaCalculos);
						return;
					}
				}
			}
		};
		new ColunaBotao(tableIndicadoresComResultado, mostrarCalculo, 11, "Mostrar");
	}

	/**
	 * 
	 * @param tableIndicadoresSemResultado tabela dos indicadores sem resultado
	 * @param indicadoresNaoValorados      lista de indicadores sem resultado
	 * @param codigo_municipio             código do município utilizado para
	 *                                     calcular o indicador
	 * @param data                         ano utilizado para calcular o indicador
	 */
	public static void mostrarIndicadoresSemResultado(JTable tableIndicadoresSemResultado,
			ArrayList<IndicadoresBuscados> indicadoresNaoValorados, int codigo_municipio, String data) {
		tableIndicadoresSemResultado.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false, false, false, true, true, false, true };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {

				if (tableIndicadoresSemResultado.getValueAt(rowIndex, 7).equals("true")) {
					return false;
				}

				if (columnIndex == 8) {
					return true;
				}
				if (tableIndicadoresSemResultado.getValueAt(rowIndex, 7).equals("")
						|| tableIndicadoresSemResultado.getValueAt(rowIndex, 7).equals(null)) {
					return false;
				}
				return canEdit[columnIndex];
			}
		});
		Indicador.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados,
				codigo_municipio, data);

		TableColumn tc = tableIndicadoresSemResultado.getColumnModel().getColumn(8);
		tc.setCellEditor(tableIndicadoresSemResultado.getDefaultEditor(Boolean.class));
		tc.setCellRenderer(tableIndicadoresSemResultado.getDefaultRenderer(Boolean.class));

		for (int i = 0; i < tableIndicadoresSemResultado.getRowCount(); i++) {
			tableIndicadoresSemResultado.setValueAt(false, i, 8);
		}
		zebraTabela(tableIndicadoresSemResultado, codigo_municipio);
		tableIndicadoresSemResultado.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * Mostra uma tabela com todas as metas disponíveis no banco de dados
	 * 
	 * @param coluna indica em qual coluna está o campo a ser selecionado
	 * @param table  tabela que será utilizada
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
	}

	/**
	 * Mostra uma tabela com todas as metas disponíveis no banco de dados
	 * 
	 * @param table tabela que será utilizada
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
	}

	/**
	 * Coloca todas as metas selecionadas em uma lista
	 * 
	 * @param table tabela que será utilizada
	 * @return uma lista de metas
	 */
	public static ArrayList<Meta> metasSelecionadas(JTable table) {
		ArrayList<Meta> metas = new ArrayList<Meta>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 3).toString().equals("true")) {
				metas.add(new Meta((String) table.getValueAt(i, 1), (String) table.getValueAt(i, 2),
						Integer.parseInt((String) table.getValueAt(i, 0))));
			}
		}
		return metas;
	}

	/**
	 * Retorna a meta selecionada na tabela
	 * 
	 * @param table tabela que será utilizada
	 * @return meta selecionada
	 */
	public static Meta metaSelecionada(JTable table) {
		Meta meta = new Meta();
		int j = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 3).toString().equals("true")) {
				j++;
				meta = new Meta(Integer.parseInt((String) table.getValueAt(i, 0)), (String) table.getValueAt(i, 1));
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
	 * Mostra uma tabela com todas as ODS disponíveis no banco de dados
	 * 
	 * @param coluna indica em qual coluna está o campo a ser selecionado
	 * @param table  tabela que será utilizada
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
	}

	/**
	 * Mostra uma tabela com todas as ODS disponíveis no banco de dados
	 * 
	 * @param table tabela que será utilizada
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
	}

	/**
	 * Coloca todas as ODS selecionadas em uma lista
	 * 
	 * @param table tabela que será utilizada
	 * @return uma lista de ODS
	 */
	public static ArrayList<ODS> odsSelecionadas(JTable table) {
		ArrayList<ODS> objetivos = new ArrayList<ODS>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 2).toString().equals("true")) {
				objetivos.add(
						new ODS(Integer.parseInt((String) table.getValueAt(i, 0)), (String) table.getValueAt(i, 1)));
			}
		}
		return objetivos;
	}

	/**
	 * Retorna a ODS selecionada na tabela
	 * 
	 * @param table tabela que será utilizada
	 * @return ODS selecionada
	 */
	public static ODS odsSelecionada(JTable table) {
		ODS ods = new ODS();
		int j = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 2).toString().equals("true")) {
				j++;
				ods = new ODS(Integer.parseInt((String) table.getValueAt(i, 0)), (String) table.getValueAt(i, 1));
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
	 * Mostra uma tabela com todas as variáveis disponíveis no banco de dados
	 * 
	 * @param coluna indica em qual coluna está o campo a ser selecionado
	 * @param table  tabela que será utilizada
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
	}

	/**
	 * Mostra uma tabela com todas as variáveis disponíveis no banco de dados
	 * 
	 * @param table tabela que será utilizada
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
	}

	/**
	 * Coloca todas as variáveis selecionadas em uma lista
	 * 
	 * @param table tabela que será utilizada
	 * @return uma lista de variáveis
	 */
	public static ArrayList<Variavel> variaveisSelecionadas(JTable table) {
		ArrayList<Variavel> variaveis = new ArrayList<Variavel>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 6).toString().equals("true")) {
				String codigo_banco = (String) table.getValueAt(i, 3);
				if (codigo_banco == null) {
					variaveis.add(new Variavel(Integer.parseInt((String) table.getValueAt(i, 0)),
							(String) table.getValueAt(i, 1), (String) table.getValueAt(i, 2), null,
							(String) table.getValueAt(i, 4), Boolean.parseBoolean((String) table.getValueAt(i, 5))));
				} else {
					variaveis.add(new Variavel(Integer.parseInt((String) table.getValueAt(i, 0)),
							(String) table.getValueAt(i, 1), (String) table.getValueAt(i, 2), codigo_banco,
							(String) table.getValueAt(i, 4), Boolean.parseBoolean((String) table.getValueAt(i, 5))));
				}
			}
		}
		return variaveis;
	}

	/**
	 * Retorna a variável selecionada na tabela
	 * 
	 * @param table tabela que será utilizada
	 * @return variável selecionada
	 */
	public static Variavel variavelSelecionada(JTable table) {
		Variavel variavel = new Variavel();
		int j = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 6).toString().equals("true")) {
				j++;
				String codigo_banco = (String) table.getValueAt(i, 3);
				if (codigo_banco == null) {
					variavel = new Variavel(Integer.parseInt((String) table.getValueAt(i, 0)), null);
				} else {
					variavel = new Variavel(Integer.parseInt((String) table.getValueAt(i, 0)), codigo_banco);
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
	 * Mostra o resultado das operações que criam no banco de dados
	 * 
	 * @param table tabela que será utilizada
	 * @param resultadoOperacoes lista com os resultados das operações e as operações
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

			@Override
			public Component getTableCellRendererComponent(JTable table2, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				if (table2.getValueAt(row, 1) == null) {
					label.setBackground(new Color(250, 255, 178));
					label.setForeground(Color.BLACK);
					return label;
				}
				if (table2.getValueAt(row, 1).equals("Sucesso")) {
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
	 * @param tableIndicadoresComResultado tabela de indicadores com resultado
	 * @param coluna                       coluna que indica se o indicador é padrão
	 */
	public static void corNaLinha(JTable tableIndicadoresComResultado, int coluna) {
		tableIndicadoresComResultado.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(tableIndicadoresComResultado, value,
						isSelected, hasFocus, row, column);

				Object texto = table.getValueAt(row, coluna);

				if (column == 10) {
					texto = table.getValueAt(row, column);
					if (texto.equals("false")) {
						label.setBackground(new Color(250, 255, 178));
						label.setForeground(Color.BLACK);
						return label;
					} else {
						label.setBackground(new Color(178, 252, 255));
						label.setForeground(Color.BLACK);
						return label;
					}
				}

				if (texto != null && texto.toString().equals("false")) {
					label.setBackground(new Color(255, 173, 173));
					label.setForeground(Color.BLACK);
					return label;
				}

				label.setBackground(new Color(173, 255, 180));
				label.setForeground(Color.BLACK);
				return label;
			}
		});
	}

	/**
	 * Colore a tabela dos indicadores sem resultado, separando os indicadores
	 * (linha na cor amarela) das variáveis (linha na cor branca).
	 * 
	 * @param tabelaIndicadoresSemResultado tabela dos indicadores sem resultado
	 * @param codigo_municipio              código do município
	 */
	public static void zebraTabela(JTable tabelaIndicadoresSemResultado, int codigo_municipio) {
		tabelaIndicadoresSemResultado.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(tabelaIndicadoresSemResultado, value,
						isSelected, hasFocus, row, column);

				if (!(table.getValueAt(row, 0).equals("") || table.getValueAt(row, 0).equals(null))) {
					label.setBackground(new Color(250, 255, 178));
					label.setForeground(new Color(0, 0, 0));
					return label;
				}
				label.setBackground(new Color(255, 255, 255));
				label.setForeground(new Color(0, 0, 0));
				return label;
			}
		});
	}
}
