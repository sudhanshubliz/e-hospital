package com.kipind.hospital.dataaccess;

import java.util.List;

import com.kipind.hospital.datamodel.Patient;
import com.kipind.hospital.datamodel.objectPrototype.PatientPrototype;

public interface IPatientDAO extends IAbstractDAO<Long, Patient> {

	List<PatientPrototype> getAllPatientInfo(String sortParam, boolean ascending, int first, int count);

}
