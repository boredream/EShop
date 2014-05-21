package com.boredream.eshop.tool;

import java.util.List;

import com.boredream.eshop.bean.DealGood;

public class CaculateUtil {
	
	public static double caculateTotalPrice(List<DealGood> goods) {
		double totalPrice = 0;
		for(DealGood good : goods) {
			totalPrice += good.getPrice();
		}
		return totalPrice;
	}
	
}
