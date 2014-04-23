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
	 * �б���dialog
	 */
	public static void showListDialog(Context context, String title, String[] items, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setItems(items, onClickListener);
		builder.setNegativeButton("ȡ��", null);
		builder.show();
	}
	
	/**
	 * �б���dialog
	 */
	public static void showListDialog(Context context, String title, List<String> items, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setItems(items.toArray(new String[items.size()]), onClickListener);
		builder.setNegativeButton("ȡ��", null);
		builder.show();
	}

	/**
	 * ��ͨ��ʾdialog
	 */
	public static void showMsgDialog(Context context, String title, String msg) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setNegativeButton("ȷ��", null);
		builder.show();
	}
	
	/**
	 * ��ͨȷ��dialog
	 */
	public static void showConfirmDialog(Context context, String title, String msg, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = new Builder(context);
		if(!TextUtils.isEmpty(title)) {
			builder.setTitle(title);
		}
		builder.setMessage(msg);
		builder.setPositiveButton("ȷ��", onClickListener);
		builder.setNegativeButton("ȡ��", null);
		builder.show();
	}
	
	/**
	 * ѡ��ȡ��Ƭ�ķ����� ����ڷ�װ��activity��onActivityResult()������Intent data������;
	 * ����ͨ��data.getData()��
	 * ͨ��data.getExtras()���Bundle����bundle.get("data")��ȡ���ص�����;
	 * 
	 * @param activity
	 *            ���ø÷�����Activity
	 * @param imageUri
	 *            ��Ƭ���·��
	 */
	public static void showImagePickDialog(final Activity activity, final Uri imageUri) {
		Context dialogContext = new ContextThemeWrapper(activity, android.R.style.Theme_Light);
		String cancel = "����";
		String[] choices = new String[]{"����", "���ֻ���ѡ��"};
		
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
//	 * �Զ��岼��dialog
//	 * 
//	 * <p>
//	 * ���ⰴť������ϵͳ��װ�õ�,ֱ��set.Ҳ�����Զ��崴����ť
//	 * 
//	 */
//	private void showMyLayoutDialog() {
//		View view = View.inflate(this, R.layout.my_dialog_layout, null); // ���һ�������ļ�
//
//		new AlertDialog.Builder(this)
//				.setTitle("�Զ��岼��")
//				.setView(view)
//				.setPositiveButton("ȷ��", null)
//				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						Toast.makeText(DialogDemoActivity.this, "�����ȡ��",
//								Toast.LENGTH_LONG).show();
//					}
//
//				})
//				.show();
//
//		view.findViewById(R.id.button1).setOnClickListener(
//				new OnClickListener() { // �Զ��岼���еĿؼ��ļ���Ӧ����
//
//					@Override
//					public void onClick(View v) {
//						Toast.makeText(DialogDemoActivity.this,
//								"������Զ��岼���еİ�ť1",
//								Toast.LENGTH_LONG).show();
//					}
//				});
//	}
//
//	private int defaultItemIndex = 0; // Ĭ��ѡ����
//
//	/**
//	 * ��ѡ��dialog
//	 * 
//	 * <p>
//	 * ���ⰴťͬ��,����setSingleChoiceItems����ʵ�ֵ�ѡ��dialog,����˵������
//	 */
//	private void showSingleChoiceDialog() {
//
//		new AlertDialog.Builder(this)
//				.setTitle("��ѡ��")
//				.setSingleChoiceItems(
//						new String[] { "ѡ��1", "ѡ��2", "ѡ��3" }, // ��ѡ���м���,����ʲô����
//						defaultItemIndex, // Ĭ�ϵ�ѡ��
//						new DialogInterface.OnClickListener() { // �����ѡ���Ĵ���
//							public void onClick(
//									DialogInterface dialog,
//									int which) { // �������һ��
//								Toast.makeText(DialogDemoActivity.this,
//										"��ѡ����ѡ��" + (which + 1), 0).show();
//								defaultItemIndex = which;
//								dialog.dismiss();
//							}
//						})
//				.setNegativeButton("ȡ��", null)
//				.show();
//	}
//
//	private boolean[] checkedItems = { true, false, false, true }; // ��ѡ���Ĭ��ѡ�����
//
//	/**
//	 * ��ѡ��dialog
//	 * 
//	 * <p>
//	 * ���ⰴťͬ��,����setMultiChoiceItems����ʵ�ֵ�ѡ��dialog,����˵������
//	 */
//	private void showMultiChoiceDialog() {
//		new AlertDialog.Builder(this)
//				.setTitle("��ѡ��")
//				.setMultiChoiceItems(
//						new String[] { "ѡ��1", "ѡ��2", "ѡ��3", "ѡ��4" }, // ��ѡ���item����
//						checkedItems, // ��ѡ���Ĭ��ѡ�����
//						new OnMultiChoiceClickListener() { // ��ѡ��������
//							@Override
//							public void onClick(
//									DialogInterface dialog,
//									int which, // ������λ��
//									boolean isChecked) { // �����ѡ��Ĺ�ѡ���
//								Toast.makeText(
//										DialogDemoActivity.this,
//										"ѡ��" + (which + 1) + " ѡ��״̬Ϊ:"
//												+ isChecked, 0).show();
//							}
//						})
//				.setPositiveButton("ȷ��", null)
//				.setNegativeButton("ȡ��", null)
//				.show();
//	}
//
//	/**
//	 * �б���dialog
//	 * 
//	 * <p>
//	 * ע��:setSingleChoiceItems, setMultiChoiceItems,
//	 * setItems����,ÿ���Ի�������ѡ������һ������, �мɼ���һ��ʹ��,�ᷢ����ֵ�����Ӵ~
//	 */
//	private void showListDialog() {
//		new AlertDialog.Builder(this).setTitle("�б��")
//				// .setSingleChoiceItems(new String[]{"1","2"}, 0, null)
//				// .setMultiChoiceItems(new String[]{"11","22"}, null, null)
//				.setItems(
//						new String[] { "�����ǵػ�", "С����Ģ��" }, // �б�����
//						new DialogInterface.OnClickListener() { // �������
//							@Override
//							public void onClick(
//									DialogInterface dialog,
//									int which) { // ���λ��
//								Toast.makeText(DialogDemoActivity.this,
//										"����˵�" + (which + 1) + "��", 0).show();
//							}
//						})
//				.setNegativeButton("ȷ��", null)
//				.show();
//	}

}
