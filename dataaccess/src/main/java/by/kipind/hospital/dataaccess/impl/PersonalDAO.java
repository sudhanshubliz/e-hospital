package by.kipind.hospital.dataaccess.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import by.kipind.hospital.dataaccess.IPersonalDAO;
import by.kipind.hospital.datamodel.Personal;
import by.kipind.hospital.datamodel.Personal_;
import by.kipind.hospital.datamodel.Visit;
import by.kipind.hospital.datamodel.Visit_;
import by.kipind.hospital.datamodel.enam.EDischargeStatus;

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

	public List<Visit> GetAllOpenVisitByPersId(Long persId) {

		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Visit> criteriaQuery = cBuilder.createQuery(Visit.class);
		Root<Visit> visit = criteriaQuery.from(Visit.class);
		Root<Personal> personal = criteriaQuery.from(Personal.class);

		criteriaQuery.select(visit);

		criteriaQuery.where(cBuilder.and(cBuilder.isMember(visit.get(Visit_.ward), personal.get(Personal_.wards)), visit.get(Visit_.dischargeFlag)
				.in(EDischargeStatus.CURING, EDischargeStatus.DENY)));

		visit.fetch(Visit_.patient);

		TypedQuery<Visit> query = getEm().createQuery(criteriaQuery);
		List<Visit> results = query.getResultList();
		return results;
	}

}
