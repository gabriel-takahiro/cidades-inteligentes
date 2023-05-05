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
package br.edu.mg.unifal.bcc.sgici.interfaces.internas.variaveis;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.sgici.modelo.Variavel;

/**
 * Classe responsável pela interface que atualiza as variáveis
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class JanelaAtualizarVariavel extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTextField textFieldCodigoVariavel;
	private static JTextField textField_1CodigoBanco;
	private static JComboBox<String> comboBoxTipoDeBanco;
	private static JTextArea textAreaNomeVariavel;
	private static JComboBox<String> comboBox_1Atualizacao;

	/**
	 * Executa a interface responsável pela atualização das variáveis
	 * @param variavel variável a ser atualizada
	 */
	public JanelaAtualizarVariavel(Variavel variavel) {
		setBounds(100, 100, 450, 300);

		setTitle("Atualizar variável");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 228);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabelCodigoVariavel = new JLabel("Código da variável:");
		lblNewLabelCodigoVariavel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabelCodigoVariavel.setBounds(10, 11, 140, 30);
		contentPane.add(lblNewLabelCodigoVariavel);

		textFieldCodigoVariavel = new JTextField(Integer.toString(variavel.getCodigo_variavel()));
		textFieldCodigoVariavel.setToolTipText("Esse campo não pode ser nulo.");
		textFieldCodigoVariavel.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldCodigoVariavel.setBounds(155, 11, 77, 30);
		contentPane.add(textFieldCodigoVariavel);
		textFieldCodigoVariavel.setColumns(10);

		textFieldCodigoVariavel.addKeyListener(new KeyAdapter() {
			/**
			 * Permite apenas que números sejam digitados
			 */
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume();
				}
			}
		});

		JLabel lblTipoDeBanco = new JLabel("Tipo de banco:");
		lblTipoDeBanco.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTipoDeBanco.setBounds(239, 11, 106, 30);
		contentPane.add(lblTipoDeBanco);

		comboBoxTipoDeBanco = new JComboBox<String>();
		comboBoxTipoDeBanco.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBoxTipoDeBanco.setModel(new DefaultComboBoxModel<String>(new String[] { "Sidra", "BD", "IPEA" }));
		comboBoxTipoDeBanco.setSelectedIndex(0);
		comboBoxTipoDeBanco.setBounds(351, 11, 77, 30);
		contentPane.add(comboBoxTipoDeBanco);
		comboBoxTipoDeBanco.setSelectedItem(variavel.getTipoBanco());

		JLabel lblNomeDaVarivel = new JLabel("Nome da variável:");
		lblNomeDaVarivel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNomeDaVarivel.setBounds(10, 62, 131, 30);
		contentPane.add(lblNomeDaVarivel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(146, 52, 282, 45);
		contentPane.add(scrollPane);

		textAreaNomeVariavel = new JTextArea(variavel.getNome());
		textAreaNomeVariavel.setToolTipText("Esse campo não pode ser nulo.");
		textAreaNomeVariavel.setFont(new Font("Arial", Font.PLAIN, 16));
		textAreaNomeVariavel.setWrapStyleWord(true);
		textAreaNomeVariavel.setLineWrap(true);
		scrollPane.setViewportView(textAreaNomeVariavel);

		JLabel lblCdigoDoBanco = new JLabel("Código do banco:");
		lblCdigoDoBanco.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCdigoDoBanco.setBounds(10, 108, 131, 30);
		contentPane.add(lblCdigoDoBanco);

		textField_1CodigoBanco = new JTextField(variavel.getCodigo_banco());
		textField_1CodigoBanco.setFont(new Font("Arial", Font.PLAIN, 16));
		textField_1CodigoBanco.setBounds(146, 109, 282, 30);
		contentPane.add(textField_1CodigoBanco);
		textField_1CodigoBanco.setColumns(10);

		JLabel lblAtualizacao = new JLabel("Atualização:");
		lblAtualizacao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAtualizacao.setBounds(10, 151, 93, 30);
		contentPane.add(lblAtualizacao);

		comboBox_1Atualizacao = new JComboBox<String>();
		comboBox_1Atualizacao
				.setModel(new DefaultComboBoxModel<String>(new String[] { "Decenal", "Anual" }));
		comboBox_1Atualizacao.setSelectedIndex(0);
		comboBox_1Atualizacao.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBox_1Atualizacao.setBounds(113, 151, 106, 30);
		contentPane.add(comboBox_1Atualizacao);
		comboBox_1Atualizacao.setSelectedItem(variavel.getAtualizacao());

		JButton btnNewButton = new JButton("Atualizar variável");
		btnNewButton.addActionListener(new ActionListener() {
			/**
			 * Atualiza variável 
			 */
			public void actionPerformed(ActionEvent e) {
				new JanelaConfirmaAtualizacaoVariavel(variavel, estaJanela());
				return;
			}
		});
		
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 16));
		btnNewButton.setBounds(239, 149, 189, 30);
		contentPane.add(btnNewButton);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(rootPaneCheckingEnabled);
	}

	/**
	 * Retorna esta janela
	 * @return esta janela
	 */
	protected JanelaAtualizarVariavel estaJanela() {
		return this;
	}

	/**
	 * Atualiza a variável com os valores da interface
	 * @param variavel variável a ser atualizada
	 * @param janelaAtualizarVariavel esta janela
	 */
	public static void atualizarVariavel(Variavel variavel, JanelaAtualizarVariavel janelaAtualizarVariavel) {
		String codigo_variavel = textFieldCodigoVariavel.getText().replace(" ", "");
		String nome_variavel = textAreaNomeVariavel.getText().replace(" ", "");

		if (codigo_variavel.equals("")) {
			new JanelaMensagem("O campo \"Código da variável\" não pode ser nulo.");
			return;
		}
		if (nome_variavel.equals("")) {
			new JanelaMensagem("O campo \"Nome da variável\" não pode ser nulo.");
			return;
		}
		String banco = (String) comboBoxTipoDeBanco.getSelectedItem();
		String codigo = textField_1CodigoBanco.getText().replace(" ", "");

		int codigo_da_variavel;
		try {
			codigo_da_variavel = Integer.parseInt(codigo_variavel);
		} catch (Exception e1) {
			new JanelaMensagem("Código da variável inválido.\nNúmero máximo permitido: 2147483647");
			return;
		}

		if (banco.equals("BD")) {
			if (codigo.equals("")) {
				Variavel.atualizarVariavel(codigo_da_variavel, (String) comboBoxTipoDeBanco.getSelectedItem(),
						textAreaNomeVariavel.getText(), null, (String) comboBox_1Atualizacao.getSelectedItem(),
						variavel.isPadrao(), variavel.getCodigo_variavel());
				janelaAtualizarVariavel.dispose();
				return;
			}
			new JanelaMensagem("O campo \"Código do banco\" deve ser nulo para o tipo de banco \"BD\".");
			return;
		}
		if (codigo.equals("")) {
			new JanelaMensagem("O campo \"Código do banco\" não pode ser nulo.");
			return;
		}
		try {
			Variavel.atualizarVariavel(codigo_da_variavel, (String) comboBoxTipoDeBanco.getSelectedItem(),
					textAreaNomeVariavel.getText(), codigo, (String) comboBox_1Atualizacao.getSelectedItem(),
					variavel.isPadrao(), variavel.getCodigo_variavel());
			janelaAtualizarVariavel.dispose();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}

}
