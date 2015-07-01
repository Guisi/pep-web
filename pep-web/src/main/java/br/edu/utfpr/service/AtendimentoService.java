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
import br.edu.utfpr.model.AntecedenteFamiliarAtendimento;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.DoencaDiagnosticadaAtendimento;
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
	@Inject
	private AntecedenteFamiliarAtendimentoService antecedenteFamiliarAtendimentoService;
	@Inject
	private DoencaDiagnosticadaAtendimentoService doencaDiagnosticadaAtendimentoService;
	
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
			List<HabitoAtendimento> habitos, List<AlergiaAtendimento> alergias, List<VacinaAtendimento> vacinas,
			List<AntecedenteFamiliarAtendimento> antecedentesFamiliares, List<DoencaDiagnosticadaAtendimento> doencasDiagnosticadas,
			List<DoencaDiagnosticadaAtendimento> doencasDiagnosticadasAnteriores) {
		
		if (atendimento.isNew()) {
			atendimento.setData(new Date());
		} else {
			// Apaga da base as queixas principais excluidas do atendimento
			queixaPrincipalAtendimentoService.removerQueixasPrincipaisExcluidas(atendimento.getId(), queixasPrincipais);
			
			// Apaga da base os medicamentos excluidos do atendimento
			medicamentoAtendimentoService.removerMedicamentosExcluidos(atendimento.getId(), medicamentos);
			
			// Apaga da base os antecedentes clinicos excluidos do atendimento
			antecedenteClinicoAtendimentoService.removerAntecedentesClinicosExcluidos(atendimento.getId(), antecedentesClinicos);
			
			// Apaga da base os antecedentes cirurgicos excluidos do atendimento
			antecedenteCirurgicoAtendimentoService.removerAntecedentesCirurgicosExcluidos(atendimento.getId(), antecedentesCirurgicos);
			
			// Apaga da base os habitos excluidos do atendimento
			habitoAtendimentoService.removerHabitosExcluidos(atendimento.getId(), habitos);
			
			// Apaga da base as alergias excluidas do atendimento
			alergiaAtendimentoService.removerAlergiasExcluidas(atendimento.getId(), alergias);
			
			// Apaga da base as vacinas excluidas do atendimento
			vacinaAtendimentoService.removerVacinasExcluidas(atendimento.getId(), vacinas);
			
			// Apaga da base os antecedentes familiares excluidos do atendimento
			antecedenteFamiliarAtendimentoService.removerAntecedentesFamiliaresExcluidos(atendimento.getId(), antecedentesFamiliares);
			
			// Apaga da base as doenças diagnosticadas excluidos do atendimento
			doencaDiagnosticadaAtendimentoService.removerDoencasDiagnosticadasExcluidas(atendimento.getId(), doencasDiagnosticadas);
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
		
		//salva vacinas do atendimento
		for (VacinaAtendimento vacinaAtendimento : vacinas) {
			vacinaAtendimento.setAtendimento(atendimento);
		}
		vacinaAtendimentoService.salvarVacinasAtendimento(vacinas);
		atendimento.setVacinas(new LinkedHashSet<VacinaAtendimento>(vacinas));
		
		//salva antecedentes familiares do atendimento
		for (AntecedenteFamiliarAtendimento antecedenteFamiliarAtendimento : antecedentesFamiliares) {
			antecedenteFamiliarAtendimento.setAtendimento(atendimento);
		}
		antecedenteFamiliarAtendimentoService.salvarAntecedentesFamiliaresAtendimento(antecedentesFamiliares);
		atendimento.setAntecedentesFamiliares(new LinkedHashSet<AntecedenteFamiliarAtendimento>(antecedentesFamiliares));
		
		//salva doencas diagnosticadas do atendimento
		for (DoencaDiagnosticadaAtendimento doencaDiagnosticadaAtendimento : doencasDiagnosticadas) {
			doencaDiagnosticadaAtendimento.setAtendimento(atendimento);
		}
		doencaDiagnosticadaAtendimentoService.salvarDoencasDiagnosticadasAtendimento(doencasDiagnosticadas);
		atendimento.setDoencasDiagnosticadas(new LinkedHashSet<DoencaDiagnosticadaAtendimento>(doencasDiagnosticadas));
		
		//salva doencas diagnosticadas anteriormente em caso de resolução de alguma
		doencaDiagnosticadaAtendimentoService.salvarDoencasDiagnosticadasAtendimento(doencasDiagnosticadasAnteriores);
		
		//seta o atendimento no exame fisico
		atendimento.getExameFisicoAtendimento().setAtendimento(atendimento);

		//salva o atendimento
		atendimentoDao.save(atendimento);

		return atendimento;
	}
}
