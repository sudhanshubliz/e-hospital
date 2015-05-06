package com.kipind.hospital.webapp.panel;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import com.kipind.hospital.webapp.page.CaseRecord;
import com.kipind.hospital.webapp.page.DayTask;
import com.kipind.hospital.webapp.page.LoginPage;
import com.kipind.hospital.webapp.page.PatientRegistration;

@SuppressWarnings("serial")
public class MainMenuPanel extends Panel {

	public MainMenuPanel(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

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

}