package com.kipind.hospital.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import com.kipind.hospital.datamodel.Visit;

public interface IVisitDAO extends IAbstractDAO<Long, Visit> {

	Long getCount();

	List<Visit> getAllVisits(SingularAttribute<Visit, ?> attr, boolean ascending, int startRecord, int pageSize);

	// -----------------------
	Visit getOpenVisitForPatient(Long patientId);

}