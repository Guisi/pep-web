package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.dao.PerfilDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Perfil;

@Named
@Stateless
public class PerfilService {

	@Inject
	private PerfilDao perfilDao;

	public List<Perfil> retornarPerfis() {
		return perfilDao.retornarPerfis();
	}
	
	public void removerPerfil(Perfil perfil) throws AppException {
		perfil = perfilDao.getById(perfil.getId());
		
		//se possui usuarios vinculados, nao permite excluir perfil
		if (!perfil.getUsuarios().isEmpty()) {
			throw new AppException("perfil.remover.erro.usuariosvinculados");
		}
		
		perfilDao.removeById(perfil.getId());
	}
	
	public void salvarPerfil(Perfil perfil) throws AppException {
		String nomePerfil = StringUtils.trimToNull(perfil.getNome());
		perfil.setNome(nomePerfil);
		
		//valida se nao esta duplicando
		Perfil perfilBase = this.retornarPerfilPorNome(nomePerfil);
		if (perfilBase != null && !perfilBase.getId().equals(perfil.getId())) {
			throw new AppException("perfil.salvar.erro.nomeexistente", nomePerfil);
		}
		
		perfilDao.save(perfil);
	}
	
	public Perfil retornarPerfil(Long id) {
		try{
			return perfilDao.retornarPerfilPorId(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Perfil retornarPerfilPorNome(String nome) {
		try {
			return perfilDao.retornarPerfilPorNome(nome);
		} catch (NoResultException e) {
			return null;
		}
	}
}
