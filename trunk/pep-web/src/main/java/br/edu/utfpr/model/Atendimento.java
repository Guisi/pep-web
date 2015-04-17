package br.edu.utfpr.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_atendimento")
public class Atendimento extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="atendimento_sequence", sequenceName=Constantes.PEP_OWNER + "atendimento_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="atendimento_sequence")
	@Column(name="id_atendimento")
	private Long id;
	
	@Column(name="data")
	private Date data;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_paciente")
	private Usuario paciente;
	
	@OrderBy("id_medicamento_atendimento")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "atendimento")
	private Set<MedicamentoAtendimento> medicamentos;

	@OrderBy("id_queixa_principal_atendimento")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "atendimento")
	private Set<QueixaPrincipalAtendimento> queixasPrincipais;
	
	@OrderBy("id_antecedente_clinico_atendimento")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "atendimento")
	private Set<AntecedenteClinicoAtendimento> antecedentesClinicos;
	
	@Column(name="historia_doenca_atual", length=1000)
	@Size(max=1000)
	private String historiaDoencaAtual;
	
	@Column(name="isda", length=1000)
	@Size(max=1000)
	private String isda;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Usuario getPaciente() {
		return paciente;
	}
	
	public String getDataFormatada() {
		return new SimpleDateFormat(Constantes.FORMATO_DATA_HORA_DIA_SEMANA).format(data);
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public Set<MedicamentoAtendimento> getMedicamentos() {
		return medicamentos;
	}

	public void setMedicamentos(Set<MedicamentoAtendimento> medicamentos) {
		this.medicamentos = medicamentos;
	}

	public Set<QueixaPrincipalAtendimento> getQueixasPrincipais() {
		return queixasPrincipais;
	}

	public void setQueixasPrincipais(Set<QueixaPrincipalAtendimento> queixasPrincipais) {
		this.queixasPrincipais = queixasPrincipais;
	}

	public String getHistoriaDoencaAtual() {
		return historiaDoencaAtual;
	}

	public void setHistoriaDoencaAtual(String historiaDoencaAtual) {
		this.historiaDoencaAtual = historiaDoencaAtual;
	}

	public String getIsda() {
		return isda;
	}

	public void setIsda(String isda) {
		this.isda = isda;
	}

	public Set<AntecedenteClinicoAtendimento> getAntecedentesClinicos() {
		return antecedentesClinicos;
	}

	public void setAntecedentesClinicos(Set<AntecedenteClinicoAtendimento> antecedentesClinicos) {
		this.antecedentesClinicos = antecedentesClinicos;
	}
}