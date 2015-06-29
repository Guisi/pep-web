package br.edu.utfpr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_exame_fisico_atendimento")
public class ExameFisicoAtendimento extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="exame_fisico_atendimento_sequence", sequenceName=Constantes.PEP_OWNER + "exame_fisico_atendimento_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="exame_fisico_atendimento_sequence")
	@Column(name="id_exame_fisico_atendimento")
	private Long id;
	
	@JoinColumn(name = "id_atendimento")
	@OneToOne(fetch = FetchType.LAZY)
	private Atendimento atendimento;
	
	@Column(name="peso")
	private Double peso;
	
	@Column(name="altura")
	private Double altura;
	
	@Column(name="superficie_corporea")
	private Double superficieCorporea;
	
	@Column(name="temperatura")
	private Double temperatura;
	
	@Column(name="imc")
	private Double imc;
	
	@Column(name="circunferencia_abdominal")
	private Double circunferenciaAbdominal;
	
	@Column(name="pressao_sentado_pas")
	private Double pressaoSentadoPas;
	
	@Column(name="pressao_sentado_pad")
	private Double pressaoSentadoPad;
	
	@Column(name="pressao_deitado_pas")
	private Double pressaoDeitadoPas;
	
	@Column(name="pressao_deitado_pad")
	private Double pressaoDeitadoPad;
	
	@Column(name="aspecto_geral", length=100)
	@Size(max=100)
	private String aspectoGeral;
	
	@Column(name="mucosas", length=100)
	@Size(max=100)
	private String mucosas;
	
	@Column(name="olhos_face", length=100)
	@Size(max=100)
	private String olhosFace;
	
	@Column(name="pescoco", length=100)
	@Size(max=100)
	private String pescoco;
	
	@Column(name="sistema_cardiorespiratorio", length=100)
	@Size(max=100)
	private String sistemaCardiorespiratorio;
	
	@Column(name="pele_dermatologico", length=100)
	@Size(max=100)
	private String peleDermatologico;
	
	@Column(name="abdome_superior", length=100)
	@Size(max=100)
	private String abdomeSuperior;
	
	@Column(name="abdome_inferior", length=100)
	@Size(max=100)
	private String abdomeInferior;
	
	@Column(name="membros", length=100)
	@Size(max=100)
	private String membros;
	
	@Column(name="neurologico", length=100)
	@Size(max=100)
	private String neurologico;
	
	@Column(name="observacoes", length=300)
	@Size(max=300)
	private String observacoes;
	
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

	public String getAspectoGeral() {
		return aspectoGeral;
	}

	public void setAspectoGeral(String aspectoGeral) {
		this.aspectoGeral = aspectoGeral;
	}

	public boolean isAtendimentoAnterior() {
		return atendimentoAnterior;
	}

	public void setAtendimentoAnterior(boolean atendimentoAnterior) {
		this.atendimentoAnterior = atendimentoAnterior;
	}

	public String getMucosas() {
		return mucosas;
	}

	public void setMucosas(String mucosas) {
		this.mucosas = mucosas;
	}

	public String getOlhosFace() {
		return olhosFace;
	}

	public void setOlhosFace(String olhosFace) {
		this.olhosFace = olhosFace;
	}

	public String getPescoco() {
		return pescoco;
	}

	public void setPescoco(String pescoco) {
		this.pescoco = pescoco;
	}

	public String getSistemaCardiorespiratorio() {
		return sistemaCardiorespiratorio;
	}

	public void setSistemaCardiorespiratorio(String sistemaCardiorespiratorio) {
		this.sistemaCardiorespiratorio = sistemaCardiorespiratorio;
	}

	public String getPeleDermatologico() {
		return peleDermatologico;
	}

	public void setPeleDermatologico(String peleDermatologico) {
		this.peleDermatologico = peleDermatologico;
	}

	public String getAbdomeSuperior() {
		return abdomeSuperior;
	}

	public void setAbdomeSuperior(String abdomeSuperior) {
		this.abdomeSuperior = abdomeSuperior;
	}

	public String getAbdomeInferior() {
		return abdomeInferior;
	}

	public void setAbdomeInferior(String abdomeInferior) {
		this.abdomeInferior = abdomeInferior;
	}

	public String getMembros() {
		return membros;
	}

	public void setMembros(String membros) {
		this.membros = membros;
	}

	public String getNeurologico() {
		return neurologico;
	}

	public void setNeurologico(String neurologico) {
		this.neurologico = neurologico;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getSuperficieCorporea() {
		return superficieCorporea;
	}

	public void setSuperficieCorporea(Double superficieCorporea) {
		this.superficieCorporea = superficieCorporea;
	}

	public Double getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Double temperatura) {
		this.temperatura = temperatura;
	}

	public Double getImc() {
		return imc;
	}

	public void setImc(Double imc) {
		this.imc = imc;
	}

	public Double getCircunferenciaAbdominal() {
		return circunferenciaAbdominal;
	}

	public void setCircunferenciaAbdominal(Double circunferenciaAbdominal) {
		this.circunferenciaAbdominal = circunferenciaAbdominal;
	}

	public Double getPressaoSentadoPas() {
		return pressaoSentadoPas;
	}

	public void setPressaoSentadoPas(Double pressaoSentadoPas) {
		this.pressaoSentadoPas = pressaoSentadoPas;
	}

	public Double getPressaoSentadoPad() {
		return pressaoSentadoPad;
	}

	public void setPressaoSentadoPad(Double pressaoSentadoPad) {
		this.pressaoSentadoPad = pressaoSentadoPad;
	}

	public Double getPressaoDeitadoPas() {
		return pressaoDeitadoPas;
	}

	public void setPressaoDeitadoPas(Double pressaoDeitadoPas) {
		this.pressaoDeitadoPas = pressaoDeitadoPas;
	}

	public Double getPressaoDeitadoPad() {
		return pressaoDeitadoPad;
	}

	public void setPressaoDeitadoPad(Double pressaoDeitadoPad) {
		this.pressaoDeitadoPad = pressaoDeitadoPad;
	}
	
}