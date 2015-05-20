package com.kipind.hospital.webapp.page;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.Personal;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.IPersonalService;
import com.kipind.hospital.services.IWardService;
import com.kipind.hospital.webapp.HelpUtil;
import com.kipind.hospital.webapp.app.BasicAuthenticationSession;

@SuppressWarnings("serial")
@AuthorizeInstantiation({ "DOCTOR", "NERS", "LEAD_DOCTOR" })
public class DayTask extends BaseLayout {

	@Inject
	private IWardService wardService;
	@Inject
	private IPersonalService personalService;

	private Personal user; //

	public DayTask() {
		super();
		this.user = personalService.getByIdFull(BasicAuthenticationSession.get().getUserId());

		// setOutputMarkupId(true);
		DayTaskDataProvider dayTaskDataProvider = new DayTaskDataProvider();

		WebMarkupContainer tableBody = new WebMarkupContainer("caseRecord");
		tableBody.setOutputMarkupId(true);
		add(tableBody);

		DataView<Visit> dataView = new DataView<Visit>("taskList", dayTaskDataProvider) {

			String patientName;

			@Override
			protected void populateItem(Item<Visit> item) {
				Visit visit = item.getModelObject();
				patientName = visit.getPatient().getLastName() + " " + visit.getPatient().getFirstName();
				Link<Void> link = new Link<Void>("caseRecordPatientLink") {

					@Override
					public void onClick() {
						setResponsePage(new CaseRecord(visit.getId()));
					}
				};
				link.add(new Label("caseRecordPatient", new Model<String>(patientName)));

				item.add(new Label("caseRecordWard", new Model<Integer>(visit.getWard().getWardNum())));
				item.add(link);
				item.add(new Label("caseRecordDiagnoz", new Model<String>(visit.getFirstDs())));
				item.add(new Label("caseRecordDay", new Model<Integer>(HelpUtil.getDayDelta(visit.getStartDt()))));

			}
		};

		tableBody.add(dataView);

	}

	private class DayTaskDataProvider extends SortableDataProvider<Visit, SingularAttribute<Visit, ?>> {

		@Override
		public Iterator<? extends Visit> iterator(long first, long count) {
			return personalService.GetLinkedPatients(user.getId()).iterator();
			// personalService.GetLinkedPatientsWithPaging(userId, (int) first,
			// (int) count).iterator();

		}

		@Override
		public long size() {
			return personalService.GetLinkedPatients(user.getId()).size();
		}

		@Override
		public IModel<Visit> model(Visit visit) {
			return new Model<Visit>(visit);
		}

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.day_task.label");
	}

}
