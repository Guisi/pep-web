package br.edu.utfpr.constants;

public enum EstadoCivilEnum {
	SOLTEIRO("Solteiro(a)", "É"),
	CASADO("Casado(a)", "É"),
	DIVORCIADO("Divorciado(a)", "É"),
	VIUVO("Viúvo(a)", "É"),
	UNIAO_ESTAVEL("União estável", "Possui");

	private final String estadoCivil;
	private final String conjugacao;

	private EstadoCivilEnum(String estadoCivil, String conjugacao) {
		this.estadoCivil = estadoCivil;
		this.conjugacao = conjugacao;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}
	
	public String getConjugacao() {
		return conjugacao;
	}

	public static EstadoCivilEnum retornarPorDescricao(String descricao) {
		for (EstadoCivilEnum estado : EstadoCivilEnum.values()) {
			if (estado.getEstadoCivil().equals(descricao)) {
				return estado;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return getEstadoCivil();
	}
}