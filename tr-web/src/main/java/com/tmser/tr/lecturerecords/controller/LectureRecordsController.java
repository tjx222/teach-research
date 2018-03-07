/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.lecturerecords.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.service.LessonInfoService;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookChapterHerperService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.meta.vo.BookLessonVo;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.DateUtils;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * 听课记录控制器接口
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: LectureRecords.java, v 1.0 2015-03-30 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/lecturerecords/")
public class LectureRecordsController extends AbstractController {
	@Autowired
	private LectureRecordsService lectureRecordsService;
	@Autowired
	private UserSpaceService userSpaceService;
	@Autowired
	private LessonInfoService lessonInfoService;
	@Autowired
	private LessonPlanService lessonPlanService;
	@Autowired
	private BookChapterHerperService bookChapterHerperService;
	@Autowired
	private BookService bookService;

	/**
	 * 按条件查询所有的听课记录页面 info:从页面获得当前页数的参数 m:把查询分页结果设置到内存里面，可以在页面进行展示
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public String list(LectureRecords info, Model m) {
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);

		LectureRecords model = new LectureRecords();
		model.pageSize(10);// 设置每页的展示数
		model.setSchoolYear(schoolYear);// 当前学年
		model.setLecturepeopleId(user.getId());// 听课人ID
		model.setIsDelete(false);// 不删除
		model.setIsEpub(1);// 发布
		model.addOrder("epubTime desc");// 按照发布时间降序

		if (info != null) {// 从页面传过来的当前页数
			model.currentPage(info.getPage().getCurrentPage());
			if ("0".equals(info.getFlags())) {// 校内听课
				model.setType(0);
				m.addAttribute("flags", "0");
			} else if ("1".equals(info.getFlags())) {// 校外听课
				model.setType(1);
				m.addAttribute("flags", "1");
			}
		}
		int cp = model.getPage().getCurrentPage();

		PageList<LectureRecords> plList = lectureRecordsService.findByPage(model);// 查询当前页的评论
		if (plList.getDatalist().size() == 0 && cp > 1) {
			model.currentPage(cp - 1);
			plList = lectureRecordsService.findByPage(model);// 查询当前页的评论
		}

		m.addAttribute("data", plList);// 按照分页进行查询

		model.setIsEpub(0);// 查找草稿的集合
		model.setType(null);// 草稿箱不区分校内校外
		List<LectureRecords> caogaoList = lectureRecordsService.findAll(model);// 查找所有的草稿
		m.addAttribute("caogaoSize", caogaoList.size());

		model.setIsEpub(null);// 查找所有的听课记录,无论是否发布
		m.addAttribute("ifCanSubmit", ifCanSubmit(user.getId(),schoolYear));
		// if(allList.size()==0){
		// return "/lecturerecords/nolecturerecordslist";
		// }else{
		return "/lecturerecords/lecturerecordslist";
		// }
	}

	/**
	 * 判断是否有提交权限（只有 校长和副校长没有提交权限）
	 * @param id
	 * @param schoolYear
	 * @return
	 */
	private Boolean ifCanSubmit(Integer id, Integer schoolYear) {
		List<UserSpace> usList = userSpaceService.listUserSpaceBySchoolYear(id, schoolYear);
		for(UserSpace us:usList){
			if(us.getSysRoleId().intValue()==SysRole.XZ.getId().intValue()||us.getSysRoleId().intValue()==SysRole.FXZ.getId().intValue()){
				return false;
			}
		}
		return true;
	}

	/**
	 * 查找草稿箱集合
	 * 
	 * @param info
	 * @param m
	 * @return
	 */
	@RequestMapping("caogaoList")
	public String caogaoList(LectureRecords info, Model m) {
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);

		LectureRecords model = new LectureRecords();
		model.pageSize(10);// 设置每页的展示数
		model.setSchoolYear(schoolYear);// 当前学年
		model.setLecturepeopleId(user.getId());// 听课人ID
		model.setIsDelete(false);// 不删除
		model.setIsEpub(0);// 不发布,草稿
		model.addOrder("epubTime desc");// 按照草稿时间降序

		if (info != null) {// 从页面传过来的当前页数
			model.currentPage(info.getPage().getCurrentPage());
		}
		int cp = model.getPage().getCurrentPage();
		PageList<LectureRecords> plList = lectureRecordsService.findByPage(model);// 查询当前页的评论
		if (plList.getDatalist().size() == 0 && cp > 1) {
			model.currentPage(cp - 1);
			plList = lectureRecordsService.findByPage(model);// 查询当前页的评论
		}
		m.addAttribute("data", plList);// 按照分页进行查询
		return "/lecturerecords/caogaolecturerecordslist";
	}

	/**
	 * 查看单个听课记录
	 * 
	 * @param info
	 * @param m
	 * @return
	 */
	@RequestMapping("seetopic")
	public String seeTopic(@RequestParam(required=false,value="id")Integer id,Model m) {
		LectureRecords lr = lectureRecordsService.findOne(id);
		m.addAttribute("lr", lr);// 按照主键查询单个
		return "/lecturerecords/lecturerecords";
	}

	/**
	 * 调到撰写校外听课记录页面,修改校外听课记录页面
	 * 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("writeLectureRecordsOuteInput")
	@UseToken
	public String writeLectureRecordsOuteInput(@RequestParam(required=false,value="id")Integer id,
			@RequestParam(required=false,value="addflag")String addflag,Model m) {
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		m.addAttribute("user", user);
		if ("true".equals(addflag)) {
			String nowDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd");// 当前默认听课时间
			m.addAttribute("nowDate", nowDate);
		} else if ("false".equals(addflag)) {
			LectureRecords lr = lectureRecordsService.findOne(id);
			m.addAttribute("lr", lr);// 按照主键查询单个
		}
		return "/lecturerecords/outeditlecturerecords";
	}

	/**
	 * 保存或修改听课记录
	 * 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("writeLectureRecords")
	@UseToken
	public String writeLectureRecords(Model m, LectureRecords jspLr) {
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		Integer xueqi = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		if (jspLr != null) {
			if(jspLr.getType() == 0 && StringUtils.isBlank(jspLr.getLessonId())){
				m.addAttribute("lr", jspLr);
				m.addAttribute("error","课题不能为空！");
				return writeLectureRecordsInnerInput(m,true,userSpace.getId(),null);
			}
			
			jspLr.setSchoolYear(schoolYear);// 当前学年
			jspLr.setTerm(xueqi);// 当前学期
			jspLr.setOrgId(user.getOrgId());
			jspLr.setLecturepeopleId(user.getId());
			jspLr.setLecturePeople(user.getName());
			jspLr.setResType(ResTypeConstants.LECTURE);// 当前资源类型
			jspLr.setIsShare(0);
			jspLr.setIsDelete(false);
			jspLr.setIsSubmit(0);
			jspLr.setIsReply(0);
			jspLr.setReplyUp(0);
			jspLr.setIsComment(0);
			jspLr.setCommentUp(0);
			jspLr.setEpubTime(new Date());// 发布时间,也可为保存草稿的时间
			jspLr.setCrtDttm(new Date());// 创建时间
			jspLr.setPhaseId(userSpace.getPhaseId());// 当前学段，校内学段id 将使用听课内容实际学段id

			if (jspLr.getGradeSubject() != null) {// 校外听课记录"年级与班级",防止空格
				if ("".equals(jspLr.getGradeSubject().trim())) {
					jspLr.setGradeSubject("");
				}
			}

			if (jspLr.getTeachingPeople() != null) {// 校外听课记录"听课人"，防止空格
				if ("".equals(jspLr.getTeachingPeople().trim())) {
					jspLr.setTeachingPeople("");
				}
			}

			// 校内听课时保存，课题编号和课题内容
			String[] t = jspLr.getTopic().split(",");
			if (t.length > 1) {
				jspLr.setTopicId(Integer.parseInt(t[0]));
				jspLr.setTopic(t[1]);// 保存课题编号
			}
			// 修改或者保存校内、校外、草稿、发布听课记录
			lectureRecordsService.saveOrupdateLectureRecords(jspLr);// 保存听课记录回调
		}
		return "redirect:list";
	}

	/**
	 * 分享或者取消分享、删除
	 * 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("changeShare")
	public String changeShare(Integer id,String state,Model m) {
		if("取消分享".equals(state)){
			 m.addAttribute("quxiaofenxiang", "您已成功取消分享！");
		}
		 m.addAttribute("kt", lectureRecordsService.deleteOrShare(id,state));
		return viewName("refulshtr");
	}

	/**
	 * 调到校内听课记录找人页面
	 * 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("findpeople")
	public String findPeople(Integer phaseId, Model m) {
		Integer pd = phaseId;
		if(pd == null){
			pd = CurrentUserContext.getCurrentSpace().getPhaseId();
		}
		m.addAttribute("phaseId", pd);
		return "/lecturerecords/findpeople";
	}

	/**
	 * 根据条件检索听课老师
	 * 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("reflushPeople")
	public String reflushPeople(Model m, UserSpace info) {
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		int i = SysRole.TEACHER.getId();// 教师角色对应的ID
		UserSpace model = new UserSpace();
		model.setSysRoleId(i);// 角色
		model.setSchoolYear(schoolYear);
		model.setEnable(UserSpace.ENABLE);
		if (info != null) {
			model.setSubjectId(info.getSubjectId());// 科目
			model.setGradeId(info.getGradeId());// 年级
			model.setOrgId(user.getOrgId());// 学校,只能是本校区
			
			Map<String, Object> paramter = new HashMap<String, Object>();
			paramter.put("userId", user.getId());// 设计当前登陆者不会被选入听课者
			model.addCustomCondition(" and userId != :userId", paramter);// 条件查询
		}
		List<UserSpace> userList = userSpaceService.findAll(model);
		m.addAttribute("userList", userList);
		return viewName("reflushPeople");
	}

	/**
	 * 调到撰写校内听课记录页面,修改校内听课记录页面
	 * 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("writeLectureRecordsInnerInput")
	@UseToken
	public String writeLectureRecordsInnerInput(Model m, Boolean addflag, Integer userSpanceId, Integer id) {
		// 听课人
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		m.addAttribute("user", user);
		if (addflag) {// 新增校内听课记录页面
			// 授课老师对象信息(授课人、年级、科目)
			UserSpace us = userSpaceService.findOne(userSpanceId);
			m.addAttribute("us", us);
			if (us.getBookId() != null) {
				String bookId = us.getBookId();
				setBookChapter(bookId,m);
			}
			String nowDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd");// 当前默认听课时间
			m.addAttribute("nowDate", nowDate);
		} else if (!addflag) {// 草稿箱修改
			LectureRecords lr = lectureRecordsService.findOne(id);
			// 课题信息(课题名称、课题主键)
			if (lr != null) {
				if (lr.getTopicId() != null) {
					LessonInfo lesson = lessonInfoService.findOne(lr.getTopicId());
					if (lesson != null && lesson.getBookId() != null) {
						String bookId = lesson.getBookId();
						setBookChapter(bookId,m);
						lr.setLessonId(lesson.getLessonId());
						m.addAttribute("lesson", lesson);
					}
				} else {
					UserSpace us = new UserSpace();
					us.setOrgId(us.getOrgId());
					us.setUserId(lr.getTeachingpeopleId());
					us.setSubjectId(lr.getSubjectId());
					us.setGradeId(lr.getGradeId());
					us.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
					List<UserSpace> uslist = userSpaceService.find(us, 1);
					if (!CollectionUtils.isEmpty(uslist)) {
						us = uslist.get(0);
						String bookId = us.getBookId();
						setBookChapter(bookId,m);
						m.addAttribute("us", us);
					}

				}
				
				m.addAttribute("lr",lr);
			}
		}
		return "/lecturerecords/innereditlecturerecords";
	}

	
	private void setBookChapter(String bookId,Model m){
		Book book = bookService.findOne(bookId);
		Integer term = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_TERM);
		if(book.getFasciculeId()!=178){
			String bookId2 = "";
			if(term==0){//上学期
				if(book.getFasciculeId()==176){
					bookId2 = book.getRelationComId();
				}else{
					bookId2 = bookId;
					bookId = book.getRelationComId();
				}
				m.addAttribute("fasiciculeName", "上册书籍目录");
				m.addAttribute("fasiciculeName2", "下册书籍目录");
			}else{//下学期
				if(book.getFasciculeId()==177){
					bookId2 = book.getRelationComId();
				}else{
					bookId2 = bookId;
					bookId = book.getRelationComId();
				}
				m.addAttribute("fasiciculeName2", "上册书籍目录");
				m.addAttribute("fasiciculeName", "下册书籍目录");
			}
			List<BookLessonVo> bookChapters = bookChapterHerperService.getBookChapterTreeByComId(bookId);
			m.addAttribute("bookChapters", bookChapters);
			List<BookLessonVo> bookChapters2 = bookChapterHerperService.getBookChapterTreeByComId(bookId2);
			m.addAttribute("bookChapters2", bookChapters2);
		}else{
			List<BookLessonVo> bookChapters = bookChapterHerperService.getBookChapterTreeByComId(bookId);
			m.addAttribute("bookChapters", bookChapters);
			m.addAttribute("fasiciculeName", "全一册书籍目录");
		}
	}
	/**
	 * 得到一个lessonInfo对象
	 * 
	 * @param m
	 * @param request
	 */
	@RequestMapping("getLessonInfo")
	public void getLessonInfo(Model m, LessonInfo info) {
		info = lectureRecordsService.getLessonInfoByLessonId(info);
		m.addAttribute("info", info);
	}

	/**
	 * 查看教案
	 * 
	 * @param m
	 * @param info
	 * @return
	 */
	@RequestMapping("seelesson")
	public String seeLesson(String topicid,Model m) {
		if (topicid != null) {
			String id = topicid.split(",")[0];// lessoninfo的主键
			LessonInfo data = lessonInfoService.findOne(Integer.parseInt(id));
			m.addAttribute("data", data);

			// 查询具体教案
			LessonPlan lessonPlan = new LessonPlan();
			lessonPlan.setInfoId(Integer.parseInt(id));// 通过资源ID查找教案
			lessonPlan.setEnable(1);// 教案可用
			lessonPlan.setPlanType(LessonPlan.JIAO_AN);
			List<LessonPlan> lessonPlans = lessonPlanService.findAll(lessonPlan);
			m.addAttribute("lessonPlans", lessonPlans);
		}
		return "/lecturerecords/view_lesson";
	}

	/**
	 * 离线移动端查看教案，在线请求访问
	 * 
	 * @param m
	 * @param info
	 * @return
	 */
	@RequestMapping("seelessonM")
	public String seelessonM(Model m, LessonInfo info) {
		info = lectureRecordsService.getLessonInfoByLessonId(info);
		if (info != null) {
			m.addAttribute("data", info);
			// 查询具体教案
			LessonPlan lessonPlan = new LessonPlan();
			lessonPlan.setInfoId(info.getId());// 通过资源ID查找教案
			lessonPlan.setEnable(1);// 教案可用
			lessonPlan.setPlanType(LessonPlan.JIAO_AN);
			List<LessonPlan> lessonPlans = lessonPlanService.findAll(lessonPlan);
			m.addAttribute("lessonPlans", lessonPlans);
		}
		return "/lecturerecords/view_lesson";
	}

	/**
	 * ************************回复、评论、查阅控制器************************
	 */
	/**
	 * 一个授课人的某一个课题有多少听课列表 接口，传一个lesson_info的主键过来
	 * 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("teacherleclist")
	public String teacherleclist(Model m, LectureRecords lecPeople) {
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		// 登陆者(授课人)
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		if (lecPeople != null) {
			lecPeople.setSchoolYear(schoolYear);// 学年
			lecPeople.setTeachingpeopleId(user.getId());// 授课人ID
			lecPeople.setIsDelete(false);// 不删除
			lecPeople.setIsEpub(1);// 已发布
			lecPeople.addOrder("epubTime desc");// 按照发布时间降序
			List<LectureRecords> lecpeoples = lectureRecordsService.findAll(lecPeople);
			m.addAttribute("lecpeoples", lecpeoples);
		}
		return "/lecturerecords/lecpeoples/lecpeoplelist";
	}

	/**
	 * 移动离线端我的备课本 一个授课人的某一个课题有多少听课列表 接口，传一个lesson_info的主键过来
	 * 
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "teacherleclistMobile", method = RequestMethod.GET)
	public String teacherleclist(@RequestParam(value = "lessonId", required = true, defaultValue = "0") String lessonId,
			@RequestParam(value = "userId", required = true, defaultValue = "0") Integer userId, Model m) {
		LessonInfo li = new LessonInfo();
		li.setLessonId(lessonId);
		li.setUserId(userId);
		li.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
		li = lessonInfoService.findOne(li);
		if (li != null) {
			LectureRecords lecPeople = new LectureRecords();
			lecPeople.setTopicId(li.getId());//
			lecPeople.setTeachingpeopleId(userId);// 授课人ID
			Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
			// 登陆者(授课人)
			User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
			if (lecPeople != null) {
				lecPeople.setSchoolYear(schoolYear);// 学年
				lecPeople.setTeachingpeopleId(user.getId());// 授课人ID
				lecPeople.setIsDelete(false);// 不删除
				lecPeople.setIsEpub(1);// 已发布
				lecPeople.addOrder("epubTime desc");// 按照发布时间降序
				List<LectureRecords> lecpeoples = lectureRecordsService.findAll(lecPeople);
				m.addAttribute("lecpeoples", lecpeoples);
			}
		}
		return "/myplanbook/lecpeoplelist";
	}

	/**
	 * 跳到查看一个听课记录、评论页面
	 * 
	 * @param m
	 * @param lecPeople
	 * @return
	 */
	@RequestMapping("lecturereply")
	public String lecturereply(Model m, LectureRecords records) {
		if (records != null) {
			// m.addAttribute("records",records);

			LectureRecords lr = lectureRecordsService.findOne(records.getId());
			m.addAttribute("lr", lr);// 按照主键查询单个
			m.addAttribute("pl", "1");// 按照主键查询单个
		}
		return "/lecturerecords/lecturerecords";
		// return "/lecturerecords/lecpeoples/lecturereply";
	}

	/**
	 * 听课记录列表查看听课评论的时候更新听课记录状态
	 * 
	 * @param m
	 * @param records
	 * @return
	 */
	@RequestMapping("updateCommentState")
	public void updateCommentState(Integer resId) {
		if (resId != null) {
			LectureRecords records = lectureRecordsService.findOne(resId);
			records.setCommentUp(0);
			lectureRecordsService.update(records);
		}
	}

	/**
	 * 判断听课意见是否被删除
	 * 
	 * @param m
	 * @param
	 * @return
	 */
	@RequestMapping("isDelete")
	@ResponseBody
	public Result isDelete(Integer id) {
		Result rs = new Result();
		LectureRecords lectureRecords = lectureRecordsService.findOne(id);
		if (lectureRecords == null || lectureRecords.getIsDelete()) {
			rs.setCode(0);
		}
		return rs;
	}

	/**
	 * 获取已提交或未提交的听课记录
	 * @param isSubmit
	 * @param m
	 * @return
	 */
	@RequestMapping("getIsOrNotSubmitRecords")
	public String getIsOrNotSubmitRecords(Integer isSubmit,Model m){
		List<LectureRecords> recordsList = lectureRecordsService.findIsOrNotSubmitRecords(isSubmit);
		m.addAttribute("recordsList", recordsList);
		m.addAttribute("isSubmit", isSubmit==1?true:false);
		return "/lecturerecords/lecturerecordsSubmit";
	}
	
	/**
	 * 提交或取消提交听课记录
	 * @param isSubmit
	 * @param m
	 */
	@RequestMapping("submitLectureRecords")
	public void submitLectureRecords(String lessonPlanIdsStr,Boolean isSubmit,Model m){
		if(isSubmit){
			try {
				lectureRecordsService.submitLectureRecords(lessonPlanIdsStr);
				m.addAttribute("result", 0);
			} catch (Exception e) {
				m.addAttribute("result", 2);
				logger.error("提交听课记录出错", e);
			}
		}else{
			try {
				Integer flag = lectureRecordsService.unsubmitLectureRecords(lessonPlanIdsStr);
				m.addAttribute("result", flag);
			} catch (Exception e) {
				m.addAttribute("result", 2);
				logger.error("取消提交听课记录出错", e);
			}
		}
		
	}
	
	
}