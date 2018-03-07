/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.thesis.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.MobileUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.rethink.controller.RethinkController;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.service.ThesisService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.JyCollectionUtils;
import com.tmser.tr.utils.StringUtils;

/**
 * 上传教学论文控制器接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Thesis.java, v 1.0 2015-03-12 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/thesis")
public class ThesisController extends AbstractController{
	
	private static final Logger logger = LoggerFactory.getLogger(RethinkController.class);

	@Resource
	private ThesisService thesisService;
	
	@Resource
	private UserService userService;
	@Resource
	private UserSpaceService userSpaceService;
	
	@Resource
	private ResourcesService resourcesService;
	@Autowired
	private CheckInfoService checkInfoService;
	//教学论文首页
	@RequestMapping("/index")
	@UseToken
	public String thesisList(Model m,Thesis thesis,Page page){
	
		UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
	    if (us!=null) {
	    	String title = thesis.getThesisTitle();
	    	if (!StringUtils.isEmpty(title)) {
	    		thesis.setThesisTitle(SqlMapping.LIKE_PRFIX+title+SqlMapping.LIKE_PRFIX);
			}
	    	if(MobileUtils.isNormal()){
	    		page.setPageSize(8);
	    	}else{
	    		page.setPageSize(1000);
	    		
	    	}
	    	thesis.addPage(page);
			PageList<Thesis> tList=thesisService.findCourseList(thesis);
			m.addAttribute("tList", tList);
			thesis.setThesisTitle(title);
			m.addAttribute("thesis", thesis);
			Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
			
			List<UserSpace> spaceList = userSpaceService.listUserSpaceBySchoolYear(us.getUserId(), schoolYear);
			m.addAttribute("sysRoleIds",StringUtils.join(JyCollectionUtils.getValues(spaceList, "sysRoleId"),","));
			m.addAttribute("sysRoleId",us.getSysRoleId());
		}
	    return viewName("thesisindex");
		
	}
	
	//保存教学论文
	@RequestMapping("/save")
	@UseToken
	public void saveThesis(Thesis thesis,Model m,String originFileName){
		try {
			thesisService.saveThesis(thesis, m, originFileName);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("保存失败");
		}
	}
	/**
	 * 通过文件Id得到文件对象
	 */
	@RequestMapping("/getFileById")
	public void getFileById(String resId,Model m){
		Resources res = resourcesService.findOne(resId);
		m.addAttribute("res",res);
	}
	//删除教学论文
	@RequestMapping("/delete")
	public void deleteThesis(Integer id,String resId,Model m){
		if (id!=null) {
			thesisService.delete(id);
			if (resId!=null) {
				resourcesService.deleteResources(resId);
			}
			boolean isOk=true;
			m.addAttribute("isOk", isOk);
		}
	}
	//分享教学论文
	@RequestMapping("/share")
	public void shareThesis(Thesis thesis,Integer isShare,Model m){
		if (thesis.getId()!=null) {
			thesis.setIsShare(isShare);
			m.addAttribute("isShare", isShare);
			if (isShare==1) {
				thesis.setShareTime(new Date());	
			}
			thesisService.update(thesis);
			boolean isOk=true;
			m.addAttribute("isOk", isOk);
		}
	}
	
//查看教学论文 
  @RequestMapping("/thesisview")
  public String thesisView(Model m,Integer id){
      Thesis thesis=thesisService.findOne(id);
      m.addAttribute("thesis", thesis);
     
    return viewName("thesisview");
  }
	
   //增加已评论已查看状态
	@RequestMapping("/reviewState")
	public void reviewState(Integer id,Integer isComment){
		Thesis thesis=new Thesis();
		thesis.setId(id);
		thesis.setIsComment(2);
		thesisService.update(thesis);
	}
	@RequestMapping("/indexto")
    public String index(){
		return viewName("index");
	}
	@RequestMapping("/preSubmit")
	public String preSubmit(Thesis thesis,Model m){
		Integer isSubmit = thesis.getIsSubmit();
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); // 用户
		Integer year = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		thesis.setSchoolYear(year);
		if(Thesis.NOT_SUBMIT.equals(thesis.getIsSubmit())){
			m.addAttribute("notSubmitData", thesisService.getSubmitData(thesis,user));
		}else{
			m.addAttribute("submitData", thesisService.getSubmitData(thesis,user));
			m.addAttribute("submitMap", checkInfoService.getCheckData(year,ResTypeConstants.JIAOXUELUNWEN,user.getId()));
		}
		m.addAttribute("thesis", thesis);
		m.addAttribute("isSubmit", isSubmit);
		return viewName("thesis_submit");
	}
	@RequestMapping("/submitThesis")
	@ResponseBody
	public Object submitThesis(Thesis thesis,Model m,@RequestParam(value="resIds") Integer[] resIds){
		return thesisService.updateSubmitState(resIds, thesis.getIsSubmit());
	}
}