package com.boredream.eshop.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import com.boredream.BaseActivity;
import com.boredream.eshop.R;
import com.boredream.eshop.bean.DealGood;

public class DetailActivity extends BaseActivity {

	private TextView tvName;
	private TextView tvOldPrice;
	private TextView tvPrice;

	private DealGood good;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.good_detail);
		
		init();
		initView();
		loadData();
	}

	private void init() {
		good = (DealGood) bundle.get("good");
		System.out.println(good.getName());
	}
	
	private void initView() {
		tvName = (TextView) findViewById(R.id.item_detail_name);
		tvOldPrice = (TextView) findViewById(R.id.item_detail_price_del);
		tvPrice = (TextView) findViewById(R.id.item_detail_price);
	}

	private void loadData() {
		tvName.setText(good.getName());
		tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		tvOldPrice.setText("гд"+good.getOldPrice());
		tvPrice.setText("гд"+good.getPrice());
	}

}
