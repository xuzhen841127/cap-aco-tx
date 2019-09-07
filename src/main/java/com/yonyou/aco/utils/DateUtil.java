package com.yonyou.aco.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * <p> 概述：日期格式化工具类
 * <p> 功能：各种日期转化及计算
 * <p> 作者：徐真
 * <p> 创建时间：2019年9月2日
 * <p> 类调用特殊情况：无
 */
public class DateUtil {
	/**
	 * 静态方法，判断字符是否为合法的日期格式（能够转换为正确的日期类型）
	 * @param date		待判断的日期字符
	 * @param format	日期格式，如：yyyy-MM-dd
	 * @return			是否合法
	 */
	public static boolean isValidDate(String date, String format){
		try {
			DateFormat formater =  new SimpleDateFormat(format);
			formater.setLenient(false);	//严格判断格式，如2009-02-29认为不合法，不转为2009-03-01
			Date temp = formater.parse(date);
			if (null == temp || ! date.equals(formater.format(temp))) {
				return false;
			} else {
				return true;
			}
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 按照格式yyyy-MM-dd比较两个日期的大小
	 * @param dateA
	 * @param dateB
	 * @return			>0 dateA>dateB; =0 dateA=dateB; <0 dateA<dateB
	 */
	public static int compareToDate(Date dateA, Date dateB) {
		 String a = formatDate(dateA, "yyyy-MM-dd");
		 String b = formatDate(dateB, "yyyy-MM-dd");
		 
		 return a.compareTo(b);
	}

	/**
	 * 将日期类型转换成指定格式的字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		DateFormat formater = new SimpleDateFormat(format);
		return formater.format(date);
	}

	/**
	 * 将日期类型转换成制定格式的字符串.
	 * @param  timestamp
	 * @return string
	 */
	public static String formatTimestamp(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(timestamp);
	}

	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 将日期类型转换成制定格式的字符串.
	 * @param  timestamp
	 * @return string
	 */
	public static String formatTimestampForDate(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(timestamp);
	}

	/**
	 * 将字符串按照指定的格式转换为Date型对象
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date parseDate(String str, String format) {
		DateFormat formater = new SimpleDateFormat(format);
		try {
			return formater.parse(str);
		} catch (Exception e) {
			return null;
		}
	}

	/***
	 * 方法: 格式化当前日期
	 * @param format 格式化要求的时间样式
	 * @return
	 */
	public static String parseDateForFormat(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date day=new Date();
		return sdf.format(day);
	}

	/**
	 * 根据指定年月日获取指定格式的日期字符串
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param format
	 * @return
	 */
	public static String getDateString(int year, int month, int day,
			String format) {
		return formatDate(getDate(year, month, day), format);
	}

	/**
	 * 根据指定格式获取当前日期
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurDate(String format) {
		return formatDate(new Date(), format);
	}

	/**
	 * 获取默认19位时间
	 * @return
	 */
	public static String getCurDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 获取四位的当前年份
	 * 
	 * @return String
	 */
	public static String getCurYear() {
		return getCurDate("yyyy");
	}

	/**
	 * 获取两位的当前月份
	 * 
	 * @return
	 */
	public static String getCurMonth() {
		return getCurDate("MM");
	}

	/**
	 * 获取两位的当前日
	 * 
	 * @return
	 */
	public static String getCurDay() {
		return getCurDate("dd");
	}

	/**
	 * 根据输入的字符串，获取其上个月的int型日期
	 * 
	 * @param str
	 * @return
	 */
	public static int[] getLastMonthValue(String str, String format) {
		return getDateIntValue(str, format, -1);
	}

	/**
	 * 根据输入的字符串，获取其int型的年、月、日
	 * 
	 * @param str
	 * @param format
	 * @return
	 */
	public static int[] getDateIntValue(String str, String format) {
		return getDateIntValue(str, format, 0);
	}

	/**
	 * 根据输入的字符串，获取其前后整数个月的int型日期
	 * 
	 * @param str
	 * @param format
	 * @param step
	 *            如果需要获取上个月的日期，step赋值为-1
	 * @return
	 */
	public static int[] getDateIntValue(String str, String format, int step) {
		int[] r = null;
		Date d = parseDate(str, format); // 将字符串转换为Date型对象
		if (d != null) {
			r = new int[3];
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + step);
			r[0] = calendar.get(Calendar.YEAR); // 年份
			int month = calendar.get(Calendar.MONTH); // 月份
			month++; // 实际月份是calendar的月份+1
			r[1] = month;
			int day = calendar.get(Calendar.DAY_OF_MONTH); // 日期
			r[2] = day;
		}
		return r;
	}
	/**
	 * 根据输入的字符串，获取其前后整数个月的String型日期
	 * 
	 * @param dateStr 传入的时间字符串
	 * @param dateStrFormat 传入时间字符串的格式
	 * @param returnFormat 返回时间字符串的格式
	 * @param step 如果需要获取上个月的日期，step赋值为-1
	 * @return
	 */
	public static String getDateStringValue(String dateStr, String dateStrFormat, String returnFormat, int step) {
		int[] r = null;
		Date d = parseDate(dateStr, dateStrFormat); // 将字符串转换为Date型对象
		if (d != null) {
			r = new int[3];
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + step);
			r[0] = calendar.get(Calendar.YEAR); // 年份
			int month = calendar.get(Calendar.MONTH); // 月份
			month++; // 实际月份是calendar的月份+1
			r[1] = month;
			int day = calendar.get(Calendar.DAY_OF_MONTH); // 日期
			r[2] = day;
		}
		return getDateString(r[0],r[1],r[2],returnFormat);
	}

	/**
	 * 获取当前指定分钟之前（后）的日期时间 minutes为正数时，获取当前时间之后的日期时间； minutes为负数时，获取当前时间之前的日期时间。
	 * 
	 * @param minutes
	 * @return
	 */
	public static Date getDate(int minutes) {
		Calendar calendar = Calendar.getInstance(); // 日历对象
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minutes);
		return DateUtils.truncate(calendar.getTime(), Calendar.DATE);
	}

	/**
	 * 根据指定年月日，将指定字符串日期转换成Date型
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date getDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();	// 日历对象
		calendar.set(Calendar.YEAR, year); 			// 设置年份
		calendar.set(Calendar.MONTH, month - 1);	// 设置月份（本月）
		calendar.set(Calendar.DAY_OF_MONTH, day);	// 设置指定日期
		return DateUtils.truncate(calendar.getTime(), Calendar.DATE);
	}

	/**
	 * 根据指定的年月，获取其下个月的日期
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getNextMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();	// 日历对象
		calendar.set(Calendar.YEAR, year); 			// 设置年份
		calendar.set(Calendar.MONTH, month); 		// 设置月份（下个月）
		calendar.set(Calendar.DAY_OF_MONTH, 1); 	// 设置第一天
		return DateUtils.truncate(calendar.getTime(), Calendar.DATE);
	}

	/**
	 * 根据指定的年月，获取其月份第一天与最后一天（Date型）
	 * 
	 * @param year
	 * @param month
	 * @return 数组中，第一个元素是第一天，第二个元素是最后一天
	 */
	public static Date[] getStartEndDate(int year, int month) {
		Date[] date = new Date[2];
		Calendar calendar = Calendar.getInstance(); // 日历对象
		calendar.set(Calendar.YEAR, year); // 设置年份
		calendar.set(Calendar.MONTH, month); // 设置月份（下个月）
		calendar.set(Calendar.DAY_OF_MONTH, 1); // 设置下月第一天
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 本月最后一天
		calendar.set(Calendar.HOUR_OF_DAY, 23); // 本天最后一小时
		calendar.set(Calendar.MINUTE, 59); // 59分
		calendar.set(Calendar.SECOND, 59); // 59秒
		calendar.set(Calendar.MILLISECOND, 997); // 毫秒
		/*
		 * 因为datetime数据类型具有精度。 必须选择23:59:59.997的原因在于，datetime数据无法保证毫秒级别的精度。
		 * 相反，datetime数据的精度在3.33毫秒内。 使用23:59:59.999这个确切的时间值是不行的，
		 * 因为该值将被舍入到最接近的时间值，即第二天的 12:00:00.000 A.M。
		 */
		date[1] = calendar.getTime(); // 截止日期
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 再次设置第一天（本月）。必须这么写，否则会得到下个月的第一天
		calendar.set(Calendar.HOUR_OF_DAY, 0); // 本天开始
		calendar.set(Calendar.MINUTE, 0); // 0分
		calendar.set(Calendar.SECOND, 0); // 0秒
		calendar.set(Calendar.MILLISECOND, 0); // 毫秒
		date[0] = calendar.getTime(); // 开始日期
		return date;
	}

	/**
	 * 获取指定年月第一天的日期，返回Date型数据
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getStartDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year); // 设置年份
		calendar.set(Calendar.MONTH, month - 1); // 设置月份
		calendar.set(Calendar.DAY_OF_MONTH, 1); // 下月第一天
		return calendar.getTime();
	}

	/**
	 * 获取指定年月第一天的日期，根据录入的格式，获取String型数据
	 * 
	 * @param year
	 * @param month
	 * @param formatStr
	 * @return
	 */
	public static String getStartDate(int year, int month, String formatStr) {
		return formatDate(getStartDate(year, month), formatStr);
	}

	/**
	 * 获取指定年月最后一天的日期，返回Date型数据
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getEndDate(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year); // 设置年份
		calendar.set(Calendar.MONTH, month); // 设置月份（指定月份的下个月）
		calendar.set(Calendar.DAY_OF_MONTH, 1); // 下月第一天
		calendar.add(Calendar.DAY_OF_MONTH, -1); // 本月最后一天
		return calendar.getTime();
	}

	/**
	 * 获取指定年月最后一天的日期，根据录入的格式，获取String型数据
	 * 
	 * @param year
	 * @param month
	 * @param formatStr
	 * @return
	 */
	public static String getEndDate(int year, int month, String formatStr) {
		return formatDate(getEndDate(year, month), formatStr);
	}

	/**
	 * 获取开始与截至日期之间的统计天数<br>
	 * 包含两头的日期（即2008-01-01至2008-01-02的天数为2）
	 * 
	 * @param startDate
	 * @param endDate
	 * @param formatStr
	 * @return
	 */
	public static int getStatisticDays(String startDate, String endDate,
			String formatStr) {
		Calendar start = Calendar.getInstance();
		start.setTime(parseDate(startDate, formatStr)); // 开始日期
		Calendar end = Calendar.getInstance();
		end.setTime(parseDate(endDate, formatStr)); // 截至日期
		Calendar temp ;
		if (start.after(end)) { // 如果开始>结束，则转换两者位置
			temp = start;
			start = end;
			end = temp;
		}
		return (int) (1 + ((end.getTimeInMillis() - start.getTimeInMillis()) / 1000 / 60 / 60 / 24));
	}
	
	/**
	 * 获取开始与截至日期之间的统计天数<br>
	 * 不包含起始日期，包含结束日期（即2008-01-01至2008-01-02的天数为1）
	 * 
	 * @param startDate
	 * @param endDate
	 * @param formatStr
	 * @return
	 */
	public static int getDays(String startDate, String endDate,
			String formatStr) {
		Calendar start = Calendar.getInstance();
		start.setTime(parseDate(startDate, formatStr)); // 开始日期
		Calendar end = Calendar.getInstance();
		end.setTime(parseDate(endDate, formatStr)); // 截至日期
		Calendar temp ;
		if (start.after(end)) { // 如果开始>结束，则转换两者位置
			temp = start;
			start = end;
			end = temp;
		}
		return (int) ((end.getTimeInMillis() - start.getTimeInMillis()) / 1000 / 60 / 60 / 24);
	}

	/**
	 * 静态方法，得到两个日期之间的天数；
	 * 不包含起始日期，包含结束日期（即2008-01-01至2008-01-02的天数为1）
	 * 
	 * @param startDate	开始日期
	 * @param endDate	结束日期
	 * @return			开始日期和结束日期之间的天数；若开始日期大于结束日期则返回相应负数值
	 */
	public static int getDays(Date s_date, Date e_date) {
		int flag = 1;	//正负标志
		Date startDate = null;
		Date endDate = null;
		if (s_date.after(e_date)) {
			Date temp = s_date;
			startDate = e_date;
			endDate = temp;
			flag = -1;
		}
		
		int days;

		/**
		 * 方式一：利用自1970年以来的秒数计算，方法简单但局限于1970年以后的日期
		 * 暂不使用
		 */
		//days = (int) ((getDateWithFirstTime(endDate).getTime() - getDateWithFirstTime(startDate).getTime()) /(1000*60*60*24));
		
		/**
		 * 方式二：利用所在当年天数计算，再加上年差
		 */
		Calendar	startCal	= Calendar.getInstance(), 
					endCal		= Calendar.getInstance();
		startCal.setTime(startDate);
		endCal.setTime(endDate);

		days = endCal.get(Calendar.DAY_OF_YEAR) - startCal.get(Calendar.DAY_OF_YEAR);
		
		int endYear = endCal.get(Calendar.YEAR);
		while (startCal.get(Calendar.YEAR) != endYear) {
			days += startCal.getActualMaximum(Calendar.DAY_OF_YEAR);
			startCal.add(Calendar.YEAR, 1);
		}
		
		return days * flag;
	}

	/**
	 * 静态方法，得到两个日期之间周末的天数（周六和周日）
	 * @param startDate		开始日期
	 * @param endDate		结束日期
	 * @return				开始日期和结束日期之间的周末天数；若开始日期大于结束日期则返回相应负数值
	 */
	public static int getWeekendDays(Date startDate, Date endDate){
		int weekendDays ;
		
		//中间相隔的完整周数，乘以每周两天周末
		Date	startMonday	= getNextMonday(startDate),
				endMonday	= getThisMonday(endDate);
		int tempDays = getDays(startMonday, endMonday);
		weekendDays = tempDays / 7 * 2;		
		
		//处理不完整的周，进行相应调整
		Calendar	startCal	= Calendar.getInstance(), 
					endCal		= Calendar.getInstance();
		startCal.setTime(startDate);
		endCal.setTime(endDate);
		int adjust = 0;
		//起始周
		startCal.add(Calendar.DAY_OF_WEEK, 1);	//起始日不计，算尾不算头
		switch (startCal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.MONDAY:
			case Calendar.TUESDAY:
			case Calendar.WEDNESDAY:
			case Calendar.THURSDAY:
			case Calendar.FRIDAY:
			case Calendar.SATURDAY:
				adjust += 2;
				break;
	
			case Calendar.SUNDAY:
				adjust += 1;
				break;
	
			default:
				break;
		}
		//结束周
		switch (endCal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SATURDAY:
				adjust += 1;
				break;
	
			case Calendar.SUNDAY:
				adjust += 2;
				break;
	
			default:
				break;
		}
		
		if (weekendDays < 0) {	//起始日期大于结束日期的情况
			weekendDays -= adjust;
		} else {
			weekendDays += adjust;
		}
		
		return weekendDays;
	}
	
	
	/**
	 * 静态方法，得到给定日期的下周周一
	 * @param date			给定日期
	 * @return				给定日期的下周周一
	 */
	public static Date getNextMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		while (Calendar.MONDAY != cal.get(Calendar.DAY_OF_WEEK)) {
			cal.add(Calendar.DAY_OF_WEEK, 1);
		}
		
		return cal.getTime();
	}

	/**
	 * 静态方法，得到给定日期的本周周一
	 * @param date			给定日期
	 * @return				给定日期的本周周一
	 */
	public static Date getThisMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		while (Calendar.MONDAY != cal.get(Calendar.DAY_OF_WEEK)) {
			cal.add(Calendar.DAY_OF_WEEK, -1);
		}
		
		return cal.getTime();
	}
	
	/**
	 * 获取指定年月下个月的日期
	 * 
	 * @param year
	 * @param month
	 * @param formatStr
	 * @return
	 */
	public static String getNextMonthStr(int year, int month, String formatStr) {
		return formatDate(getNextMonth(year, month), formatStr);
	}

	/**
	 * 获取date的第n天后的日期（使用getAfterDaysDate(Date date, int n)函数，兼容历史版本）
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getAfterDate(Date date, int n) {
		return getAfterDaysDate(date, n);
	}
	
	/**
	 * 获取date的第n天后的日期
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getAfterDaysDate(Date date, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, n);
		return c.getTime();
	}
	
	/**
	 * 获取date的第n月后的日期
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getAfterMonthsDate(Date date, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, n);
		return c.getTime();
	}
	
	/**
	 * 获取date的第n年后的日期
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date getAfterYearsDate(Date date, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, n);
		return c.getTime();
	}
	
	/**
	 * 清除日期中的时间，置为零时：例如2009-08-08 08:10:51 456清除以后结果为2009-08-08 00:00:00 000；
	 * 主要用途为日期比较中使用，避免出现因为时间问题出现少一天的情况
	 * @param date
	 * @return
	 */
	public static Date getDateWithFirstTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 清除日期中的时间，置为下一日零时以前：例如2009-08-08 08:10:51 456清除以后结果为2009-08-08 23:59:59 999；
	 * 主要用途为日期比较中使用，避免出现因为时间问题出现少一天的情况
	 * @param date
	 * @return
	 */
	public static Date getDateWithLastTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}
	
	/**
	 * 获取服务器时间
	 */
	public static String getServerDataTime(boolean haveFenGe){
       //Date dattoday = new Date();
       Calendar calendar = Calendar.getInstance();
       //calendar.setTime(dattoday);
       String sDataTime ;
       if(haveFenGe){
          // Calendar _tmp = calendar;
           sDataTime = calendar.get(1) + "-" + fillZeroBeforeNum(String.valueOf(calendar.get(2) + 1)) + "-" + fillZeroBeforeNum(String.valueOf(calendar.get(5))) + " " + fillZeroBeforeNum(String.valueOf(calendar.get(11))) + ":" + fillZeroBeforeNum(String.valueOf(calendar.get(12))) + ":" + fillZeroBeforeNum(String.valueOf(calendar.get(13)));
       } else{
           //Calendar _tmp1 = calendar;
           sDataTime = calendar.get(1) + "" + fillZeroBeforeNum(String.valueOf(calendar.get(2) + 1)) + "" + fillZeroBeforeNum(String.valueOf(calendar.get(5))) + "" + fillZeroBeforeNum(String.valueOf(calendar.get(11))) + "" + fillZeroBeforeNum(String.valueOf(calendar.get(12))) + "" + fillZeroBeforeNum(String.valueOf(calendar.get(13)));
       }
       return sDataTime;
   }

   public static String fillZeroBeforeNum(String num){
	   if(num.length()>1)return num;	   
	   return "0"+num;
   }
  
   /**
    * 得到现在时间
    * @return
    */
   public static Date getDate(){
	   return new Date();
   }

   /**
    * 得到指定字符的时间型
    * @param str
    * @return
    */
   public  static Date getDate(String str){
	   try{
		   Date date = null;  
	        SimpleDateFormat formater = new SimpleDateFormat();  
	        formater.applyPattern("yyyy-MM-dd");  
	        try {  
	            date = formater.parse(str);  
	        } catch (ParseException e) {  
	            e.printStackTrace();  
	        }  
	        return date;
	   }catch(Exception e)
	   {
		   return null;
	   }
   }

   /**
	 * 与当前时间比较，得到多少年，多少月，多少天前,多少小时前，多小分钟前 add by 周威 2018-01-02
	 * 
	 * @param dateStr 与当前时间比较的日期值（字符串格式）
	 * @return 格式化之后的字符串
	 */
	public static String getDateCompareNow(String dateStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String strdate = "";

		if (dateStr.isEmpty()) {
			return strdate;
		}

		try {
			// 解析日期参数成Calendar对象
			Date date = df.parse(dateStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);

			// 获取当前系统日期成Calendar对象
			Calendar curCalendar = Calendar.getInstance();
			strdate = null;
			int day = curCalendar.get(Calendar.DAY_OF_MONTH) - calendar.get(Calendar.DAY_OF_MONTH);
			int month = curCalendar.get(Calendar.MONTH) - calendar.get(Calendar.MONTH);
			int year = curCalendar.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
			if (day != 0 || month!=0 || year!=0) {
				if(dateStr.length() > 19){
					strdate = dateStr.substring(0, 19);
				}
			} else if (day == 0) {
				int hour = curCalendar.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY);
				if (hour > 0) {
					strdate = hour + "小时前";
				} else if (hour == 0) {
					int minute = curCalendar.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY);
					if (minute > 0) {
						strdate = minute + "分钟前";
					} else if (minute == 0) {
						strdate = "刚刚";
					}
				}
			}else {
				return "";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return strdate;
	}
	
	/**
	 * 根据aformat格式化bformat类型String时间
	 * @param dateStr
	 * @param bformat 格式化前时间类型
	 * @param aformat 格式化后时间类型
	 * @return
	 * gzy
	 */
	public static String getStringDate(String dateStr,String bformat,String aformat){
		
		DateFormat formater = new SimpleDateFormat(bformat);
		Date date = null;
		String sdate = "";
		try {
			date =formater.parse(dateStr);
			formater = new SimpleDateFormat(aformat);
			sdate = formater.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			//格式化异常时，返回dateStr值
			sdate = dateStr;
		}
		
		return sdate;
		
	}
	/**
	 * 时间比对并返回long型数据
	 * 
	 */
	public static long compareDate(String dataStr,String format,Date date){
		long num = 0;
		try {
			SimpleDateFormat df = new SimpleDateFormat(format);
			Date tsDate = df.parse(dataStr);
			return (date.getTime() - tsDate.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	
}