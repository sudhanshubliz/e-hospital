package com.kipind.hospital.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class ResultSource extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Prescribe.class)
	private Prescribe prescribe;
	private String sourseLink;

	public Prescribe getPrescribe() {
		return prescribe;
	}

	public void setPrescribe(Prescribe prescribe) {
		this.prescribe = prescribe;
	}

	@Column
	public String getSourseLink() {
		return sourseLink;
	}

	public void setSourseLink(String sourseLink) {
		this.sourseLink = sourseLink;
	}
}
