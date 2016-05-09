/*****************************
 * Copyright (c) 2011 by ZbxSoft Co. Ltd.  All rights reserved.
 ****************************/
package com.dkd.common.utils;

import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

public class DateUtils {

	private static String defaultDatePattern = "yyyy-MM-dd";
	public static final long oneDayMillSeconds = 24 * 60 * 60 * 1000;/** 1天对应的毫秒数 */

	/**
	 * 获得默认的 date pattern
	 * 
	 * @return String  一个 yyyy-mm-dd 形式的 String
	 */
	public static String getDatePattern() {
		return defaultDatePattern;
	}
	
	public static String getNowTime(){
		return format(new Date(), DateUtils.FmtStr.yyyyMMdd_HHmmss.toString());
	}

	/**
	 * 返回预设Format的当前日期字符串
	 * 
	 * @return String 把当前日期格式化为"yyyy-MM-dd"形式的字符串     
	 */
	public static String getToday() {
		Date today = new Date();
		return format(today);
	}

	/**
	 * 使用预设Format格式化Date成字符串
	 * 
	 * @param date    给定日期
	 * @return String 将给定日期格式化为"yyyy-MM-dd"形式的字符串
	 */
	public static String format(Date date) {
		return date == null ? "" : format(date, getDatePattern());
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 * 
	 * @param date    给定日期
	 * @param pattern 给定的格式化字符串
	 * @return String 将给定的日期按照pattern进行格式化，并返回格式化好的日期字符串。
	 */
	public static String format(Date date, String pattern) {
		return date == null ? "" : new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 使用预设格式将字符串转为Date
	 * 
	 * @param strDate 给定的字符串
	 * @return Date   将给定的字符串格式化为"yyyy-MM-dd"的日期类型返回
	 */
	public static Date parse(String strDate) throws ParseException {
		return StrUtil.isNull(strDate) ? null : parse(strDate,
				getDatePattern());
	}

	/**
	 * 使用参数Format将字符串转为Date
	 * 
	 * @param strDate 给定的字符串
	 * @param pattern 给定的格式化格式
	 * @return Date   将给定的字符串按照给定的格式格式化成日期类型返回
	 */
	public static Date parse(String strDate, String pattern)
			throws ParseException {
		return StrUtil.isNull(strDate) ? null : new SimpleDateFormat(
				pattern).parse(strDate);
	}

	/**
	 * 根据年月日获得指定的日期
	 * 
	 * @param year  给定的年份
	 * @param month 给定的月份
	 * @param day   给定的日期
	 * @return Date 根据给定的年月日返回指定的日期
	 */
	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day, 0, 0, 0);
		return cal.getTime();
	}

	/**
	 * 判断给定日期是否为当月的最后一天
	 * 
	 * @param date     给定日期
	 * @return boolean 为true表示该日期为当月最后一天，为false表示该日期不是当月的最后一天。
	 */
	public static boolean isEndOfTheMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return cal.get(Calendar.DATE) == maxDay;
	}

	/**
	 * 判断给定日期是否为当年的最后一天
	 * 
	 * @param date     给定的日期
	 * @return boolean 为true表示该日期为当年最后一天，为false表示该日期不是当年最后一天
	 */
	public static boolean isEndOfTheYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return (11 == cal.get(Calendar.MONTH))
				&& (31 == cal.get(Calendar.DATE));
	}

	/**
	 * 获得给定日期的月份的最后一天
	 * 
	 * @param date 给定的日期
	 * @return int 给定日期月份的最后一天
	 */
	public static int getLastDayOfTheMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 判断开始日期是否比结束日期早
	 * 
	 * @param startTime 给定的开始时间
	 * @param endTime   给定的结束时间
	 * @return boolean  为true表示开始时间比结束时间早，为false表示开始时间比结束时间晚。
	 */
	public static boolean isStartBeforeEndTime(Date startTime, Date endTime) {
		Assert.notNull(startTime, "StartTime is null");
		Assert.notNull(endTime, "EndTime is null");
		return startTime.getTime() < endTime.getTime();
	}

	/**
	 * 比较两个日期相差天数
	 * 
	 * @param startTime 给定的开始时间
	 * @param endTime   给定的结束时间
	 * @return long     给定的开始时间和给定的结束时间相差天数，返回long型值。
	 */
	public static long comparisonDifferenceDays(Date startTime, Date endTime)
			throws ParseException {
		Assert.notNull(startTime, "StartTime is null");
		Assert.notNull(endTime, "EndTime is null");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		long timethis = calendar.getTimeInMillis();
		calendar.setTime(endTime);
		long timeend = calendar.getTimeInMillis();
		long theday = (timeend - timethis) / (1000 * 60 * 60 * 24);
		return theday;
	}

	/**
	 * 判断给定日期是否为对应日期月份的第一天
	 * 
	 * @param date     给定日期
	 * @return boolean 为true表示给定日期是对应日期月份的第一天，为false表示给定日期不是对应日期的第一天。
	 */
	public static boolean isStartOfTheMonth(Date date) {
		Assert.notNull(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return 1 == cal.get(Calendar.DATE);
	}

	/**
	 * 判断给定日期是否为对应日期年份的第一天
	 * 
	 * @param date     给定日期
	 * @return boolean 为true表示给定日期是对应日期年份的第一天，为false表示给定日期不是对应日期年份的第一天。
	 */
	public static boolean isStartOfTheYear(Date date) {
		Assert.notNull(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return (1 == cal.get(Calendar.MONTH)) && (1 == cal.get(Calendar.DATE));
	}

	/**
	 * 获取给定日期的月份
	 * 
	 * @param  date 给定日期
	 * @return int  给定日期的月份
	 */
	public static int getMonth(Date date) {
		Assert.notNull(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	/**
	 * 获取给定日期的年份
	 * 
	 * @param  date 给定日期
	 * @return int  给定日期的年份
	 */
	public static int getYear(Date date) {
		Assert.notNull(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 获取不含不含小时分钟秒的系统日期
	 * 
	 * @return Date 系统当前日期，不含小时分钟秒。
	 */
	public static Date getSystemDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(cal.getTime().getTime());
	}

	/**
	 * 获取系统的 Timestamp
	 * 
	 * @return Timestamp 系统当前时间的时间戳
	 */
	public static Timestamp getSystemTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 日志格式类，包含常用的日期时间格式
	 * 
	 * @author yangyamin 
	 */
	public static class FmtStr {
		private String fmtStr;

		private FmtStr(String str) {
			this.fmtStr = str;
		}

		public String toString() {
			return this.fmtStr;
		}

		public static FmtStr yyyy = new FmtStr("yyyy");
		public static FmtStr yyyyMM = new FmtStr("yyyy-MM");
		public static FmtStr yyyyMMdd = new FmtStr("yyyy-MM-dd");
		public static FmtStr yMd = new FmtStr("yyyyMMdd");
		public static FmtStr yyyyMMdd_HH = new FmtStr("yyyy-MM-dd HH");
		public static FmtStr yyyyMMdd_HHmm = new FmtStr("yyyy-MM-dd HH:mm");
		public static FmtStr yyyyMMdd_HHmmss = new FmtStr("yyyy-MM-dd HH:mm:ss");
		public static FmtStr yyyyMMdd_HHmmssSSS = new FmtStr("yyyy-MM-dd HH:mm:ss:SSS");

		public static FmtStr HHmm = new FmtStr("HH:mm");
		public static FmtStr HHmmss = new FmtStr("HH:mm:ss");
		public static FmtStr Hms = new FmtStr("HHmmss");
		public static FmtStr hhmmssSSS = new FmtStr("HH:mm:ss:SSS");

		public static FmtStr CN_yyyyMMdd = new FmtStr("yyyy年MM月dd日");/** 中文格式：yyyy年MM月dd日 */
		public static FmtStr CN_HHmmss = new FmtStr("HH时mm分ss秒");/** 中文格式：HH时mm分ss秒 */
		public static FmtStr CN_yyyyMMdd_HHmmss = new FmtStr("yyyy年MM月dd日 HH时mm分ss秒");/** 中文格式：yyyy年MM月dd日 HH时mm分ss秒 */
	}

	/**
	 * 由某个日期，前推若干毫秒
	 * 
	 * @param date        给定的日期
	 * @param millSeconds 给定前推的秒数
	 * @return Date       将给定的日期前推给定的秒数后的日期
	 */
	public static Date before(Date date, long millSeconds) {
		return fromLong(date.getTime() - millSeconds);
	}
	

	/**
	 * 由某个日期，后推若干毫秒
	 * 
	 * @param date        给定的日期
	 * @param millSeconds 给定后推的秒数
	 * @return Date       将给定的日期后推给定的秒数后的日期
	 */
	public static Date after(Date date, long millSeconds) {
		return fromLong(date.getTime() + millSeconds);
	}
	
	/**
	 * 得到某个日期之后n天后的日期
	 * 
	 * @param date  给定日期
	 * @param nday  给定天数
	 * @return Date 给定日期n天后的日期
	 */
	public static Date after(Date date, int nday) {
		return fromLong(date.getTime() + nday * oneDayMillSeconds);
	}
	
	
	/**
	 * 得到当前日期之后n天后的日期
	 * 
	 * @param n     给定天数
	 * @return Date 当前日期n天后的日期
	 */
	public static Date afterNDays(int n) {
		return after(getDate(), n * oneDayMillSeconds);
	}

	/**
	 * 得到当前日期n天前的日期
	 * 
	 * @param n     给定天数
	 * @return Date 当前日期n天数的日期
	 */
	public static Date beforeNDays(int n) {
		return beforeNDays(getDate(), n );
		
	}

    /**
	 * 得到某个日期n天前的日期
	 * 
	 * @param date  给定日期
	 * @param n     给定天数
	 * @return Date 给定日期n天前的日期
	 */
	public static Date beforeNDays(Date date, int n) {
		return fromLong(date.getTime() - n * oneDayMillSeconds);
	}


	/**
	 * 昨天
	 * 
	 * @return Date 昨天
	 */
	public static Date yesterday() {
		return before(getDate(), oneDayMillSeconds);
	}

	/**
	 * 明天
	 * 
	 * @return Date 明天
	 */
	public static Date tomorrow() {
		return after(getDate(), oneDayMillSeconds);
	}

	public static long getA_B(Date dateA, Date dateB) {
		return dateA.getTime() - dateB.getTime();
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @return Date 当前系统时间
	 */
	public static Date getDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 得到一个日期的毫秒表达
	 * 
	 * @param  date 给定日期
	 * @return long 给定日期的毫秒表达值（Long型）
	 */
	public static long toLong(Date date) {
		return date.getTime();
	}

	/**
	 * 将毫秒的日期数值转化为Date对象
	 * 
	 * @param  time 给定毫秒值
	 * @return Date 把给定的time转换成日期类型
	 */
	public static Date fromLong(long time) {
		Date date = getDate();
		date.setTime(time);

		return date;
	}

	/**
	 * 根据某个字符串得到日期对象
	 * 
	 * @param  dateStr 给定的日期字符串
	 * @param  fmtStr  给定的日期格式类
	 * @return Date    根据日期格式类fmtStr把字符串dateStr转换成日期类型
	 */
	public static Date strToDate(String dateStr, FmtStr fmtStr) {
		DateFormat df = new SimpleDateFormat(fmtStr.toString());
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 根据某个字符串得到日期对象
	 * 
	 * @param  dateStr 给定的日期字符串
	 * @param  fmtStr  给定的字符串格式
	 * @return Date    把dateStr日期字符串格式成fmtStr的日期类型
	 */
	public static Date strToDate(String dateStr, String fmtStr) {
		DateFormat df = new SimpleDateFormat(fmtStr);
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 将毫秒数值日期转化为字符串日期
	 * 
	 * @param time    毫秒数
	 * @param fmtStr  日期格式类
	 * @return String 将参数time转换成格式为fmtStr的字符串日期
	 */
	public static String longToStr(long time, FmtStr fmtStr) {

		return format(fromLong(time), fmtStr.toString());
	}

	/**
	 * 将字符串日期转化为毫秒的数值日期
	 * 
	 * @param dateStr 字符串日期
	 * @param fmtStr  日期格式类
	 * @return long   将字符串日期dateStr转换成毫秒的数值日期
	 */
	public static long strToLong(String dateStr, FmtStr fmtStr) {
		return strToDate(dateStr, fmtStr).getTime();
	}

	/**
	 * 当前时间对象
	 * 
	 * @author yangyamin
	 * 
	 */
	public static class Now {
		public static String fmtNow(FmtStr fmtStr) {
			return format(getDate(), fmtStr.toString());
		}

		/**
		 * 将当前时间转为毫秒数
		 * 
		 * @return long 当前时间的毫秒数
		 */
		public static long toLong() {
			return getDate().getTime();
		}

		/**
		 * 得到当前时间的字符串表达，格式：yyyy
		 * 
		 * @return String 当前时间格式为yyyy的字符串
		 */
		public static String fmt_yyyy() {
			return fmtNow(FmtStr.yyyy);
		}

		/**
		 * 得到当前时间的字符串表达，格式：yyyy-MM
		 * 
		 * @return String 当前时间的字符串表达，格式：yyyy-MM
		 */
		public static String fmt_yyyyMM() {
			return fmtNow(FmtStr.yyyyMM);
		}

		/**
		 * 得到当前时间的字符串表达，格式：yyyy-MM-dd
		 * 
		 * @return String 当前时间的字符串表达，格式：yyyy-MM-dd
		 */
		public static String fmt_yyyyMMdd() {
			return fmtNow(FmtStr.yyyyMMdd);
		}
		/**
		 * 得到当前时间的字符串表达，格式：yyyyMMdd
		 * 
		 * @return String 当前时间的字符串表达，格式：yyyy-MM-dd
		 */
		public static String fmt_yMd() {
			return fmtNow(FmtStr.yMd);
		}

		/**
		 * 得到当前时间的字符串表达，格式：yyyy-MM-dd HH
		 * 
		 * @return String 当前时间的字符串表达，格式：yyyy-MM-dd HH
		 */
		public static String fmt_yyyyMMdd_HH() {
			return fmtNow(FmtStr.yyyyMMdd_HH);
		}

		/**
		 * 得到当前时间的字符串表达，格式：yyyy-MM-dd HH:mm
		 * 
		 * @return String 当前时间的字符串表达，格式：yyyy-MM-dd HH:mm
		 */ 
		public static String fmt_yyyyMMdd_HHmm() {
			return fmtNow(FmtStr.yyyyMMdd_HHmm);
		}

		/**
		 * 得到当前时间的字符串表达，格式：yyyy-MM-dd HH:mm:ss
		 * 
		 * @return String 当前时间的字符串表达，格式：yyyy-MM-dd HH:mm:ss
		 */
		public static String fmt_yyyyMMdd_HHmmss() {
			return fmtNow(FmtStr.yyyyMMdd_HHmmss);
		}

		/**
		 * 得到当前时间的字符串表达，格式：yyyy-MM-dd HH:mm:ss:SSS
		 * 
		 * @return String 当前时间的字符串表达，格式：yyyy-MM-dd HH:mm:ss:SSS
		 */
		public static String fmt_yyyyMMdd_HHmmssSSS() {
			return fmtNow(FmtStr.yyyyMMdd_HHmmssSSS);
		}

		/**
		 * 得到当前时间的字符串表达，格式：HH:mm
		 * 
		 * @return String 当前时间的字符串表达，格式：HH:mm
		 */
		public static String fmt_HHmm() {
			return fmtNow(FmtStr.HHmm);
		}

		/**
		 * 得到当前时间的字符串表达，格式：HH:mm:ss
		 * 
		 * @return String 当前时间的字符串表达，格式：HH:mm:ss
		 */
		public static String fmt_HHmmss() {
			return fmtNow(FmtStr.HHmmss);
		}
		/**
		 * 得到当前时间的字符串表达，格式：HHmmss
		 * 
		 * @return String 当前时间的字符串表达，格式：HH:mm:ss
		 */
		public static String fmt_Hms() {
			return fmtNow(FmtStr.Hms);
		}

		/**
		 * 得到当前时间的字符串表达，格式：HH:mm:ss:SSS
		 * 
		 * @return String 得到当前时间的字符串表达，格式：HH:mm:ss:SSS
		 */
		public static String fmt_HHmmssSSS() {
			return fmtNow(FmtStr.hhmmssSSS);
		}

		/**
		 * 得到当前时间的字符串表达，格式：yyyy年MM月dd日 HH时mm分ss秒
		 * 
		 * @return String 得到当前时间的字符串表达，格式：yyyy年MM月dd日 HH时mm分ss秒
		 */
		public static String fmt_CN_yyyyMMdd_HHmmss() {
			return fmtNow(FmtStr.CN_yyyyMMdd_HHmmss);
		}

		/**
		 * 得到当前时间的字符串表达，格式：yyyy年MM月dd日
		 * 
		 * @return String 得到当前时间的字符串表达，格式：yyyy年MM月dd日
		 */
		public static String fmt_CN_yyyyMMdd() {
			return fmtNow(FmtStr.CN_yyyyMMdd);
		}

		/**
		 * 得到当前时间的字符串表达，格式：HH时mm分ss秒
		 * 
		 * @return String 得到当前时间的字符串表达，格式：HH时mm分ss秒
		 */
		public static String fmt_CN_HHmmss() {
			return fmtNow(FmtStr.CN_HHmmss);
		}
	}

	/**
	 * 得到环境变量中操作系统时区，即得到系统属性：user.timezone
	 * 
	 * @return String 得到环境变量中操作系统时区，即得到系统属性：user.timezone
	 */
	public static String getTimeZoneOfSystem() {
		Properties sysProp = new Properties(System.getProperties());
		String sysTimeZone = sysProp.getProperty("user.timezone");
		return sysTimeZone;
	}

	/**
	 * 得到jvm中系统时区
	 * 
	 * @return String 得到jvm中系统时区
	 */
	public static String getTimeZoneOfJVM() {
		String jvmTimeZone = TimeZone.getDefault().getID();
		return jvmTimeZone;
	}

	/**
	 * 检验当前操作系统时区是否正确。<br>
	 * 判断依据：操作系统环境变量的时区和jvm得到的时区是否一致，一致则表明正确，否则错误。
	 * 
	 * @return boolean true:正确；false:错误
	 */
	public static boolean checkTimeZone() {
		String sysTimeZone = getTimeZoneOfSystem();
		String jvmTimeZone = getTimeZoneOfJVM();
		return sysTimeZone == null ? false : sysTimeZone.equals(jvmTimeZone);
	}
	
	public static void main(String[] args) throws Exception{
//		String str = getPreOrNextTime(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), 20+"", "sub");
//		System.out.println(str);
//		int startMin = 9*60;
//		int endMin = 24*60;
//		String[] ary = getCurTimeTime(10,startMin,endMin);
//		System.out.println(ary[0]+"     "+ary[1]);
		//System.out.println(DateUtils.getDate());
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		System.out.println(cal.getTime().getTime());
		System.out.println(DateUtils.toLong(DateUtils.getSystemDate()));
	}
	
	/**
	 * 指定时向前推几分钟或向后推几分钟
	 * @param sj1
	 * @param jj
	 * @param addOrSub
	 * @return
	 */
	public static String getPreOrNextTime(String sj1, String jj, String addOrSub) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = 1l;
			if(addOrSub.equals("sub")){
				Time = (date1.getTime() / 1000) - Integer.parseInt(jj) * 60;
			}else{
				Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			}
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mydate1;
	}
	
	/**
	 * 计算当前时间按min时间计算往前推的时间段
	 * @param min 时间偏移量
	 * @return 数组，开始时间，结束时间
	 */
	public static String[] getCurTimeAtTime(int min,String aa){
		String[] times = new String[2];
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY); // 当前小时
		int hourToMinute = hour*60;// 当前小时转成分钟
		int minute = calendar.get(Calendar.MINUTE);
		int curDateTime = hourToMinute+minute; // 当前小时与分钟的整合
		
		int st = ConfigUtils.readConfigInt("startTime")*60; // 初始化早上开市时间
		int ed = ConfigUtils.readConfigInt("endTime")*60; // 初始化晚上毕市时间
		
		int mide = ed-st;
		int duan = mide/min;
		int preDuan = 0;
		
		for(int i=1; i<=duan; i++){
			int s = i*min+540;
			int e = (i+1)*min+540;
			if(curDateTime >= s && curDateTime < e){
				preDuan = i-1;
				break;
			}
		}
		for(int i=1; i<=duan; i++){
			int s = i*min+540;
			int e = (i+1)*min+540;
			if(i==preDuan){
				Date dd = DateUtils.getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				long startTime = dd.getTime()+(s*60*1000);
				long endTime = dd.getTime()+(e*60*1000);
				times[0] = DateUtils.format(new Date(startTime), "yyyy-MM-dd HH:mm:ss");
				times[0] = times[0].substring(0,times[0].length()-1);
				times[0] = times[0]+"1";
				times[1] = DateUtils.format(new Date(endTime), "yyyy-MM-dd HH:mm:ss");
				break;
			}
		}
		if(preDuan==0){
			times[0] = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
			times[0] = times[0].substring(0,times[0].length()-1);
			times[0] = times[0]+"1";
			times[1] = DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		}
		return times;
	}
	
	/**
	 * 计算当前时间按min时间计算往后推的时间段
	 * @param min 时间偏移量
	 * @return 数组，开始时间，结束时间
	 */
	public static String[] getCurTimeAtNextTime(int min, int startMin, int endMin){
		String[] times = new String[2];
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY); // 当前小时
		int hourToMinute = hour*60;// 当前小时转成分钟
		int minute = calendar.get(Calendar.MINUTE);
		int curDateTime = hourToMinute+minute+1; // 当前小时与分钟的整合
		
		int st = startMin; // 初始化早上开市时间
		int ed = endMin; // 初始化晚上毕市时间
		
		int mide = ed-st;
		int duan = mide/min;
		int preDuan = 0;
		
		for(int i=1; i<=duan; i++){
			int s = i*min+st;
			int e = (i+1)*min+st;
			if(curDateTime > s && curDateTime <= e){
				preDuan = i+1;
				break;
			}
		}
		if(preDuan>0){
			for(int i=1; i<=duan; i++){
				int s = i*min+st;
				int e = (i+1)*min+st;
				if(i==preDuan){
					Date dd = DateUtils.getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
					long startTime = dd.getTime()+(s*60*1000);
					long endTime = dd.getTime()+(e*60*1000);
					times[0] = DateUtils.format(new Date(startTime), "yyyy-MM-dd HH:mm:ss");
					times[0] = times[0].substring(0,times[0].length()-1);
					times[0] = times[0]+"1";
					times[1] = DateUtils.format(new Date(endTime), "yyyy-MM-dd HH:mm:ss");
					break;
				}
			}
		}else{
			int s = st;
			int e = min+st;
			Date dd = DateUtils.getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
			long startTime = dd.getTime()+(s*60*1000);
			long endTime = dd.getTime()+(e*60*1000);
			times[0] = DateUtils.format(new Date(startTime), "yyyy-MM-dd HH:mm:ss");
			times[0] = times[0].substring(0,times[0].length()-1);
			times[0] = times[0]+"1";
			times[1] = DateUtils.format(new Date(endTime), "yyyy-MM-dd HH:mm:ss");
		}
		return times;
	}
	
	/**
	 * 计算当前时间按min时间计算往前推的时间段
	 * @param min 时间偏移量
	 * @return 数组，开始时间，结束时间
	 */
	public static String[] getCurTimeAtPrevTime(int min, int startMin, int endMin){
		String[] times = new String[2];
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY); // 当前小时
		int hourToMinute = hour*60;// 当前小时转成分钟
		int minute = calendar.get(Calendar.MINUTE);
		int curDateTime = hourToMinute+minute+1; // 当前小时与分钟的整合
		
		int st = startMin; // 初始化早上开市时间
		int ed = endMin; // 初始化晚上毕市时间
		
		int mide = ed-st;
		int duan = mide/min;
		int preDuan = 0;
		
		for(int i=1; i<=duan; i++){
			int s = i*min+st;
			int e = (i+1)*min+st;
			if(curDateTime > s && curDateTime <= e){
				preDuan = i-1;
				break;
			}
		}
		if(preDuan>0){
			for(int i=1; i<=duan; i++){
				int s = i*min+st;
				int e = (i+1)*min+st;
				if(i==preDuan){
					Date dd = DateUtils.getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
					long startTime = dd.getTime()+(s*60*1000);
					long endTime = dd.getTime()+(e*60*1000);
					times[0] = DateUtils.format(new Date(startTime), "yyyy-MM-dd HH:mm:ss");
					times[0] = times[0].substring(0,times[0].length()-1);
					times[0] = times[0]+"1";
					times[1] = DateUtils.format(new Date(endTime), "yyyy-MM-dd HH:mm:ss");
					break;
				}
			}
		}else{
			int s = st;
			int e = min+st;
			Date dd = DateUtils.getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
			long startTime = dd.getTime()+(s*60*1000);
			long endTime = dd.getTime()+(e*60*1000);
			times[0] = DateUtils.format(new Date(startTime), "yyyy-MM-dd HH:mm:ss");
			times[0] = times[0].substring(0,times[0].length()-1);
			times[0] = times[0]+"1";
			times[1] = DateUtils.format(new Date(endTime), "yyyy-MM-dd HH:mm:ss");
		}
		return times;
	}
	
	/**
	 * 计算当前时间按min时间计算当前的时间段
	 * @param min 时间偏移量
	 * @return 数组，开始时间，结束时间
	 */
	public static String[] getCurTimeTime(int min, int startMin, int endMin){
		String[] times = new String[2];
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY); // 当前小时
		int hourToMinute = hour*60;// 当前小时转成分钟
		int minute = calendar.get(Calendar.MINUTE);
		int curDateTime = hourToMinute+minute+1; // 当前小时与分钟的整合
		
		int st = startMin; // 初始化早上开市时间
		int ed = endMin; // 初始化晚上毕市时间
		
		int mide = ed-st;
		int duan = mide/min;
		int preDuan = 0;
		
		for(int i=1; i<=duan; i++){
			int s = i*min+st;
			int e = (i+1)*min+st;
			if(curDateTime > s && curDateTime <= e){
				preDuan = i;
				break;
			}
		}
		if(preDuan>0){
			for(int i=1; i<=duan; i++){
				int s = i*min+st;
				int e = (i+1)*min+st;
				if(i==preDuan){
					Date dd = DateUtils.getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
					long startTime = dd.getTime()+(s*60*1000);
					long endTime = dd.getTime()+(e*60*1000);
					times[0] = DateUtils.format(new Date(startTime), "yyyy-MM-dd HH:mm:ss");
					times[0] = times[0].substring(0,times[0].length()-1);
					times[0] = times[0]+"1";
					times[1] = DateUtils.format(new Date(endTime), "yyyy-MM-dd HH:mm:ss");
					break;
				}
			}
		}else{
			int s = st;
			int e = min+st;
			Date dd = DateUtils.getDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
			long startTime = dd.getTime()+(s*60*1000);
			long endTime = dd.getTime()+(e*60*1000);
			times[0] = DateUtils.format(new Date(startTime), "yyyy-MM-dd HH:mm:ss");
			times[0] = times[0].substring(0,times[0].length()-1);
			times[0] = times[0]+"1";
			times[1] = DateUtils.format(new Date(endTime), "yyyy-MM-dd HH:mm:ss");
		}
		return times;
	}
	
	/**
	 * 截取时间字符串；得到13:30类似数据
	 * @param dateTime 参数个数2012-02-28 13:30:23
	 * @return
	 */
	public static String getHhmm(String dateTime){
		return dateTime.substring(11, 16);
	}
	
	/**
	 * 截取时间字符串，获得月日 02-28
	 * @param dateTime 参数格式： 2012-02-28
	 * @return
	 */
	public static String getMmdd(String dateTime){
		return dateTime.substring(5, 10);
	}
	
	/**
	 * 获得今天日期不含时间的毫秒数
	 * @return
	 */
	public static long getDateLong(){
		try {
			Date d = DateUtils.parse(DateUtils.getToday());
			return d.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 返回两位年两位月
	 * @return
	 */
	public static String getYear2Month2(){
		return DateUtils.format(new Date(), "yyMM");
	}
	
	/**
	 * 截取时间字符串；得到13:30类似数据
	 * @param dateTime 参数个数2012-02-28 13:30:23
	 * @return
	 */
	public static String getDd(String dateTime){
		return dateTime.substring(11, 16);
	}
	
	/**
	 * 获得散户系统时间
	 * @param httpUrl
	 * @return
	 * @throws Exception
	 */
	public static String getWebContent() throws Exception{
		InputStream inputStream = null;
		//"http://localhost:8090/user/pages/index-get-time"
		URL url = new URL(ConfigUtils.readConfigString("getTime_url"));
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(10*1000);
		if(conn.getResponseCode() == 200){
			inputStream = conn.getInputStream();
			InputStreamReader in = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(in);
			String tempStr = br.readLine();
			br.close();
			in.close();
			inputStream.close();
			return tempStr;
		}else{
			return null;
		}
	}
	public static String getShortTime(String time) {
		String shortstring = null;
		long now = Calendar.getInstance().getTimeInMillis();
		Date date = getDateByString(time);
		if (date == null)
			return getShortDateByString(time);
		long deltime = (now - date.getTime()) / 1000;
		if (deltime >= 3 * 24 * 60 * 60) {
			return getShortDateByString(time);
		} else if (deltime > 24 * 60 * 60 && deltime < 3 * 24 * 60 * 60) {
			shortstring = (int) (deltime / (24 * 60 * 60)) + "天前";
		} else if (deltime > 60 * 60 && deltime < 24 * 60 * 60) {
			shortstring = (int) (deltime / (60 * 60)) + "小时前";
		} else if (deltime > 60) {
			shortstring = (int) (deltime / (60)) + "分前";
		} else if (deltime > 1) {
			shortstring = deltime + "秒前";
		} else {
			shortstring = "1秒前";
		}
		return shortstring;
	}

	public static Date getDateByString(String time) {
		Date date = null;
		if (time == null)
			return date;
		String date_format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat format = new SimpleDateFormat(date_format);
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getShortDateByString(String time) {
		Date date = null;
		if (time == null)
			return "-";
		String date_format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat format = new SimpleDateFormat(date_format);

		String year = "";
		try {
			date = format.parse(time);

			//String localDate = date.toLocaleString();
			String localDate = time;	
			year = localDate.substring(0, localDate.indexOf("-"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return year + "-" + (date.getMonth() + 1) + "-" + date.getDate();
	}
}