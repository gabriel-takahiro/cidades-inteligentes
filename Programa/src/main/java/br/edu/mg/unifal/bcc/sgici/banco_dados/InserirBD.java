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

package br.edu.mg.unifal.bcc.sgici.banco_dados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import br.edu.mg.unifal.bcc.sgici.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.sgici.modelo.Indicador;

/**
 * Essa classe é responsável por realizar a inserção de valores nas tabelas do
 * banco de dados a partir do esquema importados. Basicamente, todos os
 * processos de inserção de valores no banco de dados a partir do esquema
 * "importados" serão realizados nessa classe.
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class InserirBD {

	private static List<ResultadoOperacao> operacoes;

	/**
	 * Este método é responsável por inserir todos os valores do esquema
	 * "importados" nas respectivas tabelas do banco de dados.
	 * 
	 * @return Lista de objetos contendo informações das operações realizadas no
	 *         banco de dados, incluindo o nome da operação e o seu status (sucesso
	 *         ou falha).
	 */
	public static List<ResultadoOperacao> inserirTudo() {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			operacoes = new ArrayList<ResultadoOperacao>();

			inserirODS(connection);
			inserirMetas(connection);
			inserirIndicadores(connection);
			inserirVariaveis(connection);
			inserirMunicipios(connection);

			return operacoes;
		} catch (Exception e) {
			e.printStackTrace();
			return operacoes;
		}
	}

	/**
	 * Este método realiza a inserção dos valores na tabela "ods" a partir da tabela
	 * "importados.ods" no banco de dados. O status (sucesso ou falha) da operação
	 * será registrado na lista de operações, juntamente com o nome da operação
	 * ("Inserir ODS").
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void inserirODS(Connection connection) {
		String inserirODS = "INSERT INTO ods SELECT numero_ods, nome_objetivo FROM importados.ods";
		try (PreparedStatement pstm = connection.prepareStatement(inserirODS)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Inserir ODS", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Inserir ODS", "Falha"));
		}
	}

	/**
	 * Este método realiza a inserção dos valores na tabela "meta" a partir da
	 * tabela "importados.meta" no banco de dados. O status (sucesso ou falha) da
	 * operação será registrado na lista de operações, juntamente com o nome da
	 * operação ("Inserir metas").
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void inserirMetas(Connection connection) {
		String inserirMeta = "INSERT INTO meta SELECT numero_meta, texto_meta, ods "
				+ "FROM importados.planilha WHERE numero_meta IS NOT NULL GROUP BY 1, 2, 3 ORDER BY 1 ASC;";
		try (PreparedStatement pstm = connection.prepareStatement(inserirMeta)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Inserir metas", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Inserir metas", "Falha"));
		}
	}

	/**
	 * Este método realiza a inserção dos valores na tabela "indicador" a partir da
	 * tabela "importados.planilha" no banco de dados. O status (sucesso ou falha)
	 * da operação será registrado na lista de operações, juntamente com o nome da
	 * operação ("Inserir indicadores").
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void inserirIndicadores(Connection connection) {
		String inserirIndicador = "INSERT INTO indicador (nome_indicador, metodo_calculo, nome_eixo, tipo_plano, nome_plano, descricao, informacoes_tecnicas, numero_meta, padrao)"
				+ " SELECT indicador, calculo, eixo, tipo_plano, nome_plano, descricao, informacao, numero_meta, padrao"
				+ " FROM importados.planilha;";
		try (PreparedStatement pstm = connection.prepareStatement(inserirIndicador)) {
			pstm.execute();
			new Indicador().padronizarMetodoCalculo();
			operacoes.add(new ResultadoOperacao("Inserir indicadores", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Inserir indicadores", "Falha"));
		}
	}

	/**
	 * Este método realiza a inserção dos valores na tabela "variaveis" a partir da
	 * tabela "importados.variaveis" no banco de dados. O status (sucesso ou falha)
	 * da operação será registrado na lista de operações, juntamente com o nome da
	 * operação ("Inserir variáveis").
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void inserirVariaveis(Connection connection) {
		String inserirVariavel = "INSERT INTO variavel "
				+ "SELECT codigo_variavel, tipo_banco, nome_variavel, codigo_banco, atualizacao, padrao "
				+ "FROM importados.variaveis;";
		try (PreparedStatement pstm = connection.prepareStatement(inserirVariavel)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Inserir variáveis", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Inserir variáveis", "Falha"));
		}
	}

	/**
	 * Este método realiza a inserção dos valores na tabela "municipio" a partir da
	 * tabela "importados.municipios" no banco de dados. O status (sucesso ou falha)
	 * da operação será registrado na lista de operações, juntamente com o nome da
	 * operação ("Inserir municípios").
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void inserirMunicipios(Connection connection) {
		String inserirMunicipio = "INSERT INTO municipio SELECT codigo, nome_municipio, nome_uf"
				+ " FROM importados.municipios;";
		try (PreparedStatement pstm = connection.prepareStatement(inserirMunicipio)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Inserir municípios", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Inserir municípios", "Falha"));
		}
	}
}
