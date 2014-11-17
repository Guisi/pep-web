package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.utfpr.dao.EspecialidadeDao;
import br.edu.utfpr.exception.AppException;
import br.edu.utfpr.model.Especialidade;

@Named
@Stateless
public class EspecialidadeService {

	@Inject
	private EspecialidadeDao especialidadeDao;

	public List<Especialidade> retornarEspecialidades() {
		return especialidadeDao.retornarEspecialidades();
	}
	
	public void removerEspecialidade(Especialidade especialidade) throws AppException {
		especialidade = especialidadeDao.getById(especialidade.getId());
		
		//se possui usuarios vinculados, nao permite excluir perfil
		if (!especialidade.getUsuarios().isEmpty()) {
			throw new AppException("especialidade.remover.erro.usuariosvinculados");
		}
		
		especialidadeDao.removeById(especialidade.getId());
	}
}
