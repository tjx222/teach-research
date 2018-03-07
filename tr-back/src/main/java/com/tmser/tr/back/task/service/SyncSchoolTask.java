/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.task.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.logger.bo.OperateLog;
import com.tmser.tr.back.logger.service.OperateLogService;
import com.tmser.tr.back.task.Task;
import com.tmser.tr.back.zzjg.SyncSchConstant;
import com.tmser.tr.back.zzjg.exception.SchoolSyncFailedException;
import com.tmser.tr.back.zzjg.service.SynchronizeOrgService;
import com.tmser.tr.manage.config.ConfigUtils;
import com.tmser.tr.uc.bo.App;
import com.tmser.tr.uc.service.AppService;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: SyncSchoolTask.java, v 1.0 2016年11月30日 上午11:05:43 3020mt Exp $
 */
public class SyncSchoolTask implements Task {
	protected Logger logger = LoggerFactory.getLogger(SyncSchoolTask.class);

	@Autowired
	private SynchronizeOrgService synchronizeOrgService;
	@Autowired
	private AppService appService;
	@Autowired
	private OperateLogService operateLogService;

	/**
	 * @return
	 * @see com.tmser.tr.back.task.Task#code()
	 */
	@Override
	public String code() {
		return this.getClass().getName();
	}

	/**
	 * @return
	 * @see com.tmser.tr.back.task.Task#desc()
	 */
	@Override
	public String desc() {
		return "定时同步学校数据";
	}

	/**
	 * @return
	 * @see com.tmser.tr.back.task.Task#cron()
	 */
	@Override
	public String cron() {
		return ConfigUtils.readConfig("task_sync_school_cron", "0 0 3 * * ?");
	}

	/**
	 * 
	 * @see com.tmser.tr.back.task.Task#execute()
	 */
	@Override
	public void execute() {
		App model = new App();
		model.setType("platform_callback");
		model.setEnable(App.ENABLE);
		List<App> appList = appService.findAll(model);
		if (CollectionUtils.isEmpty(appList)) {
			return;
		}
		for (App app : appList) {
				try {
					Date now = new Date();
					synchronizeOrgService.synchronousSchool(app.getAppid(), app.getAppkey(), null);
					OperateLog log = new OperateLog();
					log.setModule(LoggerModule.SYNCSCHOOL.getCname());
					log.setCreatetime(now);
					log.setMessage(SyncSchConstant.SYNCSCH_SUCCESS);
					log.setType(SyncSchConstant.SYNCSCH_SUCCESS_TYPE);
					log.setThreadName(app.getAppid());
					operateLogService.save(log);
				} catch (SchoolSyncFailedException e) {
					logger.error("同步机构失败!", e);
					OperateLog log = new OperateLog();
					// 用0表示失败
					log.setType(SyncSchConstant.SYNCSCH_FAILED_TYPE);
					log.setCallerClass(this.getClass().getName());
					log.setCreatetime(e.getTime() == null ? null : new Date(e.getTime()));
					log.setMessage(SyncSchConstant.SYNCSCH_FAILED);
					log.setModule(LoggerModule.SYNCSCHOOL.getCname());
					log.setThreadName(app.getAppid());
					operateLogService.save(log);

				}
			}
		}

}
