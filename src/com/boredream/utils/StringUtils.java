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
	 * ����ת������:�������ڵ���ʾ"ʱ:��",�������ʾ�ַ�"����",��֮ǰ����ʾ"��/��/��"
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
			newString = "����";
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
	 *            ��С��ʱ��
	 * @param newTime
	 *            �ϴ��ʱ�� (���Ϊ�� Ĭ�ϵ�ǰʱ�� ,��ʾ�͵�ǰʱ�����)
	 * @return -1 ��ͬһ��. 0������ . 1 ��������ǰ��.
	 * @throws ParseException
	 *             ת���쳣
	 */
	private static int isYesterday(Date oldTime, Date newTime)
			throws ParseException {
		if (newTime == null) {
			newTime = new Date();
		}
		// ������� ���� yyyy-MM-dd 00��00��00 ��������
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.CHINA);
		String todayStr = format.format(newTime);
		Date today = format.parse(todayStr);
		// ���� 86400000=24*60*60*1000 һ��
		if ((today.getTime() - oldTime.getTime()) > 0
				&& (today.getTime() - oldTime.getTime()) <= 86400000) {
			return 0;
		}
		else if ((today.getTime() - oldTime.getTime()) <= 0) { // �����ǽ���
			return -1;
		}
		else { // ������ǰ��
			return 1;
		}

	}
	
}
