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
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.utils.FormatUtils;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_usuario")
@Audited
public class Usuario extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="usuario_sequence", sequenceName=Constantes.PEP_OWNER + "usuario_sequence", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="usuario_sequence")
	@Column(name="id_usuario")
	private Long id;
	
	@Column(name="nome_completo", length=100)
	@Size(max=100)
	private String nomeCompleto;
	
	@Column(name="nome_fantasia", length=50)
	@Size(max=50)
	private String nomeFantasia;
	
	@Column(name="cpf", length=11)
	@Size(max=11)
	private String cpf;
	
	@Column(name="email", length=100, unique = true)
	@Size(max=100)
	private String email;
	
	@NotAudited
	@Column(name="senha", length=50)
	@Size(max=50)
	private String senha;
	
	@NotAudited
	@Column(name="chk_senha_provisoria")
	private Boolean chkSenhaProvisoria;
	
	@Column(name="telefone", length=20)
	@Size(max=20)
	private String telefone;
	
	@NotAudited
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name=Constantes.PEP_OWNER + "tb_usuario_perfil",
        joinColumns=@JoinColumn(name="id_usuario"),
        inverseJoinColumns=@JoinColumn(name="id_perfil"))
	private Set<Perfil> perfisUsuario;
	
	@NotAudited
	@Column(name="qt_acessos_errados", length=2)
	private Short qtdeAcessosErrados;
	
	@Column(name="chk_ativo")
	private Boolean chkAtivo;
	
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

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getNomeFantasia() {
		if (nomeFantasia == null) {
			return nomeCompleto;
		}
		
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
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

	public Short getQtdeAcessosErrados() {
		return qtdeAcessosErrados;
	}

	public void setQtdeAcessosErrados(Short qtdeAcessosErrados) {
		this.qtdeAcessosErrados = qtdeAcessosErrados;
	}

	public Boolean getChkAtivo() {
		return chkAtivo;
	}

	public void setChkAtivo(Boolean chkAtivo) {
		this.chkAtivo = chkAtivo;
	}

	public Boolean getChkSenhaProvisoria() {
		return chkSenhaProvisoria;
	}

	public void setChkSenhaProvisoria(Boolean chkSenhaProvisoria) {
		this.chkSenhaProvisoria = chkSenhaProvisoria;
	}
	
}