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
 * Essa classe é responsável por realizar a importação de valores das variáveis
 * no banco de dados a partir de arquivos externos no formato ".csv".
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 */
public class ImportarValores {

	private static List<ResultadoOperacao> operacoes;

	/**
	 * Este método executa a operação de importação dos valores das variáveis no
	 * banco de dados.
	 * 
	 * @return Lista de objetos contendo informações das operações realizadas no
	 *         banco de dados, incluindo o nome da operação e o seu status (sucesso
	 *         ou falha).
	 */
	public static List<ResultadoOperacao> importarValores(String diretorioArquivo) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			operacoes = new ArrayList<ResultadoOperacao>();
			operacoes.add(new ResultadoOperacao("Importar valores", null));

			importarValorVariavel(diretorioArquivo, connection);

			return operacoes;
		} catch (Exception e) {
			return operacoes;
		}
	}

	/**
	 * Este método realiza a importação da tabela "valor_variavel" no banco de
	 * dados, a qual contém informações sobre os resultados das variáveis. O status
	 * (sucesso ou falha) da operação será registrado na lista de operações,
	 * juntamente com o nome da operação ("Importar valores de " +
	 * diretorioArquivo).
	 * 
	 * @param connection conexão com o banco de dados
	 */
	private static void importarValorVariavel(String diretorioArquivo, Connection connection) {
		String importarPlanilha = "COPY valor_variavel FROM '" + diretorioArquivo + "'" + "DELIMITER '|'"
				+ "CSV HEADER;";
		try (PreparedStatement pstm = connection.prepareStatement(importarPlanilha)) {
			pstm.execute();
			operacoes.add(new ResultadoOperacao("Importar valores de " + diretorioArquivo, "Sucesso"));
		} catch (Exception e) {
			operacoes.add(new ResultadoOperacao("Importar valores de " + diretorioArquivo, "Falha"));
		}
	}
}
