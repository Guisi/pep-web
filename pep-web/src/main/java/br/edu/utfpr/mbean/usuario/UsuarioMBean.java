package br.edu.utfpr.mbean.usuario;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.service.UsuarioService;

@ManagedBean
@ViewScoped
public class UsuarioMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;
	
	private List<Usuario> usuarioList;
	private Usuario usuarioSelecionado;
	
	@PostConstruct
	public void init() {
		this.listarUsuarios();
	}
	
	private void listarUsuarios() {
		this.usuarioList = usuarioService.retornarUsuarios(Boolean.TRUE);
	}
	
	public String onEditUsuarioClick(Long idUsuario) {
		return "/secure/usuarios/editarUsuario.xhtml?faces-redirect=true&idUsuario=" + idUsuario;
	}
	
	public String novoUsuario() {
		return "/secure/usuarios/editarUsuario.xhtml?faces-redirect=true";
	}
	
	public void removerUsuario() {
		usuarioService.inativarUsuario(getUsuarioLogado(), usuarioSelecionado);
		
		this.listarUsuarios();
		
		addInfoMessage(getMsgs().getString("usuario.remover.sucesso"));
	}
	
	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
}
