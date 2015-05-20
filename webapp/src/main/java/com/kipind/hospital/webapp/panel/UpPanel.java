package com.kipind.hospital.webapp.panel;

import javax.servlet.http.HttpServletRequest;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.http.handler.RedirectRequestHandler;

import com.kipind.hospital.webapp.app.BasicAuthenticationSession;
import com.kipind.hospital.webapp.app.WicketWebApplication;

@SuppressWarnings("serial")
public class UpPanel extends Panel {

	public UpPanel(String id) {
		super(id);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		// add(new BookmarkablePageLink<Void>("mainMenu", HomePage.class));
		String hello = new ResourceModel("header.lb_hello").getObject();
		add(new Label("helloUser", new Model<String>(hello + BasicAuthenticationSession.get().getUserName())));
		add(new Link("logout") {

			@Override
			protected void onConfigure() {
				super.onConfigure();
			}

			@Override
			public void onClick() {
				final HttpServletRequest servletReq = (HttpServletRequest) getRequest().getContainerRequest();
				servletReq.getSession().invalidate();
				getSession().invalidate();

				getRequestCycle().scheduleRequestHandlerAfterCurrent(new RedirectRequestHandler(WicketWebApplication.LOGIN_URL));

			}
		});
	}

}
