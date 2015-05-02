package by.kipind.hospital.datamodel;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Prescribe extends AbstractEntity {

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Checkup.class)
	private Checkup checkup;
	private String coment;
	private String resValue;
	private Date resDt;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Personal.class)
	private Personal resPersonal;
	@OneToMany(fetch = FetchType.LAZY, targetEntity = ResultSource.class)
	private Set<ResultSource> resSourseList;

	public Checkup getCheckup() {
		return checkup;
	}

	@Column
	public String getComent() {
		return coment;
	}

	@Column
	public String getResValue() {
		return resValue;
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

	public void setCheckup(Checkup checkup) {
		this.checkup = checkup;
	}

	public void setComent(String coment) {
		this.coment = coment;
	}

	public void setResValue(String resValue) {
		this.resValue = resValue;
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

}
