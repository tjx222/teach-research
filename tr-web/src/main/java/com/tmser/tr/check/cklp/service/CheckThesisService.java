/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.cklp.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.uc.bo.UserSpace;

/**
 * 查阅教学文章
 * <pre>
 *
 * </pre>
 *
 * @author wangyao
 * @version $Id: CheckThesisService.java, v 1.0 2016年8月12日 上午11:20:29 wangyao Exp $
 */
public interface CheckThesisService {
	/**
	 * 查阅统计年级学科内所有可查阅教师资源信息统计
	 * @param grade
	 * @param subject
	 * @param type
	 * @return
	 */
	Map<Integer, Map<String,Long>> listTchThesisStatics(Thesis thesis,UserSpace userSpace);

	/**
	 * @param userId
	 * @param thesis
	 * @param year 
	 * @param paramSpace 
	 * @return
	 */
	Long countWriteInfo(Thesis thesis,UserSpace paramSpace);

	/**
	 * @param userId
	 * @param thesis
	 * @param year
	 * @param paramSpace
	 * @return
	 */
	Long countSubmitThesis(Thesis thesis, UserSpace paramSpace);

	/**
	 * 
	 * @param checkUser  查阅者
	 * @param userId   被查阅者
	 * @param thesis
	 * @param year
	 * @return
	 */
	Long countCheckThesis(UserSpace checkUser,Thesis thesis,UserSpace paramSpace);
	
	/**
	 * 
	 * @param checkUser 查阅者
	 * @param authorId 被查阅者
	 * @param thesis
	 * @return
	 */
	Map<Integer,CheckInfo> checkedThesisMap(UserSpace checkUser,Integer authorId,Thesis thesis,UserSpace paramSpace);
	/**
	 * 根据学科年级学期查询用户相关数据
	 * @param authorId
	 * @param thesis
	 * @return
	 */
	List<Thesis> getThesisData(Integer authorId,Thesis thesis);
	/**
	 * 查询用户详细信息
	 * @param dataMap
	 * @return
	 */
	Map<Integer,Map<String,String>> getUserDetail(Map<Integer, Map<String, Long>> dataMap,Integer userId);
	
}
