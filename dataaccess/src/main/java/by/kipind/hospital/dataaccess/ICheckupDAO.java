package by.kipind.hospital.dataaccess;

import java.util.List;

import by.kipind.hospital.datamodel.Checkup;

public interface ICheckupDAO extends IAbstractDAO<Long, Checkup> {

	List<Checkup> getAllCheckupsOfVisit(Long visitId);
}
