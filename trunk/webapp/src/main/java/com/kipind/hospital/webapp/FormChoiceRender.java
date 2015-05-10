package com.kipind.hospital.webapp;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

public class FormChoiceRender implements IChoiceRenderer<ChoiceRender> {

	public static FormChoiceRender INSTANCE = new FormChoiceRender();

	private FormChoiceRender() {
		super();
	}

	@Override
	public Object getDisplayValue(ChoiceRender choiceRender) {
		return choiceRender.getChoiseVal();
	}

	@Override
	public String getIdValue(ChoiceRender choiceRender, int index) {
		return "FEMALE";// String.valueOf(choiceRender.getChoiseId());
	}

}
