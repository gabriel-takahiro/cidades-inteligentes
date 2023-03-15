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

package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.banco_dados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;

/**
 * Classe responsável pela importação das tabelas no banco de dados
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class ImportarBD {

	private static List<ResultadoOperacao> operacoes;

	/**
	 * Importa todas as tabelas no banco de dados
	 * @return lista com as operações realizadas 
	 */
	public static List<ResultadoOperacao> importarTudo() {
		Connection connection = ConnectionFactory.recuperarConexao();
		
		operacoes = new ArrayList<ResultadoOperacao>();
		operacoes.add(new ResultadoOperacao("Criar tabelas para importar", null));
		operacoes.addAll(CriarBD.criarTudoImportados(connection));
		
		operacoes.add(new ResultadoOperacao("Importar tabelas", null));
		importarPlanilha(connection);
		importarMunicipios(connection);
		importarVariaveis(connection);
		importarODS(connection);
		
		return operacoes;
	}

	/**
	 * Importa a tabela "planilha" no banco de dados
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
			operacoes.add(new ResultadoOperacao("Importar planilha", "Falha"));
		}
	}
	
	/**
	 * Importa a tabela "municipios" no banco de dados
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
			operacoes.add(new ResultadoOperacao("Importar municípios", "Falha"));
		}
	}

	/**
	 * Importa a tabela "variaveis" no banco de dados
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
			operacoes.add(new ResultadoOperacao("Importar variáveis", "Falha"));
		}
	}

	/**
	 * Importa a tabela "ods" no banco de dados
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
			operacoes.add(new ResultadoOperacao("Importar ODS", "Falha"));
		}
	}
}
