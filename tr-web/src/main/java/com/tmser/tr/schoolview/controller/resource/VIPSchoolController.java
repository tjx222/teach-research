package com.tmser.tr.schoolview.controller.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.schoolview.controller.CommonController;
import com.tmser.tr.schoolview.vo.CommonModel;


/**
 * 同伴学校资源控制器
 * <pre>
 *		返回同伴学校的展示数据
 * </pre>
 *
 * @author yangchao
 * @version $Id: LectureRecords.java, v 1.0 2015-03-30 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/schoolview/res/vipschool")
public class VIPSchoolController  extends CommonController{
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * 获取同伴学校
	 * @return
	 */
	@RequestMapping("/index/getVIPSchools")
	public String getVIPSchools(CommonModel cm,Model m){
		Organization organization = organizationService.findOne(cm.getOrgID());
		Organization model=setVIPschools(organization);
		List<Organization> vipschools=organizationService.findAll(model);//当前同伴校的集合
		m.addAttribute("vipschools", vipschools);
		return viewName("/refulshschoolVIP");
	}

	/**
	 * 得到学校详细页(带分页)
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("/index/getVIPSchoolsDetailed")
	public String getVIPSchoolsDetailed(CommonModel cm,Organization oz,Model m){
		Organization organization = organizationService.findOne(cm.getOrgID());
		Organization model=setVIPschools(organization);//设置查询同伴校的条件
		model.pageSize(8);//设置每页的展示数
		if(oz!=null){
			model.currentPage(oz.getPage().getCurrentPage());//设置传递当前页数
		}

		PageList<Organization> vipschools=organizationService.findByPage(model);//同伴校分页
		m.addAttribute("data", vipschools);//按照分页进行查询
		handleCommonVo(cm, m);
		return  viewName("/schoolVIPsdetailed");
	}

	/**
	 * 设置查询同伴校的条件
	 * @param request
	 * @return
	 */
	private Organization setVIPschools(Organization organization){
		Organization model=new Organization();
		model.setType(organization.getType());//学校
		model.setOrgType(organization.getOrgType());
		Map<String,Object> paramter = new HashMap<String,Object>();
		model.addCustomCulomn("id,parentId,name,shortName,logo,type,orgType");
		if(organization.getId()!=null){
			paramter.put("id", organization.getId());//不能包括登录者自己的学校
			model.addCustomCondition("and id != :id",paramter);//条件查询
		}
		return model;
	}
}
