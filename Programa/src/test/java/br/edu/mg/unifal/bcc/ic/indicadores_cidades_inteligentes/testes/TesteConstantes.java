package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.testes;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;

class TesteConstantes {

	@Test
	void testIndicador1() {
		List<Variavel> variaveis = new ArrayList<Variavel>();

		Variavel variavel1 = new Variavel(1, "Sidra",
				"População com 10 anos ou mais e recebe até 1/2 salário mínimo ",
				"1384/n1/all/v/allxp/p/all/c11570/92973", "Decenal");

		Variavel variavel2 = new Variavel(224, "Sidra", "População residente com 10 anos ou mais",
				"1384/n1/all/v/allxp/p/all/c11570/0", "Decenal");
		variaveis.add(variavel1);
		variaveis.add(variavel2);
		
		String resultado = TesteCalculoIndicador.testeCalculoIndicador(variaveis, "1/224x[100]", 3518800, "2010", true);
		assertEquals("2,09", resultado);
	}
	
	@Test
	void testeConstanteMultDiv() {
		List<Variavel> variaveis = new ArrayList<Variavel>();
		
		String resultado = TesteCalculoIndicador.testeCalculoIndicador(variaveis, "[2] / [1] x [6] / [4] x [5]",3518800, "2010", true);
		assertEquals("15,00", resultado);
	}
	
	@Test
	void testeConstanteMultDivParenteses() {
		List<Variavel> variaveis = new ArrayList<Variavel>();
		
		String resultado = TesteCalculoIndicador.testeCalculoIndicador(variaveis, "[2] / ([1] x [6] / [4]) x [5]",3518800, "2010", true);
		assertEquals("6,67", resultado);
	}
	
	@Test
	void testeConstanteSomaComeco() {
		List<Variavel> variaveis = new ArrayList<Variavel>();
		
		String resultado = TesteCalculoIndicador.testeCalculoIndicador(variaveis, "[2] + [1]",3518800, "2010", true);
		assertEquals("3,00", resultado);
	}
	
	@Test
	void testeConstanteSomaComecoComMult() {
		List<Variavel> variaveis = new ArrayList<Variavel>();
		
		String resultado = TesteCalculoIndicador.testeCalculoIndicador(variaveis, "[2] + [5] x [3] x [2]",3518800, "2010", true);
		assertEquals("32,00", resultado);
	}
	
	@Test
	void testeConstanteSomaComeco2() {
		List<Variavel> variaveis = new ArrayList<Variavel>();
		
		String resultado = TesteCalculoIndicador.testeCalculoIndicador(variaveis, "[2] x [2] + [1] + [5] x [2] + [7] x [5]",3518800, "2010", true);
		assertEquals("50,00", resultado);
	}//Problema que está olhando só para o que vem depois, não está fazendo 2 + 1 no calculo de [2] + [1] + [5] resultado 5
	//Que bom, parece mais fácil de resolver sempre que tem algo depois de uma soma, tudo que foi feito antes será esquecido, 
	//vide exemplo [2] x [2] + [1] + [5] x [2] que obteve como resultado 10 para ter certeza agr testar com um mais depois e o valor 1 no fim
	//[2] x [2] + [1] + [5] x [2] + [7] x [5] x [2] valor igual a 10, engraçado que o resultado ignorou a multiplicação com o 7
	
	@Test
	void testeConstanteSomaComeco3() {
		List<Variavel> variaveis = new ArrayList<Variavel>();
		
		String resultado = TesteCalculoIndicador.testeCalculoIndicador(variaveis, "[2] x [2] + [1] + [5] x [2]",3518800, "2010", true);
		assertEquals("15,00", resultado);
	}//Problema que está olhando só para o que vem depois, não está fazendo 2 + 1 no calculo de [2] + [1] + [5] resultado 5
	//Que bom, parece mais fácil de resolver sempre que tem algo depois de uma soma, tudo que foi feito antes será esquecido, 
	//vide exemplo [2] x [2] + [1] + [5] x [2] que obteve como resultado 10 para ter certeza agr testar com um mais depois e o valor 1 no fim
	//[2] x [2] + [1] + [5] x [2] + [7] x [5] x [2] valor igual a 10, engraçado que o resultado ignorou a multiplicação com o 7
	
	@Test
	void testIndicadorIndicadorSomaComeco() {
		List<Variavel> variaveis = new ArrayList<Variavel>();

		Variavel variavel1 = new Variavel(1, "Sidra",
				"População com 10 anos ou mais e recebe até 1/2 salário mínimo ",
				"1384/n1/all/v/allxp/p/all/c11570/92973", "Decenal");

		Variavel variavel2 = new Variavel(224, "Sidra", "População residente com 10 anos ou mais",
				"1384/n1/all/v/allxp/p/all/c11570/0", "Decenal");
		variaveis.add(variavel1);
		variaveis.add(variavel2);
		//1 = 21650;  224 = 1035020;
		
		String resultado = TesteCalculoIndicador.testeCalculoIndicador(variaveis, "1 x 1 + 1 + (224 x 1 - 1 x 1)", 3518800, "2010", true);
		assertEquals("22408204650,00", resultado);
		//variável 1 = 21650  ; variável 224 = 1035020
	}
	
	@Test
	void testIndicadorIndicadorSomaComeco2() {
		List<Variavel> variaveis = new ArrayList<Variavel>();

		Variavel variavel1 = new Variavel(1, "Sidra",
				"População com 10 anos ou mais e recebe até 1/2 salário mínimo ",
				"1384/n1/all/v/allxp/p/all/c11570/92973", "Decenal");

		Variavel variavel2 = new Variavel(224, "Sidra", "População residente com 10 anos ou mais",
				"1384/n1/all/v/allxp/p/all/c11570/0", "Decenal");
		variaveis.add(variavel1);
		variaveis.add(variavel2);
		//1 = 21650;  224 = 1035020;
		
		String resultado = TesteCalculoIndicador.testeCalculoIndicador(variaveis, "1 + 1 x 224", 3518800, "2010", true);
		assertEquals("22408204650,00", resultado);
		//variável 1 = 21650  ; variável 224 = 1035020
	}
	
	@Test
	void testeIdentico() {
		List<Variavel> variaveis = new ArrayList<Variavel>();
		
		String resultado = TesteCalculoIndicador.testeCalculoIndicador(variaveis, "[2] x [3] + [4] + [5] x [6] - [7] x [8]",3518800, "2010", true);
		assertEquals("-16,00", resultado);
	}
	
	@Test
	void testeIdentico2() {
		List<Variavel> variaveis = new ArrayList<Variavel>();
		
		String resultado = TesteCalculoIndicador.testeCalculoIndicador(variaveis, "[2] + ([3] + [4] + [5] x [6] x [7]) x [8]",3518800, "2010", true);
		assertEquals("1738,00", resultado);
	}

}
