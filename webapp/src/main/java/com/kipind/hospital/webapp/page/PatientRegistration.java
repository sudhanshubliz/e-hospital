package com.kipind.hospital.webapp.page;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.AbstractTextComponent;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.Patient;
import com.kipind.hospital.datamodel.Patient_;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.datamodel.Ward;
import com.kipind.hospital.datamodel.enam.EHumanSex;
import com.kipind.hospital.services.IPatientService;
import com.kipind.hospital.services.IWardService;

public class PatientRegistration extends BaseLayout {

	/*
	 * private String socialNumber; private String firstName; private String
	 * lastName; private Date birthDt; private String address; private Integer
	 * sex; private String wardNum;
	 */

	@Inject
	private IWardService wardService;
	@Inject
	private IPatientService patientService;

	private Patient patient = new Patient();
	private Visit visit = new Visit();
	private Ward ward = new Ward();

	public PatientRegistration() {
		super();
		InitGui();

	}

	public PatientRegistration(Patient p) {
		super();
		patient = p;
		InitGui();

	}

	private void InitGui() {

		FeedbackPanel feedbackPanel = new FeedbackPanel("infPanel");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		Form<Patient> patientRegForm = new Form<Patient>("patientRegForm", new CompoundPropertyModel<Patient>(this.patient));
		patientRegForm.setOutputMarkupId(true);
		add(patientRegForm);

		Map<String, TextField<?>> textFormFields = new HashMap<String, TextField<?>>();
		textFormFields.put("form_name", new TextField<String>("firstName"));
		textFormFields.put("form_surname", new TextField<String>("lastName"));
		textFormFields.put("form_address", new TextField<String>("address"));
		textFormFields.put("form_social_num", new TextField<String>("socialNumber"));
		textFormFields.put("form_birth_day", new TextField<Date>("birthDt"));

		addTextComponentAttr(textFormFields, patientRegForm);

		textFormFields.get("form_social_num").add(new AjaxEventBehavior("onblur") {

			@Override
			protected void onEvent(AjaxRequestTarget target) {
				info(" number: " + patient.getSocialNumber());

				patient = patientService.getAllByField(Patient_.socialNumber, patient.getSocialNumber()).get(0);
				info(" number: " + patient.getFirstName());

				target.add(patientRegForm);
				target.add(feedbackPanel);
			}
		});

		/*
		 * 
		 * String resStr; for (Ward ward : wardService.getAllWards()) {
		 * choiceList.add(new ChoiceRender(ward.getId(), ward.getWardNum() + "("
		 * + ward.getPlaceNumBisy() + "/" + ward.getPlaceNumBisy() + ")")); }
		 */
		List<Ward> choiceList = new ArrayList<Ward>();
		choiceList = wardService.getAllWards();

		final DropDownChoice<Ward> fieldProduct = new DropDownChoice<Ward>("wardNum", new Model<Ward>(null), choiceList, new IChoiceRenderer<Ward>() {

			@Override
			public Object getDisplayValue(Ward object) {
				return object.getWardNum();
			}

			@Override
			public String getIdValue(Ward object, int index) {
				// TODO Auto-generated method stub
				return object.getWardNum().toString();
			}

		});

		patientRegForm.add(fieldProduct);

		// new DropDownChoice<ChoiceRender>("wardNum", new Model<Ward>(),
		// choiceList, FormChoiceRender.INSTANCE)
		Button submitButton = new Button("submitButton") {

			@Override
			public void onSubmit() {
				patient.setSex(EHumanSex.MALE);
				patientService.saveOrUpdate(patient);
				info("ward number: " + ward.getWardNum() + "   " + fieldProduct.getModelObject().getWardNum());
				setResponsePage(new PatientRegistration());
			}
		};
		submitButton.add(AttributeModifier.append("value", new ResourceModel("button.save").getObject()));
		patientRegForm.add(submitButton);

	}

	private void addTextComponentAttr(Map<String, TextField<?>> textField, Form<Patient> form) {
		String resValue;
		for (Map.Entry<String, TextField<?>> entry : textField.entrySet()) {
			// entry.getValue().add(new PropertyValidator<?>());
			resValue = new ResourceModel("p.patient_reg." + entry.getKey()).getObject();
			entry.getValue().setOutputMarkupId(true);
			entry.getValue().add(AttributeModifier.append("value", resValue));

			entry.getValue().add(AttributeModifier.append("onFocus", "if(this.value=='" + resValue + "')this.value=''"));
			entry.getValue().add(AttributeModifier.append("onblur", "if(this.value=='')this.value='" + resValue + "'"));

			form.add(entry.getValue());

		}

	}

	private <T> void textFieldAttr(AbstractTextComponent<T> element, String resValue) {

		element.add(AttributeModifier.append("onFocus", "if(this.value=='" + resValue + "')this.value=''"));
		element.add(AttributeModifier.append("onblur", "if(this.value=='')this.value='" + resValue + "'"));
		element.add(AttributeModifier.append("value", resValue));
		element.setOutputMarkupId(true);
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.patient_reg.label");
	}

}
