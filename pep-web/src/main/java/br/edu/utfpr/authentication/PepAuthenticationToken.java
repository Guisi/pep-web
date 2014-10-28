/*
 * SSOAuthenticationToken.java
 * 
 * Data: 18/08/2011
 *
 * Copyright Grupo OVD
 */

package br.edu.utfpr.authentication;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Implementacao da autenticacao
 * 
 * @author mauriciovigolo
 */
public class PepAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private final Object principal;
	private Object credentials;
	private String ipUsuario;

	/**
	 * Construtor para usuario nao autenticado
	 * 
	 * @param principal
	 * @param credentials
	 * @param grupo
	 * @param aplicacao
	 * @param idioma
	 */
	public PepAuthenticationToken(Object principal, Object credentials, String ipUsuario) {

		super(null);
		this.principal = principal;
		this.credentials = credentials;
		this.ipUsuario = ipUsuario;
		super.setAuthenticated(false);
	}

	/**
	 * Construtor para usuario autenticado
	 * 
	 * @param principal
	 * @param credentials
	 * @param aplicacao
	 * @param idioma
	 * @param authorities
	 */
	public PepAuthenticationToken(Object principal, Object credentials, String ipUsuario, Collection<? extends GrantedAuthority> authorities) {

		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		this.ipUsuario = ipUsuario;
		super.setAuthenticated(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AbstractAuthenticationToken#setAuthenticated(boolean)
	 */
	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

		if (isAuthenticated) {
			throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AbstractAuthenticationToken#eraseCredentials()
	 */
	@Override
	public void eraseCredentials() {

		super.eraseCredentials();
		credentials = null;
	}

	public Object getCredentials() {
		return credentials;
	}

	public void setCredentials(Object credentials) {
		this.credentials = credentials;
	}

	public String getIpUsuario() {
		return ipUsuario;
	}

	public void setIpUsuario(String ipUsuario) {
		this.ipUsuario = ipUsuario;
	}

	public Object getPrincipal() {
		return principal;
	}

}
