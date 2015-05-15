package com.kipind.hospital.dataaccess.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.kipind.hospital.dataaccess.IAssignDAO;
import com.kipind.hospital.datamodel.Assign;
import com.kipind.hospital.datamodel.Assign_;
import com.kipind.hospital.datamodel.Visit;

@Repository
public class AssignDAO extends AbstractDAO<Long, Assign> implements IAssignDAO {

	protected AssignDAO() {
		super(Assign.class);
	}

	@Override
	public Assign getByIdFull(Long id) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Assign> criteriaQuery = cBuilder.createQuery(Assign.class);

		Root<Assign> assign = criteriaQuery.from(Assign.class);

		criteriaQuery.select(assign);
		criteriaQuery.where(cBuilder.equal(assign.get("id"), id));

		assign.fetch(Assign_.prscPersonal, JoinType.LEFT);
		assign.fetch(Assign_.resPersonal, JoinType.LEFT);
		assign.fetch(Assign_.visit, JoinType.LEFT);
		assign.fetch(Assign_.resSourseList, JoinType.LEFT);

		TypedQuery<Assign> query = getEm().createQuery(criteriaQuery);

		try {
			Assign result = query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long getMaxGroupId() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQuery = cBuilder.createQuery(Long.class);
		Root<Assign> assign = criteriaQuery.from(Assign.class);

		criteriaQuery.select(cBuilder.max(assign.get(Assign_.periodGroupKey)));

		TypedQuery<Long> query = getEm().createQuery(criteriaQuery);
		try {
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Assign> getAllAssignsOfVisit(Long visitId) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Assign> criteriaQuery = cBuilder.createQuery(Assign.class);
		Root<Assign> assign = criteriaQuery.from(Assign.class);
		Root<Visit> visit = criteriaQuery.from(Visit.class);

		criteriaQuery.select(assign);

		criteriaQuery.where(cBuilder.equal(assign.get(Assign_.visit), visitId));

		assign.fetch(Assign_.visit, JoinType.LEFT);
		assign.fetch(Assign_.prscPersonal, JoinType.LEFT);
		assign.fetch(Assign_.prscPersonal, JoinType.LEFT);
		criteriaQuery.distinct(true);

		TypedQuery<Assign> query = getEm().createQuery(criteriaQuery);
		List<Assign> results = query.getResultList();
		return results;
	}
}
