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

package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Classe responsável com a criação dos submenus na interface principal.
 * @author Gabriel Takahiro
 * @version 0.3	
 */
public class Submenus {
	
	private static List<JMenuItem> listaSubmenus = new ArrayList<JMenuItem>();

	/**
	 * Cria o submenu que mostra os indicadores
	 * @param menuIndicadores menu dos indicadores
	 */
	public static void mostrarIndicadores(JMenu menuIndicadores) {
		JMenuItem submenuMostrarIndicadores = new JMenuItem("Mostrar indicadores");
		submenuMostrarIndicadores.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que mostra os indicadores
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.mostrarIndicadores();
			}
		});
		menuIndicadores.add(submenuMostrarIndicadores);
		listaSubmenus.add(submenuMostrarIndicadores);
	}

	/**
	 * Cria o submenu que edita os indicadores
	 * @param menuIndicadores menus dos indicadores
	 */
	public static void editarIndicadores(JMenu menuIndicadores) {
		JMenuItem submenuEditarIndicadores = new JMenuItem("Editar indicadores");
		submenuEditarIndicadores.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que edita os indicadores
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.editarIndicadores();
			}
		});
		menuIndicadores.add(submenuEditarIndicadores);
		listaSubmenus.add(submenuEditarIndicadores);
	}

	/**
	 * Cria o submenu que cadastra os indicadores
	 * @param menuIndicadores menus dos indicadores
	 */
	public static void cadastrarIndicadores(JMenu menuIndicadores) {
		JMenuItem submenuCadastrarIndicadores = new JMenuItem("Cadastrar indicadores");
		submenuCadastrarIndicadores.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que cadastra os indicadores
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.cadastrarIndicadores();
			}
		});
		menuIndicadores.add(submenuCadastrarIndicadores);
		listaSubmenus.add(submenuCadastrarIndicadores);
	}

	/**
	 * Cria o submenu que mostra as variáveis
	 * @param menuVariaveis menus das variáveis
	 */
	public static void mostrarVariaveis(JMenu menuVariaveis) {
		JMenuItem submenuMostrarVariaveis = new JMenuItem("Mostrar variáveis");
		submenuMostrarVariaveis.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que mostra as variáveis
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.mostrarVariaveis();
			}
		});
		menuVariaveis.add(submenuMostrarVariaveis);
		listaSubmenus.add(submenuMostrarVariaveis);
	}

	/**
	 * Cria o submenu que edita as variáveis
	 * @param menuVariaveis menus das variáveis
	 */
	public static void editarVariaveis(JMenu menuVariaveis) {
		JMenuItem submenuEditarVariveis = new JMenuItem("Editar variáveis");
		submenuEditarVariveis.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que edita as variáveis
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.editarVariaveis();
			}
		});
		menuVariaveis.add(submenuEditarVariveis);
		listaSubmenus.add(submenuEditarVariveis);
	}

	/**
	 * Cria o submenu que cadastra as variáveis
	 * @param menuVariaveis menus das variáveis
	 */
	public static void cadastrarVariaveis(JMenu menuVariaveis) {
		JMenuItem submenuCadastrarVariaveis = new JMenuItem("Cadastrar variáveis");
		submenuCadastrarVariaveis.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que cadastra as variáveis
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.cadastrarVariaveis();
			}
		});
		menuVariaveis.add(submenuCadastrarVariaveis);
		listaSubmenus.add(submenuCadastrarVariaveis);
	}
	
	/**
	 * Cria o submenu que mostra as metas
	 * @param menuMetas menus das metas
	 */
	public static void mostrarMetas(JMenu menuMetas) {
		JMenuItem submenuMostrarMetas = new JMenuItem("Mostrar metas");
		submenuMostrarMetas.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que mostra as metas
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.mostrarMetas();
			}
		});
		menuMetas.add(submenuMostrarMetas);
		listaSubmenus.add(submenuMostrarMetas);
	}

	/**
	 * Cria o submenu que edita as metas
	 * @param menuMetas menus das metas
	 */
	public static void editarMetas(JMenu menuMetas) {
		JMenuItem submenuEditarMetas = new JMenuItem("Editar metas");
		submenuEditarMetas.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que edita as metas
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.editarMetas();
			}
		});
		menuMetas.add(submenuEditarMetas);
		listaSubmenus.add(submenuEditarMetas);
	}

	/**
	 * Cria o submenu que cadastra as metas
	 * @param menuMetas menus das metas
	 */
	public static void cadastrarMetas(JMenu menuMetas) {
		JMenuItem submenuCadastrarMetas = new JMenuItem("Cadastrar metas");
		submenuCadastrarMetas.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que cadastra as metas
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.cadastrarMetas();
			}
		});
		menuMetas.add(submenuCadastrarMetas);
		listaSubmenus.add(submenuCadastrarMetas);
	}

	/**
	 * Cria o submenu que mostra as ODS
	 * @param menuODS menus das ODS
	 */
	public static void mostrarODS(JMenu menuODS) {
		JMenuItem submenuMostrarODS = new JMenuItem("Mostrar ODS");
		submenuMostrarODS.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que mostra as ods
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.mostrarODS();
			}
		});
		menuODS.add(submenuMostrarODS);
		listaSubmenus.add(submenuMostrarODS);
	}

	/**
	 * Cria o submenu que edita as ODS
	 * @param menuODS menus das ODS
	 */
	public static void editarODS(JMenu menuODS) {
		JMenuItem submenuEditarODS = new JMenuItem("Editar ODS");
		submenuEditarODS.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que edita as ods
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.editarODS();
			}
		});
		menuODS.add(submenuEditarODS);
		listaSubmenus.add(submenuEditarODS);
	}

	/**
	 * Cria o submenu que cadastra as ODS
	 * @param menuODS menus das ODS
	 */
	public static void cadastrarODS(JMenu menuODS) {
		JMenuItem submenuCadastrarODS = new JMenuItem("Cadastrar ODS");
		submenuCadastrarODS.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que cadastra as ods
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.cadastrarODS();
			}
		});
		menuODS.add(submenuCadastrarODS);
		listaSubmenus.add(submenuCadastrarODS);
	}

	/**
	 * Cria o submenu que consulta os indicadores calculados
	 * @param menuConsultas menu das consultas
	 */
	public static void consultarIndicadoresCalculados(JMenu menuConsultas) {
		JMenuItem submenuConsultarIndicadoresCalculados = new JMenuItem("Consultar indicadores calculados");
		submenuConsultarIndicadoresCalculados.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que consulta os indicadores calculados
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.consultarIndicadoresCalculados();
			}
		});
		menuConsultas.add(submenuConsultarIndicadoresCalculados);
		listaSubmenus.add(submenuConsultarIndicadoresCalculados);
	}

	/**
	 * Cria o submenu que calcula os indicadores
	 * @param menuCalculos menu dos cálculos
	 */
	public static void calcularIndicadores(JMenu menuCalculos) {
		JMenuItem submenuCalcularIndicadores = new JMenuItem("Calcular indicadores");
		submenuCalcularIndicadores.addActionListener(new ActionListener() {
			/**
			 * Abre a interface responsável por calcular os indicadores
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.calcularIndicadores();
			}
		});
		menuCalculos.add(submenuCalcularIndicadores);
		listaSubmenus.add(submenuCalcularIndicadores);
	}

	/**
	 * Cria o submenu que recalcula os indicadores
	 * @param menuCalculos menu dos cálculos
	 */
	public static void recalcularIndicadores(JMenu menuCalculos) {
		JMenuItem submenuRecalcularIndicadores = new JMenuItem("Recalcular indicadores");
		submenuRecalcularIndicadores.addActionListener(new ActionListener() {
			/**
			 * Abre a interface responsável por recalcular os indicadores
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.recalcularIndicadores();
			}
		});
		menuCalculos.add(submenuRecalcularIndicadores);
		listaSubmenus.add(submenuRecalcularIndicadores);
	}

	/**
	 * Cria o submenu que cria as tabelas no banco de dados
	 * @param menuBancoDados menu do banco de dados
	 */
	public static void criarTabelas(JMenu menuBancoDados) {
		JMenuItem submenuCriarTabelas = new JMenuItem("Criar tabelas");
		submenuCriarTabelas.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que cria as tabelas no banco de dados
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.criarTabelas();
			}
		});
		menuBancoDados.add(submenuCriarTabelas);
		listaSubmenus.add(submenuCriarTabelas);
	}

	/**
	 * Cria o submenu que exclui as tabelas no banco de dados
	 * @param menuBancoDados menu do banco de dados
	 */
	public static void excluirTabelas(JMenu menuBancoDados) {
		JMenuItem submenuExcluirTabelas = new JMenuItem("Excluir tabelas");
		submenuExcluirTabelas.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que exclui as tabelas no banco de dados
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.excluirTabelas();
			}
		});
		menuBancoDados.add(submenuExcluirTabelas);
		listaSubmenus.add(submenuExcluirTabelas);
	}

	/**
	 * Cria o submenu que importa as tabelas para o banco de dados
	 * @param menuBancoDados menu do banco de dados
	 */
	public static void importarTabelas(JMenu menuBancoDados) {
		JMenuItem submenuImportarTabelas = new JMenuItem("Importar tabelas");
		submenuImportarTabelas.addActionListener(new ActionListener() {
			/**
			 * Abre a interface que importa as tabelas no banco de dados
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.importarTabelas();
			}
		});
		menuBancoDados.add(submenuImportarTabelas);
		listaSubmenus.add(submenuImportarTabelas);
	}

	/**
	 * Cria o submenu que mostra o tutorial
	 * @param menuAjuda menu de ajuda
	 */
	public static void tutorial(JMenu menuAjuda) {
		JMenuItem submenuTutorial = new JMenuItem("Tutorial");
		submenuTutorial.addActionListener(new ActionListener() {
			/**
			 * Abre a interface do tutorial
			 */
			public void actionPerformed(ActionEvent e) {
				JanelaPrincipal.tutorial();
			}
		});
		menuAjuda.add(submenuTutorial);
		listaSubmenus.add(submenuTutorial);
	}

	/**
	 * 
	 * @return uma lista contendo todos os submenus
	 */
	public static List<JMenuItem> getListaSubmenus() {
		return listaSubmenus;
	}
}
