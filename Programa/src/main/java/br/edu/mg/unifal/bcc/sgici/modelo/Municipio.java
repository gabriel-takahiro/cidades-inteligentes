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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.mg.unifal.bcc.sgici.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.MunicipioDAO;

/**
 * Classe que representa a tabela municipio do banco de dados.
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class Municipio {

	private int codigo;
	private String nome;
	private String uf;
	private String cep;

	/**
	 * Construtor padrão
	 */
	public Municipio() {

	}
	
	/**
	 * 
	 * @param codigo código do município
	 * @param nome   nome do município
	 */
	public Municipio(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	/**
	 * 
	 * @param codigo código do município
	 * @param nome   nome do município
	 * @param uf     nome da unidade federativa
	 */
	public Municipio(int codigo, String nome, String uf) {
		this.codigo = codigo;
		this.nome = nome;
		this.uf = uf;
	}

	/**
	 * Busca e extrai um json de uma url
	 * @param typed_url url a ser buscada
	 * @return json com as informações da url
	 * @throws Exception caso não consiga extrair um json de uma url
	 */
	public static String search(String typed_url) throws Exception {
		StringBuilder result = new StringBuilder();
		URL url = new URL(typed_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
			for (String line; (line = reader.readLine()) != null;) {
				result.append(line);
			}
		}
		String json = result.toString();
		json = json.replace("[", "");
		json = json.replace("]", "");
		return json;
	}

	/**
	 * Faz uma busca no json com o parâmetro informado
	 * @param json json com as informações
	 * @param parametro parâmetro desejado
	 * @return valor correspondente do parâmetro
	 */
	public static String buscaJson(String json, String parametro) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(json);
			String temp = node.get(parametro).asText();
			return temp;
		} catch (JsonMappingException e) {
			System.out.println("Falha na busca Json JsonMapping.");
		} catch (JsonProcessingException e) {
			System.out.println("Falha na busca Json JsonProcessingException.");
		}
		return "";
	}

	/**
	 * Realiza a busca do nome do município junto com a unidade federativa a partir
	 * do cep
	 * 
	 * @param cep cep de uma localidade
	 * @return nome do município junto com a unidade federativa
	 * @throws Exception caso falhe ao busca o nome do município junto com a unidade federativa
	 */
	public static String buscaNomeComUF(String cep) throws Exception {

		cep = cep.replace("-", "");
		cep = cep.replace(" ", "");

		String url = "https://viacep.com.br/ws/" + cep + "/json/";

		String json = search(url);
		String municipio = buscaJson(json, "localidade");
		String uf = "(" + buscaJson(json, "uf") + ")";
		int codigo = Integer.parseInt(buscaJson(json, "ibge"));

		verificaExistenciaDoMunicipio(codigo, municipio, uf);

		String nomeComUF = municipio + uf;
		return nomeComUF;
	}

	/**
	 * Verifica a existência do município. Caso o município não esteja cadastrado no
	 * banco de dados, ele será cadastrado na hora.
	 * 
	 * @param codigo    código do município
	 * @param municipio nome do município
	 * @param uf        unidade federativa
	 */
	private static void verificaExistenciaDoMunicipio(int codigo, String municipio, String uf) {

		try (Connection connection = ConnectionFactory.recuperarConexao();){

			MunicipioDAO municipioDAO = new MunicipioDAO(connection);

			municipioDAO.verificaExistencia(codigo, municipio, uf);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao verificar a existencia do municipio.");
		}

	}

	/**
	 * Realiza a busca do código do município a partir do cep
	 * 
	 * @param cep cep de uma localidade
	 * @return código do município
	 * @throws Exception caso falhe ao busca o código do município
	 */
	public static int buscaCodigoDoMunicipio(String cep) throws Exception {

		cep = cep.replace("-", "");
		cep = cep.replace(" ", "");

		String url = "https://viacep.com.br/ws/" + cep + "/json/";

		String json = search(url);
		return Integer.parseInt(buscaJson(json, "ibge"));

	}

	/**
	 * 
	 * @return código do município
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * 
	 * @return nome do município
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * 
	 * @return unidade federativa
	 */
	public String getUf() {
		return uf;
	}

	/**
	 * 
	 * @return cep
	 */
	public String getCep() {
		return cep;
	}

}
