package com.kipind.hospital.webapp.page;

import java.util.Iterator;

import javax.inject.Inject;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.objectPrototype.PatientPrototype;
import com.kipind.hospital.services.IPatientService;
import com.kipind.hospital.webapp.panel.MenuReport;

@AuthorizeInstantiation({ "LEAD_DOCTOR" })
public class ReportPatient extends BaseLayout {

	@Inject
	private IPatientService patientService;

	private PatientPrototype patientPrototype;

	public ReportPatient() {
		super();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		CaseRecordDataProvider caseRecordDataProvider = new CaseRecordDataProvider();

		add(new MenuReport("menuPanel"));

		add(new OrderByBorder<String>("sortBySNumber", "socialNumber", caseRecordDataProvider));
		add(new OrderByBorder<String>("sortByFirstName", "firstName", caseRecordDataProvider));
		add(new OrderByBorder<String>("sortByLastName", "lastName", caseRecordDataProvider));
		add(new OrderByBorder<String>("sortByOld", "yearsOld", caseRecordDataProvider));
		add(new OrderByBorder<String>("sortByAddress", "address", caseRecordDataProvider));
		add(new OrderByBorder<String>("sortByGender", "sex", caseRecordDataProvider));
		add(new OrderByBorder<String>("sortByVisitNum", "visitsNum", caseRecordDataProvider));

		WebMarkupContainer iterBody = new WebMarkupContainer("caseRecord");
		// iterBody.setOutputMarkupId(true);
		add(iterBody);

		DataView<PatientPrototype> dataView = new DataView<PatientPrototype>("elemList", caseRecordDataProvider, 5) {

			@Override
			protected void populateItem(Item<PatientPrototype> item) {
				PatientPrototype patient = item.getModelObject();

				item.add(new Label("caseRecordSNumber", new Model<String>(patient.getSocialNumber())));
				item.add(new Label("caseRecordFirstName", new Model<String>(patient.getFirstName())));
				item.add(new Label("caseRecordLastName", new Model<String>(patient.getLastName())));
				item.add(new Label("caseRecordOld", new Model<Integer>(patient.getYearsOld())));
				item.add(new Label("caseRecordAddress", new Model<String>(patient.getAddress())));
				item.add(new Label("caseRecordGender", new ResourceModel("p.report_patient.gender_value_" + patient.getSex()).getObject()));
				item.add(new Label("caseRecordVisitNum", new Model<Integer>(patient.getVisitsNum())));

			}

		};

		iterBody.add(dataView);

		add(new PagingNavigator("paging", dataView));

	}

	private class CaseRecordDataProvider extends SortableDataProvider<PatientPrototype, String> {

		public CaseRecordDataProvider() {
			super();
			setSort("secondName", SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends PatientPrototype> iterator(long first, long count) {
			String sortParam = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(sortParam);
			boolean ascending = SortOrder.ASCENDING.equals(propertySortOrder);
			return patientService.getAllPatientInfo(sortParam, ascending, (int) first, (int) count).iterator();
		}

		@Override
		public long size() {
			return patientService.getCount();

		}

		@Override
		public IModel<PatientPrototype> model(PatientPrototype patientObj) {
			return new Model<PatientPrototype>(patientObj);
		}

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.report_patient.label");
	}

}
