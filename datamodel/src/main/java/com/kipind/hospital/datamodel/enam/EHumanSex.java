package com.kipind.hospital.datamodel.enam;

public enum EHumanSex {
	MALE(0), FEMALE(1);

	private int id;

	EHumanSex(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name();
	}
}
