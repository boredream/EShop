package com.boredream.volley;

import android.graphics.Bitmap;

import com.android.volley.Response.ErrorListener;

public interface BDImageListener extends ErrorListener {
	/**
	 * ����ͼƬ���
	 * 
	 * @param bitmap	Ϊnullʱ����ͼƬ��ȡʧ��
	 */
	public void onComplete(Bitmap bitmap);
}