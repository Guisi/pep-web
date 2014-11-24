package br.edu.utfpr.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

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
	
	public Especialidade retornarEspecialidade(Long id) {
		return especialidadeDao.getById(id);
	}
	
	public Especialidade retornarEspecialidadePorDescricao(String descricao) {
		try {
			return especialidadeDao.retornarEspecialidadePorDescricao(descricao);
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public void salvarEspecialidade(Especialidade especialidade) throws AppException {
		String nomeEspecialidade = StringUtils.trimToNull(especialidade.getDescricao());
		especialidade.setDescricao(nomeEspecialidade);
		
		//valida se nao esta duplicando
		Especialidade especialidadeBase = this.retornarEspecialidadePorDescricao(nomeEspecialidade);
		if (especialidadeBase != null && !especialidadeBase.getId().equals(especialidade.getId())) {
			throw new AppException("especialidade.salvar.erro.descricaoexistente", nomeEspecialidade);
		}
		
		especialidadeDao.save(especialidade);
	}
	
	public void removerEspecialidade(Especialidade especialidade) throws AppException {
		especialidade = especialidadeDao.getById(especialidade.getId());
		
		//se possui usuarios vinculados, nao permite excluir especialidade
		if (!especialidade.getUsuarios().isEmpty()) {
			throw new AppException("especialidade.remover.erro.usuariosvinculados");
		}
		
		especialidadeDao.removeById(especialidade.getId());
	}
}
