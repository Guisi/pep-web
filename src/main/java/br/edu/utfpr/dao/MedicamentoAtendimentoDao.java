package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.MedicamentoAtendimento;
import br.edu.utfpr.model.MedicamentoAtendimento_;
import br.edu.utfpr.model.Usuario_;

public class MedicamentoAtendimentoDao extends GenericDao<MedicamentoAtendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<MedicamentoAtendimento> retornarMedicamentosAtendimento(Long idAtendimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<MedicamentoAtendimento> q = qb.createQuery(MedicamentoAtendimento.class);
		Root<MedicamentoAtendimento> root = q.from(MedicamentoAtendimento.class);

		q.where(qb.equal(root.get(MedicamentoAtendimento_.atendimento).get(Atendimento_.id), idAtendimento));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<MedicamentoAtendimento> retornarMedicamentosEmUsoPaciente(Long idPaciente, Long idAtendimentoIgnorar) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<MedicamentoAtendimento> q = qb.createQuery(MedicamentoAtendimento.class);
		Root<MedicamentoAtendimento> root = q.from(MedicamentoAtendimento.class);
		Join<MedicamentoAtendimento, Atendimento> atendimentoJoin = root.join(MedicamentoAtendimento_.atendimento);
		root.fetch(MedicamentoAtendimento_.medicamento, JoinType.LEFT);

		List<Predicate> predicados = new ArrayList<>();
		predicados.add(qb.equal(root.get(MedicamentoAtendimento_.emUso), Boolean.TRUE));
		predicados.add(qb.equal(atendimentoJoin.get(Atendimento_.paciente).get(Usuario_.id), idPaciente));

		if (idAtendimentoIgnorar != null) {
			predicados.add(qb.lessThan(atendimentoJoin.get(Atendimento_.id), idAtendimentoIgnorar));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.desc(atendimentoJoin.get(Atendimento_.id)), qb.asc(root.get(MedicamentoAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
}
