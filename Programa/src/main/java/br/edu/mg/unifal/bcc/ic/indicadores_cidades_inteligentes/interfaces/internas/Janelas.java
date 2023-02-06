package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

/**
 * Interface responsável pela abertura de interfaces gráficas
 * @author Gabriel Takahiro
 *
 */
public interface Janelas {

	/**
	 * Método responsável pela exibição de uma nova interface
	 * @param janela interface a ser aberta
	 * @param desktopPane interface principal
	 */
	public abstract void abrirJanela(JInternalFrame janela, JDesktopPane desktopPane);
}
