package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.calculo;

import java.util.List;

public class PonteiroPrioridade {
	
	public static int calcular(List<String> seqCalculo, int posicao) {// Função que devolve a posição do ponteiro
		// após resolver o que está dentro do parênteses
		int k = 1;
		for (int j = posicao; j < seqCalculo.size(); j++) {
			if (seqCalculo.get(j).equals("(")) {
				k++;
			}
			if (seqCalculo.get(j).equals(")")) {
				k--;
				if (k == 0) {
					return j;
				}
			}
		}
		return -1;
	}
}
