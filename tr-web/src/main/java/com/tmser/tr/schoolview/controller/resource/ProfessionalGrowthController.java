package com.tmser.tr.schoolview.controller.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.schoolres.service.SchoolResService;
import com.tmser.tr.schoolview.controller.CommonController;
import com.tmser.tr.schoolview.vo.CommonModel;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.service.ThesisService;

/**
 * 学校资源-专业成长控制器
 * 
 * <pre>
 * 返回学校资源 - 专业成长的展示数据
 * </pre>
 *
 * @author yangchao
 * @version $Id: LectureRecords.java, v 1.0 2015-03-30 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/schoolview/res/progrowth")
public class ProfessionalGrowthController extends CommonController {

	@Autowired
	private LectureRecordsService lectureRecordsService;
	@Autowired
	private SchoolResService schoolResService;
	@Autowired
	private ThesisService thesisService;
	@Autowired
	private PlainSummaryService plainSummaryService;

	// --------------------------------专业成长-----------------------------------------
	/**
	 * 得到专业成长前5条
	 * 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("/index/getProfession")
	public String getProfession(CommonModel cm, Model m) {
		if ("1".equals(cm.getRestype())) {// 计划总结
			PlainSummary plainSummary = setjihuazongjie(cm.getOrgID(), cm.getXdid(), cm.getSubject());// 设置计划总结的查询条件
			List<PlainSummary> plainSummarys = schoolResService.findAllPlainSummary(plainSummary);

			m.addAttribute("professions", plainSummarys);
		} else if ("2".equals(cm.getRestype())) {// 教学文章
			Thesis thesis = setjiaoxuewenzhang(cm.getOrgID(), cm.getXdid(), cm.getSubject());// 设置计划总结的查询条件
			List<Thesis> thesises = schoolResService.findAllThesis(thesis);

			m.addAttribute("professions", thesises);
		} else if ("3".equals(cm.getRestype())) {// 校际教研
			LectureRecords lectureRecords = setLectureRecords(cm.getOrgID(), cm.getXdid(), cm.getSubject());// 设置听课记录的查询条件
			List<LectureRecords> lectureRecordses = schoolResService.findAllLectureRecords(lectureRecords);

			m.addAttribute("professions", lectureRecordses);
		}
		handleCommonVo(cm, m);
		return "/schoolview/res/progrowth/refulshprofession";
	}

	/**
	 * 查看计划总结详细
	 * 
	 * @param psId
	 * @param m
	 */
	@RequestMapping("/viewFile")
	public String cancelSubmit(CommonModel cm, Integer id, Model m) {
		if (id != null) {
			PlainSummary ps = plainSummaryService.findOne(id);
			m.addAttribute("ps", ps);
		}
		m.addAttribute("resType", ResTypeConstants.TPPLAIN_SUMMARY_SUMMARY);// 教学资源的resType类型
		handleCommonVo(cm, m);
		return viewName("planSummaryView");
	}

	/**
	 * 查看教学文章详细
	 * 
	 * @param m
	 * @param id
	 * @return
	 */
	@RequestMapping("/thesisview")
	public String thesisView(CommonModel cm, Integer id, Model m) {
		Thesis thesis = thesisService.findOne(id);
		m.addAttribute("thesis", thesis);
		m.addAttribute("resType", ResTypeConstants.JIAOXUELUNWEN);// 教学资源的resType类型
		handleCommonVo(cm, m);
		return viewName("thesisview");
	}

	/**
	 * 查看单个听课记录
	 * 
	 * @param info
	 * @param m
	 * @return
	 */
	@RequestMapping("seetopic")
	public String seeTopic(CommonModel cm, Integer id, Model m) {
		WebThreadLocalUtils.getRequest().getSession().setAttribute("dh", "4");// 区分跳转的导航
		LectureRecords lr = lectureRecordsService.findOne(id);
		m.addAttribute("lr", lr);// 按照主键查询单个
		m.addAttribute("resType", ResTypeConstants.LECTURE);// 教学资源的resType类型
		handleCommonVo(cm, m);
		return viewName("lecturerecords");
	}

	/**
	 * 得到专业成长详细带分页
	 * 
	 * @param m
	 * @param request
	 * @param lp
	 * @return 注意:为测试分页现在每页为5条数据,原来是每页10条数据.
	 */
	@RequestMapping("getSpecificProfession")
	public String getSpecificProfession(CommonModel cm, PlainSummary ps, Model m) {
		cm.setDh("4");
		if ("1".equals(cm.getRestype())) {// 计划总结
			PlainSummary plainSummary = setjihuazongjie(cm.getOrgID(), cm.getXdid(), cm.getSubject());
			plainSummary.pageSize(5);// 设置每页的展示数
			if (ps != null) {
				plainSummary.currentPage(ps.getPage().getCurrentPage());// 设置传递当前页数
			}
			int count = schoolResService.findAllPlainSummary(plainSummary).size();// 资源的总数量
			m.addAttribute("count", count);

			PageList<PlainSummary> plainSummarys = schoolResService.findPagePlainSummary(plainSummary);// 备课资源分页
			m.addAttribute("data", plainSummarys);// 按照分页进行查询
		} else if ("2".equals(cm.getRestype())) {// 教学文章
			Thesis thesis = setjiaoxuewenzhang(cm.getOrgID(), cm.getXdid(), cm.getSubject());
			thesis.pageSize(5);// 设置每页的展示数
			if (ps != null) {
				thesis.currentPage(ps.getPage().getCurrentPage());// 设置传递当前页数
			}
			int count = schoolResService.findAllThesis(thesis).size();// 资源的总数量
			m.addAttribute("count", count);

			PageList<Thesis> thesises = schoolResService.findPageThesis(thesis);// 备课资源分页
			m.addAttribute("data", thesises);// 按照分页进行查询
		} else if ("3".equals(cm.getRestype())) {
			LectureRecords lectureRecords = setLectureRecords(cm.getOrgID(), cm.getXdid(), cm.getSubject());
			lectureRecords.pageSize(5);// 设置每页的展示数
			if (ps != null) {
				lectureRecords.currentPage(ps.getPage().getCurrentPage());// 设置传递当前页数
			}
			int count = schoolResService.findAllLectureRecords(lectureRecords).size();// 资源的总数量
			m.addAttribute("count", count);

			PageList<LectureRecords> lectureRecordses = schoolResService.findPageLectureRecords(lectureRecords);// 备课资源分页
			m.addAttribute("data", lectureRecordses);// 按照分页进行查询
		}
		m.addAttribute("subjectsID", getSubjectsByxdid(cm.getXdid()));// 查找相应学段学科的集合
		handleCommonVo(cm, m);
		return viewName("professiondetailed");
	}

	/**
	 * 设置计划总结查询条件
	 * 
	 * @param request
	 * @return
	 */
	protected PlainSummary setjihuazongjie(Integer orgID, Integer xdid, Integer subject) {
		PlainSummary plainSummary = new PlainSummary();
		if (orgID != null) {
			plainSummary.setOrgId(orgID);
		}

		if (xdid != null && xdid != 0) {
			plainSummary.setPhaseId(xdid);// 设置学段
		}

		plainSummary.setIsShare(1);// 设置分享
//		Object schoolYear = WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
//		if (schoolYear != null) {
//			plainSummary.setSchoolYear(Integer.parseInt(schoolYear.toString()));// 设置当前学年
//		} else {
//			plainSummary.setSchoolYear(schoolYearService.getCurrentSchoolYear());// 设置当前学年
//		}
		// 处理学科
		if (subject != null) {
			if (0 != subject) {
				plainSummary.setSubjectId(subject);
			}
		}
		plainSummary.addOrder("crtDttm desc");// 创建时间降序排列
		return plainSummary;
	}

	/**
	 * 设置教学文章查询条件
	 * 
	 * @param request
	 * @return
	 */
	protected Thesis setjiaoxuewenzhang(Integer orgID, Integer xdid, Integer subject) {
		Thesis thesis = new Thesis();
		if (orgID != null) {
			thesis.setOrgId(orgID);
		}

		if (xdid != null && xdid != 0) {
			thesis.setPhaseId(xdid);// 设置学段
		}

		thesis.setIsShare(1);// 设置分享
		// 处理学科
		if (subject != null && subject != 0) {
			thesis.setSubjectId(subject);
		}
		thesis.addOrder("crtDttm desc");// 创建时间降序排列
		return thesis;
	}

	/**
	 * 设置听课记录查询条件(公共方法)
	 * 
	 * @param request
	 * @return
	 */
	protected LectureRecords setLectureRecords(Integer orgID, Integer xdid, Integer subject) {
		LectureRecords lectureRecords = new LectureRecords();
		if (orgID != null) {
			lectureRecords.setOrgId(orgID);
		}

		if (xdid != null) {
			lectureRecords.setPhaseId(xdid);// 设置学段
		}

		lectureRecords.setIsShare(1);// 设置分享
//		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
//		if (schoolYear != null) {
//			lectureRecords.setSchoolYear(schoolYear);// 设置当前学年
//		} else {
//			lectureRecords.setSchoolYear(schoolYearService.getCurrentSchoolYear());// 设置当前学年
//		}
		// 处理学科
		if (subject != null && subject != 0) {
			lectureRecords.setSubjectId(subject);
		}
		lectureRecords.addOrder("crtDttm desc");// 创建时间降序排列
		return lectureRecords;
	}
}
