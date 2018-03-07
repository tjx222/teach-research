/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.tmser.tr.activity.bo.Activity;
import com.tmser.tr.activity.service.ActivityService;
import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lecturerecords.bo.LectureRecords;
import com.tmser.tr.lecturerecords.service.LectureRecordsService;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.plainsummary.bo.PlainSummary;
import com.tmser.tr.plainsummary.service.PlainSummaryService;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.recordbag.dao.RecordbagDao;
import com.tmser.tr.recordbag.service.RecordService;
import com.tmser.tr.recordbag.service.RecordbagService;
import com.tmser.tr.thesis.bo.Thesis;
import com.tmser.tr.thesis.service.ThesisService;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserMenu;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.service.UserMenuService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.DateUtils;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * 成长档案袋 服务实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Recordbag.java, v 1.0 2015-04-13 Generate Tools Exp $
 */
@Service
@Transactional
public class RecordbagServiceImpl extends AbstractService<Recordbag, Integer> implements RecordbagService {

	private final int DEFULT_TYPE = 0;
	private final int DEFULT_DELETE = 0;
	private final int DEFULT_SHARE = 0;
	private final int DEFULT_STATUS = 0;
	private final int DEFULT_ORG_STATUS = 0;
	private final int DEFULT_SUBMIT = 0;
	private final int DEFULT_PINGLUN = 0;
	private final int DELETE = 1;
	private final int TYPE = 1;
	
	@Autowired
	private RecordService recordService;// 精选档案service

	@Autowired
	private ThesisService thesisService;// 获取教学文章

	@Autowired
	private ActivityService activityService;// 获取教研活动

	@Autowired
	private RecordbagDao recordbagDao;

	@Autowired
	private PlainSummaryService plainSummaryService;// 获取计划总结

	@Autowired
	private LectureRecordsService lectureRecordsService;// 获取听课记录

	@Autowired
	private LessonPlanService lpService; // 教案service

	@Autowired
	private BookService bookService;// 获取上下册书 service

	@Autowired
	private ResourcesService resService;// 获取文件的后缀

	@Autowired
	private UserMenuService userMenuService;

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Recordbag, Integer> getDAO() {
		return recordbagDao;
	}

	/**
	 * 
	 * @see com.tmser.tr.recordbag.service.RecordbagService#initRecordBags(int)
	 */
	@Override
	public void initRecordBags() {
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);

		Map<String, Integer> map = getUserMenuMap();
		int id = user.getId();
		String[] sys = new String[] { Recordbag.JXSJ, Recordbag.ZZKJ, Recordbag.JXFS, Recordbag.JYHD, Recordbag.JXWZ, Recordbag.JHZJ, Recordbag.TKJL };
		int sort = 1;
		
		Recordbag bag1 = new Recordbag();
		bag1.setTeacherId(String.valueOf(id));
		bag1.setType(DEFULT_TYPE);
		bag1.setGradeId(userSpace.getGradeId());
		bag1.setSubjectId(userSpace.getSubjectId());
		bag1.setOrgId(user.getOrgId());
		List<Recordbag> bags = recordbagDao.listAll(bag1);
		for (String str : sys) {
			if (map.get(str) == null) {
				continue;
			}
			bag1.setName(str);
			Date time = new Date();
			bag1.setMenuId(map.get(str));
			
			Recordbag temp = recordbagDao.getOne(bag1); 
			if (temp == null) {
					bag1.setCreateTime(time);
					bag1.setSort(sort);
					bag1.setShare(DEFULT_SHARE);
					bag1.setStatus(DEFULT_STATUS);
					bag1.setOrgStatus(DEFULT_ORG_STATUS);
					bag1.setSubmit(DEFULT_SUBMIT);
					bag1.setPinglun(DEFULT_PINGLUN);
					bag1.setIsPinglun(DEFULT_PINGLUN);
					bag1.setIsStatus(DEFULT_PINGLUN);
					bag1.setDelete(DEFULT_DELETE);
					bag1.setGrade(MetaUtils.getMeta(userSpace.getGradeId()).getName());
					bag1.setSubject(MetaUtils.getMeta(userSpace.getSubjectId()).getName());
					bag1.setResCount(0);
					recordbagDao.insert(bag1);
			} else {
				bags.remove(temp);
				if(DELETE == temp.getDelete()){
					temp.setDelete(DEFULT_DELETE);
					temp.setModifyTime(time);
					recordbagDao.update(temp);
				}
			}
			sort += 1;
		}
		Recordbag model = new Recordbag();
		for(Recordbag bag : bags){
			if(bag.getDelete() == DEFULT_DELETE){
				model.setId(bag.getId());
				model.setDelete(DELETE);
				recordbagDao.update(model);
			}
		}

	}

	// 根据用户的菜单名称选择相应的成长档案袋
	private Map<String, Integer> getUserMenuMap() {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		List<UserMenu> menuList = userMenuService.findUserMenuByUser(user.getId(), userSpace.getRoleId(), null);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (UserMenu um : menuList) {
			Menu m = um.getMenu();
			if (m.getName().equals("撰写教案")) {
				map.put(Recordbag.JXSJ, m.getId());
			} else if (m.getName().equals("上传课件")) {
				map.put(Recordbag.ZZKJ, m.getId());
			} else if (m.getName().equals("教学反思")) {
				map.put(Recordbag.JXFS, m.getId());
			} else if (m.getName().equals("集体备课")) {
				map.put(Recordbag.JYHD, m.getId());
			} else if (m.getName().equals("教学文章")) {
				map.put(Recordbag.JXWZ, m.getId());
			} else if (m.getName().equals("计划总结")) {
				map.put(Recordbag.JHZJ, m.getId());
			} else if (m.getName().equals("听课记录")) {
				map.put(Recordbag.TKJL, m.getId());
			}
		}
		return map;
	}

	/**
	 * @return
	 * @see com.tmser.tr.recordbag.service.RecordbagService#findAll()
	 */
	@Override
	public List<Recordbag> findAll() {
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Recordbag bag = new Recordbag();
		bag.setDelete(DEFULT_DELETE);
		bag.setTeacherId(String.valueOf(user.getId()));
		bag.setGradeId(userSpace.getGradeId());
		bag.setSubjectId(userSpace.getSubjectId());
		bag.addOrder("sort asc");
		return recordbagDao.listAll(bag);
	}

	@Override
	public List<Recordbag> findAll(String userID) {
		Recordbag bag = new Recordbag();
		bag.setDelete(DEFULT_DELETE);// 设置不删除
		bag.setShare(1);// 设置分享
		bag.setTeacherId(userID);
		bag.addOrder("sort asc");
		return recordbagDao.listAll(bag);
	}

	@Override
	public List<Recordbag> findAll(Recordbag rdmodel) {
		rdmodel.setDelete(DEFULT_DELETE);// 设置不删除
		rdmodel.setShare(1);// 设置分享
		rdmodel.addOrder("sort asc");
		return recordbagDao.listAll(rdmodel);
	}

	/**
	 * @param name
	 * @param desc
	 * @see com.tmser.tr.recordbag.service.RecordbagService#save(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Recordbag addBag(String name, String desc) {
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		int id = user.getId();
		Recordbag bag = new Recordbag();
		bag.setName(name);
		bag.setDesc(desc);
		bag.setTeacherId(String.valueOf(id));
		bag.setType(TYPE);
		bag.setShare(DEFULT_SHARE);
		bag.setStatus(DEFULT_STATUS);
		bag.setOrgStatus(DEFULT_ORG_STATUS);
		bag.setSubmit(DEFULT_SUBMIT);
		bag.setPinglun(DEFULT_PINGLUN);
		bag.setIsPinglun(DEFULT_PINGLUN);
		bag.setIsStatus(DEFULT_PINGLUN);
		bag.setGradeId(userSpace.getGradeId());
		bag.setGrade(MetaUtils.getMeta(userSpace.getGradeId()).getName());
		bag.setSubjectId(userSpace.getSubjectId());
		bag.setSubject(MetaUtils.getMeta(userSpace.getSubjectId()).getName());
		bag.setResCount(0);
		Date time = new Date();
		bag.setDelete(DEFULT_DELETE);
		bag.setCreateTime(time);
		Recordbag temp = new Recordbag();
		temp.addCustomCondition(" and sort=(select max(sort) from teacher_record_bag)", null);
		Recordbag i = recordbagDao.list(temp, 1).get(0);
		bag.setSort(i.getSort() + 1);
		bag.setOrgId(user.getOrgId());
		bag = recordbagDao.insert(bag);
		return bag;
	}

	@Override
	public void initPlan(LessonPlan plan, Recordbag bag, Model m) {
		String name = plan.getPlanName();
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		plan.setGradeId(userSpace.getGradeId());
		plan.setSubjectId(userSpace.getSubjectId());
		if (plan.getPlanName() != null && !plan.getPlanName().equals("")) {// 模糊查询
			m.addAttribute("name", plan.getPlanName());// 将查询条件带过去
			plan.addCustomCondition(" and planName like '%" + plan.getPlanName() + "%'", null);
			plan.setPlanName("");
		}
		if (bag.getName().equals(Recordbag.JXSJ)) {
			plan.setPlanType(ResTypeConstants.JIAOAN);
			plan.addCustomCulomn(
					" *,(select parent_id from commdity_book_chapter where chapter_id=lesson_id) as parent_id,(select chapter_level from commdity_book_chapter where chapter_id=lesson_id) as chapter_level,(select order_num from commdity_book_chapter where chapter_id=lesson_id) as order_num ");
			plan.addOrder("   fascicule_id desc,chapter_level desc,parent_id desc, order_num desc,lesson_id desc,order_value desc ");
		} else if (bag.getName().equals(Recordbag.ZZKJ)) {
			plan.setPlanType(ResTypeConstants.KEJIAN);
		} else if (bag.getName().equals(Recordbag.JXFS)) {
			// plan.setPlanType(ResTypeConstants.FANSI);
			StringBuffer sb = new StringBuffer(" and (planType = " + ResTypeConstants.FANSI + " or planType=" + ResTypeConstants.FANSI_OTHER + ")");
			if (name != null && !name.equals("")) {
				sb.append(" and planName like '%" + name + "%'");
			}
			plan.addCustomCondition(sb.toString(), null);
			plan.addOrder(" fasciculeId desc , planType asc ");
		}
	}

	@Override
	public void initActivity(LessonPlan plan, Recordbag bag, Model m, int id, List<Record> recordList, int type) {
		// TODO Auto-generated method stub
		Activity act = new Activity();
		if (plan.getPlanName() != null && !plan.getPlanName().equals("")) {// 模糊查询
			m.addAttribute("name", plan.getPlanName());// 将查询条件带过去
			// act.addCustomCondition(" and activityName like
			// '%"+plan.getPlanName()+"%'", null);
			act.setActivityName(SqlMapping.LIKE_PRFIX + "," + plan.getPlanName() + "," + SqlMapping.LIKE_PRFIX);
			// act.setActivityName("");
		}
		act.currentPage(plan.getPage().getCurrentPage());
		act.getPage().setPageSize(plan.getPage().getPageSize());
		PageList<Activity> actList = activityService.findOtherActivityList(act);

		for (Activity at : actList.getDatalist()) {
			String term = "";// 获得学期名称
			Integer termId = at.getTerm();// 获得学期Id
			if (termId == 0) {
				term = "上学期";
			} else {
				term = "下学期";
			}
			Record record = new Record();
			record.setResId(at.getId());
			record.setBagId(id);
			List<Record> list = recordService.find(record, 1);
			if (list == null || list.size() == 0)
				record.setStatus(0);
			else
				record.setStatus(1);
			record.setRecordName(at.getActivityName());
			record.setFlago("【" + term + "】【" + at.getTypeName() + "】");
			at.setFlago(term);
			at.setFlags(String.valueOf(record.getStatus()));
			record.setVolume(termId);
			record.setCreateTime(at.getCreateTime());
			String time = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
			record.setTime(time);
			record.setResType(0);
			recordList.add(record);
		}

		PageList<Record> reList = new PageList<Record>(recordList, actList.getPage());// 构造Record
		m.addAttribute("data", reList);// 按照分页进行查询
		m.addAttribute("activityList", actList);
		m.addAttribute("page", actList.getPage());// 分页
		m.addAttribute("id", id);
		m.addAttribute("type", type);
		m.addAttribute("name1", bag.getName());
	}

	@Override
	public void initThesis(LessonPlan plan, Recordbag bag, Model m, int id, List<Record> recordList, int type) {
		Thesis thesis = new Thesis();
		if (plan.getPlanName() != null && !plan.getPlanName().equals("")) {// 模糊查询
			m.addAttribute("name", plan.getPlanName());// 将查询条件带过去
			thesis.addCustomCondition(" and thesisTitle like '%" + plan.getPlanName() + "%'", null);
			thesis.setThesisTitle("");
		}
		// thesis.setSubjectId(userSpace.getSubjectId());
		thesis.setUserId(plan.getUserId());
		thesis.currentPage(plan.getPage().getCurrentPage());
		thesis.getPage().setPageSize(plan.getPage().getPageSize());
		PageList<Thesis> tList = thesisService.findCourseList(thesis);
		for (Thesis the : tList.getDatalist()) {
			String term = "";// 获得学期名称
			Integer termId = the.getSchoolTerm();// 获得学期Id
			if (termId == 0) {
				term = "上学期";
			} else {
				term = "下学期";
			}
			Record record = new Record();
			record.setResId(the.getId());
			record.setBagId(id);
			List<Record> list = recordService.find(record, 1);
			if (list == null || list.size() == 0)
				record.setStatus(0);
			else
				record.setStatus(1);
			record.setFlago("【" + term + "】【" + the.getThesisType() + "】");
			record.setRecordName(the.getThesisTitle());
			record.setCreateTime(the.getCrtDttm());
			String time = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
			record.setTime(time);
			record.setResType(0);
			record.setPath(the.getResId());
			recordList.add(record);
		}
		PageList<Record> reList = new PageList<Record>(recordList, tList.getPage());// 构造Record
		m.addAttribute("data", reList);// 按照分页进行查询
		m.addAttribute("thesisList", tList);
		m.addAttribute("page", tList.getPage());// 分页
		m.addAttribute("id", id);
		m.addAttribute("type", type);
		m.addAttribute("name1", bag.getName());
	}

	@Override
	public void initPlainSummary(LessonPlan plan, Recordbag bag, Model m, int id, List<Record> recordList, int type) {
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		PlainSummary model = new PlainSummary();
		model.setUserId(user.getId());
		model.setSubjectId(userSpace.getSubjectId());
		model.setGradeId(userSpace.getGradeId());
		// 获取用户的所有计划总结
		if (plan.getPlanName() != null && !plan.getPlanName().equals("")) {// 模糊查询
			m.addAttribute("name", plan.getPlanName());// 将查询条件带过去
			model.addCustomCondition(" and title like '%" + plan.getPlanName() + "%'", null);
			model.setTitle("");
		}
		model.currentPage(plan.getPage().getCurrentPage());
		model.getPage().setPageSize(plan.getPage().getPageSize());
		PageList<PlainSummary> plist = plainSummaryService.findByPage(model);
		for (PlainSummary the : plist.getDatalist()) {
			String term = "";// 获得学期名称
			Integer termId = the.getTerm();// 获得学期Id
			if (termId == 0) {
				term = "上学期";
			} else {
				term = "下学期";
			}
			Record record = new Record();
			record.setResId(the.getId());
			record.setBagId(id);
			List<Record> list = recordService.find(record, 1);
			if (list == null || list.size() == 0)
				record.setStatus(0);
			else
				record.setStatus(1);
			record.setRecordName(the.getTitle());
			record.setCreateTime(the.getCrtDttm());
			record.setFlago("【" + term + "】【" + switchCategory(the.getCategory(), the.getRoleId()) + "】");
			String time = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
			record.setTime(time);
			record.setResType(0);
			record.setPath(the.getContentFileKey());
			recordList.add(record);
		}
		PageList<Record> reList = new PageList<Record>(recordList, plist.getPage());// 构造Record
		m.addAttribute("data", reList);// 按照分页进行查询
		m.addAttribute("thesisList", plist);
		m.addAttribute("page", plist.getPage());// 分页
		m.addAttribute("id", id);
		m.addAttribute("type", type);
		m.addAttribute("name1", bag.getName());
	}

	private String switchCategory(int key, Integer roleId) {
		String str = "";
		// 1:个人计划；2：个人总结；3、当前身份计划；4、当前身份总结
		if (key == 1) {
			str = "个人计划";
		} else if (key == 2) {
			str = "个人总结";
		} else if (key == 3) {
			if (SysRole.XZ.getId().equals(roleId) || SysRole.ZR.getId().equals(roleId)) {
				str = "学校";
			} else if (SysRole.NJZZ.getId().equals(roleId)) {
				str = "年级";
			} else if (SysRole.XKZZ.getId().equals(roleId)) {
				str = "学科";
			} else if (SysRole.BKZZ.getId().equals(roleId)) {
				str = "备课";
			}
			str += "计划";
		} else if (key == 4) {
			if (SysRole.XZ.getId().equals(roleId) || SysRole.ZR.getId().equals(roleId)) {
				str = "学校";
			} else if (SysRole.NJZZ.getId().equals(roleId)) {
				str = "年级";
			} else if (SysRole.XKZZ.getId().equals(roleId)) {
				str = "学科";
			} else if (SysRole.BKZZ.getId().equals(roleId)) {
				str = "备课";
			}
			str += "总结";
		} else {
			str = "未找到该类型";
		}
		return str;
	}

	@Override
	public void initLecture(LessonPlan plan, Recordbag bag, Model m, int id, List<Record> recordList, int type) {
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		LectureRecords model = new LectureRecords();
		model.pageSize(10);// 设置每页的展示数
		model.setSchoolYear(schoolYear);// 当前学年
		model.setLecturepeopleId(user.getId());// 听课人ID
		model.setIsDelete(false);// 不删除
		model.setIsEpub(1);// 发布
		model.addOrder("epubTime desc");// 按照发布时间降序
		// 获取用户的所有计划总结
		if (plan.getPlanName() != null && !plan.getPlanName().equals("")) {// 模糊查询
			m.addAttribute("name", plan.getPlanName());// 将查询条件带过去
			model.addCustomCondition(" and topic like '%" + plan.getPlanName() + "%'", null);
			model.setTopic("");
		}
		int size = lectureRecordsService.count(model);
		model.currentPage(plan.getPage().getCurrentPage());
		model.getPage().setPageSize(plan.getPage().getPageSize());
		PageList<LectureRecords> plist = lectureRecordsService.findByPage(model);// 查询当前页的评论
		for (LectureRecords the : plist.getDatalist()) {
			Record record = new Record();
			record.setResId(the.getId());
			record.setBagId(id);
			List<Record> list = recordService.find(record, 1);
			if (list == null || list.size() == 0)
				record.setStatus(0);
			else
				record.setStatus(1);
			record.setRecordName(the.getTopic());
			record.setCreateTime(the.getLectureTime());
			String string = "校内";
			if (the.getType() == 1) {
				string = "校外";
			}
			String term = "上学期";
			if (the.getTerm() == 1) {
				term = "下学期";
			}
			record.setFlago("【" + term + "】【" + string + "】");
			if (record.getCreateTime() != null) {
				String time = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
				record.setTime(time);
			}
			record.setResType(0);
			Map<String, String> map = new HashMap<String, String>();
			map.put("teachPeople", the.getTeachingPeople());
			if (the.getType() == 1)
				map.put("grade", the.getGradeSubject());
			if (the.getGrade() != null && the.getSubject() != null)
				map.put("grade", the.getGrade() + the.getSubject());
			map.put("num", String.valueOf(the.getNumberLectures()));
			record.setExt(map);
			recordList.add(record);
		}
		int p = plist.getCurrentPage();
		Page page = new Page(15, p);
		page.setTotalCount(size);
		PageList<Record> reList = new PageList<Record>(recordList, page);// 构造Record
		m.addAttribute("data", reList);// 按照分页进行查询
		m.addAttribute("thesisList", plist);
		m.addAttribute("page", page);// 分页
		m.addAttribute("id", id);
		m.addAttribute("type", type);
		m.addAttribute("name1", bag.getName());
	}

	@Override
	public void savePlan(Integer one, Integer id, Integer type, String desc, Recordbag bag) {
		LessonPlan plan = lpService.findOne(one);
		Book book = bookService.findOne(plan.getBookId());
		String cebie = book.getFascicule();// 获得册别名称
		Integer cebieId = book.getFasciculeId();// 获得册别Id
		String grade = book.getGradeLevel();// 获得年级名称
		Record record = new Record();
		record.setBagId(id);
		record.setResType(type);
		record.setResId(plan.getPlanId());
		record.setStatus(1);
		record.setRecordName(plan.getPlanName());
		record.setFlago("【" + grade + cebie + "】");
		record.setVolume(cebieId);
		record.setCreateTime(new Date());
		String time = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
		record.setTime(time);
		record.setResType(Recordbag.switchResType(bag.getName()));
		record.setPath(plan.getResId());// 将资源id放入此字段中负责显示详情
		record.setDesc(desc.trim());
		record.setSchoolYear(CurrentUserContext.getCurrentSpace().getSchoolYear());
		record.setModifyTime(new Date());
		record.setShare(bag.getShare());
		addBagResCount(id);
		record.setUserId(CurrentUserContext.getCurrentUserId());
		recordService.save(record);
	}

	protected void addBagResCount(Integer bagId) {
		Recordbag bag = new Recordbag();
		bag.setId(bagId);
		bag.setModifyTime(new Date());
		bag.addCustomCulomn("resCount = resCount + 1");
		recordbagDao.update(bag);
	}

	protected void delBagResCount(Integer bagId) {
		Recordbag bag = new Recordbag();
		bag.setId(bagId);
		bag.setModifyTime(new Date());
		bag.addCustomCulomn("resCount = resCount - 1");
		recordbagDao.update(bag);
	}

	@Override
	public void saveActivity(Integer one, Integer id, Integer type, String desc, Recordbag bag) {
		Activity at = activityService.findOne(one);
		String term = "";// 获得学期名称
		Integer termId = at.getTerm();// 获得学期Id
		if (termId == 0) {
			term = "上学期";
		} else {
			term = "下学期";
		}
		Record record = new Record();
		record.setResId(at.getId());
		record.setStatus(1);
		record.setRecordName(at.getActivityName());
		record.setFlago("【" + term + "】【" + at.getTypeName() + "】");
		at.setFlago(term);
		at.setFlags(String.valueOf(record.getStatus()));
		record.setVolume(termId);
		record.setCreateTime(new Date());
		String time = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
		record.setTime(time);
		record.setBagId(id);
		record.setResType(Recordbag.switchResType(bag.getName()));
		record.setShare(bag.getShare());
		record.setDesc(desc.trim());
		record.setSchoolYear(CurrentUserContext.getCurrentSpace().getSchoolYear());
		record.setModifyTime(new Date());
		addBagResCount(id);
		record.setUserId(CurrentUserContext.getCurrentUserId());
		recordService.save(record);
	}

	@Override
	public void saveThesis(Integer one, Integer id, Integer type, String desc, Recordbag bag) {
		Thesis thesis = thesisService.findOne(one);
		Record record = new Record();
		record.setResId(thesis.getId());
		record.setStatus(1);
		record.setRecordName(thesis.getThesisTitle());
		record.setCreateTime(new Date());
		record.setVolume(thesis.getIsShare());
		record.setPath(thesis.getResId());// 将资源id放入此字段中负责显示详情
		String time = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
		record.setTime(time);
		record.setBagId(id);
		record.setResType(Recordbag.switchResType(bag.getName()));
		record.setDesc(desc.trim());
		record.setModifyTime(new Date());
		record.setSchoolYear(CurrentUserContext.getCurrentSpace().getSchoolYear());
		addBagResCount(id);
		record.setUserId(CurrentUserContext.getCurrentUserId());
		record.setShare(bag.getShare());
		recordService.save(record);
	}

	@Override
	public void saveLecture(Integer one, Integer id, Integer type, String desc, Recordbag bag) {
		LectureRecords plan = lectureRecordsService.findOne(one);
		Record record = new Record();
		record.setResId(plan.getId());
		record.setStatus(1);
		record.setRecordName(plan.getTopic());
		record.setCreateTime(new Date());
		record.setVolume(plan.getIsShare());
		// record.setPath(plan.getReplyUp());//将资源id放入此字段中负责显示详情
		String time = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
		record.setTime(time);
		record.setBagId(id);
		record.setResType(Recordbag.switchResType(bag.getName()));
		record.setDesc(desc.trim());
		record.setSchoolYear(CurrentUserContext.getCurrentSpace().getSchoolYear());
		record.setModifyTime(new Date());
		addBagResCount(id);
		record.setUserId(CurrentUserContext.getCurrentUserId());
		record.setShare(bag.getShare());
		recordService.save(record);
	}

	@Override
	public void saveSummary(Integer one, Integer id, Integer type, String desc, Recordbag bag) {
		PlainSummary plan = plainSummaryService.findOne(one);
		Record record = new Record();
		record.setResId(plan.getId());
		record.setStatus(1);
		record.setRecordName(plan.getTitle());
		record.setCreateTime(new Date());
		record.setVolume(plan.getIsShare());
		record.setPath(plan.getContentFileKey());// 将资源id放入此字段中负责显示详情
		String time = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
		record.setTime(time);
		record.setBagId(id);
		record.setResType(Recordbag.switchResType(bag.getName()));
		record.setDesc(desc.trim());
		record.setModifyTime(new Date());
		record.setSchoolYear(CurrentUserContext.getCurrentSpace().getSchoolYear());
		addBagResCount(id);
		record.setUserId(CurrentUserContext.getCurrentUserId());
		record.setShare(bag.getShare());
		recordService.save(record);
	}

	@Override
	public void saveLessonPlan(Record record) {
		LessonPlan plan = lpService.findOne(record.getResId());
		if (plan != null) {
			Book book = bookService.findOne(plan.getBookId());
			String cebie = book.getFascicule();// 获得册别名称
			String grade = book.getGradeLevel();// 获得年级名称
			Resources re = resService.findOne(plan.getResId());
			if (re != null && re.getExt() != null)
				record.setFlags(re.getExt());
			record.setFlago("【" + grade + cebie + "】");
			if (record.getDesc() != null)
				record.setDesc(record.getDesc().trim());
		}
	}

	@Override
	public int saveActivity(Record record, int flag, int id) {

		Activity at = activityService.findOne(record.getResId());
		if (at != null) {
			flag = 1;
			String term = "";// 获得学期名称
			Integer termId = at.getTerm();// 获得学期Id
			if (termId == 0) {
				term = "上学期";
			} else {
				term = "下学期";
			}
			record.setResId(at.getId());
			record.setFlago("【" + term + "】【" + at.getTypeName() + "】");
			record.setFlags(String.valueOf(at.getTypeId()));
			record.setVolume(termId);
			record.setCreateTime(new Date());
			record.setBagId(id);
			record.setResType(0);
			Map<String, String> map = new HashMap<String, String>();
			record.setExt(map);
			record.getExt().put("subjectName", at.getSubjectName());
			record.getExt().put("gradeName", at.getGradeName());
			record.getExt().put("mainUserName", at.getOrganizeUserName());
			record.getExt().put("typeId", String.valueOf(at.getTypeId()));
			record.getExt().put("typeName", at.getTypeName());
			String start = DateUtils.formatDate(at.getStartTime(), "MM-dd HH:mm");
			String end = "";
			if (at.getEndTime() != null)
				end = DateUtils.formatDate(at.getEndTime(), "MM-dd HH:mm");
			record.getExt().put("startTime", start);
			record.getExt().put("endTime", end);
			record.getExt().put("commentsNum", String.valueOf(at.getCommentsNum()));
			if (record.getDesc() != null)
				record.setDesc(record.getDesc().trim());
		}
		return flag;
	}

	@Override
	public void saveThesis(Record record, int id) {
		// TODO Auto-generated method stub

		Thesis thesis = thesisService.findOne(record.getResId());
		if (thesis != null) {
			String term = "";// 获得学期名称
			Integer termId = thesis.getSchoolTerm();// 获得学期Id
			if (termId == 0) {
				term = "上学期";
			} else {
				term = "下学期";
			}
			record.setResId(thesis.getId());
			record.setCreateTime(new Date());
			record.setBagId(id);
			record.setResType(0);
			record.setFlago("【" + term + "】【" + thesis.getThesisType() + "】");
			record.setFlags(thesis.getFileSuffix());
			if (record.getDesc() != null)
				record.setDesc(record.getDesc().trim());
		}

	}

	@Override
	public void savePlainSummary(Record record, int id) {
		PlainSummary ps = plainSummaryService.findOne(record.getResId());
		if (ps != null) {
			String term = "";// 获得学期名称
			Integer termId = ps.getTerm();// 获得学期Id
			if (termId == 0) {
				term = "上学期";
			} else {
				term = "下学期";
			}
			record.setResId(ps.getId());
			record.setCreateTime(new Date());
			record.setBagId(id);
			record.setResType(0);
			record.setFlago("【" + term + "】【" + switchCategory(ps.getCategory(), ps.getRoleId()) + "】");
			record.setFlags(ps.getContentFileType());
			if (record.getDesc() != null)
				record.setDesc(record.getDesc().trim());
		}

	}

	@Override
	public int saveLectureRecords(Record record, int flag, int id) {
		LectureRecords lr = lectureRecordsService.findOne(record.getResId());
		if (lr != null) {
			flag = 2;
			record.setResId(lr.getId());
			record.setBagId(id);
			record.setResType(0);
			record.setCreateTime(new Date());
			record.setChildType(lr.getType());
			String string = "校内";
			if (lr.getType() == 1) {
				string = "校外";
			}
			String term = "上学期";
			if (lr.getTerm() == 1) {
				term = "下学期";
			}
			record.setFlago("【" + term + "】【" + string + "】");
			String t = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
			record.setTime(t);
			Map<String, String> map = new HashMap<String, String>();
			map.put("teachPeople", lr.getTeachingPeople());
			if (lr.getType() == 1)
				map.put("grade", lr.getGradeSubject());
			if (lr.getGrade() != null && lr.getSubject() != null)
				map.put("grade", lr.getGrade() + lr.getSubject());
			map.put("num", String.valueOf(lr.getNumberLectures()));
			record.setExt(map);
			if (record.getDesc() != null)
				record.setDesc(record.getDesc().trim());

		}
		return flag;
	}

	/**
	 * @param rid
	 * @see com.tmser.tr.recordbag.service.RecordbagService#deleteBagRecord(java.lang.Integer)
	 */
	@Override
	public Record deleteBagRecord(Integer rid) {
		Record record = recordService.findOne(rid);
		if (record != null) {
			recordService.delete(rid);
			delBagResCount(record.getBagId());
		}
		return record;
	}

	/**
	 * @param record
	 * @return
	 * @see com.tmser.tr.recordbag.service.RecordbagService#saveSelfRecord(com.tmser.tr.recordbag.bo.Record)
	 */
	@Override
	public Record saveSelfRecord(Record record) {
		record.setUserId(CurrentUserContext.getCurrentUserId());
		recordService.saveSelfRecord(record);
		addBagResCount(record.getBagId());
		return record;
	}

	/**
	 * 
	 * @see com.tmser.tr.recordbag.service.RecordbagService#batchUpdateResCount()
	 */
	@Override
	public void batchUpdateResCount(Integer schoolYear) {
		recordbagDao.updateResCount(schoolYear, null, null);
	}
}
