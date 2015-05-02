package by.kipind.hospital.services;

import java.util.List;

import org.hibernate.LazyInitializationException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.kipind.hospital.datamodel.Visit;
import by.kipind.hospital.services.testUtil.TestRandomVal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class VisitServiceTest extends BaseTest {

	Visit CheckLazyJoinInVisit;

	@Before
	public void beforTest() {
		cleanDB();
		generateDB();
	}

	@After
	public void afterTest() {
		cleanDB();

	}

	@Test
	public void DITest() {
		Assert.assertNotNull(visitService); // тест должен пройти, если DI
											// работает

	}

	@Test
	public void basicCRUDForVisitTest() {

		List<Visit> visitsList = visitService.getAllVisits();
		Assert.assertNotEquals(0, visitsList.size());

		try { // LazyInitializationException expected
			CheckLazyJoinInVisit = TestRandomVal.randomFromCollection(visitsList);
			CheckLazyJoinInVisit.getPatient().getId();
			Assert.assertEquals(1, 2);
		} catch (LazyInitializationException e) {

		}

		Visit visit = visitService.getByIdFull(CheckLazyJoinInVisit.getId());
		Visit visitForUpdate = visitService.getById(CheckLazyJoinInVisit.getId());

		Assert.assertNotNull(visit);
		Assert.assertEquals(visit, visitForUpdate); //

		try { // no LazyInitializationException expected
			visit.getPatient().getId();
			visit.getWard().getId();

		} catch (LazyInitializationException e) {
			Assert.assertEquals(1, 2);

		}
		// :TODO взять из списка существующих пациентов отличого пациета и
		// проерить метод update у visit
		// existPatients.remove(visit.getPatient());

	}
}
