package com.boredream.volley;

import com.boredream.eshop.R;

public class BDVolleyConfig {
	/**
	 * url地址encode编码,默认为utf-8
	 */
	public static final String URL_ENCODE_CHARSET_NAME = "UTF-8";
	/**
	 * 响应数据编码,默认为utf-8
	 */
	public static final String RESPONSE_CHARSET_NAME = "UTF-8";
	
	/**
	 * 图片缓存类型: 1-限制缓存图片总大小; 2-限制图片缓存池总数量
	 */
	public static int IMAGE_CACHE_TYPE = 1;
	/**
	 * IMAGE_CACHE_TYPE=1 时启用. scale=8即用app可用内存的1/8作为图片缓存池总缓存大小
	 */
	public static int IMAGE_CACHE_SCALE = 8;
	/**
	 * IMAGE_CACHE_TYPE=2 时启用. count=20即应用缓存池中最多存20张图片
	 */
	public static int IMAGE_CACHE_COUNT = 20;
	/**
	 * 默认加载图片
	 */
	public static int DEFAULT_IMAGE_RESID = R.drawable.ic_launcher;
	/**
	 * 加载失败时图片
	 */
	public static int ERROR_IMAGE_RESID = R.drawable.ic_launcher;
	
}
