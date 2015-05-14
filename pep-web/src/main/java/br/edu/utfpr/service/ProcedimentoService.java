package br.edu.utfpr.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.constants.TipoProcedimento;
import br.edu.utfpr.dao.AntecedenteCirurgicoAtendimentoDao;
import br.edu.utfpr.dao.ProcedimentoDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.AntecedenteCirurgicoAtendimento;
import br.edu.utfpr.model.Procedimento;
import br.edu.utfpr.service.vo.ProcedimentoRealizado;

@Named
@Stateless
public class ProcedimentoService {

	@Inject
	private ProcedimentoDao procedimentoDao;
	@Inject
	private AntecedenteCirurgicoAtendimentoDao antecedenteCirurgicoAtendimentoDao;
	
	public List<Procedimento> retornarProcedimentos(Boolean chkAtivo, TipoProcedimento tipoProcedimento) {
		return retornarProcedimentos(null, chkAtivo, tipoProcedimento);
	}
	
	public List<Procedimento> retornarProcedimentos(String textoPesquisa, Boolean chkAtivo, TipoProcedimento tipoProcedimento) {
		return procedimentoDao.retornarProcedimentos(textoPesquisa, chkAtivo, tipoProcedimento);
	}
	
	public Procedimento retornarProcedimento(Long id) {
		try {
			return procedimentoDao.getById(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Procedimento retornarProcedimento(String descricao) {
		try {
			return procedimentoDao.retornarProcedimento(descricao);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarProcedimento(Procedimento procedimento) throws AppException {
		String descricao = StringUtils.trimToNull(procedimento.getDescricao());
		procedimento.setDescricao(descricao);
		
		//valida se nao esta duplicando
		Procedimento procedimentoBase = this.retornarProcedimento(descricao);
		if (procedimentoBase != null && !procedimentoBase.getId().equals(procedimento.getId())) {
			throw new AppException("procedimento.salvar.erro.existente", descricao);
		}
		
		procedimentoDao.save(procedimento);
	}
	
	public List<ProcedimentoRealizado> retornarProcedimentosRealizados(Long idPaciente) {
		//busca procedimentos de antecedentes cirurgicos
		List<AntecedenteCirurgicoAtendimento> antecedentesCirurgicos = antecedenteCirurgicoAtendimentoDao.retornarAntecedentesCirurgicosRealizados(idPaciente);
		List<ProcedimentoRealizado> procedimentos = new ArrayList<>();
		
		for (AntecedenteCirurgicoAtendimento antecedenteCirurgicoAtendimento : antecedentesCirurgicos) {
			ProcedimentoRealizado procedimentoRealizado = new ProcedimentoRealizado();
			Procedimento procedimento = antecedenteCirurgicoAtendimento.getProcedimento();
			if (procedimento != null) {
				procedimentoRealizado.setDescricao(procedimento.getDescricao());
			} else {
				procedimentoRealizado.setDescricao(antecedenteCirurgicoAtendimento.getDescricao());
			}
			procedimentoRealizado.setObservacao(antecedenteCirurgicoAtendimento.getObservacao());
			procedimentos.add(procedimentoRealizado);
		}
		
		return procedimentos;
	}
}
