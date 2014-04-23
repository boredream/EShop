package com.boredream.eshop.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.GridView;

import com.boredream.eshop.BaseActivity;
import com.boredream.eshop.R;
import com.boredream.eshop.adapter.GridViewAdapter;
import com.boredream.eshop.adapter.ImagePagerAdatper;

public class HomeActivity extends BaseActivity {

	private ViewPager ImageVp;
	private GridView gvHot;
	private GridView gvNew;
	
	private ImagePagerAdatper imageAdapter;
	private GridViewAdapter hotAdapter;
	private GridViewAdapter newAdapter;
	
	private int currentPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_home);

		ImageVp = (ViewPager) findViewById(R.id.home_vp_image);
		gvHot = (GridView) findViewById(R.id.home_gv_hot);
		gvNew = (GridView) findViewById(R.id.home_gv_new);
		imageAdapter = new ImagePagerAdatper(this);
		ImageVp.setAdapter(imageAdapter);
		ImageVp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(final int position) {
				currentPosition = position;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		hotAdapter = new GridViewAdapter(this);
		gvHot.setAdapter(hotAdapter);
		newAdapter = new GridViewAdapter(this);
		gvNew.setAdapter(newAdapter);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				HomeActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						System.out.println(currentPosition);
						if(currentPosition == 2) {
							ImageVp.setCurrentItem(0);
						} else {
							ImageVp.setCurrentItem(currentPosition+1);
						}
					}
				});
			}
		}, 3000, 3000);
	}
}
