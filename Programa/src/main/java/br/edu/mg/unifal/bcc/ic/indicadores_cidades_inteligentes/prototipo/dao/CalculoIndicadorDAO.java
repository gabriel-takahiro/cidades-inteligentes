package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.janelas.unica.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.CalculoIndicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Indicador;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.IndicadoresBuscados;

/**
 * Classe que faz conexão com a tabela calculo_indicador.
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class CalculoIndicadorDAO {

	private Connection connection;

	public CalculoIndicadorDAO(Connection connection) {
		this.connection = connection;
	}

	public Set<CalculoIndicador> buscarResultadosSet(int codigoMunicipio, String data) {
		Set<CalculoIndicador> resultados = new HashSet<CalculoIndicador>();
		try {
			String sql = "SELECT codigo_indicador, codigo_municipio, data, resultado FROM calculo_indicador"
					+ " WHERE codigo_municipio = ? AND data = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, codigoMunicipio);
				pstm.setString(2, data);
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						CalculoIndicador resultado = new CalculoIndicador(rst.getInt(1), rst.getInt(2),
								rst.getString(3), rst.getString(4));

						resultados.add(resultado);
					}
				}
			}
			return resultados;
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar o conjunto dos resultados.");
		}
	}

	public String buscaResultado(int codigo, int codigo_municipio, String data) {
		try {
			String sql = "SELECT resultado FROM calculo_indicador WHERE codigo_indicador = ? AND codigo_municipio = ? AND data = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, codigo);
				pstm.setInt(2, codigo_municipio);
				pstm.setString(3, data);

				pstm.execute();

				String resultado = "";

				try (ResultSet rst = pstm.getResultSet()) {
					if (rst.next()) {
						resultado = rst.getString(1);
					}
				}
				System.out.println(resultado);
				return resultado;
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar o resultado do indicador." + e);
		}
	}

	public List<IndicadoresBuscados> buscarResultadosList(int codigoMunicipio, String data,
			ArrayList<Indicador> listaIndicadoresSelecionados) {
		List<IndicadoresBuscados> resultados = new ArrayList<IndicadoresBuscados>();
		try {
			String sqlIndicadoresCalculados = "SELECT codigo_indicador, numero_ods, numero_meta, nome_indicador, codigo_municipio,"
					+ " nome_municipio, data, resultado, nome_uf, metodo_calculo, data_variaveis, valor_oficial, padrao FROM calculo_indicador"
					+ " NATURAL JOIN municipio NATURAL JOIN indicador NATURAL JOIN meta NATURAL JOIN ods"
					+ " WHERE codigo_municipio = ? AND data = ? ORDER BY 2, 3;";

			String sqlIndicadoresNaoCalculados = "SELECT codigo_indicador, numero_ods, numero_meta, nome_indicador, codigo_municipio,"
					+ " nome_municipio, nome_uf, metodo_calculo, padrao FROM indicador"
					+ " NATURAL JOIN municipio NATURAL JOIN meta NATURAL JOIN ods"
					+ " WHERE codigo_municipio = ? AND codigo_indicador = ? ORDER BY 2, 3;";

			try (PreparedStatement pstm = connection.prepareStatement(sqlIndicadoresCalculados)) {
				pstm.setInt(1, codigoMunicipio);
				pstm.setString(2, data);
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						for (Indicador indicador : listaIndicadoresSelecionados) {
							if (indicador.getCodigo() == rst.getInt(1)) {
								IndicadoresBuscados resultado = new IndicadoresBuscados(rst.getInt(1), rst.getInt(2),
										rst.getString(3), rst.getString(4), rst.getInt(5), rst.getString(6),
										rst.getString(7), rst.getString(8), rst.getString(9), rst.getString(10),
										rst.getString(11), rst.getBoolean(12), rst.getBoolean(13));

								resultados.add(resultado);
							}
						}
					}

					if (listaIndicadoresSelecionados.size() == resultados.size()) {
						return resultados;
					}

					ArrayList<Integer> listaCodigoIndicadores = new ArrayList<>();
					for (Indicador indicadoresSelecionados : listaIndicadoresSelecionados) {
						int valor = 0;
						for (IndicadoresBuscados indicadoresBuscados : resultados) {
							if (indicadoresBuscados.getCodigo_indicador() == indicadoresSelecionados.getCodigo()) {
								valor++;
							}
						}
						if (valor == 0) {
							listaCodigoIndicadores.add(indicadoresSelecionados.getCodigo());
						}
					}

					for (int i = 0; i < listaCodigoIndicadores.size(); i++) {
						try (PreparedStatement pstm2 = connection.prepareStatement(sqlIndicadoresNaoCalculados)) {
							pstm2.setInt(1, codigoMunicipio);
							pstm2.setInt(2, listaCodigoIndicadores.get(i));
							pstm2.execute();

							try (ResultSet rst2 = pstm2.getResultSet()) {
								if (rst2.next()) {
									IndicadoresBuscados resultado = new IndicadoresBuscados(rst2.getInt(1),
											rst2.getInt(2), rst2.getString(3), rst2.getString(4), rst2.getInt(5),
											rst2.getString(6), data, "-", rst2.getString(7), rst2.getString(8), "",
											false, rst2.getBoolean(9));

									resultados.add(resultado);
								}
							}
						}
					}
				}
			}
			return resultados;
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar a lista dos resultados.");
		}
	}

	public void insereResultado(int codigo, int codigo_municipio, String data, String resultado, String data_variaveis,
			boolean valor_oficial) {
		try {
			String sql = "INSERT INTO calculo_indicador (codigo_indicador, codigo_municipio, data, resultado, data_variaveis, valor_oficial)"
					+ " VALUES (?, ?, ?, ?, ?, ?)";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, codigo);
				pstm.setInt(2, codigo_municipio);
				pstm.setString(3, data);
				pstm.setString(4, resultado);
				pstm.setString(5, data_variaveis);
				pstm.setBoolean(6, valor_oficial);

				pstm.execute();
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao inserir o resultado do indicador.");
		}
	}

	public void updateResultado(String resultado, int codigo_indicador, int codigo_municipio, String data,
			String data_variaveis, boolean valor_oficial) {
		try {
			String sql = "UPDATE calculo_indicador SET resultado = ?, data_variaveis = ?, valor_oficial = ? WHERE codigo_indicador = ? AND"
					+ " codigo_municipio = ? AND data = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, resultado);
				pstm.setString(2, data_variaveis);
				pstm.setBoolean(3, valor_oficial);
				pstm.setInt(4, codigo_indicador);
				pstm.setInt(5, codigo_municipio);
				pstm.setString(6, data);
				pstm.execute();
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao atualizar o resultado do indicador.");
		}
	}

	public boolean possuiResultado(int codigo, int codigo_municipio, String data) {
		try {
			String sql = "SELECT * FROM calculo_indicador WHERE codigo_indicador = ? AND"
					+ " codigo_municipio = ? AND data = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, codigo);
				pstm.setInt(2, codigo_municipio);
				pstm.setString(3, data);
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (rst.next()) {
						return true;
					}
					return false;
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Falha ao verificar se existe resultado para um indicador.");
		}
	}

	public Set<CalculoIndicador> buscarResultadosTodosMunicipios(List<Integer> codigos, String data) {
		Set<CalculoIndicador> resultados = new HashSet<CalculoIndicador>();
		try {
			String sql = "SELECT codigo_indicador, codigo_municipio, data FROM calculo_indicador"
					+ " WHERE codigo_municipio = ? AND data = ?";

			for (int codigoMunicipio : codigos) {
				try (PreparedStatement pstm = connection.prepareStatement(sql)) {
					pstm.setInt(1, codigoMunicipio);
					pstm.setString(2, data);
					pstm.execute();

					try (ResultSet rst = pstm.getResultSet()) {
						while (rst.next()) {
							CalculoIndicador resultado = new CalculoIndicador(rst.getInt(1), rst.getInt(2),
									rst.getString(3));

							resultados.add(resultado);
						}
					}
				}
			}
			return resultados;
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar o conjunto dos resultados.");
		}
	}

	public void excluir(int codigo_indicador, int codigo_municipio, String data) {
		try {
			String sql = "DELETE FROM calculo_indicador WHERE codigo_indicador = ? AND codigo_municipio = ? AND data = ?";
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, codigo_indicador);
				pstm.setInt(2, codigo_municipio);
				pstm.setString(3, data);
				pstm.execute();

				new JanelaMensagem("Resultado excluído com sucesso");
			}

		} catch (Exception e) {
			throw new RuntimeException(
					"Falha ao excluir o calculo_indicador. " + codigo_indicador + " " + codigo_municipio + " " + data);
		}
	}

}
