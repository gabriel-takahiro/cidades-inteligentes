package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.banco_dados;

public class ResultadoOperacao {
	
	private String operacao;
	private String resultado;
	
	public ResultadoOperacao(String operacao, String resultado) {
		this.operacao = operacao;
		this.resultado = resultado;
	}
	public String getOperacao() {
		return operacao;
	}
	public String getResultado() {
		return resultado;
	}	
	
}
