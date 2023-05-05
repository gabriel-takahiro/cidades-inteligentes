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

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.sgici.banco_dados.ExcluirTabelaBD;
import br.edu.mg.unifal.bcc.sgici.banco_dados.GerenciaBD;
import br.edu.mg.unifal.bcc.sgici.banco_dados.LogBancoDados;
import br.edu.mg.unifal.bcc.sgici.banco_dados.ResultadoOperacao;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;

/**
 * Classe responsável pela criação da interface gráfica que confirma o cálculo
 * dos indicadores selecionados para todos os municípios.
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 */
public class ConfirmaPorEscrito extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldConfirmacao;

	/**
	 * Cria uma janela de confirmação por escrito para executar operações críticas,
	 * como exclusão de tabelas e restaurar tabelas para o modelo padrão.
	 * 
	 * @param operacao Operação que será realizada
	 */
	public ConfirmaPorEscrito(String operacao) {
		setTitle("Confirmação ");
		if (operacao.equals("Padrao")) {
			setTitle(getTitle() + "Restaurar Padrão");
		} else {
			setTitle(getTitle() + "Excluir tabelas");
		}
		setBounds(100, 100, 260, 175);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblConfirmacao = new JLabel("Digite 'Sim' para confirmar:");
		lblConfirmacao.setHorizontalAlignment(SwingConstants.CENTER);

		lblConfirmacao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblConfirmacao.setBounds(10, 11, 224, 30);
		contentPanel.add(lblConfirmacao);

		textFieldConfirmacao = new JTextField();
		textFieldConfirmacao.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldConfirmacao.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldConfirmacao.setBounds(10, 52, 224, 30);
		contentPanel.add(textFieldConfirmacao);
		textFieldConfirmacao.setColumns(10);

		JButton btnNewButton = new JButton("Confirma");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldConfirmacao.getText().equals("Sim")) {
					List<ResultadoOperacao> operacoes = new ArrayList<>();
					if (operacao.equals("Padrao")) {
						operacoes = GerenciaBD.restaurarPadrao();
					} else {
						operacoes.add(new ResultadoOperacao("Excluir tabelas", null));
						operacoes.addAll(ExcluirTabelaBD.excluirTodas(false));
					}
					JanelaPrincipal.abrirJanelas(new LogBancoDados(operacoes));
					JanelaPrincipal.atualizarTudo();
					dispose();
				} else {
					new JanelaMensagem("Confirmação inválida! Digite 'Sim'.");
				}
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton.setBounds(10, 90, 224, 35);
		contentPanel.add(btnNewButton);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
	}
}
