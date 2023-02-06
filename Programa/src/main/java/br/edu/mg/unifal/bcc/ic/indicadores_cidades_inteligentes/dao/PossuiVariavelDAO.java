package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.IndicadoresBuscados;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.PossuiVariavel;

/**
 * Classe que faz conexão com a tabela possui_variavel.
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class PossuiVariavelDAO {

	private Connection connection;

	public PossuiVariavelDAO(Connection connection) {
		this.connection = connection;
	}

	public void salvar(PossuiVariavel pv) {
		try {
			String sql = "INSERT INTO possui_variavel VALUES (?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {

				pstm.setInt(1, pv.getVariavel());
				pstm.setInt(2, pv.getCodigo());

				pstm.execute();
			}
		} catch (Exception e) {
			System.out.println("falha ao salvar PossuiVariavel indicador:" + pv.getCodigo() + "		variável: "
					+ pv.getVariavel());
		}
	}

	public void tabelaCalculos(JTable tableIndicadoresSemResultado, IndicadoresBuscados indicador,
			int valorRetroativo) {
		try {
			String sqlVariaveisIndicador = "SELECT codigo_variavel, nome_variavel FROM possui_variavel NATURAL JOIN variavel WHERE codigo_indicador = ?";
			String sqlVariaveisValor = "SELECT valor, valor_oficial, valor_atualizado FROM valor_variavel WHERE codigo_variavel = ? AND codigo_municipio = ? AND data = ?";
			DefaultTableModel model = (DefaultTableModel) tableIndicadoresSemResultado.getModel();
			String[] nomeColuna = new String[6];
			nomeColuna[0] = "Código da variavel";
			nomeColuna[1] = "Nome da variavel";
			nomeColuna[2] = "Valor da variável";
			nomeColuna[3] = "Data";
			nomeColuna[4] = "Valor atualizado";
			nomeColuna[5] = "Valor oficial";
			model.setColumnIdentifiers(nomeColuna);
			try (PreparedStatement pstmVariaveisIndicador = connection.prepareStatement(sqlVariaveisIndicador)) {
				pstmVariaveisIndicador.setInt(1, indicador.getCodigo_indicador());
				pstmVariaveisIndicador.execute();
				try (ResultSet rstVariaveisIndicador = pstmVariaveisIndicador.getResultSet();) {
					while (rstVariaveisIndicador.next()) {
						try (PreparedStatement pstmVariaveisValor = connection.prepareStatement(sqlVariaveisValor)) {
							String data = buscaDataVariavel(indicador.getData_variaveis(),
									rstVariaveisIndicador.getInt(1));
							pstmVariaveisValor.setInt(1, rstVariaveisIndicador.getInt(1));
							pstmVariaveisValor.setInt(2, indicador.getCodigo_municipio());
							pstmVariaveisValor.setString(3, data);
							pstmVariaveisValor.execute();
							try (ResultSet rstVariaveisValor = pstmVariaveisValor.getResultSet();) {
								if (rstVariaveisValor.next()) {
									String[] linha = { Integer.toString(rstVariaveisIndicador.getInt(1)),
											rstVariaveisIndicador.getString(2),
											Integer.toString(rstVariaveisValor.getInt(1)), data,
											Boolean.toString(rstVariaveisValor.getBoolean(3)),
											Boolean.toString(rstVariaveisValor.getBoolean(2)) };
									model.addRow(linha);
								}
							} catch (Exception e) {
								System.out.println("Erro " + e);
							}
						}
					}
				}
			}
			tableIndicadoresSemResultado.setEnabled(false);
		} catch (Exception e) {
			throw new RuntimeException("Erro ao buscar a tabela de calculos.");
		}
	}

	private String buscaDataVariavel(String data_variaveis, int codigo_indicador) {
		String datasVariaveis = data_variaveis.replace(" ", "");
		String valor = "";
		String aux;
		ArrayList<String> seqVariaveis = new ArrayList<String>();
		try {
			for (int i = 0; i < datasVariaveis.length(); i++) {
				aux = datasVariaveis.substring(i, i + 1);
				if (aux.equals("/")) {
					if (!valor.isEmpty()) {
						seqVariaveis.add(valor);
					}
					seqVariaveis.add(aux);
					valor = "";
				} else {
					valor = valor.concat(aux);
				}
			}
			if (!valor.isEmpty()) {
				seqVariaveis.add(valor);
			}

			for (int i = 0; i < seqVariaveis.size(); i += 4) {
				if (Integer.parseInt(seqVariaveis.get(i)) == codigo_indicador) {
					return seqVariaveis.get(i + 2);
				}
			}
			return "";
		}catch (Exception e) {
			System.out.println("Erro buscaDV " + e);
			return "";
		}
	}
}
