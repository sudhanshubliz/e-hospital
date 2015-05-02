package by.kipind.hospital.services.testUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;

import by.kipind.hospital.datamodel.Patient;
import by.kipind.hospital.datamodel.Personal;
import by.kipind.hospital.datamodel.Visit;
import by.kipind.hospital.datamodel.Ward;
import by.kipind.hospital.datamodel.enam.EDischargeStatus;
import by.kipind.hospital.datamodel.enam.EHumanSex;
import by.kipind.hospital.datamodel.enam.EProf;
import by.kipind.hospital.datamodel.enam.EWardComfort;

public abstract class TestModelGenerator extends TestRandomVal {

	public static Patient getPatient() {
		Patient patient = new Patient();
		patient.setAddress(randomString("address-"));
		patient.setBirthDt(randomDate());
		patient.setFirstName(randomString("f_name-"));
		patient.setLastName(randomString("l_name-"));
		patient.setSex(EHumanSex.values()[(randomInteger(0, EHumanSex.values().length - 1))]);
		patient.setSocialNumber(randomString());
		return patient;
	}

	public static Personal getPersonal() {
		Personal personal = new Personal();
		personal.setFirstName(randomString("f_name-"));
		personal.setSecondName(randomString("s_name-"));
		personal.setTabelNumber(randomInteger(1000, 99999));
		personal.setPass(randomString());
		personal.setDelMarker(false);
		personal.setConMarker(randomBoolean());
		personal.setProf(EProf.values()[(randomInteger(0, EProf.values().length - 1))]);
		return personal;
	}

	public static Ward getWard(Set<Personal> pers) {
		int nMax = randomInteger(1, 5);
		Ward ward = new Ward();
		ward.setWardNum(randomInteger(1, 99999));
		ward.setPlaceNumSum(nMax);
		ward.setPlaceNumBisy(randomInteger(0, nMax));
		ward.setComfortLvl(EWardComfort.values()[(randomInteger(0, EWardComfort.values().length - 1))]);
		ward.setPersonal(pers);
		return ward;
	}

	public static List<Visit> getVisitsForPatient(Set<Ward> wards, Patient patient, Boolean discharge) {
		List<Visit> resultSet = new ArrayList<Visit>();

		Date startDt;
		Date endDt;
		int dischargeIndex = 0;
		int[] notDischargeArr = { 0, 1, 2 }, dischargeArr = { 3 };

		if (discharge) { // true - выписан
			endDt = randomPastDate();
			startDt = DateUtils.addDays(endDt, -(randomInteger(1, 99)));
			dischargeIndex = randomBetween(dischargeArr);

		} else {
			endDt = null;
			startDt = DateUtils.addDays(Calendar.getInstance().getTime(), -1 * (randomInteger(1, 99)));
			dischargeIndex = randomBetween(notDischargeArr);

		}
		int n = randomInteger(1, 10);
		for (int i = 1; i <= n; i++) {
			Visit visit = new Visit();
			visit.setPatient(patient);
			visit.setStartDt(startDt);
			visit.setEndDt(endDt);
			visit.setFirstDs(randomString("firstDs"));
			visit.setLastDs(randomString("lastDs"));
			visit.setImportantFlag(randomInteger(0, 1));
			visit.setDischargeFlag(EDischargeStatus.values()[dischargeIndex]);
			visit.setWard(randomFromCollection(wards));

			resultSet.add(visit);

			endDt = DateUtils.addDays(startDt, -(randomInteger(1, 99)));
			startDt = DateUtils.addDays(endDt, -(randomInteger(1, 99)));
			dischargeIndex = randomBetween(dischargeArr);

		}

		return resultSet;
	}
}
