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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas;

import java.util.ArrayList;

import javax.swing.JTable;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.IndicadoresBuscados;

/**
 * Classe responsável pelos parâmetros da classe Mostrar Calculos
 * @author Gabriel Takahiro
 * @version 0.4
 *
 */
public class ParametrosMostrarCalculo {
	private IndicadoresBuscados indicador;
	private int codigo_municipio;
	private String data;
	private String nome_municipio;
	private int valorRetroativo;
	private JTable tableIndicadoresComResultado;
	private JTable tableIndicadoresSemResultado;
	private ArrayList<IndicadoresBuscados> indicadoresNaoValorados;
	private ArrayList<IndicadoresBuscados> indicadoresValorados;
	private boolean valorOficial;

	/**
	 * 
	 * @param indicador indicador a ser analisado
	 * @param codigo_municipio código do município
	 * @param data ano em que o indicador foi calculado
	 * @param nome_municipio nome do município
	 * @param valorRetroativo quantidade de anos a mais que foi utilizado na busca retroativa das variáveis
	 * @param tableIndicadoresComResultado tabela de indicadores com resultado
	 * @param tableIndicadoresSemResultado tabela de indicadores sem resultado
	 * @param indicadoresNaoValorados lista de indicadores que não possuem valor
	 * @param indicadoresValorados lista de indicadores que possuem valor
	 * @param valorOficial indica se o indicador foi calculado totalmente com valores oficiais
	 */
	public ParametrosMostrarCalculo(IndicadoresBuscados indicador, int codigo_municipio, String data,
			String nome_municipio, int valorRetroativo, JTable tableIndicadoresComResultado,
			JTable tableIndicadoresSemResultado, ArrayList<IndicadoresBuscados> indicadoresNaoValorados,
			ArrayList<IndicadoresBuscados> indicadoresValorados, boolean valorOficial) {
		this.indicador = indicador;
		this.codigo_municipio = codigo_municipio;
		this.data = data;
		this.nome_municipio = nome_municipio;
		this.valorRetroativo = valorRetroativo;
		this.tableIndicadoresComResultado = tableIndicadoresComResultado;
		this.tableIndicadoresSemResultado = tableIndicadoresSemResultado;
		this.indicadoresNaoValorados = indicadoresNaoValorados;
		this.indicadoresValorados = indicadoresValorados;
		this.valorOficial = valorOficial;
	}

	/**
	 * 
	 * @return código do indicador
	 */
	public IndicadoresBuscados getIndicador() {
		return indicador;
	}

	/**
	 * 
	 * @return código do município
	 */
	public int getCodigo_municipio() {
		return codigo_municipio;
	}

	/**
	 * 
	 * @return ano
	 */
	public String getData() {
		return data;
	}

	/**
	 * 
	 * @return nome do município
	 */
	public String getNome_municipio() {
		return nome_municipio;
	}

	/**
	 * 
	 * @return anos a mais para busca retroativa
	 */
	public int getValorRetroativo() {
		return valorRetroativo;
	}

	/**
	 * 
	 * @return tabela dos indicadores com resultado
	 */
	public JTable getTableIndicadoresComResultado() {
		return tableIndicadoresComResultado;
	}

	/**
	 * 
	 * @return tabela dos indicadores sem resultado
	 */
	public JTable getTableIndicadoresSemResultado() {
		return tableIndicadoresSemResultado;
	}

	/**
	 * 
	 * @return lista de indicadores não valorados
	 */
	public ArrayList<IndicadoresBuscados> getIndicadoresNaoValorados() {
		return indicadoresNaoValorados;
	}

	/**
	 * 
	 * @return lista de indicadores valorados
	 */
	public ArrayList<IndicadoresBuscados> getIndicadoresValorados() {
		return indicadoresValorados;
	}

	/**
	 * 
	 * @return valor oficial de algum órgão público
	 */
	public boolean isValorOficial() {
		return valorOficial;
	}
}