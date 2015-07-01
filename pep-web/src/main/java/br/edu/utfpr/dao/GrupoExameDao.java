package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.model.GrupoExame;
import br.edu.utfpr.model.GrupoExame_;

public class GrupoExameDao extends GenericDao<GrupoExame, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<GrupoExame> retornarGruposExames(String textoPesquisa, Boolean chkAtivo, Boolean fetchExame) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrupoExame> q = qb.createQuery(GrupoExame.class);
		Root<GrupoExame> root = q.from(GrupoExame.class);
		
		if (fetchExame) {
			q.select(root);
			q.distinct(true);
			root.fetch(GrupoExame_.exames);
		}

		List<Predicate> predicados = new ArrayList<>();
		if (StringUtils.isNotBlank(textoPesquisa)) {
			predicados.add(qb.like(qb.lower(root.get(GrupoExame_.descricao)), "%" + textoPesquisa.toLowerCase() + "%"));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(GrupoExame_.chkAtivo), chkAtivo));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.asc(root.get(GrupoExame_.descricao)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public GrupoExame retornarGrupoExame(String descricao) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrupoExame> q = qb.createQuery(GrupoExame.class);
		Root<GrupoExame> root = q.from(GrupoExame.class);
		
		Predicate predicate = qb.equal(qb.lower(root.get(GrupoExame_.descricao)), StringUtils.lowerCase(descricao));
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
	
	public GrupoExame retornarGrupoExamePorId(Long id) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<GrupoExame> q = qb.createQuery(GrupoExame.class);
		Root<GrupoExame> root = q.from(GrupoExame.class);
		root.fetch(GrupoExame_.exames);
		
		q.where(qb.equal(root.get(GrupoExame_.id), id));
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
