package com.boredream.volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class BDJsonRequest<T> extends BDRequest<T> {
	private Gson mGson;
	private Class<T> mClazz;
	
	public BDJsonRequest(
			int method,
			String url,
			Class<T> mClazz,
			BDListener<T> mListener) {
		this(method, url, null, mClazz, mListener);
	}
	
	public BDJsonRequest(
			int method,
			String url,
			Map<String, Object> params,
			Class<T> mClazz,
			BDListener<T> mListener) {
		super(method, url, params, mListener);
		init(mClazz);
	}
	
	private void init(Class<T> mClazz) {
		this.mGson = new Gson();
		this.mClazz = mClazz;
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data,
					BDVolleyUtils.getCharsetFromHeaders(response.headers));
			return Response.success(mGson.fromJson(json, mClazz),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}
}