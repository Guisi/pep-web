package br.edu.utfpr.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.InvalidSessionStrategy;


/**
 * Implementacao para tratar o session timeout tanto das chamadas get / post quanto das requisicoes Ajax.
 * 
 * @author douglas.guisi
 */
public class JsfInvalidSessionStrategy implements InvalidSessionStrategy {
	
	private String sessionTimeOutURL;

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.session.InvalidSessionStrategy#onInvalidSessionDetected(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		boolean ajaxRequest = "partial/ajax".equals(request.getHeader("faces-request"));
		
		String contextPath = request.getContextPath();
		String redirectUrl = contextPath + sessionTimeOutURL;
		
		request.getSession(true);
		
		if (ajaxRequest) {
			String ajaxRedirectXml = createAjaxRedirectXml(redirectUrl);

			response.setContentType("text/xml");
			response.getWriter().write(ajaxRedirectXml);
		} else {
			response.sendRedirect(redirectUrl);
		}
	}

	/**
	 * Cria o XML para o redirect AJAX para incluido no response
	 * 
	 * @param redirectUrl		
	 * 			URL para a qual sera feito o redirecionamento
	 * @return 					
	 * 			XML para redirecionamento AJAX - pagina de session timeout.
	 */
	private String createAjaxRedirectXml(String redirectUrl) {

		return new StringBuilder().
				append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").
				append("<partial-response><redirect url=\"").
				append(redirectUrl).
				append("\"></redirect></partial-response>").toString();
		
	}

	/**
	 * Setter de sessionTimeOutURL
	 * 
	 * @param sessionTimeOutURL
	 *            Valor a ser atribuido a sessionTimeOutURL
	 */
	public void setSessionTimeOutURL(String sessionTimeOutURL) {

		this.sessionTimeOutURL = sessionTimeOutURL;
	}
	
}
