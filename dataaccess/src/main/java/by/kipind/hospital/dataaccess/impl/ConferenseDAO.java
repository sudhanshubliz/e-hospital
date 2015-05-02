package by.kipind.hospital.dataaccess.impl;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.kipind.hospital.dataaccess.IConferenseDAO;
import by.kipind.hospital.datamodel.Conferense;
import by.kipind.hospital.datamodel.Conferense_;

@Repository
public class ConferenseDAO extends AbstractDAO<Long, Conferense> implements IConferenseDAO {

	protected ConferenseDAO() {
		super(Conferense.class);
		// TODO Auto-generated constructor stub
	}

	public Conferense getByIdFull(Long id) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		CriteriaQuery<Conferense> criteriaQuery = cBuilder.createQuery(Conferense.class);

		Root<Conferense> conferense = criteriaQuery.from(Conferense.class);

		criteriaQuery.select(conferense);
		criteriaQuery.where(cBuilder.equal(conferense.get("id"), id));

		conferense.fetch(Conferense_.personal, JoinType.LEFT);
		conferense.fetch(Conferense_.visits, JoinType.LEFT);

		TypedQuery<Conferense> query = getEm().createQuery(criteriaQuery);

		try {
			Conferense result = query.getSingleResult();
			return result;
		} catch (NoResultException e) {
			return null;
		}

	}
}
