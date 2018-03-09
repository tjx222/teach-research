/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.feedback.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.feedback.bo.Recieve;
import com.tmser.tr.feedback.bo.Reply;
import com.tmser.tr.feedback.dao.RecieveDao;
import com.tmser.tr.feedback.dao.ReplyDao;
import com.tmser.tr.feedback.service.RecieveService;
import com.tmser.tr.feedback.vo.FeedBackVo;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.utils.StringUtils;
/**
 * 用户反馈信息 服务实现类
 * <pre>
 *@author tmser 
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Recieve.java, v 1.0 2015-09-15 Generate Tools Exp $
 */
@Service
@Transactional
public class RecieveServiceImpl extends AbstractService<Recieve, Integer> implements RecieveService {

	@Autowired
	private RecieveDao recieveDao;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private ReplyDao replydao;
	@Override
	public BaseDAO<Recieve, Integer> getDAO() {
		return recieveDao;
	}

	@Override
	public PageList<Recieve> getFeedbackListByPage(UserSpace us,
			Recieve model) {
		// TODO Auto-generated method stub
		PageList<Recieve> plList=this.findByPage(model);//查询当前页的评论
		return plList;
	}

	@Override
	public List<FeedBackVo> getReplyDetail(Recieve re) {
				//所有回复记录--所有回复记录中的不同附件和附件名
				Reply reply = new Reply();
				reply.setPid(re.getId());
				List<Reply> list = replydao.listAll(reply);
				List<FeedBackVo> voList = new ArrayList<FeedBackVo>();//volist
				for(Reply forReply:list){
					List<Resources> resourceList = new ArrayList<Resources>();//附件list
					FeedBackVo vo = new FeedBackVo();
					vo.setId(forReply.getId());
					vo.setReplyContent(forReply.getContent());
					vo.setSenderTime(forReply.getSenderTime());
					//遍历附件id，得到附件名和附件地址的list
					 String ids = forReply.getAttachment1();
					 String[] idArray = ids.split(",");
					 if(StringUtils.isNotEmpty(ids)){
						 for(String id:idArray){
							 Resources res = resourcesService.findOne(id);
							 resourceList.add(res);
						 }
					 }
					 vo.setList(resourceList);
					 voList.add(vo);
				}
				return voList;
	}
	
	@Override
	public Recieve getRecieveDetail(Recieve recieve) {
		// TODO Auto-generated method stub
		List<Resources> list = new ArrayList<Resources>();
		Recieve  newrecieve = recieveDao.getOne(recieve);
		String recieveAttachment1 = newrecieve.getAttachment1();
		String[] recieveAttachment1Array =  recieveAttachment1.split(",");
		if(StringUtils.isNotBlank(recieveAttachment1)){
			for(String forAttachment:recieveAttachment1Array){
				list.add(resourcesService.findOne(forAttachment));
			}
		}
		newrecieve.setList(list);
		return newrecieve;
	}

	@Override
	public void saveRecieve(Recieve re) {
		// TODO Auto-generated method stub
		re.setUserIdSender(CurrentUserContext.getCurrentUserId());
		re.setUserNameSender(CurrentUserContext.getCurrentUser().getName());
		re.setSenderTime(new Date());
		re.setIshavareply(0);
		this.save(re);
		//更新resource
		 String ids = re.getAttachment1();
		 String[] stringArr = ids.split(",");
		 for(String id:stringArr){
			 resourcesService.updateTmptResources(id);
		 }
	}
}
