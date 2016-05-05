/**
 * 
 */
package com.love.entity;

/**
 * @author bao
 *
 */
public class Product {

	private int id;
	private String name;
	private String model;
	private String brand;
	private String unit;
	private int catId;
	private float price1;
	private float price2;
	private float price3;
	private float price4;
	private float price5;
	private String param;
	private String remark;

	public int getId() {
		return id;
	}

	public String getModel() {
		return model;
	}

	public int getCatId() {
		return catId;
	}

	public String getRemark() {
		return remark;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public String getBrand() {
		return brand;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public float getPrice1() {
		return price1;
	}

	public float getPrice2() {
		return price2;
	}

	public float getPrice3() {
		return price3;
	}

	public float getPrice4() {
		return price4;
	}

	public float getPrice5() {
		return price5;
	}

	public void setPrice1(float price1) {
		this.price1 = price1;
	}

	public void setPrice2(float price2) {
		this.price2 = price2;
	}

	public void setPrice3(float price3) {
		this.price3 = price3;
	}

	public void setPrice4(float price4) {
		this.price4 = price4;
	}

	public void setPrice5(float price5) {
		this.price5 = price5;
	}

}
