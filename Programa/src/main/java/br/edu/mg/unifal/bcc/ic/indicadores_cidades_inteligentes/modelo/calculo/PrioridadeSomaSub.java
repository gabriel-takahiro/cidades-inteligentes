package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo;

import java.util.List;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.CalculoPrioridadeSomaSub;

public class PrioridadeSomaSub {

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
