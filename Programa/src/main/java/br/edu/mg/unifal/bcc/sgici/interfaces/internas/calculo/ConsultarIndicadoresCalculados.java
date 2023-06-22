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
package br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelasInternas;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.Tabelas;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.resultado.GraficoIndicadores;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.resultado.TabelaIndicadoresCalculados;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.sgici.modelo.Municipio;

/**
 * Classe responsável por consultar os resultados dos indicadores de cidades
 * inteligentes.
 * 
 * <p>
 * Esta classe implementa uma interface gráfica que permite ao usuário
 * selecionar os indicadores que serão consultados e realizá-los para um único
 * município a partir de um CEP e um ano específicos. Na própria interface, é
 * possível informar o CEP e o ano, além de selecionar os indicadores que serão
 * consultados.
 * </p>
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class ConsultarIndicadoresCalculados extends JanelasInternas {

	private static final long serialVersionUID = 1L;

	private JTextField textFieldCEP;
	private static JTable table;
	private JFormattedTextField textFieldData;

	/**
	 * Cria uma interface gráfica contendo uma tabela que lista todos os indicadores
	 * disponíveis no banco de dados, permitindo a seleção daqueles que serão
	 * consultados. Além disso, ela dispõe de campos específicos, como o campo CEP e
	 * o campo Ano, e duas opções de exibição dos resultados: a exibição em forma de
	 * tabela ou em forma de gráfico de barras.
	 */
	public ConsultarIndicadoresCalculados() {
		setTitle("Consultar indicadores");
		setBounds(100, 100, 720, 323);
		setLocation(0, 0);
		setVisible(true);
		setMaximizable(true);
		setResizable(true);
		setClosable(true);
		setIconifiable(true);

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
			 * Mostra a tabela dos indicadores selecionados
			 */
			public void actionPerformed(ActionEvent e) {
				tabelaIndicadoreCalculados();
			}
		});
		btnTabelaIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnTabelaDeIndicadoresCalculados = new JButton("Gráfico de indicadores calculados");
		btnTabelaDeIndicadoresCalculados.addActionListener(new ActionListener() {
			/**
			 * Mostra o gráfico dos indicadores selecionados
			 */
			public void actionPerformed(ActionEvent e) {
				graficoIndicadoresCalculados();
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
				int posicaoBoxSeleciona = 6;
				if(btnSelecionarTodos.getText().equals("Selecionar Todos")) {
					btnSelecionarTodos.setText("Desmarcar Todos");
					Tabelas.selecionarTodos(posicaoBoxSeleciona, table);
				}else {
					btnSelecionarTodos.setText("Selecionar Todos");
					Tabelas.desmarcarTodos(posicaoBoxSeleciona, table);
				}
			}
		});
		btnSelecionarTodos.setFont(new Font("Arial", Font.PLAIN, 16));

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblAno, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldData, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblCep, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldCEP, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
							.addGap(56)
							.addComponent(lblListaDosIndicadores, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSelecionarTodos, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
						.addComponent(btnTabelaIndicadores, GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
						.addComponent(btnTabelaDeIndicadoresCalculados, GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAno, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
						.addComponent(textFieldData, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCep, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldCEP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSelecionarTodos, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblListaDosIndicadores, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTabelaIndicadores, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTabelaDeIndicadoresCalculados, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);

		table = new JTable();
		scrollPaneTabela.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		try {
			Tabelas.mostrarIndicadores(6, table);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Gera uma tabela com os indicadores selecionados para um município específico
	 * a partir do CEP e ano informados pelo usuário. Caso o CEP esteja vazio, uma
	 * mensagem de erro será exibida e o método será encerrado. Caso o ano esteja
	 * vazio, uma mensagem de erro será exibida e o método será encerrado. Caso o
	 * CEP seja inválido, uma mensagem de erro será exibida e o método será
	 * encerrado. Caso a busca do nome do município associado ao CEP falhe, uma
	 * mensagem de erro será exibida. A tabela será exibida em uma nova interface.
	 */
	private void tabelaIndicadoreCalculados() {
		ArrayList<Integer> listaIndicadoresSelecionados = new ArrayList<Integer>();
		listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);

		// Verifica se o CEP não está vazio.
		if (textFieldCEP.getText().isBlank()) {
			new JanelaMensagem("É necessário preencher o campo CEP.");
			return;
		}

		// Verifica se o ano não está vazio.
		if (textFieldData.getText().isBlank()) {
			new JanelaMensagem("É necessário preencher o campo Ano.");
			return;
		}

		// Verifica se o CEP é válido.
		int codigo_municipio = 0;
		try {
			codigo_municipio = Municipio.buscaCodigoDoMunicipio(textFieldCEP.getText());
		} catch (Exception erroCodigoMunicipio) {
			new JanelaMensagem("CEP inválido.");
			return;
		}

		// Abre a interface da tabela dos indicadores selecionados.
		try {
			int valorRetroativo = 0;
			String nomeMunicipio = Municipio.buscaNomeComUF(textFieldCEP.getText());
			JanelaPrincipal.abrirJanelas(new TabelaIndicadoresCalculados(listaIndicadoresSelecionados, nomeMunicipio,
					codigo_municipio, textFieldData.getText(), valorRetroativo));
		} catch (Exception e1) {
			new JanelaMensagem("CEP inválido.");
		}
	}

	/**
	 * Gera um gráfico com os indicadores selecionados para um município específico
	 * a partir do CEP e ano informados pelo usuário. Caso o CEP esteja vazio, uma
	 * mensagem de erro será exibida e o método será encerrado. Caso o ano esteja
	 * vazio, uma mensagem de erro será exibida e o método será encerrado. Caso o
	 * CEP seja inválido, uma mensagem de erro será exibida e o método será
	 * encerrado. Caso a busca do nome do município associado ao CEP falhe, uma
	 * mensagem de erro será exibida. O gráfico será exibido em uma nova interface.
	 */
	private void graficoIndicadoresCalculados() {
		ArrayList<Integer> listaIndicadoresSelecionados = new ArrayList<Integer>();
		listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);

		// Verifica se o CEP não está vazio.
		if (textFieldCEP.getText().isBlank()) {
			new JanelaMensagem("É necessário preencher o campo CEP.");
			return;
		}

		// Verifica se o ano não está vazio.
		if (textFieldData.getText().isBlank()) {
			new JanelaMensagem("É necessário preencher o campo Ano.");
			return;
		}

		// Verifica se o CEP é válido.
		int codigo_municipio = 0;
		try {
			codigo_municipio = Municipio.buscaCodigoDoMunicipio(textFieldCEP.getText());
		} catch (Exception erroCodigoMunicipio) {
			new JanelaMensagem("CEP inválido.");
			return;
		}

		// Abre a interface do gráfico dos indicadores selecionados.
		try {
			String nomeMunicipio = Municipio.buscaNomeComUF(textFieldCEP.getText());
			JanelaPrincipal.abrirJanelas(new GraficoIndicadores(listaIndicadoresSelecionados, nomeMunicipio,
					codigo_municipio, textFieldData.getText()));
		} catch (Exception e1) {
			System.out.println(e1);
			System.out.println(e1.getLocalizedMessage() + " " + e1.getStackTrace());
			new JanelaMensagem("CEP inválido.");
		}
	}

	/**
	 * Este método retorna a tabela dos indicadores disponíveis na classe.
	 * 
	 * @return A tabela dos indicadores
	 */
	public static JTable getTable() {
		return table;
	}

	/**
	 * Sobrescreve o método da classe JanelasInternas para armazenar a referência à
	 * janela ConsultarIndicadoresCalculados na JanelaPrincipal.
	 */
	@Override
	protected void abrirJanelaJDesktopPane(JInternalFrame janela) {
		JanelaPrincipal.setJanelaConsultarIndicadoresCalculados((ConsultarIndicadoresCalculados) janela);
	}

	/**
	 * Cria uma nova instância da classe ConsultarIndicadoresCalculados.
	 */
	@Override
	protected JInternalFrame instanciaClasseAberta() {
		return new ConsultarIndicadoresCalculados();
	}
}
