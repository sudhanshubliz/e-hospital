package com.kipind.hospital.webapp.page;

import java.util.Date;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
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
	// private Checkup checkup;
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

		add(new BookmarkablePageLink<Void>("btInterview", HomePage.class));
		add(new BookmarkablePageLink<Void>("btPrescribe", HomePage.class));

	}

	private class CaseRecordDataProvider extends SortableDataProvider<Checkup, SingularAttribute<Checkup, ?>> {

		@Override
		public Iterator<? extends Checkup> iterator(long first, long count) {
			return checkupService.getAllCheckupsOfVisit(visitId).iterator();

		}

		@Override
		public long size() {
			return checkupService.getAllCheckupsOfVisit(visitId).size();
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
