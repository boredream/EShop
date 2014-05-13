package com.boredream.eshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.boredream.BaseFragment;
import com.boredream.eshop.R;
import com.boredream.eshop.adapter.CategoryExpAdapter;

public class CategoryFragment extends BaseFragment{
	private ExpandableListView explv;
	private CategoryExpAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_category, container, false);
		explv = (ExpandableListView) view.findViewById(R.id.category_explv);
		adapter = new CategoryExpAdapter(activity);
		explv.setAdapter(adapter);
		TextView tvTitile = (TextView) view.findViewById(R.id.titlebar_tv_title);
		tvTitile.setText("商品分类");
		return view;
	}

}
