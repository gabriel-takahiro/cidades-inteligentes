package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.busca;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.calculo.ProgressoCalculo;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.ValorVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.ParametrosBuscaValorVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.ValorVariavelDAO;

public class BuscaIPEA implements Busca {

	@Override
	public void buscaUmMunicipio(Variavel variavel, ParametrosBuscaValorVariavel parametros) {
		int minimo, ano = Integer.parseInt(parametros.getData());
		if (variavel.getAtualizacao().equals("Decenal")) {
			minimo = ano - 10 - parametros.getAnosRetroativos();
		} else {
			minimo = ano - 1 - parametros.getAnosRetroativos();
		}

		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			ValorVariavelDAO valorVariavelDAO = new ValorVariavelDAO(connection);
			List<ValorVariavel> listaResultados = buscaValorIPEA(variavel.getCodigo_banco(), minimo, ano);
			for (ValorVariavel valorVariavel : listaResultados) {
				valorVariavelDAO.inserirUpdateValorVariavel(variavel.getCodigo_variavel(),
						valorVariavel.getCodigo_municipio(), valorVariavel.getData(), valorVariavel.getValor(), true,
						true);
			}
			ProgressoCalculo.atualizarValorAtual();
		} catch (Exception e) {
		}
	}

	@Override
	public void buscarTodosMunicipios(Variavel variavel, ParametrosBuscaValorVariavel parametros) {
		int minimo, ano = Integer.parseInt(parametros.getData());
		if (variavel.getAtualizacao().equals("Decenal")) {
			minimo = ano - 10 - parametros.getAnosRetroativos();
		} else {
			minimo = ano - 1 - parametros.getAnosRetroativos();
		}

		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			ValorVariavelDAO valorVariavelDAO = new ValorVariavelDAO(connection);
			List<ValorVariavel> listaResultados = buscaValorIPEA(variavel.getCodigo_banco(), minimo, ano);
			for (ValorVariavel valorVariavel : listaResultados) {
				valorVariavelDAO.inserirUpdateValorVariavel(variavel.getCodigo_variavel(),
						valorVariavel.getCodigo_municipio(), valorVariavel.getData(), valorVariavel.getValor(), true,
						true);
			}
			ProgressoCalculo.atualizarVariaveisTodosMunicipios();
		} catch (Exception e) {
			ProgressoCalculo.atualizarVariaveisTodosMunicipios();
		}
	}

	private List<ValorVariavel> buscaValorIPEA(String codigoBanco, int anoMinimo, int ano) {
		String url = "http://www.ipeadata.gov.br/api/odata4/ValoresSerie(SERCODIGO=";
		codigoBanco = "'" + codigoBanco + "')";
		url += codigoBanco;

		int tentativas = 1;
		int maxTentativas = 2;

		while (tentativas <= maxTentativas) {
			try {
				URL urlBusca = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlBusca.openConnection();

				connection.setConnectTimeout(5000);
				connection.setReadTimeout(180000);

				InputStream inputStream = connection.getInputStream();

				Gson gson = new Gson();
				Reader reader = new InputStreamReader(inputStream);
				JsonElement root = gson.fromJson(reader, JsonElement.class);
				JsonArray jsonArray = root.getAsJsonObject().get("value").getAsJsonArray();

				List<ValorVariavel> listaResultados = new ArrayList<>();

				for (JsonElement jsonElement : jsonArray) {
					JsonObject jsonObject = jsonElement.getAsJsonObject();
					String data = jsonObject.get("VALDATA").getAsString();
					String codigo = jsonObject.get("TERCODIGO").getAsString();
					String valor = jsonObject.get("VALVALOR").getAsString();
					int anoJson = Integer.parseInt(data.substring(0, 4));
					if (anoJson >= anoMinimo && anoJson <= ano && codigo.length() == 7) {
						listaResultados
								.add(new ValorVariavel(Integer.parseInt(codigo), Integer.toString(anoJson), valor));
					}
				}
				return listaResultados;
			} catch (Exception e) {
				System.out.println("Tentativa: " + tentativas + " Erro: " + e);
				tentativas++;
			}
		}
		throw new RuntimeException("Falha ao buscar o valor IPEA para todos os municípios.");
	}

}
