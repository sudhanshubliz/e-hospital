package com.kipind.hospital.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.kipind.hospital.datamodel.Assign;

public interface IAssignServiсe {

	Assign getById(Long id);

	Assign getByIdFull(Long id);

	@Transactional
	void saveOrUpdate(Assign assign);

	@Transactional
	void delete(Assign assign);

	@Transactional
	void delete(List<Assign> ids);

	@Transactional
	void deleteAll();

	List<Assign> getAllAssigns(); // с ограничением

	Long getFreeGroupId();

	List<Assign> getAllAssignsOfVisit(Long visitId);

	@Transactional
	void saveAssignGroup(Assign assign, Integer period);// throws Exception;
}