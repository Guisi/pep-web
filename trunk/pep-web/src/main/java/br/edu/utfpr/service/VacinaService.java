package br.edu.utfpr.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.dao.VacinaAtendimentoDao;
import br.edu.utfpr.dao.VacinaDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Vacina;
import br.edu.utfpr.model.VacinaAtendimento;
import br.edu.utfpr.service.vo.VacinaPaciente;

@Named
@Stateless
public class VacinaService {

	@Inject
	private VacinaDao vacinaDao;
	@Inject
	private VacinaAtendimentoDao vacinaAtendimentoDao;
	
	public List<Vacina> retornarVacinas(Boolean chkAtivo) {
		return retornarVacinas(null, chkAtivo);
	}
	
	public List<Vacina> retornarVacinas(String textoPesquisa, Boolean chkAtivo) {
		return vacinaDao.retornarVacinas(textoPesquisa, chkAtivo);
	}
	
	public List<Vacina> retornarVacinasMaisUsadas(Integer quantidade, List<Long> idsVacinasIgnorar, Boolean chkAtivo) {
		return vacinaDao.retornarVacinasMaisUsadas(quantidade, idsVacinasIgnorar, chkAtivo);
	}
	
	public Vacina retornarVacina(Long id) {
		try {
			return vacinaDao.getById(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Vacina retornarVacina(String descricao) {
		try {
			return vacinaDao.retornarVacina(descricao);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarVacina(Vacina vacina) throws AppException {
		String descricao = StringUtils.trimToNull(vacina.getDescricao());
		vacina.setDescricao(descricao);
		
		//valida se nao esta duplicando
		Vacina vacinaBase = this.retornarVacina(descricao);
		if (vacinaBase != null && !vacinaBase.getId().equals(vacina.getId())) {
			throw new AppException("vacina.salvar.erro.existente", descricao);
		}
		
		vacinaDao.save(vacina);
	}
	
	public List<VacinaPaciente> retornarVacinasPaciente(Long idPaciente) {
		List<VacinaPaciente> vacinasPaciente = new ArrayList<>();
		List<VacinaAtendimento> vacinasAtendimento = vacinaAtendimentoDao.retornarVacinasPaciente(idPaciente);
		for (VacinaAtendimento vacinaAtendimento : vacinasAtendimento) {
			VacinaPaciente vacinaPaciente = new VacinaPaciente();
			Vacina vacina = vacinaAtendimento.getVacina();
			if (vacina != null) {
				vacinaPaciente.setDescricao(vacina.getDescricao());
			} else {
				vacinaPaciente.setDescricao(vacinaAtendimento.getDescricao());
			}
			vacinaPaciente.setObservacao(vacinaAtendimento.getObservacao());
			vacinasPaciente.add(vacinaPaciente);
		}
		return vacinasPaciente;
	}
}
