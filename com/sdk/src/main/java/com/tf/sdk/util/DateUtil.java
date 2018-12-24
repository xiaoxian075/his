package com.tf.sdk.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	
	public static final String DATA_FORMAT = "yyyy-MM-dd";
	public static final String DATA_FORMAT_WITHOUT_YEAR = "MM-dd";

	public static final String DATA_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATA_TIME_FORMAT2 = "yyyyMMddHHmmss";
	
	private static final TimeZone beijingTimeZone;
	
	static {
		int newTime=(int)(8 * 60 * 60 * 1000);
		//TimeZone timeZone;
		String[] ids = TimeZone.getAvailableIDs(newTime);
		if (ids.length == 0) {
			beijingTimeZone = TimeZone.getDefault();
		} else {
			beijingTimeZone = new SimpleTimeZone(newTime, ids[0]);
		}
	}
	
	public static long currentTime() {
		return System.currentTimeMillis();
	}

	public static Calendar getCalendarInstance(){
		TimeZone.setDefault(beijingTimeZone);
		Calendar calendar = Calendar.getInstance(beijingTimeZone);
		return calendar;
	}
	
	public static String getCurrentTime() {
		return dateToStr(System.currentTimeMillis(), null);
	}
	
//	public static TimeZone getBeijing() {
//		int newTime=(int)(8 * 60 * 60 * 1000);
//		TimeZone timeZone;
//		String[] ids = TimeZone.getAvailableIDs(newTime);
//		if (ids.length == 0) {
//			timeZone = TimeZone.getDefault();
//		} else {
//			timeZone = new SimpleTimeZone(newTime, ids[0]);
//		}
//		return timeZone;
//	}
	
	/** 
	 * 时间戳转换成日期格式字符串 
	 * @param timestamp 精确到毫秒
	 * @return
	*/  
	public static String dateToStr(long timestamp) {
		try {
			if(timestamp <= 0) {  
				return null;  
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat(DATA_TIME_FORMAT);  
			sdf.setTimeZone(beijingTimeZone);
			return sdf.format(new Date(timestamp));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String dateToStr(Date date) {
		try {
			//Date date = new Date();
	        SimpleDateFormat sf = new SimpleDateFormat(DATA_TIME_FORMAT);
			sf.setTimeZone(beijingTimeZone);
	        return sf.format(date);
		} catch(Exception e) {
			return "";
		}
	}	

	/** 
	 * 时间戳转换成日期格式字符串 
	 * @param timestamp 精确到毫秒
	 * @param format 格式 
	 * @return 
	*/  
	public static String dateToStr(long timestamp, String format) {
		try {
			if(timestamp <= 0) {  
				return null;  
			}  
			if(format == null || format.isEmpty()){
				format = DATA_TIME_FORMAT;
			}   
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setTimeZone(beijingTimeZone);
			return sdf.format(new Date(timestamp));
		} catch (Exception e) {
			return null;
		}
	}

    /**
	 * 日期格式字符串转换成时间戳 
	 * @param str 字符串日期
	 * @param format 如：yyyy-MM-dd HH:mm:ss
	 * @return 
	 */  
	public static long strToDate(String str,String format){
		if (str == null || str.isEmpty()) {
			return 0;
		}
		if(format == null || format.isEmpty()){
			format = DATA_TIME_FORMAT;
		} 

		try {  
			SimpleDateFormat sdf = new SimpleDateFormat(format);  
			sdf.setTimeZone(beijingTimeZone);
			Date date = sdf.parse(str);
			return date.getTime();
		} catch (Exception e) {  
			logger.error("strToDate str=" + str + ", format=" + format, e);
			return 0; 
		} 
	}
	
	/**
	 * 一天开始时间戮（日期格式字符串转换成时间戳 ）
	 * @param str 字符串日期
	 * @return
	 */  
	public static long strToStartDate(String str){
		if (str == null || str.isEmpty()) {
			return 0;
		}
		if(str.length() == 10){
			str += " 00:00:00";
		}
		
		try {  
			SimpleDateFormat sdf = new SimpleDateFormat(DATA_TIME_FORMAT);  
			sdf.setTimeZone(beijingTimeZone);
			return sdf.parse(str).getTime();
		} catch (Exception e) {  
			return 0; 
		} 
	}
	
	/**
	 * 一天结束时间戮（日期格式字符串转换成时间戳 ）
	 * @param str 字符串日期
	 * @return
	 */  
	public static long strToEndDate(String str){
		if (str == null || str.isEmpty()) {
			return 0;
		}
		if(str.length() == 10){
			str += " 23:59:59";
		} 
		
		try {  
			SimpleDateFormat sdf = new SimpleDateFormat(DATA_TIME_FORMAT);  
			sdf.setTimeZone(beijingTimeZone);
			return sdf.parse(str).getTime() + 999;
		} catch (Exception e) {  
			return 0; 
		} 
	}


    /**
     * 根据生日时间戳获取年龄
     * @param timestamp
     * @return
     */
//    public static int getAgeByBirth(long timestamp) {
//        Date birthday = new Date(timestamp);
//        int age = 0;
//        try {
//            Calendar now = Calendar.getInstance();
//            now.setTime(new Date());// 当前时间
//
//            Calendar birth = Calendar.getInstance();
//            birth.setTime(birthday);
//
//            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
//                age = 0;
//            } else {
//                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
//                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
//                    age += 1;
//                }
//            }
//            return age;
//        } catch (Exception e) {//兼容性更强,异常后返回数据
//            return 0;
//        }
//    }

    /**
     * 根据生日时间戳获取年龄描述（大于24个月显示岁，大于2个月显示月，大于0天显示天）
	 * eg:3个月
     * @param timestamp
     * @return
     */
    public static String getAgeStringByBirth(long timestamp) {
        Date birthday = new Date(timestamp);
        String result = "0天";
        try {
            Calendar now = getCalendarInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = getCalendarInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                result = "0天";
            } else {

				Calendar d2m = getCalendarInstance();
				d2m.setTime(birth.getTime());
				d2m.set(Calendar.MONTH, birth.get(Calendar.MONTH) + 2);
				Calendar d2y = getCalendarInstance();
				d2y.setTime(birth.getTime());
				d2y.set(Calendar.YEAR, birth.get(Calendar.YEAR) + 2);


				int ageInterval = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
				int monthInterval = (now.get(Calendar.YEAR)*12+now.get(Calendar.MONTH))-(birth.get(Calendar.YEAR)*12+birth.get(Calendar.MONTH));
                Long dayInterval = (now.getTimeInMillis()-birth.getTimeInMillis())/ (1000 * 60 * 60 * 24);
                if(now.compareTo(d2m)>0){
                	if(now.compareTo(d2y)>0){
						result = ageInterval+"岁";
					}else{
						result = monthInterval+"月";
					}
				}else {
                	result = dayInterval+"天";
				}
            }
            return result;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return result;
        }
    }

	/** 
	 * 取得当前时间戳（精确到秒） 
	 * @return 
	 */  
	public static String timeStamp(){  
		long time = System.currentTimeMillis();
		String t = String.valueOf(time/1000);  
		return t;  
	}  

	public static void main(String[] args) throws ParseException {

//	    String s = "2018-09-01 00:00:00";
//        SimpleDateFormat sdf = new SimpleDateFormat(DATA_TIME_FORMAT);
//		Date birth = null;
//		try {
//			birth = sdf.parse(s);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		System.out.println(getIntervalDays(27837648422000l));

//		long t = getTimeBeforeDays(0);
//		System.out.println(dateToStr(t));
	}

	public static long getTimeByMonth(int month) {
		return getTimeByDay(month*30);
	}
	
	public static long getTimeByDay(int day) {
		Calendar calendar = getCalendarInstance();

		calendar.set(java.util.Calendar.HOUR_OF_DAY, 23);
		calendar.set(java.util.Calendar.MINUTE, 59);
		calendar.set(java.util.Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND,0);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
		return calendar.getTime().getTime();
	}

	public static long getTimeBeginByDay(int day) {
		Calendar now = getCalendarInstance();
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		now.set(Calendar.HOUR_OF_DAY,0);
		now.set(Calendar.MINUTE,0);
		now.set(Calendar.SECOND,0);
		now.set(Calendar.MILLISECOND,0);
		return now.getTime().getTime();
	}

	public static long getTodayBeginTime() {
		Calendar now = getCalendarInstance();
		now.set(Calendar.HOUR_OF_DAY,0);
		now.set(Calendar.MINUTE,0);
		now.set(Calendar.SECOND,0);
		now.set(Calendar.MILLISECOND,0);
		return now.getTime().getTime();
	}
	
	/**
	 * 剩余时间（单位/天）
	 * @param timestamp
	 * @return
	 */
	public static int getIntervalDays(long timestamp) {
		long currentTime = System.currentTimeMillis();
		if(timestamp <= 0){
			return -1;
		}
		java.util.Calendar calst = getCalendarInstance();
		java.util.Calendar caled = getCalendarInstance();
		calst.setTimeInMillis(currentTime);
		caled.setTimeInMillis(timestamp);
		//设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		//得到两个日期相差的天数
		BigDecimal days = new BigDecimal(caled.getTime().getTime() -calst
				.getTime().getTime() ).divide(new BigDecimal(1000 * 3600 * 24),0, BigDecimal.ROUND_HALF_UP);
	
		return days.intValue();
	}

	/**
	 * 返回几天前0点0分0秒的时间戳
	 * @param days
	 * @return
	 */
	public static long getTimeBeforeDays(int days) {
		long currentTime = System.currentTimeMillis();
		if(days < 0){
			return 0L;
		}
		if(days==0){
			java.util.Calendar calst = getCalendarInstance();
			calst.setTimeInMillis(currentTime);
			calst.set(java.util.Calendar.DAY_OF_YEAR, calst.get(java.util.Calendar.DAY_OF_YEAR)-days);
			//设置时间为0时
			calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
			calst.set(java.util.Calendar.MINUTE, 0);
			calst.set(java.util.Calendar.SECOND, 0);
			calst.set(Calendar.MILLISECOND,0);
			return calst.getTimeInMillis();
		}else {
			return currentTime-new BigDecimal(24*60*60*1000).multiply(new BigDecimal(days)).longValue();
		}
	}


	
	public static long dateToLong(Date date) {
		try {
			return date.getTime();
		} catch(Exception e) {
			return 0;
		}
	}

	public static List<String> lastDaysArray(Integer days,String dataFormat) {
		List<String> result = new ArrayList<>();
		for(int i=days-1;i>=0;i--){
			java.util.Calendar calst = getCalendarInstance();
			calst.set(java.util.Calendar.DAY_OF_YEAR, calst.get(java.util.Calendar.DAY_OF_YEAR)-i);
			result.add(dateToStr(calst.getTimeInMillis(),dataFormat));
		}
		return result;
	}

	/**
	 * 获取指定时间几个月后的时间
	 * @param beginTime
	 * @param month
	 * @return
	 */
	public static long getTimeByIntervalMonths(Long beginTime, int month){
		Calendar calendar = getCalendarInstance();
		if(beginTime!=null){
			// 设置开始时间
			calendar.setTimeInMillis(beginTime);
			calendar.set(java.util.Calendar.HOUR_OF_DAY, 23);
			calendar.set(java.util.Calendar.MINUTE, 59);
			calendar.set(java.util.Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND,0);
		}
		// 设置为几个月后
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
		return (calendar.getTimeInMillis());
	}
}
