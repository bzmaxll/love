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
public class Category {

	private int id;
	private String name;
	private int parentId;

	private List<Category> children;
	
	public Category() {
	}
	
	public Category(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void addChildren(Category cat) {
		if(children == null){
			children = new ArrayList<Category>();
		}
		
		children.add(cat);
	}
	
	public int getId() {
		return id;
	}

	public String getText() {
		return name;
	}
	
	public String getName() {
		return name;
	}

	public int getParentId() {
		return parentId;
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

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

}
