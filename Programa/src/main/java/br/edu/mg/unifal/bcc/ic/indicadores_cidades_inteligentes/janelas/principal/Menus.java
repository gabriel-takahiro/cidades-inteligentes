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

package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal;


import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * Classe responsável com a criação dos menus na interface principal.
 * @author Gabriel Takahiro
 * @version 0.3	
 */
public class Menus {

	private static List<JMenu> listaMenus = new ArrayList<JMenu>();

	/**
	 * Cria todos os menus com os submenus na interface principal
	 * @param barraMenu barra de menu da interface principal
	 */
	public static void criarMenus(JMenuBar barraMenu) {
		menuIndicadores(barraMenu);
		menuVariaveis(barraMenu);
		menuMetas(barraMenu);
		menuODS(barraMenu);
		menuConsultas(barraMenu);
		menuCalculos(barraMenu);
		menuBancoDados(barraMenu);
		menuAjuda(barraMenu);
	}

	/**
	 * Cria todos os menus e submenus dos indicadores
	 * @param barraMenu barra de menu da interface principal
	 */
	public static void menuIndicadores(JMenuBar barraMenu) {
		JMenu menuIndicadores = new JMenu("Indicadores");
		barraMenu.add(menuIndicadores);
		listaMenus.add(menuIndicadores);
		
		Submenus.mostrarIndicadores(menuIndicadores);
		Submenus.editarIndicadores(menuIndicadores);
		Submenus.cadastrarIndicadores(menuIndicadores);
	}
	
	/**
	 * Cria todos os menus e submenus das variáveis
	 * @param barraMenu barra de menu da interface principal
	 */
	public static void menuVariaveis(JMenuBar barraMenu) {
		JMenu menuVariaveis = new JMenu("Variaveis");
		barraMenu.add(menuVariaveis);
		listaMenus.add(menuVariaveis);
		
		Submenus.mostrarVariaveis(menuVariaveis);
		Submenus.editarVariaveis(menuVariaveis);
		Submenus.cadastrarVariaveis(menuVariaveis);
	}
	
	/**
	 * Cria todos os menus e submenus das metas
	 * @param barraMenu barra de menu da interface principal
	 */
	public static void menuMetas(JMenuBar barraMenu) {
		JMenu menuMetas = new JMenu("Metas");
		barraMenu.add(menuMetas);
		listaMenus.add(menuMetas);
		
		Submenus.mostrarMetas(menuMetas);
		Submenus.editarMetas(menuMetas);
		Submenus.cadastrarMetas(menuMetas);
	}
	
	/**
	 * Cria todos os menus e submenus das ODS
	 * @param barraMenu barra de menu da interface principal
	 */
	public static void menuODS(JMenuBar barraMenu) {
		JMenu menuODS = new JMenu("ODS");
		barraMenu.add(menuODS);
		listaMenus.add(menuODS);
		
		Submenus.mostrarODS(menuODS);
		Submenus.editarODS(menuODS);
		Submenus.cadastrarODS(menuODS);
	}
	
	/**
	 * Cria todos os menus e submenus das consultas
	 * @param barraMenu barra de menu da interface principal
	 */
	public static void menuConsultas(JMenuBar barraMenu) {
		JMenu menuConsultas = new JMenu("Consultas");
		barraMenu.add(menuConsultas);
		listaMenus.add(menuConsultas);
		
		Submenus.consultarIndicadoresCalculados(menuConsultas);
	}
	
	/**
	 * Cria todos os menus e submenus dos calculos
	 * @param barraMenu barra de menu da interface principal
	 */
	public static void menuCalculos(JMenuBar barraMenu) {
		JMenu menuCalculos = new JMenu("Cálculos");
		barraMenu.add(menuCalculos);
		listaMenus.add(menuCalculos);
		
		Submenus.calcularIndicadores(menuCalculos);
		Submenus.recalcularIndicadores(menuCalculos);
	}
	
	/**
	 * Cria todos os menus e submenus do banco de dados
	 * @param barraMenu barra de menu da interface principal
	 */
	public static void menuBancoDados(JMenuBar barraMenu) {
		JMenu menuBancoDados = new JMenu("Banco de dados");
		barraMenu.add(menuBancoDados);
		listaMenus.add(menuBancoDados);
		
		Submenus.criarTabelas(menuBancoDados);
		Submenus.excluirTabelas(menuBancoDados);
		Submenus.importarTabelas(menuBancoDados);
	}
	
	/**
	 * Cria todos os menus e submenus de ajuda
	 * @param barraMenu barra de menu da interface principal
	 */
	public static void menuAjuda(JMenuBar barraMenu) {
		JMenu menuAjuda = new JMenu("Ajuda");
		barraMenu.add(menuAjuda);
		listaMenus.add(menuAjuda);
		
		Submenus.tutorial(menuAjuda);
	}
	
	/**
	 *
	 * @return uma lista contendo todos os menus
	 */
	public static List<JMenu> getListaMenus() {
		return listaMenus;
	}
}
