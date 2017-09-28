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
	
	public void salvarQueixasPrincipaisAtendimento(List<QueixaPrincipalAtendimento> queixasPrincipaisAtendimento) {
		for (QueixaPrincipalAtendimento queixaPrincipalAtendimento : queixasPrincipaisAtendimento) {
			queixaPrincipalAtendimentoDao.save(queixaPrincipalAtendimento);
		}
	}
	
	public void removerQueixasPrincipaisExcluidas(Long idAtendimento, List<QueixaPrincipalAtendimento> queixasPrincipais) {
		List<QueixaPrincipalAtendimento> queixasPrincipaisBase = this.retornarQueixasPrincipaisAtendimento(idAtendimento);
		for (QueixaPrincipalAtendimento queixaPrincipalBase : queixasPrincipaisBase) {
			boolean excluido = true;
			for (QueixaPrincipalAtendimento queixaPrincipalAtendimento : queixasPrincipais) {
				if (queixaPrincipalBase.equals(queixaPrincipalAtendimento)) {
					excluido = false;
					break;
				}
			}
			
			if (excluido) {
				queixaPrincipalAtendimentoDao.remove(queixaPrincipalBase);
			}
		}
	}
}
