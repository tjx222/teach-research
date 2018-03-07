/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lecturerecords.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.bo.LectureReply;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lecturerecords.service.LectureReplyService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 听课记录回复控制器接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LectureReply.java, v 1.0 2015-04-27 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/lecturereply")
public class LectureReplyController extends AbstractController{
	@Autowired
	private LectureReplyService lectureReplyService;
	
	@Autowired
	private LectureRecordsService lectureRecordsService;
	
	/**
	 * 授课人与听课人之间的回复列表
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("reply")
	public String reply(Model m,LectureReply lectureReply){
		LectureReply model=new LectureReply();
		model.pageSize(5);
		model.setIsDelete(0);//不删除
		model.setIsHidden(0);//不隐藏
		
		if(lectureReply!=null){
			if("1".equals(lectureReply.getFlags())){//判断如果是听课列表进行的查看
				LectureRecords records=lectureRecordsService.findOne(lectureReply.getResId());
				records.setReplyUp(0);//修改该听课列表的回复的更新状态
				lectureRecordsService.update(records);
			}
			
			model.setAuthorId(lectureReply.getAuthorId());//听课记录者ID
			model.setTeacherId(lectureReply.getTeacherId());//授课人ID
			model.setResId(lectureReply.getResId());//听课记录主键
			model.setType(0);//普通回复
			model.setFlags(lectureReply.getFlags());//是否显示回复框
			model.currentPage(lectureReply.getPage().getCurrentPage());//设置当前页数
			model.addOrder("crt_dttm desc");//按照创建时间降序
		}
		PageList<LectureReply> plList=lectureReplyService.findByPage(model);
		
		List<LectureReply> dataList=plList.getDatalist();//得到当前页的回复
		Map<Integer, List<LectureReply>> huifuMap=new HashMap<Integer, List<LectureReply>>();//存放评论的map
		for (LectureReply reply : dataList) {
			LectureReply huifuCom=new LectureReply();
			huifuCom.setParentId(reply.getId());//查询当前评论回复
			huifuCom.setIsDelete(0);//删除状态
			huifuCom.setIsHidden(0);//隐藏状态
			huifuCom.setType(1);//回复的回复
			huifuCom.addOrder("crt_dttm desc,userId asc");//按照创建时间降序,如果时间相同则按照用户ID排序
			
			List<LectureReply> huifuList=lectureReplyService.findAll(huifuCom);//当前评论回复的集合
			huifuMap.put(reply.getId(), huifuList);
		}
		m.addAttribute("model", model);
		m.addAttribute("data", plList);
		m.addAttribute("huifuMap", huifuMap);
		if(lectureReply!=null&&lectureReply.getFlags()!=null){
			m.addAttribute("containsInput","true".equalsIgnoreCase(lectureReply.getFlags())||"1".equals(lectureReply.getFlags()) ? "1":"0");
		}else{
			m.addAttribute("containsInput","false");
		}
		
		return "/lecturerecords/lecpeoples/reply";
	}
	
	/**
	 * 增加一个课题回复
	 * @param m
	 * @param lectureReply
	 * @return
	 */
	@RequestMapping("addreply")
	@ResponseBody
	public Result addreply(Model m,LectureReply lectureReply){
		Result result=new Result();
		if(lectureReply!=null){
			lectureReply.setType(0);//设置恢复类型为回复
			saveReplyCommon(lectureReply);
			result.setMsg("保存成功！");
		}
		return result;
	}
	
	/**
	 *保存回复的回复 
	 * @param m
	 * @param lectureReply
	 * @return
	 */
	@RequestMapping("replyreply")
	public String replyreply(Model m,LectureReply lectureReply){
		if(lectureReply!=null){
			lectureReply.setType(1);//设置回复类型为回复的回复
			saveReplyCommon(lectureReply);
			m.addAttribute("childList", lectureReply);
		}
		return "/lecturerecords/lecpeoples/reply_reply";
	}
	
	/**
	 * 保存回复和回复的回复的公共方法
	 */
	private void saveReplyCommon(LectureReply lectureReply){
		User user=(User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		lectureReply.setUserId(user.getId());//回复人ID
		lectureReply.setUsername(user.getName());//回复人姓名
		lectureReply.setIsDelete(0);//不删除
		lectureReply.setIsHidden(0);//不隐藏
		lectureReply.setCrtDttm(new Date());
		lectureReplyService.saveReply(lectureReply);//增加成功之后通知回调
	}
}