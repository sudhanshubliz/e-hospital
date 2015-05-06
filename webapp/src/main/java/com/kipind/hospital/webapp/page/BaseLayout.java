package com.kipind.hospital.webapp.page;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;

import com.kipind.hospital.webapp.panel.MainMenuPanel;
import com.kipind.hospital.webapp.panel.UpPanel;

public abstract class BaseLayout extends WebPage {

	private Component menuPanel;
	private Component upPanel;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(menuPanel = new MainMenuPanel("mainMenuPanel"));
		add(upPanel = new UpPanel("upPanel"));
		add(new Label("pageTitle", getPageTitle()));
		add(new FeedbackPanel("errPanel"));
	}

	protected abstract IModel<String> getPageTitle();

}
