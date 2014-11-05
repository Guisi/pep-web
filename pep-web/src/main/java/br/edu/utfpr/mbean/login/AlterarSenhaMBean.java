package br.edu.utfpr.mbean.login;

import static javax.faces.context.FacesContext.getCurrentInstance;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.TokenCadastro;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.service.TokenCadastroService;
import br.edu.utfpr.service.UsuarioService;

@ManagedBean
@ViewScoped
public class AlterarSenhaMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;
	
	@Inject
	private TokenCadastroService tokenCadastroService;
	
	private String email;
	private String senha;
	private String confirmacaoSenha;
	
	private boolean bloquearCampos;
	
	@PostConstruct
	public void init() {
		HttpServletRequest request = (HttpServletRequest) getCurrentInstance().getExternalContext().getRequest();
		String token = request.getParameter("token");
		
		this.bloquearCampos = true;
		TokenCadastro tokenCadastro = tokenCadastroService.retornarTokenAtivo(token);
		if (tokenCadastro != null) {
			Usuario usuario = usuarioService.retornarUsuarioPorEmail(tokenCadastro.getTxEmailUsuario(), Boolean.TRUE);
			
			if (usuario != null) {
				this.email = usuario.getEmail();
				this.bloquearCampos = false;
			} else {
				addErrorMessage(getMsgs().getString("alterarsenha.msg.erro.usuarionaolocalizado"));
			}
		} else {
			addErrorMessage(getMsgs().getString("alterarsenha.msg.erro.tokeninvalido"));
		}
	}
	
	public void alterarSenha() {
		try {
			usuarioService.alterarSenhaUsuario(email, senha);
			addInfoMessage(getMsgs().getString("alterarsenha.msg.info.sucesso"));
			this.bloquearCampos = true;
		} catch (AppException e) {
			addressException(e);
		}
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
