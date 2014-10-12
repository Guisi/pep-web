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
		HttpServletRequest request = (HttpServletRequest) getCurrentInstance().getExternalContext().getRequest();
		if (request.getParameter("editou") != null) {
			addInfoMessage(getMsgs().getString("usuario.editar.sucesso"));
		} else if (request.getParameter("criou") != null) {
			addInfoMessage(getMsgs().getString("usuario.criar.sucesso"));
		} else if (request.getParameter("excluiu") != null) {
			addInfoMessage(getMsgs().getString("usuario.remover.sucesso"));
		}
		
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
	
	public String removerUsuario() {
		usuarioService.removerUsuario(usuarioSelecionado);
		usuarioSelecionado = null;
		
		this.listarUsuarios();
		
		return "/secure/usuarios/usuarios.xhtml?faces-redirect=true&excluiu=true";
	}
	
	public String salvar() {
		String telefone = usuarioSelecionado.getTelefone();
		telefone = telefone.replaceAll("[^\\d.]", "");
		usuarioSelecionado.setTelefone(telefone);
		
		usuarioService.salvarUsuario(usuarioSelecionado);
		
		return "/secure/usuarios/usuarios.xhtml?faces-redirect=true&editou=true";
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

	public String getEmptyStr() {
		return "";
	}
}
