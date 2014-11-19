package br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.List;

import br.edu.utfpr.model.Autorizacao;
import br.edu.utfpr.model.Autorizacao_;

public class AutorizacaoDao extends GenericDao<Autorizacao, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Autorizacao> retornarAutorizacoes() {
		return findAll(Autorizacao_.descricao);
	}
}
