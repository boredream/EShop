package com.boredream.eshop.test;

import java.util.ArrayList;
import java.util.List;

import com.boredream.eshop.bean.DealGood;
import com.boredream.eshop.bean.Good;

public class Datas {
	
	public static List<Good> getHotGoods() {
		List<Good> goods = new ArrayList<Good>();
		for(int i=0; i<10; i++) {
			Good good = new Good();
			good.setName("热销商品"+i);
			good.setPrice(i+1.99+"元");
			goods.add(good);
		}
		return goods;
	}
	public static List<Good> getSaleGoods() {
		List<Good> goods = new ArrayList<Good>();
		for(int i=0; i<10; i++) {
			Good good = new Good();
			good.setName("促销商品"+i);
			good.setOldPrice(i+0.88+"元");
			good.setPrice(i+1.99+"元");
			good.setSale(true);
			goods.add(good);
		}
		return goods;
	}
	public static List<Good> getNewGoods() {
		List<Good> goods = new ArrayList<Good>();
		for(int i=0; i<10; i++) {
			Good good = new Good();
			good.setName("新商品"+i);
			good.setPrice(i+1.99+"元");
			goods.add(good);
		}
		return goods;
	}
	
	public static List<DealGood> getRecentGoods() {
		List<DealGood> goods = new ArrayList<DealGood>();
		for(int i=0; i<2; i++) {
			DealGood good = new DealGood();
			good.setName("热销商品"+i);
			good.setOldPrice(i+0.88+"元");
			good.setPrice(i+1.99+"元");
			good.setSale(true);
			goods.add(good);
		}
		for(int i=0; i<4; i++) {
			DealGood good = new DealGood();
			good.setName("热销商品"+i);
			good.setPrice(i+1.99+"元");
			goods.add(good);
		}
		return goods;
	}
}
