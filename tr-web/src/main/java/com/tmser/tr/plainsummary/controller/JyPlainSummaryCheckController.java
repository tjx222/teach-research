/**
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.plainsummary.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.plainsummary.service.JyPlainSummaryCheckService;
import com.tmser.tr.plainsummary.vo.PlainSummaryCheckStatisticsVo;
import com.tmser.tr.plainsummary.vo.PlainSummarySimpleCountVo;
import com.tmser.tr.plainsummary.vo.PlainSummaryVo;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.JyAssert;

/**
 * plainSummary控制器接口
 * <pre>
 * 计划总结评阅
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyPlainSummaryCheck.java, v 1.0 2015-04-09 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/planSummaryCheck")
public class JyPlainSummaryCheckController extends AbstractController{

	@Autowired
	private JyPlainSummaryCheckService jyPlainSummaryCheckService;


	@Autowired
	private UserSpaceService userSpaceService;

	@Autowired
	private UserService userService;

	/**
	 * 进入评阅页面
	 * @param userSpaceId
	 * @return
	 */
	@RequestMapping("/index")
	public String indexPage(){
		UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		//如果是校长
		if(us.getSysRoleId().equals(SysRole.XZ.getId())){
			return "/plainSummary/psCheck_schoolMaster";
		}
		return "forward:/jy/planSummaryCheck/teacher";
	}

	/**
	 * 教师展示页面
	 * @param gradeId 被评阅的年级id
	 * @param subjectId 被评阅的学科id
	 * @param teacherName 被评阅的教师名称
	 * @return
	 */
	@RequestMapping("/teacher")
	public String teacherIndex(
			@RequestParam(value="grade",required =  false)Integer grade,
			@RequestParam(value="subject", required = false)Integer subject,
			Model model){
		model.addAttribute("gradeId", grade);
		model.addAttribute("subjectId", subject);
		return "/plainSummary/teacher";
	}

	/**
	 * 单用户空间，计划总结文档页面
	 * @param userSpaceId
	 * @param model
	 * @return
	 */
	@RequestMapping("/userSpace/{userSpaceId}/planSummary")
	public String planSummaryListSingle(@PathVariable("userSpaceId") Integer userSpaceId,Model model,Integer userId){
		UserSpace userSpace = userSpaceService.findOne(userSpaceId);
		JyAssert.notNull(userSpace, "工作空间不存在！");
		User user = userService.findOne(userId);
		model.addAttribute("user", user);
		model.addAttribute("us", userSpace);
		JyAssert.notNull(user, "用户不存在！");
		return "/plainSummary/planSummary_singleWorkSpace";
	}

	/**
	 * 单用户空间，移动端计划总结文档页面
	 * @param userSpaceId
	 * @param model
	 * @return
	 */
	@RequestMapping("/userSpace/{userSpaceId}/planSummarySingle")
	public String planSummarySingleList(@PathVariable("userSpaceId") Integer userSpaceId,
			@RequestParam(value="category",required=false) Integer category,
			@RequestParam(value="term",required=false) Integer term,Model model){
		UserSpace userSpace = userSpaceService.findOne(userSpaceId);
		JyAssert.notNull(userSpace, "工作空间不能存在！");
		User user = userService.findOne(userSpace.getUserId());
		model.addAttribute("user", user);
		model.addAttribute("us", userSpace);
//		JyAssert.notNull(user, "用户不存在！");
//		JyAssert.notNull(userSpaceId, "工作空间不能为空！");
//		List<Integer> usIds = new ArrayList<Integer>();
//		usIds.add(userSpaceId);
		//查询计划总结文档
		List<PlainSummaryVo> psVos = jyPlainSummaryCheckService.findPlainSummaryVoByTerm(null, term,userSpace.getGradeId(),userSpace.getSubjectId(),user.getId(),userSpace.getSysRoleId());
//		List<Integer> spaceIds = new ArrayList<Integer>();
//		spaceIds.add(userSpaceId);
		//统计计划撰写、提交、查阅状况
		PlainSummaryCheckStatisticsVo vo = jyPlainSummaryCheckService.getPlainSummaryStatistics(userSpace.getGradeId(),userSpace.getSubjectId(),user.getId(),SysRole.TEACHER.getId(),userSpace.getPhaseId());
		model.addAttribute("planItems", this.getPlainVo(psVos));
		model.addAttribute("summaryItems", this.getSummaryVo(psVos));
		model.addAttribute("total", vo);
		model.addAttribute("term", term);
		model.addAttribute("category", category);
		return "/plainSummary/planSummary_singleWorkSpace";
	}
	
	/**
	 * 多用户空间，计划总结文档页面
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/role/{roleId}/planSummary")
	public String planSummaryListMulti(@PathVariable("roleId") Integer roleId,Model model,
									   @RequestParam(value="grade",required=false)Integer grade,
									   @RequestParam(value="subject",required=false)Integer subject){
		model.addAttribute("roleId",roleId);
		model.addAttribute("grade", grade);
		model.addAttribute("subject", subject);
		return "/plainSummary/planSummary_multiWorkSpace";
	}
	
	/**
	 * 多用户空间，移动端计划总结文档页面
	 * @param roleId
	 * @return
	 */
	@RequestMapping("/role/{roleId}/planSummaryMulti")
	public String planSummaryMultiList(@PathVariable("roleId") Integer roleId,
			@RequestParam("gradeId") Integer gradeId,
			@RequestParam("subjectId") Integer subjectId,
			@RequestParam(value="category",required=false) Integer category,
			@RequestParam(value="term",required=false) Integer term,Model model){
		model.addAttribute("roleId",roleId);
		model.addAttribute("term", term);
		model.addAttribute("category", category);
		model.addAttribute("gradeId", gradeId);
		model.addAttribute("subjectId", subjectId);
		return "/plainSummary/planSummary_multiWorkSpace";
	}
	
	/**
   * 单用户空间，计划总结详情页面
   * @param planSummaryId 计划总结id
   * @param type 类型
   * @param
   * @return
   */
  @RequestMapping("/userSpace/{userSpaceId}/planSummary/{planSummaryId}")
  public String planSummaryView(@PathVariable("planSummaryId") Integer planSummaryId,@PathVariable("userSpaceId") Integer userSpaceId
      ,Model model,String ids){
    //获取工作空间
    UserSpace us = userSpaceService.findOne(userSpaceId);
    //获取计划总结信息
    PlainSummaryVo plainSummary=jyPlainSummaryCheckService.getPlanSummaryVo(planSummaryId);
    //根据用户空间id统计计划总计
    PlainSummarySimpleCountVo countVo = jyPlainSummaryCheckService.countByUserSpaceId(userSpaceId);
    //获取用户信息
    User user = userService.findOne(plainSummary.getUserId());
    model.addAttribute("userSpaceId", userSpaceId);
    model.addAttribute("us", us);
    model.addAttribute("user", user);
    model.addAttribute("ps", plainSummary);
    model.addAttribute("countVo", countVo);
    model.addAttribute("ids", ids);
    return "plainSummary/view_singleWorkSpace";
  }

	/**
   * 多用户空间，计划总结文档详情页面
   * @param planSummaryId 计划总结id
   * @param type 类型
   * @param
   * @return
   */
  @RequestMapping("/role/{roleId}/planSummary/{planSummaryId}")
  public String planSummaryViewMulti(@PathVariable("planSummaryId") Integer planSummaryId,@PathVariable("roleId") Integer roleId
      ,String ids,Integer userId,Integer gradeId,Integer subjectId,Integer checkStatus,Model model){
    @SuppressWarnings("unchecked")
    HashMap<String,Object> data = (HashMap<String, Object>) this.getPlainSummaryData(roleId,gradeId,subjectId,checkStatus,userId).getData();    
    //获取计划总结信息
    PlainSummaryVo plainSummary=jyPlainSummaryCheckService.getPlanSummaryVo(planSummaryId);
    model.addAttribute("roleId",roleId);
    model.addAttribute("ps", plainSummary);
    model.addAttribute("roleId", roleId);
    model.addAttribute("total", data.get("total"));
    model.addAttribute("ids", ids);
    return "plainSummary/view_multiWorkSpace";
  }

	/**
	 * 查询教师统计信息
	 * @param gradeId 被评阅的年级id
	 * @param subjectId 被评阅的学科id
	 * @param teacherName 被评阅的教师名称
	 * @return
	 */
	@RequestMapping("/teacher/list")
	public Result getTeacherData(@RequestParam("gradeId") Integer gradeId,@RequestParam("subjectId") Integer subjectId){
		UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		gradeId=us.getGradeId()!=0?us.getGradeId():gradeId;
		subjectId=us.getSubjectId()!=0?us.getSubjectId():subjectId;
		//查阅用户空间评阅的计划统计数据
		List<PlainSummaryCheckStatisticsVo> checkStatisticses = jyPlainSummaryCheckService.getUserSpaceStatistics(gradeId,subjectId);
		//获取总共统计信息
		PlainSummaryCheckStatisticsVo totalStatistics = getTotalStatistics(checkStatisticses);

		HashMap<String,Object> data = new HashMap<>();
		data.put("checkStatisticses", checkStatisticses);
		data.put("total", totalStatistics);
		return new Result(data);
	}

	/**
	 * 查询教师统计信息
	 * @param gradeId 被评阅的年级id
	 * @param subjectId 被评阅的学科id
	 * @param teacherName 被评阅的教师名称
	 * @return
	 */
	@RequestMapping("/teacher/userlist")
	public String getTeacherList(@RequestParam("gradeId") Integer gradeId,@RequestParam("subjectId") Integer subjectId,Model model){		
		@SuppressWarnings("unchecked")
		HashMap<String,Object> data = (HashMap<String, Object>) this.getTeacherData(gradeId,subjectId).getData();
		model.addAttribute("checkStatisticses", data.get("checkStatisticses"));
		model.addAttribute("total", data.get("total"));
		model.addAttribute("gradeId", gradeId);
		model.addAttribute("subjectId", subjectId);
		return "plainSummary/cyjh/user_list";
	}
	
	/**
	 * 查询计划总结信息统计，从角色维度
	 * @param roleId 角色id 备课组长、学科组长、年级组长
	 * @param gradeId 被评阅的年级id
	 * @param subjectId 被评阅的学科id
	 * @param checkStatus 文档评阅状态
	 * @return
	 */
	@RequestMapping("/role/{roleId}/plainSummary/list")
	public Result getPlainSummaryData(@PathVariable("roleId") Integer roleId
			,@RequestParam("gradeId") Integer gradeId,@RequestParam("subjectId") Integer subjectId
			,@RequestParam(value="checkStatus",required=false) Integer checkStatus,Integer userId){
		if(roleId==null){
			return new Result("角色id不能为空！");
		}
		//备课组长
		if(SysRole.BKZZ.getId().equals(roleId)){
			if(gradeId==null||gradeId==0||subjectId==null||subjectId==0){
				return new Result("年级、学科均不能为空！");
			}
		}else if(SysRole.XKZZ.getId().equals(roleId)){//学科组长
			if(subjectId==null||subjectId==0){
				return new Result("学科不能为空！");
			}
		}else if(SysRole.NJZZ.getId().equals(roleId)){//年级组长
			if(gradeId==null||gradeId==0){
				return new Result("年级不能为空！");
			}
		}else{
			return new Result("查询角色错误！");
		}
		UserSpace us = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer phaseId = us.getPhaseId();
		//查询计划总结文档
		List<PlainSummaryVo> psVos = jyPlainSummaryCheckService.findPlainSummaryVo(checkStatus,gradeId==null?0:gradeId,subjectId==null?0:subjectId,userId,roleId);
		List<PlainSummaryVo> plainVos = getPlainVo(psVos);
		List<PlainSummaryVo> summaryVos = getSummaryVo(psVos);
		//统计计划撰写、提交、查阅状况
		PlainSummaryCheckStatisticsVo vo = jyPlainSummaryCheckService.getPlainSummaryStatistics(gradeId==null?0:gradeId,subjectId==null?0:subjectId,userId,roleId,phaseId);
		//组装返回参数
		HashMap<String, Object> map = new HashMap<>();
		map.put("planItems", plainVos);
		map.put("total", vo);
		map.put("summaryItems", summaryVos);
		return new Result(map);
	}

	/**
	 * 移动端查询计划总结信息统计，从角色维度
	 * @param roleId 角色id 备课组长、学科组长、年级组长
	 * @param gradeId 被评阅的年级id
	 * @param subjectId 被评阅的学科id
	 * @param checkStatus 文档评阅状态
	 * @return
	 */
	@RequestMapping("/role/{roleId}/plainSummary/listMulti")
	public String getPlainSummaryDataMulti(@PathVariable("roleId") Integer roleId
			,@RequestParam("gradeId") Integer gradeId,@RequestParam("subjectId") Integer subjectId
			,@RequestParam(value="category",required=false) Integer category
			,@RequestParam(value="term",required=false) Integer term,Model model,Integer userId){
//		UserSpace us = new UserSpace();
//		us.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
//		us.setSysRoleId(roleId==null?0:roleId);
//		us.setGradeId(gradeId==null?0:gradeId);
//		us.setSubjectId(subjectId==null?0:subjectId);
//		us.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
//		List<UserSpace> list = userSpaceService.findAll(us);
//		List<Integer> userSpaceIds = JyCollectionUtils.getValues(list, "id");
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer phaseId = userSpace.getPhaseId();
		//查询计划总结文档
		List<PlainSummaryVo> psVos = jyPlainSummaryCheckService.findPlainSummaryVoByTerm(category,term,gradeId,subjectId,userId,roleId);
		//统计计划撰写、提交、查阅状况
		PlainSummaryCheckStatisticsVo vo = jyPlainSummaryCheckService.getPlainSummaryStatistics(gradeId,subjectId,userId,roleId,phaseId);
		//组装返回参数		
		model.addAttribute("planItems", getPlainVo(psVos));
		model.addAttribute("summaryItems", getSummaryVo(psVos));
		model.addAttribute("total", vo);
		model.addAttribute("roleId", roleId);
		model.addAttribute("term", term);
		model.addAttribute("category", category);
		model.addAttribute("gradeId", gradeId);
		model.addAttribute("subjectId",subjectId);
		return "plainSummary/cyjh/user_multiList";
	}
	
	/**
	 * 查询计划总结信息统计，从用户空间角度
	 * @param userSpaceId 工作空间id
	 * @param checkStatus 文档评阅状态
	 * @return
	 */
	@RequestMapping("/userSpace/{userSpaceId}/plainSummary/list")
	public Result getPlainSummaryData(@PathVariable("userSpaceId") Integer userSpaceId
			,@RequestParam(value="checkStatus",required=false) Integer checkStatus){
		//		try{
		JyAssert.notNull(userSpaceId, "工作空间不能为空！");
		UserSpace userSpace = userSpaceService.findOne(userSpaceId);
		User user = userService.findOne(userSpace.getUserId());
		JyAssert.notNull(userSpaceId, "工作空间不能存在！");
		//查询计划总结文档
		List<PlainSummaryVo> psVos = jyPlainSummaryCheckService.findPlainSummaryVo(checkStatus,userSpace.getGradeId(),userSpace.getSubjectId(),user.getId(),userSpace.getSysRoleId());
		List<PlainSummaryVo> plainVos = getPlainVo(psVos);
		List<PlainSummaryVo> summaryVos = getSummaryVo(psVos);
		List<Integer> spaceIds = new ArrayList<Integer>();
		spaceIds.add(userSpaceId);
		UserSpace us = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer phaseId = us.getPhaseId();
		//统计计划撰写、提交、查阅状况
		PlainSummaryCheckStatisticsVo vo = jyPlainSummaryCheckService.getPlainSummaryStatistics(userSpace.getGradeId(),userSpace.getSubjectId(),user.getId(),SysRole.TEACHER.getId(),phaseId);
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("us", userSpace);
		map.put("planItems", plainVos);
		map.put("summaryItems", summaryVos);
		map.put("total", vo);
		return new Result(map);
	}

	/**
	 * 获取计划总结详情
	 * @param planSummaryId
	 * @return
	 */
	@RequestMapping("/planSummary/{planSummaryId}")
	public Result getPlanSummary(@PathVariable("planSummaryId") Integer planSummaryId){
		PlainSummaryVo vo = jyPlainSummaryCheckService.getPlanSummaryVo(planSummaryId);
		if(vo==null){
			return new Result("未查找到记录");
		}else{
			//获取用户信息
			User user = userService.findOne(vo.getUserId());
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("ps", vo);
			vo.setEditName(user.getName());
			return new Result(map);
		}
	}

	/**
	 * 获取提交时间在指定时间之前切距离指定日期最近的文档
	 * @param date
	 * @return
	 */
	@RequestMapping("/workSpace/{workSpaceId}/{category}/planSummarys/{psId}/pre")
	public Result getPrePlanSummary(@PathVariable("workSpaceId") Integer workSpaceId,@PathVariable("psId") Integer psId
			,@PathVariable("category") Integer category,Integer checkplId){
		PlainSummaryVo vo = jyPlainSummaryCheckService.getPrePlanSummary(checkplId);
		if(vo==null){
			return new Result("未查找到记录");
		}else{
			//获取用户信息
			User user = userService.findOne(vo.getUserId());
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("ps", vo);
			vo.setEditName(user.getName());
			return new Result(map);
		}
	}

	/**
	 * 获取提交时间在指定时间之后切距离指定日期最近的文档
	 * @param date
	 * @return
	 */
	@RequestMapping("/workSpace/{workSpaceId}/{category}/planSummarys/{psId}/next")
	public Result getNextPlanSummaryteacher(@PathVariable("workSpaceId") Integer workSpaceId,@PathVariable("psId") Integer psId
			,@PathVariable("category") Integer category,Integer checkplId){
		PlainSummaryVo vo = jyPlainSummaryCheckService.getPrePlanSummary(checkplId);
		if(vo==null){
			return new Result("未查找到记录");
		}else{
			//获取用户信息
			User user = userService.findOne(vo.getUserId());
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("ps", vo);
			vo.setEditName(user.getName());
			return new Result(map);
		}
	}

	/**
	 * 获取提交时间在指定时间之前切距离指定日期最近的文档
	 * @param date
	 * @return
	 */
	@RequestMapping("/role/{roleId}/{category}/planSummarys/{psId}/pre")
	public Result getPrePlanSummaryRole(@PathVariable("roleId") Integer roleId,@PathVariable("psId") Integer psId
			,@PathVariable("category") Integer category,Integer checkplId){
		PlainSummaryVo vo = jyPlainSummaryCheckService.getPrePlanSummary(checkplId);
		if(vo==null){
			return new Result("未查找到记录");
		}else{
			//获取用户信息
			User user = userService.findOne(vo.getUserId());
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("ps", vo);
			vo.setEditName(user.getName());
			return new Result(map);
		}
	}

	/**
	 * 获取提交时间在指定时间之后切距离指定日期最近的文档
	 * @param date
	 * @return
	 */
	@RequestMapping("/role/{roleId}/{category}/planSummarys/{psId}/next")
	public Result getNextPlanSummary(@PathVariable("roleId") Integer roleId,@PathVariable("psId") Integer psId
			,@PathVariable("category") Integer category,Integer checkplId){
		PlainSummaryVo vo = jyPlainSummaryCheckService.getPrePlanSummary(checkplId);
		if(vo==null){
			return new Result("未查找到记录");
		}else{
			//获取用户信息
			User user = userService.findOne(vo.getUserId());
			HashMap<String,Object> map = new HashMap<String, Object>();
			map.put("ps", vo);
			vo.setEditName(user.getName());
			return new Result(map);
		}
	}

	/**
	 * 获取总结文档
	 * @param psVos
	 * @return
	 */
	private List<PlainSummaryVo> getSummaryVo(List<PlainSummaryVo> psVos) {
		List<PlainSummaryVo> result = new ArrayList<PlainSummaryVo>();
		if(CollectionUtils.isEmpty(psVos)){
			return result;
		}
		for(PlainSummaryVo vo:psVos){
			if(vo.getCategory()==2||vo.getCategory()==4){
				result.add(vo);
			}
		}
		return result;
	}

	/***
	 * 初始化一个计划总结统计对象
	 * @return
	 */
	private PlainSummaryCheckStatisticsVo getInitStatisticsVo(){
		PlainSummaryCheckStatisticsVo vo = new PlainSummaryCheckStatisticsVo();
		vo.setPlainNum(0);
		vo.setPlainSubmitNum(0);
		vo.setPlainCheckedNum(0);
		vo.setSummaryNum(0);
		vo.setSummarySubmitNum(0);
		vo.setSummaryCheckedNum(0);
		return vo;
	}

	/**
	 * 获取计划总计统计信息的总和
	 * @param checkStatisticses
	 * @return
	 */
	private PlainSummaryCheckStatisticsVo getTotalStatistics(
			List<PlainSummaryCheckStatisticsVo> checkStatisticses) {
		PlainSummaryCheckStatisticsVo vo = getInitStatisticsVo();
		if(CollectionUtils.isEmpty(checkStatisticses)){
			return vo;
		}
		Iterator<PlainSummaryCheckStatisticsVo> it = checkStatisticses.iterator();
		while(it.hasNext()){
			PlainSummaryCheckStatisticsVo item = it.next();
			if(item.getPlainSubmitNum()==0&&item.getSummarySubmitNum()==0){
				it.remove();
				continue;
			}
			vo.setPlainNum(vo.getPlainNum()+item.getPlainNum());
			vo.setPlainSubmitNum(vo.getPlainSubmitNum()+item.getPlainSubmitNum());
			vo.setPlainCheckedNum(vo.getPlainCheckedNum()+item.getPlainCheckedNum());
			vo.setSummaryNum(vo.getSummaryNum()+item.getSummaryNum());
			vo.setSummarySubmitNum(vo.getSummarySubmitNum()+item.getSummarySubmitNum());
			vo.setSummaryCheckedNum(vo.getSummaryCheckedNum()+item.getSummaryCheckedNum());
		}
		return vo;
	}

	/**
	 * 获取计划文档
	 * @param psVos
	 * @return
	 */
	private List<PlainSummaryVo> getPlainVo(List<PlainSummaryVo> psVos) {
		List<PlainSummaryVo> result = new ArrayList<PlainSummaryVo>();
		if(CollectionUtils.isEmpty(psVos)){
			return result;
		}
		for(PlainSummaryVo vo:psVos){
			if(vo.getCategory()==1||vo.getCategory()==3){
				result.add(vo);
			}
		}
		return result;
	}

	public void setJyPlainSummaryCheckService(
			JyPlainSummaryCheckService jyPlainSummaryCheckService) {
		this.jyPlainSummaryCheckService = jyPlainSummaryCheckService;
	}

	public void setUserSpaceService(UserSpaceService userSpaceService) {
		this.userSpaceService = userSpaceService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}