package com.boredream.eshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.boredream.eshop.R;

public class CategoryRootAdapter extends BaseAdapter {

	private int type;//0root 1second
	private LayoutInflater inflater;
	
	public CategoryRootAdapter(Context context, int type) {
		this.inflater = LayoutInflater.from(context);
		this.type = type;
	}
	
	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_text, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.textitem_tv_name);
			holder.tvInfo = (TextView) convertView.findViewById(R.id.textitem_tv_info);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(type == 1) {
			holder.tvName.setText("二级商品类型" + (position+1));
		} else {
			holder.tvName.setText("基本商品类型" + (position+1));
		}
		holder.tvInfo.setText("详情XXX");
		return convertView;
	}
	
	static class ViewHolder {
		TextView tvName;
		TextView tvInfo;
	}

}
