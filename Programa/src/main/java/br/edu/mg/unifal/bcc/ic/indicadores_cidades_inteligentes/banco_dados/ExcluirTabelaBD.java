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
 * Classe responsável pela exclusão de tabelas no banco de dados
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class ExcluirTabelaBD {

	private static List<ResultadoOperacao> operacoes;

	/**
	 * Exclui todas as tabelas do banco de dados
	 * 
	 * @param excluirApenasImportados verifica se é necessario excluir apenas as
	 *                                tabelas presentes no esquema "importados"
	 * @return lista com as operações realizadas 
	 */
	public static List<ResultadoOperacao> excluirTodas(boolean excluirApenasImportados) {
		Connection connection = ConnectionFactory.recuperarConexao();

		operacoes = new ArrayList<ResultadoOperacao>();

		if (!excluirApenasImportados) {
			excluirCalculoIndicador(connection);
			excluirData(connection);
			excluirIndicador(connection);
			excluirMeta(connection);
			excluirMunicipio(connection);
			excluirODS(connection);
			excluirPossuiVariavel(connection);
			excluirValorVariavel(connection);
			excluirVariavel(connection);
			excluirTipoPeriodo(connection);
		}

		excluirImportadosPlanilha(connection);
		excluirImportadosMunicipio(connection);
		excluirImportadosVariaveis(connection);
		excluirImportadosODS(connection);
		excluirEsquemaImportados(connection);

		return operacoes;
	}

	/**
	 * Exclui a tabela "calculo_indicador" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirCalculoIndicador(Connection connection) {
		String dropCalculoIndicador = "DROP TABLE calculo_indicador CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropCalculoIndicador)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela calculo_indicador", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela calculo_indicador", "Falha"));
		}
	}

	/**
	 * Exclui a tabela "data" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirData(Connection connection) {
		String dropData = "DROP TABLE data CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropData)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela data", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela data", "Falha"));
		}
	}

	/**
	 * Exclui a tabela "indicador" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirIndicador(Connection connection) {
		String dropIndicador = "DROP TABLE indicador CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropIndicador)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela indicador", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela indicador", "Falha"));
		}
	}

	/**
	 * Exclui a tabela "meta" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirMeta(Connection connection) {
		String dropMeta = "DROP TABLE meta CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropMeta)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela meta", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela meta", "Falha"));
		}
	}

	/**
	 * Exclui a tabela "municipio" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirMunicipio(Connection connection) {
		String dropMunicipio = "DROP TABLE municipio CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropMunicipio)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela municipio", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela municipio", "Falha"));
		}
	}

	/**
	 * Exclui a tabela "ods" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirODS(Connection connection) {
		String dropOds = "DROP TABLE ods CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropOds)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela ods", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela ods", "Falha"));
		}
	}

	/**
	 * Exclui a tabela "possui_variavel" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirPossuiVariavel(Connection connection) {
		String dropPossuiVariavel = "DROP TABLE possui_variavel CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropPossuiVariavel)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela possui_variavel", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela possui_variavel", "Falha"));
		}
	}

	/**
	 * Exclui a tabela "valor_variavel" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirValorVariavel(Connection connection) {
		String dropValorVariavel = "DROP TABLE valor_variavel CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropValorVariavel)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela valor_variavel", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela valor_variavel", "Falha"));
		}
	}

	/**
	 * Exclui a tabela "variavel" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirVariavel(Connection connection) {
		String dropVariavel = "DROP TABLE variavel CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropVariavel)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela variavel", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela variavel", "Falha"));
		}
	}

	/**
	 * Exclui o tipo "periodo" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirTipoPeriodo(Connection connection) {
		String dropTipoPeriodo = "DROP TYPE PERIODO CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropTipoPeriodo)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tipo periodo", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tipo periodo", "Falha"));
		}
	}

	/**
	 * Exclui a tabela "planilha" do esquema "importados" do banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirImportadosPlanilha(Connection connection) {
		String dropImportadosPlanilha = "DROP TABLE importados.planilha CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropImportadosPlanilha)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela importados.planilha", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela importados.planilha", "Falha"));
		}
	}

	/**
	 * Exclui a tabela "municipio" do esquema "importados" do banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirImportadosMunicipio(Connection connection) {
		String dropImportadosMunicipio = "DROP TABLE importados.municipios CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropImportadosMunicipio)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela importados.municipios", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela importados.municipios", "Falha"));
		}
	}

	/**
	 * Exclui a tabela "variaveis" do esquema "importados" do banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirImportadosVariaveis(Connection connection) {
		String dropImportadosVariaveis = "DROP TABLE importados.variaveis CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropImportadosVariaveis)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela importados.variaveis", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela importados.variaveis", "Falha"));
		}
	}

	/**
	 * Exclui a tabela "ods" do esquema "importados" do banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirImportadosODS(Connection connection) {
		String dropImportadosVariaveis = "DROP TABLE importados.ods CASCADE;";
		try (PreparedStatement pstm = connection.prepareStatement(dropImportadosVariaveis)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir tabela importados.ods", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir tabela importados.ods", "Falha"));
		}
	}

	/**
	 * Exclui o esquema "importado" do banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void excluirEsquemaImportados(Connection connection) {
		String dropEsquemaImportados = "DROP SCHEMA importados;";
		try (PreparedStatement pstm = connection.prepareStatement(dropEsquemaImportados)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Excluir esquema importados", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Excluir esquema importados", "Falha"));
		}
	}
}
