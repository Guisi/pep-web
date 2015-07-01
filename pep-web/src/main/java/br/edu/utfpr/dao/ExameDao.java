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

import br.edu.utfpr.model.Exame;
import br.edu.utfpr.model.ExameAtendimento;
import br.edu.utfpr.model.ExameAtendimento_;
import br.edu.utfpr.model.Exame_;

public class ExameDao extends GenericDao<Exame, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Exame> retornarExames(String textoPesquisa, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Exame> q = qb.createQuery(Exame.class);
		Root<Exame> root = q.from(Exame.class);

		List<Predicate> predicados = new ArrayList<>();
		if (StringUtils.isNotBlank(textoPesquisa)) {
			predicados.add(qb.like(qb.lower(root.get(Exame_.descricao)), "%" + textoPesquisa.toLowerCase() + "%"));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Exame_.chkAtivo), chkAtivo));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.asc(root.get(Exame_.descricao)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<Exame> retornarExamesMaisUsados(Integer quantidade, List<Long> idsExamesIgnorar, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Exame> cq = qb.createQuery(Exame.class);
		Root<Exame> rootExame = cq.from(Exame.class);
		SetJoin<Exame, ExameAtendimento> examesAtendimentoJoin = rootExame.join(Exame_.examesAtendimentos, JoinType.LEFT);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (idsExamesIgnorar != null && !idsExamesIgnorar.isEmpty()) {
			predicados.add(qb.not(rootExame.get(Exame_.id).in(idsExamesIgnorar)));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(rootExame.get(Exame_.chkAtivo), chkAtivo));
		}
		
		if (!predicados.isEmpty()) {
			cq.where(predicados.toArray(new Predicate[predicados.size()]));
		}
		
		cq.groupBy(rootExame.get(Exame_.id));
		cq.orderBy(qb.desc(qb.count(examesAtendimentoJoin.get(ExameAtendimento_.id))), qb.asc(rootExame.get(Exame_.descricao)));
		
		return entityManager.createQuery(cq).setMaxResults(quantidade).getResultList();
	}
	
	public Exame retornarExame(String descricao) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Exame> q = qb.createQuery(Exame.class);
		Root<Exame> root = q.from(Exame.class);
		
		Predicate predicate = qb.equal(qb.lower(root.get(Exame_.descricao)), StringUtils.lowerCase(descricao));
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
