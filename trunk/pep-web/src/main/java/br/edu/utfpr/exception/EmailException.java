package br.edu.utfpr.exception;

/**
 * Excecao nas operacoes do email.
 * 
 * @author douglas.guisi
 */
public class EmailException extends AppException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construtor default
	 */
	public EmailException() {
		super();
	}
	
	public EmailException(Throwable cause) {
		super(cause);
	}

	public EmailException(String errorCode, Object... errorMessageParams) {
		super(errorCode, errorMessageParams);
	}
	
	public EmailException(String errorCode, String errorMessage, Throwable cause, Object... errorMessageParams) {
		super(errorCode, errorMessage, cause, errorMessageParams);
	}
	
	public EmailException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}
	
}
