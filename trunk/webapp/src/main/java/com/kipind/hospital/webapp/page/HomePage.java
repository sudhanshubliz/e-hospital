package com.kipind.hospital.webapp.page;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

@SuppressWarnings("serial")
@AuthorizeInstantiation({ "DOCTOR", "NERS", "LEAD_DOCTOR" })
public class HomePage extends BaseLayout {

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new BookmarkablePageLink<Void>("patient_reg", PatientRegistration.class));
		// add(new BookmarkablePageLink<Void>("report", CaseRecord.class));
		add(new BookmarkablePageLink<Void>("patient", DayTask.class));
		add(new BookmarkablePageLink<Void>("meeting", LoginPage.class));

		Link<Void> link = new Link<Void>("report") {

			@Override
			public void onClick() {

				setResponsePage(new CaseRecord(236l)); // TODO: visit id from
														// Request

			}
		};
		add(link);

	}

	@Override
	protected IModel<String> getPageTitle() {
		return new ResourceModel("page.start.label");
	}

}
