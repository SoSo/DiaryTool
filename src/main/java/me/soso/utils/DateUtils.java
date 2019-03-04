package me.soso.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String dateFormat(Date date) {
        return dateFormat(date, null);
    }

    public static String dateFormat(Date date, String pattern) {
        if (pattern == null) {
            pattern = "yyyy-MM-dd";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);

        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);

        return year1 == year2 && month1 == month2;
    }

    public static boolean isSameWeek(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int week1 = calendar.get(Calendar.WEEK_OF_YEAR);

        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);
        int week2 = calendar.get(Calendar.WEEK_OF_YEAR);

        int subYear = year1 - year2;

        return week1 == week2 && (subYear == 0 || (subYear == 1 && month2 == 11) || (subYear == -1 && month1 == 11));
    }
}