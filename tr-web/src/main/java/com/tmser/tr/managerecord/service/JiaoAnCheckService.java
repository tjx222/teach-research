/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service;

import org.springframework.ui.Model;


/**
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: JiaoAnCheckService.java, v 1.0 2015年6月5日 下午5:20:36 wangdawei Exp $
 */
public interface JiaoAnCheckService extends CheckRecordInfoService{
	
		/**
		 * 查看教案课题的查阅详情页
		 * @param planInfoId
		 * @param m
		 */
		void viewLessonPlanCheckInfo(Integer planInfoId, Model m);

}
