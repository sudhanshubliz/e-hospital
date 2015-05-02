package by.kipind.hospital.dataaccess.impl;

import org.springframework.stereotype.Repository;

import by.kipind.hospital.dataaccess.IPatientDAO;
import by.kipind.hospital.datamodel.Patient;

@Repository
public class PatientDAO extends AbstractDAO<Long, Patient> implements IPatientDAO {

	protected PatientDAO() {
		super(Patient.class);
		// TODO Auto-generated constructor stub
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
