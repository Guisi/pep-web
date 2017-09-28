package br.edu.utfpr.constants;

/**
 * Enum com os nomes dos tipos de procedimentos
 * 
 * @author douglas.guisi
 */
public enum TipoProcedimento {

	CIRURGICO("Cirúrgico");
	
	private String nome;
	
	/**
	 * Default Construtor
	 * @param value
	 */
	private TipoProcedimento(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
