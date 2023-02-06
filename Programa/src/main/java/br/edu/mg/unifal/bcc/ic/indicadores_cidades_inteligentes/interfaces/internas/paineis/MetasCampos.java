package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.paineis;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MetasCampos extends JPanel {

	private static final long serialVersionUID = 1L;

	public MetasCampos() {
		JPanel panelMetas = new JPanel();
		panelMetas.setBounds(100, 100, 800, 90);

		JLabel lblNumeroMeta = new JLabel(
				"<html><p Align=\"justify\"><b>numero_meta:</b> Campo que guarda o número da meta.</p></html>");
		lblNumeroMeta.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lblTextoMeta = new JLabel(
				"<html><p Align=\"justify\"><b>texto_meta:</b> Campo que guarda o texto da meta.</p></html>");
		lblTextoMeta.setFont(new Font("Arial", Font.PLAIN, 12));

		JLabel lblNumeroODS = new JLabel(
				"<html><p Align=\"justify\"><b>numero_ods:</b> Campo que guarda o número da ODS que a meta está atrelada.</p></html>");
		lblNumeroODS.setFont(new Font("Arial", Font.PLAIN, 12));
		GroupLayout gl_panelMetas = new GroupLayout(panelMetas);
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
		panelMetas.setLayout(gl_panelMetas);
	}

}
