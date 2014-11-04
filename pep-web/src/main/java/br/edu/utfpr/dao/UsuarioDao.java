package 
br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.model.Usuario_;

public class UsuarioDao extends GenericDao<Usuario, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Usuario> retornarUsuarios(Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> q = qb.createQuery(Usuario.class);
		Root<Usuario> root = q.from(Usuario.class);

		if (chkAtivo != null) {
			q.where(qb.equal(root.get(Usuario_.chkAtivo), chkAtivo));
		}
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public Usuario retornarUsuarioPorEmail(String email) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> q = qb.createQuery(Usuario.class);
		Root<Usuario> root = q.from(Usuario.class);

		q.where(qb.equal(root.get(Usuario_.email), email));
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
