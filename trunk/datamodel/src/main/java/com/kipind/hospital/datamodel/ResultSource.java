package com.kipind.hospital.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class ResultSource extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Assign.class)
	private Assign assign;
	private String sourceLink;

	public Assign getAssign() {
		return assign;
	}

	@Column
	public String getSourceLink() {
		return sourceLink;
	}

	public void setAssign(Assign assign) {
		this.assign = assign;
	}

	public void setSourceLink(String sourceLink) {
		this.sourceLink = sourceLink;
	}
}
