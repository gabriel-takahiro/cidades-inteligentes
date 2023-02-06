package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao;

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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.MostrarResultadoParaUm;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.IndicadoresBuscados;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Municipio;

public class JanelaConfirmaUmMunicipio extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	public JanelaConfirmaUmMunicipio(String cep, String data, String nomeMunicipio, boolean recalcular,
			ArrayList<Indicador> listaIndicadoresSelecionados, int valorRetroativo) {
		setBounds(100, 100, 555, 173);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Deseja fazer a busca para o município: " + nomeMunicipio + "?");
		if (recalcular) {
			lblNewLabel = new JLabel("Deseja recalcular os indicadores para o município: " + nomeMunicipio + "?");
		}
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 519, 30);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 82, 539, 52);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("Sim");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							int codigoMunicipio = Municipio.buscaCodigoDoMunicipio(cep);
							List<IndicadoresBuscados> indicadoresCalculados = CalculoIndicador
									.listaIndicadoresCalculados(listaIndicadoresSelecionados, codigoMunicipio, data,
											recalcular, valorRetroativo);

							JanelaPrincipal.abrirJanelas(
									new MostrarResultadoParaUm(listaIndicadoresSelecionados, indicadoresCalculados,
											nomeMunicipio, codigoMunicipio, data, valorRetroativo, recalcular));
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
				cancelButton.setBounds(327, 11, 71, 30);
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
