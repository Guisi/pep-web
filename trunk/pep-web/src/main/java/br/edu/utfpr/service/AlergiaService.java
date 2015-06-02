package br.edu.utfpr.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.dao.AlergiaAtendimentoDao;
import br.edu.utfpr.dao.AlergiaDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Alergia;
import br.edu.utfpr.model.AlergiaAtendimento;
import br.edu.utfpr.service.vo.AlergiaPaciente;

@Named
@Stateless
public class AlergiaService {

	@Inject
	private AlergiaDao alergiaDao;
	@Inject
	private AlergiaAtendimentoDao alergiaAtendimentoDao;
	
	public List<Alergia> retornarAlergias(Boolean chkAtivo) {
		return retornarAlergias(null, chkAtivo);
	}
	
	public List<Alergia> retornarAlergias(String textoPesquisa, Boolean chkAtivo) {
		return alergiaDao.retornarAlergias(textoPesquisa, chkAtivo);
	}
	
	public List<Alergia> retornarAlergiasMaisUsados(Integer quantidade, List<Long> idsAlergiasIgnorar, Boolean chkAtivo) {
		return alergiaDao.retornarAlergiasMaisUsados(quantidade, idsAlergiasIgnorar, chkAtivo);
	}
	
	public Alergia retornarAlergia(Long id) {
		try {
			return alergiaDao.getById(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Alergia retornarAlergia(String descricao) {
		try {
			return alergiaDao.retornarAlergia(descricao);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarAlergia(Alergia alergia) throws AppException {
		String descricao = StringUtils.trimToNull(alergia.getDescricao());
		alergia.setDescricao(descricao);
		
		//valida se nao esta duplicando
		Alergia alergiaBase = this.retornarAlergia(descricao);
		if (alergiaBase != null && !alergiaBase.getId().equals(alergia.getId())) {
			throw new AppException("alergia.salvar.erro.existente", descricao);
		}
		
		alergiaDao.save(alergia);
	}
	
	public List<AlergiaPaciente> retornarAlergiasPaciente(Long idPaciente) {
		List<AlergiaPaciente> alergiasPaciente = new ArrayList<>();
		List<AlergiaAtendimento> alergiasAtendimento = alergiaAtendimentoDao.retornarAlergiasPaciente(idPaciente);
		for (AlergiaAtendimento alergiaAtendimento : alergiasAtendimento) {
			AlergiaPaciente alergiaPaciente = new AlergiaPaciente();
			Alergia alergia = alergiaAtendimento.getAlergia();
			if (alergia != null) {
				alergiaPaciente.setDescricao(alergia.getDescricao());
			} else {
				alergiaPaciente.setDescricao(alergiaAtendimento.getDescricao());
			}
			alergiaPaciente.setObservacao(alergiaAtendimento.getObservacao());
			alergiasPaciente.add(alergiaPaciente);
		}
		return alergiasPaciente;
	}
}
