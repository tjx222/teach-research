/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.companion.dao;

import java.util.Date;
import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.companion.bo.JyCompanion;
import com.tmser.tr.companion.bo.JyCompanionMessage;

/**
 * 同伴互助留言 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyCompanionMessage.java, v 1.0 2015-05-20 Generate Tools Exp $
 */
public interface JyCompanionMessageDao extends BaseDAO<JyCompanionMessage, Integer>{

	/**
	 * 查询消息
	 * @param userId
	 * @param userIdCompanion
	 * @param startDate
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	List<JyCompanionMessage> findMessage(Integer userId,Integer userIdCompanion,Date startDate,Integer pageSize,Integer pageNum);

	/**
	 * 分页查询消息
	 * @param message
	 * @return
	 */
	PageList<JyCompanionMessage> findMessageWithPage(JyCompanionMessage message);

	/**
	 * 获取所有和用户沟通的时间
	 * @param message
	 * @return
	 */
	List<Date> findAllComunicateDates(JyCompanionMessage message);

	/**
	 * 获取指定时间前消息
	 * @param userId
	 * @param date
	 * @param pageSize
	 * @return
	 */
	List<JyCompanionMessage> getPreMessages(Integer currentUserId,Integer companionUserId, Date date,
			Integer pageSize);

	/**
	 * 获取指定时间后消息
	 * @param userId
	 * @param date
	 * @param pageSize
	 * @return
	 */
	List<JyCompanionMessage> getNextMessages(Integer currentUserId,Integer companionUserId, Date date,
			Integer pageSize);

	/**
	 * 第一次沟通时间
	 * @param currentUserId
	 * @param userId
	 * @return
	 */
	Date getFirstMessageDate(Integer currentUserId, Integer userId);

	/**
	 * 最近一次沟通时间
	 * @param currentUserId
	 * @param userId
	 * @return
	 */
	Date getLatestMessageDate(Integer currentUserId, Integer userId);

	/**
	 * 和我有消息沟通的人，按时间最近排序
	 * @param id
	 * @param username
	 */
	List<JyCompanion> findMsgUser(Integer id, String username);

}