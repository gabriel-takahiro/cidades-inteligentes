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
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal.JanelaPrincipal;

/**
 * Classe responsável pela criação das tabelas do banco de dados
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class CriarBD {

	private static List<ResultadoOperacao> operacoes;

	/**
	 * Cria todas as tabelas do banco de dados
	 */
	public static void criarTudo() {
		Connection connection = ConnectionFactory.recuperarConexao();

		operacoes = new ArrayList<ResultadoOperacao>();

		criarODS(connection);
		criarMeta(connection);
		criarIndicador(connection);
		criarTipoPeriodo(connection);
		criarVariavel(connection);
		criarPossuiVariavel(connection);
		criarData(connection);
		criarMunicipio(connection);
		criarCalculoIndicador(connection);
		criarValorVariavel(connection);

		JanelaPrincipal.abrirJanelas(new LogBancoDados(operacoes));
	}

	/**
	 * Cria o esquema "importados" e todas as suas tabelas
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public static void criarTudoImportados(Connection connection) {
		operacoes = new ArrayList<ResultadoOperacao>();

		criarEsquemaImportados(connection);
		criarImportadosPlanilha(connection);
		criarImportadosMunicipio(connection);
		criarImportadosVariaveis(connection);
		criarImportadosODS(connection);

		JanelaPrincipal.abrirJanelas(new LogBancoDados(operacoes));
	}

	/**
	 * Cria a tabela "ods" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void criarODS(Connection connection) {
		String criarOds = "CREATE TABLE ods(" + "    numero_ods INTEGER PRIMARY KEY,"
				+ "    nome_objetivo VARCHAR(200) NOT NULL" + ");";
		try (PreparedStatement pstm = connection.prepareStatement(criarOds)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela ODS", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela ODS", "Falha"));
		}
	}

	/**
	 * Cria a tabela "meta" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void criarMeta(Connection connection) {
		String criarMeta = "CREATE TABLE meta(" + "    numero_meta VARCHAR(20) PRIMARY KEY,"
				+ "    texto_meta VARCHAR(1500) NOT NULL," + "    numero_ods INTEGER NOT NULL,"
				+ "    FOREIGN KEY(numero_ods)" + "    REFERENCES ods(numero_ods)" + "    ON DELETE RESTRICT"
				+ "    ON UPDATE RESTRICT" + ");";
		try (PreparedStatement pstm = connection.prepareStatement(criarMeta)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela meta", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela meta", "Falha"));
		}
	}

	/**
	 * Cria a tabela "indicador" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void criarIndicador(Connection connection) {
		String criarIndicador = "CREATE TABLE indicador(" + "	codigo_indicador SERIAL PRIMARY KEY,"
				+ "	nome_indicador VARCHAR(500) NOT NULL," + "	metodo_calculo VARCHAR(100) UNIQUE NOT NULL,"
				+ "    nome_eixo VARCHAR(250) NOT NULL," + "    tipo_plano VARCHAR(100),"
				+ "    nome_plano VARCHAR(200)," + "    descricao VARCHAR(1500) NOT NULL,"
				+ "    informacoes_tecnicas VARCHAR(1500)," + "    numero_meta VARCHAR(20),"
				+ "    padrao BOOLEAN NOT NULL," + "    FOREIGN KEY(numero_meta)" + "    REFERENCES meta(numero_meta)"
				+ "    ON DELETE RESTRICT" + "    ON UPDATE CASCADE" + ");";
		try (PreparedStatement pstm = connection.prepareStatement(criarIndicador)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela indicador", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela indicador", "Falha"));
		}
	}

	/**
	 * Cria o tipo "periodo" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void criarTipoPeriodo(Connection connection) {
		String criarTipoPeriodo = "CREATE TYPE PERIODO AS ENUM('Trimestral', 'Anual', 'Decenal');";
		try (PreparedStatement pstm = connection.prepareStatement(criarTipoPeriodo)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tipo periodo", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tipo periodo", "Falha"));
		}
	}

	/**
	 * Cria a tabela "variavel" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void criarVariavel(Connection connection) {
		String criarVariavel = "CREATE TABLE variavel(" + "	codigo_variavel INTEGER PRIMARY KEY,"
				+ "	tipo_banco VARCHAR(50) NOT NULL," + "	nome_variavel VARCHAR(500) NOT NULL,"
				+ "	codigo_banco VARCHAR(500)," + " atualizacao PERIODO NOT NULL," + " padrao BOOLEAN NOT NULL" + ");";
		try (PreparedStatement pstm = connection.prepareStatement(criarVariavel)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela variavel", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela variavel", "Falha"));
		}
	}

	/**
	 * Cria a tabela "possui_variavel" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void criarPossuiVariavel(Connection connection) {
		String criarPossuiVariavel = "CREATE TABLE possui_variavel(" + "	codigo_variavel INTEGER,"
				+ "	codigo_indicador INTEGER," + "	PRIMARY KEY(codigo_variavel, codigo_indicador),"
				+ "	FOREIGN KEY(codigo_variavel)" + "	REFERENCES variavel(codigo_variavel)" + "	ON DELETE RESTRICT"
				+ "	ON UPDATE CASCADE," + "	FOREIGN KEY(codigo_indicador)" + "	REFERENCES indicador(codigo_indicador)"
				+ "	ON DELETE CASCADE" + "	ON UPDATE CASCADE" + ");";
		try (PreparedStatement pstm = connection.prepareStatement(criarPossuiVariavel)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela possui_variavel", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela possui_variavel", "Falha"));
		}
	}

	/**
	 * Cria a tabela "data" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void criarData(Connection connection) {
		String criarData = "CREATE TABLE data(" + "	data VARCHAR(50) PRIMARY KEY,"
				+ "	codigo_data VARCHAR(100) NOT NULL" + ");";
		try (PreparedStatement pstm = connection.prepareStatement(criarData)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela data", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela data", "Falha"));
		}
	}

	/**
	 * Cria a tabela "municipio" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void criarMunicipio(Connection connection) {
		String criarMunicipio = "CREATE TABLE municipio(" + "	codigo_municipio INTEGER PRIMARY KEY ,"
				+ "	nome_municipio VARCHAR(100) NOT NULL," + "	nome_uf VARCHAR(100) NOT NULL" + ");";
		try (PreparedStatement pstm = connection.prepareStatement(criarMunicipio)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela municipio", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela municipio", "Falha"));
		}
	}

	/**
	 * Cria a tabela "calculo_indicador" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void criarCalculoIndicador(Connection connection) {
		String criarCalculoIndicador = "CREATE TABLE calculo_indicador(" + "	codigo_indicador INTEGER,"
				+ "	codigo_municipio INTEGER," + "	data VARCHAR(50)," + "	resultado VARCHAR(50) NOT NULL,"
				+ " data_variaveis VARCHAR(100)," + " valor_oficial BOOLEAN,"
				+ "	PRIMARY KEY(codigo_indicador, codigo_municipio, data)," + "	FOREIGN KEY(codigo_indicador)"
				+ "	REFERENCES indicador(codigo_indicador)" + "	ON DELETE RESTRICT" + "	ON UPDATE CASCADE,"
				+ "	FOREIGN KEY(codigo_municipio)" + "	REFERENCES municipio(codigo_municipio)" + "	ON DELETE RESTRICT"
				+ "	ON UPDATE CASCADE," + "	FOREIGN KEY(data)" + "	REFERENCES data (data)" + "	ON DELETE RESTRICT"
				+ "	ON UPDATE CASCADE" + ");";
		try (PreparedStatement pstm = connection.prepareStatement(criarCalculoIndicador)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela calculo_indicador", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela calculo_indicador", "Falha"));
		}
	}

	/**
	 * Cria a tabela "valor_variavel" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void criarValorVariavel(Connection connection) {
		String criarValorVariavel = "CREATE TABLE valor_variavel(" + "	codigo_variavel INTEGER,"
				+ "	codigo_municipio INTEGER," + "	data VARCHAR(50)," + "	valor VARCHAR(50),"
				+ "	valor_atualizado BOOLEAN," + " valor_oficial BOOLEAN,"
				+ "	PRIMARY KEY(codigo_variavel, codigo_municipio, data)," + "	FOREIGN KEY(codigo_variavel)"
				+ "	REFERENCES variavel(codigo_variavel)" + "	ON DELETE RESTRICT" + "	ON UPDATE CASCADE,"
				+ "	FOREIGN KEY(codigo_municipio)" + "	REFERENCES municipio(codigo_municipio)" + "	ON DELETE RESTRICT"
				+ "	ON UPDATE CASCADE," + "	FOREIGN KEY(data)" + "	REFERENCES data (data)" + "	ON DELETE RESTRICT"
				+ "	ON UPDATE CASCADE" + ");";
		try (PreparedStatement pstm = connection.prepareStatement(criarValorVariavel)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela valor_variavel", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela valor_variavel", "Falha"));
		}
	}

	/**
	 * Cria o esquema "importados" no banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public static void criarEsquemaImportados(Connection connection) {
		String criaEsquemaImportados = "CREATE SCHEMA importados;";
		try (PreparedStatement pstm = connection.prepareStatement(criaEsquemaImportados)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar esquema importados", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar esquema importados", "Falha"));
		}
	}

	/**
	 * Cria a tabela "planilha" no esquema "importados" do banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public static void criarImportadosPlanilha(Connection connection) {
		String criaImportadosPlanilha = "CREATE TABLE importados.planilha(" + "    tipo_plano VARCHAR(100),"
				+ "    nome_plano VARCHAR(200)," + "    ods INTEGER," + "    nome_ods VARCHAR(200),"
				+ "    eixo VARCHAR(250)," + "    numero_meta VARCHAR(20)," + "    texto_meta VARCHAR(1500),"
				+ "    indicador VARCHAR(500)," + "    descricao VARCHAR(1500)," + "    variaveis VARCHAR(100),"
				+ "    calculo VARCHAR(100) PRIMARY KEY," + "    informacao VARCHAR(1500),"
				+ "    padrao BOOLEAN NOT NULL" + ");";
		try (PreparedStatement pstm = connection.prepareStatement(criaImportadosPlanilha)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela importados.planilha", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela importados.planilha", "Falha"));
		}
	}

	/**
	 * Cria a tabela "municipio" no esquema "importados" do banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public static void criarImportadosMunicipio(Connection connection) {
		String criarImportadosMunicipio = "CREATE TABLE importados.municipios(" + "    nome_uf VARCHAR(100),"
				+ "    codigo INTEGER PRIMARY KEY," + "    nome_municipio VARCHAR(100)" + ");";
		try (PreparedStatement pstm = connection.prepareStatement(criarImportadosMunicipio)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela importados.municipios", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela importados.municipios", "Falha"));
		}
	}

	/**
	 * Cria a tabela "variaveis" no esquema "importados" do banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public static void criarImportadosVariaveis(Connection connection) {
		String criarImportadosVariaveis = "CREATE TABLE importados.variaveis("
				+ "    codigo_variavel INTEGER PRIMARY KEY," + "    tipo_banco VARCHAR(50) NOT NULL,"
				+ "    nome_variavel VARCHAR(500) NOT NULL," + "    codigo_banco VARCHAR(500),"
				+ "    atualizacao PERIODO NOT NULL," + "    padrao BOOLEAN NOT NULL" + ");";
		try (PreparedStatement pstm = connection.prepareStatement(criarImportadosVariaveis)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela importados.variaveis", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela importados.variaveis", "Falha"));
		}
	}

	/**
	 * Cria a tabela "ods" no esquema "importados" do banco de dados
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public static void criarImportadosODS(Connection connection) {
		String criarImportadosODS = "CREATE TABLE importados.ods(numero_ods INTEGER PRIMARY KEY,"
				+ "    nome_objetivo VARCHAR(200) NOT NULL)";
		try (PreparedStatement pstm = connection.prepareStatement(criarImportadosODS)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Criar tabela importados.ods", "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Criar tabela importados.ods", "Falha"));
		}
	}
}
