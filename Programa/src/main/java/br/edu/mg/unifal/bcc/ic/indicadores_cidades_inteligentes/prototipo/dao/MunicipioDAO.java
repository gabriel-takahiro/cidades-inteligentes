package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDAO {

	private Connection connection;

	public MunicipioDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Integer> buscarTodosMunicipios() {
		List<Integer> codigos = new ArrayList<Integer>();
		try {
			String sql = "SELECT codigo_municipio FROM municipio";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						codigos.add(rst.getInt(1));
					}
				}
				return codigos;
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar todos os municípios.");
		}
	}

	public void verificaExistencia(int codigo, String municipio, String uf) {

		try {
			String sql = "SELECT * FROM municipio WHERE codigo_municipio = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setInt(1, codigo);
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (!rst.next()) {
						sql = "INSERT INTO municipio (codigo_municipio, nome_municipio, nome_uf) VALUES (?, ?, ?)";

						switch (uf) {
						case "AC":
							uf = "Acre";
							break;

						case "AL":
							uf = "Alagoas";
							break;

						case "AM":
							uf = "Amazonas";
							break;

						case "AP":
							uf = "Amapá";
							break;

						case "BA":
							uf = "Bahia";
							break;

						case "CE":
							uf = "Ceará";
							break;

						case "DF":
							uf = "Distrito Federal";
							break;

						case "ES":
							uf = "Espírito Santo";
							break;

						case "GO":
							uf = "Goiás";
							break;

						case "MA":
							uf = "Maranhão";
							break;

						case "MG":
							uf = "Minas Gerais";
							break;

						case "MS":
							uf = "Mato Grosso do Sul";
							break;

						case "MT":
							uf = "Mato Grosso";
							break;

						case "PA":
							uf = "Pará";
							break;

						case "PB":
							uf = "Paraíba";
							break;

						case "PE":
							uf = "Pernambuco";
							break;

						case "PI":
							uf = "Piauí";
							break;

						case "PR":
							uf = "Paraná";
							break;

						case "RJ":
							uf = "Rio de Janeiro";
							break;

						case "RN":
							uf = "Rio Grande do Norte";
							break;

						case "RO":
							uf = "Rondônia";
							break;

						case "RS":
							uf = "Rio Grande do Sul";
							break;

						case "SC":
							uf = "Santa Catarina";
							break;

						case "SE":
							uf = "Sergipe";
							break;

						case "SP":
							uf = "São Paulo";
							break;

						case "TO":
							uf = "Tocantins";
							break;

						default:
							break;
						}

						pstm.setInt(1, codigo);
						pstm.setString(2, municipio);
						pstm.setString(3, uf);
						pstm.execute();
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao verificar a existência de um municipio.");
		}
	}
}
