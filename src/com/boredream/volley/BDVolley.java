package com.boredream.volley;

import android.app.ActivityManager;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class BDVolley {
	private static RequestQueue mRequestQueue;
	private static ImageLoader mImageLoader;

	private BDVolley() {
		// no instances
	}

	public static RequestQueue getRequestQueue(Context context) {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(context);
		}
		return mRequestQueue;
	}

	public static ImageLoader getImageLoader(Context context) {
		if (mImageLoader == null) {
			mRequestQueue = getRequestQueue(context);
			
			int memClass = ((ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			int cacheSize = 1024 * 1024 * memClass / BDVolleyConfig.IMAGE_CACHE_SCALE;
			int cacheCount = BDVolleyConfig.IMAGE_CACHE_COUNT;
					
			mImageLoader = new ImageLoader(mRequestQueue, new BDBitmapLruCache(
					BDVolleyConfig.IMAGE_CACHE_TYPE==1?cacheSize:cacheCount));
		}
		return mImageLoader;
	}
}
