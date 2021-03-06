package com.kipind.hospital.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kipind.hospital.dataaccess.IAssignDAO;
import com.kipind.hospital.datamodel.Assign;
import com.kipind.hospital.services.IAssignServiсe;

@Service
public class AssignServise implements IAssignServiсe {

	private static final Logger LOGGER = LoggerFactory.getLogger(AssignServise.class);

	@Inject
	private IAssignDAO assignDAO;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of ProductService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Assign getById(Long id) {
		Assign entity = assignDAO.getById(id);
		return entity;
	}

	@Override
	public Assign getByIdFull(Long id) {
		Assign entity = assignDAO.getByIdFull(id);
		return entity;
	}

	@Override
	public void saveOrUpdate(Assign assign) {
		if (assign.getId() == null) {
			assignDAO.insert(assign);
		} else {
			assignDAO.update(assign);
		}

	}

	@Override
	public void delete(Assign assign) {
		LOGGER.debug("Remove: {}", assign);
		assignDAO.delete(assign.getId());

	}

	@Override
	public void delete(List<Assign> assigns) {
		LOGGER.debug("Remove List: {}");

		List<Long> idList = new ArrayList<Long>();

		for (Assign assign : assigns) {
			idList.add(assign.getId());
		}
		assignDAO.delete(idList);

	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all products");
		assignDAO.dropAll();
	}

	@Override
	public List<Assign> getAllAssigns() {
		return assignDAO.getAll();

	}

	@Override
	public Long getFreeGroupId() {
		/*
		 * Long res = assignDAO.getMaxGroupId(); if (res == null) { res = 1; }
		 */
		return assignDAO.getMaxGroupId() + 1l;
	}

	@Override
	public List<Assign> getAllAssignsOfVisit(Long visitId) {
		return assignDAO.getAllAssignsOfVisit(visitId);

	}

	@Override
	public void saveAssignGroup(Assign assign, Integer period) {// throws
																// Exception {
		// try {
		assign.setPeriodGroupKey(this.getFreeGroupId());
		assign.setPrscDt(Calendar.getInstance().getTime());

		for (int i = 0; i < period; i++) {
			Assign insAssign = new Assign();
			insAssign.setPrscPersonal(assign.getPrscPersonal());
			insAssign.setVisit(assign.getVisit());
			insAssign.setPrscText(assign.getPrscText());
			insAssign.setPeriodGroupKey(assign.getPeriodGroupKey());
			insAssign.setPrscDt(DateUtils.addDays(assign.getPrscDt(), i));
			assignDAO.insert(insAssign);
		}
		/*
		 * } catch (Exception e) {
		 * 
		 * }
		 */
	}

}
