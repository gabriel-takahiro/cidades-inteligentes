package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.prototipo.modelo;

public class CalculoPrioridadeSomaSub {
	private double calculo;
	private int posicao;
	
	public CalculoPrioridadeSomaSub(double calculo, int posicao) {
		this.calculo = calculo;
		this.posicao = posicao;
	}
	
	public double getCalculo() {
		return calculo;
	}
	public int getPosicao() {
		return posicao;
	}
	
}
