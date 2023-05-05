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

/**
 * Classe responsável por recalcular os indicadores de cidades inteligentes.
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
public class RecalcularIndicadores extends JanelasInternas {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTable table;
	private JFormattedTextField textFieldAno;
	private static JRadioButton rdbtnRecalcularParaTodos;
	private static JFormattedTextField campoFormatadoCep;
	private static JFormattedTextField formattedTextFieldAnos;

	/**
	 * Cria uma interface gráfica contendo uma tabela que lista todos os indicadores
	 * disponíveis no banco de dados, permitindo a seleção daqueles que serão
	 * recalculados. Além disso, ela dispõe de campos específicos, como o campo CEP,
	 * para o cálculo de indicadores de um município em particular, e o campo Ano,
	 * que juntamente com o campo anos retroativos, possibilita recalcular os
	 * indicadores tanto para um município específico quanto para todos os
	 * municípios. Adicionalmente, a classe oferece a opção de calcular os
	 * indicadores tanto para um único município quanto para todos os municípios.
	 */
	public RecalcularIndicadores() {
		setBounds(100, 100, 450, 300);
		setTitle("Recalcular indicadores");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 720, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIndicadores = new JLabel("Selecione os indicadores para recalcular:");
		lblIndicadores.setHorizontalAlignment(SwingConstants.CENTER);
		lblIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));
		lblIndicadores.setBounds(10, 11, 504, 30);
		contentPane.add(lblIndicadores);

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

		JRadioButton rdbtnParaUmMunicipio = new JRadioButton("Recalcular para um município");
		rdbtnParaUmMunicipio.setSelected(true);
		rdbtnParaUmMunicipio.addActionListener(new ActionListener() {
			/**
			 * Exibe apenas as opções que precisam ser preenchidas para recalcular para um
			 * município
			 */
			public void actionPerformed(ActionEvent e) {
				rdbtnParaUmMunicipio.setSelected(true);
				rdbtnRecalcularParaTodos.setSelected(false);
				lblDigiteUmCep.setVisible(true);
				campoFormatadoCep.setVisible(true);
			}
		});
		rdbtnParaUmMunicipio.setFont(new Font("Arial", Font.PLAIN, 16));
		rdbtnParaUmMunicipio.setBounds(10, 233, 312, 30);
		contentPane.add(rdbtnParaUmMunicipio);

		rdbtnRecalcularParaTodos = new JRadioButton("Recalcular para todos os municípios ");
		rdbtnRecalcularParaTodos.addActionListener(new ActionListener() {
			/**
			 * Exibe apenas as opções que precisam ser preenchidas para recalcular para
			 * todos os municípios
			 */
			public void actionPerformed(ActionEvent e) {
				rdbtnRecalcularParaTodos.setSelected(true);
				rdbtnParaUmMunicipio.setSelected(false);
				lblDigiteUmCep.setVisible(false);
				campoFormatadoCep.setVisible(false);
			}
		});
		rdbtnRecalcularParaTodos.setFont(new Font("Arial", Font.PLAIN, 16));
		rdbtnRecalcularParaTodos.setBounds(332, 232, 362, 30);
		contentPane.add(rdbtnRecalcularParaTodos);

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

		JButton btnRecalcular = new JButton("Recalcular");
		btnRecalcular.addActionListener(new ActionListener() {
			/**
			 * Recalcula os indicadores selecionados
			 */
			public void actionPerformed(ActionEvent e) {
				recalcularIndicadores();
			}
		});
		btnRecalcular.setFont(new Font("Arial", Font.PLAIN, 16));
		btnRecalcular.setBounds(500, 280, 194, 116);
		contentPane.add(btnRecalcular);

		JButton btnSelecionarTodos = new JButton("Selecionar Todos");
		btnSelecionarTodos.addActionListener(new ActionListener() {
			/**
			 * Seleciona todos os indicadores para serem recalculados
			 */
			public void actionPerformed(ActionEvent e) {
				int posicaoBoxSeleciona = 5;
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
		setIconifiable(true);
	}

	/**
	 * Realiza a ação de recalcular os indicadores selecionados para um ou todos os
	 * municípios, baseado nos dados preenchidos nos campos de entrada. Caso o
	 * usuário selecione a opção de recalcular para todos os municípios, será aberta
	 * uma janela de confirmação para confirmar a ação. Caso contrário, será aberta
	 * uma janela de confirmação para o recálculo dos indicadores para um município.
	 */
	private void recalcularIndicadores() {
		// Verifica se o programa já está calculando alguns indicadores
		if (JanelaPrincipal.isCalculando()) {
			new JanelaMensagem(
					"O programa já está calculando alguns indicadores.\nTente novamente após o cálculo desses indicadores.");
			return;
		}

		// Verifica se o campo Ano não está nulo
		if (textFieldAno.getText().isBlank()) {
			new JanelaMensagem("É necessário preencher o campo Ano.");
			return;
		}

		String anosRetroativos = formattedTextFieldAnos.getText();
		// Verifica se o campo anos retroativos não está nulo
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

		// Verifica se a ação de recalcular deve ser feita para todos os municípios.
		if (rdbtnRecalcularParaTodos.isSelected()) {

			ArrayList<Integer> listaIndicadoresSelecionados = new ArrayList<Integer>();
			listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);
			new JanelaConfirmaCalculoTodosMunicipios(textFieldAno.getText(), true, listaIndicadoresSelecionados,
					valorRetroativo);
			return;
		}

		// Verifica se o campo CEP não está nulo
		if (campoFormatadoCep.getText().isBlank()) {
			new JanelaMensagem("É necessário preencher o campo CEP.");
			return;
		}

		String nomeMunicipio;
		try {
			nomeMunicipio = Municipio.buscaNomeComUF(campoFormatadoCep.getText());
			ArrayList<Integer> listaIndicadoresSelecionados = Tabelas.indicadoresSelecionados(table);
			new JanelaConfirmaCalculoUmMunicipio(campoFormatadoCep.getText(), textFieldAno.getText(), nomeMunicipio,
					true, listaIndicadoresSelecionados, valorRetroativo);
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
	 * janela RecalcularIndicadores na JanelaPrincipal.
	 */
	@Override
	protected void abrirJanelaJDesktopPane(JInternalFrame janela) {
		JanelaPrincipal.setJanelaRecalcularIndicadores((RecalcularIndicadores) janela);
	}

	/**
	 * Cria uma nova instância da classe RecalcularIndicadores.
	 */
	@Override
	protected JInternalFrame instanciaClasseAberta() {
		return new RecalcularIndicadores();
	}
}
