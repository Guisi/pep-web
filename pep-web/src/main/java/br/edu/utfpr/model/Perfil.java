package br.edu.utfpr.model;

import java.util.List;

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
@Table(name=Constantes.PEP_OWNER + "tb_perfil")
public class Perfil extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="perfil_sequence", sequenceName=Constantes.PEP_OWNER + "perfil_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="perfil_sequence")
	@Column(name="id_perfil")
	private Long id;
	
	@Column(name="nome", length=100, unique = true)
	@Size(max=100)
	private String nome;
	
	@Column(name="descricao", length=500)
	@Size(max=500)
	private String descricao;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="tb_perfil_autorizacao",
        joinColumns=@JoinColumn(name="id_perfil"),
        inverseJoinColumns=@JoinColumn(name="id_autorizacao"))
	private List<Autorizacao> autorizacoes;
	
	@ManyToMany(mappedBy="perfisUsuario", fetch = FetchType.LAZY)
	private List<Usuario> usuarios;

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

	public List<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}

	public void setAutorizacoes(List<Autorizacao> autorizacoes) {
		this.autorizacoes = autorizacoes;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}