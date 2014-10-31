package br.edu.utfpr.mbean.usuario;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.DualListModel;
import org.springframework.security.core.context.SecurityContextHolder;

import br.edu.utfpr.authentication.PepUser;
import br.edu.utfpr.mbean.BaseMBean;
import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.service.PerfilService;
import br.edu.utfpr.service.UsuarioService;

@ManagedBean
@ViewScoped
public class UsuarioMBean extends BaseMBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;
	@Inject
	private PerfilService perfilService;
	
	private List<Usuario> usuarioList;
	private Usuario usuarioSelecionado;
	private DualListModel<Perfil> perfisPickList;
	
	@PostConstruct
	public void init() {
		this.listarUsuarios();
		
		PepUser userDetails = (PepUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(userDetails);
	}
	
	private void listarUsuarios() {
		this.usuarioList = usuarioService.retornarUsuarios();
	}
	
	private void listarPerfis() {
		List<Perfil> perfis = perfilService.retornarPerfis();
		List<Perfil> perfisUsuario = usuarioSelecionado.getPerfisUsuario();
		if (perfisUsuario != null) {
			for (Perfil perfil : perfisUsuario) {
				perfis.remove(perfil);
			}
		} else {
			perfisUsuario = new ArrayList<Perfil>();
		}
		perfisPickList = new DualListModel<Perfil>(perfis, perfisUsuario);
	}
	
	public String onEditUsuarioClick(Long idUsuario) {
		return "/secure/usuarios/editarUsuario.xhtml?faces-redirect=true&idUsuario=" + idUsuario;
	}
	
	public void novoUsuario() {
		this.usuarioSelecionado = new Usuario();
		this.listarPerfis();
	}
	
	public void editarUsuario() {
		HttpServletRequest request = (HttpServletRequest) getCurrentInstance().getExternalContext().getRequest();
		String idUsuario = request.getParameter("idUsuario");
		
		if (StringUtils.isNotEmpty(idUsuario) && StringUtils.isNumeric(idUsuario)) {
			this.usuarioSelecionado = usuarioService.retornarUsuario(Long.parseLong(idUsuario));
			this.listarPerfis();
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
		usuarioSelecionado.setPerfisUsuario(perfisPickList.getTarget());
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

	public DualListModel<Perfil> getPerfisPickList() {
		return perfisPickList;
	}

	public void setPerfisPickList(DualListModel<Perfil> perfisPickList) {
		this.perfisPickList = perfisPickList;
	}
}
