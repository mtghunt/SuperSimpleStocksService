package org.jpmorgan.supersimplestocks.service.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Chuvilin Sergey
 *
 */
public class DateTimeUtils {

	static final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs

	/**
	 *
	 * @param minutes
	 * @return Time stamp Past from Current on defined minutes
	 */
	public static Date getCurrentTimeMinusProvidedMinutes(int minutes) {

		Calendar date = Calendar.getInstance();
		long t = date.getTimeInMillis();
		Date afterAddingTenMins = new Date(t - (minutes * ONE_MINUTE_IN_MILLIS));

		return afterAddingTenMins;
	}

}
