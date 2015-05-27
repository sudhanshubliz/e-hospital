package com.kipind.hospital.services;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Transactional;

import com.kipind.hospital.datamodel.Patient;
import com.kipind.hospital.datamodel.objectPrototype.PatientPrototype;

public interface IPatientService {

	Patient getById(Long id);

	@Transactional
	Patient saveOrUpdate(Patient patient);

	@Transactional
	void delete(Patient patient);

	@Transactional
	void delete(List<Patient> ids);

	@Transactional
	void deleteAll();

	List<Patient> getAllPatients();

	List<Patient> getAllByField(SingularAttribute<? super Patient, ?> attribute, Object value);

	List<PatientPrototype> getAllPatientInfo(String sortParam, boolean ascending, int first, int count);

	Long getCount();

}