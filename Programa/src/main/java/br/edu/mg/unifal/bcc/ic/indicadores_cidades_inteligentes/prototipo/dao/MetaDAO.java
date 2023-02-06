package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Meta;

public class MetaDAO {

	private Connection connection;

	public MetaDAO(Connection connection) {
		this.connection = connection;
	}

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

	public void cadastrarMeta(String numero_meta, String texto_meta, int numero_ods) {
		try {
			String sql = "INSERT INTO meta (numero_meta, texto_meta, numero_ods) VALUES (?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, numero_meta);
				pstm.setString(2, texto_meta);
				pstm.setInt(3, numero_ods);
				pstm.execute();

				JanelaPrincipal.atualizarMetas();
				new JanelaMensagem("Meta cadastrada");
			}
		} catch (Exception e) {
			new JanelaMensagem("A meta \"" + numero_meta + "\" já existe.");
		}
	}

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
			throw new RuntimeException("Falha ao buscar a variável " + meta.getNumero_meta());
		}
	}

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
				new JanelaMensagem("Atualizado com sucesso!");
			}
		} catch (Exception e) {
			new JanelaMensagem("Falha ao atualizar a meta: " + meta_antiga);
			throw new RuntimeException("Falha na atualização.");
		}
	}

	public void excluir(Meta meta) {
		try {
			String sql = "DELETE FROM meta WHERE numero_meta = ? AND numero_ods = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, meta.getNumero_meta());
				pstm.setInt(2, meta.getNumero_ods());

				pstm.execute();

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao excluir a meta " + meta.getNumero_meta());
		}
	}

}
