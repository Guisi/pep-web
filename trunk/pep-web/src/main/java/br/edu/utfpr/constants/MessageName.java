package br.edu.utfpr.constants;

/**
 * Enum com os nomes dos arquivos de mensagens
 * 
 * @author douglas.guisi
 */
public enum MessageName {

	MESSAGES("messages"),
	ERRORS("errors");
	
	private String value;
	
	/**
	 * Default Construtor
	 * @param value
	 */
	private MessageName(String value) {
		this.value = value;
	}

	/**
	 * Getter de value
	 * @return o valor de value
	 */
	public String value() {
		return value;
	}

}
