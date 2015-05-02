package by.kipind.hospital.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.kipind.hospital.datamodel.Patient;
import by.kipind.hospital.datamodel.Personal;
import by.kipind.hospital.datamodel.Visit;
import by.kipind.hospital.datamodel.Ward;
import by.kipind.hospital.services.testUtil.TestModelGenerator;
import by.kipind.hospital.services.testUtil.TestRandomVal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class BaseTest extends TestModelGenerator {

	private static int BASIC_SIZE = 50;

	@Inject
	protected IWardService wardService;
	@Inject
	protected IPersonalService personalService;
	@Inject
	protected IPatientService patientService;
	@Inject
	protected IVisitService visitService;

	protected Set<Personal> existPersonal = new HashSet<Personal>();
	protected Set<Patient> existPatients = new HashSet<Patient>();
	protected Set<Ward> existWards = new HashSet<Ward>();
	protected Set<Visit> existVisits = new HashSet<Visit>();

	@Before
	public void beforTest() {
		cleanDB();

	}

	@After
	public void afterTest() {
		cleanDB();

	}

	@Test
	public void cleanDB() {
		visitService.deleteAll();// link: patient,ward
		Assert.assertEquals(0, visitService.getAllVisits().size());

		wardService.deleteAll();// many_2_many:personal
		Assert.assertEquals(0, wardService.getAllWards().size());

		patientService.deleteAll();
		Assert.assertEquals(0, patientService.getAllPatients().size());
		personalService.deleteAll();
		Assert.assertEquals(0, personalService.getAllPersonal().size());
	}

	@Test
	public void generateDB() {
		if (personalService.getAllPersonal().size() == 0) {
			// создаем персонал
			int n = TestRandomVal.randomInteger(1, BASIC_SIZE); //
			for (int i = 1; i <= n; i++) {
				existPersonal.add(personalService.saveOrUpdate(TestModelGenerator.getPersonal()));
			}
			Assert.assertEquals(n, personalService.getAllPersonal().size());

			// создаем пациентов
			n = TestRandomVal.randomInteger(1, BASIC_SIZE); //
			for (int i = 1; i <= n; i++) {
				existPatients.add(patientService.saveOrUpdate(TestModelGenerator.getPatient()));
			}
			Assert.assertEquals(n, patientService.getAllPatients().size());

			// создаем палаты
			n = TestRandomVal.randomInteger(1, Math.round(BASIC_SIZE * 0.1f));
			for (int i = 1; i <= n; i++) {
				existWards.add(wardService.saveOrUpdate(TestModelGenerator.getWard(TestRandomVal.randomSubCollection(existPersonal, 3))));
			}
			Assert.assertEquals(n, wardService.getAllWards().size());

			// создаем визиты
			n = 0;
			List<Visit> visitsPerPAtient = new ArrayList<Visit>();
			for (Patient patient : existPatients) {
				visitsPerPAtient = TestModelGenerator.getVisitsForPatient(existWards, patient, TestRandomVal.randomBoolean());
				n = n + visitsPerPAtient.size();
				existVisits.addAll(visitService.saveOrUpdate(visitsPerPAtient));
			}
			Assert.assertEquals(n, visitService.getAllVisits().size());
		}

	}
}
