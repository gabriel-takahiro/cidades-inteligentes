package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.busca;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.Variavel;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo.calculo.ParametrosBuscaValorVariavel;

public interface Busca {
	void buscaUmMunicipio(Variavel variavel, ParametrosBuscaValorVariavel parametros);
	
	void buscarTodosMunicipios(Variavel variavel, ParametrosBuscaValorVariavel parametros);
}
