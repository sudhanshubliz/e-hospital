package com.kipind.hospital.webapp.page;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.string.Strings;

public class LoginPage extends WebPage {

	private String itLogin;
	private String itPass;

	@Override
	protected void onInitialize() {
		super.onInitialize();

		if (AuthenticatedWebSession.get().isSignedIn()) {
			setResponsePage(Application.get().getHomePage());
			return;
		}

		Form<LoginPage> form = new Form<LoginPage>("fmLogin") {

			@Override
			protected void onSubmit() {
				if (Strings.isEmpty(itLogin) || Strings.isEmpty(itPass)) {
					return;
				}
				final boolean authResult = AuthenticatedWebSession.get().signIn(itLogin, itPass);
				if (authResult) {
					// continueToOriginalDestination();
					setResponsePage(Application.get().getHomePage());
				} else {
					error("Ошибка авторизации");
				}
			}
		};

		FeedbackPanel feedbackPanel = new FeedbackPanel("infPanel");
		feedbackPanel.setOutputMarkupId(true);
		form.add(feedbackPanel);

		form.setDefaultModel(new CompoundPropertyModel<LoginPage>(this));
		form.add(new RequiredTextField<Integer>("itLogin"));
		form.add(new PasswordTextField("itPass"));
		add(form);
	}

}
