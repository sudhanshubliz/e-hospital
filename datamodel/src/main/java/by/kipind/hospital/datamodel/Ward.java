package by.kipind.hospital.datamodel;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import by.kipind.hospital.datamodel.enam.EWardComfort;

@Entity
public class Ward extends AbstractEntity {

	private Integer wardNum;
	private EWardComfort comfortLvl;
	@JoinTable(name = "ward_2_personal", joinColumns = { @JoinColumn(name = "ward_id") }, inverseJoinColumns = { @JoinColumn(name = "personal_id") })
	@ManyToMany(targetEntity = Personal.class, fetch = FetchType.LAZY)
	private Set<Personal> personal;
	private Integer placeNumSum;
	private Integer placeNumBisy;

	@Column
	public Integer getWardNum() {
		return wardNum;
	}

	@Column
	@Enumerated(EnumType.ORDINAL)
	public EWardComfort getComfortLvl() {
		return comfortLvl;
	}

	public Set<Personal> getPersonal() {
		return personal;
	}

	@Column
	public Integer getPlaceNumSum() {
		return placeNumSum;
	}

	@Column
	public Integer getPlaceNumBisy() {
		return placeNumBisy;
	}

	public void setWardNum(Integer wardNum) {
		this.wardNum = wardNum;
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
