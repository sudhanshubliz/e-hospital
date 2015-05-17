package com.kipind.hospital.services;

import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.kipind.hospital.datamodel.Checkup;
import com.kipind.hospital.datamodel.Visit;

public interface IVisitService {

	Visit getById(Long id);

	Visit getByIdFull(Long id);

	@Transactional
	Visit saveOrUpdate(Visit visit);

	@Transactional
	Set<Visit> saveOrUpdate(List<Visit> visits);

	@Transactional
	void delete(Visit visit);

	@Transactional
	void delete(List<Visit> ids);

	@Transactional
	void deleteAll();

	List<Visit> getAllVisits(); // с ограничением

	// -----------------------
	Visit getOpenVisitForPatient(Long patientId);

	List<Checkup> getCaseRecordForVisit(Long visitId);
}