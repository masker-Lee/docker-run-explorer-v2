package com.nemtool.explorer.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
*
* @author Masker
* @date 2020.09.19
*/
@Component
public class TimeUtil {
	
	//Date.UTC(2015, 2, 29, 0, 6, 25, 0)
	static long nemTime = 1427587585L;
	
	public static long getTimeInNem() {
		long nowTime = new Date().getTime();
		return Math.round(nowTime/1000 - nemTime);
	}
	
	public static long getTimeBeforeOneDayInNem() {
		long nowTime = new Date().getTime();
		return Math.round(nowTime/1000 - nemTime) - 24*60*60;
	}
	
	public static long getTimeBeforeOneMonthInNem() {
		long nowTime = new Date().getTime();
		return Math.round(nowTime/1000 - nemTime) - 30*24*60*60;
	}
	
	public static long getTimeInReal(long timestamp) {
		return (timestamp + nemTime)*1000;
	}
	
	public static long convertToNemTime(long time) {
		return Math.round(time/1000 - nemTime);
	}
	
	public static long getYearAddOneInNem(long timestamp) {
		Date date = new Date((timestamp + nemTime)*1000);
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    //add 1 year
	    cal.add(Calendar.YEAR, 1);
	    long result = cal.getTimeInMillis()/1000 - nemTime;
	    return result;
	}

}
