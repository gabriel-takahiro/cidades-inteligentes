/*Copyright (C) <2022> <Gabriel Takahiro Toma de Lima>
 
 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.
 You should have received a copy of the GNU General Public License
 along with this program. If not, see <https://www.gnu.org/licenses/>.
 
Versão em português:

Este programa é um software livre: você pode redistribuí-lo e/ou
modificá-lo sob os termos da Licença Pública Geral GNU, conforme
publicado pela Free Software Foundation, seja a versão 3 da Licença
ou (a seu critério) qualquer versão posterior.
Este programa é distribuído na esperança de que seja útil,
mas SEM QUALQUER GARANTIA; sem a garantia implícita de
COMERCIALIZAÇÃO OU ADEQUAÇÃO A UM DETERMINADO PROPÓSITO. Veja a
Licença Pública Geral GNU para obter mais detalhes.
Você deve ter recebido uma cópia da Licença Pública Geral GNU
junto com este programa. Se não, veja <https://www.gnu.org/licenses/>.
*/
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo;

import java.util.List;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.CalculoPrioridadeSomaSub;

/**
 * Classe responsável por reconhecer "-" no método de cálculo
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public class Subtracao extends Calculo {

	/**
	 * 
	 * @param proximo próximo componente do método de cálculo
	 */
	public Subtracao(Calculo proximo) {
		super(proximo);
	}

	/**
	 * Ação a ser tomada após reconhecer o elemento "-" no método de cálculo
	 */
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
			calculo -= calculoPrioridadeSomaSub.getCalculo();
			posicao = calculoPrioridadeSomaSub.getPosicao();
		} else if (seq.get(posicao + 1).equals("(")) {
			valor1 = CalculoPrioridade.calcular(compostoCalculo, posicao + 2);
			posicao = PonteiroPrioridade.calcular(seq, posicao + 2);
			if (posicao == -1) {
				deuCerto = false;
				posicao = seq.size();
				mensagemErro = "Erro na subtracao falha ao calcular a prioridade.";
			} else {
				CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = PrioridadeSomaSub.calcular(compostoCalculo, posicao,
						valor1);
				calculo -= calculoPrioridadeSomaSub.getCalculo();
				posicao = calculoPrioridadeSomaSub.getPosicao();
			}
		} else {
			valor1 = Double.parseDouble(compostoCalculo.getValorVariavelDAO().buscaValorVariavel(compostoCalculo,
					Integer.parseInt(seq.get(posicao + 1))));

			CalculoPrioridadeSomaSub calculoPrioridadeSomaSub = PrioridadeSomaSub.calcular(compostoCalculo, posicao + 1,
					valor1);
			calculo -= calculoPrioridadeSomaSub.getCalculo();
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

	/**
	 * Verifica se o elemento apontado pelo ponteiro no método de cálculo é o "-"
	 */
	public String deveAplicar(List<String> seqCalculo, int posicao) {
		return "-";
	}
}
