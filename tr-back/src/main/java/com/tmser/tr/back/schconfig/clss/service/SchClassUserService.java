/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.schconfig.clss.service;

import com.tmser.tr.schconfig.clss.bo.SchClassUser;
import com.tmser.tr.schconfig.clss.vo.SchClassUserVo;
import com.tmser.tr.common.service.BaseService;

/**
 * 学校班级用户关联 服务类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClassUser.java, v 1.0 2016-02-19 tmser Exp $
 */

public interface SchClassUserService extends BaseService<SchClassUser, Integer>{
	
	/**
	 * 编辑班级用户
	 * @param vo
	 */
	void editSchClassUser(SchClassUserVo vo);

}
