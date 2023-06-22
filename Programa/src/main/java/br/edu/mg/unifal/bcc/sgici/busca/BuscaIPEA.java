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
 * dados do IPEA e armazená-los localmente no banco de dados. É importante
 * destacar que o processo de busca é feito utilizando a API do IPEA e as
 * informações coletadas são inseridas ou atualizadas na base de dados local por
 * meio da classe ValorVariavelDAO.
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 *
 */
public class BuscaIPEA implements Busca {

	/**
	 * Realiza a busca de valores de uma variável para um único município e
	 * insere/atualiza os dados no banco de dados.
	 * 
	 * @param variavel   a variável que se deseja buscar os valores.
	 * @param parametros os parâmetros da busca, contendo o código do município e o
	 *                   ano que se deseja buscar os valores, bem como a quantidade
	 *                   de anos retroativos que devem ser buscados.
	 */
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
			ProgressoCalculo.atualizarValorAtual();
		}
	}

	/**
	 * Busca o valor de uma variável para todos os municípios do Brasil, e
	 * insere/atualiza o banco de dados com os resultados.
	 * 
	 * @param variavel   a variável cujos valores serão buscados e atualizados no
	 *                   banco de dados.
	 * @param parametros os parâmetros para a busca, incluindo o ano base e o número
	 *                   de anos retroativos a serem considerados.
	 */
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

	/**
	 * Método responsável por buscar os valores de uma variável em todos os
	 * municípios através da API do IPEA.
	 * 
	 * @param codigoBanco o código da variável no banco de dados do IPEA.
	 * @param anoMinimo   o ano mínimo a ser considerado na busca.
	 * @param ano         o ano máximo a ser considerado na busca.
	 * @return uma lista de objetos ValorVariavel contendo os valores da variável, o
	 *         município e o ano.
	 */
	private List<ValorVariavel> buscaValorIPEA(String codigoBanco, int anoMinimo, int ano) {
		
		Configuracao conf = JanelaPrincipal.getConfiguracao();
		int connectTimeoutIpea = conf.getConnectTimeoutIpea();
		int readTimeoutIpea = conf.getReadTimeoutIpea();
		
		String url = "http://www.ipeadata.gov.br/api/odata4/ValoresSerie(SERCODIGO=";
		codigoBanco = "'" + codigoBanco + "')";
		url += codigoBanco;

		int tentativas = 1;
		int maxTentativas = 2;

		while (tentativas <= maxTentativas) {
			try {
				URL urlBusca = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlBusca.openConnection();

				connection.setConnectTimeout(connectTimeoutIpea);
				connection.setReadTimeout(readTimeoutIpea);

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
					
					if(valor.replace(" ", "").equals("") || valor.equals(null)) {
						valor = "0";
					}
					
					int anoJson = Integer.parseInt(data.substring(0, 4));
					if (anoJson >= anoMinimo && anoJson <= ano && codigo.length() == 7) {
						listaResultados
								.add(new ValorVariavel(Integer.parseInt(codigo), Integer.toString(anoJson), valor));
					}
				}
				return listaResultados;
			} catch (Exception e) {
				tentativas++;
			}
		}
		throw new RuntimeException("Falha ao buscar o valor IPEA para todos os municípios.");
	}

}
