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

package br.edu.mg.unifal.bcc.sgici.modelo;

import java.sql.Connection;
import java.util.List;

import javax.swing.JTable;

import br.edu.mg.unifal.bcc.sgici.banco_dados.ResultadoOperacao;
import br.edu.mg.unifal.bcc.sgici.calculo.SequenciaCalculo;
import br.edu.mg.unifal.bcc.sgici.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.IndicadorDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.PossuiVariavelDAO;

/**
 * Classe que representa a tabela possui_variavel do banco de dados.
 *
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class PossuiVariavel {

	private int codigo_variavel;
	private int codigo_indicador;

	/**
	 * Edita a tabela para mostrar os valores utilizados para calcular o indicador
	 * 
	 * @param tabela          tabela que será editada
	 * @param indicador       indicador que será analisado
	 * @param valorRetroativo quantos anos a mais foram utilizados para buscar os
	 *                        valores das variáveis
	 * @throws RuntimeException se falhar na busca dos valores que compõem o
	 *                          indicador
	 */
	public static void tabelaCalculos(JTable tabela, IndicadoresBuscados indicador, int valorRetroativo) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {

			PossuiVariavelDAO possuiVariavelDAO = new PossuiVariavelDAO(connection);

			possuiVariavelDAO.tabelaCalculos(tabela, indicador, valorRetroativo);

		} catch (Exception e) {
			throw new RuntimeException("Falha na tabela de calculos de possui variavel.");
		}
	}

	/**
	 * Insere na tabela possui_variavel o código da variável e o código do indicador
	 * a partir de um método de cálculo.
	 * 
	 * @param metodoCalculo  método de cálculo do indicador
	 * @param listaVariaveis
	 * @throws RuntimeException se ocorrer um erro ao salvar o valor na tabela
	 *                          possui_variavel
	 */
	public static void inserir(String metodoCalculo, List<Integer> listaVariaveis) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			PossuiVariavelDAO possuiVariavelDAO = new PossuiVariavelDAO(connection);

			int codigo = Indicador.buscaCodigo(metodoCalculo);
			for (int var : listaVariaveis) {
				possuiVariavelDAO.salvar(new PossuiVariavel(var, codigo));
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Insere na tabela possui_variavel o código da variável e o código do indicador
	 * para todos os indicadores cadastrados no banco de dados
	 * 
	 * @throws RuntimeException se ocorrer um erro ao salvar o valor na tabela
	 *                          possui_variavel
	 */
	public static ResultadoOperacao insereTodos() {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			PossuiVariavelDAO possuiVariavelDAO = new PossuiVariavelDAO(connection);
			IndicadorDAO indicadorDAO = new IndicadorDAO(connection);

			List<Indicador> indicadores = indicadorDAO.buscarCodigoEMetodo();

			for (Indicador indicador : indicadores) {
				List<Integer> variaveis = SequenciaCalculo.listaVariaveis(indicador.getCalculo());
				for (int var : variaveis) {
					PossuiVariavel pv = new PossuiVariavel(var, indicador.getCodigo());
					possuiVariavelDAO.salvar(pv);
				}
			}
			return new ResultadoOperacao("Inserção na tabela possui_variavel", "Sucesso");
		} catch (Exception e) {
			return new ResultadoOperacao("Inserção na tabela possui_variavel", "Falha");
		}
	}

	/**
	 * Substitui o código antigo da variável pelo novo para todos os métodos de
	 * cálculo que utilizam essa variável
	 * 
	 * @param codigo_antigo   código antigo da variável
	 * @param codigo_variavel código novo da variável
	 */
	public static void atualizarVariaveis(int codigo_antigo, int codigo_variavel) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			new PossuiVariavelDAO(connection).atualizarVariaveis(codigo_antigo, codigo_variavel);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * Constrói um objeto contendo o código da variável e do indicador
	 * 
	 * @param codigo_variavel  código da variável
	 * @param codigo_indicador código do indicador
	 */
	public PossuiVariavel(int codigo_variavel, int codigo_indicador) {
		this.codigo_variavel = codigo_variavel;
		this.codigo_indicador = codigo_indicador;
	}

	/**
	 * 
	 * @return código do indicador
	 */
	public int getCodigo() {
		return codigo_indicador;
	}

	/**
	 * 
	 * @return código da variável
	 */
	public int getVariavel() {
		return codigo_variavel;
	}

	/**
	 * Exclui da tabela possui_variavel os valores em que aparece o código do
	 * indicador. O código do indicador é obtido através do método de cálculo.
	 * 
	 * @param metodoCalculo método do cálculo do indicador. Será utilizado para
	 *                      ecnontrar o código do indicador.
	 */
	public static void excluir(String metodoCalculo) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			PossuiVariavelDAO possuiVariavelDAO = new PossuiVariavelDAO(connection);

			int codigo = Indicador.buscaCodigo(metodoCalculo);
			possuiVariavelDAO.excluir(codigo);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
