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
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Municipio;

/**
 * Classe responsável pela interface que realiza a consulta dos indicadores calculados
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class ConsultarIndicadoresCalculados extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	private JTextField textFieldCEP;
	private static JTable table;
	private JFormattedTextField textFieldData;

	/**
	 * Executa a interface que realiza a consulta dos indicadores calculados
	 */
	public ConsultarIndicadoresCalculados() {
		setTitle("Consultar indicadores");
		setBounds(100, 100, 690, 323);

		JLabel lblAno = new JLabel("Ano:");
		lblAno.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblListaDosIndicadores = new JLabel("Lista dos indicadores:");
		lblListaDosIndicadores.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaDosIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));

		JScrollPane scrollPaneTabela = new JScrollPane();

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Arial", Font.PLAIN, 16));

		textFieldCEP = new JTextField();
		textFieldCEP.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldCEP.setColumns(10);

		JButton btnTabelaIndicadores = new JButton("Tabela dos indicadores");
		btnTabelaIndicadores.addActionListener(new ActionListener() {
			/**
			 * Mostra a tabela dos indicadores calculados
			 */
			public void actionPerformed(ActionEvent e) {
				ArrayList<Indicador> listaIndicadoresSelecionados = new ArrayList<Indicador>();
				listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);

				if (textFieldCEP.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo CEP.");
					return;
				}

				if (textFieldData.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo Data.");
					return;
				}

				int codigo_municipio = 0;
				try {
					codigo_municipio = Municipio.buscaCodigoDoMunicipio(textFieldCEP.getText());
				} catch (Exception erroCodigoMunicipio) {
					new JanelaMensagem("CEP inválido.");
					return;
				}

				try {
					String nomeMunicipio = Municipio.buscaNomeComUF(textFieldCEP.getText());
					JanelaPrincipal.abrirJanelas(new TabelaIndicadoresCalculados(listaIndicadoresSelecionados,
							nomeMunicipio, codigo_municipio, textFieldData.getText(), 0));
				} catch (Exception e1) {
					new JanelaMensagem("CEP inválido.");
				}

			}
		});
		btnTabelaIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnTabelaDeIndicadoresCalculados = new JButton("Gráfico de indicadores calculados");
		btnTabelaDeIndicadoresCalculados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Indicador> listaIndicadoresSelecionados = new ArrayList<Indicador>();
				listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);

				if (textFieldCEP.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo CEP.");
					return;
				}

				if (textFieldData.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo Data.");
					return;
				}

				int codigo_municipio = 0;
				try {
					codigo_municipio = Municipio.buscaCodigoDoMunicipio(textFieldCEP.getText());
				} catch (Exception erroCodigoMunicipio) {
					new JanelaMensagem("CEP inválido.");
					return;
				}

				try {
					String nomeMunicipio = Municipio.buscaNomeComUF(textFieldCEP.getText());
					JanelaPrincipal.abrirJanelas(new GraficoIndicadores(listaIndicadoresSelecionados, nomeMunicipio,
							codigo_municipio, textFieldData.getText()));
				} catch (Exception e1) {
					new JanelaMensagem("CEP inválido.");
				}
			}
		});
		btnTabelaDeIndicadoresCalculados.setFont(new Font("Arial", Font.PLAIN, 16));

		MaskFormatter mascaraAno;
		try {
			mascaraAno = new MaskFormatter("####");
			textFieldData = new JFormattedTextField(mascaraAno);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		textFieldData.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldData.setColumns(10);

		JButton btnSelecionarTodos = new JButton("Selecionar Todos");
		btnSelecionarTodos.addActionListener(new ActionListener() {
			/**
			 * Seleciona todos os indicadores 
			 */
			public void actionPerformed(ActionEvent e) {
				Tabelas.selecionarTodos(5, table);
			}
		});
		btnSelecionarTodos.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
								.addComponent(lblAno, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textFieldData, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblCep, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(textFieldCEP, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblListaDosIndicadores, GroupLayout.PREFERRED_SIZE, 172,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnSelecionarTodos, GroupLayout.PREFERRED_SIZE, 170,
										GroupLayout.PREFERRED_SIZE)
								.addGap(2))
						.addComponent(btnTabelaIndicadores, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
						.addComponent(btnTabelaDeIndicadoresCalculados, GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAno, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
								.addComponent(textFieldData, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCep, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldCEP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblListaDosIndicadores, GroupLayout.PREFERRED_SIZE, 25,
										GroupLayout.PREFERRED_SIZE))
						.addComponent(btnSelecionarTodos, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnTabelaIndicadores, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(btnTabelaDeIndicadoresCalculados, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addContainerGap()));

		table = new JTable();
		scrollPaneTabela.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		try {
			Tabelas.mostrarIndicadores(5, table);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setResizable(true);
		setClosable(true);
		setIconifiable(true);
	}

	/**
	 * 
	 * @return tabela dos indicadores
	 */
	public static JTable getTable() {
		return table;
	}


	@Override
	public void abrirJanela(JInternalFrame janelaConsultarIndicadoresCalculados, JDesktopPane desktopPane) {
		if (janelaConsultarIndicadoresCalculados.isClosed()) {
			ConsultarIndicadoresCalculados consultarIndicadoresCalculados = new ConsultarIndicadoresCalculados();
			desktopPane.add(consultarIndicadoresCalculados);
			JanelaPrincipal.setJanelaConsultarIndicadoresCalculados(consultarIndicadoresCalculados);
			return;
		}
		if (janelaConsultarIndicadoresCalculados.isVisible()) {
			janelaConsultarIndicadoresCalculados.moveToFront();
			return;
		}
		janelaConsultarIndicadoresCalculados.setVisible(true);
		janelaConsultarIndicadoresCalculados.moveToFront();
	}
}
