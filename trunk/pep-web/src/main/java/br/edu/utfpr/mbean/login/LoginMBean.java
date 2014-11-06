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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.exception.SenhaProvisoriaException;
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
	private boolean mostrarModalAlteracaoSenha;

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
			
			//se a excecao eh de senha provisoria, exibe modal para trocar senha
			if (ex instanceof SenhaProvisoriaException) {
	            mostrarModalAlteracaoSenha = true;
			} else {
				//senao, exibe mensagem de erro
				if (ex.getMessage() != null) {
					addErrorMessage(ex.getMessage());
				} else {
					addErrorMessage(getMsgs().getString("login.error.usuariosenhainvalido"));
				}
			}
			
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.invalidate();
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
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
			dispatcher.forward(request, response);
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

	public boolean isMostrarModalAlteracaoSenha() {
		return mostrarModalAlteracaoSenha;
	}

	public void setMostrarModalAlteracaoSenha(boolean mostrarModalAlteracaoSenha) {
		this.mostrarModalAlteracaoSenha = mostrarModalAlteracaoSenha;
	}
	
}