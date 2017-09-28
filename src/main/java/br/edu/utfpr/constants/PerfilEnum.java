package br.edu.utfpr.constants;

/**
 * Enum com os nomes dos perfis fixos
 * 
 * @author douglas.guisi
 */
public enum PerfilEnum {

	PACIENTE("Paciente");
	
	private String nomePerfil;
	
	/**
	 * Default Construtor
	 * @param value
	 */
	private PerfilEnum(String nomePerfil) {
		this.nomePerfil = nomePerfil;
	}

	public String getNomePerfil() {
		return nomePerfil;
	}
	
	public PerfilEnum getPerfilPorNome(String nomePerfil) {
		for (PerfilEnum perfilEnum : PerfilEnum.values()) {
			if (perfilEnum.getNomePerfil().equals(nomePerfil)) {
				return perfilEnum;
			}
		}
		return null;
	}

}
