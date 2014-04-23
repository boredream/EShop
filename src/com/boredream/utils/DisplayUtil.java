package com.boredream.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;

public class DisplayUtil {

	/**
	 * 获取屏幕宽度
	 */
	public static int getScreenWidth(Activity activity) {
		return activity.getWindow().getWindowManager().getDefaultDisplay().getWidth();
	}
	
	/**
	 * 获取屏幕高度
	 */
	public static int getScreenHeight(Activity activity) {
		return activity.getWindow().getWindowManager().getDefaultDisplay().getHeight();
	}
	
	private static int mWidth = 0;
	/**
	 * 获取onCreate时控件宽度
	 * 无法通过方法调用,需要直接在oncreate里复制方法内代码
	 */
	public static int getViewOnCreateWidth(final View view) {
		ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				mWidth = view.getMeasuredWidth();
				return true;
			}
		});
		return mWidth;
	}
	
	private static int mHeight = 0;
	/**
	 * 获取onCreate时控件高度
	 * 无法通过方法调用,需要直接在oncreate里复制方法内代码
	 */
	public static int getViewOnCreateHeight(final View view) {
		ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				mHeight = view.getMeasuredHeight();
				return true;
			}
		});
		return mHeight;
	}

	/**
	 * px转dip
	 * */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * dip转px
	 * */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
