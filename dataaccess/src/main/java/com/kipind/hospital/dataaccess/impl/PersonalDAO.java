package com.kipind.hospital.dataaccess.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.Validate;
import org.hibernate.jpa.criteria.OrderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kipind.hospital.dataaccess.IPersonalDAO;
import com.kipind.hospital.datamodel.Personal;
import com.kipind.hospital.datamodel.Personal_;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.datamodel.Visit_;
import com.kipind.hospital.datamodel.enam.EDischargeStatus;
import com.kipind.hospital.datamodel.objectPrototype.PersonalPrototype;

@Repository
public class PersonalDAO extends AbstractDAO<Long, Personal> implements IPersonalDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonalDAO.class);

	protected PersonalDAO() {
		super(Personal.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void delete(Long key) {
		Personal personal = getEm().find(Personal.class, key);
		personal.setDelMarker(true);
		getEm().merge(personal);
		getEm().flush();
	}

	@Override
	public void delete(List<Long> ids) {

		if (ids.size() > 0) {
			String qStr = ("UPDATE from " + Personal.class.getSimpleName() + " p SET p.delMarker=:delMark where p.id in (:ids) ");
			Query deleteWithFlagQuery = getEm().createQuery(qStr); //
			deleteWithFlagQuery.setParameter("ids", ids);
			deleteWithFlagQuery.setParameter("delMark", true);
			deleteWithFlagQuery.executeUpdate();

		} else {
			// TODO: прокинуть эксепшен до service слоя
			LOGGER.warn("Attempt to delete objects by empty parametr list", getEm().hashCode(), Personal.class);
		}
	}

	@Override
	public void deleteAll() {

		String qStr = ("UPDATE from " + Personal.class.getSimpleName() + " p SET p.delMarker=:delMark  ");
		Query deleteWithFlagQuery = getEm().createQuery(qStr); //
		deleteWithFlagQuery.setParameter("delMark", true);
		deleteWithFlagQuery.executeUpdate();

	}

	@Override
	public Personal getById(Long id) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Personal> criteriaQuery = cBuilder.createQuery(Personal.class);
		Root<Personal> personal = criteriaQuery.from(Personal.class);

		criteriaQuery.select(personal);
		criteriaQuery.where(cBuilder.and(cBuilder.equal(personal.get(Personal_.id), id), cBuilder.equal(personal.get(Personal_.delMarker), false)));

		TypedQuery<Personal> query = getEm().createQuery(criteriaQuery);
		Personal results;
		try {
			results = query.getSingleResult();
		} catch (NoResultException e) {
			results = null;
		}
		return results;

	}

	@Override
	public Personal getByIdFull(Long id) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Personal> criteriaQuery = cBuilder.createQuery(Personal.class);
		Root<Personal> personal = criteriaQuery.from(Personal.class);

		criteriaQuery.select(personal);

		criteriaQuery.where(cBuilder.and(cBuilder.equal(personal.get(Personal_.id), id), cBuilder.equal(personal.get(Personal_.delMarker), false)));

		personal.fetch(Personal_.wards, JoinType.LEFT);

		TypedQuery<Personal> query = getEm().createQuery(criteriaQuery);
		Personal results;
		try {
			results = query.getSingleResult();
		} catch (NoResultException e) {
			results = null;
		}
		return results;

	}

	@Override
	public List<Personal> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Personal> criteriaQuery = cBuilder.createQuery(Personal.class);
		Root<Personal> personal = criteriaQuery.from(Personal.class);

		criteriaQuery.select(personal);

		criteriaQuery.where(cBuilder.equal(personal.get(Personal_.delMarker), false));

		TypedQuery<Personal> query = getEm().createQuery(criteriaQuery);
		List<Personal> results = query.getResultList();
		return results;
	}

	@Override
	public List<Visit> GetAllOpenVisitByPersId(Long persId) {

		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Visit> criteriaQuery = cBuilder.createQuery(Visit.class);
		Root<Visit> visit = criteriaQuery.from(Visit.class);
		Root<Personal> personal = criteriaQuery.from(Personal.class);
		// Root<Ward> ward = criteriaQuery.from(Ward.class);

		criteriaQuery.select(visit);

		criteriaQuery.where(cBuilder.and(
				cBuilder.and(cBuilder.isMember(visit.get(Visit_.ward), personal.get(Personal_.wards)),
						cBuilder.equal(personal.get(Personal_.id), persId)), visit.get(Visit_.dischargeFlag).in(EDischargeStatus.CURING))); // ,
																																			// EDischargeStatus.DENY,
																																			// EDischargeStatus.REQUEST

		visit.fetch(Visit_.patient);
		visit.fetch(Visit_.ward);
		criteriaQuery.distinct(true);
		criteriaQuery.orderBy(new OrderImpl(visit.get(Visit_.ward), true));

		TypedQuery<Visit> query = getEm().createQuery(criteriaQuery);
		List<Visit> results = query.getResultList();
		return results;
	}

	@Override
	public List<Visit> GetLinkedPatientsWithPaging(Long personalId, int first, int count) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Visit> criteriaQuery = cBuilder.createQuery(Visit.class);
		Root<Visit> visit = criteriaQuery.from(Visit.class);
		Root<Personal> personal = criteriaQuery.from(Personal.class);

		criteriaQuery.select(visit);

		criteriaQuery.where(cBuilder.and(cBuilder.isMember(visit.get(Visit_.ward), personal.get(Personal_.wards)), visit.get(Visit_.dischargeFlag)
				.in(EDischargeStatus.CURING, EDischargeStatus.DENY)));

		visit.fetch(Visit_.patient);
		visit.fetch(Visit_.ward);
		criteriaQuery.distinct(true);

		TypedQuery<Visit> query = getEm().createQuery(criteriaQuery);
		query.setFirstResult(first);
		query.setMaxResults(count);

		List<Visit> results = query.getResultList();
		return results;
	}

	@Override
	public List<Personal> getAllByFieldFull(final SingularAttribute<? super Personal, ?> whereAttr, final Object whereVal,
			SetAttribute<? super Personal, ?> fetchArr) {
		Validate.notNull(whereVal, "Search attributes can't be empty. Attribute: " + whereAttr.getName());
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Personal> criteriaQuery = cBuilder.createQuery(Personal.class);
		Root<Personal> entity = criteriaQuery.from(Personal.class);

		criteriaQuery.select(entity);
		criteriaQuery.where(cBuilder.equal(entity.get(whereAttr), whereVal));

		// for (SingularAttribute<? super Entity, ?> singularAttribute :
		// fetchArr) {
		entity.fetch(Personal_.wards, JoinType.LEFT);

		// }

		return getEm().createQuery(criteriaQuery).getResultList();
	}

	@Override
	public Personal getPersonalByTab(Integer tabNum) throws NonUniqueResultException {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Personal> criteriaQuery = cBuilder.createQuery(Personal.class);
		Root<Personal> personal = criteriaQuery.from(Personal.class);

		criteriaQuery.select(personal);
		criteriaQuery.where(cBuilder.and(cBuilder.equal(personal.get(Personal_.tabelNumber), tabNum),
				cBuilder.equal(personal.get(Personal_.delMarker), false)));

		TypedQuery<Personal> query = getEm().createQuery(criteriaQuery);
		Personal results;
		try {
			results = query.getSingleResult();
		} catch (NoResultException e) {
			results = null;
		} catch (NonUniqueResultException e) {
			results = null;
		}
		return results;
	}

	@Override
	public List<PersonalPrototype> getAllPersonalInfo(String attr, boolean ascending, int startRecord, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Personal> getAllPersonal(String attr, boolean ascending, int startRecord, int pageSize) {
		CriteriaBuilder сriteriaBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Personal> criteriaQuery = сriteriaBuilder.createQuery(Personal.class);
		Root<Personal> rootPersonal = criteriaQuery.from(Personal.class);

		criteriaQuery.select(rootPersonal);

		rootPersonal.fetch(Personal_.wards, JoinType.LEFT);

		criteriaQuery.orderBy(new OrderImpl(rootPersonal.get(attr), ascending));

		TypedQuery<Personal> query = getEm().createQuery(criteriaQuery);

		query.setFirstResult(startRecord);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

}
