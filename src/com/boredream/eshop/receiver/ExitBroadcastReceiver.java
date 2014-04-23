package com.boredream.eshop.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// 写一个广播的内部类，当收到动作时，结束activity
public class ExitBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		context.unregisterReceiver(this); // 这句话必须要写要不会报错，不写虽然能关闭，会报一堆错
		((Activity) context).finish();
	}
}