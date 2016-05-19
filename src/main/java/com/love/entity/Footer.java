/**
 * 
 */
package com.love.entity;

/**
 * @author bao
 *
 */
public class Footer {

	private float price;
	
	public String getName() {
		return "设备合计";
	}

	public String getIconCls() {
		return "icon-sum";
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
