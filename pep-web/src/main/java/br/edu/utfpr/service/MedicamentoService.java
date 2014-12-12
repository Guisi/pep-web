package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import br.edu.utfpr.dao.MedicamentoDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Medicamento;

@Named
@Stateless
public class MedicamentoService {

	@Inject
	private MedicamentoDao medicamentoDao;

	public List<Medicamento> retornarMedicamentos() {
		return retornarMedicamentos(null);
	}
	
	public List<Medicamento> retornarMedicamentos(String textoPesquisa) {
		return medicamentoDao.retornarMedicamentos(textoPesquisa);
	}
	
	public Medicamento retornarMedicamento(Long id) {
		try {
			return medicamentoDao.getById(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Medicamento retornarMedicamento(String principioAtivo, String apresentacao) {
		try {
			return medicamentoDao.retornarMedicamento(principioAtivo, apresentacao);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarMedicamento(Medicamento medicamento) throws AppException {
		String principioAtivo = StringUtils.trimToNull(medicamento.getPrincipioAtivo());
		medicamento.setPrincipioAtivo(principioAtivo);
		String apresentacao = StringUtils.trimToNull(medicamento.getApresentacao());
		medicamento.setApresentacao(apresentacao);
		
		//valida se nao esta duplicando
		Medicamento medicamentoBase = this.retornarMedicamento(principioAtivo, apresentacao);
		if (medicamentoBase != null && !medicamentoBase.getId().equals(medicamento.getId())) {
			throw new AppException("medicamento.salvar.erro.existente", principioAtivo, apresentacao);
		}
		
		medicamentoDao.save(medicamento);
	}
}
