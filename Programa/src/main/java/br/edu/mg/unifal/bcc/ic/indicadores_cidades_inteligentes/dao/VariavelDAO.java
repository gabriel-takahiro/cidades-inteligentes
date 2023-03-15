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
package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.PossuiVariavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;


/**
 * Classe que faz conexão com a tabela "variavel".
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class VariavelDAO {

	private Connection connection;

	/**
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public VariavelDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Busca todas as variáveis cadastradas no banco de dados
	 * @return lista com todas as variáveis
	 */
	public List<Variavel> buscarLista() {
		List<Variavel> variaveis = new ArrayList<Variavel>();
		try {
			String sql = "SELECT codigo_variavel, tipo_banco, nome_variavel, codigo_banco, atualizacao FROM variavel";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						Variavel variavel = new Variavel(rst.getInt(1), rst.getString(2), rst.getString(3),
								rst.getString(4), rst.getString(5));

						variaveis.add(variavel);
					}
				}
			}
			return variaveis;
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar a lista de variaveis.");
		}
	}

	/**
	 * Preenche a tabela com todos as variáveis do banco de dados
	 * @param tableVariaveis tabela a ser preenchida
	 * @param seleciona é necessário um coluna extra para uma caixa de selecionar?
	 */
	public void mostrarVariaveis(JTable tableVariaveis, boolean seleciona) {
		try {
			String sql = "SELECT codigo_variavel, tipo_banco, nome_variavel, codigo_banco, atualizacao, padrao FROM variavel ORDER BY 6 DESC, 1 ASC";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					DefaultTableModel model = (DefaultTableModel) tableVariaveis.getModel();
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
						if (rsmd.getColumnName(i).equals("codigo_variavel")) {
							nomeColuna[i - 1] = "Código";
						} else if (rsmd.getColumnName(i).equals("nome_variavel")) {
							nomeColuna[i - 1] = "Nome";
						} else if (rsmd.getColumnName(i).equals("tipo_banco")) {
							nomeColuna[i - 1] = "Banco";
						} else if (rsmd.getColumnName(i).equals("codigo_banco")) {
							nomeColuna[i - 1] = "Código do banco";
						} else if (rsmd.getColumnName(i).equals("atualizacao")) {
							nomeColuna[i - 1] = "Atualização";
						} else if (rsmd.getColumnName(i).equals("padrao")) {
							nomeColuna[i - 1] = "Variável padrão";
						} else {
							nomeColuna[i - 1] = rsmd.getColumnName(i);
						}

					}

					if (seleciona) {
						nomeColuna[coluna] = "Seleciona";
					}

					model.setColumnIdentifiers(nomeColuna);
					tableVariaveis.setRowSorter(new TableRowSorter<>(model));

					int codigo_variavel;
					String tipo_banco, nome_variavel, codigo_banco, atualizacao;
					boolean padrao;
					while (rst.next()) {
						codigo_variavel = rst.getInt(1);
						tipo_banco = rst.getString(2);
						nome_variavel = rst.getString(3);
						codigo_banco = rst.getString(4);
						atualizacao = rst.getString(5);
						padrao = rst.getBoolean(6);

						String[] linha = { Integer.toString(codigo_variavel), tipo_banco, nome_variavel, codigo_banco,
								atualizacao, Boolean.toString(padrao) };
						model.addRow(linha);
					}
					tableVariaveis.setEnabled(true);
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar as variaveis na tabela.");
		}
	}

	/**
	 * Cadastra variável no banco de dados
	 * @param codigo_variavel código da variável
	 * @param tipo_banco tipo de banco
	 * @param nome_variavel nome da variável
	 * @param codigo_banco código do banco de dados
	 * @param atualizacao período de atualização da variável
	 */
	public void cadastrarVariavel(int codigo_variavel, String tipo_banco, String nome_variavel, String codigo_banco,
			String atualizacao) {
		try {
			String sql = "INSERT INTO variavel (codigo_variavel, tipo_banco, nome_variavel, codigo_banco, atualizacao, padrao) VALUES (?, ?, ?, ?, ?::PERIODO, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, codigo_variavel);
				pstm.setString(2, tipo_banco);
				pstm.setString(3, nome_variavel);
				pstm.setString(4, codigo_banco);
				pstm.setString(5, atualizacao);
				pstm.setBoolean(6, false);
				pstm.execute();

				JanelaPrincipal.atualizarVariaveis();
				new JanelaMensagem("Variável cadastrada com sucesso.");
			}
		} catch (Exception e) {
			new JanelaMensagem("A variável \"" + codigo_variavel + "\" já existe.");
			throw new RuntimeException("Falha ao cadastrar variável");
		}
	}

	/**
	 * Busca uma variável no banco de dados a partir do código dela
	 * @param variavel variável contendo código da variável e o código do banco
	 * @return variável contendo todas as informações
	 */
	public Variavel buscaVariavel(Variavel variavel) {
		try {
			String sql = "SELECT codigo_variavel, tipo_banco, nome_variavel, codigo_banco, atualizacao, padrao FROM variavel WHERE codigo_variavel = ?";

			if (variavel.getCodigo_banco() == null) {
				sql = sql + " AND codigo_banco is null";
			} else {
				sql = sql + " AND codigo_banco = ?";
			}

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, variavel.getCodigo_variavel());

				if (!(variavel.getCodigo_banco() == null)) {
					pstm.setString(2, variavel.getCodigo_banco());
				}

				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (rst.next()) {
						return new Variavel(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
								rst.getString(5), rst.getBoolean(6));
					}
					throw new RuntimeException("Falha ao buscar a variável " + variavel.getCodigo_variavel());
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar a variável " + variavel.getCodigo_variavel());
		}
	}

	/**
	 * Atualiza as informações da variável no banco de dados
	 * @param codigo_variavel código da variável
	 * @param tipo_banco tipo de banco de dados
	 * @param nome_variavel nome da variável
	 * @param codigo_banco código do banco de dados
	 * @param atualizacao período de atualização da variável
	 * @param padrao a variável e padrão do programa?
	 * @param codigo_antigo código antigo da variável
	 */
	public void atualizarVariavel(int codigo_variavel, String tipo_banco, String nome_variavel, String codigo_banco,
			String atualizacao, boolean padrao, int codigo_antigo) {
		try {
			String sql = "UPDATE variavel SET codigo_variavel = ?,  tipo_banco = ?, nome_variavel = ?, codigo_banco = ?, atualizacao = ?::PERIODO, padrao = ? WHERE codigo_variavel = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, codigo_variavel);
				pstm.setString(2, tipo_banco);
				pstm.setString(3, nome_variavel);
				pstm.setString(4, codigo_banco);
				pstm.setString(5, atualizacao);
				pstm.setBoolean(6, padrao);
				pstm.setInt(7, codigo_antigo);
				pstm.execute();

				if(codigo_variavel != codigo_antigo) {
					PossuiVariavel.atualizarVariaveis(codigo_antigo, codigo_variavel);
				}
				
				JanelaPrincipal.atualizarVariaveis();
				new JanelaMensagem("Atualizado com sucesso!");
			}
		} catch (Exception e) {
			new JanelaMensagem("Falha ao atualizar a variável: " + codigo_antigo + "\nA variável com código " + codigo_variavel + " já existe.");
			throw new RuntimeException("Falha na atualização.");
		}
	}

	/**
	 * Exclui variável do banco de dados
	 * @param variavel variável a ser excluída contendo o código dela e o código do banco de dados
	 */
	public void excluir(Variavel variavel) {
		try {
			String sql = "DELETE FROM variavel WHERE codigo_variavel = ?";

			if (variavel.getCodigo_banco() == null) {
				sql = sql + " AND codigo_banco is null";
			} else {
				sql = sql + " AND codigo_banco = ?";
			}
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, variavel.getCodigo_variavel());

				if (!(variavel.getCodigo_banco() == null)) {
					pstm.setString(2, variavel.getCodigo_banco());
				}

				pstm.execute();

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao excluir a variável " + variavel.getCodigo_variavel() + "\nEsta variável está sendo utilizada em algum indicador");
		}
	}
}
