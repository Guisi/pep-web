package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.model.Perfil_;

public class PerfilDao extends GenericDao<Perfil, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Perfil> retornarPerfis() {
		return findAll(Perfil_.nome);
	}
	
	public Perfil retornarPerfilPorNome(String nome) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Perfil> q = qb.createQuery(Perfil.class);
		Root<Perfil> root = q.from(Perfil.class);
		q.where(qb.equal(root.get(Perfil_.nome), nome));
		
		return entityManager.createQuery(q).getSingleResult();
	}
	
	public Perfil retornarPerfilPorId(Long id) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Perfil> q = qb.createQuery(Perfil.class);
		Root<Perfil> root = q.from(Perfil.class);
		root.fetch(Perfil_.autorizacoes);
		q.where(qb.equal(root.get(Perfil_.id), id));
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
