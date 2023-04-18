package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import com.fathzer.soft.javaluator.DoubleEvaluator;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.ValorVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.busca.BuscaIPEA;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.busca.BuscaSidra;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.CalculoIndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.DataDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.ValorVariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.VariavelDAO;

public class CalculaIndicador {

	public void buscarVariaveisUmMunicipio(ExecutorService executor, List<Variavel> listaVariaveisNaoValoradas,
			ParametrosBuscaValorVariavel parametros) {

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

		int tempo = 120;
		int tempoEspera = Math.round((qtdVariaveis / 10 + 2) * tempo);

		executor.shutdown();
		try {
			executor.awaitTermination(tempoEspera, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	public void buscarVariaveisTodosMunicipios(ExecutorService executor, List<Variavel> listaVariaveisRelacionadas,
			ParametrosBuscaValorVariavel parametros) {
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
		int tempo = 120;
		int tempoEspera = Math.round((qtdVariaveis / 10 + 2) * tempo);

		executor.shutdown();
		try {
			executor.awaitTermination(tempoEspera, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}

	public void calcularIndicador(ExecutorService executor, List<Indicador> listaIndicadoresMetodoCalculo,
			ParametrosBuscaValorVariavel parametros) {
		for (Indicador indicador : listaIndicadoresMetodoCalculo) {
			executor.execute(() -> realizaCalculo(indicador, parametros));
		}

		int qtdVariaveis = listaIndicadoresMetodoCalculo.size();
		int tempo = 5;
		int tempoEspera = Math.round((qtdVariaveis / 10 + 2) * tempo);
		
		executor.shutdown();
		try {
			executor.awaitTermination(tempoEspera, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println("Erro no calculo do indicador");
			System.out.println(e);
		}
	}

	private void realizaCalculo(Indicador indicador, ParametrosBuscaValorVariavel parametros) {
		List<String> listaCaracteres = SequenciaCalculo.listaSimbolos(indicador.getCalculo());
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			CalculoDataVariaveis calculoDataVariaveis = montaCalculo(listaCaracteres, parametros);
			DoubleEvaluator evaluator = new DoubleEvaluator();
			double resultado = evaluator.evaluate(calculoDataVariaveis.getCalculo().replace("x", "*"));
			CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(connection);
			calculoIndicadorDAO.insereResultado(indicador.getCodigo(), parametros.getCodigoMunicipio(),
					parametros.getData(), String.format("%.02f", resultado), calculoDataVariaveis.getDataVariaveis(),
					calculoDataVariaveis.isOficial());
		} catch (Exception e) {
		}
	}

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
}
