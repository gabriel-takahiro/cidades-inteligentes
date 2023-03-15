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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo;

import java.util.List;

/**
 * Classe responsável por trabalhar com o ponteiro do método de cálculo
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public class PonteiroPrioridade {

	/**
	 * Analisa e devolve a posição do pointeiro após realizar o cálculo dentros dos
	 * parênteses
	 * 
	 * @param seqCalculo método de cálculo
	 * @param posicao posição do ponteiro
	 * @return posição do ponteiro após cálcular o que está dentro dos parênteses
	 */
	public static int calcular(List<String> seqCalculo, int posicao) {// Função que devolve a posição do ponteiro
		// após resolver o que está dentro do parênteses
		int k = 1;
		for (int j = posicao; j < seqCalculo.size(); j++) {
			if (seqCalculo.get(j).equals("(")) {
				k++;
			}
			if (seqCalculo.get(j).equals(")")) {
				k--;
				if (k == 0) {
					return j;
				}
			}
		}
		return -1;
	}
}
