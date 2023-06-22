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
package br.edu.mg.unifal.bcc.sgici.interfaces.internas.bd;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.sql.Connection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import br.edu.mg.unifal.bcc.sgici.banco_dados.ImportarValores;
import br.edu.mg.unifal.bcc.sgici.banco_dados.LogBancoDados;
import br.edu.mg.unifal.bcc.sgici.banco_dados.ResultadoOperacao;
import br.edu.mg.unifal.bcc.sgici.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelasInternas;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.DataDAO;

/**
 * Classe responsável por importar tabelas dos resultados das variáveis no banco de dados.
 * 
 * <p>
 * Esta classe possui uma interface gráfica que permite ao usuário importar
 * o valor das variáveis.
 * </p>
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 *
 */
public class ImportarValorVariavel extends JanelasInternas {

	private static final long serialVersionUID = 1L;
	private JTextField textAnoInicial;
	private JTextField textAnoFinal;
	private JTextField textFieldArquivo;

	/**
	 * Cria uma nova janela de importação dos valores das variáveis.
	 */
	public ImportarValorVariavel() {
		// Configuração da janela
		setTitle("Importar valor das variáveis");
		setBounds(100, 100, 500, 280);
		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setResizable(false);
		setClosable(true);
		setIconifiable(true);
		setFocusable(true);
		
		textAnoInicial = new JTextField();
		textAnoInicial.setBounds(98, 163, 120, 30);
		textAnoInicial.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JButton btnSelecionaArquivo = new JButton("Selecionar arquivo");
		btnSelecionaArquivo.setBounds(10, 11, 464, 35);
		btnSelecionaArquivo.setFont(new Font("Arial", Font.PLAIN, 16));
		btnSelecionaArquivo.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
	                JFileChooser fileChooser = new JFileChooser();
	                fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivos CSV", "csv"));
	                int result = fileChooser.showOpenDialog(null);
	                if (result == JFileChooser.APPROVE_OPTION) {
	                    File selectedFile = fileChooser.getSelectedFile();
	                    String filePath = selectedFile.getAbsolutePath();
	                    textFieldArquivo.setText(filePath);
	                }
	            }
		});
		
		JLabel lblAnoInicial = new JLabel("Ano inicial:");
		lblAnoInicial.setBounds(10, 163, 96, 28);
		lblAnoInicial.setHorizontalAlignment(SwingConstants.LEFT);
		lblAnoInicial.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JLabel lblAnoFinal = new JLabel("Ano final:");
		lblAnoFinal.setBounds(266, 163, 78, 28);
		lblAnoFinal.setHorizontalAlignment(SwingConstants.LEFT);
		lblAnoFinal.setFont(new Font("Arial", Font.PLAIN, 16));
		
		textAnoFinal = new JTextField();
		textAnoFinal.setBounds(354, 163, 120, 30);
		textAnoFinal.setFont(new Font("Arial", Font.PLAIN, 16));
		
		
		((AbstractDocument) textAnoInicial.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                    throws BadLocationException {
                if (text.matches("[0-9]+") && (fb.getDocument().getLength() + text.length() <= 4)) {
                    super.insertString(fb, offset, text, attr);
                }
            }
    
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("[0-9]+") && (fb.getDocument().getLength() - length + text.length() <= 4)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        
        ((AbstractDocument) textAnoFinal.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                    throws BadLocationException {
                if (text.matches("[0-9]+") && (fb.getDocument().getLength() + text.length() <= 4)) {
                    super.insertString(fb, offset, text, attr);
                }
            }
    
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("[0-9]+") && (fb.getDocument().getLength() - length + text.length() <= 4)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        
        textAnoInicial.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String text = textAnoInicial.getText().replaceAll("^0+(?!$)", "");
                textAnoInicial.setText(text);
            }
        });
        
        textAnoFinal.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String text = textAnoFinal.getText().replaceAll("^0+(?!$)", "");
                textAnoFinal.setText(text);
            }
        });
		
		
		JButton btnImportarValores = new JButton("Importar valores");
		btnImportarValores.setBounds(10, 207, 464, 35);
		btnImportarValores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!textFieldArquivo.getText().toString().contains(".csv")) {
					new JanelaMensagem("É necessário selecionar um arquivo .csv.");
					return;
				}
				
				if(textAnoInicial.getText().isBlank() || textAnoFinal.getText().isBlank()) {
					new JanelaMensagem("É necessário inserir o ano inicial e o ano final.");
					return;
				}
				
				int anoInicial = Integer.parseInt(textAnoInicial.getText().toString());
				int anoFinal = Integer.parseInt(textAnoFinal.getText().toString());
				
				if(!(anoInicial >= 1900 && anoInicial <= 2100 
						&& anoFinal >= 1900 && anoFinal <= 2100)) {
					new JanelaMensagem("É necessário que o período esteja entre 1900 e 2100.");
					return;
				}
				
				if(anoInicial >= anoFinal) {
					new JanelaMensagem("O ano final precisa ser igual ou posterior ao ano inicial.");
					return;
				}
				
				for(int ano = anoInicial; ano <= anoFinal; ano++) {
					try (Connection conn = ConnectionFactory.recuperarConexao();) {
						new DataDAO(conn).atualizaData(Integer.toString(ano));
					} catch (Exception e1) {
						System.out.println("Falha ao inserir o ano " + ano);
					}
				}
				
				List<ResultadoOperacao> operacoes = ImportarValores.importarValores(textFieldArquivo.getText().toString());
				JanelaPrincipal.abrirJanelas(new LogBancoDados(operacoes));
				dispose();
			}
		});
		btnImportarValores.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JLabel lblArquivo = new JLabel("Arquivo:");
		lblArquivo.setBounds(10, 62, 65, 30);
		lblArquivo.setHorizontalAlignment(SwingConstants.LEFT);
		lblArquivo.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JLabel lblPeriodo = new JLabel("Período");
		lblPeriodo.setBounds(10, 110, 464, 30);
		lblPeriodo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeriodo.setFont(new Font("Arial", Font.PLAIN, 16));
		
		textFieldArquivo = new JTextField();
		textFieldArquivo.setBounds(74, 63, 400, 30);
		textFieldArquivo.setEditable(false);
		textFieldArquivo.setFont(new Font("Arial", Font.PLAIN, 16));
		getContentPane().setLayout(null);
		getContentPane().add(btnSelecionaArquivo);
		getContentPane().add(lblArquivo);
		getContentPane().add(textFieldArquivo);
		getContentPane().add(lblPeriodo);
		getContentPane().add(lblAnoInicial);
		getContentPane().add(textAnoInicial);
		getContentPane().add(lblAnoFinal);
		getContentPane().add(textAnoFinal);
		getContentPane().add(btnImportarValores);
	}
	
	/**
	 * Sobrescreve o método da classe JanelasInternas para armazenar a referência à
	 * janela ImportarValorVariavel na JanelaPrincipal.
	 */
	@Override
	protected void abrirJanelaJDesktopPane(JInternalFrame janela) {
		JanelaPrincipal.setJanelaImportarValorVariavel((ImportarValorVariavel) janela);
	}

	/**
	 * Cria uma nova instância da classe ImportarValorVariavel.
	 */
	@Override
	protected JInternalFrame instanciaClasseAberta() {
		return new ImportarValorVariavel();
	}
}
