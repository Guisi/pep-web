package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.QueixaPrincipalAtendimento;
import br.edu.utfpr.model.QueixaPrincipalAtendimento_;

public class QueixaPrincipalAtendimentoDao extends GenericDao<QueixaPrincipalAtendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<QueixaPrincipalAtendimento> retornarQueixasPrincipaisAtendimento(Long idAtendimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<QueixaPrincipalAtendimento> q = qb.createQuery(QueixaPrincipalAtendimento.class);
		Root<QueixaPrincipalAtendimento> root = q.from(QueixaPrincipalAtendimento.class);

		q.where(qb.equal(root.get(QueixaPrincipalAtendimento_.atendimento).get(Atendimento_.id), idAtendimento));
		
		return entityManager.createQuery(q).getResultList();
	}
}
