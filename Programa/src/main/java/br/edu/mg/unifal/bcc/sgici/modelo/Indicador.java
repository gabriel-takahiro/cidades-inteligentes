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
package br.edu.mg.unifal.bcc.sgici.modelo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import br.edu.mg.unifal.bcc.sgici.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.CadastradosIndicadorDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.CalculoIndicadorDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.DataDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.IndicadorDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.PossuiVariavelDAO;
import br.edu.mg.unifal.bcc.sgici.modelo.dao.ValorVariavelDAO;

/**
 * Classe que representa a tabela indicador do banco de dados.
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */
public class Indicador {

	private int codigo;
	private String nome;
	private String metodo_calculo;
	private String eixo;
	private String tipo_plano;
	private String nome_plano;
	private String descricao;
	private String informacoes;
	private String meta;
	private boolean padrao;

	/**
	 * Construtor padrão
	 */
	public Indicador() {
	}

	/**
	 * 
	 * @param codigo         código do indicador
	 * @param metodo_calculo método de cálculo
	 */
	public Indicador(int codigo, String metodo_calculo) {
		this.codigo = codigo;
		this.metodo_calculo = metodo_calculo;
	}

	/**
	 * 
	 * @param codigo         código do indicador
	 * @param nome           nome do indicador
	 * @param descricao		 descrição do indicador
	 * @param meta           meta do indicador
	 * @param metodo_calculo método de cálculo
	 * @param padrao         true caso o indicador seja padrão
	 */
	public Indicador(int codigo, String nome, String descricao, String meta, String metodo_calculo, boolean padrao) {
		this.codigo = codigo;
		this.nome = nome;
		this.descricao = descricao;
		this.meta = meta;
		this.metodo_calculo = metodo_calculo;
		this.padrao = padrao;
	}

	/**
	 * 
	 * @param codigo         código do indicador
	 * @param nome           nome do indicador
	 * @param metodo_calculo método de cálculo
	 * @param eixo           eixo do indicador
	 * @param tipo_plano     tipo de plano
	 * @param nome_plano     nome do plano
	 * @param descricao      descrição do indicador
	 * @param informacoes    informações técnicas do indicador
	 * @param meta           meta do indicador
	 * @param padrao         true caso o indicador seja padrão
	 */
	public Indicador(int codigo, String nome, String metodo_calculo, String eixo, String tipo_plano, String nome_plano,
			String descricao, String informacoes, String meta, boolean padrao) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.metodo_calculo = metodo_calculo;
		this.eixo = eixo;
		this.tipo_plano = tipo_plano;
		this.nome_plano = nome_plano;
		this.descricao = descricao;
		this.informacoes = informacoes;
		this.meta = meta;
		this.padrao = padrao;
	}

	/**
	 * Realiza o cálculo do indicador a partir do método de cálculo e dos parâmetros
	 * fornecidos
	 * 
	 * @param variaveis           lista de variáveis
	 * @param codigo_municipio    código do município
	 * @param valorVariavelDAO    conexão com a tabela valor_variavel do banco de
	 *                            dados
	 * @param calculoIndicadorDAO conexão com a tabela calculo_indicador do banco de
	 *                            dados
	 * @param possuiVariavelDAO   conexão com a tabela possui_variavel do banco de
	 *                            dados
	 * @param data                ano
	 * @param dataDAO             conexão com a tabela data do banco de dados
	 * @param recalcular          true caso seja necessário recalcular o indicador
	 * @param valorRetroativo     anos a mais para a busca retroativa das variáveis
	 */
	public void calculaIndicador(List<Variavel> variaveis, int codigo_municipio, ValorVariavelDAO valorVariavelDAO,
			CalculoIndicadorDAO calculoIndicadorDAO, PossuiVariavelDAO possuiVariavelDAO, String data, DataDAO dataDAO,
			boolean recalcular, int valorRetroativo) {

		CalculoIndicador indicadorCalculado = new CalculoIndicador("-", "", true);

		try {
			if (recalcular) {
				if (calculoIndicadorDAO.possuiResultado(this.codigo, codigo_municipio, data)) {
					calculoIndicadorDAO.updateResultado(indicadorCalculado.getResultado(), this.codigo,
							codigo_municipio, data, indicadorCalculado.getData_variaveis(),
							indicadorCalculado.isValor_oficial());
					return;
				}
				calculoIndicadorDAO.insereResultado(this.codigo, codigo_municipio, data,
						indicadorCalculado.getResultado(), indicadorCalculado.getData_variaveis(),
						indicadorCalculado.isValor_oficial());
				return;
			}
			calculoIndicadorDAO.insereResultado(this.codigo, codigo_municipio, data, indicadorCalculado.getResultado(),
					indicadorCalculado.getData_variaveis(), indicadorCalculado.isValor_oficial());

		} catch (Exception e) {
			if (recalcular) {
				if (calculoIndicadorDAO.possuiResultado(this.codigo, codigo_municipio, data)) {
					dataDAO.atualizaData(data);
					calculoIndicadorDAO.updateResultado("-", this.codigo, codigo_municipio, data,
							indicadorCalculado.getData_variaveis(), false);
					return;
				}
				dataDAO.atualizaData(data);
				calculoIndicadorDAO.insereResultado(this.codigo, codigo_municipio, data, "-",
						indicadorCalculado.getData_variaveis(), false);
				return;
			} else {
				dataDAO.atualizaData(data);
				calculoIndicadorDAO.insereResultado(this.codigo, codigo_municipio, data, "-",
						indicadorCalculado.getData_variaveis(), false);
				return;
			}
		}

	}

	/**
	 * Cadastra o indicador, a partir das informações passadas como parâmetro, no
	 * banco de dados
	 * 
	 * @param nome_indicador       nome do indicador
	 * @param metodo_calculo       método de cálculo do indicador
	 * @param eixo                 eixo do indicador
	 * @param tipo_plano           tipo de plano
	 * @param nome_plano           nome do plano
	 * @param descricao            descrição do indicador
	 * @param informacoes_tecnicas informações técnicas do indicador
	 * @param meta                 meta do indicador
	 */
	public static void cadastrarIndicador(String nome_indicador, String metodo_calculo, String eixo, String tipo_plano,
			String nome_plano, String descricao, String informacoes_tecnicas, String meta) {

		try (Connection connection = ConnectionFactory.recuperarConexao();) {

			CadastradosIndicadorDAO cadastroIndicadorDAO = new CadastradosIndicadorDAO(connection);

			cadastroIndicadorDAO.cadastrarIndicador(nome_indicador, metodo_calculo, eixo, tipo_plano, nome_plano,
					descricao, informacoes_tecnicas, meta);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Preenche uma tabela, passada como parâmetro, com todos os indicadores
	 * 
	 * @param tableIndicadores tabela a ser utilizada
	 * @param seleciona        true caso seja necessário uma coluna extra que
	 *                         permite selecionar os indicadores
	 */
	public static void mostrarIndicadores(JTable tableIndicadores, boolean seleciona) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {

			IndicadorDAO indicadorDAO = new IndicadorDAO(connection);

			indicadorDAO.mostrarIndicadores(tableIndicadores, seleciona);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar os indicadores na tabela.");
		}
	}

	/**
	 * Preenche uma tabela apenas com os indicadores presentes na lista de
	 * indicadores passada por parâmetro
	 * 
	 * @param tableIndicadores tabela a ser utilizada
	 * @param listaIndicadores lista de indicadores selecionados
	 */
	public static void mostrarIndicadores(JTable tableIndicadores, List<Integer> listaIndicadores) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			if(listaIndicadores.isEmpty()) {
				throw new RuntimeException("Não há indicadores");
			}
			
			IndicadorDAO indicadorDAO = new IndicadorDAO(connection);

			List<Indicador> listaIndicadoresCompleto = indicadorDAO.buscarIndicadorCompleto(listaIndicadores);

			int coluna = 6;

			DefaultTableModel model = (DefaultTableModel) tableIndicadores.getModel();
			String[] nomeColuna = new String[coluna];

			nomeColuna[0] = "Código";
			nomeColuna[1] = "Nome";
			nomeColuna[2] = "Descrição";
			nomeColuna[3] = "Meta";
			nomeColuna[4] = "Método de cálculo";
			nomeColuna[5] = "Indicador padrão";

			model.setColumnIdentifiers(nomeColuna);

			listaIndicadoresCompleto.forEach(indicador -> {
				String[] linha = { Integer.toString(indicador.codigo), indicador.nome, indicador.descricao, indicador.meta,
						indicador.metodo_calculo, Boolean.toString(indicador.padrao) };
				model.addRow(linha);

				tableIndicadores.setRowSorter(new TableRowSorter<>(model));
			});

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar os indicadores na tabela.");
		}
	}

	/**
	 * Preenche uma tabela apenas com os indicadores que não possuem resultado
	 * 
	 * @param tableIndicadoresSemResultado tabela a ser utilizar
	 * @param indicadoresNaoValorados      lista de indicadores sem resultado
	 * @param codigo_municipio             código do município
	 * @param data                         ano
	 */
	public static void mostrarIndicadoresSemResultado(JTable tableIndicadoresSemResultado,
			ArrayList<IndicadoresBuscados> indicadoresNaoValorados, int codigo_municipio, String data) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {

			IndicadorDAO indicadorDAO = new IndicadorDAO(connection);

			indicadorDAO.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados,
					codigo_municipio, data);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar os indicadores sem resultado.");
		}
	}

	/**
	 * Busca o código do indicador a partir do método de cálculo dele
	 * 
	 * @param metodoCalculo método de cálculo
	 * @return código do indicador
	 */
	public static int buscaCodigo(String metodoCalculo) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {

			IndicadorDAO indicadorDAO = new IndicadorDAO(connection);

			return indicadorDAO.buscaCodigo(metodoCalculo);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao busca o codigo a partir do metodo.");
		}
	}

	/**
	 * Busca o código e o método de cálculo do indicador a partir do código dele
	 * 
	 * @param codigo_indicador código do indicador
	 * @return indicador contendo o código e o método de cálculo
	 */
	public static Indicador buscarCodigoEMetodo(int codigo_indicador) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {

			IndicadorDAO indicadorDAO = new IndicadorDAO(connection);

			Indicador indicador = indicadorDAO.buscarCodigoEMetodo(codigo_indicador);
			return indicador;

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar o codigo e metodo.");
		}
	}

	/**
	 * Exclui uma lista de indicadores do banco de dados
	 * 
	 * @param listaIndicadores lista de indicadores a serem excluídos
	 */
	public static void excluir(List<Integer> listaIndicadores) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {

			IndicadorDAO indicadorDAO = new IndicadorDAO(connection);

			for (Integer codigo : listaIndicadores) {
				indicadorDAO.excluir(codigo);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Busca um indicador contendo todas as informações
	 * 
	 * @param indicador indicador com apenas algumas informações
	 * @return indicador com todas as informações
	 */
	public static Indicador buscaIndicador(Indicador indicador) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {

			IndicadorDAO indicadorDAO = new IndicadorDAO(connection);

			return indicadorDAO.buscaIndicador(indicador);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar o indicador: " + indicador.getCodigo());
		}
	}

	/**
	 * Atualiza um indicador no banco de dados com as informações passadas por
	 * parâmetro
	 * 
	 * @param codigo_indicador     código do indicador
	 * @param nome_indicador       nome do indicador
	 * @param metodo_calculo       método de cálculo do indicador
	 * @param eixo                 eixo do indicador
	 * @param tipo_plano           tipo de plano do indicador
	 * @param nome_plano           nome do plano
	 * @param descricao            descrição do indicador
	 * @param informacoes_tecnicas informações técnicas do indicador
	 * @param meta                 meta do indicador
	 * @param padrao               true caso o indicador seja padrão
	 */
	public static void atualizarIndicador(int codigo_indicador, String nome_indicador, String metodo_calculo,
			String eixo, String tipo_plano, String nome_plano, String descricao, String informacoes_tecnicas,
			String meta, boolean padrao) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {

			CadastradosIndicadorDAO cadastroIndicadorDAO = new CadastradosIndicadorDAO(connection);

			cadastroIndicadorDAO.atualizarIndicador(codigo_indicador, nome_indicador, metodo_calculo, eixo, tipo_plano,
					nome_plano, descricao, informacoes_tecnicas, meta, padrao);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * Atualiza o método de cálculo do indicador
	 * 
	 * @param codigo_indicador código do indicador
	 * @param metodo_calculo   método de cálculo
	 */
	public static void atualizarMetodoCalculo(int codigo_indicador, String metodo_calculo) {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {

			CadastradosIndicadorDAO cadastroIndicadorDAO = new CadastradosIndicadorDAO(connection);

			cadastroIndicadorDAO.atualizarMetodoCalculo(codigo_indicador, metodo_calculo);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao atualizar o indicador: " + codigo_indicador);
		}
	}

	/**
	 * Padroniza o método de cálculo de todos os indicadores importados
	 */
	public void padronizarMetodoCalculo() {
		try (Connection connection = ConnectionFactory.recuperarConexao();) {
			new IndicadorDAO(connection).padronizarMetodoCalculo();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * @return nome do indicador
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * 
	 * @return eixo do indicador
	 */
	public String getEixo() {
		return eixo;
	}

	/**
	 * 
	 * @return tipo de plano do indicador
	 */
	public String getTipo_plano() {
		return tipo_plano;
	}

	/**
	 * 
	 * @return nome do plano
	 */
	public String getNome_plano() {
		return nome_plano;
	}

	/**
	 * 
	 * @return descrição do indicador
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * 
	 * @return informações técnicas do indicador
	 */
	public String getInformacoes() {
		return informacoes;
	}

	/**
	 * 
	 * @return meta do indicador
	 */
	public String getMeta() {
		return meta;
	}

	/**
	 * 
	 * @return true caso o indicador seja padrão
	 */
	public boolean isPadrao() {
		return padrao;
	}

	/**
	 * 
	 * @return método de cálculo do indicador
	 */
	public String getCalculo() {
		return metodo_calculo;
	}

	/**
	 * 
	 * @return código do indicador
	 */
	public int getCodigo() {
		return codigo;
	}

}
