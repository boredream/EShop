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
	 * get��ʽ��ȡ�ַ�������(json������������)
	 * 
	 * @param url		������url(������BDVolleyUtils.encodeUrl��������ƴװ����)
	 * @param listener	��Ӧ�ص�
	 */
	public static void getString(Context context, String url,
			final BDListener<String> listener) {
		doString(context, url, Request.Method.GET, null, listener);
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
			final BDListener<String> listener) {
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
			final BDListener<String> listener) {
		Map<String, Object> params = BDVolleyUtils.bean2params(bean);
		doString(context, url, Request.Method.POST, params, listener);
	}
	
	/**
	 * get��ʽ��ȡ���ݶ���
	 * 
	 * <p>
	 * Ҫ��ȡ������json�ַ���,��BDVolleyHttp.getString����
	 * 
	 * @param context
	 * @param url		������url(������BDVolleyUtils.encodeUrl��������ƴװ����)
	 * @param listener	��Ӧ�ص�,ֱ�Ӵ�json������Object����
	 * @param clazz		��Ҫ��json��װ�ɶ������������
	 */
	public static <T> void getJsonObject(Context context, String url,
			final Class<T> clazz, final BDListener<T> listener) {
		doJsonObject(context, url, Request.Method.GET, null, clazz, listener);
	}

	/**
	 * post��ʽ��ȡjson���ݶ���
	 * 
	 * <p>
	 * Ҫ��ȡ������json�ַ���,��BDVolleyHttp.getString����
	 * 
	 * @param context
	 * @param url
	 * @param params	post������<String, Object>
	 * @param listener	��Ӧ�ص�,ֱ�Ӵ�json������Object����
	 * @param clazz		��Ҫ��json��װ�ɶ������������
	 */
	public static <T> void postJsonObject(Context context, String url,
			final Map<String, Object> params,
			final Class<T> clazz, final BDListener<T> mListener) {
		doJsonObject(context, url, Request.Method.POST, params, clazz, mListener);
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
			final Class<T> clazz, final BDListener<T> mListener) {
		Map<String, Object> params = BDVolleyUtils.bean2params(bean);
		doJsonObject(context, url, Request.Method.POST, params, clazz, mListener);
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
