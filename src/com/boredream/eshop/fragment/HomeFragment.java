package com.boredream.eshop.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.boredream.BaseFragment;
import com.boredream.eshop.R;
import com.boredream.eshop.adapter.GoodAdapter;
import com.boredream.eshop.bean.Good;
import com.boredream.eshop.test.Datas;
import com.boredream.utils.ViewUtils;

public class HomeFragment extends BaseFragment implements OnCheckedChangeListener {
	
	/**
	 * 1-hot 2-sale 3-new
	 */
	private int type;
	
	private RadioGroup rg;
	private LinearLayout llHot;
	private LinearLayout llSale;
	private LinearLayout llNew;
	private ListView lvHot;
	private ListView lvSale;
	private ListView lvNew;
	private GoodAdapter hotAdapter;
	private GoodAdapter saleAdapter;
	private GoodAdapter newAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_home, container, false);
		rg = (RadioGroup) view.findViewById(R.id.home_rg);
		rg.setOnCheckedChangeListener(this);
		
		llHot = (LinearLayout) view.findViewById(R.id.layout_hotlist);
		llSale = (LinearLayout) view.findViewById(R.id.layout_salelist);
		llNew = (LinearLayout) view.findViewById(R.id.layout_newlist);
		lvHot = (ListView) llHot.findViewById(R.id.list_lv);
		lvSale = (ListView) llSale.findViewById(R.id.list_lv);
		lvNew = (ListView) llNew.findViewById(R.id.list_lv);
		hotAdapter = new GoodAdapter(activity);
		saleAdapter = new GoodAdapter(activity);
		newAdapter = new GoodAdapter(activity);
		lvHot.setAdapter(hotAdapter);
		lvSale.setAdapter(saleAdapter);
		lvNew.setAdapter(newAdapter);
		
		List<Good> hotGoods = Datas.getHotGoods();
		hotAdapter.addGoods(hotGoods);
		List<Good> saleGoods = Datas.getSaleGoods();
		saleAdapter.addGoods(saleGoods);
		List<Good> newGoods = Datas.getNewGoods();
		newAdapter.addGoods(newGoods);
		type = 1;
		ViewUtils.setListViewHeightBasedOnChildren(lvHot);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.hot:
			type = 1;
			llHot.setVisibility(View.VISIBLE);
			llSale.setVisibility(View.GONE);
			llNew.setVisibility(View.GONE);
			ViewUtils.setListViewHeightBasedOnChildren(lvHot);
			System.out.println("type"+type);
			break;
		case R.id.sale:
			type = 2;
			llHot.setVisibility(View.GONE);
			llSale.setVisibility(View.VISIBLE);
			llNew.setVisibility(View.GONE);
			ViewUtils.setListViewHeightBasedOnChildren(lvSale);
			System.out.println("type"+type);
			break;
		case R.id.new_product:
			type = 3;
			llHot.setVisibility(View.GONE);
			llSale.setVisibility(View.GONE);
			llNew.setVisibility(View.VISIBLE);
			ViewUtils.setListViewHeightBasedOnChildren(lvNew);
			System.out.println("type"+type);
			break;

		default:
			break;
		}
	}

}
