package by.kipind.hospital.services.testUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.math3.random.RandomData;
import org.apache.commons.math3.random.RandomDataImpl;

import by.kipind.hospital.datamodel.Personal;

public abstract class TestRandomVal {

	private final static Random random = new Random();
	private static final int RANDOM_STRING_SIZE = 7;

	protected static final RandomData RANDOM_DATA = new RandomDataImpl();

	public static String randomString() {
		return RandomStringUtils.randomAlphabetic(RANDOM_STRING_SIZE);
	}

	public static String randomString(final String prefix) {
		return String.format("%s-%s", new Object[] { prefix, randomString() });
	}

	public static int randomTestObjectsCount() {
		return RANDOM_DATA.nextInt(0, 5) + 1;
	}

	public static int randomInteger() {
		return randomInteger(0, 9999);
	}

	public static int randomInteger(final int lower, final int upper) {
		int res;
		if (lower == upper) {
			res = lower;
		} else {
			res = RANDOM_DATA.nextInt(lower, upper);
		}

		return res;
	}

	public static boolean randomBoolean() {
		return Math.random() < 0.5;
	}

	public static long randomLong() {
		return RANDOM_DATA.nextLong(0, 9999999);
	}

	public static BigDecimal randomBigDecimal() {
		return new BigDecimal(randomDouble()).setScale(2, RoundingMode.HALF_UP);
	}

	public static double randomDouble() {
		final double value = random.nextDouble() + randomInteger();
		return Math.round(value * 1e2) / 1e2;

	}

	public static int randomBetween(int[] arr) {

		return arr[randomInteger(0, arr.length - 1)];
	}

	public static <T> T randomFromCollection(final Collection<T> all) {
		final int size = all.size();
		final int item = new Random().nextInt(size);
		int i = 0;
		for (final T obj : all) {
			if (i == item) {
				return obj;
			}
			i = i + 1;
		}
		return null;
	}

	public static <T> Set<T> randomSubCollection(final Collection<T> all, Integer subSize) {
		final int size = all.size();
		if (subSize.equals(null)) {
			subSize = randomInteger(1, size);
		}
		Set<T> subCollection = new HashSet<T>();
		for (int i = 1; i <= subSize; i++) {
			subCollection.add(randomFromCollection(all));
		}
		return subCollection;
	}

	public static Date randomDate() {
		final int year = randomInteger(1900, 2014);
		final GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.YEAR, year);
		final int dayOfYear = randomInteger(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
		gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
		return gc.getTime();
	}

	public static Date randomPastDate() {

		return DateUtils.addDays(Calendar.getInstance().getTime(), -1 * randomInteger(1, 99999));

	}

	public static Set<Personal> randomPersonalSet() {
		final Set<Personal> result = new HashSet<Personal>();
		for (int i = 1; i <= randomInteger(2, 5); i++) {
			result.add(new Personal());
		}
		return result;
	}

}
