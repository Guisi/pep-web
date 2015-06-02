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

import br.edu.utfpr.model.Vacina;
import br.edu.utfpr.model.VacinaAtendimento;
import br.edu.utfpr.model.VacinaAtendimento_;
import br.edu.utfpr.model.Vacina_;

public class VacinaDao extends GenericDao<Vacina, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Vacina> retornarVacinas(String textoPesquisa, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Vacina> q = qb.createQuery(Vacina.class);
		Root<Vacina> root = q.from(Vacina.class);

		List<Predicate> predicados = new ArrayList<>();
		if (StringUtils.isNotBlank(textoPesquisa)) {
			predicados.add(qb.like(qb.lower(root.get(Vacina_.descricao)), "%" + textoPesquisa.toLowerCase() + "%"));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Vacina_.chkAtivo), chkAtivo));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.asc(root.get(Vacina_.descricao)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<Vacina> retornarVacinasMaisUsadas(Integer quantidade, List<Long> idsVacinasIgnorar, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Vacina> cq = qb.createQuery(Vacina.class);
		Root<Vacina> rootVacina = cq.from(Vacina.class);
		SetJoin<Vacina, VacinaAtendimento> vacinasAtendimentoJoin = rootVacina.join(Vacina_.vacinasAtendimentos, JoinType.LEFT);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (idsVacinasIgnorar != null && !idsVacinasIgnorar.isEmpty()) {
			predicados.add(qb.not(rootVacina.get(Vacina_.id).in(idsVacinasIgnorar)));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(rootVacina.get(Vacina_.chkAtivo), chkAtivo));
		}
		
		if (!predicados.isEmpty()) {
			cq.where(predicados.toArray(new Predicate[predicados.size()]));
		}
		
		cq.groupBy(rootVacina.get(Vacina_.id));
		cq.orderBy(qb.desc(qb.count(vacinasAtendimentoJoin.get(VacinaAtendimento_.id))), qb.asc(rootVacina.get(Vacina_.descricao)));
		
		return entityManager.createQuery(cq).setMaxResults(quantidade).getResultList();
	}
	
	public Vacina retornarVacina(String descricao) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Vacina> q = qb.createQuery(Vacina.class);
		Root<Vacina> root = q.from(Vacina.class);
		
		Predicate predicate = qb.equal(qb.lower(root.get(Vacina_.descricao)), StringUtils.lowerCase(descricao));
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
