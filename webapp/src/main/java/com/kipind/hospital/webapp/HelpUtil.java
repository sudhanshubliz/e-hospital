package com.kipind.hospital.webapp;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;

public class HelpUtil {

	public static int getAge(Date date) {

		DateTime today = new DateTime(Calendar.getInstance().getTime());
		DateTime startDate = new DateTime(date);
		Period p = new Period(startDate, today);

		return p.getYears();

	}

	public static int getDayDelta(Date date) {

		DateTime today = new DateTime(Calendar.getInstance().getTime());
		DateTime startDate = new DateTime(date);
		return Days.daysBetween(startDate, today).getDays();

	}
}
