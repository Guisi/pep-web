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

import br.edu.utfpr.model.AntecedenteClinicoAtendimento;
import br.edu.utfpr.model.AntecedenteClinicoAtendimento_;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.model.Doenca_;
import br.edu.utfpr.model.Usuario_;

public class AntecedenteClinicoAtendimentoDao extends GenericDao<AntecedenteClinicoAtendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<AntecedenteClinicoAtendimento> retornarAntecedentesClinicosAtendimento(Long idAtendimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AntecedenteClinicoAtendimento> q = qb.createQuery(AntecedenteClinicoAtendimento.class);
		Root<AntecedenteClinicoAtendimento> root = q.from(AntecedenteClinicoAtendimento.class);

		q.where(qb.equal(root.get(AntecedenteClinicoAtendimento_.atendimento).get(Atendimento_.id), idAtendimento));
		
		return entityManager.createQuery(q).getResultList();
	}

	public List<AntecedenteClinicoAtendimento> retornarAntecedentesClinicosAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AntecedenteClinicoAtendimento> q = qb.createQuery(AntecedenteClinicoAtendimento.class);
		Root<AntecedenteClinicoAtendimento> root = q.from(AntecedenteClinicoAtendimento.class);
		Join<AntecedenteClinicoAtendimento, Atendimento> atendimentoJoin = root.join(AntecedenteClinicoAtendimento_.atendimento);
		root.fetch(AntecedenteClinicoAtendimento_.doenca, JoinType.LEFT);

		List<Predicate> predicados = new ArrayList<>();
		predicados.add(qb.equal(atendimentoJoin.get(Atendimento_.paciente).get(Usuario_.id), idPaciente));

		if (idAtendimentoIgnorar != null) {
			predicados.add(qb.lessThan(atendimentoJoin.get(Atendimento_.id), idAtendimentoIgnorar));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.desc(atendimentoJoin.get(Atendimento_.id)), qb.asc(root.get(AntecedenteClinicoAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<Doenca> retornarAntecedentesClinicosMaisUsados(Integer quantidade, List<Long> idsDoencasIgnorar, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doenca> cq = qb.createQuery(Doenca.class);
		Root<Doenca> root = cq.from(Doenca.class);
		SetJoin<Doenca, AntecedenteClinicoAtendimento> antecedentesClinicosAtendimentoJoin = root.join(Doenca_.antecedentesClinicosAtendimentos, JoinType.LEFT);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (idsDoencasIgnorar != null && !idsDoencasIgnorar.isEmpty()) {
			predicados.add(qb.not(root.get(Doenca_.id).in(idsDoencasIgnorar)));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Doenca_.chkAtivo), chkAtivo));
		}
		
		if (!predicados.isEmpty()) {
			cq.where(predicados.toArray(new Predicate[predicados.size()]));
		}
		
		cq.groupBy(root.get(Doenca_.id));
		cq.orderBy(qb.desc(qb.count(antecedentesClinicosAtendimentoJoin.get(AntecedenteClinicoAtendimento_.id))), 
					qb.asc(root.get(Doenca_.codigoCid)), qb.asc(root.get(Doenca_.descricao)));
		
		return entityManager.createQuery(cq).setMaxResults(quantidade).getResultList();
	}
}
