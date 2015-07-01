package br.edu.utfpr.service.vo;

import java.io.Serializable;

import br.edu.utfpr.constants.StatusDoenca;

public class DoencaDiagnosticada implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigoCid;
	private String descricao;
	private String observacao;
	private StatusDoenca statusDoenca;

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
	public StatusDoenca getStatusDoenca() {
		return statusDoenca;
	}
	public void setStatusDoenca(StatusDoenca statusDoenca) {
		this.statusDoenca = statusDoenca;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoCid == null) ? 0 : codigoCid.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoencaDiagnosticada other = (DoencaDiagnosticada) obj;
		if (codigoCid == null) {
			if (other.codigoCid != null)
				return false;
		} else if (!codigoCid.equals(other.codigoCid))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		return true;
	}
	
}
