package com.boredream.eshop.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.boredream.BaseActivity;
import com.boredream.eshop.R;
import com.boredream.eshop.fragment.CarFragment;
import com.boredream.eshop.fragment.CategoryFragment;
import com.boredream.eshop.fragment.HomeFragment;
import com.boredream.eshop.fragment.MyFavoriteFragment;
import com.boredream.eshop.fragment.SettingFragment;
import com.boredream.eshop.receiver.ExitBroadcastReceiver;
import com.boredream.utils.DialogUtils;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener {

	private int CURRENT_TAB = 1; // 当前选项卡索引

	private FragmentTransaction ft;
	private FragmentManager fm;
	private HomeFragment homeFragment;
	private CategoryFragment categoryFragment;
	private MyFavoriteFragment myFavoriteFragment;
	private CarFragment carFragment;
	private SettingFragment settingFragment;
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

	public void attachFragmentMyFavorite() {

		if (myFavoriteFragment == null) {
			myFavoriteFragment = new MyFavoriteFragment();
			ft.add(android.R.id.tabcontent, myFavoriteFragment, "myfavorite");
		} else {
			ft.attach(myFavoriteFragment);
		}
	}
	
	public void attachFragmentCar() {
		
		if (carFragment == null) {
			carFragment = new CarFragment();
			ft.add(android.R.id.tabcontent, carFragment, "car");
		} else {
			ft.attach(carFragment);
		}
	}

	public void attachFragmentSetting() {

		if (settingFragment == null) {
			settingFragment = new SettingFragment();
			ft.add(android.R.id.tabcontent, settingFragment, "more");
		} else {
			ft.attach(settingFragment);
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
		case R.id.main_tab_my_favorite:
			attachFragmentMyFavorite();
			CURRENT_TAB = 3;
			break;
		case R.id.main_tab_car:
			attachFragmentCar();
			CURRENT_TAB = 4;
			break;
		case R.id.main_tab_more:
			attachFragmentSetting();
			CURRENT_TAB = 5;
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
		myFavoriteFragment = (MyFavoriteFragment) fm.findFragmentByTag("myfavorite");
		carFragment = (CarFragment) fm.findFragmentByTag("car");
		settingFragment = (SettingFragment) fm.findFragmentByTag("more");
		ft = fm.beginTransaction();

		/** 如果存在Detaches掉 */
		if (homeFragment != null)
			ft.detach(homeFragment);

		/** 如果存在Detaches掉 */
		if (categoryFragment != null)
			ft.detach(categoryFragment);

		/** 如果存在Detaches掉 */
		if (myFavoriteFragment != null)
			ft.detach(myFavoriteFragment);
		
		/** 如果存在Detaches掉 */
		if (carFragment != null)
			ft.detach(carFragment);

		/** 如果存在Detaches掉 */
		if (settingFragment != null)
			ft.detach(settingFragment);
	}

}
