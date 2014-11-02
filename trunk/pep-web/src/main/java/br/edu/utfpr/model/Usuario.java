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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.utils.FormatUtils;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_usuario")
@NamedQueries({
	@NamedQuery(name = "Usuario.retornarUsuarioPorEmailSenha",
			query = "SELECT u FROM Usuario u LEFT JOIN FETCH u.perfisUsuario p LEFT JOIN FETCH p.autorizacoes WHERE u.email = :email AND u.senha = :senha")})		
public class Usuario extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="usuario_sequence", sequenceName=Constantes.PEP_OWNER + "usuario_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="usuario_sequence")
	@Column(name="id_usuario")
	private Long id;
	
	@Column(name="nome", length=100)
	@Size(max=100)
	private String nome;
	
	@Column(name="cpf", length=11)
	@Size(max=11)
	private String cpf;
	
	@Column(name="email", length=100, unique = true)
	@Size(max=100)
	private String email;
	
	@Column(name="senha", length=100)
	@Size(max=100)
	private String senha;
	
	@Column(name="telefone", length=20)
	@Size(max=20)
	private String telefone;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="tb_usuario_perfil",
        joinColumns=@JoinColumn(name="id_usuario"),
        inverseJoinColumns=@JoinColumn(name="id_perfil"))
	private Set<Perfil> perfisUsuario;
	
	@Transient
	public String getTelefoneFormatado() {
		return FormatUtils.getFormattedPhoneNumber(telefone);
	}
	
	@Transient
	public String getCpfFormatado() {
		return FormatUtils.getFormattedCpf(cpf);
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Set<Perfil> getPerfisUsuario() {
		return perfisUsuario;
	}

	public void setPerfisUsuario(Set<Perfil> perfisUsuario) {
		this.perfisUsuario = perfisUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}