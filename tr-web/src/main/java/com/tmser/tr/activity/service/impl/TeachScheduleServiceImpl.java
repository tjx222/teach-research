/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.bo.TeachSchedule;
import com.tmser.tr.activity.dao.SchoolTeachCircleOrgDao;
import com.tmser.tr.activity.dao.TeachScheduleDao;
import com.tmser.tr.activity.service.TeachScheduleService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.dao.OrganizationDao;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.notice.constants.NoticeType;
import com.tmser.tr.notice.service.impl.NoticeUtils;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;
/**
 * 教研进度表 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: TeachSchedule.java, v 1.0 2015-05-18 Generate Tools Exp $
 */
@Service
@Transactional
public class TeachScheduleServiceImpl extends AbstractService<TeachSchedule, Integer> implements TeachScheduleService {

	@Autowired
	private TeachScheduleDao teachScheduleDao;
	
	@Autowired
	private SchoolTeachCircleOrgDao schoolTeachCircleOrgDao;
	
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private UserSpaceDao userSpaceDao;
	@Autowired
	private OrganizationDao organizationDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<TeachSchedule, Integer> getDAO() {
		return teachScheduleDao;
	}
	//展示进度表信息
    @Override
	public List<SchoolTeachCircleOrg> getindex(){
    	
    	UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    	List<SchoolTeachCircleOrg> sList=schoolTeachCircleOrgDao.getSchoolTeachCircle( us.getOrgId(), (Integer)WebThreadLocalUtils
 				.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
    	 return sList ;
    }
    
    /**
	 * @param teachSchedule
	 * @param page
	 * @return
	 * @see com.tmser.tr.activity.service.TeachScheduleService#findTeachlList(com.tmser.tr.activity.bo.TeachSchedule, com.tmser.tr.common.page.Page)
	 */
	@Override
	public PageList<TeachSchedule> findTeachlList(TeachSchedule teachSchedule,
			Page page) {
		// TODO Auto-generated method stub
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR); //学年
		
		teachSchedule.setIsRelease(true);
		teachSchedule.addOrder("lastupDttm desc");
		teachSchedule.setSchoolYear(schoolYear);
		page.setPageSize(12);//每页显示多少条
		teachSchedule.addPage(page);
		
		Integer sysRoleId=userSpace.getSysRoleId();
		if(sysRoleId.intValue() == SysRole.TEACHER.getId().intValue() || sysRoleId.intValue()==SysRole.BKZZ.getId().intValue()){//教师 、备课组长
			teachSchedule.setSubjectId(userSpace.getSubjectId());
			teachSchedule.setGradeId(userSpace.getGradeId());
		}else if(sysRoleId.intValue()==SysRole.XKZZ.getId().intValue()){//学科组长
			teachSchedule.setSubjectId(userSpace.getSubjectId());;
		}else if(sysRoleId.intValue()==SysRole.NJZZ.getId().intValue()){//年级组长
			teachSchedule.setGradeId(userSpace.getGradeId());
		}else if(sysRoleId.intValue()==SysRole.JYY.getId().intValue()){//教研员
			Organization org = organizationDao.get(userSpace.getOrgId());
			teachSchedule.setAreaIds(SqlMapping.LIKE_PRFIX+","+org.getAreaId()+","+SqlMapping.LIKE_PRFIX);
		}else if(sysRoleId.intValue()==SysRole.JYZR.getId().intValue()){//教研主任
			Organization org = organizationDao.get(userSpace.getOrgId());
			teachSchedule.setAreaIds(SqlMapping.LIKE_PRFIX+","+org.getAreaId()+","+SqlMapping.LIKE_PRFIX);
		}
		PageList<TeachSchedule> listPage = teachScheduleDao.listPage(teachSchedule);
		return listPage;
	}
    
     //保存校际进度表信息
    @Override
	public void saveTeach(TeachSchedule ts,String originFileName){
    	UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    	if (ts!=null) {
    		ts.setCrtDttm(new Date());
    		ts.setLastupDttm(new Date());
    		ts.setIsRelease(false);
    		if (ts.getId()!=null) {
    			TeachSchedule oldSchedule=teachScheduleDao.get(ts.getId());
    			if (StringUtils.isNotEmpty(oldSchedule.getResId())) {
    				resourcesService.deleteResources(oldSchedule.getResId());
				}
				teachScheduleDao.update(ts);
				resourcesService.updateTmptResources(ts.getResId());
			}else {
				String fileSuffix=originFileName.substring(originFileName.lastIndexOf(".")+1);
				ts.setFileSuffix(fileSuffix);
				ts.setOrgId(us.getOrgId());
				Organization org = organizationDao.get(us.getOrgId());
				ts.setAreaIds(SqlMapping.LIKE_PRFIX+","+org.getAreaId()+","+SqlMapping.LIKE_PRFIX);
				ts.setSchoolYear((Integer)WebThreadLocalUtils
	    				.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
				ts.setCrtId(us.getUserId());
				ts.setLastupId(us.getUserId());
				ts.setEnable(1);
	        	ts = teachScheduleDao.insert(ts);
	        	resourcesService.updateTmptResources(ts.getResId());
			}
		}
    }
	/**
	 * 添加上传进度表的消息通知
	 * @param ts
	 */
    @Override
	public void addXiaoXiTongZhi(TeachSchedule ts) {
    	if(ts!=null && ts.getId()!=null){
    		ts = teachScheduleDao.get(ts.getId());
    		if(ts.getSchoolTeachCircleId()!=null && ts.getSubjectId()!=null && ts.getGradeId()!=null && ts.getIsRelease()){
    			SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
    			stco.setStcId(ts.getSchoolTeachCircleId());
    			stco.addCustomCondition(" and state in ("+SchoolTeachCircleOrg.YI_TONG_YI+","+SchoolTeachCircleOrg.YI_HUI_FU+")", new HashMap<String,Object>());
    			stco.addCustomCulomn(" orgId ");
    			List<SchoolTeachCircleOrg> listAll = schoolTeachCircleOrgDao.listAll(stco);
    			if(listAll!=null && listAll.size()>0){
    				List<Integer> orgIds = new ArrayList<Integer>();
    				for(SchoolTeachCircleOrg stcoTemp : listAll){
    					if(stcoTemp.getOrgId()!=null){
    						orgIds.add(stcoTemp.getOrgId());
    					}
    				}
    				if(orgIds!=null && orgIds.size()>0){
    					UserSpace us = new UserSpace();
    					Map<String,Object> paramsMap = new HashMap<String,Object>();
    					paramsMap.put("orgIds", orgIds);
    					paramsMap.put("subjectId", ts.getSubjectId());
    					paramsMap.put("gradeId", ts.getGradeId());
    					User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    					String sql = "and orgId in (:orgIds) and ( subjectId=:subjectId or gradeId=:gradeId or (subjectId=0 and gradeId=0) ) and userId !="+user.getId();
    					us.addCustomCondition(sql, paramsMap);
    					us.addCustomCulomn(" distinct(userId) ");
    					List<UserSpace> userList = userSpaceDao.listAll(us);
    					if(userList!=null && userList.size()>0){
    						Map<String,Object> noticeInfo = new HashMap<String, Object>();
    						String title = "发布"+ts.getName()+"教研进度表";
    						UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
    						Organization organization = organizationDao.get(userSpace.getOrgId());
    						if(organization!=null){
    							String content = organization.getAreaName()+organization.getName()+userSpace.getSpaceName()+userSpace.getUsername();
    							noticeInfo.put("type", 3);
    							noticeInfo.put("id", ts.getId());
    							noticeInfo.put("name", ts.getName());
    							noticeInfo.put("content", content);
    							for(UserSpace usTemp : userList){
    								if(usTemp.getUserId()!=null){
    									NoticeUtils.sendNotice(NoticeType.SCHOOL_ACTIVITY_TZ, title, userSpace.getUserId(), usTemp.getUserId(), noticeInfo);
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    	}
	}
	
}
