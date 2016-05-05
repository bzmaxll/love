/**
 * 
 */
package com.love.action;

import java.io.File;

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
public class ImportAction extends BaseAction {

	private static final long serialVersionUID = 618862351149919135L;
	@Autowired
	private PageService pageService;
	private File attachment;
	private String attachmentContentType;
	private String attachmentFileName;

	@Override
	public String execute() throws Exception {
		if (attachment == null) {
			tip = "没有文件！";
			return JSON;
		}
		
		if(!attachmentContentType.equalsIgnoreCase("application/vnd.ms-excel") && !attachmentContentType.equalsIgnoreCase("application/x-msexcel")){
			tip = "不是xls文件！";
			return JSON;
		}

		tip = pageService.importReport(attachment, attachmentFileName);
		return JSON;
	}

	@Override
	public String getTip() {
		return tip;
	}
	
	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}

	public void setAttachmentContentType(String attachmentContentType) {
		this.attachmentContentType = attachmentContentType;
	}

	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}
}
