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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Classe que faz conexão com a tabela "data"
 * @author Gabriel Takahiro
 * @version 0.2
 */
public class DataDAO {

	private Connection connection;

	/**
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public DataDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Verifica se existe o ano no banco de dados. Caso exista nada é feito, mas caso não exista é criado este ano no banco de dados. 
	 * @param data ano a ser analisado
	 */
	public void atualizaData(String data) {
		try {
			String sql = "SELECT * FROM data WHERE data = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, data);
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (!(rst.next())) {
						sql = "INSERT INTO data (data, codigo_data) VALUES (?, ?)";
						try (PreparedStatement pstm2 = connection.prepareStatement(sql)) {
							pstm2.setString(1, data);
							pstm2.setString(2, data);
							pstm2.execute();
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Falha ao executar data " + data + e.getCause());
		}
	}
}
