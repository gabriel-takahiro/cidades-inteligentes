package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo;

import javax.swing.JTable;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.ValorVariavelDAO;

/**
 * Classe que representa a tabela valor_variavel do banco de dados.
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class ValorVariavel {

	private int codigo_variavel;
	private int codigo_municipio;
	private String data;
	private String valor;
	private boolean valor_oficial;
	
	public ValorVariavel(String valor, boolean valor_real) {
		this.valor = valor;
		this.valor_oficial = valor_real;
	}

	public ValorVariavel(int codigo_variavel, int codigo_municipio, String data, String valor) {
		this.codigo_variavel = codigo_variavel;
		this.codigo_municipio = codigo_municipio;
		this.data = data;
		this.valor = valor;
	}
	
	public static void inserirValorVariavel(int codigo_variavel, int codigo_municipio, String data, float valor) {
		try {

			ValorVariavelDAO valorVariavelDAO = new ValorVariavelDAO(ConnectionFactory.recuperarConexao());

			valorVariavelDAO.tentarInserirValorVariavel(codigo_variavel, codigo_municipio, data, valor);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao inserir valor variavel: " + codigo_variavel + " Valor: " + valor);
		}
	}

	public static void buscarValores(JTable table, int codigo_municipio, int row) {
		try {

			ValorVariavelDAO valorVariavelDAO = new ValorVariavelDAO(ConnectionFactory.recuperarConexao());

			valorVariavelDAO.buscarValores(table, codigo_municipio, row);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar valor variavel: " + table.getValueAt(row, 3));
		}
	}
	
	public int getCodigo_variavel() {
		return codigo_variavel;
	}

	public int getCodigo_municipio() {
		return codigo_municipio;
	}

	public String getData() {
		return data;
	}

	public String getValor() {
		return valor;
	}

	public boolean isValor_oficial() {
		return valor_oficial;
	}

}
