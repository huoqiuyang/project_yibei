package com.yibei.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期格式化处理组件
 */
public class TimeUtils {

	/**
	 * 将长整型的日期转化为字符型日期字符串
	 * 
	 * @param intDate
	 *            长整型日期
	 */
	public static String formatIntToDateString(long intDate) {
		Date time;
		SimpleDateFormat format;
		String strtime;
		if (intDate > 0) {
			try {
				long c_unix_time2 = intDate;
				time = new Date();
				time.setTime(c_unix_time2 * 1000);
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
						Locale.getDefault());
				strtime = format.format(time);
			} catch (Exception ex) {
				strtime = "";
				ex.printStackTrace();
			}
		} else {
			strtime = "";
		}
		return strtime;
	}

	/**
	 * 将长整型的日期转化为字符型日期字符串
	 * 
	 * @param intDate
	 *            长整型日期
	 * @return pattern 格式
	 */
	public static String formatIntToDateString(long intDate, String pattern) {
		Date time;
		SimpleDateFormat format;
		String strtime;
		if (intDate > 0) {
			try {
				long c_unix_time2 = intDate;
				time = new Date();
				time.setTime(c_unix_time2 * 1000);
				if (pattern != null) {
					format = new SimpleDateFormat(pattern, Locale.getDefault());
				} else {
					format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
							Locale.getDefault());
				}
				strtime = format.format(time);
			} catch (Exception ex) {
				strtime = "";
				ex.printStackTrace();
			}
		} else {
			strtime = "";
		}
		return strtime;
	}

	/**
	 * 将长整型的日期转化为字符型日期字符串yyyy-MM-dd
	 * 
	 * @param intDate
	 *            长整型日期
	 * @return String 字符型时间
	 */
	public static String formatIntToDateStringT(long intDate) {
		Date time;
		SimpleDateFormat format;
		String strtime;
		if (intDate > 0) {
			try {
				long c_unix_time2 = intDate;
				time = new Date();
				time.setTime(c_unix_time2 * 1000);
				format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
				strtime = format.format(time);
			} catch (Exception ex) {
				strtime = "";
				ex.printStackTrace();
			}
		} else {
			strtime = "";
		}
		return strtime;
	}

	/**
	 * 将长整型的日期转化为一定格式字符型日期字符串
	 * 
	 * @param _format
	 *            格式化 例如:yyyy-MM-dd HH:mm:ss
	 * @param intDate
	 *            长整型日期
	 * @return String 字符型时间
	 */
	public static String formatIntToDateString(String _format, long intDate) {
		Date time = new Date();
		SimpleDateFormat format;
		String strtime;
		if (intDate > 0) {
			try {
				long c_unix_time2 = intDate;
				time.setTime(c_unix_time2 * 1000);
				format = new SimpleDateFormat(_format, Locale.getDefault());
				strtime = format.format(time);
			} catch (Exception ex) {
				strtime = "";
				ex.printStackTrace();
			}
		} else {
			strtime = "";
		}
		return strtime;
	}

	/**
	 * 将长整型转换为日期类型
	 * 
	 * @param intDate
	 *            长整型日期
	 * @return Date 日期类型时间
	 */
	public static Date formatIntToDate(long intDate) {
		Date time = new Date();
		if (intDate > 0) {
			time.setTime(intDate * 1000);
		}
		return time;
	}

	/**
	 * 将字符串转换为日期类型
	 * 
	 * @param strDate
	 *            字符型日期
	 * @return Date 日期类型时间
	 */
	public static Date formatStringToDate(String strDate) {
		SimpleDateFormat format;
		if (strDate.trim().equals(""))
			return null;
		try {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			return format.parse(strDate);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将字符串转换为日期类型
	 * 
	 * @param strDate
	 *            字符型日期
	 * @param pattern
	 *            日期格式
	 * @return Date 日期类型时间
	 */
	public static Date formatStringToDate(String strDate, String pattern) {
		SimpleDateFormat format;
		if (strDate.trim().equals(""))
			return null;
		try {
			format = new SimpleDateFormat(pattern, Locale.getDefault());
			return format.parse(strDate);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将字符串转换为日期类型 
	 * @param strDate  字符型日期
	 * @return Date 日期类型时间
	 */
	public static Date formatStrToDate(String strDate) {
		SimpleDateFormat format;
		if (strDate.trim().equals(""))
			return null;
		try {
			format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
			return format.parse(strDate);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 将日期转换成长整型
	 * 
	 * @param p_date
	 *            日期型时间
	 * @return long 长整型时间
	 */
	public static long formatDateToInt(Date p_date) {
		if (p_date != null) {
			return p_date.getTime() / 1000;
		}
		return 0;
	}

	/**
	 * 将字符串日期转换成长整类型日期
	 * 
	 * @param strDate
	 *            字符串型时间
	 * @return long 长整型时间
	 */
	public static long formatDateStringToInt(String strDate) {
		SimpleDateFormat format;
		Date time;
		if(strDate == null){
			strDate = "";
		}
		if (strDate.trim().equals(""))
			return -1;
		String strAry[] = strDate.split(":");
		if (strAry.length > 1)
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
		else
			format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		try {
			time = format.parse(strDate + ":00");
			return time.getTime() / 1000;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 将字符串类型转换为长整型(按格式)
	 * 
	 * @param strDate
	 *            字符串型时间
	 * @param _format
	 *            字符串时间格式
	 * @return long 长整型时间
	 */
	public static long formatDateStringToInt(String strDate, String _format) {
		Date time;
		SimpleDateFormat format = new SimpleDateFormat(_format,
				Locale.getDefault());
		try {
			time = format.parse(strDate);
			return time.getTime() / 1000;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 将日期类型转化为长日期字符串
	 * 
	 * @param p_date
	 *            日期型时间
	 * @return String 字符串时间
	 */
	public static String formatLongDateToString(Date p_date) {
		String strtime = "";
		SimpleDateFormat format;
		if (formatDateToInt(p_date) > 0) {
			format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			strtime = format.format(p_date);
		}
		return strtime;
	}

	/**
	 * 将日期类型转化为短日期字符串
	 * 
	 * @param p_date
	 *            日期型时间
	 * @return String 字符串时间
	 */
	public static String formatShortDateToString(Date p_date) {
		String strtime = "";
		SimpleDateFormat format;
		format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		strtime = format.format(p_date);
		return strtime;
	}

	/**
	 * 将日期类型转化为日期字符串
	 * 
	 * @param p_date
	 *            日期型时间
	 * @param _format
	 *            格式
	 * @return String 字符串时间
	 */
	public static String formatDateToString(Date p_date, String _format) {
		String strtime = "";
		SimpleDateFormat format;
		format = new SimpleDateFormat(_format, Locale.getDefault());
		strtime = format.format(p_date);
		return strtime;
	}

	/**
	 * 用于获得指定格式的当前日期
	 * 
	 * @param format
	 *            字符串时间格式 eg:yyyy-MM-dd HH:mm:ss
	 * @return String 字符串时间
	 */
	public static String getCurrentDate(String format) {
		String currentDate = "";
		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat simpleDateFormat;
			Date date = calendar.getTime();
			simpleDateFormat = new SimpleDateFormat(format);
			currentDate = simpleDateFormat.format(date);
		} catch (Exception e) {
			currentDate = "";
		}
		return currentDate;

	}

	/**
	 * 用于获取周
	 */
	public static Map<Integer, String> getWeek(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sm = new SimpleDateFormat(format);
		String month = sm.format(cal.getTime());

		cal.set(Calendar.YEAR, Integer.parseInt(month.substring(0, 4)));
		cal.set(Calendar.MONTH,
				Integer.parseInt(month.substring(month.indexOf("-") + 1,
						month.length())) - 1);
		int maxDate = cal.getActualMaximum(Calendar.DATE);// 当前月最大天数
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		if (maxDate % 4 != 0) {
			map.put(5, month + "-" + 28 + " and " + month + "-" + maxDate);
		}
		map.put(1, month + "-" + 1 + " and " + month + "-" + 7);
		map.put(2, month + "-" + 7 + " and " + month + "-" + 14);
		map.put(3, month + "-" + 14 + " and " + month + "-" + 21);
		map.put(4, month + "-" + 21 + " and " + month + "-" + 28);
		return map;
	}

	/**
	 * 用于获得当前日期
	 * 
	 * @return Date 日期型时间
	 */
	public static Date getCurrentDate() {
		Date date = null;
		Calendar calendar = Calendar.getInstance();
		date = calendar.getTime();
		return date;
	}

	/**
	 * "00:00:00" 格式的时分秒转换成秒
	 * 
	 * @param date
	 * @return String
	 */
	public static long getSecondNew(String date) {
		long secondLong = 0;
		if (date != null && !"".equals(date)) {
			String[] timeStr = date.split(":");
			long hour = Integer.valueOf(timeStr[0]).intValue() * 3600;
			long minute = Integer.valueOf(timeStr[1]).intValue() * 60;
			long second = Integer.valueOf(timeStr[2]).intValue();
			secondLong = hour + minute + second;
		}
		return secondLong;
	}

	/**
	 * 获取当前时间的秒数
	 * 
	 * @return
	 */
	public static long getCurrentTime() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * 获取指定日期的年
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return 返回的月,例如2010-05-17 则返回2010
	 */
	public static int getYearOfDate(Date p_date) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取指定日期的月时间
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return 返回的月,例如2010-05-17 则返回5
	 */
	public static int getMonthOfDate(Date p_date) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定日期的日时间
	 * 
	 * @param p_date
	 *            util.Date日期格式
	 * @return 返回的日,例如2010-05-17 则返回17
	 */
	public static int getDayOfDate(Date p_date) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 比较两个字符串时间相差的年数,只要年份不同，不到一年的，返回一年
	 * 
	 * @param startTime
	 *            一个源时间数据(格式要求：yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss)
	 * @param endTime
	 *            一个目标时间数据(格式要求：yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss)
	 * @return 如果endTime>startTime返回正整数,否则返回负整数,相等则返回0
	 */
	public static long yearBetween(String startTime, String endTime) {
		long distanceYear = 0;
		String[] date = dateBetween(startTime, endTime).split(":");
		if (date.length != 6) {
			int year = Integer.parseInt(date[0]);
			distanceYear = year;
		}
		return distanceYear;
	}

	/**
	 * 比较两个字符串时间相差的月数
	 * 
	 * @param startTime
	 *            一个源时间数据(格式要求：yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss)
	 * @param endTime
	 *            一个目标时间数据(格式要求：yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss)
	 * @return 如果endTime>startTime返回正整数,否则返回负整数,相等则返回0
	 */
	public static long monthBetween(String startTime, String endTime) {
		long distanceDay = 0;
		String[] date = dateBetween(startTime, endTime).split(":");
		if (date.length == 6) {
			int year = Integer.parseInt(date[0]);
			int month = Integer.parseInt(date[1]);
			distanceDay = year * 12 + month;
		}
		return distanceDay;
	}

	/**
	 * 比较两个字符串时间相差的天数
	 * 
	 * @param startTime
	 *            一个源时间数据(格式要求：yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss)
	 * @param endTime
	 *            一个目标时间数据(格式要求：yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss)
	 * @return 如果endTime>startTime返回正整数,否则返回负整数,相等则返回0
	 */
	public static long daysBetween(String startTime, String endTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long distanceDay = 0;
		try {
			if (StringUtils.isNotBlank(startTime)
					&& StringUtils.isNotBlank(endTime)) {
				Date startDate = format.parse(startTime);
				Date endDate = format.parse(endTime);
				distanceDay = (endDate.getTime() - startDate.getTime())
						/ (1000 * 60 * 60 * 24);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return distanceDay;
	}

	/**
	 * 比较两个字符串时间相差的小时数
	 * 
	 * @param startTime
	 *            一个源数据时间(格式要求：yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss)
	 * @param endTime
	 *            一个目标数据时间(格式要求：yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss)
	 * @return 返回两个时间相差的小时数,如果endTime>startTime返回正整数,否则返回负整数,相等则返回0
	 */
	public static long timeBetween(String startTime, String endTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long distanceTime = 0;
		try {
			Date startDate = format.parse(startTime);
			Date endDate = format.parse(endTime);
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			distanceTime = endCalendar.get(Calendar.HOUR)
					- startCalendar.get(Calendar.HOUR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return distanceTime;
	}

	/**
	 * 比较两个字符串时间相差的小时数 按24小时制
	 * 
	 * @param startTime
	 *            一个源数据时间(格式要求：yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss)
	 * @param endTime
	 *            一个目标数据时间(格式要求：yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss)
	 * @return 返回两个时间相差的小时数,如果endTime>startTime返回正整数,否则返回负整数,相等则返回0
	 */
	public static long timeBetweenOfDay24(String startTime, String endTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		long distanceTime = 0;
		try {
			Date startDate = format.parse(startTime);
			Date endDate = format.parse(endTime);
			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			startCalendar.setTime(startDate);
			endCalendar.setTime(endDate);
			distanceTime = endCalendar.get(Calendar.HOUR_OF_DAY)
					- startCalendar.get(Calendar.HOUR_OF_DAY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return distanceTime;
	}

	/**
	 * 比较两个字符串时间的时间差
	 * 
	 * @param startTime
	 *            一个源时间数据(格式要求：yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss)
	 * @param endTime
	 *            一个目标时间数据(格式要求：yyyy-MM-dd HH:mm 或 yyyy-MM-dd HH:mm:ss) return
	 *            输出格式为 年:月:日:时:分:秒
	 */
	private static String dateBetween(String startTime, String endTime) {
		String date = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if (StringUtils.isNotBlank(startTime)
					&& StringUtils.isNotBlank(endTime)) {
				Date startDate = format.parse(startTime);
				Date endDate = format.parse(endTime);
				int year = endDate.getYear() - startDate.getYear();
				int month = endDate.getMonth() - startDate.getMonth();
				int day = endDate.getDay() - startDate.getDay();
				int hour = endDate.getHours() - startDate.getHours();
				int minute = endDate.getMinutes() - startDate.getMinutes();
				int second = endDate.getSeconds() - startDate.getSeconds();
				date = year + ":" + month + ":" + day + ":" + hour + ":"
						+ minute + ":" + second;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 两个字符串日期时间进行大小比较
	 * 
	 * @param src_time
	 *            源时间数据
	 * @param dest_time
	 *            目标时间数据
	 * @return 如果dest_time>src_time则返回true,否则返回false
	 */
	public static boolean timeCompare(String src_time, String dest_time,
			String _format) {
		boolean flag = false;
		SimpleDateFormat format = new SimpleDateFormat(_format);
		try {
			Date start = format.parse(src_time);
			Date end = format.parse(dest_time);
			long time = (end.getTime() - start.getTime());
			if (time > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 计算两个日期相差的天数
	 * 
	 * @author yhg
	 * @param fistDate
	 * @param secondDate
	 * @return
	 */
	public static int getBetweenDays(String begin, String end) {
		if (begin == null || end == null) {
			return 0;
		}
		int days = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date sdate = sdf.parse(begin);
			Date edate = sdf.parse(end);
			long times = edate.getTime() - sdate.getTime();
			days = (int) (times / 86400000);// 24 * 60 * 60 * 1000 = 86400000
		} catch (Exception pe) {
			// log.warn("计算两个日期的时间发生异常，可能是日期的格式有错,请用yyyy-MM-dd格式");
			pe.printStackTrace();
		}
		return days;
	}

	/**
	 * 制定时间格式，将String时间转换成Data
	 * 
	 * @author yhg
	 * @param time
	 * @param dateFormate
	 * @return
	 */
	public static Date getDate(String time, String dateFormate) {
		SimpleDateFormat smdf = new SimpleDateFormat(dateFormate);
		Date theDate = null;
		try {
			theDate = smdf.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theDate;
	}

	/**
	 * 为给定的日期添加或减去指定的天数
	 * 
	 * @author magl
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getModifyDate(Date date, int n) {
		Date theDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, n);
		theDate = cal.getTime();
		return theDate;
	}

	/**
	 * 为给定的月份添加或减去指定的月数
	 * 
	 * @author magl
	 * @param ym
	 *            yyyy-MM 格式年月
	 * @param n
	 * @return
	 */
	public static String getModifyMonth(String ym, int n) {
		Date theDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatStringToDate(ym, "yyyy-MM"));
		cal.add(Calendar.MONTH, n);

		theDate = cal.getTime();
		return formatDateToString(theDate, "yyyy-MM");
	}
	
	/**
	 * 为给定的日期添加或减去指定的年数
	 * 
	 * @author magl
	 * @param date
	 * @param n
	 * @return
	 */
	public static String getModifyYear(String date, int n) {
		String _format = "yyyy-MM-dd";
		if(date.indexOf(":")>0) {
			_format = "yyyy-MM-dd HH:mm:ss";
		}else {
			if(date.indexOf("-")>0) {
				if(date.length() == 4) {
					_format = "yyyy";
				}else if(date.length() == 7) {
					_format = "yyyy-MM";
				}else if(date.length() == 10){
					_format = "yyyy-MM-dd";
				}
			}else {
				if(date.length() == 4) {
					_format = "yyyy";
				}else if(date.length() == 6) {
					_format = "yyyyMM";
				}else if(date.length() == 8){
					_format = "yyyyMMdd";
				}else if(date.length() > 8) {
					_format = "yyyyMMddHHmmss";
				}
			}
		}
		
		Date theDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatStringToDate(date, _format));
		cal.add(Calendar.YEAR, n);
		theDate = cal.getTime();
		return formatDateToString(theDate, _format);
	}

	/**
	 * 获得指定日期的当月最大天数
	 * 
	 * @author magl
	 * @param date
	 * @param n
	 * @return
	 */
	public static int getDayNumOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 判断某天是星期几
	 * 
	 * @author yhg
	 * @param date
	 * @return
	 */
	public static String getWeekday(String date) {// 必须yyyy-MM-dd
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdw = new SimpleDateFormat("E");
		Date d = null;
		try {
			d = sd.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdw.format(d);
	}

	/**
	 * 将"00:00:00" 格式的时分秒转换成秒
	 * 
	 * @author yhg
	 * @param date
	 * @return
	 */
	public static long getSecond(String date) {
		long secondLong = 0;
		if (date != null && !"".equals(date)) {
			String[] timeStr = date.split(":");
			long hour = Integer.valueOf(timeStr[0]).intValue() * 3600;
			long minute = Integer.valueOf(timeStr[1]).intValue() * 60;
			long second = Integer.valueOf(timeStr[2]).intValue();
			secondLong = hour + minute + second;
		}
		return secondLong;
	}

	/**
	 * 查询yyyy-MM月有几天
	 * 
	 * @author yhg
	 * @param data
	 */
	public static int getData(String data) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(data.substring(0, 4)));
		cal.set(Calendar.MONTH,
				Integer.parseInt(data.substring(data.indexOf("-") + 1,
						data.length())) - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int maxDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);// 当前月最大天数
		return maxDate;

	}

	/**
	 * 判断某天是否是周末
	 * 
	 * @author yhg
	 * @param date
	 * @return
	 */
	public static boolean isWeekend(Date date) {
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		// 判断是否周末
		int dayOfWeek = dateCal.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
			return true;
		}
		return false;
	}

	/**
	 * 取得规定日期是第几季度,null取当前系统时间
	 * 
	 * @return -1,异常;1,第一季度;2,第二季度;3,第三季度;4,第四季度
	 */
	public static int getCurrentQuarter(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int month = calendar.get(Calendar.MONTH);
		switch (month) {
		case 0:
			return 1;
		case 1:
			return 1;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 2;
		case 5:
			return 2;
		case 6:
			return 3;
		case 7:
			return 3;
		case 8:
			return 3;
		case 9:
			return 4;
		case 10:
			return 4;
		case 11:
			return 4;
		default:
			return -1;
		}
	}

	/**
	 * 获取指定日期的月份
	 * 
	 * @param 日期时间的秒
	 * @return int 月份
	 */
	public static int getMonthOfDate(Long dateTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(dateTime * 1000);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定日期的日份
	 * 
	 * @param 日期时间的秒
	 * @return int 日份
	 */
	public static int getDayOfDate(Long dateTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(dateTime * 1000);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定日期为周几
	 * 
	 * @param dateTime
	 * @return
	 */
	public static long getDayOfWeek(Long dateTime) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(dateTime * 1000);
		return c.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 获取指定日期的小时
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return int 日份
	 */
	public static int getHourOfDate(Date p_date) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 取得当前日期周期内对应的最后日期
	 * 
	 * @param date
	 *            日期
	 * @param cycle
	 *            周期 HALFMONTH,半月;MONTH,月;SEASON,季;HALFYEAR,半年;YEAR,年
	 * @return
	 */
	public static Long getEndDayByCycle(Long date, String cycle) {
		cycle = cycle.toUpperCase();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date * 1000);
		String year = TimeUtils.formatDateToString(cal.getTime(), "yyyy");
		String ymd = "";
		if ("HALFMONTH".equals(cycle)) {
			if (cal.get(Calendar.DAY_OF_MONTH) > 15) {
				ymd = TimeUtils.formatIntToDateString(date, "yyyy-MM-")
						+ getDayNumOfMonth(cal.getTime());
			} else {
				ymd = TimeUtils.formatIntToDateString(date, "yyyy-MM-") + "15";
			}
		} else if ("MONTH".equals(cycle)) {
			ymd = TimeUtils.formatDateToString(cal.getTime(), "yyyy-MM-")
					+ getDayNumOfMonth(cal.getTime());
		} else if ("TWOMONTH".equals(cycle)) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			ymd = TimeUtils.formatDateToString(cal.getTime(), "yyyy-MM-")
					+ getDayNumOfMonth(cal.getTime());
		} else if ("SEASON".equals(cycle)) {
			switch (getCurrentQuarter(cal.getTime())) {
			case 1:
				ymd = year + "-03-31";
				break;
			case 2:
				ymd = year + "-06-30";
				break;
			case 3:
				ymd = year + "-09-30";
				break;
			case 4:
				ymd = year + "-12-31";
				break;
			default:
				;
			}
		} else if ("HALFYEAR".equals(cycle)) {
			if (cal.get(Calendar.MONTH) > 5) {
				ymd = year + "-12-31";
			} else {
				ymd = year + "-06-30";
			}
		} else if ("YEAR".equals(cycle)) {
			ymd = year + "-12-31";
		}
		return TimeUtils.formatDateStringToInt(ymd, "yyyy-MM-dd");
	}

	/**
	 * 获得当前时间的时分秒 24小时制度
	 * 
	 * @return
	 */
	public static long getHourTimeHH() {
		long hourTime = 0l;
		String timme = getCurrentDate("yyyy-MM-dd HH:mm:ss");
		hourTime = getSecond(timme.substring(11));
		return hourTime;
	}

	/**
	 * 获取本月第一天
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMonthFirstDay() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		return simpleFormate.format(calendar.getTime());
	}

	/**
	 * 获取本月最后一天
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getMonthLastDay() {
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return simpleFormate.format(calendar.getTime());
	}

	/**
	 * 比较两个日期的前后
	 * 
	 * @param starts
	 *            秒
	 * @param ends
	 *            秒
	 * @param format
	 * @return
	 */
	public static boolean after(long starts, long ends, String formats) {
		boolean flag = false;
		try {
			SimpleDateFormat format = new SimpleDateFormat(formats);
			Date start = formatIntToDate(starts);
			Date end = formatIntToDate(ends);
			String startStr = format.format(start);
			String endStr = format.format(end);
			System.out.println("startStr=" + startStr + ",endStr=" + endStr);
			Date tarS = format.parse(startStr);
			Date tarE = format.parse(endStr);
			flag = tarE.after(tarS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 日期+小时 ，返回的是字符串型的时间， 24小时制
	 * 
	 * @param day
	 *            例如2013-08-29 08:16:36
	 * @param x
	 *            例如5
	 * @author yhg
	 * @return
	 */
	public static String addDateHour(String day, int x) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制
		Date date = null;
		try {
			date = format.parse(day);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null)
			return "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, x);
		date = cal.getTime();
		return format.format(date);
	}
	public static String formatDateToDateString(Date p_date, String _format) {
		String date = null;
		try {
			SimpleDateFormat simpleDateFormat;
			simpleDateFormat = new SimpleDateFormat(_format);
			date = simpleDateFormat.format(p_date);
		} catch (Exception e) {
			date = "";
		}
		return date;

	}

	/**
	 * 获取本周开始时间
	 * @return
	 */
	public static String weekStartTime(String _format) {
		String yesterday = null;
		try {
			Calendar c=Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat(_format);

			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			dayOfWeek-=2;
			//今天-1天（昨天）
			c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);

			yesterday=df.format(c.getTime());
		}catch (Exception e){

		}
		return yesterday;
	}
}
