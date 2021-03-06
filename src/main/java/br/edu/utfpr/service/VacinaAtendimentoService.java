package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.VacinaAtendimentoDao;
import br.edu.utfpr.model.Vacina;
import br.edu.utfpr.model.VacinaAtendimento;

@Named
@Stateless
public class VacinaAtendimentoService {

	@Inject
	private VacinaAtendimentoDao vacinaAtendimentoDao;
	
	public List<VacinaAtendimento> retornarVacinasAtendimento(Long idAtendimento) {
		return vacinaAtendimentoDao.retornarVacinasAtendimento(idAtendimento);
	}

	public List<VacinaAtendimento> retornarVacinasAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		return vacinaAtendimentoDao.retornarVacinasAtendimentosAnteriores(idPaciente, idAtendimentoIgnorar);
	}
	
	public List<Vacina> retornarVacinasMaisUsadas(Integer quantidade, List<Long> idsVacinasIgnorar, Boolean chkAtivo) {
		return vacinaAtendimentoDao.retornarVacinasMaisUsadas(quantidade, idsVacinasIgnorar, chkAtivo);
	}
	
	public void salvarVacinasAtendimento(List<VacinaAtendimento> vacinasAtendimento) {
		for (VacinaAtendimento vacinaAtendimento : vacinasAtendimento) {
			vacinaAtendimentoDao.save(vacinaAtendimento);
		}
	}
	
	public void removerVacinasExcluidas(Long idAtendimento, List<VacinaAtendimento> vacinas) {
		List<VacinaAtendimento> vacinasBase = this.retornarVacinasAtendimento(idAtendimento);
		for (VacinaAtendimento vacinaBase : vacinasBase) {
			boolean excluido = true;
			for (VacinaAtendimento vacinaAtendimento : vacinas) {
				if (vacinaBase.equals(vacinaAtendimento)) {
					excluido = false;
					break;
				}
			}
			
			if (excluido) {
				vacinaAtendimentoDao.remove(vacinaBase);
			}
		}
	}
}
