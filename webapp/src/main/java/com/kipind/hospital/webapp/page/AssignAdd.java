package com.kipind.hospital.webapp.page;

import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.Assign;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.IAssignServise;
import com.kipind.hospital.services.IPersonalService;
import com.kipind.hospital.services.IVisitService;
import com.kipind.hospital.webapp.app.BasicAuthenticationSession;
import com.kipind.hospital.webapp.panel.VisitDetailsPanel;

public class AssignAdd extends BaseLayout {

	@Inject
	private IPersonalService personalService;
	@Inject
	private IVisitService visitService;
	@Inject
	private IAssignServise assignServiсe;

	private Visit visit;
	private Assign assign = new Assign();

	private Integer assignPeriod = 1;

	public AssignAdd(Visit curVisit) {
		this.visit = curVisit;
	}

	public AssignAdd(Long vId) {
		this.visit = visitService.getByIdFull(vId);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new VisitDetailsPanel("visitPanel", visit));

		AssignRecordDataProvider assignRecordDataProvider = new AssignRecordDataProvider();

		WebMarkupContainer iterBody = new WebMarkupContainer("record");
		iterBody.setOutputMarkupId(true);
		add(iterBody);

		DataView<Assign> dataView = new DataView<Assign>("elemList", assignRecordDataProvider) {

			String personalName;

			@Override
			protected void populateItem(Item<Assign> item) {
				Assign assign = item.getModelObject();
				personalName = assign.getPrscPersonal().getSecondName() + " " + assign.getPrscPersonal().getFirstName().substring(0, 1) + ".";
				item.add(new Label("caseRecordDate", new Model<Date>(assign.getPrscDt())));
				item.add(new Label("caseRecordText", new Model<String>(assign.getPrscText())));
				item.add(new Label("caseRecordExecutor", new Model<String>(personalName)));
				// TODO: для разых пользоателей разый исполитель
			}
		};
		iterBody.add(dataView);

		Form<Assign> assignForm = new Form<Assign>("prescribeForm", new CompoundPropertyModel<Assign>(assign));

		FeedbackPanel feedbackPanel = new FeedbackPanel("infPanel");
		feedbackPanel.setOutputMarkupId(true);
		assignForm.add(feedbackPanel);

		assignForm.add(new TextArea<String>("prscText").add(new PropertyValidator<String>()));
		assignForm.add(new TextField<Integer>("period", new PropertyModel<Integer>(this, "assignPeriod")).add(new PropertyValidator<Integer>()));

		Button submitButton = new Button("submitButton") {

			@Override
			public void onSubmit() {
				try {
					assign.setVisit(visit);
					assign.setPrscPersonal(personalService.getByIdFull(BasicAuthenticationSession.get().getUserId()));
					assignServiсe.saveAssignGroup(assign, assignPeriod);

					cleanAssignForm();
				} catch (RuntimeException e) {
					// TODO:ошибку в лог фаил
					error(new ResourceModel("error.general_save.callIT").getObject());

				}

			}

			private void cleanAssignForm() {
				assign.setPrscText(null);
				assignPeriod = 1;
			}
		};
		// submitButton.add(AttributeModifier.append("value", new
		// ResourceModel("button.save").getObject()));
		assignForm.add(submitButton);
		add(assignForm);

	}

	private class AssignRecordDataProvider extends SortableDataProvider<Assign, SingularAttribute<Assign, ?>> {

		@Override
		public Iterator<? extends Assign> iterator(long first, long count) {
			return assignServiсe.getAllAssignsOfVisit(visit.getId()).iterator();

		}

		@Override
		public long size() {
			return assignServiсe.getAllAssignsOfVisit(visit.getId()).size();
		}

		@Override
		public IModel<Assign> model(Assign assignObj) {
			return new Model<Assign>(assignObj);
		}

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.case_record.label");
	}

}
