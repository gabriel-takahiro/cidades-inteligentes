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
 * Classe responsável por realizar o cálculo do indicador respeitando a ordem de
 * prioridade dos operadores
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public class CalculoPrioridade {

	/**
	 * Realiza o cálculo da parte entre parênteses no método do cálculo
	 * @param compostoCalculo parâmetros encapsulados para calcular o indicador
	 * @param ponteiro posição do ponteiro
	 * @return valor do cálculo dentro dos parênteses
	 */
	public static double calcular(CompostoCalculo compostoCalculo, int ponteiro) {
		double calculo = 0;
		double valor1 = 0;
		List<String> seq = compostoCalculo.getSeqCalculo();
		for (int posicao = ponteiro; posicao < seq.size(); posicao++) {
			switch (seq.get(posicao)) {
			case "×":
				// Se o próximo elemento for um abre colchetes, pega o valor da constante
				// multiplica e devolve o ponteiro 3 posições a frente
				if (seq.get(posicao + 1).equals("[")) {
					calculo *= Double.parseDouble(seq.get(posicao + 2));
					posicao += 3;
					// Se o próximo elemento for um abre chaves, pega o valor do indicador
					// multiplica e devolve o ponteiro 3 posições a frente
				} else if (seq.get(posicao + 1).equals("{")) {
					calculo *= CalculoIndicador.calcularIndicador(Integer.parseInt(seq.get(posicao + 2)),
							compostoCalculo);
					posicao += 3;
					// Se o próximo elemento for um abre parênteses, chama a função de
					// CalculoPrioridade e o PonteiroPrioridade
				} else if (seq.get(posicao + 1).equals("(")) {
					calculo *= calcular(compostoCalculo, posicao + 2);
					posicao = PonteiroPrioridade.calcular(seq, posicao + 2);
					if (posicao == -1) {
						posicao = seq.size();
						System.out.println("Posicao do ponteiro errada");
					}
				} else {
					try {
						calculo *= Double.parseDouble(compostoCalculo.getValorVariavelDAO()
								.buscaValorVariavel(compostoCalculo, Integer.parseInt(seq.get(posicao + 1))));
					} catch (Exception e) {
						posicao = seq.size();
						System.out.println("Posicao do ponteiro errada");
					}
					posicao++;
				}
				break;

			case "x":
				// Se o próximo elemento for um abre colchetes, pega o valor da constante
				// multiplica e devolve o ponteiro 3 posições a frente
				if (seq.get(posicao + 1).equals("[")) {
					calculo *= Double.parseDouble(seq.get(posicao + 2));
					posicao += 3;
					// Se o próximo elemento for um abre chaves, pega o valor do indicador
					// multiplica e devolve o ponteiro 3 posições a frente
				} else if (seq.get(posicao + 1).equals("{")) {
					calculo *= CalculoIndicador.calcularIndicador(Integer.parseInt(seq.get(posicao + 2)),
							compostoCalculo);
					posicao += 3;
					// Se o próximo elemento for um abre parênteses, chama a função de
					// CalculoPrioridade e o PonteiroPrioridade
				} else if (seq.get(posicao + 1).equals("(")) {
					calculo *= calcular(compostoCalculo, posicao + 2);
					posicao = PonteiroPrioridade.calcular(seq, posicao + 2);
					if (posicao == -1) {
						posicao = seq.size();
						System.out.println("Posicao do ponteiro errada");
					}
				} else {
					try {
						calculo *= Double.parseDouble(compostoCalculo.getValorVariavelDAO()
								.buscaValorVariavel(compostoCalculo, Integer.parseInt(seq.get(posicao + 1))));
					} catch (Exception e) {
						posicao = seq.size();
						System.out.println("Posicao do ponteiro errada");
					}
					posicao++;
				}
				break;

			case "/":
				// Se o próximo elemento for um abre colchetes, pega o valor da constante
				// multiplica e devolve o ponteiro 3 posições a frente
				if (seq.get(posicao + 1).equals("[")) {
					calculo /= Double.parseDouble(seq.get(posicao + 2));
					posicao += 3;
					// Se o próximo elemento for um abre chaves, pega o valor do indicador
					// multiplica e devolve o ponteiro 3 posições a frente
				} else if (seq.get(posicao + 1).equals("{")) {
					calculo /= CalculoIndicador.calcularIndicador(Integer.parseInt(seq.get(posicao + 2)),
							compostoCalculo);
					posicao += 3;
					// Se o próximo elemento for um abre parênteses, chama a função de
					// CalculoPrioridade e o PonteiroPrioridade
				} else if (seq.get(posicao + 1).equals("(")) {
					calculo /= calcular(compostoCalculo, posicao + 2);
					posicao = PonteiroPrioridade.calcular(seq, posicao + 2);
					if (posicao == -1) {
						posicao = seq.size();
						System.out.println("Posicao do ponteiro errada");
					}
				} else {
					try {
						calculo /= Double.parseDouble(compostoCalculo.getValorVariavelDAO()
								.buscaValorVariavel(compostoCalculo, Integer.parseInt(seq.get(posicao + 1))));
					} catch (Exception e) {
						posicao = seq.size();
						System.out.println("Posicao do ponteiro errada");
					}
					posicao++;
				}
				break;

			case "+":
				if (seq.get(posicao + 1).equals("[") || seq.get(posicao + 1).equals("{")) {
					if (seq.get(posicao + 1).equals("[")) {
						valor1 = Double.parseDouble(seq.get(posicao + 2));
					} else {
						valor1 = CalculoIndicador.calcularIndicador(Integer.parseInt(seq.get(posicao + 2)),
								compostoCalculo);
					}
					posicao += 3;
					CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = PrioridadeSomaSub.calcular(compostoCalculo,
							posicao, valor1);
					calculo += calculoPrioridadeSomaSub.getCalculo();
					posicao = calculoPrioridadeSomaSub.getPosicao();
				} else if (seq.get(posicao + 1).equals("(")) {
					valor1 = calcular(compostoCalculo, posicao + 2);
					posicao = PonteiroPrioridade.calcular(seq, posicao + 2);
					if (posicao == -1) {
						posicao = seq.size();
						System.out.println("Posicao do ponteiro errada");
					}
					CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = PrioridadeSomaSub.calcular(compostoCalculo,
							posicao, valor1);
					calculo += calculoPrioridadeSomaSub.getCalculo();
					posicao = calculoPrioridadeSomaSub.getPosicao();
				} else {
					valor1 = Double.parseDouble(compostoCalculo.getValorVariavelDAO()
							.buscaValorVariavel(compostoCalculo, Integer.parseInt(seq.get(posicao + 1))));

					CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = PrioridadeSomaSub.calcular(compostoCalculo,
							posicao + 1, valor1);
					calculo += calculoPrioridadeSomaSub.getCalculo();
					posicao = calculoPrioridadeSomaSub.getPosicao();
				}
				break;

			case "-":
				if (seq.get(posicao + 1).equals("[") || seq.get(posicao + 1).equals("{")) {
					if (seq.get(posicao + 1).equals("[")) {
						valor1 = Double.parseDouble(seq.get(posicao + 2));
					} else {
						valor1 = CalculoIndicador.calcularIndicador(Integer.parseInt(seq.get(posicao + 2)),
								compostoCalculo);
					}
					posicao += 3;
					CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = PrioridadeSomaSub.calcular(compostoCalculo,
							posicao, valor1);
					calculo -= calculoPrioridadeSomaSub.getCalculo();
					posicao = calculoPrioridadeSomaSub.getPosicao();
				} else if (seq.get(posicao + 1).equals("(")) {
					valor1 = calcular(compostoCalculo, posicao + 2);
					posicao = PonteiroPrioridade.calcular(seq, posicao + 2);
					if (posicao == -1) {
						posicao = seq.size();
						System.out.println("Posicao do ponteiro errada");
					}
					CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = PrioridadeSomaSub.calcular(compostoCalculo,
							posicao, valor1);
					calculo -= calculoPrioridadeSomaSub.getCalculo();
					posicao = calculoPrioridadeSomaSub.getPosicao();
				} else {
					valor1 = Double.parseDouble(compostoCalculo.getValorVariavelDAO()
							.buscaValorVariavel(compostoCalculo, Integer.parseInt(seq.get(posicao + 1))));

					CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = PrioridadeSomaSub.calcular(compostoCalculo,
							posicao + 1, valor1);
					calculo -= calculoPrioridadeSomaSub.getCalculo();
					posicao = calculoPrioridadeSomaSub.getPosicao();
				}
				break;

			case "[":
				calculo = Double.parseDouble(seq.get(posicao + 1));
				posicao += 2;
				break;

			case "(":
				calculo = calcular(compostoCalculo, posicao + 1);
				posicao = PonteiroPrioridade.calcular(seq, posicao + 1);
				if (posicao == -1) {
					posicao = seq.size();
					System.out.println("Posicao do ponteiro errada");
				}
				break;

			case "{":
				calculo = CalculoIndicador.calcularIndicador(Integer.parseInt(seq.get(posicao + 1)), compostoCalculo);
				posicao += 2;
				break;

			case ")":
				posicao = seq.size();
				break;

			default:
				try {
					calculo = Double.parseDouble(compostoCalculo.getValorVariavelDAO()
							.buscaValorVariavel(compostoCalculo, Integer.parseInt(seq.get(posicao))));
				} catch (Exception e) {
					posicao = seq.size();
					System.out.println("Erro dentro prioridade");
				}
				break;
			}
		}
		return calculo;
	}
}
