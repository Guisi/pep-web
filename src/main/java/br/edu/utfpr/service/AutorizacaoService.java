package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.AutorizacaoDao;
import br.edu.utfpr.model.Autorizacao;

@Named
@Stateless
public class AutorizacaoService {

	@Inject
	private AutorizacaoDao autorizacaoDao;

	public List<Autorizacao> retornarAutorizacoes() {
		return autorizacaoDao.retornarAutorizacoes();
	}
}
