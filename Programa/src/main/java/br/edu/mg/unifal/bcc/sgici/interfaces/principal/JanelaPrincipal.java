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

package br.edu.mg.unifal.bcc.sgici.interfaces.principal;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Frame;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.mg.unifal.bcc.sgici.interfaces.internas.Tabelas;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.bd.CriarTabelas;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.bd.ExcluirTabelas;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.bd.ImportarTabelas;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.bd.ImportarValorVariavel;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.bd.RestaurarPadrao;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.CalcularIndicadores;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.ConsultarIndicadoresCalculados;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.RecalcularIndicadores;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.resultado.MostrarCalculos;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.calculo.resultado.TabelaIndicadoresCalculados;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.ods.CadastrarODS;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.ods.EditarODS;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.ods.MostrarODS;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.variaveis.CadastrarVariaveis;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.variaveis.EditarVariaveis;
import br.edu.mg.unifal.bcc.sgici.interfaces.internas.variaveis.MostrarVariaveis;
import br.edu.mg.unifal.bcc.sgici.internas.indicadores.CadastrarIndicadores;
import br.edu.mg.unifal.bcc.sgici.internas.indicadores.EditarIndicadores;
import br.edu.mg.unifal.bcc.sgici.internas.indicadores.MostrarIndicadores;
import br.edu.mg.unifal.bcc.sgici.internas.metas.CadastrarMetas;
import br.edu.mg.unifal.bcc.sgici.internas.metas.EditarMetas;
import br.edu.mg.unifal.bcc.sgici.internas.metas.MostrarMetas;

/**
 * Classe responsável pela interface principal do programa
 * 
 * @author Gabriel Takahiro
 * @version 0.3
 */
public class JanelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private static JDesktopPane desktopPane;

	private static MostrarIndicadores janelaMostrarIndicadores;
	private static EditarIndicadores janelaEditarIndicadores;
	private static CadastrarIndicadores janelaCadastrarIndicadores;

	private static MostrarVariaveis janelaMostrarVariaveis;
	private static EditarVariaveis janelaEditarVariaveis;
	private static CadastrarVariaveis janelaCadastrarVariaveis;

	private static MostrarMetas janelaMostrarMetas;
	private static EditarMetas janelaEditarMetas;
	private static CadastrarMetas janelaCadastrarMetas;

	private static MostrarODS janelaMostrarODS;
	private static EditarODS janelaEditarODS;
	private static CadastrarODS janelaCadastrarODS;

	private static ConsultarIndicadoresCalculados janelaConsultarIndicadoresCalculados;

	private static CalcularIndicadores janelaCalcularIndicadores;
	private static RecalcularIndicadores janelaRecalcularIndicadores;

	private static CriarTabelas janelaCriarTabelas;
	private static ExcluirTabelas janelaExcluirTabelas;
	private static ImportarTabelas janelaImportarTabelas;
	private static ImportarValorVariavel janelaImportarValorVariavel;
	private static RestaurarPadrao janelaRestaurarPadrao;

	public static ExecutorService executor = Executors.newFixedThreadPool(2);
	private static boolean calculando = false;
	private static Configuracao configuracao;

	/**
	 * Executa a interface principal do programa
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaPrincipal frame = new JanelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		});
	}

	/**
	 * Executa a interface principal do programa. Responsável por executar a barra
	 * de menu e todos os submenus
	 */
	public JanelaPrincipal() {
		setTitle("Sistema de gestão de indicadores para cidades inteligentes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 450);
		setExtendedState(Frame.MAXIMIZED_BOTH);

		JMenuBar barraMenu = new JMenuBar();
		setJMenuBar(barraMenu);

		Menus.criarMenus(barraMenu);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		desktopPane = new JDesktopPane();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(desktopPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(desktopPane,
				GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE));
		GroupLayout gl_desktopPane = new GroupLayout(desktopPane);
		gl_desktopPane.setHorizontalGroup(
				gl_desktopPane.createParallelGroup(Alignment.LEADING).addGap(0, 674, Short.MAX_VALUE));
		gl_desktopPane.setVerticalGroup(
				gl_desktopPane.createParallelGroup(Alignment.LEADING).addGap(0, 379, Short.MAX_VALUE));
		desktopPane.setLayout(gl_desktopPane);
		contentPane.setLayout(gl_contentPane);

		List<JMenu> listaMenu = Menus.getListaMenus();
		List<JMenuItem> listaSubmenus = Submenus.getListaSubmenus();

		listaMenu.forEach(menu -> menu.setEnabled(false));
		listaSubmenus.forEach(subMenu -> subMenu.setBackground(new Color(90, 220, 90)));

		barraMenu.setBackground(new Color(40, 150, 40));
		desktopPane.setBackground(new Color(90, 180, 90));
		contentPane.setBackground(new Color(50, 200, 50));
		setBackground(new Color(60, 210, 70));

		desktopPane.add(new ConexaoBD(listaMenu, this, desktopPane));
	}

	/**
	 * Método responsável por abrir novas interfaces na interface principal
	 * 
	 * @param janela interface que será aberta
	 */
	public static void abrirJanelas(JInternalFrame janela) {
		desktopPane.add(janela);
		janela.setVisible(true);
		janela.moveToFront();
	}

	/**
	 * 
	 * @param janelaMostrarIndicadores interface que mostra os indicadores
	 */
	public static void setJanelaMostrarIndicadores(MostrarIndicadores janelaMostrarIndicadores) {
		JanelaPrincipal.janelaMostrarIndicadores = janelaMostrarIndicadores;
		JanelaPrincipal.janelaMostrarIndicadores.moveToFront();
	}

	/**
	 *
	 * @param janelaEditarIndicadores interface que edita os indicadores
	 */
	public static void setJanelaEditarIndicadores(EditarIndicadores janelaEditarIndicadores) {
		JanelaPrincipal.janelaEditarIndicadores = janelaEditarIndicadores;
		JanelaPrincipal.janelaEditarIndicadores.moveToFront();
	}

	/**
	 * 
	 * @param janelaCadastrarIndicadores interface que cadastra os indicadores
	 */
	public static void setJanelaCadastrarIndicadores(CadastrarIndicadores janelaCadastrarIndicadores) {
		JanelaPrincipal.janelaCadastrarIndicadores = janelaCadastrarIndicadores;
		JanelaPrincipal.janelaCadastrarIndicadores.moveToFront();
	}

	/**
	 * 
	 * @param janelaMostrarVariaveis interface que mostra as variáveis
	 */
	public static void setJanelaMostrarVariaveis(MostrarVariaveis janelaMostrarVariaveis) {
		JanelaPrincipal.janelaMostrarVariaveis = janelaMostrarVariaveis;
		JanelaPrincipal.janelaMostrarVariaveis.moveToFront();
	}

	/**
	 * 
	 * @param janelaEditarVariaveis interface que edita as varáveis
	 */
	public static void setJanelaEditarVariaveis(EditarVariaveis janelaEditarVariaveis) {
		JanelaPrincipal.janelaEditarVariaveis = janelaEditarVariaveis;
		JanelaPrincipal.janelaEditarVariaveis.moveToFront();
	}

	/**
	 * 
	 * @param janelaCadastrarVariaveis interface que cadastra as variáveis
	 */
	public static void setJanelaCadastrarVariaveis(CadastrarVariaveis janelaCadastrarVariaveis) {
		JanelaPrincipal.janelaCadastrarVariaveis = janelaCadastrarVariaveis;
		JanelaPrincipal.janelaCadastrarVariaveis.moveToFront();
	}

	/**
	 * 
	 * @param janelaMostrarMetas interface que mostra as metas
	 */
	public static void setJanelaMostrarMetas(MostrarMetas janelaMostrarMetas) {
		JanelaPrincipal.janelaMostrarMetas = janelaMostrarMetas;
		JanelaPrincipal.janelaMostrarMetas.moveToFront();
	}

	/**
	 * 
	 * @param janelaEditarMetas interface que edita as metas
	 */
	public static void setJanelaEditarMetas(EditarMetas janelaEditarMetas) {
		JanelaPrincipal.janelaEditarMetas = janelaEditarMetas;
		JanelaPrincipal.janelaEditarMetas.moveToFront();
	}

	/**
	 * 
	 * @param janelaCadastrarMetas interface que cadastra as metas
	 */
	public static void setJanelaCadastrarMetas(CadastrarMetas janelaCadastrarMetas) {
		JanelaPrincipal.janelaCadastrarMetas = janelaCadastrarMetas;
		JanelaPrincipal.janelaCadastrarMetas.moveToFront();
	}

	/**
	 * 
	 * @param janelaMostrarODS interface que mostra as ODS
	 */
	public static void setJanelaMostrarODS(MostrarODS janelaMostrarODS) {
		JanelaPrincipal.janelaMostrarODS = janelaMostrarODS;
		JanelaPrincipal.janelaMostrarODS.moveToFront();
	}

	/**
	 * 
	 * @param janelaEditarODS interface que edita as ODS
	 */
	public static void setJanelaEditarODS(EditarODS janelaEditarODS) {
		JanelaPrincipal.janelaEditarODS = janelaEditarODS;
		JanelaPrincipal.janelaEditarODS.moveToFront();
	}

	/**
	 * 
	 * @param janelaCadastrarODS interface que cadastra as ODS
	 */
	public static void setJanelaCadastrarODS(CadastrarODS janelaCadastrarODS) {
		JanelaPrincipal.janelaCadastrarODS = janelaCadastrarODS;
		JanelaPrincipal.janelaCadastrarODS.moveToFront();
	}

	/**
	 * 
	 * @param janelaConsultarIndicadoresCalculados interface que consulta os
	 *                                             indicadores calculados
	 */
	public static void setJanelaConsultarIndicadoresCalculados(
			ConsultarIndicadoresCalculados janelaConsultarIndicadoresCalculados) {
		JanelaPrincipal.janelaConsultarIndicadoresCalculados = janelaConsultarIndicadoresCalculados;
	}

	/**
	 * 
	 * @param janelaCalcularIndicadores interface que calcula os indicadores
	 */
	public static void setJanelaCalcularIndicadores(CalcularIndicadores janelaCalcularIndicadores) {
		JanelaPrincipal.janelaCalcularIndicadores = janelaCalcularIndicadores;
		JanelaPrincipal.janelaCalcularIndicadores.moveToFront();
	}

	/**
	 * 
	 * @param janelaRecalcularIndicadores interface que recalcula os indicadores
	 */
	public static void setJanelaRecalcularIndicadores(RecalcularIndicadores janelaRecalcularIndicadores) {
		JanelaPrincipal.janelaRecalcularIndicadores = janelaRecalcularIndicadores;
		JanelaPrincipal.janelaRecalcularIndicadores.moveToFront();
	}

	/**
	 * 
	 * @param janelaCriarTabelas interface que cria as tabelas no banco de dados
	 */
	public static void setJanelaCriarTabelas(CriarTabelas janelaCriarTabelas) {
		JanelaPrincipal.janelaCriarTabelas = janelaCriarTabelas;
		JanelaPrincipal.janelaCriarTabelas.moveToFront();
	}

	/**
	 * 
	 * @param janelaExcluirTabelas interface que exclui as tabelas do banco de dados
	 */
	public static void setJanelaExcluirTabelas(ExcluirTabelas janelaExcluirTabelas) {
		JanelaPrincipal.janelaExcluirTabelas = janelaExcluirTabelas;
		JanelaPrincipal.janelaExcluirTabelas.moveToFront();
	}

	/**
	 * 
	 * @param janelaImportarTabelas interface que importa as tabelas no banco de
	 *                              dados
	 */
	public static void setJanelaImportarTabelas(ImportarTabelas janelaImportarTabelas) {
		JanelaPrincipal.janelaImportarTabelas = janelaImportarTabelas;
		JanelaPrincipal.janelaImportarTabelas.moveToFront();
	}
	
	/**
	 * 
	 * @param janelaImportarValorVariavel interface que importa o valor das variáveis no banco de
	 *                              dados
	 */
	public static void setJanelaImportarValorVariavel(ImportarValorVariavel janelaImportarValorVariavel) {
		JanelaPrincipal.janelaImportarValorVariavel = janelaImportarValorVariavel;
		JanelaPrincipal.janelaImportarValorVariavel.moveToFront();
	}

	/**
	 * 
	 * @param janelaRestaurarPadrao interface que restaura os dados das tabelas para o modelo padrão
	 */
	public static void setJanelaRestaurarPadrao(RestaurarPadrao janelaRestaurarPadrao) {
		JanelaPrincipal.janelaRestaurarPadrao = janelaRestaurarPadrao;
		JanelaPrincipal.janelaRestaurarPadrao.moveToFront();
	}

	/**
	 * Exibe a interface que mostra os indicadores
	 */
	public static void mostrarIndicadores() {
		janelaMostrarIndicadores.abrirJanela(janelaMostrarIndicadores, desktopPane);
	}

	/**
	 * Exibe a interface que edita os indicadores
	 */
	public static void editarIndicadores() {
		janelaEditarIndicadores.abrirJanela(janelaEditarIndicadores, desktopPane);
	}

	/**
	 * Exibe a interface que cadastra os indicadores
	 */
	public static void cadastrarIndicadores() {
		janelaCadastrarIndicadores.abrirJanela(janelaCadastrarIndicadores, desktopPane);
	}

	/**
	 * Exibe a interface que mostra as variáveis
	 */
	public static void mostrarVariaveis() {
		janelaMostrarVariaveis.abrirJanela(janelaMostrarVariaveis, desktopPane);
	}

	/**
	 * Exibe a interface que edita as variáveis
	 */
	public static void editarVariaveis() {
		janelaEditarVariaveis.abrirJanela(janelaEditarVariaveis, desktopPane);
	}

	/**
	 * Exibe a interface que cadastra as variáveis
	 */
	public static void cadastrarVariaveis() {
		janelaCadastrarVariaveis.abrirJanela(janelaCadastrarVariaveis, desktopPane);
	}

	/**
	 * Exibe a interface que mostra as metas
	 */
	public static void mostrarMetas() {
		janelaMostrarMetas.abrirJanela(janelaMostrarMetas, desktopPane);
	}

	/**
	 * Exibe a interface que edita as metas
	 */
	public static void editarMetas() {
		janelaEditarMetas.abrirJanela(janelaEditarMetas, desktopPane);
	}

	/**
	 * Exibe a interface que cadastra as metas
	 */
	public static void cadastrarMetas() {
		janelaCadastrarMetas.abrirJanela(janelaCadastrarMetas, desktopPane);
	}

	/**
	 * Exibe a interface que mostra as ODS
	 */
	public static void mostrarODS() {
		janelaMostrarODS.abrirJanela(janelaMostrarODS, desktopPane);
	}

	/**
	 * Exibe a interface que edita as ODS
	 */
	public static void editarODS() {
		janelaEditarODS.abrirJanela(janelaEditarODS, desktopPane);
	}

	/**
	 * Exibe a interface que cadastra as ODS
	 */
	public static void cadastrarODS() {
		janelaCadastrarODS.abrirJanela(janelaCadastrarODS, desktopPane);
	}

	/**
	 * Exibe a interface que consulta os indicadores calculados
	 */
	public static void consultarIndicadoresCalculados() {
		janelaConsultarIndicadoresCalculados.abrirJanela(janelaConsultarIndicadoresCalculados, desktopPane);
	}

	/**
	 * Exibe a interface que calcula os indicadores
	 */
	public static void calcularIndicadores() {
		janelaCalcularIndicadores.abrirJanela(janelaCalcularIndicadores, desktopPane);
	}

	/**
	 * Exibe a interface que recalcula os indicadores
	 */
	public static void recalcularIndicadores() {
		janelaRecalcularIndicadores.abrirJanela(janelaRecalcularIndicadores, desktopPane);
	}

	/**
	 * Exibe a interface que cria as tabelas no banco de dados
	 */
	public static void criarTabelas() {
		janelaCriarTabelas.abrirJanela(janelaCriarTabelas, desktopPane);
	}

	/**
	 * Exibe a interface que exclui as tabelas no banco de dados
	 */
	public static void excluirTabelas() {
		janelaExcluirTabelas.abrirJanela(janelaExcluirTabelas, desktopPane);
	}

	/**
	 * Exibe a interface que importa as tabelas para o banco de dados
	 */
	public static void importarTabelas() {
		janelaImportarTabelas.abrirJanela(janelaImportarTabelas, desktopPane);
	}

	/**
	 * Exibe a interface que importa os valores das variáveis para o banco de dados
	 */
	public static void importarValorVariavel() {
		janelaImportarValorVariavel.abrirJanela(janelaImportarValorVariavel, desktopPane);
	}
	
	/**
	 * Exibe a interface que restaura as tabelas para o formato padrão no banco de
	 * dados
	 */
	public static void restaurarPadrao() {
		janelaRestaurarPadrao.abrirJanela(janelaRestaurarPadrao, desktopPane);
	}

	/**
	 * Retorna o valor booleano que indica se está sendo realizado um cálculo de
	 * indicador ou não na aplicação
	 * 
	 * @return Boolean calculando que indica se está sendo realizado um cálculo de
	 *         indicador ou não na aplicação
	 */
	public static boolean isCalculando() {
		return calculando;
	}

	/**
	 * Insere o valor na variável calculando, que indica se está sendo realizado um
	 * cálculo de indicador na aplicação
	 * 
	 * @param calculando Booleano que indica se está sendo realizado ou não um
	 *                   cálculo de indicador na aplicação
	 */
	public static void setCalculando(boolean calculando) {
		JanelaPrincipal.calculando = calculando;
	}

	/**
	 * Retorna o a configuração de desempenho
	 * 
	 * @return Enum que representa a configuração de desemepenho
	 */
	public static Configuracao getConfiguracao() {
		return configuracao;
	}

	/**
	 * Insere a configuração de desempenho
	 * 
	 * @param configuracao configuração de desempenho
	 */
	public static void setConfiguracao(Configuracao configuracao) {
		JanelaPrincipal.configuracao = configuracao;
	}

	/**
	 * Instancia todas as interfaces do programa
	 * 
	 * @param abrirTutorial verifica se é necessário abrir o tutorial após fazer o
	 *                      login
	 * @param configuracao  configuração de desempenho
	 */
	public void instanciarJanelas(boolean abrirTutorial, Configuracao configuracao, String tabela) {
		setTitle("Sistema de gestao de indicadores para cidades inteligentes (Tabela: " + tabela + ")");
		setConfiguracao(configuracao);
		instanciarBancoDados();
		instanciarTutorial(abrirTutorial);
		instanciarVariaveis();
		instanciarMetas();
		instanciarODS();
		instanciarConsultas();
		instanciarCalculos();
		instanciarIndicadores();
	}

	/**
	 * Instancia todas as interfaces dos indicadores
	 */
	public static void instanciarIndicadores() {
		janelaMostrarIndicadores = new MostrarIndicadores();
		desktopPane.add(janelaMostrarIndicadores);
		janelaMostrarIndicadores.setVisible(false);

		janelaEditarIndicadores = new EditarIndicadores();
		desktopPane.add(janelaEditarIndicadores);
		janelaEditarIndicadores.setVisible(false);

		janelaCadastrarIndicadores = new CadastrarIndicadores();
		desktopPane.add(janelaCadastrarIndicadores);
		janelaCadastrarIndicadores.setVisible(false);
	}

	/**
	 * Instancia todas as interfaces das variáveis
	 */
	public static void instanciarVariaveis() {
		janelaMostrarVariaveis = new MostrarVariaveis();
		desktopPane.add(janelaMostrarVariaveis);
		janelaMostrarVariaveis.setVisible(false);

		janelaEditarVariaveis = new EditarVariaveis();
		desktopPane.add(janelaEditarVariaveis);
		janelaEditarVariaveis.setVisible(false);

		janelaCadastrarVariaveis = new CadastrarVariaveis();
		desktopPane.add(janelaCadastrarVariaveis);
		janelaCadastrarVariaveis.setVisible(false);
	}

	/**
	 * Instancia todas as interfaces das metas
	 */
	public static void instanciarMetas() {
		janelaMostrarMetas = new MostrarMetas();
		desktopPane.add(janelaMostrarMetas);
		janelaMostrarMetas.setVisible(false);

		janelaEditarMetas = new EditarMetas();
		desktopPane.add(janelaEditarMetas);
		janelaEditarMetas.setVisible(false);

		janelaCadastrarMetas = new CadastrarMetas();
		desktopPane.add(janelaCadastrarMetas);
		janelaCadastrarMetas.setVisible(false);
	}

	/**
	 * Instancia todas as interfaces das ODS
	 */
	public static void instanciarODS() {
		janelaMostrarODS = new MostrarODS();
		desktopPane.add(janelaMostrarODS);
		janelaMostrarODS.setVisible(false);

		janelaEditarODS = new EditarODS();
		desktopPane.add(janelaEditarODS);
		janelaEditarODS.setVisible(false);

		janelaCadastrarODS = new CadastrarODS();
		desktopPane.add(janelaCadastrarODS);
		janelaCadastrarODS.setVisible(false);
	}

	/**
	 * Instancia todas as interfaces das consultas
	 */
	public static void instanciarConsultas() {
		janelaConsultarIndicadoresCalculados = new ConsultarIndicadoresCalculados();
		desktopPane.add(janelaConsultarIndicadoresCalculados);
		janelaConsultarIndicadoresCalculados.setVisible(false);
	}

	/**
	 * Instancia todas as interfaces dos cálculos
	 */
	public static void instanciarCalculos() {
		janelaCalcularIndicadores = new CalcularIndicadores();
		desktopPane.add(janelaCalcularIndicadores);
		janelaCalcularIndicadores.setVisible(false);

		janelaRecalcularIndicadores = new RecalcularIndicadores();
		desktopPane.add(janelaRecalcularIndicadores);
		janelaRecalcularIndicadores.setVisible(false);
	}

	/**
	 * Instancia todas as interfaces do banco de dados
	 */
	public static void instanciarBancoDados() {
		janelaCriarTabelas = new CriarTabelas();
		desktopPane.add(janelaCriarTabelas);
		janelaCriarTabelas.setVisible(false);

		janelaExcluirTabelas = new ExcluirTabelas();
		desktopPane.add(janelaExcluirTabelas);
		janelaExcluirTabelas.setVisible(false);

		janelaImportarTabelas = new ImportarTabelas();
		desktopPane.add(janelaImportarTabelas);
		janelaImportarTabelas.setVisible(false);
		
		janelaImportarValorVariavel = new ImportarValorVariavel();
		desktopPane.add(janelaImportarValorVariavel);
		janelaImportarValorVariavel.setVisible(false);

		janelaRestaurarPadrao = new RestaurarPadrao();
		desktopPane.add(janelaRestaurarPadrao);
		janelaRestaurarPadrao.setVisible(false);
	}

	/**
	 * Instancia a interface tutorial
	 * 
	 * @param abrirTutorial verifica se é necessário mostrar o tutorial após
	 *                      realizar o login
	 */
	public static void instanciarTutorial(boolean abrirTutorial) {

		if (abrirTutorial) {
			try {
				Desktop desktop = Desktop.getDesktop();
				URI uri = new URI(
						"https://gabriel-takahiro.github.io/cidades-inteligentes/como%20utilizar/html/index.html");
				desktop.browse(uri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Atualiza todas as interfaces (indicadores, variáveis, metas e ODS)
	 */
	public static void atualizarTudo() {
		atualizarIndicadores();
		atualizarVariaveis();
		atualizarMetas();
		atualizarODS();
	}

	/**
	 * Atualiza todas as interfaces que possui alguma relação com os indicadores
	 */
	public static void atualizarIndicadores() {
		try {
			if (!(janelaEditarIndicadores.isClosed())) {
				Tabelas.mostrarIndicadores(6, EditarIndicadores.getTable());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			if (!(janelaMostrarIndicadores.isClosed())) {
				Tabelas.mostrarIndicadores(MostrarIndicadores.getTable());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			if (!(janelaCalcularIndicadores.isClosed())) {
				Tabelas.mostrarIndicadores(6, CalcularIndicadores.getTable());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			if (!(janelaRecalcularIndicadores.isClosed())) {
				Tabelas.mostrarIndicadores(6, RecalcularIndicadores.getTable());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			if (!(janelaConsultarIndicadoresCalculados.isClosed())) {
				Tabelas.mostrarIndicadores(6, ConsultarIndicadoresCalculados.getTable());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			JInternalFrame[] lista = desktopPane.getAllFrames();
			for (JInternalFrame jInternalFrame : lista) {
				if (jInternalFrame.getClass().getSimpleName().equals("TabelaIndicadoresCalculados")) {
					TabelaIndicadoresCalculados tabelaIndicadoresCalculados = (TabelaIndicadoresCalculados) jInternalFrame;
					tabelaIndicadoresCalculados.atualizar();
				}
				if (jInternalFrame.getClass().getSimpleName().equals("MostrarCalculos")) {
					MostrarCalculos mostrarCalculos = (MostrarCalculos) jInternalFrame;
					mostrarCalculos.atualizar();
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Atualiza todas as interfaces que possui alguma relação com as variáveis
	 */
	public static void atualizarVariaveis() {
		atualizarIndicadores();

		try {
			if (!(janelaCadastrarIndicadores.isClosed())) {
				Tabelas.mostrarVariaveis(CadastrarIndicadores.getTable());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			if (!(janelaMostrarVariaveis.isClosed())) {
				Tabelas.mostrarVariaveis(MostrarVariaveis.getTable());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			if (!(janelaEditarVariaveis.isClosed())) {
				Tabelas.mostrarVariaveis(6, EditarVariaveis.getTable());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Atualiza todas as interfaces que possui alguma relação com as metas
	 */
	public static void atualizarMetas() {
		atualizarIndicadores();

		try {
			if (!(janelaCadastrarIndicadores.isClosed())) {
				CadastrarIndicadores.mostrarMetas();
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			if (!(janelaMostrarMetas.isClosed())) {
				Tabelas.mostrarMetas(MostrarMetas.getTable());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			if (!(janelaEditarMetas.isClosed())) {
				Tabelas.mostrarMetas(3, EditarMetas.getTable());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Atualiza todas as interfaces que possui alguma relação com as ODS
	 */
	public static void atualizarODS() {
		try {
			if (!(janelaCadastrarMetas.isClosed())) {
				CadastrarMetas.mostrarODS();
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			if (!(janelaMostrarODS.isClosed())) {
				Tabelas.mostrarODS(MostrarODS.getTable());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			if (!(janelaEditarODS.isClosed())) {
				Tabelas.mostrarODS(2, EditarODS.getTable());
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			JInternalFrame[] lista = desktopPane.getAllFrames();
			for (JInternalFrame jInternalFrame : lista) {
				if (jInternalFrame.getClass().getSimpleName().equals("TabelaIndicadoresCalculados")) {
					TabelaIndicadoresCalculados tabelaIndicadoresCalculados = (TabelaIndicadoresCalculados) jInternalFrame;
					tabelaIndicadoresCalculados.atualizar();
				}
				if (jInternalFrame.getClass().getSimpleName().equals("MostrarCalculos")) {
					MostrarCalculos mostrarCalculos = (MostrarCalculos) jInternalFrame;
					mostrarCalculos.atualizar();
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Fecha a janela que cadastra indicadores
	 */
	public static void fechaCadastrarIndicadores() {
		janelaCadastrarIndicadores.dispose();
	}

	/**
	 * Fecha a janela que cadastra variáveis
	 */
	public static void fechaCadastrarVariaveis() {
		janelaCadastrarVariaveis.dispose();
	}

	/**
	 * Fecha a janela que cadastra metas
	 */
	public static void fechaCadastrarMetas() {
		janelaCadastrarMetas.dispose();
	}

	/**
	 * Fecha a janela que cadastra ODS
	 */
	public static void fechaCadastrarODS() {
		janelaCadastrarODS.dispose();
	}
}
