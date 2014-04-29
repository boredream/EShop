package com.boredream.eshop.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.boredream.BaseActivity;
import com.boredream.eshop.R;

public class DetailActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		findViewById(R.id.titlebar_btn_back).setVisibility(View.VISIBLE);
		findViewById(R.id.titlebar_btn_back).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						DetailActivity.this.finish();
					}
				});
	}

}
