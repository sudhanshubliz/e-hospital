package com.kipind.hospital.webapp.panel;

import java.util.Date;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.kipind.hospital.datamodel.Visit;
import com.kipind.hospital.services.IVisitService;
import com.kipind.hospital.webapp.HelpUtil;

public class VisitDetailsPanel extends Panel {

	@Inject
	private IVisitService visitService;
	private Visit visit;

	public VisitDetailsPanel(String id, Long visitId) {
		super(id);
		this.visit = visitService.getByIdFull(visitId);

	}

	public VisitDetailsPanel(String id, Visit visit) {
		super(id);
		this.visit = visit;

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		String fullName = visit.getPatient().getLastName() + "  " + visit.getPatient().getFirstName();
		int age = HelpUtil.getAge(visit.getPatient().getBirthDt());

		add(new Label("name", new Model<String>(fullName)));
		add(new Label("age", new Model<Integer>(age)));
		add(new Label("diagnoz", new Model<String>(visit.getFirstDs())));
		add(new Label("start_dt", new Model<Date>(visit.getStartDt())));

	}
}
