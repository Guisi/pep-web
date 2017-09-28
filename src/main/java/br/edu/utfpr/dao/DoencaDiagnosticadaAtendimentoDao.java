package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import br.edu.utfpr.constants.StatusDoenca;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.model.DoencaDiagnosticadaAtendimento;
import br.edu.utfpr.model.DoencaDiagnosticadaAtendimento_;
import br.edu.utfpr.model.Doenca_;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.model.Usuario_;

public class DoencaDiagnosticadaAtendimentoDao extends GenericDao<DoencaDiagnosticadaAtendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<DoencaDiagnosticadaAtendimento> retornarDoencasDiagnosticadasAtendimento(Long idAtendimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoencaDiagnosticadaAtendimento> q = qb.createQuery(DoencaDiagnosticadaAtendimento.class);
		Root<DoencaDiagnosticadaAtendimento> root = q.from(DoencaDiagnosticadaAtendimento.class);

		q.where(qb.equal(root.get(DoencaDiagnosticadaAtendimento_.atendimento).get(Atendimento_.id), idAtendimento));
		
		return entityManager.createQuery(q).getResultList();
	}

	public List<DoencaDiagnosticadaAtendimento> retornarDoencasDiagnosticadasAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoencaDiagnosticadaAtendimento> q = qb.createQuery(DoencaDiagnosticadaAtendimento.class);
		Root<DoencaDiagnosticadaAtendimento> root = q.from(DoencaDiagnosticadaAtendimento.class);
		Join<DoencaDiagnosticadaAtendimento, Atendimento> atendimentoJoin = root.join(DoencaDiagnosticadaAtendimento_.atendimento);
		root.fetch(DoencaDiagnosticadaAtendimento_.doenca, JoinType.LEFT);

		Predicate predicado = qb.equal(atendimentoJoin.get(Atendimento_.paciente).get(Usuario_.id), idPaciente);
		// doencas resolvidas nao aparecem mais em novos atendimentos
		predicado = qb.and(predicado, qb.notEqual(root.get(DoencaDiagnosticadaAtendimento_.statusDoenca), StatusDoenca.RESOLVIDO));

		if (idAtendimentoIgnorar != null) {
			predicado = qb.and(predicado, qb.lessThan(atendimentoJoin.get(Atendimento_.id), idAtendimentoIgnorar));
		}
		
		q.where(predicado);
		q.orderBy(qb.desc(atendimentoJoin.get(Atendimento_.id)), qb.asc(root.get(DoencaDiagnosticadaAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<Doenca> retornarDoencasDiagnosticadasMaisUsadas(Integer quantidade, List<Long> idsDoencasIgnorar, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doenca> cq = qb.createQuery(Doenca.class);
		Root<Doenca> root = cq.from(Doenca.class);
		SetJoin<Doenca, DoencaDiagnosticadaAtendimento> doencasDiagnosticadasAtendimentoJoin = root.join(Doenca_.doencasDiagnosticadasAtendimentos, JoinType.LEFT);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (idsDoencasIgnorar != null && !idsDoencasIgnorar.isEmpty()) {
			predicados.add(qb.not(root.get(Doenca_.id).in(idsDoencasIgnorar)));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Doenca_.chkAtivo), chkAtivo));
		}
		
		if (!predicados.isEmpty()) {
			cq.where(predicados.toArray(new Predicate[predicados.size()]));
		}
		
		cq.groupBy(root.get(Doenca_.id));
		cq.orderBy(qb.desc(qb.count(doencasDiagnosticadasAtendimentoJoin.get(DoencaDiagnosticadaAtendimento_.id))), 
					qb.asc(root.get(Doenca_.codigoCid)), qb.asc(root.get(Doenca_.descricao)));
		
		return entityManager.createQuery(cq).setMaxResults(quantidade).getResultList();
	}
	
	public List<DoencaDiagnosticadaAtendimento> retornarDoencasDiagnosticadas(Long idPaciente) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DoencaDiagnosticadaAtendimento> q = qb.createQuery(DoencaDiagnosticadaAtendimento.class);
		Root<DoencaDiagnosticadaAtendimento> root = q.from(DoencaDiagnosticadaAtendimento.class);
		
		Join<DoencaDiagnosticadaAtendimento, Atendimento> atendimentoDoencaDiagnoticadaJoin = root.join(DoencaDiagnosticadaAtendimento_.atendimento);
		Join<Atendimento, Usuario> pacienteJoin = atendimentoDoencaDiagnoticadaJoin.join(Atendimento_.paciente);
		root.fetch(DoencaDiagnosticadaAtendimento_.doenca, JoinType.LEFT);
		
		q.where(qb.equal(pacienteJoin.get(Usuario_.id), idPaciente));

		q.orderBy(qb.asc(root.get(DoencaDiagnosticadaAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
}
