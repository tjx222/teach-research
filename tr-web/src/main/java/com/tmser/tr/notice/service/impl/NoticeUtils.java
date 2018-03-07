package com.tmser.tr.notice.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.notice.bo.JyNotice;
import com.tmser.tr.notice.constants.JyNoticeConstants;
import com.tmser.tr.notice.constants.NoticeType;
import com.tmser.tr.notice.service.JyNoticeService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.utils.Exceptions;
import com.tmser.tr.utils.SpringContextHolder;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * <pre>
 *	通知工具类
 * </pre>
 *
 * @author wanzheng,tmser
 * @version $Id: NoticeUtils.java, v 1.0 May 15, 2015 10:02:05 AM wanzheng Exp $
 */
public class NoticeUtils{
	
	private static final Logger logger = LoggerFactory.getLogger(NoticeUtils.class);
	
	private static final Map<String,Template> TEMPLATE_MAP = new HashMap<String,Template>();
	
	private static final Configuration getFreeMarkerConfig(){
		try {
			return SpringContextHolder.getBean("noticeFreemarkerConfig");
		} catch (Exception e) {
			logger.error("load freemarker config by name [noticeFreemarkerConfig] failed,attempt load by class type", e);
			return SpringContextHolder.getBeanDefaultNull(Configuration.class);
		}
	}
	
	/**
	 * 发送通知
	 * @param businessType
	 * @param info
	 */
	public static void sendNotice(NoticeType noticeType,String title,Integer senderId,Integer receiverId,Map<String,Object> info){
		JyNotice notice = initNotice(noticeType,senderId,receiverId);
		String noticeDetail = getNoticeDetail(noticeType.getTemplateName(),info);
		notice.setTitle(title);
		notice.setDetail(noticeDetail);
		addNotice(notice);
	}
	
	/**
	 * 初始化通知对象
	 * @param businessType
	 * @param senderId
	 * @param receiverId
	 * @return
	 */
	private static JyNotice initNotice(NoticeType noticeType, Integer senderId,
			Integer receiverId) {
		UserService dao = SpringContextHolder.getBean(UserService.class);
		JyNotice notice = new JyNotice();
		notice.setBusinessType(noticeType.getValue());
		notice.setSenderId(senderId);
		notice.setSenderState(JyNoticeConstants.SEND_STATE_UNDELETE);
		notice.setReceiverId(receiverId);
		notice.setReceiverState(JyNoticeConstants.RECIEVER_STATE_UNREAD);
		notice.setDetailType(1);
		notice.setSenderStateChangeDate(new Date());
		notice.setReceiverStateChangeDate(new Date());
		notice.setSenderUserName(dao.findOne(senderId).getName());
		notice.setReceiverUserName(dao.findOne(receiverId).getName());
		return notice;
	}

	/**
	 * 新增通知
	 * @param notice
	 */
	private static void addNotice(JyNotice notice) {
		JyNoticeService service=SpringContextHolder.getBean(JyNoticeService.class);
		service.addNotice(notice);
	}

	/**
	 * 组装通知详情
	 * @param info
	 * @return
	 */
	private static String getNoticeDetail(String templateName,Map<String,Object> info) {
		try {
			StringWriter result = new StringWriter();
			Template tpl = TEMPLATE_MAP.get(templateName);
			if(tpl ==  null){
				tpl = loadTpl(templateName);
			}
			//附加信息不为空，添加相对路径
			if(info!=null){
				String ctx = WebThreadLocalUtils.getRequest().getContextPath();
				info.put("ctx", ctx);
			}
			tpl.process(info, result);
			return result.toString();
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
	}
	
	private static Template loadTpl(String templateName) throws IOException{
		logger.debug("load notice template [{}]", templateName);
		Template tpl = getFreeMarkerConfig().getTemplate(templateName+".ftl");
		logger.info("success load notice template [{}]", templateName);
		TEMPLATE_MAP.put(templateName, tpl);
		return tpl;
	}

}
