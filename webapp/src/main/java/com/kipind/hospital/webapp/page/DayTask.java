package com.kipind.hospital.webapp.page;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.IPersonalService;
import com.kipind.hospital.services.IWardService;
import com.kipind.hospital.webapp.HelpUtil;

@SuppressWarnings("serial")
public class DayTask extends BaseLayout {

	@Inject
	private IWardService wardService;
	@Inject
	private IPersonalService personalService;

	private Long userId; //

	public DayTask() {
		this.userId = 34l;// из сессии
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		// final Set<Ward> userWards =
		// personalService.getByIdFull(userId).getWards();
		final List<Visit> openVisitsForUser = personalService.GetLinkedPatients(userId);

		add(new ListView<Visit>("caseRecord", openVisitsForUser) {

			String patientName;

			@Override
			protected void populateItem(ListItem<Visit> item) {
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
				// item.add(new Label("caseRecordPatient", new
				// Model<String>(patientName)));
				item.add(new Label("caseRecordDiagnoz", new Model<String>(visit.getFirstDs())));
				item.add(new Label("caseRecordDay", new Model<Integer>(HelpUtil.getDayDelta(visit.getStartDt()))));

			}
		});
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.day_task.label");
	}

}
