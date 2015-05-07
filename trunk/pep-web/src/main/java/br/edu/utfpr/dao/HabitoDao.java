package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.model.Habito;
import br.edu.utfpr.model.HabitoAtendimento;
import br.edu.utfpr.model.HabitoAtendimento_;
import br.edu.utfpr.model.Habito_;

public class HabitoDao extends GenericDao<Habito, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Habito> retornarhabitos(String textoPesquisa, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Habito> q = qb.createQuery(Habito.class);
		Root<Habito> root = q.from(Habito.class);

		List<Predicate> predicados = new ArrayList<>();
		if (StringUtils.isNotBlank(textoPesquisa)) {
			predicados.add(qb.like(qb.lower(root.get(Habito_.descricao)), "%" + textoPesquisa.toLowerCase() + "%"));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Habito_.chkAtivo), chkAtivo));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.asc(root.get(Habito_.descricao)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<Habito> retornarHabitosMaisUsados(Integer quantidade, List<Long> idsHabitosIgnorar, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Habito> cq = qb.createQuery(Habito.class);
		Root<Habito> rootHabito = cq.from(Habito.class);
		SetJoin<Habito, HabitoAtendimento> habitosAtendimentoJoin = rootHabito.join(Habito_.habitosAtendimentos, JoinType.LEFT);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (idsHabitosIgnorar != null && !idsHabitosIgnorar.isEmpty()) {
			predicados.add(qb.not(rootHabito.get(Habito_.id).in(idsHabitosIgnorar)));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(rootHabito.get(Habito_.chkAtivo), chkAtivo));
		}
		
		if (!predicados.isEmpty()) {
			cq.where(predicados.toArray(new Predicate[predicados.size()]));
		}
		
		cq.groupBy(rootHabito.get(Habito_.id));
		cq.orderBy(qb.desc(qb.count(habitosAtendimentoJoin.get(HabitoAtendimento_.id))), qb.asc(rootHabito.get(Habito_.descricao)));
		
		return entityManager.createQuery(cq).setMaxResults(quantidade).getResultList();
	}
	
	public Habito retornarHabito(String descricao) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Habito> q = qb.createQuery(Habito.class);
		Root<Habito> root = q.from(Habito.class);
		
		Predicate predicate = qb.equal(qb.lower(root.get(Habito_.descricao)), StringUtils.lowerCase(descricao));
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
