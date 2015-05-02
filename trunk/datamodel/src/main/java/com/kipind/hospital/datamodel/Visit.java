package com.kipind.hospital.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.kipind.hospital.datamodel.enam.EDischargeStatus;

@Entity
public class Visit extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Ward.class)
	private Ward ward;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Patient.class)
	private Patient patient;
	private Date startDt;
	private Date endDt;
	private String firstDs;
	private String lastDs;
	private Integer importantFlag;
	private EDischargeStatus dischargeFlag; // статус пациента по выписке

	public Ward getWard() {
		return ward;
	}

	public Patient getPatient() {
		return patient;
	}

	@Column
	public Date getStartDt() {
		return startDt;
	}

	@Column
	public Date getEndDt() {
		return endDt;
	}

	@Column
	public String getFirstDs() {
		return firstDs;
	}

	@Column
	public String getLastDs() {
		return lastDs;
	}

	@Column
	public Integer getImportantFlag() {
		return importantFlag;
	}

	@Column
	@Enumerated(EnumType.ORDINAL)
	public EDischargeStatus getDischargeFlag() {
		return dischargeFlag;
	}

	public void setWard(Ward ward) {
		this.ward = ward;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public void setFirstDs(String firstDs) {
		this.firstDs = firstDs;
	}

	public void setLastDs(String lastDs) {
		this.lastDs = lastDs;
	}

	public void setImportantFlag(Integer importantFlag) {
		this.importantFlag = importantFlag;
	}

	public void setDischargeFlag(EDischargeStatus dischargeFlag) {
		this.dischargeFlag = dischargeFlag;
	}

}
