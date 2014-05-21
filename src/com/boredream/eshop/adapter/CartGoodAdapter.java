package com.boredream.eshop.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boredream.eshop.R;
import com.boredream.eshop.activity.GoodDetailActivity;
import com.boredream.eshop.bean.DealGood;
import com.boredream.eshop.constants.HandlerWhatConstants;

public class CartGoodAdapter extends BaseAdapter {
	
	private boolean isEdit;
	private Context context;
	private Handler handler;
	private List<DealGood> dealGoods = new ArrayList<DealGood>();

	public List<DealGood> getDealGoods() {
		return dealGoods;
	}

	public CartGoodAdapter(Context context, Handler handler, List<DealGood> DealGoods) {
		this.context = context;
		this.handler = handler;
		this.dealGoods = DealGoods;
		this.isEdit = false;
	}
	
	public CartGoodAdapter(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
		this.isEdit = false;
	}

	public void addDealGoods(List<DealGood> DealGoods) {
		this.dealGoods.addAll(DealGoods);
		this.notifyDataSetChanged();
	}
	
	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	@Override
	public int getCount() {
		return dealGoods.size();
	}

	@Override
	public DealGood getItem(int position) {
		return dealGoods.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.shopping_list_item_new, null);
			holder.ivGood = (ImageView) convertView.findViewById(R.id.shopping_list_item_img);
			holder.tvName = (TextView) convertView.findViewById(R.id.shopping_list_item_content);
			holder.tvOldPrice = (TextView) convertView.findViewById(R.id.shopping_list_item_delprice);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.shopping_list_item_price);
			holder.tvCount = (TextView) convertView.findViewById(R.id.shopping_list_item_num);
			holder.llDel = (LinearLayout) convertView.findViewById(R.id.del_btn_layout);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final DealGood dealGood = getItem(position);
		
		holder.ivGood.setImageResource(R.drawable.ic_launcher);
		holder.ivGood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, GoodDetailActivity.class);
				intent.putExtra("good", dealGood);
				context.startActivity(intent);
			}
		});
		holder.tvName.setText(dealGood.getName());
		holder.tvPrice.setText(dealGood.getPrice()+"元");
		if(dealGood.isSale()) {
			holder.tvOldPrice.setVisibility(View.VISIBLE);
			holder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			holder.tvOldPrice.setText(dealGood.getOldPrice()+"元");
		} else {
			holder.tvOldPrice.setVisibility(View.GONE);
		}
		holder.tvCount.setText(dealGood.getCount()+"");
		
		// 编辑模式下才显示删除按钮
		holder.llDel.setVisibility(isEdit?View.VISIBLE:View.GONE);
		holder.llDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dealGoods.remove(dealGood);
				CartGoodAdapter.this.notifyDataSetChanged();
				handler.sendEmptyMessage(HandlerWhatConstants.REFRESH_CART_INFO);
			}
		});
		
		return convertView;
	}

	static class ViewHolder {
		ImageView ivGood;
		TextView tvName;
		TextView tvOldPrice;
		TextView tvPrice;
		TextView tvCount;
		// 编辑模式
		LinearLayout llDel;
	}

}
