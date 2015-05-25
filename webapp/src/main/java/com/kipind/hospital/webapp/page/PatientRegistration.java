package com.kipind.hospital.webapp.page;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.Patient;
import com.kipind.hospital.datamodel.Patient_;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.datamodel.Ward;
import com.kipind.hospital.datamodel.enam.EDischargeStatus;
import com.kipind.hospital.datamodel.enam.EHumanSex;
import com.kipind.hospital.services.IPatientService;
import com.kipind.hospital.services.IVisitService;
import com.kipind.hospital.services.IWardService;

@AuthorizeInstantiation({ "DOCTOR", "NERS", "LEAD_DOCTOR" })
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
	@Inject
	private IVisitService visitService;

	private Patient patient;
	private Visit visit = new Visit();

	private List<Ward> choiceList;

	public PatientRegistration() {
		super();
		patient = new Patient();
		InitGui();

	}

	public PatientRegistration(Patient p) {
		super();
		patient = p;
		InitGui();

	}

	private void InitGui() {

		Form<Patient> patientRegForm = new Form<Patient>("patientRegForm", new CompoundPropertyModel<Patient>(patient));
		patientRegForm.setOutputMarkupId(true);

		FeedbackPanel feedbackPanel = new FeedbackPanel("infPanel");
		feedbackPanel.setOutputMarkupId(true);
		add(feedbackPanel);

		Map<String, TextField<String>> textFormFields = new HashMap<String, TextField<String>>();
		textFormFields.put("form_name", new TextField<String>("firstName"));
		textFormFields.put("form_surname", new TextField<String>("lastName"));
		textFormFields.put("form_address", new TextField<String>("address"));
		textFormFields.put("form_social_num", new TextField<String>("socialNumber"));
		addTextComponentAttr(textFormFields, patientRegForm);

		TextField<Date> dateField = new TextField<Date>("birthDt");
		dateField.add(new PropertyValidator<Date>());
		dateField.setLabel(new ResourceModel("p.patient_reg.form_birth_day"));
		dateField.setOutputMarkupId(true);
		patientRegForm.add(dateField);

		AjaxFormComponentUpdatingBehavior tfOnBlurAjax = new AjaxFormComponentUpdatingBehavior("onblur") {

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (patient.getSocialNumber() != null) {
					List<Patient> p = patientService.getAllByField(Patient_.socialNumber, patient.getSocialNumber());
					if (p.size() != 0) {
						patient.setAddress(p.get(0).getAddress());
						patient.setBirthDt(p.get(0).getBirthDt());
						patient.setFirstName(p.get(0).getFirstName());
						patient.setLastName(p.get(0).getLastName());
						patient.setSex(p.get(0).getSex());
						patient.setId(p.get(0).getId());

					}

					target.add(patientRegForm);
					target.add(feedbackPanel);
				}

			}
		};

		textFormFields.get("form_social_num").add(tfOnBlurAjax);
		patientRegForm.add(textFormFields.get("form_social_num"));

		patientRegForm.add(new RadioChoice<EHumanSex>("sex", Arrays.asList(EHumanSex.values()), new ChoiceRenderer<EHumanSex>() {

			@Override
			public String getDisplayValue(EHumanSex sexType) {
				return new ResourceModel("p.patient_reg.form_sex_" + sexType.getName()).getObject();
			}
		}).setLabel(new ResourceModel("p.patient_reg.form_gender")));

		choiceList = wardService.getAllWards();
		patientRegForm.add(new DropDownChoice<Ward>("wardNum", new PropertyModel<Ward>(visit, "ward"), choiceList, new IChoiceRenderer<Ward>() {

			@Override
			public Object getDisplayValue(Ward object) {
				return object.getWardNum();// + "(" + object.getPlaceNumBisy() +
											// "/" + object.getPlaceNumSum() +
											// ")";
			}

			@Override
			public String getIdValue(Ward object, int index) {
				return object.getId().toString();
			}

		}).setLabel(new ResourceModel("p.patient_reg.form_ward")));

		Button submitButton = new Button("submitButton") {

			@Override
			public void onSubmit() {
				try {
					visit.setPatient(patientService.saveOrUpdate(patient));
					visit.setStartDt(Calendar.getInstance().getTime());
					visit.setImportantFlag(0);
					visit.setDischargeFlag(EDischargeStatus.CURING);
					if (visit.getWard().equals(null)) {// TODO: подсчет затых
														// вободых койко мест
						visit.setWard(WardAvtoSelect()); // TODO: релизация
															// алгоритма
															// автовыбоа
															// лучшей палаты
					}
					visitService.saveOrUpdate(visit);

					PatientRegistration respPage = new PatientRegistration();
					respPage.info(new ResourceModel("info.visit_save").getObject() + visit.getWard().getWardNum());
					setResponsePage(respPage);
				} catch (RuntimeException e) {
					// TODO:ошибку в лог фаил
					error(new ResourceModel("error.general_save.callIT").getObject());

				}
			}
		};
		submitButton.add(AttributeModifier.append("value", new ResourceModel("button.save").getObject()));
		patientRegForm.add(submitButton);
		add(patientRegForm);

	}

	private <T> void addTextComponentAttr(Map<String, TextField<String>> textField, Form<T> form) {
		for (Map.Entry<String, TextField<String>> entry : textField.entrySet()) {
			entry.getValue().add(new PropertyValidator<String>());
			entry.getValue().setLabel(new ResourceModel("p.patient_reg." + entry.getKey()));
			entry.getValue().setOutputMarkupId(true);
			form.add(entry.getValue());

		}

	}

	private Ward WardAvtoSelect() {
		return this.choiceList.get((int) Math.round(Math.random() * (choiceList.size() - 1)));
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.patient_reg.label");
	}

}
