package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.MedicamentoDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Medicamento;

@Named
@Stateless
public class MedicamentoService {

	@Inject
	private MedicamentoDao medicamentoDao;

	public List<Medicamento> retornarMedicamentos() {
		return medicamentoDao.retornarMedicamentos();
	}
	
	public void removerMedicamento(Medicamento medicamento) throws AppException {
		medicamentoDao.removeById(medicamento.getId());
	}
}
