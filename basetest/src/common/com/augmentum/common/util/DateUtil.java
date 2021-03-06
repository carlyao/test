/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.augmentum.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * <a href="DateUtil.java.html"><b><i>View Source</i></b></a>
 * 
 * @author Brian Wing Shun Chan
 * 
 */
public class DateUtil {

	public static final String ISO_8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

	public static int compareTo(Date date1, Date date2) {

		// Workaround for bug in JDK 1.5.x. This bug is fixed in JDK 1.5.07. See
		// http://bugs.sun.com/bugdatabase/view_bug.do;:YfiG?bug_id=6207898 for
		// more information.

		if ((date1 != null) && (date2 == null)) {
			return -1;
		} else if ((date1 == null) && (date2 != null)) {
			return 1;
		} else if ((date1 == null) && (date2 == null)) {
			return 0;
		}

		long time1 = date1.getTime();
		long time2 = date2.getTime();

		if (time1 == time2) {
			return 0;
		} else if (time1 < time2) {
			return -1;
		} else {
			return 1;
		}
	}

	public static String getCurrentDate(String pattern, Locale locale) {
		return getDate(new Date(), pattern, locale);
	}

	public static String getDate(Date date, String pattern, Locale locale) {
		DateFormat dateFormat = new SimpleDateFormat(pattern, locale);

		return dateFormat.format(date);
	}

	public static int getDaysBetween(Date startDate, Date endDate, TimeZone timeZone) {

		Calendar startCal = new GregorianCalendar(timeZone);
		startCal.setTime(DateUtils.truncate(startDate, Calendar.DAY_OF_MONTH));

		Calendar endCal = new GregorianCalendar(timeZone);
		endCal.setTime(DateUtils.truncate(endDate, Calendar.DAY_OF_MONTH));

		int daysBetween = 0;
		while (startCal.before(endCal)) {
			daysBetween++;

			startCal.add(Calendar.DAY_OF_MONTH, 1);
		}

		return daysBetween;
	}

	public static DateFormat getISOFormat() {
		return getISOFormat(StringPool.BLANK);
	}

	public static DateFormat getISOFormat(String text) {
		String pattern = StringPool.BLANK;

		if (text.length() == 8) {
			pattern = "yyyyMMdd";
		} else if (text.length() == 12) {
			pattern = "yyyyMMddHHmm";
		} else if (text.length() == 13) {
			pattern = "yyyyMMdd'T'HHmm";
		} else if (text.length() == 14) {
			pattern = "yyyyMMddHHmmss";
		} else if (text.length() == 15) {
			pattern = "yyyyMMdd'T'HHmmss";
		} else if ((text.length() > 8) && (text.charAt(8) == 'T')) {
			pattern = "yyyyMMdd'T'HHmmssz";
		} else {
			pattern = "yyyyMMddHHmmssz";
		}

		return new SimpleDateFormat(pattern);
	}

	public static DateFormat getISO8601Format() {
		return new SimpleDateFormat(ISO_8601_PATTERN);
	}

	public static DateFormat getUTCFormat() {
		return getUTCFormat(StringPool.BLANK);
	}

	public static DateFormat getUTCFormat(String text) {
		String pattern = StringPool.BLANK;

		if (text.length() == 8) {
			pattern = "yyyyMMdd";
		} else if (text.length() == 12) {
			pattern = "yyyyMMddHHmm";
		} else if (text.length() == 13) {
			pattern = "yyyyMMdd'T'HHmm";
		} else if (text.length() == 14) {
			pattern = "yyyyMMddHHmmss";
		} else if (text.length() == 15) {
			pattern = "yyyyMMdd'T'HHmmss";
		} else {
			pattern = "yyyyMMdd'T'HHmmssz";
		}

		DateFormat dateFormat = new SimpleDateFormat(pattern);

		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		return dateFormat;
	}

	public static Date getTruncatedDate(int mon, int day, int year) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MONTH, mon);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.YEAR, year);

		return DateUtils.truncate(cal.getTime(), Calendar.DAY_OF_MONTH);
	}

	public static boolean isBetweenTime(Date date, String start, String end) {
		boolean isTime = false;

		if ((date != null) && StringUtils.isNotBlank(start) && StringUtils.isNotBlank(end)) {
			Date startDate = getDateFromStr(start);
			Date endDate = getDateFromStr(end);

			if (endDate.after(startDate)) {
				if ((date.compareTo(startDate) >= 0) && (date.compareTo(endDate) <= 0)) {
					isTime = true;
				}
			}
		}

		return isTime;
	}

	public static Date getDateFromStr(String str) {
		Date date = null;

		if (NumberUtils.isDigits(str) && (str.length() == 4)) {
			Calendar cal = Calendar.getInstance();
			cal = DateUtils.truncate(cal, Calendar.MINUTE);
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(str.substring(0, 2)));
			cal.set(Calendar.MINUTE, Integer.parseInt(str.substring(2)));

			date = cal.getTime();
		}

		return date;
	}

	public static Date convertTimeZones(Calendar fromCal, String tzId) {
		Date date = null;

		if ((fromCal != null) && StringUtils.isNotBlank(tzId)) {
			Calendar tzCal = new GregorianCalendar(TimeZone.getTimeZone(tzId));
			tzCal.setTimeInMillis(fromCal.getTimeInMillis());
			date = tzCal.getTime();
		}

		return date;
	}
	
	public static int getDaysBetween(Date startDate, Date endDate) {
		return getDaysBetween(startDate, endDate, TimeZone.getDefault());
	}
}