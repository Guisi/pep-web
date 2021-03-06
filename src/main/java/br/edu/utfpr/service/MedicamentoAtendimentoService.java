package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.MedicamentoAtendimentoDao;
import br.edu.utfpr.model.MedicamentoAtendimento;

@Named
@Stateless
public class MedicamentoAtendimentoService {

	@Inject
	private MedicamentoAtendimentoDao medicamentoAtendimentoDao;

	public List<MedicamentoAtendimento> retornarMedicamentosEmUsoPaciente(Long idPaciente, Long idAtendimentoIgnorar) {
		return medicamentoAtendimentoDao.retornarMedicamentosEmUsoPaciente(idPaciente, idAtendimentoIgnorar);
	}
	
	public void salvarMedicamentosAtendimento(List<MedicamentoAtendimento> medicamentosAtendimento) {
		for (MedicamentoAtendimento medicamentoAtendimento : medicamentosAtendimento) {
			medicamentoAtendimentoDao.save(medicamentoAtendimento);
		}
	}
	
	public List<MedicamentoAtendimento> retornarMedicamentosAtendimento(Long idAtendimento) {
		return medicamentoAtendimentoDao.retornarMedicamentosAtendimento(idAtendimento);
	}
	
	public void removerMedicamentosExcluidos(Long idAtendimento, List<MedicamentoAtendimento> medicamentos) {
		List<MedicamentoAtendimento> medicamentosBase = this.retornarMedicamentosAtendimento(idAtendimento);
		for (MedicamentoAtendimento medicamentoAtendimentoBase : medicamentosBase) {
			boolean excluido = true;
			for (MedicamentoAtendimento medicamentoAtendimento : medicamentos) {
				if (medicamentoAtendimentoBase.equals(medicamentoAtendimento)) {
					excluido = false;
					break;
				}
			}
			
			if (excluido) {
				medicamentoAtendimentoDao.remove(medicamentoAtendimentoBase);
			}
		}
	}
}
