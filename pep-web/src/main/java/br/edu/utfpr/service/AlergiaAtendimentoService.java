package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.AlergiaAtendimentoDao;
import br.edu.utfpr.model.Alergia;
import br.edu.utfpr.model.AlergiaAtendimento;

@Named
@Stateless
public class AlergiaAtendimentoService {

	@Inject
	private AlergiaAtendimentoDao alergiaAtendimentoDao;
	
	public List<AlergiaAtendimento> retornarAlergiasAtendimento(Long idAtendimento) {
		return alergiaAtendimentoDao.retornarAlergiasAtendimento(idAtendimento);
	}

	public List<AlergiaAtendimento> retornarAlergiasAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		return alergiaAtendimentoDao.retornarAlergiasAtendimentosAnteriores(idPaciente, idAtendimentoIgnorar);
	}
	
	public List<Alergia> retornarAlergiasMaisUsadas(Integer quantidade, List<Long> idsAlergiasIgnorar, Boolean chkAtivo) {
		return alergiaAtendimentoDao.retornarAlergiasMaisUsadas(quantidade, idsAlergiasIgnorar, chkAtivo);
	}
	
	public void salvarAlergiasAtendimento(List<AlergiaAtendimento> alergiasAtendimento) {
		for (AlergiaAtendimento alergiaAtendimento : alergiasAtendimento) {
			alergiaAtendimentoDao.save(alergiaAtendimento);
		}
	}
	
	public void removerAlergiasExcluidas(Long idAtendimento, List<AlergiaAtendimento> alergias) {
		List<AlergiaAtendimento> alergiasBase = this.retornarAlergiasAtendimento(idAtendimento);
		for (AlergiaAtendimento alergiaBase : alergiasBase) {
			boolean excluido = true;
			for (AlergiaAtendimento alergiaAtendimento : alergias) {
				if (alergiaBase.equals(alergiaAtendimento)) {
					excluido = false;
					break;
				}
			}
			
			if (excluido) {
				alergiaAtendimentoDao.remove(alergiaBase);
			}
		}
	}
}
