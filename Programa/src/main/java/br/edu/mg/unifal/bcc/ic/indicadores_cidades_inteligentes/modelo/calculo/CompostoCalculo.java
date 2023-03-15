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

import java.util.ArrayList;
import java.util.List;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.CalculoIndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.DataDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.PossuiVariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.ValorVariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;

/**
 * Classe responsável por encapsular e passar os parâmetros para o cálculo do
 * indicador. Os parâmetros podem ser alterados dinamicamente.
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class CompostoCalculo {

	private List<Variavel> variaveis;
	private int codigo_municipio;
	private ValorVariavelDAO valorVariavelDAO;
	private CalculoIndicadorDAO calculoIndicadorDAO;
	private PossuiVariavelDAO possuiVariavelDAO;
	private String data;
	private DataDAO dataDAO;
	private boolean recalcular;
	private int valorRetroativo;
	private List<String> seqCalculo;
	private double calculo;
	private double valor1;
	private int posicao;
	private String data_variaveis;
	private ArrayList<Integer> codigo_variaveis;
	private boolean valor_oficial;

	/**
	 * 
	 * @param variaveis           lista com todas as variáveis do banco de dados
	 * @param codigo_municipio    código do município
	 * @param valorVariavelDAO    conexão com a tabela valor_variavel do banco de
	 *                            dados
	 * @param calculoIndicadorDAO conexão com a tabela calculo_indicador do banco de
	 *                            dados
	 * @param possuiVariavelDAO   conexão com a tabela possui_variavel do banco de
	 *                            dados
	 * @param data                ano
	 * @param dataDAO             conexão com a tabela ano do banco de dados
	 * @param recalcular          true caso seja necessário recalcular o indicador
	 * @param valorRetroativo     anos a mais para a busca retroativa das variáveis
	 * @param seqCalculo          método de cálculo do indicador
	 * @param calculo             valor do indicador até o momento
	 * @param valor1              valor esperando a prioridade para ser acrescentado
	 *                            ou subtraído do cálculo do indicador
	 * @param posicao             posição do ponteiro
	 * @param data_variaveis      ano utilizado para buscar os valores das variáveis
	 * @param codigo_variaveis    lista com os códigos das variáveis
	 * @param valor_oficial       true caso o valor seja oficial
	 */
	public CompostoCalculo(List<Variavel> variaveis, int codigo_municipio, ValorVariavelDAO valorVariavelDAO,
			CalculoIndicadorDAO calculoIndicadorDAO, PossuiVariavelDAO possuiVariavelDAO, String data, DataDAO dataDAO,
			boolean recalcular, int valorRetroativo, List<String> seqCalculo, double calculo, double valor1,
			int posicao, String data_variaveis, ArrayList<Integer> codigo_variaveis, boolean valor_oficial) {
		this.variaveis = variaveis;
		this.codigo_municipio = codigo_municipio;
		this.valorVariavelDAO = valorVariavelDAO;
		this.calculoIndicadorDAO = calculoIndicadorDAO;
		this.possuiVariavelDAO = possuiVariavelDAO;
		this.data = data;
		this.dataDAO = dataDAO;
		this.recalcular = recalcular;
		this.valorRetroativo = valorRetroativo;
		this.seqCalculo = seqCalculo;
		this.calculo = calculo;
		this.valor1 = valor1;
		this.posicao = posicao;
		this.data_variaveis = data_variaveis;
		this.codigo_variaveis = codigo_variaveis;
		this.valor_oficial = valor_oficial;
	}

	/**
	 * Coloca o ano utilizado para buscar o valor da variável
	 * 
	 * @param codigo_variavel código da variável
	 * @param data            ano
	 */
	public void setDataVariaveis(int codigo_variavel, String data) {
		try {
			if (this.codigo_variaveis.contains(codigo_variavel)) {
				return;
			}
			if (this.data_variaveis.equals("")) {
				this.data_variaveis = codigo_variavel + "/" + data;
				this.codigo_variaveis.add(codigo_variavel);
				return;
			}
			this.data_variaveis = this.data_variaveis + "/" + codigo_variavel + "/" + data;
			this.codigo_variaveis.add(codigo_variavel);
		} catch (Exception e) {
			System.out.println("Erro setDataVariaveis " + e);
		}
	}

	/**
	 * Coloca como true caso o valor seja oficial
	 * 
	 * @param valor_oficial true caso o valor seja oficial
	 */
	public void setValor_oficial(boolean valor_oficial) {
		this.valor_oficial = valor_oficial;
	}

	/**
	 * 
	 * @return true caso o valor seja oficial
	 */
	public boolean isValor_oficial() {
		return valor_oficial;
	}

	/**
	 * 
	 * @return lista de variáveis
	 */
	public List<Variavel> getVariaveis() {
		return variaveis;
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
	 * @return conexão com a tabela valor_variavel do banco de dados
	 */
	public ValorVariavelDAO getValorVariavelDAO() {
		return valorVariavelDAO;
	}

	/**
	 * 
	 * @return conexão com a tabela calculo_indicador do banco de dados
	 */
	public CalculoIndicadorDAO getCalculoIndicadorDAO() {
		return calculoIndicadorDAO;
	}

	/**
	 * 
	 * @return conexão com a tabela possui_variavel do banco de dados
	 */
	public PossuiVariavelDAO getPossuiVariavelDAO() {
		return possuiVariavelDAO;
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
	 * @return conexão com a tabela data do banco de dados
	 */
	public DataDAO getDataDAO() {
		return dataDAO;
	}

	/**
	 * 
	 * @return true caso seja necessário recalcular o indicador
	 */
	public boolean isRecalcular() {
		return recalcular;
	}

	/**
	 * 
	 * @return anos a mais para a busca retroativa das variáveis
	 */
	public int getValorRetroativo() {
		return valorRetroativo;
	}

	/**
	 * 
	 * @return método de cálculo disposto em uma lista
	 */
	public List<String> getSeqCalculo() {
		return seqCalculo;
	}

	/**
	 * 
	 * @return valor atual do indicador
	 */
	public double getCalculo() {
		return calculo;
	}

	/**
	 * 
	 * @return valor que será utilizado pelo indicador respeitando a prioridade
	 */
	public double getValor1() {
		return valor1;
	}

	/**
	 * 
	 * @return posiçao do ponteiro
	 */
	public int getPosicao() {
		return posicao;
	}

	/**
	 * 
	 * @return ano e código da variável indicando qual o ano que foi utilizado para
	 *         buscar o valor da variável
	 */
	public String getData_variaveis() {
		return data_variaveis;
	}

	/**
	 * Coloca o valor do indicador
	 * @param calculo valor a ser colocado
	 */
	public void setCalculo(double calculo) {
		this.calculo = calculo;
	}

	/**
	 * Coloca o valor que será utilizado conforme a prioridade
	 * @param valor1 valor que será utilizado
	 */
	public void setValor1(double valor1) {
		this.valor1 = valor1;
	}

	/**
	 * Coloca a posição do ponteiro
	 * @param posicao posição do ponteiro
	 */
	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

}
