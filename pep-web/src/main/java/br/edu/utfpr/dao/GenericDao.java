package br.edu.utfpr.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import br.edu.utfpr.model.BaseEntity;

@SuppressWarnings("unchecked")
public abstract class GenericDao<T extends BaseEntity, PK> {
	
	@PersistenceContext(unitName = "pepPU")
	protected EntityManager entityManager;
	
	public void save(T entity) {
		if (entity.getId() == null) {
			this.persist(entity);
		} else {
			this.merge(entity);
		}
	}
	
	private void persist(T entity) {
		entityManager.persist(entity);
	}

	private void merge(T entity) {
		entityManager.merge(entity);
	}

	public void remove(T entity) {
		entityManager.remove(entity);
	}

	public void removeById(PK id) {
		T entity = getById(id);
		entityManager.remove(entity);
	}

	public T getById(PK id) {
		return entityManager.find(getTypeClass(), id);
	}

	@SuppressWarnings("rawtypes")
	public List<T> findAll(SingularAttribute... orderBy) {
		return findAll(false, orderBy);
	}
	
	@SuppressWarnings("rawtypes")
	public List<T> findAll(boolean orderDesc, SingularAttribute... orderBy) {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> q = qb.createQuery(getTypeClass());
		Root<T> root = q.from(getTypeClass());
		q.select(root);
		
		if (orderBy != null && orderBy.length > 0) {
			List<Order> orders = new ArrayList<>();
			for (SingularAttribute<? super T, ?> singularAttribute : orderBy) {
				if (orderDesc) {
					orders.add(qb.desc(root.get(singularAttribute)));
				} else {
					orders.add(qb.asc(root.get(singularAttribute)));
				}
			}
			q.orderBy(orders);
		}
		return entityManager.createQuery(q).getResultList();
	}
	
	private Class<T> getTypeClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
}