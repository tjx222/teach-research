/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.web.tag;

import com.tmser.tr.manage.resources.service.ResViewService;
import com.tmser.tr.utils.SpringContextHolder;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tmser
 * @version $Id: IsViewerFunction.java, v 1.0 2015年12月25日 下午2:50:05 tmser Exp $
 */
public class IsViewerFunction {

	public static boolean isView(String ext){
		ResViewService rs = SpringContextHolder.getBean(ResViewService.class);
		if(rs != null ){
			return rs.supports(ext);
		}
		return false;
	}
}
