package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.AntecedenteClinicoAtendimentoDao;
import br.edu.utfpr.model.AntecedenteClinicoAtendimento;
import br.edu.utfpr.model.Doenca;

@Named
@Stateless
public class AntecedenteClinicoAtendimentoService {

	@Inject
	private AntecedenteClinicoAtendimentoDao antecedenteClinicoAtendimentoDao;
	
	public List<AntecedenteClinicoAtendimento> retornarAntecedentesClinicosAtendimento(Long idAtendimento) {
		return antecedenteClinicoAtendimentoDao.retornarAntecedentesClinicosAtendimento(idAtendimento);
	}

	public List<AntecedenteClinicoAtendimento> retornarAntecedentesClinicosAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		return antecedenteClinicoAtendimentoDao.retornarAntecedentesClinicosAtendimentosAnteriores(idPaciente, idAtendimentoIgnorar);
	}
	
	public List<Doenca> retornarAntecedentesClinicosMaisUsados(Integer quantidade, List<Long> idsDoencasIgnorar, Boolean chkAtivo) {
		return antecedenteClinicoAtendimentoDao.retornarAntecedentesClinicosMaisUsados(quantidade, idsDoencasIgnorar, chkAtivo);
	}
	
	public void salvarAntecedentesClinicosAtendimento(List<AntecedenteClinicoAtendimento> antecedentesClinicosAtendimento) {
		for (AntecedenteClinicoAtendimento antecedenteClinicoAtendimento : antecedentesClinicosAtendimento) {
			antecedenteClinicoAtendimentoDao.save(antecedenteClinicoAtendimento);
		}
	}
	
	public void removerAntecedentesClinicosExcluidos(Long idAtendimento, List<AntecedenteClinicoAtendimento> antecedentesClinicos) {
		List<AntecedenteClinicoAtendimento> antecedentesClinicosBase = this.retornarAntecedentesClinicosAtendimento(idAtendimento);
		for (AntecedenteClinicoAtendimento antecedenteClinicoBase : antecedentesClinicosBase) {
			boolean excluido = true;
			for (AntecedenteClinicoAtendimento antecedenteClinicoAtendimento : antecedentesClinicos) {
				if (antecedenteClinicoBase.equals(antecedenteClinicoAtendimento)) {
					excluido = false;
					break;
				}
			}
			
			if (excluido) {
				antecedenteClinicoAtendimentoDao.remove(antecedenteClinicoBase);
			}
		}
	}
}
