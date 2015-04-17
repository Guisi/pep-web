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
	
	public void removerAntecedenteClinicoAtendimento(AntecedenteClinicoAtendimento antecedenteClinicoAtendimento) {
		antecedenteClinicoAtendimentoDao.remove(antecedenteClinicoAtendimento);
	}
	
	public void salvarAntecedentesClinicosAtendimento(List<AntecedenteClinicoAtendimento> antecedentesClinicosAtendimento) {
		for (AntecedenteClinicoAtendimento antecedenteClinicoAtendimento : antecedentesClinicosAtendimento) {
			antecedenteClinicoAtendimentoDao.save(antecedenteClinicoAtendimento);
		}
	}
}
