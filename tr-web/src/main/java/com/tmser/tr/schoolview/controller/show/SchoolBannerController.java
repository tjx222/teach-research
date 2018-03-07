package com.tmser.tr.schoolview.controller.show;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.schoolbanner.SchoolBannerServices;
import com.tmser.tr.schoolview.controller.CommonController;
import com.tmser.tr.schoolview.vo.CommonModel;
import com.tmser.tr.xxsy.bannermanner.bo.SchoolBanner;

/**
 * 首页横幅广告
 * @author ljh
 *
 */
@Controller
@RequestMapping("/jy/schoolview/show/")
public class SchoolBannerController extends CommonController {

	@Autowired
	private SchoolBannerServices schoolBannerServices;

	@Autowired
	private ResourcesService resourcesService;

	/**
	 * 加载首页banner
	 * 
	 * @param orgID
	 * @param m
	 * @return
	 */
	@RequestMapping("/loadSchoolBanner")
	public String loadSchoolBanner(CommonModel cm, Model m, SchoolBanner schoolBanner) {
		schoolBanner.setIsview(1);// 显示
		schoolBanner.setOrder("crtDttm desc");
		schoolBanner.setOrgId(cm.getOrgID());
		List<SchoolBanner> SchoolBannerList = schoolBannerServices.find( schoolBanner, 3);
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		if (SchoolBannerList != null) {
			for (int i = 0; i < SchoolBannerList.size(); i++) {
				map.put(i, resourcesService.findOne( SchoolBannerList.get(i).getAttachs()).getPath());
			}
		}
		m.addAttribute("imgUrls", map);
		handleCommonVo(cm, m);
		return "/schoolview/show/hfgg/schoolbanner";
	}
}
