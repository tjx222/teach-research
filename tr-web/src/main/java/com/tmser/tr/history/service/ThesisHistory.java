/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.thesis.bo.Thesis;

/**
 * <pre>
 *  历史资源-教学文章service
 * </pre>
 *
 * @author dell
 * @version $Id: ThesisHistoryService.java, v 1.0 2016年5月31日 下午4:49:22 dell Exp $
 */
public interface ThesisHistory {
	
   /**
    * 统计某学年教学文章历史资源数
	*/
	Integer getThesisHistory(Integer userId,String code,Integer currentYear);
	
	/**
	 * 分页查询教学论文
	 * @param lp
	 * @return
	 */
	PageList<Thesis> findCourseList(Thesis thesis);
}
