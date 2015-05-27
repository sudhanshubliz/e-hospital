package com.kipind.hospital.datamodel.objectPrototype;

import java.util.Date;

import com.kipind.hospital.datamodel.AbstractEntity;
import com.kipind.hospital.datamodel.Patient;
import com.kipind.hospital.datamodel.enam.EHumanSex;

@SuppressWarnings("serial")
public class PatientPrototype extends AbstractEntity {

	private String socialNumber;
	private String firstName;
	private String lastName;
	private Date birthDt;
	// private Integer yearsOld;
	private String address;
	private EHumanSex sex;
	private Long visitsNum;

	public PatientPrototype(Patient patient, Long visitCount) {
		this.setSocialNumber(patient.getSocialNumber());
		this.setFirstName(patient.getFirstName());
		;
		this.setBirthDt(patient.getBirthDt());
		;
		this.setAddress(patient.getAddress());
		;
		this.setId(patient.getId());
		;
		this.setLastName(patient.getLastName());
		;
		this.setSex(patient.getSex());
		;
		// this.setYearsOld(yearsOld);;
		this.setVisitsNum(visitCount);
	};

	public String getSocialNumber() {
		return socialNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public EHumanSex getSex() {
		return sex;
	}

	public Long getVisitsNum() {
		return visitsNum;
	}

	public void setSocialNumber(String socialNumber) {
		this.socialNumber = socialNumber;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setSex(EHumanSex sex) {
		this.sex = sex;
	}

	public void setVisitsNum(Long visitsNum) {
		this.visitsNum = visitsNum;
	}

	public Date getBirthDt() {
		return birthDt;
	}

	public void setBirthDt(Date birthDt) {
		this.birthDt = birthDt;
	}

}
