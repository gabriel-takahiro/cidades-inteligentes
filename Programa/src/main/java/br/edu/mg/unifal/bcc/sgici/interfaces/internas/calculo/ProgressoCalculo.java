package br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo;

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

import br.edu.mg.unifal.bcc.sgici.modelo.Variavel;

/**
 * Essa classe é responsável por criar uma interface gráfica que exibe uma barra
 * de progresso para indicar o progresso do cálculo dos indicadores. A barra de
 * progresso é configurada de acordo com a atividade que está sendo realizada,
 * como por exemplo, a busca das variáveis ou o cálculo dos indicadores para
 * todos os municípios. Durante a busca das variáveis, a barra de progresso
 * exibe a quantidade de variáveis já buscadas em relação ao total de variáveis
 * que serão buscadas. Já durante o cálculo dos indicadores para todos os
 * municípios, a barra de progresso exibe a quantidade de municípios que já
 * tiveram todos os indicadores selecionados calculados.
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 *
 */
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

	/**
	 * Cria uma interface gráfica que exibe uma barra de progresso para acompanhar a
	 * evolução do cálculo dos indicadores. A barra de progresso mostra visualmente
	 * o andamento do processo de cálculo, permitindo que o usuário tenha uma ideia
	 * do tempo estimado para a conclusão. Essa interface é utilizada em conjunto
	 * com o processo de cálculo dos indicadores para fornecer feedback ao usuário
	 * sobre o progresso da tarefa.
	 * 
	 * @param nomeMunicipio
	 */
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
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addComponent(lblIndicadores, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
						.addComponent(lblMunicipio, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblProgresso, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup().addContainerGap()
						.addComponent(lblIndicadores, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblMunicipio, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblProgresso, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		contentPane.setLayout(gl_contentPane);

		setLocation(0, 0);
		setVisible(true);
		setMaximizable(false);
		setResizable(false);
		setIconifiable(false);
	}

	/**
	 * Atualiza o valor atual da barra de progresso. Utilizado para a busca das
	 * variáveis.
	 */
	public static void atualizarValorAtual() {
		int novoValor = valorAtual.incrementAndGet();
		progressBar.setValue(novoValor);
		lblProgresso.setText("Progresso:  " + valorAtual.get() + " de " + valorTotal.get());
	}

	/**
	 * Atualiza o valor atual da barra de progresso. Utilizado para o cálculo dos
	 * indicadores. O texto atualizado é o nome do município que vai ter os
	 * indicadores calculados.
	 * 
	 * @param nome Nome do município que vai ter os indicadores calculados
	 */
	public static void atualizarValorAtual(String nome) {
		int novoValor = valorAtual.incrementAndGet();
		lblMunicipio.setText(nome);
		progressBar.setValue(novoValor);
		lblProgresso.setText("Progresso:  " + valorAtual.get() + " de " + valorTotal.get());
	}

	/**
	 * Define o valor total da barra de progresso com base na quantidade de
	 * variáveis do tipo "IPEA" que serão buscadas. Caso não haja variáveis desse
	 * tipo na lista fornecida, a barra de progresso será configurada com um valor
	 * total de 50% (1/2), indicando que metade do processo já foi concluído.
	 * 
	 * @param listaVariaveisNaoValoradas representa a lista das variáveis sem
	 *                                   valores que serão buscadas nas bases de
	 *                                   dados públicas.
	 */
	public static void setValorTotal(List<Variavel> listaVariaveisNaoValoradas) {
		int instancias = 0;
		for (Variavel variavel : listaVariaveisNaoValoradas) {
			if (variavel.getTipoBanco().equals("IPEA")) {
				instancias++;
			}
		}
		valorAtual.set(0);
		if (instancias == 0) {
			progressBar.setMinimum(0);
			progressBar.setMaximum(2);
			progressBar.setValue(1);
			lblProgresso.setText("Progresso:  1 de 2");
			return;
		}
		valorTotal.set(instancias);
		progressBar.setMinimum(0);
		progressBar.setValue(0);
		progressBar.setMaximum(valorTotal.get());
		lblProgresso.setText("Progresso:  0 de " + valorTotal.get());
	}

	/**
	 * Define o valor total da barra de progresso como o número total de municípios
	 * que serão calculados para os indicadores selecionados.
	 * 
	 * @param quantidadeMunicipios Quantidade total de municípios.
	 */
	public static void setValorTotal(int quantidadeMunicipios) {
		valorTotal.set(quantidadeMunicipios);
		valorAtual.set(0);
		progressBar.setMinimum(0);
		progressBar.setValue(valorAtual.get());
		progressBar.setMaximum(valorTotal.get() + 1);
		lblProgresso.setText("Progresso:  " + valorAtual.get() + " de " + valorTotal.get());
	}

	/**
	 * Atualiza a barra de progresso para a busca das variáveis para todos os
	 * municípios, definindo o valor máximo da barra como a quantidade de variáveis
	 * que serão buscadas.
	 * 
	 * @param listaVariaveisRelacionadas Lista das variáveis que serão buscadas.
	 */
	public static void variaveisTodosMunicipios(List<Variavel> listaVariaveisRelacionadas) {
		int variaveis = listaVariaveisRelacionadas.size();

		lblIndicadores.setText("Buscando as variáveis.");
		lblMunicipio.setText("");

		valorAtual.set(0);
		valorTotal.set(variaveis);
		progressBar.setMinimum(0);
		progressBar.setValue(0);
		progressBar.setMaximum(valorTotal.get());
		lblProgresso.setText("Progresso:  0 de " + valorTotal.get() + " variáveis.");
	}

	/**
	 * Atualiza o valor atual da barra de progresso. Utilizado para a busca das
	 * variáveis em todos os municípios.
	 */
	public static void atualizarVariaveisTodosMunicipios() {
		int novoValor = valorAtual.incrementAndGet();
		progressBar.setValue(novoValor);
		lblProgresso.setText("Progresso:  " + valorAtual.get() + " de " + valorTotal.get() + " variáveis.");
	}
}
