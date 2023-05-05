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
 * Essa classe é responsável por realizar as operações que excluem informações
 * no banco de dados, incluindo tipos, esquemas e tabelas. Basicamente, todos os
 * processos relacionados à exclusão no banco de dados são realizados nessa
 * classe.
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class ExcluirTabelaBD {

	private static List<ResultadoOperacao> operacoes;

	/**
	 * Este método é responsável por executar as operações que excluem tabelas do
	 * banco de dados. Dependendo do valor do parâmetro booleano
	 * "excluirApenasImportados", pode-se remover todas as tabelas do banco de dados
	 * ou apenas as tabelas pertencentes ao esquema "importados". Caso o valor seja
	 * falso, todas as informações, incluindo tabelas, tipos e esquemas, serão
	 * excluídas. Se for verdadeiro, apenas as tabelas presentes no esquema
	 * "importados" serão excluídas, juntamente com o esquema.
	 * 
	 * @param excluirApenasImportados verifica se é necessario excluir apenas as
	 *                                tabelas presentes no esquema "importados".
	 * 
	 * @return Lista de objetos contendo informações das operações realizadas no
	 *         banco de dados, incluindo o nome da operação e o seu status (sucesso
	 *         ou falha).
	 */
	public static List<ResultadoOperacao> excluirTodas(boolean excluirApenasImportados) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
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
		} catch (Exception e) {
			return operacoes;
		}

	}

	/**
	 * Esse método executa a operação de exclusão da tabela "calculo_indicador" no
	 * banco de dados. O status (sucesso ou falha) da operação será registrado na
	 * lista de operações, juntamente com o nome da operação ("Excluir tabela
	 * calculo_indicador").
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
	 * Este método executa a operação de exclusão da tabela "data" no banco de
	 * dados. O status (sucesso ou falha) da operação será registrado na lista de
	 * operações, juntamente com o nome da operação ("Excluir tabela data").
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
	 * Este método executa a operação de exclusão da tabela "indicador" no banco de
	 * dados. O status (sucesso ou falha) da operação será registrado na lista de
	 * operações, juntamente com o nome da operação ("Excluir tabela indicador").
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
	 * Este método executa a operação de exclusão da tabela "meta" no banco de
	 * dados. O status (sucesso ou falha) da operação será registrado na lista de
	 * operações, juntamente com o nome da operação ("Excluir tabela meta").
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
	 * Este método executa a operação de exclusão da tabela "municipio" no banco de
	 * dados. O status (sucesso ou falha) da operação será registrado na lista de
	 * operações, juntamente com o nome da operação ("Excluir tabela municipio").
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
	 * Este método executa a operação de exclusão da tabela "ods" no banco de dados.
	 * O status (sucesso ou falha) da operação será registrado na lista de
	 * operações, juntamente com o nome da operação ("Excluir tabela ods").
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
	 * Este método executa a operação de exclusão da tabela "possui_variavel" no
	 * banco de dados. O status (sucesso ou falha) da operação será registrado na
	 * lista de operações, juntamente com o nome da operação ("Excluir tabela
	 * possui_variavel").
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
	 * Este método executa a operação de exclusão da tabela "valor_variavel" no
	 * banco de dados. O status (sucesso ou falha) da operação será registrado na
	 * lista de operações, juntamente com o nome da operação ("Excluir tabela
	 * valor_variavel").
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
	 * Este método executa a operação de exclusão da tabela "variavel" no banco de
	 * dados. O status (sucesso ou falha) da operação será registrado na lista de
	 * operações, juntamente com o nome da operação ("Excluir tabela variavel").
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
	 * Este método executa a operação de exclusão do tipo "periodo" no banco de
	 * dados. O status (sucesso ou falha) da operação será registrado na lista de
	 * operações, juntamente com o nome da operação ("Excluir tipo periodo").
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
	 * Este método executa a operação de exclusão da tabela "planilha" do esquema
	 * "importados" no banco de dados. O status (sucesso ou falha) da operação será
	 * registrado na lista de operações, juntamente com o nome da operação ("Excluir
	 * tabela importados.planilha").
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
	 * Este método executa a operação de exclusão da tabela "municipios" do esquema
	 * "importados" no banco de dados. O status (sucesso ou falha) da operação será
	 * registrado na lista de operações, juntamente com o nome da operação ("Excluir
	 * tabela importados.municipios").
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
	 * Este método executa a operação de exclusão da tabela "variaveis" do esquema
	 * "importados" no banco de dados. O status (sucesso ou falha) da operação será
	 * registrado na lista de operações, juntamente com o nome da operação ("Excluir
	 * tabela importados.variaveis").
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
	 * Este método executa a operação de exclusão da tabela "ods" do esquema
	 * "importados" no banco de dados. O status (sucesso ou falha) da operação será
	 * registrado na lista de operações, juntamente com o nome da operação ("Excluir
	 * tabela importados.ods").
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
	 * Este método executa a operação de exclusão do esquema "importados" do banco
	 * de dados. O status (sucesso ou falha) da operação será registrado na lista de
	 * operações, juntamente com o nome da operação ("Excluir esquema importados").
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
