/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tmser.tr.activity.bo.SchoolTeachCircle;
import com.tmser.tr.activity.service.SchoolTeachCircleService;
import com.tmser.tr.activity.vo.SchoolTeachCircleVo;
import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 校际教研圈控制器接口
 * 
 * <pre>
 *
 * </pre>
 *
 * @author zpp
 * @version $Id: SchoolTeachCircleOrg.java, v 1.0 2015-05-12 zpp Exp $
 */
@Controller
@RequestMapping("/jy/schoolactivity")
public class SchoolTeachCircleController extends AbstractController {

	private static final Logger logger = LoggerFactory
			.getLogger(SchoolTeachCircleController.class);

	@Resource
	private OrganizationService organizationService;
	@Resource
	private SchoolTeachCircleService schoolTeachCircleService;
	@Resource
	private UserSpaceService userSpaceService;
	@Resource
	private AreaService areaService;

	/**
	 * 教研圈管理首页
	 */
	@RequestMapping("/circle/index")
	@UseToken
	public String teachCircleIndex(Model m) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Integer sysRoleId = userSpace.getSysRoleId();
		if (sysRoleId.intValue() == SysRole.XZ.getId().intValue()
				|| sysRoleId.intValue() == SysRole.FXZ.getId().intValue()
				|| sysRoleId == SysRole.ZR.getId().intValue()) {

			// 查询校际教研圈
			List<SchoolTeachCircle> stcList = schoolTeachCircleService
					.findAllCircleByOrg();
			m.addAttribute("stcList", stcList);

			List<Area> provinceList = areaService.findAreaListByParentId(0, 1);// 区域省列表
			m.addAttribute("provinceList", provinceList);

			return "/schoolactivity/tch_circle/school_teach_circle_index";
		} else {
			return "forward:/jy/schoolactivity/circle/lookTeachCircle";
		}
	}

	/**
	 * 查询相关的学校
	 */
	@RequestMapping("/circle/searchSchool")
	public String searchSchool(String schoolName, String areaIds, Model m) {
		List<Organization> findAll = organizationService
				.findOrgByNameAndAreaIds(schoolName, areaIds);
		m.addAttribute("orgList", findAll);
		return "";
	}

	/**
	 * 判断教研圈名称是否重复
	 */
	@RequestMapping("/circle/checkName")
	public String save(SchoolTeachCircle stc, Model m) {
		Boolean isOk = true;
		try {
			String saveCircle = schoolTeachCircleService.checkCircleName(stc);
			if ("yicunzai".equals(saveCircle)) {
				isOk = false;
			}
		} catch (Exception e) {
			isOk = false;
			logger.error("校际教研圈保存出现错误！", e);
		}
		m.addAttribute("isOk", isOk);
		return "";
	}

	/**
	 * 保存校际教研圈
	 */
	@RequestMapping("/circle/save")
	@UseToken
	public String save(String circleOrgs, SchoolTeachCircle stc, Model m) {
		Boolean isOk = true;
		try {
			String saveCircle = schoolTeachCircleService.saveCircle(stc,
					circleOrgs);
			if ("yicunzai".equals(saveCircle)) {
				isOk = false;
				m.addAttribute("msg", "yicunzai");
			}
		} catch (Exception e) {
			logger.error("校际教研圈保存出现错误！", e);
			isOk = false;
		}
		m.addAttribute("isOk", isOk);
		return "";
	}

	/**
	 * 删除校际教研圈
	 */
	@RequestMapping("/circle/delete")
	public String deleteCircle(SchoolTeachCircle stc, Model m) {
		Boolean isOk = true;
		try {
			schoolTeachCircleService.deleteCircle(stc);
		} catch (Exception e) {
			logger.error("校际教研圈删除出现错误！", e);
			isOk = false;
		}
		m.addAttribute("isOk", isOk);
		return "";
	}

	/**
	 * 退出校际教研圈
	 */
	@RequestMapping("/circle/tuichu")
	public String tuichuCircle(SchoolTeachCircle stc, Model m) {
		Boolean isOk = true;
		try {
			schoolTeachCircleService.setCircleOrgState(stc, 4);
		} catch (Exception e) {
			logger.error("校际教研圈退出出现错误！", e);
			isOk = false;
		}
		m.addAttribute("isOk", isOk);
		return "";
	}

	/**
	 * 恢复校际教研圈
	 */
	@RequestMapping("/circle/huifu")
	public String huifuCircle(SchoolTeachCircle stc, Model m) {
		Boolean isOk = true;
		try {
			schoolTeachCircleService.setCircleOrgState(stc, 5);
		} catch (Exception e) {
			logger.error("校际教研圈恢复出现错误！", e);
			isOk = false;
		}
		m.addAttribute("isOk", isOk);
		return "";
	}

	/**
	 * 查看校际教研圈
	 */
	@RequestMapping("/circle/lookTeachCircle")
	public String lookTeachCircle(SchoolTeachCircle stc, Model m) {
		// 查询校际教研圈
		List<SchoolTeachCircle> stcList = schoolTeachCircleService
				.lookTeachCircle();
		m.addAttribute("stcList", stcList);
		return "/schoolactivity/tch_circle/lookTeachCircle";
	}

	/**
	 * 校际教研圈邀请加入教研圈首页
	 */
	@RequestMapping("/circle/yaoQingIndex")
	public String lookTeachCircle(Integer stcId, Integer spaceId, Model m) {
		SchoolTeachCircle stc = schoolTeachCircleService.findOne(stcId);
		m.addAttribute("stc", stc);
		UserSpace userSpace = userSpaceService.findOne(spaceId);
		m.addAttribute("userSpace", userSpace);
		if (userSpace != null) {
			Organization org = organizationService
					.findOne(userSpace.getOrgId());
			m.addAttribute("org", org);
		}
		return "/schoolactivity/tch_circle/yaoqing";
	}

	/**
	 * 学校机构是否接受校际教研圈的邀请
	 */
	@RequestMapping("/circle/saveYaoQing")
	public String huifuCircle(Integer state, Integer stcId, Long noticeId,
			String content, Model m) {
		Boolean isSuccess = true;
		try {
			isSuccess = schoolTeachCircleService.saveYaoQing(state, stcId,
					noticeId, content);
		} catch (Exception e) {
			logger.error("校际教研圈的邀请操作错误！", e);
			isSuccess = false;
		}
		m.addAttribute("isSuccess", isSuccess);
		return "";
	}

	/**
	 * 获取学校所在的教研圈列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/joinSchoolCycles", method = RequestMethod.GET)
	public ModelAndView getSchoolCycles() {
		ModelAndView mv = new ModelAndView();
		// 查询学校参加的教研圈
		List<SchoolTeachCircleVo> vos = schoolTeachCircleService
				.getCurrentSchoolJoinCicles();
		mv.addObject("vos", vos);
		return mv;
	}

	/**
	 * 根据父级id获取级别区域列表
	 * 
	 * @param area
	 *            parentId:父级id，level:级别等级
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/circle/findAreaListByParentId")
	public List<Area> findAreaListByParentId(Area area) {
		List<Area> areaList = new ArrayList<Area>();
		areaList = areaService.findAreaListByParentId(area.getParentId(),
				area.getLevel());
		return areaList;
	}
}