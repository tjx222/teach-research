/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service;

/**
 * <pre>
 *  历史资源-听课记录service
 * </pre>
 *
 * @author dell
 * @version $Id: LectureRecordHistory.java, v 1.0 2016年5月31日 下午5:14:26 dell Exp $
 */

public interface LectureRecordHistory {
	
	/**
     * 统计某学年听课记录历史资源数
	 */
	Integer getLectureRecordHistory(Integer userId,String code,Integer currentYear);
}
