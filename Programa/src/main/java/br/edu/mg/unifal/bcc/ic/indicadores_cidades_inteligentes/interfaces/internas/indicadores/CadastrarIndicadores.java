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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.indicadores;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.Janelas;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Meta;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.PossuiVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.SequenciaCalculo;

/**
 * Classe responsável pela interface que cadastra os indicadores
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class CadastrarIndicadores extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTextField textFieldNomeIndicador;
	private static JTextField textFieldTipoPlano;
	private static JTextField textFieldNomePlano;
	private static JTextField textFieldMetodoCalculo;
	private static JTextArea textAreaInformacoesTecnicas;
	private static JTextArea textAreaDescricao;
	private static JComboBox<String> comboBoxEixo;
	private static JComboBox<String> comboBoxMeta;
	private static JTable tableVariaveis;
	
	private static int contadorParenteses = 0;
	
	/**
	 * Quando "true" significa que os colchetes estão abertos; quando "false" significa que os 
	 * colchetes estão fechados
	 */
	private static boolean estadoColchetes = false;

	/**
	 * Executa a interface responsável pelo cadastro dos indicadores
	 */
	public CadastrarIndicadores() {
		setTitle("Cadastrar indicador");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 641, 615);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNomeIndicador = new JLabel("Nome do indicador:");
		lblNomeIndicador.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNomeIndicador.setBounds(10, 10, 139, 22);
		panel.add(lblNomeIndicador);

		textFieldNomeIndicador = new JTextField();
		textFieldNomeIndicador.setToolTipText("Este campo não pode ser nulo.");
		textFieldNomeIndicador.setFont(new Font("Arial", Font.PLAIN, 14));
		textFieldNomeIndicador.setBounds(159, 10, 446, 22);
		panel.add(textFieldNomeIndicador);
		textFieldNomeIndicador.setColumns(10);

		JLabel lblTipoDoPlano = new JLabel("Tipo do plano:");
		lblTipoDoPlano.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTipoDoPlano.setBounds(10, 37, 139, 19);
		panel.add(lblTipoDoPlano);

		textFieldTipoPlano = new JTextField();
		textFieldTipoPlano.setFont(new Font("Arial", Font.PLAIN, 14));
		textFieldTipoPlano.setColumns(10);
		textFieldTipoPlano.setBounds(159, 36, 446, 22);
		panel.add(textFieldTipoPlano);

		JLabel lblNomeDoPlano = new JLabel("Nome do plano:");
		lblNomeDoPlano.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNomeDoPlano.setBounds(10, 62, 139, 19);
		panel.add(lblNomeDoPlano);

		textFieldNomePlano = new JTextField();
		textFieldNomePlano.setFont(new Font("Arial", Font.PLAIN, 14));
		textFieldNomePlano.setColumns(10);
		textFieldNomePlano.setBounds(159, 61, 446, 22);
		panel.add(textFieldNomePlano);

		JLabel lblEixo = new JLabel("Eixo:");
		lblEixo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEixo.setBounds(10, 92, 40, 19);
		panel.add(lblEixo);

		comboBoxEixo = new JComboBox<String>();
		comboBoxEixo.setMaximumRowCount(5);
		comboBoxEixo.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBoxEixo.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Ação Local para a Saúde", "Bens Naturais Comuns",
						"Consumo Responsável e Opções de Estilo de Vida", "Cultura para a Sustentabilidade",
						"Do Local para o Global", "Economia Local Dinâmica, Criativa e Sustentável",
						"Educação para a Sustentabilidade e Qualidade de Vida",
						"Equidade, Justiça Social e Cultura de Paz", "Gestão Local para a Sustentabilidade",
						"Governança", "Melhor Mobilidade, Menos Tráfego", "Planejamento e Desenho Urbano" }));
		comboBoxEixo.setSelectedIndex(0);
		comboBoxEixo.setBounds(52, 92, 389, 22);
		panel.add(comboBoxEixo);

		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDescricao.setBounds(10, 142, 76, 19);
		panel.add(lblDescricao);

		JScrollPane scrollPaneDescricao = new JScrollPane();
		scrollPaneDescricao.setBounds(91, 125, 514, 51);
		panel.add(scrollPaneDescricao);

		textAreaDescricao = new JTextArea();
		textAreaDescricao.setToolTipText("Este campo não pode ser nulo.");
		textAreaDescricao.setFont(new Font("Arial", Font.PLAIN, 14));
		textAreaDescricao.setWrapStyleWord(true);
		textAreaDescricao.setLineWrap(true);
		scrollPaneDescricao.setViewportView(textAreaDescricao);

		JLabel lblInformacoesTecnicas = new JLabel("Informações técnicas:");
		lblInformacoesTecnicas.setFont(new Font("Arial", Font.PLAIN, 16));
		lblInformacoesTecnicas.setBounds(10, 203, 159, 19);
		panel.add(lblInformacoesTecnicas);

		JScrollPane scrollPaneInformacoesTecnicas = new JScrollPane();
		scrollPaneInformacoesTecnicas.setBounds(168, 187, 436, 49);
		panel.add(scrollPaneInformacoesTecnicas);

		textAreaInformacoesTecnicas = new JTextArea();
		scrollPaneInformacoesTecnicas.setViewportView(textAreaInformacoesTecnicas);
		textAreaInformacoesTecnicas.setFont(new Font("Arial", Font.PLAIN, 14));
		textAreaInformacoesTecnicas.setWrapStyleWord(true);
		textAreaInformacoesTecnicas.setLineWrap(true);

		JButton btnLimparCampos = new JButton("Limpar campos");
		btnLimparCampos.addActionListener(new ActionListener() {
			/**
			 * Limpa todos os campos 
			 */
			public void actionPerformed(ActionEvent e) {
				new JanelaConfirmaLimpeza(true);
			}
		});
		btnLimparCampos.setFont(new Font("Arial", Font.PLAIN, 14));
		btnLimparCampos.setBounds(304, 526, 301, 40);
		panel.add(btnLimparCampos);

		JButton btnCadastrarIndicador = new JButton("Cadastrar indicador");
		btnCadastrarIndicador.addActionListener(new ActionListener() {
			/**
			 * Cadastrar indicador
			 */
			public void actionPerformed(ActionEvent e) {
				new JanelaConfirmaCadastroIndicador();
			}
		});
		btnCadastrarIndicador.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCadastrarIndicador.setBounds(10, 526, 294, 40);
		panel.add(btnCadastrarIndicador);

		JLabel lblMeta = new JLabel("Meta:");
		lblMeta.setFont(new Font("Arial", Font.PLAIN, 16));
		lblMeta.setBounds(451, 92, 40, 19);
		panel.add(lblMeta);

		JLabel lblMetodoCalculo = new JLabel("Método de cálculo:");
		lblMetodoCalculo.setFont(new Font("Arial", Font.PLAIN, 16));
		lblMetodoCalculo.setBounds(10, 256, 139, 19);
		panel.add(lblMetodoCalculo);

		textFieldMetodoCalculo = new JTextField();
		textFieldMetodoCalculo.setToolTipText("Este campo não pode ser nulo.");
		textFieldMetodoCalculo.setEditable(false);
		textFieldMetodoCalculo.setForeground(Color.BLACK);
		textFieldMetodoCalculo.setBackground(Color.WHITE);
		textFieldMetodoCalculo.setFont(new Font("Arial", Font.PLAIN, 14));
		textFieldMetodoCalculo.setColumns(10);
		textFieldMetodoCalculo.setBounds(153, 247, 452, 32);
		panel.add(textFieldMetodoCalculo);

		JScrollPane scrollPaneTabelaVariaveis = new JScrollPane();
		scrollPaneTabelaVariaveis.setBounds(10, 385, 595, 130);
		panel.add(scrollPaneTabelaVariaveis);

		tableVariaveis = new JTable();
		tableVariaveis.setFont(new Font("Arial", Font.PLAIN, 12));
		scrollPaneTabelaVariaveis.setViewportView(tableVariaveis);

		JLabel lblNewLabelVariaveis = new JLabel("Variaveis cadastradas no banco de dados:");
		lblNewLabelVariaveis.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabelVariaveis.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabelVariaveis.setBounds(10, 358, 595, 22);
		panel.add(lblNewLabelVariaveis);

		JButton btn0 = new JButton("0");
		btn0.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "0" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoNumeros(metCalc, tamanho, "0");
			}
		});
		btn0.setFont(new Font("Arial", Font.PLAIN, 16));
		btn0.setBounds(175, 286, 43, 33);
		panel.add(btn0);

		JButton btn1 = new JButton("1");
		btn1.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "1" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoNumeros(metCalc, tamanho, "1");
			}
		});
		btn1.setFont(new Font("Arial", Font.PLAIN, 16));
		btn1.setBounds(218, 286, 43, 33);
		panel.add(btn1);

		JButton btn2 = new JButton("2");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Adiciona o elemento "2" no campo método de cálculo 
				 */
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoNumeros(metCalc, tamanho, "2");
			}
		});
		btn2.setFont(new Font("Arial", Font.PLAIN, 16));
		btn2.setBounds(261, 286, 43, 33);
		panel.add(btn2);

		JButton btn3 = new JButton("3");
		btn3.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "3" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoNumeros(metCalc, tamanho, "3");
			}
		});
		btn3.setFont(new Font("Arial", Font.PLAIN, 16));
		btn3.setBounds(304, 286, 43, 33);
		panel.add(btn3);

		JButton btn4 = new JButton("4");
		btn4.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "4" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoNumeros(metCalc, tamanho, "4");
			}
		});
		btn4.setFont(new Font("Arial", Font.PLAIN, 16));
		btn4.setBounds(347, 286, 43, 33);
		panel.add(btn4);

		JButton btn5 = new JButton("5");
		btn5.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "5" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoNumeros(metCalc, tamanho, "5");
			}
		});
		btn5.setFont(new Font("Arial", Font.PLAIN, 16));
		btn5.setBounds(390, 286, 43, 33);
		panel.add(btn5);

		JButton btn6 = new JButton("6");
		btn6.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "6" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoNumeros(metCalc, tamanho, "6");
			}
		});
		btn6.setFont(new Font("Arial", Font.PLAIN, 16));
		btn6.setBounds(433, 286, 43, 33);
		panel.add(btn6);

		JButton btn7 = new JButton("7");
		btn7.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "7" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoNumeros(metCalc, tamanho, "7");
			}
		});
		btn7.setFont(new Font("Arial", Font.PLAIN, 16));
		btn7.setBounds(476, 286, 43, 33);
		panel.add(btn7);

		JButton btn8 = new JButton("8");
		btn8.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "8" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoNumeros(metCalc, tamanho, "8");
			}
		});
		btn8.setFont(new Font("Arial", Font.PLAIN, 16));
		btn8.setBounds(519, 286, 43, 33);
		panel.add(btn8);

		JButton btn9 = new JButton("9");
		btn9.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "9" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoNumeros(metCalc, tamanho, "9");
			}
		});
		btn9.setFont(new Font("Arial", Font.PLAIN, 16));
		btn9.setBounds(562, 286, 43, 33);
		panel.add(btn9);

		JButton btnMultiplicar = new JButton("x");
		btnMultiplicar.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "x" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoOperadores(metCalc, tamanho, "x");
			}
		});
		btnMultiplicar.setFont(new Font("Arial", Font.PLAIN, 16));
		btnMultiplicar.setBounds(175, 319, 43, 33);
		panel.add(btnMultiplicar);

		JButton btnDividir = new JButton("/");
		btnDividir.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "/" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoOperadores(metCalc, tamanho, "/");
			}
		});
		btnDividir.setFont(new Font("Arial", Font.PLAIN, 16));
		btnDividir.setBounds(218, 319, 43, 33);
		panel.add(btnDividir);

		JButton btnSomar = new JButton("+");
		btnSomar.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "+" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoOperadores(metCalc, tamanho, "+");
			}
		});
		btnSomar.setFont(new Font("Arial", Font.PLAIN, 16));
		btnSomar.setBounds(261, 319, 43, 33);
		panel.add(btnSomar);

		JButton btnSubtrair = new JButton("-");
		btnSubtrair.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "-" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoOperadores(metCalc, tamanho, "-");
			}
		});
		btnSubtrair.setFont(new Font("Arial", Font.PLAIN, 16));
		btnSubtrair.setBounds(304, 319, 43, 33);
		panel.add(btnSubtrair);

		JButton btnAbreColchetes = new JButton("[");
		btnAbreColchetes.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "[" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoInicio(metCalc, tamanho, "[");
			}
		});
		btnAbreColchetes.setFont(new Font("Arial", Font.PLAIN, 16));
		btnAbreColchetes.setBounds(347, 319, 43, 33);
		panel.add(btnAbreColchetes);

		JButton btnFechaColchetes = new JButton("]");
		btnFechaColchetes.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "]" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoFim(metCalc, tamanho, "]");
			}
		});
		btnFechaColchetes.setFont(new Font("Arial", Font.PLAIN, 16));
		btnFechaColchetes.setBounds(390, 319, 43, 33);
		panel.add(btnFechaColchetes);

		JButton btnAbreParenteses = new JButton("(");
		btnAbreParenteses.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento "(" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoInicio(metCalc, tamanho, "(");
			}
		});
		btnAbreParenteses.setFont(new Font("Arial", Font.PLAIN, 16));
		btnAbreParenteses.setBounds(433, 319, 43, 33);
		panel.add(btnAbreParenteses);

		JButton btnFechaParenteses = new JButton(")");
		btnFechaParenteses.addActionListener(new ActionListener() {
			/**
			 * Adiciona o elemento ")" no campo método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				String metCalc = textFieldMetodoCalculo.getText();
				testeGenericoFim(metCalc, tamanho, ")");
			}
		});
		btnFechaParenteses.setFont(new Font("Arial", Font.PLAIN, 16));
		btnFechaParenteses.setBounds(476, 319, 43, 33);
		panel.add(btnFechaParenteses);

		JButton btnApagar = new JButton("Apagar");
		btnApagar.addActionListener(new ActionListener() {
			/**
			 * Apaga o último elemento do campo método de cálculo
			 */
			public void actionPerformed(ActionEvent e) {
				int tamanho = textFieldMetodoCalculo.getText().length();
				if (textFieldMetodoCalculo.getText().substring(tamanho - 1, tamanho).equals("}")) {
					for (int i = tamanho; i > 0; i--) {
						if (textFieldMetodoCalculo.getText().substring(i - 1, i).equals("{")) {
							textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText().substring(0, i - 1));
							i = -1;
						}
					}
					return;
				}
				if (textFieldMetodoCalculo.getText().substring(tamanho - 1, tamanho).equals("[")) {
					textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText().substring(0, tamanho - 1));
					estadoColchetes = false;
					return;
				}
				if (textFieldMetodoCalculo.getText().substring(tamanho - 1, tamanho).equals("(")) {
					textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText().substring(0, tamanho - 1));
					contadorParenteses--;
					return;
				}
				if (textFieldMetodoCalculo.getText().substring(tamanho - 1, tamanho).equals("]")) {
					textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText().substring(0, tamanho - 1));
					estadoColchetes = true;
					return;
				}
				if (textFieldMetodoCalculo.getText().substring(tamanho - 1, tamanho).equals(")")) {
					textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText().substring(0, tamanho - 1));
					contadorParenteses++;
					return;
				}

				textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText().substring(0, tamanho - 1));
			}
		});
		btnApagar.setFont(new Font("Arial", Font.PLAIN, 16));
		btnApagar.setBounds(519, 319, 86, 33);
		panel.add(btnApagar);

		comboBoxMeta = new JComboBox<String>();
		comboBoxMeta.setMaximumRowCount(5);
		comboBoxMeta.setFont(new Font("Arial", Font.PLAIN, 14));
		comboBoxMeta.setBounds(498, 92, 107, 22);
		panel.add(comboBoxMeta);

		mostrarMetas();

		JButton btnIncluirIndicadores = new JButton("Incluir indicadores");
		btnIncluirIndicadores.addActionListener(new ActionListener() {
			/**
			 * Inclui indicador no método de cálculo 
			 */
			public void actionPerformed(ActionEvent e) {
				new JanelaIncluirIndicador(true);
			}
		});
		btnIncluirIndicadores.setFont(new Font("Arial", Font.PLAIN, 14));
		btnIncluirIndicadores.setBounds(10, 286, 159, 66);
		panel.add(btnIncluirIndicadores);

		setLocation(0, 0);
		try {
			mostrarVariaveis();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		setVisible(true);
		setMaximizable(false);
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
	}

	/**
	 * Mostra as variáveis disponíveis no banco de dados
	 */
	public static void mostrarVariaveis() {
		tableVariaveis.setModel(new DefaultTableModel(new Object[][] {}, new String[][] {}) {

			private static final long serialVersionUID = 1L;

			boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		Variavel.mostrarVariaveis(tableVariaveis, false);
	}
	
	/**
	 * Mostra as ODS disponíveis no banco de dados
	 */
	public static void mostrarMetas() {
		comboBoxMeta.setModel(new DefaultComboBoxModel<>());
		Meta.buscarMetas(comboBoxMeta);
	}

	/**
	 * Apaga todos os campos disponíveis na interface
	 */
	public static void apagarCampos() {

		textFieldNomeIndicador.setText("");
		textFieldTipoPlano.setText("");
		textFieldNomePlano.setText("");
		textFieldMetodoCalculo.setText("");
		textAreaDescricao.setText("");
		textAreaInformacoesTecnicas.setText("");
		comboBoxMeta.setSelectedIndex(0);
	}

	/**
	 * Cadastra o indicador no banco de dados
	 */
	public static void cadastrarIndicador() {
		if (textFieldNomeIndicador.getText().equals("")) {
			new JanelaMensagem("O campo \"Nome do indicador\" não pode ser nulo.");
			return;
		}
		if (textAreaDescricao.getText().equals("")) {
			new JanelaMensagem("O campo \"Descrição\" não pode ser nulo.");
			return;
		}
		if (textFieldMetodoCalculo.getText().equals("")) {
			new JanelaMensagem("O campo \"Método de cálculo\" não pode ser nulo.");
			return;
		}
		if (estadoColchetes) {
			new JanelaMensagem("É necessário fechar os colchetes no método de cálculo.");
			return;
		}
		if (contadorParenteses != 0) {
			new JanelaMensagem("É necessário fechar todos os parênteses no método de cálculo.");
			return;
		}

		String meta = (String) comboBoxMeta.getSelectedItem();
		if (meta.equals("")) {
			meta = null;
		}
		
		try {
			List<Integer> listaVariaveis = SequenciaCalculo.listaVariaveis(textFieldMetodoCalculo.getText());
			Variavel.verificaVariaveis(listaVariaveis);
			Indicador.cadastrarIndicador(textFieldNomeIndicador.getText(), textFieldMetodoCalculo.getText(),
					(String) comboBoxEixo.getSelectedItem(), textFieldTipoPlano.getText(), textFieldNomePlano.getText(),
					textAreaDescricao.getText(), textAreaInformacoesTecnicas.getText(), meta);
			PossuiVariavel.inserir(textFieldMetodoCalculo.getText(), listaVariaveis);
			JanelaPrincipal.fechaCadastrarIndicadores();
		} catch (Exception e) {
			new JanelaMensagem(e.getMessage());
		}
	}

	/**
	 * Atualiza o campo "meta"
	 */
	public static void atualizarMeta() {
		comboBoxMeta.setModel(new DefaultComboBoxModel<>());
		Meta.buscarMetas(comboBoxMeta);
	}

	/**
	 * Coloca o indicador no método de cálculo após fazer a verificação da existência dele no banco de dados
	 * @param codigo_indicador código do indicador a ser verificado no banco de dados
	 */
	public static void colocarIndicador(String codigo_indicador) {
		try {
			int tamanho = textFieldMetodoCalculo.getText().length();
			String metodo_calculo = textFieldMetodoCalculo.getText();
			String elemento = metodo_calculo.substring(tamanho - 1, tamanho);

			if (tamanho == 0 || elemento.equals("+") || elemento.equals("-") || elemento.equals("x") || elemento.equals("/")
					|| elemento.equals("(")) {
				textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText() + "{" + codigo_indicador + "}");
				return;
			}
			new JanelaMensagem("Não é possível adicionar o indicador após \""
					+ textFieldMetodoCalculo.getText().substring(tamanho - 1, tamanho) + "\"");
		} catch (Exception e) {
			textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText() + "{" + codigo_indicador + "}");
		}
	}

	/**
	 * Verificação utilizada para a inserção dos operadores: subtração (-), soma (+), multiplicação (x) e divisão (/)
	 * @param metodo_calculo método de cálculo atual do indicador
	 * @param tamanho tamanho atual do método de cálculo
	 * @param valor operador a ser inserido
	 */
	public void testeGenericoOperadores(String metodo_calculo, int tamanho, String valor) {

		try {
			String elemento = metodo_calculo.substring(tamanho - 1, tamanho);

			if (elemento.equals("0") || elemento.equals("1") || elemento.equals("2") || elemento.equals("3")
					|| elemento.equals("4") || elemento.equals("5") || elemento.equals("6") || elemento.equals("7")
					|| elemento.equals("8") || elemento.equals("9") || elemento.equals("]") || elemento.equals(")")
					|| elemento.equals("}")) {
				if (estadoColchetes) {
					new JanelaMensagem("Não é possível colocar operadores dentro do colchetes.");
					return;
				}
				textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText() + valor);
				return;
			}
			new JanelaMensagem("Não é possível utilizar \"" + valor + "\" após \""
					+ textFieldMetodoCalculo.getText().substring(tamanho - 1, tamanho) + "\"");
		} catch (Exception e) {
			new JanelaMensagem("Não é possível utilizar \"" + valor + "\" no começo do método.");
		}
	}

	/**
	 * Verificação utilizada para a inserção de números
	 * @param metodo_calculo método de cálculo atual do indicador
	 * @param tamanho tamanho atual do método de cálculo
	 * @param valor número a ser inserido
	 */
	public void testeGenericoNumeros(String metodo_calculo, int tamanho, String valor) {
		try {
			String elemento = metodo_calculo.substring(tamanho - 1, tamanho);

			if (tamanho == 0 || elemento.equals("0") || elemento.equals("1") || elemento.equals("2") || elemento.equals("3")
					|| elemento.equals("4") || elemento.equals("5") || elemento.equals("6") || elemento.equals("7")
					|| elemento.equals("8") || elemento.equals("9") || elemento.equals("(") || elemento.equals("[")
					|| elemento.equals("+") || elemento.equals("-") || elemento.equals("x") || elemento.equals("/")) {
				textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText() + valor);
				return;
			}
			new JanelaMensagem("Não é possível utilizar \"" + valor + "\" após \""
					+ textFieldMetodoCalculo.getText().substring(tamanho - 1, tamanho) + "\"");
		} catch (Exception e) {
			textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText() + valor);
		}
	}

	/**
	 * Verificação utilizada para a inserção de abre parênteses "(" ou abre chaves "["
	 * @param metodo_calculo método de cálculo atual do indicador
	 * @param tamanho tamanho atual do método de cálculo
	 * @param valor abre parênteses "(" ou abre chaves "["
	 */
	public void testeGenericoInicio(String metodo_calculo, int tamanho, String valor) { // Usado em "(" e "["
		try {
			String elemento = metodo_calculo.substring(tamanho - 1, tamanho);

			if (tamanho == 0 || elemento.equals("+") || elemento.equals("-") || elemento.equals("x") || elemento.equals("/")
					|| elemento.equals("(")) {
				if (estadoColchetes) {
					if (valor.equals("(")) {
						new JanelaMensagem("Não é possível abrir um parênteses dentro de um colchetes.");
						return;
					}
					new JanelaMensagem("Não é possível abrir um colchetes dentro de um colchetes.");
					return;
				}
				textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText() + valor);
				if (valor.equals("(")) {
					contadorParenteses++;
				}
				if (valor.equals("[")) {
					estadoColchetes = true;
				}
				return;
			}
			new JanelaMensagem("Não é possível utilizar \"" + valor + "\" após \""
					+ textFieldMetodoCalculo.getText().substring(tamanho - 1, tamanho) + "\"");
		} catch (Exception e) {
			if (estadoColchetes) {
				if (valor.equals("(")) {
					new JanelaMensagem("Não é possível abrir um parênteses dentro de um colchetes.");
					return;
				}
				new JanelaMensagem("Não é possível abrir um colchetes dentro de um colchetes.");
				return;
			}
			textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText() + valor);
			if (valor.equals("(")) {
				contadorParenteses++;
			}
			if (valor.equals("[")) {
				estadoColchetes = true;
			}
		}
	}

	/**
	 * Verificação utilizada para a inserção de fecha parênteses ")" ou fecha chaves "]"
	 * @param metodo_calculo método de cálculo atual do indicador
	 * @param tamanho tamanho atual do método de cálculo
	 * @param valor fecha parênteses ")" ou fecha chaves "]"
	 */
	public void testeGenericoFim(String metodo_calculo, int tamanho, String valor) {
		try {
			String elemento = metodo_calculo.substring(tamanho - 1, tamanho);

			if (elemento.equals("0") || elemento.equals("1") || elemento.equals("2") || elemento.equals("3")
					|| elemento.equals("4") || elemento.equals("5") || elemento.equals("6") || elemento.equals("7")
					|| elemento.equals("8") || elemento.equals("9") || elemento.equals(")") || elemento.equals("]")
					|| elemento.equals("}")) {
				if (estadoColchetes) {
					if (valor.equals(")")) {
						new JanelaMensagem("Não é possível fechar um parênteses dentro de um colchetes.");
						return;
					}
					textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText() + valor);
					estadoColchetes = false;
					return;
				}
				if (valor.equals(")")) {
					if (contadorParenteses <= 0) {
						new JanelaMensagem("Não é possível fechar um parênteses antes de abri-lo.");
						return;
					}
					textFieldMetodoCalculo.setText(textFieldMetodoCalculo.getText() + valor);
					contadorParenteses--;
					return;
				}
				new JanelaMensagem("Não é possível fechar um colchetes antes de abri-lo.");
				return;
			}
			new JanelaMensagem("Não é possível utilizar \"" + valor + "\" após \""
					+ textFieldMetodoCalculo.getText().substring(tamanho - 1, tamanho) + "\"");
		} catch (Exception e) {
			new JanelaMensagem("Não é possível utilizar \"" + valor + "\" no começo do método.");
		}
	}

	@Override
	public void abrirJanela(JInternalFrame janelaCadastrarIndicadores, JDesktopPane desktopPane) {
		if (janelaCadastrarIndicadores.isClosed()) {
			CadastrarIndicadores cadastrarIndicadores = new CadastrarIndicadores();
			desktopPane.add(cadastrarIndicadores);
			JanelaPrincipal.setJanelaCadastrarIndicadores(cadastrarIndicadores);
			return;
		}
		if (janelaCadastrarIndicadores.isVisible()) {
			janelaCadastrarIndicadores.moveToFront();
			return;
		}
		janelaCadastrarIndicadores.setVisible(true);
		janelaCadastrarIndicadores.moveToFront();
	}
}
