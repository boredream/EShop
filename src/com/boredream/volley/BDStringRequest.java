package com.boredream.volley;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

public class BDStringRequest extends BDRequest<String> {
	public BDStringRequest(
			int method,
			String url,
			BDListener<String> mListener) {
		super(method, url, mListener);
		this.setShouldCache(false);
	}

	public BDStringRequest(
			int method,
			String url,
			Map<String, Object> params,
			BDListener<String> mListener) {
		super(method, url, params, mListener);
		this.setShouldCache(false);
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		String parsed;
		try {
			parsed = new String(response.data,
					BDVolleyUtils.getCharsetFromHeaders(response.headers));
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		return Response.success(parsed,
				HttpHeaderParser.parseCacheHeaders(response));
	}
}
