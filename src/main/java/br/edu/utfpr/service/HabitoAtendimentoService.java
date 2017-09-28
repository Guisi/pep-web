package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.HabitoAtendimentoDao;
import br.edu.utfpr.model.Habito;
import br.edu.utfpr.model.HabitoAtendimento;

@Named
@Stateless
public class HabitoAtendimentoService {

	@Inject
	private HabitoAtendimentoDao habitoAtendimentoDao;
	
	public List<HabitoAtendimento> retornarHabitosAtendimento(Long idAtendimento) {
		return habitoAtendimentoDao.retornarHabitosAtendimento(idAtendimento);
	}

	public List<HabitoAtendimento> retornarHabitosAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		return habitoAtendimentoDao.retornarHabitosAtendimentosAnteriores(idPaciente, idAtendimentoIgnorar);
	}
	
	public List<Habito> retornarHabitosMaisUsados(Integer quantidade, List<Long> idsHabitosIgnorar, Boolean chkAtivo) {
		return habitoAtendimentoDao.retornarHabitosMaisUsados(quantidade, idsHabitosIgnorar, chkAtivo);
	}
	
	public void salvarHabitosAtendimento(List<HabitoAtendimento> habitosAtendimento) {
		for (HabitoAtendimento habitoAtendimento : habitosAtendimento) {
			habitoAtendimentoDao.save(habitoAtendimento);
		}
	}
	
	public void removerHabitosExcluidos(Long idAtendimento, List<HabitoAtendimento> habitos) {
		List<HabitoAtendimento> habitosBase = this.retornarHabitosAtendimento(idAtendimento);
		for (HabitoAtendimento habitoBase : habitosBase) {
			boolean excluido = true;
			for (HabitoAtendimento habitoAtendimento : habitos) {
				if (habitoBase.equals(habitoAtendimento)) {
					excluido = false;
					break;
				}
			}
			
			if (excluido) {
				habitoAtendimentoDao.remove(habitoBase);
			}
		}
	}
}
