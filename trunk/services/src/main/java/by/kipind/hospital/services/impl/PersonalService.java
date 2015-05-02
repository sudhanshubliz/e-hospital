package by.kipind.hospital.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.kipind.hospital.dataaccess.IPersonalDAO;
import by.kipind.hospital.datamodel.Personal;
import by.kipind.hospital.datamodel.Visit;
import by.kipind.hospital.services.IPersonalService;

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

}
