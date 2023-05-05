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
package br.edu.mg.unifal.bcc.sgici.interfaces.internas.bd;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import br.edu.mg.unifal.bcc.sgici.banco_dados.CriarBD;
import br.edu.mg.unifal.bcc.sgici.banco_dados.LogBancoDados;
import br.edu.mg.unifal.bcc.sgici.banco_dados.ResultadoOperacao;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelasInternas;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;

/**
 * Classe responsável por criar tabelas no banco de dados.
 * 
 * <p>
 * Esta classe possui uma interface gráfica que permite ao usuário criar todas
 * as tabelas do banco de dados com um único clique.
 * </p>
 * 
 * @version 0.3
 */
public class CriarTabelas extends JanelasInternas {

	private static final long serialVersionUID = 1L;

	/**
	 * Cria uma nova janela de criação de tabelas.
	 */
	public CriarTabelas() {
		// Configuração da janela
		setTitle("Criar tabelas");
		setBounds(100, 100, 340, 117);
		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		setFocusable(true);

		// Criação dos componentes da interface gráfica
		JLabel lblCriarTabelas = new JLabel("Deseja criar todas as tabelas?");
		lblCriarTabelas.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnSim = new JButton("Sim");
		btnSim.addActionListener(new ActionListener() {
			/**
			 * Cria as tabelas no banco de dados
			 */
			public void actionPerformed(ActionEvent e) {
				List<ResultadoOperacao> operacoes = new ArrayList<>();
				operacoes.add(new ResultadoOperacao("Criar tabelas", null));
				operacoes.addAll(CriarBD.criarTudo());

				JanelaPrincipal.abrirJanelas(new LogBancoDados(operacoes));
				JanelaPrincipal.atualizarTudo();
				dispose();
			}
		});
		btnSim.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnNao = new JButton("Não");
		btnNao.addActionListener(new ActionListener() {
			/**
			 * Fecha a janela.
			 */
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNao.setFont(new Font("Arial", Font.PLAIN, 16));
		
		// Configuração do layout da janela
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addContainerGap()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblCriarTabelas, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
										.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
												.addComponent(btnSim, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(btnNao, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)))
								.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addComponent(lblCriarTabelas, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnSim, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnNao, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addContainerGap(197, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);
	}

	/**
	 * Sobrescreve o método da classe JanelasInternas para armazenar a referência à
	 * janela CriarTabelas na JanelaPrincipal.
	 */
	@Override
	protected void abrirJanelaJDesktopPane(JInternalFrame janela) {
		JanelaPrincipal.setJanelaCriarTabelas((CriarTabelas) janela);
	}

	/**
	 * Cria uma nova instância da classe CriarTabelas.
	 */
	@Override
	protected JInternalFrame instanciaClasseAberta() {
		return new CriarTabelas();
	}
}