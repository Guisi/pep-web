package br.edu.utfpr.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_especialidade")
public class Especialidade extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="especialidade_sequence", sequenceName=Constantes.PEP_OWNER + "especialidade_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="especialidade_sequence")
	@Column(name="id_especialidade")
	private Long id;
	
	@Column(name="nome_completo", length=100)
	@Size(max=100)
	private String descricao;
	
	@ManyToMany(mappedBy="especialidades", fetch = FetchType.LAZY)
	private Set<Usuario> usuarios;

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

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}