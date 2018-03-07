/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.zzjg;

import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author ghw
 * @version $Id: WxSchoolVo.java, v 1.0 2016年11月18日 下午5:31:21 ghw Exp $
 */
public class WxSchoolVo {
	/*
	 * 
	 * { "fetchtime": 1458185573728, "count": 1, "schools": [ { "id": 10010,
	 * "name": "测试小学123", "website": "", "place": "2327", "address": "",
	 * "zipcode": "", "tel": "", "email": "", "fax": "", "edudeptid": 205,
	 * "date_type": 1 } ] }
	 */
	private String fetchtime;
	private Integer count;
	private List<School> schools;

	public String getFetchtime() {
		return fetchtime;
	}

	public void setFetchtime(String fetchtime) {
		this.fetchtime = fetchtime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<School> getSchools() {
		return schools;
	}

	public void setSchools(List<School> schools) {
		this.schools = schools;
	}

	public static class School {
		private String id;
		private String name;
		private String website;
		//区域id
		private Integer place;
		private String address;
		private String zipcode;
		private String tel;
		private String email;
		private String fax;
		//所属机构id
		private String edudeptid;
		private Integer date_type;
		//学段
		private String fstage;
		//学制
		private String feducational;

		public String getFstage() {
			return fstage;
		}

		public String getFeducational() {
			return feducational;
		}

		public void setFeducational(String feducational) {
			this.feducational = feducational;
		}

		public void setFstage(String fstage) {
			this.fstage = fstage;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getWebsite() {
			return website;
		}

		public void setWebsite(String website) {
			this.website = website;
		}

		public Integer getPlace() {
			return place;
		}

		public void setPlace(Integer place) {
			this.place = place;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getZipcode() {
			return zipcode;
		}

		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}

		public String getTel() {
			return tel;
		}

		public void setTel(String tel) {
			this.tel = tel;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getFax() {
			return fax;
		}

		public void setFax(String fax) {
			this.fax = fax;
		}

		public String getEdudeptid() {
			return edudeptid;
		}

		public void setEdudeptid(String edudeptid) {
			this.edudeptid = edudeptid;
		}

		public Integer getDate_type() {
			return date_type;
		}

		public void setDate_type(Integer date_type) {
			this.date_type = date_type;
		}

	}
}
