package br.edu.utfpr.authentication;

import java.util.LinkedHashSet;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

public class PepAuthenticationProvider implements AuthenticationProvider {

	/**
	 * 
	 * @param authentication
	 * @return Authentication
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		// Cria usuario Spring Autenticado
		PepUser user = new PepUser("douglasguisi", "Douglas Guisi", "guisiagudos@gmail.com", "05009490935", false, true, new LinkedHashSet<GrantedAuthority>());

		// Finaliza a autenticacao
		Authentication result = new UsernamePasswordAuthenticationToken(user, authentication, user.getAuthorities());

		return result;
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (PepAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
}
