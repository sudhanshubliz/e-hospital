package com.kipind.hospital.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kipind.hospital.dataaccess.IPersonalDAO;
import com.kipind.hospital.datamodel.Personal;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.IPersonalService;

@Service
public class PersonalService implements IPersonalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonalService.class);

	@Inject
	private IPersonalDAO personalDAO;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of ProductService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Personal getById(Long id) {

		return personalDAO.getById(id);
	}

	@Override
	public Personal getByIdFull(Long id) {

		return personalDAO.getByIdFull(id);
	}

	@Override
	public Personal saveOrUpdate(Personal personal) {
		if (personal.getId() == null) {
			return personalDAO.insert(personal);
		} else {
			return personalDAO.update(personal);
		}

	}

	@Override
	public void delete(Personal personal) {
		LOGGER.debug("Remove: {}", personal);
		personalDAO.delete(personal.getId());

	}

	@Override
	public void delete(List<Personal> personals) {
		LOGGER.debug("Remove List: {}");

		List<Long> idList = new ArrayList<Long>();

		for (Personal personal : personals) {
			idList.add(personal.getId());
		}
		personalDAO.delete(idList);

	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all products");
		personalDAO.dropAll();
	}

	@Override
	public List<Personal> getAllPersonal() {
		return personalDAO.getAll();

	}

	@Override
	public List<Visit> GetLinkedPatients(Long persId) {
		return personalDAO.GetAllOpenVisitByPersId(persId);
	}

	@Override
	public List<Visit> GetLinkedPatientsWithPaging(Long personalId, Integer first, Integer count) {
		return personalDAO.GetLinkedPatientsWithPaging(personalId, (int) first, (int) count);
	}

	@Override
	public List<Personal> getAllByField(SingularAttribute<? super Personal, ?> attribute, Object value) {

		return personalDAO.getAllByField(attribute, value);
	}

	@Override
	public List<Personal> getAllByFieldFull(SingularAttribute<? super Personal, ?> whereAttr, Object whereVal,
			SetAttribute<? super Personal, ?> fetchArr) {
		return personalDAO.getAllByFieldFull(whereAttr, whereVal, fetchArr);
	}
}
