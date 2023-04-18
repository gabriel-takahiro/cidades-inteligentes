package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.busca;

import java.io.BufferedReader;
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
import com.google.gson.JsonParser;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.calculo.ProgressoCalculo;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.ValorVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.ParametrosBuscaValorVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.ValorVariavelDAO;

public class BuscaSidra implements Busca {

	@Override
	public void buscaUmMunicipio(Variavel variavel, ParametrosBuscaValorVariavel parametros) {
		int ano = Integer.parseInt(parametros.getData());
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			ValorVariavelDAO valorVariavelDAO = new ValorVariavelDAO(connection);
			if (variavel.getAtualizacao().equals("Decenal")) {
				for (int i = 0; i <= 10 + parametros.getAnosRetroativos(); i++) {
					try {
						String valor = buscaValorSidraUmMunicipio(variavel.getCodigo_banco(), Integer.toString(ano),
								parametros.getCodigoMunicipio());
						if (Integer.toString(ano).equals(parametros.getData())) {
							valorVariavelDAO.inserirUpdateValorVariavel(variavel.getCodigo_variavel(),
									parametros.getCodigoMunicipio(), Integer.toString(ano), valor, true, true);
						} else {
							valorVariavelDAO.inserirUpdateValorVariavel(variavel.getCodigo_variavel(),
									parametros.getCodigoMunicipio(), Integer.toString(ano), valor, true, true);
							valorVariavelDAO.inserirUpdateValorVariavel(variavel.getCodigo_variavel(),
									parametros.getCodigoMunicipio(), parametros.getData(), valor, false, true);
						}
						return;
					} catch (Exception e) {
					}
					ano--;
				}
			} else {
				for (int i = 0; i <= 1 + parametros.getAnosRetroativos(); i++) {
					try {
						String valor = buscaValorSidraUmMunicipio(variavel.getCodigo_banco(), Integer.toString(ano),
								parametros.getCodigoMunicipio());
						if (Integer.toString(ano).equals(parametros.getData())) {
							valorVariavelDAO.inserirUpdateValorVariavel(variavel.getCodigo_variavel(),
									parametros.getCodigoMunicipio(), Integer.toString(ano), valor, true, true);
						} else {
							valorVariavelDAO.inserirUpdateValorVariavel(variavel.getCodigo_variavel(),
									parametros.getCodigoMunicipio(), Integer.toString(ano), valor, true, true);
							valorVariavelDAO.inserirUpdateValorVariavel(variavel.getCodigo_variavel(),
									parametros.getCodigoMunicipio(), parametros.getData(), valor, false, true);
						}
						return;
					} catch (Exception e) {
					}
					ano--;
				}
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void buscarTodosMunicipios(Variavel variavel, ParametrosBuscaValorVariavel parametros) {
		int ano = Integer.parseInt(parametros.getData());
		int anosRetroativos = parametros.getAnosRetroativos();
		StringBuilder anos = new StringBuilder();
		int anosAtualizacao = variavel.getAtualizacao().equals("Decenal") ? 10 : 1;
		for (int i = 0; i <= anosAtualizacao + anosRetroativos; i++) {
			anos.append(ano);
			anos.append(",");
			ano--;
		}
		anos.deleteCharAt(anos.length() - 1);

		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			List<ValorVariavel> listaVariaveis = buscaValorSidraTodosMunicipios(variavel.getCodigo_banco(),
					anos.toString());
			ValorVariavelDAO valorVariavelDAO = new ValorVariavelDAO(connection);
			for (ValorVariavel valorVariavel : listaVariaveis) {
				valorVariavelDAO.inserirUpdateValorVariavel(variavel.getCodigo_variavel(),
						valorVariavel.getCodigo_municipio(), valorVariavel.getData(), valorVariavel.getValor(), true,
						true);
			}
			ProgressoCalculo.atualizarVariaveisTodosMunicipios();
		} catch (Exception e) {
			ProgressoCalculo.atualizarVariaveisTodosMunicipios();
			System.out.println("Erro busca todos municipios " + e.getMessage());
		}
	}

	private String buscaValorSidraUmMunicipio(String codigoBanco, String ano, int codigoMunicipio) {
		String url = "https://apisidra.ibge.gov.br/values/h/n/t/";
		codigoBanco = codigoBanco.replaceAll("/p/all", "/p/" + ano);
		codigoBanco = codigoBanco.replaceAll("/n1/all", "/n6/" + codigoMunicipio);
		url += codigoBanco;

		int tentativas = 1;
		int maxTentativas = 2;

		while (tentativas <= maxTentativas) {
			try {
				URL urlBusca = new URL(url);
				StringBuilder result = new StringBuilder();
				HttpURLConnection conn = (HttpURLConnection) urlBusca.openConnection();

				conn.setConnectTimeout(5000);
				conn.setReadTimeout(10000);

				conn.setRequestMethod("GET");

				try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
					for (String line; (line = reader.readLine()) != null;) {
						result.append(line);
					}
				}

				String json = result.toString();
				json = json.replace("[", "");
				json = json.replace("]", "");

				JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
				String valor = jsonObject.get("V").getAsString();
				return valor;
			} catch (Exception e) {
				System.out.println("Tentativa: " + tentativas + " Erro: " + e);
				tentativas++;
			}
		}
		throw new RuntimeException("Falha ao buscar o valor Sidra para um municípios.");
	}

	public static List<ValorVariavel> buscaValorSidraTodosMunicipios(String codigoBanco, String anos) {
		String url = "https://apisidra.ibge.gov.br/values/h/n/t/";
		codigoBanco = codigoBanco.replaceAll("/p/all", "/p/" + anos);
		codigoBanco = codigoBanco.replaceAll("/n1/all", "/n6/all");
		url += codigoBanco;

		int tentativas = 1;
		int maxTentativas = 2;

		while (tentativas <= maxTentativas) {
			try {
				URL urlBusca = new URL(url);
				HttpURLConnection httpConnection = (HttpURLConnection) urlBusca.openConnection();

				httpConnection.setConnectTimeout(5000);
				httpConnection.setReadTimeout(300000);

				InputStream inputStream = httpConnection.getInputStream();

				Gson gson = new Gson();
				Reader reader = new InputStreamReader(inputStream);
				JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

				List<ValorVariavel> listaResultados = new ArrayList<>();

				for (JsonElement jsonElement : jsonArray) {
					JsonObject jsonObject = jsonElement.getAsJsonObject();
					String codigo = jsonObject.get("D1C").getAsString();
					String data = jsonObject.get("D3C").getAsString();
					String valor = jsonObject.get("V").getAsString();
					listaResultados.add(new ValorVariavel(Integer.parseInt(codigo), data, valor));
				}
				return listaResultados;
			} catch (Exception e) {
				System.out.println("Tentativa: " + tentativas + " Erro: " + e);
				tentativas++;
			}
		}
		throw new RuntimeException("Falha ao buscar o valor Sidra para todos os municípios.");
	}

}
