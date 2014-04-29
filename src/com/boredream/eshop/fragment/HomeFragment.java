package com.boredream.eshop.fragment;

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
import com.boredream.volley.BDListener;
import com.boredream.volley.BDVolley;
import com.boredream.volley.BDVolleyHttp;

public class HomeFragment extends BaseFragment {
	private ViewPager vpImage;
	private GridView gvHot;
	private GridView gvNew;

	private ImagePagerAdatper imageAdapter;
	private GridViewAdapter hotAdapter;
	private GridViewAdapter newAdapter;

	private int currentPosition;
	private Timer timer;

	String url = "http://42.96.153.196:8080/AndroidService.svc/GetGroupInformationList?groupID=549&userID=2416&page=1";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		BDVolleyHttp.getString(getActivity(), url, new BDListener<String>() {
			@Override
			public void onErrorResponse(VolleyError error) {
				System.out.println(error.toString());
			}
			
			@Override
			public void onResponse(String response) {
				System.out.println(response);
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
							vpImage.setCurrentItem(0);
						} else {
							vpImage.setCurrentItem(currentPosition + 1);
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

		vpImage = (ViewPager) view.findViewById(R.id.home_vp_image);
		gvHot = (GridView) view.findViewById(R.id.home_gv_hot);
		gvNew = (GridView) view.findViewById(R.id.home_gv_new);
		imageAdapter = new ImagePagerAdatper(activity, new String[]{
				"https://lh3.googleusercontent.com/-LMUs793rAL4/SUQczGj6CBI/AAAAAAAAJqs/NLBzZMDMhS4/s720/P7300049aasd.JPG",
				"http://www.zhaozuzhiapp.com:145/Items/GetImageByID?imageID=516036",
				"http://www.zhaozuzhiapp.com:145/Items/GetImageByID?imageID=516048"
		});
		vpImage.setAdapter(imageAdapter);
		vpImage.setOnPageChangeListener(new OnPageChangeListener() {

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
