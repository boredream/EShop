package com.boredream.eshop.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// дһ���㲥���ڲ��࣬���յ�����ʱ������activity
public class ExitBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		context.unregisterReceiver(this); // ��仰����ҪдҪ���ᱨ����д��Ȼ�ܹرգ��ᱨһ�Ѵ�
		((Activity) context).finish();
	}
}