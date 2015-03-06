package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.DoencaDao;
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
}
