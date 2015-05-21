package com.kipind.hospital.webapp.page;

import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.Personal;
import com.kipind.hospital.datamodel.Personal_;
import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.ICheckupService;
import com.kipind.hospital.services.IPersonalService;
import com.kipind.hospital.services.IVisitService;
import com.kipind.hospital.services.utilObject.PersonalPrototype;
import com.kipind.hospital.webapp.panel.MenuReport;

@AuthorizeInstantiation({ "DOCTOR", "NERS", "LEAD_DOCTOR" })
public class ReportPersonal extends BaseLayout {

	@Inject
	private ICheckupService checkupService;
	@Inject
	private IVisitService visitService;
	private Visit visit;
	@Inject
	private IPersonalService personalService;
	private PersonalPrototype PersonalPrototype;

	public ReportPersonal(Visit curVisit) {

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new MenuReport("menuPanel"));
		add(new OrderByBorder<SingularAttribute<PersonalPrototype, ?>>("sortByName", Personal_.firstName, caseRecordDataProvider));
		add(new OrderByBorder<SingularAttribute<PersonalPrototype, ?>>("sortBySecondName", Personal_.secondName, caseRecordDataProvider));
		// add(new OrderByBorder<SingularAttribute<PersonalPrototype,
		// ?>>("sortByName",
		// PersonalPrototype_.firstName, caseRecordDataProvider));
		add(new OrderByBorder<SingularAttribute<PersonalPrototype, ?>>("sortByProf", Personal_.prof, caseRecordDataProvider));
		add(new OrderByBorder<SingularAttribute<PersonalPrototype, ?>>("sortByLoadLvl", Personal_.firstName, caseRecordDataProvider));
		add(new OrderByBorder<SingularAttribute<PersonalPrototype, ?>>("sortByWorkStatus", Personal_.basePrice, caseRecordDataProvider));

		CaseRecordDataProvider caseRecordDataProvider = new CaseRecordDataProvider();

		WebMarkupContainer iterBody = new WebMarkupContainer("caseRecord");
		// iterBody.setOutputMarkupId(true);
		add(iterBody);

		DataView<PersonalPrototype> dataView = new DataView<PersonalPrototype>("elemList", caseRecordDataProvider) {

			String personalName, strInterview;

			@Override
			protected void populateItem(Item<PersonalPrototype> item) {
				Personal personal = item.getModelObject();

				/*
				 * personalName = personal.getPersonal().getSecondName() + " " +
				 * personal.getPersonal().getFirstName().substring(0, 1) + ".";
				 * strInterview = personal.getInterview().replace("[assign]",
				 * new ResourceModel("p.case_record.txt_prescribe").getObject())
				 * .replace("[result]", new
				 * ResourceModel("p.case_record.txt_prsc_res").getObject())
				 * .replace("[interview]", new
				 * ResourceModel("p.case_record.txt_interview").getObject());
				 * 
				 * item.add(new Label("caseRecordDate", new
				 * Model<Date>(personal.getChDt()))); item.add(new
				 * Label("caseRecordText", new Model<String>(strInterview)));
				 * item.add(new Label("caseRecordExecutor", new
				 * Model<String>(personalName)));
				 */
			}

		};

		iterBody.add(dataView);

	}

	private class CaseRecordDataProvider extends SortableDataProvider<PersonalPrototype, SingularAttribute<PersonalPrototype, ?>> {

		@Override
		public Iterator<? extends PersonalPrototype> iterator(long first, long count) {
			return personalService.getAllPersonal().iterator();
		}

		@Override
		public long size() {
			return visitService.getCaseRecordForVisit(visit.getId()).size();

		}

		@Override
		public IModel<Personal> model(PersonalPrototype personalObj) {
			return new Model<Personal>(personalObj);
		}

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.report_personal.label");
	}

}
