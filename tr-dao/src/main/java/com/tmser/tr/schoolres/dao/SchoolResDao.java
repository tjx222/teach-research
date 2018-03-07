/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.schoolres.dao;
import java.util.List;
import java.util.Map;

import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;

 /**
 * 听课记录 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LectureRecords.java, v 1.0 2015-03-31 Generate Tools Exp $
 */
public interface SchoolResDao{
	/**
	 * 根据老师分组查找所有分享的档案袋
	 * @return
	 */
	public List<Map<String, Object>> findAllRecordbags(Integer orgID);
	
	/**
	 * 根据老师分组查找所有分享的档案袋，带分页
	 * @return
	 */
	public PageList<Map<String, Object>> findPageRecordbags(Page page,Integer orgID);
}