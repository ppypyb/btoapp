package com.example.factoryapplication.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author dyc
 * 
 */
public class TimeUtil {

	/**
	 * @roseuid 480D5FF90261
	 */
	public TimeUtil() {

	}

	/**
	 * 时间格式 yyyy-MM-dd
	 */
	public static final String FORMART1 = "yyyy-MM-dd";

	/**
	 * 时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static final String FORMART2 = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMART21 = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 时间格式 yyyyMMdd
	 */
	public static final String FORMART3 = "yyyyMMdd";

	/**
	 * 时间格式 yyyy年MM月dd日
	 */
	public static final String FORMART4 = "yyyy年MM月dd日";

	/**
	 * 时间格式 yyyy年MM月dd日
	 */
	public static final String FORMART41 = "yyyy年MM月dd日HH时";
	public static final String FORMART42 = "yyyy年MM月dd日HH时";


	/**
	 * 时间格式 yyyy年MM月dd日 HH:mm:ss
	 */
	public static final String FORMART5 = "yyyy年MM月dd日 HH:mm:ss";
	public static final String FORMART51 = "yyyy年MM月dd日HH时mm分ss";
	/**
	 * 时间格式 yyyyMMddHHmmss
	 */
	public static final String FORMART6 = "yyyyMMddHHmmss";

	/**
	 * 时间格式 yyyy/MM/dd HH:mm:ss
	 */
	public static final String FORMART7 = "yyyy/MM/dd HH:mm:ss";

	private static SimpleDateFormat sdf;

	/**
	 * 将字符串转换成为日期类型
	 * 
	 * @param dateFormat
	 *            指定的格式
	 * @param date
	 *            需要转换成日期的字符串
	 * @return 转换后的日期
	 * @throws IllegalArgumentException
	 *             参数不合法异常
	 * @throws ParseException
	 *             解析日期异常
	 */
	public static Date stringToDate(String dateFormat, String date) throws IllegalArgumentException, ParseException {
		if (dateFormat == null || dateFormat.equals("")) {
			throw new IllegalArgumentException("parameter dateFormat is not valid");
		}
		if (date == null || date.equalsIgnoreCase("")) {
			throw new IllegalArgumentException("parameter date is not valid");
		}
		SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
		try {
			return formater.parse(date);
		} catch (ParseException exception) {
			throw exception;
		}

	}

	public static long dateDiff(Date startTime, Date endTime) {
//按照传入的格式生成一个simpledateformate对象
		long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
		long nh = 1000 * 60 * 60;//一小时的毫秒数
		long nm = 1000 * 60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数long diff;try {
//获得两个时间的毫秒时间差异
		long diff = endTime.getTime() - startTime.getTime();
		long day = diff / nd;//计算差多少天
		return day ;
	}

	/**
	 * @see 把字符串类型的时间转换为 yyyy年MM月dd日
	 */
	public static String getLastWeekByStrToYMD1() {
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
//		cl.add(Calendar.DAY_OF_YEAR, -1);	//一天
//		cl.add(Calendar.WEEK_OF_YEAR, -1);	//一周
		cl.add(Calendar.MONTH, -1);			//一个月
		Date dateFrom = cl.getTime();
		DateFormat df = new SimpleDateFormat(FORMART1, java.util.Locale.CHINA);
		String date = df.format(dateFrom);
		return date;
	}

	/**
	 * @see 把字符串类型的时间转换为 yyyy年MM月dd日
	 */
	public static String getDateByStrToYMD1(String str) {
		String date = null;
		Date date1 = getDateByStrToYMD(str);
		DateFormat df = new SimpleDateFormat(FORMART4, java.util.Locale.CHINA);
		date = df.format(date1);
		return date;
	}

	/**
	 * @see 把字符串类型的时间转换为yyyy-MM-dd的时间格式
	 */
	public static Date getDateByStrToYMD(String str) {
		Date date = null;
		if (str != null && str.trim().length() > 0) {
			DateFormat dFormat = new SimpleDateFormat(TimeUtil.FORMART1);
			try {
				date = dFormat.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 将date类型数据转换成EEE MM/dd 格式字符串
	 * 
	 * @param date
	 *            需要转换的时间
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	public static String DateToStringEEE(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd  EEE", java.util.Locale.CHINA); // EEE
																							// 表示星期
		return df.format(date);

	}

	/**
	 * 将date类型数据转换成yyyy-MM-dd格式字符串
	 * 
	 * @param date
	 *            需要转换的时间
	 * @throws IllegalArgumentException
	 * @throws ParseException
	 */
	public static String getDateByStrTo(Date date) {
		DateFormat df = new SimpleDateFormat(TimeUtil.FORMART1, java.util.Locale.CHINA); // EEE
																							// 表示星期
		return df.format(date);

	}

	/**
	 * 时：分 : 秒
	 * 
	 * @param calendar
	 * @return
	 */
	public static String DateToString(Date date) {
		DateFormat df = new SimpleDateFormat("HH:mm:ss", java.util.Locale.CHINA); // EEE
																					// 表示星期
		return df.format(date);

	}

	/**
	 * 时：分 : 秒
	 * 
	 * @param calendar
	 * @return
	 */
	public static String ToString(String date) {
		Date date1 = null;
		if (sdf == null) {
			sdf = new SimpleDateFormat("HH:mm:ss");
		} else {
			sdf.applyPattern("HH:mm:ss");
		}
		try {
			date1 = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateFormat df = new SimpleDateFormat("HH:mm:ss", java.util.Locale.CHINA); // EEE
																					// 表示星期
		return df.format(date1);

	}

	/**
	 * 时：分
	 * 
	 * @param calendar
	 * @return
	 */
	public static String DateToMin(Date date) {
		DateFormat df = new SimpleDateFormat("HH:mm", java.util.Locale.CHINA); // EEE
																				// 表示星期
		return df.format(date);

	}

	/**
	 * 将日期类型转换为字符串的形式
	 * 
	 * @param dateFormat
	 *            转换后的格式
	 * @param date
	 *            需要转换的日期
	 * @return 转换后的字符串
	 * @throws IllegalArgumentException
	 */
	public static String dateToString(String dateFormat, Date date) throws IllegalArgumentException {
		if (dateFormat == null || dateFormat.equals("")) {
			throw new IllegalArgumentException("parameter dateFormat is not valid");
		}
		if (date == null) {
			throw new IllegalArgumentException("parameter date is not valid");
		}
		SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
		return formater.format(date);

	}

	/*
	 * 比较当前日期和指定日期 return boolean 如果当前日期在指定日期之后返回true否则返回flase
	 */
	public static boolean dateCompare(Date str) {
		boolean bea = false;
		SimpleDateFormat sdf_d = new SimpleDateFormat("yyyy-MM-dd");
		String isDate = sdf_d.format(new Date());
		Date date0;
		try {
			date0 = sdf_d.parse(isDate);
			if (date0.after(str)) {
				bea = true;
			}
		} catch (ParseException e) {
			bea = false;
		}
		return bea;
	}

	public static void main(String[] args) {
		try {
			System.out.println(TimeUtil.stringToDate(TimeUtil.FORMART2, dateToString(TimeUtil.FORMART2, new Date())));

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 获取当前时间
	public static String getCurrentTime() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date submitTime = new Date(System.currentTimeMillis());// 获取当前时间
		String strSubmitTime = formatter.format(submitTime);

		return strSubmitTime;

	}

	/**
	 * 获得当前的时间(yyyyMMddHHmmss)
	 * 
	 * @return 返回当前时间
	 */
	public static String getCurrentDateAccurateToSecond_() {
		return getCurrentDate("yyyyMMddHHmmss");
	}

	/**
	 * 返回指定格式的当天日期 例如：getCurrentDate("yyyy-MM-dd");
	 * 
	 * @param pattern
	 *            时间样式
	 * @return 返回指定格式的当天日期
	 */
	public static String getCurrentDate(String pattern) {
		return dateToString(new Date(), pattern);
	}

	/**
	 * Date转换为String
	 * 
	 * @param date
	 * @param pattern
	 * @return 字符串格式日期
	 */
	public static String dateToString(Date date, String pattern) {
		if (sdf == null) {
			sdf = new SimpleDateFormat(pattern);
		} else {
			sdf.applyPattern(pattern);
		}
		return sdf.format(date);
	}

	/**
	 * 将date类型数据转换成自己需要的字符串
	 * 
	 * @param date
	 *            需要转换的时间
	 */
	public static String getDateToString(Date date, String formart) {
		DateFormat df = new SimpleDateFormat(formart, java.util.Locale.CHINA);
		try {
			return df.format(date);
		} catch (Exception e) {
			return "";
		}
	}
}
