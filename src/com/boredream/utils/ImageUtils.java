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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class ImageUtils {
	
	public static final int GET_IMAGE_BY_CAMERA = 5001;
	public static final int GET_IMAGE_FROM_PHONE = 5002;
	public static Uri imageUriFromCamera;

	/**
	 * 初始化一个图片地址uri,用于保存拍照后的照片
	 * 
	 * @param context
	 * @return 图片的uri
	 */
	public static Uri initImagePathUri(Context context) {
		Uri imageFilePath = null;
		String status = Environment.getExternalStorageState();
		SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
		long time = System.currentTimeMillis();
		String imageName = timeFormatter.format(new Date(time));
		// ContentValues是我们希望这条记录被创建时包含的数据信息
		ContentValues values = new ContentValues(3);
		values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
		values.put(MediaStore.Images.Media.DATE_TAKEN, time);
		values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
		if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
			imageFilePath = context.getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		} else {
			imageFilePath = context.getContentResolver().insert(
					MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
		}
		Log.i("", "生成的照片输出路径：" + imageFilePath.toString());
		return imageFilePath;
	}
	
	public static void openCameraImage(final Activity activity) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		imageUriFromCamera = ImageUtils.initImagePathUri(activity);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
		activity.startActivityForResult(intent, ImageUtils.GET_IMAGE_BY_CAMERA);
	}
	
	public static void openLocalImage(final Activity activity) {
		Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
		intent.setType("image/*");
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(intent, ImageUtils.GET_IMAGE_FROM_PHONE);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param activity
	 * @param uri
	 * @param request
	 */
	public static void startPhotoZoom(Activity activity, Uri uri, int request) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP
		 */
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		activity.startActivityForResult(intent, request);
	}
	
	public static Bitmap decodeBitmapByUri(Context context, Uri uri) {
		Bitmap bitmap = null;
		if(uri != null) {
			bitmap = BitmapFactory.decodeFile(getImageAbsolutePath(context, uri));
		}
		return bitmap;
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
				// 最后根据索引值获取图片路径
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
	
	public static Uri getImageOnActivityResult(Context context, int requestCode, Intent data) {
		Uri uri = null;
		switch (requestCode) {
		// 拍照获取图片
		case ImageUtils.GET_IMAGE_BY_CAMERA:
			// ImageUtils.openCameraImage传入自定义uri时系统会将拍摄的照片保存至此uri中
			// 不传入自定义uri的方式由于是获取缩略图,不采用,可以手动获取压缩实例避免OOM
			uri = ImageUtils.imageUriFromCamera;
			break;
		// 手机相册获取图片
		case ImageUtils.GET_IMAGE_FROM_PHONE:
			uri = data.getData();
			break;
		}
		return uri;
	}
	
}
