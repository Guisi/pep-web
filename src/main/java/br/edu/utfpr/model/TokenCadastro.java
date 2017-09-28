package br.edu.utfpr.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.edu.utfpr.constants.Constantes;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_token_cadastro")
public class TokenCadastro extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="token_cadastro_sequence", sequenceName=Constantes.PEP_OWNER + "token_cadastro_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="token_cadastro_sequence")
	@Column(name="id_token_cadastro")
	private Long id;

	@Column(name = "chk_ativo", nullable = false, length = 1)
	private Boolean chkAtivo;

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_geracao", nullable = false)
	private Date dtGeracao;

	@Column(name = "tx_email_usuario", nullable = false, length = 100)
	private String txEmailUsuario;

	@Column(name = "tx_token_value", nullable = false, length = 255)
	private String txTokenValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getChkAtivo() {
		return chkAtivo;
	}

	public void setChkAtivo(Boolean chkAtivo) {
		this.chkAtivo = chkAtivo;
	}

	public Date getDtGeracao() {
		return dtGeracao;
	}

	public void setDtGeracao(Date dtGeracao) {
		this.dtGeracao = dtGeracao;
	}

	public String getTxEmailUsuario() {
		return txEmailUsuario;
	}

	public void setTxEmailUsuario(String txEmailUsuario) {
		this.txEmailUsuario = txEmailUsuario;
	}

	public String getTxTokenValue() {
		return txTokenValue;
	}

	public void setTxTokenValue(String txTokenValue) {
		this.txTokenValue = txTokenValue;
	}
	
}