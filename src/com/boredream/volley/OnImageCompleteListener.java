package com.boredream.volley;
import android.graphics.Bitmap;

import com.android.volley.Response.ErrorListener;

public interface OnImageCompleteListener extends ErrorListener {
		/**
		 * 加载图片完成
		 * @param bitmap 为null时代表图片获取失败
		 */
		public void onComplete(Bitmap bitmap);
	}