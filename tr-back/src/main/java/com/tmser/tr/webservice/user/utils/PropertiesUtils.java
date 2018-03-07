/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.webservice.user.utils;

import java.util.ResourceBundle;


/**
 * <pre>
 * 
 * </pre>
 *
 * @author tmser
 * @version $Id: ConnectUtils.java, v 1.0 2015年11月30日 下午5:31:44 tmser Exp $
 */
public class PropertiesUtils {

  
  public static final String proper = "config.init.ws_user_syc";
  
  public static String getValueByKey(String proper,String key){
	  try {
		ResourceBundle resource = ResourceBundle.getBundle(proper);
		String value = resource.getString(key);
		return value;
	} catch (Exception e) {
		throw new RuntimeException("读取配置文件失败！",e);
	}
  }

}
