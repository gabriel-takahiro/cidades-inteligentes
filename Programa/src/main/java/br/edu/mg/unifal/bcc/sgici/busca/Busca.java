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
package br.edu.mg.unifal.bcc.sgici.busca;

import br.edu.mg.unifal.bcc.sgici.calculo.ParametrosBuscaValorVariavel;
import br.edu.mg.unifal.bcc.sgici.modelo.Variavel;

/**
 * 
 * Interface responsável por definir os métodos de busca de valores de variáveis
 * em bases de dados, para inserção/atualização desses valores no banco de dados
 * local.
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 *
 */
public interface Busca {
	/**
	 * Realiza a busca de valores de uma variável para um único município e
	 * insere/atualiza os dados no banco de dados.
	 * 
	 * @param variavel   a variável que se deseja buscar os valores.
	 * @param parametros os parâmetros da busca, contendo o código do município e o
	 *                   ano que se deseja buscar os valores, bem como a quantidade
	 *                   de anos retroativos que devem ser buscados.
	 */
	void buscaUmMunicipio(Variavel variavel, ParametrosBuscaValorVariavel parametros);

	/**
	 * Busca o valor de uma variável para todos os municípios do Brasil, e
	 * insere/atualiza o banco de dados com os resultados.
	 * 
	 * @param variavel   a variável cujos valores serão buscados e atualizados no
	 *                   banco de dados.
	 * @param parametros os parâmetros para a busca, incluindo o ano base e o número
	 *                   de anos retroativos a serem considerados.
	 */
	void buscarTodosMunicipios(Variavel variavel, ParametrosBuscaValorVariavel parametros);
}
