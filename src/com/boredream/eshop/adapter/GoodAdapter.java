package com.boredream.eshop.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boredream.eshop.R;
import com.boredream.eshop.bean.Good;

public class GoodAdapter extends BaseAdapter {

	private Context context;
	private List<Good> goods = new ArrayList<Good>();

	public GoodAdapter(Context context, List<Good> goods) {
		this.context = context;
		this.goods = goods;
	}
	
	public GoodAdapter(Context context) {
		this.context = context;
	}
	
	public void addGoods(List<Good> goods) {
		this.goods.addAll(goods);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return goods.size();
	}

	@Override
	public Good getItem(int position) {
		return goods.get(position);
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
			convertView = View.inflate(context, R.layout.item_good, null);
			holder.ivGood = (ImageView) convertView.findViewById(R.id.index_item_img);
			holder.ivSale = (ImageView) convertView.findViewById(R.id.sale_item_img);
			holder.tvName = (TextView) convertView.findViewById(R.id.index_item_content);
			holder.tvOldPrice = (TextView) convertView.findViewById(R.id.index_item_old_price);
			holder.tvPrice = (TextView) convertView.findViewById(R.id.index_item_price);
			holder.ivAdd2Car = (ImageView) convertView.findViewById(R.id.index_item_iv_buy);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Good good = getItem(position);
		holder.tvName.setText(good.getName());
		holder.tvPrice.setText(good.getPrice());
		if(good.isSale()) {
			holder.tvOldPrice.setVisibility(View.VISIBLE);
			holder.ivSale.setVisibility(View.VISIBLE);
			holder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); 
			holder.tvOldPrice.setText(good.getOldPrice());
		} else {
			holder.tvOldPrice.setVisibility(View.GONE);
			holder.ivSale.setVisibility(View.GONE);
		}
		
		holder.ivGood.setImageResource(R.drawable.ic_launcher);
		
		return convertView;
	}

	static class ViewHolder {
		ImageView ivGood;
		ImageView ivSale;
		TextView tvName;
		TextView tvOldPrice;
		TextView tvPrice;
		ImageView ivAdd2Car;
	}

}
