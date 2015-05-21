package com.kipind.hospital.services.utilObject;

import java.util.Set;

import com.kipind.hospital.datamodel.Ward;
import com.kipind.hospital.datamodel.enam.EProf;

public class PersonalPrototype {

	private Long Id;
	private String firstName;
	private Integer tabelNumber;
	private String pass;
	private Boolean delMarker;
	private Boolean conMarker;
	private EProf prof;
	private String secondName;
	private Set<Ward> wards;
	private String wardsString;
	private Float workLvl;

	public Long getId() {
		return Id;
	}

	public String getFirstName() {
		return firstName;
	}

	public Integer getTabelNumber() {
		return tabelNumber;
	}

	public String getPass() {
		return pass;
	}

	public Boolean getDelMarker() {
		return delMarker;
	}

	public Boolean getConMarker() {
		return conMarker;
	}

	public EProf getProf() {
		return prof;
	}

	public String getSecondName() {
		return secondName;
	}

	public Set<Ward> getWards() {
		return wards;
	}

	public String getWardsString() {
		String res = "";
		for (Ward ward : wards) {
			res = res + ward.getWardNum().toString() + ";";
		}
		return res;
	}

	public Float getWorkLvl() {
		return workLvl;
	}

	public void setId(Long id) {
		Id = id;
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

	public void setWardsString(String wardsString) {
		this.wardsString = wardsString;
	}

	public void setWorkLvl(Float workLvl) {
		this.workLvl = workLvl;
	}

}
