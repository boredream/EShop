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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.boredream.BaseFragment;
import com.boredream.eshop.R;
import com.boredream.eshop.activity.DetailActivity;
import com.boredream.eshop.adapter.DealGoodAdapter;
import com.boredream.eshop.bean.DealGood;
import com.boredream.eshop.constants.HandlerWhatConstants;
import com.boredream.eshop.test.Datas;

public class MyFavoriteFragment extends BaseFragment implements OnCheckedChangeListener {
	
	/**
	 * 1-Recent 2-Collect 3-Often
	 */
	private int type;
	private RadioGroup rg;
	private LinearLayout llRecent;
	private LinearLayout llCollect;
	private LinearLayout llOften;
	private ListView lvRecent;
	private ListView lvCollect;
	private ListView lvOften;
	private DealGoodAdapter recentAdapter;
	private DealGoodAdapter collectAdapter;
	private DealGoodAdapter oftenAdapter;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HandlerWhatConstants.GOOD_DETAIL:
				if(msg.obj instanceof DealGood) {
					DealGood dealGood = (DealGood) msg.obj;
					Intent intent = new Intent(activity, DetailActivity.class);
					intent.putExtra("good", dealGood);
					startActivity(intent);
				}
				break;

			default:
				break;
			}
		}
		
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_my_favorite, container, false);
		rg = (RadioGroup) view.findViewById(R.id.myfavorite_rg);
		rg.setOnCheckedChangeListener(this);
		
		llRecent = (LinearLayout) view.findViewById(R.id.layout_recentlist);
		llCollect = (LinearLayout) view.findViewById(R.id.layout_collectlist);
		llOften = (LinearLayout) view.findViewById(R.id.layout_oftenlist);
		lvRecent = (ListView) llRecent.findViewById(R.id.list_lv);
		lvCollect = (ListView) llCollect.findViewById(R.id.list_lv);
		lvOften = (ListView) llOften.findViewById(R.id.list_lv);
		recentAdapter = new DealGoodAdapter(activity, handler);
		collectAdapter = new DealGoodAdapter(activity, handler);
		oftenAdapter = new DealGoodAdapter(activity, handler);
		lvRecent.setAdapter(recentAdapter);
		lvCollect.setAdapter(collectAdapter);
		lvOften.setAdapter(oftenAdapter);
		
		List<DealGood> RecentGoods = Datas.getRecentGoods();
		recentAdapter.addDealGoods(RecentGoods);
		type = 1;
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.myfavorite_rb_recent:
			type = 1;
			llRecent.setVisibility(View.VISIBLE);
			llCollect.setVisibility(View.GONE);
			llOften.setVisibility(View.GONE);
			System.out.println("type"+type);
			break;
		case R.id.myfavorite_rb_collect:
			type = 2;
			llRecent.setVisibility(View.GONE);
			llCollect.setVisibility(View.VISIBLE);
			llOften.setVisibility(View.GONE);
			System.out.println("type"+type);
			break;
		case R.id.myfavorite_rb_often:
			type = 3;
			llRecent.setVisibility(View.GONE);
			llCollect.setVisibility(View.GONE);
			llOften.setVisibility(View.VISIBLE);
			System.out.println("type"+type);
			break;

		default:
			break;
		}
	}

}
