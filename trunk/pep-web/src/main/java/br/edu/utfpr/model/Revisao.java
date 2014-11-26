package br.edu.utfpr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
}
