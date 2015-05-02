package by.kipind.hospital.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.kipind.hospital.datamodel.Conferense;

public interface IConferenseService {

	Conferense getById(Long id);

	@Transactional
	void saveOrUpdate(Conferense conferense);

	@Transactional
	void delete(Conferense conferense);

	@Transactional
	void delete(List<Conferense> ids);

	@Transactional
	void deleteAll();

	List<Conferense> getAllConferense();

}