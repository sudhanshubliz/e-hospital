package by.kipind.hospital.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.LazyInitializationException;
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
import by.kipind.hospital.datamodel.Ward;
import by.kipind.hospital.services.testUtil.TestModelGenerator;
import by.kipind.hospital.services.testUtil.TestRandomVal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class WardServiceTest extends BaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(WardServiceTest.class);

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
		Assert.assertNotNull(wardService); // тест должен пройти, если DI
											// работает

	}

	@Test
	public void basicCRUDForWardTest() {

		Set<Personal> pers = new HashSet<Personal>();
		Personal existPers = null;
		int n = TestRandomVal.randomInteger(2, 5);
		int m = TestRandomVal.randomInteger(1, n);
		for (int i = 1; i <= n; i++) {
			if (i == m) {
				existPers = personalService.saveOrUpdate(TestModelGenerator.getPersonal());
				pers.add(existPers);
				continue;
			}
			pers.add(personalService.saveOrUpdate(TestModelGenerator.getPersonal()));
		}

		Ward ward = TestModelGenerator.getWard(pers);
		wardService.saveOrUpdate(ward);

		Ward wardFromDb = wardService.getByIdFull(ward.getId());

		Assert.assertNotNull(wardFromDb);
		Assert.assertEquals(wardFromDb.getComfortLvl(), ward.getComfortLvl());
		Assert.assertEquals(wardFromDb.getWardNum(), ward.getWardNum());

		try { // no LazyInitializationException expected
			Set<Personal> checkLazyPersonalFromWard = wardFromDb.getPersonal();
			Assert.assertEquals(wardFromDb.getPersonal().size(), ward.getPersonal().size());
			Assert.assertEquals((wardFromDb.getPersonal().containsAll(ward.getPersonal())), Boolean.TRUE);

		} catch (LazyInitializationException e) {
			Assert.assertEquals(1, 2);

		}

		ward.getPersonal().remove(existPers);
		wardService.saveOrUpdate(ward);
		Ward wardFromDbUpdated = wardService.getByIdFull(wardFromDb.getId());

		Assert.assertNotEquals(wardFromDbUpdated.getPersonal().size(), wardFromDb.getPersonal().size());

		wardService.delete(wardFromDbUpdated);
		wardFromDbUpdated = wardService.getById(wardFromDb.getId());
		Assert.assertNull(wardFromDbUpdated);

	}

	@Test
	public void blockCRUDWardTest() {
		Set<Personal> pers = new HashSet<Personal>();
		Set<Personal> existPers = new HashSet<Personal>();

		int n = TestRandomVal.randomInteger(1, 50); //
		for (int i = 1; i <= n; i++) {
			pers.add(personalService.saveOrUpdate(TestModelGenerator.getPersonal()));
		}
		int m = TestRandomVal.randomInteger(1, 10);

		for (int i = 1; i <= m; i++) {
			existPers.clear();
			existPers.addAll(TestRandomVal.randomSubCollection(pers, TestRandomVal.randomInteger(1, 5)));
			wardService.saveOrUpdate(TestModelGenerator.getWard(existPers));
		}

		List<Ward> wards = wardService.getAllWards();
		Assert.assertEquals(wards.size(), m);

		try {
			// exception will be thrown because of lazy collection
			TestRandomVal.randomFromCollection(wards.get(TestRandomVal.randomInteger(0, wards.size()) - 1).getPersonal()).getId();
			Assert.assertEquals(1, 2);
		} catch (LazyInitializationException e) {
			Assert.assertEquals(1, 1);
		}

		/*
		 * Personal persWithWards =
		 * TestRandomVal.randomFromCollection(existPers); wards.clear(); wards =
		 * wardService.getAllPersonalWards(persWithWards);
		 * Assert.assertNotEquals(wards.size(), 0); try { // exception will be
		 * thrown because of lazy collection Personal checkLazyPersonal2 =
		 * TestRandomVal
		 * .randomFromCollection(wards.get(TestRandomVal.randomInteger(0,
		 * wards.size()) - 1) .getPersonal()); Assert.assertEquals(1, 2); }
		 * catch (LazyInitializationException e) { Assert.assertEquals(1, 1); }
		 */
	}

}
