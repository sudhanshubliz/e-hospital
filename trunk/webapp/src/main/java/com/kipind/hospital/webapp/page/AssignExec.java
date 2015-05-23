package com.kipind.hospital.webapp.page;

import javax.inject.Inject;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kipind.hospital.datamodel.Assign;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.IAssignServise;
import com.kipind.hospital.services.IPersonalService;
import com.kipind.hospital.services.IVisitService;
import com.kipind.hospital.services.impl.PersonalService;
import com.kipind.hospital.webapp.panel.VisitDetailsPanel;

@AuthorizeInstantiation({ "DOCTOR", "LEAD_DOCTOR" })
public class AssignExec extends BaseLayout {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonalService.class);

	@Inject
	private IAssignServise assignService;
	@Inject
	private IVisitService visitService;
	@Inject
	private IPersonalService personalService;

	// private Patient patient;
	private Visit visit;
	private Assign assign;

	public AssignExec(Visit visit, Assign assign) {
		this.visit = visit;
		this.assign = assign;
	}

	public AssignExec(Long vId, Long aId) {
		this.visit = visitService.getByIdFull(vId);
		this.assign = assignService.getById(aId);// TODO:
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new VisitDetailsPanel("visitPanel", visit));
		Form<Assign> assignExecForm = new Form<Assign>("assignForm", new CompoundPropertyModel<Assign>(assign));

		FeedbackPanel feedbackPanel = new FeedbackPanel("infPanel");
		feedbackPanel.setOutputMarkupId(true);
		assignExecForm.add(feedbackPanel);

		assignExecForm.add(new TextArea<String>("prscText").add(new PropertyValidator<String>()).setEnabled(false));
		assignExecForm.add(new TextArea<String>("resText").add(new PropertyValidator<String>()));
		Button submitButton = new Button("submitButton") {

			@Override
			public void onSubmit() {

				try {
					/*
					 * checkup.setPersonal(personalService.getByIdFull(
					 * BasicAuthenticationSession.get().getUserId()));
					 * checkup.setVisit(visit);
					 * checkup.setChDt(Calendar.getInstance().getTime());
					 * 
					 * checkupService.saveOrUpdate(checkup); if
					 * (!checkup.getDiagnosis().equals(visit.getFirstDs())) {
					 * visit.setFirstDs(checkup.getDiagnosis());
					 * visitService.saveOrUpdate(visit); } setResponsePage(new
					 * CaseRecord(visit.getId()));
					 */} catch (RuntimeException e) {
					LOGGER.error("try to save interview. Class is: {}" + e, getClass().getName());
					error(new ResourceModel("error.general_save.callIT").getObject());

				}

			}
		};
		assignExecForm.add(submitButton);
		add(assignExecForm);

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.assign_exeс.label");
	}

}
