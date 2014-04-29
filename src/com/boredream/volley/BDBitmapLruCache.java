package com.boredream.volley;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BDBitmapLruCache extends LruCache<String, Bitmap> implements ImageCache {
	
    public BDBitmapLruCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
    	if(BDVolleyConfig.IMAGE_CACHE_TYPE == 1) {
    		return value.getRowBytes() * value.getHeight();
    	}
    	return super.sizeOf(key, value);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}