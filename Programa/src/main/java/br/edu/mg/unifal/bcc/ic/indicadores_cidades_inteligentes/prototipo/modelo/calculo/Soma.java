package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.calculo;

import java.util.List;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.CalculoPrioridadeSomaSub;

public class Soma extends Calculo {

	public Soma(Calculo proximo) {
		super(proximo);
	}

	public CompostoCalculo efetuarCalculo(CompostoCalculo compostoCalculo) {
		int posicao = compostoCalculo.getPosicao();
		double calculo = compostoCalculo.getCalculo();
		double valor1 = compostoCalculo.getValor1();
		List<String> seq = compostoCalculo.getSeqCalculo();
		boolean deuCerto = true;
		String mensagemErro = "";
		if (seq.get(posicao + 1).equals("[") || seq.get(posicao + 1).equals("{")) {
			if (seq.get(posicao + 1).equals("[")) {
				valor1 = Double.parseDouble(seq.get(posicao + 2));
			} else {
				valor1 = CalculoIndicador.calcularIndicador(Integer.parseInt(seq.get(posicao + 2)), compostoCalculo);
			}
			posicao += 3;
			CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = PrioridadeSomaSub.calcular(compostoCalculo, posicao,
					valor1);
			calculo += calculoPrioridadeSomaSub.getCalculo();
			posicao = calculoPrioridadeSomaSub.getPosicao();
		} else if (seq.get(posicao + 1).equals("(")) {
			valor1 = CalculoPrioridade.calcular(compostoCalculo, posicao + 2);
			posicao = PonteiroPrioridade.calcular(seq, posicao + 2);
			if (posicao == -1) {
				deuCerto = false;
				posicao = seq.size();
				mensagemErro = "Erro na soma falha ao calcular a prioridade.";
			} else {
				CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = PrioridadeSomaSub.calcular(compostoCalculo, posicao,
						valor1);
				calculo += calculoPrioridadeSomaSub.getCalculo();
				posicao = calculoPrioridadeSomaSub.getPosicao();
			}
		} else {
			valor1 = Double.parseDouble(compostoCalculo.getValorVariavelDAO().buscaValorVariavel(compostoCalculo,
					Integer.parseInt(seq.get(posicao + 1))));

			CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = PrioridadeSomaSub.calcular(compostoCalculo, posicao + 1,
					valor1);
			calculo += calculoPrioridadeSomaSub.getCalculo();
			posicao = calculoPrioridadeSomaSub.getPosicao();
		}

		compostoCalculo.setPosicao(posicao);
		compostoCalculo.setCalculo(calculo);
		compostoCalculo.setValor1(valor1);

		if (deuCerto) {
			return compostoCalculo;
		}

		throw new RuntimeException(mensagemErro);
	}

	public String deveAplicar(List<String> seqCalculo, int posicao) {
		return "+";
	}
}
