package br.edu.utfpr.utils;

import org.hibernate.envers.RevisionListener;

import br.edu.utfpr.model.Revisao;

/**
 * Classe revisora para o hivernate envers. 
 *
 */
public class RevisaoListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		Long idUsuario = UserThreadLocal.getThreadLocal().get();
		Revisao revisao = (Revisao) revisionEntity;
		revisao.setIdUsuario(idUsuario);
	}
}
