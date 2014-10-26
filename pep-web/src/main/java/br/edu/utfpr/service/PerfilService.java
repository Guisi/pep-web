package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import br.edu.utfpr.dao.PerfilDao;
import br.edu.utfpr.model.Perfil;

@Named
@Stateless
public class PerfilService {

	@Inject
	private PerfilDao perfilDao;

	public List<Perfil> retornarPerfis() {
		return perfilDao.retornarPerfis();
	}
	
	public void removerPerfil(Perfil perfil) {
		perfilDao.removeById(perfil.getId());
	}
	
	public void salvarPerfil(Perfil perfil) {
		perfilDao.save(perfil);
	}
	
	public Perfil retornarPerfil(Long id) {
		Perfil perfil = perfilDao.getById(id);
		perfil.getAutorizacoes().size();
		return perfil;
	}
	
	public Perfil retornarPerfilPorNome(String nome) {
		try {
			return perfilDao.retornarPerfilPorNome(nome);
		} catch (NoResultException e) {
			return null;
		}
	}
}
