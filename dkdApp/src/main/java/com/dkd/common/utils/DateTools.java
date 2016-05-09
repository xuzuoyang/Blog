package com.dkd.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间帮助类
 * @author xiaoyaoyao
 * @version [版本号, 2011-06-08]
 */
public final class DateTools extends SimpleDateFormat
{
    /**
	 * 注释内容
	 */
	private static final long serialVersionUID = -2987750868895651661L;

	/**
     * 时间格式为yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 时间格式为yyyy-MM-dd HH:mm:ss.sss
     */
    public static final String YYYY_MM_DD_HH_MM_SS_S = "yyyy-MM-dd HH:mm:ss.SSS";
    
    /**
     * 时间格式为yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    
    /**
     * 时间格式为yyyyMMddHHmmss
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    
    /**
     * 时间格式为yyyyMMddHHmm
     */
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    
    /**
     * 时间格式为yyyyMMdd
     */
    public static final String YYYYMMDD = "yyyyMMdd";
    
    /**
     * 时间格式为ddHHmmss
     */
    public static final String DDHHMMSS = "ddHHmmss";
    
    /**
     * 时间格式为HHmmss
     */
    public static final String HHMMSS = "ddHHmmss";
    
    /**
     * 时间格式为yyyy-MM-dd HH:mm
     */
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    
    /**
     * 时间格式为yyyy-MM-dd HH
     */
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    
    /**
     * DATETOOL
     */
    private static DateTools dateTools = null;
    
    /**
     * 日历类
     */
    private final Calendar calendar = Calendar.getInstance();
    
    /**
     * <默认私有构造函数>
     */
    private DateTools()
    {
        super(YYYY_MM_DD_HH_MM_SS);
    }
    
    /** 
     * <默认构造函数>
     * @param mode 模态
     */
    private DateTools(String mode)
    {
        super(mode);
    }
    
    /**
     * 此类构造的日期工具类不可以格式化日期
     * @return DateTools
     */
    public static DateTools getDateTools()
    {
        if (null == dateTools)
        {
            dateTools = new DateTools();
        }
        return dateTools;
    }
    
    /**
     * 此类构造的日期工具类可以格式化日期
     * @param mode mode模式 以哪种时间模式去创建或转换
     * @return DateTools
     */
    public static DateTools getDateTools(String mode)
    {
        if (null == dateTools)
        {
            dateTools = new DateTools(mode);
        }
        return dateTools;
    }
    
    /**
     * 得到会传入日期的月份
     * @param date 日期
     * @return int
     * @throws ParseException ParseException
     */
    public int getMonth(Date date) throws ParseException
    {
        setCalendar(date);
        return calendar.get(Calendar.MONTH) + 1;
    }
    
    /**
     * 得到会传入日期的年份
     * @param date 日期
     * @return int
     * @throws ParseException ParseException
     */
    public int getYear(Date date) throws ParseException
    {
        setCalendar(date);
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * 得到会传入日期的是日期所在月的哪一天
     * @param date 日期
     * @return int
     * @throws ParseException ParseException
     */
    public int getDate(Date date) throws ParseException
    {
        setCalendar(date);
        return calendar.get(Calendar.DATE) + 1;
    }
    
    /**
     * 得到会传入日期的分钟
     * @param date 日期
     * @return int
     * @throws ParseException ParseException
     */
    public int getMinute(Date date) throws ParseException
    {
        setCalendar(date);
        return calendar.get(Calendar.MINUTE);
    }
    
    /**
     * 得到会传入日期的小时
     * @param date 日期
     * @param isStandard 是否是24小时制的,true:是
     * @return int
     * @throws ParseException ParseException
     */
    public int getHour(Date date, boolean isStandard) throws ParseException
    {
        setCalendar(date);
        if (isStandard)
        {
            return calendar.get(Calendar.HOUR_OF_DAY);
        }
        else
        {
            return calendar.get(Calendar.HOUR);
        }
    }
    
    /**
     * 得到会传入日期的小时
     * @param date 日期
     * @return int
     * @throws ParseException ParseException
     */
    public int getSecond(Date date) throws ParseException
    {
        setCalendar(date);
        return calendar.get(Calendar.SECOND);
    }
    
    /**
     * 得到会传入日期的月份
     * @param strDate 字符串日期
     * @return int
     * @throws ParseException ParseException
     */
    public int getMonth(String strDate) throws ParseException
    {
        setCalendar(strDate);
        return calendar.get(Calendar.MONTH) + 1;
    }
    
    /**
     * 得到会传入日期的年份
     * @param strDate 字符串日期
     * @return int
     * @throws ParseException ParseException
     */
    public int getYear(String strDate) throws ParseException
    {
        setCalendar(strDate);
        return calendar.get(Calendar.YEAR);
    }
    
    /**
     * 得到会传入日期的是日期所在月的哪一天
     * @param strDate 字符串日期
     * @return int
     * @throws ParseException ParseException
     */
    public int getDate(String strDate) throws ParseException
    {
        setCalendar(strDate);
        return calendar.get(Calendar.DATE) + 1;
    }
    
    /**
     * 得到会传入日期的分钟
     * @param strDate 字符串日期
     * @return int
     * @throws ParseException ParseException
     */
    public int getMinute(String strDate) throws ParseException
    {
        setCalendar(strDate);
        return calendar.get(Calendar.MINUTE);
    }
    
    /**
     * 得到会传入日期的小时
     * @param strDate 字符串日期
     * @param isStandard 是否是24小时制的,true:是
     * @return int
     * @throws ParseException ParseException
     */
    public int getHour(String strDate, boolean isStandard)
            throws ParseException
    {
        setCalendar(strDate);
        if (isStandard)
        {
            return calendar.get(Calendar.HOUR_OF_DAY);
        }
        else
        {
            return calendar.get(Calendar.HOUR);
        }
    }
    
    /**
     * 得到会传入日期的小时
     * @param strDate 字符串日期
     * @return int
     * @throws ParseException ParseException
     */
    public int getSecond(String strDate) throws ParseException
    {
        setCalendar(strDate);
        return calendar.get(Calendar.SECOND);
    }
    
    /**
     * 在原有的时间小时上加上或减去多少
     * @param date 原有时间
     * @param levevHour 要加减的小时数
     * @param isStandard 是还是24小时制
     * @return Date
     */
    public Date operationHour(Date date, int levevHour, boolean isStandard)
    {
        setCalendar(date);
        if (isStandard)
        {
            calendar.add(Calendar.HOUR_OF_DAY, levevHour);
        }
        else
        {
            calendar.add(Calendar.HOUR, levevHour);
        }
        return calendar.getTime();
    }
    
    /**
     * 在原有的时间分钟上加上或减去多少
     * @param date 原有时间
     * @param levevMinute 要加减的分钟数
     * @return Date
     */
    public Date operationMinute(Date date, int levevMinute)
    {
        setCalendar(date);
        calendar.add(Calendar.MINUTE, levevMinute);
        return calendar.getTime();
    }
    
    /**
     * 在原有的时间年份上加上或减去多少
     * @param date 原有时间
     * @param levevYear 要加减的年份数
     * @return Date
     */
    public Date operationYear(Date date, int levevYear)
    {
        setCalendar(date);
        calendar.add(Calendar.YEAR, levevYear);
        return calendar.getTime();
    }
    
    /**
     * 在原有的时间月份上加上或减去多少
     * @param date 原有时间
     * @param levevMonth 要加减的月份数
     * @return Date
     */
    public Date operationMonth(Date date, int levevMonth)
    {
        setCalendar(date);
        calendar.add(Calendar.MONTH, levevMonth);
        return calendar.getTime();
    }
    
    /**
     * 在原有的时间月份上加上或减去多少
     * @param date 原有时间
     * @param levevDate 要加减的月份数
     * @return Date
     */
    public Date operationDate(Date date, int levevDate)
    {
        setCalendar(date);
        calendar.add(Calendar.DATE, levevDate);
        return calendar.getTime();
    }
    
    /**
     * 在原有的时间秒上加上或减去多少
     * @param date 原有时间
     * @param levevSecond 要加减的秒数
     * @return Date
     */
    public Date operationSecond(Date date, int levevSecond)
    {
        setCalendar(date);
        calendar.add(Calendar.SECOND, levevSecond);
        return calendar.getTime();
    }
    
    /**
     * 在原有的时间小时上加上或减去多少(字符串时间)
     * @param strDate 原有时间
     * @param levevHour 要加减的小时数
     * @param isStandard 是还是24小时制
     * @return String
     * @throws ParseException ParseException
     */
    public String operationHour(String strDate, int levevHour,
            boolean isStandard) throws ParseException
    {
        setCalendar(strDate);
        if (isStandard)
        {
            calendar.add(Calendar.HOUR_OF_DAY, levevHour);
        }
        else
        {
            calendar.add(Calendar.HOUR, levevHour);
        }
        return format(calendar.getTime());
    }
    
    /**
     * 在原有的时间分钟上加上或减去多少
     * @param strDate 原有时间
     * @param levevMinute 要加减的分钟数
     * @return String
     * @throws ParseException ParseException
     */
    public String operationMinute(String strDate, int levevMinute)
            throws ParseException
    {
        setCalendar(strDate);
        calendar.add(Calendar.MINUTE, levevMinute);
        return format(calendar.getTime());
    }
    
    /**
     * 在原有的时间年份上加上或减去多少
     * @param strDate 原有时间
     * @param levevYear 要加减的年份数
     * @return String
     * @throws ParseException ParseException
     */
    public String operationYear(String strDate, int levevYear)
            throws ParseException
    {
        setCalendar(strDate);
        calendar.add(Calendar.YEAR, levevYear);
        return format(calendar.getTime());
    }
    
    /**
     * 在原有的时间月份上加上或减去多少
     * @param strDate 原有时间
     * @param levevMonth 要加减的月份数
     * @return Date
     * @throws ParseException ParseException
     */
    public String operationMonth(String strDate, int levevMonth)
            throws ParseException
    {
        setCalendar(strDate);
        calendar.add(Calendar.MONTH, levevMonth);
        return format(calendar.getTime());
    }
    
    /**
     * 在原有的时间月份上加上或减去多少
     * @param strDate 原有时间
     * @param levevDate 要加减的月份数
     * @return Date
     * @throws ParseException ParseException
     */
    public String operationDate(String strDate, int levevDate)
            throws ParseException
    {
        setCalendar(strDate);
        calendar.add(Calendar.DATE, levevDate);
        return format(calendar.getTime());
    }
    
    /**
     * 在原有的时间秒上加上或减去多少
     * @param strDate 原有时间
     * @param levevSecond 要加减的秒数
     * @return Date
     * @throws ParseException ParseException
     */
    public String operationSecond(String strDate, int levevSecond)
            throws ParseException
    {
        setCalendar(strDate);
        calendar.add(Calendar.SECOND, levevSecond);
        return format(calendar.getTime());
    }
    
    /**
     * 返回两个时间段之间的间隔(天)
     * @param srcDate 时间点1
     * @param destDate 时间点2
     * @return int
     * @throws ParseException ParseException
     */
    public int getDaysOperationDate(Date srcDate, Date destDate)
            throws ParseException
    {
        return (int)StrictMath.abs((srcDate.getTime() - destDate.getTime()) / 30);
    }
    
    /**
     * 返回两个时间段之间的间隔(天)
     * @param strSrcDate 时间点1
     * @param strDestDate 时间点2
     * @return int
     * @throws ParseException ParseException
     */
    public int getDaysOperationDate(String strSrcDate, String strDestDate)
            throws ParseException
    {
        return (int)StrictMath.abs((parse(strSrcDate).getTime() - parse(strDestDate).getTime()) / 30);
    }
    
    /**
     * 判断用户输入的时间是否介于两个时间段内
     * @param afterDate 结束时间
     * @param beforeDate 起始时间
     * @param currentDate 用户输入的时间
     * @return boolean true:是介于两个时间段之间
     */
    public boolean compareDate(Date afterDate, Date beforeDate, Date currentDate)
    {
        if (currentDate.after(beforeDate) && currentDate.before(afterDate))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 判断用户输入的时间是否介于两个时间段内(字符串时间)
     * @param strAfterDate 结束时间
     * @param strBeforeDate 起始时间
     * @param strCurrentDate 用户输入的时间
     * @return boolean true:是介于两个时间段之间
     * @throws ParseException ParseException
     */
    public boolean compareDate(String strAfterDate, String strBeforeDate,
            String strCurrentDate) throws ParseException
    {
    	Date currentDate = parse(strCurrentDate);
        if (currentDate.after(parse(strBeforeDate))
                && currentDate.before(parse(strAfterDate)))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 返回系统的当前时间,以字符串形式
     * @return String
     */
    public String getSystemStrDate()
    {
        return format(new Date());
    }
    
    /**
     * 设置日历的时间
     */
    private void setCalendar(Date date)
    {
        calendar.setTime(date);
    }
    
    /**
     * 设置日历的时间
     */
    private void setCalendar(String strDate) throws ParseException
    {
        calendar.setTime(parse(strDate));
    }
    
    /**
     * 判断当前时间是否介于开始时间和结束时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return boolean
     * @throws ParseException ParseException
     */
    public boolean compareDate(String startTime, String endTime) throws ParseException
    {
        Date currentDate = new Date();
        String strCurrentTime = format(currentDate);
        String time = strCurrentTime.substring(0, strCurrentTime.indexOf(" ") + 1);
        Date startDate = parse(time + startTime);
        Date endDate = parse(time + endTime);
        if (currentDate.before(startDate))
        {
            if (currentDate.before(endDate))
            {
                return true;
            }
        }
        else if (endDate.before(startDate))
        {
            if (currentDate.after(startDate))
            {
                return true;
            }
        }
        else 
        {
            if (currentDate.after(startDate) && currentDate.before(endDate))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
	 * 将日期格式化为字符串
	 * @param date 要转换的日期
	 * @param formatStr 格式串

	 * @return
	 */
	public static String format(Date date, String formatStr) {

		SimpleDateFormat formatDate = new SimpleDateFormat(formatStr);

		return formatDate.format(date);
	}
}