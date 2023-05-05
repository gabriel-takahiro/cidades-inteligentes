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
package br.edu.mg.unifal.bcc.sgici.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.mg.unifal.bcc.sgici.modelo.Municipio;

/**
 * Classe que faz conexão com a tabela "municipio".
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public class MunicipioDAO {

	private Connection connection;

	/**
	 * 
	 * @param connection conexão com o banco de dados
	 */
	public MunicipioDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Busca todos os municípios cadastrados no banco de dados
	 * 
	 * @return lista com todos os códigos dos municípios
	 */
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

	/**
	 * Busca todos os municípios cadastrados no banco de dados com seus respectivos
	 * nomes e UF.
	 * 
	 * @return uma lista de objetos do tipo Municipio, contendo o código, nome e UF
	 *         de cada município encontrado.
	 */
	public List<Municipio> buscarTodosMunicipiosComNome() {
		List<Municipio> listaMunicipiosComNome = new ArrayList<>();

		try {
			String sql = "SELECT codigo_municipio, nome_municipio, nome_uf FROM municipio";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();

				try (ResultSet rst = pstm.getResultSet()) {
					while (rst.next()) {
						listaMunicipiosComNome
								.add(new Municipio(rst.getInt(1), rst.getString(2) + " (" + rst.getString(3) + ")"));
					}
				}
				return listaMunicipiosComNome;
			}
		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar todos os municípios.");
		}
	}

	/**
	 * Verifica se o município já foi cadastrado no banco de dados. Caso não tenha
	 * sido cadastrado, ele será cadastrado.
	 * 
	 * @param codigo    código do município
	 * @param municipio nome do município
	 * @param uf        unidade federativa
	 */
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

						case "RR":
							uf = "Roraima";
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
