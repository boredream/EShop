package com.boredream.eshop.fragment;

import java.text.DecimalFormat;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.boredream.BaseFragment;
import com.boredream.eshop.R;
import com.boredream.eshop.adapter.CartGoodAdapter;
import com.boredream.eshop.bean.DealGood;
import com.boredream.eshop.constants.HandlerWhatConstants;
import com.boredream.eshop.test.Datas;
import com.boredream.eshop.tool.CaculateUtil;
import com.boredream.utils.ViewUtils;

public class CartFragment extends BaseFragment implements OnClickListener {

	private LinearLayout llEdit;
	private TextView tvEdit;
	// 购物车为空时
	private FrameLayout flEmpty;
	private Button btnRencent;
	private Button btnCollect;
	// 购物车有货物时
	private TextView tvCount;
	private ListView lvCart;
	private CartGoodAdapter adapter;
	private TextView tvTotalPrice;
	private Button btnCheckout;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HandlerWhatConstants.REFRESH_CART_INFO:
				refreshCartInfo();
				break;

			default:
				break;
			}
		}

	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_cart, container, false);
		
		// 标题栏
		llEdit = (LinearLayout) view.findViewById(R.id.topbar_settlement_lin);
		tvEdit = (TextView) view.findViewById(R.id.topbar_edit_tv);
		
		llEdit.setOnClickListener(this);
		// 购物车为空时
		flEmpty = (FrameLayout) view.findViewById(R.id.shopping_list_empty_layout);
		btnRencent = (Button) view.findViewById(R.id.cart_btn_recent);
		btnCollect = (Button) view.findViewById(R.id.cart_btn_collect);
		
		btnRencent.setOnClickListener(this);
		btnCollect.setOnClickListener(this);
		
		// 有货物时
		tvCount = (TextView) view.findViewById(R.id.item_num_tv);
		lvCart = (ListView) view.findViewById(R.id.shopping_list_item_list);
		tvTotalPrice = (TextView) view.findViewById(R.id.shopping_cart_total_money);
		btnCheckout = (Button) view.findViewById(R.id.bottom_settlement_btn);
		
		adapter = new CartGoodAdapter(activity, handler, Datas.getDealGoods());
		lvCart.setAdapter(adapter);
		btnCheckout.setOnClickListener(this);
		
		
		refreshCartInfo();
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	private void refreshCartInfo() {
		List<DealGood> dealGoods = adapter.getDealGoods();
		if(dealGoods.size() > 0) {
			flEmpty.setVisibility(View.GONE);
			ViewUtils.setListViewHeightBasedOnChildren(lvCart);
			
			tvCount.setText(dealGoods.size()+"");
			double totalPrice = CaculateUtil.caculateTotalPrice(dealGoods);
			DecimalFormat df = new DecimalFormat(".##");
			tvTotalPrice.setText("￥"+ df.format(totalPrice));
		} else {
			flEmpty.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.topbar_settlement_lin:
			if(adapter.isEdit()) {
				tvEdit.setText("编辑");
				adapter.setEdit(false);
			} else {
				tvEdit.setText("完成");
				adapter.setEdit(true);
			}
			adapter.notifyDataSetChanged();
			break;
		case R.id.cart_btn_recent:
			activity.myFavoriteType = 0;
			((RadioButton)activity.rg.getChildAt(2)).setChecked(true);
			break;
		case R.id.cart_btn_collect:
			activity.myFavoriteType = 1;
			((RadioButton)activity.rg.getChildAt(2)).setChecked(true);
			break;
		case R.id.bottom_settlement_btn:
			
			break;

		default:
			break;
		}
	}

}
