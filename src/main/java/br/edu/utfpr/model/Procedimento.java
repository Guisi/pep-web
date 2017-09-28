package br.edu.utfpr.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.constants.TipoProcedimento;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_procedimento")
public class Procedimento extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="procedimento_sequence", sequenceName=Constantes.PEP_OWNER + "procedimento_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="procedimento_sequence")
	@Column(name="id_procedimento")
	private Long id;
	
	@Column(name="descricao", length=300)
	@Size(max=300)
	private String descricao;
	
	@Column(name="tipo_procedimento")
	@Enumerated(EnumType.STRING)
	private TipoProcedimento tipoProcedimento;
	
	@Column(name="chk_ativo")
	private Boolean chkAtivo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "procedimento")
	private Set<AntecedenteCirurgicoAtendimento> antecedentesCirurgicosAtendimentos;
	
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

	public TipoProcedimento getTipoProcedimento() {
		return tipoProcedimento;
	}

	public void setTipoProcedimento(TipoProcedimento tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}

	public Set<AntecedenteCirurgicoAtendimento> getAntecedentesCirurgicosAtendimentos() {
		return antecedentesCirurgicosAtendimentos;
	}

	public void setAntecedentesCirurgicosAtendimentos(Set<AntecedenteCirurgicoAtendimento> antecedentesCirurgicosAtendimentos) {
		this.antecedentesCirurgicosAtendimentos = antecedentesCirurgicosAtendimentos;
	}

}