package com.kipind.hospital.dataaccess;

import java.util.List;

import com.kipind.hospital.datamodel.Personal;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.datamodel.objectPrototype.PersonalPrototype;

public interface IPersonalDAO extends IAbstractDAO<Long, Personal> {

	List<Visit> GetAllOpenVisitByPersId(Long persId);

	List<Visit> GetLinkedPatientsWithPaging(Long personalId, int first, int count);

	Personal getPersonalByTab(Integer tabNum);

	List<PersonalPrototype> getAllPersonalInfo(String attr, boolean ascending, int startRecord, int pageSize);

	List<Personal> getAllPersonal(String attr, boolean ascending, int startRecord, int pageSize);

}
