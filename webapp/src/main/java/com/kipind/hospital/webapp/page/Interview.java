package com.kipind.hospital.webapp.page;

import java.util.Calendar;

import javax.inject.Inject;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.Checkup;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.ICheckupService;
import com.kipind.hospital.services.IPersonalService;
import com.kipind.hospital.services.IVisitService;
import com.kipind.hospital.webapp.panel.VisitDetailsPanel;

@AuthorizeInstantiation({ "DOCTOR", "LEAD_DOCTOR" })
public class Interview extends BaseLayout {

	@Inject
	private ICheckupService checkupService;
	@Inject
	private IVisitService visitService;
	@Inject
	private IPersonalService personalService;

	// private Patient patient;
	private Visit visit;
	private Checkup checkup = new Checkup();

	public Interview(Visit visit) {
		this.visit = visit;
		// visit = visitService.getOpenVisitForPatient(this.patient.getId());
	}

	public Interview(Long vId) {
		this.visit = visitService.getByIdFull(vId);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new VisitDetailsPanel("visitPanel", visit));
		checkup.setDiagnosis(visit.getFirstDs());
		Form<Checkup> interviewForm = new Form<Checkup>("interviewForm", new CompoundPropertyModel<Checkup>(checkup));

		FeedbackPanel feedbackPanel = new FeedbackPanel("infPanel");
		feedbackPanel.setOutputMarkupId(true);
		interviewForm.add(feedbackPanel);

		interviewForm.add(new TextField<String>("diagnosis").add(new PropertyValidator<String>()));
		interviewForm.add(new TextArea<String>("interview").add(new PropertyValidator<String>()));
		Button submitButton = new Button("submitButton") {

			@Override
			public void onSubmit() {

				try {
					checkup.setPersonal(personalService.getAllPersonal().get(0));
					checkup.setVisit(visit);
					checkup.setChDt(Calendar.getInstance().getTime());

					checkupService.saveOrUpdate(checkup);
					if (!checkup.getDiagnosis().equals(visit.getFirstDs())) {
						visit.setFirstDs(checkup.getDiagnosis());
						visitService.saveOrUpdate(visit);
					}
					setResponsePage(new CaseRecord(visit.getId()));
				} catch (RuntimeException e) {
					// TODO:ошибку в лог фаил
					error(new ResourceModel("error.general_save.callIT").getObject());

				}

			}
		};
		interviewForm.add(submitButton);
		add(interviewForm);

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.interview.label");
	}

}
