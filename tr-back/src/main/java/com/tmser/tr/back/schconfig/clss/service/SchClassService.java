/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.schconfig.clss.service;

import com.tmser.tr.schconfig.clss.bo.SchClass;
import com.tmser.tr.common.service.BaseService;

/**
 * 学校班级 服务类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClass.java, v 1.0 2016-02-19 tmser Exp $
 */

public interface SchClassService extends BaseService<SchClass, Integer>{

	/**
	 * 删除班级及班级关联的用户记录，真实删除无法恢复
	 * @param clsid
	 */
	public void realDelete(Integer clsid);
}
