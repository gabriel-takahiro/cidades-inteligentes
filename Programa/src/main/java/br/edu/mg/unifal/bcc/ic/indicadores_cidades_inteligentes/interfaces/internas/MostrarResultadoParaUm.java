package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.IndicadoresBuscados;

/**
 * Classe responsável pela interface precede exibição dos resultados. É possível selecionar visualizar
 * os indicadores por meio de uma tabela ou de um gráfico de barras. Esta interface aparece apenas quando 
 * for realizado o cálculo de indicadores para um único município.
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class MostrarResultadoParaUm extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JTable table;
	private ArrayList<Indicador> listaIndicadoresSelecionados;

	/**
	 * Executa a interface que precede a exibição dos resultados
	 * @param listaIndicadoresSelecionados lista com os indicadores selecionados
	 * @param indicadoresCalculados lista com os indicadores calculados
	 * @param nomeMunicipio nome do município
	 * @param codigo_municipio código do município
	 * @param data ano que foi utilizado para calcular os indicadores
	 * @param valorRetroativo quantidade de anos a mais utilizados para a busca retroativa dos variáveis
	 * @param recalcular indica se os resultados foram recalculados ou não
	 */
	public MostrarResultadoParaUm(ArrayList<Indicador> listaIndicadoresSelecionados,
			List<IndicadoresBuscados> indicadoresCalculados, String nomeMunicipio, int codigo_municipio, String data,
			int valorRetroativo, boolean recalcular) {
		this.listaIndicadoresSelecionados = listaIndicadoresSelecionados;
		if (recalcular) {
			setTitle("Mostrar resultados recalculados");
		} else {
			setTitle("Mostrar resultados");
		}
		setBounds(100, 100, 650, 323);

		JLabel lblData = new JLabel("Data: " + data);
		lblData.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblListaDosIndicadores = new JLabel("Lista dos indicadores:");
		lblListaDosIndicadores.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaDosIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));

		JScrollPane scrollPaneTabela = new JScrollPane();

		JLabel lblCep = new JLabel("Município: " + nomeMunicipio);
		lblCep.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnTabelaIndicadores = new JButton("Tabela dos indicadores");
		btnTabelaIndicadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JanelaPrincipal.abrirJanelas(new TabelaIndicadoresCalculados(listaIndicadoresSelecionados,
							nomeMunicipio, codigo_municipio, data, valorRetroativo));
				} catch (Exception e1) {
					new JanelaMensagem("Erro ao carregar a tabela de indicadores calculados.");
				}
			}
		});
		btnTabelaIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnGraficoIndicadores = new JButton("Gráfico de indicadores calculados");
		btnGraficoIndicadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JanelaPrincipal.abrirJanelas(new GraficoIndicadores(listaIndicadoresSelecionados, nomeMunicipio,
							codigo_municipio, data));
				} catch (Exception e1) {
					new JanelaMensagem("Erro ao carregar o gráfico dos indicadores calculados");
				}
			}
		});
		btnGraficoIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneTabela, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addComponent(lblData, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(lblCep, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblListaDosIndicadores, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
						.addComponent(btnGraficoIndicadores, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 614,
								Short.MAX_VALUE)
						.addComponent(btnTabelaIndicadores, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblCep, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblListaDosIndicadores, GroupLayout.PREFERRED_SIZE, 25,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblData, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnTabelaIndicadores, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnGraficoIndicadores,
								GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		table = new JTable();
		scrollPaneTabela.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		try {
			Indicador.mostrarIndicadores(table, listaIndicadoresSelecionados);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		table.setEnabled(false);

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setResizable(true);
		setClosable(true);
		setIconifiable(true);
	}

	/**
	 * Atualiza a interface que precede a exibição dos resultados
	 */
	public void atualizar() {
		try {
			Indicador.mostrarIndicadores(table, listaIndicadoresSelecionados);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
