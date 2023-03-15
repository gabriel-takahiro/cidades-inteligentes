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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.IndicadoresBuscados;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.PossuiVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.SequenciaCalculo;

/**
 * Classe que faz conexão com a tabela "possui_variavel".
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class PossuiVariavelDAO {

	private Connection connection;

	/**
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public PossuiVariavelDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Cadastra PossuiVariavel no banco de dados
	 * 
	 * @param pv PossuiVariavel a ser cadastrada, contendo o código da variável e o
	 *           código do indicador
	 */
	public void salvar(PossuiVariavel pv) {
		try {
			String sql = "INSERT INTO possui_variavel VALUES (?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {

				pstm.setInt(1, pv.getVariavel());
				pstm.setInt(2, pv.getCodigo());

				pstm.execute();
			}
		} catch (Exception e) {
			System.out.println("falha ao salvar PossuiVariavel indicador:" + pv.getCodigo() + "		variável: "
					+ pv.getVariavel());
		}
	}

	/**
	 * Preenche a tabela com as variáveis e os valores utilizados no cálculo do
	 * indicador selecionado
	 * 
	 * @param tableIndicadoresSemResultado tabela que será preenchida
	 * @param indicador                    indicador selecionado
	 * @param valorRetroativo              anos a mais utilizados na busca
	 *                                     retroativa das variáveis
	 */
	public void tabelaCalculos(JTable tableIndicadoresSemResultado, IndicadoresBuscados indicador,
			int valorRetroativo) {
		try {
			String sqlVariaveisIndicador = "SELECT codigo_variavel, nome_variavel FROM possui_variavel NATURAL JOIN variavel WHERE codigo_indicador = ?";
			String sqlVariaveisValor = "SELECT valor, valor_oficial, valor_atualizado FROM valor_variavel WHERE codigo_variavel = ? AND codigo_municipio = ? AND data = ?";
			DefaultTableModel model = (DefaultTableModel) tableIndicadoresSemResultado.getModel();
			String[] nomeColuna = new String[6];
			nomeColuna[0] = "Código da variavel";
			nomeColuna[1] = "Nome da variavel";
			nomeColuna[2] = "Valor da variável";
			nomeColuna[3] = "Data";
			nomeColuna[4] = "Valor atualizado";
			nomeColuna[5] = "Valor oficial";
			model.setColumnIdentifiers(nomeColuna);
			try (PreparedStatement pstmVariaveisIndicador = connection.prepareStatement(sqlVariaveisIndicador)) {
				pstmVariaveisIndicador.setInt(1, indicador.getCodigo_indicador());
				pstmVariaveisIndicador.execute();
				try (ResultSet rstVariaveisIndicador = pstmVariaveisIndicador.getResultSet();) {
					while (rstVariaveisIndicador.next()) {
						try (PreparedStatement pstmVariaveisValor = connection.prepareStatement(sqlVariaveisValor)) {
							String data = buscaDataVariavel(indicador.getData_variaveis(),
									rstVariaveisIndicador.getInt(1));
							pstmVariaveisValor.setInt(1, rstVariaveisIndicador.getInt(1));
							pstmVariaveisValor.setInt(2, indicador.getCodigo_municipio());
							pstmVariaveisValor.setString(3, data);
							pstmVariaveisValor.execute();
							try (ResultSet rstVariaveisValor = pstmVariaveisValor.getResultSet();) {
								if (rstVariaveisValor.next()) {
									String[] linha = { Integer.toString(rstVariaveisIndicador.getInt(1)),
											rstVariaveisIndicador.getString(2),
											Integer.toString(rstVariaveisValor.getInt(1)), data,
											Boolean.toString(rstVariaveisValor.getBoolean(3)),
											Boolean.toString(rstVariaveisValor.getBoolean(2)) };
									model.addRow(linha);
								}
							} catch (Exception e) {
								System.out.println("Erro " + e);
							}
						}
					}
				}
			}
			tableIndicadoresSemResultado.setEnabled(false);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar a tabela de calculos.");
		}
	}

	/**
	 * Busca o ano utilizado para buscar a variável
	 * 
	 * @param data_variaveis  uma expressão que contém as variáveis e as datas
	 *                        utilizadas nas variáveis
	 * @param codigo_variavel código da variável que deseja saber o ano
	 * @return ano utilizado para buscar a variável
	 */
	private String buscaDataVariavel(String data_variaveis, int codigo_variavel) {
		String datasVariaveis = data_variaveis.replace(" ", "");
		String valor = "";
		String aux;
		ArrayList<String> seqVariaveis = new ArrayList<String>();
		try {
			for (int i = 0; i < datasVariaveis.length(); i++) {
				aux = datasVariaveis.substring(i, i + 1);
				if (aux.equals("/")) {
					if (!valor.isEmpty()) {
						seqVariaveis.add(valor);
					}
					seqVariaveis.add(aux);
					valor = "";
				} else {
					valor = valor.concat(aux);
				}
			}
			if (!valor.isEmpty()) {
				seqVariaveis.add(valor);
			}

			for (int i = 0; i < seqVariaveis.size(); i += 4) {
				if (Integer.parseInt(seqVariaveis.get(i)) == codigo_variavel) {
					return seqVariaveis.get(i + 2);
				}
			}
			return "";
		} catch (Exception e) {
			System.out.println("Erro buscaDV " + e);
			return "";
		}
	}

	/**
	 * Substitui o código antigo da variável pelo novo para todos os métodos de
	 * cálculo que utilizam essa variável
	 * 
	 * @param codigo_antigo	código antigo da variável
	 * @param codigo_variavel código novo da variável
	 */
	public void atualizarVariaveis(int codigo_antigo, int codigo_variavel) {
		String consulta = "SELECT codigo_indicador, metodo_calculo FROM possui_variavel NATURAL JOIN indicador WHERE codigo_variavel = ?";
		try (PreparedStatement pstm = connection.prepareStatement(consulta)) {

			pstm.setInt(1, codigo_variavel);

			pstm.execute();

			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					List<String> listaMetodoCalculo = SequenciaCalculo.lista((String) rst.getString(2));
					String metodoCalculo = "";
					for (int i = 0; i < listaMetodoCalculo.size(); i++) {
						if (listaMetodoCalculo.get(i).equals("[") || listaMetodoCalculo.get(i).equals("{")) {
							metodoCalculo = metodoCalculo.concat(listaMetodoCalculo.get(i));
							metodoCalculo = metodoCalculo.concat(listaMetodoCalculo.get(i + 1));
							metodoCalculo = metodoCalculo.concat(listaMetodoCalculo.get(i + 2));
							i += 2;
							continue;
						}
						if (listaMetodoCalculo.get(i).equals(Integer.toString(codigo_antigo))) {
							metodoCalculo = metodoCalculo.concat(Integer.toString(codigo_variavel));
						} else {
							metodoCalculo = metodoCalculo.concat(listaMetodoCalculo.get(i));
						}
					}
					Indicador.atualizarMetodoCalculo(rst.getInt(1), metodoCalculo);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}
}
