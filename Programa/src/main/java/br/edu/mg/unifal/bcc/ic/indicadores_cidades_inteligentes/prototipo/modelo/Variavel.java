package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.VariavelDAO;

/**
 * Classe que representa a tabela variavel do banco de dados.
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class Variavel {

	private int codigo_variavel;
	private String banco;
	private String nome;
	private String codigo_banco;
	private String atualizacao;
	private boolean padrao;

	private String url = "https://apisidra.ibge.gov.br/values/h/n/t/";

	public Variavel() {

	}

	public Variavel(int codigo_variavel, String codigo_banco) {
		super();
		this.codigo_variavel = codigo_variavel;
		this.codigo_banco = codigo_banco;
	}

	public Variavel(int codigo_variavel, String banco, String nome, String codigo_banco, String atualizacao) {
		this.codigo_variavel = codigo_variavel;
		this.banco = banco;
		this.nome = nome;
		this.codigo_banco = codigo_banco;
		this.atualizacao = atualizacao;
	}

	public Variavel(int codigo_variavel, String banco, String nome, String codigo_banco, String atualizacao,
			boolean padrao) {
		this.codigo_variavel = codigo_variavel;
		this.banco = banco;
		this.nome = nome;
		this.codigo_banco = codigo_banco;
		this.atualizacao = atualizacao;
		this.padrao = padrao;
	}

	public String calcularResultado(String data, int codigo_municipio) {
		String codigo = this.codigo_banco.replaceAll("/p/all", "/p/" + data);
		codigo = codigo.replaceAll("/n1/all", "/n6/" + codigo_municipio);
		String urlBusca = this.url + codigo;
		String resultado = "";
		try {
			String json = Municipio.search(urlBusca);
			resultado = Municipio.buscaJson(json, "V");
		} catch (Exception e) {
			System.out.println("Falha na busca do resultado da variavel: " + this.codigo_variavel + ", "
					+ codigo_municipio + ", " + data);
		}
		return resultado;
	}

	public static void mostrarVariaveis(JTable tableVariaveis, boolean seleciona) {
		try {

			VariavelDAO variavelDAO = new VariavelDAO(ConnectionFactory.recuperarConexao());

			variavelDAO.mostrarVariaveis(tableVariaveis, seleciona);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar tabela de variaveis.");
		}
	}

	public static void mostrarVariaveis(JTable tableVariaveis, List<Variavel> listaVariaveis) {
		try {
			int coluna = 6;

			DefaultTableModel model = (DefaultTableModel) tableVariaveis.getModel();
			String[] nomeColuna = new String[coluna];

			nomeColuna[0] = "Código";
			nomeColuna[1] = "Banco";
			nomeColuna[2] = "Nome";
			nomeColuna[3] = "Código do banco";
			nomeColuna[4] = "Atualização";
			nomeColuna[5] = "Variável padrão";

			model.setColumnIdentifiers(nomeColuna);

			listaVariaveis.forEach(variavel -> {
				String[] linha = { Integer.toString(variavel.codigo_variavel), variavel.banco, variavel.nome,
						variavel.codigo_banco, variavel.atualizacao, Boolean.toString(variavel.padrao) };
				model.addRow(linha);
			});

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar as variáveis na tabela.");
		}
	}

	public static void cadastrarVariavel(int codigo_variavel, String tipo_banco, String nome_variavel,
			String codigo_banco, String atualizacao) {
		try {

			VariavelDAO variavelDAO = new VariavelDAO(ConnectionFactory.recuperarConexao());

			variavelDAO.cadastrarVariavel(codigo_variavel, tipo_banco, nome_variavel, codigo_banco, atualizacao);

		} catch (Exception e) {
			System.out.println("Falha ao cadastrar variaveis.");
		}

	}

	public static Variavel buscaVariavel(Variavel variavel) {
		try {

			VariavelDAO variavelDAO = new VariavelDAO(ConnectionFactory.recuperarConexao());

			return variavelDAO.buscaVariavel(variavel);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar o indicador: " + variavel.getCodigo());
		}
	}

	public static void atualizarVariavel(int codigo_variavel, String tipo_banco, String nome_variavel,
			String codigo_banco, String atualizacao, boolean padrao, int codigo_antigo) {
		try {
			VariavelDAO variavelDAO = new VariavelDAO(ConnectionFactory.recuperarConexao());

			variavelDAO.atualizarVariavel(codigo_variavel, tipo_banco, nome_variavel, codigo_banco, atualizacao, padrao,
					codigo_antigo);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao atualizar a variável: " + codigo_variavel);
		}
	}

	public static void excluir(List<Variavel> listaVariaveis) {
		try {

			VariavelDAO variavelDAO = new VariavelDAO(ConnectionFactory.recuperarConexao());

			for (Variavel variavel : listaVariaveis) {
				variavelDAO.excluir(variavel);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public int getCodigo() {
		return codigo_variavel;
	}

	public String getAtualizacao() {
		return atualizacao;
	}
	
	public String getCodigo_banco() {
		return codigo_banco;
	}

	public int getCodigo_variavel() {
		return codigo_variavel;
	}

	public String getBanco() {
		return banco;
	}

	public String getNome() {
		return nome;
	}

	public boolean isPadrao() {
		return padrao;
	}
	
	public String getTipoBanco() {
		return banco;
	}
}
