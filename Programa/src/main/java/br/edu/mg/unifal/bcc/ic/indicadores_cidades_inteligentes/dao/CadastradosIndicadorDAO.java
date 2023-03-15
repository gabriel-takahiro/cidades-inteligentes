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

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.internas.confirmacao.JanelaMensagem;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.interfaces.principal.JanelaPrincipal;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.PossuiVariavel;

/**
 * Classe que faz conexão com a tabela cadastrados.indicadores.
 * 
 * @author Gabriel Takahiro
 * @version 0.2
 */

public class CadastradosIndicadorDAO {

	private Connection connection;

	/**
	 * 
	 * @param connection conexão com a banco de dados
	 */
	public CadastradosIndicadorDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Realizar o cadastro de um indicador no banco de dados
	 * @param nomeIndicador nome do indicador
	 * @param metodoCalculo método de cálculo do indicador
	 * @param eixo eixo que o indicador pertence
	 * @param tipoPlano tipo de plano do indicador
	 * @param nomePlano nome do plano do indicador
	 * @param descricao descrição do indicador
	 * @param informacoesTecnicas informações técnicas sobre o indicador
	 * @param meta meta na qual o indicador pertence
	 */
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
			throw new RuntimeException();
		}
	}

	/**
	 * Faz a atualização de um indicador no banco de dados
	 * @param codigo_indicador código do indicador
	 * @param nome_indicador nome do indicador
	 * @param metodo_calculo método de cálculo do indicador
	 * @param eixo eixo do indicador
	 * @param tipo_plano tipo de plano do indicador
	 * @param nome_plano  nome do plano do indicador
	 * @param descricao descrição do indicador
	 * @param informacoes_tecnicas informações técnicas do indicador
	 * @param meta meta do indicador
	 * @param padrao indicador é padrão, ou seja, veio junto com o programa?
	 */
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
			new JanelaMensagem("Falha ao atualizar o indicador: " + codigo_indicador + "\nNão é permitido ter dois métodos de cálculo idênticos");
			throw new RuntimeException("Falha no cadastro.");
		}
	}

	/**
	 * Faz a atualização de um indicador no banco de dados
	 * @param codigo_indicador código do indicador
	 * @param metodo_calculo novo método de cálculo
	 */
	public void atualizarMetodoCalculo(int codigo_indicador, String metodo_calculo) {
		String sql = "UPDATE indicador SET metodo_calculo = ? WHERE codigo_indicador = ?";
		
		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.setString(1, metodo_calculo);
			pstm.setInt(2, codigo_indicador);
			pstm.execute();
		}catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}
}
