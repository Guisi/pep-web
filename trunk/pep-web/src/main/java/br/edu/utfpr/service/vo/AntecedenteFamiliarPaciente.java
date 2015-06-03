package br.edu.utfpr.service.vo;

import java.io.Serializable;

public class AntecedenteFamiliarPaciente implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigoCid;
	private String descricao;
	private String observacao;

	public String getCodigoCid() {
		return codigoCid;
	}
	public void setCodigoCid(String codigoCid) {
		this.codigoCid = codigoCid;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
}
