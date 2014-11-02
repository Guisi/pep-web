package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.dao.UsuarioDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Perfil;
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
	
	public Usuario retornarUsuarioPorEmail(String email) {
		try {
			return usuarioDao.retornarUsuarioPorEmail(email);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarUsuario(Usuario usuario) throws AppException {
		String email = StringUtils.trimToNull(usuario.getEmail());
		usuario.setEmail(email);
		
		Usuario usuarioBase = this.retornarUsuarioPorEmail(email);
		if (usuarioBase != null && !usuarioBase.getId().equals(usuario.getId())) {
			throw new AppException("usuario.salvar.erro.emailexistente", email);
		}
		
		String telefone = usuario.getTelefone();
		telefone = telefone.replaceAll("[^\\d.]", "");
		usuario.setTelefone(telefone);

		usuarioDao.save(usuario);
	}
	
	public Usuario autenticarUsuario(String username, String password) throws AppException {
		try {
			Usuario usuario = usuarioDao.retornarUsuarioPorEmailSenha(username, password);
			for (Perfil perfil : usuario.getPerfisUsuario()) {
				perfil.getAutorizacoes().size();
			}
			return usuario;
		} catch (NoResultException e) {
			throw new AppException("login.error.usuariosenhainvalido");
		}
	}
}
