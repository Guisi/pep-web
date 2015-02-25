package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.model.QueixaPrincipal;
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
	
	public QueixaPrincipal retornarQueixaPrincipal(String descricao) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<QueixaPrincipal> q = qb.createQuery(QueixaPrincipal.class);
		Root<QueixaPrincipal> root = q.from(QueixaPrincipal.class);
		
		Predicate predicate = qb.equal(qb.lower(root.get(QueixaPrincipal_.descricao)), StringUtils.lowerCase(descricao));
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
