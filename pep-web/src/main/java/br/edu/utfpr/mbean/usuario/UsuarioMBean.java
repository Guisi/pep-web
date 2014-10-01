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
	
	@PostConstruct
	public void init() {
		this.listarUsuarios();
	}
	
	private void listarUsuarios() {
		this.usuarioList = usuarioService.retornarUsuarios();
	}

	public List<Usuario> getUsuarioList() {
		return usuarioList;
	}

}
