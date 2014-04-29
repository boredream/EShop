package com.boredream.volley;

import com.boredream.eshop.R;

public class BDVolleyConfig {
	/**
	 * url��ַencode����,Ĭ��Ϊutf-8
	 */
	public static final String URL_ENCODE_CHARSET_NAME = "UTF-8";
	/**
	 * ��Ӧ���ݱ���,Ĭ��Ϊutf-8
	 */
	public static final String RESPONSE_CHARSET_NAME = "UTF-8";
	
	/**
	 * ͼƬ��������: 1-���ƻ���ͼƬ�ܴ�С; 2-����ͼƬ�����������
	 */
	public static int IMAGE_CACHE_TYPE = 1;
	/**
	 * IMAGE_CACHE_TYPE=1 ʱ����. scale=8����app�����ڴ��1/8��ΪͼƬ������ܻ����С
	 */
	public static int IMAGE_CACHE_SCALE = 8;
	/**
	 * IMAGE_CACHE_TYPE=2 ʱ����. count=20��Ӧ�û����������20��ͼƬ
	 */
	public static int IMAGE_CACHE_COUNT = 20;
	/**
	 * Ĭ�ϼ���ͼƬ
	 */
	public static int DEFAULT_IMAGE_RESID = R.drawable.ic_launcher;
	/**
	 * ����ʧ��ʱͼƬ
	 */
	public static int ERROR_IMAGE_RESID = R.drawable.ic_launcher;
	
}
