package com.kipind.hospital.webapp.page;

import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.util.string.Strings;

import com.kipind.hospital.webapp.app.BasicAuthenticationSession;

public class LoginPage extends WebPage {

	static private String IMG_RUS_FLAG = "images/russia-flag.png";
	static private String IMG_RUS_FLAG_SMALL = "images/russia-flag32.png";
	static private String IMG_ENG_FLAG = "images/united-kingdom-flag.png";
	static private String IMG_ENG_FLAG_SMALL = "images/united-kingdom-flag32.png";

	private String itLogin;
	private String itPass;

	private Component fr;
	private Component fes;
	private Component frs;
	private Component fe;

	Locale ruLocale = new Locale("ru");
	Locale enLocale = new Locale("en");

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
					setResponsePage(Application.get().getHomePage());
				} else {
					error(new ResourceModel("error.autor_fail").getObject());
				}
			}
		};

		fr = new Image("flagRu", new ContextRelativeResource(IMG_RUS_FLAG)).setVisible(false);
		fes = new Image("flagEnSmall", new ContextRelativeResource(IMG_ENG_FLAG_SMALL)).setVisible(false);
		frs = new Image("flagRuSmall", new ContextRelativeResource(IMG_RUS_FLAG_SMALL)).setVisible(false);
		fe = new Image("flagEn", new ContextRelativeResource(IMG_ENG_FLAG)).setVisible(false);

		final Link<Void> toEn = new Link<Void>("toEn") {

			@Override
			public void onClick() {
				BasicAuthenticationSession.get().setLocale(enLocale);
				fr.setVisible(false);
				frs.setVisible(true);
				fe.setVisible(true);
				fes.setVisible(false);
			}
		};

		final Link<Void> toRu = new Link<Void>("toRu") {

			@Override
			public void onClick() {
				BasicAuthenticationSession.get().setLocale(ruLocale);
				frs.setVisible(false);
				fr.setVisible(true);
				fes.setVisible(true);
				fe.setVisible(false);

			}
		};
		toRu.add(fr);
		toEn.add(fes);
		toRu.add(frs);
		toEn.add(fe);

		add(toRu);
		add(toEn);

		FeedbackPanel feedbackPanel = new FeedbackPanel("infPanel");
		feedbackPanel.setOutputMarkupId(true);
		form.add(feedbackPanel);

		form.setDefaultModel(new CompoundPropertyModel<LoginPage>(this));
		form.add(new RequiredTextField<Integer>("itLogin").setLabel(new ResourceModel("p.login.er_login")));
		form.add(new PasswordTextField("itPass").setLabel(new ResourceModel("p.login.er_pass")));

		add(form);
	}

	@Override
	protected void onBeforeRender() {
		String localLang = BasicAuthenticationSession.get().getLocale().getLanguage();

		if (localLang.equals(new Locale("be").getLanguage()) || localLang.equals(new Locale("uk").getLanguage())
				|| localLang.equals(new Locale("ru").getLanguage())) {
			BasicAuthenticationSession.get().setLocale(ruLocale);
			frs.setVisible(false);
			fr.setVisible(true);
			fes.setVisible(true);
			fe.setVisible(false);

		} else {
			BasicAuthenticationSession.get().setLocale(enLocale);
			fr.setVisible(false);
			frs.setVisible(true);
			fe.setVisible(true);
			fes.setVisible(false);
		}
		super.onBeforeRender();
	}
}
