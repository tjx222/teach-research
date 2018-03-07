/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.xxsy.schoolbanner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.xxsy.schoolbanner.service.SchoolBannerService;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.xxsy.bannermanner.bo.SchoolBanner;

/**
 * 学校横幅广告控制器接口
 * 
 * <pre>
 *
 * </pre>
 *
 * @author lijianghu
 * @version $Id: SchoolBanner.java, v 1.0 2015-10-28 lijianghu Exp $
 */
@Controller
@RequestMapping("/jy/back/xxsy/hfgg")
public class SchoolBannerController extends AbstractController {

	@Autowired
	private SchoolBannerService schoolBannerService;

	@Autowired
	private ResourcesService resourcesService;
	@Value("#{config.getProperty('front_web_url','')}")
	private String defaultFrontWebUrl;
	private final static Logger logger = LoggerFactory.getLogger(SchoolBannerController.class);
	/**
	 * 展示首页横幅广告列表
	 */
	@RequestMapping("/schoolBannerList")
	public String flatformAnnouncementList(SchoolBanner schoolBanner, Model m) {
		if (StringUtils.isNotEmpty(schoolBanner.order())) {
			schoolBanner.addOrder("crtDttm desc");
		}
		Organization org = (Organization)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
		if(null!=org){
			schoolBanner.setOrgId(org.getId());
			m.addAttribute("orgId",org.getId());
			
		}else{
			//判断是否已经选择orgid
			if(schoolBanner==null||schoolBanner.getOrgId()==null){
				//模块列表url
				m.addAttribute("orgurl", "/jy/back/xxsy/hfgg/schoolBannerList?orgId=");
				//模块加载divId
				m.addAttribute("divId", "hfggid");
				return "/back/xxsy/selectOrg";
			}else{
				m.addAttribute("orgId", schoolBanner.getOrgId());
			}
		}
		schoolBanner.addOrder("crtDttm desc");
		PageList<SchoolBanner> schoolBannerList = this.schoolBannerService
				.findByPage(schoolBanner);
		m.addAttribute("schoolBannerList", schoolBannerList);
		return viewName("/schoolBannerList");
	}

	/**
	 * 删除首页广告
	 */
	@RequestMapping(value = "/batchdelete")
	@ResponseBody
	public JuiResult batchdelete(String ids) {
		JuiResult rs = new JuiResult();
		try {
			if (ids != null) {
				for (String id : ids.split(",")) {
					SchoolBanner model = new SchoolBanner();
					model = schoolBannerService.findOne(Integer.parseInt(id));
					for(String resId:model.getAttachs().split(",")){
						this.deleteImg(resId, true);
					}
					this.schoolBannerService.delete(Integer.parseInt(id));
					LoggerUtils.deleteLogger(LoggerModule.XXSY, "平台公告——横幅广告——删除，操作人ID："+CurrentUserContext.getCurrentUser().getId());
				}
			}
			rs.setMessage("删除成功！");
			rs.setStatusCode(JuiResult.SUCCESS);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("删除失败！");
			logger.error("删除失败", e);
		}
		return rs;
	}

	/**
	 * 首页横幅广告的显示或隐藏
	 */
	@RequestMapping("/isShowBanner")
	@ResponseBody
	public JuiResult isShowBanner(SchoolBanner schoolBanner, String flag) {
		JuiResult rs = new JuiResult();
		rs.setMessage("操作成功！");
		try {
			if (flag.equalsIgnoreCase("0")) {// 隐藏图片
				schoolBanner.setIsview(0);
				schoolBannerService.update(schoolBanner);
				LoggerUtils.updateLogger(LoggerModule.XXSY, "平台公告——横幅广告——更新(隐藏)，操作人ID："+CurrentUserContext.getCurrentUser().getId());
			} else {
				/**
				 * 显示图片，要操作其他图片的状态
				 */
				schoolBanner.setIsview(1);
				this.schoolBannerService.updateSchoolBanner(schoolBanner);
				LoggerUtils.updateLogger(LoggerModule.XXSY, "平台公告——横幅广告——更新(显示)，操作人ID："+CurrentUserContext.getCurrentUser().getId());
				rs.setStatusCode(JuiResult.SUCCESS);
			}
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("操作失败！");
			logger.error("显示或隐藏图片失败", e);
		}
		return rs;
	}

	/**
	 * 跳转发布或个修改横幅广告页面
	 * 
	 * @return
	 */
	@RequestMapping("/toReleaseSchoolBanner")
	public String releaseHomeAds(SchoolBanner schoolBanner, Model m) {
		if (schoolBanner.getId() != null) {
			schoolBanner = schoolBannerService.findOne(schoolBanner.getId());
			Resources res = resourcesService.findOne(schoolBanner.getAttachs());
			if (res != null) {
				m.addAttribute("picpath", res.getPath());
			}
		}
		m.addAttribute("schoolBannerdata", schoolBanner);
		return viewName("releaseSchoolBanner");
	}

	/**
	 * 保存/更新 横幅广告
	 * 
	 * @return
	 */
	@RequestMapping("/saveHomeAds")
	@ResponseBody
	public JuiResult saveHomeAds(SchoolBanner schoolBanner) {
		JuiResult rs = new JuiResult();
		try {
			// 维护resources表图片id状态
			if (schoolBanner.getAttachs() != null) {
				resourcesService.updateTmptResources(schoolBanner.getAttachs());
			}
			if (schoolBanner.getId() != null) {// 编辑
				if(!StringUtils.isEmpty(schoolBanner.getFlags())){
					this.deleteImg(schoolBanner.getFlags(), true);
			}
				schoolBannerService.update(schoolBanner);
				LoggerUtils.updateLogger(LoggerModule.XXSY, "平台公告——横幅广告——更新，操作人ID："+CurrentUserContext.getCurrentUser().getId());
			} else {
				schoolBannerService.saveSchoolBanner(schoolBanner);
				LoggerUtils.insertLogger(LoggerModule.XXSY, "平台公告——横幅广告——保存，操作人ID："+CurrentUserContext.getCurrentUser().getId());
			}
			rs.setMessage(defaultFrontWebUrl+"/jy/schoolview/index?orgID="+schoolBanner.getOrgId());
			rs.setStatusCode(JuiResult.SUCCESS);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("保存失败！");
			logger.error("保存新闻广告图片失败-->FlatformAnnouncementController->saveHomeAds",e);
		}
		return rs;
	}
	/**
	 * 删除文件
	 * @param imgId 资源id
	 * @param isweb 是否是web下资源
	 * @return
	 */
	@RequestMapping("/deleteImg")
	public void deleteImg(String imgId,Boolean isweb){
		resourcesService.deleteResources(imgId);
	} 
}