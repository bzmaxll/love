/**
 * 
 */
package com.love.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.love.entity.Product;
import com.love.service.PageService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author bao
 *
 */
@Controller
@Scope("prototype")
public class ProductFormAction extends BaseAction implements ModelDriven<Product> {

	private static final long serialVersionUID = 1569353510883143409L;
	@Autowired
	private PageService pageService;
	private Product product = new Product();
	private String ids;
	
	public String add() throws Exception {
		tip = pageService.addProduct(product);
		return JSON;
	}
	
	public String delete() throws Exception {
		tip = pageService.deleteProduct(ids);
		return JSON;
	}
	
	@Override
	public Product getModel() {
		return product;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
