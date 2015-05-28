package com.kipind.hospital.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kipind.hospital.datamodel.Ward;

public interface IWardService {

	Ward getById(Long id);

	Ward getByIdFull(Long id);

	@Transactional
	Ward saveOrUpdate(Ward ward);

	@Transactional
	void delete(Ward ward);

	@Transactional
	void deleteAll();// Только для тестирования

	List<Ward> getAllWards();

	void busyPlaceChange(Ward ward, int i);

	// ----

}