/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.check.cklp.controller;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.check.bo.CheckInfo;
import com.tmser.tr.check.cklp.service.CheckThesisService;
import com.tmser.tr.check.service.CheckInfoService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.utils.CookieUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.service.ThesisService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 查阅教学文章
 * <pre>
 *
 * </pre>
 *
 * @author wangyao
 * @version $Id: CheckThesisController.java, v 1.0 2016年8月12日 上午9:51:27 wangyao Exp $
 */
@Controller
@RequestMapping("/jy/check/thesis")
public class CheckThesisController extends AbstractController{
	
	@Autowired
	private CheckThesisService checkThesisService;
	@Autowired
	private ThesisService thesisService;
	@Autowired
	private CheckInfoService checkInfoService;
	
	/**
	 * 查阅入口页
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/index" ,method=RequestMethod.GET)
	public String index(@RequestParam(value="gradeId",required = false)Integer gradeId,
			@RequestParam(value="subjectId", required = false)Integer subjectId,
			@RequestParam(value="sysRoleId", required = false)Integer sysRoleId,
			Model m){
		
		m.addAttribute("grade",gradeId==null?-1:gradeId);
		m.addAttribute("subject",subjectId==null?-1:subjectId);
		m.addAttribute("sysRole",sysRoleId==null?-1:sysRoleId);
		m.addAttribute("term",WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM));
		UserSpace checkUser = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		m.addAttribute("flago","t");//区分教师与管理者
		m.addAttribute("flags","mt");//区分面包屑
		if(checkUser != null && (SysRole.XZ.getId().equals(checkUser.getSysRoleId()) || SysRole.FXZ.getId().equals(checkUser.getSysRoleId()) || SysRole.ZR.getId().equals(checkUser.getSysRoleId()))){
			return viewName("select");
		}
		return viewName("teacher_index");
	}
	@RequestMapping(value="/select" ,method=RequestMethod.GET)
	public String select(@RequestParam(value="flago", required = false)String flago,
			@RequestParam(value="gradeId",required = false)Integer gradeId,
			@RequestParam(value="subjectId", required = false)Integer subjectId,
			@RequestParam(value="sysRoleId", required = false)Integer sysRoleId,
			Model m){
		m.addAttribute("grade",gradeId==null?-1:gradeId);
		m.addAttribute("subject",subjectId==null?-1:subjectId);
		m.addAttribute("sysRole",sysRoleId==null?-1:sysRoleId);
		m.addAttribute("term",WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM));
		UserSpace checkUser = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		m.addAttribute("flago",flago);//区分教师与管理者
		if("m".equals(flago)){
			if(SysRole.ZR.getId().equals(checkUser.getSysRoleId())){
				m.addAttribute("sysRoles",Arrays.asList(SysRole.XKZZ,SysRole.NJZZ,SysRole.BKZZ));
			}else{
				m.addAttribute("sysRoles",Arrays.asList(SysRole.ZR,SysRole.XKZZ,SysRole.NJZZ,SysRole.BKZZ));
			}
			m.addAttribute("checkUser",checkUser);
			return viewName("manager_index");
		}
		return viewName("teacher_index");
	}
	
	
	/**
	 * 教师统计列表
	 * @return
	 */
	@RequestMapping(value="/tchlist")
	public String teacherList(Thesis thesis,Model m,UserSpace paramSpace,@RequestParam(value="spaceName",required = false)String spaceName){
		Integer year = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		thesis.setSchoolYear(year);
		
		m.addAttribute("grade",paramSpace.getGradeId());
		m.addAttribute("subject",paramSpace.getSubjectId());
		m.addAttribute("sysRoleId",paramSpace.getSysRoleId());
		m.addAttribute("thesis",thesis);
		Map<Integer, Map<String, Long>> dataMap = checkThesisService.listTchThesisStatics(thesis,paramSpace);
		m.addAttribute("staticsMap",dataMap);
		m.addAttribute("spaceName",spaceName);
		return viewName("user_list");
	}
	/**
	 * 更新查阅意见状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/setScan")
	public Object setScan(Thesis thesis,Model m){
		thesis.setScanUp(Thesis.NOT_SCAN);
		return thesisService.update(thesis);
	}
	/**
	 * 查阅单个教师单册书课题入口
	 * @param userid 
	 * @param bookid 
	 * @return
	 */
	@RequestMapping(value="/tch/{userid}")
	public String entryCheckTeacher(@PathVariable("userid")Integer userId,Thesis thesis,Model m,UserSpace paramSpace){
		Integer year = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		UserSpace checkUserSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		thesis.setOrgId(checkUserSpace.getOrgId());
		thesis.setPhaseId(checkUserSpace.getPhaseId());
		thesis.setSchoolYear(year);
		thesis.setUserId(userId);
		m.addAttribute("userId", userId);
		m.addAttribute("term",thesis.getSchoolTerm());
		m.addAttribute("checkMaps", checkThesisService.checkedThesisMap(checkUserSpace, userId, thesis,paramSpace));
		m.addAttribute("writeList", checkThesisService.getThesisData(userId, thesis));
		m.addAttribute("writeCount",checkThesisService.countWriteInfo(thesis,paramSpace));
		m.addAttribute("checkCount",checkThesisService.countCheckThesis(checkUserSpace,thesis,paramSpace));
		m.addAttribute("submitCount",checkThesisService.countSubmitThesis(thesis,paramSpace));
		m.addAttribute("thesis",thesis);
		m.addAttribute("paramSpace",paramSpace);
		return viewName("user_detail");
	}
	
	/**
   * 查看教学文章
   * @param 
   * @param m
   * @return
   */
  @RequestMapping("/tch/{userId}/view")
  public String viewThesis(@PathVariable("userId")Integer userId,Thesis thesis,Model m,UserSpace paramSpace,@RequestParam(value="ids",required = false)String ids){
   
    UserSpace checkUserSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    Integer schoolTeam = thesis.getSchoolTerm();
    Integer thesisId = thesis.getId();
    if(checkUserSpace != null && thesisId != null){
      Thesis findOne = thesisService.findOne(thesisId);
      Thesis model = new Thesis();
      if(findOne != null){
        model.setSubjectId(findOne.getSubjectId());
        model.setSchoolTerm(schoolTeam!=null?schoolTeam:(Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM));
        model.setSchoolYear((Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
        model.setThesisType(findOne.getThesisType());
        model.setIsSubmit(Thesis.SUBMIT);
        model.buildCondition(" and id not in (:id)").put("id", findOne.getId());
        model.setOrgId(findOne.getOrgId());
        model.addOrder("submitTime desc");
      }
      CheckInfo check = new CheckInfo();
      check.setSpaceId(checkUserSpace.getId());
      check.setResType(ResTypeConstants.JIAOXUELUNWEN);
      check.setResId(thesisId);
      CheckInfo checked = checkInfoService.findOne(check);
      m.addAttribute("isCheck",checked==null ?false:true);
      m.addAttribute("findOne",findOne);
      m.addAttribute("thesis",thesis);
      m.addAttribute("paramSpace",paramSpace);
      m.addAttribute("thesisData",thesisService.findAll(model));
      m.addAttribute("userId",userId);
      m.addAttribute("thesisId",thesisId);
      m.addAttribute("thesisIds",ids);
      if(!StringUtils.isEmpty(ids)){
        int index= -1;
        for (String s : ids.split(",")) {
          index++;
          if(String.valueOf(thesisId).equals(s)){
            break;
          }
        }
        m.addAttribute("idIndex",index);
      }
    }
    return viewName("view_thesis");
  }
	
	@RequestMapping("/tch/other")
	public String viewOther(Thesis thesis,Model m,UserSpace paramSpace){
		Integer thesisId = thesis.getId();
		Thesis findOne = thesisService.findOne(thesisId);
		if(findOne != null){
			m.addAttribute("userId",findOne.getUserId());
		}
		m.addAttribute("findOne",findOne);
		m.addAttribute("paramSpace",paramSpace);
		m.addAttribute("thesisId",thesisId);
		return viewName("view_other_thesis");
	}
}
