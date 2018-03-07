/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.zzjg.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.logger.bo.OperateLog;
import com.tmser.tr.back.logger.service.OperateLogService;
import com.tmser.tr.back.zzjg.SyncSchConstant;
import com.tmser.tr.back.zzjg.exception.SchoolSyncFailedException;
import com.tmser.tr.back.zzjg.service.SynchronizeOrgService;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;

/**
 * <pre>
 *
 * </pre>
 *
 * @author ghw
 * @version $Id: SchoolSynchronousController.java, v 1.0 2016年11月21日 下午3:13:01 ghw Exp $
 */
@Controller
@RequestMapping("/jy/ws")
public class SchoolSynchronousController extends AbstractController {
	@Autowired
	private SynchronizeOrgService synchronizeOrgService;
	@Autowired
	private OperateLogService operateLogService;
	
	@RequestMapping("/school/synchronize")
	@ResponseBody
	private JuiResult synchronousSchool(String appid, String appkey){
		JuiResult result = new JuiResult();
		result.setCallbackType("");
		result.setStatusCode(JuiResult.SUCCESS);
		result.setMessage("同步成功");
		try{
			Date now = new Date();
			synchronizeOrgService.synchronousSchool(appid, appkey,null);
			OperateLog log = new OperateLog();
			log.setModule(LoggerModule.SYNCSCHOOL.getCname());
			log.setCreatetime(now);
			log.setMessage(SyncSchConstant.SYNCSCH_SUCCESS);
			log.setType(SyncSchConstant.SYNCSCH_SUCCESS_TYPE);
			log.setThreadName(appid);
			log.setCallerClass(this.getClass().getName());
			operateLogService.save(log);
		} catch (SchoolSyncFailedException e) {
			logger.error("同步机构失败!",e);
			result.setMessage("同步失败");
			result.setStatusCode(JuiResult.FAILED);
			OperateLog model = new OperateLog();
			//用0表示失败
			model.setType(SyncSchConstant.SYNCSCH_FAILED_TYPE);
			model.setCallerClass(this.getClass().getName());
			model.setCreatetime(e.getTime() ==null ? null : new Date(e.getTime()));
			model.setMessage(SyncSchConstant.SYNCSCH_FAILED);
			model.setModule(LoggerModule.SYNCSCHOOL.getCname());
			model.setThreadName(appid);
			operateLogService.save(model);
			
		}
		 return result;
	}

}
