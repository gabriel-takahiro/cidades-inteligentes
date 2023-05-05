package br.edu.mg.unifal.bcc.sgici.interfaces.internas;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 * Classe abstrata que implementa a interface Janelas e define um conjunto de
 * métodos para gerenciamento de janelas internas em uma interface desktop. Esta
 * classe deve ser estendida por outras classes que representam janelas internas
 * específicas na aplicação. As subclasses devem implementar os métodos
 * abstratos abrirJanelaJDesktopPane() e instanciaClasseAberta().
 * 
 * @see Janelas
 * @author Gabriel Takahiro
 * @version 0.4
 */
public abstract class JanelasInternas extends JInternalFrame implements Janelas {

	private static final long serialVersionUID = 1L;

	/**
	 * Implementação do método definido na interface Janelas, responsável por abrir
	 * uma nova janela na interface desktop da aplicação. Este método verifica se a
	 * janela está fechada ou não. Se estiver fechada, instancia uma nova janela
	 * usando o método instanciaClasseAberta() e adiciona ao desktopPane usando o
	 * método add(). Em seguida, chama o método abrirJanelaJDesktopPane() passando a
	 * nova janela como parâmetro. Caso a janela já esteja aberta, apenas a traz
	 * para frente usando o método moveToFront(). As subclasses devem implementar o
	 * método abrirJanelaJDesktopPane(), que é responsável por configurar a nova
	 * janela e exibi-la na interface desktop. Também devem implementar o método
	 * instanciaClasseAberta(), que deve retornar uma nova instância da janela que
	 * será aberta.
	 * 
	 * @param janela      a interface que será aberta na aplicação.
	 * @param desktopPane a interface principal da aplicação, onde a nova janela
	 *                    será exibida.
	 */
	@Override
	public void abrirJanela(JInternalFrame janela, JDesktopPane desktopPane) {
		if (janela.isClosed()) {
			JInternalFrame novaJanela = instanciaClasseAberta();
			desktopPane.add(novaJanela);
			abrirJanelaJDesktopPane(novaJanela);
			return;
		}
		if (janela.isVisible()) {
			janela.moveToFront();
			return;
		}
		janela.setVisible(true);
		janela.moveToFront();
	}

	/**
	 * Método abstrato que deve ser implementado nas subclasses. Responsável por
	 * definir como a janela será aberta no JDesktopPane.
	 * 
	 * @param janela a nova janela que foi aberta.
	 */
	protected abstract void abrirJanelaJDesktopPane(JInternalFrame janela);

	/**
	 * Método abstrato que deve ser implementado nas subclasses. Responsável por
	 * instanciar a classe que representa a janela interna que será aberta na
	 * aplicação.
	 * 
	 * @return a nova instância da janela interna.
	 */
	protected abstract JInternalFrame instanciaClasseAberta();
}
