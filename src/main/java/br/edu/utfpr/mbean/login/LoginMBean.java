package br.edu.utfpr.mbean.login;

import java.io.IOException;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import org.springframework.security.core.AuthenticationException;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.service.UsuarioService;

/**
 * ManagedBean da pagina de login da aplicacao
 * 
 * @author douglas.guisi
 */
@ManagedBean
@RequestScoped
public class LoginMBean extends BaseMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioService usuarioService;
	
	private String login;
	private String mensagem;
	private String loginAlteracao;
	private boolean sucessoSolicitacaoAlteracao;

	/**
	 * Metodo a ser executado depois da construcao do objeto. Este tem a finalidade de remover a mensagem de erro da sessao para que o usuario nao veja o erro anterior.
	 */
	@PostConstruct
	public void init() {
		Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

		// Recupera o ultimo login do usuario se na sessao
		if (sessionMap.get("SPRING_SECURITY_LAST_USERNAME") != null) {
			login = sessionMap.get("SPRING_SECURITY_LAST_USERNAME").toString();
		}

		// Trata se ocorreu uma excecao na ultima execucao
		AuthenticationException ex = (AuthenticationException) sessionMap.get("SPRING_SECURITY_LAST_EXCEPTION");
		if (ex != null) {
			
			if (ex.getMessage() != null) {
				addErrorMessage(ex.getMessage());
			} else {
				addErrorMessage(getMsgs().getString("login.error.usuariosenhainvalido"));
			}
			
			getSession().invalidate();
		}
	}

	/**
	 * Faz a limpeza dos messages
	 */
	public void limparMessages() {
		for (Iterator<FacesMessage> iterator = FacesContext.getCurrentInstance().getMessageList().iterator(); iterator.hasNext();) {
			iterator.remove();
		}
		sucessoSolicitacaoAlteracao = false;
		loginAlteracao = null;
	}
	
	/**
	 * Processar login do usuario.
	 * 
	 * @return
	 */
	public void processLogin() {
		try {
			RequestDispatcher dispatcher = getRequest().getRequestDispatcher("/j_spring_security_check");
			dispatcher.forward(getRequest(), getResponse());
			FacesContext.getCurrentInstance().responseComplete();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void solicitarAlteracaoSenha() {
		try {
			usuarioService.reiniciarSenha(loginAlteracao);
			addInfoMessage(MessageFormat.format(getMsgs().getString("login.solicitaralteracaosenha.sucesso"), loginAlteracao));
			sucessoSolicitacaoAlteracao = true;
		} catch (AppException e) {
			addressException(e);
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getLoginAlteracao() {
		return loginAlteracao;
	}

	public void setLoginAlteracao(String loginAlteracao) {
		this.loginAlteracao = loginAlteracao;
	}

	public boolean isSucessoSolicitacaoAlteracao() {
		return sucessoSolicitacaoAlteracao;
	}

	public void setSucessoSolicitacaoAlteracao(boolean sucessoSolicitacaoAlteracao) {
		this.sucessoSolicitacaoAlteracao = sucessoSolicitacaoAlteracao;
	}
	
}