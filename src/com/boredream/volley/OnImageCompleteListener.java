package com.boredream.volley;
import android.graphics.Bitmap;

import com.android.volley.Response.ErrorListener;

public interface OnImageCompleteListener extends ErrorListener {
		/**
		 * ����ͼƬ���
		 * @param bitmap Ϊnullʱ����ͼƬ��ȡʧ��
		 */
		public void onComplete(Bitmap bitmap);
	}