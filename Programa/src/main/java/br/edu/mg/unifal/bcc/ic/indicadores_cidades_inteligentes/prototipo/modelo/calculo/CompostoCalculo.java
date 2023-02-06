package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.calculo;

import java.util.ArrayList;
import java.util.List;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.CalculoIndicadorDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.DataDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.PossuiVariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.dao.ValorVariavelDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo.Variavel;

public class CompostoCalculo {

	private List<Variavel> variaveis;
	private int codigo_municipio;
	private ValorVariavelDAO valorVariavelDAO;
	private CalculoIndicadorDAO calculoIndicadorDAO;
	private PossuiVariavelDAO possuiVariavelDAO;
	private String data;
	private DataDAO dataDAO;
	private boolean recalcular;
	private int valorRetroativo;
	private List<String> seqCalculo;
	private double calculo;
	private double valor1;
	private int posicao;
	private String data_variaveis;
	private ArrayList<Integer> codigo_variaveis;
	private boolean valor_oficial;

	public CompostoCalculo(List<Variavel> variaveis, int codigo_municipio, ValorVariavelDAO valorVariavelDAO,
			CalculoIndicadorDAO calculoIndicadorDAO, PossuiVariavelDAO possuiVariavelDAO, String data, DataDAO dataDAO,
			boolean recalcular, int valorRetroativo, List<String> seqCalculo, double calculo, double valor1,
			int posicao, String data_variaveis, ArrayList<Integer> codigo_variaveis, boolean valor_oficial) {
		this.variaveis = variaveis;
		this.codigo_municipio = codigo_municipio;
		this.valorVariavelDAO = valorVariavelDAO;
		this.calculoIndicadorDAO = calculoIndicadorDAO;
		this.possuiVariavelDAO = possuiVariavelDAO;
		this.data = data;
		this.dataDAO = dataDAO;
		this.recalcular = recalcular;
		this.valorRetroativo = valorRetroativo;
		this.seqCalculo = seqCalculo;
		this.calculo = calculo;
		this.valor1 = valor1;
		this.posicao = posicao;
		this.data_variaveis = data_variaveis;
		this.codigo_variaveis = codigo_variaveis;
		this.valor_oficial = valor_oficial;
	}
	
	public void setDataVariaveis(int codigo_variavel, String data) {
		try {
			if (this.codigo_variaveis.contains(codigo_variavel)) {
				return;
			}
			if (this.data_variaveis.equals("")) {
				this.data_variaveis = codigo_variavel + "/" + data;
				this.codigo_variaveis.add(codigo_variavel);
				return;
			}
			this.data_variaveis = this.data_variaveis + "/" + codigo_variavel + "/" + data;
			this.codigo_variaveis.add(codigo_variavel);
		} catch (Exception e) {
			System.out.println("Erro setDataVariaveis " + e);
		}
	}

	public void setValor_oficial(boolean valor_oficial) {
		this.valor_oficial = valor_oficial;
	}

	public boolean isValor_oficial() {
		return valor_oficial;
	}

	public List<Variavel> getVariaveis() {
		return variaveis;
	}

	public int getCodigo_municipio() {
		return codigo_municipio;
	}

	public ValorVariavelDAO getValorVariavelDAO() {
		return valorVariavelDAO;
	}

	public CalculoIndicadorDAO getCalculoIndicadorDAO() {
		return calculoIndicadorDAO;
	}

	public PossuiVariavelDAO getPossuiVariavelDAO() {
		return possuiVariavelDAO;
	}

	public String getData() {
		return data;
	}

	public DataDAO getDataDAO() {
		return dataDAO;
	}

	public boolean isRecalcular() {
		return recalcular;
	}

	public int getValorRetroativo() {
		return valorRetroativo;
	}

	public List<String> getSeqCalculo() {
		return seqCalculo;
	}

	public double getCalculo() {
		return calculo;
	}

	public double getValor1() {
		return valor1;
	}

	public int getPosicao() {
		return posicao;
	}
	
	public String getData_variaveis() {
		return data_variaveis;
	}

	public void setCalculo(double calculo) {
		this.calculo = calculo;
	}

	public void setValor1(double valor1) {
		this.valor1 = valor1;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

}
