/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: CheckRecordContainer.java, v 1.0 2015年5月26日 下午2:04:21 tmser Exp $
 */
@Component
@Lazy(false)
public class CheckRecordContainer implements ApplicationContextAware{
	
	private final static Logger logger = LoggerFactory.getLogger(CheckRecordContainer.class);

	private final static Set<CheckRecordService> container = new TreeSet<CheckRecordService>();
	
	public static Set<CheckRecordService> getCheckRecordServiceSet(){
		return container;
		}
	/**
	 * @param applicationContext
	 * @throws BeansException
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		Map<String, CheckRecordService> crsMap = applicationContext.getBeansOfType(CheckRecordService.class);
		for(String name: crsMap.keySet()){
         try {
	         if (logger.isDebugEnabled()) {
	         	logger.debug("Initializing bean [" + name + "]...");
	           }
	                container.add(crsMap.get(name));
	            } catch (Exception e) {
	                throw new FatalBeanException("Error initializing bean [" + name + "]", e);
	            }
	   }
	}

	
}
