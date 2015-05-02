package by.kipind.hospital.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import by.kipind.hospital.datamodel.enam.EHumanSex;

@Entity
public class Patient extends AbstractEntity {

	private String socialNumber;
	private String firstName;
	@Column
	private String lastName;
	@Column
	private Date birthDt;
	@Column
	private String address;
	@Column
	@Enumerated(EnumType.ORDINAL)
	private EHumanSex sex;

	@Column
	public String getSocialNumber() {
		return socialNumber;
	}

	@Column
	public String getFirstName() {
		return firstName;
	}

	@Column
	public String getLastName() {
		return lastName;
	}

	@Column
	public Date getBirthDt() {
		return birthDt;
	}

	@Column
	public String getAddress() {
		return address;
	}

	@Column
	@Enumerated(EnumType.ORDINAL)
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
