package com.tmser.tr.schoolview.controller.resource;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.bo.SchoolActivity;
import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.activity.service.SchoolActivityService;
import com.tmser.tr.activity.service.SchoolTeachCircleOrgService;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.schoolres.service.SchoolResService;
import com.tmser.tr.schoolview.controller.CommonController;
import com.tmser.tr.schoolview.vo.CommonModel;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 教研活动控制器
 * <pre>
 *		返回教研活动的展示数据
 * </pre>
 *
 * @author tmser
 * @version $Id: TeachActiveController.java, v 1.0 2015-11-3 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/schoolview/res/teachactive")
public class TeachActiveController extends CommonController{

	@Autowired
	protected SchoolResService schoolResService;
	@Autowired
	private SchoolActivityService schoolActivityService;
	@Autowired
	private SchoolTeachCircleOrgService schoolTeachCircleOrgService;
	@Autowired
	private OrganizationService organizationService;
	@Resource
	private ActivityService activityService;

	/**
	 * 教研活动首页展示5条
	 * @param m
	 * @return
	 */
	@RequestMapping("/index/refulshactive")
	public String index(CommonModel cm,Model m){
		handleCommonVo(cm, m);//设置页面头部信息返回学校对象
		try{
			if("1".equals(cm.getRestype())){//集体备课
				Activity activity = (Activity)setActivityCondition(cm);//设置集体备课的查询条件
				List<Activity> activities = schoolResService.findLimitActivity(activity,5);
				m.addAttribute("activities", activities);
			}else if("2".equals(cm.getRestype())){//校际教研
				SchoolActivity schoolActivity = (SchoolActivity)setActivityCondition(cm);//设置校际教研的查询条件
				List<SchoolActivity> activities=schoolResService.findLimitSchoolActivity(schoolActivity,5);
				m.addAttribute("activities", activities);
			}
		}catch(Exception e){
			e.printStackTrace();;
		}
		return  viewName("/refulshactive");
	}

	/**
	 * 设置教研活动的查询条件
	 * @return
	 */
	protected Object setActivityCondition(CommonModel cm){
//		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		if("1".equals(cm.getRestype())){//集体备课
			Activity obj = new Activity();
			if(cm.getOrgID()!=null){
				obj.setOrgId(cm.getOrgID());
			}
			if(cm.getXdid()!=null){
				obj.setPhaseId(cm.getXdid());//设置学段
			}
			obj.setIsShare(true);//设置分享
//			obj.setSchoolYear(schoolYear);//设置当前学年
			//处理学科
			if(cm.getSubject()!=null){
				if(cm.getSubject()!=0){
					obj.setSubjectIds(SqlMapping.LIKE_PRFIX+","+cm.getSubject()+","+SqlMapping.LIKE_PRFIX);
				}
			}
			obj.addOrder("shareTime desc");//分享时间降序排列
			return obj;
		}else{//校际教研
			SchoolActivity obj = new SchoolActivity();
			if(cm.getOrgID()!=null){
				obj.setOrgId(cm.getOrgID());
			}
			if(cm.getXdid()!=null){
				obj.setPhaseId(cm.getXdid());//设置学段
			}
			obj.setIsShare(true);//设置分享
//			obj.setSchoolYear(schoolYear);//设置当前学年
			//处理学科
			if(cm.getSubject()!=null){
				if(cm.getSubject()!=0){
					obj.setSubjectIds(SqlMapping.LIKE_PRFIX+","+cm.getSubject()+","+SqlMapping.LIKE_PRFIX);
				}
			}
			obj.addOrder("shareTime desc");//分享时间降序排列
			return obj;
		}

	}

	/**
	 * 得到教研活动详细带分页
	 * @param m
	 * @param request
	 * @param lp
	 * @return
	 */
	@RequestMapping("/getSpecificAvtive")
	public String getSpecificAvtive(CommonModel cm,Page page,Model m){
		cm.setDh("3");
		handleCommonVo(cm, m);//设置页面头部信息返回学校对象
		if("1".equals(cm.getRestype())){//集体备课
			Activity activity = (Activity)setActivityCondition(cm);
			activity.pageSize(10);//设置每页的展示数
			activity.currentPage(page.getCurrentPage());//设置传递当前页数
			int count = schoolResService.findActivityCount(activity);//资源的总数量
			m.addAttribute("count", count);
			PageList<Activity> activitys=schoolResService.findPageActivity(activity);//备课资源分页
			m.addAttribute("data", activitys);//按照分页进行查询
		}else if("2".equals(cm.getRestype())){//校际教研
			SchoolActivity schoolActivity = (SchoolActivity)setActivityCondition(cm);
			schoolActivity.pageSize(10);//设置每页的展示数
			schoolActivity.currentPage(page.getCurrentPage());//设置传递当前页数
			int count=schoolResService.findActivityCount(schoolActivity);//资源的总数量
			m.addAttribute("count", count);
			PageList<SchoolActivity> schoolActivitys=schoolResService.findPageSchoolActivity(schoolActivity);//备课资源分页
			m.addAttribute("data", schoolActivitys);//按照分页进行查询
		}
		m.addAttribute("subjectsID", getSubjectsByxdid(cm.getXdid()));//查找相应学段学科的集合
		return  viewName("/activedetailed");
	}

	/**
	 * 集体备课判断是否有参与权限
	 * @param activity
	 * @return
	 */
	@RequestMapping("/havePowerOfActivity")
	private void havePowerOfActivity(Activity activity,Model m) {
		activity = activityService.findOne(activity.getId());
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		Integer roleId = userSpace.getSysRoleId();//角色id
		if(activity.getOrgId().intValue()==userSpace.getOrgId().intValue()){//资源和用户同一机构
			if(roleId.intValue()==SysRole.XZ.getId().intValue() || roleId.intValue()==SysRole.FXZ.getId().intValue()
					|| roleId.intValue()==SysRole.ZR.getId().intValue()){//校长、副校长、主任
				m.addAttribute("havePower", true);
			}else if(roleId.intValue()==SysRole.XKZZ.getId().intValue()){//学科组长
				if(activity.getOrgId().intValue()==userSpace.getOrgId().intValue() && activity.getSubjectIds().contains(","+String.valueOf(userSpace.getSubjectId())+",")){
					m.addAttribute("havePower", true);
				}else{
					m.addAttribute("havePower", false);
				}
			}else if(roleId.intValue()==SysRole.NJZZ.getId().intValue()){//年级组长
				if(activity.getOrgId().intValue()==userSpace.getOrgId().intValue() && activity.getGradeIds().contains(","+String.valueOf(userSpace.getGradeId())+",")){
					m.addAttribute("havePower", true);
				}else{
					m.addAttribute("havePower", false);
				}
			}else if(roleId.intValue()==SysRole.BKZZ.getId().intValue() || roleId.intValue()==SysRole.TEACHER.getId().intValue()){//备课组长或老师
				if(activity.getOrgId().intValue()==userSpace.getOrgId().intValue() && activity.getSubjectIds().contains(","+String.valueOf(userSpace.getSubjectId())+",") && activity.getGradeIds().contains(","+String.valueOf(userSpace.getGradeId())+",")){
					m.addAttribute("havePower", true);
				}else{
					m.addAttribute("havePower", false);
				}
			}
		}else{
			m.addAttribute("havePower", false);
		}
	}
	/**
	 * 校际教研判断是否有参与权限
	 * @param schoolActivity
	 * @return
	 */
	@RequestMapping("/havePowerOfSchoolActivity")
	private void havePowerOfSchoolActivity(SchoolActivity schoolActivity,String flag,Model m) {
		UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
		schoolActivity = schoolActivityService.findOne(schoolActivity.getId());
		Integer roleId = userSpace.getSysRoleId();
		//用户所在机构是否满足活动指定的教研圈
		boolean isMatchTheCircle = false;
		if("join".equals(flag)){
			isMatchTheCircle = schoolTeachCircleOrgService.ifMatchTheCircleByStates(userSpace.getOrgId(),schoolActivity.getSchoolTeachCircleId(),new Integer[]{SchoolTeachCircleOrg.YI_TONG_YI,SchoolTeachCircleOrg.YI_HUI_FU});
		}else if("view".equals(flag)){
			isMatchTheCircle = schoolTeachCircleOrgService.ifMatchTheCircleByStates(userSpace.getOrgId(),schoolActivity.getSchoolTeachCircleId(),new Integer[]{SchoolTeachCircleOrg.YI_TONG_YI,SchoolTeachCircleOrg.YI_HUI_FU,SchoolTeachCircleOrg.YI_TUI_CHU});
		}
		String areaIds = organizationService.findOne(userSpace.getOrgId()).getAreaIds(); //当前用户所属的地区层级
		if(roleId.intValue()==SysRole.JYZR.getId().intValue() || roleId.intValue()==SysRole.JYY.getId().intValue()){
			if(areaIds.equals(schoolActivity.getAreaIds())){
				m.addAttribute("havePower", true);
			}else{
				m.addAttribute("havePower", false);
			}
		}else if(roleId.intValue()==SysRole.XZ.getId().intValue() || roleId.intValue()==SysRole.FXZ.getId().intValue()
				|| roleId.intValue()==SysRole.ZR.getId().intValue()){//校长、副校长或主任
			if(isMatchTheCircle){
				m.addAttribute("havePower", true);
			}else{
				m.addAttribute("havePower", false);
			}
		}else if(roleId.intValue()==SysRole.XKZZ.getId().intValue()){//学科组长
			if(isMatchTheCircle && schoolActivity.getSubjectIds().contains("," + userSpace.getSubjectId()+ ",")){
				m.addAttribute("havePower", true);
			}else{
				m.addAttribute("havePower", false);
			}
		}else if(roleId.intValue()==SysRole.NJZZ.getId().intValue()){//年级组长
			if(isMatchTheCircle && schoolActivity.getGradeIds().contains("," + userSpace.getGradeId()+ ",")){
				m.addAttribute("havePower", true);
			}else{
				m.addAttribute("havePower", false);
			}
		}else if(roleId.intValue()==SysRole.BKZZ.getId().intValue() || roleId.intValue()==SysRole.TEACHER.getId().intValue()){//备课组长或老师
			if(isMatchTheCircle && schoolActivity.getSubjectIds().contains("," + userSpace.getSubjectId()+ ",") && schoolActivity.getGradeIds().contains("," + userSpace.getGradeId()+ ",")){
				m.addAttribute("havePower", true);
			}else{
				m.addAttribute("havePower", false);
			}
		}
	}
}
