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

/**
 * Classe responsável por reconhecer "x" no método de cálculo
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public class Multiplicacao extends Calculo {

	/**
	 * 
	 * @param proximo próximo componente do método de cálculo
	 */
	public Multiplicacao(Calculo proximo) {
		super(proximo);
	}

	/**
	 * Ação a ser tomada após reconhecer o elemento "x" no método de cálculo
	 */
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

	/**
	 * Verifica se o elemento apontado pelo ponteiro no método de cálculo é o "x"
	 */
	public String deveAplicar(List<String> seqCalculo, int posicao) {
		if (seqCalculo.get(posicao).equals("×")) {
			return "×";
		}
		return "x";
	}
}
