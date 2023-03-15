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

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.CalculoPrioridadeSomaSub;

/**
 * Classe responsável por realizar o cálculo de prioridade após encontrar uma
 * soma "+" ou subtração "-".
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public class PrioridadeSomaSub {

	/**
	 * Realiza o cálculo de prioridade após encontrar uma soma ou subtração
	 * 
	 * @param compostoCalculo parâmetros encapsulados para calcular o indicador
	 * @param posicaoInicial  posição inicial do ponteiro
	 * @param valor1          valor a ser somado ou subtraído após realizar o
	 *                        cálculo de prioridade dos indicadores
	 * @return retorna um objeto do tipo CalculoPrioridadeSomaSub que encapsula os
	 *         valores calculados
	 */
	public static CalculoPrioridadeSomaSub calcular(CompostoCalculo compostoCalculo, int posicaoInicial,
			double valor1) {
		int posicao = posicaoInicial;
		List<String> seq = compostoCalculo.getSeqCalculo();
		try {
			// Se o próximo elemento após o parênteses for de multiplicação ou divisão ele
			// deverá ser executado primeiro que a soma
			if (seq.get(posicao + 1).equals("×") || seq.get(posicao + 1).equals("x")
					|| seq.get(posicao + 1).equals("/")) {
				if (seq.get(posicao + 2).equals("(") || seq.get(posicao + 2).equals("[")
						|| seq.get(posicao + 2).equals("{")) {
					// Se for constante ou indicador
					if (seq.get(posicao + 2).equals("[") || seq.get(posicao + 2).equals("{")) {
						if (seq.get(posicao + 1).equals("×") || seq.get(posicao + 1).equals("x")) {
							if (seq.get(posicao + 2).equals("[")) {
								CalculoPrioridadeSomaSub prioridade = PrioridadeSomaSub.calcular(compostoCalculo,
										posicao + 4, Double.parseDouble(seq.get(posicao + 3)));
								valor1 *= prioridade.getCalculo();
								posicao = prioridade.getPosicao();
							} else {
								CalculoPrioridadeSomaSub prioridade = PrioridadeSomaSub.calcular(compostoCalculo,
										posicao + 4, CalculoIndicador.calcularIndicador(
												Integer.parseInt(seq.get(posicao + 3)), compostoCalculo));
								valor1 *= prioridade.getCalculo();
								posicao = prioridade.getPosicao();
							}
						} else {
							if (seq.get(posicao + 2).equals("[")) {
								CalculoPrioridadeSomaSub prioridade = PrioridadeSomaSub.calcular(compostoCalculo,
										posicao + 4, Double.parseDouble(seq.get(posicao + 3)));
								valor1 /= prioridade.getCalculo();
								posicao = prioridade.getPosicao();
							} else {
								CalculoPrioridadeSomaSub prioridade = PrioridadeSomaSub.calcular(compostoCalculo,
										posicao + 4, CalculoIndicador.calcularIndicador(
												Integer.parseInt(seq.get(posicao + 3)), compostoCalculo));
								valor1 /= prioridade.getCalculo();
								posicao = prioridade.getPosicao();
							}
						}
						// Se for parênteses
					} else {
						double valor2 = CalculoPrioridade.calcular(compostoCalculo, posicao + 3);
						int posicao2 = PonteiroPrioridade.calcular(seq, posicao + 3);
						if (seq.get(posicao + 1).equals("×") || seq.get(posicao + 1).equals("x")) {
							CalculoPrioridadeSomaSub prioridade = PrioridadeSomaSub.calcular(compostoCalculo, posicao2,
									valor2);
							valor1 *= prioridade.getCalculo();
							posicao = prioridade.getPosicao();
						} else {
							CalculoPrioridadeSomaSub prioridade = PrioridadeSomaSub.calcular(compostoCalculo, posicao2,
									valor2);
							valor1 /= prioridade.getCalculo();
							posicao = prioridade.getPosicao();
						}
					}
					// Se for o código da variável
				} else {
					if (seq.get(posicao + 1).equals("×") || seq.get(posicao + 1).equals("x")) {
						CalculoPrioridadeSomaSub prioridade = PrioridadeSomaSub.calcular(compostoCalculo, posicao + 2,
								Double.parseDouble(compostoCalculo.getValorVariavelDAO()
										.buscaValorVariavel(compostoCalculo, Integer.parseInt(seq.get(posicao + 2)))));
						valor1 *= prioridade.getCalculo();
						posicao = prioridade.getPosicao();
					} else {
						CalculoPrioridadeSomaSub prioridade = PrioridadeSomaSub.calcular(compostoCalculo, posicao + 2,
								Double.parseDouble(compostoCalculo.getValorVariavelDAO()
										.buscaValorVariavel(compostoCalculo, Integer.parseInt(seq.get(posicao + 2)))));
						valor1 /= prioridade.getCalculo();
						posicao = prioridade.getPosicao();
					}
				}
			} else {
				CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = new CalculoPrioridadeSomaSub(valor1, posicao);
				return calculoPrioridadeSomaSub;
			}
			CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = new CalculoPrioridadeSomaSub(valor1, posicao);
			return calculoPrioridadeSomaSub;

		} catch (Exception e) {
			CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = new CalculoPrioridadeSomaSub(valor1, posicao);
			return calculoPrioridadeSomaSub;
		}
	}
}
