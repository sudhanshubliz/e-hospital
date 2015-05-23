package com.kipind.hospital.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Assign extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Visit.class)
	@NotNull
	private Visit visit;
	@NotNull
	private String prscText;
	@NotNull
	private Date prscDt;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Personal.class)
	@NotNull
	private Personal prscPersonal;
	private String resText;
	private Date resDt;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Personal.class)
	@NotNull
	private Personal resPersonal;
	@NotNull
	private Long periodGroupKey;

	public Visit getVisit() {
		return visit;
	}

	@Column
	@NotNull
	public String getPrscText() {
		return prscText;
	}

	@Column
	@NotNull
	public Date getPrscDt() {
		return prscDt;
	}

	public Personal getPrscPersonal() {
		return prscPersonal;
	}

	@Column
	@NotNull
	public String getResText() {
		return resText;
	}

	@Column
	@NotNull
	public Date getResDt() {
		return resDt;
	}

	public Personal getResPersonal() {
		return resPersonal;
	}

	/*
	 * public Set<ResultSource> getResultSource() { return resultSource; }
	 */
	@Column
	public Long getPeriodGroupKey() {
		return periodGroupKey;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}

	public void setPrscText(String prscText) {
		this.prscText = prscText;
	}

	public void setPrscDt(Date prscDt) {
		this.prscDt = prscDt;
	}

	public void setPrscPersonal(Personal prscPersonal) {
		this.prscPersonal = prscPersonal;
	}

	public void setResText(String resText) {
		this.resText = resText;
	}

	public void setResDt(Date resDt) {
		this.resDt = resDt;
	}

	public void setResPersonal(Personal resPersonal) {
		this.resPersonal = resPersonal;
	}

	/*
	 * public void setResultSource(Set<ResultSource> resultSource) {
	 * this.resultSource = resultSource; }
	 */
	public void setPeriodGroupKey(Long periodGroupKey) {
		this.periodGroupKey = periodGroupKey;
	}

}
