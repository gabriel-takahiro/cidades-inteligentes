package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.CadastradosIndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.CalculoIndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.DataDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.IndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.PossuiVariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.ValorVariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.AbreChaves;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.AbreColchetes;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.AbreParenteses;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.Calculo;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.CompostoCalculo;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.Default;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.Divisao;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.Multiplicacao;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.SequenciaCalculo;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.Soma;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.Subtracao;

/**
 * Classe que representa a tabela indicador do banco de dados.
 * 
 * @author Gabriel Takahiro
 * @version 0.1
 */

public class Indicador {

	private int codigo;
	private String nome;
	private String calculo;
	private String eixo;
	private String tipo_plano;
	private String nome_plano;
	private String descricao;
	private String informacoes;
	private String meta;
	private boolean padrao;

	public Indicador() {
	}

	public Indicador(int codigo, String calculo) {
		this.codigo = codigo;
		this.calculo = calculo;
	}

	public Indicador(int codigo, String nome, String meta, String calculo, boolean padrao) {
		this.codigo = codigo;
		this.nome = nome;
		this.meta = meta;
		this.calculo = calculo;
		this.padrao = padrao;
	}

	public Indicador(int codigo, String nome, String calculo, String eixo, String tipo_plano, String nome_plano,
			String descricao, String informacoes, String meta, boolean padrao) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.calculo = calculo;
		this.eixo = eixo;
		this.tipo_plano = tipo_plano;
		this.nome_plano = nome_plano;
		this.descricao = descricao;
		this.informacoes = informacoes;
		this.meta = meta;
		this.padrao = padrao;
	}

	public void calculaIndicador(List<Variavel> variaveis, int codigo_municipio, ValorVariavelDAO valorVariavelDAO,
			CalculoIndicadorDAO calculoIndicadorDAO, PossuiVariavelDAO possuiVariavelDAO, String data, DataDAO dataDAO,
			boolean recalcular, int valorRetroativo) {

		List<String> seqCalculo = SequenciaCalculo.lista(this.calculo);
		CalculoIndicador indicadorCalculado = new CalculoIndicador("-", "", true);

		try {
			CompostoCalculo compostoCalculo = new CompostoCalculo(variaveis, codigo_municipio, valorVariavelDAO,
					calculoIndicadorDAO, possuiVariavelDAO, data, dataDAO, recalcular, valorRetroativo, seqCalculo, 0,
					0, 0, "", new ArrayList<Integer>(), true);

			Calculo calculoIndicador = new Multiplicacao(new Divisao(
					new Soma(new Subtracao(new AbreParenteses(new AbreColchetes(new AbreChaves(new Default())))))));

			indicadorCalculado = calculoIndicador.calcular(compostoCalculo);

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

	public static void cadastrarIndicador(String nomeIndicador, String metodoCalculo, String eixo, String tipoPlano,
			String nomePlano, String descricao, String informacoesTecnicas, String meta) {

		try {

			CadastradosIndicadorDAO cadastroIndicadorDAO = new CadastradosIndicadorDAO(ConnectionFactory.recuperarConexao());

			cadastroIndicadorDAO.cadastrarIndicador(nomeIndicador, metodoCalculo, eixo, tipoPlano, nomePlano, descricao,
					informacoesTecnicas, meta);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao cadastrar indicador: " + nomeIndicador);
		}
	}

	public static void mostrarIndicadores(JTable tableIndicadores, boolean seleciona) {
		try {

			IndicadorDAO indicadorDAO = new IndicadorDAO(ConnectionFactory.recuperarConexao());

			indicadorDAO.mostrarIndicadores(tableIndicadores, seleciona);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar os indicadores na tabela.");
		}
	}

	public static void mostrarIndicadores(JTable tableIndicadores, List<Indicador> listaIndicadores) {
		try {
			int coluna = 5;

			DefaultTableModel model = (DefaultTableModel) tableIndicadores.getModel();
			String[] nomeColuna = new String[coluna];

			nomeColuna[0] = "Código";
			nomeColuna[1] = "Nome";
			nomeColuna[2] = "Meta";
			nomeColuna[3] = "Método de cálculo";
			nomeColuna[4] = "Indicador padrão";

			model.setColumnIdentifiers(nomeColuna);

			listaIndicadores.forEach(indicador -> {
				String[] linha = { Integer.toString(indicador.codigo), indicador.nome, indicador.meta,
						indicador.calculo, Boolean.toString(indicador.padrao) };
				model.addRow(linha);

				tableIndicadores.setRowSorter(new TableRowSorter<>(model));
			});

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar os indicadores na tabela.");
		}
	}

	public static void mostrarIndicadoresSemResultado(JTable tableIndicadoresSemResultado,
			ArrayList<IndicadoresBuscados> indicadoresNaoValorados, int codigo_municipio, String data) {
		try {

			IndicadorDAO indicadorDAO = new IndicadorDAO(ConnectionFactory.recuperarConexao());

			indicadorDAO.mostrarIndicadoresSemResultado(tableIndicadoresSemResultado, indicadoresNaoValorados,
					codigo_municipio, data);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar os indicadores sem resultado.");
		}
	}

	public static int buscaCodigo(String metodoCalculo) {
		try {

			IndicadorDAO indicadorDAO = new IndicadorDAO(ConnectionFactory.recuperarConexao());

			return indicadorDAO.buscaCodigo(metodoCalculo);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao busca o codigo a partir do metodo.");
		}
	}

	public static Indicador buscarCodigoEMetodo(int codigo_indicador) {
		try {

			IndicadorDAO indicadorDAO = new IndicadorDAO(ConnectionFactory.recuperarConexao());

			Indicador indicador = indicadorDAO.buscarCodigoEMetodo(codigo_indicador);
			return indicador;

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar o codigo e metodo.");
		}
	}

	public static void excluir(List<Indicador> listaIndicadores) {
		try {

			IndicadorDAO indicadorDAO = new IndicadorDAO(ConnectionFactory.recuperarConexao());

			for (Indicador indicador : listaIndicadores) {
				indicadorDAO.excluir(indicador);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static Indicador buscaIndicador(Indicador indicador) {
		try {

			IndicadorDAO indicadorDAO = new IndicadorDAO(ConnectionFactory.recuperarConexao());

			return indicadorDAO.buscaIndicador(indicador);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar o indicador: " + indicador.getCodigo());
		}
	}

	public static void atualizarIndicador(int codigo_indicador, String nome_indicador, String metodo_calculo,
			String eixo, String tipo_plano, String nome_plano, String descricao, String informacoes_tecnicas,
			String meta, boolean padrao) {
		try {

			CadastradosIndicadorDAO cadastroIndicadorDAO = new CadastradosIndicadorDAO(ConnectionFactory.recuperarConexao());

			cadastroIndicadorDAO.atualizarIndicador(codigo_indicador, nome_indicador, metodo_calculo, eixo, tipo_plano,
					nome_plano, descricao, informacoes_tecnicas, meta, padrao);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao atualizar o indicador: " + codigo_indicador);
		}
	}
	
	public String getNome() {
		return nome;
	}

	public String getEixo() {
		return eixo;
	}

	public String getTipo_plano() {
		return tipo_plano;
	}

	public String getNome_plano() {
		return nome_plano;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getInformacoes() {
		return informacoes;
	}

	public String getMeta() {
		return meta;
	}

	public boolean isPadrao() {
		return padrao;
	}
	
	public String getCalculo() {
		return calculo;
	}

	public int getCodigo() {
		return codigo;
	}

//	public static ArrayList<Indicador> verificaLista(ArrayList<Indicador> listaIndicadoresSelecionados, Connection ConnectionFactory.recuperarConexao()) {
//		try {
//			IndicadorDAO indicadorDAO = new IndicadorDAO(ConnectionFactory.recuperarConexao());
//			return indicadorDAO.verificaLista(listaIndicadoresSelecionados);
//		}catch (Exception e) {
//			System.out.println(e);
//			return null;
//		}
//	}

}
