package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo;

import javax.swing.JTable;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.CadastradosIndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;

public class IndicadoresBuscados {

	private int codigo_indicador;
	private int ods;
	private String meta;
	private String nome_indicador;
	private int codigo_municipio;
	private String nome_municipio;
	private String data;
	private String resultado;
	private String nome_uf;
	private String metodo_calculo;
	private String data_variaveis;
	private boolean valor_oficial;
	private boolean padrao;

	public IndicadoresBuscados(int codigo_indicador, int ods, String meta, String nome_indicador, int codigo_municipio,
			String nome_municipio, String data, String resultado, String nome_uf, String metodo_calculo,
			String data_variaveis, boolean valor_oficial, boolean padrao) {
		this.codigo_indicador = codigo_indicador;
		this.ods = ods;
		this.meta = meta;
		this.nome_indicador = nome_indicador;
		this.codigo_municipio = codigo_municipio;
		this.nome_municipio = nome_municipio;
		this.data = data;
		this.resultado = resultado;
		this.nome_uf = nome_uf;
		this.metodo_calculo = metodo_calculo;
		this.data_variaveis = data_variaveis;
		this.valor_oficial = valor_oficial;
		this.padrao = padrao;
	}
	
	public static void buscarIndicadores(JTable table) {
		try {

			CadastradosIndicadorDAO cadastroIndicadorDAO = new CadastradosIndicadorDAO(ConnectionFactory.recuperarConexao());

			cadastroIndicadorDAO.buscaIndicadoresCadastrados(table);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar indicadores.");
		}
	}

	public boolean isPadrao() {
		return padrao;
	}

	public boolean isValor_oficial() {
		return valor_oficial;
	}

	public int getCodigo_indicador() {
		return codigo_indicador;
	}

	public int getOds() {
		return ods;
	}

	public String getMeta() {
		return meta;
	}

	public String getNome_indicador() {
		return nome_indicador;
	}

	public int getCodigo_municipio() {
		return codigo_municipio;
	}

	public String getNome_municipio() {
		return nome_municipio;
	}

	public String getData() {
		return data;
	}

	public String getResultado() {
		return resultado;
	}

	public String getNome_uf() {
		return nome_uf;
	}

	public String getMetodo_calculo() {
		return metodo_calculo;
	}

	public String getData_variaveis() {
		return data_variaveis;
	}

}
