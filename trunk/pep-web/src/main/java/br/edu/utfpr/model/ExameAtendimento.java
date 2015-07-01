package br.edu.utfpr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_exame_atendimento")
public class ExameAtendimento extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="exame_atendimento_sequence", sequenceName=Constantes.PEP_OWNER + "exame_atendimento_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="exame_atendimento_sequence")
	@Column(name="id_exame_atendimento")
	private Long id;
	
	@JoinColumn(name = "id_atendimento")
	@ManyToOne(fetch = FetchType.LAZY)
	private Atendimento atendimento;
	
	@JoinColumn(name = "id_exame")
	@ManyToOne(fetch = FetchType.LAZY)
	private Exame exame;
	
	@Column(name="observacao", length=1000)
	@Size(max=1000)
	private String observacao;
	
	@Transient
	private boolean atendimentoAnterior;
	
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Atendimento getAtendimento() {
		return atendimento;
	}

	public void setAtendimento(Atendimento atendimento) {
		this.atendimento = atendimento;
	}

	public Exame getExame() {
		return exame;
	}

	public void setExame(Exame exame) {
		this.exame = exame;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public boolean isAtendimentoAnterior() {
		return atendimentoAnterior;
	}

	public void setAtendimentoAnterior(boolean atendimentoAnterior) {
		this.atendimentoAnterior = atendimentoAnterior;
	}
	
}