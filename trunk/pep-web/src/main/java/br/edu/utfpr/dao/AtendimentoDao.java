package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.Usuario_;

public class AtendimentoDao extends GenericDao<Atendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Atendimento> retornarAtendimentosPaciente(Long idPaciente) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Atendimento> q = qb.createQuery(Atendimento.class);
		Root<Atendimento> root = q.from(Atendimento.class);

		q.where(qb.equal(root.get(Atendimento_.paciente).get(Usuario_.id), idPaciente));
		
		q.orderBy(qb.desc(root.get(Atendimento_.data)));
		
		return entityManager.createQuery(q).getResultList();
	}
}
