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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.calculo.ProgressoCalculo;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.CalculaIndicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.ParametrosBuscaValorVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.CalculoIndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.IndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.MunicipioDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.PossuiVariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.ValorVariavelDAO;

/**
 * Classe que representa a tabela calculo_indicador do banco de dados.
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class CalculoIndicador {

	private int codigo_indicador;
	private int codigo_municipio;
	private String data;
	private String resultado;
	private String data_variaveis;
	private boolean valor_oficial;

	public CalculoIndicador() {
	}

	/**
	 * 
	 * @param resultado      resultado do indicador
	 * @param data_variaveis ano das variáveis utilizadas
	 * @param valor_oficial  true caso o valor seja oficial
	 */
	public CalculoIndicador(String resultado, String data_variaveis, boolean valor_oficial) {
		this.resultado = resultado;
		this.data_variaveis = data_variaveis;
		this.valor_oficial = valor_oficial;
	}

	/**
	 * 
	 * @param codigo_indicador código do indicador
	 * @param codigo_municipio código do município
	 * @param data             ano
	 */
	public CalculoIndicador(int codigo_indicador, int codigo_municipio, String data) {
		this.codigo_indicador = codigo_indicador;
		this.codigo_municipio = codigo_municipio;
		this.data = data;
	}

	/**
	 * 
	 * @param codigo_indicador código do indicador
	 * @param codigo_municipio código do município
	 * @param data             ano
	 * @param resultado        resultado do indicador
	 */
	public CalculoIndicador(int codigo_indicador, int codigo_municipio, String data, String resultado) {
		this.codigo_indicador = codigo_indicador;
		this.codigo_municipio = codigo_municipio;
		this.data = data;
		this.resultado = resultado;
	}

	/**
	 * Verifica se dentro do conjunto de resultados possui um resultado para um
	 * indicador específico
	 * 
	 * @param resultados conjunto de resultados
	 * @param codigo     código do indicador
	 * @return true caso encontre um resultado vinculado com o código do indicador
	 */
	public static boolean possuiResultado(Set<CalculoIndicador> resultados, int codigo) {
		for (CalculoIndicador resultado : resultados) {
			if (resultado.codigo_indicador == codigo) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica se dentro do conjunto de resultados possui um resultado para um
	 * indicador e um município específicos
	 * 
	 * @param resultados       conjunto de resultados
	 * @param codigo_indicador código do indicador
	 * @param codigo_municipio código do município
	 * @return true caso encontre um resultado vinculado com o código do indicador e
	 *         o código do município
	 */
	public static boolean possuiResultado(Set<CalculoIndicador> resultados, int codigo_indicador,
			int codigo_municipio) {
		for (CalculoIndicador resultado : resultados) {
			if (resultado.codigo_indicador == codigo_indicador && resultado.codigo_municipio == codigo_municipio) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Busca uma lista de indicadores no banco de dados a partir dos indicadores
	 * selecionados, o código do município e o ano
	 * 
	 * @param listaIndicadoresSelecionados lista dos indicadores a serem buscados
	 * @param codigoMunicipio              código do município
	 * @param data                         ano
	 * @return lista com os indicadores encontrados
	 */
	public static List<IndicadoresBuscados> buscarIndicadoresList(ArrayList<Integer> listaIndicadoresSelecionados,
			int codigoMunicipio, String data) {
		List<IndicadoresBuscados> listaIndicadoresCalculados = new ArrayList<IndicadoresBuscados>();

		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(connection);

			listaIndicadoresCalculados = calculoIndicadorDAO.buscarResultadosList(codigoMunicipio, data,
					listaIndicadoresSelecionados);

			return listaIndicadoresCalculados;

		} catch (Exception e) {
			throw new RuntimeException(
					"Falha ao tentar buscar a lista dos indicadores para: " + codigoMunicipio + ",  " + data);
		}
	}

	/**
	 * Busca uma lista com os indicadores calculados a partir da lista dos
	 * indicadores selecionados, o código do município e o ano. Caso o resultado do
	 * indicador não seja encontrado, será feito o cálculo dele a partir das
	 * informações de entrada.
	 * 
	 * @param listaIndicadoresSelecionados lista de indicadores selecionados para a
	 *                                     busca
	 * @param codigoMunicipio              código do município
	 * @param data                         ano
	 * @param recalcular                   true caso seja necessário recalcular o
	 *                                     indicador
	 * @param valorRetroativo              anos a mais utilizados para a busca
	 *                                     retroativa das variáveis
	 * @return uma lista com os indicadores calculados
	 */
	public static List<IndicadoresBuscados> calculaIndicadores(List<Integer> listaIndicadoresSelecionados,
			int codigoMunicipio, String data, boolean recalcular, int valorRetroativo) {
		List<IndicadoresBuscados> listaIndicadoresCalculados = new ArrayList<IndicadoresBuscados>();

		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			// Calcula primeiro os indicadores que são utilizados como variáveis no método
			// de cálculo
			List<Integer> listaIndicadoresVariaveis = new IndicadorDAO(connection)
					.buscaIndicadoresComoVariaveis(listaIndicadoresSelecionados);
			if (!listaIndicadoresVariaveis.isEmpty()) {
				calculaIndicadores(listaIndicadoresVariaveis, codigoMunicipio, data, recalcular, valorRetroativo);
			}

			ExecutorService executor = Executors.newFixedThreadPool(10);
			CalculaIndicador calculaIndicador = new CalculaIndicador();

			ParametrosBuscaValorVariavel parametros = new ParametrosBuscaValorVariavel(data, codigoMunicipio,
					valorRetroativo, recalcular);

			if (recalcular) {
				long start = System.currentTimeMillis();
				List<Variavel> listaVariaveisRelacionadas = new PossuiVariavelDAO(connection)
						.buscaVariaveisRelacionadas(listaIndicadoresSelecionados);

				listaVariaveisRelacionadas = variaveisNaoBD(listaVariaveisRelacionadas);
				
				ProgressoCalculo.setValorTotal(listaVariaveisRelacionadas);

				calculaIndicador.buscarVariaveisUmMunicipio(executor, listaVariaveisRelacionadas, parametros);

				ExecutorService executor2 = Executors.newFixedThreadPool(10);

				IndicadorDAO indicadorDAO = new IndicadorDAO(connection);
				List<Indicador> listaIndicadoresMetodoCalculo = indicadorDAO
						.buscarCodigoEMetodo(listaIndicadoresSelecionados);

				calculaIndicador.calcularIndicador(executor2, listaIndicadoresMetodoCalculo, parametros);

				CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(connection);

				listaIndicadoresCalculados = calculoIndicadorDAO.buscarResultadosList(codigoMunicipio, data,
						listaIndicadoresSelecionados);

				long end = System.currentTimeMillis();
				System.out.println((end - start) + " ms");

			} else {
				long start = System.currentTimeMillis();

				List<Integer> listaIndicadoresNaoCalculados = new CalculoIndicadorDAO(connection)
						.buscaIndicadoresNaoCalculados(listaIndicadoresSelecionados, parametros);

				// Caso todos os indicadores já tenham sido calculados, basta buscar os
				// resultados dos indicadores
				
				if (!(listaIndicadoresNaoCalculados == null || listaIndicadoresNaoCalculados.isEmpty())) {

					List<Variavel> listaVariaveisRelacionadas = new PossuiVariavelDAO(connection)
							.buscaVariaveisRelacionadas(listaIndicadoresNaoCalculados);

					listaVariaveisRelacionadas = variaveisNaoBD(listaVariaveisRelacionadas);
					
					List<Variavel> listaVariaveisNaoValoradas = new ValorVariavelDAO(connection)
							.buscaVariaveisNaoValoradas(listaVariaveisRelacionadas, parametros);

					ProgressoCalculo.setValorTotal(listaVariaveisNaoValoradas);

					calculaIndicador.buscarVariaveisUmMunicipio(executor, listaVariaveisNaoValoradas, parametros);

					ExecutorService executor2 = Executors.newFixedThreadPool(10);

					IndicadorDAO indicadorDAO = new IndicadorDAO(connection);

					List<Indicador> listaIndicadoresMetodoCalculo = indicadorDAO
							.buscarCodigoEMetodo(listaIndicadoresSelecionados);

					calculaIndicador.calcularIndicador(executor2, listaIndicadoresMetodoCalculo, parametros);
				}

				CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(connection);

				listaIndicadoresCalculados = calculoIndicadorDAO.buscarResultadosList(codigoMunicipio, data,
						listaIndicadoresSelecionados);

				long end = System.currentTimeMillis();
				System.out.println((end - start) + " ms");
			}
			return listaIndicadoresCalculados;

		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar mostrar a lista dos indicadores calculados para: "
					+ codigoMunicipio + ",  " + data + " " + e.getMessage());
		}
	}

	public static void calculaIndicadoresTodosMunicipios(List<Integer> listaIndicadoresSelecionados, String data,
			boolean recalcular, int valorRetroativo) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			
			List<Integer> listaIndicadoresVariaveis = new IndicadorDAO(connection)
					.buscaIndicadoresComoVariaveis(listaIndicadoresSelecionados);
			if (!listaIndicadoresVariaveis.isEmpty()) {
				calculaIndicadoresTodosMunicipios(listaIndicadoresVariaveis, data, recalcular, valorRetroativo);
			}
			
			List<Municipio> listaMunicipios = new MunicipioDAO(connection).buscarTodosMunicipiosComNome();

			List<Variavel> listaVariaveisRelacionadas = new PossuiVariavelDAO(connection)
					.buscaVariaveisRelacionadas(listaIndicadoresSelecionados);

			listaVariaveisRelacionadas = variaveisNaoBD(listaVariaveisRelacionadas);

			if (!recalcular) {
				listaVariaveisRelacionadas = new ValorVariavelDAO(connection)
						.buscaVariaveisNaoValoradas(listaVariaveisRelacionadas, data, valorRetroativo);
			}

			ProgressoCalculo.variaveisTodosMunicipios(listaVariaveisRelacionadas);
			
			ParametrosBuscaValorVariavel parametros = new ParametrosBuscaValorVariavel(data, valorRetroativo,
					recalcular);

			ExecutorService executor = Executors.newFixedThreadPool(10);
			
			CalculaIndicador calculaIndicador = new CalculaIndicador();
			

			long start = System.currentTimeMillis();
			calculaIndicador.buscarVariaveisTodosMunicipios(executor, listaVariaveisRelacionadas, parametros);
			long end = System.currentTimeMillis();
			System.out.println("Tempo: " + (end - start) + " ms");
			
			ProgressoCalculo.setValorTotal(listaMunicipios.size());
			for (Municipio municipio : listaMunicipios) {

				ProgressoCalculo.atualizarValorAtual(municipio.getNome());

				parametros = new ParametrosBuscaValorVariavel(data, municipio.getCodigo(), valorRetroativo, recalcular);

				List<Integer> listaIndicadoresSelecionadosNaoCalculados = listaIndicadoresSelecionados;

				if (!recalcular) {
					listaIndicadoresSelecionadosNaoCalculados = new CalculoIndicadorDAO(connection)
							.buscaIndicadoresNaoCalculados(listaIndicadoresSelecionadosNaoCalculados, parametros);
				}

				IndicadorDAO indicadorDAO = new IndicadorDAO(connection);
				ExecutorService executor2 = Executors.newFixedThreadPool(10);
				List<Indicador> listaIndicadoresMetodoCalculo = indicadorDAO.buscarCodigoEMetodo(listaIndicadoresSelecionados);
				
				calculaIndicador.calcularIndicador(executor2, listaIndicadoresMetodoCalculo, parametros);
			}
			long end2 = System.currentTimeMillis();
			System.out.println("Tempo total: " + (end2 - start) + " ms");
		} catch (Exception e) {
			throw new RuntimeException("Falha ao calcular os indicadores para todos os municípios; Data: " + data
					+ " Erro: " + e.getMessage());
		}
	}

	private static List<Variavel> variaveisNaoBD(List<Variavel> listaVariaveisRelacionadas) {
		List<Variavel> listaVariaveisNaoBD = new ArrayList<>();
		for (Variavel variavel : listaVariaveisRelacionadas) {
			if (!variavel.getTipoBanco().equals("BD")) {
				listaVariaveisNaoBD.add(variavel);
			}
		}
		return listaVariaveisNaoBD;
	}

	/**
	 * Exclui um indicador calculado do banco de dados
	 * 
	 * @param codigo_indicador código do indicador a ser excluído
	 * @param codigo_municipio código do município
	 * @param data             ano
	 */
	public static void excluir(int codigo_indicador, int codigo_municipio, String data) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(connection);
			calculoIndicadorDAO.excluir(codigo_indicador, codigo_municipio, data);
		} catch (Exception e) {
			new JanelaMensagem(e.getMessage());
		}
	}

	/**
	 * 
	 * @return data das variáveis
	 */
	public String getData_variaveis() {
		return data_variaveis;
	}

	/**
	 * 
	 * @return código do indicador
	 */
	public int getCodigo_indicador() {
		return codigo_indicador;
	}

	/**
	 * 
	 * @return código do município
	 */
	public int getCodigo_municipio() {
		return codigo_municipio;
	}

	/**
	 * 
	 * @return ano
	 */
	public String getData() {
		return data;
	}

	/**
	 * 
	 * @return resultado
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * 
	 * @return true caso o valor seja oficial
	 */
	public boolean isValor_oficial() {
		return valor_oficial;
	}

}
