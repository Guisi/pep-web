package br.edu.utfpr.mbean.usuario;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.AlteracaoAtributo;
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
	private String pesquisa;
	
	private List<AlteracaoAtributo> historicoAlteracoes;
	
	@PostConstruct
	public void init() {
		this.pesquisa = null;
		this.listarUsuarios();
	}
	
	public void listarUsuarios() {
		this.usuarioList = usuarioService.retornarUsuarios(pesquisa, Boolean.TRUE);
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
	
	public void listarHistoricoUsuario(Long idUsuario) {
		this.historicoAlteracoes = usuarioService.retornarHistoricoAlteracaoUsuario(idUsuario);
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

	public String getPesquisa() {
		return pesquisa;
	}

	public void setPesquisa(String pesquisa) {
		this.pesquisa = pesquisa;
	}

	public List<AlteracaoAtributo> getHistoricoAlteracoes() {
		return historicoAlteracoes;
	}

	public void setHistoricoAlteracoes(List<AlteracaoAtributo> historicoAlteracoes) {
		this.historicoAlteracoes = historicoAlteracoes;
	}
}
