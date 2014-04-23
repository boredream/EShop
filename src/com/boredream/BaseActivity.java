package com.boredream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.boredream.eshop.constants.CommonConstants;
import com.boredream.eshop.receiver.ExitBroadcastReceiver;
import com.boredream.utils.DialogUtils;
import com.boredream.utils.ImageUtils;

public abstract class BaseActivity extends Activity {

	public static final String EXIT_ACTION = "com.boredream.action.EXIT";
	private String tag;
	
	protected ProgressDialog progressDialog;
	
	protected BaseApplication application;
	private ExitBroadcastReceiver receiver;
	protected Bundle bundle;
	protected SharedPreferences preferences;

	protected Uri pickImageUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tag = this.getClass().getSimpleName();
		showLog("onCreate()", 2);
		
		application = (BaseApplication) getApplication();
		
		if(getIntent() != null) {
			bundle = getIntent().getExtras();
		}
		preferences = getSharedPreferences(CommonConstants.SP_NAME, MODE_PRIVATE);

		progressDialog = new ProgressDialog(this);
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		// 在当前的activity中注册广播
		receiver = new ExitBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(EXIT_ACTION);
        registerReceiver(receiver, filter);
		
		showLog("onStart()", 2);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		showLog("onResume()", 2);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		showLog("onDestroy()", 2);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		showLog("onStop()", 2);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		showLog("onPause()", 2);
	}
	
	protected void finishActivity() {
		this.finish();
	}
	
	protected void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	protected void showLog(String log) {
		showLog(log, 1);
	}
	
	/**
	 * 显示log
	 * @param log
	 * @param level 1-info; 2-debug; 3-verbose
	 */
	protected void showLog(String log, int level) {
		switch (level) {
		case 1:
			Log.i(tag, log);
			break;
		case 2:
			Log.d(tag, log);
			break;
		case 3:
			Log.v(tag, log);
			break;
		default:
			Log.i(tag, log);
			break;
		}
	}
	
	protected void pickImage() {
		DialogUtils.showImagePickDialog(this, pickImageUri);
	}
	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		showLog("onActivityResult()", 2);
		
		if (resultCode == RESULT_CANCELED) {
			return;
		}

		switch (requestCode) {
		case ImageUtils.GET_IMAGE_BY_CAMERA:
			// data.getExtras().get("data"); return bitmap
			break;
		case ImageUtils.GET_IMAGE_FROM_PHONE:
			pickImageUri = data.getData();
			break;
		default:
			break;
		}
	}
   
}
