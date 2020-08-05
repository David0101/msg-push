package com.jim.msg.push.commons.util;

import com.jim.msg.push.commons.enu.TimeType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * @program: msg-push
 * @author: jim
 * @create: 2020-08-05 14:35
 */
public abstract class DateUtil {

    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter dateFmt = DateTimeFormat.forPattern(PATTERN_DATE);
    private static final DateTimeFormatter timeFmt = DateTimeFormat.forPattern(PATTERN_TIME);
    private static final DateTimeFormatter dateTimeFmt = DateTimeFormat.forPattern(PATTERN_DATETIME);

    /**
     * 解析成yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        if (date == null) {
            return null;
        }
        return dateFmt.parseDateTime(date).toDate();
    }

    /**
     * 解析成HH:mm:ss
     *
     * @param time
     * @return
     */
    public static Date parseTime(String time) {
        if (time == null) {
            return null;
        }
        return timeFmt.parseDateTime(time).toDate();
    }

    /**
     * 解析成yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @return
     */
    public static Date parseDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }
        return dateTimeFmt.parseDateTime(dateTime).toDate();
    }

    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(pattern);
    }

    public static String formatDate(Date date) {
        return format(date, PATTERN_DATE);
    }

    /**
     * 解析成 HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        return format(date, PATTERN_TIME);
    }

    /**
     * 解析成 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return format(date, PATTERN_DATETIME);
    }

    /**
     * 设置日期的时间为23:59:59秒
     *
     * @param date
     * @return
     */
    public static Date getMaxTimeOfDate(Date date) {
        if (date == null) {
            return null;
        }
        DateTime dateTime1 = new DateTime(date.getTime());
        return new DateTime(dateTime1.getYear(), dateTime1.getMonthOfYear(), dateTime1.getDayOfMonth(), 23, 59, 59).toDate();
    }

    /**
     * 设置日期的时间为00:00:00
     *
     * @param date
     * @return
     */
    public static Date getMinTimeOfDate(Date date) {
        if (date == null) {
            return null;
        }
        DateTime dateTime1 = new DateTime(date.getTime());
        return new DateTime(dateTime1.getYear(), dateTime1.getMonthOfYear(), dateTime1.getDayOfMonth(), 0, 0, 0).toDate();
    }

    public static Date plusYears(Date date, int years) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.plusYears(years).toDate();
    }

    public static Date plusMonths(Date date, int months) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.plusMonths(months).toDate();
    }

    public static Date plusDays(Date date, int days) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.plusDays(days).toDate();
    }

    public static Date plusHours(Date date, int hours) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.plusHours(hours).toDate();
    }

    public static Date plusMinutes(Date date, int minutes) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.plusMinutes(minutes).toDate();
    }

    public static Date plusSeconds(Date date, int seconds) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.plusSeconds(seconds).toDate();
    }

    public static Date minusYears(Date date, int years) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.minusYears(years).toDate();
    }

    public static Date minusMonths(Date date, int months) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.minusMonths(months).toDate();
    }

    public static Date minusDays(Date date, int days) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.minusDays(days).toDate();
    }

    public static Date minusHours(Date date, int hours) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.minusHours(hours).toDate();
    }

    public static Date minusMinutes(Date date, int minutes) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.minusMinutes(minutes).toDate();
    }

    public static Date minusSeconds(Date date, int seconds) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.minusSeconds(seconds).toDate();
    }

    /**
     * 一个月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.dayOfMonth().withMinimumValue().toDate();
    }

    /**
     * 一个月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.dayOfMonth().withMaximumValue().toDate();
    }

    /**
     * 当前日期
     *
     * @return
     */
    public static Date getNow() {
        return new Date();    // return new DateTime().toDate();	//
    }

    /**
     * 转换成Calendar
     *
     * @param date
     * @return
     */
    public static Calendar convertDate2Calendar(Date date) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.toCalendar(Locale.CHINESE);
    }

    /**
     * 1-7（1-7对应一-日）
     *
     * @param date
     * @return
     */
    public static String getDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.dayOfWeek().getAsString();
    }

    /**
     * 获得两个时间的差,根据timeType，返回差值
     *
     * @param endDate   较大的时间
     * @param startDate 较小的时间
     * @param timeType  时间类型:Day,Hour,Min,Sec
     * @return 根据timeType，返回差值(长整型)
     */
    public static long getDateDiff(Date endDate, Date startDate, TimeType timeType) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        switch (timeType) {
            case Day:
                return diff / nd;// 计算差多少天
            case Hour:
                return diff / nh;// 计算差多少小时
            case Min:
                return diff / nm;// 计算差多少分钟
            default:
                return diff / ns;// 计算差多少秒
        }
    }

    public static long getTime(String timeStr, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = sdf.parse(timeStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前时间所在周的第一天
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getFirstOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);

        // 所在周开始日期
        return getMinTimeOfDate(cal.getTime());

    }

    /**
     * 获取当前时间所在周的第一天
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getLastOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        cal.add(Calendar.DAY_OF_WEEK, 6);

        // 所在周开始日期
        return getMaxTimeOfDate(cal.getTime());

    }

    /**
     * 根据类型增加对应偏移量的时间
     * @param timeOrig 初始时间
     * @param type 类型 the calendar field
     * @param offset 偏移量 the amount of date or time to be added to the field
     * @return
     */
    public static Date getAddOffsetTime(Date timeOrig, int type, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeOrig);
        calendar.add(type, offset);
        Date time = calendar.getTime();
        return time;
    }


    public static void main(String[] args) {

        String str0 = "2016-05-01";
        String str1 = "22:01:58";
        String str2 = "2016-01-02 23:10:05";

        Date dateA = DateUtil.parseDate(str0);
        Date dateB = DateUtil.parseDate(str0);
        Date dateC = DateUtil.parseTime(str1);
        Date dateD = DateUtil.parseDateTime(str2);

        String strDate1 = DateUtil.format(dateA, "yyyy-MM-dd HH:mm:ss.SS");
        String strDate2 = DateUtil.formatDate(dateA);
        String strDate3 = DateUtil.formatTime(dateC);
        String strDate4 = DateUtil.formatDateTime(dateD);

        Date dateE = DateUtil.getMinTimeOfDate(dateD);
        Date dateF = DateUtil.getMaxTimeOfDate(dateD);

        Calendar cal = DateUtil.convertDate2Calendar(dateD);

        System.out.println("dateA = " + dateA);
        System.out.println("dateB = " + dateB);
        System.out.println("dateC = " + dateC);
        System.out.println("dateD = " + dateD);
        System.out.println("strDate1 = " + strDate1);
        System.out.println("strDate2 = " + strDate2);
        System.out.println("strDate3 = " + strDate3);
        System.out.println("strDate4 = " + strDate4);
        System.out.println("dateE = " + DateUtil.formatDateTime(dateE));
        System.out.println("dateF = " + DateUtil.formatDateTime(dateF));

        System.out.println("calendar = " + DateUtil.formatDateTime(cal.getTime()));


//		Date date = new Date();
        long startTime = System.nanoTime();
        Date date = DateUtil.parseDateTime(str2);
//		Date date1 = DateUtil.plusDays(date, 2);
//		Date date2 = DateUtil.plusMonths(date, 2);
//		Date date3 = DateUtil.minusDays(date, 7);
//		Date date4 = DateUtil.minusMonths(date, 24);
        for (int i = 0; i < 100000; i++) {
//			DateUtil.parseDateTime(str1);
//			DateUtil.formatDate(date);
//			DateUtil.getNow();
//			DateUtil.getMaxTimeOfDate(date);
//			DateUtil.minusDays(date, 10);
//			DateUtil.convertDate2Calendar(date);
            DateUtil.getFirstDayOfMonth(date);
        }
        long endTime = System.nanoTime();
        System.out.println("耗时: " + (endTime - startTime) / 1000 + " ms");
//		System.out.println("plusDays 2 = " + DateUtil.formatDateTime(date1));
//		System.out.println("plusMonths 2 = " + DateUtil.formatDateTime(date2));
//		System.out.println("minusDays 7 = " + DateUtil.formatDateTime(date3));
//		System.out.println("minusMonths 24 = " + DateUtil.formatDateTime(date4));

//		Date firstDay = DateUtil.getFirstDayOfMonth(date);
//		Date lastDay = DateUtil.getLastDayOfMonth(date);
//
//		System.out.println("firstDay = " + DateUtil.formatDateTime(firstDay));
//		System.out.println("lastDay = " + DateUtil.formatDateTime(lastDay));

        long diff = getDateDiff(DateUtil.parseDateTime("2017-03-29 18:10:00"), DateUtil.parseDateTime("2017-03-29 15:15:00"), TimeType.Hour);
        System.out.println("相差：" + diff + " Hour");
    }

}
