package br.edu.utfpr.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	
	@Column(name="historia_doenca_atual", length=1000)
	@Size(max=1000)
	private String historiaDoencaAtual;
	
	@Column(name="isda", length=1000)
	@Size(max=1000)
	private String isda;
	
	@OrderBy("id_antecedente_clinico_atendimento")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "atendimento")
	private Set<AntecedenteClinicoAtendimento> antecedentesClinicos;
	
	@OrderBy("id_antecedente_cirurgico_atendimento")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "atendimento")
	private Set<AntecedenteCirurgicoAtendimento> antecedentesCirurgicos;
	
	@OrderBy("id_habito_atendimento")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "atendimento")
	private Set<HabitoAtendimento> habitos;
	
	@OrderBy("id_alergia_atendimento")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "atendimento")
	private Set<AlergiaAtendimento> alergias;
	
	@OrderBy("id_vacina_atendimento")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "atendimento")
	private Set<VacinaAtendimento> vacinas;
	
	@OrderBy("id_antecedente_familiar_atendimento")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "atendimento")
	private Set<AntecedenteFamiliarAtendimento> antecedentesFamiliares;
	
	@OneToOne(mappedBy = "atendimento", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private ExameFisicoAtendimento exameFisicoAtendimento;
	
	@OrderBy("id_doenca_diagnosticada_atendimento")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "atendimento")
	private Set<DoencaDiagnosticadaAtendimento> doencasDiagnosticadas;

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

	public Set<AntecedenteCirurgicoAtendimento> getAntecedentesCirurgicos() {
		return antecedentesCirurgicos;
	}

	public void setAntecedentesCirurgicos(Set<AntecedenteCirurgicoAtendimento> antecedentesCirurgicos) {
		this.antecedentesCirurgicos = antecedentesCirurgicos;
	}

	public Set<HabitoAtendimento> getHabitos() {
		return habitos;
	}

	public void setHabitos(Set<HabitoAtendimento> habitos) {
		this.habitos = habitos;
	}

	public Set<AlergiaAtendimento> getAlergias() {
		return alergias;
	}

	public void setAlergias(Set<AlergiaAtendimento> alergias) {
		this.alergias = alergias;
	}

	public Set<VacinaAtendimento> getVacinas() {
		return vacinas;
	}

	public void setVacinas(Set<VacinaAtendimento> vacinas) {
		this.vacinas = vacinas;
	}

	public Set<AntecedenteFamiliarAtendimento> getAntecedentesFamiliares() {
		return antecedentesFamiliares;
	}

	public void setAntecedentesFamiliares(Set<AntecedenteFamiliarAtendimento> antecedentesFamiliares) {
		this.antecedentesFamiliares = antecedentesFamiliares;
	}

	public ExameFisicoAtendimento getExameFisicoAtendimento() {
		return exameFisicoAtendimento;
	}

	public void setExameFisicoAtendimento(ExameFisicoAtendimento exameFisicoAtendimento) {
		this.exameFisicoAtendimento = exameFisicoAtendimento;
	}

	public Set<DoencaDiagnosticadaAtendimento> getDoencasDiagnosticadas() {
		return doencasDiagnosticadas;
	}

	public void setDoencasDiagnosticadas(Set<DoencaDiagnosticadaAtendimento> doencasDiagnosticadas) {
		this.doencasDiagnosticadas = doencasDiagnosticadas;
	}
}