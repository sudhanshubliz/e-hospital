package com.kipind.hospital.webapp.page;

import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.Assign;
import com.kipind.hospital.datamodel.Checkup;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.ICheckupService;
import com.kipind.hospital.services.IPersonalService;
import com.kipind.hospital.services.IVisitService;
import com.kipind.hospital.webapp.app.BasicAuthenticationSession;
import com.kipind.hospital.webapp.panel.VisitDetailsPanel;

public class PrescribeAdd extends BaseLayout {

	@Inject
	private ICheckupService checkupService;
	@Inject
	private IVisitService visitService;
	@Inject
	private IPersonalService personalService;

	private Visit visit;
	private Assign assign;

	private Integer assignPeriod;

	public PrescribeAdd(Visit curVisit) {
		this.visit = curVisit;
	}

	public PrescribeAdd(Long vId) {
		this.visit = visitService.getByIdFull(vId);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new VisitDetailsPanel("visitPanel", visit));

		CaseRecordDataProvider caseRecordDataProvider = new CaseRecordDataProvider();

		WebMarkupContainer iterBody = new WebMarkupContainer("caseRecord");
		iterBody.setOutputMarkupId(true);
		add(iterBody);

		DataView<Checkup> dataView = new DataView<Checkup>("elemList", caseRecordDataProvider) {

			String personalName;

			@Override
			protected void populateItem(Item<Checkup> item) {
				Checkup checkup = item.getModelObject();
				personalName = checkup.getPersonal().getSecondName() + " " + checkup.getPersonal().getFirstName().substring(0, 1) + ".";
				item.add(new Label("caseRecordDate", new Model<Date>(checkup.getChDt())));
				item.add(new Label("caseRecordData", new Model<String>(checkup.getInterview())));
				item.add(new Label("caseRecordExecutor", new Model<String>(personalName)));

			}
		};
		iterBody.add(dataView);

		Form<Assign> prescribeForm = new Form<Assign>("prescribeForm", new CompoundPropertyModel<Assign>(assign));

		prescribeForm.add(new TextField<Date>("prscDt"));
		prescribeForm.add(new TextArea<String>("prscText"));
		prescribeForm.add(new TextField<Integer>("period", new PropertyModel<Integer>(this, "assignPeriod")));

		Button submitButton = new Button("submitButton") {

			@Override
			public void onSubmit() {
				// TODO: user из сессии
				assign.setPrscPersonal(personalService.getByIdFull(BasicAuthenticationSession.get().getUserId()));
				// assign.setPeriodGroupKey(assignServise.getMaxGroupId()+1);
				// ?? assign.setResSourseList(resSourseList);
				assign.setVisit(visit);
				// assignServise.saveOrUpdate(checkup);

				setResponsePage(new CaseRecord(visit.getId()));
			}
		};
		// submitButton.add(AttributeModifier.append("value", new
		// ResourceModel("button.save").getObject()));
		prescribeForm.add(submitButton);
		add(prescribeForm);

	}

	private class CaseRecordDataProvider extends SortableDataProvider<Checkup, SingularAttribute<Checkup, ?>> {

		@Override
		public Iterator<? extends Checkup> iterator(long first, long count) {
			return checkupService.getAllCheckupsOfVisit(visit.getId()).iterator();

		}

		@Override
		public long size() {
			return checkupService.getAllCheckupsOfVisit(visit.getId()).size();
		}

		@Override
		public IModel<Checkup> model(Checkup checkupObj) {
			return new Model<Checkup>(checkupObj);
		}

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.case_record.label");
	}

}
