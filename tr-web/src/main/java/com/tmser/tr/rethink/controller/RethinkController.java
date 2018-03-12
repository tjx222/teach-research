/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.rethink.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.MobileUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookChapterHerperService;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.meta.vo.BookLessonVo;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.rethink.service.RethinkService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

/**
 * <pre>
 * 教学反思相关功能操作Controller
 * </pre>
 *
 * @author tmser
 * @version $Id: RethinkController.java, v 1.0 2015年2月10日 下午2:46:12 tmser Exp $
 */
@Controller
@RequestMapping("/jy/rethink")
public class RethinkController extends AbstractController {

	private static final Logger logger = LoggerFactory
			.getLogger(RethinkController.class);

	@Resource
	private RethinkService rethinkService;
	@Resource
	private BookChapterHerperService bookChapterHerperService;
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private BookService bookService;
	@Resource
	private LessonPlanService lessonPlanService;

	/**
	 * 进入教学反思管理首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	@UseToken
	public String index(LessonPlan lp, Integer spaceId, Model m) {
		String bookId = lessonPlanService.filterCurrentBook(lp, spaceId);
		m.addAttribute("currentBookId", bookId);
		m.addAttribute("lessonId", lp.getLessonId());
		if (MobileUtils.isNormal()) {
			lp.pageSize(8);// 每页显示多少条
		} else {
			lp.pageSize(1000);// 每页显示多少条
		}
		m.addAttribute("editModel", lessonPlanService.findOne(lp.getPlanId()));
		lp.setPlanId(null);
		PageList<LessonPlan> lpList = rethinkService.findCourseList(lp);
		m.addAttribute("rethinkList", lpList);
		m.addAttribute("model", lp);
		if (StringUtils.isNotEmpty(bookId)) {
			List<Book> books = new ArrayList<Book>();
			Integer term = (Integer) WebThreadLocalUtils
					.getSessionAttrbitue(SessionKey.CURRENT_TERM);
			Book book = bookService.findOne(bookId);
			if (book.getFasciculeId() != 178) {
				String bookId2 = "";
				if (term == 0) {// 上学期
					if (book.getFasciculeId() == 176) {
						bookId2 = book.getRelationComId();
					} else {
						bookId2 = bookId;
						bookId = book.getRelationComId();
					}
					m.addAttribute("fasiciculeName", "上册书籍目录");
					m.addAttribute("fasiciculeName2", "下册书籍目录");
				} else {// 下学期
					if (book.getFasciculeId() == 177) {
						bookId2 = book.getRelationComId();
					} else {
						bookId2 = bookId;
						bookId = book.getRelationComId();
					}
					m.addAttribute("fasiciculeName2", "上册书籍目录");
					m.addAttribute("fasiciculeName", "下册书籍目录");
				}
				List<BookLessonVo> bookChapters = bookChapterHerperService
						.getBookChapterTreeByComId(bookId);
				m.addAttribute("bookChapters", bookChapters);
				books.add(bookService.findOne(bookId));
				if (StringUtils.isNotEmpty(bookId2)) {
					List<BookLessonVo> bookChapters2 = bookChapterHerperService
							.getBookChapterTreeByComId(bookId2);
					m.addAttribute("bookChapters2", bookChapters2);
					books.add(bookService.findOne(bookId2));
				}
			} else {
				List<BookLessonVo> bookChapters = bookChapterHerperService
						.getBookChapterTreeByComId(bookId);
				m.addAttribute("bookChapters", bookChapters);
				m.addAttribute("fasiciculeName", "全一册书籍目录");
				books.add(book);
			}
			m.addAttribute("books", books);
		}
		return "/rethink/rethinkIndex";
	}

	@RequestMapping("/dlog")
	public String index_d() {
		return "/rethink/dlog_rethink";
	}

	/**
	 * 通过书籍Id获得书籍章节目录的树形结构
	 */
	@ResponseBody
	@RequestMapping("/charpterTree")
	public List<BookLessonVo> charpterTree(
			@RequestParam(value = "lessonId") String bookId) {
		List<BookLessonVo> lessonList = null;
		if (bookId != null) {
			lessonList = bookChapterHerperService.getBookChapterByComId(bookId);
		}
		return lessonList;
	}

	/**
	 * 保存教学反思
	 */
	@RequestMapping("/save")
	@UseToken
	public void save(LessonPlan lp, Model m) {
		try {
			m.addAttribute("isOk", rethinkService.saveRethink(lp));
			m.addAttribute("planType", lp.getPlanType());
		} catch (Exception e) {
			logger.error("教学反思保存抛出异常...", e);
			m.addAttribute("isOk", false);
		}
	}

	/**
	 * 删除教学反思
	 */
	@RequestMapping("/delete")
	public void delete(LessonPlan lp, Model m) {
		Boolean isOk = true;
		try {
			isOk = rethinkService.deleteRethink(lp);
		} catch (Exception e) {
			isOk = false;
			logger.error("---删除反思失败---", e);
		}
		m.addAttribute("isOk", isOk);
		m.addAttribute("planType", lp.getPlanType());
	}

	/**
	 * 分享教学反思
	 */
	@RequestMapping("/sharing")
	public void sharing(LessonPlan lp, Model m) {
		Integer isOk = 0;
		try {
			Boolean state = rethinkService.sharingRethink(lp);
			if (!state) {
				isOk = 1;
			}
		} catch (Exception e) {
			isOk = 2;
			logger.error("---分享反思的相关操作失败---", e);
		}
		m.addAttribute("isOk", isOk);
		m.addAttribute("isShare", lp.getIsShare());
	}

	/**
	 * 通过文件Id得到文件对象
	 */
	@RequestMapping("/getFileById")
	public void getFileById(String resId, Model m) {
		Resources res = resourcesService.findOne(resId);
		m.addAttribute("res", res);
	}

	/**
	 * 提交教学反思之前的数据查询展示
	 */
	@RequestMapping("/preSubmit")
	public String preSubmit(@RequestParam(value = "isSubmit") Integer isSubmit,
			Model m) {
		Map<String, Object> submitDatas = rethinkService
				.getSubmitData(isSubmit);
		m.addAttribute("bookName", submitDatas.get("name"));
		m.addAttribute("bookName2", submitDatas.get("name2"));
		m.addAttribute("treeList", submitDatas.get("treeList"));
		m.addAttribute("dataMap", submitDatas.get("list"));
		m.addAttribute("treeList2", submitDatas.get("treeList2"));
		m.addAttribute("dataMap2", submitDatas.get("list2"));
		List<LessonPlan> lpList = rethinkService.getQTrethink(isSubmit);
		m.addAttribute("qtSubmitDatas", lpList);
		m.addAttribute("isSubmit", isSubmit);
		return "/rethink/rethinkSubmit";
	}

	/**
	 * 提交或者取消提交反思
	 */
	@RequestMapping("/submitRethink")
	public void submitRethink(
			@RequestParam(value = "isSubmit") String isSubmit,
			@RequestParam(value = "planIds") String planIds,
			@RequestParam(value = "qtFanSiIds") String qtFanSiIds, Model m) {
		Boolean isOk = true;
		try {
			isOk = rethinkService.submitRethink(isSubmit, planIds, qtFanSiIds);
		} catch (Exception e) {
			isOk = false;
			logger.error("---反思的提交相关操作失败---", e);
		}
		m.addAttribute("isOk", isOk);
	}

	/**
	 * 移动端反思提交页面
	 * 
	 * @param lp
	 * @param m
	 * @param page
	 * @return
	 */
	@RequestMapping("/submitIndex_mobile")
	public String submitIndex_mobile(LessonPlan lp,Integer spaceId, Model m) {
		lessonPlanService.filterCurrentBook(lp, spaceId);
		lp.pageSize(1000);
		PageList<LessonPlan> lpList = rethinkService.findCourseList(lp);
		m.addAttribute("rethinkList", lpList);
		m.addAttribute("planType", lp.getPlanType());
		m.addAttribute("lessonId", lp.getLessonId());
		return "/rethink/rethinkSubmit";
	}
}
