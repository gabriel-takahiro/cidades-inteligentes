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

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaConfirmaCadastroODS;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.ODS;

/**
 * Classe responsável pela interface que cadastra as ODS
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 *
 */
public class CadastrarODS extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JTextField textFieldODS;
	private static JTextArea textAreaTextoODS;

	/**
	 * Executa a interface responsável pelo cadastro das ODS
	 */
	public CadastrarODS() {
		setTitle("Cadastrar ODS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 179);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textFieldODS = new JTextField();
		textFieldODS.setToolTipText("Este campo não pode ser nulo.");
		textFieldODS.setFont(new Font("Arial", Font.PLAIN, 16));
		textFieldODS.setBounds(157, 6, 177, 25);
		contentPane.add(textFieldODS);
		textFieldODS.setColumns(10);

		JLabel lblNewLabelODS = new JLabel("Número da ODS:");
		lblNewLabelODS.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabelODS.setBounds(10, 6, 137, 25);
		contentPane.add(lblNewLabelODS);

		JLabel lblNomeDaODS = new JLabel("Nome da ODS:");
		lblNomeDaODS.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNomeDaODS.setBounds(10, 56, 119, 25);
		contentPane.add(lblNomeDaODS);

		JScrollPane scrollPaneTextoODS = new JScrollPane();
		scrollPaneTextoODS.setBounds(136, 42, 198, 55);
		contentPane.add(scrollPaneTextoODS);

		textAreaTextoODS = new JTextArea();
		textAreaTextoODS.setToolTipText("Este campo não pode ser nulo.");
		textAreaTextoODS.setFont(new Font("Arial", Font.PLAIN, 16));
		textAreaTextoODS.setWrapStyleWord(true);
		textAreaTextoODS.setLineWrap(true);
		scrollPaneTextoODS.setViewportView(textAreaTextoODS);

		JButton btnCadastrarODS = new JButton("Cadastrar ODS");
		btnCadastrarODS.addActionListener(new ActionListener() {
			/**
			 * Cadastrar ODS
			 */
			public void actionPerformed(ActionEvent e) {
				new JanelaConfirmaCadastroODS();
			}
		});
		btnCadastrarODS.setFont(new Font("Arial", Font.PLAIN, 16));
		btnCadastrarODS.setBounds(10, 108, 324, 30);
		contentPane.add(btnCadastrarODS);

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setClosable(true);
		setIconifiable(true);

	}

	/**
	 * Cadastra a ODS no banco de dados
	 */
	public static void cadastrarODS() {
		String numero_ods = textFieldODS.getText().replace(" ", "");
		String nome_objetivo = textAreaTextoODS.getText();
		if (numero_ods.equals("")) {
			new JanelaMensagem("O campo \"Número da ODS\" não pode ser nulo.");
			return;
		}
		if (nome_objetivo.equals("")) {
			new JanelaMensagem("O campo \"Nome da ODS\" não pode ser nulo.");
			return;
		}

		try {
			ODS.cadastrarODS(new ODS(Integer.parseInt(numero_ods), nome_objetivo));
			JanelaPrincipal.fechaCadastrarODS();
		} catch (Exception e) {
			return;
		}
	}

	@Override
	public void abrirJanela(JInternalFrame janelaCadastrarODS, JDesktopPane desktopPane) {
		if (janelaCadastrarODS.isClosed()) {
			CadastrarODS cadastrarODS = new CadastrarODS();
			desktopPane.add(cadastrarODS);
			JanelaPrincipal.setJanelaCadastrarODS(cadastrarODS);
			return;
		}
		if (janelaCadastrarODS.isVisible()) {
			janelaCadastrarODS.moveToFront();
			return;
		}
		janelaCadastrarODS.setVisible(true);
		janelaCadastrarODS.moveToFront();
	}

}
