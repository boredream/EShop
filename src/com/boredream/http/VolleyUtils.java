package com.boredream.http;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.protocol.HTTP;
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
import com.android.volley.Response.ErrorListener;
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
import com.boredream.eshop.R;
import com.google.gson.Gson;

public class VolleyUtils {
	
	private static String GET_PARAMS_CHARSET_NAME = "UTF-8";
	private static String DEFAULT_RESPONSE_CHARSET_NAME = "UTF-8";
	private static final int IMAGE_CACHE_NUMBER = 20;
	
	public static int defaultImageResId = R.drawable.ic_launcher;
	public static int errorImageResId = R.drawable.ic_launcher;
	
	/**
	 * get��ʽ��ȡ�ַ�������(json������������)
	 * 
	 * @param context
	 * @param url		�Ѿ�ƴװ�ò�����url
	 * @param listener	��Ӧ�ص�
	 */
	public static void getString(Context context, String url,
			final OnStringResponseListener listener) {
		doString(context, url, Request.Method.GET, null, listener);
	}

	/**
	 * get��ʽ��ȡ�ַ�������(json������������)
	 * 
	 * @param context
	 * @param url
	 * @param getParams	get�ύ��ʽ�Ĳ���,���Զ���������urlƴװ
	 * @param listener	��Ӧ�ص�
	 */
	public static void getString(Context context, String url, Map<String, Object> getParams, 
			final OnStringResponseListener listener) {
		String paramsUrl = addParams4GetUrl(url, getParams);
		doString(context, paramsUrl, Request.Method.GET, null, listener);
	}

	/**
	 * get��ʽ��ȡ�ַ�������(json������������)
	 * 
	 * @param context
	 * @param url
	 * @param bean		����bean,���Զ����ձ�����-����ֵ�Ĵ�����ֵ�Է�װΪ������urlƴװ
	 * @param listener	��Ӧ�ص�
	 */
	public static void getString(Context context, String url, Bean2Paramsable bean, 
			final OnStringResponseListener listener) {
		String paramsUrl = addParams4GetUrl(url, bean2params(bean));
		doString(context, paramsUrl, Request.Method.GET, null, listener);
	}

	/**
	 * post��ʽ��ȡ�ַ�������(json������������)
	 * 
	 * @param context
	 * @param url
	 * @param params	post������<String, Object>
	 * @param listener	��Ӧ�ص�
	 */
	public static void postString(Context context, String url, Map<String, Object> params, 
			final OnStringResponseListener listener) {
		doString(context, url, Request.Method.POST, params, listener);
	}

	/**
	 * post��ʽ��ȡ�ַ�������(json������������)
	 * 
	 * @param context
	 * @param url
	 * @param bean		����bean,���Զ����ձ�����-����ֵ�Ĵ�����ֵ�Է�װΪ��������map
	 * @param listener	��Ӧ�ص�
	 */
	public static void postString(Context context, String url, Bean2Paramsable bean,
			final OnStringResponseListener listener) {
		Map<String, Object> params = bean2params(bean);
		doString(context, url, Request.Method.POST, params, listener);
	}
	
	/**
	 * get��ʽ��ȡ���ݶ���
	 * 
	 * <p>
	 * Ҫ��ȡ������json�ַ���,��<b>getString</b>����
	 * 
	 * @param context
	 * @param url
	 * @param params	get�ύ��ʽ�Ĳ���,���Զ���������urlƴװ
	 * @param listener	��Ӧ�ص�,ֱ�Ӵ�json������Object����
	 * @param clazz		��Ҫ��json��װ�ɶ������������
	 */
	public static <T> void getJsonObject(Context context, String url, Map<String, Object> params,
			final OnJsonResponseListener<T> listener, final Class<T> clazz) {
		doJsonObject(context, url, Request.Method.GET, null, listener, clazz);
	}
	
	/**
	 * get��ʽ��ȡ���ݶ���
	 * 
	 * <p>
	 * Ҫ��ȡ������json�ַ���,��<b>getString</b>����
	 * 
	 * @param context
	 * @param url
	 * @param bean		����bean,���Զ����ձ�����-����ֵ�Ĵ�����ֵ�Է�װΪ������urlƴװ
	 * @param listener	��Ӧ�ص�,ֱ�Ӵ�json������Object����
	 * @param clazz		��Ҫ��json��װ�ɶ������������
	 */
	public static <T> void getJsonObject(Context context, String url, Bean2Paramsable bean,
			final OnJsonResponseListener<T> listener, final Class<T> clazz) {
		String paramsUrl = addParams4GetUrl(url, bean2params(bean));
		doJsonObject(context, paramsUrl, Request.Method.GET, null, listener, clazz);
	}

	/**
	 * post��ʽ��ȡjson���ݶ���
	 * 
	 * <p>
	 * Ҫ��ȡ������json�ַ���,��<b>postString</b>����
	 * 
	 * @param context
	 * @param url
	 * @param params	post������<String, Object>
	 * @param listener	��Ӧ�ص�,ֱ�Ӵ�json������Object����
	 * @param clazz		��Ҫ��json��װ�ɶ������������
	 */
	public static <T> void postJsonObject(Context context, String url,
			final Map<String, Object> params,
			final OnJsonResponseListener<T> listener, final Class<T> clazz) {
		doJsonObject(context, url, Request.Method.POST, params, listener, clazz);
	}
	
	/**
	 * post��ʽ��ȡjson���ݶ���
	 * 
	 * <p>
	 * Ҫ��ȡ������json�ַ���,��<b>postString</b>����
	 * 
	 * @param context
	 * @param url
	 * @param bean		����bean,���Զ����ձ�����-����ֵ�Ĵ�����ֵ�Է�װΪ��������map
	 * @param listener	��Ӧ�ص�,ֱ�Ӵ�json������Object����
	 * @param clazz		��Ҫ��json��װ�ɶ������������
	 */
	public static <T> void postJsonObject(Context context, String url, 
			final Bean2Paramsable bean,
			final OnJsonResponseListener<T> listener, final Class<T> clazz) {
		Map<String, Object> params = bean2params(bean);
		doJsonObject(context, url, Request.Method.POST, params, listener, clazz);
	}

	/**
	 * ����Volley�첽����ͼƬ
	 * 
	 * @param context
	 * @param imageUrl
	 * @param iv
	 */
	public static void loadImageByVolley(Context context, String imageUrl, ImageView iv) {
		loadImageByVolley(context, imageUrl, iv, null);
	}

	/**
	 * ����Volley�첽����ͼƬ,�лص���
	 * 
	 * @param context
	 * @param imageUrl
	 * @param iv
	 * @param listener ͼƬ�ص��ӿ�,bitmapΪ��ʱ��Ϊ��ȡʧ��
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
	 * ��ȡget��ʽƴ�Ӻ��url
	 * 
	 * <p>����urlΪ"http:www.abc.com/get"����map����Ϊ["a":1, "b":"aaaaaa"]��ƴװ���urlΪ"http:www.abc.com/get?a=1&b=aaaaaa"
	 * 
	 * @param url			ԭurl
	 * @param getParams		��Ҫƴ�ӵĲ���map����,valueֵ������ʱ��Ĭ��utf-8����
	 * @return 				ƴװ��ɵ�url
	 */
	private static String addParams4GetUrl(String url, Map<String, Object> getParams) {
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
	 * ��url�����е�������GET_PARAMS_CHARSET_NAME=utf-8ȥencodeһ��
	 * @param url 	����ǰurl
	 * @return		encode���url
	 */
	private static String encodeGetParamsUrl(String url) {
		// ��������
		String regex = "[\\u4e00-\\u9fa5]+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		while (matcher.find()) {
			String cString = matcher.group();
			try {
				url = url.replaceFirst(cString, URLEncoder.encode(
						cString, GET_PARAMS_CHARSET_NAME));
			} catch (UnsupportedEncodingException e) { }
		}
		return url;
	}

	/**
	 * ������תΪmap����,������Ϊ�������ʹ��
	 * 
	 * <p>map�����б�����������еı���,��������Ϊkey,��������ֵ��Ϊvalue
	 * 
	 * @param bean	��Ҫת���Ķ���
	 * @return		ת�����map����
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
     * ������Ӧheader�����еı����ʽ,���û�еĻ�����Ĭ��ֵ
     * DEFAULT_RESPONSE_CHARSET_NAME = UTF-8
     */
	private static String parseCharset(Map<String, String> headers) {
        String contentType = headers.get(HTTP.CONTENT_TYPE);
        if (contentType != null) {
            String[] params = contentType.split(";");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }

        return DEFAULT_RESPONSE_CHARSET_NAME;
    }

	private static <T> void doJsonObject(Context context, String url, int method, 
			final Map<String, Object> postParams, 
			final OnJsonResponseListener<T> listener, final Class<T> clazz) {
		// �������ĵ�url��ʧ��,��Ҫ��������
		String encodeGetParamsUrl = encodeGetParamsUrl(url);
		
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
			/* ��дparseNetworkResponse�����޸���Ӧ�������ı��뷽ʽ,
			 * ��ȡ��Ӧ���ݱ����ʽ��������,�����ȡʧ����Ĭ����
			 * DEFAULT_RESPONSE_CHARSET_NAME = UTF-8 ����
			 * ע:HttpHeaderParser.parseCacheHeaders(response)�����޸�
			 */
			@Override
			protected Response<JSONObject> parseNetworkResponse(
					NetworkResponse response) {
				try {
					String jsonString = new String(response.data, parseCharset(response.headers));
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
			final Map<String, Object> postParams, final OnStringResponseListener listener) {
		// �������ĵ�url��ʧ��,��Ҫ��������
		String encodeGetParamsUrl = encodeGetParamsUrl(url);
		
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
			/* ��дparseNetworkResponse�����޸���Ӧ�������ı��뷽ʽ,
			 * ��ȡ��Ӧ���ݱ����ʽ��������,�����ȡʧ����Ĭ����
			 * DEFAULT_RESPONSE_CHARSET_NAME = UTF-8 ����
			 */
			@Override
			protected Response<String> parseNetworkResponse(
					NetworkResponse response) {
				String parsed;
		        try {
		            parsed = new String(response.data, parseCharset(response.headers));
		        } catch (UnsupportedEncodingException e) {
		            parsed = new String(response.data);
		        }
		        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
			}
		};
		sRequest.setShouldCache(false);
		requestQueue.add(sRequest);
	}

	public interface OnImageCompleteListener extends ErrorListener {
		/**
		 * ����ͼƬ���
		 * @param bitmap Ϊnullʱ����ͼƬ��ȡʧ��
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
