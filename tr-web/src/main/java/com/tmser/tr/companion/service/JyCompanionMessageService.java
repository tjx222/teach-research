/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.companion.service;

import java.util.Date;
import java.util.List;

import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.companion.bo.JyCompanionMessage;

/**
 * 同伴互助留言 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyCompanionMessage.java, v 1.0 2015-05-20 Generate Tools Exp $
 */

public interface JyCompanionMessageService extends BaseService<JyCompanionMessage, Integer>{

	/**
	 * 新增消息
	 * @param recevier
	 * @param message
	 */
	public void addMessage(JyCompanionMessage message);

	/**
	 * 查询消息
	 * @param message
	 */
	public List<JyCompanionMessage> findMessage(Integer userId,Date startDate,Integer pageSize,Integer pageNum);

	/**
	 * 分页查询消息
	 * @param message
	 * @return
	 */
	public PageList<JyCompanionMessage> findMessageWithPage(JyCompanionMessage message);

	/**
	 * 查询所有的沟通时间
	 * @param message
	 * @return
	 */
	public List<Date> findAllComunicateDates(JyCompanionMessage message);

	/**
	 * 获取指定时间前消息
	 * @param userId
	 * @param date
	 * @param pageSize
	 * @return
	 */
	public List<JyCompanionMessage> getPreMessages(Integer userId, Date date,
			Integer pageSize);

	/**
	 * 获取指定时间后消息
	 * @param userId
	 * @param date
	 * @param pageSize
	 * @return
	 */
	public List<JyCompanionMessage> getNextMessages(Integer userId, Date date,
			Integer pageSize);

	/**
	 * 首次沟通的时间
	 * @param userId
	 * @return
	 */
	public Date getFirstMessageDate(Integer userId);

	/**
	 * 最近一次沟通的时间
	 * @param userId
	 * @return
	 */
	public Date getLatestMessageDate(Integer userId);

	/**
	 * 最后一个给我发送消息的人
	 * @return
	 */
	public Integer getLastSenderUserID();

	/**
	 * 查询消息的分页，按照时间的倒序，数据反转输出
	 * @param companionId
	 * @param page
	 * @return
	 */
	public PageList<JyCompanionMessage> findMessageLastPage(
			Integer companionId, Page page);

}
