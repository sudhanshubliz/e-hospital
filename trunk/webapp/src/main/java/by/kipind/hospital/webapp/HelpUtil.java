package by.kipind.hospital.webapp;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;

public class HelpUtil {

	public static int getAge(Date date) {

		DateTime today = new DateTime(Calendar.getInstance().getTime());
		DateTime startDate = new DateTime(date);
		Period p = new Period(startDate, today);

		return p.getYears();

	}

	/*
	 * public static int getAge(Date dateOfBirth) {
	 * 
	 * Calendar today = Calendar.getInstance(); Calendar birthDate =
	 * Calendar.getInstance();
	 * 
	 * int age = 0;
	 * 
	 * birthDate.setTime(dateOfBirth); if (birthDate.after(today)) { throw new
	 * IllegalArgumentException("Can't be born in the future"); }
	 * 
	 * age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
	 * 
	 * // If birth date is greater than todays date (after 2 days adjustment of
	 * // leap year) then decrement age one year if
	 * ((birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) >
	 * 3) || (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
	 * age--;
	 * 
	 * // If birth date and todays date are of same month and birth day of //
	 * month is greater than todays day of month then decrement age } else if
	 * ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH)) &&
	 * (birthDate.get(Calendar.DAY_OF_MONTH) >
	 * today.get(Calendar.DAY_OF_MONTH))) { age--; }
	 * 
	 * return age; }
	 */
	public static int getDayDelta(Date date) {

		DateTime today = new DateTime(Calendar.getInstance().getTime());
		DateTime startDate = new DateTime(date);
		Period p = new Period(startDate, today);

		return p.getDays();

	}
}
