package br.edu.mg.unifal.bcc.sgici.interfaces.principal;

/**
 * <p>
 * Configurações pré-definidas para acesso a serviços externos.
 * </p>
 * <p>
 * Possui três opções de configuração: BAIXO, MEDIO e ALTO.
 * </p>
 * <p>
 * Cada configuração define valores para os seguintes parâmetros:
 * </p>
 * <p>
 * retryDelay, retryAttempts, connectTestPeriod, poolInicialSize, poolSize,
 * threadPoolSize, tempoBusca, tempoCalculo, connectTimeoutIpea,
 * readTimeoutIpea, connectTimeoutSidraUmMunicipio, readTimeoutSidraUmMunicipio,
 * connectTimeoutSidraTodosMunicipios e readTimeoutSidraTodosMunicipios.
 * </p>
 * 
 * @author Gabriel Takahiro
 * @version 0.4
 *
 */
public enum Configuracao {
	/**
	 * Configuração de baixo desempenho
	 */
	BAIXO(1000, 10, 30, 2, 15, 2, 180, 15, 10000, 300000, 10000, 20000, 10000, 500000),
	/**
	 * Configuração de médio desempenho
	 */
	MEDIO(500, 10, 30, 5, 30, 5, 150, 10, 7500, 240000, 7500, 15000, 7500, 400000),
	/**
	 * Configuração de alto desempenho
	 */
	ALTO(100, 10, 30, 10, 50, 10, 120, 5, 5000, 180000, 5000, 10000, 5000, 300000);

	private final int retryDelay;
	private final int retryAttempts;
	private final int connectTestPeriod;
	private final int poolInicialSize;
	private final int poolSize;

	private final int threadPoolSize;
	private final int tempoBusca;
	private final int tempoCalculo;

	private final int connectTimeoutIpea;
	private final int readTimeoutIpea;

	private final int connectTimeoutSidraUmMunicipio;
	private final int readTimeoutSidraUmMunicipio;
	private final int connectTimeoutSidraTodosMunicipios;
	private final int readTimeoutSidraTodosMunicipios;

	/**
	 * Construtor da classe Configuracao que recebe diversos parâmetros relacionados
	 * a conexão e timeout.
	 * 
	 * @param retryDelay                         tempo de espera entre as tentativas
	 *                                           de conexão
	 * @param retryAttempts                      número de tentativas de conexão que
	 *                                           devem ser realizadas em caso de
	 *                                           falha
	 * @param connectTestPeriod                  período de tempo entre as checagens
	 *                                           de conexão
	 * @param poolInicialSize                    tamanho inicial da pool de conexões
	 * @param poolSize                           tamanho máximo da pool de conexões
	 * @param threadPoolSize                     tamanho do pool de threads
	 * @param tempoBusca                         tempo máximo para uma busca das
	 *                                           variáveis
	 * @param tempoCalculo                       tempo máximo para o cálculo de um
	 *                                           indicador
	 * @param connectTimeoutIpea                 tempo limite para a conexão com o
	 *                                           site do IPEA
	 * @param readTimeoutIpea                    tempo limite para a leitura de
	 *                                           dados do site do IPEA
	 * @param connectTimeoutSidraUmMunicipio     tempo limite para a conexão com o
	 *                                           site do SIDRA para um município
	 * @param readTimeoutSidraUmMunicipio        tempo limite para a leitura de
	 *                                           dados do site do SIDRA para um
	 *                                           município
	 * @param connectTimeoutSidraTodosMunicipios tempo limite para a conexão com o
	 *                                           site do SIDRA para todos os
	 *                                           municípios de uma agregação
	 */
	private Configuracao(int retryDelay, int retryAttempts, int connectTestPeriod, int poolInicialSize, int poolSize,
			int threadPoolSize, int tempoBusca, int tempoCalculo, int connectTimeoutIpea, int readTimeoutIpea,
			int connectTimeoutSidraUmMunicipio, int readTimeoutSidraUmMunicipio, int connectTimeoutSidraTodosMunicipios,
			int readTimeoutSidraTodosMunicipios) {
		this.retryDelay = retryDelay;
		this.retryAttempts = retryAttempts;
		this.connectTestPeriod = connectTestPeriod;
		this.poolInicialSize = poolInicialSize;
		this.poolSize = poolSize;
		this.threadPoolSize = threadPoolSize;
		this.tempoBusca = tempoBusca;
		this.tempoCalculo = tempoCalculo;
		this.connectTimeoutIpea = connectTimeoutIpea;
		this.readTimeoutIpea = readTimeoutIpea;
		this.connectTimeoutSidraUmMunicipio = connectTimeoutSidraUmMunicipio;
		this.readTimeoutSidraUmMunicipio = readTimeoutSidraUmMunicipio;
		this.connectTimeoutSidraTodosMunicipios = connectTimeoutSidraTodosMunicipios;
		this.readTimeoutSidraTodosMunicipios = readTimeoutSidraTodosMunicipios;
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
