package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.dao.DoencaDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Doenca;

@Named
@Stateless
public class DoencaService {

	@Inject
	private DoencaDao doencaDao;
	
	public List<Doenca> retornarDoencas(Boolean chkAtivo) {
		return retornarDoencas(null, chkAtivo);
	}
	
	public List<Doenca> retornarDoencas(String textoPesquisa, Boolean chkAtivo) {
		return doencaDao.retornarDoencas(textoPesquisa, chkAtivo);
	}
	
	public Doenca retornarDoenca(Long id) {
		try {
			return doencaDao.getById(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Doenca retornarDoenca(String codigoCid) {
		try {
			return doencaDao.retornarDoenca(codigoCid);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarDoenca(Doenca doenca) throws AppException {
		String codigoCid = StringUtils.trimToNull(doenca.getCodigoCid());
		doenca.setCodigoCid(codigoCid);
		
		String descricao = StringUtils.trimToNull(doenca.getDescricao());
		doenca.setDescricao(descricao);
		
		//valida se nao esta duplicando
		Doenca doencaBase = this.retornarDoenca(codigoCid);
		if (doencaBase != null && !doencaBase.getId().equals(doenca.getId())) {
			throw new AppException("doenca.salvar.erro.existente", codigoCid);
		}
		
		doencaDao.save(doenca);
	}
}
