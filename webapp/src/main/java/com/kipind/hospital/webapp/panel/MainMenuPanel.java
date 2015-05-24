package com.kipind.hospital.webapp.panel;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import com.kipind.hospital.webapp.page.CaseRecord;
import com.kipind.hospital.webapp.page.DayTask;
import com.kipind.hospital.webapp.page.PatientRegistration;
import com.kipind.hospital.webapp.page.ReportPersonal;

@SuppressWarnings("serial")
public class MainMenuPanel extends Panel {

	public MainMenuPanel(String id) {
		super(id);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new BookmarkablePageLink<Void>("patient_reg", PatientRegistration.class));
		add(new BookmarkablePageLink<Void>("patient", DayTask.class));
		add(new LinkForDoctor("report", ReportPersonal.class));

		Link<Void> link = new Link<Void>("meeting") {

			@Override
			public void onClick() {

				setResponsePage(new CaseRecord(236l)); // TODO: visit id from
														// Request

			}
		};
		add(link.setVisible(false));

	}

	@AuthorizeAction(action = Action.RENDER, roles = { "LEAD_DOCTOR" })
	private class LinkForDoctor extends BookmarkablePageLink<Void> {

		public LinkForDoctor(String id, Class cl) {
			super(id, cl);
		}
	}

}
