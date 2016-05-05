/**
 * 
 */
package com.love;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author bao
 *
 */
public class BootListener implements ServletContextListener {

	static {
		DOMConfigurator.configure(BootListener.class.getResource("/log4j.xml"));
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
	}

}
