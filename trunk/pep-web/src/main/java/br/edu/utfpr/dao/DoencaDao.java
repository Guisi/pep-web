package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.model.Doenca_;

public class DoencaDao extends GenericDao<Doenca, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Doenca> retornarDoencas(String textoPesquisa, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doenca> q = qb.createQuery(Doenca.class);
		Root<Doenca> root = q.from(Doenca.class);

		List<Predicate> predicados = new ArrayList<>();
		if (StringUtils.isNotBlank(textoPesquisa)) {
			predicados.add(qb.like(qb.lower(root.get(Doenca_.descricao)), "%" + textoPesquisa.toLowerCase() + "%"));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Doenca_.chkAtivo), chkAtivo));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.asc(root.get(Doenca_.codigoCid)), qb.asc(root.get(Doenca_.descricao)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public Doenca retornarDoenca(String codigoCid) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doenca> q = qb.createQuery(Doenca.class);
		Root<Doenca> root = q.from(Doenca.class);
		
		Predicate predicate = qb.equal(qb.lower(root.get(Doenca_.codigoCid)), StringUtils.lowerCase(codigoCid));
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
