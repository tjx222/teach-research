/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.annunciate.bo.AnnunciatePunishView;
import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.annunciate.service.AnnunciatePunishViewService;
import com.tmser.tr.annunciate.vo.JyAnnunciateVo;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.uc.utils.CurrentUserContext;

/**
 * 通告发布查看控制器接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: AnnunciatePunishView.java, v 1.0 2015-07-01 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/annunciate")
public class AnnunciatePunishViewController extends AbstractController{
	
	@Autowired
	private AnnunciatePunishViewService annunciatePunishViewService;
	
	
	/**
	 * 查看发布的通知公告消息列表
	 * @param vo
	 * @return
	 */
	@RequestMapping("/noticeIndex")
	public String getPunishs(Model m,JyAnnunciateVo vo,Page page, Integer orgID){
		vo.currentPage(1);
		vo.setStatus(1);
		page.setPageSize(10);
		vo.addPage(page);
		vo.setIsDelete(0);
		vo.addOrder("lastupDttm desc");
		PageList<JyAnnunciateVo> pagelist = annunciatePunishViewService.findPunishsByPage(vo,m);
		m.addAttribute("pagelist", pagelist);
		m.addAttribute("userId", CurrentUserContext.getCurrentUserId());
		return viewName("annunciate_notice_index");
	}
	
	//预览通知公告页面
	@RequestMapping("/preview")
	public String getPreview(Model m,Integer type){
		m.addAttribute("date", new Date());
		m.addAttribute("id", CurrentUserContext.getCurrentUserId());
		m.addAttribute("type", type);
		return viewName("annunciate_preview");
	}
	
	/**
	 * 删除发布的通知公告
	 * @param vo
	 * @return
	 */
	@RequestMapping("/deleteAnnunciate")
	public Result deletePunishAnnunciate(Model m,Integer id){
		Result result = new Result();
		try{
			annunciatePunishViewService.deletePunishAnnunciate(id);
			m.addAttribute("code", 1);
		}catch(Exception e){
			m.addAttribute("code", 0);
			m.addAttribute("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 新增查看的记录
	 * @param id
	 * @return
	 */
	@RequestMapping("/readedPunishAnnunciates")
	public String readPunishAnnunciate(Integer id,Integer status){
		AnnunciatePunishView view = new AnnunciatePunishView();
		view.setAnnunciateId(id);
		view.setUserId(CurrentUserContext.getCurrentUserId());
		int count = annunciatePunishViewService.count(view);
		if(count==0){
			try{
				view.setViewTime(new Date());
				annunciatePunishViewService.save(view);
			}catch(Exception e){
				logger.info("新增查阅记录失败!",e);
			}
		}
		return "redirect:/jy/annunciate/view?id="+id+"&&status="+status+"&&type="+1;
	}
	 
	 /**
	 * 查看已发布未被查看的通知公告
	 * @param id
	 * @return
	 */
	@RequestMapping("/getNotReadAnnunciate")
	public Result getNotReadJyAnnunciate(){
		List<JyAnnunciate> jList=annunciatePunishViewService.getNotReadJyList();
		return new Result(jList);
	}
}