package by.kipind.hospital.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.kipind.hospital.datamodel.ResultSource;

public interface IResultSourceService {

	ResultSource getById(Long id);

	@Transactional
	void saveOrUpdate(ResultSource resultSource);

	@Transactional
	void delete(ResultSource resultSource);

	@Transactional
	void delete(List<ResultSource> ids);

	@Transactional
	void deleteAll();

	List<ResultSource> getAllResultSource();

}