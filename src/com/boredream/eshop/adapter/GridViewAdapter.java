package com.boredream.eshop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.boredream.eshop.R;

public class GridViewAdapter extends BaseAdapter {

	private Context context;

	public GridViewAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_gridview, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.gvitem_iv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.iv.setImageResource(R.drawable.ic_launcher);
		
		return convertView;
	}

	static class ViewHolder {
		ImageView iv;
	}

}
