package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.paineis;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class VariaveisCampos extends JPanel {

	private static final long serialVersionUID = 1L;

	public VariaveisCampos() {
		JPanel panelIndicadores = new JPanel();
		panelIndicadores.setBounds(100, 100, 800, 180);

		JLabel lblCodigo = new JLabel(
				"<html><p Align=\"justify\"><b>codigo_variavel:</b> Campo que guarda o código da variável.</p></html>");
		lblCodigo.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lblNome = new JLabel(
				"<html><p Align=\"justify\"><b>tipo_banco:</b> Campo que guarda o nome do banco que está armazenado essa variável. No momento, uma variável pode ser encontrada ou no banco de dados local (BD) ou no banco de dados do SIDRA (SIDRA).</p></html>");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lblNomeE = new JLabel(
				"<html><p Align=\"justify\"><b>nome_variavel:</b> Campo que guarda o nome da variável.</p></html>");
		lblNomeE.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lblTipoP = new JLabel(
				"<html><p Align=\"justify\"><b>codigo_banco:</b> Campo que guarda o código necessário para acessar o banco de dados. No caso do banco de dados local, o valor é nulo.</p></html>");
		lblTipoP.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lblNomeP = new JLabel(
				"<html><p Align=\"justify\"><b>atualizacao:</b> Campo que guarda o período de atualização de uma variável. Esse valor pode ser \"Trimestral\", \"Anual\" ou \"Decenal\".</p></html>");
		lblNomeP.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lblpadraoCampoQue = new JLabel(
				"<html><p Align=\"justify\"><b>padrao:</b> Campo que indica se o variável é padrão ou não. Uma variável é considerada padrão se ela for uma variável baixada pelo sistema.</p></html>");
		lblpadraoCampoQue.setFont(new Font("Arial", Font.PLAIN, 12));
		GroupLayout gl_panelIndicadores = new GroupLayout(panelIndicadores);
		gl_panelIndicadores.setHorizontalGroup(gl_panelIndicadores.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIndicadores.createSequentialGroup().addContainerGap().addGroup(gl_panelIndicadores
						.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNome, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblCodigo, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblNomeE, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblTipoP, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblNomeP, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblpadraoCampoQue, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
						.addContainerGap()));
		gl_panelIndicadores
				.setVerticalGroup(gl_panelIndicadores.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelIndicadores.createSequentialGroup().addContainerGap()
								.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblNome)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNomeE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblTipoP, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNomeP, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblpadraoCampoQue,
										GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(139, Short.MAX_VALUE)));
		panelIndicadores.setLayout(gl_panelIndicadores);
	}

}
