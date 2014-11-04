package br.edu.utfpr.authentication;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.constants.MessageName;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Autorizacao;
import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.service.UsuarioService;
import br.edu.utfpr.utils.PasswordHandler;

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
		
		password = PasswordHandler.encryptPassword(password);
		
		Usuario usuario;
		try {
			usuario = usuarioService.autenticarUsuario(username, password);
		} catch (AppException e) {
			throw new AuthenticationServiceException(getExceptionMessage(e));
		}
		
		// Carrega as Authorities do Usuario
		Set<Perfil> perfis = usuario.getPerfisUsuario();
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		if (perfis != null) {
			for (Perfil perfil : perfis) {
				Set<Autorizacao> autorizacoes = perfil.getAutorizacoes();

				if (autorizacoes != null) {
					for (Autorizacao autorizacao : autorizacoes) {
						GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(autorizacao.getNome());
						authorities.add(grantedAuthority);
					}
				}
			}
		}
		
		// Cria usuario Spring Autenticado
		PepUser user = new PepUser(usuario.getEmail(), 
								   usuario.getNome(), 
								   usuario.getCpf(), 
								   false, 
								   true, 
								   authorities);

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
	
	protected String getExceptionMessage(AppException exception) {
		String errorCode = exception.getErrorCode();
		
		if (errorCode != null) {
			ResourceBundle bundle = ResourceBundle.getBundle(MessageName.ERRORS.value(), Constantes.LOCALE_PT_BR);
			Object[] errorMessageParams = exception.getErrorMessageParams();
			if (errorMessageParams != null) {
				return MessageFormat.format(bundle.getString(errorCode), errorMessageParams);
			} else {
				return bundle.getString(errorCode);
			}
		} else {
			return exception.getMessage();
		}
	}
	
}
