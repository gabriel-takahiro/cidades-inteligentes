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
package br.edu.mg.unifal.bcc.sgici.modelo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.edu.mg.unifal.bcc.sgici.calculo.CalculaIndicador;
import br.edu.mg.unifal.bcc.sgici.calculo.ParametrosBuscaValorVariavel;
import br.edu.mg.unifal.bcc.sgici.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.ProgressoCalculo;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.Configuracao;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.CalculoIndicadorDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.IndicadorDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.MunicipioDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.PossuiVariavelDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.ValorVariavelDAO;

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

	/**
	 * Construtor padrão
	 */
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
	 * <p>
	 * Realiza o cálculo dos indicadores selecionados para o município e ano em
	 * questão e retorna uma lista contendo os indicadores calculados. Para realizar
	 * o cálculo dos indicadores é seguido os seguintes passos:
	 * </p>
	 * 
	 * <ul>
	 * <li><b>Passo 1</b>: Realiza uma varredura nos métodos de cálculo dos
	 * indicadores selecionados e realiza a extração dos indicadores, que são
	 * utilizados como variáveis, para uma lista.</li>
	 * <li><b>Passo 2</b>: Verifica se essa lista está vazia. Caso ela esteja vazia
	 * siga para o terceiro passo, caso contrário faça uma chamada recursiva desse
	 * método utilizando a lista adquirida no passo 1 como parâmetro.</li>
	 * </ul>
	 * 
	 * <p>
	 * A partir daqui o código será separado em duas partes, a parte que precisa
	 * recalcular os indicadores e a parte que não precisa.
	 * </p>
	 * 
	 * <p>
	 * <b>Parte que precisa recalcular os indicadores:</b>
	 * </p>
	 * <ul>
	 * <li><b>Passo 1 (Recalcular)</b>: Selecione todas as variáveis que são
	 * utilizadas nos métodos de cálculo dos indicadores selecionados.</li>
	 * 
	 * <li><b>Passo 2 (Recalcular)</b>: Faça uma filtragem nessas variáveis e
	 * selecione apenas as variáveis que não são do tipo "BD".</li>
	 * 
	 * <li><b>Passo 3 (Recalcular)</b>: Para cada variável será feito uma busca nas
	 * bases de dados governamentais, para os anos dentro do limite de validade de
	 * atualização e para o município em questão. Todo valor encontrado será salvo
	 * na base de dados local.</li>
	 * 
	 * <li><b>Passo 4 (Recalcular)</b>: Faça o cálculo de todos os indicadores
	 * selecionados.
	 * 
	 * <li><b>Passo 5 (Recalcular)</b>: Faça uma busca em todos os indicadores
	 * selecionados e retorne uma lista com todos eles calculados.</li>
	 * </ul>
	 * 
	 * <p>
	 * <b>Parte que não precisa recalcular os indicadores:</b>
	 * </p>
	 * 
	 * <ul>
	 * <li><b>Passo 1 (Não recalcular)</b>: Faça uma busca e retorne uma lista com
	 * os indicadores que não possuem resultados para o ano e município em
	 * questão.</li>
	 * 
	 * <li><b>Passo 2 (Não recalcular)</b>: Caso a lista esteja vazia, vá para o
	 * passo 7 (Não recalcular).</li>
	 * 
	 * <li><b>Passo 3 (Não recalcular)</b>: Selecione todas as variáveis que são
	 * utilizadas nos métodos de cálculo dos indicadores selecionados.</li>
	 * 
	 * <li><b>Passo 4 (Não recalcular)</b>: Faça uma filtragem nessas variáveis e
	 * selecione apenas as variáveis que não são do tipo "BD".</li>
	 * 
	 * <li><b>Passo 5 (Não recalcular)</b>: Faça uma nova filtragem e selecione
	 * apenas as variáveis que não possuem valores salvos no banco de dados.</li>
	 * 
	 * <li><b>Passo 6 (Recalcular)</b>: Para cada variável será feito uma busca nas
	 * bases de dados governamentais, para os anos dentro do limite de validade de
	 * atualização e para o município em questão. Todo valor encontrado será salvo
	 * na base de dados local.</li>
	 * 
	 * <li><b>Passo 7 (Recalcular)</b>: Faça o cálculo de todos os indicadores
	 * selecionados no passo 1 (Não recalcular).</li>
	 * 
	 * <li><b>Passo 8 (Recalcular)</b>: Faça uma busca em todos os indicadores
	 * selecionados e retorne uma lista com todos eles calculados.</li>
	 * </ul>
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

		Configuracao conf = JanelaPrincipal.getConfiguracao();
		int threadPoolSize = conf.getThreadPoolSize();
		
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			// Passo 1
			List<Integer> listaIndicadoresVariaveis = new IndicadorDAO(connection)
					.buscaIndicadoresComoVariaveis(listaIndicadoresSelecionados);

			// Passo 2
			if (!listaIndicadoresVariaveis.isEmpty()) {
				calculaIndicadores(listaIndicadoresVariaveis, codigoMunicipio, data, recalcular, valorRetroativo);
			}

			ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
			CalculaIndicador calculaIndicador = new CalculaIndicador();

			ParametrosBuscaValorVariavel parametros = new ParametrosBuscaValorVariavel(data, codigoMunicipio,
					valorRetroativo, recalcular);

			if (recalcular) {
				long start = System.currentTimeMillis();
				// Passo 1 (Recalcular)
				List<Variavel> listaVariaveisRelacionadas = new PossuiVariavelDAO(connection)
						.buscaVariaveisRelacionadas(listaIndicadoresSelecionados);

				// Passo 2 (Recalcular)
				listaVariaveisRelacionadas = variaveisNaoBD(listaVariaveisRelacionadas);

				ProgressoCalculo.setValorTotal(listaVariaveisRelacionadas);

				// Passo 3 (Recalcular)
				calculaIndicador.buscarVariaveisUmMunicipio(executor, listaVariaveisRelacionadas, parametros);

				ExecutorService executor2 = Executors.newFixedThreadPool(threadPoolSize);

				IndicadorDAO indicadorDAO = new IndicadorDAO(connection);
				List<Indicador> listaIndicadoresMetodoCalculo = indicadorDAO
						.buscarCodigoEMetodo(listaIndicadoresSelecionados);

				// Passo 4 (Recalcular)
				calculaIndicador.calcularIndicador(executor2, listaIndicadoresMetodoCalculo, parametros);

				CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(connection);

				// Passo 5 (Recalcular)
				listaIndicadoresCalculados = calculoIndicadorDAO.buscarResultadosList(codigoMunicipio, data,
						listaIndicadoresSelecionados);

				long end = System.currentTimeMillis();
				System.out.println((end - start) + " ms");

			} else {
				long start = System.currentTimeMillis();

				// Passo 1 (Não recalcular)
				List<Integer> listaIndicadoresNaoCalculados = new CalculoIndicadorDAO(connection)
						.buscaIndicadoresNaoCalculados(listaIndicadoresSelecionados, parametros);

				// Passo 2 (Não recalcular)
				if (!(listaIndicadoresNaoCalculados == null || listaIndicadoresNaoCalculados.isEmpty())) {

					// Passo 3 (Não recalcular)
					List<Variavel> listaVariaveisRelacionadas = new PossuiVariavelDAO(connection)
							.buscaVariaveisRelacionadas(listaIndicadoresNaoCalculados);

					// Passo 4 (Não recalcular)
					listaVariaveisRelacionadas = variaveisNaoBD(listaVariaveisRelacionadas);

					// Passo 5 (Não recalcular)
					List<Variavel> listaVariaveisNaoValoradas = new ValorVariavelDAO(connection)
							.buscaVariaveisNaoValoradas(listaVariaveisRelacionadas, parametros);

					ProgressoCalculo.setValorTotal(listaVariaveisNaoValoradas);

					// Passo 6 (Não recalcular)
					calculaIndicador.buscarVariaveisUmMunicipio(executor, listaVariaveisNaoValoradas, parametros);

					ExecutorService executor2 = Executors.newFixedThreadPool(threadPoolSize);

					IndicadorDAO indicadorDAO = new IndicadorDAO(connection);

					List<Indicador> listaIndicadoresMetodoCalculo = indicadorDAO
							.buscarCodigoEMetodo(listaIndicadoresSelecionados);

					// Passo 7 (Não recalcular)
					calculaIndicador.calcularIndicador(executor2, listaIndicadoresMetodoCalculo, parametros);
				}

				CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(connection);

				// Passo 8 (Não recalcular)
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

	/**
	 * <p>
	 * Realiza o cálculo dos indicadores selecionados para todos os municípios. Para
	 * realizar o cálculo dos indicadores é seguido os seguintes passos:
	 * </p>
	 * <ul>
	 * 
	 * <li><b>Passo 1</b>: Realiza uma varredura nos métodos de cálculo dos
	 * indicadores selecionados e realiza a extração dos indicadores, que são
	 * utilizados como variáveis, para uma lista.</li>
	 * 
	 * <li><b>Passo 2</b>: Verifica se essa lista está vazia. Caso ela esteja vazia
	 * siga para o terceiro passo, caso contrário faça uma chamada recursiva desse
	 * método utilizando a lista adquirida no passo 1 como parâmetro.</li>
	 * 
	 * <li><b>Passo 3</b>: Selecione todas as variáveis que são utilizadas nos
	 * métodos de cálculo dos indicadores selecionados.</li>
	 * 
	 * <li><b>Passo 4</b>: Faça uma filtragem nessas variáveis e selecione apenas as
	 * variáveis que não são do tipo "BD".</li>
	 * 
	 * <li><b>Passo 5</b>: Caso não seja necessário recalcular os indicadores
	 * (parâmetro recalcular) selecione apenas as variáveis que não possuem valores
	 * salvos no banco de dados. Caso seja necessário recalcular os indicadores siga
	 * adiante utilizando a lista obtida no passo 4.</li>
	 * 
	 * <li><b>Passo 6</b>: Para cada variável será feito uma busca nas bases de
	 * dados governamentais, para os anos dentro do limite de validade de
	 * atualização e para todos os município. Todo valor encontrado será salvo na
	 * base de dados local.</li>
	 * 
	 * <li><b>Passo 7</b>: Para cada município será realizado o cálculo de todos os
	 * indicadores selecionados. Como os valores já estão salvos na base de dados
	 * local, durante essa etapa somente esses dados serão utilizados no processo. O
	 * processo de cálculo dos indicadores é feito para cada município e suas
	 * tarefas podem ser definidas da seguinte maneira:
	 * <ul>
	 * 
	 * <li><b>Passo 7.1</b>: Caso não seja necessário recalcular os indicadores
	 * (parâmetro recalcular) selecione apenas os indicadores que não possuem
	 * resultados salvos no banco de dados para o ano e município em questão. Caso
	 * seja necessário recalcular os indicadores siga adiante utilizando a lista com
	 * os indicadores selecionados.</li>
	 * 
	 * <li><b>Passo 7.2</b>: Faça o cálculo dos indicadores para todos os
	 * indicadores adquiridos no passo anterior.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 * @param listaIndicadoresSelecionados Lista com os indicadores selecionados
	 *                                     pelo usuário
	 * @param data                         ano em que serão calculados os
	 *                                     indicadores
	 * @param recalcular                   Booleano que indica se é necessário ou
	 *                                     não recalcular os indicadores
	 * @param valorRetroativo              a quantidade de anos retroativos que
	 *                                     devem ser considerados no cálculo dos
	 *                                     indicadores.
	 */
	public static void calculaIndicadoresTodosMunicipios(List<Integer> listaIndicadoresSelecionados, String data,
			boolean recalcular, int valorRetroativo) {
		
		Configuracao conf = JanelaPrincipal.getConfiguracao();
		int threadPoolSize = conf.getThreadPoolSize();
		
		try (Connection connection = ConnectionFactory.recuperarConexao();) {

			// Passo 1
			List<Integer> listaIndicadoresVariaveis = new IndicadorDAO(connection)
					.buscaIndicadoresComoVariaveis(listaIndicadoresSelecionados);

			// Passo 2
			if (!listaIndicadoresVariaveis.isEmpty()) {
				calculaIndicadoresTodosMunicipios(listaIndicadoresVariaveis, data, recalcular, valorRetroativo);
			}

			List<Municipio> listaMunicipios = new MunicipioDAO(connection).buscarTodosMunicipiosComNome();

			// Passo 3
			List<Variavel> listaVariaveisRelacionadas = new PossuiVariavelDAO(connection)
					.buscaVariaveisRelacionadas(listaIndicadoresSelecionados);

			// Passo 4
			listaVariaveisRelacionadas = variaveisNaoBD(listaVariaveisRelacionadas);

			// Passo 5
			if (!recalcular) {
				listaVariaveisRelacionadas = new ValorVariavelDAO(connection)
						.buscaVariaveisNaoValoradas(listaVariaveisRelacionadas, data, valorRetroativo);
			}

			ProgressoCalculo.variaveisTodosMunicipios(listaVariaveisRelacionadas);

			ParametrosBuscaValorVariavel parametros = new ParametrosBuscaValorVariavel(data, valorRetroativo,
					recalcular);

			ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

			CalculaIndicador calculaIndicador = new CalculaIndicador();

			long start = System.currentTimeMillis();

			// Passo 6
			calculaIndicador.buscarVariaveisTodosMunicipios(executor, listaVariaveisRelacionadas, parametros);

			long end = System.currentTimeMillis();
			System.out.println("Tempo: " + (end - start) + " ms");

			// Passo 7
			ProgressoCalculo.setValorTotal(listaMunicipios.size());
			for (Municipio municipio : listaMunicipios) {

				ProgressoCalculo.atualizarValorAtual(municipio.getNome());

				parametros = new ParametrosBuscaValorVariavel(data, municipio.getCodigo(), valorRetroativo, recalcular);

				List<Integer> listaIndicadoresSelecionadosNaoCalculados = listaIndicadoresSelecionados;

				// Passo 7.1
				if (!recalcular) {
					listaIndicadoresSelecionadosNaoCalculados = new CalculoIndicadorDAO(connection)
							.buscaIndicadoresNaoCalculados(listaIndicadoresSelecionadosNaoCalculados, parametros);
				}

				IndicadorDAO indicadorDAO = new IndicadorDAO(connection);
				ExecutorService executor2 = Executors.newFixedThreadPool(threadPoolSize);
				List<Indicador> listaIndicadoresMetodoCalculo = indicadorDAO
						.buscarCodigoEMetodo(listaIndicadoresSelecionados);

				// Passo 7.2
				calculaIndicador.calcularIndicador(executor2, listaIndicadoresMetodoCalculo, parametros);
			}
			long end2 = System.currentTimeMillis();
			System.out.println("Tempo total: " + (end2 - start) + " ms");
		} catch (Exception e) {
			throw new RuntimeException("Falha ao calcular os indicadores para todos os municípios; Data: " + data
					+ " Erro: " + e.getMessage());
		}
	}

	/**
	 * Seleciona e retorna apenas as variáveis que não são do tipo "BD".
	 * 
	 * @param listaVariaveisRelacionadas Lista de variáveis a ser analisada
	 * @return Lista com as variáveis que não são do tipo "BD"
	 */
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
