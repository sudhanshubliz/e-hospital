package com.kipind.hospital.webapp.page;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.AbstractTextComponent;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.Patient;
import com.kipind.hospital.datamodel.enam.EHumanSex;
import com.kipind.hospital.services.IPatientService;

public class PatientRegistration extends BaseLayout {

	/*
	 * private String socialNumber; private String firstName; private String
	 * lastName; private Date birthDt; private String address; private Integer
	 * sex; private String wardNum;
	 */

	@Inject
	private IPatientService patientService;
	private Patient patient = new Patient();

	public PatientRegistration() {
		super();
		InitGui(patient);

	}

	public PatientRegistration(Patient p) {
		super();
		patient = p;
		InitGui(patient);

	}

	private void InitGui(Patient p) {
		Form<Patient> patientRegForm = new Form<Patient>("patientRegForm", new CompoundPropertyModel<Patient>(p));

		Map<String, String> textIdValue = new HashMap<String, String>();
		textIdValue.put("firstName", "p.patient_reg.form_name");
		textIdValue.put("lastName", "p.patient_reg.form_surname");
		textIdValue.put("address", "p.patient_reg.form_address");
		textIdValue.put("socialNumber", "p.patient_reg.form_social_num");
		for (TextField<String> component : createTextComponent(textIdValue)) {
			patientRegForm.add(component);

		}

		TextField<Date> tfDate = new TextField<Date>("birthDt");
		// tfDate.add(new PropertyValidator<Date>());
		textFieldAttr(tfDate, new ResourceModel("p.patient_reg.form_birth_day").getObject());
		patientRegForm.add(tfDate);

		/*
		 * final List<ChoiceRender> choiceList = new ArrayList<ChoiceRender>();
		 * String resStr; for (EHumanSex gender : EHumanSex.values()) { resStr =
		 * "p.patient_reg.form_sex_" + gender.name(); choiceList.add(new
		 * ChoiceRender(gender.ordinal(), (String) new
		 * ResourceModel(resStr).getObject())); } patientRegForm.add(new
		 * RadioChoice<ChoiceRender>("sex", choiceList,
		 * FormChoiceRender.INSTANCE));
		 */
		Button submitButton = new Button("submitButton") {

			@Override
			public void onSubmit() {
				p.setSex(EHumanSex.MALE);
				patientService.saveOrUpdate(p);
			}
		};
		submitButton.add(AttributeModifier.append("value", new ResourceModel("button.save").getObject()));
		patientRegForm.add(submitButton);

		add(patientRegForm);

	}

	private List<TextField<String>> createTextComponent(Map<String, String> resStr) {
		List<TextField<String>> result = new ArrayList<TextField<String>>();
		for (Map.Entry<String, String> entry : resStr.entrySet()) {
			TextField<String> textField = new TextField<String>(entry.getKey());
			// textField.add(new PropertyValidator<String>());
			result.add(textField);
			textFieldAttr(textField, new ResourceModel(entry.getValue()).getObject());
		}
		return result;
	}

	private <T> void textFieldAttr(AbstractTextComponent<T> element, String resValue) {

		element.add(AttributeModifier.append("onFocus", "if(this.value=='" + resValue + "')this.value=''"));
		element.add(AttributeModifier.append("onblur", "if(this.value=='')this.value='" + resValue + "'"));
		element.add(AttributeModifier.append("value", resValue));
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.patient_reg.label");
	}

}
