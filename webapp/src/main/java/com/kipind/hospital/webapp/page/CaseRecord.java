package com.kipind.hospital.webapp.page;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.Checkup;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.ICheckupService;
import com.kipind.hospital.webapp.panel.VisitDetailsPanel;

public class CaseRecord extends BaseLayout {

	@Inject
	private ICheckupService checkupService;
	private Long visitId; //

	public CaseRecord(Visit curVisit) {
		this.visitId = curVisit.getId();
	}

	public CaseRecord(Long vId) {
		this.visitId = vId;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new VisitDetailsPanel("visitPanel", visitId));

		final List<Checkup> caseRecord = checkupService.getAllCheckupsOfVisit(visitId);

		add(new ListView<Checkup>("caseRecord", caseRecord) {

			String personalName;

			@Override
			protected void populateItem(ListItem<Checkup> item) {
				Checkup checkup = item.getModelObject();
				personalName = checkup.getPersonal().getSecondName() + " " + checkup.getPersonal().getFirstName().substring(0, 1) + ".";
				item.add(new Label("caseRecordDate", new Model<Date>(checkup.getChDt())));
				item.add(new Label("caseRecordData", new Model<String>(checkup.getInterview())));
				item.add(new Label("caseRecordExecutor", new Model<String>(personalName)));

			}
		});
	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.case_record.label");
	}

}
