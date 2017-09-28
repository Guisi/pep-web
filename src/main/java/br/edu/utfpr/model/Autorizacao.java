package br.edu.utfpr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_autorizacao")
public class Autorizacao extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="autorizacao_sequence", sequenceName=Constantes.PEP_OWNER + "autorizacao_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="autorizacao_sequence")
	@Column(name="id_autorizacao")
	private Long id;
	
	@Column(name="nome", length=100)
	@Size(max=100)
	private String nome;
	
	@Column(name="descricao", length=500)
	@Size(max=500)
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}