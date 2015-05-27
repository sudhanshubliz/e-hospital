package com.kipind.hospital.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import com.kipind.hospital.dataaccess.IPatientDAO;
import com.kipind.hospital.datamodel.Patient;
import com.kipind.hospital.datamodel.Patient_;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.datamodel.Visit_;
import com.kipind.hospital.datamodel.objectPrototype.PatientPrototype;

@Repository
public class PatientDAO extends AbstractDAO<Long, Patient> implements IPatientDAO {

	protected PatientDAO() {
		super(Patient.class);
	}

	/*
	 * public List<Patient> getAllPatientsBySex(Integer sex) { CriteriaBuilder
	 * cBuilder = getEm().getCriteriaBuilder();
	 * 
	 * CriteriaQuery<Patient> criteriaQuery =
	 * cBuilder.createQuery(Patient.class); Root<Patient> patient =
	 * criteriaQuery.from(Patient.class);
	 * 
	 * criteriaQuery.where(cBuilder.equal(patient.get(Patient_.sex), sex));
	 * 
	 * TypedQuery<Patient> query = getEm().createQuery(criteriaQuery);
	 * List<Patient> results = query.getResultList(); return results; }
	 */

	public Patient getByIdFull(Long id) {
		return this.getById(id);
	}

	@Override
	public List<PatientPrototype> getAllPatientInfo(String sortParam, boolean ascending, int startRecord, int pageSize) {

		CriteriaBuilder сriteriaBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<PatientPrototype> criteriaQuery = сriteriaBuilder.createQuery(PatientPrototype.class);
		Root<Visit> rootVisit = criteriaQuery.from(Visit.class);
		// Fetch<Visit, Patient> patient = rootVisit.fetch(Visit_.patient);
		Join<Visit, Patient> patient = rootVisit.join(Visit_.patient, JoinType.LEFT);
		rootVisit.fetch(Visit_.patient, JoinType.LEFT);

		// criteriaQuery.select(сriteriaBuilder.construct(PatientPrototype.class,
		// rootVisit.get(Visit_.patient), сriteriaBuilder.count(rootVisit)));
		criteriaQuery.select(сriteriaBuilder.construct(PatientPrototype.class, rootVisit.get(Visit_.patient),
				сriteriaBuilder.count(rootVisit).alias("visitsNum")));

		criteriaQuery.groupBy(rootVisit.get(Visit_.patient).get(Patient_.socialNumber), rootVisit.get(Visit_.patient).get(Patient_.address),
				rootVisit.get(Visit_.patient).get(Patient_.birthDt), rootVisit.get(Visit_.patient).get(Patient_.firstName),
				rootVisit.get(Visit_.patient).get(Patient_.lastName), rootVisit.get(Visit_.patient).get(Patient_.sex), rootVisit.get(Visit_.patient)
						.get(Patient_.id));

		// criteriaQuery.groupBy(patient.get(Patient_.socialNumber));

		// criteriaQuery.orderBy(new OrderImpl(patient.get(sortParam),
		// ascending));
		if (sortParam != "visitsNum") {
			criteriaQuery.orderBy(new OrderImpl(rootVisit.get(Visit_.patient).get(sortParam), ascending));
		} else {
			criteriaQuery.orderBy(new OrderImpl(сriteriaBuilder.count(rootVisit), ascending));
		}
		TypedQuery<PatientPrototype> query = getEm().createQuery(criteriaQuery);

		query.setFirstResult(startRecord);
		query.setMaxResults(pageSize);

		return query.getResultList();
	}

	/*
	 * @Override public void deleteAll() {
	 * 
	 * CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
	 * CriteriaDelete<Patient> delete =
	 * cBuilder.createCriteriaDelete(Patient.class); Root<Patient> patient =
	 * delete.from(Patient.class);
	 * 
	 * Query query = getEm().createQuery(delete); int rowsDel =
	 * query.executeUpdate();
	 * 
	 * }
	 * 
	 * @Override public Patient update(Patient entity) { CriteriaBuilder
	 * cBuilder = getEm().getCriteriaBuilder();
	 * 
	 * CriteriaUpdate update = cBuilder.createCriteriaUpdate(Patient.class);
	 * Root patient = update.from(Patient.class); update.set("sex",
	 * entity.getSex());
	 * 
	 * Query query = getEm().createQuery(update); query.executeUpdate(); return
	 * entity; }
	 */

}
