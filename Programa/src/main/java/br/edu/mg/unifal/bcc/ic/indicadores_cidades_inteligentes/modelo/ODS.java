package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.ODSDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;

public class ODS {

	private int numero_ods;
	private String nome_objetivo;
	
	public ODS(int numero_ods, String nome_objetivo) {
		super();
		this.numero_ods = numero_ods;
		this.nome_objetivo = nome_objetivo;
	}
	
	public ODS() {}
	
	public static void buscarODS(JComboBox<Object> comboBoxODS) {
		try {

			ODSDAO odsDAO = new ODSDAO(ConnectionFactory.recuperarConexao());
			
			odsDAO.buscarODS(comboBoxODS);

		} catch (Exception e) {
			System.out.println("Falha ao buscar ODS.");
		}
	}

	public static void mostrarODS(JTable tableODS, boolean seleciona) {
		try {

			ODSDAO oDSDAO = new ODSDAO(ConnectionFactory.recuperarConexao());

			oDSDAO.mostrarODS(tableODS, seleciona);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar tabela de ODS.");
		}
	}

	public static void cadastrarODS(ODS ods) {
		try {
			ODSDAO oDSDAO = new ODSDAO(ConnectionFactory.recuperarConexao());

			oDSDAO.cadastrarODS(ods.numero_ods, ods.nome_objetivo);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao cadastrar ODS.");
		}
	}

	public static ODS buscaODS(ODS ods) {
		try {
			ODSDAO oDSDAO = new ODSDAO(ConnectionFactory.recuperarConexao());

			return oDSDAO.buscaODS(ods.numero_ods, ods.nome_objetivo);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar a ODS " + ods.numero_ods);
		}
	}

	public static void atualizarODS(ODS ods, int numero_ods) {
		try {
			ODSDAO oDSDAO = new ODSDAO(ConnectionFactory.recuperarConexao());

			oDSDAO.atualizarODS(ods.numero_ods, ods.nome_objetivo, numero_ods);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao atualizar a ODS: " + numero_ods);
		}
	}

	public static void mostrarODS(JTable tableODS, List<ODS> listaODS) {
		try {
			int coluna = 2;

			DefaultTableModel model = (DefaultTableModel) tableODS.getModel();
			String[] nomeColuna = new String[coluna];

			nomeColuna[0] = "ODS";
			nomeColuna[1] = "Nome do objetivo";

			model.setColumnIdentifiers(nomeColuna);

			listaODS.forEach(ods -> {
				String[] linha = { Integer.toString(ods.numero_ods), ods.nome_objetivo };
				model.addRow(linha);
			});

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar as ODS na tabela.");
		}
	}

	public static void excluir(List<ODS> listaODS) {
		try {

			ODSDAO oDSDAO = new ODSDAO(ConnectionFactory.recuperarConexao());

			for (ODS ods : listaODS) {
				oDSDAO.excluir(ods);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public int getNumero_ods() {
		return numero_ods;
	}

	public String getNome_objetivo() {
		return nome_objetivo;
	}
	
}
