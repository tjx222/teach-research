/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller.ws.wx;

import java.util.List;

/**
 * <pre>
 *	
 * </pre>
 *
 * @author tmser
 * @version $Id: WxUserVo.java, v 1.0 2016年1月6日 下午12:00:15 tmser Exp $
 */
public class WxUserVo {
	
	/*
	 * 接口数据：
		{"user":{
		"id":"e01a189aa9d64a4f893043077bda12fb",
		"name":"金乡",
		"ftechlevelid":"140",
		"ftechsubjectid":"100",
		"type":"2",
		"sex":"1",
		"home":"",
		"nation":"",
		"idNumber":"110258996655555",
		"email":"",
		"phone":"",
		"isDeleted":0,
		"loginCode":"A10058020001",
		}
	*/
	/**
	 * 用户真实姓名
	 */
	private String name;
	/**
	 *  所属学段， 暂不使用
	 */
	private Integer ftechlevelid;
	
	/**
	 * 所属学科， 暂不使用
	 */
	private Integer ftechsubjectid;

	/**
	 * 性别
	 */
	private Integer sex;
	
	/**
	 * 手机
	 */
	private String phone;
	
	
	/**
	 * 家庭地址 
	 */
	private String home;
	
	/**
	 * 民族
	 */
	private String nation;
	
	/**
	 * 身份证
	 */
	private String idNumber;
	
	private String email;
	
	private String techNumber;
	/**
	 * 是否删除
	 */
	private Integer isDeleted;
	
	/**
	 * 用户id
	 */
	private String id;
	
	/**
	 * 登录账号 
	 */
	private String loginCode;
	
	/**
	 * 对接机构id
	 */
	private String orgId;
	
	
	/** 
	 * Getter method for property <tt>orgId</tt>. 
	 * @return property value of orgId 
	 */
	public String getOrgId() {
		return orgId;
	}



	/**
	 * Setter method for property <tt>orgId</tt>.
	 * @param orgId value to be assigned to property orgId
	 */
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}



	/**
	 * 用户类型
	 *  教育局职工	0
		学校职工	1
		学校教师	2
		学校学生	3
		学校校长	4
		家长		
		教育局管理员 200
		学校管理员   300

	 */
	private Integer type;
	
	
	private List<Org> orgs;
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Integer getFtechlevelid() {
		return ftechlevelid;
	}



	public void setFtechlevelid(Integer ftechlevelid) {
		this.ftechlevelid = ftechlevelid;
	}



	public Integer getFtechsubjectid() {
		return ftechsubjectid;
	}



	public void setFtechsubjectid(Integer ftechsubjectid) {
		this.ftechsubjectid = ftechsubjectid;
	}



	public Integer getSex() {
		return sex==null?null:sex == 1 ? 0 : 1;
	}



	public void setSex(Integer sex) {
		this.sex = sex;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getHome() {
		return home;
	}



	public void setHome(String home) {
		this.home = home;
	}



	public String getNation() {
		return nation;
	}



	public void setNation(String nation) {
		this.nation = nation;
	}



	public String getIdNumber() {
		return idNumber;
	}



	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getTechNumber() {
		return techNumber;
	}



	public void setTechNumber(String techNumber) {
		this.techNumber = techNumber;
	}



	public Integer getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getLoginCode() {
		return loginCode;
	}



	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}



	public Integer getType() {
		return type;
	}



	public void setType(Integer type) {
		this.type = type;
	}



	public List<Org> getOrgs() {
		return orgs;
	}



	public void setOrgs(List<Org> orgs) {
		this.orgs = orgs;
	}



	public static class Org{
	   /*
		"isschool":0,
		"orgtype":"9",
		"areaname":"广安区",
		"orgid":56,
		"educationalid":"6,3"
		"orgname":"广安市第一小学",
		"porgid":5600,
		"areaid":"2405"
		 */
		
		private Integer isschool;
		
		private Integer orgtype;
		
		private String areaname;
		
		private String orgid;
		
		private String orgname;
		
		private String porgid;
		
		private Integer areaid;
		
		private String educationalid;
		
		private String phaseid;
		
		private String simplename;

		public String getEducationalid() {
			return educationalid;
		}

		public void setEducationalid(String educationalid) {
			this.educationalid = educationalid;
		}

		public String getPhaseid() {
			return phaseid;
		}

		public void setPhaseid(String phaseid) {
			this.phaseid = phaseid;
		}

		public String getSimplename() {
			return simplename;
		}

		public void setSimplename(String simplename) {
			this.simplename = simplename;
		}

		public Integer getIsschool() {
			return isschool;
		}

		public void setIsschool(Integer isschool) {
			this.isschool = isschool;
		}

		public Integer getOrgtype() {
			return orgtype;
		}

		public void setOrgtype(Integer orgtype) {
			this.orgtype = orgtype;
		}

		public String getAreaname() {
			return areaname;
		}

		public void setAreaname(String areaname) {
			this.areaname = areaname;
		}

		public String getOrgid() {
			return orgid;
		}

		public void setOrgid(String orgid) {
			this.orgid = orgid;
		}

		public String getOrgname() {
			return orgname;
		}

		public void setOrgname(String orgname) {
			this.orgname = orgname;
		}

		public String getPorgid() {
			return porgid;
		}

		public void setPorgid(String porgid) {
			this.porgid = porgid;
		}

		public Integer getAreaid() {
			return areaid;
		}

		public void setAreaid(Integer areaid) {
			this.areaid = areaid;
		}
		
		
		
	}
	
}
