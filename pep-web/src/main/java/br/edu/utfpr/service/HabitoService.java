package br.edu.utfpr.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.dao.HabitoAtendimentoDao;
import br.edu.utfpr.dao.HabitoDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Habito;
import br.edu.utfpr.model.HabitoAtendimento;
import br.edu.utfpr.service.vo.HabitoPaciente;

@Named
@Stateless
public class HabitoService {

	@Inject
	private HabitoDao habitoDao;
	@Inject
	private HabitoAtendimentoDao habitoAtendimentoDao;
	
	public List<Habito> retornarHabitos(Boolean chkAtivo) {
		return retornarHabitos(null, chkAtivo);
	}
	
	public List<Habito> retornarHabitos(String textoPesquisa, Boolean chkAtivo) {
		return habitoDao.retornarHabitos(textoPesquisa, chkAtivo);
	}
	
	public List<Habito> retornarHabitosMaisUsados(Integer quantidade, List<Long> idsHabitosIgnorar, Boolean chkAtivo) {
		return habitoDao.retornarHabitosMaisUsados(quantidade, idsHabitosIgnorar, chkAtivo);
	}
	
	public Habito retornarHabito(Long id) {
		try {
			return habitoDao.getById(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Habito retornarHabito(String descricao) {
		try {
			return habitoDao.retornarHabito(descricao);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarHabito(Habito habito) throws AppException {
		String descricao = StringUtils.trimToNull(habito.getDescricao());
		habito.setDescricao(descricao);
		
		//valida se nao esta duplicando
		Habito habitoBase = this.retornarHabito(descricao);
		if (habitoBase != null && !habitoBase.getId().equals(habito.getId())) {
			throw new AppException("habito.salvar.erro.existente", descricao);
		}
		
		habitoDao.save(habito);
	}
	
	public List<HabitoPaciente> retornarHabitosPaciente(Long idPaciente) {
		List<HabitoPaciente> habitosPaciente = new ArrayList<>();
		List<HabitoAtendimento> habitosAtendimento = habitoAtendimentoDao.retornarHabitosPaciente(idPaciente);
		for (HabitoAtendimento habitoAtendimento : habitosAtendimento) {
			HabitoPaciente habitoPaciente = new HabitoPaciente();
			Habito habito = habitoAtendimento.getHabito();
			if (habito != null) {
				habitoPaciente.setDescricao(habito.getDescricao());
			} else {
				habitoPaciente.setDescricao(habitoAtendimento.getDescricao());
			}
			habitoPaciente.setObservacao(habitoAtendimento.getObservacao());
			habitosPaciente.add(habitoPaciente);
		}
		return habitosPaciente;
	}
}
