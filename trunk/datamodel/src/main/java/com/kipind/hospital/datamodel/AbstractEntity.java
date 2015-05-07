package com.kipind.hospital.datamodel;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		if (getId() != null) {
			return getId().hashCode();
		} else {
			return super.hashCode();
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}

		if ((o == null) || !(o instanceof AbstractEntity)) {
			return false;
		}
		if (getClass() != o.getClass()) {
			return false;
		}

		final AbstractEntity other = (AbstractEntity) o;

		// if the id is missing, return false
		if (getId() == null) {
			return false;
		}

		// equivalence by id
		return getId().equals(other.getId());
	}

}