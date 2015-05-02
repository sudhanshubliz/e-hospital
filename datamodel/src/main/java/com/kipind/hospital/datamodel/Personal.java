package com.kipind.hospital.datamodel;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.kipind.hospital.datamodel.enam.EProf;

@Entity
public class Personal extends AbstractEntity {

	private String firstName;
	private Integer tabelNumber;
	private String pass;
	private Boolean delMarker;
	private Boolean conMarker;
	private EProf prof;
	private String secondName;
	@JoinTable(name = "ward_2_personal", joinColumns = { @JoinColumn(name = "personal_id") }, inverseJoinColumns = { @JoinColumn(name = "ward_id") })
	@ManyToMany(targetEntity = Ward.class, fetch = FetchType.LAZY)
	private Set<Ward> wards;

	@Column
	public String getFirstName() {
		return firstName;
	}

	@Column
	public Integer getTabelNumber() {
		return tabelNumber;
	}

	@Column
	public String getPass() {
		return pass;
	}

	@Column
	public Boolean getDelMarker() {
		return delMarker;
	}

	@Column
	public Boolean getConMarker() {
		return conMarker;
	}

	@Column
	@Enumerated(EnumType.ORDINAL)
	public EProf getProf() {
		return prof;
	}

	@Column
	public String getSecondName() {
		return secondName;
	}

	public Set<Ward> getWards() {
		return wards;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setTabelNumber(Integer tabelNumber) {
		this.tabelNumber = tabelNumber;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setDelMarker(Boolean delMarker) {
		this.delMarker = delMarker;
	}

	public void setConMarker(Boolean conMarker) {
		this.conMarker = conMarker;
	}

	public void setProf(EProf prof) {
		this.prof = prof;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public void setWards(Set<Ward> wards) {
		this.wards = wards;
	}

}
