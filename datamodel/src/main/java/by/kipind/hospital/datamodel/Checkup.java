package by.kipind.hospital.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Checkup extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Personal.class)
	private Personal personal;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Visit.class)
	private Visit visit;
	private Date chDt;
	private String interview;
	private String diagnosis;

	public Personal getPersonal() {
		return personal;
	}

	public Visit getVisit() {
		return visit;
	}

	@Column
	public Date getChDt() {
		return chDt;
	}

	@Column
	public String getInterview() {
		return interview;
	}

	@Column
	public String getDiagnosis() {
		return diagnosis;
	}

	public void setPersonal(Personal personal) {
		this.personal = personal;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public void setChDt(Date chDt) {
		this.chDt = chDt;
	}

	public void setInterview(String interview) {
		this.interview = interview;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

}
