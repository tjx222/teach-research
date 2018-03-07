/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.operation.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.back.operation.service.OperationManageService;
import com.tmser.tr.back.operation.vo.LeaderOperationInfoVo;
import com.tmser.tr.back.operation.vo.OrgOperationInfoVo;
import com.tmser.tr.back.operation.vo.TeacherOperationInfoVo;
import com.tmser.tr.back.operationmanage.vo.SearchVo;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.bo.Role;
import com.tmser.tr.uc.service.RoleService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 * 运营管理controller
 * </pre>
 * 
 * @author daweiwbs
 * @version $Id: OperationManageController.java, v 1.0 2015年10月29日 下午4:29:52
 *          daweiwbs Exp $
 */
@Controller
@RequestMapping("/jy/back/operation")
public class OperationManageController extends AbstractController {

	@Autowired
	private OperationManageService operationManageService;
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
	private RoleService roleService;

	@RequestMapping("/index")
	public String toIndex(Model m){
		Organization org = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
		if (org == null) {
			return "/back/operation/index";
		} else {
			SearchVo search = new SearchVo();
			search.setAreaId(org.getAreaId());
			return toOrgOperationInfoList(search,m);
		}
	}

	/**
	 * 到学校运营情况列表页
	 * 
	 * @param search
	 * @param m
	 * @return
	 */
	@RequestMapping("/toOrgOperationInfoList")
	public String toOrgOperationInfoList(SearchVo search, Model m) {
		search.pageSize(20);
		Area area = areaService.findOne(search.getAreaId());
		// 全部学段
		List<MetaRelationship> phaseList = MetaUtils.getPhaseMetaProvider().listAll();
		if (phaseList.size() > 0) {
			if (search.getPhaseId() == null) {
				MetaRelationship mr = phaseList.get(0);
				search.setPhaseId(mr.getId());
			}
			getSubjectAndGradeByPhase(search.getPhaseId(),m);
		}
		// 统计数据
		PageList<OrgOperationInfoVo> infoPageList = operationManageService.getOrgOperationInfoList(search);
		if (infoPageList.getDatalist().size() > 0) {
			// 各项统计数据合计
			OrgOperationInfoVo totalVo = operationManageService.getTotalCountOfArea(search);
			m.addAttribute("totalVo", totalVo);
		}
		m.addAttribute("phaseList", phaseList);
		m.addAttribute("infoPageList", infoPageList);
		m.addAttribute("search", search);
		m.addAttribute("area", area);
		return "/back/operation/orgInfoList";
	}

	/**
	 * 到学校内运营情况列表页
	 * 
	 * @param search
	 * @param m
	 * @return
	 */
	@RequestMapping("/toUserOperationInfo")
	public String toUserOperationInfo(SearchVo search, Model m) {
		Organization org = orgService.findOne(search.getOrgId());
		m.addAttribute("org", org);
		m.addAttribute("search", search);
		return "/back/operation/userInfo";
	}

	/**
	 * 到教师运营情况列表页
	 * 
	 * @param search
	 * @param m
	 * @return
	 */
	@RequestMapping("/toTeacherOperationInfoList")
	public String toTeacherOperationInfoList(SearchVo search, Model m) {
		search.pageSize(20);
		Organization org = orgService.findOne(search.getOrgId());
		List<MetaRelationship> phaseList = orgService.listPhasebyOrgId(org.getId());
		if (phaseList.size() > 0) {
			if (search.getPhaseId() == null) {
				MetaRelationship mr = phaseList.get(0);
				search.setPhaseId(mr.getId());
			}
			getSubjectAndGradeByPhase(search.getPhaseId(),m);
		}
		PageList<TeacherOperationInfoVo> infoPageList = operationManageService.getTeacherOperationInfoList(search);
		// 学校下老师的各项统计数据合计
		if (infoPageList.getDatalist().size() > 0) {
			TeacherOperationInfoVo totalVo = operationManageService.getTotalCountOfTeacherOfOrg(search);
			m.addAttribute("totalVo", totalVo);
		}
		m.addAttribute("phaseList", phaseList);
		m.addAttribute("infoPageList", infoPageList);
		m.addAttribute("search", search);
		m.addAttribute("org", org);
		return "/back/operation/teacherInfoList";
	}

	/**
	 * 到管理者运营情况列表页
	 * 
	 * @param search
	 * @param m
	 * @return
	 */
	@RequestMapping("/toLeaderOperationInfoList")
	public String toLeaderOperationInfoList(SearchVo search, Model m) {
		search.pageSize(20);
		Organization org = orgService.findOne(search.getOrgId());
		// 学段
		List<MetaRelationship> phaseList = orgService.listPhasebyOrgId(org.getId());
		// 职务
		List<Role> roleList = roleService.findRoleListByUseOrgId(search.getOrgId(), 2);
		if (phaseList.size() > 0) {
			if (search.getPhaseId() == null) {
				MetaRelationship mr = phaseList.get(0);
				search.setPhaseId(mr.getId());
			}
			getSubjectAndGradeByPhase(search.getPhaseId(),m);
		}
		PageList<LeaderOperationInfoVo> infoPageList = operationManageService.toLeaderOperationInfoList(search);
		// 学校下管理者的各项统计数据合计
		if (infoPageList.getDatalist().size() > 0) {
			LeaderOperationInfoVo totalVo = operationManageService.getTotalCountOfLeaderOfOrg(search);
			m.addAttribute("totalVo", totalVo);
		}
		m.addAttribute("phaseList", phaseList);
		m.addAttribute("roleList", roleList);
		m.addAttribute("infoPageList", infoPageList);
		m.addAttribute("search", search);
		m.addAttribute("org", org);
		return "/back/operation/leaderInfoList";
	}

	/**
	 * 根据学段获取学科和年级
	 * 
	 * @param phaseId
	 * @return
	 */
	@RequestMapping("/getSubjectAndGradeByPhase")
	public void getSubjectAndGradeByPhase(Integer phaseId, Model m) {
		// 某学段下的学科
		List<Meta> subjectList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectByPhaseId(phaseId);
		if ( subjectList != null && subjectList.size() > 0) {
			m.addAttribute("subjectList", subjectList);
		}
		// 某学段下的年级
		List<Meta> gradeList = MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(phaseId);
		if (gradeList != null && gradeList.size() > 0) {
			m.addAttribute("gradeList", gradeList);
		}
	}

	/**
	 * 导出区域下全部学校的运营情况数据
	 * 
	 * @param search
	 */
	@RequestMapping("/exportOrgsOperation")
	public void exportOrgsOperation(SearchVo search, HttpServletResponse response) {
		Area area = areaService.findOne(search.getAreaId());
		search.getPage().setPageSize(1000);
		List<OrgOperationInfoVo> orginfoList = operationManageService.getOrgOperationInfoList(search).getDatalist();
		// 各项统计数据合计
		OrgOperationInfoVo totalVo = operationManageService.getTotalCountOfArea(search);
		orginfoList.add(totalVo);
		// 导出数据
		String[] headers = { "学校名称", "用户数", "撰写教案（总数）", "教学检查（查阅数）", "分享发表（篇数）", "集体备课（发起数）", "集体备课（参与次数）", "成长档案（资源数）", "同伴互助（留言数）", "资源总数", "人均资源总数" };
		String[] propertyNames = { "orgName", "userCount", "lessonPlanCount", "viewCount", "shareCount", "activityPushCount", "activityJoinCount", "progressResCount", "peerMessageCount",
				"resTotalCount", "perResCount" };
		operationManageService.exportToExcel(headers, propertyNames, orginfoList, area.getName() + "各校教研情况总览", response);
	}

	/**
	 * 导出学校下老师的运营情况数据
	 * 
	 * @param search
	 * @param response
	 */
	@RequestMapping("/exportTeacherOperation")
	public void exportTeacherOperation(SearchVo search, HttpServletResponse response) {
		Organization org = orgService.findOne(search.getOrgId());
		search.getPage().setPageSize(1000);
		List<TeacherOperationInfoVo> teacherInfoList = operationManageService.getTeacherOperationInfoList(search).getDatalist();
		// 各项统计数据合计
		TeacherOperationInfoVo totalVo = operationManageService.getTotalCountOfTeacherOfOrg(search);
		teacherInfoList.add(totalVo);
		// 导出数据
		String[] headers = { "姓名", "状态", "撰写教案（总数）", "上传课件（总数）", "教学反思（总数）", "听课记录（节数）", "教学文章（篇数）", "计划总结（篇数）", "集体备课（参与次数）", "集体备课（讨论数）", "集体备课（任主备人次数	）", "校际教研（参与次数）", "校际教研（讨论数）",
				"校际教研（任主备人次数	）", "区域教研（参与次数）", "区域教研（讨论数）", "同伴互助（留言数）", "成长档案袋（资源数）", "分享发表（篇数）", "资源总数" };
		String[] propertyNames = { "userName", "status", "lessonPlanCount", "kejianCount", "fansiCount", "listenCount", "teachTextCount", "planSummaryCount", "activityJoinCount",
				"activityDiscussCount", "activityMainCount", "schoolActivityJoinCount", "schoolActivityDiscussCount", "schoolActivityMainCount", "regionActivityJoinCount",
				"regionActivityDiscussCount", "peerMessageCount", "progressResCount", "shareCount", "resTotalCount" };
		operationManageService.exportToExcel(headers, propertyNames, teacherInfoList, org.getName() + "教师教研情况一览", response);
	}

	/**
	 * 导出学校下管理者的运营情况数据
	 * 
	 * @param search
	 * @param response
	 */
	@RequestMapping("/exportLeaderOperation")
	public void exportLeaderOperation(SearchVo search, HttpServletResponse response) {
		Organization org = orgService.findOne(search.getOrgId());
		search.getPage().setPageSize(1000);
		List<LeaderOperationInfoVo> leaderInfoList = operationManageService.toLeaderOperationInfoList(search).getDatalist();
		// 各项数据统计
		LeaderOperationInfoVo totalVo = operationManageService.getTotalCountOfLeaderOfOrg(search);
		leaderInfoList.add(totalVo);
		// 导出数据
		String[] headers = { "姓名", "状态", "查阅教案（总数）", "查阅课件（总数）", "查阅反思（总数）", "查阅计划总结（篇数）", "听课记录（节数）", "教学文章（篇数）", "撰写计划总结（篇数）", "集体备课（发起次数）", "集体备课（参与次数）", "集体备课（讨论数）", "集体备课（查阅次数）", "校际教研（发起次数）",
				"校际教研（参与次数）", "校际教研（讨论数）", "区域教研（参与次数）", "区域教研（讨论数）", "同伴互助（留言数）", "分享发布（篇数）", "资源总数" };
		String[] propertyNames = { "userName", "status", "viewLessonPlanCount", "viewKejianCount", "viewFansiCount", "viewPlanSummaryCount", "listenCount", "teachTextCount", "planSummaryCount",
				"activityPushCount", "activityJoinCount", "activityDiscussCount", "activityViewCount", "schoolActivityPushCount", "schoolActivityJoinCount", "schoolActivityDiscussCount",
				"regionActivityJoinCount", "regionActivityDiscussCount", "peerMessageCount", "shareCount", "resTotalCount" };
		operationManageService.exportToExcel(headers, propertyNames, leaderInfoList, org.getName() + "教学管理情况一览", response);
	}

	/**
	 * 导出学校下的老师和管理者的运营情况到excel
	 * 
	 * @param search
	 * @param response
	 */
	@RequestMapping("/exportUserOperation")
	public void exportUserOperation(SearchVo search, HttpServletResponse response) {
		Organization org = orgService.findOne(search.getOrgId());
		search.getPage().setPageSize(1000);
		List<TeacherOperationInfoVo> teacherInfoList = operationManageService.getTeacherOperationInfoList(search).getDatalist();
		List<LeaderOperationInfoVo> leaderInfoList = operationManageService.toLeaderOperationInfoList(search).getDatalist();
		// 导出数据
		String[] headers1 = { "姓名", "状态", "撰写教案（总数）", "上传课件（总数）", "教学反思（总数）", "听课记录（节数）", "教学文章（篇数）", "计划总结（篇数）", "集体备课（参与次数）", "集体备课（讨论数）", "集体备课（任主备人次数	）", "校际教研（参与次数）", "校际教研（讨论数）",
				"校际教研（任主备人次数	）", "区域教研（参与次数）", "区域教研（讨论数）", "同伴互助（留言数）", "成长档案袋（资源数）", "分享发表（篇数）", "资源总数" };
		String[] propertyNames1 = { "userName", "status", "lessonPlanCount", "kejianCount", "fansiCount", "listenCount", "teachTextCount", "planSummaryCount", "activityJoinCount",
				"activityDiscussCount", "activityMainCount", "schoolActivityJoinCount", "schoolActivityDiscussCount", "schoolActivityMainCount", "regionActivityJoinCount",
				"regionActivityDiscussCount", "peerMessageCount", "progressResCount", "shareCount", "resTotalCount" };
		String[] headers2 = { "姓名", "状态", "查阅教案（总数）", "查阅课件（总数）", "查阅反思（总数）", "查阅计划总结（篇数）", "听课记录（节数）", "教学文章（篇数）", "撰写计划总结（篇数）", "集体备课（发起次数）", "集体备课（参与次数）", "集体备课（讨论数）", "集体备课（查阅次数）", "校际教研（发起次数）",
				"校际教研（参与次数）", "校际教研（讨论数）", "区域教研（参与次数）", "区域教研（讨论数）", "同伴互助（留言数）", "分享发布（篇数）", "资源总数" };
		String[] propertyNames2 = { "userName", "status", "viewLessonPlanCount", "viewKejianCount", "viewFansiCount", "viewPlanSummaryCount", "listenCount", "teachTextCount", "planSummaryCount",
				"activityPushCount", "activityJoinCount", "activityDiscussCount", "activityViewCount", "schoolActivityPushCount", "schoolActivityJoinCount", "schoolActivityDiscussCount",
				"regionActivityJoinCount", "regionActivityDiscussCount", "peerMessageCount", "shareCount", "resTotalCount" };
		operationManageService.exportUserOperationToExcel(headers1, propertyNames1, teacherInfoList, headers2, propertyNames2, leaderInfoList, org.getName(), response);
	}

}
