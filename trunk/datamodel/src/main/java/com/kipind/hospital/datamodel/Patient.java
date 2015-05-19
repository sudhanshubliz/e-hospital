package com.kipind.hospital.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.kipind.hospital.datamodel.enam.EHumanSex;

@Entity
public class Patient extends AbstractEntity {

	private String socialNumber;
	private String firstName;
	private String lastName;
	private Date birthDt;
	private String address;
	private EHumanSex sex;

	@Column
	@NotNull
	public String getSocialNumber() {
		return socialNumber;
	}

	@Column
	@NotNull
	@Size(max = 22)
	public String getFirstName() {
		return firstName;
	}

	@Column
	@NotNull
	public String getLastName() {
		return lastName;
	}

	@Column
	@NotNull
	@Past
	public Date getBirthDt() {
		return birthDt;
	}

	@Column
	@NotNull
	public String getAddress() {
		return address;
	}

	@Column
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	public EHumanSex getSex() {
		return sex;
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

	public void setBirthDt(Date birthDt) {
		this.birthDt = birthDt;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setSex(EHumanSex sex) {
		this.sex = sex;
	}

}
