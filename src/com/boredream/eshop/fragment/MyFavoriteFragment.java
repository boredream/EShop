package com.boredream.eshop.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.boredream.BaseFragment;
import com.boredream.eshop.R;
import com.boredream.eshop.activity.GoodDetailActivity;
import com.boredream.eshop.adapter.DealGoodAdapter;
import com.boredream.eshop.bean.DealGood;
import com.boredream.eshop.constants.HandlerWhatConstants;
import com.boredream.eshop.test.Datas;

public class MyFavoriteFragment extends BaseFragment implements OnCheckedChangeListener {
	
	private RadioGroup rg;
	private LinearLayout llRecent;
	private LinearLayout llCollect;
	private LinearLayout llOften;
	private FrameLayout rlEmptyRecent;
	private FrameLayout rlEmptyCollect;
	private FrameLayout rlEmptyOften;
	private ListView lvRecent;
	private ListView lvCollect;
	private ListView lvOften;
	private DealGoodAdapter recentAdapter;
	private DealGoodAdapter collectAdapter;
	private DealGoodAdapter oftenAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("onCreateView");
		View view = inflater.inflate(R.layout.main_my_favorite, container, false);
		rg = (RadioGroup) view.findViewById(R.id.myfavorite_rg);
		rg.setOnCheckedChangeListener(this);
		
		llRecent = (LinearLayout) view.findViewById(R.id.layout_recentlist);
		llCollect = (LinearLayout) view.findViewById(R.id.layout_collectlist);
		llOften = (LinearLayout) view.findViewById(R.id.layout_oftenlist);
		rlEmptyRecent = (FrameLayout) llRecent.findViewById(R.id.item_empty_layout);
		rlEmptyCollect = (FrameLayout) llCollect.findViewById(R.id.item_empty_layout);
		rlEmptyOften = (FrameLayout) llOften.findViewById(R.id.item_empty_layout);
		TextView tvRecentEmpty = (TextView) llRecent.findViewById(R.id.item_empty_tv);
		TextView tvCollectEmpty = (TextView) llCollect.findViewById(R.id.item_empty_tv);
		TextView tvOftenEmpty = (TextView) llOften.findViewById(R.id.item_empty_tv);
		lvRecent = (ListView) llRecent.findViewById(R.id.list_lv);
		lvCollect = (ListView) llCollect.findViewById(R.id.list_lv);
		lvOften = (ListView) llOften.findViewById(R.id.list_lv);
		recentAdapter = new DealGoodAdapter(activity);
		collectAdapter = new DealGoodAdapter(activity);
		oftenAdapter = new DealGoodAdapter(activity);
		lvRecent.setAdapter(recentAdapter);
		lvCollect.setAdapter(collectAdapter);
		lvOften.setAdapter(oftenAdapter);
		
		List<DealGood> RecentGoods = Datas.getDealGoods();
		recentAdapter.addDealGoods(RecentGoods);
		
		((RadioButton)rg.getChildAt(activity.myFavoriteType)).setChecked(true);
		
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		checkItem(checkedId);
	}

	private void checkItem(int checkedId) {
		switch (checkedId) {
		case R.id.myfavorite_rb_recent:
			activity.myFavoriteType = 0;
			showRecent();
			break;
		case R.id.myfavorite_rb_collect:
			activity.myFavoriteType = 1;
			showCollect();
			break;
		case R.id.myfavorite_rb_often:
			activity.myFavoriteType = 2;
			showOften();
			break;

		default:
			break;
		}
	}

	private void showOften() {
		llRecent.setVisibility(View.GONE);
		llCollect.setVisibility(View.GONE);
		llOften.setVisibility(View.VISIBLE);
		System.out.println("type"+activity.myFavoriteType);
	}

	private void showCollect() {
		llRecent.setVisibility(View.GONE);
		llCollect.setVisibility(View.VISIBLE);
		llOften.setVisibility(View.GONE);
		System.out.println("type"+activity.myFavoriteType);
	}

	private void showRecent() {
		llRecent.setVisibility(View.VISIBLE);
		llCollect.setVisibility(View.GONE);
		llOften.setVisibility(View.GONE);
		System.out.println("type"+activity.myFavoriteType);
	}

}
