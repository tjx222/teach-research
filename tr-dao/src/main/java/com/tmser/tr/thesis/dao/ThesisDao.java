/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.thesis.dao;

import java.util.List;
import java.util.Map;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.thesis.bo.Thesis;

 /**
 * 上传教学论文 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Thesis.java, v 1.0 2015-03-12 Generate Tools Exp $
 */
public interface ThesisDao extends BaseDAO<Thesis, Integer>{
	
	/**
	 * @author wangyao
	 * @param model
	 * @param subjectId
	 * @param gradeId
	 * @param sysRoleId
	 * @return
	 */
	List<Map<String,Object>> countThesisGroupByAuth(Thesis model,Integer subjectId,Integer gradeId,Integer sysRoleId);

}