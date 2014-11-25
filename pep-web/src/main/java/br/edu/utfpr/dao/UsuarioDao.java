package 
br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import br.edu.utfpr.constants.PerfilEnum;
import br.edu.utfpr.model.Perfil;
import br.edu.utfpr.model.Perfil_;
import br.edu.utfpr.model.Usuario;
import br.edu.utfpr.model.Usuario_;

import com.ocpsoft.pretty.faces.util.StringUtils;

public class UsuarioDao extends GenericDao<Usuario, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public List<Usuario> retornarUsuarios(String textoPesquisa, Boolean chkAtivo, PerfilEnum perfil) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> q = qb.createQuery(Usuario.class);
		Root<Usuario> root = q.from(Usuario.class);

		List<Predicate> predicados = new ArrayList<>();
		if (StringUtils.isNotBlank(textoPesquisa)) {
			predicados.add(qb.like(qb.lower(root.get(Usuario_.nomeCompleto)), "%" + textoPesquisa.toLowerCase() + "%"));
		}
		
		if (perfil != null) {
			SetJoin<Usuario, Perfil> perfilJoin = root.join(Usuario_.perfisUsuario);
			predicados.add(qb.equal(perfilJoin.get(Perfil_.nome), perfil.getNomePerfil()));
		}
		
		if (chkAtivo != null) {
			predicados.add(qb.equal(root.get(Usuario_.chkAtivo), chkAtivo));
		}
		
		q.where(predicados.toArray(new Predicate[predicados.size()]));
		
		q.orderBy(qb.asc(root.get(Usuario_.nomeCompleto)));
		
		return entityManager.createQuery(q).getResultList();
	}
	
	public Usuario retornarUsuarioPorEmail(String email, Boolean chkAtivo) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> q = qb.createQuery(Usuario.class);
		Root<Usuario> root = q.from(Usuario.class);

		Predicate predicate = qb.equal(root.get(Usuario_.email), email);
		if (chkAtivo != null) {
			predicate = qb.and(predicate, qb.equal(root.get(Usuario_.chkAtivo), chkAtivo));
		}
		
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
	
	public Usuario retornarUsuarioPorId(Long id) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Usuario> q = qb.createQuery(Usuario.class);
		Root<Usuario> root = q.from(Usuario.class);
		root.fetch(Usuario_.perfisUsuario, JoinType.LEFT);
		root.fetch(Usuario_.especialidades, JoinType.LEFT);
		root.fetch(Usuario_.convenios, JoinType.LEFT);
		
		q.where(qb.equal(root.get(Usuario_.id), id));
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
