package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.constants.TipoProcedimento;
import br.edu.utfpr.model.AntecedenteClinicoAtendimento;
import br.edu.utfpr.model.AntecedenteClinicoAtendimento_;
import br.edu.utfpr.model.Atendimento;
import br.edu.utfpr.model.Atendimento_;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.model.Doenca_;
import br.edu.utfpr.model.Procedimento;
import br.edu.utfpr.model.Procedimento_;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.model.Usuario_;

public class ProcedimentoDao extends GenericDao<Procedimento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Procedimento> retornarProcedimentos(String textoPesquisa, Boolean chkAtivo, TipoProcedimento tipoProcedimento) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Procedimento> q = qb.createQuery(Procedimento.class);
		Root<Procedimento> root = q.from(Procedimento.class);

		List<Predicate> predicados = new ArrayList<>();
		if (StringUtils.isNotBlank(textoPesquisa)) {
			predicados.add(qb.like(qb.lower(root.get(Procedimento_.descricao)), "%" + textoPesquisa.toLowerCase() + "%"));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Procedimento_.chkAtivo), chkAtivo));
		}
		
		if (tipoProcedimento != null) {
			predicados.add(qb.equal(root.get(Procedimento_.tipoProcedimento), tipoProcedimento));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		q.orderBy(qb.asc(root.get(Procedimento_.descricao)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public Doenca retornarDoenca(String codigoCid) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doenca> q = qb.createQuery(Doenca.class);
		Root<Doenca> root = q.from(Doenca.class);
		
		Predicate predicate = qb.equal(qb.lower(root.get(Doenca_.codigoCid)), StringUtils.lowerCase(codigoCid));
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
	
	public List<Doenca> retornarDoencasDiagnosticadas(Long idPaciente) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Doenca> q = qb.createQuery(Doenca.class);
		Root<Doenca> root = q.from(Doenca.class);
		
		SetJoin<Doenca, AntecedenteClinicoAtendimento> antecedentesClinicosJoin = root.join(Doenca_.antecedentesClinicosAtendimentos);
		Join<AntecedenteClinicoAtendimento, Atendimento> atendimentoAntecedenteClinicoJoin = antecedentesClinicosJoin.join(AntecedenteClinicoAtendimento_.atendimento);
		Join<Atendimento, Usuario> pacienteJoin = atendimentoAntecedenteClinicoJoin.join(Atendimento_.paciente);
		
		q.where(qb.equal(pacienteJoin.get(Usuario_.id), idPaciente));

		q.orderBy(qb.asc(antecedentesClinicosJoin.get(AntecedenteClinicoAtendimento_.id)));
		
		return entityManager.createQuery(q).getResultList();
	}
}
