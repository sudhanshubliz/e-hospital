package by.kipind.hospital.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.kipind.hospital.dataaccess.IVisitDAO;
import by.kipind.hospital.datamodel.Visit;
import by.kipind.hospital.services.IVisitService;

@Service
public class VisitService implements IVisitService {

	private static final Logger LOGGER = LoggerFactory.getLogger(VisitService.class);

	@Inject
	private IVisitDAO VisitDAO;

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

}
