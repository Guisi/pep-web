package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.List;

import br.edu.utfpr.model.Convenio;

public class ConvenioDao extends GenericDao<Convenio, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Convenio> retornarConvenios() {
		return findAll();
	}
}
