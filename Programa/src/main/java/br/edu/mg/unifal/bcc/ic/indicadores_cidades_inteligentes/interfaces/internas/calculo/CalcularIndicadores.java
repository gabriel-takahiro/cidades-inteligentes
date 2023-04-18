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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.calculo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.Janelas;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.Tabelas;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Municipio;

/**
 * Classe responsável pela interface que realiza o cálculo dos indicadores
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class CalcularIndicadores extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTable table;
	private JFormattedTextField textFieldAno;
	private static JRadioButton rdbtnConsultarParaTodos;
	private static JFormattedTextField campoFormatadoCep;
	private static JFormattedTextField formattedTextFieldAnos;

	/**
	 * Executa a interface que realiza o cálculo dos indicadores
	 */
	public CalcularIndicadores() {
		setBounds(100, 100, 450, 300);

		setTitle("Calcular indicadores");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 720, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIndicadoresConsulta = new JLabel("Selecione os indicadores para a consulta:");
		lblIndicadoresConsulta.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndicadoresConsulta.setFont(new Font("Arial", Font.PLAIN, 16));
		lblIndicadoresConsulta.setBounds(10, 11, 504, 30);
		contentPane.add(lblIndicadoresConsulta);

		JScrollPane scrollPaneTabela = new JScrollPane();
		scrollPaneTabela.setBounds(10, 49, 684, 163);
		contentPane.add(scrollPaneTabela);

		table = new JTable();
		scrollPaneTabela.setViewportView(table);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 223, 704, 2);
		contentPane.add(separator);

		JLabel lblDigiteUmCep = new JLabel("Digite um CEP do município:");
		lblDigiteUmCep.setHorizontalAlignment(SwingConstants.LEFT);
		lblDigiteUmCep.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDigiteUmCep.setBounds(20, 280, 345, 30);
		contentPane.add(lblDigiteUmCep);

		campoFormatadoCep = new JFormattedTextField();
		campoFormatadoCep.setFont(new Font("Arial", Font.PLAIN, 16));
		campoFormatadoCep.setBounds(375, 280, 110, 30);
		contentPane.add(campoFormatadoCep);

		JRadioButton rdbtnParaUmMunicipio = new JRadioButton("Realizar os cálculos para um município");
		rdbtnParaUmMunicipio.setSelected(true);
		rdbtnParaUmMunicipio.addActionListener(new ActionListener() {
			/**
			 * Mostra os campos que são necessários preencher para realizar o cálculo para um município 
			 */
			public void actionPerformed(ActionEvent e) {
				rdbtnParaUmMunicipio.setSelected(true);
				rdbtnConsultarParaTodos.setSelected(false);
				lblDigiteUmCep.setVisible(true);
				campoFormatadoCep.setVisible(true);
			}
		});
		rdbtnParaUmMunicipio.setFont(new Font("Arial", Font.PLAIN, 16));
		rdbtnParaUmMunicipio.setBounds(10, 233, 312, 30);
		contentPane.add(rdbtnParaUmMunicipio);

		rdbtnConsultarParaTodos = new JRadioButton("Realizar os cálculos para todos os municípios ");
		rdbtnConsultarParaTodos.addActionListener(new ActionListener() {
			/**
			 * Mostra os campos que são necessários preencher para realizar o cálculo para todos os municípios
			 */
			public void actionPerformed(ActionEvent e) {
				rdbtnConsultarParaTodos.setSelected(true);
				rdbtnParaUmMunicipio.setSelected(false);
				lblDigiteUmCep.setVisible(false);
				campoFormatadoCep.setVisible(false);
			}
		});
		rdbtnConsultarParaTodos.setFont(new Font("Arial", Font.PLAIN, 16));
		rdbtnConsultarParaTodos.setBounds(332, 232, 362, 30);
		contentPane.add(rdbtnConsultarParaTodos);

		JLabel lblDigiteUmAno = new JLabel("Digite um ano para a consulta (Formato AAAA):");
		lblDigiteUmAno.setHorizontalAlignment(SwingConstants.LEFT);
		lblDigiteUmAno.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDigiteUmAno.setBounds(20, 325, 345, 30);
		contentPane.add(lblDigiteUmAno);

		MaskFormatter mascaraAno;
		try {
			mascaraAno = new MaskFormatter("####");
			textFieldAno = new JFormattedTextField(mascaraAno);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		textFieldAno.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldAno.setBounds(375, 325, 110, 30);
		contentPane.add(textFieldAno);
		textFieldAno.setColumns(10);

		JButton btnConsultar = new JButton("Calcular");
		btnConsultar.addActionListener(new ActionListener() {
			/**
			 * Realiza a consulta dos indicadores selecionados 
			 */
			public void actionPerformed(ActionEvent e) {
				if (rdbtnConsultarParaTodos.isSelected()) {
					if (textFieldAno.getText().isBlank()) {
						new JanelaMensagem("É necessário preencher o campo Ano.");
						return;
					}

					String anosRetroativos = formattedTextFieldAnos.getText();
					if (anosRetroativos.isBlank()) {
						new JanelaMensagem("Coloque um valor válido em Anos retroativos");
						return;
					}

					int valorRetroativo = 0;
					try {
						valorRetroativo = Integer.parseInt(anosRetroativos);
						if (valorRetroativo < 0) {
							new JanelaMensagem("Coloque um valor não negativo em Anos retroativos");
							return;
						}
					} catch (Exception erroValorRetroativo) {
						new JanelaMensagem("Coloque um valor válido em Anos retroativos");
						return;
					}

					ArrayList<Integer> listaIndicadoresSelecionados = new ArrayList<Integer>();
					listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);
					new JanelaConfirmaTodosMunicipios(textFieldAno.getText(), false, listaIndicadoresSelecionados,
							valorRetroativo);
					return;
				}
				if (campoFormatadoCep.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo CEP.");
					return;
				}
				if (textFieldAno.getText().isBlank()) {
					new JanelaMensagem("É necessário preencher o campo Ano.");
					return;
				}

				String anosRetroativos = formattedTextFieldAnos.getText();
				if (anosRetroativos.isBlank()) {
					new JanelaMensagem("Coloque um valor válido em Anos retroativos");
					return;
				}

				int valorRetroativo = 0;
				try {
					valorRetroativo = Integer.parseInt(anosRetroativos);
					if (valorRetroativo < 0) {
						new JanelaMensagem("Coloque um valor não negativo em Anos retroativos");
						return;
					}
				} catch (Exception erroValorRetroativo) {
					new JanelaMensagem("Coloque um valor válido em Anos retroativos");
					return;
				}

				String nomeMunicipio;
				try {
					nomeMunicipio = Municipio.buscaNomeComUF(campoFormatadoCep.getText());
					ArrayList<Integer> listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);
					new JanelaConfirmaUmMunicipio(campoFormatadoCep.getText(), textFieldAno.getText(), nomeMunicipio,
							false, listaIndicadoresSelecionados, valorRetroativo);
				} catch (Exception e1) {
					new JanelaMensagem("CEP inválido.");
				}
			}

		});
		btnConsultar.setFont(new Font("Arial", Font.PLAIN, 16));
		btnConsultar.setBounds(500, 280, 194, 116);
		contentPane.add(btnConsultar);

		JButton btnSelecionarTodos = new JButton("Selecionar Todos");
		btnSelecionarTodos.addActionListener(new ActionListener() {
			/**
			 * Seleciona todos indicadores da tabela 
			 */
			public void actionPerformed(ActionEvent e) {
				Tabelas.selecionarTodos(5, table);
			}
		});
		btnSelecionarTodos.setFont(new Font("Arial", Font.PLAIN, 16));
		btnSelecionarTodos.setBounds(524, 11, 170, 30);
		contentPane.add(btnSelecionarTodos);

		JLabel lblAnosAMais = new JLabel("Anos a mais para busca retroativa das variáveis:");
		lblAnosAMais.setHorizontalAlignment(SwingConstants.LEFT);
		lblAnosAMais.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAnosAMais.setBounds(20, 366, 345, 30);
		contentPane.add(lblAnosAMais);

		formattedTextFieldAnos = new JFormattedTextField("0");
		formattedTextFieldAnos.setFont(new Font("Arial", Font.PLAIN, 16));
		formattedTextFieldAnos.setBounds(375, 366, 110, 30);
		contentPane.add(formattedTextFieldAnos);

		try {
			Tabelas.mostrarIndicadores(5, table);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setClosable(true);
		setResizable(false);
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
	public void abrirJanela(JInternalFrame janelaCalcularIndicadores, JDesktopPane desktopPane) {
		if (janelaCalcularIndicadores.isClosed()) {
			CalcularIndicadores calcularIndicadores = new CalcularIndicadores();
			desktopPane.add(calcularIndicadores);
			JanelaPrincipal.setJanelaCalcularIndicadores(calcularIndicadores);
			return;
		}
		if (janelaCalcularIndicadores.isVisible()) {
			janelaCalcularIndicadores.moveToFront();
			return;
		}
		janelaCalcularIndicadores.setVisible(true);
		janelaCalcularIndicadores.moveToFront();
	}

}
