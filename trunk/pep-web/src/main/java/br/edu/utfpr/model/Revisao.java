package br.edu.utfpr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import br.edu.utfpr.constants.Constantes;
import br.edu.utfpr.utils.RevisaoListener;

@Entity
@Table(name=Constantes.PEP_OWNER + "tb_revisao")
@RevisionEntity(RevisaoListener.class)
public class Revisao extends DefaultRevisionEntity {

	private static final long serialVersionUID = 1L;

	@Column(name = "id_usuario")
	private Long idUsuario;
	
	@JoinColumn(name = "id_usuario", insertable = false, updatable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	private Usuario usuario;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
