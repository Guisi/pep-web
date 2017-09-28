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

import br.edu.utfpr.model.Alergia;
import br.edu.utfpr.model.AlergiaAtendimento;
import br.edu.utfpr.model.AlergiaAtendimento_;
import br.edu.utfpr.model.Alergia_;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.model.Usuario_;

public class AlergiaAtendimentoDao extends GenericDao<AlergiaAtendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<AlergiaAtendimento> retornarAlergiasAtendimento(Long idAtendimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AlergiaAtendimento> q = qb.createQuery(AlergiaAtendimento.class);
		Root<AlergiaAtendimento> root = q.from(AlergiaAtendimento.class);

		q.where(qb.equal(root.get(AlergiaAtendimento_.atendimento).get(Atendimento_.id), idAtendimento));
		
		return entityManager.createQuery(q).getResultList();
	}

	public List<AlergiaAtendimento> retornarAlergiasAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AlergiaAtendimento> q = qb.createQuery(AlergiaAtendimento.class);
		Root<AlergiaAtendimento> root = q.from(AlergiaAtendimento.class);
		Join<AlergiaAtendimento, Atendimento> atendimentoJoin = root.join(AlergiaAtendimento_.atendimento);
		root.fetch(AlergiaAtendimento_.alergia, JoinType.LEFT);

		List<Predicate> predicados = new ArrayList<>();
		predicados.add(qb.equal(atendimentoJoin.get(Atendimento_.paciente).get(Usuario_.id), idPaciente));

		if (idAtendimentoIgnorar != null) {
			predicados.add(qb.lessThan(atendimentoJoin.get(Atendimento_.id), idAtendimentoIgnorar));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.desc(atendimentoJoin.get(Atendimento_.id)), qb.asc(root.get(AlergiaAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<Alergia> retornarAlergiasMaisUsadas(Integer quantidade, List<Long> idsAlergiasIgnorar, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Alergia> cq = qb.createQuery(Alergia.class);
		Root<Alergia> root = cq.from(Alergia.class);
		SetJoin<Alergia, AlergiaAtendimento> AlergiasAtendimentoJoin = root.join(Alergia_.alergiasAtendimentos, JoinType.LEFT);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (idsAlergiasIgnorar != null && !idsAlergiasIgnorar.isEmpty()) {
			predicados.add(qb.not(root.get(Alergia_.id).in(idsAlergiasIgnorar)));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Alergia_.chkAtivo), chkAtivo));
		}
		
		if (!predicados.isEmpty()) {
			cq.where(predicados.toArray(new Predicate[predicados.size()]));
		}
		
		cq.groupBy(root.get(Alergia_.id));
		cq.orderBy(qb.desc(qb.count(AlergiasAtendimentoJoin.get(AlergiaAtendimento_.id))), 
					qb.asc(root.get(Alergia_.descricao)));
		
		return entityManager.createQuery(cq).setMaxResults(quantidade).getResultList();
	}
	
	public List<AlergiaAtendimento> retornarAlergiasPaciente(Long idPaciente) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AlergiaAtendimento> q = qb.createQuery(AlergiaAtendimento.class);
		Root<AlergiaAtendimento> root = q.from(AlergiaAtendimento.class);
		
		Join<AlergiaAtendimento, Atendimento> atendimentoAlergiasJoin = root.join(AlergiaAtendimento_.atendimento);
		Join<Atendimento, Usuario> pacienteJoin = atendimentoAlergiasJoin.join(Atendimento_.paciente);
		root.fetch(AlergiaAtendimento_.alergia, JoinType.LEFT);
		
		q.where(qb.equal(pacienteJoin.get(Usuario_.id), idPaciente));

		q.orderBy(qb.asc(root.get(AlergiaAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
}
