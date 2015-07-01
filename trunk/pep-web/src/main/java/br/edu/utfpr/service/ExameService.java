package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.dao.ExameDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Exame;

@Named
@Stateless
public class ExameService {

	@Inject
	private ExameDao exameDao;
	
	public List<Exame> retornarExames(Boolean chkAtivo) {
		return retornarExames(null, chkAtivo);
	}
	
	public List<Exame> retornarExames(String textoPesquisa, Boolean chkAtivo) {
		return exameDao.retornarExames(textoPesquisa, chkAtivo);
	}
	
	public Exame retornarExame(Long id) {
		try {
			return exameDao.getById(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Exame retornarExame(String descricao) {
		try {
			return exameDao.retornarExame(descricao);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarExame(Exame exame) throws AppException {
		String descricao = StringUtils.trimToNull(exame.getDescricao());
		exame.setDescricao(descricao);
		
		//valida se nao esta duplicando
		Exame exameBase = this.retornarExame(descricao);
		if (exameBase != null && !exameBase.getId().equals(exame.getId())) {
			throw new AppException("exame.salvar.erro.existente", descricao);
		}
		
		exameDao.save(exame);
	}
}
