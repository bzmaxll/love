/**
 * 
 */
package com.love.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.love.entity.Product;
import com.love.service.PageService;

/**
 * @author bao
 *
 */
@Controller
@Scope("prototype")
public class ProductAction extends BaseAction {

	private static final long serialVersionUID = 3237846733359263364L;
	@Autowired
	private PageService pageService;
	private int total;
	private int catId;
	private List<Product> rows;
	
	public String list() throws Exception {
		rows = pageService.findProductList(catId);
		total = rows.size();
		
		return JSON;
	}
	
	public int getTotal() {
		return total;
	}
	public List<Product> getRows() {
		return rows;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setRows(List<Product> rows) {
		this.rows = rows;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

}
