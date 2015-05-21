package com.kipind.hospital.webapp.panel;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import com.kipind.hospital.webapp.page.AssignAdd;
import com.kipind.hospital.webapp.page.ReportPersonal;

@SuppressWarnings("serial")
public class MenuReport extends Panel {

	public MenuReport(String id) {
		super(id);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new BookmarkablePageLink<Void>("btPersonal", ReportPersonal.class));

		add(new Link<Void>("btPatients") {

			@Override
			public void onClick() {
				setResponsePage(new AssignAdd(1l));
			}
		});

		add(new Link<Void>("btHaspitalStat") {

			@Override
			public void onClick() {
				setResponsePage(new AssignAdd(1l));
			}
		});
	}

}
