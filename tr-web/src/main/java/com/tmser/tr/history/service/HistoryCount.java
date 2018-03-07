/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.service;

import java.util.List;

import com.tmser.tr.history.vo.HistoryColumn;

/**
 * <pre>
 *  历史资源栏目统计服务
 * </pre>
 *
 * @author tmser
 * @version $Id: HistoryColumn.java, v 1.0 2016年5月18日 下午3:05:47 tmser Exp $
 */
public interface HistoryCount{

	/**
	 * 是否支持当前的code
	 * @param code
	 * @return
	 */
	boolean supports(String code);
	
	/**
	 * 获取当前服务支持的所有栏目
	 * @return
	 */
	List<HistoryColumn> getColumns();
	
	
	/**
	 * 栏目数
	 * @param userId
	 * @param code
	 * @return 负值代表不需要统计，0或正值代表资源数
	 */
	Integer[] count(Integer userId,String code,Integer currentYear);
}
