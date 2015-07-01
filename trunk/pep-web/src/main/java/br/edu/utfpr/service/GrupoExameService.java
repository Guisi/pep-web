package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.dao.GrupoExameDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.GrupoExame;

@Named
@Stateless
public class GrupoExameService {

	@Inject
	private GrupoExameDao grupoExameDao;
	
	public List<GrupoExame> retornarGruposExames(Boolean chkAtivo, Boolean fetchExames) {
		return retornarGruposExames(null, chkAtivo, fetchExames);
	}
	
	public List<GrupoExame> retornarGruposExames(String textoPesquisa, Boolean chkAtivo, Boolean fetchExames) {
		return grupoExameDao.retornarGruposExames(textoPesquisa, chkAtivo, fetchExames);
	}
	
	public GrupoExame retornarGrupoExame(Long id) {
		try {
			return grupoExameDao.retornarGrupoExamePorId(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public GrupoExame retornarGrupoExame(String descricao) {
		try {
			return grupoExameDao.retornarGrupoExame(descricao);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarGrupoExame(GrupoExame grupoExame) throws AppException {
		String descricao = StringUtils.trimToNull(grupoExame.getDescricao());
		grupoExame.setDescricao(descricao);
		
		//valida se nao esta duplicando
		GrupoExame grupoExameBase = this.retornarGrupoExame(descricao);
		if (grupoExameBase != null && !grupoExameBase.getId().equals(grupoExame.getId())) {
			throw new AppException("grupoexame.salvar.erro.existente", descricao);
		}
		
		grupoExameDao.save(grupoExame);
	}
}
