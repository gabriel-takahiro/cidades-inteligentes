package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.paineis;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MostrarIndicadores extends JPanel {

	private static final long serialVersionUID = 1L;

	public MostrarIndicadores() {
		JPanel panelMostrarIndicadores = new JPanel();
		panelMostrarIndicadores.setBounds(100, 100, 800, 100);
		
		JLabel lblTexto = new JLabel("<html><p Align=\"justify\">Esta janela mostra os indicadores que estão cadastrados no banco de dados. Os indicadores são colocados em uma tabela e são mostrados apenas alguns dos campos. Os campos mostrados são: codigo_indicador (Código), nome_indicador (Nome), numero_meta (Meta), metodo_calculo (Método de cálculo),  padrao (Indicador padrão).</p></html>");
		lblTexto.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_panelMostrarIndicadores = new GroupLayout(panelMostrarIndicadores);
		gl_panelMostrarIndicadores.setHorizontalGroup(
			gl_panelMostrarIndicadores.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMostrarIndicadores.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTexto, GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelMostrarIndicadores.setVerticalGroup(
			gl_panelMostrarIndicadores.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMostrarIndicadores.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTexto)
					.addContainerGap(127, Short.MAX_VALUE))
		);
		panelMostrarIndicadores.setLayout(gl_panelMostrarIndicadores);
	}

}
