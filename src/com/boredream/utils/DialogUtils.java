package com.boredream.utils;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.text.TextUtils;

public class DialogUtils {
	

	/**
	 * ��ʾ��Ϣdialog
	 * 
	 * @param context
	 * @param title					��������,����Ϊ��ʱ�������ñ���
	 * @param msg					��ʾ��Ϣ����
	 * @return
	 */
	public static AlertDialog showMsgDialog(Context context, String title, String msg) {
		AlertDialog.Builder builder = new Builder(context);
		if(!TextUtils.isEmpty(title)) {
			builder.setTitle(title);
		}
		AlertDialog dialog = builder
				.setMessage(msg)
				.setNegativeButton("ȷ��", null)
				.show();
		return dialog;
	}
	
	/**
	 * ȷ��dialog
	 * 
	 * @param context
	 * @param title					��������,����Ϊ��ʱ�������ñ���
	 * @param msg					ȷ����Ϣ����
	 * @param onClickListener		ȷ����ť����
	 * @return
	 */
	public static AlertDialog showConfirmDialog(Context context, String title, String msg, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = new Builder(context);
		if(!TextUtils.isEmpty(title)) {
			builder.setTitle(title);
		}
		AlertDialog dialog = builder
				.setMessage(msg)
				.setPositiveButton("ȷ��", onClickListener)
				.setNegativeButton("ȡ��", null)
				.show();
		return dialog;
	}

	/**
	 * �б���dialog
	 * 
	 * @param context
	 * @param title					��������,����Ϊ��ʱ�������ñ���
	 * @param items					����itemѡ�������
	 * @param onClickListener		ȷ����ť����
	 * @return
	 */
	public static AlertDialog showListDialog(Context context, String title, List<String> items, 
			DialogInterface.OnClickListener onClickListener) {
		return showListDialog(context, title, items.toArray(new String[items.size()]), onClickListener);
	}
	
	/**
	 * �б���dialog
	 * 
	 * @param context
	 * @param title					��������,����Ϊ��ʱ�������ñ���
	 * @param items					����itemѡ�������
	 * @param onClickListener		ȷ����ť����
	 * @return
	 */
	public static AlertDialog showListDialog(Context context, String title, String[] items, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = new Builder(context);
		if(!TextUtils.isEmpty(title)) {
			builder.setTitle(title);
		}
		AlertDialog dialog = builder
				.setItems(items, onClickListener)
				.setNegativeButton("ȡ��", null)
				.show();
		return dialog;
	}
	
	/**
	 * ��ѡ��dialog
	 * 
	 * @param context
	 * @param title					��������,����Ϊ��ʱ�������ñ���
	 * @param items					����itemѡ�������
	 * @param defaultItemIndex		Ĭ��ѡ��
	 * @param onClickListener		ȷ����ť����
	 * @return
	 */
	public static AlertDialog showSingleChoiceDialog(Context context, String title, String[] items,
			int defaultItemIndex, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = new Builder(context);
		if(!TextUtils.isEmpty(title)) {
			builder.setTitle(title);
		}
		AlertDialog dialog = builder
				.setSingleChoiceItems(items, defaultItemIndex, onClickListener)
				.setNegativeButton("ȡ��", null)
				.show();
		return dialog;
	}

	/**
	 * ��ѡ��Ի���
	 * 
	 * @param context
	 * @param title							��������,����Ϊ��ʱ�������ñ���
	 * @param items							����itemѡ�������
	 * @param defaultCheckedItems			��ʼ��ѡ��,��itemsͬ����,true�����Ӧλ��ѡ��,��{true, true, false}�����һ����ѡ��,�����ѡ��
	 * @param onMultiChoiceClickListener	��ѡ����
	 * @param onClickListener 				ȷ����ť����
	 * @return
	 */
	public static AlertDialog showMultiChoiceDialog(Context context, String title, String[] items,
			boolean[] defaultCheckedItems, OnMultiChoiceClickListener onMultiChoiceClickListener,
			DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = new Builder(context);
		if(!TextUtils.isEmpty(title)) {
			builder.setTitle(title);
		}
		AlertDialog dialog = builder
				.setMultiChoiceItems(items, defaultCheckedItems, onMultiChoiceClickListener)
				.setPositiveButton("ȷ��", null)
				.setNegativeButton("ȡ��", null)
				.show();
		return dialog;
	}

	
	/**
	 * ѡ��ȡ��Ƭ�ķ���,�����activity��onActivityResult()������,
	 * ����ImageUtils.getImageOnActivityResult��ȡ
	 * 
	 * @param activity
	 *            ���ø÷�����Activity
	 */
	public static void showImagePickDialog(final Activity activity) {
		String title = "ѡ���ȡͼƬ�ķ�ʽ";
		String[] choices = new String[]{"����", "���ֻ���ѡ��"};
		showSingleChoiceDialog(activity, title, choices, 0, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				switch (which) {
				case 0:
					ImageUtils.openCameraImage(activity);
					break;
				case 1:
					ImageUtils.openLocalImage(activity);
					break;
				}
			}
		});
	}

}
