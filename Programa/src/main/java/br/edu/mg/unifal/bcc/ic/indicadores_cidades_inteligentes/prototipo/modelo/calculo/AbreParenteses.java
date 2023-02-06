package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.calculo;

import java.util.List;

public class AbreParenteses extends Calculo {

	public AbreParenteses(Calculo proximo) {
		super(proximo);
	}

	public CompostoCalculo efetuarCalculo(CompostoCalculo compostoCalculo) {
		int posicao = compostoCalculo.getPosicao();
		boolean deuCerto = true;
		double calculo = CalculoPrioridade.calcular(compostoCalculo, posicao + 1);
		posicao = PonteiroPrioridade.calcular(compostoCalculo.getSeqCalculo(), posicao + 1);

		if (posicao == -1) {
			deuCerto = false;
			posicao = compostoCalculo.getSeqCalculo().size();
		}

		compostoCalculo.setPosicao(posicao);
		compostoCalculo.setCalculo(calculo);
		
		if (deuCerto) {
			return compostoCalculo;
		}

		throw new RuntimeException("Erro no abre parenteses falha no calculo da prioridade");
	}

	public String deveAplicar(List<String> seqCalculo, int posicao) {
		return "(";
	}
}
