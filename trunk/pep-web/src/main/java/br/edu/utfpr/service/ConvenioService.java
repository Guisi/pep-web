package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

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
	
	public Convenio retornarConvenio(Long id) {
		try {
			return convenioDao.getById(id);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Convenio retornarConvenioPorDescricao(String descricao) {
		try {
			return convenioDao.retornarConvenioPorDescricao(descricao);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarConvenio(Convenio convenio) throws AppException {
		String descricao = StringUtils.trimToNull(convenio.getDescricao());
		convenio.setDescricao(descricao);
		
		//valida se nao esta duplicando
		Convenio convenioBase = this.retornarConvenioPorDescricao(descricao);
		if (convenioBase != null && !convenioBase.getId().equals(convenio.getId())) {
			throw new AppException("convenio.salvar.erro.descricaoexistente", descricao);
		}
		
		convenioDao.save(convenio);
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
