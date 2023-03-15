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

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.CalculoIndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.DataDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.IndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.MunicipioDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.PossuiVariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.ValorVariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.VariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.CompostoCalculo;

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
	public static List<IndicadoresBuscados> buscarIndicadoresList(ArrayList<Indicador> listaIndicadoresSelecionados,
			int codigoMunicipio, String data) {
		List<IndicadoresBuscados> listaIndicadoresCalculados = new ArrayList<IndicadoresBuscados>();

		try {
			CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(ConnectionFactory.recuperarConexao());

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
	public static List<IndicadoresBuscados> listaIndicadoresCalculados(
			ArrayList<Indicador> listaIndicadoresSelecionados, int codigoMunicipio, String data, boolean recalcular,
			int valorRetroativo) {
		List<IndicadoresBuscados> listaIndicadoresCalculados = new ArrayList<IndicadoresBuscados>();

		try {

			CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(ConnectionFactory.recuperarConexao());
			Set<CalculoIndicador> resultados = calculoIndicadorDAO.buscarResultadosSet(codigoMunicipio, data);

			VariavelDAO variavelDAO = new VariavelDAO(ConnectionFactory.recuperarConexao());
			List<Variavel> variaveis = variavelDAO.buscarLista();

			ValorVariavelDAO valorVariavelDAO = new ValorVariavelDAO(ConnectionFactory.recuperarConexao());
			PossuiVariavelDAO possuiVariavelDAO = new PossuiVariavelDAO(ConnectionFactory.recuperarConexao());
			DataDAO dataDAO = new DataDAO(ConnectionFactory.recuperarConexao());

			if (recalcular) {
				for (Indicador ic : listaIndicadoresSelecionados) {
					ic.calculaIndicador(variaveis, codigoMunicipio, valorVariavelDAO, calculoIndicadorDAO,
							possuiVariavelDAO, data, dataDAO, recalcular, valorRetroativo);

				}
			} else {
				for (Indicador ic : listaIndicadoresSelecionados) {
					if (!possuiResultado(resultados, ic.getCodigo())) {
						ic.calculaIndicador(variaveis, codigoMunicipio, valorVariavelDAO, calculoIndicadorDAO,
								possuiVariavelDAO, data, dataDAO, recalcular, valorRetroativo);
					}
				}
			}

			listaIndicadoresCalculados = calculoIndicadorDAO.buscarResultadosList(codigoMunicipio, data,
					listaIndicadoresSelecionados);
			return listaIndicadoresCalculados;

		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar mostrar a lista dos indicadores calculados para: "
					+ codigoMunicipio + ",  " + data);
		}
	}

	/**
	 * Realiza o cálculo do indicador a partir do seu código e método de cálculo
	 * @param codigo_indicador código do indicador
	 * @param compostoCalculo conjunto de valores que compõem o cálculo do indicador
	 * @return resultado do indicador
	 */
	public static double calcularIndicador(int codigo_indicador, CompostoCalculo compostoCalculo) {

		try {
			IndicadorDAO indicadorDAO = new IndicadorDAO(ConnectionFactory.recuperarConexao());
			Indicador indicadorCalculo = indicadorDAO.buscarCodigoEMetodo(codigo_indicador);

			indicadorCalculo.calculaIndicador(compostoCalculo.getVariaveis(), compostoCalculo.getCodigo_municipio(),
					compostoCalculo.getValorVariavelDAO(), compostoCalculo.getCalculoIndicadorDAO(),
					compostoCalculo.getPossuiVariavelDAO(), compostoCalculo.getData(), compostoCalculo.getDataDAO(),
					compostoCalculo.isRecalcular(), compostoCalculo.getValorRetroativo());

			String resultado = compostoCalculo.getCalculoIndicadorDAO().buscaResultado(codigo_indicador,
					compostoCalculo.getCodigo_municipio(), compostoCalculo.getData());
			return Double.parseDouble(resultado.replace(",", "."));

		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar calcular o indicador: " + codigo_indicador + " municipio: "
					+ compostoCalculo.getCodigo_municipio() + " data: " + compostoCalculo.getData() + e);
		}
	}

	/**
	 * Realiza o cálculo de indicadores para todos os municípios
	 * @param data ano
	 * @param recalcular true caso seja necessário recalcular os indicadores que já possuírem resultado
	 * @param listaIndicadoresSelecionados lista de indicadores que serão calculados
	 * @param valorRetroativo anos a mais utilizados para a busca retroativa das variáveis
	 */
	public static void calcularTodosMunicipios(String data, boolean recalcular,
			ArrayList<Indicador> listaIndicadoresSelecionados, int valorRetroativo) {

		try {
			CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(ConnectionFactory.recuperarConexao());

			MunicipioDAO municipioDAO = new MunicipioDAO(ConnectionFactory.recuperarConexao());
			List<Integer> codigos = new ArrayList<Integer>();
			codigos = municipioDAO.buscarTodosMunicipios();
			Set<CalculoIndicador> resultados = calculoIndicadorDAO.buscarResultadosTodosMunicipios(codigos, data);

			VariavelDAO variavelDAO = new VariavelDAO(ConnectionFactory.recuperarConexao());
			List<Variavel> variaveis = variavelDAO.buscarLista();

			ValorVariavelDAO valorVariavelDAO = new ValorVariavelDAO(ConnectionFactory.recuperarConexao());
			PossuiVariavelDAO possuiVariavelDAO = new PossuiVariavelDAO(ConnectionFactory.recuperarConexao());
			DataDAO dataDAO = new DataDAO(ConnectionFactory.recuperarConexao());

			if (recalcular) {
				for (Indicador ic : listaIndicadoresSelecionados) {
					for (int codigoMunicipio : codigos) {
						System.out.println(ic.getCodigo() + "  : " + codigoMunicipio);
						ic.calculaIndicador(variaveis, codigoMunicipio, valorVariavelDAO, calculoIndicadorDAO,
								possuiVariavelDAO, data, dataDAO, true, valorRetroativo);
					}
				}
			} else {
				for (Indicador ic : listaIndicadoresSelecionados) {
					for (int codigoMunicipio : codigos) {
						if (!(possuiResultado(resultados, ic.getCodigo(), codigoMunicipio))) {
							System.out.println(ic.getCodigo() + "  : " + codigoMunicipio);
							ic.calculaIndicador(variaveis, codigoMunicipio, valorVariavelDAO, calculoIndicadorDAO,
									possuiVariavelDAO, data, dataDAO, true, valorRetroativo);
						}
					}
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Falha ao tentar calcular os indicadores para todos os municípios." + e);
		}

	}

	/**
	 * Exclui um indicador calculado do banco de dados
	 * @param codigo_indicador código do indicador a ser excluído
	 * @param codigo_municipio código do município
	 * @param data ano
	 */
	public static void excluir(int codigo_indicador, int codigo_municipio, String data) {
		try {
			CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(ConnectionFactory.recuperarConexao());
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
