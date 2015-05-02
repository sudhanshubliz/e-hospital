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

import by.kipind.hospital.dataaccess.ICheckupDAO;
import by.kipind.hospital.datamodel.Checkup;
import by.kipind.hospital.services.ICheckupService;

@Service
public class CheckupService implements ICheckupService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckupService.class);

	@Inject
	private ICheckupDAO CheckupDAO;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of CheckupService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Checkup getById(Long id) {
		Checkup entity = CheckupDAO.getById(id);
		return entity;
	}

	@Override
	public Checkup saveOrUpdate(Checkup checkup) {
		if (checkup.getId() == null) {
			return CheckupDAO.insert(checkup);
		} else {
			return CheckupDAO.update(checkup);
		}

	}

	@Override
	public Set<Checkup> saveOrUpdate(List<Checkup> checkups) {
		Set<Checkup> resultSet = new HashSet<Checkup>();
		for (Checkup checkup : checkups) {
			if (checkup.getId() == null) {
				resultSet.add(CheckupDAO.insert(checkup));
			} else {
				resultSet.add(CheckupDAO.update(checkup));
			}
		}
		return resultSet;

	}

	@Override
	public void delete(Checkup checkup) {
		LOGGER.debug("Remove: {}", checkup);
		CheckupDAO.delete(checkup.getId());

	}

	@Override
	public void delete(List<Checkup> checkupsList) {
		LOGGER.debug("Remove List: {}");

		List<Long> idList = new ArrayList<Long>();

		for (Checkup checkup : checkupsList) {
			idList.add(checkup.getId());
		}
		CheckupDAO.delete(idList);

	}

	@Override
	public void deleteAll() {
		CheckupDAO.dropAll();
	}

	@Override
	public List<Checkup> getAllCheckups() {
		return CheckupDAO.getAll();

	}

	@Override
	public List<Checkup> getAllCheckupsOfVisit(Long vistId) {
		return CheckupDAO.getAllCheckupsOfVisit(vistId);

	}

}
