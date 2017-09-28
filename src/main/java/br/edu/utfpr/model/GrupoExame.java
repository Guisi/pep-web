package br.edu.utfpr.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_grupo_exame")
public class GrupoExame extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="grupo_exame_sequence", sequenceName=Constantes.PEP_OWNER + "grupo_exame_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="grupo_exame_sequence")
	@Column(name="id_grupo_exame")
	private Long id;
	
	@Column(name="descricao", length=300)
	@Size(max=300)
	private String descricao;
	
	@Column(name="chk_ativo")
	private Boolean chkAtivo;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name=Constantes.PEP_OWNER + "tb_exame_grupo_exame",
        joinColumns=@JoinColumn(name="id_grupo_exame"),
        inverseJoinColumns=@JoinColumn(name="id_exame"))
	private Set<Exame> exames;
	
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

	public Set<Exame> getExames() {
		return exames;
	}

	public void setExames(Set<Exame> exames) {
		this.exames = exames;
	}

}