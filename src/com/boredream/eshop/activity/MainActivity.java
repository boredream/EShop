package com.boredream.eshop.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.boredream.BaseActivity;
import com.boredream.eshop.R;
import com.boredream.eshop.fragment.CategoryFragment;
import com.boredream.eshop.fragment.HomeFragment;
import com.boredream.eshop.fragment.MoreFragment;
import com.boredream.eshop.fragment.SearchFragment;
import com.boredream.eshop.receiver.ExitBroadcastReceiver;
import com.boredream.utils.DialogUtils;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener {

	private int CURRENT_TAB = 1; // 当前选项卡索引

	private FragmentTransaction ft;
	private FragmentManager fm;
	private HomeFragment homeFragment;
	private CategoryFragment categoryFragment;
	private SearchFragment searchFragment;
	private MoreFragment moreFragment;
	private RadioGroup rg;
	
	private ExitBroadcastReceiver receiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
	    initView();
	    
	    ((RadioButton) rg.getChildAt(0)).setChecked(true);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();

		// 在当前的activity中注册广播
		receiver = new ExitBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(BaseActivity.EXIT_ACTION);
		registerReceiver(receiver, filter);
	}
	
	/**
     * 关闭
     */
    public void exitApp() {
        Intent intent = new Intent();
        intent.setAction(BaseActivity.EXIT_ACTION); // 说明动作
        sendBroadcast(intent);// 该函数用于发送广播
        finish();
    }

	public void attachFragmentHome() {
		if (homeFragment == null) {
			homeFragment = new HomeFragment();
			ft.add(android.R.id.tabcontent, homeFragment, "home");
		} else {
			ft.attach(homeFragment);
		}
	}

	public void attachFragmentCategory() {

		if (categoryFragment == null) {
			categoryFragment = new CategoryFragment();
			ft.add(android.R.id.tabcontent, categoryFragment, "category");
		} else {
			ft.attach(categoryFragment);
		}
	}

	public void attachFragmentSearch() {

		if (searchFragment == null) {
			searchFragment = new SearchFragment();
			ft.add(android.R.id.tabcontent, searchFragment, "search");
		} else {
			ft.attach(searchFragment);
		}
	}

	public void attachFragmentMore() {

		if (moreFragment == null) {
			moreFragment = new MoreFragment();
			ft.add(android.R.id.tabcontent, moreFragment, "more");
		} else {
			ft.attach(moreFragment);
		}
	}

	/**
	 * 找到Tabhost布局
	 */
	public void initView() {
		rg = (RadioGroup) findViewById(R.id.main_tab_group);
		rg.setOnCheckedChangeListener(this);
	}
	
    @Override
    public void onBackPressed() {
    	DialogUtils.showConfirmDialog(this, null, "关闭应用程序", 
    			new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						MainActivity.this.exitApp();
					}
		});
    }

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		initFragment();

		switch (checkedId) {
		case R.id.main_tab_home:
			attachFragmentHome();
			CURRENT_TAB = 1;
			break;
		case R.id.main_tab_catagory:
			attachFragmentCategory();
			CURRENT_TAB = 2;
			break;
		case R.id.main_tab_car:
			attachFragmentSearch();
			CURRENT_TAB = 3;
			break;
		case R.id.main_tab_more:
			attachFragmentMore();
			CURRENT_TAB = 4;
			break;
		default:
			break;
		}
		ft.commit();
	}

	private void initFragment() {
		/** 碎片管理 */
		fm = getSupportFragmentManager();
		homeFragment = (HomeFragment) fm.findFragmentByTag("home");
		categoryFragment = (CategoryFragment) fm
				.findFragmentByTag("category");
		searchFragment = (SearchFragment) fm.findFragmentByTag("search");
		moreFragment = (MoreFragment) fm.findFragmentByTag("more");
		ft = fm.beginTransaction();

		/** 如果存在Detaches掉 */
		if (homeFragment != null)
			ft.detach(homeFragment);

		/** 如果存在Detaches掉 */
		if (categoryFragment != null)
			ft.detach(categoryFragment);

		/** 如果存在Detaches掉 */
		if (searchFragment != null)
			ft.detach(searchFragment);

		/** 如果存在Detaches掉 */
		if (moreFragment != null)
			ft.detach(moreFragment);
	}

}
