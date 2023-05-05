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
 * Esta classe fornece uma maneira de encapsular e passar as informações do
 * cálculo do indicador como parâmetro.
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 *
 */
public class CalculoDataVariaveis {
	private String calculo;
	private String dataVariaveis;
	private boolean oficial;

	/**
	 * Construtor utilizando todos os valores.
	 * 
	 * @param calculo       cálculo matemático do indicador obtido substituindo no
	 *                      método de cálculo os valores das variáveis por seus
	 *                      respectivos valores.
	 * @param dataVariaveis Uma String contendo a variável utilizada seguida pelo
	 *                      ano em que ela foi buscada. Ex: "1/2010/224/2010",
	 *                      variável 1 foi buscada no ano de 2010 assim como a
	 *                      variável 224.
	 * @param oficial       Indica se os valores obtidos dessas variáveis são
	 *                      oficiais ou não, ou seja, se são provenientes de bases
	 *                      de dados oficiais.
	 */
	public CalculoDataVariaveis(String calculo, String dataVariaveis, boolean oficial) {
		this.calculo = calculo;
		this.dataVariaveis = dataVariaveis;
		this.oficial = oficial;
	}

	/**
	 * Retorna o valor oficial
	 * 
	 * @return valor oficial
	 */
	public boolean isOficial() {
		return oficial;
	}

	/**
	 * Insere o valor oficial
	 * 
	 * @param oficial valor oficial a ser inserido
	 */
	public void setOficial(boolean oficial) {
		this.oficial = oficial;
	}

	/**
	 * Retorna o cálculo do indicador
	 * 
	 * @return cálculo do indicador
	 */
	public String getCalculo() {
		return calculo;
	}

	/**
	 * Insere o cálculo do indicador
	 * 
	 * @param calculo cálculo do indicador
	 */
	public void setCalculo(String calculo) {
		this.calculo = calculo;
	}

	/**
	 * Retorna a data das variáveis
	 * 
	 * @return data das variáveis
	 */
	public String getDataVariaveis() {
		return dataVariaveis;
	}

	/**
	 * Insere a data das variáveis
	 * 
	 * @param dataVariaveis data das variáveis
	 */
	public void setDataVariaveis(String dataVariaveis) {
		this.dataVariaveis = dataVariaveis;
	}

}
