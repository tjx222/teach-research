/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.managerecord.service;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 *
 * </pre>
 *
 * @author sysc
 * @version $Id: managerServiceOne.java, v 1.0 2015年5月7日 上午11:42:37 sysc Exp $
 */
public interface ManagerService {
	 /**
	  * 查阅记录列表
	  * 统计查阅记录
	  * @param grade
	  * @param term
	  * @param subject
	  * @return
	  */
      public List<ManagerVO> findRecordList(int grade,int subject,int term );
      
      /**
       * 统计教学管理记录
       * @param term
       * @return
       */
      public List<ManagerVO> findMangerList(Integer term);
      /**
       * 统计教研活动记录
       * @param term
       * @return
       */
      public List<ManagerVO> findActivityList(Integer term);
      
  	/**
  	 * 查阅记录首页
  	 * @param sql
  	 * @return
  	 */
  	void index(Map<String,Integer> map);
}
