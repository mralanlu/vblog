package com.northbund.vblog.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * SimpleDateFormat是线程不安全的，多线程慎用
 */
public class DateUtil {

	public static final SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
	public static final SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyy-M");
	public static final SimpleDateFormat yyyyMMB = new SimpleDateFormat("yyyy年M月");
	public static final SimpleDateFormat yyyy_MM = new SimpleDateFormat(
			"yyyy-MM");
	public static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static final SimpleDateFormat yyyy_MM_dd_HH = new SimpleDateFormat(
			"yyyy-MM-dd HH");
	public static final SimpleDateFormat yyyy_MM_dd_HH_mm = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat yyyy_MM_dd_HH_mm_ss_S = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.S");

	public static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	
	public static final SimpleDateFormat yyyyMMddHHmmssSSS = new SimpleDateFormat(
			"yyyyMMddHHmmssSSS");
	
	public static final SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm");

	public static final String FULL_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	public static final SimpleDateFormat utcTimePatten = new SimpleDateFormat(
			FULL_UTC);

	public static final SimpleDateFormat cron = new SimpleDateFormat(
			"ss mm HH dd MM ? yyyy");

	public static String default_format = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(
			"yyyyMMdd");


	public static String formatDate(Date date,String format){
		if(format == null|| format == ""){
			format = default_format;
		}
		return new SimpleDateFormat(format).format(date);
	}
	public static String getCurrentyyyyMMdd(){
		return yyyyMMdd.format(new Date());
	}

	public static String getCurrentyyyyMMddHHmmss(){
		return yyyyMMddHHmmss.format(new Date());
	}

	public static Date parseString2Date(String date,String format){
		if(StringUtils.isEmpty(date)){
			return null;
		}
		if(StringUtils.isEmpty(format)){
			format = default_format;
		}
		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static String formatYYYY(Date date) {
		if (date == null) {
			return null;
		}
		return yyyy.format(date);
	}

	public static String formatYYYYMM(Date date) {
		if (date == null) {
			return null;
		}
		return yyyy_MM.format(date);
	}

	public static String formatYYYYMMDD(Date date) {
		if (date == null) {
			return null;
		}
		return yyyy_MM_dd.format(date);
	}

	public static String formatYYYYMMDDHH(Date date) {
		if (date == null) {
			return null;
		}
		return yyyy_MM_dd_HH.format(date);
	}

	public static String formatYYYYMMDDHHMM(Date date) {
		if (date == null) {
			return null;
		}
		return yyyy_MM_dd_HH_mm.format(date);
	}

	public static String formatYYYYMMDDHHMMSS(Date date) {
		if (date == null) {
			return null;
		}
		return yyyy_MM_dd_HH_mm_ss.format(date);
	}

	public static String formatYYYYMMDDHHMMSSS(Date date) {
		if (date == null) {
			return null;
		}
		return yyyy_MM_dd_HH_mm_ss_S.format(date);
	}

	public static String formatUTC(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, 8);
		return formatUTC(calendar.getTime(), FULL_UTC);
	}

	public static String formatUTC(Date date, String pattern) {
		return DateFormatUtils.formatUTC(date, pattern);
	}

	public static String utc2DefaultLocal(String utcTime) {
		if (StringUtils.isBlank(utcTime)) {
			return null;
		}
		return utc2Local(utcTime, DateUtil.utcTimePatten,
				DateUtil.yyyy_MM_dd_HH_mm_ss);
	}

	public static String utc2DefaultLocal(String utcTime,
			boolean returnDefaultTime) {
		if (StringUtils.isBlank(utcTime)) {
			return null;
		}
		String result = utc2Local(utcTime, DateUtil.utcTimePatten,
				DateUtil.yyyy_MM_dd_HH_mm_ss);
		if (result != null) {
			return result;
		} else {
			return returnDefaultTime ? utcTime : null;
		}
	}

	public static String utc2Local(String utcTime,
			SimpleDateFormat utcFormater, SimpleDateFormat localFormater) {
		if (StringUtils.isBlank(utcTime)) {
			return null;
		}
		utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date gpsUTCDate = null;
		try {
			gpsUTCDate = utcFormater.parse(utcTime);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		localFormater.setTimeZone(TimeZone.getDefault());
		String localTime = localFormater.format(gpsUTCDate.getTime());
		return localTime;
	}

	public static Date formatWxTime(String wxTime) {
		return new Date(Long.parseLong(wxTime) * 1000L);
	}

	public static Date parseDate(Long time) {
		return time != null && time != 0L ? new Date(time) : null;
	}

	public static Date changeDate(Date date, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}

	/**
	 * 获取本月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateFirstDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取下月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateFirstDayOfNextMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取本月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateLastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		int lastDay = c.getActualMaximum(Calendar.DATE);
		c.set(Calendar.DAY_OF_MONTH, lastDay);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取日期的开始
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateBegin(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 1);
		return cal.getTime();
	}

	/**
	 * 获取日期的结束
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateEnd(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		//cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * <p>
	 * Parse src to Date.
	 * </p>
	 * <p>
	 * A <code>null</code> or empty ("") or empty(" ") String input returns
	 * <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * DateUtils.parse(null)                         = null
	 * DateUtils.parse("")                           = null
	 * DateUtils.parse(" ")                          = null
	 * DateUtils.parse("2016")                       = Sat Jan 01 00:00:00 CST 2016
	 * DateUtils.parse("2016-11")                    = Sat Jan 01 00:00:00 CST 2016
	 * DateUtils.parse("2016-11-12")                 = Sat Nov 12 00:00:00 CST 2016
	 * DateUtils.parse("2016-11-12 08")              = Sat Nov 12 08:00:00 CST 2016
	 * DateUtils.parse("2016-11-12 08:20")           = Sat Nov 12 08:20:00 CST 2016
	 * DateUtils.parse("2016-11-12 08:20:20")        = Sat Nov 12 08:20:20 CST 2016
	 * DateUtils.parse("2016-11-12 08:20:20.3")      = Sat Nov 12 08:20:20.3 CST 2016
	 * </pre>
	 * 
	 * @param src
	 *            the String to parse date
	 * @return Date
	 */
	public static Date parse(String src) {
		if (StringUtils.isBlank(src))
			return null;
		try {
			if (src.indexOf("-") == -1) {
				return yyyy.parse(src);
			} else if (StringUtils.countMatches(src, "-") == 1) {
				return yyyy_MM.parse(src);
			} else if (StringUtils.countMatches(src, "-") == 2
					&& StringUtils.countMatches(src, " ") == 0) {
				return yyyy_MM_dd.parse(src);
			} else if (StringUtils.countMatches(src, "-") == 2
					&& StringUtils.countMatches(src, ":") == 0) {
				return yyyy_MM_dd_HH.parse(src);
			} else if (StringUtils.countMatches(src, ":") == 1) {
				return yyyy_MM_dd_HH_mm.parse(src);
			} else if (StringUtils.countMatches(src, ":") == 2
					&& StringUtils.countMatches(src, ".") == 0) {
				return yyyy_MM_dd_HH_mm_ss.parse(src);
			} else if (StringUtils.countMatches(src, ".") == 1) {
				return yyyy_MM_dd_HH_mm_ss_S.parse(src);
			} else {
				return null;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date getYesterday(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,-24);
		return calendar.getTime();
	}

	public static String getYesterdayStr(){
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY,-24);
		return yyyy_MM_dd.format(calendar.getTime());
	}


	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param start
	 *            较小的时间
	 * @param end
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date start, Date end) {
		try {
			start = yyyy_MM_dd.parse(yyyy_MM_dd.format(start));
			end = yyyy_MM_dd.parse(yyyy_MM_dd.format(end));
			Calendar cal = Calendar.getInstance();
			cal.setTime(start);
			long time1 = cal.getTimeInMillis();
			cal.setTime(end);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取HH:mm时间戳
	 * 
	 * @param timeStr:(如："13:30")
	 * @return
	 * @throws ParseException
	 */
	public static long formatHHMM(String timeStr) throws ParseException{
		return HHmm.parse(timeStr).getTime();
	}
	
	/**
	 * 获取日期间隔列表
	 * 
	 * @return
	 */
	public static List<String> getDateIntervalList(Date startDate, Date endDate){
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
	    Long startTime = start.getTimeInMillis();  
	  
	    Calendar end = Calendar.getInstance();  
	    end.setTime(endDate);
	    Long endTime = end.getTimeInMillis();  
	  
	    Long oneDay = 1000 * 60 * 60 * 24l;
	    Long time = startTime;  

	    List<String> dateList = new ArrayList<String>();
	    Integer num = 1;
	    while (time <= endTime) {
	    	String date = DateUtil.formatYYYYMMDD(new Date(time));
	        time += oneDay;
	        dateList.add(date);
	        num++;
	    }
	    return dateList;
	}


	public static String getyyyyMMddHHmmss(){
		return yyyyMMddHHmmss.format(new Date());
	}

	public static String getyyyyMMddHHmmss(Date date){
		return yyyyMMddHHmmss.format(date);
	}

	public static String getyyyyMMddHHmmss(Date date, int minute){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minute);
		return yyyyMMddHHmmss.format(cal.getTime());
	}

	public static Integer  getMM(Date date)  {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Integer month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	public static Date  getPreviousMonth(Date date)  {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,-1);
		date=parse(formatYYYYMM(cal.getTime()));
		return date;
	}

	public static Date  getNextMonth(Date date)  {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,+1);
		date=parse(formatYYYYMM(cal.getTime()));
		return date;
	}

	/**
	 * 获取当前季度
	 *
	 */
	public static int getQuarter() {
		Calendar c = Calendar.getInstance();
		int month = c.get(c.MONTH) + 1;
		int quarter = 0;
		if (month >= 1 && month <= 3) {
			quarter = 1;
		} else if (month >= 4 && month <= 6) {
			quarter = 2;
		} else if (month >= 7 && month <= 9) {
			quarter = 3;
		} else {
			quarter = 4;
		}
		return quarter ;
	}

	/**
	 * 获取某季度的第一天和最后一天
	 *	@param num  第几个季度
	 */
	public static String[] getCurrQuarterFirstDayAndLastDay(int num) {
		String[] s = new String[2];
		String str = "";
		// 设置本年的季
		Calendar quarterCalendar = null;
		switch (num) {
			case 1: // 本年到现在经过了一个季度，在加上前4个季度
				quarterCalendar = Calendar.getInstance();
				quarterCalendar.set(Calendar.MONTH, 3);
				quarterCalendar.set(Calendar.DATE, 1);
				quarterCalendar.add(Calendar.DATE, -1);
				str = formatDate1(quarterCalendar.getTime(), "yyyy-MM-dd");
				s[0] = str.substring(0, str.length() - 5) + "01-01";
				s[1] = str;
				break;
			case 2: // 本年到现在经过了二个季度，在加上前三个季度
				quarterCalendar = Calendar.getInstance();
				quarterCalendar.set(Calendar.MONTH, 6);
				quarterCalendar.set(Calendar.DATE, 1);
				quarterCalendar.add(Calendar.DATE, -1);
				str = formatDate1(quarterCalendar.getTime(), "yyyy-MM-dd");
				s[0] = str.substring(0, str.length() - 5) + "04-01";
				s[1] = str;
				break;
			case 3:// 本年到现在经过了三个季度，在加上前二个季度
				quarterCalendar = Calendar.getInstance();
				quarterCalendar.set(Calendar.MONTH, 9);
				quarterCalendar.set(Calendar.DATE, 1);
				quarterCalendar.add(Calendar.DATE, -1);
				str = formatDate1(quarterCalendar.getTime(), "yyyy-MM-dd");
				s[0] = str.substring(0, str.length() - 5) + "07-01";
				s[1] = str;
				break;
			case 4:// 本年到现在经过了四个季度，在加上前一个季度
				quarterCalendar = Calendar.getInstance();
				str = formatDate1(quarterCalendar.getTime(), "yyyy-MM-dd");
				s[0] = str.substring(0, str.length() - 5) + "10-01";
				s[1] = str.substring(0, str.length() - 5) + "12-31";
				break;
		}
		return s;
	}

	/**
	 * 用途：以指定的格式格式化日期字符串
	 * @param pattern 字符串的格式
	 * @param currentDate 被格式化日期
	 * @return String 已格式化的日期字符串
	 * @throws NullPointerException 如果参数为空
	 */
	public static String formatDate1(Date currentDate, String pattern){
		if(currentDate == null || "".equals(pattern) || pattern == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(currentDate);
	}
}