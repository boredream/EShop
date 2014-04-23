package com.boredream.http;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.boredream.eshop.R;
import com.google.gson.Gson;

public class VolleyUtils {
	
	private static final int IMAGE_CACHE_NUMBER = 20;
	private static final String DEFAULT_CACHE_DIR = "volley";
	
	public static int defaultImageResId = R.drawable.ic_launcher;
	public static int errorImageResId = R.drawable.ic_launcher;
	
	/**
	 * 利用Volley获取JSON数据
	 * @param <T>
	 * @param <T>
	 */
	public static <T> void getJson(Context context, String url, final OnJsonResponseListener<T> listener, final Class<T> clazz) {
		RequestQueue requestQueue = Volley.newRequestQueue(context);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
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
				});
		requestQueue.add(jsonObjectRequest);
	}
	
	/**
	 * 利用Volley获取JSON数据
	 * @param <T>
	 * @param <T>
	 */
	public static <T> void postJson(Context context, String url, final Map<String, Object> params, final OnJsonResponseListener<T> listener, final Class<T> clazz) {
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.POST, url, null,
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
						Map<String, String> allParams = super.getParams();
						if(params != null && params.size() > 0) {
							for(Entry<String, Object> entry : params.entrySet()) {
								allParams.put(entry.getKey(), entry.getValue().toString());
							}
						}
						return allParams;
					}
			
		};
		requestQueue.add(jsonObjectRequest);
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
	public static void loadImageByVolley(Context context, String imageUrl, final ImageView iv, final OnImageCompleteListener listener) {
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
	                	listener.onComplete(null);
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
	
	public interface OnImageCompleteListener {
		/**
		 * 加载图片完成
		 * @param bitmap 为null时代表图片获取失败
		 */
		public void onComplete(Bitmap bitmap);
	}
	
	public interface OnJsonResponseListener<T> {
		public void onResponse(T t);
		public void onErrorResponse(VolleyError errorInfo);
	}
}
