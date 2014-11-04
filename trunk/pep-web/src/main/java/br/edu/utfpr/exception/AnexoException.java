package br.edu.utfpr.exception;

/**
 * Excecao nas operacoes com o anexo do email.
 * 
 * @author mauriciovigolo
 */
public class AnexoException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor default
	 * 
	 */
	public AnexoException() {
		super();
	}

	/**
	 * Construtor com Mensagem e Throwable.
	 * 
	 * @param message
	 * @param cause
	 */
	public AnexoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Construtor somente com Mensagem.
	 * 
	 * @param message
	 */
	public AnexoException(String message) {
		super(message);
	}

	/**
	 * Construtor com Throwable
	 * 
	 * @param cause
	 */
	public AnexoException(Throwable cause) {
		super(cause);
	}
	
}
