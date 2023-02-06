package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Variavel;


/**
 * Classe que faz conexão com a tabela variavel.
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class VariavelDAO {

	private Connection connection;

	public VariavelDAO(Connection connection) {
		this.connection = connection;
	}

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
		}
	}

	public Variavel buscaVariavel(Variavel variavel) {
		try {
			String sql = "SELECT codigo_variavel, tipo_banco, nome_variavel, codigo_banco, atualizacao, padrao FROM variavel WHERE codigo_variavel = ?";

			if (variavel.getCodigo_banco() == null) {
				sql = sql + " AND codigo_banco is null";
			} else {
				sql = sql + " AND codigo_banco = ?";
			}

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, variavel.getCodigo());

				if (!(variavel.getCodigo_banco() == null)) {
					pstm.setString(2, variavel.getCodigo_banco());
				}

				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (rst.next()) {
						return new Variavel(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getString(4),
								rst.getString(5), rst.getBoolean(6));
					}
					throw new RuntimeException("Falha ao buscar a variável " + variavel.getCodigo());
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar a variável " + variavel.getCodigo());
		}
	}

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

				JanelaPrincipal.atualizarVariaveis();
				new JanelaMensagem("Atualizado com sucesso!");
			}
		} catch (Exception e) {
			new JanelaMensagem("Falha ao atualizar a variável: " + codigo_antigo);
			throw new RuntimeException("Falha na atualização.");
		}
	}

	public void excluir(Variavel variavel) {
		try {
			String sql = "DELETE FROM variavel WHERE codigo_variavel = ?";

			if (variavel.getCodigo_banco() == null) {
				sql = sql + " AND codigo_banco is null";
			} else {
				sql = sql + " AND codigo_banco = ?";
			}
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, variavel.getCodigo());

				if (!(variavel.getCodigo_banco() == null)) {
					pstm.setString(2, variavel.getCodigo_banco());
				}

				pstm.execute();

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao excluir a variável " + variavel.getCodigo());
		}
	}
}
