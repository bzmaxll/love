/**
 * 
 */
package com.love.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.love.entity.Report;
import com.love.service.PageService;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author bao
 *
 */
@Controller
@Scope("prototype")
public class ReportFormAction extends BaseAction implements ModelDriven<Report> {

	private static final long serialVersionUID = 5803830495106429L;
	@Autowired
	private PageService pageService;
	private Report report = new Report();
	
	public String edit() throws Exception {
		tip = pageService.editReport(report);
		return JSON;
	}

	public String add() throws Exception {
		tip = pageService.addReport(report);

		return JSON;
	}
	
	public String remove() throws Exception {
		tip = pageService.removeReport(report.getId());

		return JSON;
	}
	
	
	@Override
	public Report getModel() {
		return report;
	}
}
