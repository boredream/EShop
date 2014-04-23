package com.boredream.http;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.boredream.eshop.R;
import com.google.gson.Gson;

public class VolleyUtils {
	
	private static String GET_PARAMS_CHARSET_NAME = "UTF-8";
	private static final int IMAGE_CACHE_NUMBER = 20;
	
	public static int defaultImageResId = R.drawable.ic_launcher;
	public static int errorImageResId = R.drawable.ic_launcher;

	/**
	 * get方式获取字符串数据(json或者其他内容)
	 * 
	 * @param context
	 * @param url
	 * @param getParams	get提交方式的参数,会自动解析并与url拼装
	 * @param listener	响应回调
	 */
	public static void getString(Context context, String url, Map<String, Object> getParams, 
			final OnStringResponseListener listener) {
		String paramsUrl = getAddGetParamsUrl(url, getParams);
		doString(context, paramsUrl, Request.Method.GET, null, listener);
	}

	/**
	 * get方式获取字符串数据(json或者其他内容)
	 * 
	 * @param context
	 * @param url
	 * @param bean		请求bean,会自动按照变量名-变量值的创建键值对封装为参数与url拼装
	 * @param listener	响应回调
	 */
	public static void getString(Context context, String url, Bean2Paramsable bean, 
			final OnStringResponseListener listener) {
		String paramsUrl = getAddGetParamsUrl(url, bean2params(bean));
		doString(context, paramsUrl, Request.Method.GET, null, listener);
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
			final OnStringResponseListener listener) {
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
			final OnStringResponseListener listener) {
		Map<String, Object> params = bean2params(bean);
		doString(context, url, Request.Method.POST, params, listener);
	}
	
	/**
	 * get方式获取数据对象
	 * 
	 * <p>
	 * 要获取单纯的json字符串,用<b>getString</b>方法
	 * 
	 * @param context
	 * @param url
	 * @param params	get提交方式的参数,会自动解析并与url拼装
	 * @param listener	响应回调,直接从json解析成Object对象
	 * @param clazz		json封装的数据类型
	 */
	public static <T> void getJsonObject(Context context, String url, Map<String, Object> params,
			final OnJsonResponseListener<T> listener, final Class<T> clazz) {
		doJsonObject(context, url, Request.Method.GET, null, listener, clazz);
	}
	
	/**
	 * get方式获取数据对象
	 * 
	 * <p>
	 * 要获取单纯的json字符串,用<b>getString</b>方法
	 * 
	 * @param context
	 * @param url
	 * @param bean		请求bean,会自动按照变量名-变量值的创建键值对封装为参数与url拼装
	 * @param listener	响应回调,直接从json解析成Object对象
	 * @param clazz		json封装的数据类型
	 */
	public static <T> void getJsonObject(Context context, String url, Bean2Paramsable bean,
			final OnJsonResponseListener<T> listener, final Class<T> clazz) {
		String paramsUrl = getAddGetParamsUrl(url, bean2params(bean));
		doJsonObject(context, paramsUrl, Request.Method.GET, null, listener, clazz);
	}

	/**
	 * post方式获取json数据对象
	 * 
	 * <p>
	 * 要获取单纯的json字符串,用<b>postString</b>方法
	 * 
	 * @param context
	 * @param url
	 * @param params	post的数据<String, Object>
	 * @param listener	响应回调,直接从json解析成Object对象
	 * @param clazz		json封装的数据类型
	 */
	public static <T> void postJsonObject(Context context, String url,
			final Map<String, Object> params,
			final OnJsonResponseListener<T> listener, final Class<T> clazz) {
		doJsonObject(context, url, Request.Method.POST, params, listener, clazz);
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
	 * @param clazz		json封装的数据类型
	 */
	public static <T> void postJsonObject(Context context, String url, Bean2Paramsable bean,
			final OnJsonResponseListener<T> listener, final Class<T> clazz) {
		Map<String, Object> params = bean2params(bean);
		doJsonObject(context, url, Request.Method.POST, params, listener, clazz);
	}

	/**
	 * 利用Volley异步加载图片
	 * @param context
	 * @param imageUrl
	 * @param iv
	 */
	public static void loadImageByVolley(Context context, String imageUrl, ImageView iv) {
		loadImageByVolley(context, imageUrl, iv, null);
	}

	/**
	 * 利用Volley异步加载图片,有回调型
	 * @param context
	 * @param imageUrl
	 * @param iv
	 * @param listener 图片回调接口,bitmap为空时即为获取失败
	 */
	public static void loadImageByVolley(Context context, String imageUrl, 
			final ImageView iv, final OnImageCompleteListener listener) {
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				IMAGE_CACHE_NUMBER);
		ImageCache imageCache = new ImageCache() {
			@Override
			public void putBitmap(String key, Bitmap value) {
				lruCache.put(key, value);
			}
			
			@Override
			public Bitmap getBitmap(String key) {
				return lruCache.get(key);
			}
		};
		ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
		imageLoader.get(imageUrl, new ImageListener() {
			 @Override
	            public void onErrorResponse(VolleyError error) {
	                if (errorImageResId != 0) {
	                	iv.setImageResource(errorImageResId);
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
	                } else if (defaultImageResId != 0) {
	                	iv.setImageResource(defaultImageResId);
	                	if(listener != null) {
	                		listener.onComplete(null);
	                	}
	                } else {
	                	if(listener != null) {
	                		listener.onComplete(null);
	                	}
	                }
	            }
		});
	}

	/**
	 * 获取get方式拼接后的url
	 * <p>例如url为"http:www.abc.com/get"参数map集合为["a":1, "b":"aaaaaa"]则拼装后的url为"http:www.abc.com/get?a=1&b=aaaaaa"
	 * @param url			原url
	 * @param getParams		需要拼接的参数map集合
	 * @return 				拼装完成的url
	 */
	private static String getAddGetParamsUrl(String url, Map<String, Object> getParams) {
		StringBuilder newUrl = new StringBuilder(url);
		if(getParams != null && getParams.size() > 0) {
			newUrl.append("?");
			for(Entry<String, Object> entry : getParams.entrySet()) {
				try {
					newUrl.append(entry.getKey() + "=" 
							+ URLEncoder.encode(entry.getValue().toString(), 
									GET_PARAMS_CHARSET_NAME) + "&");
				} catch (Exception e) { }
			}
			newUrl.substring(0, newUrl.length()-2);
		}
		return newUrl.toString();
	}

	/**
	 * 将对象转为map数组,方便作为请求参数使用
	 * <p>map数组中保存对象类所有的变量,变量命作为key,变量具体值作为value
	 * @param bean	需要转换的对象
	 * @return		转换后的map数组
	 */
	private static Map<String, Object> bean2params(Bean2Paramsable bean) {
		Map<String, Object> params = new HashMap<String, Object>();
		for(Field field : bean.getClass().getFields()) {
			try {
				params.put(field.getName(), field.get(bean));
			} catch (Exception e) { }
		}
		return params;
	}

	/**
	 * 获取json对象
	 * @param context
	 * @param url
	 * @param method
	 * @param postParams
	 * @param listener
	 * @param clazz
	 */
	private static <T> void doJsonObject(Context context, String url, int method, 
			final Map<String, Object> postParams, 
			final OnJsonResponseListener<T> listener, final Class<T> clazz) {
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				method, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						String json = response.toString();
						T t = new Gson().fromJson(json, clazz);
						listener.onResponse(t);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError errorInfo) {
						listener.onErrorResponse(errorInfo);
					}
				}){
			
			@Override
			protected Map<String, String> getParams()
					throws AuthFailureError {
				Map<String, String> params = super.getParams();
				if(postParams != null && postParams.size() > 0) {
					for(Entry<String, Object> entry : postParams.entrySet()) {
						params.put(entry.getKey(), entry.getValue().toString());
					}
				}
				return params;
			}
			
		};
		requestQueue.add(jsonObjectRequest);
	}

	private static void doString(Context context, String url, int method, 
			final Map<String, Object> postParams, final OnStringResponseListener listener) {
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		StringRequest sRequest = new StringRequest(method, url, 
				new Listener<String>() {
					@Override
					public void onResponse(String response) {
						listener.onResponse(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						listener.onErrorResponse(error);
					}
				}){
			@Override
			protected Map<String, String> getParams()
					throws AuthFailureError {
				Map<String, String> params = super.getParams();
				if(postParams != null && postParams.size() > 0) {
					for(Entry<String, Object> entry : postParams.entrySet()) {
						params.put(entry.getKey(), entry.getValue().toString());
					}
				}
				return params;
			}
			
		};
		sRequest.setShouldCache(false);
		requestQueue.add(sRequest);
	}

	public interface OnImageCompleteListener extends ErrorListener {
		/**
		 * 加载图片完成
		 * @param bitmap 为null时代表图片获取失败
		 */
		public void onComplete(Bitmap bitmap);
	}

	public interface OnJsonResponseListener<T> extends ErrorListener {
		public void onResponse(T t);
	}

	public interface OnStringResponseListener extends ErrorListener {
		public void onResponse(String str);
	}
	
	public interface Bean2Paramsable {
		/* empty */
	}
}
