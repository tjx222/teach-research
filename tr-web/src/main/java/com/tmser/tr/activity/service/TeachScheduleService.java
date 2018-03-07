/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service;

import java.util.List;

import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.bo.TeachSchedule;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;

/**
 * 教研进度表 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: TeachSchedule.java, v 1.0 2015-05-18 Generate Tools Exp $
 */

public interface TeachScheduleService extends BaseService<TeachSchedule, Integer>{
	
	public List<SchoolTeachCircleOrg> getindex();
	
	PageList<TeachSchedule> findTeachlList(TeachSchedule teachSchedule,Page page);
	
	//保存校际进度表信息
    public void saveTeach(TeachSchedule ts,String originFileName);

	/**
	 * 添加消息通知
	 * @param teachSchedule
	 */
	public void addXiaoXiTongZhi(TeachSchedule teachSchedule);
}
