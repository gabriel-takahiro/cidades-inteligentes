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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.ODS;

/**
 * Classe que faz conexão com a tabela "ods".
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public class ODSDAO {

	private Connection connection;

	/**
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public ODSDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Preenche o comboBox com todas as ODS cadastradas no banco de dados
	 * @param comboBoxODS comboBox a ser preenchida
	 */
	public void buscarODS(JComboBox<Object> comboBoxODS) {
		try {
			String sql = "SELECT numero_ods FROM ods ORDER BY 1";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						comboBoxODS.addItem(rst.getInt(1));
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar a ODS.");
		}
	}

	/**
	 * Preenche a tabela com todos as ods do banco de dados
	 * @param tableODS tabela a ser preenchida
	 * @param seleciona é necessário um coluna extra para uma caixa de selecionar?
	 */
	public void mostrarODS(JTable tableODS, boolean seleciona) {
		try {
			String sql = "SELECT numero_ods, nome_objetivo FROM ods ORDER BY 1";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					DefaultTableModel model = (DefaultTableModel) tableODS.getModel();
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
						} else if (rsmd.getColumnName(i).equals("nome_objetivo")) {
							nomeColuna[i - 1] = "Nome do objetivo";
						} else {
							nomeColuna[i - 1] = rsmd.getColumnName(i);
						}

					}

					if (seleciona) {
						nomeColuna[coluna] = "Seleciona";
					}

					model.setColumnIdentifiers(nomeColuna);

					tableODS.setRowSorter(new TableRowSorter<>(model));
					int numero_ods;
					String nome_objetivo;
					while (rst.next()) {
						numero_ods = rst.getInt(1);
						nome_objetivo = rst.getString(2);

						String[] linha = { Integer.toString(numero_ods), nome_objetivo };
						model.addRow(linha);
					}
					tableODS.setEnabled(true);
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar as ODS na tabela.");
		}
	}

	/**
	 * Cadastra ODS no banco de dados
	 * @param numero_ods número da ODS a ser cadastrada
	 * @param nome_objetivo nome do objetivo da ODS
	 */
	public void cadastrarODS(int numero_ods, String nome_objetivo) {
		try {
			String sql = "INSERT INTO ods (numero_ods, nome_objetivo) VALUES (?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, numero_ods);
				pstm.setString(2, nome_objetivo);
				pstm.execute();

				JanelaPrincipal.atualizarODS();
				new JanelaMensagem("ODS cadastrada");
			}
		} catch (Exception e) {
			new JanelaMensagem("A ODS \"" + numero_ods + "\" já existe.");
			throw new RuntimeException();
		}
	}

	/**
	 * Verifica a existência da ODS no banco de dados
	 * @param numero_ods número da ODS
	 * @param nome_objetivo nome do objetivo
	 * @return ods com número de ODS e o nome do objetivo
	 */
	public ODS buscaODS(int numero_ods, String nome_objetivo) {
		try {
			String sql = "SELECT numero_ods, nome_objetivo FROM ods WHERE numero_ods = ? AND nome_objetivo = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, numero_ods);
				pstm.setString(2, nome_objetivo);
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (rst.next()) {
						return new ODS(rst.getInt(1), rst.getString(2));
					}
					throw new RuntimeException("Falha ao buscar a ODS " + numero_ods);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar a ODS " + numero_ods);
		}
	}

	/**
	 * Atualiza as informações da ODS no banco de dados
	 * @param nome_objetivo nome do objetivo
	 * @param numero_ods_atualizar número da ODS a ser atualizada
	 */
	public void atualizarODS(String nome_objetivo, int numero_ods_atualizar) {
		try {
			String sql = "UPDATE ods SET nome_objetivo = ? WHERE numero_ods = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, nome_objetivo);
				pstm.setInt(2, numero_ods_atualizar);
				pstm.execute();

				JanelaPrincipal.atualizarODS();
				new JanelaMensagem("Atualizado com sucesso!");
			}
		} catch (Exception e) {
			new JanelaMensagem("Falha ao atualizar a ODS: " + numero_ods_atualizar);
			throw new RuntimeException();
		}
	}

	/**
	 * Exclui da tabela "ods" uma ODS a partir do número da ODS e do nome do objetivo
	 * @param ods ods contendo o número da ODS e o nome do objetivo
	 */
	public void excluir(ODS ods) {
		try {
			String sql = "DELETE FROM ods WHERE numero_ods = ? AND nome_objetivo = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, ods.getNumero_ods());
				pstm.setString(2, ods.getNome_objetivo());

				pstm.execute();

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao excluir a ods: " + ods.getNumero_ods() + "\nEsta ODS está sendo utilizada por alguma meta.");
		}
	}

}
