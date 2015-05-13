package com.kipind.hospital.webapp.app;

import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;

import com.kipind.hospital.datamodel.Personal;
import com.kipind.hospital.datamodel.enam.EProf;
import com.kipind.hospital.services.IPersonalService;

public class BasicAuthenticationSession extends AuthenticatedWebSession {

	private Long userId;
	private String userName;
	private EProf userRoleId;
	private Roles resultRoles;

	@Inject
	private IPersonalService personalService;

	public BasicAuthenticationSession(final Request request) {
		super(request);
		Injector.get().inject(this);
	}

	public static BasicAuthenticationSession get() {
		return (BasicAuthenticationSession) Session.get();
	}

	@Override
	public boolean authenticate(final String userTab, final String password) {
		boolean authenticationResult = false;

		Personal account = personalService.getPersonalByTab(userTab);

		if (account != null && account.getPass().equals(password)) {
			this.userId = account.getId();
			this.userName = account.getSecondName() + " " + account.getFirstName();
			this.userRoleId = account.getProf();
			authenticationResult = true;
		}
		return authenticationResult;
	}

	@Override
	public Roles getRoles() {
		if (isSignedIn() && (resultRoles == null)) {
			resultRoles = new Roles();
			resultRoles.add(userRoleId.name());
		}
		return resultRoles;
	}

	@Override
	public void signOut() {
		super.signOut();
		this.userId = null;
	}

	public String getUserName() {
		return this.userName;
	}

	public Long getUserId() {
		return this.userId;
	}

}
