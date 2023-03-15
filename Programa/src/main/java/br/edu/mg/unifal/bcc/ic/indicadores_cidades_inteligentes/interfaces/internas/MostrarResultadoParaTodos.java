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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Municipio;

/**
 * Classe responsável pela interface que permite que o usuário escolha um CEP para visualizar
 * os resultados obtida para este município. Esta interface aparece apenas quando for realizado
 * o cálculo de indicadores para todos os municípios.
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class MostrarResultadoParaTodos extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JTextField textField;
	private JTable table;
	private boolean recalcular;
	private String data;
	private ArrayList<Indicador> listaIndicadoresSelecionados;
	private int valorRetroativo;

	/**
	 * Executa a interface que precede a exibição dos resultados
	 * @param listaIndicadoresSelecionados lista dos indicadores que foram calculados
	 * @param data ano que foi utilizado para calcular os indicadores
	 * @param valorRetroativo quantidade de anos a mais utilizados para a busca retroativa dos variáveis
	 * @param recalcular indica se os resultados foram recalculados ou não
	 */
	public MostrarResultadoParaTodos(ArrayList<Indicador> listaIndicadoresSelecionados, String data,
			int valorRetroativo, boolean recalcular) {
		this.listaIndicadoresSelecionados = listaIndicadoresSelecionados;
		this.data = data;
		this.valorRetroativo = valorRetroativo;
		this.recalcular = recalcular;

		atualizar();
	}

	/**
	 * Atualiza a interface que precede a exibição dos resultados
	 */
	public void atualizar() {
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

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Arial", Font.PLAIN, 16));

		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 16));
		textField.setColumns(10);

		JButton btnTabelaIndicadores = new JButton("Tabela dos indicadores");
		btnTabelaIndicadores.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que exibe a tabela dos indicadores calculados
			 */
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo CEP.");
					return;
				}

				int codigo_municipio = 0;
				try {
					codigo_municipio = Municipio.buscaCodigoDoMunicipio(textField.getText());
				} catch (Exception erroCodigoMunicipio) {
					new JanelaMensagem("CEP inválido.");
					return;
				}

				try {
					String nomeMunicipio = Municipio.buscaNomeComUF(textField.getText());
					JanelaPrincipal.abrirJanelas(new TabelaIndicadoresCalculados(listaIndicadoresSelecionados,
							nomeMunicipio, codigo_municipio, data, valorRetroativo));
				} catch (Exception e1) {
					new JanelaMensagem("CEP inválido.");
				}

			}
		});
		btnTabelaIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnGraficoIndicadores = new JButton("Gráfico de indicadores calculados");
		btnGraficoIndicadores.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que mostra os indicadores calculados em um gráfico de barras
			 */
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo CEP.");
					return;
				}

				int codigo_municipio = 0;
				try {
					codigo_municipio = Municipio.buscaCodigoDoMunicipio(textField.getText());
				} catch (Exception erroCodigoMunicipio) {
					new JanelaMensagem("CEP inválido.");
					return;
				}

				try {
					String nomeMunicipio = Municipio.buscaNomeComUF(textField.getText());
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
								.addComponent(lblCep, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(lblListaDosIndicadores, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
						.addComponent(btnTabelaIndicadores, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
						.addComponent(btnGraficoIndicadores, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 614,
								Short.MAX_VALUE))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblListaDosIndicadores, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblData, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
								.addComponent(lblCep, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
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
}
