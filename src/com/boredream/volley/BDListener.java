package com.boredream.volley;

import com.android.volley.Response.ErrorListener;

public interface BDListener<T> extends ErrorListener {
	public void onResponse(T response);
}
