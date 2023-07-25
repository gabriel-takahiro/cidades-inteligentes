package br.edu.mg.unifal.bcc.sgici.interfaces.principal;

/**
 * Classe com as configurações utilizadas no programa
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 *
 */
public class Configuracao {
	private final int retryDelay = 500;
	private final int retryAttempts = 10;
	private final int connectTestPeriod = 30;
	private final int poolInicialSize;
	private final int poolSize;

	private final int threadPoolSize;
	private final int tempoBusca = 200;
	private final int tempoCalculo = 20;

	private final int connectTimeoutIpea = 10000;
	private final int readTimeoutIpea = 300000;

	private final int connectTimeoutSidraUmMunicipio = 10000;
	private final int readTimeoutSidraUmMunicipio = 20000;
	private final int connectTimeoutSidraTodosMunicipios = 10000;
	private final int readTimeoutSidraTodosMunicipios = 500000;

	/**
	 * Construtor da classe Configuracao que recebe a quantidade de processadores disponíveis.
	 * 
	 * @param quantidadeNucleos                   quantidade de núcleos da máquina do usuário
	 */
	public Configuracao(int quantidadeNucleos) {
		this.poolInicialSize = quantidadeNucleos;
		this.poolSize = quantidadeNucleos * 2;
		this.threadPoolSize = quantidadeNucleos;
	}

	/**
	 * Método que retorna o tempo de espera entre as tentativas de conexão.
	 * 
	 * @return tempo de espera entre as tentativas de conexão
	 */
	public int getRetryDelay() {
		return retryDelay;
	}

	/**
	 * Método que retorna o número máximo de tentativas de conexão antes de falhar.
	 * 
	 * @return número máximo de tentativas de conexão antes de falhar
	 */
	public int getRetryAttempts() {
		return retryAttempts;
	}

	/**
	 * Método que retorna o período de tempo entre as checagens de conexão.
	 * 
	 * @return período de tempo entre as checagens de conexão
	 */
	public int getConnectTestPeriod() {
		return connectTestPeriod;
	}

	/**
	 * Método que retorna o tamanho inicial da pool de conexões
	 * 
	 * @return tamanho inicial da pool de conexões
	 */
	public int getPoolInicialSize() {
		return poolInicialSize;
	}

	/**
	 * Método que retorna o tamanho máximo da pool de conexões
	 * 
	 * @return tamanho máximo da pool de conexões
	 */
	public int getPoolSize() {
		return poolSize;
	}

	/**
	 * Método que retorna o tamanho do thread pool
	 * 
	 * @return tamanho do thread pool
	 */
	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	/**
	 * Método que retorna o tempo máximo para realizar a busca das variáveis
	 * 
	 * @return tempo máximo para realizar a busca das variáveis
	 */
	public int getTempoBusca() {
		return tempoBusca;
	}

	/**
	 * Método que retorna o tempo máximo para realizar o cálculo de um indicador
	 * 
	 * @return tempo máximo para realizar o cálculo de um indicador
	 */
	public int getTempoCalculo() {
		return tempoCalculo;
	}

	/**
	 * Método que retorna o tempo limite de conexão com o servidor IPEA
	 * 
	 * @return tempo limite de conexão com o servidor IPEA
	 */
	public int getConnectTimeoutIpea() {
		return connectTimeoutIpea;
	}

	/**
	 * Método que retorna o tempo limite de leitura com o servidor IPEA
	 * 
	 * @return tempo limite de leitura com o servidor IPEA
	 */
	public int getReadTimeoutIpea() {
		return readTimeoutIpea;
	}

	/**
	 * Método que retorna o tempo limite de conexão com o servidor SIDRA (um
	 * município)
	 * 
	 * @return tempo limite de conexão com o servidor SIDRA (um município)
	 */
	public int getConnectTimeoutSidraUmMunicipio() {
		return connectTimeoutSidraUmMunicipio;
	}

	/**
	 * Método que retorna o tempo limite de leitura com o servidor SIDRA (um
	 * município)
	 * 
	 * @return tempo limite de leitura com o servidor SIDRA (um município)
	 */
	public int getReadTimeoutSidraUmMunicipio() {
		return readTimeoutSidraUmMunicipio;
	}

	/**
	 * Método que retorna o tempo limite de conexão com o servidor SIDRA (todos os
	 * municípios)
	 * 
	 * @return tempo limite de conexão com o servidor SIDRA (todos os municípios)
	 */
	public int getConnectTimeoutSidraTodosMunicipios() {
		return connectTimeoutSidraTodosMunicipios;
	}

	/**
	 * Método que retorna o tempo limite de leitura com o servidor SIDRA (todos os
	 * municípios)
	 * 
	 * @return tempo limite de leitura com o servidor SIDRA (todos os municípios)
	 */
	public int getReadTimeoutSidraTodosMunicipios() {
		return readTimeoutSidraTodosMunicipios;
	}
}
