package br.edu.utfpr.mbean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.service.UsuarioService;

@ManagedBean
@ViewScoped
public class HeaderMBean extends BaseMBean {
	
	private static final long serialVersionUID = -4584871656033166513L;
	
	private boolean mostrarModalAlteracaoSenha;
	private String email;
	private String senha;
	private String confirmacaoSenha;
	private boolean bloquearCampos;
	
	@Inject
	private UsuarioService usuarioService;
	
	@PostConstruct
	public void init() {
		if (isAuthenticated()) {
			this.mostrarModalAlteracaoSenha = getUsuarioLogado().isDeveAlterarSenha();
			
			if (this.mostrarModalAlteracaoSenha) {
				this.inicializarUsuarioLogado();
			}
		}
	}
	
	public void inicializarUsuarioLogado() {
		this.email = getUsuarioLogado().getUsername();
		this.bloquearCampos = false;
	}

	public void alterarSenha() {
		try {
			usuarioService.alterarSenhaUsuario(email, senha);
			getUsuarioLogado().setDeveAlterarSenha(Boolean.FALSE);
			
			String msgKey = isAuthenticated() ? "alterarsenha.msg.info.autenticado.sucesso" : "alterarsenha.msg.info.token.sucesso";
			addInfoMessage(getMsgs().getString(msgKey));
			this.bloquearCampos = true;
			this.mostrarModalAlteracaoSenha = false;
		} catch (AppException e) {
			addressException(e);
		}
	}
	
	public boolean isMostrarModalAlteracaoSenha() {
		return mostrarModalAlteracaoSenha;
	}

	public void setMostrarModalAlteracaoSenha(boolean mostrarModalAlteracaoSenha) {
		this.mostrarModalAlteracaoSenha = mostrarModalAlteracaoSenha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public boolean isBloquearCampos() {
		return bloquearCampos;
	}

	public void setBloquearCampos(boolean bloquearCampos) {
		this.bloquearCampos = bloquearCampos;
	}

}