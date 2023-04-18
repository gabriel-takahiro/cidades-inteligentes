package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.calculo;

import java.awt.Font;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;

public class ProgressoCalculo extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static JProgressBar progressBar;
	private static JLabel lblIndicadores;
	private JPanel contentPane;
	private static AtomicInteger valorTotal = new AtomicInteger(0);
	private static AtomicInteger valorAtual = new AtomicInteger(1);
	private static JLabel lblMunicipio;
	private static JLabel lblProgresso;

	public ProgressoCalculo(String nomeMunicipio) {
		setBounds(100, 100, 500, 200);
		setTitle("Progresso dos calculos");
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(100); // define o valor máximo da barra de progresso
		progressBar.setStringPainted(true); // habilita a exibição do valor em formato de string na barra de progresso

		lblIndicadores = new JLabel("Calculando os indicadores para o município:");
		lblIndicadores.setFont(new Font("Arial", Font.PLAIN, 16));
		
		lblMunicipio = new JLabel(nomeMunicipio);
		lblMunicipio.setFont(new Font("Arial", Font.PLAIN, 16));
		
		lblProgresso = new JLabel("Progresso: ");
		lblProgresso.setFont(new Font("Arial", Font.PLAIN, 16));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addComponent(lblIndicadores, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addComponent(lblMunicipio, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblProgresso, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblIndicadores, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblMunicipio, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblProgresso, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setResizable(false);
		setIconifiable(false);
	}

	// Método para atualizar o valor atual da barra de progresso
	public static void atualizarValorAtual() {
		int novoValor = valorAtual.incrementAndGet();
		progressBar.setValue(novoValor);
		lblProgresso.setText("Progresso:  " + valorAtual.get() + " de " + valorTotal.get());
	}
	
	public static void atualizarValorAtual(String nome) {
		int novoValor = valorAtual.incrementAndGet();
		lblMunicipio.setText(nome);
		progressBar.setValue(novoValor);
		lblProgresso.setText("Progresso:  " + valorAtual.get() + " de " + valorTotal.get());
	}

	// Método para definir o valor total da barra de progresso
	public static void setValorTotal(List<Variavel> listaVariaveisNaoValoradas) {
		int instancias = 1;
		for (Variavel variavel : listaVariaveisNaoValoradas) {
			if(variavel.getTipoBanco().equals("IPEA")) {
				instancias++;
			}
		}
		if(instancias == 1) {
			progressBar.setMinimum(0);
			progressBar.setMaximum(2);
			progressBar.setValue(1);
			lblProgresso.setText("Progresso:  1 de 2");
			return;
		}
		valorTotal.set(instancias+1);
		progressBar.setMinimum(0);
		progressBar.setValue(1);
		progressBar.setMaximum(instancias+1);
		lblProgresso.setText("Progresso:  1 de " + valorTotal.get());
	}

	public static void setValorTotal(int quantidadeMunicipios) {
		valorTotal.set(quantidadeMunicipios);
		valorAtual.set(0);
		progressBar.setMinimum(0);
		progressBar.setValue(valorAtual.get());
		progressBar.setMaximum(valorTotal.get() + 1);
		lblProgresso.setText("Progresso:  " + valorAtual.get() + " de " + valorTotal.get());
	}

	public static void variaveisTodosMunicipios(List<Variavel> listaVariaveisRelacionadas) {
		int variaveis = listaVariaveisRelacionadas.size();
		
		lblIndicadores.setText("Buscando as variáveis.");
		lblMunicipio.setText("");
		
		valorTotal.set(variaveis);
		progressBar.setMinimum(0);
		progressBar.setValue(0);
		progressBar.setMaximum(valorTotal.get());
		lblProgresso.setText("Progresso:  0 de " + valorTotal.get() + " variáveis.");
	}

	public static void atualizarVariaveisTodosMunicipios() {
		int novoValor = valorAtual.incrementAndGet();
		progressBar.setValue(novoValor);
		lblProgresso.setText("Progresso:  " + valorAtual.get() + " de " + valorTotal.get() + " variáveis.");
	}
}
