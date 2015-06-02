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
import javax.persistence.criteria.SetJoin;

import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.model.Usuario_;
import br.edu.utfpr.model.Vacina;
import br.edu.utfpr.model.VacinaAtendimento;
import br.edu.utfpr.model.VacinaAtendimento_;
import br.edu.utfpr.model.Vacina_;

public class VacinaAtendimentoDao extends GenericDao<VacinaAtendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<VacinaAtendimento> retornarVacinasAtendimento(Long idAtendimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<VacinaAtendimento> q = qb.createQuery(VacinaAtendimento.class);
		Root<VacinaAtendimento> root = q.from(VacinaAtendimento.class);

		q.where(qb.equal(root.get(VacinaAtendimento_.atendimento).get(Atendimento_.id), idAtendimento));
		
		return entityManager.createQuery(q).getResultList();
	}

	public List<VacinaAtendimento> retornarVacinasAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<VacinaAtendimento> q = qb.createQuery(VacinaAtendimento.class);
		Root<VacinaAtendimento> root = q.from(VacinaAtendimento.class);
		Join<VacinaAtendimento, Atendimento> atendimentoJoin = root.join(VacinaAtendimento_.atendimento);
		root.fetch(VacinaAtendimento_.vacina, JoinType.LEFT);

		List<Predicate> predicados = new ArrayList<>();
		predicados.add(qb.equal(atendimentoJoin.get(Atendimento_.paciente).get(Usuario_.id), idPaciente));

		if (idAtendimentoIgnorar != null) {
			predicados.add(qb.lessThan(atendimentoJoin.get(Atendimento_.id), idAtendimentoIgnorar));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.desc(atendimentoJoin.get(Atendimento_.id)), qb.asc(root.get(VacinaAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<Vacina> retornarVacinasMaisUsadas(Integer quantidade, List<Long> idsVacinasIgnorar, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Vacina> cq = qb.createQuery(Vacina.class);
		Root<Vacina> root = cq.from(Vacina.class);
		SetJoin<Vacina, VacinaAtendimento> vacinasAtendimentoJoin = root.join(Vacina_.vacinasAtendimentos, JoinType.LEFT);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (idsVacinasIgnorar != null && !idsVacinasIgnorar.isEmpty()) {
			predicados.add(qb.not(root.get(Vacina_.id).in(idsVacinasIgnorar)));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Vacina_.chkAtivo), chkAtivo));
		}
		
		if (!predicados.isEmpty()) {
			cq.where(predicados.toArray(new Predicate[predicados.size()]));
		}
		
		cq.groupBy(root.get(Vacina_.id));
		cq.orderBy(qb.desc(qb.count(vacinasAtendimentoJoin.get(VacinaAtendimento_.id))), 
					qb.asc(root.get(Vacina_.descricao)));
		
		return entityManager.createQuery(cq).setMaxResults(quantidade).getResultList();
	}
	
	public List<VacinaAtendimento> retornarVacinasPaciente(Long idPaciente) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<VacinaAtendimento> q = qb.createQuery(VacinaAtendimento.class);
		Root<VacinaAtendimento> root = q.from(VacinaAtendimento.class);
		
		Join<VacinaAtendimento, Atendimento> atendimentoVacinasJoin = root.join(VacinaAtendimento_.atendimento);
		Join<Atendimento, Usuario> pacienteJoin = atendimentoVacinasJoin.join(Atendimento_.paciente);
		root.fetch(VacinaAtendimento_.vacina, JoinType.LEFT);
		
		q.where(qb.equal(pacienteJoin.get(Usuario_.id), idPaciente));

		q.orderBy(qb.asc(root.get(VacinaAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
}
