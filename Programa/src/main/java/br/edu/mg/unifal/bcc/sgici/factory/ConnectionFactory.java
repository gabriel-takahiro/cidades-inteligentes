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

package br.edu.mg.unifal.bcc.sgici.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import br.edu.mg.unifal.bcc.sgici.interfaces.principal.Configuracao;

/**
 * Classe ConnectionFactory é responsável por gerenciar as conexões com o banco
 * de dados através de um DataSource. O DataSource é uma classe que permite
 * configurar e gerenciar conexões com o banco de dados de forma mais eficiente,
 * mantendo um pool de conexões abertas e prontas para uso. Esta classe utiliza
 * o ComboPooledDataSource para criar e gerenciar o pool de conexões com o banco
 * de dados PostgreSQL. Além disso, permite configurar vários parâmetros
 * relacionados à gestão do pool de conexões, como o tempo máximo de espera para
 * obter uma conexão (setAcquireRetryDelay()), o número máximo de tentativas de
 * conexão (setAcquireRetryAttempts()), o tempo de inatividade antes de testar a
 * conexão (setIdleConnectionTestPeriod()), o tamanho inicial do pool
 * (setInitialPoolSize()) e o tamanho máximo do pool (setMaxPoolSize()). Para
 * utilizar esta classe, basta criar uma instância passando os parâmetros de
 * conexão (usuário, senha e nome do banco de dados) e depois chamar o método
 * recuperarConexao() para obter uma nova conexão com o banco de dados. Caso não
 * seja possível obter uma conexão, uma RuntimeException será lançada.
 * 
 * @author Gabriel Takahiro
 * @version 0.2
 */

public class ConnectionFactory {

	/**
	 * DataSource do banco de dados
	 */
	public static DataSource dataSource;

	/**
	 * Cria um novo DataSource com as configurações informadas e o utiliza para
	 * gerenciar as conexões com o banco de dados.
	 * 
	 * @param usuario      usuário do banco de dados
	 * @param senha        senha do banco de dados
	 * @param database     nome banco de dados utilizado
	 * @param configuracao configuração de desempenho
	 */
	public ConnectionFactory(String usuario, String senha, String database, Configuracao configuracao) {
		int retryDelay = configuracao.getRetryDelay();
		int retryAttempts = configuracao.getRetryAttempts();
		int testPeriod = configuracao.getConnectTestPeriod();
		int initialPoolSize = configuracao.getPoolInicialSize();
		int poolSize = configuracao.getPoolSize();

		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setJdbcUrl("jdbc:postgresql://localhost/" + database);
		comboPooledDataSource.setUser(usuario);
		comboPooledDataSource.setPassword(senha);

		// Configurações do pool de conexões
		comboPooledDataSource.setAcquireRetryDelay(retryDelay);
		comboPooledDataSource.setAcquireRetryAttempts(retryAttempts);
		comboPooledDataSource.setIdleConnectionTestPeriod(testPeriod);
		comboPooledDataSource.setInitialPoolSize(initialPoolSize);
		comboPooledDataSource.setMaxPoolSize(poolSize);
		dataSource = comboPooledDataSource;
	}

	/**
	 * Recupera uma nova conexão com o banco de dados a partir do DataSource
	 * configurado.
	 * 
	 * @return uma nova conexão com o banco de dados
	 * @throws RuntimeException se não for possível obter uma nova conexão
	 */
	public static Connection recuperarConexao() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
