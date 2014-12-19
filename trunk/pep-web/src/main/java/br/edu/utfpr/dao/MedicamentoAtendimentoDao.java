package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.MedicamentoAtendimento;
import br.edu.utfpr.model.MedicamentoAtendimento_;

public class MedicamentoAtendimentoDao extends GenericDao<MedicamentoAtendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<MedicamentoAtendimento> retornarMedicamentosAtendimento(Long idAtendimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<MedicamentoAtendimento> q = qb.createQuery(MedicamentoAtendimento.class);
		Root<MedicamentoAtendimento> root = q.from(MedicamentoAtendimento.class);

		q.where(qb.equal(root.get(MedicamentoAtendimento_.atendimento).get(Atendimento_.id), idAtendimento));
		
		return entityManager.createQuery(q).getResultList();
	}
}
