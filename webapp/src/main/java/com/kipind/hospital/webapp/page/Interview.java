package com.kipind.hospital.webapp.page;

import java.util.Calendar;

import javax.inject.Inject;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.Checkup;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.ICheckupService;
import com.kipind.hospital.services.IPersonalService;
import com.kipind.hospital.services.IVisitService;
import com.kipind.hospital.webapp.panel.VisitDetailsPanel;

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
		// visit = visitService.getOpenVisitForPatient(this.patient.getId());

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new VisitDetailsPanel("visitPanel", visit));

		Form<Checkup> interviewForm = new Form<Checkup>("interviewForm", new CompoundPropertyModel<Checkup>(checkup));

		interviewForm.add(new TextField<String>("diagnosis"));
		interviewForm.add(new TextArea<String>("interview"));
		Button submitButton = new Button("submitButton") {

			@Override
			public void onSubmit() {
				// TODO: user из сессии
				checkup.setPersonal(personalService.getAllPersonal().get(0));
				checkup.setVisit(visit);
				checkup.setChDt(Calendar.getInstance().getTime());

				checkupService.saveOrUpdate(checkup);

				setResponsePage(new CaseRecord(visit.getId()));
			}
		};
		// submitButton.add(AttributeModifier.append("value", new
		// ResourceModel("button.save").getObject()));
		interviewForm.add(submitButton);
		add(interviewForm);

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.interview.label");
	}

}
