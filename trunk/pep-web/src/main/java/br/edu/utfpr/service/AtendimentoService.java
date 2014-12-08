package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.AtendimentoDao;
import br.edu.utfpr.model.Atendimento;

@Named
@Stateless
public class AtendimentoService {

	@Inject
	private AtendimentoDao atendimentoDao;

	public List<Atendimento> retornarAtendimentosPaciente(Long idPaciente) {
		return atendimentoDao.retornarAtendimentosPaciente(idPaciente);
	}
}
