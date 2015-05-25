package com.kipind.hospital.webapp.page;

import java.util.Iterator;

import javax.inject.Inject;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.kipind.hospital.datamodel.objectPrototype.PersonalPrototype;
import com.kipind.hospital.services.IPersonalService;
import com.kipind.hospital.webapp.panel.MenuReport;

@AuthorizeInstantiation({ "LEAD_DOCTOR" })
public class ReportPersonal extends BaseLayout {

	@Inject
	private IPersonalService personalService;

	private PersonalPrototype personalPrototype;

	public ReportPersonal() {
		super();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		CaseRecordDataProvider caseRecordDataProvider = new CaseRecordDataProvider();

		add(new MenuReport("menuPanel"));
		add(new OrderByBorder<String>("sortByName", "firstName", caseRecordDataProvider));
		add(new OrderByBorder<String>("sortBySecondName", "secondName", caseRecordDataProvider));
		add(new OrderByBorder<String>("sortByProf", "prof", caseRecordDataProvider));
		add(new OrderByBorder<String>("sortByTab", "tabelNumber", caseRecordDataProvider));
		add(new OrderByBorder<String>("sortByWorkStatus", "delMarker", caseRecordDataProvider));

		WebMarkupContainer iterBody = new WebMarkupContainer("caseRecord");
		// iterBody.setOutputMarkupId(true);
		add(iterBody);

		DataView<PersonalPrototype> dataView = new DataView<PersonalPrototype>("elemList", caseRecordDataProvider, 5) {

			@Override
			protected void populateItem(Item<PersonalPrototype> item) {
				PersonalPrototype personal = item.getModelObject();

				item.add(new Label("caseRecordName", new Model<String>(personal.getFirstName())));
				item.add(new Label("caseRecordSecondName", new Model<String>(personal.getSecondName())));
				item.add(new Label("caseRecordWardList", new Model<String>(personal.getWardsString())));
				item.add(new Label("caseRecordProf", new ResourceModel("p.report_personal.prof_value_" + personal.getProf()).getObject()));
				item.add(new Label("caseRecordTab", new Model<Integer>(personal.getTabelNumber())));
				item.add(new CheckBox("caseRecordWorkStatus", new Model<Boolean>(!personal.getDelMarker())).setEnabled(false));

			}

		};

		iterBody.add(dataView);

		add(new PagingNavigator("paging", dataView));

	}

	private class CaseRecordDataProvider extends SortableDataProvider<PersonalPrototype, String> {

		public CaseRecordDataProvider() {
			super();
			setSort("secondName", SortOrder.ASCENDING);
		}

		@Override
		public Iterator<? extends PersonalPrototype> iterator(long first, long count) {
			String sortParam = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(sortParam);
			boolean ascending = SortOrder.ASCENDING.equals(propertySortOrder);
			return personalService.getAllPersonalInfo(sortParam, ascending, (int) first, (int) count).iterator();
		}

		@Override
		public long size() {
			return personalService.getCount();

		}

		@Override
		public IModel<PersonalPrototype> model(PersonalPrototype personalObj) {
			return new Model<PersonalPrototype>(personalObj);
		}

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.report_personal.label");
	}

}
