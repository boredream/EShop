package com.boredream.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String DATE_FORMAT_WITHOUT_YEAR = "MM-dd";
	public static final String TIME_FORMAT_WITHOUT_SECOND = "HH:mm";

	public static String formatTime(Date date) {
		SimpleDateFormat srcDateFormat = new SimpleDateFormat(DATE_FORMAT + " "
				+ TIME_FORMAT, Locale.CHINA);
		return srcDateFormat.format(date);
	}

	/**
	 * 日期转换规则:今年以内的显示"时:分",昨天的显示字符"昨天",再之前的显示"年/月/日"
	 * 
	 * @param srcString
	 * @return
	 * @throws ParseException
	 */
	public static String parseDateString(String srcString)
			throws ParseException {
		String newString = null;
		SimpleDateFormat srcDateFormat = new SimpleDateFormat(DATE_FORMAT + " "
				+ TIME_FORMAT, Locale.CHINA);
		Date date = srcDateFormat.parse(srcString);
		int yesterday = isYesterday(date, new Date());
		SimpleDateFormat newDateFormat;
		switch (yesterday) {
		case -1:
			newDateFormat = new SimpleDateFormat(TIME_FORMAT_WITHOUT_SECOND, Locale.CHINA);
			newString = newDateFormat.format(date);
			break;
		case 0:
			newString = "昨天";
			break;

		default:
			newDateFormat = new SimpleDateFormat(DATE_FORMAT_WITHOUT_YEAR, Locale.CHINA);
			newString = newDateFormat.format(date);
			break;
		}
		return newString;
	}

	/**
	 * @param oldTime
	 *            较小的时间
	 * @param newTime
	 *            较大的时间 (如果为空 默认当前时间 ,表示和当前时间相比)
	 * @return -1 ：同一天. 0：昨天 . 1 ：至少是前天.
	 * @throws ParseException
	 *             转换异常
	 */
	private static int isYesterday(Date oldTime, Date newTime)
			throws ParseException {
		if (newTime == null) {
			newTime = new Date();
		}
		// 将下面的 理解成 yyyy-MM-dd 00：00：00 更好理解点
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.CHINA);
		String todayStr = format.format(newTime);
		Date today = format.parse(todayStr);
		// 昨天 86400000=24*60*60*1000 一天
		if ((today.getTime() - oldTime.getTime()) > 0
				&& (today.getTime() - oldTime.getTime()) <= 86400000) {
			return 0;
		}
		else if ((today.getTime() - oldTime.getTime()) <= 0) { // 至少是今天
			return -1;
		}
		else { // 至少是前天
			return 1;
		}

	}
	
}
