package br.edu.utfpr.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.dao.AntecedenteClinicoAtendimentoDao;
import br.edu.utfpr.dao.DoencaDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.AntecedenteClinicoAtendimento;
import br.edu.utfpr.model.Doenca;
import br.edu.utfpr.service.vo.DoencaDiagnosticada;

@Named
@Stateless
public class DoencaService {

	@Inject
	private DoencaDao doencaDao;
	@Inject
	private AntecedenteClinicoAtendimentoDao antecedenteClinicoAtendimentoDao;
	
	public List<Doenca> retornarDoencas(Boolean chkAtivo) {
		return retornarDoencas(null, chkAtivo);
	}
	
	public List<Doenca> retornarDoencas(String textoPesquisa, Boolean chkAtivo) {
		return doencaDao.retornarDoencas(textoPesquisa, chkAtivo);
	}
	
	public Doenca retornarDoenca(Long id) {
		try {
			return doencaDao.getById(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Doenca retornarDoenca(String codigoCid) {
		try {
			return doencaDao.retornarDoenca(codigoCid);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarDoenca(Doenca doenca) throws AppException {
		String codigoCid = StringUtils.trimToNull(doenca.getCodigoCid());
		doenca.setCodigoCid(codigoCid);
		
		String descricao = StringUtils.trimToNull(doenca.getDescricao());
		doenca.setDescricao(descricao);
		
		//valida se nao esta duplicando
		Doenca doencaBase = this.retornarDoenca(codigoCid);
		if (doencaBase != null && !doencaBase.getId().equals(doenca.getId())) {
			throw new AppException("doenca.salvar.erro.existente", codigoCid);
		}
		
		doencaDao.save(doenca);
	}
	
	public List<DoencaDiagnosticada> retornarDoencasDiagnosticadas(Long idPaciente) {
		//busca doencas de antecedentes clinicos
		List<AntecedenteClinicoAtendimento> antecedentes = antecedenteClinicoAtendimentoDao.retornarAntecedentesClinicosDiagnosticadas(idPaciente);
		List<DoencaDiagnosticada> doencas = new ArrayList<>();
		for (AntecedenteClinicoAtendimento antecedenteClinicoAtendimento : antecedentes) {
			DoencaDiagnosticada doencaDiagnosticada = new DoencaDiagnosticada();
			Doenca doenca = antecedenteClinicoAtendimento.getDoenca();
			if (doenca != null) {
				doencaDiagnosticada.setCodigoCid(doenca.getCodigoCid());
				doencaDiagnosticada.setDescricao(doenca.getDescricao());
			} else {
				doencaDiagnosticada.setDescricao(antecedenteClinicoAtendimento.getDescricao());
			}
			doencaDiagnosticada.setObservacao(antecedenteClinicoAtendimento.getObservacao());
			doencas.add(doencaDiagnosticada);
		}
		
		return doencas;
	}
}
