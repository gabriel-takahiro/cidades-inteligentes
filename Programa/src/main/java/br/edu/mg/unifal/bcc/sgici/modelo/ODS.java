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
package br.edu.mg.unifal.bcc.sgici.modelo;

import java.sql.Connection;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.edu.mg.unifal.bcc.sgici.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.ODSDAO;

/**
 * Classe que representa a tabela ods do banco de dados.
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public class ODS {

	private int numero_ods;
	private String nome_objetivo;

	/**
	 * 
	 * @param numero_ods    número da ods
	 * @param nome_objetivo nome do objetivo
	 */
	public ODS(int numero_ods, String nome_objetivo) {
		this.numero_ods = numero_ods;
		this.nome_objetivo = nome_objetivo;
	}

	public ODS() {
	}

	/**
	 * Preenche o comboBox com todas as ods do banco de dados
	 * @param comboBoxODS comboBox a ser utilizada
	 */
	public static void buscarODS(JComboBox<Object> comboBoxODS) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			ODSDAO odsDAO = new ODSDAO(connection);

			odsDAO.buscarODS(comboBoxODS);

		} catch (Exception e) {
			System.out.println("Falha ao buscar ODS.");
		}
	}

	/**
	 * Preenche uma tabela com todas as ods do banco de dados
	 * 
	 * @param tableODS  tabela a ser utilizada
	 * @param seleciona true caso seja necesário uma coluna extra que permite
	 *                  selecionar as ods
	 */
	public static void mostrarODS(JTable tableODS, boolean seleciona) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			ODSDAO oDSDAO = new ODSDAO(connection);

			oDSDAO.mostrarODS(tableODS, seleciona);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar tabela de ODS.");
		}
	}

	/**
	 * Cadastra ods no banco de dados
	 * 
	 * @param ods ods a ser cadastrada
	 */
	public static void cadastrarODS(ODS ods) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){
			ODSDAO oDSDAO = new ODSDAO(connection);

			oDSDAO.cadastrarODS(ods.numero_ods, ods.nome_objetivo);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Busca todas as informações de uma meta a partir de algumas informações
	 * 
	 * @param ods ods com algumas informações
	 * @return ods com todas as informações
	 */
	public static ODS buscaODS(ODS ods) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){
			ODSDAO oDSDAO = new ODSDAO(connection);

			return oDSDAO.buscaODS(ods.numero_ods, ods.nome_objetivo);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar a ODS " + ods.numero_ods);
		}
	}

	/**
	 * Atualiza uma ods no banco de dados com as informações passadas como parâmetro
	 * 
	 * @param ods        ods atualizada
	 * @param numero_ods número da ods a ser atualizada
	 */
	public static void atualizarODS(ODS ods, int numero_ods) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){
			ODSDAO oDSDAO = new ODSDAO(connection);

			oDSDAO.atualizarODS(ods.nome_objetivo, numero_ods);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Preenche uma tabela apenas com as ods presentes na lista de ods passada por
	 * parâmetro
	 * 
	 * @param tableODS tabela a ser utilizada
	 * @param listaODS lista de ods selecionadas
	 */
	public static void mostrarODS(JTable tableODS, List<ODS> listaODS) {
		try {
			if(listaODS.isEmpty()) {
				throw new RuntimeException("Não há metas");
			}
			int coluna = 2;

			DefaultTableModel model = (DefaultTableModel) tableODS.getModel();
			String[] nomeColuna = new String[coluna];

			nomeColuna[0] = "ODS";
			nomeColuna[1] = "Nome do objetivo";

			model.setColumnIdentifiers(nomeColuna);

			listaODS.forEach(ods -> {
				String[] linha = { Integer.toString(ods.numero_ods), ods.nome_objetivo };
				model.addRow(linha);
			});

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar as ODS na tabela.");
		}
	}

	/**
	 * Exclui uma lista de ods do banco de dados
	 * 
	 * @param listaODS lista de ods a serem excluídas
	 */
	public static void excluir(List<ODS> listaODS) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			ODSDAO oDSDAO = new ODSDAO(connection);

			for (ODS ods : listaODS) {
				oDSDAO.excluir(ods);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 
	 * @return número da ods
	 */
	public int getNumero_ods() {
		return numero_ods;
	}

	/**
	 * 
	 * @return nome do objetivo
	 */
	public String getNome_objetivo() {
		return nome_objetivo;
	}

}
