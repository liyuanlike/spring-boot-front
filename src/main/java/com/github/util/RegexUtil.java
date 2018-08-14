package com.github.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *	正则表达式工具
 *	
 *	<!-- 2013.10.23 10:00, 姚康明, YAOKM@FOUNDER.COM.CN -->
 */
public class RegexUtil {
		
	/**
	 * 获得匹配组字符串
	 */
	public static String getMatchContent(String regex, CharSequence input) {
		return getMatchContent(regex, 0, input);
	}
	/**
	 * 获得匹配组字符串
	 */
	public static String getGroup1MatchContent(String regex, CharSequence input) {
		return getMatchContent(regex, 1, input);
	}
	/**
	 * 获得匹配组字符串
	 */
	public static String getMatchContent(String regex, int group, CharSequence input) {
		String matchContent = null;
		Matcher matcher = matcher(regex, input);
		if (matcher.find() && group <= matcher.groupCount()) {
			matchContent = matcher.group(group);
		} else {
			matchContent = null;
		}
		return matchContent;
	}
	/**
	 * 获得第几次匹配到的字符串
	 */
	public static String getMatchContentWithTime(String regex, int matchTime, CharSequence input) {
		String matchContent = null;
		Matcher matcher = matcher(regex, input);
		for (int i = 0; i < matchTime; i++) {
			matcher.find();
		}
		matchContent = matcher.group();
		return matchContent;
	}
	/**
	 * 域名匹配
	 */
	public static String getDomainName(CharSequence input) {
		return getMatchContent("http[s]?://[^/]+/", input);
	}
	/**
	 * Ip匹配
	 */
	public static String getIp(CharSequence input) {
		return getMatchContent("((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))", input);
	}
	/**
	 * 判断是否包含域名
	 */
	public static boolean isMatchContainDomainName(CharSequence input) {
		return isMatch("http://[^/]+/", input);
	}
	/**
	 * 启用忽略大小写和DOTALL模式进行匹配
	 */
	public static Matcher matcher(String regex, CharSequence input) {
		int flags = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
		return matcher(regex, input, flags);
	}
	
	/**
	 * 根据Match flags匹配
	 */
	public static Matcher matcher(String regex, CharSequence input, int flags) {
		Pattern pattern = Pattern.compile(regex, flags);
		return pattern.matcher(input);
	}
	
	/**
	 * 判断目标字符串是否匹配
	 */
	public static boolean isMatch(String regex, CharSequence input) {
		Matcher matcher = matcher(regex, input);
		return matcher.find();
	}

	/**
	 * 判断目标字符串是否匹配其中一个正则
	 */
	public static boolean isMatch(String[] regexArray, CharSequence input) {
		for (final String regex : regexArray) {
			if (isMatch(regex, input)) {
				return true;
			}
		}
		return false;
	}
}
