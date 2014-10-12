package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.UsuarioDao;
import br.edu.utfpr.model.Usuario;

@Named
@Stateless
public class UsuarioService {

	@Inject
	private UsuarioDao usuarioDao;

	public List<Usuario> retornarUsuarios() {
		return usuarioDao.retornarUsuarios();
	}
	
	public Usuario retornarUsuario(Long id) {
		return usuarioDao.getById(id);
	}
	
	public void removerUsuario(Usuario usuario) {
		usuarioDao.removeById(usuario.getId());
	}
	
	public void salvarUsuario(Usuario usuario) {
		usuarioDao.save(usuario);
	}
}
