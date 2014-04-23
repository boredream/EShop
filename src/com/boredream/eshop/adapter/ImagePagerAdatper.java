package com.boredream.eshop.adapter;

import java.util.ArrayList;

import com.boredream.eshop.R;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class ImagePagerAdatper extends PagerAdapter {

	private ArrayList<View> views;// 存放View的ArrayList

	public ImagePagerAdatper(Context context) {
		views = new ArrayList<View>();
		View view1 = View.inflate(context, R.layout.item_image, null);
		ImageView iv1 = (ImageView) view1.findViewById(R.id.imageitem_iv);
		iv1.setImageResource(R.drawable.t1);
		View view2 = View.inflate(context, R.layout.item_image, null);
		ImageView iv2 = (ImageView) view2.findViewById(R.id.imageitem_iv);
		iv2.setImageResource(R.drawable.t2);
		View view3 = View.inflate(context, R.layout.item_image, null);
		ImageView iv3 = (ImageView) view3.findViewById(R.id.imageitem_iv);
		iv3.setImageResource(R.drawable.t3);
		views.add(view1);
		views.add(view2);
		views.add(view3);
	}
	
	public ImagePagerAdatper(ArrayList<View> views) {
		this.views = views;
	}

	/*
	 * 返回View的个数
	 */
	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	/*
	 * 销毁View
	 */
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(views.get(position));
	}

	/*
	 * 初始化
	 */
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(views.get(position), 0);
		return views.get(position);
	}

	/*
	 * 判断View是否来自Object
	 */
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (view == object);
	}

}
