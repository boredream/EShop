package com.boredream.eshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.boredream.BaseFragment;
import com.boredream.eshop.R;
import com.boredream.eshop.adapter.CategoryRootAdapter;

public class CategoryFragment extends BaseFragment{
	private ListView lv;
	private CategoryRootAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_category, container, false);
		
		TextView tvTitile = (TextView) view.findViewById(R.id.titlebar_tv_title);
		tvTitile.setText("商品分类");
		
		lv = (ListView) view.findViewById(R.id.category_lv);
		adapter = new CategoryRootAdapter(activity, 0);
		lv.setAdapter(adapter);
		return view;
	}

}
