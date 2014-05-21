package com.boredream.eshop.bean;

import java.io.Serializable;

public class Good implements Serializable {
	private String name;
	private boolean isSale;
	private double oldPrice;
	private double price;

	public double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(double d) {
		this.oldPrice = d;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSale() {
		return isSale;
	}

	public void setSale(boolean isSale) {
		this.isSale = isSale;
	}

}
