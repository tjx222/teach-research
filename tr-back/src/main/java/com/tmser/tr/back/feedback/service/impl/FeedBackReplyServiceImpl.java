package com.tmser.tr.back.feedback.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.feedback.service.FeedbackReplyService;
import com.tmser.tr.back.feedback.service.FeedbackService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.feedback.bo.Recieve;
import com.tmser.tr.feedback.bo.Reply;
import com.tmser.tr.feedback.dao.ReplyDao;
import com.tmser.tr.feedback.vo.FeedBackVo;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.utils.StringUtils;
@Service
@Transactional
public class FeedBackReplyServiceImpl extends AbstractService<Reply, Integer> implements FeedbackReplyService{
	@Autowired
	private ReplyDao replyDao;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private FeedbackService feedBackService;
	@Override
	public BaseDAO<Reply, Integer> getDAO() {
		// TODO Auto-generated method stub
		return replyDao;
	}

	@Override
	public List<FeedBackVo> getReplyDetail(Recieve re) {
		// TODO Auto-generated method stub
		//所有回复记录--不同回复记录中的不同附件和附件名
		Reply reply = new Reply();
		reply.setPid(re.getId());
		reply.addOrder("id desc");
		List<Reply> list = replyDao.listAll(reply);
		List<FeedBackVo> voList = new ArrayList<FeedBackVo>();//volist
		for(Reply forReply:list){
			List<Resources> resourceList = new ArrayList<Resources>();//附件list
			FeedBackVo vo = new FeedBackVo();
			vo.setId(forReply.getId());
			vo.setReplyContent(forReply.getContent());
			vo.setReplyDate(forReply.getSenderTime());
			vo.setUserNameReceiver(forReply.getUserNameReceiver());
			//附件名和附件地址的集合
			 String ids = forReply.getAttachment1();
			 String[] idArray = ids.split(",");
			 if(StringUtils.isNotBlank(ids)){
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
	public void saveFeedbackReply(Reply reply) {
		// TODO Auto-generated method stub
		//保存回复信息
		reply.setReceiver(CurrentUserContext.getCurrentUser().getId());
		reply.setUserNameReceiver(CurrentUserContext.getCurrentUser().getName());
		reply.setSenderTime(new Date());
		this.save(reply);
		//维护反馈表中ishavereply字段
		Recieve rec = new Recieve();
		rec.setId(reply.getPid());
		rec.setIshavareply(1);
		feedBackService.update(rec);
		//更新resource
		 String ids = reply.getAttachment1();
		 if(StringUtils.isNotBlank(ids)){
			 String[] stringArr = ids.split(",");
			 for(String id:stringArr){
				 resourcesService.updateTmptResources(id);
			 }
		 }
	}
}
