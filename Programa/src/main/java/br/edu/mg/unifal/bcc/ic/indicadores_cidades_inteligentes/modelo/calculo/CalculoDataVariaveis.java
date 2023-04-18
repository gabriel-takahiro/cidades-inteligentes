package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo;

public class CalculoDataVariaveis {
	private String calculo;
	private String dataVariaveis;
	private boolean oficial;
	
	public CalculoDataVariaveis(String calculo, String dataVariaveis, boolean oficial) {
		this.calculo = calculo;
		this.dataVariaveis = dataVariaveis;
		this.oficial = oficial;
	}
	
	public boolean isOficial() {
		return oficial;
	}

	public void setOficial(boolean oficial) {
		this.oficial = oficial;
	}

	public String getCalculo() {
		return calculo;
	}
	
	public void setCalculo(String calculo) {
		this.calculo = calculo;
	}
	
	public String getDataVariaveis() {
		return dataVariaveis;
	}
	
	public void setDataVariaveis(String dataVariaveis) {
		this.dataVariaveis = dataVariaveis;
	}
	
}
