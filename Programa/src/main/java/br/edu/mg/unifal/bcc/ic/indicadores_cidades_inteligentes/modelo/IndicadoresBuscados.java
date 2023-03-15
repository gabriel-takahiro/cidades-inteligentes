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

/**
 * Esta classe fornece uma maneira de encapsular e passar as informações do
 * indicador como parâmetro
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
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

	/**
	 * 
	 * @param codigo_indicador código do indicador
	 * @param ods              ods do indicador
	 * @param meta             meta do indicador
	 * @param nome_indicador   nome do indicador
	 * @param codigo_municipio código do município
	 * @param nome_municipio   nome do município
	 * @param data             ano
	 * @param resultado        resultado do indicador
	 * @param nome_uf          nome da unidade federativa
	 * @param metodo_calculo   método de cálculo do indicador
	 * @param data_variaveis   ano utilizado para encontrar as variáveis
	 * @param valor_oficial    true caso o valor do indicador seja oficial, ou seja,
	 *                         os valores obtidos apenas de fontes confiáveis como a
	 *                         API SIDRA
	 * @param padrao           true caso o indicador seja padrão
	 */
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

	/**
	 * 
	 * @return true caso o indicador seja padrão
	 */
	public boolean isPadrao() {
		return padrao;
	}

	/**
	 * 
	 * @return true caso o valor do indicador seja oficial
	 */
	public boolean isValor_oficial() {
		return valor_oficial;
	}

	/**
	 * 
	 * @return código do indicador
	 */
	public int getCodigo_indicador() {
		return codigo_indicador;
	}

	/**
	 * 
	 * @return ods
	 */
	public int getOds() {
		return ods;
	}

	/**
	 * 
	 * @return meta
	 */
	public String getMeta() {
		return meta;
	}

	/**
	 * 
	 * @return nome do indicador
	 */
	public String getNome_indicador() {
		return nome_indicador;
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
	 * @return nome do município
	 */
	public String getNome_municipio() {
		return nome_municipio;
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
	 * @return resultado do indicador
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * 
	 * @return nome da unidade federativa
	 */
	public String getNome_uf() {
		return nome_uf;
	}

	/**
	 * 
	 * @return método de cálculo do indicador
	 */
	public String getMetodo_calculo() {
		return metodo_calculo;
	}

	/**
	 * 
	 * @return ano utilizado para encontrar as variáveis
	 */
	public String getData_variaveis() {
		return data_variaveis;
	}

}
