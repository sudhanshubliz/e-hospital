package com.kipind.hospital.webapp.page;

import java.util.Date;
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

import com.kipind.hospital.datamodel.Checkup;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.ICheckupService;
import com.kipind.hospital.services.IVisitService;
import com.kipind.hospital.webapp.panel.VisitDetailsPanel;

@AuthorizeInstantiation({ "DOCTOR", "NERS", "LEAD_DOCTOR" })
public class CaseRecord extends BaseLayout {

	@Inject
	private ICheckupService checkupService;
	@Inject
	private IVisitService visitService;
	private Visit visit;

	public CaseRecord(Visit curVisit) {
		this.visit = curVisit;
	}

	public CaseRecord(Long vId) {
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

			String personalName, strInterview;

			@Override
			protected void populateItem(Item<Checkup> item) {
				Checkup checkup = item.getModelObject();
				personalName = checkup.getPersonal().getSecondName() + " " + checkup.getPersonal().getFirstName().substring(0, 1) + ".";
				strInterview = checkup.getInterview().replace("[assign]", new ResourceModel("p.case_record.txt_prescribe").getObject())
						.replace("[result]", new ResourceModel("p.case_record.txt_prsc_res").getObject())
						.replace("[interview]", new ResourceModel("p.case_record.txt_interview").getObject());

				item.add(new Label("caseRecordDate", new Model<Date>(checkup.getChDt())));
				item.add(new Label("caseRecordText", new Model<String>(strInterview)));
				item.add(new Label("caseRecordExecutor", new Model<String>(personalName)));

			}
		};

		iterBody.add(dataView);

		add(new Link<Void>("btInterview") {

			@Override
			public void onClick() {
				setResponsePage(new Interview(visit));
			}
		});
		add(new Link<Void>("btPrescribe") {

			@Override
			public void onClick() {
				setResponsePage(new AssignAdd(visit));
			}
		});

	}

	private class CaseRecordDataProvider extends SortableDataProvider<Checkup, SingularAttribute<Checkup, ?>> {

		@Override
		public Iterator<? extends Checkup> iterator(long first, long count) {
			// return
			// checkupService.getAllCheckupsOfVisit(visit.getId()).iterator();
			return visitService.getCaseRecordForVisit(visit.getId()).iterator();
		}

		@Override
		public long size() {
			// return
			// checkupService.getAllCheckupsOfVisit(visit.getId()).size();
			return visitService.getCaseRecordForVisit(visit.getId()).size();

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
