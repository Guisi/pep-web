package 
br.edu.utfpr.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.edu.utfpr.model.TokenCadastro;
import br.edu.utfpr.model.TokenCadastro_;

public class TokenCadastroDao extends GenericDao<TokenCadastro, Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	public TokenCadastro retornarTokenAtivoPorUsuario(String username, Date dataValidade) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<TokenCadastro> q = qb.createQuery(TokenCadastro.class);
		Root<TokenCadastro> root = q.from(TokenCadastro.class);

		//email do usuario
		Predicate predicate = qb.equal(root.get(TokenCadastro_.txEmailUsuario), username);
		
		//se esta ativo
		predicate = qb.and(predicate, qb.equal(root.get(TokenCadastro_.chkAtivo), true));
		
		//se estah valido
		if (dataValidade != null) {
			predicate = qb.and(predicate, qb.greaterThanOrEqualTo(root.get(TokenCadastro_.dtGeracao), dataValidade));
		}
		
		q.where(predicate);
		
		return entityManager.createQuery(q).getSingleResult();
	}
}
