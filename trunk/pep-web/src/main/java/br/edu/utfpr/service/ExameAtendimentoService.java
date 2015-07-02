package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.ExameAtendimentoDao;
import br.edu.utfpr.model.ExameAtendimento;

@Named
@Stateless
public class ExameAtendimentoService {

	@Inject
	private ExameAtendimentoDao exameAtendimentoDao;
	
	public List<ExameAtendimento> retornarExamesAtendimento(Long idAtendimento) {
		return exameAtendimentoDao.retornarExamesAtendimento(idAtendimento);
	}

	public void salvarExamesAtendimento(List<ExameAtendimento> examesAtendimento) {
		for (ExameAtendimento exameAtendimento : examesAtendimento) {
			exameAtendimentoDao.save(exameAtendimento);
		}
	}
	
	public void removerExamesExcluidos(Long idAtendimento, List<ExameAtendimento> exames) {
		List<ExameAtendimento> examesBase = this.retornarExamesAtendimento(idAtendimento);
		for (ExameAtendimento exameBase : examesBase) {
			boolean excluido = true;
			for (ExameAtendimento exameAtendimento : exames) {
				if (exameBase.equals(exameAtendimento)) {
					excluido = false;
					break;
				}
			}
			
			if (excluido) {
				exameAtendimentoDao.remove(exameBase);
			}
		}
	}
}
