package br.edu.utfpr.exception;

public class AppException extends Exception {

	private static final long serialVersionUID = 7407427270338443515L;
	
	private String errorCode;
	private String errorMessage;
	
	public AppException() {
		super();
	}
	
	public AppException(Throwable cause) {
		super(cause);
		if (cause instanceof AppException) {
			AppException appException = (AppException) cause;
			this.errorCode = appException.getErrorCode();
			this.errorMessage = appException.getErrorMessage();
		}
	}

	public AppException(String errorCode, String errorMessage, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public AppException(String errorMessage, Throwable cause) {
		super(cause);
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
