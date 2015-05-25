package com.kipind.hospital.webapp.page;

import java.util.Calendar;

import javax.inject.Inject;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kipind.hospital.datamodel.Assign;
import com.kipind.hospital.datamodel.Checkup;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.IAssignServiсe;
import com.kipind.hospital.services.ICheckupService;
import com.kipind.hospital.services.IPersonalService;
import com.kipind.hospital.services.IVisitService;
import com.kipind.hospital.webapp.app.BasicAuthenticationSession;
import com.kipind.hospital.webapp.panel.VisitDetailsPanel;

@AuthorizeInstantiation({ "DOCTOR", "LEAD_DOCTOR" })
public class Interview extends BaseLayout {

	private static final Logger LOGGER = LoggerFactory.getLogger(Interview.class);

	@Inject
	private ICheckupService checkupService;
	@Inject
	private IVisitService visitService;
	@Inject
	private IPersonalService personalService;
	@Inject
	private IAssignServiсe assignService;

	private boolean dischargeFlag = false;
	private Visit visit;
	private Checkup checkup = new Checkup();

	public Interview(Visit visit) {
		this.visit = visit;
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

		interviewForm.add(new TextField<String>("diagnosis").add(new PropertyValidator<String>()).setLabel(
				new Model<String>(getString("p.interview.lb_ds"))));
		interviewForm.add(new TextArea<String>("interview").add(new PropertyValidator<String>()).setLabel(
				new Model<String>(getString("p.interview.lb_textarea"))));
		interviewForm.add(new CheckBox("dischargeFlag", new PropertyModel<Boolean>(this, "dischargeFlag")).setLabel(new Model<String>(
				getString("p.interview.cb_discharg"))));

		Button submitButton = new Button("submitButton") {

			@Override
			public void onSubmit() {

				try {
					// throw new RuntimeException("test");

					checkup.setPersonal(personalService.getByIdFull(BasicAuthenticationSession.get().getUserId()));
					checkup.setVisit(visit);
					checkup.setChDt(Calendar.getInstance().getTime());

					checkupService.saveOrUpdate(checkup);
					if (!checkup.getDiagnosis().equals(visit.getFirstDs())) {
						visit.setFirstDs(checkup.getDiagnosis());

					}
					if (dischargeFlag) {
						visit.setLastDs(checkup.getDiagnosis());
						visitService.saveOrUpdate(visit);
						Assign endAssign = new Assign();
						endAssign.setVisit(visit);
						endAssign.setPrscText(getString("p.interview.cb_discharg"));
						endAssign.setPrscDt(Calendar.getInstance().getTime());
						endAssign.setPeriodGroupKey(-assignService.getFreeGroupId());
						endAssign.setPrscPersonal(checkup.getPersonal());
						assignService.saveOrUpdate(endAssign);
					}

					setResponsePage(new CaseRecord(visit.getId()));

				} catch (RuntimeException e) {
					LOGGER.error("try to save interview. Class is: {}" + e, getClass().getName());
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

	public boolean isDischargeFlag() {
		return dischargeFlag;
	}

	public void setDischargeFlag(boolean dischargeFlag) {
		this.dischargeFlag = dischargeFlag;
	}

}
