package br.edu.utfpr.model;

import java.io.Serializable;
import java.util.Date;

public class AlteracaoAtributo implements Serializable, Comparable<AlteracaoAtributo> {

	private static final long serialVersionUID = 1L;

	private String campo;
	private Date dataAlteracao;
	private String loginUsuario;
	private String valorAntigo;
	private String valorNovo;

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

	public String getValorAntigo() {
		return valorAntigo;
	}

	public void setValorAntigo(String valorAntigo) {
		this.valorAntigo = valorAntigo;
	}

	public String getValorNovo() {
		return valorNovo;
	}

	public void setValorNovo(String valorNovo) {
		this.valorNovo = valorNovo;
	}

	@Override
	public int compareTo(AlteracaoAtributo o) {
		return this.dataAlteracao.compareTo(o.getDataAlteracao());
	}
}
