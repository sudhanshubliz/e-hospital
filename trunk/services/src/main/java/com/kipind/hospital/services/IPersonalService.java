package com.kipind.hospital.services;

import java.util.List;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Transactional;

import com.kipind.hospital.datamodel.Personal;
import com.kipind.hospital.datamodel.Visit;

public interface IPersonalService {

	Personal getById(Long id);

	Personal getByIdFull(Long id);

	@Transactional
	Personal saveOrUpdate(Personal personal);

	@Transactional
	void delete(Personal personal);

	@Transactional
	void delete(List<Personal> ids);

	@Transactional
	void deleteAll();

	List<Personal> getAllPersonal();

	List<Personal> getAllByField(SingularAttribute<? super Personal, ?> attribute, Object value);

	List<Personal> getAllByFieldFull(SingularAttribute<? super Personal, ?> whereAttr, Object whereVal, SetAttribute<? super Personal, ?> fetchArr);

	// ------
	List<Visit> GetLinkedPatients(Long personalId);

	List<Visit> GetLinkedPatientsWithPaging(Long personalId, Integer first, Integer count);

	Personal getPersonalByTab(String tabNum);

	// Object getAllByFieldFull(SingularAttribute<Personal, EProf> prof, int
	// ordinal, SetAttribute<Personal, Ward> wards);
}