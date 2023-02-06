package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.calculo;

import java.util.List;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.CalculoIndicador;

public class Multiplicacao extends Calculo {

	public Multiplicacao(Calculo proximo) {
		super(proximo);
	}

	public CompostoCalculo efetuarCalculo(CompostoCalculo compostoCalculo) {
		int posicao = compostoCalculo.getPosicao();
		double calculo = compostoCalculo.getCalculo();
		List<String> seq = compostoCalculo.getSeqCalculo();
		boolean deuCerto = true;
		String mensagemErro = "";
		if (seq.get(posicao + 1).equals("[")) { // Se o próximo elemento for um abre
			// colchetes, pega o valor da constante multiplica e devolve o ponteiro 3
			// posições a frente
			calculo *= Double.parseDouble(seq.get(posicao + 2));
			posicao += 3;
		} else if (seq.get(posicao + 1).equals("{")) {// Se o próximo elemento for um abre
			// chaves, pega o valor do indicador multiplica e devolve o ponteiro 3 posições
			// a frente
			calculo *= CalculoIndicador.calcularIndicador(Integer.parseInt(seq.get(posicao + 2)), compostoCalculo);
			posicao += 3;
		} else if (seq.get(posicao + 1).equals("(")) {// Se o próximo elemento for um abre
			// parênteses, chama a função de CalculoPrioridade e o PonteiroPrioridade
			calculo *= CalculoPrioridade.calcular(compostoCalculo, posicao + 2);
			posicao = PonteiroPrioridade.calcular(seq, posicao + 2);
			if (posicao == -1) {
				deuCerto = false;
				posicao = seq.size();
				mensagemErro = "Erro na multiplicação ao tentar calcular a prioridade com o próximo elemento sendo um abre parênteses";
			}
		} else {
			try {
				calculo *= Double.parseDouble(compostoCalculo.getValorVariavelDAO().buscaValorVariavel(compostoCalculo,
						Integer.parseInt(seq.get(posicao + 1))));
			} catch (Exception e) {
				deuCerto = false;
				posicao = seq.size();
				mensagemErro = "Erro na multiplicação ao tentar buscar o valor da variável";
			}
			posicao++;
		}

		compostoCalculo.setPosicao(posicao);
		compostoCalculo.setCalculo(calculo);

		if (deuCerto) {
			return compostoCalculo;
		}

		throw new RuntimeException(mensagemErro);
	}

	public String deveAplicar(List<String> seqCalculo, int posicao) {
		if (seqCalculo.get(posicao).equals("×")) {
			return "×";
		}
		return "x";
	}
}
