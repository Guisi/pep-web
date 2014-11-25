package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.utfpr.model.Convenio;
import br.edu.utfpr.model.Convenio_;

public class ConvenioDao extends GenericDao<Convenio, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Convenio> retornarConvenios() {
		return findAll(Convenio_.descricao);
	}
	
	public Convenio retornarConvenioPorDescricao(String descricao) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Convenio> q = qb.createQuery(Convenio.class);
		Root<Convenio> root = q.from(Convenio.class);
		q.where(qb.equal(root.get(Convenio_.descricao), descricao));
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
