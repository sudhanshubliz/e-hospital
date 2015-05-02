package by.kipind.hospital.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.kipind.hospital.dataaccess.IPrescribeDAO;
import by.kipind.hospital.datamodel.Prescribe;
import by.kipind.hospital.services.IPrescribeService;

@Service
public class PrescribeService implements IPrescribeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PrescribeService.class);

	@Inject
	private IPrescribeDAO prescribeDAO;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of ProductService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Prescribe getById(Long id) {
		Prescribe entity = prescribeDAO.getById(id);
		return entity;
	}

	@Override
	public void saveOrUpdate(Prescribe prescribe) {
		if (prescribe.getId() == null) {
			prescribeDAO.insert(prescribe);
		} else {
			prescribeDAO.update(prescribe);
		}

	}

	@Override
	public void delete(Prescribe prescribe) {
		LOGGER.debug("Remove: {}", prescribe);
		prescribeDAO.delete(prescribe.getId());

	}

	@Override
	public void delete(List<Prescribe> prescribes) {
		LOGGER.debug("Remove List: {}");

		List<Long> idList = new ArrayList<Long>();

		for (Prescribe prescribe : prescribes) {
			idList.add(prescribe.getId());
		}
		prescribeDAO.delete(idList);

	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all products");
		prescribeDAO.dropAll();
	}

	@Override
	public List<Prescribe> getAllPrescribes() {
		return prescribeDAO.getAll();

	}

}
