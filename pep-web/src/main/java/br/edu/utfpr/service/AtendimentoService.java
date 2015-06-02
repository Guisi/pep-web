package br.edu.utfpr.service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import br.edu.utfpr.dao.AtendimentoDao;
import br.edu.utfpr.model.AlergiaAtendimento;
import br.edu.utfpr.model.AntecedenteCirurgicoAtendimento;
import br.edu.utfpr.model.AntecedenteClinicoAtendimento;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.HabitoAtendimento;
import br.edu.utfpr.model.MedicamentoAtendimento;
import br.edu.utfpr.model.QueixaPrincipalAtendimento;
import br.edu.utfpr.model.VacinaAtendimento;

@Named
@Stateless
public class AtendimentoService {

	@Inject
	private AtendimentoDao atendimentoDao;
	@Inject
	private MedicamentoAtendimentoService medicamentoAtendimentoService;
	@Inject
	private QueixaPrincipalAtendimentoService queixaPrincipalAtendimentoService;
	@Inject
	private AntecedenteClinicoAtendimentoService antecedenteClinicoAtendimentoService;
	@Inject
	private AntecedenteCirurgicoAtendimentoService antecedenteCirurgicoAtendimentoService;
	@Inject
	private HabitoAtendimentoService habitoAtendimentoService;
	@Inject
	private AlergiaAtendimentoService alergiaAtendimentoService;
	@Inject
	private VacinaAtendimentoService vacinaAtendimentoService;
	
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
			List<MedicamentoAtendimento> medicamentosAnteriores, List<QueixaPrincipalAtendimento> queixasPrincipais,
			List<AntecedenteClinicoAtendimento> antecedentesClinicos, List<AntecedenteCirurgicoAtendimento> antecedentesCirurgicos,
			List<HabitoAtendimento> habitos, List<AlergiaAtendimento> alergias, List<VacinaAtendimento> vacinas) {
		
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
			
			// Apaga da base os antecedentes clinicos excluidos do atendimento
			List<AntecedenteClinicoAtendimento> antecedentesClinicosBase = antecedenteClinicoAtendimentoService.retornarAntecedentesClinicosAtendimento(atendimento.getId());
			for (AntecedenteClinicoAtendimento antecedenteClinicoBase : antecedentesClinicosBase) {
				boolean excluido = true;
				for (AntecedenteClinicoAtendimento antecedenteClinicoAtendimento : antecedentesClinicos) {
					if (antecedenteClinicoBase.equals(antecedenteClinicoAtendimento)) {
						excluido = false;
						break;
					}
				}
				
				if (excluido) {
					antecedenteClinicoAtendimentoService.removerAntecedenteClinicoAtendimento(antecedenteClinicoBase);
				}
			}
			
			// Apaga da base os antecedentes cirurgicos excluidos do atendimento
			List<AntecedenteCirurgicoAtendimento> antecedentesCirurgicosBase = antecedenteCirurgicoAtendimentoService.retornarAntecedentesCirurgicosAtendimento(atendimento.getId());
			for (AntecedenteCirurgicoAtendimento antecedenteCirurgicoBase : antecedentesCirurgicosBase) {
				boolean excluido = true;
				for (AntecedenteCirurgicoAtendimento antecedenteCirurgicoAtendimento : antecedentesCirurgicos) {
					if (antecedenteCirurgicoBase.equals(antecedenteCirurgicoAtendimento)) {
						excluido = false;
						break;
					}
				}
				
				if (excluido) {
					antecedenteCirurgicoAtendimentoService.removerAntecedenteCirurgicoAtendimento(antecedenteCirurgicoBase);
				}
			}
			
			// Apaga da base os habitos excluidos do atendimento
			List<HabitoAtendimento> habitosBase = habitoAtendimentoService.retornarHabitosAtendimento(atendimento.getId());
			for (HabitoAtendimento habitoBase : habitosBase) {
				boolean excluido = true;
				for (HabitoAtendimento habitoAtendimento : habitos) {
					if (habitoBase.equals(habitoAtendimento)) {
						excluido = false;
						break;
					}
				}
				
				if (excluido) {
					habitoAtendimentoService.removerHabitoAtendimento(habitoBase);
				}
			}
			
			// Apaga da base as alergias excluidass do atendimento
			List<AlergiaAtendimento> alergiasBase = alergiaAtendimentoService.retornarAlergiasAtendimento(atendimento.getId());
			for (AlergiaAtendimento alergiaBase : alergiasBase) {
				boolean excluido = true;
				for (AlergiaAtendimento alergiaAtendimento : alergias) {
					if (alergiaBase.equals(alergiaAtendimento)) {
						excluido = false;
						break;
					}
				}
				
				if (excluido) {
					alergiaAtendimentoService.removerAlergiaAtendimento(alergiaBase);
				}
			}
			
			// Apaga da base as alergias excluidass do atendimento
			List<VacinaAtendimento> vacinasBase = vacinaAtendimentoService.retornarVacinasAtendimento(atendimento.getId());
			for (VacinaAtendimento vacinaBase : vacinasBase) {
				boolean excluido = true;
				for (VacinaAtendimento vacinaAtendimento : vacinas) {
					if (vacinaBase.equals(vacinaAtendimento)) {
						excluido = false;
						break;
					}
				}
				
				if (excluido) {
					vacinaAtendimentoService.removerVacinaAtendimento(vacinaBase);
				}
			}
		}
		
		//salva medicamentos anteriores para o caso de descontinuidade em algum tratamento
		medicamentoAtendimentoService.salvarMedicamentosAtendimento(medicamentosAnteriores);
		
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
		
		//salva antecedentes clinicos do atendimento
		for (AntecedenteClinicoAtendimento antecedenteClinicoAtendimento : antecedentesClinicos) {
			antecedenteClinicoAtendimento.setAtendimento(atendimento);
		}
		antecedenteClinicoAtendimentoService.salvarAntecedentesClinicosAtendimento(antecedentesClinicos);
		atendimento.setAntecedentesClinicos(new LinkedHashSet<AntecedenteClinicoAtendimento>(antecedentesClinicos));
		
		//salva antecedentes cirurgicos do atendimento
		for (AntecedenteCirurgicoAtendimento antecedenteCirurgicoAtendimento : antecedentesCirurgicos) {
			antecedenteCirurgicoAtendimento.setAtendimento(atendimento);
		}
		antecedenteCirurgicoAtendimentoService.salvarAntecedentesCirurgicosAtendimento(antecedentesCirurgicos);
		atendimento.setAntecedentesCirurgicos(new LinkedHashSet<AntecedenteCirurgicoAtendimento>(antecedentesCirurgicos));
		
		//salva habitos do atendimento
		for (HabitoAtendimento habitoAtendimento : habitos) {
			habitoAtendimento.setAtendimento(atendimento);
		}
		habitoAtendimentoService.salvarHabitosAtendimento(habitos);
		atendimento.setHabitos(new LinkedHashSet<HabitoAtendimento>(habitos));
		
		//salva alergias do atendimento
		for (AlergiaAtendimento alergiaAtendimento : alergias) {
			alergiaAtendimento.setAtendimento(atendimento);
		}
		alergiaAtendimentoService.salvarAlergiasAtendimento(alergias);
		atendimento.setAlergias(new LinkedHashSet<AlergiaAtendimento>(alergias));
		
		//salva Vacinas do atendimento
		for (VacinaAtendimento vacinaAtendimento : vacinas) {
			vacinaAtendimento.setAtendimento(atendimento);
		}
		vacinaAtendimentoService.salvarVacinasAtendimento(vacinas);
		atendimento.setVacinas(new LinkedHashSet<VacinaAtendimento>(vacinas));

		//salva o atendimento
		atendimentoDao.save(atendimento);

		return atendimento;
	}
}
