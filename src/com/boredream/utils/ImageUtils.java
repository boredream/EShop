package com.boredream.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class ImageUtils {
	
	public static final int GET_IMAGE_BY_CAMERA = 5001;
	public static final int GET_IMAGE_FROM_PHONE = 5002;

	/**
	 * ����һ������
	 * 
	 * @param context
	 * @return ����ͼƬ��uri
	 */
	public static Uri getImagePathUri(Context context) {
		Uri imageFilePath = null;
		String status = Environment.getExternalStorageState();
		SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
		long time = System.currentTimeMillis();
		String imageName = timeFormatter.format(new Date(time));
		// ContentValues������ϣ��������¼������ʱ������������Ϣ
		ContentValues values = new ContentValues(3);
		values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
		values.put(MediaStore.Images.Media.DATE_TAKEN, time);
		values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
		if (status.equals(Environment.MEDIA_MOUNTED)) {// �ж��Ƿ���SD��,����ʹ��SD���洢,��û��SD��ʱʹ���ֻ��洢
			imageFilePath = context.getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		} else {
			imageFilePath = context.getContentResolver().insert(
					MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
		}
		Log.i("", "���ɵ���Ƭ���·����" + imageFilePath.toString());
		return imageFilePath;
	}
	
	public static void openCameraImage(final Activity activity, final Uri imageFilePath) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFilePath);
		activity.startActivityForResult(intent, ImageUtils.GET_IMAGE_BY_CAMERA);
	}
	public static void openLocalImage(final Activity activity) {
		Intent intent = new Intent();
		/* ����Pictures����Type�趨Ϊimage */
		intent.setType("image/*");
		/* ʹ��Intent.ACTION_GET_CONTENT���Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(intent, ImageUtils.GET_IMAGE_FROM_PHONE);
	}

	/**
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param activity
	 * @param uri
	 * @param request
	 */
	public static void startPhotoZoom(Activity activity, Uri uri, int request) {
		/*
		 * �����������Intent��ACTION����ô֪���ģ���ҿ��Կ����Լ�·���µ�������ҳ
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * ֱ��������Ctrl+F�ѣ�CROP
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// �������crop=true�������ڿ�����Intent��������ʾ��VIEW�ɲü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		activity.startActivityForResult(intent, request);
	}

	public static String getImageAbsolutePath(Context context, String imageUri) {
		Uri uri = Uri.parse(imageUri);
		return getImageAbsolutePath(context, uri);
	}
	
	public static String getImageAbsolutePath(Context context, Uri imageUri) {
		String path = null;
		Cursor cursor = null;
		try {
			ContentResolver contentResolver = context.getContentResolver();
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = contentResolver.query(imageUri, proj, null, null, null);
			if (cursor.moveToFirst()) {
				// ����������ֵ��ȡͼƬ·��
				path = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return path;
	}
	
}
