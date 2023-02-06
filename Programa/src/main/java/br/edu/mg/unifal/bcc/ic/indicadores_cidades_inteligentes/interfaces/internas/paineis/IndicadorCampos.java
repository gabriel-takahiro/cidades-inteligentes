package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.paineis;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class IndicadorCampos extends JPanel {

	private static final long serialVersionUID = 1L;

	public IndicadorCampos() {
		JPanel panelIndicadores = new JPanel();
		panelIndicadores.setBounds(100, 100, 800, 310);
		
		JLabel lblCodigo = new JLabel("<html><p Align=\"justify\"><b>codigo_indicador:</b> Campo que guarda o código do indicador.</p></html>");
		lblCodigo.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNome = new JLabel("<html><p Align=\"justify\"><b>nome_indicador:</b> Campo que guarda o nome do indicador.</p></html>");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblMetodoC = new JLabel("<html><p Align=\"justify\"><b>metodo_calculo:</b> Campo que guarda o método de cálculo do indicador. Os operadores são \"+\" (soma), \"-\" (subtração), \"x\" (multiplicação) e \"/\" (divisão). Os números representam o código de uma variável, números entre colchetes \"[ ]\" indicam números naturais e números entre chaves \"{ }\" representam o código de um indicador. É possível utilizar parênteses \"( )\" para determinar a precedência de uma operação.</p></html>");
		lblMetodoC.setHorizontalAlignment(SwingConstants.CENTER);
		lblMetodoC.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNomeE = new JLabel("<html><p Align=\"justify\"><b>nome_eixo:</b> Campo que guarda o nome do eixo.</p></html>");
		lblNomeE.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblTipoP = new JLabel("<html><p Align=\"justify\"><b>tipo_plano:</b> Campo que guarda o tipo de plano que o indicador está atrelado.</p></html>");
		lblTipoP.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNomeP = new JLabel("<html><p Align=\"justify\"><b>nome_plano:</b> Campo que guarda o nome do plano.</p></html>");
		lblNomeP.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblDescricao = new JLabel("<html><p Align=\"justify\"><b>descricao:</b> Campo que guarda uma descrição do indicador.</p></html>");
		lblDescricao.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblinformacoestecnicasCampoQue = new JLabel("<html><p Align=\"justify\"><b>informacoes_tecnicas:</b> Campo que guarda informações técnicas do indicador.</p></html>");
		lblinformacoestecnicasCampoQue.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblnumerometaCampoQue = new JLabel("<html><p Align=\"justify\"><b>numero_meta:</b> Campo que guarda o número da meta do indicador.</p></html>");
		lblnumerometaCampoQue.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblpadraoCampoQue = new JLabel("<html><p Align=\"justify\"><b>padrao:</b> Campo que indica se o indicador é padrão ou não. Um indicador é considerado padrão se ele for o indicador baixado pelo sistema.</p></html>");
		lblpadraoCampoQue.setFont(new Font("Arial", Font.PLAIN, 12));
		GroupLayout gl_panelIndicadores = new GroupLayout(panelIndicadores);
		gl_panelIndicadores.setHorizontalGroup(
			gl_panelIndicadores.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelIndicadores.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelIndicadores.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMetodoC, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblCodigo, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblNome, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblpadraoCampoQue, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblNomeE, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblTipoP, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblDescricao, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblinformacoestecnicasCampoQue, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblnumerometaCampoQue, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblNomeP, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelIndicadores.setVerticalGroup(
			gl_panelIndicadores.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIndicadores.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblCodigo, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(lblMetodoC, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNomeE, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTipoP, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNomeP, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDescricao, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblinformacoestecnicasCampoQue, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblnumerometaCampoQue, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblpadraoCampoQue, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(75, Short.MAX_VALUE))
		);
		panelIndicadores.setLayout(gl_panelIndicadores);
	}

}
