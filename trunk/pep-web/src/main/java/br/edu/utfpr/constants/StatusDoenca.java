package br.edu.utfpr.constants;

/**
 * Enum com os nomes dos status de doenças
 * 
 * @author douglas.guisi
 */
public enum StatusDoenca {

	CONFIRMADO("Confirmado"),
	RESOLVIDO("Resolvido"),
	HIPOTESE_DIAGNOSTICA("Hipótese diagnóstica"),
	COMPENSADO("Compensado"),
	DESCOMPENSADO("Descompensado"),
	EM_REMISSAO("Em remissão"),
	CONTROLADO("Controlado"),
	DESCONTROLADO("Descontrolado");
	
	private String nome;
	
	private StatusDoenca(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}
}
