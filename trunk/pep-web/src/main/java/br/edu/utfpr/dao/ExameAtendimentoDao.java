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

import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.ExameAtendimento;
import br.edu.utfpr.model.ExameAtendimento_;
import br.edu.utfpr.model.Usuario_;

public class ExameAtendimentoDao extends GenericDao<ExameAtendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<ExameAtendimento> retornarExamesAtendimento(Long idAtendimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ExameAtendimento> q = qb.createQuery(ExameAtendimento.class);
		Root<ExameAtendimento> root = q.from(ExameAtendimento.class);

		q.where(qb.equal(root.get(ExameAtendimento_.atendimento).get(Atendimento_.id), idAtendimento));
		
		return entityManager.createQuery(q).getResultList();
	}

	public List<ExameAtendimento> retornarExamesAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ExameAtendimento> q = qb.createQuery(ExameAtendimento.class);
		Root<ExameAtendimento> root = q.from(ExameAtendimento.class);
		Join<ExameAtendimento, Atendimento> atendimentoJoin = root.join(ExameAtendimento_.atendimento);
		root.fetch(ExameAtendimento_.exame, JoinType.LEFT);

		List<Predicate> predicados = new ArrayList<>();
		predicados.add(qb.equal(atendimentoJoin.get(Atendimento_.paciente).get(Usuario_.id), idPaciente));

		if (idAtendimentoIgnorar != null) {
			predicados.add(qb.lessThan(atendimentoJoin.get(Atendimento_.id), idAtendimentoIgnorar));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.desc(atendimentoJoin.get(Atendimento_.id)), qb.asc(root.get(ExameAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
}
