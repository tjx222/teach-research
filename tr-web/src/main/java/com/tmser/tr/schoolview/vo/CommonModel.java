package com.tmser.tr.schoolview.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.utils.SpringContextHolder;

public class CommonModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//学校ID
	private Integer orgID;
	//学段ID
	private Integer xdid;
	//科目
	private Integer subject;
	//科目
	private Integer subjectId;
	//年级
	private Integer gradeId;
	//教师
	private Integer teacherId;
	//备课资源类型
	private String restype;
	//首页导航菜单栏标识{首页：1,备课资源：2,教研活动:3,专业成长:4,成长档案袋:5}
	private String dh; 
	//学段集合
	private List<MetaRelationship> xueduans;
	//默认学段
	private	MetaRelationship xddefault;
	//通过学校ID得到学校的信息
	private Organization organization ;
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public Integer getOrgID() {
		return orgID;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public Integer getGradeId() {
		return gradeId;
	}
	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}
	public Integer getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}
	public MetaRelationship getXddefault() {
		return xddefault;
	}
	public void setXddefault(MetaRelationship xddefault) {
		this.xddefault = xddefault;
	}
	public void setOrgID(Integer orgID) {
		this.orgID = orgID;
	}
	public Integer getXdid() {
		if(xdid == null){
			setPhase();
		}
		
		return xdid;
	}
	public void setXdid(Integer xdid) {
		this.xdid = xdid;
	}
	public Integer getSubject() {
		return subject;
	}
	public void setSubject(Integer subject) {
		this.subject = subject;
	}
	public String getRestype() {
		return restype;
	}
	public void setRestype(String restype) {
		this.restype = restype;
	}
	
	public List<MetaRelationship> getXueduans() {
		if(xueduans == null){
			setPhase();
		}
		return xueduans;
	}
	public void setXueduans(List<MetaRelationship> xueduans) {
		this.xueduans = xueduans;
	}
	
	/**
	 * 设置页面头部信息,得到学校对象(相关信息一存储到session)
	 * @param m
	 * @param request
	 * @return
	 */
	@Deprecated
	protected void setPhase(){
		
		if(orgID == null){//同伴校的ID
			return;
		}
		organization = SpringContextHolder.getBean(OrganizationService.class).findOne(orgID);
		//根据学制查询学段关系
		String xueduan[] ={};
		if(organization!=null&&organization.getPhaseTypes()!=null){
			xueduan = organization.getPhaseTypes().split(",");//学段进行拆分
		}
		List<MetaRelationship> xueduans = new ArrayList<MetaRelationship>();
		int flag=0;
		for(String xueduanStr : xueduan){
			if(!StringUtils.isEmpty(xueduanStr)){
				MetaRelationship xd = MetaUtils.getMetaRelation(Integer.parseInt(xueduanStr));
				if(this.xdid == null&&flag==0){
					this.xdid = Integer.parseInt(xueduanStr);
					this.xddefault=xd;
					flag++;
				}else if(this.xdid !=null&&this.xdid == Integer.parseInt(xueduanStr)){
					this.xddefault=xd;
				}
				xueduans.add(xd);
			}
		}
		
		this.xueduans = xueduans;
	}
	public String getDh() {
		return dh;
	}
	public void setDh(String dh) {
		this.dh = dh;
	}
}
