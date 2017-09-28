package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.model.Medicamento;
import br.edu.utfpr.model.Medicamento_;

public class MedicamentoDao extends GenericDao<Medicamento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Medicamento> retornarMedicamentos(String textoPesquisa, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Medicamento> q = qb.createQuery(Medicamento.class);
		Root<Medicamento> root = q.from(Medicamento.class);

		List<Predicate> predicados = new ArrayList<>();
		if (StringUtils.isNotBlank(textoPesquisa)) {
			predicados.add(qb.like(qb.lower(root.get(Medicamento_.principioAtivo)), "%" + StringUtils.lowerCase(textoPesquisa) + "%"));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Medicamento_.chkAtivo), chkAtivo));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		
		q.orderBy(qb.asc(root.get(Medicamento_.principioAtivo)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public Medicamento retornarMedicamento(String principioAtivo, String apresentacao) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Medicamento> q = qb.createQuery(Medicamento.class);
		Root<Medicamento> root = q.from(Medicamento.class);

		Predicate predicate = qb.equal(qb.lower(root.get(Medicamento_.principioAtivo)), StringUtils.lowerCase(principioAtivo));
		predicate = qb.and(predicate, qb.equal(qb.lower(root.get(Medicamento_.apresentacao)), StringUtils.lowerCase(apresentacao)));
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
	
}
