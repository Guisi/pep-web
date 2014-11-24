package br.edu.utfpr.authentication;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * <p>
 * Este usuario e utilizado para a autenticacao via Spring Security
 * </p>
 * 
 * @author douglas.guisi
 */
public class PepUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final String username;
	private String credentials;
	private final Long idUsuario;
	private final String nomeUsuario;
	private final String cpf;
	private final boolean enabled;
	private final boolean isCredentialsNonExpired;
	private final Set<GrantedAuthority> autorizacoes;
	private boolean deveAlterarSenha;

	/**
	 * Construtor de OvdUser
	 * 
	 * @param username
	 * @param nomeUsuario
	 * @param email
	 * @param documento
	 * @param senhaExpirada
	 * @param enabled
	 * @param grupos
	 * @param perfis
	 * @param acessos
	 * @param autorizacoes
	 */
	public PepUser(String username, Long idUsuario, String nomeUsuario, String cpf, boolean isCredentialsNonExpired, boolean enabled, Set<? extends GrantedAuthority> autorizacoes, boolean deveAlterarSenha) {
		this.username = username;
		this.idUsuario = idUsuario;
		this.nomeUsuario = nomeUsuario;
		this.cpf = cpf;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
		this.enabled = enabled;
		this.autorizacoes = Collections.unmodifiableSet(sortAuthorities(autorizacoes));
		this.deveAlterarSenha = deveAlterarSenha;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {

		return autorizacoes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {

		return username;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	public String getPassword() {

		return credentials;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {

		return enabled;
	}

	/**
	 * Faz a ordenacao das autorizacoes
	 * 
	 * @param authorities
	 * @return SortedSet<GrantedAuthority>
	 */
	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {

		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		// Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<GrantedAuthority>(new AuthorityComparator());

		for (GrantedAuthority grantedAuthority : authorities) {
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}

	/**
	 * Faz a comparacao das autorizacoes
	 * 
	 * @author mauriciovigolo
	 */
	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {

		private static final long serialVersionUID = 1L;

		@Override
		public int compare(GrantedAuthority g1, GrantedAuthority g2) {

			// Neither should ever be null as each entry is checked before adding it to the set.
			// If the authority is null, it is a custom authority and should precede others.
			if (g2.getAuthority() == null) {
				return -1;
			}

			if (g1.getAuthority() == null) {
				return 1;
			}

			return g1.getAuthority().compareTo(g2.getAuthority());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {

		return isCredentialsNonExpired;
	}
	
	public String getCredentials() {
		return credentials;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public String getCpf() {
		return cpf;
	}

	public Set<GrantedAuthority> getAutorizacoes() {
		return autorizacoes;
	}

	public boolean isDeveAlterarSenha() {
		return deveAlterarSenha;
	}

	public void setDeveAlterarSenha(boolean deveAlterarSenha) {
		this.deveAlterarSenha = deveAlterarSenha;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}
	
	/**
	 * Retorna true se os usernames forem iguais, pois campos login e UNIQUE.
	 * <p>
	 * 
	 * @param user
	 * @return boolean
	 */
	@Override
	public boolean equals(Object user) {

		if (user instanceof PepUser) {
			return username.equals(((PepUser) user).username);
		}
		return false;
	}
	
	/**
	 * Retorna o hashcode de username
	 * <p>
	 * 
	 * @return int
	 */
	@Override
	public int hashCode() {

		return username.hashCode();
	}

	/**
	 * Retorna as informacoes do usuario
	 * <p>
	 * 
	 * @return String
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append("Username: ").append(this.username).append("; ");
		sb.append("Password: [PROTECTED]; ");
		sb.append("Enabled: ").append(this.enabled).append("; ");

		if (!autorizacoes.isEmpty()) {
			sb.append("Granted Authorities: ");

			boolean first = true;
			for (GrantedAuthority auth : autorizacoes) {
				if (!first) {
					sb.append(",");
				}
				first = false;

				sb.append(auth);
			}
		} else {
			sb.append("Not granted any authorities");
		}

		return sb.toString();
	}

}
