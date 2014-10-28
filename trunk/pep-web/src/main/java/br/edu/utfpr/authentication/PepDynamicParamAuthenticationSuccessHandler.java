package br.edu.utfpr.authentication;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

/**
 * Implementa uma estrategia para redirecionamento de pagina em caso de autenticacao com sucesso, baseando-se nos valores atrelados em targetUrls. O redirecionamento, baseia-se no parametro dinamico, que pode estar no escopo de request ou session.
 * 
 * @author douglas.guisi
 */
public class PepDynamicParamAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	/**
	 * Map para armazenar os nomes das roles e as respectivas urls para redirecionamento.
	 */
	private Map<String, String> targetUrlsBasedOnRole;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

		/* Variaveis locais */
		String targetUrl = null;

		/* Se obrigatorio utilizar o DefaultTargetUrl */
		if (isAlwaysUseDefaultTargetUrl() && StringUtils.hasText(request.getParameter(getTargetUrlParameter()))) {
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}

		/*
		 * Se o map de targetUrlsBasedOnRoles nao informado, envia o tratamento do redirecionamento sera no objeto pai.
		 */
		if ((targetUrlsBasedOnRole == null) || (targetUrlsBasedOnRole.isEmpty())) {
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}

		/* Verifica as roles do usuario autenticado para fazer o redirecionamento */
		if (authentication.getAuthorities() != null) {
			/* Itera as roles do usuario */
			for (GrantedAuthority auth : authentication.getAuthorities()) {
				/* Itera o Map para determinar a pagina para redirecionamento */
				for (Entry<String, String> entry : targetUrlsBasedOnRole.entrySet()) {
					String key = entry.getKey();

					if (key.equals(auth.getAuthority())) {
						// Utiliza o targetUrl do Map
						targetUrl = entry.getValue();
						break;
					}
				}
			}
		}

		/*
		 * Se targetUrl nao localizado, tratamento do redirecionamento sera feito pelo objeto pai.
		 */
		if (targetUrl == null || targetUrl.trim().isEmpty()) {
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		} else {
			/*
			 * Caso contrario, encaminha para a pagina informada como padrao para o grupo
			 */
			clearAuthenticationAttributes(request);
			getRedirectStrategy().sendRedirect(request, response, targetUrl);
		}
	}

	/**
	 * Setter de targetUrls
	 * 
	 * @param targetUrls
	 *            Valor a ser atribuido a targetUrls
	 */
	public void setTargetUrlsBasedOnRole(Map<String, String> targetUrlsBasedOnRole) {

		this.targetUrlsBasedOnRole = targetUrlsBasedOnRole;
	}

}
