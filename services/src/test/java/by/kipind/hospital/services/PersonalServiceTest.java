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

import by.kipind.hospital.datamodel.Personal;
import by.kipind.hospital.services.testUtil.TestModelGenerator;
import by.kipind.hospital.services.testUtil.TestRandomVal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class PersonalServiceTest extends BaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonalServiceTest.class);

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
		Assert.assertNotNull(personalService); // тест должен пройти, если DI
												// работает

	}

	@Test
	public void basicCRUDForPersonalTest() {
		Personal personal = TestModelGenerator.getPersonal();
		personalService.saveOrUpdate(personal);

		Personal personalFromDb = personalService.getById(personal.getId());

		Assert.assertNotNull(personalFromDb);
		Assert.assertEquals(personalFromDb.getFirstName(), personal.getFirstName());
		Assert.assertEquals(personalFromDb.getSecondName(), personal.getSecondName());
		Assert.assertEquals(personalFromDb.getTabelNumber(), personal.getTabelNumber());
		Assert.assertEquals(personalFromDb.getPass(), personal.getPass());
		Assert.assertEquals(personalFromDb.getDelMarker(), personal.getDelMarker());
		Assert.assertEquals(personalFromDb.getConMarker(), personal.getConMarker());
		Assert.assertEquals(personalFromDb.getProf(), personal.getProf());

		personalFromDb.setFirstName(TestRandomVal.randomString("newFName--"));
		personalService.saveOrUpdate(personalFromDb);

		Personal personalFromDbUpdated = personalService.getById(personal.getId());
		Assert.assertNotEquals(personalFromDbUpdated.getFirstName(), personal.getFirstName());
		Assert.assertEquals(personalFromDbUpdated.getSecondName(), personal.getSecondName());
		Assert.assertEquals(personalFromDbUpdated.getTabelNumber(), personal.getTabelNumber());
		Assert.assertEquals(personalFromDbUpdated.getPass(), personal.getPass());
		Assert.assertEquals(personalFromDbUpdated.getDelMarker(), personal.getDelMarker());
		Assert.assertEquals(personalFromDbUpdated.getConMarker(), personal.getConMarker());
		Assert.assertEquals(personalFromDbUpdated.getProf(), personal.getProf());

		personalService.delete(personalFromDbUpdated);
		Assert.assertNull(personalService.getByIdFull(personal.getId()));

		int n = TestRandomVal.randomInteger(2, 100);
		for (int i = 1; i <= n; i++) {
			personalService.saveOrUpdate(TestModelGenerator.getPersonal());
		}
		List<Personal> personalsList = personalService.getAllPersonal();
		Assert.assertEquals(personalsList.size(), n);

		int j = 0;
		List<Personal> existPersonalList = new ArrayList<Personal>();
		for (Personal personal2 : personalsList) {
			if (TestRandomVal.randomInteger(0, 1) == 1) {
				existPersonalList.add(personal2);
				j++;
			}
		}

		personalService.delete(existPersonalList);

		personalsList.clear();
		personalsList = personalService.getAllPersonal();
		Assert.assertEquals(personalsList.size(), n - j);

		personalService.deleteAll();

		personalsList.clear();
		personalsList = personalService.getAllPersonal();
		Assert.assertEquals(personalsList.size(), 0);

	}

}
