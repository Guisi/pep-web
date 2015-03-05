package br.edu.utfpr.service;

import java.util.Date;
import java.util.LinkedHashSet;
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
	
	public Atendimento salvarAtendimento(Atendimento atendimento, List<MedicamentoAtendimento> medicamentos,
			List<MedicamentoAtendimento> medicamentosAnteriores, List<QueixaPrincipalAtendimento> queixasPrincipais) {
		
		if (atendimento.isNew()) {
			atendimento.setData(new Date());
		} else {
			// Apaga da base as queixas principais excluidas do atendimento
			List<QueixaPrincipalAtendimento> queixasPrincipaisBase = queixaPrincipalAtendimentoService.retornarQueixasPrincipaisAtendimento(atendimento.getId());
			for (QueixaPrincipalAtendimento queixaPrincipalBase : queixasPrincipaisBase) {
				boolean excluido = true;
				for (QueixaPrincipalAtendimento queixaPrincipalAtendimento : queixasPrincipais) {
					if (queixaPrincipalBase.equals(queixaPrincipalAtendimento)) {
						excluido = false;
						break;
					}
				}
				
				if (excluido) {
					queixaPrincipalAtendimentoService.removerQueixaPrincipalAtendimento(queixaPrincipalBase);
				}
			}
			
			// Apaga da base os medicamentos excluidos do atendimento
			List<MedicamentoAtendimento> medicamentosBase = medicamentoAtendimentoService.retornarMedicamentosAtendimento(atendimento.getId());
			for (MedicamentoAtendimento medicamentoAtendimentoBase : medicamentosBase) {
				boolean excluido = true;
				for (MedicamentoAtendimento medicamentoAtendimento : medicamentos) {
					if (medicamentoAtendimentoBase.equals(medicamentoAtendimento)) {
						excluido = false;
						break;
					}
				}
				
				if (excluido) {
					medicamentoAtendimentoService.removerMedicamentoAtendimento(medicamentoAtendimentoBase);
				}
			}
		}
		
		//salva medicamentos do atendimento
		for (MedicamentoAtendimento medicamentoAtendimento : medicamentos) {
			medicamentoAtendimento.setAtendimento(atendimento);
		}
		medicamentoAtendimentoService.salvarMedicamentosAtendimento(medicamentos);
		atendimento.setMedicamentos(new LinkedHashSet<MedicamentoAtendimento>(medicamentos));
		
		//salva queixas principais do atendimento
		for (QueixaPrincipalAtendimento queixaPrincipalAtendimento : queixasPrincipais) {
			queixaPrincipalAtendimento.setAtendimento(atendimento);
		}
		queixaPrincipalAtendimentoService.salvarQueixasPrincipaisAtendimento(queixasPrincipais);
		atendimento.setQueixasPrincipais(new LinkedHashSet<QueixaPrincipalAtendimento>(queixasPrincipais));

		//salva medicamentos anteriores para o caso de descontinuidade em algum tratamento
		medicamentoAtendimentoService.salvarMedicamentosAtendimento(medicamentosAnteriores);
		
		atendimentoDao.save(atendimento);

		return atendimento;
	}
}