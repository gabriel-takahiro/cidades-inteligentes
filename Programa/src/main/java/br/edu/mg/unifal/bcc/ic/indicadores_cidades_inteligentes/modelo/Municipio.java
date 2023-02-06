package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.MunicipioDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;

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

	public Municipio() {

	}

	public Municipio(int codigo, String nome, String uf) {
		this.codigo = codigo;
		this.nome = nome;
		this.uf = uf;
	}

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

	public void busca_cep() throws Exception {

		Scanner scan = new Scanner(System.in);

		System.out.println("Digite o CEP:");
		String cep = scan.nextLine();
		scan.close();
		cep = cep.replace("-", "");
		cep = cep.replace(" ", "");

		this.cep = cep;
		String url = "https://viacep.com.br/ws/" + cep + "/json/";

		String json = search(url);
		this.codigo = Integer.parseInt(buscaJson(json, "ibge"));
		this.nome = buscaJson(json, "localidade");
		this.uf = buscaJson(json, "uf");

	}

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

	private static void verificaExistenciaDoMunicipio(int codigo, String municipio, String uf) {

		try {

			MunicipioDAO municipioDAO = new MunicipioDAO(ConnectionFactory.recuperarConexao());

			municipioDAO.verificaExistencia(codigo, municipio, uf);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao verificar a existencia do municipio.");
		}

	}

	public static int buscaCodigoDoMunicipio(String cep) throws Exception {

		cep = cep.replace("-", "");
		cep = cep.replace(" ", "");

		String url = "https://viacep.com.br/ws/" + cep + "/json/";

		String json = search(url);
		return Integer.parseInt(buscaJson(json, "ibge"));

	}
	
	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public String getUf() {
		return uf;
	}

	public String getCep() {
		return cep;
	}

}
