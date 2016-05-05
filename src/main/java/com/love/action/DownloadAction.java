/**
 * 
 */
package com.love.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hisupplier.commons.util.DateUtil;
import com.love.service.PageService;

/**
 * @author bao
 *
 */
@Controller
@Scope("prototype")
public class DownloadAction extends BaseAction {

	private static final long serialVersionUID = -3343939632527082240L;
	@Autowired
	private PageService pageService;
	private String fileName;
	private String fileDir;
	private int id;
	
	public String download() throws Exception {
		fileDir = ServletActionContext.getServletContext().getRealPath("") + "/xls/" + new DateUtil().getTime4();
		fileName = this.pageService.createExcelFile(fileDir, id);

		return SUCCESS;
	}
	
	public InputStream getTargetFile() throws Exception {
		return new FileInputStream(new File(fileDir + "/" + fileName));
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		try {
			return new String(fileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			return "file.xls";
		}
	}
}
