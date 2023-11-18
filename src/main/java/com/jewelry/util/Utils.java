package com.jewelry.util;

import org.springframework.util.ObjectUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Utils {

	  /**
	   * 현재 일자를 원하는 포맷으로 가져옴
	   * 
	   * @return String 포맷
	   */
	  public static synchronized String getTodayDateFormat(String format) {
		  format = format == null || format.equals("") ? "yyyy-MM-dd" : format;
		  return new SimpleDateFormat(format).format(new Date());
	  }
	  
	  /**
	   * 어제 일자를 원하는 포맷으로 가져옴
	   * 
	   * @return String 포맷
	   */
	  public static synchronized String getCalculateDateFormat(String format, int add) {
		  format = format == null || format.equals("") ? "yyyy-MM-dd" : format;
		  Calendar calendar = Calendar.getInstance();
		  calendar.add(Calendar.DATE, add);
		  return new SimpleDateFormat(format).format(calendar.getTime());
	  }
	  
	  /**
	   * 원하는 일자를 원하는 포맷으로 가져옴
	   * 
	   * @return String 포맷
	   */
	  public static synchronized String getDateCalDateFormat(String dateStr, String format, int add) {
		  String retVal = dateStr;
		  format = format == null || format.equals("") ? "yyyy-MM-dd" : format;
		  try {
			  SimpleDateFormat formatter = new SimpleDateFormat(format);
			  Date date = formatter.parse(dateStr);
			  
			  Calendar calendar = Calendar.getInstance();
			  calendar.setTime(date);
			  calendar.add(Calendar.DATE, add);
			  retVal = new SimpleDateFormat(format).format(calendar.getTime());
		  }
		  catch(Exception e) {
			  e.printStackTrace();
			  retVal = getTodayDateFormat(format);
		  }
		  return retVal;
	  }

		public static synchronized LocalDateTime convertLocalDateTime(String dateStr){
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			if(ObjectUtils.isEmpty(dateStr)){
				return null;
			}

			if(dateStr.length() == 10){
				dateStr += " 00:00:00";
			}
			return LocalDateTime.parse(dateStr, formatter);
		}
}
