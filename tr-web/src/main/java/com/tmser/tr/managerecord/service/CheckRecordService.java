/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service;


/**
 * <pre>
 *
 * </pre>
 *
 * @author sysc
 * @version $Id: managerService.java, v 1.0 2015年5月7日 上午10:33:25 sysc Exp $
 */
public interface CheckRecordService extends Comparable<CheckRecordService>{
    
	/**
	 * 管理记录-查阅记录回调接口
	 * @param grade
	 * @param term
	 * @param subject
	 * @return
	 */
	ManagerVO getCheckRecord(Integer grade,Integer subject,Integer term);
	
	/**
	 * 查阅资源的类型
	 * @return
	 */
	Integer[] getType();
	
	/**
	 * 当前查阅资源显示排序
	 * @return
	 */
	Integer getOrder();
	
	
}
