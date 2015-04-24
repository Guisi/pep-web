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

import br.edu.utfpr.constants.TipoProcedimento;
import br.edu.utfpr.model.AntecedenteCirurgicoAtendimento;
import br.edu.utfpr.model.AntecedenteCirurgicoAtendimento_;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.Procedimento;
import br.edu.utfpr.model.Procedimento_;
import br.edu.utfpr.model.Usuario_;

public class AntecedenteCirurgicoAtendimentoDao extends GenericDao<AntecedenteCirurgicoAtendimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<AntecedenteCirurgicoAtendimento> retornarAntecedentesCirurgicosAtendimento(Long idAtendimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AntecedenteCirurgicoAtendimento> q = qb.createQuery(AntecedenteCirurgicoAtendimento.class);
		Root<AntecedenteCirurgicoAtendimento> root = q.from(AntecedenteCirurgicoAtendimento.class);

		q.where(qb.equal(root.get(AntecedenteCirurgicoAtendimento_.atendimento).get(Atendimento_.id), idAtendimento));
		
		return entityManager.createQuery(q).getResultList();
	}

	public List<AntecedenteCirurgicoAtendimento> retornarAntecedentesCirurgicosAtendimentosAnteriores(Long idPaciente, Long idAtendimentoIgnorar) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<AntecedenteCirurgicoAtendimento> q = qb.createQuery(AntecedenteCirurgicoAtendimento.class);
		Root<AntecedenteCirurgicoAtendimento> root = q.from(AntecedenteCirurgicoAtendimento.class);
		Join<AntecedenteCirurgicoAtendimento, Atendimento> atendimentoJoin = root.join(AntecedenteCirurgicoAtendimento_.atendimento);
		root.fetch(AntecedenteCirurgicoAtendimento_.procedimento, JoinType.LEFT);

		List<Predicate> predicados = new ArrayList<>();
		predicados.add(qb.equal(atendimentoJoin.get(Atendimento_.paciente).get(Usuario_.id), idPaciente));

		if (idAtendimentoIgnorar != null) {
			predicados.add(qb.lessThan(atendimentoJoin.get(Atendimento_.id), idAtendimentoIgnorar));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.desc(atendimentoJoin.get(Atendimento_.id)), qb.asc(root.get(AntecedenteCirurgicoAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public List<Procedimento> retornarAntecedentesCirurgicosMaisUsados(Integer quantidade, List<Long> idsProcedimentosIgnorar, Boolean chkAtivo, TipoProcedimento tipoProcedimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Procedimento> cq = qb.createQuery(Procedimento.class);
		Root<Procedimento> root = cq.from(Procedimento.class);
		SetJoin<Procedimento, AntecedenteCirurgicoAtendimento> antecedentesCirurgicosAtendimentoJoin = root.join(Procedimento_.antecedentesCirurgicosAtendimentos, JoinType.LEFT);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (idsProcedimentosIgnorar != null && !idsProcedimentosIgnorar.isEmpty()) {
			predicados.add(qb.not(root.get(Procedimento_.id).in(idsProcedimentosIgnorar)));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Procedimento_.chkAtivo), chkAtivo));
		}
		
		if (tipoProcedimento != null) {
			predicados.add(qb.equal(root.get(Procedimento_.tipoProcedimento), tipoProcedimento));
		}
		
		if (!predicados.isEmpty()) {
			cq.where(predicados.toArray(new Predicate[predicados.size()]));
		}
		
		cq.groupBy(root.get(Procedimento_.id));
		cq.orderBy(qb.desc(qb.count(antecedentesCirurgicosAtendimentoJoin.get(AntecedenteCirurgicoAtendimento_.id))), 
					qb.asc(root.get(Procedimento_.descricao)));
		
		return entityManager.createQuery(cq).setMaxResults(quantidade).getResultList();
	}
}
