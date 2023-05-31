package com.yibei.common.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;


public class DateUtil {
	private final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	private final static SimpleDateFormat sdfYearMonth = new SimpleDateFormat("yyyyMM");

	private final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");

	private final static SimpleDateFormat sdfYearMonthDay = new SimpleDateFormat("yyyy/MM/dd");

	private final static SimpleDateFormat sdfday = new SimpleDateFormat("dd");

	private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");

	private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final static SimpleDateFormat sdfD = new SimpleDateFormat("yyyy.MM.dd HH:mm");

	private final static SimpleDateFormat sdfTimes = new SimpleDateFormat("yyyyMMddHHmmss");

	private final static SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");

	/**
	 * 获取YYYY格式
	 *
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取dd格式
	 *
	 * @return
	 */
	public static String getday() {
		return sdfday.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}

	/**
	 * 获取YYYYMMDD格式
	 *
	 * @return
	 */
	public static String getDays() {
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getDay(Date d) {
		return sdfDay.format(d);
	}

	/**
	 * 获取YYYY-MM-DD格式
	 *
	 * @return
	 */
	public static String getYMDay(Date d) {
		return sdfYearMonthDay.format(d);
	}

	/**
	 * 获取YYYYMM格式
	 * @param d
	 * @return
	 */
	public static String getYearMonth(Date d){
		return sdfYearMonth.format(d);
	}

	/**
	 * 获取MM格式
	 * @param d
	 * @return
	 */
	public static String getMonth(Date d){
		return sdfMonth.format(d);
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 *
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}


	/**
	 * 获取YYYY.MM.DD HH:mm格式
	 *
	 * @return
	 */
	public static String getData() {
		return sdfD.format(new Date());
	}

	/**
	 * 获取YYYYMMDDHHmmss格式
	 *
	 * @return
	 */
	public static String getTimes() {
		return sdfTimes.format(new Date());
	}

	public static String getTime(Date date) {
		return sdfTime.format(date);
	}
	public static Date getTime(String date){
		try {
			return sdfTime.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	/**
	 * yyyy-MM-dd HH:mm:ss格式 转换 yyyy-MM-dd
	 */
	public static String subTimeSting(String date) {
		Date fomatDate = fomatDate(date);
		return sdfDay.format(fomatDate);
	}

	public static Date getDate(String date){
		Date fomatDate = fomatDate(date);
		return fomatDate;
	}

	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws
	 * @author luguosui
	 */
	public static boolean compareDate(String s, String e) {
		if (fomatDate(s) == null || fomatDate(e) == null) {
			return false;
		}
		return fomatDate(s).getTime() >= fomatDate(e).getTime();
	}

	public static boolean compareDate(Date s, Date e) {
		return s.getTime() >= e.getTime();
	}

	public static Date fomatDateWithSecond(String date) {
		try {
			return sdfTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 格式化日期
	 *
	 * @return
	 */
	public static Date fomatDate(String date) {
		try {
			return sdfDay.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 *
	 * @return
	 */
	public static boolean isValidDate(String s) {
		try {
			sdfDay.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}

	public static int getDiffYear(String startTime, String endTime) {
		try {
			long aa = 0;
			int years = (int) (((sdfDay.parse(endTime).getTime() - sdfDay.parse(startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 *
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		Date beginDate = null;
		Date endDate = null;

		try {
			beginDate = sdfDay.parse(beginDateStr);
			endDate = sdfDay.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);

		return day;
	}

	/**
	 * 获取相隔的时间段
	* @Title: getDaytoDay
	* @Description: TODO
	* @param @param beginDate
	* @param @param endDate
	* @param @return
	* @return List<Date>
	* @throws
	 */
	public static List<Date> getDaytoDay(Date beginDate, Date endDate) {

		List<Date> list = new ArrayList<Date>();
		long daySub = getDaySub(beginDate, endDate);
		for (int i = 0; i < daySub; i++) {
			Date n = DateUtils.addDays(beginDate, i);
			list.add(n);
		}
		list.add(endDate);
		return list;
	}

	/**
	 * 获取相隔的时间段
	* @Title: getDaytoDay
	* @Description: TODO
	* @param @param beginDate
	* @param @param endDate
	* @param @return
	* @return List<Date>
	* @throws
	 */
	public static List<Date> getDaytoDay(String beginDateStr, String endDateStr) {

		Date beginDate = fomatDate(beginDateStr);
		Date endDate = fomatDate(endDateStr);
		List<Date> list = new ArrayList<Date>();
		long daySub = getDaySub(beginDate, endDate);
		for (int i = 0; i < daySub; i++) {
			Date n = DateUtils.addDays(beginDate, i);
			list.add(n);
		}
		list.add(endDate);
		return list;
	}

	/**
	 * 相隔天数
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	public static long getDaySub(Date beginDateStr, Date endDateStr) {
		long day = (endDateStr.getTime() - beginDateStr.getTime()) / (24 * 60 * 60 * 1000);
		// System.out.println("相隔的天数="+day);
		return day;
	}

	/**
	 * 得到n天之前的日期
	 *
	 * @param days
	 * @return
	 */
	public static String getBeforeDayDate(Integer days) {
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, days*-1); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		String dateStr = sdfDay.format(date);
		return dateStr;
	}

	/**
	 * 得到n天之后的时间
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(Integer days) {
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		String dateStr = sdfTime.format(date);
		return dateStr;
	}
	/**
	 * 得到n天之后的时间
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayDate(String d,String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.setTime(getDate(d));
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();
		String dateStr = sdfTime.format(date);

		return dateStr;
	}


	/**
	 * 得到n月之后的日期
	 * @return
	 */
	public static String getAfterMonthDate(int monthsInt) {

		Calendar canlendar = Calendar.getInstance();
		canlendar.add(Calendar.MONTH, monthsInt);
		canlendar.add(Calendar.DATE, -1);

		Date date = canlendar.getTime();
		String dateStr = sdfDay.format(date);

		return dateStr;
	}

	/**
	 * 得到n月之后的时间
	 * @return
	 */
	public static LocalDateTime getAfterMonthTime(int monthsInt) {

		Calendar canlendar = Calendar.getInstance();
		canlendar.add(Calendar.MONTH, monthsInt);
		canlendar.add(Calendar.DATE, -1);

		Date date = canlendar.getTime();
		String dateStr = sdfTime.format(date);

		DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(dateStr, timeDtf);
		return localDateTime;
	}

	/**
	 * 得到n天之后是周几
	 *
	 * @param days
	 * @return
	 */
	public static String getAfterDayWeek(String days) {
		int daysInt = Integer.parseInt(days);

		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	/**
	 * 指定时间n天后的时间搓
	 * @param time//指定时间字符串
	 * @param n//指定天数
	 */
	public static long getAfterDate(String time,Integer n) throws ParseException{
		Date date = sdfTime.parse(time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long timestamp = cal.getTimeInMillis(); //单位为毫秒
		return timestamp + (n*86400000);
	}

	/**
	 * 根据生日获取当前周岁
	 * @param birthday 生日
	 */
	public static long getNowAge(Date birthday){
		//当前时间
		Calendar curr = Calendar.getInstance();
		//生日
		Calendar born = Calendar.getInstance();
		born.setTime(birthday);
		//年龄 = 当前年 - 出生年
		int age = curr.get(Calendar.YEAR) - born.get(Calendar.YEAR);
		if(age <= 0){
			return 0;
		}
		//如果当前月份小于出生月份：age - 1
		//如果当前月份等于出生月份，且当前日小于出生日：age - 1
		int currMonth = curr.get(Calendar.MONTH);
		int currDay = curr.get(Calendar.DAY_OF_MONTH);
		int bornMonth = born.get(Calendar.MONTH);
		int bornDay = born.get(Calendar.DAY_OF_MONTH);
		if((currMonth < bornMonth) || (currMonth == bornMonth && currDay <= bornDay)){
			age--;
		}
		return age < 0 ? 0 : age;
	}


	/**
	 * 获取当前是今年的第几周
	 */
	public static int getManyWeeks(){
		WeekFields week = WeekFields.of(Locale.getDefault());
		int i = LocalDate.now().get(week.weekOfWeekBasedYear());
		return i;
	}

	/**
	 * 获取当前是否是单周
	 */
	public static boolean getSingleWeek(){
		WeekFields week = WeekFields.of(Locale.getDefault());
		int i = LocalDate.now().get(week.weekOfWeekBasedYear());
		return i % 2 == 1 ? true : false;
	}

	/**
	 * 获取当前是周几
	 */
	public static Map<String,String> getWeeksDays(){
		int value = LocalDate.now().getDayOfWeek().getValue();
		Map<String,String> map = new HashMap();
		switch(value){
			case 1 : map.put("one","周一"); map.put("two","星期一"); map.put("three","1"); map.put("four","一"); break;
			case 2 : map.put("one","周二"); map.put("two","星期二"); map.put("three","2"); map.put("four","二"); break;
			case 3 : map.put("one","周三"); map.put("two","星期三"); map.put("three","3"); map.put("four","三"); break;
			case 4 : map.put("one","周四"); map.put("two","星期四"); map.put("three","4"); map.put("four","四"); break;
			case 5 : map.put("one","周五"); map.put("two","星期五"); map.put("three","5"); map.put("four","五"); break;
			case 6 : map.put("one","周六"); map.put("two","星期六"); map.put("three","6"); map.put("four","六"); break;
			case 7 : map.put("one","周日"); map.put("two","星期日"); map.put("three","7"); map.put("four","日"); break;
			default : break;
		}
		return map;
	}

	/**
	 * 字符串转LocalDateTime
	 * @param timeStr
	 * @return
	 */
	public static LocalDateTime stringLocalDateTime(String timeStr){
		DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.parse(timeStr, timeDtf);
		return localDateTime;
	}

	/**
	 * LocalDateTime转字符串
	 * @param localDateTime
	 * @return
	 */
	public static String localDateTimeString(LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDateTime.format(formatter);
	}

	/**
	 * LocalDateTime相隔天数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long apartDays(LocalDateTime startTime,LocalDateTime  endTime) {
		Duration duration = Duration.between(startTime,endTime);
		return duration.toDays();
	}

	/**
	 * LocalDateTime n天后的时间
	 * @param localDateTime
	 * @return
	 */
	public static LocalDateTime laterDayTime(LocalDateTime localDateTime,long day) {
		LocalDateTime time = localDateTime.plus(day, ChronoUnit.DAYS);
		return time;
	}

	/**
	 * LocalDateTime 转 Date
	 * @param time
	 * @return
	 */
	public static Date LocalDateTimeChangeDate(LocalDateTime time) {
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = time.atZone(zone).toInstant();
		return Date.from(instant);
	}

}
