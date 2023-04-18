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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.ValorVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.ParametrosBuscaValorVariavel;

/**
 * Classe que faz conexão com a tabela "valor_variavel".
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class ValorVariavelDAO {

	private Connection connection;

	/**
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public ValorVariavelDAO(Connection connection) {
		this.connection = connection;
	}

	public ValorVariavel buscaValorVariavel(int codigo_variavel, int codigoMunicipio, int ano, int minimo) {
		try {
			String sql = "SELECT valor, data, valor_atualizado, valor_oficial FROM valor_variavel WHERE codigo_variavel = ? AND codigo_municipio = ? ORDER BY 2 DESC";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, codigo_variavel);
				pstm.setInt(2, codigoMunicipio);
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						if (rst.getBoolean(3) && ano >= Integer.parseInt(rst.getString(2))
								&& Integer.parseInt(rst.getString(2)) >= minimo) {
							return new ValorVariavel(rst.getString(1), rst.getBoolean(4), rst.getString(2));
						}
					}
					throw new RuntimeException("Não encontrou valores");
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar valor.");
		}
	}

	/**
	 * Insere no banco de dados o valor da variável
	 * 
	 * @param codigo_variavel  código da variável
	 * @param codigo_municipio código do município
	 * @param data             ano
	 * @param resultado        resultado da variável
	 * @param atualizado       indica se o valor está atualizado
	 * @param valor_oficial    indica se o valor é oficial
	 */
	public void inserirValorVariavel(int codigo_variavel, int codigo_municipio, String data, String resultado,
			boolean atualizado, boolean valor_oficial) {

		try {
			String sql = "INSERT INTO valor_variavel (codigo_variavel, codigo_municipio, data, valor, "
					+ "valor_atualizado, valor_oficial) VALUES (?, ?, ?, ?, ?, ?)";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, codigo_variavel);
				pstm.setInt(2, codigo_municipio);
				pstm.setString(3, data);
				pstm.setString(4, resultado);
				pstm.setBoolean(5, atualizado);
				pstm.setBoolean(6, valor_oficial);
				pstm.execute();
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha na inserção do valor da variável " + codigo_variavel);
		}

	}

	public void inserirUpdateValorVariavel(int codigo_variavel, int codigo_municipio, String data, String resultado,
			boolean atualizado, boolean valor_oficial) {
		try {
			inserirValorVariavel(codigo_variavel, codigo_municipio, data, resultado, atualizado, valor_oficial);
		} catch (Exception e) {
			try {
				String sql = "UPDATE valor_variavel SET valor = ?, valor_atualizado = ?, valor_oficial = ? WHERE codigo_variavel = ? AND codigo_municipio = ? AND data = ?";
				try (PreparedStatement pstm = connection.prepareStatement(sql)) {
					pstm.setString(1, resultado);
					pstm.setBoolean(2, atualizado);
					pstm.setBoolean(3, valor_oficial);
					pstm.setInt(4, codigo_variavel);
					pstm.setInt(5, codigo_municipio);
					pstm.setString(6, data);
					pstm.execute();
				}
			} catch (Exception e2) {
				System.out.println("Falha no update do valor da variável " + codigo_variavel);
			}
		}
	}

	public List<Variavel> buscaVariaveisNaoValoradas(List<Variavel> listaVariaveisRelacionadas,
			ParametrosBuscaValorVariavel parametros) {
		try {
			String sql = "SELECT valor, valor_oficial, data FROM valor_variavel WHERE codigo_variavel = ? AND codigo_municipio = ? ORDER BY 3 DESC";

			List<Variavel> listaVariaveisNaoValoradas = new ArrayList<>();

			for (Variavel variavel : listaVariaveisRelacionadas) {
				try (PreparedStatement pstm = connection.prepareStatement(sql)) {
					pstm.setInt(1, variavel.getCodigo_variavel());
					pstm.setInt(2, parametros.getCodigoMunicipio());
					pstm.execute();
					try (ResultSet rst = pstm.getResultSet()) {
						int ano = Integer.parseInt(parametros.getData());
						int anosRetroativos = parametros.getAnosRetroativos();
						int anosAtualizacao = variavel.getAtualizacao().equals("Decenal") ? 10 : 1;
						boolean adicionaLista = true;
			            while (rst.next()) {
			                int anoBusca = Integer.parseInt(rst.getString(3));
			                if ((anoBusca <= ano) && (anoBusca >= (ano - anosAtualizacao - anosRetroativos))) {
			                    adicionaLista = false;
			                    break;
			                }
			            }
			            if (adicionaLista) {
			                listaVariaveisNaoValoradas.add(variavel);
			            }
					}
				}
			}
			return listaVariaveisNaoValoradas;
		} catch (Exception e) {
			System.out.println("erro na buscaVariaveisNaoValoradas" + e);
			throw new RuntimeException();
		}
	}

	public List<Variavel> buscaVariaveisNaoValoradas(List<Variavel> listaVariaveisRelacionadas, String data,
			int valorRetroativo) {
		try {
			String sql = "SELECT COUNT (*) FROM valor_variavel WHERE codigo_variavel = ? AND data = ?";
			int numeroMunicipios = 5570;
			double porcentagemAceitavel = 0.75;
			int valorAceitavel = (int) Math.round(numeroMunicipios * porcentagemAceitavel);
			List<Variavel> listaVariaveisNaoValoradas = new ArrayList<>();

			for (Variavel variavel : listaVariaveisRelacionadas) {
				try (PreparedStatement pstm = connection.prepareStatement(sql)) {
					pstm.setInt(1, variavel.getCodigo_variavel());
					pstm.setString(2, data);
					pstm.execute();
					try (ResultSet rst = pstm.getResultSet()) {
						if (rst.next()) {
							if (rst.getLong(1) < valorAceitavel) {
								listaVariaveisNaoValoradas.add(variavel);
							}
						}
					}
				}
			}
			return listaVariaveisNaoValoradas;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}
