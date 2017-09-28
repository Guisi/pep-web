package br.edu.utfpr.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.Assert;

/**
 * Filtro para realizar a autenticacao
 * 
 * @author douglas.guisi
 */
public class PepAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "j_username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "j_password";
	public static final String SPRING_SECURITY_FORM_APPLICATION_KEY = "aplicacao";
	public static final String SPRING_SECURITY_LAST_USERNAME_KEY = "SPRING_SECURITY_LAST_USERNAME";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

	@SuppressWarnings("unused")
	private boolean postOnly = true;

	/**
	 * Construtor default
	 */
	public PepAuthenticationFilter() {

		super("/j_spring_security_check");
	}

	/**
	 * Enables subclasses to override the composition of the username, such as by including additional values and a separator.
	 * 
	 * @param request
	 *            so that request attributes can be retrieved
	 * 
	 * @return the username that will be presented in the <code>Authentication</code> request token to the <code>AuthenticationManager</code>
	 */
	protected String obtainUsername(HttpServletRequest request) {

		String username;

		username = (String) request.getAttribute(usernameParameter);

		if (username == null || username.isEmpty()) {
			username = request.getParameter(usernameParameter);
		}

		return username;
	}

	/**
	 * Enables subclasses to override the composition of the password, such as by including additional values and a separator.
	 * <p>
	 * This might be used for example if a postcode/zipcode was required in addition to the password. A delimiter such as a pipe (|) should be used to separate the password and extended value(s). The <code>AuthenticationDao</code> will need to generate
	 * the expected password in a corresponding manner.
	 * </p>
	 * 
	 * @param request
	 *            so that request attributes can be retrieved
	 * 
	 * @return the password that will be presented in the <code>Authentication</code> request token to the <code>AuthenticationManager</code>
	 */
	protected String obtainPassword(HttpServletRequest request) {

		String password;

		password = (String) request.getAttribute(passwordParameter);

		if (password == null || password.isEmpty()) {
			password = request.getParameter(passwordParameter);
		}

		return password;
	}

	/**
	 * Provided so that subclasses may configure what is put into the authentication request's details property.
	 * 
	 * @param request
	 *            that an authentication request is being created for
	 * @param authRequest
	 *            the authentication request object that should have its details set
	 */
	protected void setDetails(HttpServletRequest request, PepAuthenticationToken authRequest) {

		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from the login request.
	 * 
	 * @param usernameParameter
	 *            the parameter name. Defaults to "j_username".
	 */
	public void setUsernameParameter(String usernameParameter) {

		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	/**
	 * Sets the parameter name which will be used to obtain the password from the login request..
	 * 
	 * @param passwordParameter
	 *            the parameter name. Defaults to "j_password".
	 */
	public void setPasswordParameter(String passwordParameter) {

		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter. If set to true, and an authentication request is received which is not a POST request, an exception will be raised immediately and authentication will not be attempted. The
	 * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {

		this.postOnly = postOnly;
	}

	/**
	 * Getter de usernameParameter
	 * 
	 * @return o valor de usernameParameter
	 */
	public String getUsernameParameter() {

		return usernameParameter;
	}

	/**
	 * Getter de passwordParameter
	 * 
	 * @return o valor de passwordParameter
	 */
	public String getPasswordParameter() {

		return passwordParameter;
	}

	/**
	 * Customizacao do metodo para tentativa de autenticacao, atendedo a necessidade das aplicacoes do Grupo OVD para autenticacao na aplicacao de Controle de Acesso via Web Service.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

		/* Recupera valores do escopo de request */
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		String ipUsuario = obtainIpAddress(request);

		// Cria o authenticationtoken do SSO do Grupo OVD
		PepAuthenticationToken authRequest = new PepAuthenticationToken(username, password, ipUsuario);

		// Coloca na sessao o ultimo login de quem tentou efetuar o login
		HttpSession session = request.getSession(false);

		if (session != null || getAllowSessionCreation()) {
			request.getSession().setAttribute(SPRING_SECURITY_LAST_USERNAME_KEY, username);
		}

		// Permite as subclasses que definam a propriedade "details"
		setDetails(request, authRequest);

		// Prossegue com a autenticacao
		return super.getAuthenticationManager().authenticate(authRequest);
	}

	/**
	 * Retorna o IP da requisicao, tratando caso passou por proxys e load-balancers.
	 * @param request
	 * @return
	 */
	private String obtainIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		return ip;
	}

}