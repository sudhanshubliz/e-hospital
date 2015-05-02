package by.kipind.hospital.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.kipind.hospital.dataaccess.IResultSourceDAO;
import by.kipind.hospital.datamodel.ResultSource;
import by.kipind.hospital.services.IResultSourceService;

@Service
public class ResultSourceService implements IResultSourceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResultSourceService.class);

	@Inject
	private IResultSourceDAO ResultSourceDAO;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of ResultSourceService is created. Class is: {}", getClass().getName());
	}

	@Override
	public ResultSource getById(Long id) {
		ResultSource entity = ResultSourceDAO.getById(id);
		return entity;
	}

	@Override
	public void saveOrUpdate(ResultSource resultSource) {
		if (resultSource.getId() == null) {
			ResultSourceDAO.insert(resultSource);
		} else {
			ResultSourceDAO.update(resultSource);
		}

	}

	@Override
	public void delete(ResultSource resultSource) {
		LOGGER.debug("Remove: {}", resultSource);
		ResultSourceDAO.delete(resultSource.getId());

	}

	@Override
	public void delete(List<ResultSource> resultSourcesList) {
		LOGGER.debug("Remove List: {}");

		List<Long> idList = new ArrayList<Long>();

		for (ResultSource resultSource : resultSourcesList) {
			idList.add(resultSource.getId());
		}
		ResultSourceDAO.delete(idList);

	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all products");
		ResultSourceDAO.dropAll();
	}

	@Override
	public List<ResultSource> getAllResultSource() {
		return ResultSourceDAO.getAll();

	}

}
