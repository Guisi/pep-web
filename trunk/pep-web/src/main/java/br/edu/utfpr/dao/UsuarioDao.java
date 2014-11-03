package 
br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.model.Perfil_;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.model.Usuario_;

public class UsuarioDao extends GenericDao<Usuario, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Usuario> retornarUsuarios() {
		return findAll();
	}
	
	public Usuario retornarUsuarioPorEmail(String email) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> q = qb.createQuery(Usuario.class);
		Root<Usuario> root = q.from(Usuario.class);

		q.where(qb.equal(root.get(Usuario_.email), email));
		
		return entityManager.createQuery(q).getSingleResult();
	}
	
	public Usuario retornarUsuarioPorEmailSenha(String email, String senha) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> q = qb.createQuery(Usuario.class);
		Root<Usuario> root = q.from(Usuario.class);
		Fetch<Usuario, Perfil> perfilFetch = root.fetch(Usuario_.perfisUsuario, JoinType.LEFT);
		perfilFetch.fetch(Perfil_.autorizacoes, JoinType.LEFT);
		
		q.where(qb.and(qb.equal(root.get(Usuario_.email), email),
				qb.equal(root.get(Usuario_.senha), senha)));
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
