package com.nemtool.explorer.util;
/**
*
* @author Masker
* @date 2020.07.27
*/
public class ExceptionsUtil {
	
	public static String getExceptionAllinformation(Exception ex){
        String sOut = "";        sOut += ex.getMessage() + "\r\n";
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement s : trace) {
            sOut += "\tat " + s + "\r\n";
        }
        return sOut;
 };

}
