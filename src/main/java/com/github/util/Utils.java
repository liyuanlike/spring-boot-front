package com.github.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Utils {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

	public static String getFileMd5(String filePath) {
		File file = new File(filePath);
		return getFileMd5(file);
	}
	public static String getFileMd5(File file) {
		String md5 = null;
		if (file.exists()) {
			try {
				InputStream inputStream = new FileInputStream(file);
				md5 = DigestUtils.md5Hex(inputStream);
				LOGGER.trace(file.getAbsolutePath() + " md5: " + md5);
			} catch (Exception e) {
				throw new RuntimeException("get file md5 failure!");
			}
		}
		return md5;
	}
	
	
	public static String printStackTraceToString(Throwable throwable) {
	    StringWriter stringWriter = new StringWriter();
	    throwable.printStackTrace(new PrintWriter(stringWriter, true));
	    return stringWriter.getBuffer().toString();
	}
	
	public static final String getRequestIp(HttpServletRequest request) {
		
		String ip = request.getHeader("X-Real-IP");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	    	ip = request.getHeader("x-forwarded-for");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	}
	
	public static final boolean isWechat(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		return userAgent.contains("MicroMessenger");
	}
	
	/** 获取当前月第一天 */
	public static final Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		return calendar.getTime();
	}
	
	/** 获取当前月最后一天 */
	public static final Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		return calendar.getTime();
	}
	
	/** 获取当前周第一天 */
	public static final Date getWeekStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		return calendar.getTime();
	}
	
	/** 获取当前周最后一天 */
	public static final Date getWeekEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		return calendar.getTime();
	}
	
	/** 获取当天开始时间 */
	public static final Date getDayStart() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		return calendar.getTime();
	}
	
	/** 获取当天结束时间 */
	public static final Date getDayEnd() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		return calendar.getTime();
	}
	
	/** 获取最近6个月 */
	public static final List<Map<String, String>> getLastSixMonth() {
		
		List<Map<String, String>> monthList = new ArrayList<Map<String,String>>();
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < 6; i++) {
			Map<String, String> month = new HashMap<String, String>();
			month.put("value", DateFormatUtils.format(calendar, "yyyyMM"));
			month.put("text", DateFormatUtils.format(calendar, "yyyy年MM月"));
			monthList.add(month);
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		}
		
		return monthList;
	}
	
	/** 日期转换 */
	public static final Date parseDate(String dateString) {
		
		Date date = null;
        if(StringUtils.isEmpty(dateString)){
        	date = new Date();
        } else {
        	try {
				date = DateUtils.parseDate(dateString, new String[]{"yyyyMM", "yyyyMMdd", "yyyyMMdd HH:mm:ss"});
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
        Assert.state(date != null, "parseDate error.");
		return date;
	}
	
	/** 反射 - 获取bean的属性值 */
	public static final Integer getBeanIntValue(Object bean, String property) {
		return Integer.parseInt(getBeanPropertyValue(bean, property));
	}
	public static final String getBeanPropertyValue(Object bean, String property) {
		String value = null;
		try {
			value = BeanUtils.getProperty(bean, property);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	
	
	public static void main(String[] args) {
		System.err.println(getDayStart());
		System.err.println(getDayEnd());
		
		System.err.println(getWeekStart(new Date()));
		System.err.println(getWeekEnd(new Date()));
	}
}
