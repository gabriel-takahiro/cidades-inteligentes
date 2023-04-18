package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.testes;

public class TesteCalculoIndicador {

	/*public static String testeCalculoIndicador(List<Variavel> variaveis, String metodoCalculo, int codigo_municipio, String data, boolean recalcular) {
		new ConnectionFactory("postgres", "senha123", "ic");
		ConnectionFactory.iniciarConexao();
		try {
			Connection connection = ConnectionFactory.recuperarConexao();
			ValorVariavelDAO valorVariavelDAO = new ValorVariavelDAO(connection);
			CalculoIndicadorDAO calculoIndicadorDAO = new CalculoIndicadorDAO(connection);
			PossuiVariavelDAO possuiVariavelDAO = new PossuiVariavelDAO(connection);
			DataDAO dataDAO = new DataDAO(connection);
			
			List<String> seqCalculo = SequenciaCalculo.lista(metodoCalculo);
			
			CompostoCalculo compostoCalculo = new CompostoCalculo(variaveis, codigo_municipio, valorVariavelDAO,
					calculoIndicadorDAO, possuiVariavelDAO, data, dataDAO, recalcular, 0, seqCalculo, 0,
					0, 0, "", new ArrayList<Integer>(), true);
			
			Calculo calculoIndicador = new Multiplicacao(new Divisao(
					new Soma(new Subtracao(new AbreParenteses(new AbreColchetes(new AbreChaves(new Default())))))));
			
			CalculoIndicador indicadorCalculado = calculoIndicador.calcular(compostoCalculo);
			
			System.out.println("O resultado para o indicador com método de cálculo: " + metodoCalculo + "é: " + indicadorCalculado.getResultado() + " e as datas das variáveis são: " + indicadorCalculado.getData_variaveis());
			return indicadorCalculado.getResultado();

		} catch (Exception e) {
			System.out.println("Erro em algum lugar. " + e);
			throw new RuntimeException("Erro");
		}
	}*/
}
