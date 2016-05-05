/**
 * 
 */
package com.love.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.love.service.PageService;


/**
 * @author bao
 *
 */
@Controller
@Scope("prototype")
public class PageAction extends BaseAction {

	private static final long serialVersionUID = -2881931185638235420L;
	@Autowired
	private PageService pageService;
	private int id;
	private int parentId;
	private String name;
	
	private int oldCatId;
	private String pids;
	
	public String home() throws Exception {
		result = pageService.getHome();
		return "home";
	}
	
	public String category_add() throws Exception {
		id = pageService.addCategory(parentId, name);
		tip = id > 0 ? "success" : "fail";
		return JSON;
	}

	public String category_edit() throws Exception {
		tip = pageService.editCategory(id, name);
		return JSON;
	}
	
	public String category_delete() throws Exception {
		tip = pageService.deleteCategory(id);
		return JSON;
	}
	
	public String category_move() throws Exception {
		tip = pageService.moveCategory(id, oldCatId, pids);
		return JSON;
	}
	
	public int getId() {
		return id;
	}

	public int getParentId() {
		return parentId;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOldCatId(int oldCatId) {
		this.oldCatId = oldCatId;
	}

	public void setPids(String pids) {
		this.pids = pids;
	}
}
