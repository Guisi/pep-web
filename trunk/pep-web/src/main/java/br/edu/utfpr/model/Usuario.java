package br.edu.utfpr.model;

import java.util.Date;
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
import javax.persistence.Version;
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
	
	@Version
    @Column(name = "optlock", nullable = false)
    private Long version = 0L;
	
	@Column(name="nome_completo", length=100)
	@Size(max=100)
	private String nomeCompleto;
	
	@Column(name="nome_fantasia", length=50)
	@Size(max=50)
	private String nomeFantasia;
	
	@Column(name="cpf", length=11)
	@Size(max=11)
	private String cpf;
	
	@Column(name="rg", length=30)
	@Size(max=30)
	private String rg;
	
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
	
	@Column(name="celular", length=20)
	@Size(max=20)
	private String celular;
	
	@NotAudited
	@Column(name="qt_acessos_errados", length=2)
	private Short qtdeAcessosErrados;
	
	@Column(name="chk_ativo")
	private Boolean chkAtivo;
	
	@Column(name = "data_nascimento")
	private Date dataNascimento;
	
	@Column(name="estado_civil", length=20)
	@Size(max=20)
	private String estadoCivil;

	@NotAudited
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name=Constantes.PEP_OWNER + "tb_usuario_perfil",
        joinColumns=@JoinColumn(name="id_usuario"),
        inverseJoinColumns=@JoinColumn(name="id_perfil"))
	private Set<Perfil> perfisUsuario;
	
	@NotAudited
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name=Constantes.PEP_OWNER + "tb_usuario_especialidade",
        joinColumns=@JoinColumn(name="id_usuario"),
        inverseJoinColumns=@JoinColumn(name="id_especialidade"))
	private Set<Especialidade> especialidades;
	
	@NotAudited
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name=Constantes.PEP_OWNER + "tb_usuario_convenio",
        joinColumns=@JoinColumn(name="id_usuario"),
        inverseJoinColumns=@JoinColumn(name="id_convenio"))
	private Set<Convenio> convenios;
	
	@Transient
	public String getTelefoneFormatado() {
		return FormatUtils.getFormattedPhoneNumber(telefone);
	}
	
	@Transient
	public String getCelularFormatado() {
		return FormatUtils.getFormattedPhoneNumber(celular);
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

	public Set<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Set<Convenio> getConvenios() {
		return convenios;
	}

	public void setConvenios(Set<Convenio> convenios) {
		this.convenios = convenios;
	}
	
}