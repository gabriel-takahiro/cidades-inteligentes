package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.MostrarResultadoParaTodos;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Indicador;

public class JanelaConfirmaTodosMunicipios extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	public JanelaConfirmaTodosMunicipios(String data, boolean recalcular,
			ArrayList<Indicador> listaIndicadoresSelecionados, int valorRetroativo) {
		setBounds(100, 100, 600, 173);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel(
				"Deseja realizar o cálculo para todos os municípios para o ano de " + data + "?");
		if (recalcular) {
			lblNewLabel = new JLabel(
					"Deseja recalcular os indicadores para todos os municípios para o ano de " + data + "?");
		}
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 564, 30);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 82, 584, 52);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("Sim");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							CalculoIndicador.calcularTodosMunicipios(data, recalcular, listaIndicadoresSelecionados,
									valorRetroativo);
							JanelaPrincipal.abrirJanelas(new MostrarResultadoParaTodos(listaIndicadoresSelecionados, data,
									valorRetroativo, recalcular));
							dispose();
						} catch (Exception erro) {
							new JanelaMensagem(erro.getMessage());
						}
					}
				});
				okButton.setBounds(133, 11, 76, 30);
				okButton.setFont(new Font("Arial", Font.PLAIN, 16));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Não");
				cancelButton.setBounds(355, 11, 71, 30);
				cancelButton.setFont(new Font("Arial", Font.PLAIN, 16));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);

				JLabel lblAnosAMais = new JLabel("Anos a mais para busca retroativa das variáveis: " + valorRetroativo);
				lblAnosAMais.setHorizontalAlignment(SwingConstants.LEFT);
				lblAnosAMais.setFont(new Font("Arial", Font.PLAIN, 16));
				lblAnosAMais.setBounds(10, 41, 519, 30);
				contentPanel.add(lblAnosAMais);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
			}
		}
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(rootPaneCheckingEnabled);
	}

}
