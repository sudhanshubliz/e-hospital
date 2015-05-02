package by.kipind.hospital.webapp.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.AbstractTextComponent;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

public class PatientRegistration extends BaseLayout {

	private String socialNumber;
	private String firstName;
	private String lastName;
	private Date birthDt;
	private String address;
	private Integer sex;
	private String wardNum;

	// private Patient patient = new Patient();

	public PatientRegistration() {
		initGui();
	}

	private void initGui() {

		Form<PatientRegistration> patientRegForm = new Form<PatientRegistration>("patientRegForm", new CompoundPropertyModel<PatientRegistration>(
				this));

		add(patientRegForm);

		Map<String, String> textIdValue = new HashMap<String, String>();
		textIdValue.put("firstName", "p.patient_reg.form_name");
		textIdValue.put("lastName", "p.patient_reg.form_surname");
		// textIdValue.put("birthDt", "p.patient_reg.form_birth_day");
		textIdValue.put("address", "p.patient_reg.form_address");
		textIdValue.put("socialNumber", "p.patient_reg.form_social_num");

		for (TextField<String> component : createTextComponent(textIdValue)) {
			patientRegForm.add(component);

		}

		TextField<Date> textField = new TextField<Date>("birthDt");
		textFieldAttr(textField, new ResourceModel("p.patient_reg.form_birth_day").getObject());
		patientRegForm.add(textField);

		final List<String> choiseVal = Arrays.asList(new String[] { new ResourceModel("p.patient_reg.form_sex_male").getObject(),
				new ResourceModel("p.patient_reg.form_sex_female").getObject() });
		patientRegForm.add(new RadioChoice<String>("sex", choiseVal));

		final List<String> listChoiseVal = Arrays.asList(new String[] { "101(1/3)", "102(2/3)", "105(0/3)", "107(2/3)" });
		patientRegForm.add(new DropDownChoice<String>("wardNum", listChoiseVal));

		Button submitButton = new Button("submitButton") {

			@Override
			public void onSubmit() {
				System.out.println("OnSubmit, sex = " + sex);
				System.out.println("OnSubmit, wardNum = " + wardNum);

			}
		};
		submitButton.add(AttributeModifier.append("value", new ResourceModel("button.save").getObject()));
		patientRegForm.add(submitButton);
	}

	private <T> void textFieldAttr(AbstractTextComponent<T> element, String resValue) {

		element.add(AttributeModifier.append("onFocus", "if(this.value=='" + resValue + "')this.value=''"));
		element.add(AttributeModifier.append("onblur", "if(this.value=='')this.value='" + resValue + "'"));
		element.add(AttributeModifier.append("value", resValue));
	}

	private List<TextField<String>> createTextComponent(Map<String, String> resStr) {
		List<TextField<String>> result = new ArrayList<TextField<String>>();
		for (Map.Entry<String, String> entry : resStr.entrySet()) {
			TextField<String> textField = new TextField<String>(entry.getKey());
			result.add(textField);
			textFieldAttr(textField, new ResourceModel(entry.getValue()).getObject());
		}
		return result;
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.patient_reg.label");
	}

}
