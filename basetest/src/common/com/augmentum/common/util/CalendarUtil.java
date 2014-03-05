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

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <a href="CalendarUtil.java.html"><b><i>View Source</i></b></a>
 * 
 */
public class CalendarUtil {

    public static String[] DAYS_ABBREVIATION = new String[] { "sunday-abbreviation",
            "monday-abbreviation", "tuesday-abbreviation", "wednesday-abbreviation",
            "thursday-abbreviation", "friday-abbreviation", "saturday-abbreviation" };

    public static int[] MONTH_IDS = new int[] { Calendar.JANUARY, Calendar.FEBRUARY,
            Calendar.MARCH, Calendar.APRIL, Calendar.MAY, Calendar.JUNE, Calendar.JULY,
            Calendar.AUGUST, Calendar.SEPTEMBER, Calendar.OCTOBER, Calendar.NOVEMBER,
            Calendar.DECEMBER };

    public static boolean afterByDay(Date date1, Date date2) {
        long millis1 = date1.getTime() / 86400000;
        long millis2 = date2.getTime() / 86400000;

        if (millis1 > millis2) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean beforeByDay(Date date1, Date date2) {
        long millis1 = date1.getTime() / 86400000;
        long millis2 = date2.getTime() / 86400000;

        if (millis1 < millis2) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean equalsByDay(Date date1, Date date2) {
        long millis1 = date1.getTime() / 86400000;
        long millis2 = date2.getTime() / 86400000;

        if (millis1 == millis2) {
            return true;
        } else {
            return false;
        }
    }

    public static int getDaysInMonth(Calendar cal) {
        return getDaysInMonth(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
    }

    public static int getDaysInMonth(int month, int year) {
        month++;

        if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8)
                || (month == 10) || (month == 12)) {

            return 31;
        } else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {

            return 30;
        } else {
            if (((year % 4) == 0) && ((year % 100) != 0) || ((year % 400) == 0)) {

                return 29;
            } else {
                return 28;
            }
        }
    }

    public static int getGregorianDay(Calendar cal) {
        int year = cal.get(Calendar.YEAR) - 1600;

        int month = cal.get(Calendar.MONTH) + 1;

        if (month < 3) {
            month += 12;
        }

        int day = cal.get(Calendar.DATE);

        int gregorianDay = (int) (6286 + (year * 365.25) - (year / 100) + (year / 400)
                + (30.6 * month) + 0.2 + day);

        return gregorianDay;
    }

    public static Date getGTDate(Calendar cal) {
        Calendar gtCal = (Calendar) cal.clone();

        gtCal.set(Calendar.HOUR_OF_DAY, 0);
        gtCal.set(Calendar.MINUTE, 0);
        gtCal.set(Calendar.SECOND, 0);
        gtCal.set(Calendar.MILLISECOND, 0);

        return gtCal.getTime();
    }

    public static int getLastDayOfWeek(Calendar cal) {
        int firstDayOfWeek = cal.getFirstDayOfWeek();

        if (firstDayOfWeek == Calendar.SUNDAY) {
            return Calendar.SATURDAY;
        } else if (firstDayOfWeek == Calendar.MONDAY) {
            return Calendar.SUNDAY;
        } else if (firstDayOfWeek == Calendar.TUESDAY) {
            return Calendar.MONDAY;
        } else if (firstDayOfWeek == Calendar.WEDNESDAY) {
            return Calendar.TUESDAY;
        } else if (firstDayOfWeek == Calendar.THURSDAY) {
            return Calendar.WEDNESDAY;
        } else if (firstDayOfWeek == Calendar.FRIDAY) {
            return Calendar.THURSDAY;
        }

        return Calendar.FRIDAY;
    }

    public static Date getLTDate(Calendar cal) {
        Calendar ltCal = (Calendar) cal.clone();

        ltCal.set(Calendar.HOUR_OF_DAY, 23);
        ltCal.set(Calendar.MINUTE, 59);
        ltCal.set(Calendar.SECOND, 59);
        ltCal.set(Calendar.MILLISECOND, 999);

        return ltCal.getTime();
    }

    public static int[] getMonthIds() {
        return MONTH_IDS;
    }

    public static Timestamp getTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return new Timestamp(date.getTime());
        }
    }

    public static boolean isDate(int month, int day, int year) {
        return Validator.isDate(month, day, year);
    }

    public static boolean isGregorianDate(int month, int day, int year) {
        return Validator.isGregorianDate(month, day, year);
    }

    public static boolean isJulianDate(int month, int day, int year) {
        return Validator.isJulianDate(month, day, year);
    }

    public static Calendar roundByMinutes(Calendar cal, int interval) {
        int minutes = cal.get(Calendar.MINUTE);

        int delta = 0;

        if (minutes < interval) {
            delta = interval - minutes;
        } else {
            delta = interval - (minutes % interval);
        }

        if (delta == interval) {
            delta = 0;
        }

        cal.add(Calendar.MINUTE, delta);

        return cal;
    }

    private static Map<String, String[]> _calendarPool = new ConcurrentHashMap<String, String[]>();

}