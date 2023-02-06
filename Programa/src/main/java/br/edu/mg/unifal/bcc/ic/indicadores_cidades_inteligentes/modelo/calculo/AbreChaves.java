package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo;

import java.util.List;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.CalculoIndicador;

public class AbreChaves extends Calculo {

	public AbreChaves(Calculo proximo) {
		super(proximo);
	}

	public CompostoCalculo efetuarCalculo(CompostoCalculo compostoCalculo) {
		int posicao = compostoCalculo.getPosicao();

		double calculo = CalculoIndicador.calcularIndicador(Integer.parseInt(compostoCalculo.getSeqCalculo().get(posicao + 1)),
				compostoCalculo);
		posicao += 2;

		compostoCalculo.setPosicao(posicao);
		compostoCalculo.setCalculo(calculo);

		return compostoCalculo;
	}

	public String deveAplicar(List<String> seqCalculo, int posicao) {
		return "{";
	}
}
