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
import br.edu.mg.unifal.bcc.sgici.modelo.dao.MetaDAO;

/**
 * Classe que representa a tabela meta do banco de dados.
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public class Meta {

	private String numero_meta;
	private String texto_meta;
	private int numero_ods;

	/**
	 * 
	 * @param numero_meta número da meta
	 * @param texto_meta  texto da meta
	 * @param numero_ods  número da ods
	 */
	public Meta(String numero_meta, String texto_meta, int numero_ods) {
		this.numero_meta = numero_meta;
		this.texto_meta = texto_meta;
		this.numero_ods = numero_ods;
	}

	/**
	 * 
	 * @param numero_ods  número da ods
	 * @param numero_meta número da meta
	 */
	public Meta(int numero_ods, String numero_meta) {
		this.numero_meta = numero_meta;
		this.numero_ods = numero_ods;
	}

	/**
	 * Construtor padrão
	 */
	public Meta() {
	}

	/**
	 * Preenche o comboBox com todas as metas do banco de dados
	 * 
	 * @param comboBoxMeta comboBox a ser utilizado
	 */
	public static void buscarMetas(JComboBox<String> comboBoxMeta) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			MetaDAO metaDAO = new MetaDAO(connection);

			metaDAO.buscarMetas(comboBoxMeta);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar as metas.");
		}
	}

	/**
	 * Cadastra meta no banco de dados
	 * 
	 * @param numero_meta número de meta
	 * @param texto_meta  texto da meta
	 * @param numero_ods  número da ods
	 */
	public static void cadastrarMeta(String numero_meta, String texto_meta, int numero_ods) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			MetaDAO metaDAO = new MetaDAO(connection);

			metaDAO.cadastrarMeta(numero_meta, texto_meta, numero_ods);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao cadastrar meta.");
		}
	}

	/**
	 * Verifica se a meta corresponde a ods informada
	 * 
	 * @param numero_meta número da meta
	 * @param ods         número da ods
	 * @return true caso a meta corresponda a ods informada
	 */
	public static boolean metaCorrespondeODS(String numero_meta, int ods) {
		String numero_ods = Integer.toString(ods);
		for (int i = 0; i < numero_ods.length(); i++) {
			if (!numero_meta.substring(i, i + 1).equals(numero_ods.substring(i, i + 1))) {
				return false;
			}
		}
		if (numero_meta.substring(numero_ods.length(), numero_ods.length() + 1).equals(".")) {
			return true;
		}
		return false;
	}

	/**
	 * Preenche uma tabela com todas as metas do banco de dados
	 * 
	 * @param tableMetas tabela a ser utilizada
	 * @param seleciona  true caso seja necessário uma coluna extra que permite
	 *                   selecionar as metas
	 */
	public static void mostrarMetas(JTable tableMetas, boolean seleciona) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			MetaDAO metaDAO = new MetaDAO(connection);

			metaDAO.mostrarMetas(tableMetas, seleciona);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar tabela de metas.");
		}
	}

	/**
	 * Preenche uma tabela apenas com as metas presentes na lista de metas passada
	 * por parâmetro
	 * 
	 * @param tableMetas tabela a ser utilizada
	 * @param listaMetas lista de metas selecionadas
	 */
	public static void mostrarMetas(JTable tableMetas, List<Meta> listaMetas) {
		try {
			if (listaMetas.isEmpty()) {
				throw new RuntimeException("Não há metas");
			}
			
			int coluna = 3;

			DefaultTableModel model = (DefaultTableModel) tableMetas.getModel();
			String[] nomeColuna = new String[coluna];

			
			nomeColuna[0] = "ODS";
			nomeColuna[1] = "Número da meta";
			nomeColuna[2] = "Texto";

			model.setColumnIdentifiers(nomeColuna);

			listaMetas.forEach(meta -> {
				String[] linha = { Integer.toString(meta.numero_ods), meta.numero_meta, meta.texto_meta };
				model.addRow(linha);
			});

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar as metas na tabela.");
		}
	}

	/**
	 * Busca todas as informações de uma meta a partir de algumas informações
	 * @param meta meta com algumas informações
	 * @return meta com todas as informações
	 */
	public static Meta buscaMeta(Meta meta) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			MetaDAO metaDAO = new MetaDAO(connection);

			return metaDAO.buscaMeta(meta);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar a meta: " + meta.getNumero_meta());
		}
	}

	/**
	 * Atualiza uma meta no banco de dados com as informações passadas como parâmetro
	 * @param numero_meta número da meta
	 * @param texto_meta texto da meta
	 * @param numero_ods número da ods
	 * @param meta_antiga numero da meta antiga
	 */
	public static void atualizarMeta(String numero_meta, String texto_meta, int numero_ods, String meta_antiga) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){
			MetaDAO metaDAO = new MetaDAO(connection);

			metaDAO.atualizarMeta(numero_meta, texto_meta, numero_ods, meta_antiga);
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Exclui uma lista de metas do banco de dados
	 * @param listaMetas lista de metas a serem excluídas
	 */
	public static void excluir(List<Meta> listaMetas) {
		try (Connection connection = ConnectionFactory.recuperarConexao();){

			MetaDAO metaDAO = new MetaDAO(connection);

			for (Meta meta : listaMetas) {
				metaDAO.excluir(meta);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 
	 * @return número de meta
	 */
	public String getNumero_meta() {
		return numero_meta;
	}

	/**
	 * 
	 * @return texto da meta
	 */
	public String getTexto_meta() {
		return texto_meta;
	}

	/**
	 * 
	 * @return número da ods
	 */
	public int getNumero_ods() {
		return numero_ods;
	}

}
