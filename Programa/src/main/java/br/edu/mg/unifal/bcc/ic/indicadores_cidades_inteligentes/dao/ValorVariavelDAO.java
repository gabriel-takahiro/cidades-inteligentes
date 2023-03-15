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

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.ValorVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.CompostoCalculo;

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

	/**
	 * Busca o valor da variável de maneira retroativa dentro do intervalo atualizado
	 * @param compostoCalculo parâmetros utilizados para calcular o indicador
	 * @param codigo_variavel código da variável
	 * @return valor da variável caso houver, ou nulo caso não houver
	 */
	public String buscaValorVariavel(CompostoCalculo compostoCalculo, int codigo_variavel) {
		String resultado = "";
		try {
			String sql = "SELECT valor, valor_atualizado, valor_oficial FROM valor_variavel "
					+ "WHERE codigo_variavel = ? AND codigo_municipio = ? AND data = ?";

			Variavel variavel = new Variavel();
			for (Variavel v : compostoCalculo.getVariaveis()) {
				if (v.getCodigo_variavel() == codigo_variavel) {
					variavel = v;
					break;
				}
			}

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {

				pstm.setInt(1, codigo_variavel);
				pstm.setInt(2, compostoCalculo.getCodigo_municipio());
				pstm.setString(3, compostoCalculo.getData());
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (compostoCalculo.isRecalcular()) {
						if (rst.next()) { // Encontrou valor no BD
							if (rst.getBoolean(2)) {// Valor está atualizado?
								if (variavel.getTipoBanco().equals("Sidra")) {
									resultado = variavel.calcularResultado(compostoCalculo.getData(),
											compostoCalculo.getCodigo_municipio());
									if (!(resultado.equals(""))) {
										updateValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
												compostoCalculo.getData(), rst.getString(1), resultado, true, true);
										compostoCalculo.setDataVariaveis(codigo_variavel, compostoCalculo.getData());
										return resultado;
									}
									if (variavel.getAtualizacao().equals("Decenal")) {
										int dataVarredura = Integer.parseInt(compostoCalculo.getData());
										for (int i = 0; i < 10 + compostoCalculo.getValorRetroativo(); i++) {// Busca nos
																												// últimos
																												// 10 anos
											dataVarredura--;
											ValorVariavel resultadoBuscaValor = buscaValor(variavel, codigo_variavel,
													compostoCalculo.getCodigo_municipio(), Integer.toString(dataVarredura),
													compostoCalculo.getDataDAO(), compostoCalculo.isRecalcular());
											if (!(resultadoBuscaValor.getValor().equals(""))) {
												compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
												updateValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
														compostoCalculo.getData(), rst.getString(1), resultadoBuscaValor.getValor(), false,
														resultadoBuscaValor.isValor_oficial());
												if(!resultadoBuscaValor.isValor_oficial()) {
													compostoCalculo.setValor_oficial(false);
												}
												compostoCalculo.setDataVariaveis(codigo_variavel,
														Integer.toString(dataVarredura));
												return resultadoBuscaValor.getValor();
											}
										}
										return resultado;
									}
									int dataVarredura = Integer.parseInt(compostoCalculo.getData());
									for (int i = 0; i < 1 + compostoCalculo.getValorRetroativo(); i++) {// Busca no último
																										// ano
										dataVarredura--;
										ValorVariavel resultadoBuscaValor = buscaValor(variavel, codigo_variavel,
												compostoCalculo.getCodigo_municipio(), Integer.toString(dataVarredura),
												compostoCalculo.getDataDAO(), compostoCalculo.isRecalcular());
										if (!(resultadoBuscaValor.getValor().equals(""))) {
											compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
											updateValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
													compostoCalculo.getData(), rst.getString(1), resultadoBuscaValor.getValor(), false,
													resultadoBuscaValor.isValor_oficial());
											if(!resultadoBuscaValor.isValor_oficial()) {
												compostoCalculo.setValor_oficial(false);
											}
											compostoCalculo.setDataVariaveis(codigo_variavel,
													Integer.toString(dataVarredura));
											return resultadoBuscaValor.getValor();
										}
									}
									return resultado;
								}
								if(!rst.getBoolean(3)) {
									compostoCalculo.setValor_oficial(false);
								}
								compostoCalculo.setDataVariaveis(codigo_variavel, compostoCalculo.getData());
								return rst.getString(1);
							}
							if (variavel.getTipoBanco().equals("Sidra")) {
								resultado = variavel.calcularResultado(compostoCalculo.getData(),
										compostoCalculo.getCodigo_municipio());
								if (!(resultado.equals(""))) {
									updateValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
											compostoCalculo.getData(), rst.getString(1), resultado, true, true);
									compostoCalculo.setDataVariaveis(codigo_variavel, compostoCalculo.getData());
									return resultado;
								}
								if (variavel.getAtualizacao().equals("Decenal")) {
									int dataVarredura = Integer.parseInt(compostoCalculo.getData());
									for (int i = 0; i < 10 + compostoCalculo.getValorRetroativo(); i++) {// Busca nos
																											// últimos
																											// 10 anos
										dataVarredura--;
										ValorVariavel resultadoBuscaValor = buscaValor(variavel, codigo_variavel,
												compostoCalculo.getCodigo_municipio(), Integer.toString(dataVarredura),
												compostoCalculo.getDataDAO(), compostoCalculo.isRecalcular());
										if (!(resultadoBuscaValor.getValor().equals(""))) {
											compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
											updateValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
													compostoCalculo.getData(), rst.getString(1), resultadoBuscaValor.getValor(), false,
													resultadoBuscaValor.isValor_oficial());
											if(!resultadoBuscaValor.isValor_oficial()) {
												compostoCalculo.setValor_oficial(false);
											}
											compostoCalculo.setDataVariaveis(codigo_variavel,
													Integer.toString(dataVarredura));
											return resultadoBuscaValor.getValor();
										}
									}
									return resultado;
								}
								int dataVarredura = Integer.parseInt(compostoCalculo.getData());
								for (int i = 0; i < 1 + compostoCalculo.getValorRetroativo(); i++) {// Busca no último
																									// ano
									dataVarredura--;
									ValorVariavel resultadoBuscaValor = buscaValor(variavel, codigo_variavel,
											compostoCalculo.getCodigo_municipio(), Integer.toString(dataVarredura),
											compostoCalculo.getDataDAO(), compostoCalculo.isRecalcular());
									if (!(resultadoBuscaValor.getValor().equals(""))) {
										compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
										updateValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
												compostoCalculo.getData(), rst.getString(1), resultadoBuscaValor.getValor(), false,
												resultadoBuscaValor.isValor_oficial());
										if(!resultadoBuscaValor.isValor_oficial()) {
											compostoCalculo.setValor_oficial(false);
										}
										compostoCalculo.setDataVariaveis(codigo_variavel,
												Integer.toString(dataVarredura));
										return resultadoBuscaValor.getValor();
									}
								}
								return resultado;
							}
							if (variavel.getAtualizacao().equals("Decenal")) {
								int dataVarredura = Integer.parseInt(compostoCalculo.getData());
								for (int i = 0; i < 10 + compostoCalculo.getValorRetroativo(); i++) {// Busca nos
																										// últimos 10
																										// anos
									dataVarredura--;
									ValorVariavel resultadoBuscaValor = buscaValor(variavel, codigo_variavel,
											compostoCalculo.getCodigo_municipio(), Integer.toString(dataVarredura),
											compostoCalculo.getDataDAO(), compostoCalculo.isRecalcular());
									if (!(resultadoBuscaValor.getValor().equals(""))) {
										compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
										updateValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
												compostoCalculo.getData(), rst.getString(1), resultadoBuscaValor.getValor(), false,
												resultadoBuscaValor.isValor_oficial());
										if(!resultadoBuscaValor.isValor_oficial()) {
											compostoCalculo.setValor_oficial(false);
										}
										compostoCalculo.setDataVariaveis(codigo_variavel,
												Integer.toString(dataVarredura));
										return resultadoBuscaValor.getValor();
									}
								}
								return resultado;
							}
							int dataVarredura = Integer.parseInt(compostoCalculo.getData());
							for (int i = 0; i < 1 + compostoCalculo.getValorRetroativo(); i++) {// Busca no último ano
								dataVarredura--;
								ValorVariavel resultadoBuscaValor = buscaValor(variavel, codigo_variavel,
										compostoCalculo.getCodigo_municipio(), Integer.toString(dataVarredura),
										compostoCalculo.getDataDAO(), compostoCalculo.isRecalcular());
								if (!(resultadoBuscaValor.getValor().equals(""))) {
									compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
									updateValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
											compostoCalculo.getData(), rst.getString(1), resultadoBuscaValor.getValor(), false,
											resultadoBuscaValor.isValor_oficial());
									if(!resultadoBuscaValor.isValor_oficial()) {
										compostoCalculo.setValor_oficial(false);
									}
									compostoCalculo.setDataVariaveis(codigo_variavel, Integer.toString(dataVarredura));
									return resultadoBuscaValor.getValor();
								}
							}
							return resultado;
						} // Não encontrou valor no BD
						if (variavel.getAtualizacao().equals("Decenal")) {
							if (variavel.getTipoBanco().equals("Sidra")) {
								resultado = variavel.calcularResultado(compostoCalculo.getData(),
										compostoCalculo.getCodigo_municipio());
								if (!(resultado.equals(""))) {
									compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
									inserirValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
											compostoCalculo.getData(), resultado, true, true);
									compostoCalculo.setDataVariaveis(codigo_variavel, compostoCalculo.getData());
									return resultado;
								}
							}
							int dataVarredura = Integer.parseInt(compostoCalculo.getData());
							for (int i = 0; i < 10 + compostoCalculo.getValorRetroativo(); i++) {// Busca nos últimos 10
																									// anos
								dataVarredura--;
								ValorVariavel resultadoBuscaValor = buscaValor(variavel, codigo_variavel,
										compostoCalculo.getCodigo_municipio(), Integer.toString(dataVarredura),
										compostoCalculo.getDataDAO(), compostoCalculo.isRecalcular());
								if (!(resultadoBuscaValor.getValor().equals(""))) {
									compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
									inserirValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
											compostoCalculo.getData(), resultadoBuscaValor.getValor(), false,
											resultadoBuscaValor.isValor_oficial());
									if(!resultadoBuscaValor.isValor_oficial()) {
										compostoCalculo.setValor_oficial(false);
									}
									compostoCalculo.setDataVariaveis(codigo_variavel, Integer.toString(dataVarredura));
									return resultadoBuscaValor.getValor();
								}
							}
							return resultado;
						}
						if (variavel.getTipoBanco().equals("Sidra")) {
							resultado = variavel.calcularResultado(compostoCalculo.getData(),
									compostoCalculo.getCodigo_municipio());
							if (!(resultado.equals(""))) {
								compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
								inserirValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
										compostoCalculo.getData(), resultado, true, true);
								compostoCalculo.setDataVariaveis(codigo_variavel, compostoCalculo.getData());
								return resultado;
							}

						}
						int dataVarredura = Integer.parseInt(compostoCalculo.getData());
						for (int i = 0; i < 1 + compostoCalculo.getValorRetroativo(); i++) {// Busca no último ano
							dataVarredura--;
							ValorVariavel resultadoBuscaValor = buscaValor(variavel, codigo_variavel, compostoCalculo.getCodigo_municipio(),
									Integer.toString(dataVarredura), compostoCalculo.getDataDAO(),
									compostoCalculo.isRecalcular());
							if (!(resultadoBuscaValor.getValor().equals(""))) {
								compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
								inserirValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
										compostoCalculo.getData(), resultadoBuscaValor.getValor(), false, resultadoBuscaValor.isValor_oficial());
								if(!resultadoBuscaValor.isValor_oficial()) {
									compostoCalculo.setValor_oficial(false);
								}
								compostoCalculo.setDataVariaveis(codigo_variavel, Integer.toString(dataVarredura));
								return resultadoBuscaValor.getValor();
							}
						}
						return resultado;
					} // sem recalcular
					if (rst.next()) { // Encontrou valor no BD
						if (rst.getBoolean(2)) {// Valor está atualizado?
							compostoCalculo.setDataVariaveis(codigo_variavel, compostoCalculo.getData());
							return rst.getString(1);
						}
						if (variavel.getTipoBanco().equals("Sidra")) {
							resultado = variavel.calcularResultado(compostoCalculo.getData(),
									compostoCalculo.getCodigo_municipio());
							if (!(resultado.equals(""))) {
								updateValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
										compostoCalculo.getData(), rst.getString(1), resultado, true, true);
								compostoCalculo.setDataVariaveis(codigo_variavel, compostoCalculo.getData());
								return resultado;
							}
						}
						colocarDataVariavel(codigo_variavel, compostoCalculo);
						return rst.getString(1);
					} // Não encontrou valor no BD
					if (variavel.getAtualizacao().equals("Decenal")) {
						if (variavel.getTipoBanco().equals("Sidra")) {
							resultado = variavel.calcularResultado(compostoCalculo.getData(),
									compostoCalculo.getCodigo_municipio());
							if (!(resultado.equals(""))) {
								compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
								inserirValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
										compostoCalculo.getData(), resultado, true, true);
								compostoCalculo.setDataVariaveis(codigo_variavel, compostoCalculo.getData());
								return resultado;
							}
						}

						int dataVarredura = Integer.parseInt(compostoCalculo.getData());
						for (int i = 0; i < 10 + compostoCalculo.getValorRetroativo(); i++) {// Busca nos últimos 10
																								// anos
							dataVarredura--;
							ValorVariavel resultadoBuscaValor = buscaValor(variavel, codigo_variavel, compostoCalculo.getCodigo_municipio(),
									Integer.toString(dataVarredura), compostoCalculo.getDataDAO(),
									compostoCalculo.isRecalcular());
							if (!(resultadoBuscaValor.getValor().equals(""))) {
								compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
								inserirValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
										compostoCalculo.getData(), resultadoBuscaValor.getValor(), false, resultadoBuscaValor.isValor_oficial());
								if(!resultadoBuscaValor.isValor_oficial()) {
									compostoCalculo.setValor_oficial(false);
								}
								compostoCalculo.setDataVariaveis(codigo_variavel, Integer.toString(dataVarredura));
								return resultadoBuscaValor.getValor();
							}
						}
						return resultado;
					}

					if (variavel.getTipoBanco().equals("Sidra")) {
						resultado = variavel.calcularResultado(compostoCalculo.getData(),
								compostoCalculo.getCodigo_municipio());
						if (!(resultado.equals(""))) {
							compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
							inserirValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
									compostoCalculo.getData(), resultado, true, true);
							compostoCalculo.setDataVariaveis(codigo_variavel, compostoCalculo.getData());
							return resultado;
						}

					}

					int dataVarredura = Integer.parseInt(compostoCalculo.getData());
					for (int i = 0; i < 1 + compostoCalculo.getValorRetroativo(); i++) {// Busca no último ano
						dataVarredura--;
						ValorVariavel resultadoBuscaValor = buscaValor(variavel, codigo_variavel, compostoCalculo.getCodigo_municipio(),
								Integer.toString(dataVarredura), compostoCalculo.getDataDAO(),
								compostoCalculo.isRecalcular());
						if (!(resultadoBuscaValor.getValor().equals(""))) {
							compostoCalculo.getDataDAO().atualizaData(compostoCalculo.getData());
							inserirValorVariavel(codigo_variavel, compostoCalculo.getCodigo_municipio(),
									compostoCalculo.getData(), resultadoBuscaValor.getValor(), false, resultadoBuscaValor.isValor_oficial());
							if(!resultadoBuscaValor.isValor_oficial()) {
								compostoCalculo.setValor_oficial(false);
							}
							compostoCalculo.setDataVariaveis(codigo_variavel, Integer.toString(dataVarredura));
							return resultadoBuscaValor.getValor();
						}
					}
				}
			}
			return resultado;
		} catch (

		Exception e) {
			throw new RuntimeException("Falha ao buscar o valor da variavel." + codigo_variavel + ", "
					+ compostoCalculo.getCodigo_municipio() + ", " + compostoCalculo.getData());
		}
	}

	/**
	 * Salva no CompostoCalculo a variável e o ano que está sendo utilizado a variável no campo data_variaveis
	 * @param codigo_variavel código da variável 
	 * @param compostoCalculo Composto cálculo
	 */
	private void colocarDataVariavel(int codigo_variavel, CompostoCalculo compostoCalculo) {
		try {
			String sqlBusca = "SELECT data, valor_atualizado FROM valor_variavel WHERE codigo_variavel = ? AND codigo_municipio = ? ORDER BY 1 DESC";
			
			try (PreparedStatement pstm = connection.prepareStatement(sqlBusca)) {
				pstm.setInt(1, codigo_variavel);
				pstm.setInt(2, compostoCalculo.getCodigo_municipio());
				pstm.execute();
				
				try (ResultSet rst = pstm.getResultSet()) {
					while(rst.next()) {
						if(rst.getBoolean(2)) {
							compostoCalculo.setDataVariaveis(codigo_variavel, rst.getString(1));
							return;
						}
					}
				}
			}
			
		}catch (Exception e) {
			System.out.println("Erro ao buscar a data certa para colocar na data_variavel");
		}
	}

	/**
	 * Atualiza o valor da variável no banco de dados
	 * @param codigo_variavel código da variável
	 * @param codigo_municipio código do município
	 * @param data ano
	 * @param resultadoAntigo resultado anterior
	 * @param resultadoNovo resultado novo
	 * @param valor_atualizado indica se o valor está atualizado
	 * @param valor_oficial indica se o valor é oficial
	 */
	private void updateValorVariavel(int codigo_variavel, int codigo_municipio, String data, String resultadoAntigo,
			String resultadoNovo, boolean valor_atualizado, boolean valor_oficial) {

		try {
			String sql = "UPDATE valor_variavel SET valor = ?, valor_atualizado = ?, valor_oficial = ? WHERE codigo_variavel = ? AND"
					+ " codigo_municipio = ? AND data = ? AND valor = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, resultadoNovo);
				pstm.setBoolean(2, valor_atualizado);
				pstm.setBoolean(3, valor_oficial);
				pstm.setInt(4, codigo_variavel);
				pstm.setInt(5, codigo_municipio);
				pstm.setString(6, data);
				pstm.setString(7, resultadoAntigo);
				pstm.execute();
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao atualizar o valor da variavel.");
		}
	}

	/**
	 * Insere no banco de dados o valor da variável
	 * @param codigo_variavel código da variável
	 * @param codigo_municipio código do município
	 * @param data ano
	 * @param resultado resultado da variável
	 * @param atualizado indica se o valor está atualizado
	 * @param valor_oficial indica se o valor é oficial
	 */
	private void inserirValorVariavel(int codigo_variavel, int codigo_municipio, String data, String resultado,
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
			throw new RuntimeException("Falha ao inserir valor variavel.");
		}

	}

	/**
	 * Busca o valor da variável no banco de dados ou na api sidra
	 * @param variavel variável que está sendo buscada
	 * @param codigo_variavel código da variável
	 * @param codigo_municipio código do município
	 * @param data ano
	 * @param dataDAO dataDAO
	 * @param recalcular é necessário buscar o valor novamente na api sidra?
	 * @return ValorVariavel contendo as informações do valor da variável
	 */
	private ValorVariavel buscaValor(Variavel variavel, int codigo_variavel, int codigo_municipio, String data,
			DataDAO dataDAO, boolean recalcular) {
		try {
			String sql = "SELECT valor, valor_atualizado, valor_oficial FROM valor_variavel "
					+ "WHERE codigo_variavel = ? AND codigo_municipio = ? AND data = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, codigo_variavel);
				pstm.setInt(2, codigo_municipio);
				pstm.setString(3, data);
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (rst.next()) {// Encontrou valor no BD
						if (rst.getBoolean(2)) {
							if (recalcular) {
								if (variavel.getTipoBanco().equals("Sidra")) {
									String resultado = variavel.calcularResultado(data, codigo_municipio);
									if (!(resultado.equals(""))) {
										updateValorVariavel(codigo_variavel, codigo_municipio, data, rst.getString(1),
												resultado, true, true);
										return new ValorVariavel(resultado, true);
									}
									excluirValorVariavel(codigo_variavel, codigo_municipio, data);
									return new ValorVariavel("", false);
								}
								return new ValorVariavel(rst.getString(1), false);
							}
							return new ValorVariavel(rst.getString(1), rst.getBoolean(3));
						}
						if (variavel.getTipoBanco().equals("Sidra")) {
							String resultado = variavel.calcularResultado(data, codigo_municipio);
							if (!(resultado.equals(""))) {
								updateValorVariavel(codigo_variavel, codigo_municipio, data, rst.getString(1),
										resultado, true, true);
								return new ValorVariavel(resultado, true);
							}
						}
						return new ValorVariavel("", false);
						// Não encontrou valor no BD
					}
					if (variavel.getTipoBanco().equals("Sidra")) {
						String resultado = variavel.calcularResultado(data, codigo_municipio);
						if (!(resultado.equals(""))) {
							dataDAO.atualizaData(data);
							inserirValorVariavel(codigo_variavel, codigo_municipio, data, resultado, true, true);
							return new ValorVariavel(resultado, true);
						}
					}
				}
				return new ValorVariavel("", false);
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"Falha ao buscar o valor variavel: " + codigo_variavel + ", " + codigo_municipio + ", " + data);
		}
	}

	/**
	 * Exclui da tabela "valor_variavel" um valor da variável a partir do código da variável, o código do município e o ano
	 * @param codigo_variavel código da variável
	 * @param codigo_municipio código do município
	 * @param data ano
	 */
	private void excluirValorVariavel(int codigo_variavel, int codigo_municipio, String data) {
		try {
			String sqlExcluir = "DELETE FROM valor_variavel WHERE codigo_variavel = ? AND codigo_municipio = ? AND data = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sqlExcluir)) {
				pstm.setInt(1, codigo_variavel);
				pstm.setInt(2, codigo_municipio);
				pstm.setString(3, data);
				pstm.execute();
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar excluir valor_variavel." + e.toString());
		}
	}

	/**
	 * Insere o valor da variável, caso o valor da variável ja esteja cadastrada no banco de dados, o valor é atualizado
	 * @param codigo_variavel código da variável
	 * @param codigo_municipio código do município
	 * @param data ano
	 * @param valor valor da variável
	 */
	public void tentarInserirValorVariavel(int codigo_variavel, int codigo_municipio, String data, float valor) {
		try {
			String sqlInserir = "SELECT * FROM valor_variavel WHERE codigo_variavel = ? AND codigo_municipio = ? AND data = ?";
			String sqlUpdate = "UPDATE valor_variavel SET valor = ?, valor_atualizado = ?, valor_oficial = ? WHERE codigo_variavel = ? AND codigo_municipio = ? AND data = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sqlInserir)) {
				pstm.setInt(1, codigo_variavel);
				pstm.setInt(2, codigo_municipio);
				pstm.setString(3, data);
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (!(rst.next())) {
						inserirValorVariavel(codigo_variavel, codigo_municipio, data, Float.toString(valor), true,
								false);
						return;
					}
					try (PreparedStatement pstm2 = connection.prepareStatement(sqlUpdate)) {
						String valorAtualizado = Float.toString(valor);
						pstm2.setString(1, valorAtualizado);
						pstm2.setBoolean(2, true);
						pstm2.setBoolean(3, false);
						pstm2.setInt(4, codigo_variavel);
						pstm2.setInt(5, codigo_municipio);
						pstm2.setString(6, data);
						pstm2.execute();
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar inserir valor variavel." + e.toString());
		}
	}
}
