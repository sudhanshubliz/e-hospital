package by.kipind.hospital.dataaccess.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.kipind.hospital.dataaccess.IVisitDAO;
import by.kipind.hospital.datamodel.Visit;
import by.kipind.hospital.datamodel.Visit_;

//import by.dzhvisuhko.sample.datamodel.Product_;

@Repository
public class VisitDAO extends AbstractDAO<Long, Visit> implements IVisitDAO {

	protected VisitDAO() {
		super(Visit.class);
	}

	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Visit> root = criteria.from(Visit.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	public List<Visit> getAllVisits(SingularAttribute<Visit, ?> attr, boolean ascending, int startRecord, int pageSize) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Visit> criteria = cBuilder.createQuery(Visit.class);
		Root<Visit> root = criteria.from(Visit.class);

		criteria.select(root);
		criteria.orderBy(new OrderImpl(root.get(attr), ascending));

		TypedQuery<Visit> query = getEm().createQuery(criteria);
		query.setFirstResult(startRecord);
		query.setMaxResults(pageSize);

		List<Visit> results = query.getResultList();
		return results;
	}

	@Override
	public Visit getByIdFull(Long id) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Visit> criteriaQuery = cBuilder.createQuery(Visit.class);

		Root<Visit> visit = criteriaQuery.from(Visit.class);

		criteriaQuery.select(visit);

		criteriaQuery.where(cBuilder.equal(visit.get(Visit_.id), id));

		visit.fetch(Visit_.patient, JoinType.LEFT);
		visit.fetch(Visit_.ward, JoinType.LEFT);
		criteriaQuery.distinct(true);

		TypedQuery<Visit> query = getEm().createQuery(criteriaQuery);
		try {
			Visit result = query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			return null;
		}

	}

}
