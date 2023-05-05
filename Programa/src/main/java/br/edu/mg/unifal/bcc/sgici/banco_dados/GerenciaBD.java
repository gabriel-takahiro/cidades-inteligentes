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

import java.util.ArrayList;
import java.util.List;

import br.edu.mg.unifal.bcc.sgici.modelo.PossuiVariavel;

/**
 * Essa classe é responsável por executar operações de gerenciamento no banco de
 * dados.
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class GerenciaBD {

	/**
	 * Este método realiza a restauração do banco de dados para o modelo padrão, o
	 * que implica na exclusão de todas as tabelas existentes, na criação de novas
	 * tabelas e no preenchimento das mesmas com os valores padrões.
	 * 
	 * @return Lista com os resultados das operações.
	 */
	public static List<ResultadoOperacao> restaurarPadrao() {
		List<ResultadoOperacao> operacoes = new ArrayList<>();

		operacoes.add(new ResultadoOperacao("Excluir tabelas", null));
		operacoes.addAll(ExcluirTabelaBD.excluirTodas(false));

		operacoes.add(new ResultadoOperacao("Criar tabelas", null));
		operacoes.addAll(CriarBD.criarTudo());

		operacoes.addAll(ImportarBD.importarTudo());

		operacoes.add(new ResultadoOperacao("Inserir valores", null));
		operacoes.addAll(InserirBD.inserirTudo());

		operacoes.add(PossuiVariavel.insereTodos());

		return operacoes;
	}
}
