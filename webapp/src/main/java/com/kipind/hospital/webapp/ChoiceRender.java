package com.kipind.hospital.webapp;

public class ChoiceRender {

	private Long choiseId;
	private String choiseVal;

	public ChoiceRender(Long id, String Val) {
		this.setChoiseId(id);
		this.setChoiseVal(Val);
	}

	public Long getChoiseId() {
		return choiseId;
	}

	public String getChoiseVal() {
		return choiseVal;
	}

	public void setChoiseId(Long gsId) {
		this.choiseId = gsId;
	}

	public void setChoiseVal(String choiseVal) {
		this.choiseVal = choiseVal;
	}

}
