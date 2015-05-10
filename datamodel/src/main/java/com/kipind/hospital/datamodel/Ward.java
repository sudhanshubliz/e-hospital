package com.kipind.hospital.datamodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.kipind.hospital.datamodel.enam.EProf;
import com.kipind.hospital.datamodel.enam.EWardComfort;

@Entity
public class Ward extends AbstractEntity {

	@Column
	private Integer wardNum;
	@Column
	private EWardComfort comfortLvl;
	@Column
	@JoinTable(name = "ward_2_personal", joinColumns = { @JoinColumn(name = "ward_id") }, inverseJoinColumns = { @JoinColumn(name = "personal_id") })
	@ManyToMany(targetEntity = Personal.class, fetch = FetchType.LAZY)
	private Set<Personal> personal;
	@Column
	private Integer placeNumSum;
	@Column
	private Integer placeNumBisy;

	public Integer getWardNum() {
		return wardNum;
	}

	public EWardComfort getComfortLvl() {
		return comfortLvl;
	}

	public Set<Personal> getDoctorPersonal() {
		Set<Personal> res = new HashSet<Personal>();
		for (Personal personal : this.personal) {
			if (personal.getProf().equals(EProf.DOCTOR)) {
				res.add(personal);
			}
		}
		return res;
	}

	public Set<Personal> getPersonal() {
		return personal;
	}

	public Integer getPlaceNumSum() {
		return placeNumSum;
	}

	public Integer getPlaceNumBisy() {
		return placeNumBisy;
	}

	public void setWardNum(Integer wardNum) {
		this.wardNum = wardNum;
	}

	public void setWardNum(String wardNum) {
		this.wardNum = Integer.valueOf(wardNum);
	}

	public void setComfortLvl(EWardComfort comfortLvl) {
		this.comfortLvl = comfortLvl;
	}

	public void setPersonal(Set<Personal> personal) {
		this.personal = personal;
	}

	public void setPlaceNumSum(Integer placeNumSum) {
		this.placeNumSum = placeNumSum;
	}

	public void setPlaceNumBisy(Integer placeNumBisy) {
		this.placeNumBisy = placeNumBisy;
	}

}
