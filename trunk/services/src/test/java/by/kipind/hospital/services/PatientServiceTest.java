package by.kipind.hospital.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.kipind.hospital.datamodel.Patient;
import by.kipind.hospital.services.testUtil.TestModelGenerator;
import by.kipind.hospital.services.testUtil.TestRandomVal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class PatientServiceTest extends BaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PatientServiceTest.class);

	@Before
	public void beforTest() {
		cleanDB();

	}

	@After
	public void afterTest() {
		cleanDB();

	}

	@Test
	public void DITest() {
		Assert.assertNotNull(patientService); // тест должен пройти, если DI
												// работает

	}

	@Test
	public void basicCRUDForPatientTest() {
		Patient patient = TestModelGenerator.getPatient();
		patientService.saveOrUpdate(patient);

		Patient patientFromDb = patientService.getById(patient.getId());

		Assert.assertNotNull(patientFromDb);
		Assert.assertEquals(patientFromDb.getAddress(), patient.getAddress());
		Assert.assertEquals(patientFromDb.getFirstName(), patient.getFirstName());
		Assert.assertEquals(patientFromDb.getLastName(), patient.getLastName());
		Assert.assertEquals(patientFromDb.getSocialNumber(), patient.getSocialNumber());
		Assert.assertEquals(patientFromDb.getBirthDt().compareTo(patient.getBirthDt()), 0);
		Assert.assertEquals(patientFromDb.getSex(), patient.getSex());

		patientFromDb.setFirstName(TestRandomVal.randomString("newFName--"));
		patientService.saveOrUpdate(patientFromDb);

		Patient patientFromDbUpdated = patientService.getById(patient.getId());
		Assert.assertNotEquals(patientFromDbUpdated.getFirstName(), patient.getFirstName());
		Assert.assertEquals(patientFromDbUpdated.getSocialNumber(), patient.getSocialNumber());
		Assert.assertEquals(patientFromDbUpdated.getAddress(), patient.getAddress());
		Assert.assertEquals(patientFromDbUpdated.getLastName(), patient.getLastName());
		Assert.assertEquals(patientFromDbUpdated.getBirthDt().compareTo(patient.getBirthDt()), 0);
		Assert.assertEquals(patientFromDbUpdated.getSex(), patient.getSex());

		patientService.delete(patientFromDbUpdated);
		Assert.assertNull(patientService.getById(patient.getId()));

		int n = TestRandomVal.randomInteger(2, 100);
		for (int i = 1; i <= n; i++) {
			patientService.saveOrUpdate(TestModelGenerator.getPatient());
		}
		List<Patient> patientsList = patientService.getAllPatients();
		Assert.assertEquals(patientsList.size(), n);

		int j = 0;
		List<Patient> existPatientList = new ArrayList<Patient>();
		for (Patient patient2 : patientsList) {
			if (TestRandomVal.randomInteger(0, 1) == 1) {
				existPatientList.add(patient2);
				j++;
			}
		}

		patientService.delete(existPatientList);

		patientsList.clear();
		patientsList = patientService.getAllPatients();
		Assert.assertEquals(patientsList.size(), n - j);

		patientService.deleteAll();

		patientsList.clear();
		patientsList = patientService.getAllPatients();
		Assert.assertEquals(patientsList.size(), 0);

	}
}
