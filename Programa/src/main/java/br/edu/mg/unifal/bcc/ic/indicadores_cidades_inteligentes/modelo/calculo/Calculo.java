package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo;

import java.util.List;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.CalculoIndicador;

public abstract class Calculo {

	protected Calculo proximo;

	public Calculo(Calculo proximo) {
		this.proximo = proximo;
	}

	public CalculoIndicador calcular(CompostoCalculo compostoCalculo) {
		try {
			for (int posicao = 0; posicao < compostoCalculo.getSeqCalculo().size(); posicao++) {
				compostoCalculo.setPosicao(posicao);
				compostoCalculo = fazerCalculo(compostoCalculo);
				posicao = compostoCalculo.getPosicao();
			}
			return new CalculoIndicador(String.format("%.02f", compostoCalculo.getCalculo()),
					compostoCalculo.getData_variaveis(), compostoCalculo.isValor_oficial());
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("Erro no cálculo\n" + e);
		}
	}

	private CompostoCalculo fazerCalculo(CompostoCalculo compostoCalculo) {
		if (compostoCalculo.getSeqCalculo().get(compostoCalculo.getPosicao())
				.equals(deveAplicar(compostoCalculo.getSeqCalculo(), compostoCalculo.getPosicao()))) {
			compostoCalculo = efetuarCalculo(compostoCalculo);
		} else {
			compostoCalculo = proximo.fazerCalculo(compostoCalculo);
		}
		return compostoCalculo;
	}

	protected abstract CompostoCalculo efetuarCalculo(CompostoCalculo compostoCalculo);

	protected abstract String deveAplicar(List<String> seqCalculo, int posicao);
}
