package br.edu.utfpr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_queixa_principal")
public class QueixaPrincipal extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="queixa_principal_sequence", sequenceName=Constantes.PEP_OWNER + "queixa_principal_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="queixa_principal_sequence")
	@Column(name="id_queixa_principal")
	private Long id;
	
	@Column(name="descricao", length=300)
	@Size(max=300)
	private String descricao;
	
	@Column(name="chk_ativo")
	private Boolean chkAtivo;
	
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getChkAtivo() {
		return chkAtivo;
	}

	public void setChkAtivo(Boolean chkAtivo) {
		this.chkAtivo = chkAtivo;
	}

}