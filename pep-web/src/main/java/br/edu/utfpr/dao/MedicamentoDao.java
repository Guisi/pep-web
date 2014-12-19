package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.edu.utfpr.model.Medicamento;
import br.edu.utfpr.model.Medicamento_;

import com.ocpsoft.pretty.faces.util.StringUtils;

public class MedicamentoDao extends GenericDao<Medicamento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Medicamento> retornarMedicamentos(String textoPesquisa, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Medicamento> q = qb.createQuery(Medicamento.class);
		Root<Medicamento> root = q.from(Medicamento.class);

		List<Predicate> predicados = new ArrayList<>();
		if (StringUtils.isNotBlank(textoPesquisa)) {
			predicados.add(qb.like(qb.lower(root.get(Medicamento_.principioAtivo)), "%" + textoPesquisa.toLowerCase() + "%"));
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
		
		Predicate predicate = qb.equal(root.get(Medicamento_.principioAtivo), principioAtivo);
		predicate = qb.and(predicate, qb.equal(root.get(Medicamento_.apresentacao), apresentacao));
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
	
}
