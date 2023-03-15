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

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por realizar o desmembramento do método de cálculo em uma
 * lista de elementos
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public class SequenciaCalculo {

	/**
	 * Realiza o desmembramento do método de cálculo em uma lista contendo cada um
	 * de seus elementos
	 * 
	 * @param metodoCalculo método de cálculo
	 * @return lista contendo o cada elemento do método de cálculo
	 */
	// Função que separa cada elemento do método do cálculo
	public static List<String> lista(String metodoCalculo) {
		String calculo = metodoCalculo.replace(" ", "");
		String valor = "";
		String aux;
		ArrayList<String> seqCalculo = new ArrayList<String>();
		for (int i = 0; i < calculo.length(); i++) {
			aux = calculo.substring(i, i + 1);
			if (aux.equals("×") || aux.equals("x") || aux.equals("/") || aux.equals("(") || aux.equals(")")
					|| aux.equals("+") || aux.equals("-") || aux.equals("[") || aux.equals("]") || aux.equals("{")
					|| aux.equals("}")) {
				if (!valor.isEmpty()) {
					seqCalculo.add(valor);
				}
				seqCalculo.add(aux);
				valor = "";
			} else {
				valor = valor.concat(aux);
			}
		}
		if (!valor.isEmpty()) {
			seqCalculo.add(valor);
		}
		return seqCalculo;
	}
}
