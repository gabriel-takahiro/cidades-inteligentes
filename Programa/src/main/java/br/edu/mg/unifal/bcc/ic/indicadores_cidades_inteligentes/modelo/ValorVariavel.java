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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo;

import java.sql.Connection;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.DataDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao.ValorVariavelDAO;

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
	
	
	public ValorVariavel() {}
	
	/**
	 * 
	 * @param codigo_variavel código da variável
	 * @param data ano
	 */
	public ValorVariavel(int codigo_variavel, String data) {
		this.codigo_variavel = codigo_variavel;
		this.data = data;
	}
	
	/**
	 * 
	 * @param valor valor da variável
	 * @param valor_real true caso o valor seja oficial
	 */
	public ValorVariavel(String valor, boolean valor_real) {
		this.valor = valor;
		this.valor_oficial = valor_real;
	}
	
	/**
	 * 
	 * @param valor valor da variável
	 * @param data ano
	 */
	public ValorVariavel(String valor, String data) {
		this.valor = valor;
		this.data = data;
	}
	
	/**
	 * 
	 * @param valor valor da variável
	 * @param valor_real true caso o valor seja oficial
	 * @param data ano
	 */
	public ValorVariavel(String valor, boolean valor_real, String data) {
		this.valor = valor;
		this.valor_oficial = valor_real;
		this.data = data;
	}

	/**
	 * 
	 * @param codigo_variavel código da variável
	 * @param codigo_municipio código do município
	 * @param data ano
	 * @param valor valor da variável
	 */
	public ValorVariavel(int codigo_variavel, int codigo_municipio, String data, String valor) {
		this.codigo_variavel = codigo_variavel;
		this.codigo_municipio = codigo_municipio;
		this.data = data;
		this.valor = valor;
	}
	
	

	public ValorVariavel(int codigo_municipio, String data, String valor) {
		this.codigo_municipio = codigo_municipio;
		this.data = data;
		this.valor = valor;
	}

	/**
	 * Insere o valor da variável, caso o valor da variável ja esteja cadastrada no banco de dados, o valor é atualizado 
	 * @param codigo_variavel código da variável
	 * @param codigo_municipio código do município
	 * @param data ano
	 * @param valor valor da variável
	 */
	public static void inserirUpdateValorVariavel(int codigo_variavel, int codigo_municipio, String data, String resultado, boolean atualizado, boolean valor_oficial) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			new DataDAO(connection).atualizaData(data);
			
			ValorVariavelDAO valorVariavelDAO = new ValorVariavelDAO(connection);

			valorVariavelDAO.inserirUpdateValorVariavel(codigo_variavel, codigo_municipio, data, resultado, atualizado, valor_oficial);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao inserir valor variavel: " + codigo_variavel + " Valor: " + resultado);
		}
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
	 * @return código do município
	 */
	public int getCodigo_municipio() {
		return codigo_municipio;
	}

	/**
	 * 
	 * @return ano
	 */
	public String getData() {
		return data;
	}

	/**
	 * 
	 * @return valor da variável
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * 
	 * @return true caso o valor da variável seja oficial
	 */
	public boolean isValor_oficial() {
		return valor_oficial;
	}

}
