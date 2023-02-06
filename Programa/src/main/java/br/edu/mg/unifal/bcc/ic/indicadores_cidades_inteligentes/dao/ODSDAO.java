package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.ODS;

public class ODSDAO {

	private Connection connection;

	public ODSDAO(Connection connection) {
		this.connection = connection;
	}

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
		}
	}

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

	public void atualizarODS(int numero_ods, String nome_objetivo, int numero_ods_atualizar) {
		try {
			String sql = "UPDATE ods SET numero_ods = ?,  nome_objetivo = ? WHERE numero_ods = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, numero_ods);
				pstm.setString(2, nome_objetivo);
				pstm.setInt(3, numero_ods_atualizar);
				pstm.execute();

				JanelaPrincipal.atualizarODS();
				new JanelaMensagem("Atualizado com sucesso!");
			}
		} catch (Exception e) {
			new JanelaMensagem("Falha ao atualizar a ODS: " + numero_ods_atualizar);
			throw new RuntimeException("Falha na atualização.");
		}
	}

	public void excluir(ODS ods) {
		try {
			String sql = "DELETE FROM ods WHERE numero_ods = ? AND nome_objetivo = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, ods.getNumero_ods());
				pstm.setString(2, ods.getNome_objetivo());

				pstm.execute();

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao excluir a ods " + ods.getNumero_ods());
		}
	}

}
