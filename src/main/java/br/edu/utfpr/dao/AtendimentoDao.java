package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.edu.utfpr.model.AlergiaAtendimento;
import br.edu.utfpr.model.AlergiaAtendimento_;
import br.edu.utfpr.model.AntecedenteCirurgicoAtendimento;
import br.edu.utfpr.model.AntecedenteCirurgicoAtendimento_;
import br.edu.utfpr.model.AntecedenteClinicoAtendimento;
import br.edu.utfpr.model.AntecedenteClinicoAtendimento_;
import br.edu.utfpr.model.AntecedenteFamiliarAtendimento;
import br.edu.utfpr.model.AntecedenteFamiliarAtendimento_;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.DoencaDiagnosticadaAtendimento;
import br.edu.utfpr.model.DoencaDiagnosticadaAtendimento_;
import br.edu.utfpr.model.ExameAtendimento;
import br.edu.utfpr.model.ExameAtendimento_;
import br.edu.utfpr.model.HabitoAtendimento;
import br.edu.utfpr.model.HabitoAtendimento_;
import br.edu.utfpr.model.MedicamentoAtendimento;
import br.edu.utfpr.model.MedicamentoAtendimento_;
import br.edu.utfpr.model.QueixaPrincipalAtendimento;
import br.edu.utfpr.model.QueixaPrincipalAtendimento_;
import br.edu.utfpr.model.Usuario_;
import br.edu.utfpr.model.VacinaAtendimento;
import br.edu.utfpr.model.VacinaAtendimento_;

public class AtendimentoDao extends GenericDao<Atendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Atendimento> retornarAtendimentosPaciente(Long idPaciente) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Atendimento> q = qb.createQuery(Atendimento.class);
		Root<Atendimento> root = q.from(Atendimento.class);

		q.where(qb.equal(root.get(Atendimento_.paciente).get(Usuario_.id), idPaciente));
		
		q.orderBy(qb.desc(root.get(Atendimento_.data)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<Atendimento> retornarAtendimentosAnterioresPaciente(Long idPaciente, Long idAtendimentoAtual) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Atendimento> q = qb.createQuery(Atendimento.class);
		Root<Atendimento> root = q.from(Atendimento.class);
		
		Fetch<Atendimento, MedicamentoAtendimento> medicamentosFetch = root.fetch(Atendimento_.medicamentos, JoinType.LEFT);
		medicamentosFetch.fetch(MedicamentoAtendimento_.medicamento, JoinType.LEFT);
		
		Fetch<Atendimento, QueixaPrincipalAtendimento> queixasPrincipaisFetch = root.fetch(Atendimento_.queixasPrincipais, JoinType.LEFT);
		queixasPrincipaisFetch.fetch(QueixaPrincipalAtendimento_.queixaPrincipal, JoinType.LEFT);
		
		Fetch<Atendimento, AntecedenteClinicoAtendimento> antecedenteClinicosFetch = root.fetch(Atendimento_.antecedentesClinicos, JoinType.LEFT);
		antecedenteClinicosFetch.fetch(AntecedenteClinicoAtendimento_.doenca, JoinType.LEFT);
		
		Fetch<Atendimento, AntecedenteCirurgicoAtendimento> antecedenteCirurgicosFetch = root.fetch(Atendimento_.antecedentesCirurgicos, JoinType.LEFT);
		antecedenteCirurgicosFetch.fetch(AntecedenteCirurgicoAtendimento_.procedimento, JoinType.LEFT);
		
		Fetch<Atendimento, HabitoAtendimento> habitosFetch = root.fetch(Atendimento_.habitos, JoinType.LEFT);
		habitosFetch.fetch(HabitoAtendimento_.habito, JoinType.LEFT);
		
		Fetch<Atendimento, AlergiaAtendimento> alergiasFetch = root.fetch(Atendimento_.alergias, JoinType.LEFT);
		alergiasFetch.fetch(AlergiaAtendimento_.alergia, JoinType.LEFT);
		
		Fetch<Atendimento, VacinaAtendimento> vacinasFetch = root.fetch(Atendimento_.vacinas, JoinType.LEFT);
		vacinasFetch.fetch(VacinaAtendimento_.vacina, JoinType.LEFT);
		
		Fetch<Atendimento, AntecedenteFamiliarAtendimento> antecedentesFamiliaresFetch = root.fetch(Atendimento_.antecedentesFamiliares, JoinType.LEFT);
		antecedentesFamiliaresFetch.fetch(AntecedenteFamiliarAtendimento_.doenca, JoinType.LEFT);
		
		Fetch<Atendimento, DoencaDiagnosticadaAtendimento> doencasDiagnosticadasFetch = root.fetch(Atendimento_.doencasDiagnosticadas, JoinType.LEFT);
		doencasDiagnosticadasFetch.fetch(DoencaDiagnosticadaAtendimento_.doenca, JoinType.LEFT);
		
		Fetch<Atendimento, ExameAtendimento> examesSolicitadosFetch = root.fetch(Atendimento_.examesSolicitados, JoinType.LEFT);
		examesSolicitadosFetch.fetch(ExameAtendimento_.exame, JoinType.LEFT);
		
		List<Predicate> predicados = new ArrayList<>();
		predicados.add(qb.equal(root.get(Atendimento_.paciente).get(Usuario_.id), idPaciente));
		
		if (idAtendimentoAtual != null) {
			predicados.add(qb.lessThan(root.get(Atendimento_.id), idAtendimentoAtual));
		}
		
		q.distinct(true);
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.desc(root.get(Atendimento_.data)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public Atendimento retornarAtendimentoPorId(Long id) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Atendimento> q = qb.createQuery(Atendimento.class);
		Root<Atendimento> root = q.from(Atendimento.class);
		root.fetch(Atendimento_.paciente);

		Fetch<Atendimento, MedicamentoAtendimento> medicamentosFetch = root.fetch(Atendimento_.medicamentos, JoinType.LEFT);
		medicamentosFetch.fetch(MedicamentoAtendimento_.medicamento, JoinType.LEFT);
		
		Fetch<Atendimento, QueixaPrincipalAtendimento> queixasPrincipaisFetch = root.fetch(Atendimento_.queixasPrincipais, JoinType.LEFT);
		queixasPrincipaisFetch.fetch(QueixaPrincipalAtendimento_.queixaPrincipal, JoinType.LEFT);
		
		Fetch<Atendimento, AntecedenteClinicoAtendimento> antecedenteClinicoFetch = root.fetch(Atendimento_.antecedentesClinicos, JoinType.LEFT);
		antecedenteClinicoFetch.fetch(AntecedenteClinicoAtendimento_.doenca, JoinType.LEFT);
		
		Fetch<Atendimento, AntecedenteCirurgicoAtendimento> antecedenteCirurgicosFetch = root.fetch(Atendimento_.antecedentesCirurgicos, JoinType.LEFT);
		antecedenteCirurgicosFetch.fetch(AntecedenteCirurgicoAtendimento_.procedimento, JoinType.LEFT);
		
		Fetch<Atendimento, HabitoAtendimento> habitosFetch = root.fetch(Atendimento_.habitos, JoinType.LEFT);
		habitosFetch.fetch(HabitoAtendimento_.habito, JoinType.LEFT);
		
		Fetch<Atendimento, AlergiaAtendimento> alergiasFetch = root.fetch(Atendimento_.alergias, JoinType.LEFT);
		alergiasFetch.fetch(AlergiaAtendimento_.alergia, JoinType.LEFT);
		
		Fetch<Atendimento, VacinaAtendimento> vacinasFetch = root.fetch(Atendimento_.vacinas, JoinType.LEFT);
		vacinasFetch.fetch(VacinaAtendimento_.vacina, JoinType.LEFT);
		
		Fetch<Atendimento, AntecedenteFamiliarAtendimento> antecedentesFamiliaresFetch = root.fetch(Atendimento_.antecedentesFamiliares, JoinType.LEFT);
		antecedentesFamiliaresFetch.fetch(AntecedenteFamiliarAtendimento_.doenca, JoinType.LEFT);
		
		Fetch<Atendimento, DoencaDiagnosticadaAtendimento> doencasDiagnosticadasFetch = root.fetch(Atendimento_.doencasDiagnosticadas, JoinType.LEFT);
		doencasDiagnosticadasFetch.fetch(DoencaDiagnosticadaAtendimento_.doenca, JoinType.LEFT);
		
		Fetch<Atendimento, ExameAtendimento> examesSolicitadosFetch = root.fetch(Atendimento_.examesSolicitados, JoinType.LEFT);
		examesSolicitadosFetch.fetch(ExameAtendimento_.exame, JoinType.LEFT);
		
		q.where(qb.equal(root.get(Atendimento_.id), id));
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
