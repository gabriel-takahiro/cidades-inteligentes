package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.PossuiVariavel;

/**
 * Classe que faz conexão com a tabela cadastrados.indicadores.
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class CadastradosIndicadorDAO {

	private Connection connection;

	public CadastradosIndicadorDAO(Connection connection) {
		this.connection = connection;
	}

	public void buscaIndicadoresCadastrados(JTable table) {
		try {
			String sql = "SELECT codigo_indicador, nome_indicador, numero_meta, metodo_calculo, numero_ods "
					+ "FROM cadastrados.indicador NATURAL JOIN ods ORDER BY 5, 3";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					ResultSetMetaData rsmd = rst.getMetaData();

					int coluna = rsmd.getColumnCount();
					String[] nomeColuna = new String[coluna + 1];
					nomeColuna[0] = "Seleciona";
					for (int i = 1; i < coluna + 1; i++) {
						if (rsmd.getColumnName(i).equals("codigo_indicador")) {
							nomeColuna[i] = "Código";
						} else if (rsmd.getColumnName(i).equals("nome_indicador")) {
							nomeColuna[i] = "Nome";
						} else if (rsmd.getColumnName(i).equals("numero_meta")) {
							nomeColuna[i] = "Meta";
						} else if (rsmd.getColumnName(i).equals("metodo_calculo")) {
							nomeColuna[i] = "Fórmula";
						} else {
							nomeColuna[i] = rsmd.getColumnName(i);
						}

					}
					model.setColumnIdentifiers(nomeColuna);

					String codigo_indicador, nome_indicador, numero_meta, metodo_calculo;
					while (rst.next()) {
						codigo_indicador = "C" + rst.getString(1);
						nome_indicador = rst.getString(2);
						numero_meta = rst.getString(3);
						metodo_calculo = rst.getString(4);

						// String[] linha = { codigo_indicador, nome_indicador, numero_meta,
						// metodo_calculo };
						model.addRow(
								new Object[] { false, codigo_indicador, nome_indicador, numero_meta, metodo_calculo });
					}
				}

			}
		} catch (Exception e) {
			throw new RuntimeException("Falha na busca dos indicadores cadastrados.");
		}
	}

	public void cadastrarIndicador(String nomeIndicador, String metodoCalculo, String eixo, String tipoPlano,
			String nomePlano, String descricao, String informacoesTecnicas, String meta) {
		try {
			String sql = "INSERT INTO indicador (nome_indicador, metodo_calculo, nome_eixo, "
					+ "tipo_plano, nome_plano, descricao, informacoes_tecnicas, numero_meta, padrao) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, nomeIndicador);
				pstm.setString(2, metodoCalculo);
				pstm.setString(3, eixo);
				pstm.setString(4, tipoPlano);
				pstm.setString(5, nomePlano);
				pstm.setString(6, descricao);
				pstm.setString(7, informacoesTecnicas);
				pstm.setString(8, meta);
				pstm.setBoolean(9, false);
				pstm.execute();

				PossuiVariavel.inserir(metodoCalculo);
				JanelaPrincipal.atualizarIndicadores();
				new JanelaMensagem("Cadastrado com sucesso!");
			}
		} catch (Exception e) {
			new JanelaMensagem("Falha no cadastro.");
			throw new RuntimeException("Falha no cadastro.");
		}
	}

	public void atualizarIndicador(int codigo_indicador, String nome_indicador, String metodo_calculo, String eixo,
			String tipo_plano, String nome_plano, String descricao, String informacoes_tecnicas, String meta,
			boolean padrao) {
		try {
			String sql = "UPDATE indicador SET nome_indicador = ?,  metodo_calculo = ?, nome_eixo = ?, tipo_plano = ?, nome_plano = ?, descricao = ?, informacoes_tecnicas = ?, numero_meta = ?, padrao = ? WHERE codigo_indicador = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, nome_indicador);
				pstm.setString(2, metodo_calculo);
				pstm.setString(3, eixo);
				pstm.setString(4, tipo_plano);
				pstm.setString(5, nome_plano);
				pstm.setString(6, descricao);
				pstm.setString(7, informacoes_tecnicas);
				pstm.setString(8, meta);
				pstm.setBoolean(9, padrao);
				pstm.setInt(10, codigo_indicador);
				pstm.execute();

				JanelaPrincipal.atualizarIndicadores();
				new JanelaMensagem("Atualizado com sucesso!");
			}
		} catch (Exception e) {
			new JanelaMensagem("Falha ao atualizar o indicador: " + codigo_indicador);
			throw new RuntimeException("Falha no cadastro.");
		}
	}
}
