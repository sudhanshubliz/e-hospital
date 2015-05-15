package com.kipind.hospital.dataaccess;

import java.util.List;

import com.kipind.hospital.datamodel.Assign;

public interface IAssignDAO extends IAbstractDAO<Long, Assign> {

	Long getMaxGroupId();

	List<Assign> getAllAssignsOfVisit(Long visitId);

}
