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

/**
 * Essa classe é responsável por realizar a importação de tabelas no banco de
 * dados a partir de arquivos externos, como planilhas. Basicamente, todos os
 * processos de importação no banco de dados serão realizados nessa classe.
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class ImportarBD {

	private static List<ResultadoOperacao> operacoes;

	/**
	 * Este método executa a operação de importação de todas as tabelas padrão no
	 * banco de dados, incluindo as tabelas importados.municipios, importados.ods,
	 * importados.planilha e importados.variaveis.
	 * 
	 * @return Lista de objetos contendo informações das operações realizadas no
	 *         banco de dados, incluindo o nome da operação e o seu status (sucesso
	 *         ou falha).
	 */
	public static List<ResultadoOperacao> importarTudo() {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			operacoes = new ArrayList<ResultadoOperacao>();
			operacoes.add(new ResultadoOperacao("Criar tabelas para importar", null));
			operacoes.addAll(CriarBD.criarTudoImportados(connection));

			operacoes.add(new ResultadoOperacao("Importar tabelas", null));
			importarPlanilha(connection);
			importarMunicipios(connection);
			importarVariaveis(connection);
			importarODS(connection);

			return operacoes;
		} catch (Exception e) {
			return operacoes;
		}
	}

	/**
	 * Este método realiza a importação da tabela "importados.planilha" no banco de
	 * dados, a qual contém informações sobre os indicadores. O status (sucesso ou
	 * falha) da operação será registrado na lista de operações, juntamente com o
	 * nome da operação ("Importar planilha").
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void importarPlanilha(Connection connection) {
		String importarPlanilha = "COPY importados.planilha FROM '"
				+ new java.io.File("TabelaIndicadores.csv").getAbsolutePath().toString() + "'" + "DELIMITER '|'"
				+ "CSV HEADER;";
		try (PreparedStatement pstm = connection.prepareStatement(importarPlanilha)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Importar planilha", "Sucesso"));
		} catch (Exception e) {
			System.out.println(e);
			operacoes.add(new ResultadoOperacao("Importar planilha", "Falha"));
		}
	}

	/**
	 * Este método realiza a importação da tabela "importados.municipios" no banco
	 * de dados. O status (sucesso ou falha) da operação será registrado na lista de
	 * operações, juntamente com o nome da operação ("Importar municípios").
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void importarMunicipios(Connection connection) {
		String importarMunicipios = "COPY importados.municipios FROM '"
				+ new java.io.File("TabelaMunicipios.csv").getAbsolutePath().toString() + "'" + "DELIMITER ';'"
				+ "CSV HEADER;";
		try (PreparedStatement pstm = connection.prepareStatement(importarMunicipios)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Importar municípios", "Sucesso"));
		} catch (Exception e) {
			System.out.println(e);
			operacoes.add(new ResultadoOperacao("Importar municípios", "Falha"));
		}
	}

	/**
	 * Este método realiza a importação da tabela "importados.variaveis" no banco de
	 * dados. O status (sucesso ou falha) da operação será registrado na lista de
	 * operações, juntamente com o nome da operação ("Importar variáveis").
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void importarVariaveis(Connection connection) {
		String importarVariaveis = "COPY importados.variaveis FROM '"
				+ new java.io.File("TabelaVariaveis.csv").getAbsolutePath().toString() + "'" + "DELIMITER '|'"
				+ "CSV HEADER;";
		try (PreparedStatement pstm = connection.prepareStatement(importarVariaveis)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Importar variáveis", "Sucesso"));
		} catch (Exception e) {
			System.out.println(e);
			operacoes.add(new ResultadoOperacao("Importar variáveis", "Falha"));
		}
	}

	/**
	 * Este método realiza a importação da tabela "importados.ods" no banco de
	 * dados. O status (sucesso ou falha) da operação será registrado na lista de
	 * operações, juntamente com o nome da operação ("Importar ods").
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void importarODS(Connection connection) {
		String importarVariaveis = "COPY importados.ods FROM '"
				+ new java.io.File("TabelaODS.csv").getAbsolutePath().toString() + "'" + "DELIMITER '|'"
				+ "CSV HEADER;";
		try (PreparedStatement pstm = connection.prepareStatement(importarVariaveis)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Importar ODS", "Sucesso"));
		} catch (Exception e) {
			System.out.println(e);
			operacoes.add(new ResultadoOperacao("Importar ODS", "Falha"));
		}
	}
}
