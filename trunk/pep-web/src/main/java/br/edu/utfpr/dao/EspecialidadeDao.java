package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.List;

import br.edu.utfpr.model.Especialidade;
import br.edu.utfpr.model.Especialidade_;

public class EspecialidadeDao extends GenericDao<Especialidade, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Especialidade> retornarEspecialidades() {
		return findAll(Especialidade_.descricao);
	}
}
