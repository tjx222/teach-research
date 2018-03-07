/**
o * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.CookieUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.plainsummary.vo.PlainSummaryVo;
import com.tmser.tr.rethink.controller.RethinkController;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.JyAssert;

/**
 * 计划总结控制器接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: PlainSummary.java, v 1.0 2015-03-19 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/planSummary/")
public class PlainSummaryController extends AbstractController{
	
	private static final Logger logger = LoggerFactory.getLogger(RethinkController.class);
	
	@Autowired
	private PlainSummaryService plainSummaryService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private UserSpaceService userSpaceService;
	@Autowired
	private UserService userService;
	/**
	 * 获取用户的所有计划总结消息
	 * @param m
	 * @return
	 */
	@UseToken
	@SuppressWarnings("unchecked")
	@RequestMapping(value="index")	
	public String plainSummaryList(@RequestParam(value="scrollLeft",required=false) String scrollLeft, Model m){
		m.addAttribute("scrollLeft", scrollLeft);
		//获取用户消息
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); 
		List<UserSpace> usl = (List<UserSpace>)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.USER_SPACE_LIST); 
		//按职务排序,顺序
		Collections.sort(usl, new Comparator<UserSpace>() {

			@Override
			public int compare(UserSpace o1, UserSpace o2) {
				if(o1.getSysRoleId()<o2.getSysRoleId()){
					return -1;
				}
				return 0;
			}
		});
		
		PlainSummary model = new PlainSummary();
		model.setUserId(user.getId());
		//学年
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		UserSpace us = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer phaseId = us.getPhaseId();
		model.setSchoolYear(schoolYear);
		model.setPhaseId(phaseId);
		model.addOrder("lastupDttm desc");
		//获取用户的所有计划总结
		List<PlainSummary> list = plainSummaryService.findAll(model ); 
		m.addAttribute("plianSummaryMap", parseToMap(list));
		m.addAttribute("usl", usl);
		//界面划分数目，最多3个
		m.addAttribute("usNum", usl.size()>3?3:usl.size());
		//总共个数
		m.addAttribute("totalUsNum",usl.size());
		//学期
		Integer schoolTerm = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		m.addAttribute("schoolYear",schoolYear);
		m.addAttribute("schoolTerm",schoolTerm);
		return "/plainSummary/index";
	}
	
	/**
	 * 以用户空间id为key来分计划总结
	 * @param list
	 * @return
	 */
	private Map<Integer,List<PlainSummary>> parseToMap(List<PlainSummary> psList) {
		Map<Integer,List<PlainSummary>> map = new HashMap<Integer,List<PlainSummary>> ();
		if(CollectionUtils.isEmpty(psList)){
			return map;
		}
		Map<String,Integer> spaceMap = new HashMap<String, Integer>();
		@SuppressWarnings("unchecked")
		List<UserSpace> usl = (List<UserSpace>)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.USER_SPACE_LIST);
		for (UserSpace userSpace : usl) {
			Integer roleId = userSpace.getRoleId();
			Integer gradeId = userSpace.getGradeId();
			Integer subjectId = userSpace.getSubjectId();
			String key = "r_"+roleId+"_g_"+gradeId+"_s_"+subjectId;
			spaceMap.put(key, userSpace.getId());
		}
		//遍历list转换为key
		for(PlainSummary ps:psList){
			String key = "r_"+ps.getUserRoleId()+"_g_"+ps.getGradeId()+"_s_"+ps.getSubjectId();
			List<PlainSummary> list = map.get(spaceMap.get(key));
			if(list==null){
				list = new ArrayList<PlainSummary>();
				if(key != null){
					map.put(spaceMap.get(key), list);
				}
			}
			list.add(ps);
		}
		return map;
	}

	/**
	 * 保存计划总结
	 * @param m
	 */
	@UseToken
	@RequestMapping(value="save")	
	public ModelAndView save(PlainSummary ps,@RequestParam(value="scrollLeft",required=false) String scrollLeft){
		ModelAndView mv = new ModelAndView("redirect:/jy/planSummary/index?scrollLeft="+scrollLeft);
		//获取用户消息
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		UserSpace us = userSpaceService.findOne(ps.getUserSpaceId());
		ps.setUserId(user.getId());	
		ps.setIsSubmit(0);
		ps.setIsShare(0);
		ps.setIsPunish(0);
		ps.setIsCheck(0);
		ps.setIsReview(0);
		ps.setReviewNum(0);
		ps.setGradeId(us.getGradeId());
		ps.setSubjectId(us.getSubjectId());
		ps.setUserRoleId(us.getRoleId());
		ps.setRoleId(us.getSysRoleId());
		
		ps.setCrtDttm(new Date());
		ps.setCrtId(user.getId());
		ps.setLastupDttm(new Date());
		ps.setLastupId(user.getId());
		ps.setOrgId(us.getOrgId());
		if(ps.getTerm()== null){
			ps.setTerm((Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM));
		}
		ps.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
		ps.setPhaseId(CurrentUserContext.getCurrentSpace().getPhaseId());
		try{
		//保存计划总结
		plainSummaryService.save(ps);
		//回调确认文件已保存
		resourcesService.updateTmptResources(ps.getContentFileKey());
		}catch(Exception e){
			logger.error("保存计划总结失败！", e);
		}
		return mv;
	}
	
	/**
	 * 删除计划总结
	 * @param psId
	 * @param m
	 */
	@RequestMapping("{id}/delete")
	public Result delete(@PathVariable("id") Integer id){
		Result result = new Result();
		
		try{
			JyAssert.notNull(id, "id不能为空！");
			plainSummaryService.delete(id);
			result.setCode(1);
		}catch(Exception e){
			logger.error("删除总结计划失败！", e);
			result.setCode(0);
		}
		return result; 
	}
	
	/**
	 * 编辑计划总结
	 * @param ps
	 * @param m
	 */
	@UseToken
	@RequestMapping("edit")
	public ModelAndView edit(PlainSummary ps,Model m,@RequestParam(value="scrollLeft",required=false) String scrollLeft){
		ModelAndView mv = new ModelAndView("redirect:/jy/planSummary/index?scrollLeft="+scrollLeft);
		try{
			plainSummaryService.edit(ps);
		}catch(Exception e){
			logger.error("编辑计划失败！", e);
		}
		return mv;
	}
	
	/**
	 * 分享
	 * @param psId
	 * @param m
	 */
	@RequestMapping("{id}/share")
	public Result share(@PathVariable("id") Integer psId,Model m){
		Result r = new Result();
		try{
			plainSummaryService.share(psId);
			r.setCode(1);
		}catch(Exception e){
			logger.error("分享失败！", e);
			r.setCode(0);
		}
		return r;
	}
	
	/**
	 * 取消分享
	 * @param psId
	 * @param m
	 */
	@RequestMapping("{id}/cancelShare")
	public Result cancelShare(@PathVariable("id") Integer psId){
		Result r = new Result();
		try{
			plainSummaryService.cancelShare(psId);
			r.setCode(1);
		}catch(Exception e){
			logger.error("取消分享失败！", e);
			r.setCode(0);
		}
		return r;
	}
	
	/**
	 * 发布
	 * @param psId
	 * @param m
	 */
	@RequestMapping(value="{id}/punish")
	public Result punish(@PathVariable("id") Integer psId,@RequestParam("punishRange") Integer punishRange,@RequestParam(value="schoolCircleId",required=false) Integer schoolCircleId){
		Result result = new Result();
		try{
			plainSummaryService.punish(psId,punishRange,schoolCircleId);
			result.setCode(1);
		}catch(Exception e){
			logger.error("发布计划失败！", e);
			result.setCode(0);
		}
		return result;
	}

	/**
	 * 取消发布
	 * @param psId
	 * @param m
	 */
	@RequestMapping("{id}/cancelPunsih")
	public Result cancelPunsih(@PathVariable("id") Integer psId){
		Result result = new Result();
		try{
			plainSummaryService.cancelPunsih(psId);
			result.setCode(1);
		}catch(Exception e){
			logger.error("取消发布计划失败！", e);
			result.setCode(0);
		}
		return result;
	}
	
	/**
	 * 提交
	 * @param psId
	 * @param m
	 */
	@RequestMapping("{id}/submit")
	public Result submit(@PathVariable("id") Integer psId){
		Result result = new Result();
		try{
			plainSummaryService.submit(psId);
			result.setCode(1);
		}catch(Exception e){
			logger.error("提交计划失败！", e);
			result.setCode(0);
		}
		return result;
	}
	
	/**
	 * 取消提交
	 * @param psId
	 * @param m
	 */
	@RequestMapping("{id}/cancelSubmit")
	public Result cancelSubmit(@PathVariable("id") Integer psId){
		Result result = new Result();
		try{

			plainSummaryService.cancelSubmit(psId);
			result.setCode(1);
		}catch(Exception e){
			logger.error("取消提交计划失败！", e);
			result.setCode(0);
		}
		return result;
	}
	
	/**
	 * 更新查阅状态
	 * @param checkState
	 * @return
	 */
	@RequestMapping(value="{id}/checkState",method=RequestMethod.PUT)
	public Result updateCheckState(@RequestParam("checkState") Integer checkState,@PathVariable("id") Integer id){
		PlainSummary model = new PlainSummary();
		model.setId(id);
		model.setIsCheck(checkState);
		plainSummaryService.update(model);
		return new Result();
	}
	
	/**
	 * 更新评论状态
	 * @param checkState
	 * @return
	 */
	@RequestMapping(value="{id}/reviewState",method=RequestMethod.PUT)
	public Result updateReviewState(@RequestParam("reviewState") Integer reviewState,@PathVariable("id") Integer id){
		PlainSummary model = new PlainSummary();
		model.setId(id);
		model.setIsReview(reviewState);
		plainSummaryService.update(model);
		return new Result();
	}
	
	/**
   * 查看文件
   * @param psId
   * @param m
   */
  @RequestMapping("{id}/viewFile")
  public String planSummaryView(@PathVariable("id") Integer psId,Model m){
   
    PlainSummary ps = plainSummaryService.findOne(psId);
    m.addAttribute("model", ps);
    return "/plainSummary/planSummaryView";
  }
	/**
	 * 查看文件
	 * @param psId
	 * @param m
	 */
	@RequestMapping("scanFiles/{id}")
	public String scanFile(@PathVariable("id") Integer psId){
		PlainSummary ps = plainSummaryService.findOne(psId);
		return "forward:/jy/scanResFile?resId="+ps.getContentFileKey();
	}
	
	
	/**
	 * 获取所有发布的计划总结
	 * @return
	 */
	@RequestMapping(value="planSummarys/punishs",method=RequestMethod.GET)
	public ModelAndView getPunishPlanSummary(PlainSummary search ){
		ModelAndView mv = new ModelAndView("/plainSummary/planSummary_punish");
		if(search==null){
			search = new PlainSummary();
		}
		search.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
		search.pageSize(15);
		search.setIsPunish(1);
		search.addOrder("punishTime desc");
		PageList<PlainSummary> page = plainSummaryService.findByPage(search);
		PageList<PlainSummaryVo> pageVo = parseModelToVo(page);
		mv.addObject("data", pageVo);
		return mv;
	}
	
	/**
	 * 上一篇发布的计划总结
	 * @param psId
	 * @param m
	 */
	@RequestMapping("punishs/{id}/pre")
	public ModelAndView getPrePunishPlanSummary(@PathVariable("id") Integer psId){
		ModelAndView mv = new ModelAndView("/plainSummary/planSummaryView");
		PlainSummary firstPs = getFirstPlainSummary();
		PlainSummary ps = null;
		//如果已经是第一篇
		if(firstPs!=null&&firstPs.getId().equals(psId)){
			ps=plainSummaryService.findOne(psId);
			mv.addObject("ps", ps);
			mv.addObject("hasNoPre", true);
			mv.addObject("isFirst", true);
			return mv;
		}
		ps =plainSummaryService.getPrePunishPlanSummary(psId);
		if(ps==null){
			ps=plainSummaryService.findOne(psId);
			mv.addObject("hasNoPre", true);
			mv.addObject("isFirst", true);
		}else{
			if(ps.getId().equals(firstPs.getId())){
				mv.addObject("isFirst", true);
			}else{
				mv.addObject("isFirst", false);
			}
			mv.addObject("hasNoPre", false);
		}
		mv.addObject("ps", ps);
		return mv;
	}
	
	/**
   * 查看提交计划总结资源除了当前人的其他人列表
   * @param lessonInfo
   * @param m
   */
  @RequestMapping(value="viewOthers")
  public String viewOthers(Integer id,Model m){
    PlainSummary ps=plainSummaryService.findOne(id);
    m.addAttribute("data", ps);
    return "/plainSummary/view_others";
  }
	
  @RequestMapping("planSummarys/{id}")
  public ModelAndView viewPlainSummaryCheck(@PathVariable("id") Integer id, @RequestParam("_view") String viewName) {
    ModelAndView mv = new ModelAndView("plainSummary/" + viewName);
    PlainSummary ps = plainSummaryService.findOne(id);
    mv.addObject("ps", ps);
    return mv;
  }

  /**
   * 获取第一篇计划总结
   * 
   * @return
   */
  private PlainSummary getFirstPlainSummary() {
    PlainSummary search = new PlainSummary();
    search.setIsPunish(1);
    search.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
    search.addOrder("punishTime desc");
    List<PlainSummary> list = plainSummaryService.find(search, 1);
    if (!CollectionUtils.isEmpty(list)) {
      return list.get(0);
    }
    return null;
  }

  /**
   * 下一篇发布的计划总计
   * 
   * @param psId
   * @param m
   */
  @RequestMapping("punishs/{id}/next")
  public ModelAndView getNextPunishPlanSummary(@PathVariable("id") Integer psId) {
    ModelAndView mv = new ModelAndView("/plainSummary/planSummaryView");
    PlainSummary lastPs = getLastPlainSummary();
    PlainSummary ps = null;
    // 如果已经是最后篇
    if (lastPs != null && lastPs.getId().equals(psId)) {
      ps = plainSummaryService.findOne(psId);
      mv.addObject("ps", ps);
      mv.addObject("hasNoNext", true);
      mv.addObject("isLast", true);
      return mv;
    }
    ps = plainSummaryService.getNextPunishPlanSummary(psId);
    if (ps == null) {
      ps = plainSummaryService.findOne(psId);
      mv.addObject("hasNoNext", true);
      mv.addObject("isLast", true);
    } else {
      if (ps.getId().equals(lastPs.getId())) {
        mv.addObject("isLast", true);
      } else {
        mv.addObject("isLast", false);
      }
      mv.addObject("hasNoNext", false);
    }
    mv.addObject("ps", ps);
    return mv;
  }

  /**
   * 获取最后一篇文章id
   * 
   * @return
   */
  private PlainSummary getLastPlainSummary() {
    PlainSummary search = new PlainSummary();
    search.setIsPunish(1);
    search.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
    search.addOrder("punishTime");
    List<PlainSummary> list = plainSummaryService.find(search, 1);
    if (!CollectionUtils.isEmpty(list)) {
      return list.get(0);
    }
    return null;
  }

  /**
   * 填充用户详情
   * 
   * @param parseModelToVo
   */
  private PageList<PlainSummaryVo> parseModelToVo(PageList<PlainSummary> page) {
    List<PlainSummaryVo> datalist = new ArrayList<PlainSummaryVo>();
    PageList<PlainSummaryVo> pageVo = new PageList<PlainSummaryVo>(datalist, page.getPage());
    if (!CollectionUtils.isEmpty(page.getDatalist())) {
      for (PlainSummary ps : page.getDatalist()) {
        PlainSummaryVo vo = new PlainSummaryVo();
        BeanUtils.copyProperties(ps, vo);
        User user = userService.findOne(ps.getUserId());
        vo.setUserName(user.getName());
        datalist.add(vo);
      }
    }
    return pageVo;
  }

  /**
   * 获取提交计划总结资源除了当前人的其他人列表
   * 
   * @param lessonInfo
   * @param m
   */
  @RequestMapping(value = "resourceSubmitOthers")
  public String resourceSubmitOthers(PlainSummary ps, Model m) {
    List<PlainSummary> dataList = plainSummaryService.resourceSubmitOthers(ps);
    m.addAttribute("datalist", dataList);
    m.addAttribute("type", ps.getCategory());
    return "/plainSummary/resource_submit_others";
  }


  public void setPlainSummaryService(PlainSummaryService plainSummaryService) {
    this.plainSummaryService = plainSummaryService;
  }

  public void setResourcesService(ResourcesService resourcesService) {
    this.resourcesService = resourcesService;
  }

  public void setUserSpaceService(UserSpaceService userSpaceService) {
    this.userSpaceService = userSpaceService;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }
}