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
package br.edu.mg.unifal.bcc.sgici.calculo;

/**
 * Esta classe fornece uma maneira de encapsular e passar as informações da
 * busca das variáveis como parâmetro.
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 *
 */
public class ParametrosBuscaValorVariavel {
	private String data;
	private int codigoMunicipio;
	private int anosRetroativos;
	private boolean recalcular;

	/**
	 * Construtor criado com os valores de ano, anos retroativos e recalcular.
	 * 
	 * @param data            ano utilizado para a busca das variáveis
	 * @param anosRetroativos quantidade de anos retroativos que devem ser
	 *                        considerados no cálculo dos indicadores.
	 * @param recalcular      um booleano que indica se os indicadores devem ser
	 *                        recalculados mesmo que já tenham sido calculados
	 *                        anteriormente.
	 */
	public ParametrosBuscaValorVariavel(String data, int anosRetroativos, boolean recalcular) {
		this.data = data;
		this.anosRetroativos = anosRetroativos;
		this.recalcular = recalcular;
	}

	/**
	 * Construtor criado com os valores de ano, código do município, anos
	 * retroativos e recalcular.
	 * 
	 * @param data            ano utilizado para a busca das variáveis
	 * @param codigoMunicipio código do município em questão
	 * @param anosRetroativos quantidade de anos retroativos que devem ser
	 *                        considerados no cálculo dos indicadores.
	 * @param recalcular      um booleano que indica se os indicadores devem ser
	 *                        recalculados mesmo que já tenham sido calculados
	 *                        anteriormente.
	 */
	public ParametrosBuscaValorVariavel(String data, int codigoMunicipio, int anosRetroativos, boolean recalcular) {
		this.data = data;
		this.codigoMunicipio = codigoMunicipio;
		this.anosRetroativos = anosRetroativos;
		this.recalcular = recalcular;
	}

	/**
	 * Retorna o ano
	 * 
	 * @return ano
	 */
	public String getData() {
		return data;
	}

	/**
	 * Retorna o código do município
	 * 
	 * @return código do município
	 */
	public int getCodigoMunicipio() {
		return codigoMunicipio;
	}

	/**
	 * Retorna a quantidade de anos retroativos
	 * 
	 * @return anos retroativos
	 */
	public int getAnosRetroativos() {
		return anosRetroativos;
	}

	/**
	 * Retorna se é necessário recalcular
	 * 
	 * @return recalcular
	 */
	public boolean isRecalcular() {
		return recalcular;
	}

}
