package br.edu.utfpr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_medicamento_atendimento")
public class MedicamentoAtendimento extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="medicamento_atendimento_sequence", sequenceName=Constantes.PEP_OWNER + "medicamento_atendimento_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="medicamento_atendimento_sequence")
	@Column(name="id_medicamento_atendimento")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Atendimento atendimento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Medicamento medicamento;
	
	@Column(name="descricao", length=600)
	@Size(max=600)
	private String descricao;
	
	@Column(name="apresentacao", length=300)
	@Size(max=300)
	private String posologia;
	
	public String getDescricao() {
		if (medicamento != null) {
			return medicamento.getPrincipioAtivo() + " - " + medicamento.getApresentacao();
		} else {
			return descricao;
		}
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

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public String getPosologia() {
		return posologia;
	}

	public void setPosologia(String posologia) {
		this.posologia = posologia;
	}
	
}