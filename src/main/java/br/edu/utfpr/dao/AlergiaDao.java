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

import br.edu.utfpr.model.Alergia;
import br.edu.utfpr.model.AlergiaAtendimento;
import br.edu.utfpr.model.AlergiaAtendimento_;
import br.edu.utfpr.model.Alergia_;

public class AlergiaDao extends GenericDao<Alergia, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Alergia> retornarAlergias(String textoPesquisa, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Alergia> q = qb.createQuery(Alergia.class);
		Root<Alergia> root = q.from(Alergia.class);

		List<Predicate> predicados = new ArrayList<>();
		if (StringUtils.isNotBlank(textoPesquisa)) {
			predicados.add(qb.like(qb.lower(root.get(Alergia_.descricao)), "%" + textoPesquisa.toLowerCase() + "%"));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Alergia_.chkAtivo), chkAtivo));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.asc(root.get(Alergia_.descricao)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<Alergia> retornarAlergiasMaisUsados(Integer quantidade, List<Long> idsAlergiasIgnorar, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Alergia> cq = qb.createQuery(Alergia.class);
		Root<Alergia> rootAlergia = cq.from(Alergia.class);
		SetJoin<Alergia, AlergiaAtendimento> AlergiasAtendimentoJoin = rootAlergia.join(Alergia_.alergiasAtendimentos, JoinType.LEFT);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (idsAlergiasIgnorar != null && !idsAlergiasIgnorar.isEmpty()) {
			predicados.add(qb.not(rootAlergia.get(Alergia_.id).in(idsAlergiasIgnorar)));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(rootAlergia.get(Alergia_.chkAtivo), chkAtivo));
		}
		
		if (!predicados.isEmpty()) {
			cq.where(predicados.toArray(new Predicate[predicados.size()]));
		}
		
		cq.groupBy(rootAlergia.get(Alergia_.id));
		cq.orderBy(qb.desc(qb.count(AlergiasAtendimentoJoin.get(AlergiaAtendimento_.id))), qb.asc(rootAlergia.get(Alergia_.descricao)));
		
		return entityManager.createQuery(cq).setMaxResults(quantidade).getResultList();
	}
	
	public Alergia retornarAlergia(String descricao) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Alergia> q = qb.createQuery(Alergia.class);
		Root<Alergia> root = q.from(Alergia.class);
		
		Predicate predicate = qb.equal(qb.lower(root.get(Alergia_.descricao)), StringUtils.lowerCase(descricao));
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
