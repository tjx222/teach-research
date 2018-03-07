/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.utils;

import java.io.File;
import org.apache.commons.lang3.StringUtils;

import com.tmser.tr.manage.config.ConfigUtils;

/**
 * <pre>
 * web 工具类
 * </pre>
 *
 * @author tmser
 * @version $Id: WebUtils.java, v 1.0 2015年5月14日 下午1:28:21 tmser Exp $
 */
public abstract class WebUtils {
	
	/**
	 * front base path
	 */
	private static String FRONT_BASE_PATH = null;
	
	/**
	 * back root path
	 */
	private static String BACK_BASE_PATH = null;
	
	private final static String getWebResRootPath(){
		return ConfigUtils.readConfig("webResRootPath","");
	}
	
	private final static String getWebAppRoot(){
		String webRootKey = ConfigUtils.readConfig("front_web_root");
		return StringUtils.isEmpty(webRootKey) ? System.getProperty("jypt.back.root") : webRootKey;
	}
	
	public static final String getRootPath(){
		if(StringUtils.isBlank(FRONT_BASE_PATH)){
			String root = null;
			String webResRootPath = getWebResRootPath();
			if((StringUtils.isNotBlank(webResRootPath) && (new File(webResRootPath).isAbsolute()))){
				root = webResRootPath;
			}else{
				root = getWebAppRoot();
			}
			
			if(StringUtils.isNotBlank(root))
				FRONT_BASE_PATH = new File(root).getAbsolutePath();
		}
		
		if(StringUtils.isBlank(FRONT_BASE_PATH)){
			String root = new File(ClassLoader.getSystemResource("").getFile())
								.getParentFile().getParent();
			if(StringUtils.isNotBlank(root))
				FRONT_BASE_PATH = new File(root).getAbsolutePath();
		}
		
		return FRONT_BASE_PATH;
	}
	
	public static final String getBackRootPath(){
		if(StringUtils.isBlank(BACK_BASE_PATH)){
			String root = System.getProperty("jypt.back.root");
			if(StringUtils.isNotBlank(root))
				BACK_BASE_PATH = new File(root).getAbsolutePath();
		}
		
		if(StringUtils.isBlank(BACK_BASE_PATH)){
			String root = new File(ClassLoader.getSystemResource("").getFile())
								.getParentFile().getParent();
			if(StringUtils.isNotBlank(root))
				BACK_BASE_PATH = new File(root).getAbsolutePath();
		}
		
		return BACK_BASE_PATH;
	}
}
