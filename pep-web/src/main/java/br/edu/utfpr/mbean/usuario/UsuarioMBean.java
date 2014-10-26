package br.edu.utfpr.mbean.usuario;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

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
		this.usuarioList = usuarioService.retornarUsuarios();
	}
	
	public String onEditUsuarioClick(Long idUsuario) {
		return "/secure/usuarios/editarUsuario.xhtml?faces-redirect=true&idUsuario=" + idUsuario;
	}
	
	public void novoUsuario() {
		this.usuarioSelecionado = new Usuario();
	}
	
	public void editarUsuario() {
		HttpServletRequest request = (HttpServletRequest) getCurrentInstance().getExternalContext().getRequest();
		String idUsuario = request.getParameter("idUsuario");
		
		if (StringUtils.isNotEmpty(idUsuario) && StringUtils.isNumeric(idUsuario)) {
			this.usuarioSelecionado = usuarioService.retornarUsuario(Long.parseLong(idUsuario));
		}
	}
	
	public void removerUsuario() {
		usuarioService.removerUsuario(usuarioSelecionado);
		usuarioSelecionado = null;
		
		this.listarUsuarios();
		
		addInfoMessage(getMsgs().getString("usuario.remover.sucesso"));
	}
	
	public String salvar() {
		boolean isNew = usuarioSelecionado.isNew();
		
		String telefone = usuarioSelecionado.getTelefone();
		telefone = telefone.replaceAll("[^\\d.]", "");
		usuarioSelecionado.setTelefone(telefone);
		
		usuarioService.salvarUsuario(usuarioSelecionado);
		
		if (isNew) {
			addInfoMessage(getMsgs().getString("usuario.criar.sucesso"), true);
		} else {
			addInfoMessage(getMsgs().getString("usuario.editar.sucesso"), true);
		}
		
		return "/secure/usuarios/usuarios.xhtml?faces-redirect=true";
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
