package br.edu.utfpr.model;

import java.io.Serializable;

public class Auditoria implements Serializable {

	private static final long serialVersionUID = 1L;

	private BaseEntity entidade;
	private Revisao revisao;
	
	public BaseEntity getEntidade() {
		return entidade;
	}

	public void setEntidade(BaseEntity entidade) {
		this.entidade = entidade;
	}

	public Revisao getRevisao() {
		return revisao;
	}

	public void setRevisao(Revisao revisao) {
		this.revisao = revisao;
	}

}