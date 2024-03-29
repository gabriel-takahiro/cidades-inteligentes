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

import java.sql.Connection;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.edu.mg.unifal.bcc.sgici.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.VariavelDAO;

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

	/**
	 * Construtor padrão
	 */
	public Variavel() {

	}

	/**
	 * 
	 * @param codigo_variavel código da variável
	 * @param codigo_banco    código do banco
	 */
	public Variavel(int codigo_variavel, String codigo_banco) {
		this.codigo_variavel = codigo_variavel;
		this.codigo_banco = codigo_banco;
	}

	/**
	 * 
	 * @param codigo_variavel código da variável
	 * @param banco           nome do banco
	 * @param nome            nome da variável
	 * @param codigo_banco    código do banco
	 * @param atualizacao     período de atualização
	 */
	public Variavel(int codigo_variavel, String banco, String nome, String codigo_banco, String atualizacao) {
		this.codigo_variavel = codigo_variavel;
		this.banco = banco;
		this.nome = nome;
		this.codigo_banco = codigo_banco;
		this.atualizacao = atualizacao;
	}

	/**
	 * 
	 * @param codigo_variavel código da variável
	 * @param banco           nome do banco
	 * @param nome            nome da variável
	 * @param codigo_banco    código do banco
	 * @param atualizacao     período de atualização
	 * @param padrao          true caso a variável seja padrão
	 */
	public Variavel(int codigo_variavel, String banco, String nome, String codigo_banco, String atualizacao,
			boolean padrao) {
		this.codigo_variavel = codigo_variavel;
		this.banco = banco;
		this.nome = nome;
		this.codigo_banco = codigo_banco;
		this.atualizacao = atualizacao;
		this.padrao = padrao;
	}

	/**
	 * 
	 * @param codigo_variavel código da variável
	 * @param banco           nome do banco
	 * @param codigo_banco    código do banco
	 * @param atualizacao     período de atualização
	 */
	public Variavel(int codigo_variavel, String banco, String codigo_banco, String atualizacao) {
		this.codigo_variavel = codigo_variavel;
		this.banco = banco;
		this.codigo_banco = codigo_banco;
		this.atualizacao = atualizacao;
	}

	/**
	 * Faz a busca do valor da variável na base de dados que ela se encontra
	 * 
	 * @param data             ano
	 * @param codigo_municipio código do município
	 * @return valor da variável
	 */
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

	/**
	 * Preenche uma tabela com todas as variáveis do banco de dados
	 * 
	 * @param tableVariaveis tabela a ser utilizada
	 * @param seleciona      true caso seja necesário uma coluna extra que permite
	 *                       selecionar as ods
	 */
	public static void mostrarVariaveis(JTable tableVariaveis, boolean seleciona) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			VariavelDAO variavelDAO = new VariavelDAO(connection);

			variavelDAO.mostrarVariaveis(tableVariaveis, seleciona);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar tabela de variaveis.");
		}
	}

	/**
	 * Preenche uma tabela apenas com as variáveis presentes na lista de variáveis
	 * passada por parâmetro
	 * 
	 * @param tableVariaveis tabela a ser utilizada
	 * @param listaVariaveis lista de variáveis selecionadas
	 */
	public static void mostrarVariaveis(JTable tableVariaveis, List<Variavel> listaVariaveis) {
		try {
			if(listaVariaveis.isEmpty()) {
				throw new RuntimeException("Não há variáveis");
			}
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

	/**
	 * Cadastra uma variável no banco de dados
	 * 
	 * @param codigo_variavel código da variável
	 * @param tipo_banco      tipo de banco
	 * @param nome_variavel   nome da variável
	 * @param codigo_banco    código do banco
	 * @param atualizacao     período de atualização
	 */
	public static void cadastrarVariavel(int codigo_variavel, String tipo_banco, String nome_variavel,
			String codigo_banco, String atualizacao) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			VariavelDAO variavelDAO = new VariavelDAO(connection);

			variavelDAO.cadastrarVariavel(codigo_variavel, tipo_banco, nome_variavel, codigo_banco, atualizacao);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Busca todas as informações de uma variável a partir de um variável contendo
	 * apenas algumas informações
	 * 
	 * @param variavel variável contendo apenas algumas informações
	 * @return variável contendo todas as informações
	 */
	public static Variavel buscaVariavel(Variavel variavel) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			VariavelDAO variavelDAO = new VariavelDAO(connection);

			return variavelDAO.buscaVariavel(variavel);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar o indicador: " + variavel.getCodigo_variavel());
		}
	}

	/**
	 * Atualiza uma variável no banco de dados com as informações passadas como parâmetro
	 * @param codigo_variavel código da variável
	 * @param tipo_banco tipo de banco
	 * @param nome_variavel nome da variável
	 * @param codigo_banco código do banco
	 * @param atualizacao período de atualização
	 * @param padrao true caso a variável seja padrão
	 * @param codigo_antigo código da variável a ser atualizada
	 */
	public static void atualizarVariavel(int codigo_variavel, String tipo_banco, String nome_variavel,
			String codigo_banco, String atualizacao, boolean padrao, int codigo_antigo) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){
			VariavelDAO variavelDAO = new VariavelDAO(connection);

			variavelDAO.atualizarVariavel(codigo_variavel, tipo_banco, nome_variavel, codigo_banco, atualizacao, padrao,
					codigo_antigo);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Exclui uma lista de variáveis do banco de dados
	 * 
	 * @param listaVariaveis lista de variáveis a serem excluídas
	 */
	public static void excluir(List<Variavel> listaVariaveis) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			VariavelDAO variavelDAO = new VariavelDAO(connection);

			for (Variavel variavel : listaVariaveis) {
				variavelDAO.excluir(variavel);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 
	 * @return período de atualização
	 */
	public String getAtualizacao() {
		return atualizacao;
	}

	/**
	 * 
	 * @return código do banco
	 */
	public String getCodigo_banco() {
		return codigo_banco;
	}

	/**
	 * 
	 * @return código da variável
	 */
	public int getCodigo_variavel() {
		return codigo_variavel;
	}

	/**
	 * 
	 * @return nome da variável
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * 
	 * @return true caso a variável seja padrão
	 */
	public boolean isPadrao() {
		return padrao;
	}

	/**
	 * 
	 * @return tipo de banco
	 */
	public String getTipoBanco() {
		return banco;
	}

	/**
	 * Verifica se todas as variáveis estão cadastradas no banco de dados
	 * @param listaVariaveis lista de variáveis que será analisada
	 */
	public static void verificaVariaveis(List<Integer> listaVariaveis) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			VariavelDAO variavelDAO = new VariavelDAO(connection);

			variavelDAO.verificaVariaveis(listaVariaveis);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
