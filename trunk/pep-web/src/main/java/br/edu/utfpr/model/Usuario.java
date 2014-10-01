package br.edu.utfpr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.utils.FormatUtils;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_usuario")
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
	
	@Column(name="email", length=100)
	@Size(max=100)
	private String email;
	
	@Column(name="telefone", length=20)
	@Size(max=20)
	private String telefone;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}