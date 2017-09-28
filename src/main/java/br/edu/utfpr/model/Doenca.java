package br.edu.utfpr.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_doenca")
public class Doenca extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="doenca_sequence", sequenceName=Constantes.PEP_OWNER + "doenca_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="doenca_sequence")
	@Column(name="id_doenca")
	private Long id;
	
	@Column(name="codigoCid", length=30)
	@Size(max=30)
	private String codigoCid;
	
	@Column(name="descricao", length=300)
	@Size(max=300)
	private String descricao;
	
	@Column(name="chk_ativo")
	private Boolean chkAtivo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "doenca")
	private Set<AntecedenteClinicoAtendimento> antecedentesClinicosAtendimentos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "doenca")
	private Set<AntecedenteFamiliarAtendimento> antecedentesFamiliaresAtendimentos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "doenca")
	private Set<DoencaDiagnosticadaAtendimento> doencasDiagnosticadasAtendimentos;
	
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

	public String getCodigoCid() {
		return codigoCid;
	}

	public void setCodigoCid(String codigoCid) {
		this.codigoCid = codigoCid;
	}

	public Set<AntecedenteClinicoAtendimento> getAntecedentesClinicosAtendimentos() {
		return antecedentesClinicosAtendimentos;
	}

	public void setAntecedentesClinicosAtendimentos(Set<AntecedenteClinicoAtendimento> antecedentesClinicosAtendimentos) {
		this.antecedentesClinicosAtendimentos = antecedentesClinicosAtendimentos;
	}

	public Set<AntecedenteFamiliarAtendimento> getAntecedentesFamiliaresAtendimentos() {
		return antecedentesFamiliaresAtendimentos;
	}

	public void setAntecedentesFamiliaresAtendimentos(Set<AntecedenteFamiliarAtendimento> antecedentesFamiliaresAtendimentos) {
		this.antecedentesFamiliaresAtendimentos = antecedentesFamiliaresAtendimentos;
	}

	public Set<DoencaDiagnosticadaAtendimento> getDoencasDiagnosticadasAtendimentos() {
		return doencasDiagnosticadasAtendimentos;
	}

	public void setDoencasDiagnosticadasAtendimentos(Set<DoencaDiagnosticadaAtendimento> doencasDiagnosticadasAtendimentos) {
		this.doencasDiagnosticadasAtendimentos = doencasDiagnosticadasAtendimentos;
	}

}