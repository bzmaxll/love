/**
 * 
 */
package com.love.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.love.entity.Category;
import com.love.entity.Product;
import com.love.entity.Report;
import com.love.entity.UpdateMap;

/**
 * @author bao
 *
 */
@Repository
public class PageDAO extends CommonDAO {

	@SuppressWarnings("unchecked")
	public List<Category> findCategoryList(){
		return (ArrayList<Category>) this.getSqlMapClientTemplate().queryForList("page.findCategoryList");
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> findProductList(int catId){
		return (ArrayList<Product>) this.getSqlMapClientTemplate().queryForList("page.findProductList", catId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> findProductListByIds(int[] ids){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		
		return (ArrayList<Product>) this.getSqlMapClientTemplate().queryForList("page.findProductListByIds", map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Report> findReportList(){
		return (ArrayList<Report>) this.getSqlMapClientTemplate().queryForList("page.findReportList");
	}
	
	@SuppressWarnings("unchecked")
	public List<Report> findReportDetailList(int id){
		return (ArrayList<Report>) this.getSqlMapClientTemplate().queryForList("page.findReportDetailList", id);
	}
	
	public int addCategory(Category category){
		return (Integer) this.getSqlMapClientTemplate().insert("page.addCategory", category);
	}
	
	public int addProduct(Product product) {
		return (Integer) this.getSqlMapClientTemplate().insert("page.addProduct", product);
	}
	
	public int addReport(Report report) {
		return (Integer) this.getSqlMapClientTemplate().insert("page.addReport", report);
	}
	
	public int addReport(final List<Report> reportList) {
		return (Integer) this.getSqlMapClientTemplate().execute(new SqlMapClientCallback<Integer>() {
			public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				for (Report report : reportList) {
					executor.update("page.addReport", report);
				}
				return executor.executeBatch();
			}
		});
	}
	
	public int updateCategory(int id, String name){
		UpdateMap map = new UpdateMap("Category");
		map.addField("name", name);
		map.addWhere("id", id);
		
		return this.update(map);
	}
	
	public int deleteCategory(int id){
		UpdateMap map = new UpdateMap("Category");
		map.addField("state", "0");
		map.addWhere("id", id);
		
		return this.update(map);
	}
	
	public int deleteChildCategory(int parentId){
		UpdateMap map = new UpdateMap("Category");
		map.addField("state", "0");
		map.addWhere("parentId", parentId);
		
		return this.update(map);
	}

	public Product findProduct(int id) {
		return (Product) this.getSqlMapClientTemplate().queryForObject("page.findProduct", id);
	}

	public int findProduct(String name, String model, String brand) {
		UpdateMap map = new UpdateMap("Product");
		map.setField("id");
		map.addWhere("name", name);
		map.addWhere("model", model);
		map.addWhere("brand", brand);
		map.addWhere("state", 20);
		
		return this.findForInt(map);
	}
	
	public Report findReport(int id) {
		return (Report) this.getSqlMapClientTemplate().queryForObject("page.findReport", id);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> findReportIdList(int parentId) {
		return (ArrayList<Integer>) this.getSqlMapClientTemplate().queryForList("page.findReportIdList", parentId);
	}
}
