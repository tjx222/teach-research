/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.config;

import java.util.Properties;

import com.tmser.tr.utils.SpringContextHolder;

/**
 * <pre>
 *   配置文件工具
 * </pre>
 *
 * @author tmser
 * @version $Id: ConfigUtils.java, v 1.0 2016年10月11日 下午5:35:36 tmser Exp $
 */
public abstract class ConfigUtils {
	
	private static Properties config;
	
	public static String readConfig(String name){
		return readConfig(name,"");
	}
	
	public static String readConfig(String name,Object defaultValue){
		return getProperties().getProperty(name, String.valueOf(defaultValue));
	}
	
	private static Properties getProperties(){
		if(config != null){
			return config;
		}
		config = SpringContextHolder.getBean("config");
		return config;
	}
}
