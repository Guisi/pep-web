package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.ConvenioDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Convenio;

@Named
@Stateless
public class ConvenioService {

	@Inject
	private ConvenioDao convenioDao;

	public List<Convenio> retornarConvenios() {
		return convenioDao.retornarConvenios();
	}
	
	public void removerConvenio(Convenio convenio) throws AppException {
		convenio = convenioDao.getById(convenio.getId());
		
		//se possui usuarios vinculados, nao permite excluir convenio
		if (!convenio.getUsuarios().isEmpty()) {
			throw new AppException("convenio.remover.erro.usuariosvinculados");
		}
		
		convenioDao.removeById(convenio.getId());
	}
}
