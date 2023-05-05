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

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import br.edu.mg.unifal.bcc.sgici.interfaces.internas.Tabelas;

import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;

/**
 * Esta classe é responsável por criar a interface que exibe os logs das
 * operações realizadas no banco de dados.
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 */
public class LogBancoDados extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;

	/**
	 * Este construtor é responsável por inicializar e configurar a interface
	 * gráfica que exibe os logs do banco de dados. Recebe como parâmetro uma lista
	 * contendo as operações executadas juntamente com o status da operação (sucesso
	 * ou falha). Essas informações são dispostas em uma tabela que apresenta a
	 * operação executada, o resultado obtido e a ação tomada. As linhas da tabela
	 * que contém as ações são marcadas em amarelo, enquanto as operações
	 * bem-sucedidas são marcadas em verde e as falhas em vermelho.
	 * 
	 * @param operacoes lista contendo as operações executadas juntamente com o
	 *                  status da operação (sucesso ou falha)
	 */
	public LogBancoDados(List<ResultadoOperacao> operacoes) {
		setTitle("Registros do Banco de Dados");
		setBounds(100, 100, 500, 300);

		JScrollPane scrollPaneTabela = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
						.addContainerGap()));

		table = new JTable();
		scrollPaneTabela.setViewportView(table);

		Tabelas.logCriar(table, operacoes);
		getContentPane().setLayout(groupLayout);
		setLocation(0, 0);
		setVisible(true);
		setResizable(true);
		setClosable(true);
		setIconifiable(false);
	}
}
