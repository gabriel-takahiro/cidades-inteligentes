package br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.modelo;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.dao.MetaDAO;
import br.edu.mg.unifal.bcc.ic.indicadores_cidades_inteligentes.factory.ConnectionFactory;

public class Meta {

	private String numero_meta;
	private String texto_meta;
	private int numero_ods;

	public Meta(String numero_meta, String texto_meta, int numero_ods) {
		this.numero_meta = numero_meta;
		this.texto_meta = texto_meta;
		this.numero_ods = numero_ods;
	}

	public Meta(int numero_ods, String numero_meta) {
		this.numero_meta = numero_meta;
		this.numero_ods = numero_ods;
	}

	public Meta() {
	}

	public static void buscarMetas(JComboBox<String> comboBoxMeta) {
		try {

			MetaDAO metaDAO = new MetaDAO(ConnectionFactory.recuperarConexao());

			metaDAO.buscarMetas(comboBoxMeta);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar as metas.");
		}
	}

	public static void cadastrarMeta(String numero_meta, String texto_meta, int numero_ods) {
		try {

			MetaDAO metaDAO = new MetaDAO(ConnectionFactory.recuperarConexao());

			metaDAO.cadastrarMeta(numero_meta, texto_meta, numero_ods);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao cadastrar meta.");
		}
	}

	public static boolean metaCorrespondeODS(String numero_meta, int ods) {
		String numero_ods = Integer.toString(ods);
		for (int i = 0; i < numero_ods.length(); i++) {
			if (!numero_meta.substring(i, i + 1).equals(numero_ods.substring(i, i + 1))) {
				return false;
			}
		}
		if (numero_meta.substring(numero_ods.length(), numero_ods.length() + 1).equals(".")) {
			return true;
		}
		return false;
	}

	public static void mostrarMetas(JTable tableMetas, boolean seleciona) {
		try {

			MetaDAO metaDAO = new MetaDAO(ConnectionFactory.recuperarConexao());

			metaDAO.mostrarMetas(tableMetas, seleciona);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar tabela de metas.");
		}
	}

	public static void mostrarMetas(JTable tableMetas, List<Meta> listaMetas) {
		try {
			int coluna = 3;

			DefaultTableModel model = (DefaultTableModel) tableMetas.getModel();
			String[] nomeColuna = new String[coluna];

			nomeColuna[0] = "ODS";
			nomeColuna[1] = "Número da meta";
			nomeColuna[2] = "Texto";

			model.setColumnIdentifiers(nomeColuna);

			listaMetas.forEach(meta -> {
				String[] linha = { Integer.toString(meta.numero_ods), meta.numero_meta, meta.texto_meta };
				model.addRow(linha);
			});

		} catch (Exception e) {
			throw new RuntimeException("Falha ao mostrar as metas na tabela.");
		}
	}

	public static Meta buscaMeta(Meta meta) {
		try {

			MetaDAO metaDAO = new MetaDAO(ConnectionFactory.recuperarConexao());

			return metaDAO.buscaMeta(meta);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao buscar a meta: " + meta.getNumero_meta());
		}
	}

	public static void atualizarMeta(String numero_meta, String texto_meta, int numero_ods, String meta_antiga) {
		try {
			MetaDAO metaDAO = new MetaDAO(ConnectionFactory.recuperarConexao());

			metaDAO.atualizarMeta(numero_meta, texto_meta, numero_ods, meta_antiga);

		} catch (Exception e) {
			throw new RuntimeException("Falha ao atualizar a meta: " + numero_meta);
		}
	}

	public static void excluir(List<Meta> listaMetas) {
		try {

			MetaDAO metaDAO = new MetaDAO(ConnectionFactory.recuperarConexao());

			for (Meta meta : listaMetas) {
				metaDAO.excluir(meta);
			}

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public String getNumero_meta() {
		return numero_meta;
	}

	public String getTexto_meta() {
		return texto_meta;
	}

	public int getNumero_ods() {
		return numero_ods;
	}

}
