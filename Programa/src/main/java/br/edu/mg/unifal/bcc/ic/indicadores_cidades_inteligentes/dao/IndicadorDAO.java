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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.IndicadoresBuscados;

/**
 * Classe que faz conexão com a tabela "indicadores".
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class IndicadorDAO {

	private Connection connection;

	/**
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public IndicadorDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Faz uma busca no banco de dados e devolve uma lista de indicadores contendo o código do indicador e o método de cálculo
	 * @return uma lista de indicadores contendo o código do indicador e o método de cálculo
	 */
	public List<Indicador> buscarCodigoEMetodo() {
		List<Indicador> indicadores = new ArrayList<Indicador>();
		try {
			String sql = "SELECT codigo_indicador, metodo_calculo FROM indicador";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						Indicador indicador = new Indicador(rst.getInt(1), rst.getString(2));

						indicadores.add(indicador);
					}
				}
			}
			return indicadores;
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar a lista de codigo e metodos.");
		}
	}

	/**
	 * Faz uma busca no banco de dados e devolve um indicador contendo o código do indicador e o método de cálculo
	 * @param codigo_indicador código do indicador utilizado para devolver o método do cálculo
	 * @return um indicador contendo o código do indicador e o método de cálculo
	 */
	public Indicador buscarCodigoEMetodo(int codigo_indicador) {
		try {
			String sql = "SELECT codigo_indicador, metodo_calculo FROM indicador WHERE codigo_indicador = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, codigo_indicador);

				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (rst.next()) {
						Indicador indicador = new Indicador(rst.getInt(1), rst.getString(2));
						return indicador;
					}
					return null;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar o codigo e metodo.");
		}
	}

	/**
	 * Preenche a tabela com todos os indicadores do banco de dados
	 * @param tableIndicadores tabela a ser preenchida
	 * @param seleciona é necessário um coluna extra para uma caixa de selecionar?
	 */
	public void mostrarIndicadores(JTable tableIndicadores, boolean seleciona) {
		try {
			String sql1 = "SELECT codigo_indicador, nome_indicador, numero_meta, metodo_calculo, padrao FROM indicador ORDER BY 5 DESC, 1 ASC";

			try (PreparedStatement pstm1 = connection.prepareStatement(sql1)) {
				pstm1.execute();

				try (ResultSet rst1 = pstm1.getResultSet()) {
					DefaultTableModel model = (DefaultTableModel) tableIndicadores.getModel();
					ResultSetMetaData rsmd = rst1.getMetaData();
					int coluna;
					if (seleciona) {
						coluna = rsmd.getColumnCount() + 1;
					} else {
						coluna = rsmd.getColumnCount();
					}

					String[] nomeColuna = new String[coluna];

					if (seleciona) {
						coluna--;
					}

					for (int i = 1; i < coluna + 1; i++) {
						if (rsmd.getColumnName(i).equals("codigo_indicador")) {
							nomeColuna[i - 1] = "Código";
						} else if (rsmd.getColumnName(i).equals("nome_indicador")) {
							nomeColuna[i - 1] = "Nome";
						} else if (rsmd.getColumnName(i).equals("numero_meta")) {
							nomeColuna[i - 1] = "Meta";
						} else if (rsmd.getColumnName(i).equals("metodo_calculo")) {
							nomeColuna[i - 1] = "Método de cálculo";
						} else if (rsmd.getColumnName(i).equals("padrao")) {
							nomeColuna[i - 1] = "Indicador padrão";
						} else {
							nomeColuna[i - 1] = rsmd.getColumnName(i);
						}

					}

					if (seleciona) {
						nomeColuna[coluna] = "Seleciona";
					}

					model.setColumnIdentifiers(nomeColuna);
					tableIndicadores.setRowSorter(new TableRowSorter<>(model));

					int codigo_indicador;
					String nome_indicador, numero_meta, metodo_calculo;
					boolean padrao;

					while (rst1.next()) {
						codigo_indicador = rst1.getInt(1);
						nome_indicador = rst1.getString(2);
						numero_meta = rst1.getString(3);
						metodo_calculo = rst1.getString(4);
						padrao = rst1.getBoolean(5);
						String[] linha = { Integer.toString(codigo_indicador), nome_indicador, numero_meta,
								metodo_calculo, Boolean.toString(padrao) };
						model.addRow(linha);
					}

				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Erro ao mostrar os indicadores na tabela.");
		}
	}

	/**
	 * Preenche a tabela com os indicadores que não possuem resultado
	 * @param tableIndicadoresSemResultado tabela a ser preenchida
	 * @param indicadoresNaoValorados lista de indicadores sem resultado
	 * @param codigo_municipio código do município que está sendo analisado
	 * @param data ano que está sendo analisado
	 */
	public void mostrarIndicadoresSemResultado(JTable tableIndicadoresSemResultado,
			ArrayList<IndicadoresBuscados> indicadoresNaoValorados, int codigo_municipio, String data) {
		try {
			String sqlVariaveisIndicador = "SELECT codigo_variavel, nome_variavel FROM possui_variavel NATURAL JOIN variavel WHERE codigo_indicador = ?";
			String sqlVariaveisValor = "SELECT valor, valor_oficial FROM valor_variavel WHERE codigo_variavel = ? AND codigo_municipio = ? AND data = ?";

			DefaultTableModel model = (DefaultTableModel) tableIndicadoresSemResultado.getModel();

			String[] nomeColuna = new String[9];
			nomeColuna[0] = "Código do indicador";
			nomeColuna[1] = "Nome do indicador";
			nomeColuna[2] = "Método de cálculo";
			nomeColuna[3] = "Código da variável";
			nomeColuna[4] = "Nome da variável";
			nomeColuna[5] = "Data";
			nomeColuna[6] = "Valor da variável";
			nomeColuna[7] = "Valor oficial";
			nomeColuna[8] = "Calcular";

			model.setColumnIdentifiers(nomeColuna);

			for (IndicadoresBuscados indNaoValorados : indicadoresNaoValorados) {

				try (PreparedStatement pstmVariaveisIndicador = connection.prepareStatement(sqlVariaveisIndicador)) {
					pstmVariaveisIndicador.setInt(1, indNaoValorados.getCodigo_indicador());
					pstmVariaveisIndicador.execute();

					String[] linha = { Integer.toString(indNaoValorados.getCodigo_indicador()),
							indNaoValorados.getNome_indicador(), indNaoValorados.getMetodo_calculo(), "", "", data, "", "" };
					model.addRow(linha);

					try (ResultSet rstVariaveisIndicador = pstmVariaveisIndicador.getResultSet();) {
						while (rstVariaveisIndicador.next()) {
							try (PreparedStatement pstmVariaveisValor = connection
									.prepareStatement(sqlVariaveisValor)) {
								pstmVariaveisValor.setInt(1, rstVariaveisIndicador.getInt(1));
								pstmVariaveisValor.setInt(2, codigo_municipio);
								pstmVariaveisValor.setString(3, data);
								pstmVariaveisValor.execute();

								try (ResultSet rstVariaveisValor = pstmVariaveisValor.getResultSet();) {
									if (rstVariaveisValor.next()) {
										String[] linha2 = { "", "", "",
												Integer.toString(rstVariaveisIndicador.getInt(1)),
												rstVariaveisIndicador.getString(2), data, rstVariaveisValor.getString(1), Boolean.toString(rstVariaveisValor.getBoolean(2)) };
										model.addRow(linha2);
									} else {
										String[] linha2 = { "", "", "",
												Integer.toString(rstVariaveisIndicador.getInt(1)),
												rstVariaveisIndicador.getString(2), data, "-", "false" };
										model.addRow(linha2);
									}
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("Falha ao mostrar os indicadores sem resultado.");
		}
	}
	
	/**
	 * Busca o código do indicador a partir do método de cálculo
	 * @param metodoCalculo método de cálculo do indicador
	 * @return código do indicador
	 */
	public int buscaCodigo(String metodoCalculo) {
		try {
			String sql = "SELECT codigo_indicador FROM indicador WHERE metodo_calculo = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, metodoCalculo);

				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (rst.next()) {
						return rst.getInt(1);
					}
					throw new RuntimeException();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar o codigo com base no metodo.");
		}
	}

	/**
	 * Exclui da tabela "indicador" um indicador a partir do código e método de cálculo dele
	 * @param indicador indicador que deseja ser excluído do banco de dados
	 */
	public void excluir(Indicador indicador) {
		try {
			String sql = "DELETE FROM indicador WHERE codigo_indicador = ? AND metodo_calculo = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, indicador.getCodigo());
				pstm.setString(2, indicador.getCalculo());

				pstm.execute();

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao excluir o indicador " + indicador.getCodigo());
		}
	}

	/**
	 * Busca todas as informações do indicador a partir do código e do método de cálculo dele
	 * @param indicador indicador contendo o código e o método de cálculo
	 * @return indicador contendo todas as informações
	 */
	public Indicador buscaIndicador(Indicador indicador) {
		try {
			String sql = "SELECT codigo_indicador, nome_indicador, metodo_calculo, nome_eixo, tipo_plano, nome_plano, descricao, informacoes_tecnicas, numero_meta, padrao FROM indicador WHERE codigo_indicador = ? AND metodo_calculo = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, indicador.getCodigo());
				pstm.setString(2, indicador.getCalculo());

				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (rst.next()) {
						return new Indicador(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
								rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8),
								rst.getString(9), rst.getBoolean(10));
					}
					throw new RuntimeException("Falha ao buscar o indicador " + indicador.getCodigo());
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar o indicador " + indicador.getCodigo());
		}
	}

	public void padronizarMetodoCalculo() {
		List<Indicador> listaIndicadores = buscarCodigoEMetodo();
		String sql = "UPDATE indicador SET metodo_calculo = ? WHERE codigo_indicador = ?";
		for (Indicador indicador : listaIndicadores) {
			try (PreparedStatement pstm = connection.prepareStatement(sql)){
				String metodoCalculo = indicador.getCalculo().replace(" ", "");
				metodoCalculo = metodoCalculo.replace("×", "x");
				pstm.setString(1, metodoCalculo);
				pstm.setInt(2, indicador.getCodigo());
				
				pstm.execute();
			} catch (Exception e) {
				
			}
		}
	}

}
