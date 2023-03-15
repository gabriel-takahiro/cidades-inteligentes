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
 * Classe responsável por calcular o indicador por meio do método de cálculo
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public abstract class Calculo {

	protected Calculo proximo;

	/**
	 * 
	 * @param proximo próximo componente do método de cálculo
	 */
	public Calculo(Calculo proximo) {
		this.proximo = proximo;
	}

	/**
	 * Realiza o cálculo do indicador por meio do método de cálculo
	 * @param compostoCalculo parâmetros encapsulados para calcular o indicador
	 * @return indicador calculado com suas informações
	 */
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

	/**
	 * Realiza operações com base no operador
	 * @param compostoCalculo parâmetros encapsulados para calcular o indicador
	 * @return parâmetros encapsulados para calcular o indicador
	 */
	private CompostoCalculo fazerCalculo(CompostoCalculo compostoCalculo) {
		if (compostoCalculo.getSeqCalculo().get(compostoCalculo.getPosicao())
				.equals(deveAplicar(compostoCalculo.getSeqCalculo(), compostoCalculo.getPosicao()))) {
			compostoCalculo = efetuarCalculo(compostoCalculo);
		} else {
			compostoCalculo = proximo.fazerCalculo(compostoCalculo);
		}
		return compostoCalculo;
	}

	/**
	 * Operação a ser realizada com base no operador
	 * @param compostoCalculo parâmetros encapsulados para calcular o indicador
	 * @return parâmetros encapsulados para calcular o indicador
	 */
	protected abstract CompostoCalculo efetuarCalculo(CompostoCalculo compostoCalculo);

	/**
	 * Verifica qual elemento o ponteiro está apontando
	 * @param seqCalculo lista com os elementos do método de cálculo
	 * @param posicao posição do ponteiro
	 * @return elemento que o ponteiro está apontando
	 */
	protected abstract String deveAplicar(List<String> seqCalculo, int posicao);
}
