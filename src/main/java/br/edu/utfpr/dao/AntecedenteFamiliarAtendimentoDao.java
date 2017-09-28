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

import br.edu.utfpr.model.AntecedenteFamiliarAtendimento;
import br.edu.utfpr.model.AntecedenteFamiliarAtendimento_;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.model.Doenca_;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.model.Usuario_;

public class AntecedenteFamiliarAtendimentoDao extends GenericDao<AntecedenteFamiliarAtendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<AntecedenteFamiliarAtendimento> retornarAntecedentesFamiliaresAtendimento(Long idAtendimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AntecedenteFamiliarAtendimento> q = qb.createQuery(AntecedenteFamiliarAtendimento.class);
		Root<AntecedenteFamiliarAtendimento> root = q.from(AntecedenteFamiliarAtendimento.class);

		q.where(qb.equal(root.get(AntecedenteFamiliarAtendimento_.atendimento).get(Atendimento_.id), idAtendimento));
		
		return entityManager.createQuery(q).getResultList();
	}

	public List<AntecedenteFamiliarAtendimento> retornarAntecedentesFamiliaresAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AntecedenteFamiliarAtendimento> q = qb.createQuery(AntecedenteFamiliarAtendimento.class);
		Root<AntecedenteFamiliarAtendimento> root = q.from(AntecedenteFamiliarAtendimento.class);
		Join<AntecedenteFamiliarAtendimento, Atendimento> atendimentoJoin = root.join(AntecedenteFamiliarAtendimento_.atendimento);
		root.fetch(AntecedenteFamiliarAtendimento_.doenca, JoinType.LEFT);

		List<Predicate> predicados = new ArrayList<>();
		predicados.add(qb.equal(atendimentoJoin.get(Atendimento_.paciente).get(Usuario_.id), idPaciente));

		if (idAtendimentoIgnorar != null) {
			predicados.add(qb.lessThan(atendimentoJoin.get(Atendimento_.id), idAtendimentoIgnorar));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.desc(atendimentoJoin.get(Atendimento_.id)), qb.asc(root.get(AntecedenteFamiliarAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<Doenca> retornarAntecedentesFamiliaresMaisUsados(Integer quantidade, List<Long> idsDoencasIgnorar, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doenca> cq = qb.createQuery(Doenca.class);
		Root<Doenca> root = cq.from(Doenca.class);
		SetJoin<Doenca, AntecedenteFamiliarAtendimento> antecedentesFamiliaresAtendimentoJoin = root.join(Doenca_.antecedentesFamiliaresAtendimentos, JoinType.LEFT);
		
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
		cq.orderBy(qb.desc(qb.count(antecedentesFamiliaresAtendimentoJoin.get(AntecedenteFamiliarAtendimento_.id))), 
					qb.asc(root.get(Doenca_.codigoCid)), qb.asc(root.get(Doenca_.descricao)));
		
		return entityManager.createQuery(cq).setMaxResults(quantidade).getResultList();
	}
	
	public List<AntecedenteFamiliarAtendimento> retornarAntecedentesFamiliaresDiagnosticados(Long idPaciente) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AntecedenteFamiliarAtendimento> q = qb.createQuery(AntecedenteFamiliarAtendimento.class);
		Root<AntecedenteFamiliarAtendimento> root = q.from(AntecedenteFamiliarAtendimento.class);
		
		Join<AntecedenteFamiliarAtendimento, Atendimento> atendimentoAntecedenteFamiliarJoin = root.join(AntecedenteFamiliarAtendimento_.atendimento);
		Join<Atendimento, Usuario> pacienteJoin = atendimentoAntecedenteFamiliarJoin.join(Atendimento_.paciente);
		root.fetch(AntecedenteFamiliarAtendimento_.doenca, JoinType.LEFT);
		
		q.where(qb.equal(pacienteJoin.get(Usuario_.id), idPaciente));

		q.orderBy(qb.asc(root.get(AntecedenteFamiliarAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
}
