package com.centit.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName: TimeUtil.java
 * @Description: 日期处理函数工具类
 * @author: zhu_hj
 * @date: 2018年5月2日 下午2:55:39
 */
public class TimeUtil {
	
	/**
	 * 得到本年的第一一天
	 */
	public static String getYearFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar
				.getActualMinimum(Calendar.DAY_OF_YEAR));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(calendar.getTime());
	}
	/**
	 * 得到本年的最后一天
	 */
	public static String getYearLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar
				.getActualMaximum(Calendar.DAY_OF_YEAR));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(calendar.getTime());
	}
  	
  	/**
	 * 得到本周的第一天
	 */
	public static String getWeekFirstDay(String date,String fmt) {
		SimpleDateFormat df = new SimpleDateFormat(fmt.equals("")?"yyyy-MM-dd":fmt);
		Calendar calendar = Calendar.getInstance();
		try {
			if(!date.equals(""))
				calendar.setTime(df.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK)+1);
		return df.format(calendar.getTime());
	}

	/**
	 * 得到本周的最后一天
	 */
	public static String getWeekLastDay(String date,String fmt) {
		SimpleDateFormat df = new SimpleDateFormat(fmt.equals("")?"yyyy-MM-dd":fmt);
		Calendar calendar = Calendar.getInstance();
		try {
			if(!date.equals(""))
				calendar.setTime(df.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK)+1);
		return df.format(calendar.getTime());
	}
	
	/**
	 * 得到本月的第一天
	 */
	public static String getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(5, calendar.getActualMinimum(5));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(calendar.getTime());
	}

	/**
	 * 得到本月的最后一天
	 */
	public static String getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(5, calendar.getActualMaximum(5));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(calendar.getTime());
	}
	
  	/**
	 * 获取当前日期
	 */
	public static String getDate(){
		return getTime("yyyy-MM-dd");
	}
	public static String getDateTime(){
		return getTime("yyyy-MM-dd HH:mm:ss");
	}
	public static String getTime(String lx){
		if(lx == null || lx.equals(""))
			lx = "yyyy-MM-dd";
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(new Date().getTime());
		SimpleDateFormat dateFormat = new SimpleDateFormat(lx);
		return dateFormat.format(c.getTime());
	}
	
	/**
	 * 获取当前日期间隔ts的日期
	 */
	public static String getTime(String lx, int ts) {
		if(lx == null || lx.equals(""))
			lx = "yyyy-MM-dd";
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + ts);
		SimpleDateFormat dateFormat = new SimpleDateFormat(lx);
		return dateFormat.format(c.getTime());
	}
	
	/**
	 * 获取mysql中的时间戳
	 */
	public static Integer getUnixTimestamp(){
		return ((Long)(System.currentTimeMillis()/1000)).intValue();
	}
		
	/**
	 * 将mysql中的时间戳转换成日期格式字符串
	 */
	public static String transUnixTimestamp(Integer time,String lx){
		if(time==null||time==0)
			return "";
		Long timestamp = 1L*time*1000; 
		if(StringUtils.isEmpty(lx))
			lx = "yyyy-MM-dd HH:mm:ss";
		String date = new SimpleDateFormat(lx).format(new Date(timestamp));
		return date;
	}
	public static String transUnixTimestamp(Integer time){
		return transUnixTimestamp(time, null);
	}

	/**
	 * 将日期字符串转换成mysql中的时间戳
	 */
	public static Integer getUnixTimestamp_(String date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = df.parse(date);
			return ((Long)(d.getTime()/1000)).intValue();
		} catch (Exception e) {}
		return 0;
	}	
		
	
	public static Date parseTimeString2Date(String timeString) {
		if ((timeString == null) || (timeString.equals(""))) {
			return null;
		}
		Date date = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = new Date(dateFormat.parse(timeString).getTime());
		} catch (ParseException e) {
			 e.printStackTrace();
		}
		return date;
	}
	
	public static String convertDate2String(Date date, String pattern) {
		if (date == null)
			return null;
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
	 
	public static int getYear(String timeString) {
		String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd");
		return Integer.parseInt(timeStr.substring(0, 4));
	}
	
	public static int getMonth(String timeString) {
		String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd");
		return Integer.parseInt(timeStr.substring(5, 7));
	}
	
	public static int getDay(String timeString) {
		String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd");
		return Integer.parseInt(timeStr.substring(8, 10));
	}
 
	public static  int getHour(String timeString) {
		String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd HH:mm:ss");
		return Integer.parseInt(timeStr.substring(11, 13));
	}
 
	public static int getMinute(String timeString) {
		String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd HH:mm:ss");
		return Integer.parseInt(timeStr.substring(14, 16));
	}
 
	public static  int getSecond(String timeString) {
		String timeStr = convertDate2String(parseTimeString2Date(timeString), "yyyy-MM-dd HH:mm:ss");
		return Integer.parseInt(timeStr.substring(17, 19));
	}
}
