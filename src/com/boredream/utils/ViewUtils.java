package com.boredream.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ViewUtils {
	// 动态设定ListView的高度
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView
				.getAdapter();

		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;

		if (listAdapter.getCount() > 0) {
			View listItem = listAdapter.getView(0, null, listView);
			listItem.measure(0, 0);
			totalHeight = listItem.getMeasuredHeight() * listAdapter.getCount();

		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();

		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));

		listView.setLayoutParams(params);
	}
}
