/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.task.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmser.tr.back.logger.bo.OperateLog;
import com.tmser.tr.back.logger.service.OperateLogService;
import com.tmser.tr.back.task.Task;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.manage.config.ConfigUtils;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.utils.DateUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: DefaultTmptResourceCleaner.java, v 1.0 2015年11月11日 下午3:06:28 tmser Exp $
 */
@Service("defaultResourceCleanTask")
public class DefaultResourceCleanTask implements Task{
	
	@Autowired
	private ResourcesService resourceService;
	
	@Autowired
	private OperateLogService operateLogService;
 
	/**
	 * 
	 * @see com.tmser.tr.manage.resources.ResourceCleanTask.TmptResourceCleaner#clear()
	 */
	@Override
	public void execute() {
		OperateLog model = new OperateLog();
		model.setCallerClass(this.getClass().getName());
		model.addOrder(" id desc");
		List<OperateLog> logs = operateLogService.find(model, 1);
		Date last =  null;
		if(logs != null && logs.size() > 0){
			last = logs.get(0).getCreatetime();
		}
		LoggerUtils.deleteLogger("auto clear temp resourse.");
		int size = 500;
		while(size > 499){
			Resources rmodel = new Resources();
			rmodel.setState(Resources.S_TEMP);
			Map<String,Object> paramMap = new HashMap<String,Object>();
			StringBuilder sql = new StringBuilder();
			if(last !=  null){
				paramMap.put("lastDate", last);
				sql.append("and `crtDttm` >= :lastDate ");
			}
			sql.append("and `crtDttm` < :curdate");
			paramMap.put("curdate", DateUtils.nextDay(-1));
			rmodel.addCustomCondition(sql.toString(), paramMap);
			rmodel.addCustomCulomn("id");
			rmodel.addOrder("`crtDttm` desc");
			
			List<Resources> res = resourceService.find(rmodel, size);
			size = res.size();
			for(Resources r : res){
				resourceService.deleteResources(r.getId());
			}
		}
	}
	

	/**
	 * @return
	 * @see com.tmser.tr.back.task.Task#desc()
	 */
	@Override
	public String desc() {
		return "定时清理无效的文件实体";
	}

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
	 * @see com.tmser.tr.back.task.Task#cron()
	 */
	@Override
	public String cron() {
		return ConfigUtils.readConfig("task_res_clear_cron","0 0 2 * * ?");
	}

}
