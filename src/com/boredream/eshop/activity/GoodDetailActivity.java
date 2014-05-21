package com.boredream.eshop.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.boredream.BaseActivity;
import com.boredream.eshop.R;
import com.boredream.eshop.bean.DealGood;

public class GoodDetailActivity extends BaseActivity implements OnClickListener {

	private TextView tvName;
	private TextView tvOldPrice;
	private TextView tvPrice;
	private Button btnGo2Cart;

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
		btnGo2Cart = (Button) findViewById(R.id.app_gocart_btn);
		
		btnGo2Cart.setOnClickListener(this);
	}

	private void loadData() {
		tvName.setText(good.getName());
		tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		tvOldPrice.setText("гд"+good.getOldPrice());
		tvPrice.setText("гд"+good.getPrice());
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.app_gocart_btn:
			intent = new Intent(this, MainActivity.class);
			intent.putExtra("type", 3);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
