package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo;

import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.CalculoIndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.DataDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.IndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.MunicipioDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.PossuiVariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.ValorVariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.VariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.calculo.CompostoCalculo;

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

	public CalculoIndicador(String resultado, String data_variaveis, boolean valor_oficial) {
		this.resultado = resultado;
		this.data_variaveis = data_variaveis;
		this.valor_oficial = valor_oficial;
	}

	public CalculoIndicador(int codigo_indicador, int codigo_municipio, String data) {
		this.codigo_indicador = codigo_indicador;
		this.codigo_municipio = codigo_municipio;
		this.data = data;
	}

	public CalculoIndicador(int codigo_indicador, int codigo_municipio, String data, String resultado) {
		this.codigo_indicador = codigo_indicador;
		this.codigo_municipio = codigo_municipio;
		this.data = data;
		this.resultado = resultado;
	}

	public static boolean possuiResultado(Set<CalculoIndicador> resultados, int codigo) {
		for (CalculoIndicador resultado : resultados) {
			if (resultado.codigo_indicador == codigo) {
				return true;
			}
		}
		return false;
	}

	public static boolean possuiResultado(Set<CalculoIndicador> resultados, int codigo_indicador,
			int codigo_municipio) {
		for (CalculoIndicador resultado : resultados) {
			if (resultado.codigo_indicador == codigo_indicador && resultado.codigo_municipio == codigo_municipio) {
				return true;
			}
		}
		return false;
	}

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

	public static void excluir(int codigo_indicador, int codigo_municipio, String data) {
		try {
			CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(ConnectionFactory.recuperarConexao());
			calculoIndicadorDAO.excluir(codigo_indicador, codigo_municipio, data);
		} catch (Exception e) {
			new JanelaMensagem(e.getMessage());
		}
	}
	
	public String getData_variaveis() {
		return data_variaveis;
	}

	public int getCodigo_indicador() {
		return codigo_indicador;
	}

	public int getCodigo_municipio() {
		return codigo_municipio;
	}

	public String getData() {
		return data;
	}

	public String getResultado() {
		return resultado;
	}

	public boolean isValor_oficial() {
		return valor_oficial;
	}

}
