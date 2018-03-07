package com.tmser.tr.schoolview.controller.resource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.browse.BaseResTypes;
import com.tmser.tr.browse.utils.BrowseRecordUtils;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.schoolres.service.SchoolResService;
import com.tmser.tr.schoolview.controller.CommonController;
import com.tmser.tr.schoolview.vo.CommonModel;

/**
 * 备课资源控制器
 * <pre>
 *		返回备课资源的展示数据
 * </pre>
 *
 * @author yangchao
 * @version $Id: LectureRecords.java, v 1.0 2015-03-30 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/schoolview/res/lessonres")
public class LessonResourceController extends CommonController{
	@Autowired
	protected SchoolResService schoolResService;
	//------------------------------备课资源-----------------------------------------
	/**
	 * 得到这个学校的备课资源(主页上前5条)
	 * @return
	 */
	@RequestMapping("/index/getPreparationRes")
	public String getPreparationRes(CommonModel cm,Model m){
		if(cm.getRestype()==null){
			LessonInfo lessonInfo=setPreparationRes(cm);//设置查询备课资源条件
			List<LessonInfo> lessonInfos=schoolResService.findAllLessonInfo(lessonInfo);
			m.addAttribute("lessonInfos", lessonInfos);
			handleCommonVo(cm,m);
			return viewName("/refulshlessoninfos");
		}else{
			LessonPlan lessonPlan=setJiaoan_kejian_fansi(cm);//设置教案、课件、反思的条件

			List<LessonPlan> lessonPlans=schoolResService.findAllLessonPlan(lessonPlan);
			m.addAttribute("lessonPlans", lessonPlans);
			handleCommonVo(cm,m);
			return viewName("/refulshjianan_kejian_fansi");
		}
	}

	/**
	 * 得到备课资源详细页(带分页)
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPreparationResDetailed")
	public String getPreparationResDetailed(CommonModel cm,Model m,LessonInfo lf){
		cm.setDh("2");
		LessonInfo lessonInfo=setPreparationRes(cm);//设置查询备课资源条件
		lessonInfo.pageSize(10);//设置每页的展示数
		if(lf!=null){
			lessonInfo.currentPage(lf.getPage().getCurrentPage());//设置传递当前页数
		}
		int count=schoolResService.findAllLessonInfo(lessonInfo).size();//资源的总数量
		m.addAttribute("count", count);

		PageList<LessonInfo> lessonInfos=schoolResService.findPageLessonInfo(lessonInfo);//备课资源分页
		m.addAttribute("data", lessonInfos);//按照分页进行查询
		m.addAttribute("subjectsID", getSubjectsByxdid(cm.getXdid()));//查找相应学段学科的集合
		handleCommonVo(cm,m);
		return viewName("/lessonsdetailed");
	}

	/**
	 * 查看备课资源(lessonInfo,lessonPlan)
	 * @param lessPlan
	 * @param m
	 * @return
	 */
	@RequestMapping("/viewlesson")
	public String viewLesson(CommonModel cm,Model m,Integer lessPlan){
		cm.setDh("2");
//		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		if(lessPlan != null){
			LessonInfo lessonInfo = schoolResService.getOneLessonInfo(lessPlan);
			LessonPlan model = new LessonPlan();
			model.setBookId(lessonInfo.getBookId());
			model.setLessonId(lessonInfo.getLessonId());
			model.setIsShare(true);
			model.setUserId(lessonInfo.getUserId());
//			model.setSchoolYear(schoolYear);
			model.addOrder("planType asc,orderValue asc");
			m.addAttribute("lessonList",schoolResService.findAllLessonPlan(model));
			m.addAttribute("data",lessonInfo);
		}
		handleCommonVo(cm,m);
		return viewName("/view_lesson");
	}
	/**
	 * 得到教案、课件、反思详细页（带分页）
	 * @param m
	 * @param request
	 * @param lp
	 * @return
	 */
	@RequestMapping("/getSpecificResjiaoankejianfansi")
	public String getSpecificResjiaoankejianfansi(CommonModel cm,Model m,LessonPlan lp){
		LessonPlan lessonPlan=setJiaoan_kejian_fansi(cm);//设置教案、课件、反思的条件
		lessonPlan.pageSize(10);//设置每页的展示数
		if(lp!=null){
			lessonPlan.currentPage(lp.getPage().getCurrentPage());//设置传递当前页数
		}
		int count=schoolResService.findAllLessonPlan(lessonPlan).size();//资源的总数量
		m.addAttribute("count", count);

		m.addAttribute("subjectsID", getSubjectsByxdid(cm.getXdid()));//查找相应学段学科的集合

		PageList<LessonPlan> lessonPlans=schoolResService.findPageLessonPlan(lessonPlan);//备课资源分页
		m.addAttribute("data", lessonPlans);//按照分页进行查询
		handleCommonVo(cm,m);
		return viewName("/jiaoan_kejian_fansidetailed");
	}
	/**
	 * 查看文章(lessonPlan)
	 * @param lesid
	 * @param m
	 * @return
	 */
	@RequestMapping("/view")
	public String view(CommonModel cm,Integer lesid,Model m){
		cm.setDh("2");
		if(lesid != null){
			LessonPlan lesson = schoolResService.getOneLessonPlan(lesid);
			m.addAttribute("data", lesson);
			if(lesson!=null){
				//添加浏览记录
				BrowseRecordUtils.addBrowseRecord(BaseResTypes.BKZY, lesson.getPlanId());
			}
		}
		handleCommonVo(cm,m);
		return viewName("/view");
	}
	/**
	 * 设置查询备课资源条件(公共方法)
	 * @param request
	 * @return
	 */
	public LessonInfo setPreparationRes(CommonModel cm){
		LessonInfo lessonInfo=new LessonInfo();
		if(cm != null){
			if(cm.getOrgID()!=null){
				lessonInfo.setOrgId(cm.getOrgID());
			}
			if(cm.getXdid()!=null){
				lessonInfo.setPhaseId(cm.getXdid());
				MetaRelationship gradeReship=MetaUtils.getMetaRelation(cm.getXdid());
				if(gradeReship!=null){//学段得到所属年级
					List<Meta> grades = MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(cm.getXdid());
					Set<Integer> gradeSet=new HashSet<Integer>();
					for (Meta grade : grades) {
						gradeSet.add(grade.getId());//一个学段的年级集合
					}
					Map<String,Object> paramter = new HashMap<String,Object>();
					paramter.put("gradeId", gradeSet);//年级的set集合
					paramter.put("cnt", 0);
					lessonInfo.addCustomCondition("and gradeId in(:gradeId) and ( jiaoanShareCount + fansiShareCount + kejianShareCount > :cnt ) "
							,paramter);
				}
			}
			//处理学科
			if(cm.getSubject()!=null){
				if(!"0".equals(cm.getSubject().toString())){
					lessonInfo.setSubjectId(cm.getSubject());
				}
			}
			lessonInfo.addOrder("crtDttm desc,share_time desc");//创建时间降序排列
		}
		return lessonInfo;
	}
	/**
	 * 设置教案、课件、反思的条件（公共方法）
	 * @param m
	 * @param request
	 * @return
	 */
	public LessonPlan setJiaoan_kejian_fansi(CommonModel cm){
		LessonPlan lessonPlan=new LessonPlan();
		if(cm != null){
			if(cm.getOrgID()!=null){
				lessonPlan.setOrgId(cm.getOrgID());
			}
			Map<String,Object> paramter = new HashMap<String,Object>();
			String hql = "";
			if(cm.getXdid()!=null){
				lessonPlan.setPhaseId(cm.getXdid());
				MetaRelationship gradeReship=MetaUtils.getMetaRelation(cm.getXdid());
				if(gradeReship!=null){//学段得到所属年级
					List<Meta> grades = MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(cm.getXdid());
					Set<Integer> gradeSet=new HashSet<Integer>();
					for (Meta grade : grades) {
						gradeSet.add(grade.getId());//一个学段的年级集合
					}
					paramter.put("gradeId", gradeSet);//年级的set集合
					hql += " and gradeId in(:gradeId) ";
				}
			}
			lessonPlan.addOrder("crtDttm desc,share_time desc");//创建时间降序排列

			//具体资源的类型
			if("fansi".equals(cm.getRestype())){//反思
				Set<Integer> fansiSet=new HashSet<Integer>();
				fansiSet.add(2);
				fansiSet.add(3);
				paramter.put("planType", fansiSet);
				hql += " and planType in(:planType) ";
			}else{//教案和课件
				lessonPlan.setPlanType(Integer.parseInt(cm.getRestype()));
			}
			if(StringUtils.isNotEmpty(hql)){
				lessonPlan.addCustomCondition(hql, paramter);
			}
			lessonPlan.setIsShare(true);//设置已经分享
			//处理学科
			if(cm.getSubject()!=null){
				if(!"0".equals(cm.getSubject().toString())){
					lessonPlan.setSubjectId(cm.getSubject());
				}
			}
			lessonPlan.setEnable(1);//有效的
		}
		return lessonPlan;
	}

	/**
	 * 添加资源浏览记录
	 * @param type
	 * @param resId
	 */
	@RequestMapping("/addBrowsingRecord")
	public void addBrowsingRecord(@RequestParam(value="type")Integer type,@RequestParam(value="resId")Integer resId){
		//添加浏览记录
		BrowseRecordUtils.addBrowseRecord(type, resId);
	}
}
