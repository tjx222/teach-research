/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.utils;

import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *	处理api接口中的一些数据工具类
 * </pre>
 *
 * @author zpp
 * @version $Id: ApiUtils.java, v 1.0 2016年7月2日 上午11:44:22 zpp Exp $
 */
public class ApiUtils {
	
	public static String formatPath(String path){
		if(StringUtils.isNotEmpty(path)){
			path = path.replaceAll("\\\\", "/");
			int index = path.indexOf("/");
			if(index==0) return path.substring(1,path.length());
			else return path;
		}
		return "";
	}
}
