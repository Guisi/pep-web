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

import br.edu.utfpr.model.QueixaPrincipal;
import br.edu.utfpr.model.QueixaPrincipalAtendimento;
import br.edu.utfpr.model.QueixaPrincipalAtendimento_;
import br.edu.utfpr.model.QueixaPrincipal_;

public class QueixaPrincipalDao extends GenericDao<QueixaPrincipal, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<QueixaPrincipal> retornarQueixasPrincipais(String textoPesquisa, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<QueixaPrincipal> q = qb.createQuery(QueixaPrincipal.class);
		Root<QueixaPrincipal> root = q.from(QueixaPrincipal.class);

		List<Predicate> predicados = new ArrayList<>();
		if (StringUtils.isNotBlank(textoPesquisa)) {
			predicados.add(qb.like(qb.lower(root.get(QueixaPrincipal_.descricao)), "%" + textoPesquisa.toLowerCase() + "%"));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(QueixaPrincipal_.chkAtivo), chkAtivo));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.asc(root.get(QueixaPrincipal_.descricao)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<QueixaPrincipal> retornarQueixasPrincipaisMaisUsadas(Integer quantidade, List<Long> idsQueixasIgnorar, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<QueixaPrincipal> cq = qb.createQuery(QueixaPrincipal.class);
		Root<QueixaPrincipal> rootQueixa = cq.from(QueixaPrincipal.class);
		SetJoin<QueixaPrincipal, QueixaPrincipalAtendimento> queixasAtendimentoJoin = rootQueixa.join(QueixaPrincipal_.queixasPrincipaisAtendimentos, JoinType.LEFT);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (idsQueixasIgnorar != null && !idsQueixasIgnorar.isEmpty()) {
			predicados.add(qb.not(rootQueixa.get(QueixaPrincipal_.id).in(idsQueixasIgnorar)));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(rootQueixa.get(QueixaPrincipal_.chkAtivo), chkAtivo));
		}
		
		if (!predicados.isEmpty()) {
			cq.where(predicados.toArray(new Predicate[predicados.size()]));
		}
		
		cq.groupBy(rootQueixa.get(QueixaPrincipal_.id));
		cq.orderBy(qb.desc(qb.count(queixasAtendimentoJoin.get(QueixaPrincipalAtendimento_.id))), qb.asc(rootQueixa.get(QueixaPrincipal_.descricao)));
		
		return entityManager.createQuery(cq).setMaxResults(quantidade).getResultList();
	}
	
	public QueixaPrincipal retornarQueixaPrincipal(String descricao) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<QueixaPrincipal> q = qb.createQuery(QueixaPrincipal.class);
		Root<QueixaPrincipal> root = q.from(QueixaPrincipal.class);
		
		Predicate predicate = qb.equal(qb.lower(root.get(QueixaPrincipal_.descricao)), StringUtils.lowerCase(descricao));
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
