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
package br.edu.mg.unifal.bcc.sgici.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import br.edu.mg.unifal.bcc.sgici.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.sgici.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.sgici.modelo.Meta;

/**
 * Classe que faz conexão com a tabela "meta".
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public class MetaDAO {

	private Connection connection;

	/**
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public MetaDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Preenche um comboBox com todas as metas do banco de dados
	 * 
	 * @param comboBoxMeta comboBox a ser preenchida
	 */
	public void buscarMetas(JComboBox<String> comboBoxMeta) {
		try {
			String sql = "SELECT * FROM meta ORDER BY 3, 1";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				comboBoxMeta.addItem("");
				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						comboBoxMeta.addItem(rst.getString(1));
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar as metas.");
		}
	}

	/**
	 * Cadastra meta no banco de dados
	 * 
	 * @param numero_meta número da meta a ser cadastrada
	 * @param texto_meta  texto da meta
	 * @param numero_ods  número da ODS que a meta pertence
	 */
	public void cadastrarMeta(String numero_meta, String texto_meta, int numero_ods) {
		try {
			String sql = "INSERT INTO meta (numero_meta, texto_meta, numero_ods) VALUES (?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, numero_meta);
				pstm.setString(2, texto_meta);
				pstm.setInt(3, numero_ods);
				pstm.execute();

				JanelaPrincipal.atualizarMetas();
				new JanelaMensagem("A Meta " + numero_meta +  " foi cadastrada com sucesso.");
			}
		} catch (Exception e) {
			new JanelaMensagem("A meta \"" + numero_meta + "\" já existe.");
			throw new RuntimeException();
		}
	}

	/**
	 * Preenche a tabela com todos as metas do banco de dados
	 * 
	 * @param tableMetas tabela a ser preenchida
	 * @param seleciona  é necessário um coluna extra para uma caixa de selecionar?
	 */
	public void mostrarMetas(JTable tableMetas, boolean seleciona) {
		try {
			String sql = "SELECT numero_ods, numero_meta, texto_meta FROM meta ORDER BY 1, 2";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					DefaultTableModel model = (DefaultTableModel) tableMetas.getModel();
					ResultSetMetaData rsmd = rst.getMetaData();

					int coluna;
					if (seleciona) {
						coluna = rsmd.getColumnCount() + 1;
					} else {
						coluna = rsmd.getColumnCount();
					}

					String[] nomeColuna = new String[coluna];

					if (seleciona) {
						coluna--;
					}

					for (int i = 1; i < coluna + 1; i++) {
						if (rsmd.getColumnName(i).equals("numero_ods")) {
							nomeColuna[i - 1] = "ODS";
						} else if (rsmd.getColumnName(i).equals("numero_meta")) {
							nomeColuna[i - 1] = "Número da meta";
						} else if (rsmd.getColumnName(i).equals("texto_meta")) {
							nomeColuna[i - 1] = "Texto";
						} else {
							nomeColuna[i - 1] = rsmd.getColumnName(i);
						}

					}

					if (seleciona) {
						nomeColuna[coluna] = "Seleciona";
					}

					model.setColumnIdentifiers(nomeColuna);
					tableMetas.setRowSorter(new TableRowSorter<>(model));

					int numero_ods;
					String numero_meta, texto_meta;
					while (rst.next()) {
						numero_ods = rst.getInt(1);
						numero_meta = rst.getString(2);
						texto_meta = rst.getString(3);

						String[] linha = { Integer.toString(numero_ods), numero_meta, texto_meta };
						model.addRow(linha);
					}
					tableMetas.setEnabled(true);
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar as metas na tabela.");
		}
	}

	/**
	 * Busca todas as informações da meta a partir do número da meta e do número da
	 * ods
	 * 
	 * @param meta meta contendo apenas o número da meta e o número da ods
	 * @return meta contendo todas as informações
	 */
	public Meta buscaMeta(Meta meta) {
		try {
			String sql = "SELECT numero_meta, texto_meta, numero_ods FROM meta WHERE numero_meta = ? AND numero_ods = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, meta.getNumero_meta());

				pstm.setInt(2, meta.getNumero_ods());

				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (rst.next()) {
						return new Meta(rst.getString(1), rst.getString(2), rst.getInt(3));
					}
					throw new RuntimeException("Falha ao buscar a meta " + meta.getNumero_meta());
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar a meta " + meta.getNumero_meta());
		}
	}

	/**
	 * Atualiza as informações da meta no banco de dados
	 * 
	 * @param numero_meta número da meta
	 * @param texto_meta  texto da meta
	 * @param numero_ods  número da ods
	 * @param meta_antiga número da meta a ser atualizada
	 */
	public void atualizarMeta(String numero_meta, String texto_meta, int numero_ods, String meta_antiga) {
		try {
			String sql = "UPDATE meta SET numero_meta = ?,  texto_meta = ?, numero_ods = ? WHERE numero_meta = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, numero_meta);
				pstm.setString(2, texto_meta);
				pstm.setInt(3, numero_ods);
				pstm.setString(4, meta_antiga);
				pstm.execute();

				JanelaPrincipal.atualizarMetas();
				new JanelaMensagem("A meta " + numero_meta + " foi atualizada com sucesso!");
			}
		} catch (Exception e) {
			new JanelaMensagem("Falha ao atualizar a meta: " + meta_antiga + "\nMeta " + numero_meta + " já existe");
			throw new RuntimeException();
		}
	}

	/**
	 * Exclui da tabela "meta" uma meta a partir do número da meta e do número da
	 * ods
	 * 
	 * @param meta meta a ser excluída
	 */
	public void excluir(Meta meta) {
		try {
			String sql = "DELETE FROM meta WHERE numero_meta = ? AND numero_ods = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, meta.getNumero_meta());
				pstm.setInt(2, meta.getNumero_ods());

				pstm.execute();

			}
		} catch (Exception e) {
			throw new RuntimeException("Não foi possível excluir a(s) meta(s) devido à seguinte razão:\nA meta "
					+ meta.getNumero_meta()
					+ " está sendo atualmente utilizada por pelo menos um indicador. Por favor, remova a meta dos indicadores em questão antes de tentar excluí-la novamente.");
		}
	}

}
