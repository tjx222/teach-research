/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.schedule.bo.Schedule;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.schedule.service.ScheduleService;
import com.tmser.tr.schedule.dao.ScheduleDao;
/**
 * 日程表 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Schedule.java, v 1.0 2015-04-08 Generate Tools Exp $
 */
@Service
@Transactional
public class ScheduleServiceImpl extends AbstractService<Schedule, Integer> implements ScheduleService {

	@Autowired
	private ScheduleDao scheduleDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Schedule, Integer> getDAO() {
		return scheduleDao;
	}

}
