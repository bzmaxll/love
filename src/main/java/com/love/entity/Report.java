/**
 * 
 */
package com.love.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bao
 *
 */
public class Report {

	private int id;
	private String name;
	private int rootId;
	private int parentId;
	private int depth;
	private int pId;
	private int num;
	private String param;
	private String createTime;

	private String ids;
	private String model;
	private String brand;
	private String unit;
	private float price4;
	private List<Report> children;
	
	public void addChildren(Report report) {
		if(children == null){
			children = new ArrayList<Report>();
		}
		
		children.add(report);
	}
	
	public float getPrice() {
		if(depth == 3){
			return num * price4;
		}else{
			int price = 0;
			if(children != null){
				for (Report report : children) {
					price += report.getPrice();
				}
			}
			
			return price;
		}
	}
	
	public String getLink() {
		return depth == 0 ? "<a href='javascript:void(0)' onclick='editReport(" + id + ")'>修改</a>" : "";
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getParentId() {
		return parentId;
	}

	public int getDepth() {
		return depth;
	}

	public int getpId() {
		return pId;
	}

	public int getNum() {
		return num;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<Report> getChildren() {
		return children;
	}

	public void setChildren(List<Report> children) {
		this.children = children;
	}

	public int getRootId() {
		return rootId;
	}

	public void setRootId(int rootId) {
		this.rootId = rootId;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getModel() {
		return model;
	}

	public String getBrand() {
		return brand;
	}

	public String getUnit() {
		return unit;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getIds() {
		return ids;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public void setPrice4(float price4) {
		this.price4 = price4;
	}

	public float getPrice4() {
		return price4;
	}
}
