package com.boredream.eshop.fragment;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.VolleyError;
import com.boredream.BaseFragment;
import com.boredream.eshop.R;
import com.boredream.eshop.adapter.GridViewAdapter;
import com.boredream.eshop.adapter.ImagePagerAdatper;
import com.boredream.http.VolleyUtils;
import com.boredream.http.VolleyUtils.OnStringResponseListener;

public class HomeFragment extends BaseFragment {
	private ViewPager ImageVp;
	private GridView gvHot;
	private GridView gvNew;

	private ImagePagerAdatper imageAdapter;
	private GridViewAdapter hotAdapter;
	private GridViewAdapter newAdapter;

	private int currentPosition;
	private Timer timer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		VolleyUtils.getJsonObject(activity, "http://42.96.153.196:8080/AndroidService.svc/GetGroupInformationList?groupID=549&userID=2416&page=1", new OnJsonResponseListener<AnnResponse>() {
//
//			@Override
//			public void onResponse(AnnResponse ann) {
//				System.out.println(ann.total);
//			}
//
//			@Override
//			public void onErrorResponse(VolleyError errorInfo) {
//				System.out.println(errorInfo.toString());
//			}
//		}, AnnResponse.class);
		
		Map<String, Object> params = new HashMap<String, Object>();
//		?location=北京&output=json&ak=640f3985a6437dad8135dae98d775a09
		params.put("location", "北京");
		params.put("output", "json");
		params.put("ak", "640f3985a6437dad8135dae98d775a09");
		VolleyUtils.getString(activity, "http://api.map.baidu.com/telematics/v3/weather", params, new OnStringResponseListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.println(error.toString());
			}
			
			@Override
			public void onResponse(String str) {
				System.out.println(str);
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (currentPosition == 2) {
							ImageVp.setCurrentItem(0);
						} else {
							ImageVp.setCurrentItem(currentPosition + 1);
						}
					}
				});
			}
		}, 3000, 3000);
	}

	@Override
	public void onStop() {
		super.onStop();
		timer.cancel();
		timer = null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_home, container, false);

		ImageVp = (ViewPager) view.findViewById(R.id.home_vp_image);
		gvHot = (GridView) view.findViewById(R.id.home_gv_hot);
		gvNew = (GridView) view.findViewById(R.id.home_gv_new);
		imageAdapter = new ImagePagerAdatper(activity);
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
		hotAdapter = new GridViewAdapter(activity);
		gvHot.setAdapter(hotAdapter);
		newAdapter = new GridViewAdapter(activity);
		gvNew.setAdapter(newAdapter);

		return view;
	}

}
