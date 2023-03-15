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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas;

import java.awt.Font;
import java.beans.PropertyVetoException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;

/**
 * Classe responsável pela interface do tutorial
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class Tutorial extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	/**
	 * Executa a interface do tutorial
	 */
	public Tutorial() {
		setTitle("Tutorial");
		setBounds(100, 100, 800, 500);
		setLocation(0, 0);

		JTabbedPane tabbedPanePrincipal = new JTabbedPane(JTabbedPane.TOP);
		tabbedPanePrincipal.setFont(new Font("Arial", Font.PLAIN, 16));

		JScrollPane scrollPanelIndicadores = new JScrollPane();
		tabbedPanePrincipal.addTab("Indicadores", null, scrollPanelIndicadores, null);

		JTabbedPane tabbedPaneIndicadores = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneIndicadores.setFont(new Font("Arial", Font.PLAIN, 14));
		scrollPanelIndicadores.setViewportView(tabbedPaneIndicadores);

		JScrollPane scrollPane = new JScrollPane();
		tabbedPaneIndicadores.addTab("Campos", null, scrollPane, null);

		
		JPanel indicadorCampos = new JPanel();
		indicadorCampos.setBounds(100, 100, 800, 310);

		JLabel lblCodigo = new JLabel(
				"<html><p Align=\"justify\"><b>codigo_indicador:</b> Campo que guarda o código do indicador.</p></html>");
		lblCodigo.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblNome = new JLabel(
				"<html><p Align=\"justify\"><b>nome_indicador:</b> Campo que guarda o nome do indicador.</p></html>");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblMetodoC = new JLabel(
				"<html><p Align=\"justify\"><b>metodo_calculo:</b> Campo que guarda o método de cálculo do indicador. Os operadores são \"+\" (soma), \"-\" (subtração), \"x\" (multiplicação) e \"/\" (divisão). Os números representam o código de uma variável, números entre colchetes \"[ ]\" indicam números naturais e números entre chaves \"{ }\" representam o código de um indicador. É possível utilizar parênteses \"( )\" para determinar a precedência de uma operação.</p></html>");
		lblMetodoC.setHorizontalAlignment(SwingConstants.CENTER);
		lblMetodoC.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblNomeE = new JLabel(
				"<html><p Align=\"justify\"><b>nome_eixo:</b> Campo que guarda o nome do eixo.</p></html>");
		lblNomeE.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblTipoP = new JLabel(
				"<html><p Align=\"justify\"><b>tipo_plano:</b> Campo que guarda o tipo de plano que o indicador está atrelado.</p></html>");
		lblTipoP.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblNomeP = new JLabel(
				"<html><p Align=\"justify\"><b>nome_plano:</b> Campo que guarda o nome do plano.</p></html>");
		lblNomeP.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblDescricao = new JLabel(
				"<html><p Align=\"justify\"><b>descricao:</b> Campo que guarda uma descrição do indicador.</p></html>");
		lblDescricao.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblinformacoestecnicasCampoQue = new JLabel(
				"<html><p Align=\"justify\"><b>informacoes_tecnicas:</b> Campo que guarda informações técnicas do indicador.</p></html>");
		lblinformacoestecnicasCampoQue.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblnumerometaCampoQue = new JLabel(
				"<html><p Align=\"justify\"><b>numero_meta:</b> Campo que guarda o número da meta do indicador.</p></html>");
		lblnumerometaCampoQue.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblpadraoCampoQue = new JLabel(
				"<html><p Align=\"justify\"><b>padrao:</b> Campo que indica se o indicador é padrão ou não. Um indicador é considerado padrão se ele for um indicador baixado pelo sistema.</p></html>");
		lblpadraoCampoQue.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panelIndicadores = new GroupLayout(indicadorCampos);
		gl_panelIndicadores.setHorizontalGroup(gl_panelIndicadores.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIndicadores.createSequentialGroup().addContainerGap().addGroup(gl_panelIndicadores
						.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMetodoC, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblCodigo, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblNome, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblpadraoCampoQue, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780,
								Short.MAX_VALUE)
						.addComponent(lblNomeE, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblTipoP, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblDescricao, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblinformacoestecnicasCampoQue, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780,
								Short.MAX_VALUE)
						.addComponent(lblnumerometaCampoQue, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780,
								Short.MAX_VALUE)
						.addComponent(lblNomeP, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
						.addContainerGap()));
		gl_panelIndicadores.setVerticalGroup(gl_panelIndicadores.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIndicadores.createSequentialGroup().addContainerGap()
						.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblMetodoC, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNomeE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblTipoP, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNomeP, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblDescricao, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblinformacoestecnicasCampoQue, GroupLayout.PREFERRED_SIZE, 22,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblnumerometaCampoQue, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblpadraoCampoQue)
						.addContainerGap(30, Short.MAX_VALUE)));
		indicadorCampos.setLayout(gl_panelIndicadores);

		scrollPane.setViewportView(indicadorCampos);

		JScrollPane scrollPane_4 = new JScrollPane();
		tabbedPaneIndicadores.addTab("Mostrar Indicadores", null, scrollPane_4, null);

		JPanel mostrarIndicadores = new JPanel();

		mostrarIndicadores.setBounds(100, 100, 800, 100);

		JLabel lblTexto = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">Esta janela mostra os indicadores que estão cadastrados no banco de dados. Os indicadores são colocados em uma tabela e são mostrados apenas alguns dos campos. Os campos mostrados são: <br><br> codigo_indicador (Código), nome_indicador (Nome), numero_meta (Meta), metodo_calculo (Método de cálculo),  padrao (Indicador padrão).\r\n</p>\r\n</html>");
		lblTexto.setHorizontalAlignment(SwingConstants.LEFT);
		lblTexto.setVerticalAlignment(SwingConstants.TOP);
		lblTexto.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panelMostrarIndicadores = new GroupLayout(mostrarIndicadores);
		gl_panelMostrarIndicadores.setHorizontalGroup(gl_panelMostrarIndicadores.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMostrarIndicadores.createSequentialGroup().addContainerGap()
						.addComponent(lblTexto, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE).addContainerGap()));
		gl_panelMostrarIndicadores.setVerticalGroup(gl_panelMostrarIndicadores.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMostrarIndicadores.createSequentialGroup().addContainerGap()
						.addComponent(lblTexto, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(253, Short.MAX_VALUE)));
		mostrarIndicadores.setLayout(gl_panelMostrarIndicadores);
		scrollPane_4.setViewportView(mostrarIndicadores);

		JScrollPane scrollPane_8 = new JScrollPane();
		tabbedPaneIndicadores.addTab("Editar Indicadores", null, scrollPane_8, null);

		JPanel panel_3 = new JPanel();
		scrollPane_8.setViewportView(panel_3);

		JLabel lblPgrandeLineheight_2 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela mostra os indicadores que estão cadastrados no banco de dados. Os indicadores são colocados em uma tabela e são mostrados apenas alguns dos campos. \r\n</p>\r\n<h2>Atualizar indicador</h2>\r\n<p class = \"grande\" Align = \"justify\">\r\nPara atualizar um indicador é necessário selecionar apenas um indicador e clicar no botão \"Atualizar\". Neste momento abrirá uma nova janela idêntica à janela \"Cadastrar Indicadores\". Os campos estarão preenchidos com os valores presentes no banco de dados para o indicador selecionado. Basta realizar as mudanças e clicar em \"Atualizar indicador\", para salvar as alterações.\r\n</p>\r\n<h2>Excluir indicador</h2>\r\n<p class = \"grande\" Align = \"justify\">\r\nPara excluir indicadores basta selecionar os indicadores a serem exlcuídos e clicar no botão \"Excluir\". Neste momento abrirá uma janela de confirmação da exclusão do indicador. Nesta janela mostrará uma tabela contendo os indicadores selecionados para exclusão. Caso deseje realmente excluí-los basta clicar em \"Sim\", caso contrário, em \"Não\".\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_3.setVerticalGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2, GroupLayout.PREFERRED_SIZE, 348,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_3.setLayout(gl_panel_3);

		JScrollPane scrollPane_12 = new JScrollPane();
		tabbedPaneIndicadores.addTab("Cadastrar Indicadores", null, scrollPane_12, null);

		JPanel panel_7 = new JPanel();
		scrollPane_12.setViewportView(panel_7);

		JLabel lblPgrandeLineheight_2_2 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela possui todos os campos necessários para cadastrar um indicador. Basta preencher os campos obrigatórios e clicar em \"Cadastrar indicador\".\r\n</p>\r\n<h2>Incluir indicadores</h2>\r\n<p class = \"grande\" Align = \"justify\">\r\nAo clicar nesse botão abrirá uma nova janela contendo todos indicadores cadastrados distribuídos em uma tabela. Para adicionar esse indicar no método de cálculo basta colocar o código do indicador e clicar em \"Adicionar esse indicador\".\r\n</p>\r\n<h2>Limpar campos</h2>\r\n<p class = \"grande\" Align = \"justify\">\r\nAo clicar nesse botão todos os dados nos campos serão apagados.\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_2.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_2.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_2, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_7.setVerticalGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_2, GroupLayout.PREFERRED_SIZE, 348,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_7.setLayout(gl_panel_7);

		// Campos das variáveis
		JScrollPane scrollPanelVariaveis = new JScrollPane();
		tabbedPanePrincipal.addTab("Variáveis", null, scrollPanelVariaveis, null);

		JTabbedPane tabbedPaneVariaveis = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneVariaveis.setFont(new Font("Arial", Font.PLAIN, 14));
		scrollPanelVariaveis.setViewportView(tabbedPaneVariaveis);

		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPaneVariaveis.addTab("Campos", null, scrollPane_1, null);

		JPanel variaveisCampos = new JPanel();

		variaveisCampos.setBounds(100, 100, 800, 180);

		JLabel lblCodigoVariavel = new JLabel(
				"<html><p Align=\"justify\"><b>codigo_variavel:</b> Campo que guarda o código da variável.</p></html>");
		lblCodigoVariavel.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblTipoBanco = new JLabel(
				"<html><p Align=\"justify\"><b>tipo_banco:</b> Campo que guarda o nome do banco que está armazenado essa variável. No momento, uma variável pode ser encontrada ou no banco de dados local (BD) ou no banco de dados do SIDRA (SIDRA).</p></html>");
		lblTipoBanco.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblNomeVariavel = new JLabel(
				"<html><p Align=\"justify\"><b>nome_variavel:</b> Campo que guarda o nome da variável.</p></html>");
		lblNomeVariavel.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblCodigoBanco = new JLabel(
				"<html><p Align=\"justify\"><b>codigo_banco:</b> Campo que guarda o código necessário para acessar o banco de dados. No caso do banco de dados local, o valor é nulo.</p></html>");
		lblCodigoBanco.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblAtualizacao = new JLabel(
				"<html><p Align=\"justify\"><b>atualizacao:</b> Campo que guarda o período de atualização de uma variável. Esse valor pode ser \"Anual\" ou \"Decenal\".</p></html>");
		lblAtualizacao.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblpadraoVariaveis = new JLabel(
				"<html><p Align=\"justify\"><b>padrao:</b> Campo que indica se o variável é padrão ou não. Uma variável é considerada padrão se ela for uma variável baixada pelo sistema.</p></html>");
		lblpadraoVariaveis.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panelVariaveis = new GroupLayout(variaveisCampos);
		gl_panelVariaveis.setHorizontalGroup(gl_panelVariaveis.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelVariaveis.createSequentialGroup().addContainerGap().addGroup(gl_panelVariaveis
						.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCodigoBanco, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblTipoBanco, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblCodigoVariavel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780,
								Short.MAX_VALUE)
						.addComponent(lblNomeVariavel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780,
								Short.MAX_VALUE)
						.addComponent(lblAtualizacao, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblpadraoVariaveis, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
						.addContainerGap()));
		gl_panelVariaveis.setVerticalGroup(gl_panelVariaveis.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelVariaveis.createSequentialGroup().addContainerGap()
						.addComponent(lblCodigoVariavel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblTipoBanco)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNomeVariavel, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblCodigoBanco)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblAtualizacao)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblpadraoVariaveis)
						.addContainerGap(88, Short.MAX_VALUE)));
		variaveisCampos.setLayout(gl_panelVariaveis);

		scrollPane_1.setViewportView(variaveisCampos);

		JScrollPane scrollPane_5 = new JScrollPane();
		tabbedPaneVariaveis.addTab("Mostrar Variáveis", null, scrollPane_5, null);

		JPanel panel = new JPanel();
		scrollPane_5.setViewportView(panel);

		JLabel lblPgrandeLineheight = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">Esta janela mostra as variáveis que estão cadastrados no banco de dados. As variáveis são colocadas em uma tabela e são mostrados apenas alguns dos campos. Os campos mostrados são: <br><br> codigo_variavel (Código), tipo_banco (Banco), nome_variavel (Nome), codigo_banco (Código do banco),  atualizacao (Atualização), padrao (Variável padrão).\r\n</p>\r\n</html>");
		lblPgrandeLineheight.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(253, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);

		JScrollPane scrollPane_9 = new JScrollPane();
		tabbedPaneVariaveis.addTab("Editar Variáveis", null, scrollPane_9, null);

		JPanel panel_4 = new JPanel();
		scrollPane_9.setViewportView(panel_4);

		JLabel lblPgrandeLineheight_2_1 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela mostra as variáveis que estão cadastradas no banco de dados. As variáveis são colocados em uma tabela e são mostrados apenas alguns dos campos. \r\n</p>\r\n<h2>Atualizar variáveis</h2>\r\n<p class = \"grande\" Align = \"justify\">\r\nPara atualizar uma variável é necessário selecionar apenas uma variável e clicar no botão \"Atualizar\". Neste momento abrirá uma nova janela idêntica à janela \"Cadastrar Variáveis\". Os campos estarão preenchidos com os valores presentes no banco de dados para a variável selecionada. Basta realizar as mudanças e clicar em \"Atualizar variável\", para salvar as alterações.\r\n</p>\r\n<h2>Excluir variáveis</h2>\r\n<p class = \"grande\" Align = \"justify\">\r\nPara excluir variáveis basta selecionar as variáveis a serem exlcuídas e clicar no botão \"Excluir\". Neste momento abrirá uma janela de confirmação da exclusão de variável. Nesta janela mostrará uma tabela contendo as variáveis selecionadas para exclusão. Caso deseje realmente excluí-las basta clicar em \"Sim\", caso contrário, em \"Não\".\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_1.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_1, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_4.setVerticalGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_1, GroupLayout.PREFERRED_SIZE, 348,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_4.setLayout(gl_panel_4);

		JScrollPane scrollPane_13 = new JScrollPane();
		tabbedPaneVariaveis.addTab("Cadastrar Variáveis", null, scrollPane_13, null);

		JPanel panel_8 = new JPanel();
		scrollPane_13.setViewportView(panel_8);

		JLabel lblPgrandeLineheight_2_2_1 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela possui todos os campos necessários para cadastrar uma variável. Basta preencher os campos obrigatórios e clicar em \"Cadastrar variável\".\r\n</p><br>\r\n<p class = \"grande\" Align = \"justify\">\r\nSe o tipo de banco for \"SIDRA\" o código do banco é obrigatório, caso contrário o código de banco deve ser nulo.\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_2_1.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_2_1, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_8.setVerticalGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_2_1, GroupLayout.PREFERRED_SIZE, 348,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_8.setLayout(gl_panel_8);

		JScrollPane scrollPanelMetas = new JScrollPane();
		tabbedPanePrincipal.addTab("Metas", null, scrollPanelMetas, null);

		JTabbedPane tabbedPaneMetas = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneMetas.setFont(new Font("Arial", Font.PLAIN, 14));
		scrollPanelMetas.setViewportView(tabbedPaneMetas);

		JScrollPane scrollPane_2 = new JScrollPane();
		tabbedPaneMetas.addTab("Campos", null, scrollPane_2, null);

		// Campos da meta
		JPanel metasCampos = new JPanel();

		metasCampos.setBounds(100, 100, 800, 90);

		JLabel lblNumeroMeta = new JLabel(
				"<html><p Align=\"justify\"><b>numero_meta:</b> Campo que guarda o número da meta.</p></html>");
		lblNumeroMeta.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblTextoMeta = new JLabel(
				"<html><p Align=\"justify\"><b>texto_meta:</b> Campo que guarda o texto da meta.</p></html>");
		lblTextoMeta.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblNumeroODS = new JLabel(
				"<html><p Align=\"justify\"><b>numero_ods:</b> Campo que guarda o número da ODS que a meta está atrelada.</p></html>");
		lblNumeroODS.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panelMetas = new GroupLayout(metasCampos);
		gl_panelMetas.setHorizontalGroup(gl_panelMetas.createParallelGroup(Alignment.LEADING).addGroup(gl_panelMetas
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panelMetas.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTextoMeta, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblNumeroMeta, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblNumeroODS, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
				.addContainerGap()));
		gl_panelMetas.setVerticalGroup(gl_panelMetas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMetas.createSequentialGroup().addContainerGap()
						.addComponent(lblNumeroMeta, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblTextoMeta)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNumeroODS, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(103, Short.MAX_VALUE)));
		metasCampos.setLayout(gl_panelMetas);

		scrollPane_2.setViewportView(metasCampos);

		JScrollPane scrollPane_6 = new JScrollPane();
		tabbedPaneMetas.addTab("Mostrar Metas", null, scrollPane_6, null);

		JPanel panel_1 = new JPanel();
		scrollPane_6.setViewportView(panel_1);

		JLabel lblPgrandeLineheight_1 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">Esta janela mostra as metas que estão cadastradas no banco de dados. As metas são colocados em uma tabela. Todos os campos são mostrados.\r\n</p>\r\n</html>");
		lblPgrandeLineheight_1.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_1.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_1, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1
						.createSequentialGroup().addContainerGap().addComponent(lblPgrandeLineheight_1,
								GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(246, Short.MAX_VALUE)));
		panel_1.setLayout(gl_panel_1);

		JScrollPane scrollPane_10 = new JScrollPane();
		tabbedPaneMetas.addTab("Editar Metas", null, scrollPane_10, null);

		JPanel panel_5 = new JPanel();
		scrollPane_10.setViewportView(panel_5);

		JLabel lblPgrandeLineheight_2_1_1 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela mostra as metas que estão cadastradas no banco de dados. As metas são colocados em uma tabela e são mostrados apenas alguns dos campos. \r\n</p>\r\n<h2>Atualizar metas</h2>\r\n<p class = \"grande\" Align = \"justify\">\r\nPara atualizar uma meta é necessário selecionar apenas uma meta e clicar no botão \"Atualizar\". Neste momento abrirá uma nova janela idêntica à janela \"Cadastrar Metas\". Os campos estarão preenchidos com os valores presentes no banco de dados para a meta selecionada. Basta realizar as mudanças e clicar em \"Atualizar meta\", para salvar as alterações.\r\n</p>\r\n<h2>Excluir metas</h2>\r\n<p class = \"grande\" Align = \"justify\">\r\nPara excluir metas basta selecionar as metas a serem exlcuídas e clicar no botão \"Excluir\". Neste momento abrirá uma janela de confirmação da exclusão de metas. Nesta janela mostrará uma tabela contendo as metas selecionadas para exclusão. Caso deseje realmente excluí-las basta clicar em \"Sim\", caso contrário, em \"Não\".\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_1_1, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_5.setVerticalGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_1_1, GroupLayout.PREFERRED_SIZE, 348,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_5.setLayout(gl_panel_5);

		JScrollPane scrollPane_14 = new JScrollPane();
		tabbedPaneMetas.addTab("Cadastrar Metas", null, scrollPane_14, null);

		JPanel panel_9 = new JPanel();
		scrollPane_14.setViewportView(panel_9);

		JLabel lblPgrandeLineheight_2_2_1_1 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n       \r\n  \tp.colorido{\r\n\t\tcolor: green;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela possui todos os campos necessários para cadastrar uma meta. Basta preencher os campos obrigatórios e clicar em \"Cadastrar meta\".\r\n</p><br>\r\n<p class = \"grande\" Align = \"justify\">\r\nA meta deve pertencer a mesma ODS.\r\n</p>\r\n<br>\r\n<p class = \"colorido\" Align = \"justify\">\r\n<b>Exemplo:</b> Ao tentar cadastrar a meta \"10.1\" o campo ODS deve estar selecionado o valor 10, para cadastrar com sucesso.\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_2_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_2_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_9 = new GroupLayout(panel_9);
		gl_panel_9.setHorizontalGroup(gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_2_1_1, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_9.setVerticalGroup(gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_2_1_1, GroupLayout.PREFERRED_SIZE, 348,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_9.setLayout(gl_panel_9);

		JScrollPane scrollPanelODS = new JScrollPane();
		tabbedPanePrincipal.addTab("ODS", null, scrollPanelODS, null);

		JTabbedPane tabbedPaneODS = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneODS.setFont(new Font("Arial", Font.PLAIN, 14));
		scrollPanelODS.setViewportView(tabbedPaneODS);

		JScrollPane scrollPane_3 = new JScrollPane();
		tabbedPaneODS.addTab("Campos", null, scrollPane_3, null);

		// Campos da ODS
		JPanel odsCampos = new JPanel();

		odsCampos.setBounds(100, 100, 800, 65);

		JLabel lblNomeObjetivo = new JLabel(
				"<html><p Align=\"justify\"><b>nome_objetivo:</b> Campo que guarda o nome do objetivo (ODS).</p></html>");
		lblNomeObjetivo.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblNumeroODSODS = new JLabel(
				"<html><p Align=\"justify\"><b>numero_ods:</b> Campo que guarda o número da ODS.</p></html>");
		lblNumeroODSODS.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panelODS = new GroupLayout(odsCampos);
		gl_panelODS.setHorizontalGroup(gl_panelODS.createParallelGroup(Alignment.LEADING).addGroup(gl_panelODS
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panelODS.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNomeObjetivo, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE).addComponent(
								lblNumeroODSODS, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
				.addContainerGap()));
		gl_panelODS.setVerticalGroup(gl_panelODS.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelODS.createSequentialGroup().addContainerGap()
						.addComponent(lblNumeroODSODS, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNomeObjetivo)
						.addContainerGap(39, Short.MAX_VALUE)));
		odsCampos.setLayout(gl_panelODS);

		scrollPane_3.setViewportView(odsCampos);

		JScrollPane scrollPane_7 = new JScrollPane();
		tabbedPaneODS.addTab("Mostrar ODS", null, scrollPane_7, null);

		JPanel panel_2 = new JPanel();
		scrollPane_7.setViewportView(panel_2);

		JLabel lblPgrandeLineheight_1_1 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">Esta janela mostra as ODS que estão cadastradas no banco de dados. As ODS são colocados em uma tabela. Todos os campos são mostrados.\r\n</p>\r\n</html>");
		lblPgrandeLineheight_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_1_1, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2
						.createSequentialGroup().addContainerGap().addComponent(lblPgrandeLineheight_1_1,
								GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(246, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

		JScrollPane scrollPane_11 = new JScrollPane();
		tabbedPaneODS.addTab("Editar ODS", null, scrollPane_11, null);

		JPanel panel_6 = new JPanel();
		scrollPane_11.setViewportView(panel_6);

		JLabel lblPgrandeLineheight_2_1_1_1 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela mostra as ODS que estão cadastradas no banco de dados. As ODS são colocados em uma tabela e são mostrados apenas alguns dos campos. \r\n</p>\r\n<h2>Atualizar ODS</h2>\r\n<p class = \"grande\" Align = \"justify\">\r\nPara atualizar uma ODS é necessário selecionar apenas uma ODS e clicar no botão \"Atualizar\". Neste momento abrirá uma nova janela idêntica à janela \"Cadastrar ODS\". Os campos estarão preenchidos com os valores presentes no banco de dados para a ODS selecionada. Basta realizar as mudanças e clicar em \"Atualizar ODS\", para salvar as alterações.\r\n</p>\r\n<h2>Excluir ODS</h2>\r\n<p class = \"grande\" Align = \"justify\">\r\nPara excluir ODS basta selecionar as ODS a serem exlcuídas e clicar no botão \"Excluir\". Neste momento abrirá uma janela de confirmação da exclusão de ODS. Nesta janela mostrará uma tabela contendo as ODS selecionadas para exclusão. Caso deseje realmente excluí-las basta clicar em \"Sim\", caso contrário, em \"Não\".\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_1_1_1, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_6.setVerticalGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_1_1_1, GroupLayout.PREFERRED_SIZE, 348,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_6.setLayout(gl_panel_6);

		JScrollPane scrollPane_15 = new JScrollPane();
		tabbedPaneODS.addTab("Cadastrar ODS", null, scrollPane_15, null);

		JPanel panel_10 = new JPanel();
		scrollPane_15.setViewportView(panel_10);

		JLabel lblPgrandeLineheight_2_2_1_1_1 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n       \r\n  \tp.colorido{\r\n\t\tcolor: green;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela possui todos os campos necessários para cadastrar uma ODS. Basta preencher os campos obrigatórios e clicar em \"Cadastrar ODS\".\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_2_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_2_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_2_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_10 = new GroupLayout(panel_10);
		gl_panel_10.setHorizontalGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_2_1_1_1, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panel_10.setVerticalGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_2_1_1_1, GroupLayout.PREFERRED_SIZE, 348,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_10.setLayout(gl_panel_10);

		JScrollPane scrollPanelConsultas = new JScrollPane();
		tabbedPanePrincipal.addTab("Consultas", null, scrollPanelConsultas, null);

		JPanel panelConsultas = new JPanel();
		scrollPanelConsultas.setViewportView(panelConsultas);

		JLabel lblPgrandeLineheight_2_2_1_1_1_1 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n       \r\n  \tp.colorido{\r\n\t\tcolor: green;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela realiza uma <b>consulta no banco de dados local</b>. Basta inserir um ano, um cep (para consultar um município) e selecionar os indicadores. Para ver a tabela dos indicadores, clique em \"Tabela dos indicadores\" e para ver o gráfico dos indicadores clique em \"Gráfico dos indicadores calculados\". \r\n</p>\r\n<br><br>\r\n<p class = \"grande\" Align = \"justify\">\r\nPara mais informações sobre a tabela e o gráfico veja o tutorial disponível na aba Cálculos.\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_2_1_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_2_1_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_2_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panelConsultas = new GroupLayout(panelConsultas);
		gl_panelConsultas.setHorizontalGroup(gl_panelConsultas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelConsultas.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_2_1_1_1_1, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panelConsultas.setVerticalGroup(gl_panelConsultas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelConsultas.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_2_1_1_1_1, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
						.addGap(54)));
		panelConsultas.setLayout(gl_panelConsultas);

		JScrollPane scrollPanelCalculos = new JScrollPane();
		tabbedPanePrincipal.addTab("Cálculos", null, scrollPanelCalculos, null);

		JTabbedPane tabbedPaneCalculos = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneCalculos.setFont(new Font("Arial", Font.PLAIN, 14));
		scrollPanelCalculos.setViewportView(tabbedPaneCalculos);

		JScrollPane scrollPane_16 = new JScrollPane();
		tabbedPaneCalculos.addTab("Calcular Indicadores", null, scrollPane_16, null);

		JPanel panel_12 = new JPanel();
		scrollPane_16.setViewportView(panel_12);

		JLabel lblPgrandeLineheight_2_2_1_1_1_1_1 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n       \r\n  \tp.colorido{\r\n\t\tcolor: green;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela realiza os cálculos dos indicadores selecionados. É necessário selecionar se deseja realizar o cálculo para apenas um município, por meio do cep, ou se deseja realizar o cálculo dos indicadores para todos os municípios. Além disso, é necessário informar o ano que deseja realizar os cálculos dos indicadores.\r\n<br><br>\r\nComo indicado na aba Variáveis/Campos do tutorial, vemos que cada variável possui uma taxa de atualização, podendo ser \"Anual\" (atualizado a cada ano) ou \"Decenal\" (atualizado a cada 10 anos). Uma opção extra é o campo \"Anos a mais para busca retroativa das variáveis\". Esse campo funciona da seguinte maneira: \r\n<br>\r\nÉ informado quantos anos a mais será analizado para o cálculo dos indicadores. O valor informado será acrescentado no valor padrão.\r\n</p>\r\n<br>\r\n<p class = \"colorido\"  Align=\"justify\">\r\n<b>Exemplo: </b> O ano indicado é 2022 e o valor informado para os anos a mais para busca retroativa é 5. Nesse caso, para uma variável que possui uma atualização decenal, será buscado o valor mais atualizado entre o período de 2007-2022 (15 anos). No caso de uma variável que possui uma atualização anual, será buscado o valor mais atualizado entre o período de 2016-2022 (6 anos).\r\n</p>\r\n<br><br>\r\n<p class=\"grande\" Align=\"justify\">\r\nApós realizar os cálculos será mostrado uma janela mostrando a data, o município e os indicadores que foram calculados. É possível ver a tabela dos indicadores clicando em \"Tabela dos indicadores\", e ver o gráfico dos indicadores clicando em \"Gráfico de indicadores calculados\". Caso seja feito o cálculo para todos os municípios, será necessário informar um cep para ver a tabela dos indicadores e o gráfico dos indicadores.\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_2_1_1_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_2_1_1_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_2_1_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_12 = new GroupLayout(panel_12);
		gl_panel_12.setHorizontalGroup(gl_panel_12.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_12
				.createSequentialGroup().addContainerGap()
				.addComponent(lblPgrandeLineheight_2_2_1_1_1_1_1, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
				.addContainerGap()));
		gl_panel_12.setVerticalGroup(gl_panel_12.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_12
						.createSequentialGroup().addContainerGap().addComponent(lblPgrandeLineheight_2_2_1_1_1_1_1,
								GroupLayout.PREFERRED_SIZE, 418, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(306, Short.MAX_VALUE)));
		panel_12.setLayout(gl_panel_12);

		JScrollPane scrollPane_17 = new JScrollPane();
		tabbedPaneCalculos.addTab("Recalcular Indicadores", null, scrollPane_17, null);

		JPanel panel_13 = new JPanel();
		scrollPane_17.setViewportView(panel_13);

		JLabel lblPgrandeLineheight_2_2_1_1_1_1_2 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n       \r\n  \tp.colorido{\r\n\t\tcolor: green;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nA diferença entre essa janela e a janela \"Calcular Indicadores\" é a seguinte:\r\n<br>\r\n<br>\r\n<ul><li>Na janela \"Calcular Indicadores\" é feito inicialmente uma busca no banco de dados local para verificar se o indicador já foi calculado, e se as variáveis já foram buscadas na API SIDRA. Caso já exista um resultado armazenado, não será feito o cálculo ou a busca novamente. </li>\r\n<br>\r\n<li>No caso da janela \"Recalcular Indicadores\", é feito uma busca novamente na API SIDRA para as variáveis independente se o valor já estiver salvo no banco de dados local, esse valor novo substituirá o valor antigo. O mesmo é válido para o cálculo do indicador. Caso exista um valor para um indicador, será feito novamente o cálculo com os valores novos que foram buscados no banco SIDRA, esse valor novo substituirá o valor antigo</li>\r\n</ul>\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_2_1_1_1_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_2_1_1_1_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_2_1_1_1_1_2.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_13 = new GroupLayout(panel_13);
		gl_panel_13.setHorizontalGroup(gl_panel_13.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_13
				.createSequentialGroup().addContainerGap()
				.addComponent(lblPgrandeLineheight_2_2_1_1_1_1_2, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
				.addContainerGap()));
		gl_panel_13.setVerticalGroup(gl_panel_13.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_13
						.createSequentialGroup().addContainerGap().addComponent(lblPgrandeLineheight_2_2_1_1_1_1_2,
								GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(434, Short.MAX_VALUE)));
		panel_13.setLayout(gl_panel_13);

		JScrollPane scrollPane_18 = new JScrollPane();
		tabbedPaneCalculos.addTab("Tabela dos indicadores", null, scrollPane_18, null);

		JPanel panel_14 = new JPanel();
		scrollPane_18.setViewportView(panel_14);

		JLabel lblPgrandeLineheight_2_2_1_1_1_1_3 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n       \r\n  \tp.colorido{\r\n\t\tcolor: green;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela possui duas tabelas diferentes:\r\n<br>\r\n A primeira tabela são os indicadores com valor. Nessa tabela são mostrados todos os indicadores que possuem resultados.  Essa tabela possui os seguintes campos:\r\n\t<ul>\r\n\t\t<li><b>Código do indicador:</b> Mostra o código do indicador; <br></li>\r\n\t\t<li><b>Número da ODS:</b> Mostra o número da ODS que o indicador está atrelado; </li>\r\n\t\t<li><b>Número da meta:</b> Mostra o número da meta que o indicador está atrelado; </li>\r\n\t\t<li><b>Nome do indicador:</b> Mostra o nome do indicador; </li>\r\n\t\t<li><b>Código do município:</b> Mostra o código do município; </li>\r\n\t\t<li><b>Nome do município:</b> Mostra o nome do município; </li>\r\n\t\t<li><b>Nome da UF:</b> Mostra o nome da unidade federativa do município; </li>\r\n\t\t<li><b>Data:</b> Mostra a data que está sendo calculado este indicador; </li>\r\n\t\t<li><b>Resultado:</b> Mostra o resultado desse indicador para uma data e um município específicos; </li>\r\n\t\t<li><b>Valor oficial:</b> Indica se o cálculo do indicador foi realizado apenas com valores oficiais do SIDRA; </li>\r\n\t\t<li><b>Indicador padrão:</b> Indica se o indicador faz parte dos indicadores padrões do sistema; </li>\r\n\t\t<li><b>Mostrar cálculos:</b> Botão que ao ser clicado abre uma nova janela contendo algumas informações do indicador, além de mostrar as variáveis que foram utilizadas no calculo desse indicador. As variáveis com a cor verde ;  </li>\r\n\t</ul>\r\nPara cada linha da tabela podemos ter a cor verde ou vermelha. Caso esteja na cor verde, significa que o indicador foi calculado apenas com valores oficiais, caso contrário estará na cor vermelha. Para a coluna \"Indicador padrão\" temos duas possibilidades de cores, azul ou amarelo. Caso esteja na cor azul, significa que o indicador é padrão, ou seja, é o disponibilizado pelo sistema, caso contrário estará na cor amarela.\r\n<br><br>\r\nJá a segunda tabela são os indicadores sem valor. Nessa tabela são mostrados todos os indicadores que não possuem resultados.  Essa tabela possui os seguintes campos:\r\n<ul>\r\n\t\t<li><b> Código do indicador:</b> Mostra o código do indicador; <br></li>\r\n\t\t<li><b>Nome do indicador:</b> Mostra o nome do indicador; </li>\r\n\t\t<li><b>Método de cálculo:</b> Mostra o método de cálculo do indicador; </li>\r\n\t\t<li><b>Código da variável:</b> Mostra o código da variável; </li>\r\n\t\t<li><b>Nome da variável:</b> Mostra o nome da variável; </li>\r\n\t\t<li><b>Data:</b> Mostra a data que está sendo calculado este indicador; </li>\r\n\t\t<li><b>Valor da variável:</b> Mostra o resultado dessa variável para uma data e um município específicos; </li>\r\n\t\t<li><b>Valor oficial:</b> Indica se o valor da variável foi buscado no SIDRA; </li>\r\n\t\t<li><b>Calcular:</b> Caixa para selecionar uma linha;  </li>\r\n\t</ul>\r\nAs linhas com a cor amarela servem para destacar o indicador. Essa tabela permite que insira valores para variáveis e permite que altere o campo data. Para salvar as mudanças nos valores das variáveis é necessário marcar a caixa no campo \"Calcular\" e clicar no botão \"Calcular\", lembre-se que o valor da variável será salvo no banco de dados local para a data inserida. Para recalcular o valor de um indicador basta marcar a caixa no campo \"Calcular\" na linha do indicador e clicar no botão \"Calcular\". Se o indicador for calculado com sucesso ele será transportado para a tabela de cima.\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_2_1_1_1_1_3.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_2_1_1_1_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_2_1_1_1_1_3.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_14 = new GroupLayout(panel_14);
		gl_panel_14.setHorizontalGroup(gl_panel_14.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_14
				.createSequentialGroup().addContainerGap()
				.addComponent(lblPgrandeLineheight_2_2_1_1_1_1_3, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
				.addContainerGap()));
		gl_panel_14.setVerticalGroup(gl_panel_14.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_14
				.createSequentialGroup().addContainerGap()
				.addComponent(lblPgrandeLineheight_2_2_1_1_1_1_3, GroupLayout.PREFERRED_SIZE, 700, Short.MAX_VALUE)
				.addGap(24)));
		panel_14.setLayout(gl_panel_14);

		JScrollPane scrollPane_19 = new JScrollPane();
		tabbedPaneCalculos.addTab("Gráfico dos indicadores calculados", null, scrollPane_19, null);

		JPanel panel_15 = new JPanel();
		scrollPane_19.setViewportView(panel_15);

		JLabel lblPgrandeLineheight_2_2_1_1_1_1_4 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n       \r\n  \tp.colorido{\r\n\t\tcolor: green;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela mostra os indicadores com valores de 0 a 100 em um gráfico de barras. Quando o valor estiver entre 0 e 30 a cor da barra será vermelha, caso estiver entre 30 e 70 será amarela e acima de 70 verde. Além disso, essa janela indica o ano e o município dos indicadores calculados. Outra funcionalidade é a possibilidade de filtrar o gráfico por ODS.\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_2_1_1_1_1_4.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_2_1_1_1_1_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_2_1_1_1_1_4.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_15 = new GroupLayout(panel_15);
		gl_panel_15.setHorizontalGroup(gl_panel_15.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_15
				.createSequentialGroup().addContainerGap()
				.addComponent(lblPgrandeLineheight_2_2_1_1_1_1_4, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
				.addContainerGap()));
		gl_panel_15.setVerticalGroup(gl_panel_15.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_15
						.createSequentialGroup().addContainerGap().addComponent(lblPgrandeLineheight_2_2_1_1_1_1_4,
								GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(24, Short.MAX_VALUE)));
		panel_15.setLayout(gl_panel_15);

		JScrollPane scrollPaneBancoDados = new JScrollPane();
		tabbedPanePrincipal.addTab("Banco de dados", null, scrollPaneBancoDados, null);

		JTabbedPane tabbedPaneBancoDados = new JTabbedPane(JTabbedPane.TOP);
		scrollPaneBancoDados.setViewportView(tabbedPaneBancoDados);

		JPanel panel_16 = new JPanel();
		tabbedPaneBancoDados.addTab("Cadastrar tabelas", null, panel_16, null);

		JLabel lblPgrandeLineheight_2_2_1_1_1_1_5 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n       \r\n  \tp.colorido{\r\n\t\tcolor: green;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela cria todas as tabelas necessárias do banco de dados para o funcionamento do programa.\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_2_1_1_1_1_5.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_2_1_1_1_1_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_2_1_1_1_1_5.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_16 = new GroupLayout(panel_16);
		gl_panel_16.setHorizontalGroup(gl_panel_16.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_16
				.createSequentialGroup().addContainerGap()
				.addComponent(lblPgrandeLineheight_2_2_1_1_1_1_5, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
				.addContainerGap()));
		gl_panel_16.setVerticalGroup(gl_panel_16.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_16
						.createSequentialGroup().addContainerGap().addComponent(lblPgrandeLineheight_2_2_1_1_1_1_5,
								GroupLayout.PREFERRED_SIZE, 348, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(26, Short.MAX_VALUE)));
		panel_16.setLayout(gl_panel_16);

		JPanel panel_17 = new JPanel();
		tabbedPaneBancoDados.addTab("Excluir tabelas", null, panel_17, null);

		JLabel lblPgrandeLineheight_2_2_1_1_1_1_6 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n       \r\n  \tp.colorido{\r\n\t\tcolor: green;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela exclui todas as tabelas presentes no banco de dados, inclusive os resultados presentes nela.\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_2_1_1_1_1_6.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_2_1_1_1_1_6.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_2_1_1_1_1_6.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_17 = new GroupLayout(panel_17);
		gl_panel_17.setHorizontalGroup(gl_panel_17.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_17
				.createSequentialGroup().addContainerGap()
				.addComponent(lblPgrandeLineheight_2_2_1_1_1_1_6, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
				.addContainerGap()));
		gl_panel_17.setVerticalGroup(gl_panel_17.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_17.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_2_1_1_1_1_6, GroupLayout.PREFERRED_SIZE, 385,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_17.setLayout(gl_panel_17);

		JPanel panel_18 = new JPanel();
		tabbedPaneBancoDados.addTab("Importar tabelas", null, panel_18, null);

		JLabel lblPgrandeLineheight_2_2_1_1_1_1_6_1 = new JLabel(
				"<html>\r\n<style>\r\n\tp.grande{\r\n\t\tline-height: 0.8;\r\n\t}\r\n       \r\n  \tp.colorido{\r\n\t\tcolor: green;\r\n\t}\r\n</style>\r\n<p class = \"grande\" Align=\"justify\">\r\nEsta janela importa os indicadores, variáveis e metas classificados como \"padrão\". É necessário que este executável esteja na mesma pasta dos seguintes arquivos: <br> TabelaIndicadores.csv, TabelaMunicipios.csv e TabelaVariaveis.csv.\r\n</p>\r\n</html>");
		lblPgrandeLineheight_2_2_1_1_1_1_6_1.setVerticalAlignment(SwingConstants.TOP);
		lblPgrandeLineheight_2_2_1_1_1_1_6_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblPgrandeLineheight_2_2_1_1_1_1_6_1.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panel_18 = new GroupLayout(panel_18);
		gl_panel_18.setHorizontalGroup(gl_panel_18.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_18
				.createSequentialGroup().addContainerGap()
				.addComponent(lblPgrandeLineheight_2_2_1_1_1_1_6_1, GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
				.addContainerGap()));
		gl_panel_18.setVerticalGroup(gl_panel_18.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_18.createSequentialGroup().addContainerGap()
						.addComponent(lblPgrandeLineheight_2_2_1_1_1_1_6_1, GroupLayout.PREFERRED_SIZE, 385,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_18.setLayout(gl_panel_18);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(10)
						.addComponent(tabbedPanePrincipal, GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE).addGap(10)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(11)
						.addComponent(tabbedPanePrincipal, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE).addGap(11)));
		getContentPane().setLayout(groupLayout);

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(true);
		setClosable(true);
		setResizable(true);
		setIconifiable(true);
	}

	@Override
	public void abrirJanela(JInternalFrame janelaTutorial, JDesktopPane desktopPane) {
		if (janelaTutorial.isClosed()) {
			Tutorial tutorial = new Tutorial();
			desktopPane.add(tutorial);
			JanelaPrincipal.setJanelaTutorial(tutorial);
			return;
		}
		if (janelaTutorial.isVisible()) {
			janelaTutorial.moveToFront();
			try {
				janelaTutorial.setMaximum(true);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
			return;
		}
		janelaTutorial.setVisible(true);
		janelaTutorial.moveToFront();
		try {
			janelaTutorial.setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}
}
