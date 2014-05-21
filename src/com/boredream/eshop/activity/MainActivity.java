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
import com.boredream.eshop.fragment.CartFragment;
import com.boredream.eshop.fragment.CategoryFragment;
import com.boredream.eshop.fragment.HomeFragment;
import com.boredream.eshop.fragment.MyFavoriteFragment;
import com.boredream.eshop.fragment.SettingFragment;
import com.boredream.eshop.receiver.ExitBroadcastReceiver;
import com.boredream.utils.DialogUtils;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener {

	/**
	 *  ��ǰѡ�����
	 *  0-home 1-cat 2-favorite 3-cart 4-setting
	 */
	private int current_type = 0;
	/**
	 * �ҵ�ϲ��Ŀ¼����
	 * 0-Recent 1-Collect 2-Often
	 */
	public int myFavoriteType = 0;

	private FragmentTransaction ft;
	private FragmentManager fm;
	private HomeFragment homeFragment;
	private CategoryFragment categoryFragment;
	private MyFavoriteFragment myFavoriteFragment;
	private CartFragment cartFragment;
	private SettingFragment settingFragment;
	public RadioGroup rg;
	
	private ExitBroadcastReceiver receiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
	    initView();
	    
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
		
		((RadioButton) rg.getChildAt(current_type)).setChecked(true);

		// �ڵ�ǰ��activity��ע��㲥
		receiver = new ExitBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(BaseActivity.EXIT_ACTION);
		registerReceiver(receiver, filter);
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		Bundle bundle = getIntent().getExtras();
		if(bundle != null) {
			current_type = bundle.getInt("type");
		}
	}
	
	/**
     * �ر�
     */
    public void exitApp() {
        Intent intent = new Intent();
        intent.setAction(BaseActivity.EXIT_ACTION); // ˵������
        sendBroadcast(intent);// �ú������ڷ��͹㲥
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
	
	public void attachFragmentCart() {
		
		if (cartFragment == null) {
			cartFragment = new CartFragment();
			ft.add(android.R.id.tabcontent, cartFragment, "cart");
		} else {
			ft.attach(cartFragment);
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
	 * �ҵ�Tabhost����
	 */
	public void initView() {
		rg = (RadioGroup) findViewById(R.id.main_tab_group);
		rg.setOnCheckedChangeListener(this);
	}
	
    @Override
    public void onBackPressed() {
    	DialogUtils.showConfirmDialog(this, null, "�ر�Ӧ�ó���", 
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
			current_type = 0;
			break;
		case R.id.main_tab_catagory:
			attachFragmentCategory();
			current_type = 1;
			break;
		case R.id.main_tab_my_favorite:
			attachFragmentMyFavorite();
			current_type = 2;
			break;
		case R.id.main_tab_cart:
			attachFragmentCart();
			current_type = 3;
			break;
		case R.id.main_tab_more:
			attachFragmentSetting();
			current_type = 4;
			break;
		default:
			break;
		}
		ft.commit();
	}

	private void initFragment() {
		/** ��Ƭ���� */
		fm = getSupportFragmentManager();
		homeFragment = (HomeFragment) fm.findFragmentByTag("home");
		categoryFragment = (CategoryFragment) fm
				.findFragmentByTag("category");
		myFavoriteFragment = (MyFavoriteFragment) fm.findFragmentByTag("myfavorite");
		cartFragment = (CartFragment) fm.findFragmentByTag("cart");
		settingFragment = (SettingFragment) fm.findFragmentByTag("more");
		ft = fm.beginTransaction();

		/** �������Detaches�� */
		if (homeFragment != null)
			ft.detach(homeFragment);

		/** �������Detaches�� */
		if (categoryFragment != null)
			ft.detach(categoryFragment);

		/** �������Detaches�� */
		if (myFavoriteFragment != null) {
			ft.detach(myFavoriteFragment);
			myFavoriteFragment = null;
		}
		
		/** �������Detaches�� */
		if (cartFragment != null)
			ft.detach(cartFragment);

		/** �������Detaches�� */
		if (settingFragment != null)
			ft.detach(settingFragment);
	}

}
