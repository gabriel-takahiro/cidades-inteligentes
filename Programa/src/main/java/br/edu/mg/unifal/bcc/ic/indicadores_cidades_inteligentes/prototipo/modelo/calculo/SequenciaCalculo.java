package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.calculo;

import java.util.ArrayList;
import java.util.List;

public class SequenciaCalculo {

	// Função que separa cada elemento do método do cálculo
	public static List<String> lista(String metodoCalculo) {
		String calculo = metodoCalculo.replace(" ", "");
		String valor = "";
		String aux;
		ArrayList<String> seqCalculo = new ArrayList<String>();
		for (int i = 0; i < calculo.length(); i++) {
			aux = calculo.substring(i, i + 1);
			if (aux.equals("×") || aux.equals("x") || aux.equals("/") || aux.equals("(") || aux.equals(")")
					|| aux.equals("+") || aux.equals("-") || aux.equals("[") || aux.equals("]") || aux.equals("{")
					|| aux.equals("}")) {
				if (!valor.isEmpty()) {
					seqCalculo.add(valor);
				}
				seqCalculo.add(aux);
				valor = "";
			} else {
				valor = valor.concat(aux);
			}
		}
		if (!valor.isEmpty()) {
			seqCalculo.add(valor);
		}
		return seqCalculo;
	}
}
