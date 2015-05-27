package com.kipind.hospital.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kipind.hospital.dataaccess.IPatientDAO;
import com.kipind.hospital.datamodel.Patient;
import com.kipind.hospital.datamodel.objectPrototype.PatientPrototype;
import com.kipind.hospital.services.IPatientService;

@Service
public class PatientService implements IPatientService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientService.class);

	@Inject
	private IPatientDAO patientDAO;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of ProductService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Patient getById(Long id) {
		Patient entity = patientDAO.getById(id);
		return entity;
	}

	@Override
	public Patient saveOrUpdate(Patient patient) {
		if (patient.getId() == null) {
			return patientDAO.insert(patient);
		} else {
			return patientDAO.update(patient);
		}

	}

	@Override
	public void delete(Patient patient) {
		LOGGER.debug("Remove: {}", patient);
		patientDAO.delete(patient.getId());

	}

	@Override
	public void delete(List<Patient> patients) {
		LOGGER.debug("Remove List: {}");

		List<Long> idList = new ArrayList<Long>();

		for (Patient patient : patients) {
			idList.add(patient.getId());
		}
		patientDAO.delete(idList);

	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all products");
		patientDAO.dropAll();
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientDAO.getAll();

	}

	@Override
	public List<Patient> getAllByField(SingularAttribute<? super Patient, ?> attribute, Object value) {

		return patientDAO.getAllByField(attribute, value);

	}

	@Override
	public List<PatientPrototype> getAllPatientInfo(String sortParam, boolean ascending, int first, int count) {
		// TODO Auto-generated method stub
		return patientDAO.getAllPatientInfo(sortParam, ascending, first, count);
	}

	@Override
	public Long getCount() {
		return patientDAO.getCount();
	};

}
