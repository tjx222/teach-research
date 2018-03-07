/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller.ws.wx;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.org.bo.Area;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.controller.ws.wx.utils.Platform;
import com.tmser.tr.uc.service.LoginService;
import com.tmser.tr.uc.service.UserService;

/**
 * <pre>
 *  文轩数字图书馆用户对接,接出
 * </pre>
 *
 * @author tmser
 * @version $Id: WenXuanLoginController.java, v 1.0 2015年7月16日 上午10:27:31 tmser Exp $
 */
@Controller
@RequestMapping("/jy/ws/uc")
public class WenXuanTuShuLoginController extends AbstractController{
	
	@Resource
	private LoginService loginService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private AreaService areaService;
	
	
	@RequestMapping("/validate")
	@ResponseBody
	public WxUser modify(String appid,String appkey,String uid,Model m) {
		if(!check(appid, appkey)){
			return null;
		}
		Integer userid = null;
		WxUser wu = null;
		try {
			userid = Integer.valueOf(Platform.decrypt(uid));
			Login login = loginService.findOne(userid);
			User u = userService.findOne(login.getId());
			wu = new WxUser();
			wu.setName(u.getName());
			wu.setId(uid);
			wu.setLoginname(login.getLoginname());
			wu.setUserType(1);
			wu.setSchoolOrgId(u.getOrgId());
			wu.setSchoolOrgName(u.getOrgName());
			
			Area area = areaService.findOne(
					organizationService.findOne(u.getOrgId()).getAreaId());
			if(area != null){
				wu.setDistrictOrgId(Integer.valueOf(area.getCode()));
				wu.setDistrictOrgName(area.getName());
				area = areaService.findOne(area.getParentId());
				if(area != null){
					wu.setCityOrgName(area.getName());
					wu.setCityOrgId(Integer.valueOf(area.getCode()));
					wu.setProvinceOrgId(Integer.valueOf(area.getCode()+"0"));
					wu.setProvinceOrgName(area.getName());
					area = areaService.findOne(area.getParentId());
					if(area != null){
						wu.setProvinceOrgId(Integer.valueOf(area.getCode()));
						wu.setProvinceOrgName(area.getName());
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("wenxuan validate has happend error.", e);
		}
		
		return wu;
	}
	
	protected boolean check(String appid,String appkey){
		if("appidtest".equals(appid) && "appkeytest".equals(appkey)){
			return true;
		}
		
		return false;
	}
	
	class WxUser{
		private Integer provinceOrgId; //省编号
		private String provinceOrgName; //省名称
		private Integer cityOrgId; //市编号
		private String cityOrgName; // 市名称
		private Integer districtOrgId; // 区县编号
		private String districtOrgName; //区县名称
		private Integer schoolOrgId; //学校编号
		private String schoolOrgName; //学校名称

		private Integer userType;// 用户类型 2：教师 1：学生
		private String loginname;// 登录用户名
		private String name; //      姓名
		private String id;
		public Integer getProvinceOrgId() {
			return provinceOrgId;
		}
		public void setProvinceOrgId(Integer provinceOrgId) {
			this.provinceOrgId = provinceOrgId;
		}
		public String getProvinceOrgName() {
			return provinceOrgName;
		}
		public void setProvinceOrgName(String provinceOrgName) {
			this.provinceOrgName = provinceOrgName;
		}
		public Integer getCityOrgId() {
			return cityOrgId;
		}
		public void setCityOrgId(Integer cityOrgId) {
			this.cityOrgId = cityOrgId;
		}
		public String getCityOrgName() {
			return cityOrgName;
		}
		public void setCityOrgName(String cityOrgName) {
			this.cityOrgName = cityOrgName;
		}
		public Integer getDistrictOrgId() {
			return districtOrgId;
		}
		public void setDistrictOrgId(Integer districtOrgId) {
			this.districtOrgId = districtOrgId;
		}
		public String getDistrictOrgName() {
			return districtOrgName;
		}
		public void setDistrictOrgName(String districtOrgName) {
			this.districtOrgName = districtOrgName;
		}
		public Integer getSchoolOrgId() {
			return schoolOrgId;
		}
		public void setSchoolOrgId(Integer schoolOrgId) {
			this.schoolOrgId = schoolOrgId;
		}
		public String getSchoolOrgName() {
			return schoolOrgName;
		}
		public void setSchoolOrgName(String schoolOrgName) {
			this.schoolOrgName = schoolOrgName;
		}
		public Integer getUserType() {
			return userType;
		}
		public void setUserType(Integer userType) {
			this.userType = userType;
		}
		public String getLoginname() {
			return loginname;
		}
		public void setLoginname(String loginname) {
			this.loginname = loginname;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
	}
}
