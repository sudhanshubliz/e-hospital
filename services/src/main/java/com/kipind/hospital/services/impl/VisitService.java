package com.kipind.hospital.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kipind.hospital.dataaccess.IVisitDAO;
import com.kipind.hospital.datamodel.Assign;
import com.kipind.hospital.datamodel.Checkup;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.IAssignServise;
import com.kipind.hospital.services.ICheckupService;
import com.kipind.hospital.services.IVisitService;

@Service
public class VisitService implements IVisitService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VisitService.class);

	@Inject
	private IVisitDAO VisitDAO;
	@Inject
	private ICheckupService checkupService;
	@Inject
	private IAssignServise assignServise;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of VisitService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Visit getById(Long id) {
		Visit entity = VisitDAO.getById(id);
		return entity;
	}

	@Override
	public Visit getByIdFull(Long id) {
		Visit entity = VisitDAO.getByIdFull(id);
		return entity;
	}

	@Override
	public Visit saveOrUpdate(Visit visit) {
		if (visit.getId() == null) {
			return VisitDAO.insert(visit);
		} else {
			return VisitDAO.update(visit);
		}

	}

	@Override
	public Set<Visit> saveOrUpdate(List<Visit> visits) {
		Set<Visit> resultSet = new HashSet<Visit>();
		for (Visit visit : visits) {
			if (visit.getId() == null) {
				resultSet.add(VisitDAO.insert(visit));
			} else {
				resultSet.add(VisitDAO.update(visit));
			}
		}
		return resultSet;

	}

	@Override
	public void delete(Visit visit) {
		LOGGER.debug("Remove: {}", visit);
		VisitDAO.delete(visit.getId());

	}

	@Override
	public void delete(List<Visit> visitsList) {
		LOGGER.debug("Remove List: {}");

		List<Long> idList = new ArrayList<Long>();

		for (Visit visit : visitsList) {
			idList.add(visit.getId());
		}
		VisitDAO.delete(idList);

	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all products");
		VisitDAO.dropAll();
	}

	@Override
	public List<Visit> getAllVisits() {
		return VisitDAO.getAll();

	}

	@Override
	public Visit getOpenVisitForPatient(Long patientId) {
		return getOpenVisitForPatient(patientId);
	}

	@Override
	public List<Checkup> getCaseRecordForVisit(Long visitId) {
		List<Checkup> res = checkupService.getAllCheckupsOfVisit(visitId);
		int i = 0;
		Boolean addRestFlag = false;
		Date prsExecDate;
		for (Assign assign : assignServise.getAllAssignsOfVisit(visitId)) {

			if (assign.getResDt() != null) {
				prsExecDate = assign.getResDt();
			} else {
				prsExecDate = assign.getPrscDt();
			}
			if (addRestFlag || assign.getPrscDt().after(res.get(i).getChDt()) || (prsExecDate.after(res.get(i).getChDt()))) {
				res.add(i, createAssign(assign));
				i++;
			} else {
				res.get(i).setInterview("[interview] " + res.get(i).getInterview());
				i++;
				while (i < res.size() && assign.getPrscDt().before(res.get(i).getChDt()) && prsExecDate.before(res.get(i).getChDt())) {
					res.get(i).setInterview("[interview] " + res.get(i).getInterview());
					i++;
				}
				if (i < res.size()) {
					res.add(i, createAssign(assign));
					i++;
				} else {
					addRestFlag = true;
					res.add(i, createAssign(assign));
					i++;
				}
			}

		}
		while (i < res.size()) {
			res.get(i).setInterview("[interview] " + res.get(i).getInterview());
			i++;
		}

		return res;
	}

	private Checkup createAssign(Assign assign) {
		Checkup checkAssign = new Checkup();
		checkAssign.setChDt(assign.getPrscDt());
		checkAssign.setInterview(createAssignInfo(assign));
		checkAssign.setPersonal(assign.getPrscPersonal());
		return checkAssign;
	}

	private String createAssignInfo(Assign assign) {
		String res;
		res = "[assign] " + assign.getPrscText();
		if (assign.getResText() != null) {
			res = res + "<<|>> [result] " + assign.getResText() + "( " + assign.getResPersonal().getSecondName() + " "
					+ assign.getResPersonal().getFirstName().substring(0, 1) + "., " + assign.getResDt() + " )";
		} else {

			res = res + " [execute]";
		}
		return res;
	}

}
