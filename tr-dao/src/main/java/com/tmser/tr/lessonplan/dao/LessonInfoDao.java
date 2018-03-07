/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lessonplan.dao;

import java.util.List;
import java.util.Map;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.lessonplan.bo.LessonInfo;

 /**
 * 课题信息表 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author wangdawei
 * @version $Id: LessonInfo.java, v 1.0 2015-03-05 wangdawei Exp $
 */
public interface LessonInfoDao extends BaseDAO<LessonInfo, Integer>{
	/**
	 * 按模型查询统计
	 * @param model
	 * @return map 包含 userid -- cnt (统计数)
	 */
	List<Map<String,Object>> countLessonGroupByUser(LessonInfo model);
}