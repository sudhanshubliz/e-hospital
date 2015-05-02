package by.kipind.hospital.dataaccess;

import java.util.List;

import by.kipind.hospital.datamodel.Personal;
import by.kipind.hospital.datamodel.Visit;

public interface IPersonalDAO extends IAbstractDAO<Long, Personal> {

	List<Visit> GetAllOpenVisitByPersId(Long persId);
}
