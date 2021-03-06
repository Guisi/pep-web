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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.constants.PerfilEnum;

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
	
	@Column(name="chk_possui_especialidades")
	private Boolean possuiEspecialidades;
	
	@Column(name="chk_possui_convenios")
	private Boolean possuiConvenios;
	
	@Column(name="chk_perfil_predefinido")
	private Boolean perfilPreDefinido;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name=Constantes.PEP_OWNER + "tb_perfil_autorizacao",
        joinColumns=@JoinColumn(name="id_perfil"),
        inverseJoinColumns=@JoinColumn(name="id_autorizacao"))
	@OrderBy(value = "nome")
	private Set<Autorizacao> autorizacoes;
	
	@ManyToMany(mappedBy="perfisUsuario", fetch = FetchType.LAZY)
	private Set<Usuario> usuarios;
	
	@Transient
	public boolean isPerfilPaciente() {
		return PerfilEnum.PACIENTE.getNomePerfil().equals(getNome());
	}

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

	public Set<Autorizacao> getAutorizacoes() {
		return autorizacoes;
	}

	public void setAutorizacoes(Set<Autorizacao> autorizacoes) {
		this.autorizacoes = autorizacoes;
	}

	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Boolean getPossuiEspecialidades() {
		return possuiEspecialidades;
	}

	public void setPossuiEspecialidades(Boolean possuiEspecialidades) {
		this.possuiEspecialidades = possuiEspecialidades;
	}

	public Boolean getPossuiConvenios() {
		return possuiConvenios;
	}

	public void setPossuiConvenios(Boolean possuiConvenios) {
		this.possuiConvenios = possuiConvenios;
	}

	public Boolean getPerfilPreDefinido() {
		return perfilPreDefinido;
	}

	public void setPerfilPreDefinido(Boolean perfilPreDefinido) {
		this.perfilPreDefinido = perfilPreDefinido;
	}
	
}