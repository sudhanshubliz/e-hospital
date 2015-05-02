package by.kipind.hospital.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.kipind.hospital.datamodel.Patient;

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

}