package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo;

import java.util.List;

public class AbreColchetes extends Calculo {

	public AbreColchetes(Calculo proximo) {
		super(proximo);
	}

	public CompostoCalculo efetuarCalculo(CompostoCalculo compostoCalculo) {
		int posicao = compostoCalculo.getPosicao();

		double calculo = Double.parseDouble(compostoCalculo.getSeqCalculo().get(posicao + 1));
		posicao += 2;

		compostoCalculo.setPosicao(posicao);
		compostoCalculo.setCalculo(calculo);

		return compostoCalculo;
	}

	public String deveAplicar(List<String> seqCalculo, int posicao) {
		return "[";
	}
}
