/**
 * 
 */
package com.love.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author bao
 *
 */
public class BaseAction extends ActionSupport {

	private static final long serialVersionUID = 6592041396181974280L;
	protected static final String JSON = "json";
	protected Map<String, Object> result;
	protected String tip;
	protected String msg;
	
	public Map<String, Object> getResult() {
		return result;
	}

	public String getMsg() {
		return msg;
	}

	public String getTip() {
		return tip;
	}
}
