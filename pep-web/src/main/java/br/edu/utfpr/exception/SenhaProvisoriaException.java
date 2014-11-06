package br.edu.utfpr.exception;

import org.springframework.security.authentication.AuthenticationServiceException;

public class SenhaProvisoriaException extends AuthenticationServiceException {

	private static final long serialVersionUID = 1L;
	
	public SenhaProvisoriaException(String msg) {
		super(msg);
	}

	public SenhaProvisoriaException(String msg, Throwable t) {
        super(msg, t);
    }
}
