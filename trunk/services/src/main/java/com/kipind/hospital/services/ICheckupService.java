package com.kipind.hospital.services;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.kipind.hospital.datamodel.Checkup;

public interface ICheckupService {

	Checkup getById(Long id);

	@Transactional
	Checkup saveOrUpdate(Checkup checkup);

	@Transactional
	Set<Checkup> saveOrUpdate(List<Checkup> checkups);

	@Transactional
	void delete(Checkup checkup);

	@Transactional
	void delete(List<Checkup> ids);

	@Transactional
	void deleteAll();

	List<Checkup> getAllCheckups();

	List<Checkup> getAllCheckupsOfVisit(Long visitId);

}