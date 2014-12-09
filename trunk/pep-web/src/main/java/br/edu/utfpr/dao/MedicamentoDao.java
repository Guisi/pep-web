package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.List;

import br.edu.utfpr.model.Medicamento;

public class MedicamentoDao extends GenericDao<Medicamento, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Medicamento> retornarMedicamentos();
		return findAll(Especialidade_.descricao);
	}
	
}
