package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.utfpr.model.Especialidade;
import br.edu.utfpr.model.Especialidade_;

public class EspecialidadeDao extends GenericDao<Especialidade, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Especialidade> retornarEspecialidades() {
		return findAll(Especialidade_.descricao);
	}
	
	public Especialidade retornarEspecialidadePorDescricao(String descricao) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Especialidade> q = qb.createQuery(Especialidade.class);
		Root<Especialidade> root = q.from(Especialidade.class);
		q.where(qb.equal(root.get(Especialidade_.descricao), descricao));
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
