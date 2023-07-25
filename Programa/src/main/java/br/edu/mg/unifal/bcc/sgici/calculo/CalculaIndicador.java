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
package br.edu.mg.unifal.bcc.sgici.calculo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import com.fathzer.soft.javaluator.DoubleEvaluator;

import br.edu.mg.unifal.bcc.sgici.busca.BuscaIPEA;
import br.edu.mg.unifal.bcc.sgici.busca.BuscaSidra;
import br.edu.mg.unifal.bcc.sgici.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.ProgressoCalculo;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.resultado.MostrarResultadoParaTodos;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.resultado.MostrarResultadoParaUm;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.Configuracao;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.sgici.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.sgici.modelo.Indicador;
import br.edu.mg.unifal.bcc.sgici.modelo.IndicadoresBuscados;
import br.edu.mg.unifal.bcc.sgici.modelo.Municipio;
import br.edu.mg.unifal.bcc.sgici.modelo.ValorVariavel;
import br.edu.mg.unifal.bcc.sgici.modelo.Variavel;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.CalculoIndicadorDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.DataDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.ValorVariavelDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.VariavelDAO;

/**
 * Classe responsável por calcular os indicadores de cidades inteligentes. Nessa
 * classe é possível buscar as variáveis e calcular os indicadores tanto para um
 * quanto para todos os municípios.
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 *
 */
public class CalculaIndicador {

	/**
	 * Realiza a busca das variáveis nos bancos de dados governamentais para um
	 * município específico, utilizando threads para paralelizar o trabalho.
	 * Inicialmente, este método armazena no banco de dados os anos dentro do limite
	 * de vencimento das variáveis. A paralelização ocorre na busca das variáveis
	 * nas bases de dados governamentais, utilizando uma thread para cada variável a
	 * ser buscada. Um ExecutorService é passado como parâmetro para gerenciar a
	 * execução das threads.
	 * 
	 * @param executor                   executorService utilizado no gerenciamento
	 *                                   das threads.
	 * @param listaVariaveisNaoValoradas lista de variáveis não valoradas.
	 * @param parametros                 um objeto que contém as informações
	 *                                   necessárias para a busca das variáveis,
	 *                                   podendo incluir a data, o código do
	 *                                   município, os anos retroativos e a opção de
	 *                                   recalcular.
	 */
	public void buscarVariaveisUmMunicipio(ExecutorService executor, List<Variavel> listaVariaveisNaoValoradas,
			ParametrosBuscaValorVariavel parametros) {

		Configuracao conf = JanelaPrincipal.getConfiguracao();
		int threadPoolSize = conf.getThreadPoolSize();
		int tempoBusca = conf.getTempoBusca();
		
		int ano = Integer.parseInt(parametros.getData());
		for (int i = 0; i <= 10 + parametros.getAnosRetroativos(); i++) {
			String data = Integer.toString(ano);
			try (Connection conn = ConnectionFactory.recuperarConexao();) {
				new DataDAO(conn).atualizaData(data);
			} catch (Exception e) {
				System.out.println("Erro ao atualizar data");
			}
			ano--;
		}

		int qtdVariaveis = 0;

		try {
			for (Variavel variavel : listaVariaveisNaoValoradas) {
				if (variavel.getTipoBanco().equals("Sidra")) {
					executor.execute(() -> new BuscaSidra().buscaUmMunicipio(variavel, parametros));
				} else if (variavel.getTipoBanco().equals("IPEA")) {
					qtdVariaveis++;
					executor.execute(() -> new BuscaIPEA().buscaUmMunicipio(variavel, parametros));
				} else {
					System.out.println("Erro: " + variavel.getTipoBanco() + "  " + variavel.getCodigo_variavel());
				}
			}
		} catch (Exception e) {
			System.out.println("Erro " + e);
		}

		int tempoExtra = 2;
		int tempoEspera = Math.round((qtdVariaveis / threadPoolSize + tempoExtra) * tempoBusca);

		executor.shutdown();
		try {
			executor.awaitTermination(tempoEspera, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	/**
	 * Realiza a busca das variáveis nos bancos de dados governamentais para todos
	 * os municípios, utilizando threads para paralelizar o trabalho. Inicialmente,
	 * este método armazena no banco de dados os anos dentro do limite de vencimento
	 * das variáveis. A paralelização ocorre na busca das variáveis nas bases de
	 * dados governamentais, utilizando uma thread para cada variável a ser buscada.
	 * Um ExecutorService é passado como parâmetro para gerenciar a execução das
	 * threads.
	 * 
	 * @param executor                   executorService utilizado no gerenciamento
	 *                                   das threads.
	 * @param listaVariaveisRelacionadas lista de variáveis relacionadas.
	 * @param parametros                 um objeto que contém as informações
	 *                                   necessárias para a busca das variáveis,
	 *                                   podendo incluir a data, o código do
	 *                                   município, os anos retroativos e a opção de
	 *                                   recalcular.
	 */
	public void buscarVariaveisTodosMunicipios(ExecutorService executor, List<Variavel> listaVariaveisRelacionadas,
			ParametrosBuscaValorVariavel parametros) {
		
		Configuracao conf = JanelaPrincipal.getConfiguracao();
		int threadPoolSize = conf.getThreadPoolSize();
		int tempoBusca = conf.getTempoBusca();
		
		int ano = Integer.parseInt(parametros.getData());
		for (int i = 0; i <= 10 + parametros.getAnosRetroativos(); i++) {
			String data = Integer.toString(ano);
			try (Connection conn = ConnectionFactory.recuperarConexao();) {
				new DataDAO(conn).atualizaData(data);
			} catch (Exception e) {
				System.out.println("Erro ao atualizar data");
			}
			ano--;
		}

		try {
			for (Variavel variavel : listaVariaveisRelacionadas) {
				if (variavel.getTipoBanco().equals("Sidra")) {
					executor.execute(() -> new BuscaSidra().buscarTodosMunicipios(variavel, parametros));
				} else if (variavel.getTipoBanco().equals("IPEA")) {
					executor.execute(() -> new BuscaIPEA().buscarTodosMunicipios(variavel, parametros));
				} else {
					System.out.println("Erro: " + variavel.getTipoBanco() + "  " + variavel.getCodigo_variavel());
				}
			}
		} catch (Exception e) {
			System.out.println("Erro " + e);
		}

		int qtdVariaveis = listaVariaveisRelacionadas.size();
		int tempoExtra = 2;
		int tempoEspera = Math.round((qtdVariaveis / threadPoolSize + tempoExtra) * tempoBusca);

		executor.shutdown();
		try {
			executor.awaitTermination(tempoEspera, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	/**
	 * Realiza o cálculo do indicador para cada indicador presente na lista de
	 * indicadores passados como parâmetro. Para cada indicador é aberta uma thread,
	 * permitindo a paralelização do cálculo.
	 * 
	 * @param executor                      executorService utilizado no
	 *                                      gerenciamento das threads.
	 * @param listaIndicadoresMetodoCalculo lista com os indicadores que devem ser
	 *                                      calculados.
	 * @param parametros                    um objeto que contém as informações
	 *                                      necessárias para a busca das variáveis,
	 *                                      podendo incluir a data, o código do
	 *                                      município, os anos retroativos e a opção
	 *                                      de recalcular.
	 */
	public void calcularIndicador(ExecutorService executor, List<Indicador> listaIndicadoresMetodoCalculo,
			ParametrosBuscaValorVariavel parametros) {
		
		Configuracao conf = JanelaPrincipal.getConfiguracao();
		int threadPoolSize = conf.getThreadPoolSize();
		int tempoCalculo = conf.getTempoCalculo();
		
		for (Indicador indicador : listaIndicadoresMetodoCalculo) {
			executor.execute(() -> realizaCalculo(indicador, parametros));
		}

		int qtdVariaveis = listaIndicadoresMetodoCalculo.size();
		int tempoExtra = 2;
		int tempoEspera = Math.round((qtdVariaveis / threadPoolSize + tempoExtra) * tempoCalculo);

		executor.shutdown();
		try {
			executor.awaitTermination(tempoEspera, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	/**
	 * Realiza o cálculo do indicador com base no método de cálculo e em certos
	 * parâmetros definidos. Após a montagem do cálculo, o indicador é calculado e
	 * seu valor é armazenado na base de dados local.
	 * 
	 * @param indicador  indicador a ser calculado
	 * @param parametros um objeto que contém as informações necessárias para a
	 *                   busca das variáveis, podendo incluir a data, o código do
	 *                   município, os anos retroativos e a opção de recalcular.
	 */
	private void realizaCalculo(Indicador indicador, ParametrosBuscaValorVariavel parametros) {
		List<String> listaCaracteres = SequenciaCalculo.listaSimbolos(indicador.getCalculo());
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			CalculoDataVariaveis calculoDataVariaveis = montaCalculo(listaCaracteres, parametros);
			DoubleEvaluator evaluator = new DoubleEvaluator();
			double resultado = evaluator.evaluate(calculoDataVariaveis.getCalculo().replace("x", "*"));
			
			if (Double.isInfinite(resultado) || Double.isNaN(resultado)) {
	            throw new ArithmeticException("Divisão por zero ocorreu no cálculo");
	        }
			
			CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(connection);
			calculoIndicadorDAO.insereResultado(indicador.getCodigo(), parametros.getCodigoMunicipio(),
					parametros.getData(), String.format("%.02f", resultado), calculoDataVariaveis.getDataVariaveis(),
					calculoDataVariaveis.isOficial());
		} catch (Exception e) {
		}
	}

	/**
	 * Realiza a montagem do cálculo do indicador a partir do método de cálculo. As
	 * variáveis presentes no método de cálculo têm seus valores substituídos pelos
	 * seus respectivos valores na base de dados local. Além da montagem do cálculo,
	 * é gerada uma string que contém as variáveis e as datas correspondentes,
	 * separadas por barras, que são utilizadas posteriormente para identificar os
	 * valores utilizados.
	 * 
	 * @param listaCaracteres método de cálculo.
	 * @param parametros      um objeto que contém as informações necessárias para a
	 *                        busca das variáveis, podendo incluir a data, o código
	 *                        do município, os anos retroativos e a opção de
	 *                        recalcular.
	 * @return um objeto que contém o método de cálculo montado, a data das
	 *         variáveis e se o valor é oficial.
	 */
	private CalculoDataVariaveis montaCalculo(List<String> listaCaracteres, ParametrosBuscaValorVariavel parametros) {
		String calculo = "";
		String dataVariaveis = "";
		boolean oficial = true;
		boolean variavel = true;
		boolean indicador = false;
		for (String simbolo : listaCaracteres) {
			switch (simbolo) {
			case "[":
				variavel = false;
				break;
			case "]":
				variavel = true;
				break;
			case "x":
				calculo += simbolo;
				break;
			case "/":
				calculo += simbolo;
				break;
			case "(":
				calculo += simbolo;
				break;
			case ")":
				calculo += simbolo;
				break;
			case "+":
				calculo += simbolo;
				break;
			case "-":
				calculo += simbolo;
				break;
			case "{":
				indicador = true;
				break;
			case "}":
				indicador = false;
				break;
			default:
				if (variavel && !indicador) {
					try (Connection connection = ConnectionFactory.recuperarConexao();) {
						ValorVariavelDAO valorVariavelDAO = new ValorVariavelDAO(connection);
						String atualizacao = new VariavelDAO(connection).buscaAtualizacao(Integer.parseInt(simbolo));
						int anosAtualizacao = atualizacao.equals("Decenal") ? 10 : 1;
						int anosRetroativos = parametros.getAnosRetroativos();
						int ano = Integer.parseInt(parametros.getData());

						int minimo = ano - anosAtualizacao - anosRetroativos;

						ValorVariavel valorVariavel = valorVariavelDAO.buscaValorVariavel(Integer.parseInt(simbolo),
								parametros.getCodigoMunicipio(), ano, minimo);

						oficial = oficial && valorVariavel.isValor_oficial();
						calculo += valorVariavel.getValor();

						if (dataVariaveis.isEmpty() || dataVariaveis.isBlank()) {
							dataVariaveis += simbolo + "/" + valorVariavel.getData();
						} else {
							if (!(dataVariaveis.contains(simbolo + "/" + valorVariavel.getData()))) {
								dataVariaveis += "/" + simbolo + "/" + valorVariavel.getData();
							}
						}
						break;
					} catch (Exception e) {
						throw new RuntimeException("Variável erro " + simbolo + e);
					}
				}
				if (!variavel && !indicador) {
					calculo += simbolo;
					break;
				}
				if (indicador) {
					try (Connection connection = ConnectionFactory.recuperarConexao();) {
						CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(connection);
						calculo += calculoIndicadorDAO.buscaResultado(Integer.parseInt(simbolo),
								parametros.getCodigoMunicipio(), parametros.getData()).replace(",", ".");
						break;
					} catch (Exception e) {
						throw new RuntimeException("Variável-indicador erro " + simbolo + e);
					}
				}
			}
		}
		return new CalculoDataVariaveis(calculo, dataVariaveis, oficial);
	}

	/**
	 * Realiza o cálculo dos indicadores para todos os municípios.
	 * 
	 * @param listaIndicadoresSelecionados lista contendo os indicadores
	 *                                     selecionados para serem calculados
	 * @param valorRetroativo              quantidade de anos retroativos que devem
	 *                                     ser considerados no cálculo dos
	 *                                     indicadores.
	 * @param data                         ano
	 * @param recalcular                   um booleano que indica se os indicadores
	 *                                     devem ser recalculados mesmo que já
	 *                                     tenham sido calculados anteriormente.
	 */
	public static void calculoIndicadoresTodosMunicipios(ArrayList<Integer> listaIndicadoresSelecionados,
			int valorRetroativo, String data, boolean recalcular) {
		if (JanelaPrincipal.isCalculando()) {
			new JanelaMensagem(
					"O programa já está calculando alguns indicadores.\nTente novamente após o cálculo desses indicadores.");
			return;
		}
		JanelaPrincipal.setCalculando(true);

		ProgressoCalculo progressoCalculo = new ProgressoCalculo("");
		JanelaPrincipal.abrirJanelas(progressoCalculo);

		Runnable tarefa = new Runnable() {
			public void run() {
				try {
					CalculoIndicador.calculaIndicadoresTodosMunicipios(listaIndicadoresSelecionados, data, recalcular,
							valorRetroativo);
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							JanelaPrincipal.abrirJanelas(new MostrarResultadoParaTodos(listaIndicadoresSelecionados,
									data, valorRetroativo, recalcular));
						}
					});
					progressoCalculo.dispose();
				} catch (Exception erro) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							progressoCalculo.dispose();
							new JanelaMensagem(erro.getMessage());
						}
					});
				} finally {
					JanelaPrincipal.setCalculando(false);
				}
			}
		};
		JanelaPrincipal.executor.execute(tarefa);
	}

	/**
	 * Realiza o cálculo dos indicadores para um município.
	 * 
	 * @param listaIndicadoresSelecionados lista contendo os indicadores
	 *                                     selecionados para serem calculados
	 * @param valorRetroativo              quantidade de anos retroativos que devem
	 *                                     ser considerados no cálculo dos
	 *                                     indicadores.
	 * @param data                         ano
	 * @param recalcular                   um booleano que indica se os indicadores
	 *                                     devem ser recalculados mesmo que já
	 *                                     tenham sido calculados anteriormente.
	 * @param nomeMunicipio                nome do município que terá os indicadores
	 *                                     calculados.
	 * @param cep                          cep que corresponde a um endereço desse
	 *                                     município.
	 */
	public static void calculoIndicadoresUmMunicipio(ArrayList<Integer> listaIndicadoresSelecionados,
			int valorRetroativo, String data, boolean recalcular, String nomeMunicipio, String cep) {
		if (JanelaPrincipal.isCalculando()) {
			new JanelaMensagem(
					"O programa já está calculando alguns indicadores.\nTente novamente após o cálculo desses indicadores.");
			return;
		}
		JanelaPrincipal.setCalculando(true);

		ProgressoCalculo progressoCalculo = new ProgressoCalculo(nomeMunicipio);
		JanelaPrincipal.abrirJanelas(progressoCalculo);

		Runnable tarefa = new Runnable() {
			public void run() {
				try {
					int codigoMunicipio = Municipio.buscaCodigoDoMunicipio(cep);

					List<IndicadoresBuscados> indicadoresCalculados = CalculoIndicador.calculaIndicadores(
							listaIndicadoresSelecionados, codigoMunicipio, data, recalcular, valorRetroativo);

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							JanelaPrincipal.abrirJanelas(
									new MostrarResultadoParaUm(listaIndicadoresSelecionados, indicadoresCalculados,
											nomeMunicipio, codigoMunicipio, data, valorRetroativo, recalcular));
						}
					});
					progressoCalculo.dispose();
				} catch (Exception erro) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							progressoCalculo.dispose();
							new JanelaMensagem(erro.getMessage());
						}
					});
				} finally {
					JanelaPrincipal.setCalculando(false);
				}
			}
		};
		JanelaPrincipal.executor.execute(tarefa);
	}
}
