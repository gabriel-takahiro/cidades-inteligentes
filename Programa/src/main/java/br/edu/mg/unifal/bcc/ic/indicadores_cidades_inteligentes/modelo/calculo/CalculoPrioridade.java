package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo;

import java.util.List;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.CalculoPrioridadeSomaSub;

public class CalculoPrioridade {

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
