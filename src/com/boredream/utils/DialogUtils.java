package com.boredream.utils;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class DialogUtils {

	/**
	 * 列表型dialog
	 */
	public static void showListDialog(Context context, String title, String[] items, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setItems(items, onClickListener);
		builder.setNegativeButton("取消", null);
		builder.show();
	}
	
	/**
	 * 列表型dialog
	 */
	public static void showListDialog(Context context, String title, List<String> items, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setItems(items.toArray(new String[items.size()]), onClickListener);
		builder.setNegativeButton("取消", null);
		builder.show();
	}

	/**
	 * 普通提示dialog
	 */
	public static void showMsgDialog(Context context, String title, String msg) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setNegativeButton("确定", null);
		builder.show();
	}
	
	/**
	 * 普通确认dialog
	 */
	public static void showConfirmDialog(Context context, String title, String msg, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = new Builder(context);
		if(!TextUtils.isEmpty(title)) {
			builder.setTitle(title);
		}
		builder.setMessage(msg);
		builder.setPositiveButton("确认", onClickListener);
		builder.setNegativeButton("取消", null);
		builder.show();
	}
	
	/**
	 * 选择取照片的方法， 结果在封装该activity的onActivityResult()方法的Intent data参数中;
	 * 可以通过data.getData()或
	 * 通过data.getExtras()获得Bundle再由bundle.get("data")获取返回的数据;
	 * 
	 * @param activity
	 *            调用该方法的Activity
	 * @param imageUri
	 *            照片输出路径
	 */
	public static void showImagePickDialog(final Activity activity, final Uri imageUri) {
		Context dialogContext = new ContextThemeWrapper(activity, android.R.style.Theme_Light);
		String cancel = "返回";
		String[] choices = new String[]{"拍照", "从手机中选择"};
		
		ListAdapter adapter = new ArrayAdapter<String>(dialogContext, android.R.layout.simple_list_item_1, choices);
		AlertDialog.Builder builder = new AlertDialog.Builder(dialogContext);
		builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				switch (which) {
				case 0:
					ImageUtils.openCameraImage(activity, imageUri);
					break;
				case 1:
					ImageUtils.openLocalImage(activity);
					break;
				}
			}
		});
		builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		AlertDialog pDialog = builder.create();
		pDialog.setOwnerActivity(activity);
		pDialog.show();
	}
	

//	/**
//	 * 自定义布局dialog
//	 * 
//	 * <p>
//	 * 标题按钮可以用系统封装好的,直接set.也可以自定义创建按钮
//	 * 
//	 */
//	private void showMyLayoutDialog() {
//		View view = View.inflate(this, R.layout.my_dialog_layout, null); // 填充一个布局文件
//
//		new AlertDialog.Builder(this)
//				.setTitle("自定义布局")
//				.setView(view)
//				.setPositiveButton("确定", null)
//				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						Toast.makeText(DialogDemoActivity.this, "点击了取消",
//								Toast.LENGTH_LONG).show();
//					}
//
//				})
//				.show();
//
//		view.findViewById(R.id.button1).setOnClickListener(
//				new OnClickListener() { // 自定义布局中的控件的简单响应处理
//
//					@Override
//					public void onClick(View v) {
//						Toast.makeText(DialogDemoActivity.this,
//								"点击了自定义布局中的按钮1",
//								Toast.LENGTH_LONG).show();
//					}
//				});
//	}
//
//	private int defaultItemIndex = 0; // 默认选择项
//
//	/**
//	 * 单选框dialog
//	 * 
//	 * <p>
//	 * 标题按钮同理,利用setSingleChoiceItems方法实现单选框dialog,参数说明如下
//	 */
//	private void showSingleChoiceDialog() {
//
//		new AlertDialog.Builder(this)
//				.setTitle("单选框")
//				.setSingleChoiceItems(
//						new String[] { "选项1", "选项2", "选项3" }, // 单选框有几项,各是什么名字
//						defaultItemIndex, // 默认的选项
//						new DialogInterface.OnClickListener() { // 点击单选框后的处理
//							public void onClick(
//									DialogInterface dialog,
//									int which) { // 点击了哪一项
//								Toast.makeText(DialogDemoActivity.this,
//										"你选择了选项" + (which + 1), 0).show();
//								defaultItemIndex = which;
//								dialog.dismiss();
//							}
//						})
//				.setNegativeButton("取消", null)
//				.show();
//	}
//
//	private boolean[] checkedItems = { true, false, false, true }; // 复选框的默认选择情况
//
//	/**
//	 * 复选框dialog
//	 * 
//	 * <p>
//	 * 标题按钮同理,利用setMultiChoiceItems方法实现单选框dialog,参数说明如下
//	 */
//	private void showMultiChoiceDialog() {
//		new AlertDialog.Builder(this)
//				.setTitle("复选框")
//				.setMultiChoiceItems(
//						new String[] { "选项1", "选项2", "选项3", "选项4" }, // 复选框的item名称
//						checkedItems, // 复选框的默认选择情况
//						new OnMultiChoiceClickListener() { // 复选框点击监听
//							@Override
//							public void onClick(
//									DialogInterface dialog,
//									int which, // 点击项的位置
//									boolean isChecked) { // 点击后复选框的勾选情况
//								Toast.makeText(
//										DialogDemoActivity.this,
//										"选项" + (which + 1) + " 选择状态为:"
//												+ isChecked, 0).show();
//							}
//						})
//				.setPositiveButton("确定", null)
//				.setNegativeButton("取消", null)
//				.show();
//	}
//
//	/**
//	 * 列表型dialog
//	 * 
//	 * <p>
//	 * 注意:setSingleChoiceItems, setMultiChoiceItems,
//	 * setItems三个,每个对话框按需求选择其中一个即可, 切忌几个一起使用,会发生奇怪的事情哟~
//	 */
//	private void showListDialog() {
//		new AlertDialog.Builder(this).setTitle("列表框")
//				// .setSingleChoiceItems(new String[]{"1","2"}, 0, null)
//				// .setMultiChoiceItems(new String[]{"11","22"}, null, null)
//				.setItems(
//						new String[] { "天王盖地虎", "小鸡炖蘑菇" }, // 列表内容
//						new DialogInterface.OnClickListener() { // 点击监听
//							@Override
//							public void onClick(
//									DialogInterface dialog,
//									int which) { // 点击位置
//								Toast.makeText(DialogDemoActivity.this,
//										"点击了第" + (which + 1) + "项", 0).show();
//							}
//						})
//				.setNegativeButton("确定", null)
//				.show();
//	}

}
