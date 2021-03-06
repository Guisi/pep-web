package br.edu.utfpr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import br.edu.utfpr.constants.StatusDoenca;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_doenca_diagnosticada_atendimento")
public class DoencaDiagnosticadaAtendimento extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="doenca_diagnosticada_atendimento_sequence", sequenceName=Constantes.PEP_OWNER + "doenca_diagnosticada_atendimento_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="doenca_diagnosticada_atendimento_sequence")
	@Column(name="id_doenca_diagnosticada_atendimento")
	private Long id;
	
	@JoinColumn(name = "id_atendimento")
	@ManyToOne(fetch = FetchType.LAZY)
	private Atendimento atendimento;
	
	@JoinColumn(name = "id_doenca")
	@ManyToOne(fetch = FetchType.LAZY)
	private Doenca doenca;
	
	@Column(name="descricao", length=300)
	@Size(max=300)
	private String descricao;
	
	@Column(name="observacao", length=1000)
	@Size(max=1000)
	private String observacao;
	
	@Column(name="status_doenca")
	@Enumerated(EnumType.STRING)
	private StatusDoenca statusDoenca;
	
	@Transient
	private boolean atendimentoAnterior;
	
	public String getDescricao() {
		return doenca != null ? doenca.getCodigoCid() + " - " + doenca.getDescricao() : descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

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

	public Doenca getDoenca() {
		return doenca;
	}

	public void setDoenca(Doenca doenca) {
		this.doenca = doenca;
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

	public StatusDoenca getStatusDoenca() {
		return statusDoenca;
	}

	public void setStatusDoenca(StatusDoenca statusDoenca) {
		this.statusDoenca = statusDoenca;
	}
	
}