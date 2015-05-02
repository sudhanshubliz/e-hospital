package by.kipind.hospital.webapp.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;

public abstract class BaseLayout extends WebPage {

	// private ResourceModel resModel;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Label("pageTitle", getPageTitle()));

		// add(new Label("helloLabel", new ResourceModel("p.header.lb_hello")));

		add(new BookmarkablePageLink<Void>("mainMenu", HomePage.class));
		add(new BookmarkablePageLink<Void>("logout", LoginPage.class));

	}

	protected abstract IModel<String> getPageTitle();

}
