package br.edu.utfpr.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import br.edu.utfpr.dao.AtendimentoDao;
import br.edu.utfpr.dao.MedicamentoAtendimentoDao;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.MedicamentoAtendimento;

@Named
@Stateless
public class AtendimentoService {

	@Inject
	private AtendimentoDao atendimentoDao;
	@Inject
	private MedicamentoAtendimentoDao medicamentoAtendimentoDao;
	@Inject
	private MedicamentoAtendimentoService medicamentoAtendimentoService;

	public List<Atendimento> retornarAtendimentosPaciente(Long idPaciente) {
		return atendimentoDao.retornarAtendimentosPaciente(idPaciente);
	}
	
	public Atendimento retornarAtendimento(Long id) {
		try {
			return atendimentoDao.retornarAtendimentoPorId(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Atendimento salvarAtendimento(Atendimento atendimento, List<MedicamentoAtendimento> medicamentosAnteriores) {
		if (atendimento.isNew()) {
			atendimento.setData(new Date());
		} else {
			
			// Apaga da base os medicamentos excluidos do atendimento
			List<MedicamentoAtendimento> medicamentos = medicamentoAtendimentoDao.retornarMedicamentosAtendimento(atendimento.getId());
			for (MedicamentoAtendimento medicamentoAtendimento : medicamentos) {
				boolean excluido = true;
				for (MedicamentoAtendimento medicamentoAtendimentoBase : atendimento.getMedicamentos()) {
					if (medicamentoAtendimento.equals(medicamentoAtendimentoBase)) {
						excluido = false;
						break;
					}
				}
				
				if (excluido) {
					medicamentoAtendimentoDao.removeById(medicamentoAtendimento.getId());
				}
			}
		}
		
		for (MedicamentoAtendimento medicamentoAtendimento : atendimento.getMedicamentos()) {
			medicamentoAtendimento.setAtendimento(atendimento);
		}

		atendimentoDao.save(atendimento);
		
		medicamentoAtendimentoService.salvarMedicamentosAtendimento(medicamentosAnteriores);

		return atendimento;
	}
}
