package com.kipind.hospital.webapp.page;

import java.util.Calendar;

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
import com.kipind.hospital.datamodel.enam.EDischargeStatus;
import com.kipind.hospital.services.IAssignServiсe;
import com.kipind.hospital.services.IPersonalService;
import com.kipind.hospital.services.IVisitService;
import com.kipind.hospital.webapp.app.BasicAuthenticationSession;
import com.kipind.hospital.webapp.panel.VisitDetailsPanel;

@AuthorizeInstantiation({ "DOCTOR", "LEAD_DOCTOR", "NERS" })
public class AssignExec extends BaseLayout {

	private static final Logger LOGGER = LoggerFactory.getLogger(AssignExec.class);

	@Inject
	private IAssignServiсe assignService;
	@Inject
	private IVisitService visitService;
	@Inject
	private IPersonalService personalService;

	private Visit visit;
	private Assign assign;

	public AssignExec(Visit visit, Assign assign) {
		this.visit = visit;
		this.assign = assign;
	}

	public AssignExec(Long vId, Long aId) {
		this.visit = visitService.getByIdFull(vId);
		this.assign = assignService.getByIdFull(aId);
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
		assignExecForm.add(new TextArea<String>("resText").add(new PropertyValidator<String>()).setLabel(
				new ResourceModel("p.assign_exec.lb_assign_exec")));
		Button submitButton = new Button("submitButton") {

			@Override
			public void onSubmit() {

				try {
					if (assign.getResText() == null) {
						error(new ResourceModel("p.assign_exec.err_null_restext").getObject());
						return;
					}

					assign.setResPersonal(personalService.getByIdFull(BasicAuthenticationSession.get().getUserId()));
					assign.setResDt(Calendar.getInstance().getTime());

					assignService.saveOrUpdate(assign);

					if (assign.getPeriodGroupKey() < 0) {
						visit.setEndDt(Calendar.getInstance().getTime());
						visit.setDischargeFlag(EDischargeStatus.ASSENT);
						visitService.saveOrUpdate(visit);
					}

					setResponsePage(new CaseRecord(visit));
				} catch (RuntimeException e) {
					LOGGER.error("try to save assign execute. Class is: {}" + e, getClass().getName());
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
