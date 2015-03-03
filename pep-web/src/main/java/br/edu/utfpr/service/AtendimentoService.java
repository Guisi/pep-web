package br.edu.utfpr.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import br.edu.utfpr.dao.AtendimentoDao;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.MedicamentoAtendimento;
import br.edu.utfpr.model.QueixaPrincipalAtendimento;

@Named
@Stateless
public class AtendimentoService {

	@Inject
	private AtendimentoDao atendimentoDao;
	@Inject
	private MedicamentoAtendimentoService medicamentoAtendimentoService;
	@Inject
	private QueixaPrincipalAtendimentoService queixaPrincipalAtendimentoService;

	public List<Atendimento> retornarAtendimentosPaciente(Long idPaciente) {
		return atendimentoDao.retornarAtendimentosPaciente(idPaciente);
	}
	
	public List<Atendimento> retornarAtendimentosAnterioresPaciente(Long idPaciente, Long idAtendimentoAtual) {
		return atendimentoDao.retornarAtendimentosAnterioresPaciente(idPaciente, idAtendimentoAtual);
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
			List<MedicamentoAtendimento> medicamentos = medicamentoAtendimentoService.retornarMedicamentosAtendimento(atendimento.getId());
			for (MedicamentoAtendimento medicamentoAtendimento : medicamentos) {
				boolean excluido = true;
				for (MedicamentoAtendimento medicamentoAtendimentoBase : atendimento.getMedicamentos()) {
					if (medicamentoAtendimento.equals(medicamentoAtendimentoBase)) {
						excluido = false;
						break;
					}
				}
				
				if (excluido) {
					medicamentoAtendimentoService.removerMedicamentoAtendimento(medicamentoAtendimento);
				}
			}
			
			// Apaga da base as queixas principais excluidas do atendimento
			List<QueixaPrincipalAtendimento> queixasPrincipais = queixaPrincipalAtendimentoService.retornarQueixasPrincipaisAtendimento(atendimento.getId());
			for (QueixaPrincipalAtendimento queixaPrincipalAtendimento : queixasPrincipais) {
				boolean excluido = true;
				for (QueixaPrincipalAtendimento queixaPrincipalBase : atendimento.getQueixasPrincipais()) {
					if (queixaPrincipalAtendimento.equals(queixaPrincipalBase)) {
						excluido = false;
						break;
					}
				}
				
				if (excluido) {
					queixaPrincipalAtendimentoService.removerQueixaPrincipalAtendimento(queixaPrincipalAtendimento);
				}
			}
		}
		
		for (MedicamentoAtendimento medicamentoAtendimento : atendimento.getMedicamentos()) {
			medicamentoAtendimento.setAtendimento(atendimento);
		}
		
		for (QueixaPrincipalAtendimento queixaPrincipalAtendimento : atendimento.getQueixasPrincipais()) {
			queixaPrincipalAtendimento.setAtendimento(atendimento);
		}

		atendimentoDao.save(atendimento);
		
		//salva medicamentos anteriores para o caso de descontinuidade em algum tratamento
		medicamentoAtendimentoService.salvarMedicamentosAtendimento(medicamentosAnteriores);

		return atendimento;
	}
}
