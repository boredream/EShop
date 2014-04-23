package com.android.volley.toolbox;

import android.util.Log;

public class VolleyConfig {
	public static String TAG = "Volley";
	public static boolean DEBUG = Log.isLoggable(TAG, Log.VERBOSE);
	/** Default on-disk cache directory. */
	public static final String DEFAULT_CACHE_DIR = "volley";
	/**
	 * userAgent to report in your HTTP requests
	 */
	public static final String DEFAULT_USERAGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36";

	public static final String DEFAULT_CONTENTTYPE = "application/x-www-form-urlencoded; charset=";

	/**
	 * Default encoding for POST or PUT parameters. See
	 * {@link #getParamsEncoding()}.
	 */
	public static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

	/** The default socket connectiontimeout in milliseconds */
	public static final int DEFAULT_CONNECTIONTIMEOUT = 2500;

	/** The default socket timeout in milliseconds */
	public static final int DEFAULT_TIMEOUT_MS = 2500;

	/** The default number of retries */
	public static final int DEFAULT_MAX_RETRIES = 1;

	/**
	 * The default backoff multiplier ��������ʧ��֮������󣬲��������ͬ��ʱ��ȥ����Server��
	 * ���������Ե�ʱ������ȥ���󣬶���һ������������һ�α�һ�γ��� ���backoff������2����ǰ��ʱΪ3�����´��������6S��
	 * */
	public static final float DEFAULT_BACKOFF_MULT = 1f;

	/** Socket timeout in milliseconds for image requests */
	public static final int IMAGE_TIMEOUT_MS = 1000;

	/** Default number of retries for image requests */
	public static final int IMAGE_MAX_RETRIES = 2;

	/** Default backoff multiplier for image requests */
	public static final float IMAGE_BACKOFF_MULT = 2f;

	public static int SLOW_REQUEST_THRESHOLD_MS = 3000;
	/**
	 * If a pool isn't passed in, then build a small default pool that will give
	 * us a lot of benefit and not use too much memory
	 */
	public static int DEFAULT_POOL_SIZE = 4096;

	/**
	 * Default maximum disk usage in bytes. Ĭ�ϵ�������ʹ���ֽڡ�
	 */
	public static final int DEFAULT_DISK_USAGE_BYTES = 5 * 1024 * 1024;

	/**
	 * High water mark percentage for the cache ���ڸ��ٻ���ĸ�ˮλ�����
	 */
	public static final float HYSTERESIS_FACTOR = 0.9f;

	/**
	 * If the {@link #PoolingByteArrayOutputStream(ByteArrayPool)} constructor
	 * is called, this is the default size to which the underlying byte array is
	 * initialized.
	 */
	public static final int DEFAULT_SIZE = 256;

	/**
	 * Magic number for current version of cache file format.
	 * ���ڸ��ٻ����ļ���ʽ�ĵ�ǰ�汾ħ�����֡�
	 */
	public static final int CACHE_MAGIC = 0x20120504;
}
