/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.comment.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.comment.bo.CommentInfo;
import com.tmser.tr.comment.service.CommentInfoService;
import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 评论信息控制器接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: CommentInfo.java, v 1.0 2015-03-20 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/comment/")
public class CommentController extends AbstractController{

	@Autowired
	private CommentInfoService commentInfoService;

	/**
	 * 跳到评论页面
	 * info:从页面获得当前页数的参数
	 * m:把查询分页结果设置到内存里面，可以在页面进行展示
	 * @return
	 */
	@RequestMapping("list")
	@UseToken
	public String list(CommentInfo info,@RequestParam(required=false,defaultValue="/comment/comment")String vn, Model m){
		findAllCommentReply(info, m);
		return vn;
	}

	/**
	 * 查询所有的评论和所有的回复带分页
	 * @param info 页面上传过来的参数,其中借用flags 传递评论框状态，flago
	 * @param m
	 * @param request
	 */
	private void findAllCommentReply(CommentInfo info,Model m){
		CommentInfo model = new CommentInfo();//评论实体
		model.pageSize(5);//设置每页的展示数
		model.setType(CommentInfo.PTPL);//限制评论类型
		model.setIsDelete(false);//删除状态
		model.setIsHidden(false);//隐藏状态
		model.setResType(info.getResType());
		model.setAuthorId(info.getAuthorId());
		model.setResId(info.getResId());

		model.addOrder("crtDttm desc");//按照创建时间降序,如果时间相同则按照用户ID排序
		model.currentPage(info.getPage().getCurrentPage());//设置这是第几页

		PageList<CommentInfo> plList=commentInfoService.findByPage(model);//查询当前页的评论

		List<CommentInfo> dataList=plList.getDatalist();//得到当前页的评论

		Map<Integer, List<CommentInfo>> huifuMap = new HashMap<Integer, List<CommentInfo>>();//存放评论的map
		for (CommentInfo commentInfo : dataList) {
			CommentInfo huifuCom=new CommentInfo();
			huifuCom.setOpinionId(commentInfo.getId());//查询当前评论回复
			huifuCom.setIsDelete(false);//删除状态
			huifuCom.setIsHidden(false);//隐藏状态
			huifuCom.addOrder("crtDttm desc");//按照创建时间降序,如果时间相同则按照用户ID排序

			List<CommentInfo> huifuList=commentInfoService.findAll(huifuCom);//当前评论回复的集合
			huifuMap.put(commentInfo.getId(), huifuList);
		}

		m.addAttribute("data", plList);//按照分页进行查询
		m.addAttribute("huifuMap", huifuMap);//回复map的集合
		m.addAttribute("model", info);
		m.addAttribute("containsInput","true".equalsIgnoreCase(info.getFlags())||
				"1".equals(info.getFlags()) ? "1":"0");
		m.addAttribute("canReply", "false".equalsIgnoreCase(info.getFlago()) || "0".equalsIgnoreCase(info.getFlago()) ? 0 : 1);
		m.addAttribute("titleShow",(info.getTitleShow()!=null && info.getTitleShow()==true) ? "1":"0");
	}

	/**
	 * 保存评论
	 * @return
	 */
	@RequestMapping("addComment")
	@ResponseBody
	@UseToken(true)
	public Result addComment(CommentInfo info,Model m){
		Result result = new Result();
		CommentInfo cinfo = new CommentInfo();
		cinfo.setTitle(info.getTitle());
		cinfo.setAuthorId(info.getAuthorId());
		cinfo.setType(CommentInfo.PTPL);
		saveComment_reply(m, info,cinfo);
		result.setMsg("保存成功");
		return result;
	}

	/**
	 * 保存回复
	 * @return
	 */
	@RequestMapping("relyComment")
	@UseToken(true)
	public String relyComment(Model m,CommentInfo info){
		CommentInfo parentinfo = commentInfoService.findOne(info.getOpinionId());
		CommentInfo cinfo = new CommentInfo();
		cinfo.setTitle(parentinfo.getTitle());
		cinfo.setType(CommentInfo.HF);
		cinfo.setAuthorId(info.getAuthorId());
		cinfo.setParentId(info.getParentId());
		cinfo.setOpinionId(info.getOpinionId());
		parentinfo.setContent(info.getContent());
		saveComment_reply(m, parentinfo,cinfo);
		m.addAttribute("childList", cinfo);
		return viewName("reply");
	}
	
	/**
	 * 保存回复
	 * @return
	 */
	@RequestMapping("relay")
	@ResponseBody
	@UseToken(true)
	public Result relay(Model m,CommentInfo info){
		Result result = new Result();
		CommentInfo parentinfo = commentInfoService.findOne(info.getOpinionId());
		CommentInfo cinfo = new CommentInfo();
		cinfo.setTitle(parentinfo.getTitle());
		cinfo.setType(CommentInfo.HF);
		cinfo.setAuthorId(info.getAuthorId());
		cinfo.setParentId(info.getParentId());
		cinfo.setOpinionId(info.getOpinionId());
		parentinfo.setContent(info.getContent());
		saveComment_reply(m, parentinfo,cinfo);
		result.setMsg("保存成功");
		return result;
	}

	/**
	 * 保存评论或者回复
	 * @param m
	 * @param info,页面传过来的参数
	 * @param cinfo，传向页面的参数
	 */
	private void saveComment_reply(Model m,CommentInfo info,CommentInfo cinfo){
		User u = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		cinfo.setContent(info.getContent());//填写评论内容
		cinfo.setUserId(u.getId());//评论者ID
		cinfo.setUsername(u.getNickname());//评论者名字
		cinfo.setIsDelete(false);//是否删除
		cinfo.setIsHidden(false);//是否隐藏
		cinfo.setCrtDttm(new Date());//评论时间
		//cinfo.setAuthorId(info.getAuthorId());//资源作者
		cinfo.setResType(info.getResType());//资源类型
		cinfo.setResId(info.getResId());//资源ID
		commentInfoService.saveComment(cinfo);//保存
	}
}