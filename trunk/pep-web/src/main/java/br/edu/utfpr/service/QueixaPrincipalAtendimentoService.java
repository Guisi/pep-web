package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.QueixaPrincipalAtendimentoDao;
import br.edu.utfpr.model.QueixaPrincipalAtendimento;

@Named
@Stateless
public class QueixaPrincipalAtendimentoService {

	@Inject
	private QueixaPrincipalAtendimentoDao queixaPrincipalAtendimentoDao;

	public List<QueixaPrincipalAtendimento> retornarQueixasPrincipaisAtendimento(Long idAtendimento) {
		return queixaPrincipalAtendimentoDao.retornarQueixasPrincipaisAtendimento(idAtendimento);
	}
	
	public void removerQueixaPrincipalAtendimento(QueixaPrincipalAtendimento queixaPrincipalAtendimento) {
		queixaPrincipalAtendimentoDao.removeById(queixaPrincipalAtendimento.getId());
	}
}
