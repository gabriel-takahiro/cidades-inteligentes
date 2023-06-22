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

import javax.swing.JButton;
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

import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelasInternas;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.Tabelas;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.sgici.modelo.Municipio;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * Classe responsável por calcular os indicadores de cidades inteligentes.
 * 
 * <p>
 * Esta classe implementa uma interface gráfica que permite ao usuário
 * selecionar os indicadores que serão calculados e realizá-los para um único
 * município a partir de um CEP e um ano específicos, ou para todos os
 * municípios a partir de um ano determinado. Na própria interface, é possível
 * selecionar quantos anos retroativos serão utilizados para buscar as variáveis
 * necessárias.
 * </p>
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class CalcularIndicadores extends JanelasInternas {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTable table;
	private JFormattedTextField textFieldAno;
	private static JRadioButton rdbtnConsultarParaTodos;
	private static JFormattedTextField campoFormatadoCep;
	private static JFormattedTextField formattedTextFieldAnos;

	/**
	 * Cria uma interface gráfica contendo uma tabela que lista todos os indicadores
	 * disponíveis no banco de dados, permitindo a seleção daqueles que serão
	 * calculados. Além disso, ela dispõe de campos específicos, como o campo CEP,
	 * para o cálculo de indicadores de um município em particular, e o campo Ano,
	 * que juntamente com o campo anos retroativos, possibilita o cálculo dos
	 * indicadores tanto para um município específico quanto para todos os
	 * municípios. Adicionalmente, a classe oferece a opção de calcular os
	 * indicadores tanto para um único município quanto para todos os municípios.
	 */
	public CalcularIndicadores() {
		setTitle("Calcular indicadores");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 720, 450);
		setLocation(0, 0);
		setVisible(true);
		setMaximizable(true);
		setClosable(true);
		setResizable(true);
		setIconifiable(true);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblIndicadoresConsulta = new JLabel("Selecione os indicadores para o cálculo:");
		lblIndicadoresConsulta.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndicadoresConsulta.setFont(new Font("Arial", Font.PLAIN, 16));

		JScrollPane scrollPaneTabela = new JScrollPane();

		table = new JTable();
		scrollPaneTabela.setViewportView(table);

		JSeparator separator = new JSeparator();

		JLabel lblDigiteUmCep = new JLabel("Digite um CEP do município:");
		lblDigiteUmCep.setHorizontalAlignment(SwingConstants.LEFT);
		lblDigiteUmCep.setFont(new Font("Arial", Font.PLAIN, 16));

		campoFormatadoCep = new JFormattedTextField();
		campoFormatadoCep.setFont(new Font("Arial", Font.PLAIN, 16));

		JRadioButton rdbtnParaUmMunicipio = new JRadioButton("Realizar os cálculos para um município");
		rdbtnParaUmMunicipio.setSelected(true);
		rdbtnParaUmMunicipio.addActionListener(new ActionListener() {
			/**
			 * Mostra os campos que são necessários preencher para realizar o cálculo para
			 * um município
			 */
			public void actionPerformed(ActionEvent e) {
				rdbtnParaUmMunicipio.setSelected(true);
				rdbtnConsultarParaTodos.setSelected(false);
				lblDigiteUmCep.setVisible(true);
				campoFormatadoCep.setVisible(true);
			}
		});
		rdbtnParaUmMunicipio.setFont(new Font("Arial", Font.PLAIN, 16));

		rdbtnConsultarParaTodos = new JRadioButton("Realizar os cálculos para todos os municípios ");
		rdbtnConsultarParaTodos.addActionListener(new ActionListener() {
			/**
			 * Mostra os campos que são necessários preencher para realizar o cálculo para
			 * todos os municípios
			 */
			public void actionPerformed(ActionEvent e) {
				rdbtnConsultarParaTodos.setSelected(true);
				rdbtnParaUmMunicipio.setSelected(false);
				lblDigiteUmCep.setVisible(false);
				campoFormatadoCep.setVisible(false);
			}
		});
		rdbtnConsultarParaTodos.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblDigiteUmAno = new JLabel("Digite um ano para o cálculo (Formato AAAA):");
		lblDigiteUmAno.setHorizontalAlignment(SwingConstants.LEFT);
		lblDigiteUmAno.setFont(new Font("Arial", Font.PLAIN, 16));

		MaskFormatter mascaraAno;
		try {
			mascaraAno = new MaskFormatter("####");
			textFieldAno = new JFormattedTextField(mascaraAno);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		textFieldAno.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldAno.setColumns(10);

		JButton btnConsultar = new JButton("Calcular");
		btnConsultar.addActionListener(new ActionListener() {
			/**
			 * Realiza o cálculo dos indicadores selecionados
			 */
			public void actionPerformed(ActionEvent e) {
				calculoIndicadores();
			}

		});
		btnConsultar.setFont(new Font("Arial", Font.PLAIN, 16));

		JButton btnSelecionarTodos = new JButton("Selecionar Todos");
		btnSelecionarTodos.addActionListener(new ActionListener() {
			/**
			 * Seleciona todos indicadores da tabela
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

		JLabel lblAnosAMais = new JLabel("Anos a mais para busca retroativa das variáveis:");
		lblAnosAMais.setHorizontalAlignment(SwingConstants.LEFT);
		lblAnosAMais.setFont(new Font("Arial", Font.PLAIN, 16));

		formattedTextFieldAnos = new JFormattedTextField("0");
		formattedTextFieldAnos.setFont(new Font("Arial", Font.PLAIN, 16));

		try {
			int posicaoBoxSeleciona = 6;
			Tabelas.mostrarIndicadores(posicaoBoxSeleciona, table);
			GroupLayout gl_contentPane = new GroupLayout(contentPane);
			gl_contentPane.setHorizontalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(10)
						.addComponent(lblIndicadoresConsulta, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
						.addGap(10)
						.addComponent(btnSelecionarTodos, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
						.addGap(10))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(10)
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
						.addGap(10))
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(10)
						.addComponent(rdbtnParaUmMunicipio, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE)
						.addGap(10)
						.addComponent(rdbtnConsultarParaTodos, GroupLayout.PREFERRED_SIZE, 357, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(20, Short.MAX_VALUE))
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(20)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(lblDigiteUmCep, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblDigiteUmAno, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblAnosAMais, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE))
						.addGap(10)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(campoFormatadoCep, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
							.addComponent(textFieldAno, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
							.addComponent(formattedTextFieldAnos, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
						.addGap(15)
						.addComponent(btnConsultar, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
						.addGap(10))
			);
			gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPane.createSequentialGroup()
						.addGap(6)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addComponent(lblIndicadoresConsulta, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSelecionarTodos, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGap(8)
						.addComponent(scrollPaneTabela, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
						.addGap(11)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(7)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGap(1)
								.addComponent(rdbtnParaUmMunicipio, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addComponent(rdbtnConsultarParaTodos, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
						.addGap(17)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblDigiteUmCep, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addGap(15)
								.addComponent(lblDigiteUmAno, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(lblAnosAMais, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(campoFormatadoCep, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addGap(15)
								.addComponent(textFieldAno, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(formattedTextFieldAnos, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
							.addComponent(btnConsultar, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
						.addGap(19))
			);
			contentPane.setLayout(gl_contentPane);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Realiza o cálculo dos indicadores selecionados para um ou todos os
	 * municípios, baseado nos dados preenchidos nos campos de entrada. Caso o
	 * usuário selecione a opção de calcular para todos os municípios, será aberta
	 * uma janela de confirmação para confirmar a ação. Caso contrário, será aberta
	 * uma janela de confirmação para o cálculo dos indicadores para um município.
	 */
	private void calculoIndicadores() {
		// Verifica se o programa já está calculando alguns indicadores
		if (JanelaPrincipal.isCalculando()) {
			new JanelaMensagem(
					"O programa já está calculando alguns indicadores.\nTente novamente após o cálculo desses indicadores.");
			return;
		}

		// Verifica se o ano não está vazio.
		if (textFieldAno.getText().isBlank()) {
			new JanelaMensagem("É necessário preencher o campo Ano.");
			return;
		}

		// Verifica se os anos retroativos são válidos
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

		// Verifica se o cálculo deve ser feito para todos os municípios.
		if (rdbtnConsultarParaTodos.isSelected()) {
			ArrayList<Integer> listaIndicadoresSelecionados = new ArrayList<Integer>();
			listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);
			new JanelaConfirmaCalculoTodosMunicipios(textFieldAno.getText(), false, listaIndicadoresSelecionados,
					valorRetroativo);
			return;
		}

		// Verifica se o CEP não está vazio.
		if (campoFormatadoCep.getText().isBlank()) {
			new JanelaMensagem("É necessário preencher o campo CEP.");
			return;
		}

		String nomeMunicipio;
		try {
			nomeMunicipio = Municipio.buscaNomeComUF(campoFormatadoCep.getText());
			ArrayList<Integer> listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);
			new JanelaConfirmaCalculoUmMunicipio(campoFormatadoCep.getText(), textFieldAno.getText(), nomeMunicipio,
					false, listaIndicadoresSelecionados, valorRetroativo);
		} catch (Exception e1) {
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
	 * janela CalcularIndicadores na JanelaPrincipal.
	 */
	@Override
	protected void abrirJanelaJDesktopPane(JInternalFrame janela) {
		JanelaPrincipal.setJanelaCalcularIndicadores((CalcularIndicadores) janela);
	}

	/**
	 * Cria uma nova instância da classe CalcularIndicadores.
	 */
	@Override
	protected JInternalFrame instanciaClasseAberta() {
		return new CalcularIndicadores();
	}

}
