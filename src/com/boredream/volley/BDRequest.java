package com.boredream.volley;

import java.util.Map;
import java.util.Map.Entry;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;

public abstract class BDRequest<T> extends Request<T> {
	private BDListener<T> mListener;
	private Map<String, Object> postParams;
	
	public BDRequest(
			int method,
			String url,
			BDListener<T> mListener) {
		this(method, url, null, mListener);
	}
	
	public BDRequest(
			int method,
			String url,
			Map<String, Object> params,
			BDListener<T> mListener) {
		super(method, url, null);
		init(method, url, null, mListener);
	}
	
	// 所有继承自BDRequest的都会运行这个初始化方法
	private void init(int method, String url, Map<String, Object> params, BDListener<T> mListener) {
		this.mListener = mListener;
		if(method == Method.POST) {
			if(params != null && params.size() > 0) {
				this.postParams = params;
			}
		}
		url = BDVolleyUtils.encodeUrl(url);
	}
	
	@Override
	public String getUrl() {
		System.out.println("url = " + super.getUrl());
		return super.getUrl();
	}
	
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

	@Override
	public void deliverError(VolleyError error) {
		mListener.onErrorResponse(error);
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}
}