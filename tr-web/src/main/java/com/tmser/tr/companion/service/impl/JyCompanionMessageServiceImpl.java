/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.companion.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.companion.bo.JyCompanionMessage;
import com.tmser.tr.companion.dao.JyCompanionDao;
import com.tmser.tr.companion.dao.JyCompanionMessageDao;
import com.tmser.tr.companion.service.JyCompanionMessageService;
import com.tmser.tr.companion.service.JyCompanionService;
import com.tmser.tr.notice.constants.NoticeType;
import com.tmser.tr.notice.service.impl.NoticeUtils;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.uc.utils.CurrentUserContext;
/**
 * 同伴互助留言 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyCompanionMessage.java, v 1.0 2015-05-20 Generate Tools Exp $
 */
@Service
@Transactional
public class JyCompanionMessageServiceImpl extends AbstractService<JyCompanionMessage, Integer> implements JyCompanionMessageService {

	@Autowired
	private JyCompanionMessageDao jyCompanionMessageDao;

	@Autowired
	private JyCompanionDao jyCompanionDao;

	@Autowired
	private JyCompanionService jyCompanionService;

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserSpaceDao userSpaceDao;
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<JyCompanionMessage, Integer> getDAO() {
		return jyCompanionMessageDao;
	}

	/**
	 * 新增消息
	 * @param message
	 * @see com.tmser.tr.companion.service.JyCompanionMessageService#addMessage(com.tmser.tr.companion.bo.JyCompanionMessage)
	 */
	@Override
	public void addMessage(JyCompanionMessage message) {
		message.setUserIdSender(CurrentUserContext.getCurrentUserId());
		message.setUserNameSender(CurrentUserContext.getCurrentUser().getName());

		User user=userDao.get(message.getUserIdReceiver());
		//同一学校用名称，不同学校用昵称
		if(isSameSchool(CurrentUserContext.getCurrentUserId(),message.getUserIdReceiver())){
			message.setUserNameReceiver(user.getName());
		}else{
			message.setUserNameReceiver(user.getNickname());
		}
		message.setSenderTime(new Date());
		jyCompanionMessageDao.insert(message);
		//更新最近一次更新时间
		Integer count = jyCompanionDao.updateLastCommunicateTime(CurrentUserContext.getCurrentUserId(),message.getUserIdReceiver());
		//如果不存在记录，则新增
		if(count==0){
			jyCompanionService.addCompanion(message.getUserIdReceiver(), false, new Date());
		}

		// 设置通知参数
		String title = CurrentUserContext.getCurrentUser().getName()
				+ "给您发送了留言";
		Map<String, Object> noticeInfo = new HashMap<String, Object>();
		noticeInfo.put("userName", CurrentUserContext.getCurrentUser()
				.getName());
		noticeInfo.put("userId", CurrentUserContext.getCurrentUserId());
		noticeInfo.put("content", message.getMessage());
		noticeInfo.put("type", 3);
		// 发送通知
		NoticeUtils.sendNotice(NoticeType.COMPANION, title,
				CurrentUserContext.getCurrentUserId(), message.getUserIdReceiver(),
				noticeInfo);
	}

	/**
	 * 是否同一学校
	 * @param currentUserId
	 * @param recevier
	 * @return
	 */
	private boolean isSameSchool(Integer currentUserId, Integer recevier) {
		List<Integer> userIds = new ArrayList<>();
		userIds.add(currentUserId);
		userIds.add(recevier);
		//查询工作区间
		List<UserSpace> list = userSpaceDao.findByUserIds(userIds);
		//用户均存在且orgId一致
		if(list.size()==2&&list.get(0).getOrgId().equals(list.get(1).getOrgId())){
			return true;
		}
		return false;
	}

	/**
	 * 分页查询消息
	 * @param message
	 * @return
	 * @see com.tmser.tr.companion.service.JyCompanionMessageService#findMessageWithPage(com.tmser.tr.companion.bo.JyCompanionMessage)
	 */
	@Override
	public PageList<JyCompanionMessage> findMessageWithPage(
			JyCompanionMessage message) {
		return jyCompanionMessageDao.findMessageWithPage(message);
	}

	/**
	 * 查询消息
	 * @param message
	 * @see com.tmser.tr.companion.service.JyCompanionMessageService#findMessage(com.tmser.tr.companion.bo.JyCompanionMessage)
	 */
	@Override
	public List<JyCompanionMessage> findMessage(Integer userId,Date startDate,Integer pageSize,Integer pageNum) {
		return jyCompanionMessageDao.findMessage(CurrentUserContext.getCurrentUserId(),userId,startDate,pageSize,pageNum);

	}

	/**
	 * 获取所有和用户沟通的时间
	 * @param message
	 * @return
	 * @see com.tmser.tr.companion.service.JyCompanionMessageService#findAllComunicateDates(com.tmser.tr.companion.bo.JyCompanionMessage)
	 */
	@Override
	public List<Date> findAllComunicateDates(JyCompanionMessage message) {
		List<Date>  result = jyCompanionMessageDao.findAllComunicateDates(message);
		Collections.sort(result);
		return result;
	}

	/**
	 * 获取指定时间前消息
	 * @param userId
	 * @param date
	 * @param pageSize
	 * @return
	 * @see com.tmser.tr.companion.service.JyCompanionMessageService#getPreMessages(java.lang.Integer, java.util.Date, java.lang.Integer)
	 */
	@Override
	public List<JyCompanionMessage> getPreMessages(Integer userId, Date date,
			Integer pageSize) {
		//查询记录
		List<JyCompanionMessage> result = jyCompanionMessageDao.getPreMessages(CurrentUserContext.getCurrentUserId(),userId,date,pageSize);
		if(!CollectionUtils.isEmpty(result)){
			//按顺序排序
			Collections.sort(result, new Comparator<JyCompanionMessage>() {
				@Override
				public int compare(JyCompanionMessage o1, JyCompanionMessage o2) {
					if(o1.getSenderTime().before(o2.getSenderTime())){
						return -1;
					}
					return 0;
				}
			});
		}
		return result;
	}

	/**
	 * 获取指定时间后消息
	 * @param userId
	 * @param date
	 * @param pageSize
	 * @return
	 * @see com.tmser.tr.companion.service.JyCompanionMessageService#getNextMessages(java.lang.Integer, java.util.Date, java.lang.Integer)
	 */
	@Override
	public List<JyCompanionMessage> getNextMessages(Integer userId, Date date,
			Integer pageSize) {

		return jyCompanionMessageDao.getNextMessages(CurrentUserContext.getCurrentUserId(),userId,date,pageSize);
	}

	/**
	 * 第一次沟通时间
	 * @param userId
	 * @return
	 * @see com.tmser.tr.companion.service.JyCompanionMessageService#getFirstMessageDate(java.lang.Integer)
	 */
	@Override
	public Date getFirstMessageDate(Integer userId) {
		return jyCompanionMessageDao.getFirstMessageDate(CurrentUserContext.getCurrentUserId(),userId);
	}

	/**
	 * 最后一次沟通时间
	 * @param userId
	 * @return
	 * @see com.tmser.tr.companion.service.JyCompanionMessageService#getLatestMessageDate(java.lang.Integer)
	 */
	@Override
	public Date getLatestMessageDate(Integer userId) {
		return jyCompanionMessageDao.getLatestMessageDate(CurrentUserContext.getCurrentUserId(),userId);
	}

	public void setJyCompanionDao(JyCompanionDao jyCompanionDao) {
		this.jyCompanionDao = jyCompanionDao;
	}

	public void setJyCompanionService(JyCompanionService jyCompanionService) {
		this.jyCompanionService = jyCompanionService;
	}

	public void setJyCompanionMessageDao(JyCompanionMessageDao jyCompanionMessageDao) {
		this.jyCompanionMessageDao = jyCompanionMessageDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserSpaceDao(UserSpaceDao userSpaceDao) {
		this.userSpaceDao = userSpaceDao;
	}

	/**
	 * 最后一个给我发送消息的人
	 * @return
	 * @see com.tmser.tr.companion.service.JyCompanionMessageService#getLastSenderUserID()
	 */
	@Override
	public Integer getLastSenderUserID() {
		JyCompanionMessage jcm = new JyCompanionMessage();
		jcm.setUserIdReceiver(CurrentUserContext.getCurrentUserId());
		jcm.addCustomCulomn("userIdSender");
		jcm.addOrder("senderTime desc");
		List<JyCompanionMessage> list = jyCompanionMessageDao.list(jcm, 1);
		if(CollectionUtils.isEmpty(list)){
			return 0;
		}else{
			return list.get(0).getUserIdSender();
		}
	}

	/**
	 * 查询消息的分页，按照时间的倒序，数据反转输出
	 * @param companionId
	 * @param page
	 * @return
	 * @see com.tmser.tr.companion.service.JyCompanionMessageService#findMessageLastPage(java.lang.Integer, com.tmser.tr.common.page.Page)
	 */
	@Override
	public PageList<JyCompanionMessage> findMessageLastPage(Integer companionId, Page page) {
		JyCompanionMessage jcm = new JyCompanionMessage();
		jcm.addPage(page);
		String customCondition = " and ((userIdSender=:useridone and userIdReceiver=:useridtwo) or "
				+ "(userIdSender=:useridtwo and userIdReceiver=:useridone))";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("useridone", CurrentUserContext.getCurrentUserId());
		paramMap.put("useridtwo", companionId);
		jcm.addCustomCondition(customCondition, paramMap);
		jcm.addOrder("senderTime desc");
		jcm.addCustomCulomn("userIdSender,userIdReceiver,message,attachment1,attachment1Name,attachment2,attachment2Name,attachment3,attachment3Name,senderTime");
		PageList<JyCompanionMessage> jcmList = jyCompanionMessageDao.listPage(jcm);
		//数据反转
		List<JyCompanionMessage> datalist = jcmList.getDatalist();
		Collections.reverse(datalist);
		jcmList.setDatalist(datalist);
		return jcmList;
	}


}
