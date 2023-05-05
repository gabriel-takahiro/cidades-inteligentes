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
package br.edu.mg.unifal.bcc.sgici.busca;

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

import br.edu.mg.unifal.bcc.sgici.calculo.ParametrosBuscaValorVariavel;
import br.edu.mg.unifal.bcc.sgici.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.ProgressoCalculo;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.Configuracao;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.sgici.modelo.ValorVariavel;
import br.edu.mg.unifal.bcc.sgici.modelo.Variavel;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.ValorVariavelDAO;

/**
 * Essa classe é responsável por buscar os valores das variáveis na base de
 * dados governamental do Sidra e armazená-los localmente no banco de dados. É
 * importante destacar que o processo de busca é feito utilizando a API do Sidra
 * e as informações coletadas são inseridas ou atualizadas na base de dados
 * local por meio da classe ValorVariavelDAO.
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 *
 */
public class BuscaSidra implements Busca {

	/**
	 * Busca o valor de uma variável para um município em um determinado ano e
	 * insere ou atualiza o valor na tabela valor_variavel do banco de dados. Se a
	 * atualização da variável for decenal, busca valores retroativos até 10 anos
	 * atrás mais o número de anos retroativos informado nos parâmetros. Caso
	 * contrário, busca valores retroativos até 1 ano atrás mais o número de anos
	 * retroativos informado nos parâmetros. Se o valor for encontrado, insere ou
	 * atualiza o valor na tabela valor_variavel com a data de referência informada
	 * nos parâmetros. Se o valor não for encontrado, tenta buscar o valor para anos
	 * retroativos até o limite informado nos parâmetros e, se encontrado, insere ou
	 * atualiza na tabela valor_variavel com a data de referência informada nos
	 * parâmetros.
	 * 
	 * @param variavel   a variável a ser buscada
	 * @param parametros os parâmetros de busca contendo o código do município, o
	 *                   ano de referência e o número de anos retroativos a serem
	 *                   buscados
	 */
	@Override
	public void buscaUmMunicipio(Variavel variavel, ParametrosBuscaValorVariavel parametros) {
		int ano = Integer.parseInt(parametros.getData());
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			ValorVariavelDAO valorVariavelDAO = new ValorVariavelDAO(connection);
			int anosAtualizacao = variavel.getAtualizacao().equals("Decenal") ? 10 : 1;
			for (int i = 0; i <= anosAtualizacao + parametros.getAnosRetroativos(); i++) {
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
		} catch (Exception e) {
		}
	}

	/**
	 * Busca o valor de uma variável para todos os municípios em um determinado ano
	 * e insere ou atualiza o valor na tabela valor_variavel do banco de dados. Se a
	 * atualização da variável for decenal, busca valores retroativos até 10 anos
	 * atrás mais o número de anos retroativos informado nos parâmetros. Caso
	 * contrário, busca valores retroativos até 1 ano atrás mais o número de anos
	 * retroativos informado nos parâmetros. Se o valor for encontrado, insere ou
	 * atualiza o valor na tabela valor_variavel com a data de referência informada
	 * nos parâmetros. A busca será feita para todos os anos, dentro do intervalo
	 * permitido, de uma vez. Os valores encontrados serão salvos na tabela
	 * valor_variavel do banco de dados local.
	 * 
	 * @param variavel   a variável a ser buscada
	 * @param parametros os parâmetros de busca contendo o ano de referência e o
	 *                   número de anos retroativos a serem buscados
	 */
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

	/**
	 * Realiza a busca do valor da variável Sidra para um município específico.
	 * 
	 * @param codigoBanco     código do banco Sidra da variável a ser buscada
	 * @param ano             ano da variável a ser buscada
	 * @param codigoMunicipio código do município a ser buscado
	 * @return o valor da variável Sidra encontrado
	 */
	private String buscaValorSidraUmMunicipio(String codigoBanco, String ano, int codigoMunicipio) {
		
		Configuracao conf = JanelaPrincipal.getConfiguracao();
		int connectTimeoutSidraUmMunicipio = conf.getConnectTimeoutSidraUmMunicipio();
		int readTimeoutSidraUmMunicipio = conf.getReadTimeoutSidraUmMunicipio();
		
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

				conn.setConnectTimeout(connectTimeoutSidraUmMunicipio);
				conn.setReadTimeout(readTimeoutSidraUmMunicipio);

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
				tentativas++;
			}
		}
		throw new RuntimeException("Falha ao buscar o valor Sidra para um municípios.");
	}

	/**
	 * Busca os valores de uma variável no Sidra para todos os municípios do Brasil
	 * em um determinado intervalo de tempo, determinado pelo parâmetro anos.
	 * Tenta-se obter o valor da variável apenas mais uma vez, em caso de erro.
	 * 
	 * @param codigoBanco código do banco Sidra da variável a ser buscada
	 * @param anos        os anos desejados para a busca, no formato
	 *                    "ano1,ano2,...". Exemplo: 2020,2019,2018...
	 * @return uma lista de objetos ValorVariavel contendo o código do município, a
	 *         data e o valor da variável.
	 */
	public static List<ValorVariavel> buscaValorSidraTodosMunicipios(String codigoBanco, String anos) {
		
		Configuracao conf = JanelaPrincipal.getConfiguracao();
		int connectTimeoutSidraTodosMunicipios = conf.getConnectTimeoutSidraTodosMunicipios();
		int readTimeoutSidraTodosMunicipios = conf.getReadTimeoutSidraTodosMunicipios();
		
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

				httpConnection.setConnectTimeout(connectTimeoutSidraTodosMunicipios);
				httpConnection.setReadTimeout(readTimeoutSidraTodosMunicipios);

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
				tentativas++;
			}
		}
		throw new RuntimeException("Falha ao buscar o valor Sidra para todos os municípios.");
	}

}
