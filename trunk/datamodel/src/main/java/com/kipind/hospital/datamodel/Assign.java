package com.kipind.hospital.datamodel;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Assign extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Visit.class)
	private Visit visit;

	private String prscText;
	private Date prscDt;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Personal.class)
	private Personal prscPersonal;

	private String resText;
	private Date resDt;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Personal.class)
	private Personal resPersonal;

	@OneToMany(fetch = FetchType.LAZY, targetEntity = ResultSource.class)
	private Set<ResultSource> resSourseList;
	private Long periodGroupKey;

	public Visit getVisit() {
		return visit;
	}

	@Column
	public String getPrscText() {
		return prscText;
	}

	@Column
	public Date getPrscDt() {
		return prscDt;
	}

	public Personal getPrscPersonal() {
		return prscPersonal;
	}

	@Column
	public String getResText() {
		return resText;
	}

	@Column
	public Date getResDt() {
		return resDt;
	}

	public Personal getResPersonal() {
		return resPersonal;
	}

	public Set<ResultSource> getResSourseList() {
		return resSourseList;
	}

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

	public void setResSourseList(Set<ResultSource> resSourseList) {
		this.resSourseList = resSourseList;
	}

	public void setPeriodGroupKey(Long periodGroupKey) {
		this.periodGroupKey = periodGroupKey;
	}

}
