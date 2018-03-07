package com.tmser.tr.schoolview.controller.resource;

import java.util.List;
import java.util.Map;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.recordbag.service.RecordService;
import com.tmser.tr.recordbag.service.RecordbagService;
import com.tmser.tr.schoolres.service.SchoolResService;
import com.tmser.tr.schoolview.controller.CommonController;
import com.tmser.tr.schoolview.vo.CommonModel;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.service.ThesisService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.writelessonplan.service.LessonPlanService;


/**
 * 学校资源-成长档案控制器
 * <pre>
 *		返回学校资源-成长档案的展示数据
 * </pre>
 *
 * @author yangchao
 * @version $Id: LectureRecords.java, v 1.0 2015-03-30 Generate Tools Exp $
 */
@Controller(value="schoolResRecordController")
@RequestMapping("/jy/schoolview/res/recordbags")
public class RecordBagsController  extends CommonController{

	@Autowired
	private RecordService recordService;//档案袋精选service

	@Autowired
	private RecordbagService recordbagService;//档案袋service
	@Autowired
	private LessonPlanService lessonPlanService;
	@Autowired
	private LectureRecordsService lectureRecordsService;
	@Autowired
	protected ActivityService activityService;
	@Autowired
	private SchoolResService schoolResService;
	@Autowired
	private ThesisService thesisService;
	@Autowired
	private PlainSummaryService plainSummaryService;
	@Autowired
	private UserSpaceService userSpaceService;

	//---------------------------成长档案袋---------------------------------------
	/**
	 * 得到成长档案袋前6条
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("/index/getRecordBag")
	public String getRecordBag(CommonModel cm,Model m){
		List<Map<String, Object>> listMapbags=schoolResService.findAllRecordbags(cm.getOrgID());//分组查找所有的档案袋
		m.addAttribute("listMapbags", listMapbags);
		handleCommonVo(cm, m);
		return viewName("refulshrecordbag");
	}
	/**
	 * 得到成长档案袋详细信息(带分页)
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("getSpecificRecordBag")
	public String getSpecificRecordBag(CommonModel cm,Recordbag rb,Model m){
		rb.getPage().setPageSize(12);//设置每页显示条数
		PageList<Map<String, Object>> pageList=schoolResService.findPageRecordbags(rb.getPage(),cm.getOrgID());//分组查找所有的档案袋,分页显示
		m.addAttribute("data", pageList);//按照分页进行查询
		m.addAttribute("rb", rb);
		cm.setDh("5");
		handleCommonVo(cm, m);
		return viewName("bagsdetailed");
	}
	/**
	 * 查找档案袋里面的资源
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("findresByBagID")
	public String findresByBagID(CommonModel cm,String userID,Integer id,Model m,Record rd) {
		cm.setDh("5");
		m.addAttribute("userID", userID);
		handleCommonVo(cm, m);
		Recordbag recordbag=new Recordbag();//档案袋
		PageList<Record> records=null;//分页查看档案袋里面的额内容
		//初始化用户的系统成长
		if(id!=null){
			Record record=new Record();//袋中精选资源
			record.setBagId(id);
			record.pageSize(6);//设置每页的展示数
			if(rd!=null){
				record.currentPage(rd.getPage().getCurrentPage());//设置传递当前页数
			}
			records=recordService.findByPage(record);//备课资源分页

			recordbag=recordbagService.findOne(id);
		}
		m.addAttribute("recordbag", recordbag);
		if(recordbag.getType()==0){
			if(Recordbag.JXSJ.equals(recordbag.getName())||Recordbag.ZZKJ.equals(recordbag.getName())||Recordbag.JXFS.equals(recordbag.getName())){//教学设计、自制课件、教学反思
				if(records!=null&&!CollectionUtils.isEmpty(records.getDatalist())){
					for(Record r:records.getDatalist()){
						LessonPlan plan=lessonPlanService.findOne(r.getResId());
						r.setCrtId(plan.getSubjectId());//科目
						r.setFlags(plan.getBookShortname());//版本
						r.setLastupId(plan.getGradeId());//年级
						r.setEnable(plan.getFasciculeId());//学期
					}
				}
				m.addAttribute("data", records);//按照分页进行查询
				return viewName("bagsreslist1");
			}else if(Recordbag.JYHD.equals(recordbag.getName())){//教研活动
				for(Record r:records.getDatalist()){
					Activity activity=activityService.findOne(r.getResId());
					r.setFlago(activity.getSubjectName());//学科名字
					r.setFlags(activity.getGradeName());//年级名字
					r.setLastupDttm(activity.getCreateTime());
					r.setLastupId(activity.getTypeId());//校验和活动资源类型
					if (activity.getIsOver()==true) {
						r.setSchoolYear(1);
					}else {
						r.setSchoolYear(0);
					}

				}
				m.addAttribute("data", records);//按照分页进行查询
				return viewName("bagsreslist2");
			}else if(Recordbag.JXWZ.equals(recordbag.getName())){
				for(Record r:records.getDatalist()){//教学文章
					Thesis thesis=thesisService.findOne(r.getResId());
					r.setCrtId(thesis.getSubjectId());//科目
					r.setFlags(thesis.getThesisType());//资源类型名
				}
				m.addAttribute("zytype", 0);
				m.addAttribute("data", records);//按照分页进行查询
				return viewName("bagsreslist1");
			}else if(Recordbag.JHZJ.equals(recordbag.getName())){
				for(Record r:records.getDatalist()){//计划总结
					PlainSummary pSummary=plainSummaryService.findOne(r.getResId());
					r.setCrtId(pSummary.getSubjectId());//科目
					r.setLastupId(pSummary.getGradeId());//年级
				}
				m.addAttribute("zytype", 1);
				m.addAttribute("data", records);//按照分页进行查询
				return viewName("bagsreslist1");
			}else {
				for(Record r:records.getDatalist()){//听课记录
					LectureRecords lRecords=lectureRecordsService.findOne(r.getResId());
					r.setCrtId(lRecords.getSubjectId());//科目
					r.setLastupId(lRecords.getGradeId());//年级
					r.setVolume(lRecords.getType());
					r.setFlags(lRecords.getGradeSubject());
				}
				m.addAttribute("data", records);//按照分页进行查询
				return viewName("bagsreslist3");
			}
		}else{
			for(Record r:records.getDatalist()){//其他
				r.setCrtId(recordbag.getSubjectId());//科目
				r.setLastupId(recordbag.getGradeId());//年级
				r.setDesc(recordbag.getDesc());
			}
			m.addAttribute("data", records);//按照分页进行查询
			return viewName("bagsreslist4");
		}
	}
	/**
	 * 成长档案袋列表页
	 * @param m
	 * @return
	 */
	@RequestMapping("findList")
	public String findRecordBagList(CommonModel cm,Model m,Recordbag rdmodel) {
		cm.setDh("5");
		handleCommonVo(cm, m);
		//初始化用户的系统成长
		if(rdmodel.getTeacherId()!=null){
			List<Recordbag> list = recordbagService.findAll(rdmodel);
			m.addAttribute("authorId", rdmodel.getTeacherId());
			m.addAttribute("recordBagList", list);
			if(list!=null&&list.size()>0){
				Recordbag bag = list.get(0);
				UserSpace us = new UserSpace();
				us.setGradeId(bag.getGradeId());
				us.setSubjectId(bag.getSubjectId());
				us.setSysRoleId(SysRole.TEACHER.getId());
				us.setUserId(Integer.valueOf(bag.getTeacherId()));
				us = userSpaceService.findOne(us);
				if(us != null){
					rdmodel.setSpaceId(us.getId());
				}
			}
		}
		m.addAttribute("rb", rdmodel);
		return viewName("list");
	}

	/**
	 * 查看计划总结详细
	 * @param psId
	 * @param m
	 */
	@RequestMapping("/viewPlain")
	public String viewPlain(CommonModel cm,Integer lesid,Integer recordbagID,Model m){
		cm.setDh("5");
		handleCommonVo(cm, m);

		if(lesid!=null){
			PlainSummary ps = plainSummaryService.findOne(lesid);
			m.addAttribute("ps", ps);
		}
		m.addAttribute("recordbagID",recordbagID );
		m.addAttribute("recordbagName",recordbagService.findOne(recordbagID).getName());
		return viewName("planSummaryView");
	}


	/**
	 * 查看教学文章详细
	 * @param m
	 * @param id
	 * @return
	 */
	@RequestMapping("/viewThesis")
	public String viewThesis(CommonModel cm,Integer lesid,Integer recordbagID,Model m){
		cm.setDh("5");
		handleCommonVo(cm, m);
		Thesis thesis=thesisService.findOne(lesid);
		m.addAttribute("thesis", thesis);
		m.addAttribute("resType", ResTypeConstants.JIAOXUELUNWEN);//教学资源的resType类型
		m.addAttribute("recordbagID",recordbagID);
		m.addAttribute("recordbagName",recordbagService.findOne(recordbagID).getName());
		return viewName("thesisview");
	}
	/**
	 * 查看单个听课记录
	 * @param info
	 * @param m
	 * @return
	 */
	@RequestMapping("viewLecture")
	public String viewLecture(CommonModel cm,Integer lesid,Integer recordbagID,Model m){
		cm.setDh("5");
		handleCommonVo(cm, m);
		LectureRecords lr=lectureRecordsService.findOne(lesid);
		m.addAttribute("lr", lr);//按照主键查询单个
		m.addAttribute("recordbagID",recordbagID );
		m.addAttribute("recordbagName",recordbagService.findOne(recordbagID).getName());
		return viewName("lecturerecords");
	}
	/**
	 * 查看文章(lessonPlan)
	 * @param lesid
	 * @param m
	 * @return
	 */
	@RequestMapping("/viewLessons")
	public String viewLessons(CommonModel cm,Integer lesid,Integer recordbagID,Model m){
		cm.setDh("5");
		handleCommonVo(cm, m);
		if(lesid != null){
			m.addAttribute("data", schoolResService.getOneLessonPlan(lesid));
		}
		m.addAttribute("resType", ResTypeConstants.RECORD_BAG);
		m.addAttribute("recordbagID", recordbagID);
		m.addAttribute("recordbagName", recordbagService.findOne(recordbagID).getName());
		return viewName("view");
	}
	/**
	 * 查看自建档案袋内容
	 * @param lesid
	 * @param m
	 * @return
	 */
	@RequestMapping("/viewOthers")
	public String viewOthers(CommonModel cm,Integer lesid,Integer recordbagID,Model m){
		cm.setDh("5");
		handleCommonVo(cm, m);
		Record  rd=recordService.findOne(lesid);
		m.addAttribute("resType", ResTypeConstants.ZIZHIRECORD);//教学资源的resType类型
		if(lesid != null){
			m.addAttribute("rd", rd);
			m.addAttribute("crtId",recordbagService.findOne(rd.getBagId()).getTeacherId());
		}
		m.addAttribute("recordbagID", recordbagID);
		m.addAttribute("recordbagName", recordbagService.findOne(recordbagID).getName());
		return viewName("viewothers");
	}
}
