/**
 * 
 */
package com.love.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.love.entity.Footer;
import com.love.entity.Report;
import com.love.service.PageService;

/**
 * @author bao
 *
 */
@Controller
@Scope("prototype")
public class ReportAction extends BaseAction {

	private static final long serialVersionUID = -6219086376338701012L;
	@Autowired
	private PageService pageService;
	private int total;
	private List<Report> rows;
	private List<Footer> footer = new ArrayList<Footer>();
	private int id;
	
	public String list() throws Exception {
		rows = pageService.findReportList();
		total = rows.size();
		
		return JSON;
	}

	public String detail() throws Exception {
		rows = pageService.findReport(id);
		
		Footer f = new Footer();
		f.setPrice(rows.get(0).getPrice());
		footer.add(f);
		total = rows.size();
		
		return JSON;
	}
	
	public String delete() throws Exception {
		tip = pageService.deleteReport(id);
		return JSON;
	}

	public int getTotal() {
		return total;
	}

	public List<Report> getRows() {
		return rows;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Footer> getFooter() {
		return footer;
	}
}
