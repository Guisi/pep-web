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

import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.Habito;
import br.edu.utfpr.model.HabitoAtendimento;
import br.edu.utfpr.model.HabitoAtendimento_;
import br.edu.utfpr.model.Habito_;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.model.Usuario_;

public class HabitoAtendimentoDao extends GenericDao<HabitoAtendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<HabitoAtendimento> retornarHabitosAtendimento(Long idAtendimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<HabitoAtendimento> q = qb.createQuery(HabitoAtendimento.class);
		Root<HabitoAtendimento> root = q.from(HabitoAtendimento.class);

		q.where(qb.equal(root.get(HabitoAtendimento_.atendimento).get(Atendimento_.id), idAtendimento));
		
		return entityManager.createQuery(q).getResultList();
	}

	public List<HabitoAtendimento> retornarHabitosAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<HabitoAtendimento> q = qb.createQuery(HabitoAtendimento.class);
		Root<HabitoAtendimento> root = q.from(HabitoAtendimento.class);
		Join<HabitoAtendimento, Atendimento> atendimentoJoin = root.join(HabitoAtendimento_.atendimento);
		root.fetch(HabitoAtendimento_.habito, JoinType.LEFT);

		List<Predicate> predicados = new ArrayList<>();
		predicados.add(qb.equal(atendimentoJoin.get(Atendimento_.paciente).get(Usuario_.id), idPaciente));

		if (idAtendimentoIgnorar != null) {
			predicados.add(qb.lessThan(atendimentoJoin.get(Atendimento_.id), idAtendimentoIgnorar));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.desc(atendimentoJoin.get(Atendimento_.id)), qb.asc(root.get(HabitoAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<Habito> retornarHabitosMaisUsados(Integer quantidade, List<Long> idsHabitosIgnorar, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Habito> cq = qb.createQuery(Habito.class);
		Root<Habito> root = cq.from(Habito.class);
		SetJoin<Habito, HabitoAtendimento> habitosAtendimentoJoin = root.join(Habito_.habitosAtendimentos, JoinType.LEFT);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (idsHabitosIgnorar != null && !idsHabitosIgnorar.isEmpty()) {
			predicados.add(qb.not(root.get(Habito_.id).in(idsHabitosIgnorar)));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Habito_.chkAtivo), chkAtivo));
		}
		
		if (!predicados.isEmpty()) {
			cq.where(predicados.toArray(new Predicate[predicados.size()]));
		}
		
		cq.groupBy(root.get(Habito_.id));
		cq.orderBy(qb.desc(qb.count(habitosAtendimentoJoin.get(HabitoAtendimento_.id))), 
					qb.asc(root.get(Habito_.descricao)));
		
		return entityManager.createQuery(cq).setMaxResults(quantidade).getResultList();
	}
	
	public List<HabitoAtendimento> retornarHabitosPaciente(Long idPaciente) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<HabitoAtendimento> q = qb.createQuery(HabitoAtendimento.class);
		Root<HabitoAtendimento> root = q.from(HabitoAtendimento.class);
		
		Join<HabitoAtendimento, Atendimento> atendimentoHabitosJoin = root.join(HabitoAtendimento_.atendimento);
		Join<Atendimento, Usuario> pacienteJoin = atendimentoHabitosJoin.join(Atendimento_.paciente);
		root.fetch(HabitoAtendimento_.habito, JoinType.LEFT);
		
		q.where(qb.equal(pacienteJoin.get(Usuario_.id), idPaciente));

		q.orderBy(qb.asc(root.get(HabitoAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
}
