/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.feedback.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.feedback.bo.Recieve;
import com.tmser.tr.feedback.service.RecieveService;
import com.tmser.tr.feedback.vo.FeedBackVo;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 * 用户反馈
 *@author lijianghu
 * </pre>
 *
 * @author tmser
 * @version $Id: FeedBackController.java, v 1.0 2015年9月9日 下午1:21:56 tmser Exp $
 */
@Controller
@RequestMapping("/jy/feedback")
public class FeedBackController extends AbstractController{
	@Autowired
	private RecieveService recieveService;
	@Autowired
	private ResourcesService resourcesService;
	@RequestMapping("/index")
	public String index(Model m,Page page,Recieve info){
		return viewName("/feedback");
	}
	/**
	 * 保存反馈信息
	 * @param re
	 * @param m
	 * @return
	 */
	@RequestMapping("/saveFeedBack")
	@ResponseBody
	public Result saveFeedBack(Recieve re){
		Result result = new Result();
		result.setMsg("反馈成功，相关人员会尽快给您答复，请耐心等待!");
		try {
			User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
			if (null != user) {
				re.setOrgId(user.getOrgId());
			}
			recieveService.saveRecieve(re);
			//保存更新资源
			if(StringUtils.isNotEmpty(re.getAttachment1())){
				for(String resId : re.getAttachment1().split(",")){
					resourcesService.updateTmptResources(resId);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("反馈信息报错...", e);
			result.setCode(0);
			result.setMsg("反馈失败，请联系相关技术人员!");
		}
		return result;
	}
	/**
	 * 加载反馈列表
	 * @return
	 */
	@RequestMapping("/feedbackList")
	public String feedbackList(Recieve model,Model m){
		model.pageSize(10);//设置每页的展示数
		model.addOrder("senderTime desc");//倒序
		model.setUserIdSender(CurrentUserContext.getCurrentUserId());
		PageList<Recieve> page = this.recieveService.findByPage(model);
		m.addAttribute("data", page);//分页查询数据
		return "/feedback/feedbackrightList";
		
	}
	/**
	 * 加载反馈/回复详情
	 * @return
	 */
	@RequestMapping("/feedbackDetail")
	public String feedbackDetail(Recieve recieve,Model m){
		//反馈详情页面数据
		Recieve  re = new Recieve();
		re  = recieveService.getRecieveDetail(recieve);
		//回复详情页面
		//根据id，查询reply表中有pid是id的对象，放到vo中，然后到页面遍历，取出。
		List<FeedBackVo> voList = new ArrayList<FeedBackVo>();
		voList =  recieveService.getReplyDetail(re);
		m.addAttribute("recieve",re);
		m.addAttribute("voList", voList);
		return "/feedback/feedbackleftDetail";
	}
	/**
	 * 删除文件
	 * 
	 * @param imgId
	 *            资源id
	 * @param isweb
	 *            是否是web下资源
	 * @return
	 */
	@RequestMapping("/deleteImg")
	public void deleteImg(String imgId, Boolean isweb) {
		if (isweb == true) {
			if (imgId != null) {
				Resources resources = resourcesService.findOne(imgId);
				if (resources != null) {
					resourcesService.deleteWebResources(resources.getPath());
				}
			}
		} else {
			resourcesService.deleteResources(imgId);
		}
	}
}
