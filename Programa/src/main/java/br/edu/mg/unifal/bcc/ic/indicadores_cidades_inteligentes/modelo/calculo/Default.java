package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo;

import java.util.List;

public class Default extends Calculo {

	public Default() {
		super(null);
	}

	public CompostoCalculo efetuarCalculo(CompostoCalculo compostoCalculo) {
		int posicao = compostoCalculo.getPosicao();
		boolean deuCerto = true;

		try {
			double calculo = Double.parseDouble(compostoCalculo.getValorVariavelDAO().buscaValorVariavel(
					compostoCalculo, Integer.parseInt(compostoCalculo.getSeqCalculo().get(posicao))));

			compostoCalculo.setCalculo(calculo);
		} catch (Exception e) {
			deuCerto = false;
			posicao = compostoCalculo.getSeqCalculo().size();
		}

		if (deuCerto) {
			return compostoCalculo;
		}

		throw new RuntimeException("Erro no default ao tentar buscar o valor da variável.");
	}

	public String deveAplicar(List<String> seqCalculo, int posicao) {
		return seqCalculo.get(posicao);
	}
}
