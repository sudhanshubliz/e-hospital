package com.kipind.hospital.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kipind.hospital.dataaccess.IAbstractDAO;

public abstract class AbstractDAO<ID, Entity> implements IAbstractDAO<ID, Entity> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDAO.class);

	private EntityManager em;
	private final Class<Entity> entityClass;

	// ===================================================

	protected AbstractDAO(final Class<Entity> entityClass) {
		Validate.notNull(entityClass, "entityClass could not be a null");
		this.entityClass = entityClass;
	}

	@PersistenceContext
	protected void setEntityManager(final EntityManager em) {
		LOGGER.info("Set EM {} to class {}", em.hashCode(), getClass().getName());
		this.em = em;
	}

	private Class<Entity> getEntityClass() {
		return entityClass;
	}

	public EntityManager getEm() {
		return em;
	}

	// ===================================================
	@Override
	public Entity getById(ID id) {
		// return em.find(getEntityClass(), id);
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Entity> criteriaQuery = cBuilder.createQuery(getEntityClass());

		Root<Entity> entity = criteriaQuery.from(getEntityClass());

		criteriaQuery.select(entity);
		criteriaQuery.where(cBuilder.equal(entity.get("id"), id));

		TypedQuery<Entity> query = getEm().createQuery(criteriaQuery);
		try {
			Entity result = query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

	public abstract Entity getByIdFull(ID id);

	@Override
	public List<Entity> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Entity> criteriaQuery = cBuilder.createQuery(getEntityClass());
		Root<Entity> entity = criteriaQuery.from(getEntityClass());

		criteriaQuery.select(entity);

		TypedQuery<Entity> query = getEm().createQuery(criteriaQuery);
		List<Entity> results = query.getResultList();
		return results;
	}

	@Override
	public List<Entity> getAllByField(final SingularAttribute<? super Entity, ?> attribute, final Object value) {
		Validate.notNull(value, "Search attributes can't be empty. Attribute: " + attribute.getName());
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Entity> criteriaQuery = cBuilder.createQuery(getEntityClass());
		Root<Entity> entity = criteriaQuery.from(getEntityClass());

		criteriaQuery.select(entity);
		criteriaQuery.where(cBuilder.equal(entity.get(attribute), value));

		return getEm().createQuery(criteriaQuery).getResultList();
	}

	@Override
	public List<Entity> getAllByFieldFull(final SingularAttribute<? super Entity, ?> whereAttr, final Object whereVal,
			SetAttribute<? super Entity, ?> fetchArr) {
		Validate.notNull(whereVal, "Search attributes can't be empty. Attribute: " + whereAttr.getName());
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Entity> criteriaQuery = cBuilder.createQuery(getEntityClass());
		Root<Entity> entity = criteriaQuery.from(getEntityClass());

		criteriaQuery.select(entity);
		criteriaQuery.where(cBuilder.equal(entity.get(whereAttr), whereVal));

		// for (SingularAttribute<? super Entity, ?> singularAttribute :
		// fetchArr) {
		entity.fetch(fetchArr, JoinType.LEFT);

		// }

		return getEm().createQuery(criteriaQuery).getResultList();
	}

	@Override
	public Entity insert(final Entity entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public Entity update(Entity entity) {
		entity = em.merge(entity);
		em.flush();
		return entity;
	}

	@Override
	public void delete(final ID key) {
		em.remove(em.find(getEntityClass(), key));
	}

	@Override
	public void delete(List<ID> ids) {

		if (ids.size() > 0) {
			Query query = em.createQuery(String.format("delete from %s e where e.id in (:ids)", getEntityClass().getSimpleName()));
			query.setParameter("ids", ids);
			query.executeUpdate();
		} else {
			LOGGER.warn("Attempt to delete objects by empty parametr list", em.hashCode(), getClass().getName());
		}
	}

	// фенкциональное удаление
	@Override
	public void deleteAll() {

		Query q1 = em.createQuery(String.format("delete from %s", getEntityClass().getSimpleName()));
		q1.executeUpdate();
		em.flush();

	}

	// для очистки базы
	@Override
	public void dropAll() {
		Query q1 = em.createQuery(String.format("delete from %s", getEntityClass().getSimpleName()));
		q1.executeUpdate();
		em.flush();
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Entity> root = criteria.from(getEntityClass());

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

}
