package com.kipind.hospital.dataaccess;

import java.util.List;

import com.kipind.hospital.datamodel.Personal;
import com.kipind.hospital.datamodel.Visit;

public interface IPersonalDAO extends IAbstractDAO<Long, Personal> {

	List<Visit> GetAllOpenVisitByPersId(Long persId);

}
