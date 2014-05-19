package com.boredream.eshop.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boredream.eshop.R;
import com.boredream.eshop.bean.DealGood;
import com.boredream.eshop.constants.HandlerWhatConstants;

public class DealGoodAdapter extends BaseAdapter {
	
	private Context context;
	private Handler handler;
	private List<DealGood> dealGoods = new ArrayList<DealGood>();

	public DealGoodAdapter(Context context, Handler handler, List<DealGood> DealGoods) {
		this.context = context;
		this.handler = handler;
		this.dealGoods = DealGoods;
	}
	
	public DealGoodAdapter(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
	}
	
	public void addDealGoods(List<DealGood> DealGoods) {
		this.dealGoods.addAll(DealGoods);
		this.notifyDataSetChanged();
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
			convertView = View.inflate(context, R.layout.item_dealgood, null);
			holder.rlImage = (RelativeLayout) convertView.findViewById(R.id.goods_img_wrap);
			holder.ivDealGood = (ImageView) convertView.findViewById(R.id.index_item_img);
			holder.tvSale = (TextView) convertView.findViewById(R.id.cutv);
			holder.tvName = (TextView) convertView.findViewById(R.id.index_item_content);
			holder.tvOldPrice = (TextView) convertView.findViewById(R.id.index_item_old_price);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.index_item_price);
			holder.ivAdd2Car = (ImageView) convertView.findViewById(R.id.index_item_iv_buy);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		final DealGood dealGood = getItem(position);
		holder.tvName.setText(dealGood.getName());
		holder.tvPrice.setText(dealGood.getPrice());
		if(dealGood.isSale()) {
			holder.tvOldPrice.setVisibility(View.VISIBLE);
			holder.tvSale.setVisibility(View.VISIBLE);
			holder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
			holder.tvOldPrice.setText(dealGood.getOldPrice());
		} else {
			holder.tvOldPrice.setVisibility(View.GONE);
			holder.tvSale.setVisibility(View.GONE);
		}
		
		holder.ivDealGood.setImageResource(R.drawable.ic_launcher);
		
		holder.rlImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handler.sendMessage(handler.obtainMessage(
						HandlerWhatConstants.GOOD_DETAIL, dealGood));
			}
		});
		
		return convertView;
	}

	static class ViewHolder {
		RelativeLayout rlImage;
		ImageView ivDealGood;
		TextView tvSale;
		TextView tvName;
		TextView tvOldPrice;
		TextView tvPrice;
		ImageView ivAdd2Car;
	}

}
