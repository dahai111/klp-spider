package com.spider.klp.spring;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

public class ContextListener extends ContextLoaderListener
{
	
	private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		logger.info("......EqianzhuangContextListener......");
		super.contextInitialized(event);
	}
}
