package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.paineis;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ODSCampos extends JPanel {

	private static final long serialVersionUID = 1L;

	public ODSCampos() {
		JPanel panelMetas = new JPanel();
		panelMetas.setBounds(100, 100, 800, 65);
		
		JLabel lblNomeObjetivo = new JLabel("<html><p Align=\"justify\"><b>nome_objetivo:</b> Campo que guarda o nome do objetivo (ODS).</p></html>");
		lblNomeObjetivo.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel lblNumeroODSODS = new JLabel("<html><p Align=\"justify\"><b>numero_ods:</b> Campo que guarda o número da ODS.</p></html>");
		lblNumeroODSODS.setFont(new Font("Arial", Font.PLAIN, 12));
		GroupLayout gl_panelMetas = new GroupLayout(panelMetas);
		gl_panelMetas.setHorizontalGroup(
			gl_panelMetas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMetas.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelMetas.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNomeObjetivo, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
						.addComponent(lblNumeroODSODS, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelMetas.setVerticalGroup(
			gl_panelMetas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMetas.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNumeroODSODS, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNomeObjetivo)
					.addContainerGap(39, Short.MAX_VALUE))
		);
		panelMetas.setLayout(gl_panelMetas);
	}

}
