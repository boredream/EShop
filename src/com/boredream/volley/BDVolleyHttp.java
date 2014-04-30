package com.boredream.volley;

import java.util.Map;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.boredream.volley.BDVolleyUtils.Bean2Paramsable;

public class BDVolleyHttp {
	
	/**
	 * get方式获取字符串数据(json或者其他内容)
	 * 
	 * @param url		完整的url(可以用BDVolleyUtils.encodeUrl方法智能拼装参数)
	 * @param listener	响应回调
	 */
	public static void getString(Context context, String url,
			final BDListener<String> listener) {
		doString(context, url, Request.Method.GET, null, listener);
	}

	/**
	 * post方式获取字符串数据(json或者其他内容)
	 * 
	 * @param context
	 * @param url
	 * @param params	post的数据<String, Object>
	 * @param listener	响应回调
	 */
	public static void postString(Context context, String url, Map<String, Object> params, 
			final BDListener<String> listener) {
		doString(context, url, Request.Method.POST, params, listener);
	}

	/**
	 * post方式获取字符串数据(json或者其他内容)
	 * 
	 * @param context
	 * @param url
	 * @param bean		请求bean,会自动按照变量名-变量值的创建键值对封装为参数数组map
	 * @param listener	响应回调
	 */
	public static void postString(Context context, String url, Bean2Paramsable bean,
			final BDListener<String> listener) {
		Map<String, Object> params = BDVolleyUtils.bean2params(bean);
		doString(context, url, Request.Method.POST, params, listener);
	}
	
	/**
	 * get方式获取数据对象
	 * 
	 * <p>
	 * 要获取单纯的json字符串,用BDVolleyHttp.getString方法
	 * 
	 * @param context
	 * @param url		完整的url(可以用BDVolleyUtils.encodeUrl方法智能拼装参数)
	 * @param listener	响应回调,直接从json解析成Object对象
	 * @param clazz		想要将json封装成对象的数据类型
	 */
	public static <T> void getJsonObject(Context context, String url,
			final Class<T> clazz, final BDListener<T> listener) {
		doJsonObject(context, url, Request.Method.GET, null, clazz, listener);
	}

	/**
	 * post方式获取json数据对象
	 * 
	 * <p>
	 * 要获取单纯的json字符串,用BDVolleyHttp.getString方法
	 * 
	 * @param context
	 * @param url
	 * @param params	post的数据<String, Object>
	 * @param listener	响应回调,直接从json解析成Object对象
	 * @param clazz		想要将json封装成对象的数据类型
	 */
	public static <T> void postJsonObject(Context context, String url,
			final Map<String, Object> params,
			final Class<T> clazz, final BDListener<T> mListener) {
		doJsonObject(context, url, Request.Method.POST, params, clazz, mListener);
	}
	
	/**
	 * post方式获取json数据对象
	 * 
	 * <p>
	 * 要获取单纯的json字符串,用<b>postString</b>方法
	 * 
	 * @param context
	 * @param url
	 * @param bean		请求bean,会自动按照变量名-变量值的创建键值对封装为参数数组map
	 * @param listener	响应回调,直接从json解析成Object对象
	 * @param clazz		想要将json封装成对象的数据类型
	 */
	public static <T> void postJsonObject(Context context, String url, 
			final Bean2Paramsable bean,
			final Class<T> clazz, final BDListener<T> mListener) {
		Map<String, Object> params = BDVolleyUtils.bean2params(bean);
		doJsonObject(context, url, Request.Method.POST, params, clazz, mListener);
	}

	/**
	 * 利用Volley异步加载图片
	 * 
	 * @param context
	 * @param imageUrl
	 * @param iv
	 */
	public static void loadImageByVolley(Context context, String imageUrl, ImageView iv) {
		loadImageByVolley(context, imageUrl, iv, null);
	}

	/**
	 * 利用Volley异步加载图片,有回调型
	 * 
	 * @param context
	 * @param imageUrl
	 * @param iv
	 * @param listener 图片回调接口,bitmap为空时即为获取失败
	 */
	public static void loadImageByVolley(Context context, String imageUrl, 
			final ImageView iv, final BDImageListener listener) {
		ImageLoader imageLoader = BDVolley.getImageLoader(context);
		imageLoader.get(imageUrl, new ImageListener() {
			 @Override
	            public void onErrorResponse(VolleyError error) {
	                if (BDVolleyConfig.ERROR_IMAGE_RESID != 0) {
	                	iv.setImageResource(BDVolleyConfig.ERROR_IMAGE_RESID);
	                }
	                if(listener != null) {
	                	listener.onErrorResponse(error);
	                }
	            }

	            @Override
	            public void onResponse(ImageContainer response, boolean isImmediate) {
	                if (response.getBitmap() != null) {
	                	iv.setImageBitmap(response.getBitmap());
	                	if(listener != null) {
	                		listener.onComplete(response.getBitmap());
	                	}
	                } else {
	                	if (BDVolleyConfig.DEFAULT_IMAGE_RESID != 0) {
		                	iv.setImageResource(BDVolleyConfig.DEFAULT_IMAGE_RESID);
	                	}
	                	if(listener != null) {
	                		listener.onComplete(null);
	                	}
	                }
	            }
		});
	}
	
	private static <T> void doJsonObject(Context context, String url, int method, 
			final Map<String, Object> params, final Class<T> mClazz,
			final BDListener<T> mListener) {
		RequestQueue requestQueue = BDVolley.getRequestQueue(context);
		BDJsonRequest<T> jsonObjectRequest = new BDJsonRequest<T>(method, url, params, mClazz, mListener);
		requestQueue.add(jsonObjectRequest);
	}

	private static void doString(Context context, String url, int method, 
			final Map<String, Object> params, final BDListener<String> mListener) {
		RequestQueue requestQueue = BDVolley.getRequestQueue(context);
		BDStringRequest sRequest = new BDStringRequest(method, url, params, mListener);
		requestQueue.add(sRequest);
	}
}
