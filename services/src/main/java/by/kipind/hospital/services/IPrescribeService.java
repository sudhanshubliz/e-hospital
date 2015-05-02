package by.kipind.hospital.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.kipind.hospital.datamodel.Prescribe;

public interface IPrescribeService {

	Prescribe getById(Long id);

	@Transactional
	void saveOrUpdate(Prescribe prescribe);

	@Transactional
	void delete(Prescribe prescribe);

	@Transactional
	void delete(List<Prescribe> ids);

	@Transactional
	void deleteAll();

	List<Prescribe> getAllPrescribes(); // с ограничением

}