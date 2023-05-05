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
package br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.resultado;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.Tabelas;
import br.edu.mg.unifal.bcc.sgici.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.sgici.modelo.IndicadoresBuscados;
import br.edu.mg.unifal.bcc.sgici.modelo.PossuiVariavel;

/**
 * Classe responsável pela interface que mostra o cálculo do indicador
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class MostrarCalculos extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTable table;

	private IndicadoresBuscados indicador;
	private int codigo_municipio;
	private String data;
	private String nome_municipio;
	private int valorRetroativo;
	private JTable tableIndicadoresComResultado;
	private JTable tableIndicadoresSemResultado;
	private ArrayList<IndicadoresBuscados> indicadoresNaoValorados;
	private ArrayList<IndicadoresBuscados> indicadoresValorados;
	private boolean valorOficial;

	/**
	 * Executa a interface que mostra o cálculo do indicador
	 * @param parametrosMostrarCalculos parâmetros utilizados na classe Mostrar Calculos
	 */
	public MostrarCalculos(ParametrosMostrarCalculo parametrosMostrarCalculos) {

		indicador = parametrosMostrarCalculos.getIndicador();
		codigo_municipio = parametrosMostrarCalculos.getCodigo_municipio();
		data = parametrosMostrarCalculos.getData();
		nome_municipio = parametrosMostrarCalculos.getNome_municipio();
		valorRetroativo = parametrosMostrarCalculos.getValorRetroativo();
		tableIndicadoresComResultado = parametrosMostrarCalculos.getTableIndicadoresComResultado();
		tableIndicadoresSemResultado = parametrosMostrarCalculos.getTableIndicadoresSemResultado();
		indicadoresNaoValorados = parametrosMostrarCalculos.getIndicadoresNaoValorados();
		indicadoresValorados = parametrosMostrarCalculos.getIndicadoresValorados();
		valorOficial = parametrosMostrarCalculos.isValorOficial();

		atualizar();
	}

	/**
	 * Monta a tabela contendo os valores das variáveis utilizadas no cálculo do indicador
	 * @param indicador indicador a ser buscado os valores utilizados no cálculo dele
	 * @param valorRetroativo quantos anos a mais foram utilizados para a busca retroativa
	 */
	private void mostrarCalculos(IndicadoresBuscados indicador, int valorRetroativo) {
		table.setModel(new DefaultTableModel());
		PossuiVariavel.tabelaCalculos(table, indicador, valorRetroativo);
	}

	/**
	 * Atualiza a interface que mostra os cálculos do indicador
	 */
	public void atualizar() {
		setTitle("Indicador: " + indicador.getCodigo_indicador() + "  Município: " + nome_municipio + " Data: " + data);
		setBounds(100, 100, 550, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JScrollPane scrollPaneTabela = new JScrollPane();

		JLabel lblCodigoIndicador = new JLabel("Código do indicador: " + indicador.getCodigo_indicador());
		lblCodigoIndicador.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblData = new JLabel("Data: " + data);
		lblData.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblMetodoCalculo = new JLabel("Método cálculo: " + indicador.getMetodo_calculo());
		lblMetodoCalculo.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblResultado = new JLabel("Resultado: " + indicador.getResultado());
		lblResultado.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnExibirResultado = new JButton("Excluir resultado");
		btnExibirResultado.addActionListener(new ActionListener() {
			/**
			 * Excluir o resultado do indicador 
			 */
			public void actionPerformed(ActionEvent e) {
				TabelaIndicadoresCalculados.removerIndicadorCalculado(indicador, codigo_municipio, data, nome_municipio,
						valorRetroativo, tableIndicadoresComResultado, tableIndicadoresSemResultado,
						indicadoresNaoValorados, indicadoresValorados);
				CalculoIndicador.excluir(indicador.getCodigo_indicador(), codigo_municipio, data);
				dispose();
			}
		});
		btnExibirResultado.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnRecalcularIndicador = new JButton("Recalcular indicador");
		btnRecalcularIndicador.addActionListener(new ActionListener() {
			/**
			 * Possibilita calcular novamente o indicador
			 */
			public void actionPerformed(ActionEvent e) {
				if (valorOficial) {
					new JanelaMensagem("Não é possível mudar os valores de um resultado oficial.");
					dispose();
					return;
				}
				TabelaIndicadoresCalculados.removerIndicadorCalculado(indicador, codigo_municipio, data, nome_municipio,
						valorRetroativo, tableIndicadoresComResultado, tableIndicadoresSemResultado,
						indicadoresNaoValorados, indicadoresValorados);
				dispose();
			}
		});
		btnRecalcularIndicador.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(btnExibirResultado, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
								.addComponent(btnRecalcularIndicador, GroupLayout.PREFERRED_SIZE, 245,
										GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPaneTabela, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
						.addComponent(lblMetodoCalculo, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 504,
								Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING,
								gl_contentPane.createSequentialGroup()
										.addComponent(lblCodigoIndicador, GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(lblData, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE))
						.addComponent(lblResultado, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCodigoIndicador, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
								.addComponent(lblData, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblMetodoCalculo, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addGap(5)
						.addComponent(lblResultado, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(scrollPaneTabela, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addGroup(
								gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnExibirResultado, GroupLayout.PREFERRED_SIZE, 30,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnRecalcularIndicador, GroupLayout.PREFERRED_SIZE, 30,
												GroupLayout.PREFERRED_SIZE))));
		table = new JTable();
		scrollPaneTabela.setViewportView(table);
		contentPane.setLayout(gl_contentPane);

		try {
			mostrarCalculos(indicador, valorRetroativo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Tabelas.corNaLinha(table, 5);
		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
	}
}