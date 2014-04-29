package com.boredream.volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.boredream.volley.BDVolleyUtils.Bean2Paramsable;
import com.google.gson.Gson;

public class BDVolleyHttp2 {
	/**
	 * get方式获取字符串数据(json或者其他内容)
	 * 
	 * @param context
	 * @param url		已经拼装好参数的url
	 * @param listener	响应回调
	 */
	public static void getString(Context context, String url,
			final BDListener<String> listener) {
		doString(context, url, Request.Method.GET, null, listener);
	}

	/**
	 * get方式获取字符串数据(json或者其他内容)
	 * 
	 * @param context
	 * @param url
	 * @param getParams	get提交方式的参数,会自动解析并与url拼装
	 * @param listener	响应回调
	 */
	public static void getString(Context context, String url, Map<String, Object> getParams, 
			final BDListener<String> listener) {
		String paramsUrl = BDVolleyUtils.parseGetUrlWithParams(url, getParams);
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
			final BDListener<String> listener) {
		String paramsUrl = BDVolleyUtils.parseGetUrlWithParams(url, BDVolleyUtils.bean2params(bean));
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
	 * 要获取单纯的json字符串,用<b>getString</b>方法
	 * 
	 * @param context
	 * @param url
	 * @param params	get提交方式的参数,会自动解析并与url拼装
	 * @param listener	响应回调,直接从json解析成Object对象
	 * @param clazz		想要将json封装成对象的数据类型
	 */
	public static <T> void getJsonObject(Context context, String url, Map<String, Object> params,
			final BDListener<T> listener, final Class<T> clazz) {
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
	 * @param clazz		想要将json封装成对象的数据类型
	 */
	public static <T> void getJsonObject(Context context, String url, Bean2Paramsable bean,
			final BDListener<T> listener, final Class<T> clazz) {
		String paramsUrl = BDVolleyUtils.parseGetUrlWithParams(url, BDVolleyUtils.bean2params(bean));
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
	 * @param clazz		想要将json封装成对象的数据类型
	 */
	public static <T> void postJsonObject(Context context, String url,
			final Map<String, Object> params,
			final BDListener<T> listener, final Class<T> clazz) {
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
	 * @param clazz		想要将json封装成对象的数据类型
	 */
	public static <T> void postJsonObject(Context context, String url, 
			final Bean2Paramsable bean,
			final BDListener<T> listener, final Class<T> clazz) {
		Map<String, Object> params = BDVolleyUtils.bean2params(bean);
		doJsonObject(context, url, Request.Method.POST, params, listener, clazz);
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
			final ImageView iv, final OnImageCompleteListener listener) {
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				BDVolleyConfig.IMAGE_CACHE_COUNT);
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
	                } else if (BDVolleyConfig.DEFAULT_IMAGE_RESID != 0) {
	                	iv.setImageResource(BDVolleyConfig.DEFAULT_IMAGE_RESID);
	                	if(listener != null) {
	                		listener.onComplete(null);
	                	}
	                }
	            }
		});
	}
	
	private static <T> void doJsonObject(Context context, String url, int method, 
			final Map<String, Object> postParams, 
			final BDListener<T> listener, final Class<T> clazz) {
		// 含有中文的url会失败,需要经过编码
		String encodeGetParamsUrl = BDVolleyUtils.encodeUrl(url);
		
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				method, encodeGetParamsUrl, null,
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
			/* 复写parseNetworkResponse方法修改响应数据中文编码方式,
			 * 获取响应数据编码格式编码中文,如果获取失败则默认以
			 * DEFAULT_RESPONSE_CHARSET_NAME = UTF-8 编码
			 * 注:HttpHeaderParser.parseCacheHeaders(response)不用修改
			 */
			@Override
			protected Response<JSONObject> parseNetworkResponse(
					NetworkResponse response) {
				try {
					String jsonString = new String(response.data, 
							BDVolleyUtils.getCharsetFromHeaders(response.headers));
					return Response.success(new JSONObject(jsonString), 
							HttpHeaderParser.parseCacheHeaders(response));
				} catch (UnsupportedEncodingException e) {
					return Response.error(new ParseError(e));
				} catch (JSONException je) {
					return Response.error(new ParseError(je));
				}
			}
			
		};
		requestQueue.add(jsonObjectRequest);
	}

	private static void doString(Context context, String url, int method, 
			final Map<String, Object> postParams, final BDListener<String> listener) {
		// 含有中文的url会失败,需要经过编码
		String encodeGetParamsUrl = BDVolleyUtils.encodeUrl(url);
		
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		StringRequest sRequest = new StringRequest(method, encodeGetParamsUrl, 
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
			/* 复写parseNetworkResponse方法修改响应数据中文编码方式,
			 * 获取响应数据编码格式编码中文,如果获取失败则默认以
			 * DEFAULT_RESPONSE_CHARSET_NAME = UTF-8 编码
			 */
			@Override
			protected Response<String> parseNetworkResponse(
					NetworkResponse response) {
				String parsed;
		        try {
		            parsed = new String(response.data, BDVolleyUtils.getCharsetFromHeaders(response.headers));
		        } catch (UnsupportedEncodingException e) {
		            parsed = new String(response.data);
		        }
		        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
			}
		};
		sRequest.setShouldCache(false);
		requestQueue.add(sRequest);
	}
}
