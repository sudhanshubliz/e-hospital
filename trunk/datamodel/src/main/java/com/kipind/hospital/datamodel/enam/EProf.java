package com.kipind.hospital.datamodel.enam;

public enum EProf {
	DOCTOR(0), NERS(1), LEAD_DOCTOR(2);

	private int id;

	EProf(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name();
	}
}
