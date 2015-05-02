package by.kipind.hospital.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.kipind.hospital.dataaccess.IConferenseDAO;
import by.kipind.hospital.datamodel.Conferense;
import by.kipind.hospital.services.IConferenseService;

@Service
public class ConferenseService implements IConferenseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConferenseService.class);

	@Inject
	private IConferenseDAO conferenseDAO;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of ProductService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Conferense getById(Long id) {
		Conferense entity = conferenseDAO.getById(id);
		return entity;
	}

	@Override
	public void saveOrUpdate(Conferense conferense) {
		if (conferense.getId() == null) {
			conferenseDAO.insert(conferense);
		} else {
			conferenseDAO.update(conferense);
		}

	}

	@Override
	public void delete(Conferense conferense) {
		LOGGER.debug("Remove: {}", conferense);
		conferenseDAO.delete(conferense.getId());

	}

	@Override
	public void delete(List<Conferense> conferenses) {
		LOGGER.debug("Remove List: {}");

		List<Long> idList = new ArrayList<Long>();

		for (Conferense conferense : conferenses) {
			idList.add(conferense.getId());
		}
		conferenseDAO.delete(idList);

	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all products");
		conferenseDAO.dropAll();
	}

	@Override
	public List<Conferense> getAllConferense() {
		return conferenseDAO.getAll();

	}

}
