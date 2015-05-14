package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.constants.TipoProcedimento;
import br.edu.utfpr.model.Procedimento;
import br.edu.utfpr.model.Procedimento_;

public class ProcedimentoDao extends GenericDao<Procedimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Procedimento> retornarProcedimentos(String textoPesquisa, Boolean chkAtivo, TipoProcedimento tipoProcedimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Procedimento> q = qb.createQuery(Procedimento.class);
		Root<Procedimento> root = q.from(Procedimento.class);

		List<Predicate> predicados = new ArrayList<>();
		if (StringUtils.isNotBlank(textoPesquisa)) {
			predicados.add(qb.like(qb.lower(root.get(Procedimento_.descricao)), "%" + textoPesquisa.toLowerCase() + "%"));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Procedimento_.chkAtivo), chkAtivo));
		}
		
		if (tipoProcedimento != null) {
			predicados.add(qb.equal(root.get(Procedimento_.tipoProcedimento), tipoProcedimento));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.asc(root.get(Procedimento_.descricao)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public Procedimento retornarProcedimento(String descricao) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Procedimento> q = qb.createQuery(Procedimento.class);
		Root<Procedimento> root = q.from(Procedimento.class);
		
		Predicate predicate = qb.equal(qb.lower(root.get(Procedimento_.descricao)), StringUtils.lowerCase(descricao));
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
