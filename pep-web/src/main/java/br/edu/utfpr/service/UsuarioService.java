package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import br.edu.utfpr.dao.UsuarioDao;
import br.edu.utfpr.exception.AppException;
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
		Usuario usuario = usuarioDao.getById(id);
		usuario.getPerfisUsuario().size();
		return usuario;
	}
	
	public void removerUsuario(Usuario usuario) {
		usuarioDao.removeById(usuario.getId());
	}
	
	public void salvarUsuario(Usuario usuario) {
		String telefone = usuario.getTelefone();
		telefone = telefone.replaceAll("[^\\d.]", "");
		usuario.setTelefone(telefone);

		usuarioDao.save(usuario);
	}
	
	public Usuario autenticarUsuario(String username, String password) throws AppException {
		try {
			return usuarioDao.retornarUsuarioPorEmailSenha(username, password);
		} catch (NoResultException e) {
			throw new AppException("login.error.usuariosenhainvalido");
		}
	}
}
