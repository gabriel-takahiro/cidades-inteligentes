package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo;

public class ParametrosBuscaValorVariavel {
	private String data;
	private int codigoMunicipio;
	private int anosRetroativos;
	private boolean recalcular;

	public ParametrosBuscaValorVariavel(String data, int anosRetroativos, boolean recalcular) {
		this.data = data;
		this.anosRetroativos = anosRetroativos;
		this.recalcular = recalcular;
	}
	
	public ParametrosBuscaValorVariavel(String data, int codigoMunicipio, int anosRetroativos, boolean recalcular) {
		this.data = data;
		this.codigoMunicipio = codigoMunicipio;
		this.anosRetroativos = anosRetroativos;
		this.recalcular = recalcular;
	}

	public String getData() {
		return data;
	}

	public int getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public int getAnosRetroativos() {
		return anosRetroativos;
	}
		
	public boolean isRecalcular() {
		return recalcular;
	}
	
}
