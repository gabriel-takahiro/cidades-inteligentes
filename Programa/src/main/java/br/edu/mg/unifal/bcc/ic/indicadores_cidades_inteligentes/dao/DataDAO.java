package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataDAO {

	private Connection connection;

	public DataDAO(Connection connection) {
		this.connection = connection;
	}

	public void atualizaData(String data) {
		try {
			String sql = "SELECT * FROM data WHERE data = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, data);
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					if (!(rst.next())) {
						sql = "INSERT INTO data (data, codigo_data) VALUES (?, ?)";
						try (PreparedStatement pstm2 = connection.prepareStatement(sql)) {
							pstm2.setString(1, data);
							pstm2.setString(2, data);
							pstm2.execute();
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao atualizar a data.");
		}
	}
}
