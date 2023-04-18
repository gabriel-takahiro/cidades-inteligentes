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

package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Classe ConnectionFactory é responsável por gerenciar as conexões com o banco de dados
 * 
 * @author Gabriel Takahiro
 * @version 0.2
 */

public class ConnectionFactory {
	
	public static DataSource dataSource;

	/**
	 * Cria conexões com o banco de dados
	 * 
	 * @param usuario usuário do banco de dados
	 * @param senha senha do banco de dados
	 * @param database nome banco de dados utilizado
	 */
	public ConnectionFactory(String usuario, String senha, String database) {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setJdbcUrl("jdbc:postgresql://localhost/" + database);
		comboPooledDataSource.setUser(usuario);
		comboPooledDataSource.setPassword(senha);
		
		comboPooledDataSource.setAcquireRetryDelay(100);
		comboPooledDataSource.setAcquireRetryAttempts(10);
		comboPooledDataSource.setIdleConnectionTestPeriod(30);
		comboPooledDataSource.setInitialPoolSize(10);
		comboPooledDataSource.setMaxPoolSize(50);
		dataSource = comboPooledDataSource;
	}

	/**
	 * Recupera uma conexão com o banco de dados
	 * 
	 * @return uma nova conexão com o banco de dados
	 * @throws RuntimeException se não conseguir iniciar uma nova conexão no banco de dados
	 */
	public static Connection recuperarConexao() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
