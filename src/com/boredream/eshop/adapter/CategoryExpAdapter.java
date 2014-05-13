package com.boredream.eshop.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.boredream.eshop.R;
import com.boredream.eshop.bean.ExpData;

public class CategoryExpAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<ExpData> datas = new ArrayList<ExpData>();
	
	public CategoryExpAdapter(Context context) {
		this.context = context;
		initData();
	}

	private void initData() {
		ArrayList<String> d1 = new ArrayList<String>();
		for(int i=0; i<5; i++) {
			d1.add("   鞋子" + i);
		}
		datas.add(new ExpData("鞋子种类", d1));
		
		ArrayList<String> d2 = new ArrayList<String>();
		for(int i=0; i<5; i++) {
			d2.add("   上衣" + i);
		}
		datas.add(new ExpData("上衣种类", d2));
		
		ArrayList<String> d3 = new ArrayList<String>();
		for(int i=0; i<5; i++) {
			d3.add("   裤子" + i);
		}
		datas.add(new ExpData("裤子种类", d3));
	}

	@Override
	public int getGroupCount() {
		return datas.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return datas.get(groupPosition).getData().size();
	}

	@Override
	public String getGroup(int groupPosition) {
		return datas.get(groupPosition).getName();
	}

	@Override
	public String getChild(int groupPosition, int childPosition) {
		return datas.get(groupPosition).getData().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.item_text, null);
		TextView tv = (TextView) view.findViewById(R.id.textitem_tv_name);
		TextView tvInfo = (TextView) view.findViewById(R.id.textitem_tv_info);
		tv.setText(getGroup(groupPosition));
		tvInfo.setText("详情XXX");
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.item_text, null);
		TextView tv = (TextView) view.findViewById(R.id.textitem_tv_name);
		TextView tvInfo = (TextView) view.findViewById(R.id.textitem_tv_info);
		tv.setText(getChild(groupPosition, childPosition));
		tvInfo.setText("详情XXX");
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

}
