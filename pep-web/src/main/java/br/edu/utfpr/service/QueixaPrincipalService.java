package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.dao.QueixaPrincipalDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.QueixaPrincipal;

@Named
@Stateless
public class QueixaPrincipalService {

	@Inject
	private QueixaPrincipalDao queixaPrincipalDao;

	public List<QueixaPrincipal> retornarQueixasPrincipais(Boolean chkAtivo) {
		return retornarQueixasPrincipais(null, chkAtivo);
	}
	
	public List<QueixaPrincipal> retornarQueixasPrincipais(String textoPesquisa, Boolean chkAtivo) {
		return queixaPrincipalDao.retornarQueixasPrincipais(textoPesquisa, chkAtivo);
	}
	
	public QueixaPrincipal retornarQueixaPrincipal(Long id) {
		try {
			return queixaPrincipalDao.getById(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public QueixaPrincipal retornarQueixaPrincipal(String descricao) {
		try {
			return queixaPrincipalDao.retornarQueixaPrincipal(descricao);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarQueixaPrincipal(QueixaPrincipal queixaPrincipal) throws AppException {
		String descricao = StringUtils.trimToNull(queixaPrincipal.getDescricao());
		queixaPrincipal.setDescricao(descricao);
		
		//valida se nao esta duplicando
		QueixaPrincipal queixaPrincipalBase = this.retornarQueixaPrincipal(descricao);
		if (queixaPrincipalBase != null && !queixaPrincipalBase.getId().equals(queixaPrincipal.getId())) {
			throw new AppException("queixaprincipal.salvar.erro.existente", descricao);
		}
		
		queixaPrincipalDao.save(queixaPrincipal);
	}
}
