package com.example.factoryapplication.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * yyyy-MM-dd HH:mm:ss example：2010-11-22 13:14:55
	 */
	public static SimpleDateFormat NOW_TIME = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat NOW_TIME2 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");

	public static SimpleDateFormat NOW_TIME3 = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	/**
	 * yyyy-MM-dd example：2010-01-02,2010-1-2
	 */
	public static SimpleDateFormat NOW_XIEGANG = new SimpleDateFormat("yyyy/MM/dd");
	public static SimpleDateFormat NOW_DATE = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat NOW_DATE3 = new SimpleDateFormat("MM-dd");
	/**
	 * yyyyMMddHHmmsssss example：20101122134455666
	 */
	public static SimpleDateFormat STR_TIME = new SimpleDateFormat(
			"yyyyMMddHHmmsssss");
	public static SimpleDateFormat STR_DATE = new SimpleDateFormat("yyyyMMdd");
	/**
	 * yyyy-MM example：2010-2
	 */
	public static SimpleDateFormat STR_NODATE = new SimpleDateFormat("yyyy-MM");
	/**
	 * EEE example：星期一
	 */
	public static SimpleDateFormat WEEK_TIME = new SimpleDateFormat("EEE");
	public static SimpleDateFormat WEEK_TIME2 = new SimpleDateFormat(
			"EEE HH:mm");

	public static SimpleDateFormat TIME = new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat TIME2 = new SimpleDateFormat("HH:mm");

	public static SimpleDateFormat TIME_NOHOURS = new SimpleDateFormat("mm:ss");

	private static final String[] weekDays = { "星期日", "星期一", "星期二", "星期三",
			"星期四", "星期五", "星期六" };

	/**
	 * 获得Date的字符串显示
	 * 
	 * @param format
	 *            :NOW_TIME,NOW_DATE,STR_TIME,WEEK_TIME in DateUtil
	 * @param date
	 *            Date
	 * @return
	 */
	public static String getDateString(Date date, SimpleDateFormat sdf) {
		return sdf.format(date);
	}

	/**
	 * 获得当天Date的字符串显示
	 * 
	 * @param format
	 * @return
	 */
	public static String getDateString(SimpleDateFormat format) {
		Date date = new Date();
		return format.format(date);
	}
	
	/**
	 * 是否是周一
	 * @param date
	 * @return
	 */
	public static boolean isMonday(Date date){
		Calendar cal=Calendar.getInstance();
		cal.setTime(date); 
		int week=cal.get(Calendar.DAY_OF_WEEK)-1;
		
		return week ==1 ? true:false ;
	}

	/**
	 * 是否是周一
	 * @param date
	 * @return
	 */
	public static boolean isFirstDayOfMonth(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// 得到本月的那一天
		int today = c.get(c.DAY_OF_MONTH);
		// 然后判断是不是本月的第一天
		
		return today ==1 ? true:false ;
	}

	public static String getStringToTimeStr(String datestr,
			SimpleDateFormat format) {
		Date date = stringToDate(datestr, STR_TIME);
		return getDateString(date, format);
	}

	public static String timeToDateString(long time, SimpleDateFormat format) {
		Date d = new Date();
		d.setTime(time);
		return getDateString(d, format);
	}

	public static String timeToDateString(String timeStr,
			SimpleDateFormat format) throws NumberFormatException {
		long time = Long.parseLong(timeStr);
		return timeToDateString(time, format);
	}

	public static String getStringToDateStr(String datestr, SimpleDateFormat sf) {
		Date date = stringToDate(datestr, sf);
		return getDateString(date, NOW_DATE);
	}

	public static String getWeekOfDate(String date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(stringToDate(date, STR_TIME));
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static String getWeekOfDate(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	public static int getNowMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	public static boolean isToday(String dateStr, SimpleDateFormat sf) {
		String today = getDateString(NOW_DATE);
		dateStr = getDateString(stringToDate(dateStr, sf), NOW_DATE);
		return today.equals(dateStr);
	}

	/**
	 * 字符串显示转换为Date对象
	 * 
	 * @param dateStr
	 * @param type
	 * @return
	 */
	public static Date stringToDate(String dateStr, SimpleDateFormat type) {
		Date date = null;
		try {
			date = type.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return date;
		}
	}

	/**
	 * 得到这周周日的Date
	 * 
	 * @param d
	 * @return
	 */
	public static Date getSunDay() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return c.getTime();
	}

	/**
	 * 得到周日的Date
	 * 
	 * @param d
	 * @return
	 */
	public static Date getSunDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return c.getTime();
	}

	/**
	 * 得到周日的Date
	 * 
	 * @param d
	 * @return
	 */
	public static Date getSunDay(String dateStr, SimpleDateFormat format) {
		Date d = stringToDate(dateStr, format);
		return getSunDay(d);
	}

	/**
	 * 得到周六的Date
	 * 
	 * @param d
	 * @return
	 */
	public static Date getSaturDay() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		return c.getTime();
	}

	/**
	 * 得到周六的Date
	 * 
	 * @param d
	 * @return
	 */
	public static Date getSaturDay(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		return c.getTime();
	}

	/**
	 * 得到周六的Date
	 * 
	 * @param d
	 * @return
	 */
	public static Date getSaturDay(String dateStr, SimpleDateFormat format) {
		Date d = stringToDate(dateStr, format);
		return getSaturDay(d);
	}

	/**
	 * 得到月的第一天的Date
	 * 
	 * @param d
	 * @return
	 */
	public static Date getFirstDateOfMonth(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DATE, 1);
		return c.getTime();
	}

	/**
	 * 得到月的第一天的Date
	 * 
	 * @param d
	 * @return
	 */
	public static Date getFirstDateOfMonth(String dateStr,
			SimpleDateFormat format) {
		Date d = stringToDate(dateStr, format);
		return getFirstDateOfMonth(d);
	}

	/**
	 * 得到当月的最后一天的Date
	 * 
	 * @param d
	 * @return
	 */
	public static Date getLastDateOfMonth() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, 1);
		c.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		c.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		return c.getTime();
	}

	/**
	 * 得到月的最后一天的Date
	 * 
	 * @param d
	 * @return
	 */
	public static Date getLastDateOfMonth(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.DATE, 1);
		c.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		c.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		return c.getTime();
	}

	/**
	 * 得到月的最后一天的Date
	 * 
	 * @param d
	 * @return
	 */
	public static Date getLastDateOfMonth(String dateStr,
			SimpleDateFormat format) {
		Date d = stringToDate(dateStr, format);
		return getLastDateOfMonth(d);
	}

	/**
	 * 一天的开始
	 * 
	 * @return
	 */
	public static Date getStartOfDay() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 得到一天的开始
	 * 
	 * @param date
	 *            指定的天
	 * @return
	 */
	public static Date getStartOfDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}

	/**
	 * 一天的结束
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndOfDay() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	/**
	 * 一天的结束
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndOfDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	/**
	 * 第二天的开始
	 * 
	 * @return
	 */
	public static Date getNextDay() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}

	/**
	 * 第二天的开始
	 * 
	 * @return
	 */
	public static Date getNextDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}

	/**
	 * 一周的开始
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartOfWeek(Date date) {
		Date startOfWeek = getSunDay(date);
		return getStartOfDay(startOfWeek);
	}

	/**
	 * 一周的结束
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndOfWeek(Date date) {
		Date endOfWeek = getSaturDay(date);
		return getEndOfDay(endOfWeek);
	}

	/**
	 * 上周的今天
	 * 
	 * @return
	 */
	public static Date getLastWeek() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -7);
		return c.getTime();
	}

	/**
	 * 一月的开始
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartOfMonth(Date date) {
		Date startOfMonth = getFirstDateOfMonth(date);
		return getStartOfDay(startOfMonth);
	}

	/**
	 * 一月的结束
	 * 
	 * @param date
	 * @return
	 */
	public static Date getEndOfMonth(Date date) {
		Date endOfMonth = getEndOfMonth(date);
		return getEndOfDay(endOfMonth);
	}

	/**
	 * 得到从指定日期开始，到现在的天数。指定日期必须早于现在。
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份，从1开始
	 * @param date
	 *            日期，从1开始
	 * @return 间隔的天数
	 */
	public static int computeDaysFrom(int year, int month, int date) {
		Calendar baseDay = Calendar.getInstance(); // 基础日期
		Calendar now = Calendar.getInstance();// 当前日期
		int betweenDays = 0; // 间隔天数
		int betweenYears = 0; // 间隔年数
		baseDay.set(year, month - 1, date);

		if (now.compareTo(baseDay) < 0) {
			return betweenDays;
		}

		betweenYears = now.get(Calendar.YEAR) - baseDay.get(Calendar.YEAR);
		betweenDays = now.get(Calendar.DAY_OF_YEAR)
				- baseDay.get(Calendar.DAY_OF_YEAR);

		for (int index = 0; index < betweenYears; index++) {
			betweenDays += baseDay.getActualMaximum(Calendar.DAY_OF_YEAR);
			baseDay.set(Calendar.YEAR, year + 1);
		}

		return betweenDays;
	}

	public static int getWeekCountOfMonth(Date d) {
		Date startOfMonth = getFirstDateOfMonth(d);
		Date ehdOfMonth = getLastDateOfMonth(d);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startOfMonth);
		int startWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.setTime(ehdOfMonth);
		int endWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		return endWeek - startWeek + 1;
	}

	public static int getWeekCountOfMonth(String dateStr,
			SimpleDateFormat format) {
		return getWeekCountOfMonth(stringToDate(dateStr, format));
	}

	public static int getWeekNumberOfMonth(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		return calendar.get(Calendar.WEEK_OF_MONTH);
	}

	public static int getWeekNumberOfMonth(String dateStr,
			SimpleDateFormat format) {
		return getWeekNumberOfMonth(stringToDate(dateStr, format));
	}

	public static int getDayNumberOfWeek(Date d) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static int getDayNumberOfWeek(String dateStr, SimpleDateFormat format) {
		return getDayNumberOfWeek(stringToDate(dateStr, format));
	}

	public static String getTime() {
		return new Date().getTime() + "";
	}

	public static long getTime(String s, SimpleDateFormat format) {
		Date date = stringToDate(s, format);
		return date.getTime();
	}

	public static String getImageDateStr(String dateStr, SimpleDateFormat format) {
		Date date = stringToDate(dateStr, format);
		return getImageDateStr(date, dateStr);
	}

	public static String getImageDateStr(Date date, String def) {
		if (date == null) {
			return def;
		}
		long nowTime = System.currentTimeMillis();
		long dateTime = date.getTime();
		long ax = nowTime - dateTime;
		String q = "前";
		// if (ax > 0) {
		// q = "前";
		// }
		// else {
		// return "刚刚";
		// }
		long x = Math.abs(ax);
		if (x <= 60 * 1000) {
			return "刚刚";
		}
		if (x <= 60 * 60 * 1000) {
			return (x / (60 * 1000)) + "分钟" + q;
		}

		if (x <= 24 * 60 * 60 * 1000) {
			return (x / (60 * 60 * 1000)) + "小时" + q;
		}

		// if (x <= 2 * 24 * 60 * 60 * 1000) {
		// if (ax > 0) {
		// return "昨天 " + getDateString(date, DateUtil.TIME2);
		// } else if (ax < 0) {
		// return "明天 " + getDateString(date, DateUtil.TIME2);
		// }
		// }
		//
		// if (x <= 3 * 24 * 60 * 60 * 1000) {
		// if (ax > 0) {
		// return "前天 " + getDateString(date, DateUtil.TIME2);
		// } else if (ax < 0) {
		// return "后天 " + getDateString(date, DateUtil.TIME2);
		// }
		// }
		//
		// Date weekstart = getStartOfWeek(new Date());
		// Date weekend = getEndOfWeek(new Date());
		// // 如果在本周之内
		// if (dateTime <= weekend.getTime() && dateTime >= weekstart.getTime())
		// {
		// return getWeekOfDate(date) + " "
		// + getDateString(date, DateUtil.TIME2);
		// }
		return getDateString(date, DateUtil.NOW_TIME2);
	}

}
