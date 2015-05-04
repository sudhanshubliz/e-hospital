package com.kipind.hospital.webapp.panel;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

import com.kipind.hospital.webapp.page.LoginPage;

@SuppressWarnings("serial")
public class UpPanel extends Panel {

	public UpPanel(String id) {
		super(id);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		// add(new BookmarkablePageLink<Void>("mainMenu", HomePage.class));
		add(new BookmarkablePageLink<Void>("logout", LoginPage.class));

	}

}
