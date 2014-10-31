package br.edu.utfpr.authentication;

import java.util.LinkedHashSet;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.service.UsuarioService;
import br.edu.utfpr.utils.MD5Util;

public class PepAuthenticationProvider implements AuthenticationProvider {

	private UsuarioService usuarioService;
	
	/**
	 * 
	 * @param authentication
	 * @return Authentication
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		// Recupera os dados do usuario para autenticacao
		String username = ((PepAuthenticationToken) authentication).getPrincipal().toString();
		String password = ((PepAuthenticationToken) authentication).getCredentials().toString();
		
		password = MD5Util.stringToMD5(password);
		
		Usuario usuario;
		try {
			usuario = usuarioService.autenticarUsuario(username, password);
		} catch (AppException e) {
			throw new AuthenticationServiceException("Usuário ou senha inválido");
		}
		
		// Cria usuario Spring Autenticado
		PepUser user = new PepUser(usuario.getEmail(), 
								   usuario.getNome(), 
								   usuario.getCpf(), 
								   false, 
								   true, 
								   new LinkedHashSet<GrantedAuthority>());

		// Finaliza a autenticacao
		Authentication result = new UsernamePasswordAuthenticationToken(user, authentication, user.getAuthorities());

		return result;
	}

	@Override
	public boolean supports(Class<? extends Object> authentication) {
		return (PepAuthenticationToken.class.isAssignableFrom(authentication));
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
}
